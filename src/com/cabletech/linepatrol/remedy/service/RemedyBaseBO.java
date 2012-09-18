package com.cabletech.linepatrol.remedy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.sm.SendSMRMI;
import com.cabletech.linepatrol.remedy.beans.RemedyApplyBean;
import com.cabletech.linepatrol.remedy.beans.RemedyCheckBean;
import com.cabletech.linepatrol.remedy.constant.RemedyConstant;
import com.cabletech.linepatrol.remedy.constant.RemedyWorkflowConstant;
import com.cabletech.linepatrol.remedy.dao.MaterialDao;
import com.cabletech.linepatrol.remedy.dao.MaterialModelDao;
import com.cabletech.linepatrol.remedy.dao.MaterialStorageDao;
import com.cabletech.linepatrol.remedy.dao.MaterialTypeDao;
import com.cabletech.linepatrol.remedy.dao.RemedyApplyAttachDao;
import com.cabletech.linepatrol.remedy.dao.RemedyApplyDao;
import com.cabletech.linepatrol.remedy.dao.RemedyApplyItemDao;
import com.cabletech.linepatrol.remedy.dao.RemedyApplyMaterialDao;
import com.cabletech.linepatrol.remedy.dao.RemedyApproveDao;
import com.cabletech.linepatrol.remedy.dao.RemedyBalanceItemDao;
import com.cabletech.linepatrol.remedy.dao.RemedyBalanceMaterialDao;
import com.cabletech.linepatrol.remedy.dao.RemedyBaseDao;
import com.cabletech.linepatrol.remedy.dao.RemedyCheckDao;
import com.cabletech.linepatrol.remedy.dao.RemedyItemDao;
import com.cabletech.linepatrol.remedy.dao.RemedySquareDao;
import com.cabletech.linepatrol.remedy.dao.RemedyTypeDao;
import com.cabletech.linepatrol.remedy.domain.RemedyApply;
import com.cabletech.linepatrol.remedy.domain.RemedyBalance;
import com.cabletech.uploadfile.UploadUtil;

public abstract class RemedyBaseBO extends BaseBisinessObject {
    protected static Logger logger = Logger.getLogger(RemedyBaseBO.class.getName());

    protected RemedyWorkflowBO workflowBo = new RemedyWorkflowBO();

    protected OracleIDImpl ora = new OracleIDImpl();

    private ConditionGenerate conditionGenerate;

    private ResultListProcess listProcess;

    private RemedyBaseDao remedyBaseDao;

    private RemedyApply oneApply;

    private String nextProcessManId;

    private String applyProjectName;

    private List applyItemList;

    private List materialList;

    private List reasonFileList;

    private List solveFileList;

    private List approveList;

    private List checkList;

    private List squareList;

    private Map approveMap;

    private List balanceItemList;

    private List balanceMaterialList;

    private RemedyBalance oneBalance;

    public ConditionGenerate getConditionGenerate() {
        return conditionGenerate;
    }

    public void setConditionGenerate(ConditionGenerate conditionGenerate) {
        this.conditionGenerate = conditionGenerate;
    }

    public ResultListProcess getListProcess() {
        return listProcess;
    }

    public void setListProcess(ResultListProcess listProcess) {
        this.listProcess = listProcess;
    }

    public RemedyBaseDao getRemedyBaseDao() {
        return remedyBaseDao;
    }

    public void setRemedyBaseDao(RemedyBaseDao remedyBaseDao) {
        this.remedyBaseDao = remedyBaseDao;
    }

    public String getNextProcessManId() {
        return nextProcessManId;
    }

    public String getApplyProjectName() {
        return applyProjectName;
    }

    public void setNextProcessManId(String nextProcessManId) {
        this.nextProcessManId = nextProcessManId;
    }

    public void setApplyProjectName(String applyProjectName) {
        this.applyProjectName = applyProjectName;
    }

    /**
     * 写入日志信息
     * 
     * @param clazz
     *            Class
     */
    public void logger(Class clazz) {
        logger.info("Enter bo class " + clazz.getName() + "...............");
    }

    /**
     * 执行根据条件查询修缮申请列表
     * 
     * @param condition
     *            String 查询条件
     * @return List 修缮申请结果列表
     * @throws Exception
     */
    public List queryList(String condition) throws Exception {
        logger(RemedyBaseBO.class);
        setRemedyBaseDao(new RemedyApplyDao());
        List list = remedyBaseDao.queryList(condition);
        // list = remedyBaseDao.processList(list);
        return list;
    }

