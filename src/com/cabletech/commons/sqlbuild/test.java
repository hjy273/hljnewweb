package com.cabletech.commons.sqlbuild;

public class test{
    public static void main( String[] args ){
        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( "select * from departinfo" );

        //����OR�ַ�
        sqlBuild.addOR();

        //����һ���̶����ʽ
        sqlBuild.addConstant( " name in ( select name from table where  " );

        sqlBuild.addCondition( "name2={0}", "name2" );
        sqlBuild.addRightParenthesis();

        sqlBuild.addRegion( "001" );
        //����һ�������̶����ʽ�� group ���� order by
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
