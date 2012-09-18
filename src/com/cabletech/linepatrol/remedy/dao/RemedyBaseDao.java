package com.cabletech.linepatrol.remedy.dao;

import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.HibernateDaoSupport;
import com.cabletech.commons.hb.HibernateSession;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.linepatrol.remedy.constant.RemedyWorkflowConstant;

public abstract class RemedyBaseDao extends HibernateDaoSupport {
    protected static Logger logger = Logger.getLogger(RemedyBaseDao.class.getName());

    protected OracleIDImpl ora = new OracleIDImpl();

    /**
     * д����־��Ϣ
     * 
     * @param clazz
     *            Class
     */
    public void logger(Class clazz) {
        logger.info("Enter dao class " + clazz.getName() + "...............");
    }

    /**
     * ִ�в��������Ϣ
     * 
     * @param obj
     *            Object Ҫ����Ķ���
     * @return Object ���ز������
     * @throws Exception
     */
    public Object insertOneObject(Object obj) throws Exception {
        logger(RemedyBaseDao.class);
        try {
            super.getHibernateTemplate().save(obj);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
        return obj;
    }

    /**
     * ִ���޸Ķ�����Ϣ
     * 
     * @param obj
     *            Object Ҫ�޸ĵĶ���
     * @return Object �����޸Ķ���
     * @throws Exception
     */
    public Object updateOneObject(Object obj) throws Exception {
        logger(RemedyBaseDao.class);
        try {
            super.getHibernateTemplate().saveOrUpdate(obj);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
        return obj;
    }

    /**
     * ִ��ɾ��������Ϣ
     * 
     * @param obj
     *            Object Ҫɾ���Ķ���
     * @return Object ����ɾ������
     * @throws Exception
     */
    public Object deleteOneObject(Object obj) throws Exception {
        logger(RemedyBaseDao.class);
        try {
            super.getHibernateTemplate().delete(obj);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
        return obj;
    }

    /**
     * ɾ����ѯ�������ж���
     * 
     * @param objectClass
     *            String ����������ַ���
     * @param condition
     *            String ��ѯ����
     * @return boolean ����ɾ���Ƿ�ɹ�
     * @throws Exception
     */
    public boolean deleteAll(String objectClass, String condition) throws Exception {
        List list = findAll(objectClass, condition);
        if (list != null) {
            super.getHibernateTemplate().deleteAll(list);
        }
        return true;
    }

    /**
     * ���ݶ�������ͺͲ�ѯ������ȡ���еĶ���ʵ���б�
     * 
     * @param objectClass
     *            String ����������ַ���
     * @param condition
     *            String ��ѯ����
     * @return List �������еĶ���ʵ���б�
     * @throws Exception
     */
    public List findAll(String objectClass, String condition) throws Exception {
        String hql = " from " + objectClass + " where 1=1 " + condition;
        List list = super.getHibernateTemplate().find(hql);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    /**
     * ִ�и����б������ȡ�µ��б���Ϣ
     * 
     * @param prevList
     *            List Ҫ������б�
     * @return List �µ��б���Ϣ
     */
    public List processList(List prevList) {
        logger(RemedyBaseDao.class);
        return prevList;
    }

    /**
     * ִ�и���ָ����Ͷ����Ż�ȡ����ʵ����Ϣ
     * 
     * @param clazz
     *            Class ָ����
     * @param objectId
     *            String ������
     * @return Object ����ʵ����Ϣ
     * @throws Exception
     */
    public Object viewOneObject(Class clazz, String objectId) throws Exception {
        logger(RemedyBaseDao.class);
        Object obj;
        try {
            obj = super.getHibernateTemplate().load(clazz, objectId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
        return obj;
    }

    /**
     * ִ�и��������������ж����������Ƿ��������
     * 
     * @param applyId
     *            String ����������
     * @return boolean ���������Ƿ��������
     * @throws Exception
     */
    public boolean judgeAllowApproved(String applyId) throws Exception {
        logger(RemedyBaseDao.class);
        String condition = " and remedy.state in (" + RemedyWorkflowConstant.APPROVE_STATUS_STRING
                + ") and remedy.id=" + applyId;
        List list = queryList(condition);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * ִ�и��������������ж����������Ƿ��������
     * 
     * @param applyId
     *            String ����������
     * @return boolean ���������Ƿ��������
     * @throws Exception
     */
    public boolean judgeAllowChecked(String applyId) throws Exception {
        logger(RemedyBaseDao.class);
        String condition = " and remedy.id='" + applyId + "' and (remedy.state='"
                + RemedyWorkflowConstant.WAIT_CHECKED_STATE + "')";
        List list = queryList(condition);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * ִ�и��������������ж����������Ƿ����ɾ��
     * 
     * @param applyId
     *            String ����������
     * @return boolean ���������Ƿ����ɾ��
     * @throws Exception
     */
    public boolean judgeAllowDeleted(String applyId) throws Exception {
        logger(RemedyBaseDao.class);
        String condition = " and remedy.id='" + applyId + "' and (remedy.state='"
                + RemedyWorkflowConstant.NOT_SUBMITED_STATE + "' )";
        List list = queryList(condition);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * ִ�и��������������ж����������Ƿ���Ա༭
     * 
     * @param applyId
     *            String ����������
     * @return boolean ���������Ƿ���Ա༭
     * @throws Exception
     */
    public boolean judgeAllowEdited(String applyId) throws Exception {
        logger(RemedyBaseDao.class);
        String condition = " and remedy.id='" + applyId + "' and (remedy.state='"
                + RemedyWorkflowConstant.NOT_SUBMITED_STATE + "' or remedy.state='"
                + RemedyWorkflowConstant.NOT_APPROVED_STATE_LEVEL_ONE + "')";
        List list = queryList(condition);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * ִ�и��������������ж����������Ƿ���Խ���
     * 
     * @param applyId
     *            String ����������
     * @return boolean ���������Ƿ���Խ���
     * @throws Exception
     */
    public boolean judgeAllowSquared(String applyId) throws Exception {
        logger(RemedyBaseDao.class);
        String condition = " and remedy.id='" + applyId + "' and (remedy.state='"
                + RemedyWorkflowConstant.WAIT_SQUARED_STATE + "')";
        List list = queryList(condition);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * ִ�и��������������ж����������Ƿ����
     * 
     * @param applyId
     *            String ����������
     * @return boolean ���������Ƿ����
     * @throws Exception
     */
    public boolean judgeExistApply(String applyId) throws Exception {
        logger(RemedyBaseDao.class);
        String condition = " and remedy.id='" + applyId + "'";
        List list = queryList(condition);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * ִ�л�ȡ����˾�û��б�
     * 
     * @return List ����˾�û��б�
     * @throws Exception
     */
    public List getSuperviseUser() throws Exception {
        // String sql = "select userid,username from userinfo u,contractorinfo c
        // ";
        // sql = sql + " where u.deptid=c.contractorid and (u.state is null or
        // u.state<>'1') ";
        // sql = sql + " and c.depttype='" + ConditionGenerate.IS_SUPERVISE_UNIT
        // + "' ";
        String sql = "select userid,username from userinfo u,contractorinfo c ";
        sql = sql + " where u.deptid=c.contractorid and c.depttype='3' ";
        sql = sql + " and (u.state is null or u.state<>'1') ";
        sql = sql + " order by username,userid ";
        try {
            QueryUtil queryUtil = new QueryUtil();
            List list = queryUtil.queryBeans(sql);
            return list;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
    }

    /**
     * ִ�л�ȡ�ƶ���˾�û��б�
     * 
     * @return List �ƶ���˾�û��б�
     * @throws Exception
     */
    public List getMobileUser(String userType) throws Exception {
        String sql = "select userid,username from userinfo ";
        sql = sql + " where deptype='1' and (state is null or state<>'1') ";
        sql = sql + " order by username,userid ";
        try {
            QueryUtil queryUtil = new QueryUtil();
            List list = queryUtil.queryBeans(sql);
            return list;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
    }

    /**
     * ִ�и���״̬��Ż�ȡ״̬˵����Ϣ
     * 
     * @param statusId
     *            ״̬���
     * @return String ״̬˵����Ϣ
     * @throws Exception
     */
    public String getRemedyApplyStatus(String statusId) throws Exception {
        String sql = "select status_name from LINEPATROL_REMEDY_STATUS where status_id='"
                + statusId + "'";
        try {
            QueryUtil queryUtil = new QueryUtil();
            List list = queryUtil.queryBeans(sql);
            if (list != null && list.size() > 0) {
                DynaBean bean = (DynaBean) list.get(0);
                String statusName = (String) bean.get("status_name");
                return statusName;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
        return "";
    }

    /**
     * ִ�и��ݴ�ά��λ��Ż�ȡ��ά��λ������Ϣ
     * 
     * @param contractorId
     *            ״̬���
     * @return String ��ά��λ������Ϣ
     * @throws Exception
     */
    public String getRemedyApplyContractorName(String contractorId) throws Exception {
        String sql = "select contractorname from contractorinfo where contractorid='"
                + contractorId + "'";
        try {
            QueryUtil queryUtil = new QueryUtil();
            List list = queryUtil.queryBeans(sql);
            if (list != null && list.size() > 0) {
                DynaBean bean = (DynaBean) list.get(0);
                String contractName = (String) bean.get("contractorname");
                return contractName;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
        return "";
    }

    /**
     * ִ�и��ݴ�ά��λ��Ż�ȡ��ά��λ��Ϣ
     * 
     * @param contractorId
     *            ״̬���
     * @return DynaBean ��ά��λ��Ϣ
     * @throws Exception
     */
    public DynaBean getRemedyApplyContractor(String contractorId) throws Exception {
        String sql = "select contractorname,alias||to_char(sysdate,'yyyymmdd') as generate_id_prefix from contractorinfo where contractorid='"
                + contractorId + "'";
        try {
            QueryUtil queryUtil = new QueryUtil();
            List list = queryUtil.queryBeans(sql);
            if (list != null && list.size() > 0) {
                DynaBean bean = (DynaBean) list.get(0);
                return bean;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
        return null;
    }

    /**
     * ִ�и������ر�Ż�ȡ����������Ϣ
     * 
     * @param townId
     *            ���ر��
     * @return String ����������Ϣ
     * @throws Exception
     */
    public String getRemedyApplyTownName(String townId) throws Exception {
        String sql = "select town from LINEPATROL_TOWNINFO where id=" + townId;
        try {
            QueryUtil queryUtil = new QueryUtil();
            List list = queryUtil.queryBeans(sql);
            if (list != null && list.size() > 0) {
                DynaBean bean = (DynaBean) list.get(0);
                String townName = (String) bean.get("town");
                return townName;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
        return "";
    }

    /**
     * ִ�л�ȡ״̬�б���Ϣ
     * 
     * @return List ״̬�б���Ϣ
     * @throws Exception
     */
    public List getRemedyApplyStatusList() throws Exception {
        String sql = "select status_id,status_name ";
        sql = sql + " from LINEPATROL_REMEDY_STATUS where status_display='1' ";
        try {
            QueryUtil queryUtil = new QueryUtil();
            List list = queryUtil.queryBeans(sql);
            return list;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
    }

    /**
     * ִ�и��ݲ��ϱ�Ż�ȡ��������
     * 
     * @param materialId
     *            String ���ϱ��
     * @return String ��������
     * @throws Exception
     */
    public String getMaterialName(String materialId) throws Exception {
        String sql = "select mb.name||'��'||mt.typename||'����'||mm.modelname||'��' as material_name ";
        sql = sql + " from LINEPATROL_MT_BASE mb,LINEPATROL_MT_MODEL mm,LINEPATROL_MT_TYPE mt ";
        sql = sql + " where mb.id=" + materialId + "";
        try {
            QueryUtil queryUtil = new QueryUtil();
            List list = queryUtil.queryBeans(sql);
            if (list != null && list.size() > 0) {
                DynaBean bean = (DynaBean) list.get(0);
                String materialName = (String) bean.get("material_name");
                return materialName;
            }
            return "";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
    }

    /**
     * ִ�и��ݴ�ŵص��Ż�ȡ��ŵص�����
     * 
     * @param addressId
     *            String ��ŵص���
     * @return String ��ŵص�����
     * @throws Exception
     */
    public String getAddressName(String addressId) throws Exception {
        String sql = "select a.address ";
        sql = sql + " from LINEPATROL_MT_ADDR a ";
        sql = sql + " where a.id=" + addressId + "";
        try {
            QueryUtil queryUtil = new QueryUtil();
            List list = queryUtil.queryBeans(sql);
            if (list != null && list.size() > 0) {
                DynaBean bean = (DynaBean) list.get(0);
                String addressName = (String) bean.get("address");
                return addressName;
            }
            return "";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
    }
    
    /**
     * ִ�и����û���Ż�ȡ�û����ֻ�����
     * 
     * @param userId
     *            String ��ŵص���
     * @return String �û����ֻ�����
     * @throws Exception
     */
    public String getUserMobile(String userId) throws Exception {
        String sql = "select u.phone ";
        sql = sql + " from userinfo u ";
        sql = sql + " where u.userid='" + userId + "'";
        try {
            QueryUtil queryUtil = new QueryUtil();
            List list = queryUtil.queryBeans(sql);
            if (list != null && list.size() > 0) {
                DynaBean bean = (DynaBean) list.get(0);
                String mobile = (String) bean.get("phone");
                return mobile;
            }
            return "";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
    }

    /**
     * �ύhibernate������
     */
    public void commitTransaction() {
        try {
            HibernateSession.currentSession().connection().commit();
            HibernateSession.commitTransaction();
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * �ع�hibernate������
     */
    public void rollbackTransaction() {
        try {
            HibernateSession.currentSession().connection().rollback();
            HibernateSession.rollbackTransaction();
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * ִ�и��ݲ�ѯ������ȡ�����б���Ϣ
     * 
     * @param condition
     *            String ��ѯ����
     * @return List �����б���Ϣ
     * @throws Exception
     */
    public List getRemedyApplyTownList(String condition) throws Exception {
        String sql = "select id,town from LINEPATROL_TOWNINFO t,contractorinfo c,region r";
        sql = sql + " where t.state='1' and t.regionid=c.regionid and t.regionid=r.regionid ";
        sql = sql + condition;
        try {
            QueryUtil queryUtil = new QueryUtil();
            List list = queryUtil.queryBeans(sql);
            return list;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
    }

    /**
     * ִ�и��ݲ�ѯ������ȡ������б���Ϣ
     * 
     * @param condition
     *            String ��ѯ����
     * @return List ������б���Ϣ
     * @throws Exception
     */
    public abstract List queryList(String condition) throws Exception;

}
