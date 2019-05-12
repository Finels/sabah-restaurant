package com.fasteam.system.dao;

import com.fasteam.common.orm.dao.BaseDao;
import com.fasteam.system.domain.SysGroup;
import com.fasteam.system.query.SysGroupQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户组dao
 */
public interface SysGroupDao extends BaseDao<SysGroup, SysGroupQuery> {

    int countByGroupName(@Param("id") Long id, @Param("groupName") String groupName);

    void updateCode(@Param("code") String code, @Param("id") Long id);

    void updateLogo(@Param("topLogo") String topLogo, @Param("id") Long id);

    void delByCode(@Param("code") String code, @Param("separator") String separator);

    List<SysGroup> getByFatherId(@Param("fatherId") Long fatherId);

    SysGroup getByCode(@Param("code") String code);
}
