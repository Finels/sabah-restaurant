package com.fasteam.system.service.impl;

import com.fasteam.common.orm.dao.BaseDao;
import com.fasteam.common.orm.service.impl.BaseServiceImpl;
import com.fasteam.common.util.JavaUtil;
import com.fasteam.security.dto.LoginUser;
import com.fasteam.security.util.AuthUtil;
import com.fasteam.system.dao.SysRemarkDao;
import com.fasteam.system.domain.SysRemark;
import com.fasteam.system.query.SysRemarkQuery;
import com.fasteam.system.service.SysRemarkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 备忘
 */
@Service
public class SysRemarkServiceImpl extends BaseServiceImpl<SysRemark, SysRemarkQuery> implements SysRemarkService {

    private static final Logger LOG = LoggerFactory.getLogger(SysRemarkServiceImpl.class);

    @Autowired
    private SysRemarkDao sysRemarkDao;

    @Override
    public BaseDao<SysRemark, SysRemarkQuery> getDao() {
        return sysRemarkDao;
    }

    @Override
    public void save(SysRemark sysRemark, HttpServletRequest request) {
        LoginUser user = AuthUtil.getUser();
        Long uid;
        if (sysRemark.getId() == null) { //新增
            uid = JavaUtil.createId();
            sysRemark.setUid(uid);
            sysRemark.setCreateOperator(user.getId());
            sysRemarkDao.insert(sysRemark);
        } else { //修改
            uid = sysRemark.getUid();
            if (uid == null) {
                LOG.info("更新备忘时，uid不能为空!");
//                throw new InternalServerErrorException("更新备忘，uid不能为空!");
            }
            sysRemark.setUpdateOperator(user.getId());
            sysRemarkDao.update(sysRemark);
        }
    }

    @Override
    public List<SysRemark> listByPuid(Long puid) {
        return sysRemarkDao.listByPuid(puid);
    }
}
