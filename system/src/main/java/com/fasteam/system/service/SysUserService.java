package com.fasteam.system.service;

import com.fasteam.common.orm.service.BaseService;
import com.fasteam.security.dto.LoginUser;
import com.fasteam.system.domain.SysUser;
import com.fasteam.system.query.SysUserQuery;

import java.util.List;

/**
 * Created by hp on 2017/9/12.
 */
public interface SysUserService extends BaseService<SysUser, SysUserQuery> {
    SysUser getUserByUsernameAndPwd(String username, String password);

    SysUser getUserByUsernameAndPwdWithoutMd5(String username, String password);

    Boolean checkUsername(String username);

    Boolean checkUserHadDel(String username);

    List<SysUser> listGroupUsers(Long groupId);

    List<SysUser> listLowGroupUsers(String groupCode);

    List<SysUser> listSuperGroupUsers(String groupCodes);

    void save(SysUser sysUser);

    void updateInfo(SysUser sysUser);

    void editPassword(Long id, String password);

    void editScore(Long userId, Integer score, String remark, String ip, Long ouserId);

    void editScore(Long userId, Integer score, String remark, Long ouserId);

    void activateUser(String username);

    void registerUser(SysUser sysUser);

    void saveLayout(String sortIds);

    void updateRole(String code, String name, Long roleId);

    LoginUser getByIdCard(String idCard);

    void resetPassword(Long beginId, Long endId);
}