package com.cabletech.partmanage.action;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.dao.UserInfoDAOImpl;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.sm.SendSMRMI;
import com.cabletech.commons.web.ClientException;
import com.cabletech.partmanage.beans.Part_requisitionBean;
import com.cabletech.partmanage.dao.PartBaseInfoDao;
import com.cabletech.partmanage.dao.PartExportDao;
import com.cabletech.partmanage.dao.PartRequisitionDao;
import com.cabletech.power.CheckPower;
import com.cabletech.watchinfo.templates.WatchDetailTemplate;

public class PartRequisitionAction extends BaseDispatchAction{
    private static Logger logger = Logger.getLogger( PartRequisitionAction.class.
                                   getName() );
    //�޸���ʾ
    public ActionForward upshow( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80104" ) ){
            return mapping.findForward( "powererror" );
        }

        Part_requisitionBean bean = ( Part_requisitionBean )form;
        Part_requisitionBean reqinfo = new Part_requisitionBean(); //����ҳ������뵥������Ϣ
        List reqpartinfo = null;
        PartRequisitionDao dao = new PartRequisitionDao();
        String reid = request.getParameter( "reid" ); //���Ҫ�޸ĵ����뵥id

        try{
            //�ܷ��޸�
            if( !dao.valiReqForUp( reid ) ){
                return forwardErrorPage( mapping, request, "80104e" );
            }
            //��õ�ǰ�û��ĵ�λ����
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������޸Ĳ��������
                return forwardErrorPage( mapping, request, "partstockerror" );
            }
            String deptname = dao.getUserDeptName( userinfo );
            request.setAttribute( "deptname", deptname );
            request.setAttribute( "deptid", userinfo.getDeptID() );
            request.setAttribute( "username", userinfo.getUserName() );
            request.setAttribute( "userid", userinfo.getUserID() );

            //��÷������ĵ�ǰʱ��
            Date nowDate = new Date();
            DateFormat df = DateFormat.getDateInstance( DateFormat.DATE_FIELD );
            //String data = DateUtil.DateToString(nowDate);
            String date = df.format( nowDate );
            request.setAttribute( "date", date );

            //��ò�����Ϣ,�Թ��û�ѡ��
            List lBaseInfo = dao.getAllInfo( userinfo.getRegionID() );
            request.setAttribute( "baseinfo", lBaseInfo );
            //������뵥������Ϣ
            reqinfo = dao.getOneReq( reid );
            request.setAttribute( "reqinfo", reqinfo );
            // add by guixy 
            // ���Ŀ�괦���ˣ����û�ѡ��.
            List ltarget = dao.getTargetman( request );
            request.setAttribute( "ltargetman", ltarget );
            // add by guixy 
            
