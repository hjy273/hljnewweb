package com.cabletech.commons.sqlbuild;

import java.util.*;

import com.cabletech.commons.sqlbuild.impl.*;

public abstract class QuerySqlBuild{

    public static QuerySqlBuild newInstance( String strSelect ){
        return new QuerySqlBuildImpl( strSelect );
    }


    /**************************************
     *
     *************************************/

    public void addCondition( String expression, String parameter ){
        addCondition( expression, new Parameter( parameter ) );
    }


    public void addCondition( String expression, int parameter ){
        addCondition( expression, new Parameter( parameter ) );
    }


    public void addCondition( String expression, double parameter ){
        addCondition( expression, new Parameter( parameter ) );
    }


    public void addCondition( String expression, float parameter ){
        addCondition( expression, new Parameter( parameter ) );
    }


    public void addCondition( String expression, long parameter ){
        addCondition( expression, new Parameter( parameter ) );
    }


    //public void addCondition(String expression,Object x){
    //     addCondition(expression,new Parameter(x));
    //}
    public void addCondition( String expression, Date x ){
        addCondition( expression, new Parameter( x ) );
    }


    public void addCondition( String expression, java.sql.Date x ){
        addCondition( expression, new Parameter( x ) );
    }


    public abstract void addCondition( String expression, Parameter para );


    /**************************************
     *
     *************************************/
    public void addConditionAnd( String expression, String parameter ){
        addConditionAnd( expression, new Parameter( parameter ) );
    }


    public void addConditionAnd( String expression, int parameter ){
        addConditionAnd( expression, new Parameter( parameter ) );
    }


    public void addConditionAnd( String expression, double parameter ){
        addConditionAnd( expression, new Parameter( parameter ) );
    }


    public void addConditionAnd( String expression, float parameter ){
        addConditionAnd( expression, new Parameter( parameter ) );
    }


    public void addConditionAnd( String expression, long parameter ){
        addConditionAnd( expression, new Parameter( parameter ) );
    }


    public void addConditionAnd( String expression, Date x ){
        addConditionAnd( expression, new Parameter( x ) );
    }


    public void addConditionAnd( String expression, java.sql.Date x ){
        addConditionAnd( expression, new Parameter( x ) );
    }
    
    //Added by YuLeyuan 用于用来拼接SQL中的Like查询条件
    public void addConditionAndLike( String expression, java.lang.String x ){
        addConditionAndLike( expression,x );
    }


    public abstract void addConditionAnd( String expression, Parameter para );


    /**************************************
     *
     *************************************/
    public void addConditionOR( String expression, String parameter ){
        addConditionOR( expression, new Parameter( parameter ) );
    }


    public void addConditionOR( String expression, int parameter ){
        addConditionOR( expression, new Parameter( parameter ) );
    }


    public void addConditionOR( String expression, double parameter ){
        addConditionOR( expression, new Parameter( parameter ) );
    }


    public void addConditionOR( String expression, float parameter ){
        addConditionOR( expression, new Parameter( parameter ) );
    }


    public void addConditionOR( String expression, long parameter ){
        addConditionOR( expression, new Parameter( parameter ) );
    }


    public void addConditionOR( String expression, Date x ){
        addConditionOR( expression, new Parameter( x ) );
    }


    public void addConditionOR( String expression, java.sql.Date x ){
        addConditionOR( expression, new Parameter( x ) );
    }


    public abstract void addConditionOR( String expression, Parameter para );


    /**************************************
     *
     *************************************/
    public abstract void addLeftParenthesis();


    public abstract void addRightParenthesis();


    public abstract void addAnd();


    public abstract void addOR();


    public abstract void addConstant( String strConstant );


    public abstract void addOther( String newOther );


    /**************************************
     *
     *************************************/


    public void addRegion( String strRegionID ){
        addAnd();
        addLeftParenthesis();
        addCondition(
            "RegionID IN (SELECT RegionID FROM region CONNECT BY PRIOR " +
            "RegionID=parentregionid START WITH RegionID={0}) ", strRegionID );
        addRightParenthesis();
    }


    /**
     * 日期格式
     * @param FieldName String
     * @param DateStr String
     * @param symbol String
     */
    public void addDateFormatStrEnd( String FieldName, String DateStr,
        String symbol ){

        if( DateStr != null && DateStr.length() > 0 ){
            addAnd();
            addConstant( " to_char(" + FieldName + ",'yyyy/mm/dd') " + symbol +
                "'" +
                DateStr + "' " );
        }

    }


    /**
     * 带有表名的情况下，增加区域条件，例如 a.regionid = '110000'
     * @param regionFieldName String
     * @param strRegionID String
     */
    public void addTableRegion( String regionFieldName, String strRegionID ){
        addAnd();
        addLeftParenthesis();
        addCondition( " " + regionFieldName +
            " IN (SELECT RegionID FROM region CONNECT BY PRIOR " +
            "RegionID=parentregionid START WITH RegionID={0}) ",
            strRegionID );
        addRightParenthesis();
    }


    public void addCurrentRegion( String strRegionID ){
        addConditionAnd( "  RegionID={0} ", strRegionID );
    }


    /**************************************
     *
     *************************************/
    public abstract String toSql();
}
