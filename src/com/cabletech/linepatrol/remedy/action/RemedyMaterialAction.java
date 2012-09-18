package com.cabletech.linepatrol.remedy.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.linepatrol.remedy.beans.RemedyMaterialBean;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.constant.RemedyPowerConstant;
import com.cabletech.linepatrol.remedy.service.RemedyMaterialManager;
import com.cabletech.power.CheckPower;

public class RemedyMaterialAction extends BaseInfoBaseDispatchAction {
    private static Logger logger = Logger.getLogger(BaseInfoBaseDispatchAction.class.getName());

    /**
     * 写入日志信息
     * 
     * @param clazz
     *            Class
     */
    public void logger(Class clazz) {
        logger.info("Enter action class " + clazz.getName() + "...............");
    }

    /**
     * 执行材料微调查询表单页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward queryMaterialForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!CheckPower.checkPower(request.getSession(), RemedyPowerConstant.ADJUST_POWER)) {
            return mapping.findForward("powererror");
        }
        logger(RemedyMaterialAction.class);
        return mapping.findForward("query_remedy_material");
    }

    /**
     * 执行材料微调列表页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward queryList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!CheckPower.checkPower(request.getSession(), RemedyPowerConstant.ADJUST_POWER)) {
            return mapping.findForward("powererror");
        }
        logger(RemedyMaterialAction.class);
        super.setPageReset(request);

        RemedyMaterialManager bo = new RemedyMaterialManager();
        String condition = bo.compositeCondition(request, form);
        List list = bo.queryList(condition);

        request.getSession().setAttribute("MATERIAL_LIST", list);
        return mapping.findForward("list_remedy_material");
    }

    /**
     * 执行材料微调表单页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward adjustMaterialForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!CheckPower.checkPower(request.getSession(), RemedyPowerConstant.ADJUST_POWER)) {
            return mapping.findForward("powererror");
        }
        logger(RemedyMaterialAction.class);

        String applyMaterialId = request.getParameter("remedy_material_id");
        RemedyMaterialManager bo = new RemedyMaterialManager();
        RemedyMaterialBean oneMaterial = bo.view(applyMaterialId);

        request.setAttribute("remedy_material", oneMaterial);
        return mapping.findForward("adjust_remedy_material");
    }

    /**
     * 执行材料微调
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward adjustMaterial(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!CheckPower.checkPower(request.getSession(), RemedyPowerConstant.ADJUST_POWER)) {
            return mapping.findForward("powererror");
        }
        RemedyMaterialManager bo = new RemedyMaterialManager();
        String url = request.getContextPath() + "/remedy_material.do?method=";
        String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
        String operationCode = ExecuteCode.FAIL_CODE;
        try {
            operationCode = bo.adjustMaterial(request, form);
        } catch (Exception e) {
            logger.error("执行调整修缮材料业务异常：" + e);
        }
        if (ExecuteCode.NOT_EXIST_MATERIAL_ERROR.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotExistMaterial", backUrl);
        }
        if (ExecuteCode.NOT_ENOUGH_STORAGE_ERROR.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotEnoughStorage", backUrl);
        }
        if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "FailCode", url
                    + "adjustMaterialForm&&remedy_material_id=" + request.getParameter("id"));
        }
        if (ExecuteCode.SUCCESS_CODE.equals(operationCode)) {
            return super.forwardInfoPageWithUrl(mapping, request, "SuccessCode", backUrl);
        }
        return mapping.findForward("");
    }
}
