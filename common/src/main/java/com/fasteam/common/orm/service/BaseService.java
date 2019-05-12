package com.fasteam.common.orm.service;


import com.fasteam.common.orm.dto.Pagination;

import java.util.List;

/**
 * 基础服务接口
 *
 * @param <T>
 * @param <V>
 */
public interface BaseService<T, V> {

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    T getById(Long id);

    /**
     * 根据uid查询数据
     *
     * @param uid
     * @return
     */
    T getByUid(Long uid);

    /**
     * 根据Ids查询
     *
     * @param ids
     * @return
     */
    List<T> listByIds(List ids);

    /**
     * 根据uids查询数据
     *
     * @param uids
     * @return
     */
    List<T> listByUids(List uids);

    /**
     * 查询所有数据
     *
     * @return
     */
    List<T> list();

    /**
     * 查询
     *
     * @param query
     * @return
     */
    List<T> listByQuery(V query);

    /**
     * 分页查询
     *
     * @param query
     * @param pageNo
     * @param pageSize
     * @return
     */
    Pagination<T> listByPage(V query, int pageNo, int pageSize);

    /**
     * 数据统计
     *
     * @param query
     * @return
     */
    long count(V query);

    /**
     * 新增
     *
     * @param entity
     */
    void add(T entity);

    /**
     * 批量新增
     *
     * @param entities
     */
    void batchAdd(List<T> entities);

    /**
     * 更新
     *
     * @param entity
     */
    void edit(T entity);

    /**
     * 根据Id删除
     *
     * @param id
     */
    void delById(Long id);

    /**
     * 根据UID删除数据
     *
     * @param uid
     */
    void delByUid(Long uid);

    /**
     * 根据ids批量删除
     *
     * @param ids
     */
    void batchDel(List ids);

    /**
     * 根据uids批量删除
     *
     * @param uids
     */
    void batchDelByUids(List uids);
}
