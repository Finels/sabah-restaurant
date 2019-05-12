package com.fasteam.system.dao;


import com.fasteam.common.orm.dao.BaseDao;
import com.fasteam.security.dto.LoginUser;
import com.fasteam.system.domain.SysUser;
import com.fasteam.system.query.SysUserQuery;
import org.apache.ibatis.annotations.Param;


import java.util.List;


/**
 * 用户dao
 * Created by Administrator on 2017/9/11.
 */
public interface SysUserDao extends BaseDao<SysUser, SysUserQuery> {
    LoginUser getByUsername(String username);

    SysUser getByUsernameAndPwd(@Param("username") String username, @Param("password") String password);

    LoginUser getByIdCard(String idCard);

    String getKeyTypeIdsById(@Param("id") Long id);

    List<SysUser> listGroupUsers(@Param("groupId") Long groupId);

    List<SysUser> listLowGroupUsers(@Param("groupCode") String groupCode);

    List<SysUser> listSuperGroupUsers(@Param("groupCodes") String groupCodes);

    List<SysUser> listByRoleId(@Param("roleId") Long roleId);

    int countByUsername(@Param("username") String username, @Param("logicDel") Integer logicDel);

    void updateInfo(@Param("entity") SysUser entity);

    void registerUser(@Param("entity") SysUser entity);

    void updatePassword(@Param("id") Long id, @Param("password") String password);

    void activateUser(@Param("password") String password, @Param("groupId") Long groupId, @Param("username") String username);

    void updateKeyTypeIdsById(@Param("keyTypeIds") String keyTypeIds, @Param("id") Long id);

    void updateScoreByUserId(@Param("score") Integer score, @Param("id") Long id);

    void updateRole(@Param("roleCodes") String roleCodes, @Param("roleNames") String roleNames, @Param("id") Long id);

    /**
     * 逻辑删除
     *
     * @param groupCode
     */
    void delByGroupCode(@Param("groupCode") String groupCode);
}
