package com.fasteam.system.service;


import com.fasteam.common.orm.service.BaseService;
import com.fasteam.system.domain.SysRemark;
import com.fasteam.system.query.SysRemarkQuery;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 备忘
 */
public interface SysRemarkService extends BaseService<SysRemark, SysRemarkQuery> {
    void save(SysRemark sysRemark, HttpServletRequest request);
    List<SysRemark> listByPuid(Long puid);
}
