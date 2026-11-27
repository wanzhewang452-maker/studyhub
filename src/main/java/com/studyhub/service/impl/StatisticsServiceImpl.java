package com.studyhub.service.impl;

import com.studyhub.common.Result;
import com.studyhub.mapper.StatisticsMapper;
import com.studyhub.service.StatisticsService;
import com.studyhub.vo.StatisticsOverviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private StatisticsMapper statisticsMapper;
    @Override
    public StatisticsOverviewVO getStatistics(Integer userId) {
        return statisticsMapper.getStatisticsOverview(userId);
    }
}
