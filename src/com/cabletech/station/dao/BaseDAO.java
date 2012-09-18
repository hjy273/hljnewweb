package com.cabletech.station.dao;

import java.util.List;

import com.cabletech.commons.hb.HibernateDaoSupport;

/**
 * 基本DAO处理的抽象类
 * 
 * @author yangjun
 * 
 */
public abstract class BaseDAO extends HibernateDaoSupport {

    /**
     * 根据对象的系统编号载入对象实体
     * 
     * @param objectId
     *            String 对象的系统编号
     * @param clazz
     *            Class 对象的类型
     * @return Object 返回对象实体
     * @throws Exception
     */
    public Object load(String objectId, Class clazz) throws Exception {
        return super.getHibernateTemplate().load(clazz, objectId);
    }

    /**
     * 根据对象的类型和查询条件获取对象实体
     * 
     * @param objectClass
     *            String 对象的类型字符串
     * @param condition
     *            Object 查询条件
     * @return Object 返回对象实体
     * @throws Exception
     */
    public Object find(String objectClass, Object condition) throws Exception {
        String hql = " from " + objectClass + " where 1=1 " + condition;
        List list = super.getHibernateTemplate().find(hql);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据对象的类型和查询条件获取所有的对象实体列表
     * 
     * @param objectClass
     *            String 对象的类型字符串
     * @param condition
     *            Object 查询条件
     * @return List 返回所有的对象实体列表
     * @throws Exception
     */
    public List findAll(String objectClass, Object condition) throws Exception {
        String hql = " from " + objectClass + " where 1=1 " + condition;
        List list = super.getHibernateTemplate().find(hql);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    /**
     * 插入对象
     * 
     * @param object
     *            Object 要插入的对象
     * @return Object 返回插入的对象
     * @throws Exception
     */
    public Object insert(Object object) throws Exception {
        super.getHibernateTemplate().save(object);
        return object;
    }

    /**
     * 更新对象
     * 
     * @param object
     *            Object 要更新的对象
     * @return Object 返回更新的对象
     * @throws Exception
     */
    public Object update(Object object) throws Exception {
        super.getHibernateTemplate().saveOrUpdate(object);
        return object;
    }

    /**
     * 删除对象
     * 
     * @param object
     *            Object 要删除的对象
     * @return Object 返回删除的对象
     * @throws Exception
     */
    public Object delete(Object object) throws Exception {
        super.getHibernateTemplate().delete(object);
        return object;
    }

    /**
     * 根据查询条件获取对象的列表信息
     * 
     * @param conditionString
     *            String 查询条件
     * @return List 返回对象的列表信息
     * @throws Exception
     */
    public abstract List queryByCondition(String conditionString) throws Exception;
}
