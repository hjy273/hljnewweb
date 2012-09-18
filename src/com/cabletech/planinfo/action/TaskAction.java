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
     * ���� ��/�� �ƻ���������
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
        //���������Ϣ
       
        bean.setRegionid( super.getLoginUserInfo( request ).getRegionid() );
        
        //���������Ϣ
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
        //������Ϣ���浽session��.
        taskList.add(bean);
        session.setAttribute("taskList", taskList);
        //
        String _strFor = "addYearMonthTask";
        if(_ss.equals("1")){ //0���������һ�� 1�������
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
     * �����޸���������Ϣ.
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
    			logger.warn("����ת������:"+numE.getMessage());
    			return null;
    		}
    		
    }
    /**
     * ɾ��������
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
    			if("1".equals(plantype)){ //1:��ƻ� 2�¼ƻ�
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
    			logger.warn("����ת������:"+numE.getMessage());
    			return null;
    		}
    	
    }
    //--------------------�ƻ��ƶ�---------//
    /**
     * ��Ӽƻ�������
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
    	String str_subline = (String) session.getAttribute("strsubline");//�߶�id�����ж��߶��Ƿ��ڶ����������
    	PlanBean plan = (PlanBean)session.getAttribute("Plan");
    	//String plantype = (String)session.getAttribute("plantype");
    	List taskList = new ArrayList();
    	if(session.getAttribute("taskList") != null){
    		taskList = (List)session.getAttribute("taskList");
    	}  
    
        //���������Ϣ
    	_taskb.setRegionid( super.getLoginUserInfo( request ).getRegionid() );
    	//�����߶�
        String[] sublineAr = request.getParameterValues("typeCheck");
        //logger.info("���ǰstrsubline�� "+str_subline);
        StringBuffer strsubline = new StringBuffer(str_subline);
        
        for(int i=0;i<sublineAr.length;i++){     	
        	strsubline.append(sublineAr[i]);
        	strsubline.append(",");
        	//plantasksubline
        	PlanTaskSubline tasksubline = new PlanTaskSubline();
        	tasksubline.setSublineid(sublineAr[i]);	
        	_taskb.Add(tasksubline);
        }
        //logger.info("����������߶�id "+ strsubline.toString());
        session.setAttribute("strsubline", strsubline.toString());
    	//ȡ���߶�mingcheng,��ѡ�� ���߶λ�ȡ�߶�����
        Map sublineMap = (Map) session.getAttribute("sublinelist");
        String sl = "";//sl ���汣��ε�����
        
        for(int j=0;j<sublineAr.length;j++){
        	//System.out.println("sublinemap�� "+sublineMap+" "+sublineAr[j]);
	       	if( (strsubline.toString().indexOf(sublineAr[j])!= -1)){
	       		//System.out.println("�߶Σ�"+sublineMap.get(sublineAr[j]));
	        	sl += sublineMap.get(sublineAr[j]).toString()+"��";
	        }
        }
        
        //��������߶�
//        int linesize = Integer.parseInt(request.getParameter("size"));
//        for(int j=0;j<linesize;j++){
//        	String parameterName = "typeCheck"+j;
//        	sl += request.getParameter(parameterName)+"��";
//        }     
        //System.out.println(sl.toString());
        if(sl.length()!=0){
        	_taskb.setSubline(sl.substring(0,sl.length()-1));
        }
      // logger.info("�����߶� ��");
        for(int i=0;i<_taskb.getTaskSubline().size();i++){
        	PlanTaskSubline sb = (PlanTaskSubline)_taskb.getTaskSubline().get(i);
        	//logger.info(sb.getSublineid());
        }
        //���������Ϣ
        List taskOperList = new ArrayList();
        String[] operAr = request.getParameterValues( "operationcheck" );
        for( int i = 0; i < operAr.length; i++ ){     
            TaskOperationList TOList = new TaskOperationList();            
            TOList.setOperationid( operAr[i] );
            _taskb.Add(TOList);
        }
        //���������
        CustomID idFactory = new CustomID();
        String[] pointAr = request.getParameterValues( "subtaskpoint" );
        //String[] idArr = idFactory.getStrSeqs( pointAr.length, "subtaskinfo", 20 ); 
        for( int i = 0; i < pointAr.length; i++ ){
            SubTask subtask = new SubTask();
            subtask.setObjectid( pointAr[i] );
            _taskb.Add(subtask);
        }
        
        //������Ϣ���浽session��.
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
        session.setAttribute("st", "y");//�Ƿ��޸Ĺ���
        long endTime = System.currentTimeMillis();
		long total=(endTime - startTime);
		logger.info("���task��ʱ��"+total+"����") ;
        logger.info("StepStatus "+stepStatus);
        if(stepStatus.equals("1")){//0���������һ�� 1�������
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
     * ����ƻ��޸�������Form
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
			int index = Integer.parseInt(request.getParameter("id"));//ȡ����������List��id
			PlanBean plan = (PlanBean)session.getAttribute("Plan"); 
			List taskList = (List)session.getAttribute("taskList");//
			TaskBean taskinfo = (TaskBean) taskList.get(index);
			List delTaskList = (List)session.getAttribute("delTaskList");
			if(delTaskList!=null){
			delTaskList.add(taskinfo.getId());
			}
			if(taskList!=null){
			taskList.remove(index); //��taskList��ɾ��ָ����ŵ�task
			}
			 TaskBO taskbo = new TaskBO();
			//һѡȡ�����
		    //TaskBO taskbo = new TaskBO();
			String taskpoint = taskbo.getStrTaskPoint(taskinfo.getTaskPoint());
	        request.setAttribute("taskpoint", taskpoint);
			request.setAttribute("taskinfo", taskinfo);
			//ȡ�������
			//Vector subtaskVct = new Vector();
//		        subtaskVct = super.getService().getPUnitListByPatrolid( plan.getExecutorid(),
//		                     "2",taskinfo.getLinelevel() );
//		    request.setAttribute( "pointlist", subtaskVct );
//			
	    
		   
			String taskHtml = taskbo.getPUnitListByPatrolid(plan.getExecutorid(), taskinfo.getLinelevel(),taskpoint);
			request.setAttribute("tasksublineHtm", taskHtml);
			session.setAttribute("sublinelist", taskbo.getPSublineByPatrol(plan.getExecutorid(), taskinfo.getLinelevel()));
		    
	        //��֤�߶��Ƿ��ظ�
	        StencilTaskBO stenciltaskbo = new StencilTaskBO();
	        String str_subline = (String) session.getAttribute("strsubline");//�߶�id�����ж��߶��Ƿ��ڶ����������
	        String level = taskinfo.getLinelevel();
	        str_subline = stenciltaskbo.interceptStr(plan.getExecutorid(), level, taskinfo.getId(), str_subline);
			logger.info("����������� "+ str_subline);
			
	        session.setAttribute("strsubline",str_subline);
			session.setAttribute("delTaskList", delTaskList);
	        session.setAttribute("edittask","edit");
			return mapping.findForward( "editPlanTask");
		}catch(NumberFormatException numE){
			numE.printStackTrace();
			logger.warn("����ת������:"+numE.getMessage());
			return null;
		}
    	
    }
    /**
     * ɾ��������
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
			//��֤�߶��Ƿ��ظ�
			PlanBean plan = (PlanBean)session.getAttribute("Plan"); 
			TaskBean taskinfo = (TaskBean) taskList.get(index);
	        StencilTaskBO stenciltaskbo = new StencilTaskBO();
	        String str_subline = (String) session.getAttribute("strsubline");//�߶�id�����ж��߶��Ƿ��ڶ����������
	        String level = taskinfo.getLinelevel();
	        str_subline = stenciltaskbo.interceptStr(plan.getExecutorid(), level, taskinfo.getId(), str_subline);
			session.setAttribute("strsubline",str_subline);
			//��list��ɾ������
			List delTaskList = (List)session.getAttribute("delTaskList");
			delTaskList.add(taskinfo.getId());
			taskList.remove(index);
			session.setAttribute("taskList", taskList);
			session.setAttribute("st", "y");//�Ƿ��޸Ĺ���
    		if(estate.equals("edit")){
    			return mapping.findForward("editPlanResult");
            }else
            	return mapping.findForward("addPlanResult");	
		}catch(NumberFormatException numE){
			numE.printStackTrace();
			logger.warn("����ת������:"+numE.getMessage());
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
    	String patrolid =((PlanBean)session.getAttribute("Plan")).getExecutorid(); //Ѳ����Ա
        String linelevel = request.getParameter("linelevel");
        if(linelevel == null || linelevel.equals("")){
        	linelevel = "1";
        }
        
        TaskBO taskbo = new TaskBO();
		String taskHtml = taskbo.getPUnitListByPatrolid(patrolid, linelevel,"");
		request.setAttribute("tasksublineHtm", taskHtml);
		session.setAttribute("sublinelist", taskbo.getPSublineByPatrol(patrolid, linelevel));
		
        String edittask = (String) session.getAttribute("edittask");//�����Ƿ�Ϊ�༭״̬.
        long endTime = System.currentTimeMillis();
		long total=(endTime - startTime);
		logger.info("���task��ʱ��"+total+"����") ;
        if("edit".equals(edittask)){
        	return mapping.findForward( "editPlanTask" );
        }else{
        	return mapping.findForward( "addPlanTask" );
        }
		
    }
    /**
     * ��ȡ����Ѳ��� ע:��ʹ�ø÷���ʱsession������Plan�������
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
    	String patrolid =((PlanBean)session.getAttribute("Plan")).getExecutorid(); //Ѳ����Ա
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
    //���� ����߶�
    public ActionForward getTaskSublines(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ) throws
            ClientException, Exception{
    	HttpSession session = request.getSession();
    	String patrolid =((PlanBean)session.getAttribute("Plan")).getExecutorid(); //Ѳ����Ա
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
    
    //���� ����߶��ϵĵ�
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
     * ����Ѳ��ƻ�����ģ��
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
    	//�ƻ�ִ����
    	String patrolid =((PlanBean)session.getAttribute("Plan")).getExecutorid(); //Ѳ����Ա
    	//��ȡģ��id
    	String stencilid = request.getParameter("stencil");
    	//ͨ��id����ģ�� ������subtask����
    	TaskBO tbo = new TaskBO();
    	List taskList = tbo.toLoad(stencilid);
    	//ͨ��request���ص�ҳ��
    	session.setAttribute("taskList", taskList);
    	return mapping.findForward( "addPlanResult");
    }



    /**
     * ȡ�� �� / �� ��������
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
            FID = request.getParameter( "FID" ); //1, ��ͨ 2,������
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
