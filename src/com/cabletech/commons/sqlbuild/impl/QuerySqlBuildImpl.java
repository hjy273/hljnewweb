package com.cabletech.commons.sqlbuild.impl;

import java.text.*;
import java.util.*;

import com.cabletech.commons.sqlbuild.*;

public class QuerySqlBuildImpl extends QuerySqlBuild{
    String sqlSelect = null;
    String strOther = "";
    Stack sqlStack = new Stack();

    public QuerySqlBuildImpl( String strSelect ){
        sqlSelect = strSelect;
    }


    /**
     * addCondition
     *
     * @param expression String
     * @param para Parameter
     */
    public void addCondition( String expression, Parameter para ){
        if( para.isValidatorParameter() ){
            sqlStack.push( formatExpression( expression, para ) );
        }
    }


    /**
     * addConditionAnd
     *
     * @param expression String
     * @param para Parameter
     */
    public void addConditionAnd( String expression, Parameter para ){
        if( para.isValidatorParameter() ){
            sqlStack.add( KeyWord.AND );
            sqlStack.push( formatExpression( expression, para ) );
        }
    }
    
    public void addConditionAndLike( String expression, String para ){
        if(para!=null&&(!para.equals(""))){
            sqlStack.add( KeyWord.AND );
            sqlStack.push( formatLikeExpression( expression, para ) );
        }
    }


    /**
     * addConditionOR
     *
     * @param expression String
     * @param para Parameter
     */
    public void addConditionOR( String expression, Parameter para ){
        if( para.isValidatorParameter() ){
            sqlStack.add( KeyWord.OR );
            sqlStack.push( formatExpression( expression, para ) );
        }

    }


    /**
     * addLeftParenthesis
     */
    public void addLeftParenthesis(){
        sqlStack.push( KeyWord.LeftParenthesis );
    }


    /**
     * addRightParenthesis
     */
    public void addRightParenthesis(){
        String strTemp = SQLBuildUtil.stackPeek( sqlStack );
        if( strTemp.equals( KeyWord.LeftParenthesis ) ){
            sqlStack.pop();
        }
        else{
            sqlStack.push( KeyWord.RightParenthesis );
        }

    }


    /**
     * addAnd
     */
    public void addAnd(){
        String strTemp = SQLBuildUtil.stackPeek( sqlStack );
        if( strTemp.equals( KeyWord.LeftParenthesis ) ||
            strTemp.equals( KeyWord.AND ) || strTemp.equals( KeyWord.OR ) ){
            //
        }
        else{
            sqlStack.push( KeyWord.AND );
        }
    }


    /**
     * addOR
     */
    public void addOR(){
        String strTemp = SQLBuildUtil.stackPeek( sqlStack );
        if( strTemp.equals( KeyWord.LeftParenthesis ) ||
            strTemp.equals( KeyWord.AND ) || strTemp.equals( KeyWord.OR ) ){
            //
        }
        else{
            sqlStack.push( KeyWord.OR );
        }
    }


    /**
     * addConstant
     */
    public void addConstant( String strConstant ){
        if( strConstant != null && !strConstant.trim().equals( "" ) ){
            sqlStack.push( strConstant );
        }
    }


    /**
     * addOther
     */
    public void addOther( String newOther ){
        strOther += "   " + newOther;
    }


    /**
     * toSql
     *
     * @return String
     */
    public String toSql(){
        return buildSQL();
    }


    private String buildSQL(){
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append( sqlSelect );
        String strCondition = buildWhere();
        if( !strCondition.equals( "" ) ){
            strBuffer.append( " WHERE " );
            strBuffer.append( strCondition );
            strBuffer.append( " " );
        }
        strBuffer.append( strOther );
        //System.out.println(" QuerySqlBuild result sql :=" + strBuffer.toString());
        return strBuffer.toString();
    }


    public String buildWhere(){
        StringBuffer strBuffer = new StringBuffer();
        Vector v = new Vector();

        Stack tempStack = ( Stack )sqlStack.clone();

        while( !tempStack.empty() ){
            String str = ( String )tempStack.pop();
            String strBefore = SQLBuildUtil.stackPeek( tempStack );

            //( 左括号
            if( str.equals( KeyWord.LeftParenthesis ) ){
                v.add( str );
            }
            else{
                if( str.equals( KeyWord.RightParenthesis ) ){
                    if( strBefore.equals( KeyWord.LeftParenthesis ) ){
                        tempStack.pop();
                    }
                    else{
                        v.add( str );
                    }
                }
                else{
                    if( str.equals( KeyWord.AND ) || str.equals( KeyWord.OR ) ){
                        if( strBefore.equals( KeyWord.LeftParenthesis ) ||
                            strBefore.equals( "" ) ){
                            //sqlStack.pop();
                        }
                        else{
                            v.add( str );
                        }
                    }
                    else{
                        v.add( str );
                    }
                }
            }
        }
        for( int i = v.size() - 1; i >= 0; i-- ){
            strBuffer.append( ( String )v.elementAt( i ) );
            strBuffer.append( "  " );
        }
        return strBuffer.toString().trim();
    }


    //String expression, Parameter para
    public String formatExpression( String expression, Parameter para ){
        MessageFormat form = new MessageFormat( expression );
        String[] args = new String[1];
        args[0] = para.getValue();
        //System.out.println(para.toString());
        String strresult = form.format( args ).toString();
        //System.out.println(expression+"--"+args[0]+"-"+strresult);
        return strresult;
    }
    
    //Added by YuLeyuan 用于用来拼接SQL中的Like查询条件，注意参数前后增加的符号
    public String formatLikeExpression( String expression, String para ){
        MessageFormat form = new MessageFormat( expression );
        String[] args = new String[]{"'%"+para+"%'"};        
        String strresult = form.format( args ).toString();        
        return strresult;
    }
    


}
