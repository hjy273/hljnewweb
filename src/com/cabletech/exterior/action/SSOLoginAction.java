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
 * 提供了综合平台调的接口，单点登陆
 * @author Administrator
 *
 */
public class SSOLoginAction extends BaseInfoBaseDispatchAction {
	private EncryptDecrypt deCode = new EncryptDecrypt();
	private static Logger logger = Logger.getLogger( "SSOLoginAction" );
	/**
	 * 从综合平台登陆巡检系统
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
		String loginflag = request.getParameter("loginflag");//第一次登陆标记
		String businessType="1";
		//解密;
		userID = deCode.Decode(userID);
		// 验证成功,跳转执行另一个Baction.java;
    	long start = System.currentTimeMillis();
        LoginDao logindao = new LoginDao();
        UserInfo userInfo = null;
        UserInfoBO bou = new UserInfoBO();
        String Flag = "nodisplay";
        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat( "yyyy-MM-dd" );
        String strDate = df.format( date );
        //用户不存在
        if( !logindao.validateUserExist( userID ) ){
            request.setAttribute( "loginerror", "nouser" );
            return mapping.findForward( "errorformward" );
        }
        else{ //存在该用户
            userInfo = super.getService().loadUserInfo( userID );
            //检查帐号是否过期
            if( userInfo.getAccountTerm().before( date ) ){
                request.setAttribute( "loginerror", "accounttermerror" );
                return mapping.findForward( "errorformward" );
            }
            logger.info( userInfo.getNewpsdate() );

            int st = bou.viladateNewPsDate( userInfo );
            long timer = bou.getTimer();
            //logger.info( userInfo.getNewpsdate() + "1 提示用户更改密码  2 密码失效 ,禁止用户登陆  0 正常登陆  当前值：" + st );
            if( st == 2 ){
                request.setAttribute( "loginerror", "perror" );
                return mapping.findForward( "errorformward" );
            }
            if( st == 1 ){
                Flag = "display";
            }
            //如果账号为暂停，
            if( !userInfo.getAccountState().equals( "1" ) ){
                request.setAttribute( "loginerror", "accerror" );
                return mapping.findForward( "errorformward" );
            }

            //载入该用户的菜单
            MenuService menuService = new MenuService();
            Vector firstMenu = menuService.getFirstMenu( userInfo,businessType);
            request.getSession().setAttribute( "MENU_FIRSTMENU", firstMenu );
            //如果没有权限 2005-05-31
            if( firstMenu == null || firstMenu.size() < 1 ){
                request.setAttribute( "loginerror", "powererror" );
                return mapping.findForward( "errorformward" );
            }

            String strFirstMenuID = null;
            if( firstMenu.size() > 0 ){
                FirstMenu menu = ( FirstMenu )firstMenu.elementAt( 0 );
                strFirstMenuID = menu.getId();
            }
            logger.info( "开始载入二级菜单" );
            HashMap mapSecondlyMenu = new HashMap();
            Vector secondlyMenu = menuService.getSecondlyMenu( strFirstMenuID, userInfo );
            mapSecondlyMenu.put( strFirstMenuID, secondlyMenu );
            request.getSession().setAttribute( "MENU_SECONDLYMENU", mapSecondlyMenu );

            logger.info( "开始载入三级菜单" );
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

            //如果登陆成功,转载用户信息
            request.getSession().setMaxInactiveInterval( 7200 ); //Sesion有效时长，以秒为单位 7200
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
                request.getSession().setAttribute( "display", Flag ); //设置提示密码过期显示
                request.getSession().setAttribute( "enddate", enddate.substring( 0, 10 ) );
            }
            //ZSH 取得地区名称
            Region region = super.getService().loadRegion( userInfo.getRegionid() );
            request.getSession().setAttribute( "LOGIN_USER_REGION_NAME", region.getRegionName() );

            if( userType.equals( "2" ) ){ //代为单位
                Contractor contractor = super.getService().loadContractor( userInfo.getDeptID() );
                request.getSession().setAttribute( "LOGIN_USER_DEPT_NAME",
                    contractor.getContractorName() );
                request.getSession().setAttribute( "LOGIN_USER_DEPT_ID", contractor.getContractorID() );
            }
            else{ //内部部门
                Depart depart = super.getService().loadDepart( userInfo.getDeptID() );
                request.getSession().setAttribute( "LOGIN_USER_DEPT_NAME", depart.getDeptName() );
            }
            RegionBO rbo = new RegionBO();

            request.getSession().setAttribute( "REGION_ROOT", rbo.getRegionID() );
            log( request, " 登陆系统 ", "登陆系统 " );
            OnLineUsers online = OnLineUsers.getInstance();
            String loginIp = request.getRemoteAddr();

            if( !online.existUser( userInfo.getUserID() ) ){
                request.getSession().setAttribute( userInfo.getUserID(), online );
                logger.info( "欢迎新用户: " + userInfo.getUserID() + " 登陆到系统！" );
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
            //判断是否是第一次登录，登录时间写入数据库
            if( loginflag == null  ){
                SimpleDateFormat dtFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
                String strDt = dtFormat.format( nowDate );


                OracleIDImpl ora = new OracleIDImpl();
                String uid = ora.getSeq( "useronlinetime", 16 );
                logger.info( "获得主键id" + uid );
                request.getSession().setAttribute( "userkeyid", uid );
                String sql2 =
                    "insert into useronlinetime ul (ul.KEYID, ul.USERID, ul.LOGINIP, ul.LOGINTIME) values ( " +
                    "'" + uid + "', " + "'" + userInfo.getUserID() + "','" + loginIp + "'," + "to_date('"
                    + strDt + "','YYYY-MM-DD HH24:MI:SS'))";
                updateU.executeUpdate( sql2 );
            }
            
            //加载tabmenu菜单
//            SetupDesktopBO sdbo= new SetupDesktopBO();
//            request.getSession().setAttribute("tabmenu",sdbo.toLoadTabMenu(userInfo));
//            String tabmm = sdbo.getTabMenuModule(userInfo);
            //公告提示信息
            NoticeBo nbo = new NoticeBo();
            Notice notice = nbo.Message(rbo.getRegionID(), userInfo);
            request.getSession().setAttribute("notice", notice);
            //加载初始信息
            InitDisplayBO initbo = new InitDisplayBO();
            //取得公告列表
    		String noticestr = initbo.getNoticeInfo(rbo.getRegionID(),userInfo.getRegionID(),"");
    		//巡检人员列表
    		String onlineman = initbo.getPatrolManStr(userInfo);
    		if(noticestr == null)
    			noticestr = "";
    		request.getSession().setAttribute("noticeli", noticestr);
    		request.getSession().setAttribute("onlineman", onlineman);
    		//设置桌面
//    		request.getSession().setAttribute("favstr",sdbo.getFavStr(userInfo));
//    		request.getSession().setAttribute("nonfavstr",sdbo.getNonFavStr(userInfo));
//    		if(tabmm.indexOf("002") != -1){
//    		//加载计划信息
//    		InitDisplayPlanBO idpbo = new InitDisplayPlanBO();
//    		request.getSession().setAttribute("FulfillPlan",idpbo.getFulfillPlan(userInfo));
//    		request.getSession().setAttribute("ProgressPlan",idpbo.getProgressPlan(userInfo));
//    		request.getSession().setAttribute("ShallStartPlan",idpbo.getShallStartPlan(userInfo));
//    		}
            //判断巡检人员是按组还是按人进行管理
    		SysDictionary service = new SysDictionary();
            if( service.isManageByArry().equals( "1" ) ){
                request.getSession().setAttribute( "PMType", "group" );
            }
            else{
                request.getSession().setAttribute( "PMType", "notGroup" );
            }

            //判断是否显示光缆信息
            if( service.isShowFIB().equals( "1" ) ){
                request.getSession().setAttribute( "ShowFIB", "show" );
            }
            else{
                request.getSession().setAttribute( "ShowFIB", "noShow" );
            }

            //判断是否发送短信
            if( service.isSendSM().equals( "1" ) ){
                request.getSession().setAttribute( "isSendSm", "send" );
            }
            else{
                request.getSession().setAttribute( "isSendSm", "nosend" );
            }
            long end = System.currentTimeMillis();
            long total=(end - start)/1000;
            log.info("运行时间："+total/60+"分"+total%60+"秒");
            return mapping.findForward( "loginformward" );
        }
	}
}
