package com.cabletech.sysmanage.util;

import java.util.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;
import com.cabletech.sysmanage.dao.LoginDao;

public class OnLineUsers   implements HttpSessionBindingListener{
    private  Logger logger = Logger.getLogger( "OnLineUsers" );

    private static OnLineUsers onLineUsers;

    private OnLineUsers(){
    }


    public static OnLineUsers getInstance(){
        if( onLineUsers == null ){
            onLineUsers = new OnLineUsers();
        }
        return onLineUsers;
    }


    private Vector usersVct = new Vector();

    public int getCount(){
        usersVct.trimToSize();
        return usersVct.capacity();
    }


    public boolean existUser( String userName ){
        usersVct.trimToSize();
        boolean existUser = false;
        for( int i = 0; i < usersVct.capacity(); i++ ){
            if( userName.equals( ( String )usersVct.get( i ) ) ){
                existUser = true;
                break;
            }
        }
        return existUser;
    }


    public boolean deleteUser( String userName ){
        usersVct.trimToSize();
        if( existUser( userName ) ){
            int currUserIndex = -1;
            for( int i = 0; i < usersVct.capacity(); i++ ){
                if( userName.equals( ( String )usersVct.get( i ) ) ){
                    currUserIndex = i;
                    break;
                }
            }
            if( currUserIndex != -1 ){
                usersVct.remove( currUserIndex );
                usersVct.trimToSize();
                return true;
            }
        }
        return false;
    }


    public Vector getOnLineUser(){
        return usersVct;
    }


    public void valueBound( HttpSessionBindingEvent e ){
        usersVct.trimToSize();
        if( !existUser( e.getName() ) ){
            usersVct.add( e.getName() );
            Date nowDate = new Date();
            System.out.print( e.getName() + "\t 登入到系统\t" + ( nowDate ) );
            System.out.println( " 在线用户数为：" + getCount() );

        }
        else{
            //System.out.println( e.getName() + "已经存在" );
        }
    }


    public void valueUnbound( HttpSessionBindingEvent e ){
        usersVct.trimToSize();
        String userName = e.getName();
        deleteUser( userName );
        logger.info( userName + "\t 退出系统\t" + ( new Date() ) + "  在线用户数为：" + getCount() );
    }
}
