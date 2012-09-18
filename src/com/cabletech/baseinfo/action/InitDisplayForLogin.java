package com.cabletech.baseinfo.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.baseinfo.services.InitDisplayBO;
import com.cabletech.commons.base.*;
import com.cabletech.commons.services.*;
import com.cabletech.commons.web.*;

public class InitDisplayForLogin extends BaseDispatchAction{
    private static Logger logger = Logger.getLogger( "InitDisplayForLogin" );
    InitDisplayBO initbo = new InitDisplayBO();
    public InitDisplayForLogin(){
    	
    }
    // 取得公告信息、巡检人员信息
    public ActionForward init(ActionMapping mapping,ActionForm form,
    		HttpServletRequest request,HttpServletResponse response) throws 
    		ClientException,Exception{
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo)session.getAttribute("LOGIN_USER");
		//取得公告列表
		String notice = "";//initbo.getNoticeInfo(user.getRegionID());
		//巡检人员列表
		if(notice == null)
			notice = "";
		session.setAttribute("noticeli", notice);
    	return null;
    }
    
    public ActionForward initAcciden( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        DBService db = new DBService();
        UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        String regionid = userInfo.getRegionID();
        String deptid = userInfo.getDeptID();
        String type = userInfo.getDeptype();
        logger.info( "deptid " + userInfo.getDeptype() );
        String sql = "select a.KEYID id,decode(a.IFLARGEACCIDENT,'0','隐患','1','障碍','隐患') as aType,"
                     + " to_char(a.SENDTIME,'YYYY-MM-DD HH24:MI') sendtime, nvl(b.pointname,'') point,"
                     + " nvl(c.sublinename,'') subline, nvl(f.operationdes,'') reason, "
                     + " nvl(decode(f.emergencylevel,'1','轻微','2','中度','3','严重','轻微'),'')"
                     + " emlevel, nvl(e.ContractorName,'') contractor,a.simid "
                     +"from ACCIDENT a, pointinfo b, sublineinfo c, contractorinfo e, "
                     + "table(cast(getAllTroubleCodes as tabletypes)) f, "
                     + " patrolmaninfo g where a.SENDTIME>hoursbeforenow(" + Integer.parseInt(userInfo.getAccidenttime() )  + ")"
                     + " and a.pid = b.pointid(+) and a.lid = c.sublineid(+)  and a.patrolid = g.patrolid(+)"
                     + " and g.parentid = e.contractorid(+) "
                     + " and a.operationcode = f.operationcode(+) "
                     + " and a.regionid IN (SELECT RegionID FROM region CONNECT BY PRIOR "
                     + " RegionID=parentregionid START WITH RegionID='" + regionid + "')"
                     + " and a.operationcode != 'A000'"
                     + " and (a.status ='0' or a.status is NULL) ";

        if( type.equals( "2" ) || type == "2" ){
            sql += " and e.CONTRACTORID='" + deptid + "'";
        }
        sql += " order by contractor,atype,emlevel,sendtime DESC";

        java.util.List list = db.queryBeans( sql );
        //为了美观，要填满4行表格
        if( list == null ){
            list = new ArrayList();
        }
        if( list.size() < 4 ){
            int n = 4 - list.size();
            String emptySpace = "&nbsp;";
            HashMap emptyRow = new HashMap();
            emptyRow.put( "atype", emptySpace );
            emptyRow.put( "sendtime", emptySpace );
            emptyRow.put( "reason", emptySpace );
            emptyRow.put( "emlevel", emptySpace );
            emptyRow.put( "subline", emptySpace );
            emptyRow.put( "point", emptySpace );
            emptyRow.put( "contractor", emptySpace );
            for( int i = 0; i < n; i++ ){
                list.add( emptyRow );
            }
        }
        request.setAttribute( "AccidentList", list );
        return mapping.findForward( "listaccident" );
    }


    public ActionForward initOnLineMen( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        String headInfo = "在线巡检员信息";
        UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        String regionid = userInfo.getRegionID();
        String deptid = userInfo.getDeptID();
        String type = userInfo.getDeptype();
        DBService db = new DBService();
        String sql = "select c.contractorname,p.patrolname,m.simid,"
                     + "decode(m.operate,'1','巡检','2','制定线路','3','盯防','巡检') operate,"
                     + "to_char(m.activetime,'YYYY-MM-DD HH24:MI:SS') activetime "
                     + "from onlineman m,terminalinfo t,"
                     + "contractorinfo c,patrolmaninfo p "
                     + "where m.simid=t.simnumber "
                     + "and t.ownerid=p.patrolid "
                     + "and t.contractorid=c.contractorid "
                     + "and activetime>hoursbeforenow(" +  Integer.parseInt(userInfo.getOnlinemantime())+ ") and t.regionid IN "
                     + "(SELECT RegionID FROM region CONNECT BY PRIOR RegionID=parentregionid "
                     + "START WITH RegionID='" + regionid + "')";
        if( userInfo.getType().equals( "22" ) || userInfo.getType() == "22" ){
            sql += " and c.CONTRACTORID ='" + deptid + "'";
        }
        if(userInfo.getType().equals("21")||userInfo.getType()=="21"){
            sql+=" and c.contractorid in (select contractorid from contractorinfo where parentcontractorid='"+deptid+"')";
        }
        sql += " order by contractorname,patrolname,simid ";
        java.util.List list = db.queryBeans( sql );
        logger.info( "onlineSQL : " + sql );
        //为了美观，要填满4行表格
        if( list == null ){
            list = new ArrayList();
        }
        if( list.size() < 4 ){
            int n = 4 - list.size();
            String emptySpace = "&nbsp;";
            HashMap emptyRow = new HashMap();
            emptyRow.put( "contractorname", emptySpace );
            emptyRow.put( "patrolname", emptySpace );
            emptyRow.put( "simid", emptySpace );
            emptyRow.put( "operate", emptySpace );
            emptyRow.put( "activetime", emptySpace );
            for( int i = 0; i < n; i++ ){
                list.add( emptyRow );
            }
        }

        request.setAttribute( "OnLineMenList", list );
        return mapping.findForward( "listonlinemen" );
    }


    public ActionForward initWatchInfo( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        String headInfo = "当前正在执行的盯防信息";
        UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        String regionid = userInfo.getRegionID();
        String deptid = userInfo.getDeptID();
        String type = userInfo.getDeptype();

        DBService db = new DBService();
        String sql = "select c.contractorname,p.patrolname,"
                     + "x.simid,to_char(max(x.executetime),'YYYY-MM-DD HH24:MI:SS') activetime,"
                     + "w.placename "
                     + "from watchexecute x,watchinfo w,"
                     + "terminalinfo t,contractorinfo c,"
                     + "patrolmaninfo p "
                     + "where x.WATCHID=w.placeid "
                     + "and x.simid=t.simnumber "
                     + "and t.ownerid=p.patrolid "
                     + "and t.contractorid=c.contractorid "
                     + "and x.executetime>hoursbeforenow("+ userInfo.getWatchtime()+") and t.regionid IN "
                     + "(SELECT RegionID FROM region CONNECT BY PRIOR RegionID=parentregionid "
                     + "START WITH RegionID='" + regionid + "')";

        if( type.equals( "2" ) || type == "2" ){
            sql += " and c.CONTRACTORID ='" + deptid + "'";
        }
        sql += " group by contractorname,patrolname,placename,simid";
        java.util.List list = db.queryBeans( sql );

        //为了美观，要填满4行表格
        if( list == null ){
            list = new ArrayList();
        }
        if( list.size() < 4 ){
            int n = 4 - list.size();
            String emptySpace = "&nbsp;";
            HashMap emptyRow = new HashMap();
            emptyRow.put( "contractorname", emptySpace );
            emptyRow.put( "patrolname", emptySpace );
            emptyRow.put( "simid", emptySpace );
            emptyRow.put( "placename", emptySpace );
            emptyRow.put( "activetime", emptySpace );
            for( int i = 0; i < n; i++ ){
                list.add( emptyRow );
            }

        }

        request.setAttribute( "OnLineWatchMenList", list );
        return mapping.findForward( "listwatchinfo" );
    }


    public ActionForward initLineCutInfo( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        String regionid = userInfo.getRegionID();
        String deptid = userInfo.getDeptID();
        String type = userInfo.getDeptype();

        String headInfo = "线路割接信息";
        DBService db = new DBService();
        String sql =
            "select s.sublinename,to_char(l.protime,'YYYY-MM-DD') protime ,l.address,SUBSTR(TRIM(' ' from l.REASON),0,48) AS reson,"
            + " l.contractorid from line_cutinfo l,sublineinfo s ,userinfo u "
            + " where s.SUBLINEID = l.sublineid " // and l.retime>hoursbeforenow(24*2) "
            + " and u.userid = l.reuserid and u.regionid IN "
            + "(SELECT RegionID FROM region CONNECT BY PRIOR RegionID=parentregionid "
            + "START WITH RegionID='" + regionid + "')"
            + " and l.isarchive ='已经审批'";

        if( type.equals( "2" ) || type == "2" ){
            sql += " and l.CONTRACTORID='" + deptid + "' ";
        }
        logger.info( "LineCut : " + sql );
        java.util.List list = db.queryBeans( sql );

        //为了美观，要填满4行表格
        if( list == null ){
            list = new ArrayList();
        }
        if( list.size() < 4 ){
            int n = 4 - list.size();
            String emptySpace = "&nbsp;";
            HashMap emptyRow = new HashMap();
            emptyRow.put( "sublinename", emptySpace );
            emptyRow.put( "protime", emptySpace );
            emptyRow.put( "address", emptySpace );
            emptyRow.put( "reson", emptySpace );
            for( int i = 0; i < n; i++ ){
                list.add( emptyRow );
            }
        }
        request.setAttribute( "LineCutInfoList", list );
        return mapping.findForward( "listlinecutinfo" );
    }
}
