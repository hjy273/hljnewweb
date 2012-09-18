package com.cabletech.commons.sqlbuild;

public class test{
    public static void main( String[] args ){
        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( "select * from departinfo" );

        //增加OR字符
        sqlBuild.addOR();

        //增加一个固定表达式
        sqlBuild.addConstant( " name in ( select name from table where  " );

        sqlBuild.addCondition( "name2={0}", "name2" );
        sqlBuild.addRightParenthesis();

        sqlBuild.addRegion( "001" );
        //增加一个其他固定表达式如 group 或者 order by
        //sqlBuild.addOther("group by school");
        //sqlBuild.addOther("order by name");
        //System.out.println( "return " + sqlBuild.toSql() );
        sqlBuild.addCurrentRegion( "003331" );
        //System.out.println( "return " + sqlBuild.toSql() );
        //System.out.println( "return " + sqlBuild.toSql() );
        //System.out.println( "return " + sqlBuild.toSql() );
        //System.out.println( "return " + sqlBuild.toSql() );
    }
}
