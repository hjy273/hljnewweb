package com.cabletech.linepatrol.remedy.action;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.remedy.beans.RemedyItemBean;
import com.cabletech.linepatrol.remedy.service.RemedyItemManager;
import com.cabletech.linepatrol.remedy.service.RemedyTypeManager;

public class RemedyItemAction extends BaseInfoBaseDispatchAction {
    private static Logger logger = Logger.getLogger(RemedyItemAction.class.getName());

    private RemedyItemManager bo = new RemedyItemManager();

    private RemedyTypeManager typeBO = new RemedyTypeManager();

    /**
     * ת�������������ҳ��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward addRemedyItemForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List regions = bo.getRegions(user);
        request.setAttribute("regions", regions);
        return mapping.findForward("addItemForm");
    }

    /**
     * �������������Ϣ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward addRemedyItem(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        RemedyItemBean bean = (RemedyItemBean) form;
        String regionID = bean.getRegionID();
        String itemName = bean.getItemName();
        String continueAddType = request.getParameter("continue_add_type");
        boolean flag = bo.isHaveItem(regionID, itemName);
        if (flag) {
            boolean result = bo.addItem(bean);
            if (result) {
                if ("0".equals(continueAddType)) {
                    return super.forwardInfoPage(mapping, request, "511add");
                } else {
                    request.setAttribute("item_id", bean.getId());
                    request.setAttribute("item_name", bean.getItemName());
                    return mapping.findForward("addItemTypeForm");
                }
            } else {
                return super.forwardErrorPage(mapping, request, "511addE");
            }
        } else {
            return super.forwardInfoPage(mapping, request, "511repeat");
        }
    }

    /**
     * ת����ѯҳ��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward queryRemedyItemForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List regions = bo.getRegions(user);
        request.setAttribute("regions", regions);
        return mapping.findForward("queryItemForm");
    }

    /**
     * �õ���ѯ��������Ŀ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward getRemedyItems(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        RemedyItemBean bean = (RemedyItemBean) form;
        super.setPageReset(request);
        request.getSession().setAttribute("querybean", bean);
        List items = bo.getItems(bean);
        request.getSession().setAttribute("items", items);
        return mapping.findForward("listItems");
    }

    /**
     * �����������ѯ��������б�ķ��ص�ԭ������ѯ���������б�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward getRemedyItemsByBack(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        RemedyItemBean bean = (RemedyItemBean) request.getSession().getAttribute("querybean");
        super.setPageReset(request);
        List items = bo.getItems(bean);
        request.getSession().setAttribute("items", items);
        return mapping.findForward("listItems");
    }

    /**
     * ����������id��ѯ�������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward getTypesByItemID(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        List types = typeBO.getTypeByItemID(id);
        request.getSession().setAttribute("types", types);
        return mapping.findForward("listTypesByItemID");
    }

    /**
     * �鿴������Ŀ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward viewTypesByID(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        RemedyItemBean bean = new RemedyItemBean();
        BasicDynaBean basicBean = bo.getItemById(id);
        if (basicBean != null) {
            BigDecimal tid = (BigDecimal) basicBean.get("id");
            String itemName = (String) basicBean.get("itemname");
            String regionid = (String) basicBean.get("regionid");
            String remark = (String) basicBean.get("remark");
            bean.setRegionID(regionid);
            bean.setRemark(remark);
            bean.setItemName(itemName);
            bean.setTid(tid.intValue());
        }
        String regionId = bean.getRegionID();
        String regionName = bo.getRegionNameById(regionId);
        request.setAttribute("bean", bean);
        request.setAttribute("regionName", regionName);
        return mapping.findForward("viewRemedyItem");
    }

    /**
     * ת���޸�ҳ��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward editRemedyItemForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        RemedyItemBean bean = new RemedyItemBean();
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        BasicDynaBean basicBean = bo.getItemById(id);
        if (basicBean != null) {
            BigDecimal tid = (BigDecimal) basicBean.get("id");
            String itemName = (String) basicBean.get("itemname");
            String regionid = (String) basicBean.get("regionid");
            String remark = (String) basicBean.get("remark");
            bean.setRegionID(regionid);
            bean.setRemark(remark);
            bean.setItemName(itemName);
            bean.setTid(tid.intValue());
        }
        request.setAttribute("bean", bean);
        List regions = bo.getRegions(user);
        request.setAttribute("regions", regions);
        return mapping.findForward("editRemedyItemForm");
    }

    /**
     * �޸�������Ŀ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward editRemedyItem(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Object[] args = new Object[1];
        args[0] = (String) request.getSession().getAttribute("S_BACK_URL");
        RemedyItemBean bean = (RemedyItemBean) form;
        boolean result = bo.isHaveItem(bean);
        if (!result) {
            return super.forwardInfoPage(mapping, request, "511editRepeat", null, args);
        }
        boolean flag = bo.editItem(bean);
        if (flag) {
            return super.forwardInfoPage(mapping, request, "511edit", null, args);
        } else {
            return super.forwardErrorPage(mapping, request, "511editE", args);
        }
    }

    /**
     * ɾ��������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward deleteRemedyItem(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Object[] args = new Object[1];
        args[0] = (String) request.getSession().getAttribute("S_BACK_URL");
        String id = request.getParameter("id");
        boolean result = bo.getItemByApply(id);
        if (result) {
            return super.forwardInfoPage(mapping, request, "511deleN", null, args);
        }
        boolean flag = bo.deleteItem(id);
        if (flag) {
            return super.forwardInfoPage(mapping, request, "511dele", null, args);
        } else {
            return super.forwardErrorPage(mapping, request, "511deleE", args);
        }
    }

    /**
     * ִ�е���Excel
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward exportItmeList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("����������Ŀ=========action===");
        List list = (List) request.getSession().getAttribute("items");
        bo.exportStorage(list, response);
        return null;
    }

}
