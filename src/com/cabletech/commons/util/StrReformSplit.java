package com.cabletech.commons.util;

import java.util.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: Cable tech</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class StrReformSplit{
    public StrReformSplit(){
    }


    /**
     * 把用“*”分隔的字符串分解开，以vector格式
     * @param orgStr String
     * @return Vector
     * @throws Exception
     */
    public Vector getVctFromStrWithToken( String orgStr, String tokenStr ) throws
        Exception{
        Vector tempVct = new Vector();

        if( orgStr != null ){
            StringTokenizer st = new StringTokenizer( orgStr, tokenStr );
            while( st.hasMoreTokens() ){
                tempVct.add( st.nextToken() );
            }
        }

        return tempVct;
    }
    public static String  compartStr(String [] str,String token){
		String result = "";
		int size = str.length;
		System.out.println("size  ="+ size);
		for(int i = 0;i<size;i++){
			result += str[i]+token;
		}
		return result.substring(1,result.length()-1);
	}
    public static List getStrSplit(String orgStr,String tokenStr){
    	List tempList = new ArrayList();
    	if( orgStr != null ){
            StringTokenizer st = new StringTokenizer( orgStr, tokenStr );
            while( st.hasMoreTokens() ){
            	tempList.add( st.nextToken() );
            }
        }

        return tempList;
    }
    public static void main(String [] args){
    	List list = getStrSplit("0,10,5,15,20",",");
    	for(int i =0; i<list.size();i++){
    		System.out.println(list.get(i));
    	}
    }
}
