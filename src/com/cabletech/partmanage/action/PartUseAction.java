package com.cabletech.partmanage.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.web.ClientException;
import com.cabletech.linecut.services.LineCutReService;
import com.cabletech.partmanage.beans.Part_requisitionBean;
import com.cabletech.partmanage.dao.PartBaseInfoDao;
import com.cabletech.partmanage.dao.PartExportDao;
import com.cabletech.partmanage.dao.PartUseDao;
import com.cabletech.power.CheckPower;

public class PartUseAction extends BaseDispatchAction{
    private static Logger logger = Logger.getLogger( PartUseAction.class.
                                   getName() );

    //�����ʾ
    public ActionForward addUseShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80301" ) ){
            return mapping.findForward( "powererror" );
        }

        PartUseDao dao = new PartUseDao();
        //��õ�ǰ�û��ĵ�λ����LOGIN_USER_DEPT_NAME
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ��ʵ�
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        String deptname = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_NAME" ); ;
        request.setAttribute( "deptname", deptname );
        request.setAttribute( "deptid", userinfo.getDeptID() );
        request.setAttribute( "username", userinfo.getUserName() );
        request.setAttribute( "userid", userinfo.getUserID() );

        //��÷������ĵ�ǰʱ��
        Date nowDate = new Date();
        DateFormat df = DateFormat.getDateInstance( DateFormat.DATE_FIELD );
        String data = df.format( nowDate );
        request.setAttribute( "date", data );

        //��øõ�λ�����Ѿ���ŵĲ�����Ϣ,�Թ��û�ѡ��
        List lBaseInfo = dao.getSrockedPartInfo( userinfo.getDeptID() );
        request.setAttribute( "baseinfo", lBaseInfo );

        request.getSession().setAttribute( "type", "use2" );
        return mapping.findForward( "success" );
    }


    //ִ�г��ⵥ
    public ActionForward addUse( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80301" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartUseDao dao = new PartUseDao();
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            OracleIDImpl ora = new OracleIDImpl();
            String useid = ora.getSeq( "part_use", 10 );
            bean.setUseid( useid );
            String[] id = request.getParameterValues( "id" );
            String[] usenewnumber = request.getParameterValues( "usenewnumber" );
            String[] useoldnumber = request.getParameterValues( "useoldnumber" );
            
            // add by guixy ȡ�ò�����;��Ϣ
            String[] linecutStr = request.getParameterValues("useobjectstr1");
            String[] changeStr = request.getParameterValues("useobjectstr2");
            String objid = ora.getSeq("part_useobject_baseinfo",10);
            
            if( !dao.addUseInfo( bean, id, useoldnumber, usenewnumber, linecutStr,changeStr,objid)) {
                return forwardInfoPage( mapping, request, "error" );
            }
            log( request, "���Ϲ���", "��ӳ��ⵥ" );
            return forwardInfoPage( mapping, request, "80302" );
        }
        catch( Exception e ){
            logger.error( "��ִ�г��ⵥ�г���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ʾ���г��ⵥ
    public ActionForward showAllUse( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80302" )
        		|| CheckPower.checkPower( request.getSession(), "80303" )
            ||CheckPower.checkPower( request.getSession(), "80309" )){
            flag=true;
        }
        if(!flag)
            return mapping.findForward( "powererror" );

        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        //if( userinfo.getType().equals( "12" ) ){ //��������ƶ���˾�ǲ������
        /*
        if( userinfo.getDeptype().equals( "1" )
            &&!userinfo.getRegionID().substring(2,6).equals("0000") ){ //��������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        */

        try{
            PartUseDao dao = new PartUseDao();
            if(request.getParameter("querytype")!=null
                &&request.getParameter("querytype").equals("1")){
                List lReqInfo = dao.getAllUsePart( request );
                request.getSession().setAttribute( "useinfo", lReqInfo );
                request.getSession().setAttribute( "type", "showuse1" );

           }else{
                List lReqInfo = dao.getAllUse( request );
                request.getSession().setAttribute( "useinfo", lReqInfo );
                request.getSession().setAttribute( "type", "use1" );
            }
            request.getSession().setAttribute("querytype","1");
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "��ʾ���г��ⵥ��Ϣ����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ʾһ����ⵥ����ϸ��Ϣ
    public ActionForward showOneUse( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80302" )
        		|| CheckPower.checkPower( request.getSession(), "80303" )
            ||CheckPower.checkPower( request.getSession(), "80309" )){
            flag=true;
        }
        if(!flag)
            return mapping.findForward( "powererror" );

        Part_requisitionBean bean = ( Part_requisitionBean )form;
        Part_requisitionBean useinfo = new Part_requisitionBean(); //����ҳ��ĳ��ⵥ������Ϣ
        List usepartinfo = null;
        PartUseDao dao = new PartUseDao();
        String useid = request.getParameter( "useid" ); //���Ҫ�޸ĵĳ��ⵥid

        try{
            //��õ�ǰ�û��ĵ�λ����
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            //if( userinfo.getType().equals( "12" ) ){ //��������ƶ���˾�ǲ������
            /*
            if( userinfo.getDeptype().equals( "1" )
                &&!userinfo.getRegionID().substring(2,6).equals("0000") ){ //��������ƶ���˾�ǲ������
                return forwardErrorPage( mapping, request, "partstockerror" );
            }
            */
            //�����ⵥ������Ϣ
            useinfo = dao.getOneUse( useid ); ;
            request.setAttribute( "useinfo", useinfo );

            //�����ⵥ�����Ĳ�����Ϣ
            usepartinfo = dao.getPartOfOneUse( useid );
            request.setAttribute( "usepartinfo", usepartinfo );
            request.getSession().setAttribute( "type", "use10" );

            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "����ʾ��ϸ�г��ִ���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //�ۺϲ�ѯ��ʾ
    public ActionForward queryShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80303" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        /*
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        */
        PartUseDao useDao = new PartUseDao();
        try{
        	List lReason = null;
        	if ("1".equals(userinfo.getDeptype())) {
        		// �ƶ��û� 
        		List lDept = useDao.getConDeptInfo(userinfo);
        		request.setAttribute( "condept", lDept );
			} else {
				// ��ά�û�
				String contractorid = ( String )request.getSession().getAttribute(
                "LOGIN_USER_DEPT_ID" );
				
				//������������
				List lUser = useDao.getUserArr( contractorid );
				request.setAttribute( "useuser", lUser );
				//��ò�����;�б�
				lReason = useDao.getReasonArr( contractorid );
			}
            
            request.setAttribute( "usereason", lReason );
            request.getSession().setAttribute( "type", "use3" );
            super.setPageReset(request);
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "�ۺϲ�ѯ��ʾ�쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //�ۺϲ�ѯִ��
    public ActionForward queryExec( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80303" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        /*
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        */
        Part_requisitionBean bean = ( Part_requisitionBean )form;
        try{
        	String contractorid = null;
        	if (!"1".equals(userinfo.getDeptype())) {
				contractorid = ( String )request.getSession().getAttribute(
                				"LOGIN_USER_DEPT_ID" );
				bean.setContractorid( contractorid );
			}                        
           
            PartUseDao dao = new PartUseDao();

            List lUseInfo = dao.getAllUseForSearch( bean,userinfo );
            request.getSession().setAttribute( "useinfo", lUseInfo );
            request.getSession().setAttribute( "type", "use1" );
            request.getSession().setAttribute("bean",bean);
            super.setPageReset(request);
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "�ۺϲ�ѯִ���쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //////////////////////ʹ��ͳ��//////////////////
    //ʹ��ͳ�Ʋ�ѯ��ʾ
    public ActionForward queryShowForStat( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80307" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            PartUseDao useDao = new PartUseDao();
            String condition="";
            if(userinfo.getType().equals("11")){}
            if(userinfo.getType().equals("12")){
                condition+=" and regionid='"+userinfo.getRegionID()+"'";
            }
            if(userinfo.getType().equals("21")){
                condition+=" and con.contractorid in ("
                    +"         select contractorid from contractorinfo "
                    +"         where parentcontractorid='"+userinfo.getDeptID()+"' "
                    +"         or contractorid='"+userinfo.getDeptID()+"')";
            }
            if(userinfo.getType().equals("22")){
                condition+=" and con.contractorid='"+userinfo.getDeptID()+"'";
            }
            //���ʹ�õ�λ�б�
            List lDept = useDao.getDeptArrs( condition );
            //��ò��������б�
            PartBaseInfoDao baseDao = new PartBaseInfoDao();
            List lPartName = baseDao.getAllNames( condition );
            //��ò����ͺ��б�
            List lPartType = baseDao.getAllTypes( condition );
            //����������������б�
            List lFactory = baseDao.getAllFactorys( condition );
            //��ò�����;�б�
            List lReason = null;
            if( userinfo.getDeptype().equals( "2" ) ){ //�Ǵ�ά��λ,������ñ���˾����;�б�
                lReason = useDao.getReasonArr( contractorid );
            }
            else{ //���ƶ���˾,���������;�б�
                lReason = useDao.getAllReasonArr( userinfo.getRegionID() );
            }
            
            // 2009-5-14 by guixy start
			//��ȡ��·����
    		LineCutReService service = new LineCutReService();
			List levelList = service.getLineLevle();
			request.setAttribute("levelInfo", levelList);
			// 2009-5-14 by guixy end
                        
            request.setAttribute( "deptinfo", lDept );
            request.setAttribute( "nameinfo", lPartName );
            request.setAttribute( "typeinfo", lPartType );
            request.setAttribute( "factoryinfo", lFactory );
            request.setAttribute( "usereason", lReason );

            request.setAttribute( "usedept", userinfo.getDeptype() );
            request.setAttribute( "usetype", userinfo.getType() );

            request.getSession().setAttribute( "type", "use7" );
            super.setPageReset(request);
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "ʹ��ͳ�Ʋ�ѯ��ʾ�쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //ͳ�Ʋ�ѯִ��
    public ActionForward dostat( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	String levanName="";
    	String sublineName="";
    	String sublineIdName="";
    	String linechangename="";
    	String [] levanTem = null;
    	String [] sublineNameTem = null;
    	String [] sublineIdNameTem = null;
    	String [] linechangenameTem = null;
        if( !CheckPower.checkPower( request.getSession(), "80307" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            if(bean.getTotaltype() == null || "".equals(bean.getTotaltype())){
            	bean = (Part_requisitionBean) request.getSession().getAttribute("partbean");
            }
            
            Part_requisitionBean partbean=new Part_requisitionBean();
            partbean.setTotaltype(bean.getTotaltype());
            partbean.setSubline(bean.getSubline());
            partbean.setLevel(bean.getLevel());
            partbean.setSublineId(bean.getSublineId());
            partbean.setLinechangename(bean.getLinechangename());
            partbean.setCutchangename(bean.getCutchangename());
            partbean.setContractorid(bean.getContractorId());
            partbean.setBegintime(bean.getBegintime());
            partbean.setEndtime(bean.getEndtime());
            request.getSession().setAttribute("partbean", partbean);
            
            if("2".equals(bean.getTotaltype())){
            	levanName = bean.getLevel();
            	sublineName = bean.getSubline();
            	sublineIdName = bean.getSublineId();
            	linechangename = bean.getLinechangename();
            	if(levanName!=null && !"".equals(levanName)){
            		levanTem = levanName.split(",");
            		bean.setLevel(levanTem[0]);
            	}
            	if(sublineName != null && !"".equals(sublineName)){
            		sublineNameTem = sublineName.split(",");
            		bean.setSubline(sublineNameTem[0]);
            	}
            	if(sublineIdName != null && !"".equals(sublineIdName)){
            		sublineIdNameTem = sublineIdName.split(",");
            		bean.setSublineId(sublineIdNameTem[0]);
            	}
            	if(linechangename != null && !"".equals(linechangename)){
            		linechangenameTem = linechangename.split(",");
            		bean.setLinechangename(linechangenameTem[0]);
            	}
            }
            if("3".equals(bean.getTotaltype())){
            	levanName = bean.getLevel();
            	if(levanName!=null && !"".equals(levanName)){
            		levanTem = levanName.split(",");
            		bean.setLevel(levanTem[0]);
            	}
            }
            bean.setRegionid( userinfo.getRegionID() );
            PartUseDao dao = new PartUseDao();
            List lUseInfo = null;
            String condition="";
            if(userinfo.getType().equals("11")){}
            if(userinfo.getType().equals("12")){
                condition+=" and con.regionid='"+userinfo.getRegionID()+"'";
            }
            if(userinfo.getType().equals("21")){
                condition+=" and con.contractorid in ("
                    +"         select contractorid from contractorinfo "
                    +"         where parentcontractorid='"+userinfo.getDeptID()+"' "
                    +"         or contractorid='"+userinfo.getDeptID()+"')";
            }
            if(userinfo.getType().equals("22")){
                condition+=" and con.contractorid='"+userinfo.getDeptID()+"'";
            }
            //if( userinfo.getDeptype().equals( "1" ) ){ //���ƶ���˾�Ĳ�ѯ
            //    lUseInfo = dao.getAllPartUse( bean );
            //}
            //else{ //�Ǵ�ά��λ�Ĳ�ѯ
            //    lUseInfo = dao.getAllPartUse( bean, contractorid );
            //}
            lUseInfo=dao.getAllPartUse(condition,bean);
            
            if(levanTem != null){
            	bean.setLevel(levanTem[1]);
            }
            if(sublineNameTem != null){
            	bean.setSubline(sublineNameTem[1]);
            }
            if(sublineIdNameTem != null){
            	bean.setSublineId(sublineIdNameTem[1]);
            }
            if(linechangenameTem != null){
            	bean.setLinechangename(linechangenameTem[1]);
            }
            
            request.getSession().setAttribute( "useinfo", lUseInfo );
            request.getSession().setAttribute( "type", "use70" );
            request.getSession().setAttribute("bean",bean);
            super.setPageReset(request);
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "�ۺϲ�ѯִ���쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    
    //����ĳ��ͳ�Ʋ�����ϸ��Ϣ jixf
    
    public ActionForward doShowOneForPart( ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ){
    	String partId = request.getParameter("partId");
    	Part_requisitionBean bean = (Part_requisitionBean)request.getSession().getAttribute("partbean");
    	
    	Part_requisitionBean partbean=new Part_requisitionBean();
        partbean.setTotaltype(bean.getTotaltype());
        partbean.setSubline(bean.getSubline());
        partbean.setLevel(bean.getLevel());
        partbean.setSublineId(bean.getSublineId());
        partbean.setLinechangename(bean.getLinechangename());
        partbean.setCutchangename(bean.getCutchangename());
        partbean.setContractorid(bean.getContractorId());
        partbean.setBegintime(bean.getBegintime());
        partbean.setEndtime(bean.getEndtime());
        request.getSession().setAttribute("partbean", partbean);
        
        String levanName = bean.getLevel();
    	String sublineName = bean.getSubline();
    	String sublineIdName = bean.getSublineId();
    	String linechangename = bean.getLinechangename();
    	if(levanName!=null && !"".equals(levanName)){
    		String[] levanTem = levanName.split(",");
    		bean.setLevel(levanTem[0]);
    	}
    	if(sublineName != null && !"".equals(sublineName)){
    		String[] sublineNameTem = sublineName.split(",");
    		bean.setSubline(sublineNameTem[0]);
    	}
    	if(sublineIdName != null && !"".equals(sublineIdName)){
    		String[] sublineIdNameTem = sublineIdName.split(",");
    		bean.setSublineId(sublineIdNameTem[0]);
    	}
    	if(linechangename != null && !"".equals(linechangename)){
    		String[] linechangenameTem = linechangename.split(",");
    		bean.setLinechangename(linechangenameTem[0]);
    	}
        
    	PartUseDao dao = new PartUseDao();    
    	BasicDynaBean partinfo = dao.getOnPartById(partId);
    	List useinfo = dao.getOnePartUse(partId,bean);
    	request.getSession().setAttribute("userInfo", useinfo);
    	request.setAttribute("partinfo", partinfo);
    	request.getSession().setAttribute("type", "use700");
    	return mapping.findForward("success");
//    	return null;
    }


    //////////////////////�˿⴦��//////////////////
    //�˿�_������;ѡ����ʾ
    public ActionForward showBackReason( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
//        if( !CheckPower.checkPower( request.getSession(), "80306" ) ){
//            return mapping.findForward( "powererror" );
//        }
//        //��õ�ǰ�û��ĵ�λ����
//        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
//                            "LOGIN_USER" );
//        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
//            return forwardErrorPage( mapping, request, "partstockerror" );
//        }
//
//        try{
//            String contractorid = ( String )request.getSession().getAttribute(
//                                  "LOGIN_USER_DEPT_ID" );
//            PartUseDao useDao = new PartUseDao();
//            //��ò�����;�б�
//            List lReason = null;
//            lReason = useDao.getReasonArrForBack( contractorid );
//            request.setAttribute( "usereason", lReason );
//
//            request.getSession().setAttribute( "type", "use6" );
//            return mapping.findForward( "success" );
//
//        }
//        catch( Exception e ){
//            logger.error( "�˿�_������;ѡ����ʾ�쳣:" + e.getMessage() );
//            return forwardErrorPage( mapping, request, "error" );
//        }
    	response.setContentType("text/html; charset=utf-8");
    	String contractorid = (String) request.getSession().getAttribute(
				"LOGIN_USER_DEPT_ID");
		PartUseDao useDao = new PartUseDao();
		// ��ò�����;�б�
		List lReason = null;
		lReason = useDao.getReasonArrForBack(contractorid);

		StringBuffer sb = new StringBuffer();
		sb.append("[");
		DynaBean row;
		for (int i = 0; i < lReason.size(); i++) {
			row = (DynaBean) lReason.get(i);
			sb.append("['").append(String.valueOf(row.get("usereason")))
					.append("--").append(String.valueOf(row.get("useid")))
					.append("'],");
		}
			
		
		int length = sb.toString().length() - 1;
		String str = sb.toString().substring(0,length) + "]";
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(str);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
    }


    //�˿�_����������ʾ
    public ActionForward showBackPart( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80306" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartUseDao useDao = new PartUseDao();
            Part_requisitionBean useinfo = new Part_requisitionBean(); //����ҳ��ĳ��ⵥ������Ϣ
            List usepartinfo = null;
            PartUseDao dao = new PartUseDao();
            String useid = request.getParameter("useid"); //���Ҫ�޸ĵĳ��ⵥid
            System.out.println(useid);
            if(useid == null) {
            	//	 ���ⵥѡ�������
            	return forwardErrorPage( mapping, request, "80306e" );
            }
            int index = useid.indexOf("--");
            if(index == -1) {
            	// ���ⵥѡ�������
            	return forwardErrorPage( mapping, request, "80306e" );
            }
            // ǰ̨��������"��;"+"--"+"����id"
            useid = useid.substring(index+2, useid.length());
            //�����ⵥ������Ϣ
            useinfo = dao.getOneUse( useid ); ;
            request.setAttribute( "useinfo", useinfo );

            if(useinfo == null || "".equals(useinfo.getUseid())) {
            	// ���ⵥѡ�������
            	return forwardErrorPage( mapping, request, "80306e" );
            }
            
            //�����ⵥ�����Ĳ�����Ϣ
            usepartinfo = dao.getPartOfOneUse( useid );
            request.setAttribute( "usepartinfo", usepartinfo );

            request.getSession().setAttribute( "type", "use60" );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "//�˿�_����������ʾ�쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ʼ�˿�
    public ActionForward doBackStock( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80306" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartUseDao dao = new PartUseDao();
            String useid = request.getParameter( "useid" );
            String contractorid = userinfo.getDeptID();
            String[] id = request.getParameterValues( "id" );
            String[] newbacknumber = request.getParameterValues( "newbacknumber" );
            String[] oldbacknumber = request.getParameterValues( "oldbacknumber" );
            if( !dao.backStock( contractorid, useid, id, newbacknumber, oldbacknumber ) ){
                return forwardErrorPage( mapping, request, "error" );
            }
            log( request, "���Ϲ���", "�����˿�" );
            return forwardInfoPage( mapping, request, "80306" );
        }
        catch( Exception e ){
            logger.error( "�ڿ�ʼ�˿��г���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //����ʹ��һ�����ѯ��������Excel��
    public ActionForward exportUse( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            Part_requisitionBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( Part_requisitionBean )request.getSession().getAttribute( "bean" );
                logger.info( "��ò�ѯ����bean������" );
                if("1".equals(bean.getTotaltype())){	                
	                logger.info( "�������ƣ�" + bean.getName());
	                logger.info( "�����ͺţ�" + bean.getType() );
	                logger.info( "�������ң�" + bean.getFactory());
	                logger.info( "������;��" + bean.getUsereason() );
                }else if("2".equals(bean.getTotaltype())){
                	logger.info("��·����" + bean.getLevel());
                	logger.info("�����߶Σ�" + bean.getSubline()) ;
                	logger.info("�м̶Σ�" + bean.getSublineId()) ;
                	logger.info("������ƣ�" + bean.getLinechangename()) ;
                }else if("3".equals(bean.getTotaltype())){
                	logger.info("��·����: " + bean.getLevel());
                	logger.info("��������: " + bean.getCutchangename()) ;
                }
                logger.info( "��ʼʱ�䣺" + bean.getBegintime() );
                logger.info( "����ʱ�䣺" + bean.getEndtime() );
                if( !bean.getcontractorid().equals( "" ) ){
                    String sql =
                        "select c.CONTRACTORNAME from contractorinfo c where c.CONTRACTORID = '" + bean.getcontractorid()
                        + "'";
                    QueryUtil query = new QueryUtil();
                    ResultSet rs = null;
                    rs = query.executeQuery( sql );
                    while( rs.next() ){
                        bean.setContractorname( rs.getString( 1 ) );
                    }
                    logger.info( "ʹ�õ�λ��" + bean.getContractorname() );
                }
//                request.getSession().removeAttribute("bean");
            }
            PartExportDao dao = new PartExportDao();
            logger.info( " ����dao" );
            List list = ( List )request.getSession().getAttribute( "useinfo" );
            logger.info( "�õ�list" );
            dao.ExportUse( list, bean, response );
            logger.info( "���excel�ɹ�" );
           
            return null;
        }
        catch( Exception e ){
            logger.error( "��������ʹ��һ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportUseResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            Part_requisitionBean bean = null;
            if(request.getSession().getAttribute("bean") != null){
                bean = ( Part_requisitionBean )request.getSession().getAttribute( "bean" );
                logger.info("��ò�ѯ����bean������");
                logger.info("id��"+bean.getUseuserid()+"����ԭ��"+bean.getUsereason()+"��ʼ���ڣ�"+bean.getBegintime() + "�������ڣ�"+bean.getEndtime());
                if( !bean.getUseuserid().equals( "" ) ){
                    String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + bean.getUseuserid() + "'";
                    QueryUtil query = new QueryUtil();
                    ResultSet rs = null;
                    rs = query.executeQuery( sql );
                    while( rs.next() ){
                        bean.setUsername( rs.getString( 1 ) );
                    }
                }
                request.getSession().removeAttribute("bean");
            }

            PartExportDao dao = new PartExportDao();
            logger.info( " ����dao" );
            List list = ( List )request.getSession().getAttribute( "useinfo" );
            logger.info( "�õ�list" );
            dao.exportUseResult( list, bean, response );
            logger.info( "���excel�ɹ�" );

            return null;
        }
        catch( Exception e ){
            logger.error( "������Ϣ���������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

    //����ά�����ϳ��ⵥ��ϸ��Ϣһ����
    public ActionForward exportUseList( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
//        if( !CheckPower.checkPower( request.getSession(), "80403" ) ){
//            return mapping.findForward( "powererror" );
//        }
//        //��õ�ǰ�û��ĵ�λ����
//        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
//                            "LOGIN_USER" );
//        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
//            return forwardErrorPage( mapping, request, "partstockerror" );
//        }

        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );

        try{

            String useuserid = request.getParameter( "useuserid" );;
            String usereason = request.getParameter( "usereason" );
            String begintime = request.getParameter( "begintime" );
            String endtime = request.getParameter( "endtime" );

            Part_requisitionBean bean = ( Part_requisitionBean )form;
            bean.setContractorid( contractorid );
            bean.setUseuserid(useuserid);
            bean.setUsereason(usereason);
            bean.setBegintime(begintime);
            bean.setEndtime(endtime);
            if( useuserid != null ){
                String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + bean.getUseuserid() + "'";
                QueryUtil query = new QueryUtil();
                ResultSet rs = null;
                rs = query.executeQuery( sql );
                while( rs.next() ){
                    bean.setUsername( rs.getString( 1 ) );
                }
                logger.info( "������������" + bean.getUsername() );
                }
            PartUseDao useDao = new PartUseDao();
            List list = useDao.getUseList( bean );

            PartExportDao dao = new PartExportDao();
            dao.exportUseList(list, bean, response);

            return null;

        }
        catch( Exception e ){
            logger.error( "������Ϣ�����쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

    /**
     * ��ѯ���в���ʹ����Ϣ�ı�
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward queryAllUse(ActionMapping mapping, ActionForm form,
        HttpServletRequest request,HttpServletResponse response)
        throws ClientException, Exception{
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );

        List regionList=null;
        List deptList=null;
        List conList=null;
        String region="select r.regionid,r.regionname "
                     +"from region r "
                     +"where (r.state is null or r.state<>'1') and substr(regionid,3,4)<>'1111'";
        String dept="select d.deptid,d.deptname,d.regionid "
                    +"from deptinfo d "
                    +"where (d.state is null or d.state<>'1')";
        String con="select c.contractorid ,c.contractorname ,c.regionid "
                   +"from contractorinfo c "
                   +"where (c.state is null or c.state<>'1')";
        if(userinfo.getType().equals("21")){
            con+=" and contractorid in ("
                +"   select contractorid from contractorinfo "
                +"   where parentcontractorid='"+userinfo.getDeptID()+"' or contractorid='"+userinfo.getDeptID()+"'"
                +" )";
        }
        if(userinfo.getType().equals("12")){
            con+=" and regionid='"+userinfo.getRegionID()+"'";
            dept+=" and regionid='"+userinfo.getRegionID()+"'";
        }
        if(userinfo.getType().equals("22")){
            con+=" and contractorid='"+userinfo.getDeptID()+"'";
        }
        dept+=" order by regionid";
        con+=" order by regionid";
        regionList = super.getDbService().queryBeans( region );
        deptList = super.getDbService().queryBeans( dept );
        conList = super.getDbService().queryBeans( con );
        request.setAttribute("regionlist",regionList);
        request.setAttribute("deptlist",deptList);
        request.setAttribute("conlist",conList);

        PartBaseInfoDao dao = new PartBaseInfoDao();
        List lName = dao.getAllName();
        List lType = dao.getAllType();
        request.setAttribute( "nameList", lName );
        request.setAttribute( "typeList", lType );

        return mapping.findForward("queryalluse");
    }
    
    // ѡ����;ģʽҳ��
    public ActionForward showUseChange(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,HttpServletResponse response)
            throws ClientException, Exception{
    	// ��¼��Ա�Ĳ���ID
    	UserInfo userinfo = ( UserInfo )request.getSession().getAttribute("LOGIN_USER" );
    	String deptId = userinfo.getDeptID();
    	
    	// ȡ��ѡ����;�ı�־
    	String useFlg = request.getParameter("flg");
    	request.setAttribute("flg", useFlg);
    	String sql = "";
    	List returnList = null;
 //   	if ("1".equals(useFlg)) {
    		// ���
    		
//    		sql = "select reid id,name name from LINE_CUTINFO where AUDITRESULT='ͨ������' and ISARCHIVE = '�Ѿ�����' " 
//				+ "and CONTRACTORID = '" + deptId + "' order by REID";	
			//��ȡ��·����
    		LineCutReService service = new LineCutReService();
			List levelList = service.getLineLevle();
			request.setAttribute("linelevelList", levelList);
//		} else {
			// ����
//			sql = "select id id,changename name from CHANGEINFO where APPROVERESULT = 'ͨ����' and  step='B2' " 
//				+ " and APPLYUNIT = '" + deptId + "' order by id";
//			returnList = super.getDbService().queryBeans(sql);
//		}
    	    	
    	
    	
    	// ��ѡ����;���϶�Ӧ�������ID
    	String hiddenObjId = request.getParameter("obj");
    	request.setAttribute("hiddenobjname", hiddenObjId);
    	// ȡ�ó�����ϵ�����
    	String sumNum = request.getParameter("sumnum");
    	request.setAttribute("sum",sumNum);
    	
    	request.setAttribute( "returnList", returnList );
    	
    	return mapping.findForward("changeuse");
    }
    
    /**
     * �����߶�ѡ��ĸ��
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward getCutNameBySublineid(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json; charset=GBK");
		String sublineid = request.getParameter("sublineId");
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
        "LOGIN_USER" );
//		String sql = "select reid id,name name from LINE_CUTINFO where AUDITRESULT='ͨ������' and ISARCHIVE = '�Ѿ�����' " 
//			+ "and CONTRACTORID = '" + userinfo.getDeptID() + "' order by REID";
		String sql;
		String deptid = userinfo.getDeptID();
		if(userinfo.getDeptype().equals("1")) {
			sql = "select l.reid id, l.name from line_cutinfo l,deptinfo d where subStr(l.SUBLINEID,0,8)='" + sublineid + "'" +
			"  and l.isarchive != '������' and l.isarchive !='�Ѿ�����' and l.deptid='" + deptid + "' and l.deptid = d.deptid";
		} else {
			sql = "select l.reid id, l.name from line_cutinfo l,deptinfo d where subStr(l.SUBLINEID,0,8)='" + sublineid + "'" +
			"  and l.isarchive != '������' and l.isarchive !='�Ѿ�����' and l.contractorid='" + deptid + "' and l.deptid = d.deptid";
		}
		try {
			List returnList = super.getDbService().queryBeans(sql);
			JSONArray ja = JSONArray.fromObject(returnList);
			PrintWriter out = response.getWriter();
			out.write(ja.toString());
			out.flush();
			out.close();
			System.out.println(ja.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
    /**
     * �����߶μ���ѡ�������
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward getLinechangeNameByLevel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json; charset=UTF-8");
		String levelId = request.getParameter("levelId");
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
        "LOGIN_USER" );
		String sql;
		if(userinfo.getDeptype().equals("1")) {
			sql = "select id id,changename name from CHANGEINFO where APPROVERESULT = 'ͨ����' and  step='B2' " 
				+ " and regionid = '" + userinfo.getRegionid() + "'"
				+ " and lineclass = '" + levelId + "'"
				+ " order by id";
		} else {
			sql = "select id id,changename name from CHANGEINFO where APPROVERESULT = 'ͨ����' and  step='B2' " 
				+ " and APPLYUNIT = '" + userinfo.getDeptID() + "'" 
				+ " and lineclass = '" + levelId + "'"
				+ " order by id";
		}
		try {
			List returnList = super.getDbService().queryBeans(sql);
			JSONArray ja = JSONArray.fromObject(returnList);
			PrintWriter out = response.getWriter();
			out.write(ja.toString());
			out.flush();
			out.close();
			System.out.println(ja.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
    // ѡ����;ģʽҳ��
    public ActionForward displayUse(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,HttpServletResponse response)
            throws ClientException, Exception {
    	// ȡ�ó��ⵥID
    	String useId = request.getParameter("useid");
    	String baseId = request.getParameter("baseid");
    	// sql���
    	String sql = "select obj.id, obj.objectnum , l.name objname , l.reid reid "
    				 + " from part_useobject_baseinfo obj "
    				 // + " left join part_baseinfo b on obj.baseid = b.id  "
    				 + " left join LINE_CUTINFO l on l.reid = obj.objectid "
    				 + " where obj.objecttype = '0' and obj.useid = '" + useId + "'"
    				 + " and obj.baseid = '" + baseId + "'";
    	List linecutList = super.getDbService().queryBeans(sql);
    	sql = "select obj.id, obj.objectnum , c.changename objname , c.id reid "
			 + " from part_useobject_baseinfo obj "
			// + " left join part_baseinfo b on obj.baseid = b.id  "
			 + " left join changeinfo c on c.id = obj.objectid "
			 + " where obj.objecttype = '1' and obj.useid = '" + useId + "'"
    	 	+ " and obj.baseid = '" + baseId + "'";
    	List chaneList = super.getDbService().queryBeans(sql);
    	
    	// �����ݷ���rquest�С�
    	
    	request.setAttribute( "linecutList", linecutList );
    	request.setAttribute( "chaneList", chaneList );
    	return mapping.findForward("showchangeuse");
    }
    
    public static void main(String[] args) {
		String str = "aaaa--bb";
		System.out.println(str.substring(str.indexOf("--")+2, str.length()));
	}
}
