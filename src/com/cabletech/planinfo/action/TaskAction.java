package com.cabletech.planinfo.action;

import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.*;

import org.apache.log4j.Logger;
import org.apache.struts.action.*;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.generatorID.*;
import com.cabletech.commons.web.*;
import com.cabletech.planinfo.beans.*;
import com.cabletech.planinfo.domainobjects.*;
import com.cabletech.planinfo.services.StencilTaskBO;
import com.cabletech.planinfo.services.TaskBO;

public class TaskAction extends PlanInfoBaseDispatchAction{

    private Logger logger = Logger.getLogger("TaskAction");


	/**
     * 新增 年/月 计划用子任务
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward addYearMonthTask( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
    	String _ss = request.getParameter("ss");
    	HttpSession session = request.getSession();
    	String estate = (String) session.getAttribute("EditS");
    	String plantype = (String)session.getAttribute("plantype");
    	List taskList ;
    	if(session.getAttribute("taskList")== null){
    		taskList = new ArrayList();
    	}else{
    		taskList = (List)session.getAttribute("taskList");
    	}
        TaskBean bean = ( TaskBean )form;
        //添加任务信息
       
        bean.setRegionid( super.getLoginUserInfo( request ).getRegionid() );
        
        //处理操作信息
        //List taskOperList = new ArrayList();
    	
        String[] operAr = request.getParameterValues( "operationcheck" );
        for( int i = 0; i < operAr.length; i++ ){
            
            TaskOperationList TOList = new TaskOperationList();
            TOList.setId( super.getDbService().getSeq( "taskoperationlist", 20 ) );
            TOList.setOperationid( operAr[i] );
            //TOList.setTaskid( taskid );
            bean.getTaskOpList().add(TOList);
            //taskOperList.add(TOList);
        }

        //bean.setTaskOpList(taskOperList);
        //任务信息保存到session中.
        taskList.add(bean);
        session.setAttribute("taskList", taskList);
        //
        String _strFor = "addYearMonthTask";
        if(_ss.equals("1")){ //0代表继续下一步 1代表完成
        	if("1".equals(plantype)){
	        	if(estate.equals("edit")){
	        		_strFor = "editYearResult";
	        	}else
	        		_strFor = "addYearResult";
        	}else{
        		if(estate.equals("edit")){
	        		_strFor = "editMonthResult";
	        	}else
	        		_strFor = "addMonthResult";
        	}
        }
        return mapping.findForward( _strFor);
    }
    /**
     * 载入修改子任务信息.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward loadTaskInfo(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ) throws
            ClientException, Exception{
    		HttpSession session = request.getSession();
    		try{
    			int index = Integer.parseInt(request.getParameter("id"));
    		
    			List taskList = (List)session.getAttribute("taskList");
    			TaskBean taskinfo = (TaskBean) taskList.get(index);
    			taskList.remove(index);
    			request.setAttribute("taskinfo", taskinfo);
    			
    			return mapping.findForward( "editYearMonthTask");
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
    public ActionForward removeTaskInfo(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ) throws
            ClientException, Exception{
    		HttpSession session = request.getSession();
    		String estate = (String) session.getAttribute("EditS");
    		String plantype = (String)session.getAttribute("plantype");
    		try{
    			int index = Integer.parseInt(request.getParameter("id"));
    		
    			List taskList = (List)session.getAttribute("taskList");
    			taskList.remove(index);
    			session.setAttribute("taskList", taskList);
    			if("1".equals(plantype)){ //1:年计划 2月计划
	    			if(estate.equals("edit")){
	    				return mapping.findForward("editYearResult");
	            	}else
	            		return mapping.findForward("addYearResult");
    			}else{
    				if(estate.equals("edit")){
	    				return mapping.findForward("editMonthResult");
	            	}else
	            		return mapping.findForward("addMonthResult");
    			}
    				
    		}catch(NumberFormatException numE){
    			numE.printStackTrace();
    			logger.warn("类型转换错误:"+numE.getMessage());
    			return null;
    		}
    	
    }
    //--------------------计划制定---------//
    /**
     * 添加计划子任务
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward addPlanTask( ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ) throws
            ClientException, Exception{
    	long startTime = System.currentTimeMillis();
    	TaskBean _taskb = (TaskBean)form;
    	HttpSession session = request.getSession();
    	String stepStatus = request.getParameter("ss");//step
    	String estate = (String) session.getAttribute("EditS");//edit status
    	String str_subline = (String) session.getAttribute("strsubline");//线段id用于判断线段是否在多个子任务中
    	PlanBean plan = (PlanBean)session.getAttribute("Plan");
    	//String plantype = (String)session.getAttribute("plantype");
    	List taskList = new ArrayList();
    	if(session.getAttribute("taskList") != null){
    		taskList = (List)session.getAttribute("taskList");
    	}  
    
        //添加任务信息
    	_taskb.setRegionid( super.getLoginUserInfo( request ).getRegionid() );
    	//处理线段
        String[] sublineAr = request.getParameterValues("typeCheck");
        //logger.info("添加前strsubline中 "+str_subline);
        StringBuffer strsubline = new StringBuffer(str_subline);
        
        for(int i=0;i<sublineAr.length;i++){     	
        	strsubline.append(sublineAr[i]);
        	strsubline.append(",");
        	//plantasksubline
        	PlanTaskSubline tasksubline = new PlanTaskSubline();
        	tasksubline.setSublineid(sublineAr[i]);	
        	_taskb.Add(tasksubline);
        }
        //logger.info("添加子任务线段id "+ strsubline.toString());
        session.setAttribute("strsubline", strsubline.toString());
    	//取得线段mingcheng,按选中 的线段获取线段名称
        Map sublineMap = (Map) session.getAttribute("sublinelist");
        String sl = "";//sl 里面保存段的名称
        
        for(int j=0;j<sublineAr.length;j++){
        	//System.out.println("sublinemap： "+sublineMap+" "+sublineAr[j]);
	       	if( (strsubline.toString().indexOf(sublineAr[j])!= -1)){
	       		//System.out.println("线段："+sublineMap.get(sublineAr[j]));
	        	sl += sublineMap.get(sublineAr[j]).toString()+"、";
	        }
        }
        
        //获得所有线段
//        int linesize = Integer.parseInt(request.getParameter("size"));
//        for(int j=0;j<linesize;j++){
//        	String parameterName = "typeCheck"+j;
//        	sl += request.getParameter(parameterName)+"、";
//        }     
        //System.out.println(sl.toString());
        if(sl.length()!=0){
        	_taskb.setSubline(sl.substring(0,sl.length()-1));
        }
      // logger.info("任务线段 ：");
        for(int i=0;i<_taskb.getTaskSubline().size();i++){
        	PlanTaskSubline sb = (PlanTaskSubline)_taskb.getTaskSubline().get(i);
        	//logger.info(sb.getSublineid());
        }
        //处理操作信息
        List taskOperList = new ArrayList();
        String[] operAr = request.getParameterValues( "operationcheck" );
        for( int i = 0; i < operAr.length; i++ ){     
            TaskOperationList TOList = new TaskOperationList();            
            TOList.setOperationid( operAr[i] );
            _taskb.Add(TOList);
        }
        //处理任务点
        CustomID idFactory = new CustomID();
        String[] pointAr = request.getParameterValues( "subtaskpoint" );
        //String[] idArr = idFactory.getStrSeqs( pointAr.length, "subtaskinfo", 20 ); 
        for( int i = 0; i < pointAr.length; i++ ){
            SubTask subtask = new SubTask();
            subtask.setObjectid( pointAr[i] );
            _taskb.Add(subtask);
        }
        
        //任务信息保存到session中.
        _taskb.setTaskpoint(_taskb.getTaskPoint().size()+"");
        taskList.add(_taskb);
      
        session.setAttribute("taskList", taskList);
        session.setAttribute("edittask", "add");
        String _strFor = "addPlanTask";
        //Vector subtaskVct = new Vector();
//        subtaskVct = super.getService().getPUnitListByPatrolid( plan.getExecutorid(),
//                     "2", "1" );
//        request.setAttribute( "pointlist", subtaskVct );
       
//        TaskBO taskbo = new TaskBO();
//		String taskHtml = taskbo.getPUnitListByPatrolid(plan.getExecutorid(), _taskb.getLinelevel(),"");
//		request.setAttribute("tasksublineHtm", taskHtml);
//		session.setAttribute("sublinelist", taskbo.getPSublineByPatrol(plan.getExecutorid(), _taskb.getLinelevel()));
//		 
        session.setAttribute("st", "y");//是否修改过。
        long endTime = System.currentTimeMillis();
		long total=(endTime - startTime);
		logger.info("添加task用时："+total+"毫秒") ;
        logger.info("StepStatus "+stepStatus);
        if(stepStatus.equals("1")){//0代表继续下一步 1代表完成
	        if(estate.equals("edit")){
	        	_strFor = "editPlanResult";
	        }else
	        	_strFor = "addPlanResult";
        }else{
        	TaskBO taskbo = new TaskBO();
    		String taskHtml = taskbo.getPUnitListByPatrolid(plan.getExecutorid(), _taskb.getLinelevel(),"");
    		request.setAttribute("tasksublineHtm", taskHtml);
    		session.setAttribute("sublinelist", taskbo.getPSublineByPatrol(plan.getExecutorid(), _taskb.getLinelevel()));
    		_strFor =  "addPlanTask";
        }
        return mapping.findForward( _strFor);
    }
    /**
     * 载入计划修改子任务Form
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward loadPlanTask( ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ) throws
            ClientException, Exception{
    	HttpSession session = request.getSession();
		try{
			int index = Integer.parseInt(request.getParameter("id"));//取出该任务在List的id
			PlanBean plan = (PlanBean)session.getAttribute("Plan"); 
			List taskList = (List)session.getAttribute("taskList");//
			TaskBean taskinfo = (TaskBean) taskList.get(index);
			List delTaskList = (List)session.getAttribute("delTaskList");
			if(delTaskList!=null){
			delTaskList.add(taskinfo.getId());
			}
			if(taskList!=null){
			taskList.remove(index); //在taskList中删除指定编号的task
			}
			 TaskBO taskbo = new TaskBO();
			//一选取任务点
		    //TaskBO taskbo = new TaskBO();
			String taskpoint = taskbo.getStrTaskPoint(taskinfo.getTaskPoint());
	        request.setAttribute("taskpoint", taskpoint);
			request.setAttribute("taskinfo", taskinfo);
			//取得任务点
			//Vector subtaskVct = new Vector();
//		        subtaskVct = super.getService().getPUnitListByPatrolid( plan.getExecutorid(),
//		                     "2",taskinfo.getLinelevel() );
//		    request.setAttribute( "pointlist", subtaskVct );
//			
	    
		   
			String taskHtml = taskbo.getPUnitListByPatrolid(plan.getExecutorid(), taskinfo.getLinelevel(),taskpoint);
			request.setAttribute("tasksublineHtm", taskHtml);
			session.setAttribute("sublinelist", taskbo.getPSublineByPatrol(plan.getExecutorid(), taskinfo.getLinelevel()));
		    
	        //验证线段是否重复
	        StencilTaskBO stenciltaskbo = new StencilTaskBO();
	        String str_subline = (String) session.getAttribute("strsubline");//线段id用于判断线段是否在多个子任务中
	        String level = taskinfo.getLinelevel();
	        str_subline = stenciltaskbo.interceptStr(plan.getExecutorid(), level, taskinfo.getId(), str_subline);
			logger.info("加载子任务后 "+ str_subline);
			
	        session.setAttribute("strsubline",str_subline);
			session.setAttribute("delTaskList", delTaskList);
	        session.setAttribute("edittask","edit");
			return mapping.findForward( "editPlanTask");
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
    public ActionForward removePlanTask(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ) throws
            ClientException, Exception{
    	HttpSession session = request.getSession();
		String estate = (String) session.getAttribute("EditS");
		try{
			int index = Integer.parseInt(request.getParameter("id"));
		
			List taskList = (List)session.getAttribute("taskList");
			//验证线段是否重复
			PlanBean plan = (PlanBean)session.getAttribute("Plan"); 
			TaskBean taskinfo = (TaskBean) taskList.get(index);
	        StencilTaskBO stenciltaskbo = new StencilTaskBO();
	        String str_subline = (String) session.getAttribute("strsubline");//线段id用于判断线段是否在多个子任务中
	        String level = taskinfo.getLinelevel();
	        str_subline = stenciltaskbo.interceptStr(plan.getExecutorid(), level, taskinfo.getId(), str_subline);
			session.setAttribute("strsubline",str_subline);
			//从list中删除任务
			List delTaskList = (List)session.getAttribute("delTaskList");
			delTaskList.add(taskinfo.getId());
			taskList.remove(index);
			session.setAttribute("taskList", taskList);
			session.setAttribute("st", "y");//是否修改过。
    		if(estate.equals("edit")){
    			return mapping.findForward("editPlanResult");
            }else
            	return mapping.findForward("addPlanResult");	
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
    	long startTime = System.currentTimeMillis();
    	String patrolid =((PlanBean)session.getAttribute("Plan")).getExecutorid(); //巡检人员
        String linelevel = request.getParameter("linelevel");
        if(linelevel == null || linelevel.equals("")){
        	linelevel = "1";
        }
        
        TaskBO taskbo = new TaskBO();
		String taskHtml = taskbo.getPUnitListByPatrolid(patrolid, linelevel,"");
		request.setAttribute("tasksublineHtm", taskHtml);
		session.setAttribute("sublinelist", taskbo.getPSublineByPatrol(patrolid, linelevel));
		
        String edittask = (String) session.getAttribute("edittask");//任务是否为编辑状态.
        long endTime = System.currentTimeMillis();
		long total=(endTime - startTime);
		logger.info("添加task用时："+total+"毫秒") ;
        if("edit".equals(edittask)){
        	return mapping.findForward( "editPlanTask" );
        }else{
        	return mapping.findForward( "addPlanTask" );
        }
		
    }
    /**
     * 获取任务巡检点 注:在使用该方法时session必须有Plan对象存在
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
    	String patrolid =((PlanBean)session.getAttribute("Plan")).getExecutorid(); //巡检人员
        String linelevel = request.getParameter("linelevel");
        if(linelevel == null || linelevel.equals("")){
        	linelevel = "1";
        }
        
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
    //测试 获得线段
    public ActionForward getTaskSublines(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ) throws
            ClientException, Exception{
    	HttpSession session = request.getSession();
    	String patrolid =((PlanBean)session.getAttribute("Plan")).getExecutorid(); //巡检人员
        String linelevel = request.getParameter("linelevel");
        if(linelevel == null || linelevel.equals("")){
        	linelevel = "1";
        }
        
        TaskBO taskbo = new TaskBO();
		String taskHtml = taskbo.getSublineByPatrolid(patrolid, linelevel,"");
		request.setAttribute("tasksublineHtm", taskHtml);
		session.setAttribute("sublinelist", taskbo.getPSublineByPatrol(patrolid, linelevel));
		
		response.setContentType("text/html; charset=GBK");
		PrintWriter out = response.getWriter();
		out.println(taskHtml);
		out.flush();
    	return null;
    }
    
    //测试 获得线段上的点
    public ActionForward getTaskPoints(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ) throws
            ClientException, Exception{
    	String sublineid = request.getParameter("sublineid");
    	TaskBO taskbo = new TaskBO();
    	String taskHtm = taskbo.getPointsBySublineid(sublineid);
    	response.setContentType("text/html; charset=GBK");
		PrintWriter out = response.getWriter();
		out.println(taskHtm);
		out.flush();
    	return null;
    }
    
    
    /**
     * 加载巡检计划任务模板
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward toLoadTaskStencil(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ) throws
            ClientException, Exception{
    	HttpSession session = request.getSession();
    	//计划执行人
    	String patrolid =((PlanBean)session.getAttribute("Plan")).getExecutorid(); //巡检人员
    	//获取模板id
    	String stencilid = request.getParameter("stencil");
    	//通过id加载模板 并返回subtask对象
    	TaskBO tbo = new TaskBO();
    	List taskList = tbo.toLoad(stencilid);
    	//通过request返回到页面
    	session.setAttribute("taskList", taskList);
    	return mapping.findForward( "addPlanResult");
    }



    /**
     * 取得 年 / 月 任务属性
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ClientException
     * @throws Exception
     * @return ActionForward
     */
    public ActionForward loadTask( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        String forwardPage = "YMTaskProperty";
        String FID = "";
        if( request.getParameter( "FID" ) != null ){
            FID = request.getParameter( "FID" ); //1, 普通 2,审批用
        }
        if( FID.equals( "2" ) ){
            forwardPage = "YMTaskProperty4Approve";
        }

        String lineNum = request.getParameter( "lineNum" );
        Task data = super.getService().loadTask( request.getParameter( "id" ) );
        Vector OpListVct = super.getService().getTaskOperListByTaskid( data.
                           getId() );

        TaskBean bean = new TaskBean();
        BeanUtil.objectCopy( data, bean );
        request.setAttribute( "taskBean", bean );
        //request.setAttribute("pointsList", super.getService().getPointsSubtask(data.getId()));
        request.setAttribute( "oplist", OpListVct );
        request.setAttribute( "lineNum", lineNum );

        return mapping.findForward( forwardPage );
    }
    
}
