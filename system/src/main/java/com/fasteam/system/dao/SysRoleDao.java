package com.fasteam.system.dao;

import com.fasteam.common.orm.dao.BaseDao;
import com.fasteam.system.domain.SysRole;
import com.fasteam.system.query.SysRoleQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统角色dao
 */
public interface SysRoleDao extends BaseDao<SysRole, SysRoleQuery> {
    int countByName(@Param("id") Long id, @Param("name") String name);

    List<SysRole> listByGroupCode(@Param("groupCode") String groupCode);

    void updateAuthorization(@Param("entity") SysRole entity);

    List<Map<String, Object>> listFindData();
}
