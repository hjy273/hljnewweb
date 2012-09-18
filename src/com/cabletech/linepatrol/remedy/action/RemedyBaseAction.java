package com.cabletech.linepatrol.remedy.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.linepatrol.remedy.constant.RemedyPowerConstant;
import com.cabletech.linepatrol.remedy.service.RemedyBaseBO;
import com.cabletech.power.CheckPower;

public abstract class RemedyBaseAction extends BaseInfoBaseDispatchAction {
    protected static Logger logger = Logger.getLogger(BaseInfoBaseDispatchAction.class.getName());

    private RemedyBaseBO remedyBaseBo;

    /**
     * @return the remedyBaseBo
     */
    public RemedyBaseBO getRemedyBaseBo() {
        return remedyBaseBo;
    }

    /**
     * @param remedyBaseBo
     *            the remedyBaseBo to set
     */
    public void setRemedyBaseBo(RemedyBaseBO remedyBaseBo) {
        this.remedyBaseBo = remedyBaseBo;
    }

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
     * 执行修缮申请的查询处理
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
        super.setPageReset(request);
        logger(RemedyBaseAction.class);

        initRemedyBaseBo();
        logger.info(remedyBaseBo.getClass().getName() + "...................");
        String condition = remedyBaseBo.compositeCondition(request, form);
        List list = remedyBaseBo.queryList(condition);

        request.getSession().setAttribute("APPLY_LIST", list);
        return mapping.findForward("list_remedy_apply");
    }

    /**
     * 执行修缮申请的查询处理
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward querySquareList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String power = request.getParameter("power");
        if (!RemedyPowerConstant.LIST_POWER.equals(power)
                && !RemedyPowerConstant.SQUARE_LIST_POWER.equals(power)) {
            if (!CheckPower.checkPower(request.getSession(), power)) {
                return mapping.findForward("powererror");
            }
        }
        super.setPageReset(request);
        logger(RemedyBaseAction.class);

        initRemedyBaseBo();
        logger.info(remedyBaseBo.getClass().getName() + "...................");
        String condition = remedyBaseBo.compositeCondition(request, form);
        List list = remedyBaseBo.queryList(condition);

        request.getSession().setAttribute("APPLY_LIST", list);
        request.getSession().setAttribute("POWER", power);

        return mapping.findForward("list_square_remedy_apply");
    }

    /**
     * 执行修缮申请的查看处理
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String power = request.getParameter("power");
        String toPage = request.getParameter("to_page");
        String applyState = request.getParameter("applyState");
        if (!RemedyPowerConstant.LIST_POWER.equals(power)
                && !RemedyPowerConstant.SQUARE_LIST_POWER.equals(power)) {
            if (!CheckPower.checkPower(request.getSession(), power)) {
                return mapping.findForward("powererror");
            }
        }
        logger(RemedyBaseAction.class);

        initRemedyBaseBo();
        String applyId = request.getParameter("apply_id");
        Map applyMap = remedyBaseBo.viewApply(applyId);

        if (applyMap == null) {
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "NotFindDataError", backUrl);
        }
        request.setAttribute("apply_map", applyMap);

        List nextProcessManList = remedyBaseBo.getNextProcessManList(applyState);
        request.setAttribute("next_process_man_list", nextProcessManList);

        List townList = remedyBaseBo.getRemedyTownList(request, form);
        request.setAttribute("town_list", townList);

        List itemList = remedyBaseBo.getRemedyItemList(request, form);
        request.setAttribute("item_list", itemList);

        List itemTypeList = remedyBaseBo.getRemedyItemTypeList(request, form);
        request.setAttribute("item_type_list", itemTypeList);

        List materialTypeList = remedyBaseBo.getRemedyMaterialTypeList(request, form);
        request.setAttribute("material_type_list", materialTypeList);

        List materialModelList = remedyBaseBo.getRemedyMaterialModelList(request, form);
        request.setAttribute("material_model_list", materialModelList);

        List materialList = remedyBaseBo.getRemedyMaterialList(request, form);
        request.setAttribute("material_list", materialList);

        List materialStorageList = remedyBaseBo.getRemedyMaterialStorageList(request, form);
        request.setAttribute("material_storage_list", materialStorageList);

        return mapping.findForward(toPage);
    }

    public abstract void initRemedyBaseBo();

}
