package com.fasteam.system.dao;

import com.fasteam.common.orm.dao.BaseDao;
import com.fasteam.system.domain.SysRemark;
import com.fasteam.system.query.SysRemarkQuery;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 备忘录dao
 */
public interface SysRemarkDao extends BaseDao<SysRemark, SysRemarkQuery> {
    List<SysRemark> listByPuid(Long puid);
    void save(SysRemark sysRemark, HttpServletRequest request);
}
