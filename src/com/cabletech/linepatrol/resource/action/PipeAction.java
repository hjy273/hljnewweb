package com.cabletech.linepatrol.resource.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.tags.services.DictionaryService;
import com.cabletech.commons.web.ClientException;
import com.cabletech.linepatrol.resource.beans.PipeBean;
import com.cabletech.linepatrol.resource.model.Pipe;
import com.cabletech.linepatrol.resource.service.PipeManager;
import com.cabletech.linepatrol.resource.service.RepeaterSectionManager;
import com.cabletech.linepatrol.resource.service.ResourceExportBO;

/**
 * @author zhj
 * 
 */
public class PipeAction extends BaseDispatchAction {
    private Logger logger = Logger.getLogger(PipeAction.class);

    public ActionForward addForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        return mapping.findForward("add");
    }

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws ClientException, Exception {
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");
        PipeBean bean = (PipeBean) form;
        Pipe pipe = new Pipe();
        BeanUtil.copyProperties(bean, pipe);
        WebApplicationContext ctx = getWebApplicationContext();
        PipeManager pm = (PipeManager) ctx.getBean("pipeManager");
        if (userInfo.getDeptype().equals("2")) {
            pipe.setMaintenanceId(userInfo.getDeptID());
        }
        pm.save(pipe);
        if ("update".equals(request.getParameter("operator"))) {
            log(request, "修改管道（管道名称为：" + bean.getWorkName() + "）", "资料管理");
        } else {
            log(request, "增加管道（管道名称为：" + bean.getWorkName() + "）", "资料管理");
        }
        logger.info("管道保存成功");
        return forwardInfoPage(mapping, request, "save_pipe_success", pipe.getPipeAddress());
    }

    public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws ClientException, Exception {
        String type = request.getParameter("type");
        String id = request.getParameter("id");
        WebApplicationContext ctx = getWebApplicationContext();
        PipeManager pm = (PipeManager) ctx.getBean("pipeManager");
        Pipe pipe = pm.getObject(id);
        request.setAttribute("pipe", pipe);
        if ("edit".equals(type)) {
            return mapping.findForward("edit");
        } else {
            return mapping.findForward("view");
        }
    }

    /**
     * 管道列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws ClientException, Exception {
    	PipeBean bean=(PipeBean)form;
    	String isQuery=request.getParameter("isQuery");
    	
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        WebApplicationContext ctx = getWebApplicationContext();
        PipeManager pm = (PipeManager) ctx.getBean("pipeManager");
        RepeaterSectionManager rsm = (RepeaterSectionManager) ctx.getBean("repeaterSectionManager");
        DictionaryService ds = (DictionaryService) ctx.getBean("dictionaryService");
        Map<String, String> property_right = ds.loadDictByAssortment("property_right", user
                .getRegionid());
        Map<String, String> terminal_address = ds.loadDictByAssortment("terminal_address", user
                .getRegionid());
        Map<String, String> pipe_type = ds.loadDictByAssortment("pipe_type", user.getRegionid());
        Map<Object, Object> contractor = rsm.loadContractor();
        List<Pipe> list = pm.getAllByDept(user,bean);
        ContractorBO contractorBO=new ContractorBO();
        List<Contractor> cons=contractorBO.getAllContractor(user);
        bean.setPipeType(setToString(bean.getPipeTypes()));
        bean.setRouteRes(setToString(bean.getRouteRess()));
        if(StringUtils.isNotBlank(isQuery)){
    		request.getSession().setAttribute("bean", bean);
    	}else{
    		request.getSession().removeAttribute("bean");
    	}
        request.getSession().setAttribute("result", list);
        request.getSession().setAttribute("property_right", property_right);
        request.getSession().setAttribute("terminal_address", terminal_address);
        request.getSession().setAttribute("pipe_type", pipe_type);
        request.getSession().setAttribute("contractor", contractor);
        request.getSession().setAttribute("cons", cons);
        return mapping.findForward("list");
    }

    /**
     * 管道重新分配第一步：转向选择代维单位界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward pipeAssignOne(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // maintenanceId用于返回上一步选择的代维单位
        String maintenanceId = request.getParameter("maintenanceId");
        request.setAttribute("maintenanceId", maintenanceId);
        return mapping.findForward("pipeAssignOne");
    }

    /**
     * 管道重新分配第二步：获得代维单位编号，转向分配管道界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward pipeAssignTwo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 获得代维单位编号
        String maintenanceId = request.getParameter("maintenanceId");
        // 获取已选择的管道编号
        String sublineid = request.getParameter("sublineid");
        WebApplicationContext ctx = getWebApplicationContext();
        PipeManager pm = (PipeManager) ctx.getBean("pipeManager");
        List<DynaBean> list = pm.getPipeInfoList(sublineid);
        request.setAttribute("maintenanceId", maintenanceId);
        request.setAttribute("list", list);
        return mapping.findForward("pipeAssignTwo");
    }

    /**
     * 根据代维单位编号和管道名称查询管道信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward searchPipe(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 管道名称
        String segmentname = request.getParameter("segmentname");
        // 代维单位编号
        String maintenanceId = request.getParameter("maintenanceId");
        // 已选择的管道
        String subline = request.getParameter("subline");
        WebApplicationContext ctx = getWebApplicationContext();
        PipeManager pm = (PipeManager) ctx.getBean("pipeManager");
        String message = pm.searchPipe(segmentname, maintenanceId, subline);
        logger.info("查询管道列表信息：" + message);
        outPrint(response, message);
        return null;
    }

    /**
     * 通过用户选择的管道查询管道详细信息列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward pipeAssignThree(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 获得代维单位编号
        String maintenanceId = request.getParameter("maintenanceId");
        // 管道编号
        String sublineid = request.getParameter("sublineid");
        if (StringUtils.isNotBlank(sublineid)) {
            if (sublineid.split(",").length > 1000) {
                return this.forwardErrorPage(mapping, request, "assignMoreThan1000");
            }
        }
        WebApplicationContext ctx = getWebApplicationContext();
        PipeManager pm = (PipeManager) ctx.getBean("pipeManager");
        List<DynaBean> list = pm.getPipeInfoList(sublineid);
        request.setAttribute("kid", sublineid);
        request.setAttribute("maintenanceId", maintenanceId);
        request.setAttribute("list", list);
        return mapping.findForward("pipeAssignThree");
    }

    /**
     * 管道重新分配
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward assignPipe(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        PipeBean bean = (PipeBean) form;
        WebApplicationContext ctx = getWebApplicationContext();
        PipeManager pm = (PipeManager) ctx.getBean("pipeManager");
        pm.assignPipe(bean);
        return forwardInfoPage(mapping, request, "pipeAssignSuccess");
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
        Map<String, String> pipeType = (Map<String, String>) request.getSession().getAttribute(
                "pipe_type");
        Map<String, String> terminalAddress = (Map<String, String>) request.getSession()
                .getAttribute("terminal_address");
        Map<String, String> propertyRight = (Map<String, String>) request.getSession()
                .getAttribute("property_right");
        exportBO.exportPipes(list, contractor, pipeType, terminalAddress, propertyRight,
                response);
        return null;
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
