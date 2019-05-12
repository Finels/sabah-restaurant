package com.fasteam.system.dao;

import com.fasteam.common.orm.dao.BaseDao;
import com.fasteam.common.query.BaseQuery;
import com.fasteam.system.domain.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by aaron on 2016/12/21.
 */
public interface MessageDao extends BaseDao<Message, BaseQuery> {


    List<Message> listTop(@Param("top") Integer top);

    int countNoReadByUser(@Param("userId") Long userId);

    void updateReadUserIds(@Param("userId") String userId);
}
