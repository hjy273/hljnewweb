package com.cabletech.commons.hb;

import java.sql.*;

import org.apache.log4j.Logger;

public class UpdateUtil{
    private HibernateSession sessionFactory;
    private Logger logger = Logger.getLogger(UpdateUtil.class);
    private Connection conn = null;
    private Statement stmt = null;

    /**
     * �ر�����
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
            logger.error( "�ر����ӳ���:" + e.getMessage() );
        }
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
     * �������ݿ�����Ӻͷ�����
     * @param conn ���ݿ������
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
     * ��Batch�����sql���
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
     * ִ��SQL���
     * @param sql SQL���
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
     * ��ʱ�ر�
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
