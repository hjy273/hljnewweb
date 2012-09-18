package com.cabletech.manager.dao;

import com.cabletech.commons.hb.*;

public class SortDao{
    public SortDao(){
    }

    public boolean saveSort1(String[] userid,String[] positionno){
         try{
             if(userid.length != positionno.length)
                return false;
                String sql = "";
            UpdateUtil up = new UpdateUtil();
            try{
                for(int i = 0; i < userid.length; i++){
                    sql = "update userinfo set positionno = '" + positionno[i] + "' "
                          + " where userid = '" + userid[i] + "'";
                    //System.out.println( "SQL:" + sql );
                    up.executeUpdate(sql);
                }
                up.commit();
                return true;
            }
            catch( Exception ex ){
                //System.out.println( "写入出错1:" + ex.getMessage() );
                up.rollback();
                return false;
            }
        }
        catch( Exception ex ){
            //System.out.println( "写入出错2:" + ex.getMessage() );
            return false;
        }
    }
}
