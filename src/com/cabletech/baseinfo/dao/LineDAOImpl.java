package com.cabletech.baseinfo.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.*;
import org.apache.struts.util.LabelValueBean;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;

public class LineDAOImpl extends HibernateDaoSupport{
    private static Logger logger = Logger.getLogger( "LineDAOImpl" );
    public Line addLine( Line line ) throws Exception{
        this.getHibernateTemplate().save( line );
        return line;
    }


    public Line findById( String id ) throws Exception{
        return( Line )this.getHibernateTemplate().load( Line.class, id );
    }


    public void removeLine( Line line ) throws Exception{
        this.getHibernateTemplate().delete( line );
    }


    public Line updateLine( Line line ) throws Exception{
        this.getHibernateTemplate().saveOrUpdate( line );
        return line;
    }


    /**
     * ����LineName �Ƿ����
     * @param name String
     * @return boolean
     */
    public boolean validateLineName( String name, String type,String region ){
        String sql = "select count(lineid) i from lineinfo where linename='" + name + "' and regionid='" + region + "'";
        ResultSet rs = null;
        try{
            QueryUtil query = new QueryUtil();
            logger.info( "validateLineName() sql :" + sql );
            rs = query.executeQuery( sql );
            rs.next();
            int i = rs.getInt( "i" );
            logger.info( "i=" + i );
            if( "edit".equals( type ) ){
                if( i < 1 ){
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
            logger.error( "�����·�Ƿ�����ʱ����: " + ex.getMessage() );
            return false;
        }

    }


    /**
     * <p>���ܣ����ָ�������ܷ�ɾ��,
     * <p>����:ָ���ߵ�id
     * <p>����ֵ:��ɾ������true,���򷵻�false;
     */
    public boolean valiLineCanDele( String lineid ){

        ResultSet rst = null;
        String sql = "select count(*) aa from  sublineinfo  where lineid='" + lineid + "'";
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
            logger.error( "���ָ�������ܷ�ɾ������:" + e.getMessage() );
            return false;
        }
    }
    public List getLine(String sql ){
    	QueryUtil query = null;
		BasicDynaBean dynaBean = null;
		//Vector resultVct = new Vector();
		ArrayList lableList = new ArrayList();
		logger.info("SQL :"+sql);
		try {
			query = new QueryUtil();
			Iterator it = query.queryBeans(sql).iterator();
			while (it.hasNext()) {
				dynaBean = (BasicDynaBean) it.next();
				//logger.info("lavel :"+dynaBean.get("linename")+"  value :"+dynaBean.get("lineid"));
				lableList.add(new LabelValueBean((String) (dynaBean.get("linename")), (String) (dynaBean.get("lineid"))));
			}
			//resultVct.add(lableList);
			logger.info(lableList);
			return lableList;
		} catch (Exception ex) {
			logger.error("������·ʱ����" + ex.getMessage());
			return null;
		}
    }
}
