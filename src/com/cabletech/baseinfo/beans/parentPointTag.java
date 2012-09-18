package com.cabletech.baseinfo.beans;

import java.util.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.cabletech.commons.services.*;

public class parentPointTag extends TagSupport{
    private String model = "E"; //read modle=R;edit model=E
    private String value = "1";
    public void setValue( String value ){
        this.value = value;
    }


    public void setModel( String model ){
        this.model = model;
    }


    public int doStartTag() throws JspException{
        //System.out.println( "doStartTag " + model + "  " + value );
        try{
            HashMap data = this.getData();
            StringBuffer bf = new StringBuffer();
            //只读模式
            if( model.equals( "R" ) ){
                bf.append( data.get( value ) );

                //编辑模式
            }
            else{
                if( model.equals( "E" ) ){
                    bf.append( "<select  name=parentID > " );
                    for( int i = 1; i <= data.size(); i++ ){
                        bf.append( "<option  VALUE=" );
                        bf.append( i );
                        if( String.valueOf( i ).equals( value ) ){
                            bf.append( " selected " );
                        }
                        bf.append( ">  " );
                        bf.append( data.get( String.valueOf( i ) ) );
                        bf.append( " </option> <BR> " );
                    }
                    bf.append( "</select> " );
                }
            }
            //System.out.println( bf.toString() );
            this.pageContext.getOut().print( bf.toString() );
        }
        catch( Exception ex ){
        }
        return this.SKIP_BODY;
    }


    private HashMap getData(){
        HashMap map = new HashMap();
        String[][] data = null;
        try{
            DBService dbService = new DBService();
            data = dbService.queryArray(
                   "select PointID,PointName   from pointinfo ", "" );
        }
        catch( Exception ex ){
            ex.printStackTrace();
        }

        map.put( "", "无上级部门" );
        for( int i = 0; i < data.length; i++ ){
            map.put( data[i][0], data[i][0] );
        }
        return map;
    }
}
