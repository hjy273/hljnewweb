package com.cabletech.planinfo.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cabletech.commons.web.ClientException;
import com.cabletech.planinfo.beans.PlanBean;
import com.cabletech.planinfo.beans.StencilSubTaskBean;
import com.cabletech.planinfo.beans.StencilTaskBean;
import com.cabletech.planinfo.beans.TaskBean;
import com.cabletech.planinfo.domainobjects.PlanTaskSubline;
import com.cabletech.planinfo.domainobjects.StencilSubTaskOper;
import com.cabletech.planinfo.domainobjects.StencilTaskPoint;
import com.cabletech.planinfo.services.StencilTaskBO;
import com.cabletech.planinfo.services.TaskBO;

public class StencilTaskAction extends PlanInfoBaseDispatchAction {
	private Logger logger = Logger.getLogger("StencilTaskAction");
	/**
	 * 添加子任务
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward addStencilTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		StencilSubTaskBean stencilTask = (StencilSubTaskBean) form;
		HttpSession session = request.getSession();
		//session.removeAttribute("taskinfo");
		String stepStatus = request.getParameter("ss");// step
		String str_subline = (String) session.getAttribute("strsubline");//线段id用于判断线段是否在多个子任务中
		String estate = (String) session.getAttribute("EditS");// edit status
		//String edittask = (String)session.getAttribute("edittask");
		List StencilTaskList = new ArrayList();
		if (session.getAttribute("StencilTaskList") != null) {
			StencilTaskList = (List) session.getAttribute("StencilTaskList");
		}
		
		// 处理操作信息
		String[] operAr = request.getParameterValues("operationcheck");
		for (int i = 0; i < operAr.length; i++) {
			StencilSubTaskOper TOList = new StencilSubTaskOper();
			TOList.setToid(operAr[i]);
			stencilTask.add(TOList);
		}
		String[] pointAr = request.getParameterValues("subtaskpoint");
		for (int i = 0; i < pointAr.length; i++) {
			StencilTaskPoint taskpoint = new StencilTaskPoint();
			taskpoint.setPointid(pointAr[i]);
			stencilTask.add(taskpoint);
		}
		
       
		// 任务信息保存到session中.
		stencilTask.setTaskpoint(pointAr.length+"");//任务点数
		StencilTaskList.add(stencilTask);
		session.setAttribute("StencilTaskList", StencilTaskList);
		//处理线段
        String[] sublineAr = request.getParameterValues("typeCheck");
        StringBuffer strsubline = new StringBuffer(str_subline);
        for(int i=0;i<sublineAr.length;i++){
        	strsubline.append(sublineAr[i]);
        	strsubline.append(",");
        }
        session.setAttribute("strsubline", strsubline.toString());
        logger.info("subline:"+strsubline.toString());
//      取得线段mingcheng,按选中 的线段获取线段名称
        Map sublineMap = (Map) session.getAttribute("sublinelist");
        String sl = "";//sl 里面保存段的名称
        
        for(int j=0;j<sublineAr.length;j++){
	       	if( (strsubline.toString().indexOf(sublineAr[j])!= -1)){
	        	sl += sublineMap.get(sublineAr[j]).toString()+"、";
	        }
        }
        stencilTask.setSubline(sl.substring(0,sl.length()-1));
		//获取巡检点信息
		StencilTaskBean stencil = (StencilTaskBean)session.getAttribute("Stencil");
//		Vector subtaskVct = new Vector();
//        subtaskVct = super.getService().getPUnitListByPatrolid( stencil.getPatrolid(),
//                     "2", "1" );
//        request.setAttribute("pointlist", subtaskVct);
//        session.setAttribute("sublinelist", subtaskVct);
		
        //session.removeAttribute("Index");
        session.setAttribute("edittask", "add");
        String _strFor = "addstencilTask";
		if (stepStatus.equals("1")) {// 0代表继续下一步 1代表完成
			if (estate.equals("edit")) {
				_strFor = "editStencilResult";
			} else
				_strFor = "addStencilResult";

		}else{
			TaskBO taskbo = new TaskBO();
			String taskHtml = taskbo.getPUnitListByPatrolid(stencil.getPatrolid(), "1","");
			request.setAttribute("tasksublineHtm", taskHtml);
			session.setAttribute("sublinelist", taskbo.getPSublineByPatrol(stencil.getPatrolid(), "1"));
			_strFor = "addstencilTask";
		}
		return mapping.findForward(_strFor);
	}
	/**
	 * 载入任务模板
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
    public ActionForward loadStencilTask( ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ) throws
            ClientException, Exception{
    	HttpSession session = request.getSession();
		try{
			int index = Integer.parseInt(request.getParameter("id"));
			//StencilTaskList 为子任务信息 List
			List StencilTaskList = (List)session.getAttribute("StencilTaskList");
			
			StencilSubTaskBean stenciltask = (StencilSubTaskBean) StencilTaskList.get(index);
			//session.setAttribute("Index", new Integer(index));
			String level = stenciltask.getLinelevel();
			StencilTaskBean stencil = (StencilTaskBean)session.getAttribute("Stencil");
			StencilTaskBO stenciltaskbo = new StencilTaskBO();
			//验证线段是否重复
			List sublineList = stenciltaskbo.getSubLineId(stencil.getPatrolid(), level);
			String str_subline = (String) session.getAttribute("strsubline");//线段id用于判断线段是否在多个子任务中
			logger.info("before delete "+str_subline);
			StringBuffer str = new StringBuffer(str_subline);
			// to  do delete "strsubline" 中的多于
			for(int i=0;i<sublineList.size();i++){		
				String strTemp = sublineList.get(i).toString();
				int start_index=str.indexOf(strTemp);
				//logger.info("strTemp"+strTemp);
				//logger.info("start index"+start_index);
				//logger.info("BB"+str.toString());
				if(start_index!=-1){
					str = str.delete(start_index, start_index+strTemp.length()+1);
				}
				//logger.info("AA"+str.toString());
				
			}	
			str_subline = str.toString();
			logger.info("after delete "+str_subline);
			session.setAttribute("strsubline",str_subline);
			List delTaskList = (List)session.getAttribute("delStencilTaskList");
			delTaskList.add(stenciltask.getID());
			//从StencilTaskList中删除指定编号的子任务;
			StencilTaskList.remove(index);
			session.setAttribute("StencilTaskList", StencilTaskList);
			session.setAttribute("edittask","edit");
			StencilTaskBO s_bo = new StencilTaskBO();
	        String taskpoint = s_bo.getStrTaskPoint(stenciltask.getStenciltaskpoint());
			TaskBO taskbo = new TaskBO();
			String taskHtml = taskbo.getPUnitListByPatrolid(stencil.getPatrolid(), stenciltask.getLinelevel(),taskpoint);
			request.setAttribute("tasksublineHtm", taskHtml);
			session.setAttribute("sublinelist", taskbo.getPSublineByPatrol(stencil.getPatrolid(), stenciltask.getLinelevel()));
			
	        //session.setAttribute("taskinfo", stenciltask);
	        request.setAttribute("taskpoint", taskpoint);
	        session.setAttribute("delStencilTaskList", delTaskList);
			request.setAttribute("taskinfo", stenciltask);
			return mapping.findForward( "editstencilTask");
		}catch(NumberFormatException numE){
			numE.printStackTrace();
			logger.warn("类型转换错误:"+numE.getMessage());
			return null;
		}
    	
    }
    /**
     * 删除子任务
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward removeStencilTask(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ) throws
            ClientException, Exception{
    	HttpSession session = request.getSession();
		String estate = (String) session.getAttribute("EditS");//是否为编辑状态.
		try{
			int index = Integer.parseInt(request.getParameter("id"));
			List StencilTaskList = (List)session.getAttribute("StencilTaskList");
			//从strsubline中删除指定任务中的线段Id
			StencilTaskBean stencil = (StencilTaskBean)session.getAttribute("Stencil");
			StencilSubTaskBean stenciltask = (StencilSubTaskBean) StencilTaskList.get(index);
	        StencilTaskBO stenciltaskbo = new StencilTaskBO();
	        String str_subline = (String) session.getAttribute("strsubline");//线段id用于判断线段是否在多个子任务中
	        String level = stenciltask.getLinelevel();
	        //这里存在问题：同一级别的线段存在于不同的子任务中时，删除其中一个子任务同一级别的线段id都从str_subline中被删除。
	        str_subline = stenciltaskbo.interceptStr(stencil.getPatrolid(), level, stenciltask.getId(), str_subline);
			session.setAttribute("strsubline",str_subline);
			List delTaskList = (List)session.getAttribute("delStencilTaskList");
			delTaskList.add(stenciltask.getID());
			StencilTaskList.remove(index);
			session.setAttribute("StencilTaskList", StencilTaskList);
    		if(estate.equals("edit")){
    			return mapping.findForward("editStencilResult");
            }else
            	return mapping.findForward("addStencilResult");	
		}catch(NumberFormatException numE){
			numE.printStackTrace();
			logger.warn("类型转换错误:"+numE.getMessage());
			return null;
		}
    }
    public ActionForward addTask(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ) throws
            ClientException, Exception{
    	HttpSession session = request.getSession();
    	String patrolid =((StencilTaskBean)session.getAttribute("Stencil")).getPatrolid(); //巡检人员
        String linelevel = request.getParameter("linelevel");
        if(linelevel == null || linelevel.equals("")){
        	linelevel = "1";
        }
        
        TaskBO taskbo = new TaskBO();
		String taskHtml = taskbo.getPUnitListByPatrolid(patrolid, linelevel,"");
		request.setAttribute("tasksublineHtm", taskHtml);
		session.setAttribute("sublinelist", taskbo.getPSublineByPatrol(patrolid, linelevel));
		
        String edittask = (String) session.getAttribute("edittask");//任务是否为编辑状态.
        if(edittask != null && edittask.equals("edit")){
        	return mapping.findForward( "editstencilTask" );
        }else{
        	return mapping.findForward( "addstencilTask" );
        }
        
    }
    /**
     * 获取模板任务点
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward getTaskPatrolPoint(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ) throws
            ClientException, Exception{
    	HttpSession session = request.getSession();
    	String patrolid =((StencilTaskBean)session.getAttribute("Stencil")).getPatrolid(); //巡检人员
        String linelevel = request.getParameter("linelevel");
        if(linelevel == null || linelevel.equals("")){
        	linelevel = "1";
        }
        logger.info("级别:"+linelevel);
        TaskBO taskbo = new TaskBO();
		String taskHtml = taskbo.getPUnitListByPatrolid(patrolid, linelevel,"");
		request.setAttribute("tasksublineHtm", taskHtml);
		session.setAttribute("sublinelist", taskbo.getPSublineByPatrol(patrolid, linelevel));
		response.setContentType("text/html; charset=GBK");
		PrintWriter out = response.getWriter();
		out.println(taskHtml);
		out.flush();
		return null;
        
    }
}
