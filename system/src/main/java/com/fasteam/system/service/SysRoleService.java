package com.fasteam.system.service;


import com.fasteam.common.orm.service.BaseService;
import com.fasteam.system.domain.SysRole;
import com.fasteam.system.query.SysRoleQuery;

import java.util.List;
import java.util.Map;

/**
 * 系统角色
 */
public interface SysRoleService extends BaseService<SysRole, SysRoleQuery> {
    List<SysRole> listByGroupCode(String groupCode);

    Boolean checkRoleName(Long id, String name);

    void save(SysRole sysRole);

    void saveAuthorization(SysRole sysRole);

    List<Map<String, Object>> listFindData();
}
