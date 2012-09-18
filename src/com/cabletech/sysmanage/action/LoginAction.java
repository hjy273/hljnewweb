package com.cabletech.sysmanage.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.cabletech.baseinfo.action.*;
import com.cabletech.baseinfo.beans.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.baseinfo.services.*;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.md5.*;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.web.*;
import com.cabletech.menu.domainobjects.*;
import com.cabletech.menu.services.*;
import com.cabletech.power.*;
import com.cabletech.sysmanage.dao.*;
import com.cabletech.sysmanage.domainobjects.Notice;
import com.cabletech.sysmanage.services.*;
import com.cabletech.sysmanage.util.*;
import com.cabletech.utils.*;
import org.apache.log4j.Logger;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import java.text.SimpleDateFormat;

public class LoginAction extends BaseInfoBaseDispatchAction{
    private static Logger logger = Logger.getLogger( "LoginAction" );

    public ActionForward login( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
    	long start = System.currentTimeMillis();
        LoginDao logindao = new LoginDao();
        String userID = request.getParameter( "userid" );
        String password = request.getParameter( "password" );
        String loginflag = request.getParameter("loginflag");
        request.getSession().setAttribute("password",password);
        String businessType = "1";
        UserInfo userInfo = null;
        UserInfoBO bou = new UserInfoBO();
        String Flag = "nodisplay";
        String password1 = MD5.encode( password, 0 ); //�������
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
            if( userInfo.getPassword().length() == 32 ){
                if( !userInfo.getPassword().equals( password1 ) ){
                    request.setAttribute( "loginerror", "passerror" );
                    return mapping.findForward( "errorformward" );
                }
            }
            else{
                if( !userInfo.getPassword().equals( password ) ){
                    request.setAttribute( "loginerror", "passerror" );
                    return mapping.findForward( "errorformward" );
                }
            }
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
            request.getSession().setMaxInactiveInterval( 1800 ); //Sesion��Чʱ��������Ϊ��λ 30����
            if(userInfo.getRegionID().substring(2).equals("0000") && !userInfo.getRegionid().startsWith("11"))
                   userInfo.setType(userInfo.getDeptype() + "1");
            else
                userInfo.setType(userInfo.getDeptype() + "2");
            logger.info("UserType:" + userInfo.getType());

            String deptId=userInfo.getDeptID();
            if("1".equals(userInfo.getDeptype())){
                String deptName=new DepartBO().loadDepart(deptId).getDeptName();
                userInfo.setDeptName(deptName);
            }
            if("2".equals(userInfo.getDeptype()) || "3".equals(userInfo.getDeptype())){
                String deptName=new ContractorBO().loadContractor(deptId).getContractorName();
                userInfo.setDeptName(deptName);
            }
            
            String regionId=userInfo.getRegionid();
            String regionName=new RegionBO().loadRegion(regionId).getRegionName();
            userInfo.setRegionName(regionName);
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

            if( userType.equals( "2" ) || userType.equals( "3" ) ){ //��Ϊ��λ
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
    		//���ؼƻ���Ϣ
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

    public ActionForward reloginNoSession( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        UserInfo userinfo  = (UserInfo)request.getSession().getAttribute( "LOGIN_USER");
        String password = (String)request.getSession().getAttribute("password") ;
        String loginflag = "1";
        //System.out.println( "UserInfo.id:" + userinfo.getUserID()+ "::" + password );
        response.sendRedirect("/WebApp/login.do?method=login&userid=" + userinfo.getUserID() + "&password=" + password +"&loginflag=" + loginflag );
        return null;
    }

    public ActionForward mainweb(ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response){
        LoginDao dao = new LoginDao();
        String sVersion = "";
        //sVersion = "V" + dao.getVersion(request.getRealPath(""));
        request.setAttribute("version",sVersion);
        return mapping.findForward( "mainweb" );
    }



    public ActionForward logout( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        LoginDao dao = new LoginDao();
        if(dao.logout( ( String )request.getSession().getAttribute( "userkeyid" )))
            log.info("===========�û��˳�ϵͳ������ϣ�=============");
        else
            log.info("===========�û��˳�ϵͳ����ʧ�ܣ�=============");
        return null;

    }

    public ActionForward relogin( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        //ϵͳ�ǳ������µ�½��ж�ص�ǰSESSION�е����ж��󣬲��ҷ��ص���½��

        HttpSession session = request.getSession();
        ArrayList objLists = new ArrayList();

        String uid = ( String )request.getSession().getAttribute( "userkeyid" );
        LoginDao dao = new LoginDao();
        dao.logout(uid);
        /**
        Date nowDate = new Date();
        SimpleDateFormat dtFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        String strDt = dtFormat.format( nowDate );
        String sql =
        "update useronlinetime set logouttime = to_date('" +
        strDt + "','yy/mm/dd hh24:mi:ss')  , onlinetime =  to_number(to_date('" +
        strDt + "','yy/mm/dd hh24:mi:ss') - LOGINTIME)*24*60*60 "
            +" where keyid = '" + uid + "'";
        //System.out.println( "�˳�ϵͳ" + sql );
        UpdateUtil updateU = new UpdateUtil();
        updateU.executeUpdate( sql );
        */
        if( session != null ){
            Enumeration senum = session.getAttributeNames();
            while( senum.hasMoreElements() ){
                //����SESSION�еĶ���
                String obj = senum.nextElement().toString();
                logger.info( "Session Object Removed:  " + obj );
                super.removeSessionAttribute( request, obj ); //ж�ض���
                objLists.add( obj );
            }
            session.invalidate();
        }
        return mapping.findForward( "relogin" );
    }


    public ActionForward loadUserpsw( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        UserInfo data = super.getService().loadUserInfo( request.getParameter(
                        "id" ) );
        UserInfoBean bean = new UserInfoBean();
        BeanUtil.objectCopy( data, bean );
        request.setAttribute( "userInfoBean", bean );
        //request.getSession().setAttribute("userinfoBean", bean);
        //logger.info("����");
        return mapping.findForward( "updatePassword" );
    }


    public ActionForward updatePsw( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        UserInfo data = new UserInfo();
        UserInfoBO uibo = new UserInfoBO();
        //String userid = ((UserInfoBean)request.getSession().getAttribute("LOGIN_USER")).getUserID();
        UserInfoBean uiBean = ( UserInfoBean )form;
        String op = uiBean.getOldpassword();
        String np = uiBean.getNewpassword();
        String ap = uiBean.getAffirmpassword();

        UserInfo user = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        //data = uibo.loadUserInfo( request.getParameter( "userID" ) ); //��ȡuserinfo����
        data = uibo.loadUserInfo( user.getUserID() ); //��ȡuserinfo����
        logger.info( "op " + op );
        if( op != null && !op.equals( "" ) ){
        	if( data.getPassword().length() == 32 ){
        		 op = MD5.encode( op, 0 );
        	}
            logger.info( "test " + request.getParameter( "userID" ) );
            logger.info( op + " = " + data.getPassword() );
            //if( op.equals( user.getPassword() ) ){
            if( op.equals( data.getPassword() ) ){
                if( np != null && ap != null && ap.equals( np ) ){
                    UserInfo userinfo = new UserInfo();
                    BeanUtil.objectCopy( data, userinfo );
                    userinfo.setPassword( np );
                    if( uibo.validatePsRepeat( userinfo ) ){
                        data.setPassword( np );
                        data.setOldps( uibo.getNewOldps( data ) ); //��������
                        np = MD5.encode( np, 0 );
                        data.setPassword( np );
                        data.setNewpsdate( new Date() );
                        super.getService().setingPassword( data );
                        logger.info( "data" + data.toString() );
                        log(request,"�޸�����","ϵͳ����");
                        return forwardInfoPage( mapping, request, "1081" );
                    }
                    else{
                        userinfo = null;
                        return forwardInfoPage( mapping, request, "1082" ); //������ǰ�����Ѿ��ù�.
                    }
                }
                else{
                    return forwardInfoPage( mapping, request, "1083" ); //�������벻һ��
                }
            }
            else{
                return forwardInfoPage( mapping, request, "1084" ); //ԭʼ���벻��ȷ
            }
            // return forwardInfoPage( mapping, request, "1084" );
        }
        else{
            return forwardInfoPage( mapping, request, "1085" ); //���벻��Ϊ��
        }
    }

    public ActionForward superLogin(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response)throws Exception{

        LoginDao logindao = new LoginDao();
       String userID = request.getParameter( "userid" );
       String password = request.getParameter( "password" );
       String regionid = request.getParameter("regionid");
       String depttype = request.getParameter("deptype");
       String contractorid = request.getParameter("deptID");
       String businessType = "1";
       UserInfo userInfo = new UserInfo();
       UserInfoBO bou = new UserInfoBO();

       //�û�������
       if( !logindao.validateUserExist( userID ) ){
           request.setAttribute( "loginerror", "nouser" );
           return mapping.findForward( "errorformward" );
       }
       else{ //���ڸ��û�

           //������û��Ĳ˵�
           MenuService menuService = new MenuService();
           Vector firstMenu = menuService.getFirstMenu( userInfo,businessType );
           request.getSession().setAttribute( "MENU_FIRSTMENU", firstMenu );

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
           request.getSession().setAttribute( "LOGIN_USER", userInfo );
           request.getSession().setAttribute( "USERCURRENTPOWER", CheckPower.getMoudules( userInfo ) ); //ysj add
           String userType = userInfo.getDeptype();
           //zhj

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
           return mapping.findForward( "loginformward" );

       }


    }

    public ActionForward superLoginShow(ActionMapping mapping,
                                        ActionForm form,
                                        HttpServletRequest request,
                                        HttpServletResponse response){
        LoginDao dao = new LoginDao();
        List lRegion = dao.getRegion();
        List lContractor = dao.getContractor();

        request.setAttribute("lRegion",lRegion);
        request.setAttribute("lContractor",lContractor);

        return mapping.findForward("superLoginShow");
    }


}



