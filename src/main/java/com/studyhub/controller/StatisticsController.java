package com.studyhub.controller;

import com.studyhub.common.Result;
import com.studyhub.service.StatisticsService;
import com.studyhub.vo.StatisticsOverviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/overview")
    public Result<StatisticsOverviewVO> getStatistics(@AuthenticationPrincipal Integer userId) {
        return Result.success(statisticsService.getStatistics(userId));
    }
}
