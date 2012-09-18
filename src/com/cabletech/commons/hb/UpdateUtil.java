package com.cabletech.commons.hb;

import java.sql.*;

import org.apache.log4j.Logger;

public class UpdateUtil{
    private HibernateSession sessionFactory;
    private Logger logger = Logger.getLogger(UpdateUtil.class);
    private Connection conn = null;
    private Statement stmt = null;

    /**
     * 关闭连接
     * **/
    public void close(){
        try{
            if( stmt != null ){
                stmt.close();
            }
            if( !conn.isClosed() ){
                conn.close();
            }
        }
        catch( Exception e ){
            logger.error( "关闭连接出错:" + e.getMessage() );
        }
    }


    /**
     * 设置连接不自动提交
     * **/
    public void setAutoCommitFalse(){
        try{
            if( true == conn.getAutoCommit() ){ //如果是自动提交,
                conn.setAutoCommit( false );
                return;
            }
        }
        catch( Exception e ){
            logger.error( "设置连接不自动提交出错:" + e.getMessage() );
        }
    }
    /**
     * 设置连接自动提交
     * **/
    public void setAutoCommitTrue(){
        try{
            if( false == conn.getAutoCommit() ){ //如果是自动提交,
                conn.setAutoCommit( true );
                return;
            }
        }
        catch( Exception e ){
            logger.error( "设置连接自动提交出错:" + e.getMessage() );
        }
    }



    /**
     * 提交连接
     * **/
    public void commit(){
        try{
            conn.commit();
        }
        catch( Exception e ){
            logger.error( "连接提交出错:" + e.getMessage() );
        }
    }


    /**
     * 回滚
     * **/
    public void rollback(){
        try{
            conn.rollback();
        }
        catch( Exception e ){
            logger.error( "连接回滚出错:" + e.getMessage() );
        }
    }


    /**
     * 构造数据库的连接和访问类
     * @param conn 数据库的连接
     * @throws Exception
     */
    public UpdateUtil() throws Exception{
        this.sessionFactory = new HibernateSession();
        this.conn = sessionFactory.currentSession().connection();
        setAutoCommitFalse();
        stmt = conn.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,
               ResultSet.CONCUR_READ_ONLY );
    }
    
    /**
     * 向Batch里添加sql语句
     * @param sql
     * @throws SQLException
     */
    public void addSQL(String sql) throws SQLException{
    	stmt.addBatch(sql);
    }

    public void exeBatch() throws SQLException{
    	stmt.executeBatch();
    }
    /**
     * 执行SQL语句
     * @param sql SQL语句
     */
    public void executeUpdate( String sql ) throws SQLException{
        try{
            if( stmt != null ){
                stmt.executeUpdate( sql );
            }
        }
        catch( SQLException e ){
            e.printStackTrace();
            if( stmt != null ){
                stmt.close();
            }
            throw e;
        }

    }


    /**
     * 即时关闭
     * @param sql String
     * @throws SQLException
     */
    public void executeUpdateWithCloseStmt( String sql ) throws SQLException{
        try{
            if( stmt != null ){
                stmt.executeUpdate( sql );
            }

            stmt.close();
            stmt = null;
        }
        catch( SQLException e ){
            e.printStackTrace();
            stmt.close();
            throw e;
        }

    }

}
