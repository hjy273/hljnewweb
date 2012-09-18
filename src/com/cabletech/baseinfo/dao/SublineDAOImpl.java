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
     * 检验SubLineName 是否存在
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
            logger.error( "检查线段是否重名时出错: " + ex.getMessage() );
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
     * <p>功能：检查指定的线段能否被删除,
     * <p>参数:指定线段的id
     * <p>返回值:能删除返回true,否则返回false;
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
            logger.error( "检查指定的线段能否被删除出错:" + e.getMessage() );
            return false;
        }
    }
    /**
     * <p>功能：统计一条线段上的需要巡检的巡检点数,
     * <p>参数:指定线段的id
     * <p>返回值:－1，程序异常，其他值是结果。
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
            logger.error("统计一条线段上的需要巡检的巡检点数异常：" + e.getMessage());
            return "-1";
        }
    }

    /**
     * <p>功能：检查指定的线段名是否存在,
     * <p>参数:指定线段的名称
     * <p>返回值:存在返回true 不存在和出错返回false。
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
            logger.error("检查指定的线段名是否存在异常:" + e.getMessage());
            return false;
        }
    }
    /**
     * 批量更新
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
             logger.error( "更新时出错:" + e.getMessage() );
             return false;
         }
    }
    public List querySublineInfo(String sql){
    	try{
            QueryUtil query = new QueryUtil();
            return query.queryBeans(sql);
        }catch(Exception e){
            logger.error("查询线段信息 异常:" + e.getMessage());
            return null;
        }
    }


}
