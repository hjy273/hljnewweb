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
     * д����־��Ϣ
     * 
     * @param clazz
     *            Class
     */
    public void logger(Class clazz) {
        logger.info("Enter bo class " + clazz.getName() + "...............");
    }

    /**
     * ִ�и���������ѯ���������б�
     * 
     * @param condition
     *            String ��ѯ����
     * @return List �����������б�
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
     * ִ�и������������Ų�ѯ����������Ϣ
     * 
     * @param applyId
     *            String ����������
     * @return Map ����������Ϣ��
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
     * ִ������������Ϣ�Ĵ��
     * 
     * @return Map ����������Ϣ��
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
     * ִ�л�ȡ״̬�б���Ϣ
     * 
     * @return List ״̬�б���Ϣ
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
     * ִ�л�ȡ�����б���Ϣ
     * 
     * @param request
     *            HttpServletRequest �û���������
     * @param form
     *            ActionForm �û������
     * @return List �����б���Ϣ
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
     * ִ�л�ȡ�������б���Ϣ
     * 
     * @param request
     *            HttpServletRequest �û���������
     * @param form
     *            ActionForm �û������
     * @return List �������б���Ϣ
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
     * ִ�л�ȡ�����������б���Ϣ
     * 
     * @param request
     *            HttpServletRequest �û���������
     * @param form
     *            ActionForm �û������
     * @return List �����������б���Ϣ
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
     * ִ�л�ȡ���������б���Ϣ
     * 
     * @param request
     *            HttpServletRequest �û���������
     * @param form
     *            ActionForm �û������
     * @return List ���������б���Ϣ
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
     * ִ�л�ȡ���Ϲ���б���Ϣ
     * 
     * @param request
     *            HttpServletRequest �û���������
     * @param form
     *            ActionForm �û������
     * @return List ���Ϲ���б���Ϣ
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
     * ִ�л�ȡ���ϻ�����Ϣ�б���Ϣ
     * 
     * @param request
     *            HttpServletRequest �û���������
     * @param form
     *            ActionForm �û������
     * @return List ���ϻ�����Ϣ�б���Ϣ
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
     * ִ�л�ȡ���Ͽ����Ϣ�б���Ϣ
     * 
     * @param request
     *            HttpServletRequest �û���������
     * @param form
     *            ActionForm �û������
     * @return List ���Ͽ����Ϣ�б���Ϣ
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
     * ִ�и��ݵ�ǰ�û���Ż�ȡ�����ڴ�ά��λ��Ϣ
     * 
     * @param request
     *            HttpServletRequest �û���������
     * @param form
     *            ActionForm �û������
     * @return DynaBean ���ڴ�ά��λ��Ϣ
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
     * ִ�и��ݵ�ǰ�û���ѯ��ǰ�·ݵ���������������Ϣ
     * 
     * @param request
     *            HttpServletRequest �û���������
     * @param form
     *            ActionForm �û������
     * @return Integer ��ǰ�·ݵ���������������Ϣ
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
     * ִ�и�����������״̬��������б�Ĳ���
     * 
     * @param prevList
     *            List ����֮ǰ�Ĳ����б�
     * @param applyState
     *            String ��������״̬
     * @return List ����֮��Ĳ����б�
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
     * ���ݵ�ǰ�û��������ɹ���Ķ���
     * 
     * @param user
     *            UserInfo ��ǰ�û�
     */
    public void sendMsg(UserInfo user) {
        try {
            setRemedyBaseDao(new RemedyApplyDao());
            String nextProcessManId = getNextProcessManId();
            String simId = remedyBaseDao.getUserMobile(nextProcessManId);
            String msg = RemedyConstant.SEND_MSG_MODEL_NAME + " " + getApplyProjectName()
                    + " ���� �ȴ����Ĵ��� �����ˣ�" + user.getUserName() + " " + SendSMRMI.MSG_NOTE;
            logger.info("���ɹ���Ķ������ݣ�" + msg);
            logger.info("���ŷ��͵�Ŀ���ˣ�" + nextProcessManId);
            logger.info("���ŷ��͵�Ŀ���ֻ��ţ�" + simId);
            SendSMRMI.sendNormalMessage(user.getUserID(), simId, msg, "00");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("���Ͷ���ҵ���쳣:", e);
        }
    }

    /**
     * ִ�и����û�������֯��ѯ����
     * 
     * @param request
     *            HttpServletRequest �û���������
     * @param form
     *            ActionForm �û������
     * @return String ��ѯ����
     */
    public abstract String compositeCondition(HttpServletRequest request, ActionForm form);

    /**
     * ִ�и������������״̬��ȡ��һ���������б�
     * 
     * @param applyState
     *            String ���������״̬
     * @return List ��һ���������б�
     */
    public abstract List getNextProcessManList(String applyState);

    /**
     * ִ�и��������������ж��Ƿ����ִ��ĳ������
     * 
     * @param applyId
     *            String ����������
     * @return boolean �жϽ��
     */
    public abstract boolean judge(String applyId);
}
