package com.cabletech.linepatrol.remedy.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.constant.RemedyPowerConstant;
import com.cabletech.linepatrol.remedy.service.RemedySquareManager;
import com.cabletech.power.CheckPower;

public class RemedySquareAction extends RemedyBaseAction {
    public ActionForward squareApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!CheckPower.checkPower(request.getSession(), RemedyPowerConstant.SQUARE_POWER)) {
            return mapping.findForward("powererror");
        }
        String applyId = request.getParameter("apply_id");
        String url = request.getContextPath() + "/remedy_square.do?method=";
        String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
        super.logger(RemedySquareAction.class);

        RemedySquareManager bo = new RemedySquareManager();
        String operationCode = ExecuteCode.FAIL_CODE;
        try {
            operationCode = bo.squareApply(request, form);
        } catch (Exception e) {
            logger.error("÷¥––Ω·À„–ﬁ………Í«Î“µŒÒ“Ï≥££∫" + e);
        }
        if (ExecuteCode.NO_DATA_ERROR.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotFindDataError", backUrl);
        }
        if (ExecuteCode.NOT_SQUARED_APPLY_ERROR.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotSquaredApply", backUrl);
        }
        if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "FailCode", url + "view&power="
                    + RemedyPowerConstant.SQUARE_POWER + "&&to_page=square_remedy_apply&&apply_id="
                    + applyId);
        }
        if (ExecuteCode.SUCCESS_CODE.equals(operationCode)) {
            return super.forwardInfoPageWithUrl(mapping, request, "SuccessCode", backUrl);
        }

        return mapping.findForward("");
    }

    /**
     * ÷¥–––ﬁ………Í«Î“≥√Ê
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward querySquareForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        initRemedyBaseBo();
        return mapping.findForward("query_square_remedy_apply");
    }

    public void initRemedyBaseBo() {
        // TODO Auto-generated method stub
        super.setRemedyBaseBo(new RemedySquareManager());
    }

}
