package com.fasteam.system.dao;

import com.fasteam.common.orm.dao.BaseDao;
import com.fasteam.common.query.BaseQuery;
import com.fasteam.system.domain.BaseAreaCode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by aaron on 2016/12/21.
 */
public interface BaseAreaCodeDao extends BaseDao<BaseAreaCode, BaseQuery> {
    List<BaseAreaCode> listCityByPCode(@Param("pCode") String pCode);

    List<BaseAreaCode> listCityByLevel(@Param("level") Integer level);
}
