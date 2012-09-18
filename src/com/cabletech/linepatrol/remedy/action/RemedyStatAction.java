package com.cabletech.linepatrol.remedy.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.remedy.dao.ExportStatDao;
import com.cabletech.linepatrol.remedy.service.RemedyStatManager;

public class RemedyStatAction extends BaseInfoBaseDispatchAction {
    private static Logger logger = Logger.getLogger(RemedyStatAction.class.getName());

    private static RemedyStatManager remedyStatManager = new RemedyStatManager();

    /**
     * 按区域统计页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward statRemedyFormByTown(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List contractors = remedyStatManager.queryAllContractor(user.getRegionid());
        List towns = remedyStatManager.queryAllTown(user.getRegionid());
        request.setAttribute("contractors", contractors);
        request.setAttribute("towns", towns);
        return mapping.findForward("statQueryByTown");
    }

    /**
     * 按代维统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward statRemedyFormByContractor(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List contractors = remedyStatManager.queryAllContractor(user.getRegionid());
        request.setAttribute("contractors", contractors);
        return mapping.findForward("statQueryByContractor");
    }

    /**
     * 代维查询统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward statRemedyFormForContractor(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mapping.findForward("statQueryForContractor");
    }

    /**
     * 按区县分类查询统计结果
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward statRemedyByTown(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String townId = request.getParameter("townId");
        String month = request.getParameter("month");
        List statList = new ArrayList();
        if (townId.equals("0")) {
            statList = remedyStatManager.statRemedyByTown(month);
            request.setAttribute("month", month);
            request.setAttribute("townMap", statList.get(0));
            request.getSession().setAttribute("townMap", statList.get(0));
            request.setAttribute("haveTown", "false");
        } else {
            statList = remedyStatManager.statRemedyDetailByTown(townId, month);
            Map townMap = (Map) request.getSession().getAttribute("townMap");
            if (townMap != null) {
                request.setAttribute("town", townMap.get(townId));
            } else {
                Map map = (Map) statList.get(statList.size() - 1);
                if (map != null) {
                    request.setAttribute("town", map.get(townId));
                }
            }
            request.setAttribute("month", month);
            request.setAttribute("itemMap", statList.get(0));
            request.setAttribute("haveTown", "true");
        }
        Map labelMap = (Map) statList.get(1);
        request.setAttribute("labelMap", labelMap);
        Iterator iterator = labelMap.keySet().iterator();
        float labelTotal = 0;
        while (iterator.hasNext()) {
            labelTotal += Float.parseFloat(labelMap.get(iterator.next()) + "");
        }
        request.setAttribute("labelTotal", labelTotal + "");
        request.setAttribute("scaleMap", statList.get(2));
        request.getSession().setAttribute("statList", statList);
        return mapping.findForward("statRemedyList");
    }

    /**
     * 饼、柱状图XML
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward statRemedyForPieChart(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String month = request.getParameter("month");
        List statList = remedyStatManager.statRemedyByTown(month);
        Map townMap = (Map) statList.get(0);
        Map labelMap = (Map) statList.get(1);
        request.setAttribute("townMap", townMap);
        request.setAttribute("labelMap", labelMap);
        request.setAttribute("month", month);
        logger.info("townMap=" + townMap);
        logger.info("labelMap=" + labelMap);
        return mapping.findForward("statRemedyForPieChart");
    }

    /**
     * 通过代维查询统计结果）
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward statRemedyByContractor(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	super.setPageReset(request);
        RemedyStatManager remedyStatManager = new RemedyStatManager();
        List statList = new ArrayList();
        String contractorId = request.getParameter("contractorId");
        String month = request.getParameter("month");
        statList = remedyStatManager.statRemedyByContractor(month, contractorId);
        request.getSession().setAttribute("statList", statList);
        request.setAttribute("itemList", statList.get(0));
        request.setAttribute("labelList", statList.get(1));
        String contractorName = remedyStatManager.queryContractorNameById(contractorId);
        request.setAttribute("contractorName", contractorName);
        request.setAttribute("month", month);
        
/*        List itemname = (List) statList.get(0);
        List lableList = (List) statList.get(1);
        for(int i = 0;i<itemname.size();i++){
        	System.out.println("itemname==== "+itemname.get(i));
        }
        
        for(int i = 0;i<lableList.size();i++){
        	List list = (List) lableList.get(i);
        	System.out.println("list.size()== "+list.size());
        	for(int j = 0;j<list.size();j++)
        	System.out.println("ssssssssssssss==== "+list.get(j));
        }*/
        
        return mapping.findForward("statRemedyDetailList");
    }

    /**
     * 代维查询统计结果
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward statRemedyForContractor(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List statList = new ArrayList();
        String contractorId = user.getDeptID();
        String month = request.getParameter("month");
        statList = remedyStatManager.statRemedyByContractor(month, contractorId);
        request.getSession().setAttribute("statList", statList);
        request.setAttribute("itemList", statList.get(0));
        request.setAttribute("labelList", statList.get(1));
        String contractorName = remedyStatManager.queryContractorNameById(contractorId);
        request.setAttribute("contractorName", contractorName);
        request.setAttribute("month", month);
        return mapping.findForward("statRemedyDetailList");
    }

    /**
     * 导出按区域统计的Excel
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward exportStatByTown(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            List statList = (List) request.getSession().getAttribute("statList");
            logger.info("得到list");
            ExportStatDao exdao = new ExportStatDao();
            if (exdao.exportStatByTown(statList, response)) {
                logger.info("输出excel成功");
            }
            return null;
        } catch (Exception e) {
            logger.error("导出修缮统计信息一览表出现异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 导出按修缮修项统计的Excel
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward exportStatDetailByTown(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            List list = (List) request.getSession().getAttribute("statList");
            logger.info("得到list");
            ExportStatDao exdao = new ExportStatDao();
            if (exdao.exportStatDetailByTown(list, response)) {
                logger.info("输出excel成功");
            }
            return null;
        } catch (Exception e) {
            logger.error("导出修缮统计信息一览表出现异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 导出按代维统计的Excel
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward exportStatByContractor(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            List list = (List) request.getSession().getAttribute("statList");
            logger.info("得到list");
            ExportStatDao exdao = new ExportStatDao();
            if (exdao.exportStatByContractor(list, response)) {
                logger.info("输出excel成功");
            }
            return null;
        } catch (Exception e) {
            logger.error("导出修缮统计信息一览表出现异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }
}