    /**
     * 执行根据修缮申请编号查询修缮申请信息
     * 
     * @param applyId
     *            String 修缮申请编号
     * @return Map 修缮申请信息包
     * @throws Exception
     */
    public Map viewApply(String applyId) throws Exception {
        logger(RemedyBaseBO.class);
        setRemedyBaseDao(new RemedyApplyDao());
        if (!remedyBaseDao.judgeExistApply(applyId)) {
            return null;
        }

        if (!judge(applyId)) {
            return null;
        }

        try {
            String condition = " and remedyid='" + applyId + "' ";
            String attachCondition = "";
            oneApply = (RemedyApply) remedyBaseDao.viewOneObject(RemedyApply.class, applyId);
            String statusName = remedyBaseDao.getRemedyApplyStatus(oneApply.getState());
            String contractorName = remedyBaseDao.getRemedyApplyContractorName(oneApply
                    .getContractorId());
            String townName = remedyBaseDao.getRemedyApplyTownName(oneApply.getTownId() + "");
            String needToUpLevel = workflowBo.isNeedToUpLevel(oneApply.getState(), oneApply
                    .getTotalFee());
            oneApply.setStatusName(statusName);
            oneApply.setContractorName(contractorName);
            oneApply.setTown(townName);
            oneApply.setNeedToUpLevel(needToUpLevel);

            List balanceList = (List) remedyBaseDao.findAll("RemedyBalance", condition);
            if (balanceList != null && balanceList.size() > 0) {
                oneBalance = (RemedyBalance) balanceList.get(0);
            } else {
                oneBalance = new RemedyBalance();
                oneBalance.setTotalFee(new Float(0));
            }

            setRemedyBaseDao(new RemedyApplyItemDao());
            applyItemList = remedyBaseDao.queryList(condition);
            setRemedyBaseDao(new RemedyApplyMaterialDao());
            materialList = remedyBaseDao.queryList(condition);
            materialList = processMaterialList(materialList, oneApply.getState());

            setRemedyBaseDao(new RemedyBalanceItemDao());
            balanceItemList = remedyBaseDao.queryList(condition);
            setRemedyBaseDao(new RemedyBalanceMaterialDao());
            balanceMaterialList = remedyBaseDao.queryList(condition);
            balanceMaterialList = processMaterialList(balanceMaterialList, oneApply.getState());

            setRemedyBaseDao(new RemedyApplyAttachDao());
            attachCondition = condition + " and flag='" + RemedyConstant.REASON_FILE_TYPE + "' ";
            reasonFileList = remedyBaseDao.queryList(attachCondition);
            attachCondition = condition + " and flag='" + RemedyConstant.SOLVE_FILE_TYPE + "' ";
            solveFileList = remedyBaseDao.queryList(attachCondition);

            setRemedyBaseDao(new RemedyApproveDao());
            approveList = remedyBaseDao.queryList(condition);
            listProcess = new ResultListProcess();
            approveMap = listProcess.processApproveList(approveList);
            setRemedyBaseDao(new RemedyCheckDao());
            checkList = remedyBaseDao.queryList(condition);
            setRemedyBaseDao(new RemedySquareDao());
            squareList = remedyBaseDao.queryList(condition);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
        Map oneMap = packageInfo();
        return oneMap;
    }

    /**
     * 执行修缮申请信息的打包
     * 
     * @return Map 修缮申请信息包
     * @throws Exception
     */
    public Map packageInfo() throws Exception {
        Map oneMap = new HashMap();
        RemedyApplyBean applyBean = new RemedyApplyBean();
        BeanUtil.objectCopy(oneApply, applyBean);

        RemedyCheckBean balanceBean = new RemedyCheckBean();
        if (oneBalance != null) {
            BeanUtil.objectCopy(oneBalance, balanceBean);
            oneMap.put("one_balance", balanceBean);
            oneMap.put("balance_item_list", balanceItemList);
            oneMap.put("balance_material_list", balanceMaterialList);
        }

        oneMap.put("one_apply", applyBean);
        oneMap.put("apply_item_list", applyItemList);
        oneMap.put("apply_material_list", materialList);
        oneMap.put("reason_file_list", UploadUtil.getFileIdsList(reasonFileList, "attachid"));
        oneMap
                .put("reason_file_name_list", UploadUtil.getFileIdsList(reasonFileList,
                        "attachname"));
        oneMap.put("solve_file_list", UploadUtil.getFileIdsList(solveFileList, "attachid"));
        oneMap.put("solve_file_name_list", UploadUtil.getFileIdsList(solveFileList, "attachname"));

        if (approveList != null) {
            oneMap.put("approve_list", approveList);
        } else {
            oneMap.put("approve_list", new ArrayList());
        }
        if (approveMap != null) {
            oneMap.put("approve_map", approveMap);
        } else {
            oneMap.put("approve_map", new HashMap());
        }
        if (checkList != null) {
            oneMap.put("check_list", checkList);
        } else {
            oneMap.put("check_list", new ArrayList());
        }
        if (squareList != null) {
            oneMap.put("square_list", squareList);
        } else {
            oneMap.put("square_list", new ArrayList());
        }
        return oneMap;
    }

    /**
     * 执行获取状态列表信息
     * 
     * @return List 状态列表信息
     * @throws Exception
     */
    public List getApplyStatusList() {
        setRemedyBaseDao(new RemedyApplyDao());
        try {
            return remedyBaseDao.getRemedyApplyStatusList();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
        }
        return null;
    }

    /**
     * 执行获取区县列表信息
     * 
     * @param request
     *            HttpServletRequest 用户输入请求
     * @param form
     *            ActionForm 用户输入表单
     * @return List 区县列表信息
     * @throws Exception
     */
    public List getRemedyTownList(HttpServletRequest request, ActionForm formBean) {
        setRemedyBaseDao(new RemedyApplyDao());
        try {
            setConditionGenerate(new ConditionGenerate());
            String condition = conditionGenerate.getUserQueryCondition(request, formBean);
            return remedyBaseDao.getRemedyApplyTownList(condition);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
        }
        return null;
    }

    /**
     * 执行获取定额项列表信息
     * 
     * @param request
     *            HttpServletRequest 用户输入请求
     * @param form
     *            ActionForm 用户输入表单
     * @return List 定额项列表信息
     * @throws Exception
     */
    public List getRemedyItemList(HttpServletRequest request, ActionForm formBean) {
        setRemedyBaseDao(new RemedyItemDao());
        try {
            setConditionGenerate(new ConditionGenerate());
            String condition = conditionGenerate.getUserQueryCondition(request, formBean);
            condition = condition + " order by t.itemname,t.id ";
            return remedyBaseDao.queryList(condition);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
        }
        return null;
    }

    /**
     * 执行获取定额项类型列表信息
     * 
     * @param request
     *            HttpServletRequest 用户输入请求
     * @param form
     *            ActionForm 用户输入表单
     * @return List 定额项类型列表信息
     * @throws Exception
     */
    public List getRemedyItemTypeList(HttpServletRequest request, ActionForm formBean) {
        setRemedyBaseDao(new RemedyTypeDao());
        try {
            setConditionGenerate(new ConditionGenerate());
            String condition = conditionGenerate.getUserQueryCondition(request, formBean);
            condition = condition + " order by t.remedyitemid,t.typename,t.id ";
            return remedyBaseDao.queryList(condition);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
        }
        return null;
    }

    /**
     * 执行获取材料类型列表信息
     * 
     * @param request
     *            HttpServletRequest 用户输入请求
     * @param form
     *            ActionForm 用户输入表单
     * @return List 材料类型列表信息
     * @throws Exception
     */
    public List getRemedyMaterialModelList(HttpServletRequest request, ActionForm formBean) {
        setRemedyBaseDao(new MaterialModelDao());
        try {
            setConditionGenerate(new ConditionGenerate());
            String condition = conditionGenerate.getUserQueryCondition(request, formBean);
            condition = condition + " order by t.typeid,t.modelname,t.id ";
            return remedyBaseDao.queryList(condition);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
        }
        return null;
    }

    /**
     * 执行获取材料规格列表信息
     * 
     * @param request
     *            HttpServletRequest 用户输入请求
     * @param form
     *            ActionForm 用户输入表单
     * @return List 材料规格列表信息
     * @throws Exception
     */
    public List getRemedyMaterialTypeList(HttpServletRequest request, ActionForm formBean) {
        setRemedyBaseDao(new MaterialTypeDao());
        try {
            setConditionGenerate(new ConditionGenerate());
            String condition = conditionGenerate.getUserQueryCondition(request, formBean);
            condition = condition + " order by t.typename,t.id ";
            return remedyBaseDao.queryList(condition);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
        }
        return null;
    }

    /**
     * 执行获取材料基本信息列表信息
     * 
     * @param request
     *            HttpServletRequest 用户输入请求
     * @param form
     *            ActionForm 用户输入表单
     * @return List 材料基本信息列表信息
     * @throws Exception
     */
    public List getRemedyMaterialList(HttpServletRequest request, ActionForm formBean) {
        setRemedyBaseDao(new MaterialDao());
        try {
            setConditionGenerate(new ConditionGenerate());
            String condition = conditionGenerate.getUserQueryCondition(request, formBean);
            condition = condition + " order by tt.id,t.modelid,t.name,t.id ";
            return remedyBaseDao.queryList(condition);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
        }
        return null;
    }

    /**
     * 执行获取材料库存信息列表信息
     * 
     * @param request
     *            HttpServletRequest 用户输入请求
     * @param form
     *            ActionForm 用户输入表单
     * @return List 材料库存信息列表信息
     * @throws Exception
     */
    public List getRemedyMaterialStorageList(HttpServletRequest request, ActionForm formBean) {
        setRemedyBaseDao(new MaterialStorageDao());
        try {
            setConditionGenerate(new ConditionGenerate());
            String condition = conditionGenerate.getUserQueryCondition(request, formBean);
            condition = condition + " order by a.address,t.addressid ";
            return remedyBaseDao.queryList(condition);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
        }
        return null;
    }

    /**
     * 执行根据当前用户编号获取其所在代维单位信息
     * 
     * @param request
     *            HttpServletRequest 用户输入请求
     * @param form
     *            ActionForm 用户输入表单
     * @return DynaBean 所在代维单位信息
     */
    public DynaBean getContractorBean(HttpServletRequest request, ActionForm formBean) {
        setRemedyBaseDao(new RemedyApplyDao());
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            DynaBean contractorBean = remedyBaseDao.getRemedyApplyContractor(userInfo.getDeptID());
            return contractorBean;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
        }
        return null;
    }

