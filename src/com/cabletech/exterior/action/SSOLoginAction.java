package com.cabletech.exterior.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.Depart;
import com.cabletech.baseinfo.domainobjects.Region;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.InitDisplayBO;
import com.cabletech.baseinfo.services.RegionBO;
import com.cabletech.baseinfo.services.UserInfoBO;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.web.EncryptDecrypt;
import com.cabletech.menu.domainobjects.FirstMenu;
import com.cabletech.menu.domainobjects.SecondlyMenu;
import com.cabletech.menu.services.MenuService;
import com.cabletech.power.CheckPower;
import com.cabletech.sysmanage.dao.LoginDao;
import com.cabletech.sysmanage.domainobjects.Notice;
import com.cabletech.sysmanage.services.NoticeBo;
import com.cabletech.sysmanage.services.SysDictionary;
import com.cabletech.sysmanage.util.OnLineUsers;
/**
 * SSOLoginAction
 * �ṩ���ۺ�ƽ̨���Ľӿڣ������½
 * @author Administrator
 *
 */
public class SSOLoginAction extends BaseInfoBaseDispatchAction {
	private EncryptDecrypt deCode = new EncryptDecrypt();
	private static Logger logger = Logger.getLogger( "SSOLoginAction" );
	/**
	 * ���ۺ�ƽ̨��½Ѳ��ϵͳ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userID = request.getParameter("u");
		String url = request.getParameter("url");
		String loginflag = request.getParameter("loginflag");//��һ�ε�½���
		String businessType="1";
		//����;
		userID = deCode.Decode(userID);
		// ��֤�ɹ�,��תִ����һ��Baction.java;
    	long start = System.currentTimeMillis();
        LoginDao logindao = new LoginDao();
        UserInfo userInfo = null;
        UserInfoBO bou = new UserInfoBO();
        String Flag = "nodisplay";
        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat( "yyyy-MM-dd" );
        String strDate = df.format( date );
        //�û�������
        if( !logindao.validateUserExist( userID ) ){
            request.setAttribute( "loginerror", "nouser" );
            return mapping.findForward( "errorformward" );
        }
        else{ //���ڸ��û�
            userInfo = super.getService().loadUserInfo( userID );
            //����ʺ��Ƿ����
            if( userInfo.getAccountTerm().before( date ) ){
                request.setAttribute( "loginerror", "accounttermerror" );
                return mapping.findForward( "errorformward" );
            }
            logger.info( userInfo.getNewpsdate() );

            int st = bou.viladateNewPsDate( userInfo );
            long timer = bou.getTimer();
            //logger.info( userInfo.getNewpsdate() + "1 ��ʾ�û���������  2 ����ʧЧ ,��ֹ�û���½  0 ������½  ��ǰֵ��" + st );
            if( st == 2 ){
                request.setAttribute( "loginerror", "perror" );
                return mapping.findForward( "errorformward" );
            }
            if( st == 1 ){
                Flag = "display";
            }
            //����˺�Ϊ��ͣ��
            if( !userInfo.getAccountState().equals( "1" ) ){
                request.setAttribute( "loginerror", "accerror" );
                return mapping.findForward( "errorformward" );
            }

            //������û��Ĳ˵�
            MenuService menuService = new MenuService();
            Vector firstMenu = menuService.getFirstMenu( userInfo,businessType);
            request.getSession().setAttribute( "MENU_FIRSTMENU", firstMenu );
            //���û��Ȩ�� 2005-05-31
            if( firstMenu == null || firstMenu.size() < 1 ){
                request.setAttribute( "loginerror", "powererror" );
                return mapping.findForward( "errorformward" );
            }

            String strFirstMenuID = null;
            if( firstMenu.size() > 0 ){
                FirstMenu menu = ( FirstMenu )firstMenu.elementAt( 0 );
                strFirstMenuID = menu.getId();
            }
            logger.info( "��ʼ��������˵�" );
            HashMap mapSecondlyMenu = new HashMap();
            Vector secondlyMenu = menuService.getSecondlyMenu( strFirstMenuID, userInfo );
            mapSecondlyMenu.put( strFirstMenuID, secondlyMenu );
            request.getSession().setAttribute( "MENU_SECONDLYMENU", mapSecondlyMenu );

            logger.info( "��ʼ���������˵�" );
            String strThirdMenuID = null;
            if( firstMenu.size() > 0 ){
                SecondlyMenu menu = ( SecondlyMenu )secondlyMenu.elementAt( 0 );
                strThirdMenuID = menu.getId();
            }
            HashMap mapThirdMenu = new HashMap();
            Vector thirdMenu = menuService.getThirdMenu( strThirdMenuID,
                               userInfo );
            mapThirdMenu.put( strThirdMenuID, thirdMenu );
            request.getSession().setAttribute( "MENU_THIRDMENU", mapThirdMenu );

            //�����½�ɹ�,ת���û���Ϣ
            request.getSession().setMaxInactiveInterval( 7200 ); //Sesion��Чʱ��������Ϊ��λ 7200
            if(userInfo.getRegionID().substring(2).equals("0000")&& !userInfo.getRegionid().startsWith("11"))
                   userInfo.setType(userInfo.getDeptype() + "1");
            else
                userInfo.setType(userInfo.getDeptype() + "2");
            logger.info("UserType:" + userInfo.getType());

            String regionId=userInfo.getRegionid();
            String regionName=new RegionBO().loadRegion(regionId).getRegionName();
            HashMap map=new HashMap();
            map.put(regionId,regionName);
            request.getSession().setAttribute("regionInfo",map);

            request.getSession().setAttribute( "LOGIN_USER", userInfo );
            request.getSession().setAttribute( "USERCURRENTPOWER", CheckPower.getMoudules( userInfo) ); //ysj add
            String userType = userInfo.getDeptype();
            //zhj
            if( Flag.equals( "display" ) ){
                String enddate = DateUtil.DateToTimeString( new Date( userInfo.getNewpsdate().getTime()
                                 + timer ) );
                request.getSession().setAttribute( "display", Flag ); //������ʾ���������ʾ
                request.getSession().setAttribute( "enddate", enddate.substring( 0, 10 ) );
            }
            //ZSH ȡ�õ�������
            Region region = super.getService().loadRegion( userInfo.getRegionid() );
            request.getSession().setAttribute( "LOGIN_USER_REGION_NAME", region.getRegionName() );

            if( userType.equals( "2" ) ){ //��Ϊ��λ
                Contractor contractor = super.getService().loadContractor( userInfo.getDeptID() );
                request.getSession().setAttribute( "LOGIN_USER_DEPT_NAME",
                    contractor.getContractorName() );
                request.getSession().setAttribute( "LOGIN_USER_DEPT_ID", contractor.getContractorID() );
            }
            else{ //�ڲ�����
                Depart depart = super.getService().loadDepart( userInfo.getDeptID() );
                request.getSession().setAttribute( "LOGIN_USER_DEPT_NAME", depart.getDeptName() );
            }
            RegionBO rbo = new RegionBO();

            request.getSession().setAttribute( "REGION_ROOT", rbo.getRegionID() );
            log( request, " ��½ϵͳ ", "��½ϵͳ " );
            OnLineUsers online = OnLineUsers.getInstance();
            String loginIp = request.getRemoteAddr();

            if( !online.existUser( userInfo.getUserID() ) ){
                request.getSession().setAttribute( userInfo.getUserID(), online );
                logger.info( "��ӭ���û�: " + userInfo.getUserID() + " ��½��ϵͳ��" );
            }

            Date nowDate = new Date();
            String nowDateStr = DateUtil.DateToTimeString( nowDate );
            String sql =
                "update USERINFO set LASTLOGINTIME = to_date('" +
                nowDateStr + "','yy/mm/dd hh24:mi'), LASTLOGINIP = '" + loginIp +
                "'  where USERID = '" +
                userInfo.getUserID() + "'";
            UpdateUtil updateU = new UpdateUtil();
            updateU.executeUpdate( sql );
            //�ж��Ƿ��ǵ�һ�ε�¼����¼ʱ��д�����ݿ�
            if( loginflag == null  ){
                SimpleDateFormat dtFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
                String strDt = dtFormat.format( nowDate );


                OracleIDImpl ora = new OracleIDImpl();
                String uid = ora.getSeq( "useronlinetime", 16 );
                logger.info( "�������id" + uid );
                request.getSession().setAttribute( "userkeyid", uid );
                String sql2 =
                    "insert into useronlinetime ul (ul.KEYID, ul.USERID, ul.LOGINIP, ul.LOGINTIME) values ( " +
                    "'" + uid + "', " + "'" + userInfo.getUserID() + "','" + loginIp + "'," + "to_date('"
                    + strDt + "','YYYY-MM-DD HH24:MI:SS'))";
                updateU.executeUpdate( sql2 );
            }
            
            //����tabmenu�˵�
//            SetupDesktopBO sdbo= new SetupDesktopBO();
//            request.getSession().setAttribute("tabmenu",sdbo.toLoadTabMenu(userInfo));
//            String tabmm = sdbo.getTabMenuModule(userInfo);
            //������ʾ��Ϣ
            NoticeBo nbo = new NoticeBo();
            Notice notice = nbo.Message(rbo.getRegionID(), userInfo);
            request.getSession().setAttribute("notice", notice);
            //���س�ʼ��Ϣ
            InitDisplayBO initbo = new InitDisplayBO();
            //ȡ�ù����б�
    		String noticestr = initbo.getNoticeInfo(rbo.getRegionID(),userInfo.getRegionID(),"");
    		//Ѳ����Ա�б�
    		String onlineman = initbo.getPatrolManStr(userInfo);
    		if(noticestr == null)
    			noticestr = "";
    		request.getSession().setAttribute("noticeli", noticestr);
    		request.getSession().setAttribute("onlineman", onlineman);
    		//��������
//    		request.getSession().setAttribute("favstr",sdbo.getFavStr(userInfo));
//    		request.getSession().setAttribute("nonfavstr",sdbo.getNonFavStr(userInfo));
//    		if(tabmm.indexOf("002") != -1){
//    		//���ؼƻ���Ϣ
//    		InitDisplayPlanBO idpbo = new InitDisplayPlanBO();
//    		request.getSession().setAttribute("FulfillPlan",idpbo.getFulfillPlan(userInfo));
//    		request.getSession().setAttribute("ProgressPlan",idpbo.getProgressPlan(userInfo));
//    		request.getSession().setAttribute("ShallStartPlan",idpbo.getShallStartPlan(userInfo));
//    		}
            //�ж�Ѳ����Ա�ǰ��黹�ǰ��˽��й���
    		SysDictionary service = new SysDictionary();
            if( service.isManageByArry().equals( "1" ) ){
                request.getSession().setAttribute( "PMType", "group" );
            }
            else{
                request.getSession().setAttribute( "PMType", "notGroup" );
            }

            //�ж��Ƿ���ʾ������Ϣ
            if( service.isShowFIB().equals( "1" ) ){
                request.getSession().setAttribute( "ShowFIB", "show" );
            }
            else{
                request.getSession().setAttribute( "ShowFIB", "noShow" );
            }

            //�ж��Ƿ��Ͷ���
            if( service.isSendSM().equals( "1" ) ){
                request.getSession().setAttribute( "isSendSm", "send" );
            }
            else{
                request.getSession().setAttribute( "isSendSm", "nosend" );
            }
            long end = System.currentTimeMillis();
            long total=(end - start)/1000;
            log.info("����ʱ�䣺"+total/60+"��"+total%60+"��");
            return mapping.findForward( "loginformward" );
        }
	}
}
