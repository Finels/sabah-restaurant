package com.fasteam.app.dao;

import com.fasteam.app.domain.Dishes;
import com.fasteam.common.orm.dao.BaseDao;
import com.fasteam.common.query.BaseQuery;
import org.apache.ibatis.annotations.Param;


/**
 * Created by aaron on 2016/12/21.
 */
public interface DishesDao extends BaseDao<Dishes, BaseQuery> {

}
