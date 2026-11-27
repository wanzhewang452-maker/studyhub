package com.studyhub.mapper;

import com.studyhub.vo.StatisticsOverviewVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StatisticsMapper {
    StatisticsOverviewVO getStatisticsOverview(@Param("userId") Integer userId);
}
