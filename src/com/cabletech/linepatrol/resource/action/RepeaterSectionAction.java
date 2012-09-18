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
 * �����м̶ι���
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
        logger.info("�м̶α���ɹ�");
        if ("update".equals(request.getParameter("operator"))) {
            log(request, "�޸Ĺ����м̶Σ��м̶�����Ϊ��" + bean.getSegmentname() + "��", "���Ϲ���");
            return super.forwardInfoPageUrl(mapping, request, "save_res_success", new Object[] { rs
                    .getSegmentname() }, (String) request.getSession().getAttribute("S_BACK_URL"));
        } else {
            log(request, "��ӹ����м̶Σ��м̶�����Ϊ��" + bean.getSegmentname() + "��", "���Ϲ���");
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
     * �м̶��б�
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
        log(request, "����м̶Σ��м̶�����Ϊ��" + rsm.getObject(id).getSegmentname() + "��", "���Ϲ���");
        return forwardInfoPage(mapping, request, "cableApproveSuccess");
    }

    /**
     * ��֤�м̶α���Ƿ��ظ�
     */
    public ActionForward validateCode(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        WebApplicationContext ctx = getWebApplicationContext();
        RepeaterSectionManager rsm = (RepeaterSectionManager) ctx.getBean("repeaterSectionManager");
        String code = request.getParameter("code");
        boolean result = rsm.validateCode(code);
        if (result) {
            outPrint(response, "�м̶α���Ѿ�����");
        } else {
            outPrint(response, "");
        }
        return null;
    }

    /**
     * ���ع������ڳ�����Ϣ
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
        options.append("<option value=\"\">��ѡ��...</option>");
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
     * ���±���
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
        log(request, "�м̶����ϣ��м̶�����Ϊ��" + rsm.getObject(id).getSegmentname() + "��", "���Ϲ���");
        super.outPrint(response, rs.getSegmentname() + "�м̶��ѱ����ϣ����ϵ��м̶β��ڽ���ά����");
        return null;
    }

    /**
     * ����
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
     * �������·����һ����ת��ѡ���ά��λ����
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
        // maintenanceId���ڷ�����һ��ѡ��Ĵ�ά��λ
        String maintenanceId = request.getParameter("maintenanceId");
        request.setAttribute("maintenanceId", maintenanceId);
        return mapping.findForward("cableAssignOne");
    }

    /**
     * �������·���ڶ�������ô�ά��λ��ţ�ת�������½���
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
        // ��ô�ά��λ���
        String maintenanceId = request.getParameter("maintenanceId");
        // ��ȡ��ѡ��Ĺ��±��
        String sublineid = request.getParameter("sublineid");
        WebApplicationContext ctx = getWebApplicationContext();
        RepeaterSectionManager rsm = (RepeaterSectionManager) ctx.getBean("repeaterSectionManager");
        List<DynaBean> list = rsm.getCableInfoList(sublineid);
        request.setAttribute("maintenanceId", maintenanceId);
        request.setAttribute("list", list);
        return mapping.findForward("cableAssignTwo");
    }

    /**
     * ���ݴ�ά��λ��ź͹������Ʋ�ѯ������Ϣ
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
        // ��������
        String segmentname = request.getParameter("segmentname");
        // ��ά��λ���
        String maintenanceId = request.getParameter("maintenanceId");
        // ��ѡ��Ĺ���
        String subline = request.getParameter("subline");
        WebApplicationContext ctx = getWebApplicationContext();
        RepeaterSectionManager rsm = (RepeaterSectionManager) ctx.getBean("repeaterSectionManager");
        String message = rsm.searchCable(segmentname, maintenanceId, subline);
        logger.info("��ѯ�����б���Ϣ��" + message);
        outPrint(response, message);
        return null;
    }

    /**
     * ͨ���û�ѡ��Ĺ��²�ѯ������ϸ��Ϣ�б�
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
        // ��ô�ά��λ���
        String maintenanceId = request.getParameter("maintenanceId");
        // ���±��
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
     * �������·���
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
