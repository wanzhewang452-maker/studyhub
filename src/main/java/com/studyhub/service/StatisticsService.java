package com.studyhub.service;

import com.studyhub.common.Result;
import com.studyhub.vo.StatisticsOverviewVO;

public interface StatisticsService {
    StatisticsOverviewVO getStatistics(Integer userId);
}
