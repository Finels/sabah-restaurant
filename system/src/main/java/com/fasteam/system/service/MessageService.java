package com.fasteam.system.service;


import com.fasteam.common.orm.service.BaseService;
import com.fasteam.common.query.BaseQuery;
import com.fasteam.system.domain.Message;

import java.util.List;

/**
 * Created by aaron on 2016/12/26.
 */
public interface MessageService extends BaseService<Message, BaseQuery> {
    /**
     * 查询前{top}条记录
     *
     * @param top
     * @return
     */
    List<Message> listTop(Integer top);

    int countNoReadByUser(Long userId);

    void updateReadUserIds(Long userId);
}
