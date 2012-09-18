package com.cabletech.commons.hb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class JdbcPreparedUtil {
	private HibernateSession sessionFactory;
    private Logger logger = Logger.getLogger(JdbcPreparedUtil.class);
    private Connection conn = null;
    private PreparedStatement psmt = null;
    
    public JdbcPreparedUtil() throws Exception{
        this.sessionFactory = new HibernateSession();
        this.conn = sessionFactory.currentSession().connection();
        setAutoCommitFalse();
    }
   
    public PreparedStatement setSQL(String sql) throws SQLException{
    	psmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
    	return psmt;
    }
    public void setString(int parameterIndex,String x) throws SQLException{
    	psmt.setString(parameterIndex, x);
    }
    public void setInt(int parameterIndex,int x) throws SQLException{
    	psmt.setInt(parameterIndex, x);
    }
    public void addBatch() throws SQLException{
    	psmt.addBatch();
    }
    public void executeUpdate() throws SQLException{
    	psmt.executeUpdate();
    	psmt.clearBatch();
    	psmt.clearParameters();
    }
    /**
     * �������Ӳ��Զ��ύ
     * **/
    public void setAutoCommitFalse(){
        try{
            if( true == conn.getAutoCommit() ){ //������Զ��ύ,
                conn.setAutoCommit( false );
                return;
            }
        }
        catch( Exception e ){
            logger.error( "�������Ӳ��Զ��ύ����:" + e.getMessage() );
        }
    }
    
    /**
     * ִ��SQL���
     * @param sql SQL���
     */
    public void executeBatch() throws SQLException{
        try{
            if( psmt != null ){
            	psmt.executeBatch();
            }
        }
        catch( SQLException e ){
            e.printStackTrace();
            if( psmt != null ){
            	psmt.close();
            }
            throw e;
        }

    }

    /**
     * �ύ����
     * **/
    public void commit(){
        try{
            conn.commit();
        }
        catch( Exception e ){
            logger.error( "�����ύ����:" + e.getMessage() );
        }
    }


    /**
     * �ع�
     * **/
    public void rollback(){
        try{
            conn.rollback();
        }
        catch( Exception e ){
            logger.error( "���ӻع�����:" + e.getMessage() );
        }
    }
    
    /**
     * ���������Զ��ύ
     * **/
    public void setAutoCommitTrue(){
        try{
            if( false == conn.getAutoCommit() ){ //������Զ��ύ,
                conn.setAutoCommit( true );
                return;
            }
        }
        catch( Exception e ){
            logger.error( "���������Զ��ύ����:" + e.getMessage() );
        }
    }

}
