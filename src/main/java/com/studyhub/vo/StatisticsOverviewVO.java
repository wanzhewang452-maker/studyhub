package com.studyhub.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StatisticsOverviewVO {
    private Long totalStudyMinutes;
    private Long totalRecordCount;
    private Long totalGoalCount;
    private Long completedGoalCount;
    private Long totalTargetMinutes;
    private Long totalCompletedMinutes;
    private BigDecimal overallProgress;
}
