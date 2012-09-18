package com.cabletech.baseinfo.dao;

import java.sql.*;
import java.util.List;

import org.apache.log4j.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;

public class SublineDAOImpl extends HibernateDaoSupport{
    private static Logger logger = Logger.getLogger( "SublineDAOImpl" );
    public Subline addSubline( Subline subline ) throws Exception{
        this.getHibernateTemplate().save( subline );
        return subline;
    }


    public Subline findById( String id ) throws Exception{
        return( Subline )this.getHibernateTemplate().load( Subline.class, id );
    }


    /**
     * ����SubLineName �Ƿ����
     * @param name String
     * @return boolean
     */
    public boolean validateSubLineName( String name, String type ){
        String sql = "select count(*) i from sublineinfo where sublinename='" + name + "'";
        ResultSet rs = null;
        try{
            QueryUtil query = new QueryUtil();
            logger.info( "sql :" + sql );
            rs = query.executeQuery( sql );
            rs.next();
            int i = rs.getInt( "i" );
            if( "edit".equals( type ) ){
                if( i <= 1 ){
                    rs.close();
                    return true;
                }
                else{
                    rs.close();
                    return false;
                }

            }
            else{
                if( i == 0 ){
                    rs.close();
                    return true;
                }
                else{
                    rs.close();
                    return false;
                }
            }
        }
        catch( Exception ex ){
            logger.error( "����߶��Ƿ�����ʱ����: " + ex.getMessage() );
            return false;
        }

    }


    public void removeSubline( Subline subline ) throws Exception{
        this.getHibernateTemplate().delete( subline );
    }


    public Subline updateSubline( Subline subline ) throws Exception{
        this.getHibernateTemplate().saveOrUpdate( subline );
        return subline;
    }


    /**
     * <p>���ܣ����ָ�����߶��ܷ�ɾ��,
     * <p>����:ָ���߶ε�id
     * <p>����ֵ:��ɾ������true,���򷵻�false;
     */
    public boolean valiSubLineCanDele( String subLineid ){

        ResultSet rst = null;
        String sql = "select count(*) aa from subline2point s where sublineid ='" + subLineid + "'";
        try{
            QueryUtil excu = new QueryUtil();
            rst = excu.executeQuery( sql );
            rst.next();
            if( rst.getInt( "aa" ) == 0 ){
                rst.close();
                return true;
            }
            else{
                rst.close();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "���ָ�����߶��ܷ�ɾ������:" + e.getMessage() );
            return false;
        }
    }
    /**
     * <p>���ܣ�ͳ��һ���߶��ϵ���ҪѲ���Ѳ�����,
     * <p>����:ָ���߶ε�id
     * <p>����ֵ:��1�������쳣������ֵ�ǽ����
     */
    public String  sumCheckPoints(String sublineid){
        String sql ="select count(*) aa from pointinfo p where p.STATE='1' and p.SUBLINEID = '" + sublineid + "'";
        try{
            QueryUtil  qu = new QueryUtil();
            ResultSet rst = null;
            rst = qu.executeQuery(sql);
            if(rst !=null && rst.next()){
                return rst.getString("aa");
            }else{
                return "-1";
            }
        }catch(Exception e){
            logger.error("ͳ��һ���߶��ϵ���ҪѲ���Ѳ������쳣��" + e.getMessage());
            return "-1";
        }
    }

    /**
     * <p>���ܣ����ָ�����߶����Ƿ����,
     * <p>����:ָ���߶ε�����
     * <p>����ֵ:���ڷ���true �����ںͳ�����false��
     */
    public boolean valiSublineName(String sublinename){
        String sql = "select count(*) aa from sublineinfo s where s.SUBLINENAME='" + sublinename + "'";
        ResultSet rst = null;
        try{
            QueryUtil qu = new QueryUtil();
            rst = qu.executeQuery(sql);
            if(rst !=null && rst.next()){
               if(rst.getString("aa").equals("0"))
                   return false;
               else
                   return true;
            }else
                return false;
        }catch(Exception e){
            logger.error("���ָ�����߶����Ƿ�����쳣:" + e.getMessage());
            return false;
        }
    }
    /**
     * ��������
     * @param sql
     * @return 
     */
    public boolean updateSublineInfo(String sql){
    	 try{
             UpdateUtil excu = new UpdateUtil();
             excu.executeUpdate(sql);
             return true;
         }
         catch( Exception e ){
             logger.error( "����ʱ����:" + e.getMessage() );
             return false;
         }
    }
    public List querySublineInfo(String sql){
    	try{
            QueryUtil query = new QueryUtil();
            return query.queryBeans(sql);
        }catch(Exception e){
            logger.error("��ѯ�߶���Ϣ �쳣:" + e.getMessage());
            return null;
        }
    }


}
