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
     * 写入日志信息
     * 
     * @param clazz
     *            Class
     */
    public void logger(Class clazz) {
        logger.info("Enter dao class " + clazz.getName() + "...............");
    }

    /**
     * 执行插入对象信息
     * 
     * @param obj
     *            Object 要插入的对象
     * @return Object 返回插入对象
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
     * 执行修改对象信息
     * 
     * @param obj
     *            Object 要修改的对象
     * @return Object 返回修改对象
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
     * 执行删除对象信息
     * 
     * @param obj
     *            Object 要删除的对象
     * @return Object 返回删除对象
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
     * 删除查询到的所有对象
     * 
     * @param objectClass
     *            String 对象的类型字符串
     * @param condition
     *            String 查询条件
     * @return boolean 返回删除是否成功
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
     * 根据对象的类型和查询条件获取所有的对象实体列表
     * 
     * @param objectClass
     *            String 对象的类型字符串
     * @param condition
     *            String 查询条件
     * @return List 返回所有的对象实体列表
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
     * 执行根据列表整理获取新的列表信息
     * 
     * @param prevList
     *            List 要整理的列表
     * @return List 新的列表信息
     */
    public List processList(List prevList) {
        logger(RemedyBaseDao.class);
        return prevList;
    }

    /**
     * 执行根据指定类和对象编号获取对象实体信息
     * 
     * @param clazz
     *            Class 指定类
     * @param objectId
     *            String 对象编号
     * @return Object 对象实体信息
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
     * 执行根据修缮申请编号判断修缮申请是否可以审批
     * 
     * @param applyId
     *            String 修缮申请编号
     * @return boolean 修缮申请是否可以审批
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
     * 执行根据修缮申请编号判断修缮申请是否可以验收
     * 
     * @param applyId
     *            String 修缮申请编号
     * @return boolean 修缮申请是否可以验收
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
     * 执行根据修缮申请编号判断修缮申请是否可以删除
     * 
     * @param applyId
     *            String 修缮申请编号
     * @return boolean 修缮申请是否可以删除
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
     * 执行根据修缮申请编号判断修缮申请是否可以编辑
     * 
     * @param applyId
     *            String 修缮申请编号
     * @return boolean 修缮申请是否可以编辑
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
     * 执行根据修缮申请编号判断修缮申请是否可以结算
     * 
     * @param applyId
     *            String 修缮申请编号
     * @return boolean 修缮申请是否可以结算
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
     * 执行根据修缮申请编号判断修缮申请是否存在
     * 
     * @param applyId
     *            String 修缮申请编号
     * @return boolean 修缮申请是否存在
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
     * 执行获取监理公司用户列表
     * 
     * @return List 监理公司用户列表
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
     * 执行获取移动公司用户列表
     * 
     * @return List 移动公司用户列表
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
     * 执行根据状态编号获取状态说明信息
     * 
     * @param statusId
     *            状态编号
     * @return String 状态说明信息
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
     * 执行根据代维单位编号获取代维单位名称信息
     * 
     * @param contractorId
     *            状态编号
     * @return String 代维单位名称信息
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
     * 执行根据代维单位编号获取代维单位信息
     * 
     * @param contractorId
     *            状态编号
     * @return DynaBean 代维单位信息
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
     * 执行根据区县编号获取区县名称信息
     * 
     * @param townId
     *            区县编号
     * @return String 区县名称信息
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
     * 执行获取状态列表信息
     * 
     * @return List 状态列表信息
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
     * 执行根据材料编号获取材料名称
     * 
     * @param materialId
     *            String 材料编号
     * @return String 材料名称
     * @throws Exception
     */
    public String getMaterialName(String materialId) throws Exception {
        String sql = "select mb.name||'（'||mt.typename||'）（'||mm.modelname||'）' as material_name ";
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
     * 执行根据存放地点编号获取存放地点名称
     * 
     * @param addressId
     *            String 存放地点编号
     * @return String 存放地点名称
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
     * 执行根据用户编号获取用户的手机号码
     * 
     * @param userId
     *            String 存放地点编号
     * @return String 用户的手机号码
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
     * 提交hibernate的事务
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
     * 回滚hibernate的事务
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
     * 执行根据查询条件获取区县列表信息
     * 
     * @param condition
     *            String 查询条件
     * @return List 区县列表信息
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
     * 执行根据查询条件获取对象的列表信息
     * 
     * @param condition
     *            String 查询条件
     * @return List 对象的列表信息
     * @throws Exception
     */
    public abstract List queryList(String condition) throws Exception;

}
