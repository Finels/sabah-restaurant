package com.fasteam.common.orm.service.impl;

import com.fasteam.common.orm.dao.BaseDao;
import com.fasteam.common.orm.dto.Pagination;
import com.fasteam.common.orm.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 基础服务类
 *
 * @param <T>
 * @param <V>
 */
public abstract class BaseServiceImpl<T, V> implements BaseService<T, V> {
    @Autowired
    private BaseDao<T, V> baseDao;

    public BaseDao<T, V> getDao() {
        return baseDao;
    }

    @Override
    public T getById(Long id) {
        return this.getDao().getById(id);
    }

    @Override
    public T getByUid(Long uid) {
        return this.getDao().getByUid(uid);
    }

    @Override
    public List<T> listByIds(List ids) {
        return this.getDao().listByIds(ids);
    }

    @Override
    public List<T> listByUids(List uids) {
        return this.getDao().listByUids(uids);
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    @Override
    public List<T> list() {
        return this.getDao().list();
    }

    @Override
    public List<T> listByQuery(V query) {
        return this.getDao().listByQuery(query);
    }

    @Override
    public Pagination<T> listByPage(V query, int pageNo, int pageSize) {
        Pagination<T> page = new Pagination<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);

        List<T> list = this.getDao().listByPage(query, page);
        long total = 0;
        if (!list.isEmpty()) {
            total = this.getDao().count(query);
        }
        page.setContents(list);
        page.setTotalCount(total);
        return page;
    }

    @Override
    public long count(V query) {
        return this.getDao().count(query);
    }

    @Override
    public void add(T entity) {
        this.getDao().insert(entity);
    }

    @Override
    public void batchAdd(List<T> entities) {
        this.getDao().batchInsert(entities);
    }

    @Override
    public void edit(T entity) {
        this.getDao().update(entity);
    }

    @Override
    public void delById(Long id) {
        this.getDao().delById(id);
    }

    @Override
    public void delByUid(Long uid) {
        this.getDao().delByUid(uid);
    }

    @Override
    public void batchDel(List ids) {
        this.getDao().batchDel(ids);
    }

    @Override
    public void batchDelByUids(List uids) {
        this.getDao().batchDelByUids(uids);
    }
}
