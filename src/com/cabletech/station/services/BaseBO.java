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
 * 基本的中继站管理业务操作类
 * 
 * @author yangjun
 * 
 */
public class BaseBO extends BaseBisinessObject {
    private static String CONTENT_TYPE = "application/vnd.ms-excel";

    protected BaseDAO baseDao;

    protected OracleIDImpl ora = new OracleIDImpl();

    /**
     * 设置执行的DAO操作类
     * 
     * @param baseDao
     *            BaseDAO 要执行的DAO操作类
     */
    public void setBaseDao(BaseDAO baseDao) {
        this.baseDao = baseDao;
    }

    /**
     * 根据对象的系统编号和对象的类型判断对象是否存在
     * 
     * @param objectId
     *            String 对象的系统编号
     * @param clazz
     *            Class 对象的类型
     * @return boolean 返回对象是否存在的标记
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
     * 根据对象的类型、对象唯一的名称和唯一名称对应的列名判断对象是否存在
     * 
     * @param objectClass
     *            String 对象的类型
     * @param columnName
     *            String 唯一名称对应的列名
     * @param objectName
     *            String 对象唯一的名称
     * @return boolean 返回对象是否存在的标记
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
     * 载入所属地州列表
     * 
     * @return List 返回所属地州列表信息
     * @throws Exception
     */
    public List queryRegion() throws Exception {
        String hql = " from Region where state='1'";
        List list = baseDao.getHibernateTemplate().find(hql);
        return list;
    }

    /**
     * 根据所属地州编号读取所属地州的名称
     * 
     * @param regionId
     *            String 所属地州编号
     * @return String 返回所属地州的名称
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
     * 读取所属巡检组列表信息
     * 
     * @param condition
     *            String 查询条件
     * @return List 返回所属巡检组列表信息
     * @throws Exception
     */
    public List queryPatrolMan(String condition) throws Exception {
        String hql = " from PatrolMan where (state is null or state<>'1') " + condition;
        List list = baseDao.getHibernateTemplate().find(hql);
        return list;
    }

    /**
     * 读取巡检终端列表信息
     * 
     * @param condition
     *            String 查询条件
     * @return List 返回巡检终端列表信息
     * @throws Exception
     */
    public List queryTerminal(String condition) throws Exception {
        String hql = " from Terminal where (state is null or state<>'1') " + condition;
        List list = baseDao.getHibernateTemplate().find(hql);
        return list;
    }

    /**
     * 根据用户编号读取用户的名称
     * 
     * @param userId
     *            String 用户编号
     * @return String 返回用户的名称
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
     * 创建输出流的头
     * 
     * @param response
     *            HttpServletResponse 输出响应头
     * @param fileName
     *            String 文件的名称
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
