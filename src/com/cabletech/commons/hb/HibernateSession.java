package com.cabletech.commons.hb;

import org.apache.log4j.*;
import org.hibernate.*;
import org.hibernate.cfg.*;
import org.hibernate.tool.hbm2ddl.*;
import org.springframework.context.ApplicationContext;

import com.cabletech.commons.config.SpringContext;

/**
 *
 * 获取Session连接工厂类
 *
 * @author Robbin Fan
 *
 */
public class HibernateSession{

    private static final ThreadLocal sessionThread = new ThreadLocal();

    private static final ThreadLocal transactionThread = new ThreadLocal();

    private static SessionFactory sf = null;

    private static Logger log = Logger.getLogger( HibernateSession.class );
    private static ApplicationContext context = SpringContext.getApplicationContext();
    public HibernateSession(){
    	context = SpringContext.getApplicationContext();
    }
    /**
     * 获取当前线程使用的Session
     *
     * @return Session
     * @throws HibernateException
     */
    public static Session currentSession() throws HibernateException{
        Session s = ( Session )sessionThread.get();
        if( s == null ){
            if( sf == null ){
            	sf = (SessionFactory) context.getBean("sessionFactory");
                //sf = new Configuration().configure().buildSessionFactory();
            }
            s = sf.openSession();
            sessionThread.set( s );
        }
        return s;
    }


    /**
     * 获取一个新的Session
     *
     * @return Session
     * @throws HibernateException
     */
    public static Session newSession() throws HibernateException{
        if( sf == null ){
        	sf = (SessionFactory) context.getBean("sessionFactory");
			//sf = new Configuration().configure().buildSessionFactory();
        }
        Session s = sf.openSession();
        return s;
    }


    /**
     * 启动或者加入当前Session的Transaction
     *
     * @return Transaction
     * @throws HibernateException
     */
    public static Transaction currentTransaction() throws HibernateException{
        Transaction tx = ( Transaction )transactionThread.get();
        if( tx == null ){
            tx = currentSession().beginTransaction();
            transactionThread.set( tx );
        }
        return tx;
    }


    /**
     * 提交当前Session的Transaction
     *
     * @throws HibernateException
     */
    public static void commitTransaction() throws HibernateException{
        Transaction tx = ( Transaction )transactionThread.get();
        transactionThread.set( null );
        if( tx != null ){
            tx.commit();
        }
    }


    /**
     * 回滚当前事务
     *
     * @throws HibernateException
     */
    public static void rollbackTransaction() throws HibernateException{
        Transaction tx = ( Transaction )transactionThread.get();
        transactionThread.set( null );
        if( tx != null ){
            tx.rollback();
        }
    }


    /**
     * 关闭当前线程使用的Session
     *
     * @throws HibernateException
     */
    public static void closeSession() throws HibernateException{
        Session s = ( Session )sessionThread.get();
        sessionThread.set( null );
        if( s != null ){
            s.close();
        }
    }


    /**
     * 根据映射文件和持久对象生成数据库DDL，生成文件为create_table.sql
     *
     * @param args 参数
     */
    public static void main( String[] args ){
        try{
            //String conf = "C:\\hibernate.cfg.xml";
            //if (args.length != 0) conf = args[0];
            Configuration cfg = new Configuration().configure();

            SchemaExport se = new SchemaExport( cfg );
            se.setOutputFile( "create_table.sql" );
            se.create( true, true );
        }
        catch( HibernateException e ){
            //System.out.println( e.getMessage() );
        }
    }
}
