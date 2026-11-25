package com.studyhub.common;

import lombok.Data;

/**
 * 统一响应结果类
 * @param <T> 数据类型
 */
@Data
public class Result<T> {

    // 状态码
    private int code;
    // 提示信息
    private String message;
    // 返回数据
    private T data;

    // 全参构造器（Lombok的@Data默认生成的是无参构造，如果你需要全参，手动加上或者加 @AllArgsConstructor）
    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    // ================== 常用静态方法，方便调用 ==================

    /**
     * 成功，无返回数据（比如删除成功、修改成功）
     */
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    /**
     * 成功，带返回数据（比如登录成功返回Token，查询成功返回List）
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    /**
     * 成功，自定义提示信息并带数据
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }

    /**
     * 失败，默认状态码 500（比如系统内部异常）
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(500, message, null);
    }

    /**
     * 失败，自定义状态码（比如未登录 401，权限不足 403，参数错误 400）
     */
    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }
}