            //������뵥������Ĳ�����Ϣ
            reqpartinfo = dao.getReqPart( reid, request );
            request.setAttribute( "reqpartinfo", reqpartinfo );
            request.getSession().setAttribute( "type", "4" );

            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "���޸���ʾ�г��ִ���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //ִ���޸�
    public ActionForward upRequisition( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        //Ȩ�޼��
        if( !CheckPower.checkPower( request.getSession(), "80104" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartRequisitionDao dao = new PartRequisitionDao();
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );
            bean.setContractorid( contractorid );
            String[] id = request.getParameterValues( "id" );
            String[] renumber = request.getParameterValues( "renumber" );

            //ִ���޸Ĳ���
            if( !dao.doUpdateRequisition( bean, id, renumber ) ){ //�������뵥-���϶�Ӧ��
                return forwardErrorPage( mapping, request, "error" );
            }
            log( request, "���Ϲ���", "�޸Ĳ������뵥" );
            return forwardInfoPage( mapping, request, "80104" );
        }
        catch( Exception e ){
            logger.error( "�ڸ������뵥�г���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ʾ�������뵥
    public ActionForward showAllRequisition( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80102" )
        	|| CheckPower.checkPower( request.getSession(), "80103" )
            ||CheckPower.checkPower( request.getSession(), "80109" )){
            flag=true;
        }
        if(!flag){
            return mapping.findForward( "powererror" );
        }

        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        //if( userinfo.getType().equals( "12" ) ){ //��������ƶ���˾�ǲ�����鿴���������
        if( userinfo.getDeptype().equals( "1" )
            &&!userinfo.getRegionID().substring(2,6).equals("0000") ){ //��������ƶ���˾�ǲ�����鿴���������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        try{
            PartRequisitionDao dao = new PartRequisitionDao();
            
            if(request.getParameter("querytype")!=null
                &&request.getParameter("querytype").equals("1")){
                List lReqInfo = dao.getAllRequisition( request );
                request.getSession().setAttribute( "reqinfo", lReqInfo );
                request.getSession().setAttribute( "type", "showall01" );
            }
            else{
                List lReqInfo = dao.getAllReq( request );
                request.getSession().setAttribute( "reqinfo", lReqInfo );
                request.getSession().setAttribute( "type", "1" );
            }
            request.getSession().setAttribute("querytype","1");
            //super.setPageReset(request);
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "��ʾ�������뵥����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //�����ʾ
    public ActionForward addReShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80101" ) ){
            return mapping.findForward( "powererror" );
        }

        PartRequisitionDao dao = new PartRequisitionDao();
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ�������Ӳ��������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        try{
            String deptname = dao.getUserDeptName( userinfo );
            request.setAttribute( "deptname", deptname );
            request.setAttribute( "deptid", userinfo.getDeptID() );
            request.setAttribute( "username", userinfo.getUserName() );
            request.setAttribute( "userid", userinfo.getUserID() );

            //��÷������ĵ�ǰʱ��
            Date nowDate = new Date();
            DateFormat df = DateFormat.getDateInstance( DateFormat.DATE_FIELD );
            String data = df.format( nowDate );
            request.setAttribute( "date", data );
            //���Ŀ�괦���ˣ����û�ѡ��.
            List ltarget = dao.getTargetman( request );
            request.setAttribute( "ltargetman", ltarget );
            //��ò�����Ϣ,�Թ��û�ѡ��
            List lBaseInfo = dao.getAllInfo( userinfo.getRegionID() );
            request.setAttribute( "baseinfo", lBaseInfo );
            request.getSession().setAttribute( "type", "2" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.warn( "��ʾ�쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    /**
     *  ִ��������뵥
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward addRequisition( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        /**��֤Ȩ��**/
        if( !CheckPower.checkPower( request.getSession(), "80101" ) ){
            return mapping.findForward( "powererror" );
        }
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartRequisitionDao dao = new PartRequisitionDao();
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            bean.setRegionid( userinfo.getRegionID() );
            OracleIDImpl ora = new OracleIDImpl();
            String reid = ora.getSeq( "Part_requisition", 10 ); //��ȡid
            bean.setReid( reid );

            String[] id = request.getParameterValues( "id" );
            String[] renumber = request.getParameterValues( "renumber" );
            // �ж��Ƿ�û��������ϵ����������һ�����ϵ�������û�У��򷵻���Ϣ��ʾ�û�
            boolean flag = false;
            // �����ظ�ѡ��ı�־
            boolean flg = false;
            for( int i = 0; i < renumber.length; i++ ){
            	flg = false;
                if( renumber[i].equals( "0" ) || id[i].trim().equals( "" )){
                    flag = false;
                    break;
                }
                for (int j = 0; j < id.length; j++) {
					if(i != j) {
						if(id[i].equals(id[j])) {
							flg = true;
							break;
						}
					}
				}
                if(flg) {
                	break;
                }
            }
            if( flag ){
                return forwardErrorPage( mapping, request, "80102e" );
            }
            if(flg) {
            	return forwardErrorPage( mapping, request, "80102e2" );
            }

            if( !dao.doAddRequisition( bean, id, renumber ) ){
                //System.out.println( "Error :" );
                return forwardErrorPage( mapping, request, "error" );
            }
            /** ���Ͷ���**/
            String reason = bean.getReason();
            String str = subString(reason,20,"......");
            StringBuffer sb = new StringBuffer();
            sb.append("<").append(str).append(">").
            append(request.getSession().getAttribute( "LOGIN_USER_DEPT_NAME" )).
            append("��ά��λ�Ĳ��Ϲ����ȴ�����ǩ�պʹ���");
            if( request.getSession().getAttribute( "isSendSm" ).equals( "send" ) ){
                String objectman = new UserInfoDAOImpl().findById( request.getParameter( "targetman" ) ).getPhone();
                if( objectman != null && !objectman.equals( "" ) ){
                    String msg = sb.toString();
                    SendSMRMI.sendNormalMessage( userinfo.getUserID(), objectman, msg, "00" );
                    System.out.println( msg );
                }
            }
            log( request, "���Ϲ���", "��Ӳ������뵥" );
            return forwardInfoPage( mapping, request, "80102" );
        }
        catch( Exception e ){
            e.printStackTrace();
            logger.error( "��������뵥�г���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ʾһ�����뵥����ϸ��Ϣ
    public ActionForward showOneInfo( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80102" )
        	||CheckPower.checkPower( request.getSession(), "80103" )
            ||CheckPower.checkPower( request.getSession(), "80109" )){
            flag=true;
        }
        if(!flag)
            return mapping.findForward( "powererror" );

        Part_requisitionBean bean = ( Part_requisitionBean )form;
        Part_requisitionBean reqinfo = new Part_requisitionBean(); //����ҳ������뵥������Ϣ
        List reqpartinfo = null;
        PartRequisitionDao dao = new PartRequisitionDao();
        String reid = request.getParameter( "reid" ); //���Ҫ�޸ĵ����뵥id

        try{
            //��õ�ǰ�û��ĵ�λ����
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            //if( userinfo.getType().equals( "12" ) ){ //��������ƶ���˾�ǲ�����鿴���������
            if( userinfo.getDeptype().equals( "1" )
                &&!userinfo.getRegionID().substring(2,6).equals("0000") ){ //��������ƶ���˾�ǲ�����鿴���������
                return forwardErrorPage( mapping, request, "partstockerror" );
            }
            String deptname = dao.getUserDeptName( userinfo );
            request.setAttribute( "deptname", deptname );
            request.setAttribute( "deptid", userinfo.getDeptID() );
            request.setAttribute( "username", userinfo.getUserName() );
            request.setAttribute( "userid", userinfo.getUserID() );

            //������뵥������Ϣ
            reqinfo = dao.getOneReq( reid );
            if( reqinfo.getAudituserid()!=null&&!reqinfo.getAudituserid().equals( "" ) ){
                String sql =
                    "select username from userinfo where userid = '" + reqinfo.getAudituserid()+ "'";
                QueryUtil query = new QueryUtil();
                ResultSet rs = null;
                rs = query.executeQuery( sql );
                while( rs.next() ){
                    reqinfo.setAuditusername( rs.getString( 1 ) );
                }
                logger.info( "�����ˣ�" + reqinfo.getAuditusername() );
            }
            request.setAttribute( "reqinfo", reqinfo );

            if(request.getParameter("querytype")!=null
                &&request.getParameter("querytype").equals("1")){
                //������뵥������Ĳ�����Ϣ
                reqpartinfo = dao.getRequisitionPart( reid, request );
                request.setAttribute( "reqpartinfo", reqpartinfo );
                request.getSession().setAttribute( "type", "showall10" );
            }
            else{
                //������뵥������Ĳ�����Ϣ
                reqpartinfo = dao.getReqPart( reid, request );
                request.setAttribute( "reqpartinfo", reqpartinfo );
                request.getSession().setAttribute( "type", "10" );
            }
            request.getSession().setAttribute("basebean",reqinfo);
            request.getSession().setAttribute("partsbean",reqpartinfo);
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "����ʾ��ϸ�г��ִ���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //ִ��ɾ��һ�����뵥
    public ActionForward delOneReqInfo( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        //Ȩ�޼��
        if( !CheckPower.checkPower( request.getSession(), "80105" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartRequisitionDao dao = new PartRequisitionDao();
            String reid = request.getParameter( "reid" );

            if( dao.valiReqForUp( reid ) ){ //����ܷ�ɾ��,��ɾ��
                dao.deletReqInfo( reid );
                log( request, "���Ϲ���", "ɾ���������뵥" );
                return forwardInfoPage( mapping, request, "80105" );
            }
            else{
                return forwardErrorPage( mapping, request, "80105e" );
            }
        }
        catch( Exception e ){
            logger.error( "��ɾ�����뵥�г���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //�ۺϲ�ѯ��ʾ
    public ActionForward queryShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80103" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            
            PartBaseInfoDao dao = new PartBaseInfoDao();
            PartRequisitionDao reqDao = new PartRequisitionDao();
            //�������������
            List lUser = reqDao.getUserOfReq( contractorid );
            request.setAttribute( "requser", lUser );
            //�������ԭ���б�
            List lReason = reqDao.getReasonOfReq( contractorid );
            request.setAttribute( "reqreason", lReason );
            request.getSession().setAttribute( "type", "3" );
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
        if( !CheckPower.checkPower( request.getSession(), "80103" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            bean.setContractorid( contractorid );
            PartRequisitionDao dao = new PartRequisitionDao();
            
            String[] id = request.getParameterValues( "id" );
            List lReqInfo = dao.doSeatchReq( bean, id );
            request.getSession().setAttribute( "reqinfo", lReqInfo );
            request.getSession().setAttribute( "type", "1" );
            request.getSession().setAttribute( "bean", bean );
            super.setPageReset(request);
            request.setAttribute("queryApply","query");
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "�ۺϲ�ѯִ���쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ӵ��봦��
    public ActionForward upLoadShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80101" ) ){
            return mapping.findForward( "powererror" );
        }

        PartRequisitionDao dao = new PartRequisitionDao();
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ�������Ӳ��������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        String deptname = dao.getUserDeptName( userinfo );
        request.setAttribute( "deptname", deptname );
        request.setAttribute( "deptid", userinfo.getDeptID() );
        request.setAttribute( "username", userinfo.getUserName() );
        request.setAttribute( "userid", userinfo.getUserID() );

        //��÷������ĵ�ǰʱ��
        Date nowDate = new Date();
        DateFormat df = DateFormat.getDateInstance( DateFormat.DATE_FIELD );
        String data = df.format( nowDate );
        request.setAttribute( "date", data );

        //��ò�����Ϣ,�Թ��û�ѡ��
        List lBaseInfo = dao.getAllInfo( userinfo.getRegionID() );
        request.setAttribute( "baseinfo", lBaseInfo );
        //��õ����ļ���Ϣ������ҳ�湩�û��޸�
        Part_requisitionBean bean = ( Part_requisitionBean )form;
        bean.setRegionid( userinfo.getRegionID() );
        String filename = bean.getFile().getFileName();
        if( filename.equals( "" ) || filename == null ){
            return forwardErrorPage( mapping, request, "fileerror" );
        }
        if( !filename.substring( filename.length() - 3, filename.length() ).equals( "xls" ) ){
            return forwardErrorPage( mapping, request, "structerror" );
        }

        String path = servlet.getServletContext().getRealPath( "/upload" );
        Vector vUpInfo = dao.getUpInfo( bean, path );
        if( vUpInfo == null ){
            return forwardErrorPage( mapping, request, "error" );
        }
        //���Ŀ�괦���ˣ����û�ѡ��.
        List ltarget = dao.getTargetman( request );
        request.setAttribute( "ltargetman", ltarget );

        request.setAttribute( "reqpartinfo", vUpInfo );
        request.getSession().setAttribute( "type", "upshow" );
        return mapping.findForward( "success" );
    }

    /**
     * ���뵥ģ������
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward downloadTemplet(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ) {
    	
    	response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String("���뵥ģ��.xls".getBytes(), "iso-8859-1"));
			OutputStream out = response.getOutputStream();

			WatchDetailTemplate template = new WatchDetailTemplate(servlet
					.getServletContext().getRealPath("/upload")
					+ "/temple.xls");
			template.write(out);
	    	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
    }

    // ///////////////////////////////////////////////����

    //��ʾ���д����������뵥
    public ActionForward doAuditShow( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){       
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "2" ) ){ //����Ǵ�ά��λ�ǲ�����������
            return forwardErrorPage( mapping, request, "partauditerror" );
        }
        try{
            PartRequisitionDao dao = new PartRequisitionDao();
            List lReqInfo = dao.getAllReqForAudit( userinfo.getRegionID() );
            request.getSession().setAttribute( "reqinfo", lReqInfo );
            request.getSession().setAttribute( "type", "audit2" );
            super.setPageReset(request);
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "��ʾ�����������뵥�г���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ʾһ�����뵥����ϸ��Ϣ����ʼ����
    public ActionForward doAuditShowOne( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){        

        Part_requisitionBean bean = ( Part_requisitionBean )form;
        Part_requisitionBean reqinfo = new Part_requisitionBean(); //����ҳ������뵥������Ϣ
        List reqpartinfo = null;
        PartRequisitionDao dao = new PartRequisitionDao();
        String reid = request.getParameter( "reid" ); //���Ҫ�޸ĵ����뵥id

        try{
            //��õ�ǰ�û��ĵ�λ����
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            if( userinfo.getDeptype().equals( "2" ) ){ //����Ǵ�ά��λ�ǲ���������
                return forwardErrorPage( mapping, request, "partauditerror" );
            }

            String deptname = dao.getUserDeptName( userinfo ); //���������λ����
            request.setAttribute( "deptname", deptname );
            request.setAttribute( "deptid", userinfo.getDeptID() );
            request.setAttribute( "username", userinfo.getUserName() );
            request.setAttribute( "userid", userinfo.getUserID() );

            //��÷������ĵ�ǰʱ��
            Date nowDate = new Date();
            DateFormat df = DateFormat.getDateInstance( DateFormat.DATE_FIELD );
            String data = df.format( nowDate );
            request.setAttribute( "date", data );

            //������뵥������Ϣ
            reqinfo = dao.getOneReq( reid );
            request.setAttribute( "reqinfo", reqinfo );

            //������뵥������Ĳ�����Ϣ
            reqpartinfo = dao.getReqPartForAudit( reid );
            request.setAttribute( "reqpartinfo", reqpartinfo );
            request.getSession().setAttribute( "type", "audit20" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "����ʾ���������뵥��ϸ��Ϣ�г��ִ���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //ִ������
    public ActionForward auditRequisition( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
       
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "2" ) ){ //����Ǵ�ά��λ�ǲ������
            return forwardErrorPage( mapping, request, "partauditerror" );
        }

        try{
            PartRequisitionDao dao = new PartRequisitionDao();
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            bean.setDeptid( userinfo.getDeptID() );

            String[] id = request.getParameterValues( "id" );
            String[] audnumber = request.getParameterValues( "audnumber" );
            //��д������
            if( !dao.doAddAudit( bean ) ){
                return forwardInfoPage( mapping, request, "error" );
            }
            //���û��ͨ��,������д��Ӧ��,
            if( bean.getAuditresult().equals( "��������" ) ){
                log( request, "���Ϲ���", "�����������뵥,��������" );
                return forwardInfoPage( mapping, request, "80202" );
            }
            //��д�������ϵ�
            if( !dao.doAddReq_PartForAudit( id, audnumber, bean.getReid() ) ){
                return forwardErrorPage( mapping, request, "error" );
            }

            log( request, "���Ϲ���", "�����������뵥,ͨ������" );
            return forwardInfoPage( mapping, request, "80202" );
        }
        catch( Exception e ){
            logger.error( "��ִ�������г���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ʾһ�����뵥����ϸ��Ϣ����ʼ���
    public ActionForward doShowOneForReAudit( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
      

        Part_requisitionBean bean = ( Part_requisitionBean )form;
        Part_requisitionBean reqinfo = new Part_requisitionBean(); //����ҳ������뵥������Ϣ
        List reqpartinfo = null;
        PartRequisitionDao dao = new PartRequisitionDao();
        String reid = request.getParameter( "reid" ); //���Ҫ�޸ĵ����뵥id

        try{
            //��õ�ǰ�û��ĵ�λ����
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            if( userinfo.getDeptype().equals( "2" ) ){ //����Ǵ�ά��λ�ǲ���������
                return forwardErrorPage( mapping, request, "partauditerror" );
            }

            String deptname = dao.getUserDeptName( userinfo ); //���������λ����
            request.setAttribute( "deptname", deptname );
            request.setAttribute( "deptid", userinfo.getDeptID() );
            request.setAttribute( "username", userinfo.getUserName() );
            request.setAttribute( "userid", userinfo.getUserID() );

            //��÷������ĵ�ǰʱ��
            Date nowDate = new Date();
            DateFormat df = DateFormat.getDateInstance( DateFormat.DATE_FIELD );
            String data = df.format( nowDate );
            request.setAttribute( "date", data );

            //������뵥������Ϣ
            reqinfo = dao.getOneReq( reid );
            request.setAttribute( "reqinfo", reqinfo );

            //������뵥������Ĳ�����Ϣ--���
            reqpartinfo = dao.getReqPartForReAudit( reid );
            request.setAttribute( "reqpartinfo", reqpartinfo );
            request.getSession().setAttribute( "type", "audit200" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "����ʾ���������뵥��ϸ��Ϣ�г��ִ���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //ִ�����
    public ActionForward doReAuditRequisition( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
       
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "2" ) ){ //����Ǵ�ά��λ�ǲ������
            return forwardErrorPage( mapping, request, "partauditerror" );
        }
        try{
            PartRequisitionDao dao = new PartRequisitionDao();
            String reid = request.getParameter( "reid" );
            String[] id = request.getParameterValues( "id" );
            //��ʼ�޸����������Ϳ������ƷӦ������
            if( !dao.doReAudit( reid, id ) ){
                return forwardErrorPage( mapping, request, "error" );
            }
            log( request, "���Ϲ���", "�����������뵥,�������" );
            return forwardInfoPage( mapping, request, "802020" );
        }
        catch( Exception e ){
            logger.error( "��ִ�������г���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ʾ����������
    public ActionForward showAllAudit( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80201" )
            ||CheckPower.checkPower( request.getSession(), "80209" )){
            flag=true;
        }
        if(!flag)
            return mapping.findForward( "powererror" );

        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "2" ) ){ //����Ǵ�ά��λ�ǲ������
            return forwardErrorPage( mapping, request, "partauditerror" );
        }

        try{
            PartRequisitionDao dao = new PartRequisitionDao();
            
            if(request.getParameter("querytype")!=null
                &&request.getParameter("querytype").equals("1")){
                List lReqInfo = dao.getAllAuditReq( request );
                request.getSession().setAttribute( "reqinfo", lReqInfo );
                request.getSession().setAttribute( "type", "showaudit1" );
            }else{
                List lReqInfo = dao.getAllAudit( request );
                request.getSession().setAttribute( "reqinfo", lReqInfo );
                request.getSession().setAttribute( "type", "audit1" );
            }
            request.setAttribute( "deptname", request.getSession().getAttribute( "LOGIN_USER_DEPT_NAME" ) );
            request.getSession().setAttribute("querytype","1");
            super.setPageReset(request);
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "��ʾ��������������:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //�鿴������,��ʾһ�����뵥����ϸ��Ϣ,
    public ActionForward doShowOneAudit( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80201" )
            ||CheckPower.checkPower( request.getSession(), "80209" )){
            flag=true;
        }
        if(!flag)
            return mapping.findForward( "powererror" );

        Part_requisitionBean bean = ( Part_requisitionBean )form;
        Part_requisitionBean reqinfo = new Part_requisitionBean(); //����ҳ������뵥������Ϣ
        List reqpartinfo = null;
        PartRequisitionDao dao = new PartRequisitionDao();
        String reid = request.getParameter( "reid" ); //���Ҫ�޸ĵ����뵥id

        try{
            //��õ�ǰ�û��ĵ�λ����
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            if( userinfo.getDeptype().equals( "2" ) ){ //����Ǵ�ά��λ�ǲ���������
                return forwardErrorPage( mapping, request, "partauditerror" );
            }

            String deptname = dao.getUserDeptName( userinfo ); //���������λ����
            request.setAttribute( "deptname", deptname );
            request.setAttribute( "deptid", userinfo.getDeptID() );
            request.setAttribute( "username", userinfo.getUserName() );
            request.setAttribute( "userid", userinfo.getUserID() );

            //��÷������ĵ�ǰʱ��
            Date nowDate = new Date();
            DateFormat df = DateFormat.getDateInstance( DateFormat.DATE_FIELD );
            String data = df.format( nowDate );
            request.setAttribute( "date", data );
            //����������Ļ�����Ϣ
            Part_requisitionBean lAuditInfo = dao.getAuditInfo( reid );
            request.setAttribute( "auditinfo", lAuditInfo );

            //������뵥������Ϣ
            reqinfo = dao.getOneReq( reid );
            request.setAttribute( "reqinfo", reqinfo );

            //������뵥������Ĳ�����Ϣ
            reqpartinfo = dao.getReqPartForAudit( reid );
            request.setAttribute( "reqpartinfo", reqpartinfo );
            request.getSession().setAttribute( "type", "audit10" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "����ʾ���������뵥��ϸ��Ϣ�г��ִ���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //�����ۺϲ�ѯ��ʾ
    public ActionForward queryShowForAudit( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80203" ) ){
            return mapping.findForward( "powererror" );
        }

        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "2" ) ){ //����Ǵ�ά��λ�ǲ������
            return forwardErrorPage( mapping, request, "partauditerror" );
        }

        try{
            PartRequisitionDao dao = new PartRequisitionDao();
            //�������������
            List lUser = dao.getUserOfAudit( userinfo.getDeptID() );
            request.setAttribute( "audituser", lUser );
            //�����������ԭ���б�
            List lReason = dao.getAllReqReason( userinfo.getDeptID() );
            request.setAttribute( "reqreason", lReason );
            //����������뵥λ����
            List lDeptName = dao.getDeptName( userinfo.getDeptID() );
            request.setAttribute( "deptname", lDeptName );
            request.getSession().setAttribute( "type", "audit3" );
            super.setPageReset(request);
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "�ۺϲ�ѯ��ʾ�쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //�����ۺϲ�ѯִ��
    public ActionForward queryExecForAudit( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80203" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "2" ) ){ //
            return forwardErrorPage( mapping, request, "partauditerror" );
        }

        try{
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            PartRequisitionDao dao = new PartRequisitionDao();
            bean.setDeptid( userinfo.getDeptID() );
            List lAuditInfo = dao.getUserOfReq( bean );

            request.getSession().setAttribute( "reqinfo", lAuditInfo );
            request.getSession().setAttribute( "deptname", request.getSession().getAttribute( "LOGIN_USER_DEPT_NAME" ) );
            request.getSession().setAttribute( "type", "audit1" );
            request.getSession().setAttribute( "bean", bean );
            super.setPageReset(request);
            request.setAttribute("audit1","query");
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "�����ۺϲ�ѯִ���쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //ά������������һ�����ѯ��������Excel��
    public ActionForward exportRequisiton( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            Part_requisitionBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( Part_requisitionBean )request.getSession().getAttribute( "bean" );
                logger.info( "��ò�ѯ����bean������" );

                logger.info( "id��" + bean.getAudituserid() );
                logger.info( "����״̬��" + bean.getAuditresult() );
                logger.info( "���뵥λ��" + bean.getcontractorid() );
                logger.info( "����ԭ��" + bean.getReason() );
                logger.info( "��ʼʱ�䣺" + bean.getBegintime() );
                logger.info( "����ʱ�䣺" + bean.getEndtime() );

                String sql =
                    "select u.USERNAME, c.CONTRACTORNAME from userinfo u, contractorinfo c  where u.USERID = '"
                    + bean.getAudituserid() + "' and c.CONTRACTORID = '" + bean.getcontractorid() + "'";
                logger.info( sql );
                QueryUtil query = new QueryUtil();
                ResultSet rs = null;
                rs = query.executeQuery( sql );
                while( rs.next() ){
                    bean.setUsername( rs.getString( 1 ) );
                    bean.setContractorname( rs.getString( 2 ) );
                }
                logger.info( "������������" + bean.getUsername() );
                logger.info( "���뵥λ��" + bean.getContractorname() );

                request.getSession().removeAttribute( "bean" );
            }
            PartExportDao dao = new PartExportDao();
            logger.info( " ����dao" );
            List list = ( List )request.getSession().getAttribute( "reqinfo" );
            logger.info( "�õ�list" );
            dao.ExportRequisition( list, bean, response );
            logger.info( "���excel�ɹ�" );
            return null;
        }
        catch( Exception e ){
            logger.error( "����ά������������һ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportRequisitonResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            Part_requisitionBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( Part_requisitionBean )request.getSession().getAttribute( "bean" );
                logger.info( "��ò�ѯ����bean������" );

                logger.info( "id��" + bean.getUserid() );
                logger.info( "����״̬��" + bean.getAuditresult() );
                logger.info( "����ԭ��" + bean.getReason() );
                logger.info( "��ʼʱ�䣺" + bean.getBegintime() );
                logger.info( "����ʱ�䣺" + bean.getEndtime() );
                if( !bean.getUserid().equals( "" ) ){
                    String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + bean.getUserid() + "'";
                    QueryUtil query = new QueryUtil();
                    ResultSet rs = null;
                    rs = query.executeQuery( sql );
                    while( rs.next() ){
                        bean.setUsername( rs.getString( 1 ) );
                    }
                    logger.info( "������������" + bean.getUsername() );
                    request.getSession().removeAttribute( "bean" );
                }
            }
            PartExportDao dao = new PartExportDao();
            logger.info( " ����dao" );
            List list = ( List )request.getSession().getAttribute( "reqinfo" );
            logger.info( "�õ�list" );
            dao.ExportRequisitionResult( list, bean, response );
            logger.info( "���excel�ɹ�" );

            return null;
        }

        catch( Exception e ){
            logger.error( "������Ϣ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

    /**
     * ��ѯ���в�������ı�
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward queryAllRequisition(ActionMapping mapping, ActionForm form,
        HttpServletRequest request,HttpServletResponse response)
        throws ClientException, Exception{

        if( !CheckPower.checkPower( request.getSession(), "80109" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        //if( !userinfo.getType().equals( "11" ) ){ //
        if(!userinfo.getDeptype().equals("1")&&!userinfo.getRegionID().substring(2,6).equals("0000")){
            return forwardErrorPage( mapping, request, "partauditerror" );
        }

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

        return mapping.findForward("queryallrequisition");
    }

    /**
     * ��ѯ���в��������ı�
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward queryAllAudit(ActionMapping mapping, ActionForm form,
        HttpServletRequest request,HttpServletResponse response)
        throws ClientException, Exception{

        if( !CheckPower.checkPower( request.getSession(), "80209" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        //if( userinfo.getType().equals( "12" ) ){ //��������ƶ���˾�ǲ�����鿴���������
        if( !userinfo.getDeptype().equals( "1" )
            &&!userinfo.getRegionID().substring(2,6).equals("0000") ){ //��������ƶ���˾�ǲ�����鿴���������
            return forwardErrorPage( mapping, request, "partauditerror" );
        }

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

        return mapping.findForward("queryallaudit");
    }

    public ActionForward exportOneRequisition( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            Part_requisitionBean bean = null;
            List partsbean=null;
            if( request.getSession().getAttribute( "basebean" ) != null ){
                bean = ( Part_requisitionBean )request.getSession().getAttribute( "basebean" );
                logger.info( "��ò�ѯ����bean������" );
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
                    logger.info( "��λ���ƣ�" + bean.getContractorname() );
                }
                if( bean.getAudituserid()!=null&&!bean.getAudituserid().equals( "" ) ){
                    String sql =
                        "select username from userinfo where userid = '" + bean.getAudituserid()+ "'";
                    QueryUtil query = new QueryUtil();
                    ResultSet rs = null;
                    rs = query.executeQuery( sql );
                    while( rs.next() ){
                        bean.setAuditusername( rs.getString( 1 ) );
                    }
                    logger.info( "�����ˣ�" + bean.getAuditusername() );
                }
               // request.getSession().removeAttribute("basebean");
            }
            if(request.getSession().getAttribute("partsbean")!=null){
                partsbean=(List)request.getSession().getAttribute("partsbean");
            }
            PartExportDao dao = new PartExportDao();
            logger.info( " ����dao" );
            List list = ( List )request.getSession().getAttribute( "useinfo" );
            logger.info( "�õ�list" );
            dao.ExportRequisition( list, bean, partsbean, response );
            logger.info( "���excel�ɹ�" );

            return null;
        }
        catch( Exception e ){
            logger.error( "��������ʹ��һ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    /**
     * ���ֽڽ�ȡ�ַ���
     * @param str//������ַ���
     * @param endCount//Ҫ��ȡ���ֽ���
     * @param more//���������Ҫ��ȡ���ֽ����������ַ�����׷����ʾ���ַ���
     * @return
     */
    private String subString(String str, int endCount, String more) {
		int resultLength = 0;
		String resultStr = "";
		if(str == null || str.trim().length() == 0) {
			return "";
		} else {
			char[] tempChars = str.toCharArray();
			int index=0;
			while(index < tempChars.length && endCount > resultLength) {
				String s1 = str.valueOf(tempChars[index]);
				byte[] b = s1.getBytes();
				resultLength += b.length;
				resultStr += tempChars[index];
				index++;
			}
		}
		if (endCount == resultLength || (endCount == resultLength - 1)) {
			resultStr += more;
		}
		return resultStr;
	}
}
