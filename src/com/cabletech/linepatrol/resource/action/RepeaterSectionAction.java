package com.cabletech.linepatrol.resource.action;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.Region;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.baseinfo.services.RegionBO;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.tags.services.DictionaryService;
import com.cabletech.commons.upload.module.UploadFileInfo;
import com.cabletech.commons.web.ClientException;
import com.cabletech.linepatrol.acceptance.service.AcceptanceConstant;
import com.cabletech.linepatrol.resource.beans.RepeaterSectionBean;
import com.cabletech.linepatrol.resource.model.RepeaterSection;
import com.cabletech.linepatrol.resource.service.RepeaterSectionManager;
import com.cabletech.linepatrol.resource.service.ResourceExportBO;
import com.cabletech.linepatrol.resource.service.TrunkManager;

/**
 * 光缆中继段管理
 * @author zhj
 *
 */
/**
 * @author Administrator
 * 
 */
public class RepeaterSectionAction extends BaseDispatchAction {

    private Logger logger = Logger.getLogger(RepeaterSectionAction.class);

    public ActionForward addForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        return mapping.findForward("add");
    }

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws ClientException, Exception {
        WebApplicationContext ctx = getWebApplicationContext();
        RepeaterSectionManager rsm = (RepeaterSectionManager) ctx.getBean("repeaterSectionManager");

        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

        List<FileItem> files = (List<FileItem>) session.getAttribute("FILES");
        List<UploadFileInfo> ufiles = (List<UploadFileInfo>) session.getAttribute("OLDFILE");
        int size = 0;
        if (files != null) {
            size += files.size();
        }
        if (ufiles != null) {
            size += ufiles.size();
        }
        if (size > 1) {
            session.removeAttribute("FILES");
            return forwardInfoPage(mapping, request, "updateFileTooManyBack");
        }

        RepeaterSectionBean bean = (RepeaterSectionBean) form;
        String[] laytype = request.getParameterValues("laytype");
        bean.setLaytype(laytype);
        RepeaterSection rs = new RepeaterSection();
        BeanUtil.copyProperties(bean, rs);
        if (userInfo.getDeptype().equals("2")) {
            rs.setMaintenanceId(userInfo.getDeptID());
        }
        rs.setRegion(userInfo.getRegionID());
        rs.setIsCheckOut("y");
        rs.setScrapState("false");
        rsm.save(rs, files, userInfo);
        logger.info("中继段保存成功");
        if ("update".equals(request.getParameter("operator"))) {
            log(request, "修改光缆中继段（中继段名称为：" + bean.getSegmentname() + "）", "资料管理");
            return super.forwardInfoPageUrl(mapping, request, "save_res_success", new Object[] { rs
                    .getSegmentname() }, (String) request.getSession().getAttribute("S_BACK_URL"));
        } else {
            log(request, "添加光缆中继段（中继段名称为：" + bean.getSegmentname() + "）", "资料管理");
        }
        return forwardInfoPage(mapping, request, "save_res_success", rs.getSegmentname());
    }

    public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws ClientException, Exception {
        WebApplicationContext ctx = getWebApplicationContext();
        RepeaterSectionManager rsm = (RepeaterSectionManager) ctx.getBean("repeaterSectionManager");
        TrunkManager trunkManager = (TrunkManager) ctx.getBean("trunkManager");

        String type = request.getParameter("type");
        String id = request.getParameter("id");
        Map<Object, Object> contractor = rsm.loadContractor();
        HttpSession session = request.getSession();
        request.setAttribute("rs", rsm.getObject(id));
        session.setAttribute("addOnes", trunkManager.getAnnexAddOneList(id,
                AcceptanceConstant.CABLEFILE, AcceptanceConstant.USABLE));
        session.setAttribute("contractor", contractor);
        if ("edit".equals(type)) {
            return mapping.findForward("edit");
        } else {
            return mapping.findForward("view");
        }
    }

    /**
     * 中继段列表
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws ClientException, Exception {
    	RepeaterSectionBean bean=(RepeaterSectionBean)form;
        super.setPageReset(request);
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        WebApplicationContext ctx = getWebApplicationContext();
        RepeaterSectionManager rsm = (RepeaterSectionManager) ctx.getBean("repeaterSectionManager");
        ContractorBO contractorBO=new ContractorBO();
        DictionaryService ds = (DictionaryService) ctx.getBean("dictionaryService");
        Map<String, String> property_right = ds.loadDictByAssortment("property_right", user
                .getRegionid());
        Map<String, String> cabletype = ds.loadDictByAssortment("cabletype", user.getRegionid());
        Map<String, String> layingmethod = ds.loadDictByAssortment("layingmethod", user
                .getRegionid());
        Map<String, String> sections = ds.loadDictByAssortment("terminal_address", user
                .getRegionid());
        RegionBO regionBO=new RegionBO();
        Map<String, String> places = regionBO.queryForMap(user.getRegionID());
        Map<Object, Object> contractor = rsm.loadContractor();
        List<Contractor> cons=contractorBO.getAllContractor(user);
//        String key = request.getParameter("key");
        String cableLevel = request.getParameter("cableLevel");
        List<RepeaterSection> list = rsm.getAllByDept(user,bean);
        String laytype=request.getParameter("laytype");
//        request.setAttribute("key", key);
        request.setAttribute("cableLevel", cableLevel);
        request.setAttribute("laytype", laytype);
        String isQuery=request.getParameter("isQuery");
        if(StringUtils.isNotBlank(isQuery)){
        	request.getSession().setAttribute("bean", bean);
        }else{
        	request.getSession().removeAttribute("bean");
        }
        bean.setCableLevel(setToString(bean.getCableLevels()));
        bean.setLaytype(setToString(bean.getLaytypes()));
        request.getSession().setAttribute("result", list);
        request.getSession().setAttribute("property_right", property_right);
        request.getSession().setAttribute("cabletype", cabletype);
        request.getSession().setAttribute("layingmethod", layingmethod);
        request.getSession().setAttribute("sections", sections);
        request.getSession().setAttribute("places", places);
        request.getSession().setAttribute("contractor", contractor);
        request.getSession().setAttribute("cons", cons);
        return mapping.findForward("list");
    }

    public ActionForward approveCableList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        WebApplicationContext ctx = getWebApplicationContext();
        RepeaterSectionManager rsm = (RepeaterSectionManager) ctx.getBean("repeaterSectionManager");
        DictionaryService ds = (DictionaryService) ctx.getBean("dictionaryService");

        Map<String, String> property_right = ds.loadDictByAssortment("property_right", user
                .getRegionid());
        Map<String, String> cabletype = ds.loadDictByAssortment("cabletype", user.getRegionid());
        Map<String, String> layingmethod = ds.loadDictByAssortment("layingmethod", user
                .getRegionid());

        List<RepeaterSection> list = rsm.getWaitApprove();
        request.getSession().setAttribute("result", list);
        request.getSession().setAttribute("property_right", property_right);
        request.getSession().setAttribute("cabletype", cabletype);
        request.getSession().setAttribute("layingmethod", layingmethod);
        return mapping.findForward("approveCableList");
    }

    public ActionForward approveLink(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        WebApplicationContext ctx = getWebApplicationContext();
        RepeaterSectionManager rsm = (RepeaterSectionManager) ctx.getBean("repeaterSectionManager");
        TrunkManager trunkManager = (TrunkManager) ctx.getBean("trunkManager");

        String type = request.getParameter("type");
        String id = request.getParameter("id");

        HttpSession session = request.getSession();
        request.setAttribute("type", type);
        request.setAttribute("rs", rsm.getObject(id));
        session.setAttribute("addOnes", trunkManager.getAnnexAddOneList(id,
                AcceptanceConstant.CABLEFILE, AcceptanceConstant.USABLE));
        return mapping.findForward("approveCable");
    }

    public ActionForward approve(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        WebApplicationContext ctx = getWebApplicationContext();
        RepeaterSectionManager rsm = (RepeaterSectionManager) ctx.getBean("repeaterSectionManager");

        String id = request.getParameter("id");
        String type = request.getParameter("type");

        rsm.approve(id, type);
        log(request, "审核中继段（中继段名称为：" + rsm.getObject(id).getSegmentname() + "）", "资料管理");
        return forwardInfoPage(mapping, request, "cableApproveSuccess");
    }

    /**
     * 验证中继段编号是否重复
     */
    public ActionForward validateCode(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        WebApplicationContext ctx = getWebApplicationContext();
        RepeaterSectionManager rsm = (RepeaterSectionManager) ctx.getBean("repeaterSectionManager");
        String code = request.getParameter("code");
        boolean result = rsm.validateCode(code);
        if (result) {
            outPrint(response, "中继段编号已经存在");
        } else {
            outPrint(response, "");
        }
        return null;
    }

    /**
     * 加载光缆所在城区信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward loadTown(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String scetion = request.getParameter("scetion");
        String sel = request.getParameter("sel");
        WebApplicationContext ctx = getWebApplicationContext();
        // DictionaryService ds = (DictionaryService)
        // ctx.getBean("dictionaryService");
        // Map<Object, Object> towns =
        // ds.findByAssortAndCode("terminal_address", scetion, user
        // .getRegionid());
        StringBuffer options = new StringBuffer();
        RegionBO regionBo = new RegionBO();
        String condition = " and parentregionid='" + user.getRegionid() + "' ";
        List<Region> regionList = regionBo.queryForList(condition);
        // Iterator<Object> it = towns.keySet().iterator();
        options.append("<option value=\"\">请选择...</option>");
        for (int i = 0; regionList != null && i < regionList.size(); i++) {
            Region region = regionList.get(i);
            String key = region.getRegionID();
            String value = region.getRegionName();
            options.append("<option value=\"" + key + "\"");
            if ((sel != null || !"".equals(sel)) && key.equals(sel)) {
                options.append(" selected=\"selected\" ");
            }
            options.append(">");
            options.append(value);
            options.append("</option>");
        }
        // while (it.hasNext()) {
        // String key = it.next().toString();
        // String value = towns.get(key).toString();
        // options.append("<option value=\"" + key + "\"");
        // if ((sel != null || !"".equals(sel)) && key.equals(sel)) {
        // options.append(" selected=\"selected\" ");
        // }
        // options.append(">");
        // options.append(value);
        // options.append("</option>");
        // }

        super.outPrint(response, options.toString());
        return null;
    }

    /**
     * 光缆报废
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward scrap(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws ClientException, Exception {
        WebApplicationContext ctx = getWebApplicationContext();
        RepeaterSectionManager rsm = (RepeaterSectionManager) ctx.getBean("repeaterSectionManager");

        String id = request.getParameter("id");
        RepeaterSection rs = rsm.scrap(id);
        List<RepeaterSection> list = (List<RepeaterSection>) request.getSession().getAttribute(
                "result");
        list.remove(rs);
        request.getSession().setAttribute("result", list);
        log(request, "中继段作废（中继段名称为：" + rsm.getObject(id).getSegmentname() + "）", "资料管理");
        super.outPrint(response, rs.getSegmentname() + "中继段已被作废！作废的中继段不在进行维护。");
        return null;
    }

    /**
     * 导出
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward exportList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResourceExportBO exportBO = new ResourceExportBO();
        List list = (List) request.getSession().getAttribute("result");
        Map<String, String> contractor = (Map<String, String>) request.getSession().getAttribute(
                "contractor");
        Map<String, String> places = (Map<String, String>) request.getSession().getAttribute(
                "places");
        Map<String, String> sections = (Map<String, String>) request.getSession().getAttribute(
                "sections");
        Map<String, String> cabletype = (Map<String, String>) request.getSession().getAttribute(
                "cabletype");
        Map<String, String> layingmethod = (Map<String, String>) request.getSession().getAttribute(
                "layingmethod");
        exportBO.exportRepeaters(list, contractor, places, sections, cabletype, layingmethod,
                response);
        return null;
    }

    /**
     * 光缆重新分配第一步：转向选择代维单位界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward cableAssignOne(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // maintenanceId用于返回上一步选择的代维单位
        String maintenanceId = request.getParameter("maintenanceId");
        request.setAttribute("maintenanceId", maintenanceId);
        return mapping.findForward("cableAssignOne");
    }

    /**
     * 光缆重新分配第二步：获得代维单位编号，转向分配光缆界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward cableAssignTwo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 获得代维单位编号
        String maintenanceId = request.getParameter("maintenanceId");
        // 获取已选择的光缆编号
        String sublineid = request.getParameter("sublineid");
        WebApplicationContext ctx = getWebApplicationContext();
        RepeaterSectionManager rsm = (RepeaterSectionManager) ctx.getBean("repeaterSectionManager");
        List<DynaBean> list = rsm.getCableInfoList(sublineid);
        request.setAttribute("maintenanceId", maintenanceId);
        request.setAttribute("list", list);
        return mapping.findForward("cableAssignTwo");
    }

    /**
     * 根据代维单位编号和光缆名称查询光缆信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward searchCable(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 光缆名称
        String segmentname = request.getParameter("segmentname");
        // 代维单位编号
        String maintenanceId = request.getParameter("maintenanceId");
        // 已选择的光缆
        String subline = request.getParameter("subline");
        WebApplicationContext ctx = getWebApplicationContext();
        RepeaterSectionManager rsm = (RepeaterSectionManager) ctx.getBean("repeaterSectionManager");
        String message = rsm.searchCable(segmentname, maintenanceId, subline);
        logger.info("查询光缆列表信息：" + message);
        outPrint(response, message);
        return null;
    }

    /**
     * 通过用户选择的光缆查询光缆详细信息列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward cableAssignThree(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 获得代维单位编号
        String maintenanceId = request.getParameter("maintenanceId");
        // 光缆编号
        String sublineid = request.getParameter("sublineid");
        if (StringUtils.isNotBlank(sublineid)) {
            if (sublineid.split(",").length > 1000) {
                return this.forwardErrorPage(mapping, request, "assignMoreThan1000");
            }
        }
        WebApplicationContext ctx = getWebApplicationContext();
        RepeaterSectionManager rsm = (RepeaterSectionManager) ctx.getBean("repeaterSectionManager");
        List<DynaBean> list = rsm.getCableInfoList(sublineid);
        request.setAttribute("kid", sublineid);
        request.setAttribute("maintenanceId", maintenanceId);
        request.setAttribute("list", list);
        return mapping.findForward("cableAssignThree");
    }

    /**
     * 光缆重新分配
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward assignCable(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        RepeaterSectionBean bean = (RepeaterSectionBean) form;
        WebApplicationContext ctx = getWebApplicationContext();
        RepeaterSectionManager rsm = (RepeaterSectionManager) ctx.getBean("repeaterSectionManager");
        rsm.assignCable(bean);
        return forwardInfoPage(mapping, request, "cableAssignSuccess");
    }
    
    public String setToString(String[] strs) {
		StringBuffer sb = new StringBuffer();
		if (strs != null && strs.length > 0) {
			sb.append(strs[0]);
			for (int i = 1; i < strs.length; i++) {
				sb.append("," + strs[i]);
			}
		}
		return sb.toString();
	}
}
