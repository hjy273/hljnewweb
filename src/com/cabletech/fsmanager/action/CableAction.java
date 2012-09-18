/** create by jixf
 *  date: 2009-08-17
 */
package com.cabletech.fsmanager.action;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.fsmanager.bean.CableBean;
import com.cabletech.fsmanager.dao.CableDao;
import com.cabletech.fsmanager.dao.ExportDao;
import com.cabletech.watchinfo.templates.WatchDetailTemplate;


public class CableAction extends BaseInfoBaseDispatchAction{

    private static Logger logger =
        Logger.getLogger( CableAction.class.getName() );

	public ActionForward queryCableInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		String regionid = userinfo.getRegionID();
		CableDao dao = new CableDao();
		List contractorlist = dao.getContractor(regionid);
		List pointList = dao.getPointInfo(regionid);
		request.setAttribute("contractorlist", contractorlist);
		request.setAttribute("pointList", pointList);
		return mapping.findForward("queryCableInfo");
	}
    
	public ActionForward addCableInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		String regionid = userinfo.getRegionID();
		CableDao dao = new CableDao();
		List contractorlist = dao.getContractor(regionid);
		List pointList = dao.getPointInfo(regionid);
		request.setAttribute("contractorlist", contractorlist);
		request.setAttribute("pointList", pointList);
		return mapping.findForward("addCableInfo");
	}

	public ActionForward addCable(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CableBean bean = (CableBean) form;
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		bean.setRegionId(userinfo.getRegionID());
		CableDao dao = new CableDao();
		
		bean.setId(this.getDbService().getSeq("cableinfo", 20));
		boolean isSuccess = dao.addCable(bean);
		if(isSuccess){
			return forwardInfoPage( mapping, request, "73001" );
		}else{
			return forwardErrorPage(mapping, request, "error");
		}
	}

	public ActionForward queryCable(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CableBean bean = (CableBean) form;
		request.getSession().setAttribute("S_BACK_URL",null); //
        request.getSession().setAttribute("theQueryBean", bean);//
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		if(request.getParameter("page")==null){
			response.sendRedirect((String)request.getSession().getAttribute("S_BACK_URL"));
			return null;
		}
		request.getSession().setAttribute("querybean", bean);
		CableDao dao = new CableDao();
		List cableList = dao.getCableBean(bean);
		request.getSession().setAttribute("cableList", cableList);
		this.setPageReset(request);
		return mapping.findForward("cableResult");
	}

	public ActionForward deleteCable(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		CableDao dao = new CableDao();
		boolean isSuccess = dao.deleteCableById(id);
		if(isSuccess){
			String strQueryString = getTotalQueryString("method=queryCable&cablename=",(CableBean)request.getSession().getAttribute("theQueryBean"));
			 Object[] args = getURLArgs("/WebApp/CableAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
			 return forwardInfoPage( mapping, request, "73003",null,args);
		}else{
			return forwardErrorPage(mapping, request, "error");
		}
	}
	
	public ActionForward loadCable(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CableBean bean = (CableBean) form; 
		String id = request.getParameter("id");
		String type = request.getParameter("t");
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		String regionid = userinfo.getRegionID();
		CableDao dao = new CableDao();
		List contractorlist = dao.getContractor(regionid);
		List pointList = dao.getPointInfo(regionid);
		request.setAttribute("contractorlist", contractorlist);
		request.setAttribute("pointList", pointList);
		 request.setAttribute("TYPE", type);
		bean = dao.getCableById(id,bean);
		//request.setAttribute("bean", bean);
		return mapping.findForward("loadCable");
	}

	public ActionForward updateCable(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CableBean bean = (CableBean) form; 
		CableDao dao = new CableDao();
		boolean isSuccess = dao.updateCableInfo(bean);
		if(isSuccess){
			String strQueryString = getTotalQueryString("method=queryCable&cablename=",(CableBean)request.getSession().getAttribute("theQueryBean"));
			 Object[] args = getURLArgs("/WebApp/CableAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
			 return forwardInfoPage( mapping, request, "73002",null,args);
		}else{
			return forwardErrorPage(mapping, request, "error");
		}
	}
		
	
	public String getTotalQueryString(String queryString,CableBean bean){
    	if (bean.getCablename() != null){
    		queryString = queryString + bean.getCablename();
    	}
    	if (bean.getCablelinename()!= null){
    		queryString = queryString + "&cablelinename=" + bean.getCablelinename();
    	}
    	if (bean.getId() != null){
    		queryString = queryString + "&id=" + bean.getId();
    	}
    	if (bean.getCableno()!= null){
    		queryString = queryString + "&cableno=" + bean.getCableno();
    	}
    	if (bean.getApoint()!= null){
    		queryString = queryString + "&aponit=" + bean.getApoint();
    	}
    	if (bean.getZpoint()!= null){
    		queryString = queryString + "&zponit=" + bean.getZpoint();
    	}
    	return queryString;
    }

	public ActionForward exportCableResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CableBean bean = (CableBean) request.getSession().getAttribute("querybean"); 
		try{
            logger.info( " 创建dao" );
            CableDao dao = new CableDao();
            List list = dao.getExportInfo(bean);;
            logger.info( "得到list" );
            ExportDao exdao = new ExportDao();
            if( exdao.exportInfo( list, response ) ){
                logger.info( "输出excel成功" );
            }
            return null;
        }
        catch( Exception e ){
            logger.error( "导出光缆段信息一览表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
	}
	
	public ActionForward downloadTemplet(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ) {
    	
    	response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String("光缆段信息导入模板.xls".getBytes(), "iso-8859-1"));
			OutputStream out = response.getOutputStream();
			
			WatchDetailTemplate template = new WatchDetailTemplate(servlet
					.getServletContext().getRealPath("/upload")
					+ "/fscablelist.xls");
			template.write(out);
	    	
		} catch (Exception e) {
			logger.warn("下载集团客户导入模板出错：" + e.getMessage());
			e.printStackTrace();
		}
		return null;
		
    }
	
	 public ActionForward upLoadShow( ActionMapping mapping, ActionForm form,
		        HttpServletRequest request,
		        HttpServletResponse response ){
			
		        CableDao dao = new CableDao();
		    	CableBean bean = ( CableBean )form;
		    	// 判断导入文件类型
		        String filename = bean.getFile().getFileName();
		        if( filename.equals( "" ) || filename == null ){
		            return forwardErrorPage( mapping, request, "fileerror" );
		        }
		        if( !filename.substring( filename.length() - 3, filename.length() ).equals( "xls" ) ){
		            return forwardErrorPage( mapping, request, "structerror" );
		        }

		        // 取得导入临时文件的存入路径
		        String path = servlet.getServletContext().getRealPath( "/upload" );
		       
		        // 保存客户资料
		        if(!dao.saveExcelGroupcustomerInfo(bean, path)){
		            return forwardErrorPage( mapping, request, "error" );
		        }
		        // 成功转向增加页面        
		        return forwardInfoPage( mapping, request, "73001up" );
		    }
	
}
