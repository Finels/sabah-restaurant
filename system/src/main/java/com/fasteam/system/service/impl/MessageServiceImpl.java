package com.fasteam.system.service.impl;

import com.fasteam.common.orm.dao.BaseDao;
import com.fasteam.common.orm.service.impl.BaseServiceImpl;
import com.fasteam.common.query.BaseQuery;
import com.fasteam.system.dao.MessageDao;
import com.fasteam.system.domain.Message;
import com.fasteam.system.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/12/26.
 */
@Service
public class MessageServiceImpl extends BaseServiceImpl<Message, BaseQuery> implements MessageService {
    @Autowired
    private MessageDao messageDao;

    @Override
    public BaseDao<Message, BaseQuery> getDao() {
        return messageDao;
    }

    @Override
    public List<Message> listTop(Integer top) {
        return this.messageDao.listTop(top);
    }

    @Override
    public int countNoReadByUser(Long userId) {
        return this.messageDao.countNoReadByUser(userId);
    }

    @Override
    public void updateReadUserIds(Long userId) {
        this.messageDao.updateReadUserIds("," + userId + ",");
    }
}
