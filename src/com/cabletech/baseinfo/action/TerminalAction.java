package com.cabletech.baseinfo.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import cabletech.business.service.RmiTerminalService;
import cabletech.sm.message.SmSubmitRespMessage;
import cabletech.sm.rmi.RmiSmProxyService;

import com.cabletech.baseinfo.beans.TerminalBean;
import com.cabletech.baseinfo.domainobjects.Terminal;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.commons.sqlbuild.QuerySqlBuild;
import com.cabletech.commons.web.ClientException;
import com.cabletech.linepatrol.maintenance.beans.TestPlanBean;
import com.cabletech.linepatrol.maintenance.service.TestPlanBO;
import com.cabletech.utils.SMSkit.SmsUtil;

public class TerminalAction extends BaseInfoBaseDispatchAction{

	private static Logger logger =
		Logger.getLogger( TerminalAction.class.getName() );

	public TerminalAction(){
	}


	/**
	 * �����豸
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ClientException
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward addTerminal( ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{

		TerminalBean bean = ( TerminalBean )form;

		Terminal data = new Terminal();
		BeanUtil.objectCopy( bean, data );
		data.setTerminalID( super.getDbService().getSeq( "terminalinfo", 20 ) );
		String regionid = ((UserInfo)request.getSession().getAttribute("LOGIN_USER")).getRegionid();

		if(data.getTerminalModel().indexOf("CT") != -1){
			//data.setTerminalID(data.getContractorID()+"00"+regionid.substring(0,4) +""+data.getTerminalID());
			data.setState("");
		}else
			//data.setTerminalID(data.getContractorID()+"00"+data.getTerminalID());
			logger.info("ϵͳID ��"+data.getTerminalID()+" �豸id"+data.getDeviceID()+"state:"+ data.getState());
		//�ж�ID��Ѳ��Ա�Ƿ�ռ��
		if( super.getService().isIdOccupied( data ) != 0 ){
			String errmsg = "ָ���豸����Ѿ���ռ�ã��������������������������������ԣ�";
			request.setAttribute( "errmsg", errmsg );

			return mapping.findForward( "commonerror" );

		}

		//�жϺ�����Ѳ��Ա�Ƿ�ռ��
		if( super.getService().isNumberOccupied( data ) != 0 ){

			String errmsg = "ָ��SIM�����Ѿ���ռ�ã��������������������������������ԣ�";
			request.setAttribute( "errmsg", errmsg );

			return mapping.findForward( "commonerror" );

		}
		data.setRegionID( super.getLoginUserInfo( request ).getRegionid() );
		super.getService().createTerminal( data );
		
		//Log
		log( request, " ����Ѳ���ն���Ϣ���豸���Ϊ��"+bean.getDeviceID()+"�� ", " �������Ϲ��� " );
		
		

		//2005 / 08 / 30
//		refreshSMSCache( request );

		return forwardInfoPage( mapping, request, "0141" );
	}


	public ActionForward loadTerminal( ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{

		Terminal data = super.getService().loadTerminal( request.getParameter(
		"id" ) );
		TerminalBean bean = new TerminalBean();
		BeanUtil.objectCopy( data, bean );
		request.setAttribute( "terminalBean", bean );
		request.getSession().setAttribute( "terminalBean", bean );
		return mapping.findForward( "updateTerminal" );
	}


	public ActionForward queryTerminal( ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{
		TerminalBean bean = (TerminalBean)form;
		request.getSession().setAttribute("S_BACK_URL",null); //
		request.getSession().setAttribute("theQueryBean", bean);//
		UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		String type = userInfo.getDeptype();
		String regionid = userInfo.getRegionID();
		String deptid = userInfo.getDeptID();
		String terminalid = "";
		terminalid = ( ( TerminalBean )form ).getTerminalID();
		if( terminalid == null ){
			terminalid = "";
		}
		//ע�������˳���������ֶ�
		String sql = "select a.holder,a.deviceid,a.password password, a.terminalID, a.SIMNUMBER sim, decode(a.STATE,'00','������·','01','�ճ�Ѳ��','','--------') state, " +
		"decode(a.terminalType,'1','�ֳ�','2','����','3','PDA','4','�ֻ�','�ֳ�') terminalType," +
		" a.produceMan, a.terminalModel, c.regionname regionID, b.contractorname contractorid, d.patrolname ownerID" +
		" from terminalinfo a, contractorinfo b, region c, patrolmaninfo d";

		QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );

		sqlBuild.addConstant( "a.ownerid = d.patrolid(+)" );
		sqlBuild.addAnd();
		sqlBuild.addConstant( "a.regionid = c.regionid(+)" );
		sqlBuild.addAnd();
		sqlBuild.addConstant( "a.contractorid = b.contractorid(+)" );

		sqlBuild.addConditionAndLike( "a.deviceid like {0}",terminalid);
		
		sqlBuild.addConditionAnd( "a.ownerid = {0}",
				( ( TerminalBean )form ).getOwnerID() );
		sqlBuild.addConditionAnd( "a.contractorid = {0}",
				( ( TerminalBean )form ).getContractorID() );
		sqlBuild.addConditionAnd( "a.terminaltype = {0}",
				( ( TerminalBean )form ).getTerminalType() );
		sqlBuild.addConditionAndLike( "a.produceman like {0}",( ( TerminalBean )form ).getProduceMan() );
		if(( ( TerminalBean )form ).getTerminalModel() != null && !( ( TerminalBean )form ).getTerminalModel().equals("")){
			sqlBuild.addConditionAndLike( "a.terminalmodel like {0}",
					( ( TerminalBean )form ).getTerminalModel() );
		}
		sqlBuild.addConditionAnd( "a.simtype = {0}",
				( ( TerminalBean )form ).getSimType() );
		sqlBuild.addConditionAndLike( "a.simnumber like {0}",( ( TerminalBean )form ).getSimNumber());
		sqlBuild.addConstant( "  and (a.state  <> '1' or a.state is null)  " );
		sqlBuild.addAnd();
		sqlBuild.addConstant( "c.regionid in (SELECT regionid "
				+ "FROM region "
				+ "START WITH regionid = '" + regionid + "' "
				+ " CONNECT BY PRIOR  regionid= parentregionid) " );
		if( "2".equals( type ) ){
			sqlBuild.addAnd();
			sqlBuild.addConstant( "b.contractorid='" + deptid + "'" );
		}
		sqlBuild.addOther(" order by a.contractorid,a.deviceid");
		sql = sqlBuild.toSql();
		//sql = sql + "  order by a.contractorid,a.deviceid";
		logger.warn("sql "+sql);
		List list = super.getDbService().queryBeans( sql );
		request.getSession().setAttribute( "queryresult", list );
		super.setPageReset(request);
		return mapping.findForward( "queryterminalresult" );
	}


	public ActionForward updateTerminal( ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{

		Terminal data = new Terminal();
		//add by steven 2007-07-13
		if (((TerminalBean)form).getTerminalModel().indexOf("CT") != -1){
			((TerminalBean)form).setState(null);
		}
		//add by steven 2007-07-13
		TerminalBean bean=(TerminalBean)form;
		BeanUtil.objectCopy( bean, data );

		String[][] oldValueArr = super.getService().getOldValues( data );
		String oldSimNumber = oldValueArr[0][0];
		String oldPatrolId = oldValueArr[0][1];

		//�жϺ�����Ѳ��Ա�Ƿ�ռ��
		if( super.getService().isNumberOccupied4Edit( data, oldSimNumber ) != 0 ){

			String errmsg = "ָ��SIM�����Ѿ���ռ�ã��������������������������������ԣ�";
			request.setAttribute( "errmsg", errmsg );

			return mapping.findForward( "commonerror" );

		}
		
		//�ж�ID��Ѳ��Ա�Ƿ�ռ��
		if( super.getService().isIdOccupied4Edit( data ) != 0 ){
			String errmsg = "ָ���豸����Ѿ���ռ�ã��������������������������������ԣ�";
			request.setAttribute( "errmsg", errmsg );

			return mapping.findForward( "commonerror" );

		}

		/*if (super.getService().isPatrolmanOccupied4Edit(data, oldPatrolId) != 0) {

			String errmsg = "ָ��Ѳ��Ա�Ѿ����������豸�����������Ѳ��Ա�������������������ԣ�";
			request.setAttribute("errmsg", errmsg);

			return mapping.findForward("commonerror");

		}*/

