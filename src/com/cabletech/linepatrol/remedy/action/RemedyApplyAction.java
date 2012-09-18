package com.cabletech.linepatrol.remedy.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.constant.RemedyPowerConstant;
import com.cabletech.linepatrol.remedy.constant.RemedyWorkflowConstant;
import com.cabletech.linepatrol.remedy.service.RemedyApplyManager;
import com.cabletech.power.CheckPower;
import com.cabletech.utils.StringUtil;

public class RemedyApplyAction extends RemedyBaseAction {
    /**
     * 执行判断是否存在相同的修缮申请
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward judgeExistSameApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        RemedyApplyManager bo = new RemedyApplyManager();
        boolean flag = bo.judgeExistSameApply(request);
        PrintWriter out = response.getWriter();
        if (flag) {
            out.print("1");
        } else {
            out.print("0");
        }
        out.close();
        return null;
    }

    /**
     * 执行进入添加修缮申请表单页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insertApplyForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        initRemedyBaseBo();
        List nextProcessManList = super.getRemedyBaseBo().getNextProcessManList("");
        request.setAttribute("next_process_man_list", nextProcessManList);

        List townList = super.getRemedyBaseBo().getRemedyTownList(request, form);
        request.setAttribute("town_list", townList);

        DynaBean contractorBean = super.getRemedyBaseBo().getContractorBean(request, form);
        Integer remedyNumber = super.getRemedyBaseBo().getRemedyApplyNumber(request, form);

        String generateId = (String) contractorBean.get("generate_id_prefix")
                + StringUtil.lpad(remedyNumber.toString(), 4, "0");
        request.setAttribute("generate_id", generateId);

        List itemList = super.getRemedyBaseBo().getRemedyItemList(request, form);
        request.setAttribute("item_list", itemList);

        List itemTypeList = super.getRemedyBaseBo().getRemedyItemTypeList(request, form);
        request.setAttribute("item_type_list", itemTypeList);

        List materialTypeList = super.getRemedyBaseBo().getRemedyMaterialTypeList(request, form);
        request.setAttribute("material_type_list", materialTypeList);

        List materialModelList = super.getRemedyBaseBo().getRemedyMaterialModelList(request, form);
        request.setAttribute("material_model_list", materialModelList);

        List materialList = super.getRemedyBaseBo().getRemedyMaterialList(request, form);
        request.setAttribute("material_list", materialList);

        List materialStorageList = super.getRemedyBaseBo().getRemedyMaterialStorageList(request,
                form);
        request.setAttribute("material_storage_list", materialStorageList);

        return mapping.findForward("insert_remedy_apply");
    }

    /**
     * 执行修缮申请页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward queryApplyForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        initRemedyBaseBo();
        List statusList = super.getRemedyBaseBo().getApplyStatusList();
        request.setAttribute("status_list", statusList);
        return mapping.findForward("query_remedy_apply");
    }

    /**
     * 执行插入修缮申请信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insertApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!CheckPower.checkPower(request.getSession(), RemedyPowerConstant.INSERT_POWER)) {
            return mapping.findForward("powererror");
        }
        String url = request.getContextPath() + "/remedy_apply.do?method=";
        super.logger(RemedyApplyAction.class);

        RemedyApplyManager bo = new RemedyApplyManager();
        String operationCode = ExecuteCode.FAIL_CODE;
        try {
            operationCode = bo.insertApply(request, form);
        } catch (Exception e) {
            logger.error("执行插入修缮申请业务异常：" + e);
        }
        if (ExecuteCode.EXIST_SAME_APPLY_ERROR.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "ExistSameApply", url
                    + "insertApplyForm");
        }
        if (ExecuteCode.NOT_EXIST_ITEM_ERROR.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotExistItem", url
                    + "insertApplyForm");
        }
        if (ExecuteCode.NOT_EXIST_MATERIAL_ERROR.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotExistMaterial", url
                    + "insertApplyForm");
        }
        if (ExecuteCode.NOT_ENOUGH_STORAGE_ERROR.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotEnoughStorage", url
                    + "insertApplyForm");
        }
        if (ExecuteCode.NOT_EXIST_MT_USED_ERROR.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotExistMtUsedError", url
                    + "insertApplyForm");
        }
        if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "FailCode", url
                    + "insertApplyForm");
        }
        if (ExecuteCode.SUCCESS_CODE.equals(operationCode)) {
            // 执行发送短信
            if (RemedyWorkflowConstant.SUBMITED_ACTION_TYPE.equals(request
                    .getParameter("isSubmited"))) {
                UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
                bo.sendMsg(user);
            }
            return super.forwardInfoPageWithUrl(mapping, request, "SuccessCode", url
                    + "queryList&&power=" + RemedyPowerConstant.LIST_POWER);
        }

        return mapping.findForward("");
    }

    /**
     * 执行修改修缮申请信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!CheckPower.checkPower(request.getSession(), RemedyPowerConstant.UPDATE_POWER)) {
            return mapping.findForward("powererror");
        }
        String applyId = request.getParameter("id");
        String url = request.getContextPath() + "/remedy_apply.do?method=";
        String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
        super.logger(RemedyApplyAction.class);

        RemedyApplyManager bo = new RemedyApplyManager();
        String operationCode = ExecuteCode.FAIL_CODE;
        try {
            operationCode = bo.updateApply(request, form);
        } catch (Exception e) {
            logger.error("执行修改修缮申请业务异常：" + e);
        }
        if (ExecuteCode.NO_DATA_ERROR.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotFindDataError", backUrl);
        }
        if (ExecuteCode.NOT_EDITED_APPLY_ERROR.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotEditedApply", backUrl);
        }
        if (ExecuteCode.EXIST_SAME_APPLY_ERROR.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "ExistSameApply", url
                    + "view&power=" + RemedyPowerConstant.UPDATE_POWER
                    + "&&to_page=update_remedy_apply&&apply_id=" + applyId);
        }
        if (ExecuteCode.NOT_EXIST_ITEM_ERROR.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotExistItem", url
                    + "view&power=" + RemedyPowerConstant.UPDATE_POWER
                    + "&&to_page=update_remedy_apply&&apply_id=" + applyId);
        }
        if (ExecuteCode.NOT_EXIST_MATERIAL_ERROR.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotExistMaterial", url
                    + "view&power=" + RemedyPowerConstant.UPDATE_POWER
                    + "&&to_page=update_remedy_apply&&apply_id=" + applyId);
        }
        if (ExecuteCode.NOT_ENOUGH_STORAGE_ERROR.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotEnoughStorage", url
                    + "view&power=" + RemedyPowerConstant.UPDATE_POWER
                    + "&&to_page=update_remedy_apply&&apply_id=" + applyId);
        }
        if (ExecuteCode.NOT_EXIST_MT_USED_ERROR.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotExistMtUsedError", backUrl);
        }
        if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "FailCode", url + "view&power="
                    + RemedyPowerConstant.UPDATE_POWER + "&&to_page=update_remedy_apply&&apply_id="
                    + applyId);
        }
        if (ExecuteCode.SUCCESS_CODE.equals(operationCode)) {
            // 执行发送短信
            if (RemedyWorkflowConstant.SUBMITED_ACTION_TYPE.equals(request
                    .getParameter("isSubmited"))) {
                UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
                bo.sendMsg(user);
            }
            return super.forwardInfoPageWithUrl(mapping, request, "SuccessCode", backUrl);
        }

        return mapping.findForward("");
    }

    /**
     * 执行删除修缮申请信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!CheckPower.checkPower(request.getSession(), RemedyPowerConstant.DELETE_POWER)) {
            return mapping.findForward("powererror");
        }
        String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
        super.logger(RemedyApplyAction.class);

        RemedyApplyManager bo = new RemedyApplyManager();
        String operationCode = ExecuteCode.FAIL_CODE;
        try {
            operationCode = bo.deleteApply(request, form);
        } catch (Exception e) {
            logger.error("执行删除修缮申请业务异常：" + e);
        }
        if (ExecuteCode.NO_DATA_ERROR.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotFindDataError", backUrl);
        }
        if (ExecuteCode.NOT_DELETED_APPLY_ERROR.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotDeletedApply", backUrl);
        }
        if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "FailCode", backUrl);
        }
        if (ExecuteCode.SUCCESS_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "SuccessCode", backUrl);
        }

        return mapping.findForward("");
    }

    public void initRemedyBaseBo() {
        // TODO Auto-generated method stub
        super.setRemedyBaseBo(new RemedyApplyManager());
    }

}