    /**
     * 执行根据当前用户查询当前月份的修缮申请数量信息
     * 
     * @param request
     *            HttpServletRequest 用户输入请求
     * @param form
     *            ActionForm 用户输入表单
     * @return Integer 当前月份的修缮申请数量信息
     */
    public Integer getRemedyApplyNumber(HttpServletRequest request, ActionForm formBean) {
        setRemedyBaseDao(new RemedyApplyDao());
        setConditionGenerate(new ConditionGenerate());
        String condition = getConditionGenerate().getUserQueryCondition(request, formBean);
        condition = condition + getConditionGenerate().getSysDateCondition();
        try {
            List list = remedyBaseDao.queryList(condition);
            if (list != null && list.size() > 0) {
                return new Integer(list.size() + 1);
            }
            return new Integer(1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
        }
        return new Integer(1);
    }

    /**
     * 执行根据修缮申请状态处理材料列表的操作
     * 
     * @param prevList
     *            List 处理之前的材料列表
     * @param applyState
     *            String 修缮申请状态
     * @return List 处理之后的材料列表
     */
    public List processMaterialList(List prevList, String applyState) {
        // TODO Auto-generated method stub
        List list = new ArrayList();
        DynaBean bean;
        for (int i = 0; prevList != null && i < prevList.size(); i++) {
            bean = (DynaBean) prevList.get(i);
            if (RemedyWorkflowConstant.WAIT_CHECKED_STATE.equals(applyState)) {
                bean.set("is_enough_material", "+");
            }
            if (RemedyWorkflowConstant.WAIT_SQUARED_STATE.equals(applyState)) {
                bean.set("is_enough_material", "+");
            }
            if (RemedyWorkflowConstant.ACHIEVED_STATE.equals(applyState)) {
                bean.set("is_enough_material", "+");
            }
            list.add(bean);
        }
        return list;
    }

    /**
     * 根据当前用户发送修缮管理的短信
     * 
     * @param user
     *            UserInfo 当前用户
     */
    public void sendMsg(UserInfo user) {
        try {
            setRemedyBaseDao(new RemedyApplyDao());
            String nextProcessManId = getNextProcessManId();
            String simId = remedyBaseDao.getUserMobile(nextProcessManId);
            String msg = RemedyConstant.SEND_MSG_MODEL_NAME + " " + getApplyProjectName()
                    + " 修缮 等待您的处理 发送人：" + user.getUserName() + " " + SendSMRMI.MSG_NOTE;
            logger.info("修缮管理的短信内容：" + msg);
            logger.info("短信发送的目标人：" + nextProcessManId);
            logger.info("短信发送的目标手机号：" + simId);
            SendSMRMI.sendNormalMessage(user.getUserID(), simId, msg, "00");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("发送短信业务异常:", e);
        }
    }

    /**
     * 执行根据用户输入组织查询条件
     * 
     * @param request
     *            HttpServletRequest 用户输入请求
     * @param form
     *            ActionForm 用户输入表单
     * @return String 查询条件
     */
    public abstract String compositeCondition(HttpServletRequest request, ActionForm form);

    /**
     * 执行根据修缮申请的状态获取下一步处理人列表
     * 
     * @param applyState
     *            String 修缮申请的状态
     * @return List 下一步处理人列表
     */
    public abstract List getNextProcessManList(String applyState);

    /**
     * 执行根据修缮申请编号判断是否可以执行某个操作
     * 
     * @param applyId
     *            String 修缮申请编号
     * @return boolean 判断结果
     */
    public abstract boolean judge(String applyId);
}
