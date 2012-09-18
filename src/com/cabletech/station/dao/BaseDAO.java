package com.cabletech.station.dao;

import java.util.List;

import com.cabletech.commons.hb.HibernateDaoSupport;

/**
 * ����DAO����ĳ�����
 * 
 * @author yangjun
 * 
 */
public abstract class BaseDAO extends HibernateDaoSupport {

    /**
     * ���ݶ����ϵͳ����������ʵ��
     * 
     * @param objectId
     *            String �����ϵͳ���
     * @param clazz
     *            Class ���������
     * @return Object ���ض���ʵ��
     * @throws Exception
     */
    public Object load(String objectId, Class clazz) throws Exception {
        return super.getHibernateTemplate().load(clazz, objectId);
    }

    /**
     * ���ݶ�������ͺͲ�ѯ������ȡ����ʵ��
     * 
     * @param objectClass
     *            String ����������ַ���
     * @param condition
     *            Object ��ѯ����
     * @return Object ���ض���ʵ��
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
     * ���ݶ�������ͺͲ�ѯ������ȡ���еĶ���ʵ���б�
     * 
     * @param objectClass
     *            String ����������ַ���
     * @param condition
     *            Object ��ѯ����
     * @return List �������еĶ���ʵ���б�
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
     * �������
     * 
     * @param object
     *            Object Ҫ����Ķ���
     * @return Object ���ز���Ķ���
     * @throws Exception
     */
    public Object insert(Object object) throws Exception {
        super.getHibernateTemplate().save(object);
        return object;
    }

    /**
     * ���¶���
     * 
     * @param object
     *            Object Ҫ���µĶ���
     * @return Object ���ظ��µĶ���
     * @throws Exception
     */
    public Object update(Object object) throws Exception {
        super.getHibernateTemplate().saveOrUpdate(object);
        return object;
    }

    /**
     * ɾ������
     * 
     * @param object
     *            Object Ҫɾ���Ķ���
     * @return Object ����ɾ���Ķ���
     * @throws Exception
     */
    public Object delete(Object object) throws Exception {
        super.getHibernateTemplate().delete(object);
        return object;
    }

    /**
     * ���ݲ�ѯ������ȡ������б���Ϣ
     * 
     * @param conditionString
     *            String ��ѯ����
     * @return List ���ض�����б���Ϣ
     * @throws Exception
     */
    public abstract List queryByCondition(String conditionString) throws Exception;
}
