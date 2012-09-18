package com.cabletech.station.services;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.cabletech.baseinfo.domainobjects.Region;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.station.dao.BaseDAO;

/**
 * �������м�վ����ҵ�������
 * 
 * @author yangjun
 * 
 */
public class BaseBO extends BaseBisinessObject {
    private static String CONTENT_TYPE = "application/vnd.ms-excel";

    protected BaseDAO baseDao;

    protected OracleIDImpl ora = new OracleIDImpl();

    /**
     * ����ִ�е�DAO������
     * 
     * @param baseDao
     *            BaseDAO Ҫִ�е�DAO������
     */
    public void setBaseDao(BaseDAO baseDao) {
        this.baseDao = baseDao;
    }

    /**
     * ���ݶ����ϵͳ��źͶ���������ж϶����Ƿ����
     * 
     * @param objectId
     *            String �����ϵͳ���
     * @param clazz
     *            Class ���������
     * @return boolean ���ض����Ƿ���ڵı��
     */
    public boolean queryExistById(String objectId, Class clazz) {
        boolean flag = false;

        try {
            Object object = baseDao.load(objectId, clazz);
            if (object != null) {
                flag = true;
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * ���ݶ�������͡�����Ψһ�����ƺ�Ψһ���ƶ�Ӧ�������ж϶����Ƿ����
     * 
     * @param objectClass
     *            String ���������
     * @param columnName
     *            String Ψһ���ƶ�Ӧ������
     * @param objectName
     *            String ����Ψһ������
     * @return boolean ���ض����Ƿ���ڵı��
     */
    public boolean queryExistByName(String objectClass, String columnName, String objectName) {
        boolean flag = false;

        try {
            String condition = " and " + columnName + "='" + objectName + "'";
            Object object = baseDao.find(objectClass, condition);
            if (object != null) {
                flag = true;
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * �������������б�
     * 
     * @return List �������������б���Ϣ
     * @throws Exception
     */
    public List queryRegion() throws Exception {
        String hql = " from Region where state='1'";
        List list = baseDao.getHibernateTemplate().find(hql);
        return list;
    }

    /**
     * �����������ݱ�Ŷ�ȡ�������ݵ�����
     * 
     * @param regionId
     *            String �������ݱ��
     * @return String �����������ݵ�����
     * @throws Exception
     */
    public String queryRegionName(String regionId) throws Exception {
        String hql = "from Region where state='1' and regionID='" + regionId + "'";
        List list = baseDao.getHibernateTemplate().find(hql);
        if (list != null && list.size() == 1) {
            return ((Region) list.get(0)).getRegionName();
        }
        return "";
    }

    /**
     * ��ȡ����Ѳ�����б���Ϣ
     * 
     * @param condition
     *            String ��ѯ����
     * @return List ��������Ѳ�����б���Ϣ
     * @throws Exception
     */
    public List queryPatrolMan(String condition) throws Exception {
        String hql = " from PatrolMan where (state is null or state<>'1') " + condition;
        List list = baseDao.getHibernateTemplate().find(hql);
        return list;
    }

    /**
     * ��ȡѲ���ն��б���Ϣ
     * 
     * @param condition
     *            String ��ѯ����
     * @return List ����Ѳ���ն��б���Ϣ
     * @throws Exception
     */
    public List queryTerminal(String condition) throws Exception {
        String hql = " from Terminal where (state is null or state<>'1') " + condition;
        List list = baseDao.getHibernateTemplate().find(hql);
        return list;
    }

    /**
     * �����û���Ŷ�ȡ�û�������
     * 
     * @param userId
     *            String �û����
     * @return String �����û�������
     * @throws Exception
     */
    public String queryUserName(String userId) throws Exception {
        // TODO Auto-generated method stub
        String hql = "from UserInfo where (state is null or state<>'1') and userID='" + userId
                + "'";
        List list = baseDao.getHibernateTemplate().find(hql);
        if (list != null && list.size() == 1) {
            return ((UserInfo) list.get(0)).getUserName();
        }
        return "";
    }


    /**
     * �����������ͷ
     * 
     * @param response
     *            HttpServletResponse �����Ӧͷ
     * @param fileName
     *            String �ļ�������
     * @throws UnsupportedEncodingException
     */
    protected void initResponse(HttpServletResponse response, String fileName)
            throws UnsupportedEncodingException {
        response.reset();
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Content-Disposition", "attachment;filename="
                + new String(fileName.getBytes(), "iso-8859-1"));
    }

}
