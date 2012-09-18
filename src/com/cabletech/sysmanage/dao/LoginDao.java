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
     * ����:���ָ�����û��Ƿ����
     * ����:ָ���û��ı��
     * ����:�û����ڷ���true �����ڷ��� false
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
            logger.error( "���ָ�����û��Ƿ�����쳣:" + e.getMessage() );
            return false;
        }
    }


    /**
     *<p>����:�ж�Ѳ����Ա�����ǰ��黹�ǰ��˽��й���,Ĭ���ǰ�����й���
     *<p>����:
     *<p>����:����ǰ��������1,���ǰ��������0
     */
    public String isManageByArry(){
        String sql = "select type from sysdictionary where lable='Ѳ����Ա����ʽ'";
        String[][] rst;
        try{
            QueryUtil query = new QueryUtil();
            rst = query.executeQueryGetArray( sql, "1" );
            return rst[0][0];
        }
        catch( Exception e ){
            logger.error( "�ж�Ѳ����Ա����ʽ�����쳣:" + e.getMessage() );
            return "1";
        }
    }


    /**
     *<p>����:�жϳ������Ƿ���ʾ������Ϣ,Ĭ������ʾ��.
     * 		������¼�ǹ��Ƿ���ʾ������Ϣ������.��typeֵΪ"1"ʱ,��ʾ������Ϣ.
     *		��typeֵΪ"0"��Ϊnullʱ,����ʾ������Ϣ
     * *<p>����:
     *<p>����:�������ʾ���·���1,����ʾ������Ϣ����0
     */
    public String isShowFIB(){
        String sql = "select type from sysdictionary where lable='�Ƿ���ʾ������Ϣ'";
        String[][] rst;
        try{
            QueryUtil query = new QueryUtil();
            rst = query.executeQueryGetArray( sql, "1" );
            return rst[0][0];
        }
        catch( Exception e ){
            logger.error( "�жϳ������Ƿ���ʾ������Ϣ�쳣:" + e.getMessage() );
            return "1";
        }
    }


    public LoginDao(){
    }


    /**
     *<p>����:�жϳ��������µ�������������ʱ�Ƿ��Ŀ�괦���˻������˷��Ͷ���,Ĭ���Ƿ��͵�.
     * 		��typeֵΪ"1"ʱ,��ʾ��Ŀ�괦���˻������˷��Ͷ���.
     *		��typeֵΪ"0"��Ϊnullʱ,��֮.
     *<p>����:
     *<p>����:������ͷ���1,�������ͷ���0.
     */

    public String isSendSM(){
        String sql = "select type from sysdictionary where lable='�Ƿ��Ŀ�괦���˷��Ͷ���'";
        String[][] rst;
        try{
            QueryUtil query = new QueryUtil();
            rst = query.executeQueryGetArray( sql, "1" );
            return rst[0][0];
        }
        catch( Exception e ){
            logger.error( "�ж����µ�������������ʱ�Ƿ��Ŀ�괦���˻������˷��Ͷ����쳣:" + e.getMessage() );

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
        logger.info( "�˳�ϵͳSQL:" + sql );

        String sql1 = "  update USERONLINETIME  "
                      + " set onlinetime = 7200,logouttime=logintime + 7200/(24*60*60) "
                      + " where  logouttime is null "
                      + "      and TO_NUMBER(sysdate - logintime)*24*60*60 > 7200 ";

        logger.info( "�˳�ϵͳSQL1:" + sql1 );

        try{
            UpdateUtil updateU = new UpdateUtil();
            updateU.executeUpdate( sql );
            updateU.commit();
            updateU.executeUpdate( sql1 );
            updateU.commit();
            return true;
        }
        catch( Exception ex ){
            logger.warn( "�û��˳���Ϣд�����ݿ����" + ex.getMessage() );
            return false;
        }
    }


    public boolean addUser( UserInfo bean ){
        String sql = "insert into userinfo  (USERID,USERNAME,DEPTID,REGIONID,DEPTYPE,AccountState,newpsdate) values('"
                     + bean.getUserID() + "','" + bean.getUserName() + "','" + bean.getDeptID() + "','"
                     + bean.getRegionID() + "','" + bean.getDeptype() + "','" + bean.getAccountState() + "',sysdate)";
        logger.info( " ƽ̨�˺�ͬ��SQL: " + sql );
        try{
            UpdateUtil up = new UpdateUtil();
            up.executeUpdate( sql );
            return true;
        }
        catch( Exception e ){
            logger.error( "�����½����û�����:" + e.getMessage() );
            return false;
        }
    }


    /**
     * ���ܣ���ȡ�汾��
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
                    logger.warn( "���ļ�����" + ex.getMessage() );
                    return version;
                }
            }
            while( str != null );

        }
        catch( FileNotFoundException ex3 ){
            logger.warn( "���ļ�����" + ex3.getMessage() );
            return version;
        }
        return version;
    }


    //������������id������
    public List getRegion(){
        String sql = "SELECT r.REGIONID,r.REGIONNAME FROM REGION r where state is null ";
        List lRegion;
        try{
            QueryUtil qu = new QueryUtil();
            lRegion = qu.queryBeans( sql );
            return lRegion;
        }
        catch( Exception ex ){
            logger.warn( "��ȡ������Ϣ����" + ex.getMessage() );
            return null;
        }
    }


    //������д�ά��id������
    public List getContractor(){
        String sql = "SELECT c.CONTRACTORID,c.CONTRACTORNAME,c.REGIONID FROM CONTRACTORINFO c WHERE c.state IS NULL ";
        List lContractor;
        try{
            QueryUtil qu = new QueryUtil();
            lContractor = qu.queryBeans( sql );
            return lContractor;
        }
        catch( Exception ex ){
            logger.warn( "��ȡ��ά��Ϣ����" + ex.getMessage() );
            return null;
        }
    }
}
