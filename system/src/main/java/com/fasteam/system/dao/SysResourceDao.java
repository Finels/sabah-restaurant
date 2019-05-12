package com.fasteam.system.dao;

import com.fasteam.common.orm.dao.BaseDao;
import com.fasteam.system.domain.SysResource;
import com.fasteam.system.query.SysResourceQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 系统资源dao
 */
public interface SysResourceDao extends BaseDao<SysResource, SysResourceQuery> {
    List<SysResource> listUserResources(@Param("ids") Set<Long> ids, @Param("valid") Integer valid,
                                        @Param("fatherId") Long fatherId, @Param("level") Integer level, @Param("type") Integer type);

    int countSysResourceByUniqueCode(@Param("id") Long id, @Param("uniqueCode") String uniqueCode);

    List<SysResource> listResourceByUniqueCode(@Param("uniqueCode") String uniqueCode);
}
