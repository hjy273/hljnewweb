package com.cabletech.commons.tags;

import java.util.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import org.apache.struts.util.*;
import com.cabletech.commons.hb.*;

public class GetConstantYearCollectionTag extends TagSupport{

    public int doStartTag() throws JspException{
        try{
            Vector resultVct = getLableValueCollection();

            String sucFlagStr = ( String )resultVct.get( 0 );
            ArrayList list = ( ArrayList )resultVct.get( 1 );

            this.pageContext.setAttribute( "yearCollection", list );

            if( !sucFlagStr.equals( "1" ) ){

                this.pageContext.getOut().print(
                    "<script>top.location.replace(\"/WebApp/login.do?&method=relogin\");</script>" );
            }
        }
        catch( Exception ex ){
        }
        return this.SKIP_BODY;
    }


    public Vector getLableValueCollection() throws Exception{

        Vector resultVct = new Vector();

        String sql = new String();
        sql += "		select yearstr ||'Äê' yearlable, yearstr yearvalue from  \n";
        sql += "    (								\n";
        sql +=
            "    select to_char(sysdate,'yyyy')-5	yearstr from dual         \n";
        sql +=
            "    union	select to_char(sysdate,'yyyy')-4	yearstr from dual \n";
        sql +=
            "    union	select to_char(sysdate,'yyyy')-3	yearstr from dual \n";
        sql +=
            "    union	select to_char(sysdate,'yyyy')-2	yearstr from dual \n";
        sql +=
            "    union	select to_char(sysdate,'yyyy')-1	yearstr from dual \n";
        sql +=
            "    union	select to_char(sysdate,'yyyy')-0	yearstr from dual \n";
        sql +=
            "    union	select to_char(sysdate,'yyyy')+1	yearstr from dual \n";
        sql +=
            "    union	select to_char(sysdate,'yyyy')+2	yearstr from dual \n";
        sql +=
            "    union	select to_char(sysdate,'yyyy')+3	yearstr from dual \n";
        sql +=
            "    union	select to_char(sysdate,'yyyy')+4	yearstr from dual \n";
        sql +=
            "    union	select to_char(sysdate,'yyyy')+5	yearstr from dual \n";
        sql +=
            "                                                              \n";
        sql +=
            "    )                                                         \n";

        //logger.info("**********************" + sql);

        //Step one
        resultVct.add( "1" );

        ArrayList lableValueList = new ArrayList();
        QueryUtil jutil = new QueryUtil();

        String[][] resultArr = jutil.executeQueryGetArray( sql, "" );
        for( int i = 0; i < resultArr.length; i++ ){

            lableValueList.add(
                new LabelValueBean( resultArr[i][0], resultArr[i][1] ) );

        }

        resultVct.add( lableValueList );
        return resultVct;
    }
}
