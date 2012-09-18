package com.cabletech.sysmanage.dao;

import org.apache.log4j.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.FileInputStream;
import java.io.*;
import java.util.List;

public class LoginDao{
    private static Logger logger = Logger.getLogger( LoginDao.class.
                                   getName() );

    /***
     * 功能:检查指定的用户是否存在
     * 参数:指定用户的编号
     * 返回:用户存在返回true 不存在返回 false
     */
    public boolean validateUserExist( String userid ){
        String sql = "select count(*) from userinfo where userid='" + userid + "' and state is null";
        try{
            QueryUtil query = new QueryUtil();
            String[][] str = query.executeQueryGetArray( sql, "" );
            if( str[0][0].equals( "0" ) ){
                return false;
            }
            else{
                return true;
            }
        }
        catch( Exception e ){
            logger.error( "检查指定的用户是否存在异常:" + e.getMessage() );
            return false;
        }
    }


    /**
     *<p>功能:判断巡检人员管理是按组还是按人进行管理,默认是按组进行管理
     *<p>参数:
     *<p>返回:如果是按组管理返回1,不是按组管理返回0
     */
    public String isManageByArry(){
        String sql = "select type from sysdictionary where lable='巡检人员管理方式'";
        String[][] rst;
        try{
            QueryUtil query = new QueryUtil();
            rst = query.executeQueryGetArray( sql, "1" );
            return rst[0][0];
        }
        catch( Exception e ){
            logger.error( "判断巡检人员管理方式出现异常:" + e.getMessage() );
            return "1";
        }
    }


    /**
     *<p>功能:判断程序中是否显示光缆信息,默认是显示的.
     * 		该条记录是关是否显示光缆信息的配置.当type值为"1"时,显示光缆信息.
     *		当type值为"0"或为null时,不显示光缆信息
     * *<p>参数:
     *<p>返回:如果是显示光缆返回1,不显示光缆信息返回0
     */
    public String isShowFIB(){
        String sql = "select type from sysdictionary where lable='是否显示光缆信息'";
        String[][] rst;
        try{
            QueryUtil query = new QueryUtil();
            rst = query.executeQueryGetArray( sql, "1" );
            return rst[0][0];
        }
        catch( Exception e ){
            logger.error( "判断程序中是否显示光缆信息异常:" + e.getMessage() );
            return "1";
        }
    }


    public LoginDao(){
    }


    /**
     *<p>功能:判断程序中有新的申请或审批情况时是否给目标处理人或申请人发送短信,默认是发送的.
     * 		当type值为"1"时,显示给目标处理人或申请人发送短信.
     *		当type值为"0"或为null时,反之.
     *<p>参数:
     *<p>返回:如果发送返回1,不不发送返回0.
     */

    public String isSendSM(){
        String sql = "select type from sysdictionary where lable='是否给目标处理人发送短信'";
        String[][] rst;
        try{
            QueryUtil query = new QueryUtil();
            rst = query.executeQueryGetArray( sql, "1" );
            return rst[0][0];
        }
        catch( Exception e ){
            logger.error( "判断有新的申请或审批情况时是否给目标处理人或申请人发送短信异常:" + e.getMessage() );

            return "1";
        }

    }


    /**
     *
     **/
    public boolean logout( String userkeyid ){

        String uid = userkeyid;
        Date nowDate = new Date();
        SimpleDateFormat dtFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        String strDt = dtFormat.format( nowDate );
        String sql =
            "update useronlinetime set logouttime = to_date('" +
            strDt + "','yy/mm/dd hh24:mi:ss')  , onlinetime =  to_number(to_date('" +
            strDt + "','yy/mm/dd hh24:mi:ss') - LOGINTIME)*24*60*60 "
            + " where keyid = '" + uid + "'";
        logger.info( "退出系统SQL:" + sql );

        String sql1 = "  update USERONLINETIME  "
                      + " set onlinetime = 7200,logouttime=logintime + 7200/(24*60*60) "
                      + " where  logouttime is null "
                      + "      and TO_NUMBER(sysdate - logintime)*24*60*60 > 7200 ";

        logger.info( "退出系统SQL1:" + sql1 );

        try{
            UpdateUtil updateU = new UpdateUtil();
            updateU.executeUpdate( sql );
            updateU.commit();
            updateU.executeUpdate( sql1 );
            updateU.commit();
            return true;
        }
        catch( Exception ex ){
            logger.warn( "用户退出信息写入数据库出错：" + ex.getMessage() );
            return false;
        }
    }


    public boolean addUser( UserInfo bean ){
        String sql = "insert into userinfo  (USERID,USERNAME,DEPTID,REGIONID,DEPTYPE,AccountState,newpsdate) values('"
                     + bean.getUserID() + "','" + bean.getUserName() + "','" + bean.getDeptID() + "','"
                     + bean.getRegionID() + "','" + bean.getDeptype() + "','" + bean.getAccountState() + "',sysdate)";
        logger.info( " 平台账号同步SQL: " + sql );
        try{
            UpdateUtil up = new UpdateUtil();
            up.executeUpdate( sql );
            return true;
        }
        catch( Exception e ){
            logger.error( "单点登陆添加用户错误:" + e.getMessage() );
            return false;
        }
    }


    /**
     * 功能：读取版本号
     * */
    public String getVersion( String path ){
        //System.out.println( "Path:" + path );
        String version = "";
        File file = new File( path + "/login/CVS/Entries" );
        FileReader fr = null;
        java.io.BufferedReader br = null;
        String temp = "", str = "";
        try{
            fr = new FileReader( file );
            br = new BufferedReader( fr );
            do{
                try{
                    temp = br.readLine();
                    str = temp;
                    int k = temp.indexOf( "relogin.jsp" );
                    if( k != -1 ){
                        int j = temp.indexOf( "/", k + 12 );
                        version = temp.substring( k + 12, j );
                        //System.out.println( "VerSion:" + version );
                        return version;
                    }
                }
                catch( IOException ex ){
                    logger.warn( "读文件错误：" + ex.getMessage() );
                    return version;
                }
            }
            while( str != null );

        }
        catch( FileNotFoundException ex3 ){
            logger.warn( "打开文件错误：" + ex3.getMessage() );
            return version;
        }
        return version;
    }


    //获得所有区域的id和名称
    public List getRegion(){
        String sql = "SELECT r.REGIONID,r.REGIONNAME FROM REGION r where state is null ";
        List lRegion;
        try{
            QueryUtil qu = new QueryUtil();
            lRegion = qu.queryBeans( sql );
            return lRegion;
        }
        catch( Exception ex ){
            logger.warn( "读取区域信息出错：" + ex.getMessage() );
            return null;
        }
    }


    //获得所有代维的id和名称
    public List getContractor(){
        String sql = "SELECT c.CONTRACTORID,c.CONTRACTORNAME,c.REGIONID FROM CONTRACTORINFO c WHERE c.state IS NULL ";
        List lContractor;
        try{
            QueryUtil qu = new QueryUtil();
            lContractor = qu.queryBeans( sql );
            return lContractor;
        }
        catch( Exception ex ){
            logger.warn( "读取代维信息出错：" + ex.getMessage() );
            return null;
        }
    }
}
