package com.fasteam.common.orm.dao;

import com.fasteam.common.orm.dto.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * dao基础接口
 *
 * @param <T>
 * @param <V>
 */
public interface BaseDao<T, V> {

    /**
     * 根据id查询数据
     *
     * @param id
     * @return
     */
    T getById(@Param("id") Long id);

    /**
     * 根据uid查询数据
     *
     * @param uid
     * @return
     */
    T getByUid(@Param("uid") Long uid);

    /**
     * 根据ids查询数据
     *
     * @param ids
     * @return
     */
    List<T> listByIds(@Param("ids") List ids);

    /**
     * 根据uids查询数据
     *
     * @param uids
     * @return
     */
    List<T> listByUids(@Param("uids") List uids);

    /**
     * 查询所有数据
     *
     * @return
     */
    List<T> list();

    /**
     * 普通查询
     *
     * @param query
     * @return
     */
    List<T> listByQuery(@Param("query") V query);

    /**
     * 分页查询
     *
     * @param query
     * @param page
     * @return
     */
    List<T> listByPage(@Param("query") V query, @Param("page") Pagination page);

    /**
     * 统计
     *
     * @param query
     * @return
     */
    long count(@Param("query") V query);

    /**
     * 新增
     *
     * @param entity
     */
    void insert(@Param("entity") T entity);

    /**
     * 批量新增
     *
     * @param entities
     */
    void batchInsert(@Param("entities") List<T> entities);

    /**
     * 更新
     *
     * @param entity
     */
    void update(@Param("entity") T entity);

    /**
     * 根据ID删除数据
     *
     * @param id
     */
    void delById(@Param("id") Long id);

    /**
     * 根据UID删除数据
     *
     * @param uid
     */
    void delByUid(@Param("uid") Long uid);

    /**
     * 批量删除
     *
     * @param ids
     */
    void batchDel(@Param("ids") List ids);

    /**
     * 批量删除
     *
     * @param uids
     */
    void batchDelByUids(@Param("uids") List uids);
}
