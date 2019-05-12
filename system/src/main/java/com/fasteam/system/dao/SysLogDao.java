package com.fasteam.system.dao;

import com.fasteam.common.orm.dao.BaseDao;
import com.fasteam.system.domain.SysLog;
import com.fasteam.system.query.SysLogQuery;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 日志dao
 */
public interface SysLogDao extends BaseDao<SysLog, SysLogQuery> {
    List<SysLog> listByUserId(@Param("userId") Long userId);

    List<Integer> listActiveUserIds(@Param("activeNum") Integer activeNum, @Param("startTime") String startTime, @Param("endTime") String endTime);

    int countLoginSysLog(@Param("userId") Long userId, @Param("timeStart") Date timeStart);
}
