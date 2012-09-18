package com.cabletech.linepatrol.remedy.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.constant.RemedyPowerConstant;
import com.cabletech.linepatrol.remedy.service.RemedyCheckManager;
import com.cabletech.power.CheckPower;

public class RemedyCheckAction extends RemedyBaseAction {
    public ActionForward checkApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!CheckPower.checkPower(request.getSession(), RemedyPowerConstant.CHECK_POWER)) {
            return mapping.findForward("powererror");
        }
        String applyId = request.getParameter("apply_id");
        String url = request.getContextPath() + "/remedy_check.do?method=";
        String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
        super.logger(RemedyCheckAction.class);

        RemedyCheckManager bo = new RemedyCheckManager();
        String operationCode = ExecuteCode.FAIL_CODE;
        try {
            operationCode = bo.checkApply(request, form);
        } catch (Exception e) {
            logger.error("执行验收修缮申请业务异常：" + e);
        }
        if (ExecuteCode.NO_DATA_ERROR.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotFindDataError", backUrl);
        }
        if (ExecuteCode.NOT_CHECKED_APPLY_ERROR.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotCheckedApply", backUrl);
        }
        if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "FailCode", url + "view&power="
                    + RemedyPowerConstant.CHECK_POWER + "&&to_page=check_remedy_apply&&apply_id="
                    + applyId);
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
        super.setRemedyBaseBo(new RemedyCheckManager());
    }

}
