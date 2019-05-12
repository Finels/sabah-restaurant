package com.fasteam.system.service;

import com.fasteam.common.orm.service.BaseService;
import com.fasteam.system.domain.SysGroup;
import com.fasteam.system.dto.SysGroupNode;
import com.fasteam.system.dto.SysObjectFatherNode;
import com.fasteam.system.query.SysGroupQuery;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 系统用户组
 */
public interface SysGroupService extends BaseService<SysGroup, SysGroupQuery> {
    List<SysGroupNode> createTree();

    List<SysGroupNode> createTree(Long groupId);

    List<SysGroupNode> createTree(String groupName);

    void save(SysGroup sysGroup, MultipartFile topLogoFile) throws IOException;

    void delByCode(String code);

    Boolean checkGroupName(Long id, String groupName);

    SysObjectFatherNode getByFatherId(Long groupId, String userIds);

    SysObjectFatherNode getTopAndLowByFatherId(Long groupId, String userIds);
}