		super.getService().updateTerminal( data );
		//bjˢ�»���
		WebApplicationContext ctx = getWebApplicationContext();
		RmiTerminalService terminalService = (RmiTerminalService)ctx.getBean("rmiTerminalService");
		terminalService.clearTerminalCache(data.getTerminalID());
		
		log( request, " ����Ѳ���ն���Ϣ���豸���Ϊ��"+bean.getDeviceID()+"�� ", " �������Ϲ��� " );
		//2005 / 08 / 30
//		refreshSMSCache( request );
		String strQueryString = getTotalQueryString("method=queryTerminal&produceMan=&terminalModel=&simNumber=",(TerminalBean)request.getSession().getAttribute("theQueryBean"));
		Object[] args = getURLArgs("/WebApp/terminalAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
		return forwardInfoPage( mapping, request, "0142",null,args);
	}


	public ActionForward deleteTerminal( ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{
		String id = request.getParameter( "id" );
//		String idd= id.substring(0,10) + "11" + id.substring(12,id.length());
		String sqld = "delete from terminalinfo where terminalid='" + id+ "'";
//		String sql = "UPDATE terminalinfo"
//			+ " SET state = '1',"
//			+ "     simnumber = (SELECT CONCAT('0',SUBSTR (simnumber, 2, length(simnumber)))"
//			+ "                  FROM terminalinfo"
//			+ "                  WHERE terminalid = '" + id + "'),"
//			+ "     terminalid =(select CONCAT(CONCAT(SUBSTR(terminalid,1,10),'11' ),SUBSTR(terminalid,13,length(terminalid)))  "
//			+ "                 from terminalinfo"
//			+ "                 where terminalid = '" + id + "')"
//			+ " WHERE terminalid = '" +  id + "'";
		logger.info("SQL:" + sqld);
		try{
			UpdateUtil exec = new 	UpdateUtil();
			exec.executeUpdate(sqld);
			exec.commit();
			//bjˢ�»���
			WebApplicationContext ctx = getWebApplicationContext();
			RmiTerminalService terminalService = (RmiTerminalService)ctx.getBean("rmiTerminalService");
			terminalService.clearTerminalCache(id);
			log( request, " ɾ��Ѳ���ն���Ϣ ", " �������Ϲ��� " );
			String strQueryString = getTotalQueryString("method=queryTerminal&produceMan=&terminalModel=&simNumber=",(TerminalBean)request.getSession().getAttribute("theQueryBean"));
			Object[] args = getURLArgs("/WebApp/terminalAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
			return forwardInfoPage( mapping, request, "0145",null,args);
		}
		catch( Exception e ){
			logger.warn( "ɾ��Ѳ���ն���Ϣ�쳣:" + e.getMessage() );
			return forwardErrorPage( mapping, request, "error" );
		}
	}

	public String getTotalQueryString(String queryString,TerminalBean bean){
		if (bean.getSimNumber() != null){
			queryString = queryString + bean.getSimNumber();
		}
		if (bean.getTerminalModel()!= null){
			queryString = queryString + "&terminalModel=" + bean.getTerminalModel();
		}
		if (bean.getProduceMan() != null){
			queryString = queryString + "&produceMan=" + bean.getProduceMan();
		}
		if (bean.getId() != null){
			queryString = queryString + "&id=" + bean.getId();
		}
		if (bean.getName() != null){
			queryString = queryString + "&name=" + bean.getName();
		}
		return queryString;
	}


	/**
	 * ��ʼ���豸
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ClientException
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward initTerminal( ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{
		String simId = request.getParameter("sid");
		String deviceId = request.getParameter("did");
//		List list = new Vector();
//		String serverid = ( String )request.getParameter( "serverid" );

		if( request.getSession().getAttribute( "queryresult" ) != null ){
			////System.out.println("���  : ���Գ�ʼ����");
//			list = ( List )request.getSession().getAttribute( "queryresult" );
			////System.out.println("��¼�� �� " + list.size());
//			super.getService().initTerminal( list, serverid );
			//������ʼ���豸
			WebApplicationContext ctx = getWebApplicationContext();
			RmiSmProxyService smSendProxy=(RmiSmProxyService)ctx.getBean("rmiSmProxyService");
			SmSubmitRespMessage resp =smSendProxy.initializeTerminal(simId,deviceId);
			if(resp.getResult()==0){
				logger.info("���ͳɹ�:sim���ţ�"+simId +" �豸��ţ�"+deviceId);
				logger.info("���͵���Ϣд����SM_MT������Ϊ��"+resp.getSubmitDbId());
				logger.info("���͵���Ϣ�Ľ������д����SM_RPT��SM_PRT.SM_MT=SM_MT.KEYID");
				log( request, " ��ʼ��Ѳ���ն���Ϣ ", " �������Ϲ��� " );
				outPrint(response,"��ʼ���ɹ���");
			}else{
				log( request, " ��ʼ��Ѳ���ն���Ϣ����ʼ��ʧ�� ", " �������Ϲ��� " );
				outPrint(response,"��ʼ��ʧ�ܣ�����ԭ��Ϊ���޷������ӵ��������أ����������ϵ");
			}
			//Log
			
		}else{
			log( request, " ��ʼ��Ѳ���ն���Ϣ����ʼ��ʧ�� ", " �������Ϲ��� " );
			outPrint(response,"��ʼ��ʧ�ܣ�");
		}
		
		return null;
		//return forwardInfoPage( mapping, request, "0143" );
	}


	//ͨѶ¼��������ʾͨѶ¼
	public ActionForward showPhoneBook( ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{
		UserInfo userinfo = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
		List lPhoneBook = super.getService().getPhoneBook(userinfo);
		List lSimNumber = super.getService().getSimNumber(userinfo);

		if(lPhoneBook == null){
			logger.info("�绰���ǿ�");
		}
		request.setAttribute("lPhoneBook",lPhoneBook);
		request.setAttribute("lSimNumber",lSimNumber);
		return mapping.findForward( "showPhoneBook" );
	}

	// addUpPhoneBook
	//ͨѶ¼����������ͨѶ¼
	public ActionForward addUpPhoneBook(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws ClientException, Exception{

		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		TerminalBean bean = (TerminalBean)form;
		String[] simnumber = request.getParameterValues("simnumber");
		String[] name = request.getParameterValues("name");
		String[] phone = request.getParameterValues("phone");
		if(name == null){
			if(!(super.getService().addUpPhoneBook(bean,simnumber,name,phone,userinfo))){
				return forwardErrorPage( mapping, request, "70808e2" );
			}else{
				return  forwardInfoPage( mapping, request, "70808" );
			}
		}
		if(name.length != phone.length){
			return forwardErrorPage( mapping, request, "70808e1" );
		}
		for(int i=0;name != null && i<name.length;i++){
			if(name[i]==null || name[i].equals("") || phone[i] == null || phone[i].equals(""))
				return forwardErrorPage( mapping, request, "70808e3" );
		}

		if(!(super.getService().addUpPhoneBook(bean,simnumber,name,phone,userinfo))){
			return forwardErrorPage( mapping, request, "70808e2" );
		}else{
			return  forwardInfoPage( mapping, request, "70808" );
		}
	}



	/**
	 * ���û���ӿ�
	 * @param request HttpServletRequest
	 * @throws Exception
	 * 
	 */
	
	public void refreshSMSCache( HttpServletRequest request ) throws Exception{

		try{
			String[] infoArr = new String[1];
			infoArr[0] = super.getLoginUserInfo( request ).getUserID();
			SmsUtil smsutil = SmsUtil.getInstance();
			smsutil.refreshCache( infoArr );
		}
		catch( Exception e ){
			//System.out.println( "ˢ�»����쳣 ��" + e.toString() );
		}

	}


	public ActionForward exportTerminalResult( ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ){
		try{
			logger.info( " ����dao" );
			List list = ( List )request.getSession().getAttribute( "queryresult" );
			logger.info( "�õ�list" );
			super.getService().exportTerminalResult( list, response );
			logger.info( "���excel�ɹ�" );
			return null;
		}
		catch( Exception e ){
			logger.error( "������Ϣ��������쳣:" + e.getMessage() );
			return forwardErrorPage( mapping, request, "error" );
		}
	}
	/**
	 * ��ʾǷѹ�豸
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showVoltage( ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ){
		UserInfo userinfo = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
		try {
			//ȡ�����豸
			List newTerminal = super.getService().getNewTerminal(userinfo);
			//��ȡǷѹ�豸
			List lowVoltage = super.getService().getLowVoltage(userinfo);
			request.setAttribute( "newTerminal", newTerminal );
			request.getSession().setAttribute( "lowVoltage", lowVoltage );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapping.findForward( "showVoltage" );

	}
	/**
	 * ���ö��Žӿڣ����͵�ѹ��ѯָ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryVoltage(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String [] simNumber = request.getParameterValues("simNumber");
		UserInfo userinfo = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
		for(int i=0;i<simNumber.length;i++){
			logger.info("simNumber:" +simNumber[i]);
			com.cabletech.commons.sm.SendSMRMI.searchV(userinfo, simNumber);
		}

		return null;
	}
	/**
	 * �鿴�ѷ��ص��豸��ѹ��ѯ�����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryLowVoltage (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd" );
		String strDate = formatter.format( new java.util.Date());
		starttime = strDate+" "+starttime;
		endtime = strDate+" "+endtime;
		logger.info("Date "+strDate);
		String sql = "select deviceid,simid simnumber,queryman,to_char(querytime,'mm-dd hh24:mi') querytime,to_char(respvoltage),to_char(resptime,'yy-mm-dd hh24:mi') resptime from query_voltage where querytime>to_date('"+starttime+"','YYYY-MM-DD HH24:MI:ss') and querytime<to_date('"+endtime+"','YYYY-MM-DD HH24:MI:ss')";
		logger.info("sql = "+sql);
		List list = null;
		try {
			list = super.getDbService().queryBeans( sql );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getSession().setAttribute( "terminalVoltage", list );
		return mapping.findForward( "terminalvoltage" );
	}
	
	/**
	 * ���ݴ�άid��ѯѲ����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPatrolGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/json; charset=GBK");
		String contractorID = request.getParameter("contractorID");
		List groups = super.getService().getPatrolGroup(contractorID);
		JSONArray ja = JSONArray.fromObject(groups);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(ja.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				out.close();
			}
		}
		return null;
	}
	
	
	
	
}

//
