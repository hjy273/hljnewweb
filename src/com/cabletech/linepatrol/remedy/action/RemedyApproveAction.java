package com.cabletech.linepatrol.remedy.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.constant.RemedyPowerConstant;
import com.cabletech.linepatrol.remedy.service.RemedyApproveManager;
import com.cabletech.power.CheckPower;

public class RemedyApproveAction extends RemedyBaseAction {
    public ActionForward approveApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!CheckPower.checkPower(request.getSession(), RemedyPowerConstant.APPROVE_POWER)) {
            return mapping.findForward("powererror");
        }
        String applyId = request.getParameter("apply_id");
        String url = request.getContextPath() + "/remedy_approve.do?method=";
        String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
        logger(RemedyApproveAction.class);

        RemedyApproveManager bo = new RemedyApproveManager();
        String operationCode = ExecuteCode.FAIL_CODE;
        try {
            operationCode = bo.approveApply(request, form);
        } catch (Exception e) {
            logger.error("执行审批修缮申请业务异常：" + e);
        }
        if (ExecuteCode.NO_DATA_ERROR.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotFindDataError", backUrl);
        }
        if (ExecuteCode.NOT_APPROVED_APPLY_ERROR.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotApprovedApply", backUrl);
        }
        if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "FailCode", url + "view&&power="
                    + RemedyPowerConstant.APPROVE_POWER
                    + "&&to_page=approve_remedy_apply&&apply_id=" + applyId);
        }
        if (ExecuteCode.SUCCESS_CODE.equals(operationCode)) {
            UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
            bo.sendMsg(user);
            return super.forwardInfoPageWithUrl(mapping, request, "SuccessCode", backUrl);
        }

        return mapping.findForward("");
    }

    public void initRemedyBaseBo() {
        // TODO Auto-generated method stub
        super.setRemedyBaseBo(new RemedyApproveManager());
    }

}
