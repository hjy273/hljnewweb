package com.cabletech.commons.tags;

import java.util.*;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import org.apache.commons.beanutils.*;
import org.apache.log4j.Logger;
import org.apache.struts.util.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.sqlbuild.*;

public class SelectOptionsInitTag extends TagSupport{
	private Logger logger = Logger.getLogger(SelectOptionsInitTag.class);
    private String valueName = "";
    private String tableName;
    private String columnName1;
    private String columnName2;
    private String condition;
    private String region = "false";
    private String currentRegion = "false";
    private String regionID = null;
    private String order = ""; //新的排序字段
    private String exterior = "false";//使用外部条件

    private String state = "true";

    public int doStartTag() throws JspException{
        try{
        	
            Vector resultVct = getLableValueCollection( tableName, columnName1, columnName2 );
            String sucFlagStr = ( String )resultVct.get( 0 );
            List list = null;
            if(resultVct.size()<=0){
            	list = null;
            }else{
            	list = ( ArrayList )resultVct.get( 1 );
            }
            this.pageContext.setAttribute( valueName, list );
            //System.out.println("sucFlagStr:" + sucFlagStr);
            for ( int i = 0 ;i<list.size();i++){
            	//System.out.println(list.get(i));
            }
           // System.out.println("valueName:" + valueName);
            if( !sucFlagStr.equals( "1" ) ){
                this.pageContext.getOut().print(
                    "<script>top.location.replace(\"/WebApp/login.do?&method=relogin\");</script>" );
            }
        }
        catch( Exception ex ){
            //System.out.println( "TAG Exception:" + ex.getMessage() );
        }
        return this.SKIP_BODY;
    }


    public ArrayList getDataFromCache( String tableName, String lblColName,
        String valColName ) throws Exception{
        String key = tableName + lblColName + valColName;
        HttpSession cacheManger = this.pageContext.getSession();
        ArrayList list = ( ArrayList )cacheManger.getAttribute( key );
        if( list == null ){
            list = ( ArrayList )getLableValueCollection( tableName, lblColName,
                   valColName ).get( 1 );
            //cacheManger.setAttribute(key,list);
        }
        return list;
    }


    public Vector getLableValueCollection( String tableName,
        String lblColName,
        String valColName ) throws
        Exception{

        Vector resultVct = new Vector();
        String sqlString = new String();

        sqlString = "select " + lblColName + "," + valColName + " from " + tableName;

        QuerySqlBuild queryBuild = QuerySqlBuild.newInstance( sqlString );
        queryBuild.addConstant( " 1=1 " );

        String sucFlagStr = "1";
        try{
            if( this.regionID == null ){
                if( getRegion().equalsIgnoreCase( "true" ) ){
                    String regionID = getRegionID( this.pageContext.getSession() );
                    queryBuild.addRegion( regionID );
                }
                if( getCurrentRegion().equalsIgnoreCase( "true" ) ){
                    String regionID = getRegionID( this.pageContext.getSession() );
                    queryBuild.addCurrentRegion( regionID );
                }
            }
            else{
                queryBuild.addCurrentRegion( this.regionID );
            }

        }
        catch( Exception e ){
            sucFlagStr = "-1";
        }

 //      System.out.print( "stat: " + this.getState() );
        String sql = queryBuild.toSql();
        if(getExterior().equalsIgnoreCase("true")){
        	Map map = (Map) this.pageContext.getSession().getAttribute("CONDITIONMAP");
        	Iterator it = map.keySet().iterator();
        	while(it.hasNext()){
        		String key = it.next().toString();
        		String value = (String)map.get(key);
        		sql = sql  + " and "+key+"='"+value+"' ";
        	}
        }
        if( this.getCondition() != null && !this.getCondition().equals( "" ) ){
            sql = sql + " and " + getCondition();
        }
        if( this.valiState( tableName ) && this.getState().equals( "true" ) ){
            sql = sql + " and state is null";
        }
        
        if( this.getOrder().equals( "" ) || this.getOrder() == null ){
            sql = sql + " order by  " + lblColName + " ";
        }
        else{
            sql = sql + " order by  " + this.getOrder() + " ";
        }
        resultVct.add( sucFlagStr );
        System.out.println( "SQL:" + sql );
        try{
            BasicDynaBean bdb;
            ArrayList lableValueList = new ArrayList();
            QueryUtil jutil = new QueryUtil();
            Iterator it = jutil.queryBeans( sql ).iterator();

            while( it.hasNext() ){

                bdb = ( BasicDynaBean )it.next();
                lableValueList.add(
                    new LabelValueBean( ( String ) ( bdb.get( lblColName ) ),
                    ( String ) ( bdb.get( valColName ) ) ) );
            }
            //Step Two
            resultVct.add( lableValueList );
            return resultVct;
        }
        catch( Exception e ){
        	logger.error("错误："+e.getMessage());
        	e.printStackTrace();
            return resultVct;
        }
    }


    public String getRegionID( HttpSession session ){
        UserInfo userinfo = ( UserInfo )session.getAttribute( "LOGIN_USER" );
        return userinfo.getRegionID();
    }


    public void setColumnName1( String columnName1 ){
        this.columnName1 = columnName1;
    }


    public void setColumnName2( String columnName2 ){
        this.columnName2 = columnName2;
    }


    public void setTableName( String tableName ){
        this.tableName = tableName;
    }


    public void setValueName( String valueName ){
        this.valueName = valueName;
    }


    public String getCondition(){
        return condition;
    }


    public void setCondition( String condition ){
        this.condition = condition;
    }


    public void setRegion( String newregion ){
        this.region = newregion;
    }


    public String getRegion(){
        return region;
    }


    public void setCurrentRegion( String currentRegion ){
        this.currentRegion = currentRegion;
    }


    public void setRegionID( String regionID ){
        this.regionID = regionID;
    }


    public void setOrder( String order ){
        this.order = order;
    }


    public void setState( String state ){
        this.state = state;
    }


    public String getCurrentRegion(){
        return currentRegion;
    }


    public String getRegionID(){
        return regionID;
    }


    public String getOrder(){
        return order;
    }


    public String getState(){
        return state;
    }


    /**
     * 功能:测试一个表中STATE字段是否存在
     * 参数:表名
     * 返回:存在返回 true 不存在返回false
     */
    public boolean valiState( String tablename ){
        String sql = " (select count(*) from user_tab_columns  "
                     + " where table_name = '" + tablename.toUpperCase() + "' "
                     + " and COLUMN_NAME='STATE')";
        try{
            QueryUtil query = new QueryUtil();
            String[][] count = query.executeQueryGetArray( sql, "" );
            if( count[0][0].equals( "0" ) ){
                return false;
            }
            else{
                return true;
            }
        }
        catch( Exception e ){
            return false;
        }
    }


    /**
     * release
     *
     * @todo Implement this javax.servlet.jsp.tagext.Tag method
     */
    public void release(){

    }


	public String getExterior() {
		return exterior;
	}


	public void setExterior(String exterior) {
		this.exterior = exterior;
	}

}
