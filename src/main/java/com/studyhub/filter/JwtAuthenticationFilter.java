package com.studyhub.filter;

import com.studyhub.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
@Slf4j
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // 1. 获取请求头
        String authorization =
                request.getHeader("Authorization");
        log.info("JWT过滤器执行，请求路径：{}", request.getRequestURI());
        // 2. 判断是否携带 JWT
        if (authorization == null ||
                !authorization.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        // 3. 提取 JWT
        String token = authorization.substring(7);

        try {

            // 4. 验证 JWT 并获取用户 ID
            Integer userId = jwtUtil.getUserId(token);

            // 5. 创建认证对象
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userId,
                            null,
                            Collections.emptyList()
                    );

            // 6. 创建安全上下文
            SecurityContext context =
                    SecurityContextHolder.createEmptyContext();

            context.setAuthentication(authentication);

            // 7. 保存安全上下文
            SecurityContextHolder.setContext(context);

        } catch (JwtException | IllegalArgumentException e) {

            // JWT 验证失败，清除认证身份
            SecurityContextHolder.clearContext();
        }

        // 8. 继续执行过滤器链
        filterChain.doFilter(request, response);
    }
}