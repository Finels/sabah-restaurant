package com.fasteam.system.service;


import com.fasteam.common.orm.service.BaseService;
import com.fasteam.system.domain.SysResource;
import com.fasteam.system.dto.SysResourceNode;
import com.fasteam.system.query.SysResourceQuery;

import java.util.List;
import java.util.Set;

/**
 * 系统资源
 */
public interface SysResourceService extends BaseService<SysResource, SysResourceQuery> {
    List<SysResource> listUserResources(Set<Long> ids, Integer valid, Long fatherId, Integer level, Integer type);

    void save(SysResource sysResource);

    Boolean checkUniqueCode(Long id, String uniqueCode);

    List<SysResourceNode> createResourceTree(Long fatherId, Boolean flag, List<String> selectResources, String roleCode);

    List<SysResource> listResourceByUniqueCode(String uniqueCode);
}
