package com.fasteam.system.service.impl;

import com.fasteam.common.orm.dao.BaseDao;
import com.fasteam.common.orm.service.impl.BaseServiceImpl;
import com.fasteam.system.dao.DictionaryDao;
import com.fasteam.system.domain.Dictionary;
import com.fasteam.system.query.DictionaryQuery;
import com.fasteam.system.service.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 数据字典
 * Created by Administrator on 2016/12/26.
 */
@Service
public class DictionaryServiceImpl extends BaseServiceImpl<Dictionary, DictionaryQuery> implements DictionaryService {
    private static final Logger LOG = LoggerFactory.getLogger(DictionaryServiceImpl.class);
    @Autowired
    private DictionaryDao dictionaryDao;

    @Override
    public BaseDao<Dictionary, DictionaryQuery> getDao() {
        return dictionaryDao;
    }

}
