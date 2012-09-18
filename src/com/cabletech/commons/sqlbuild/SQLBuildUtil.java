package com.cabletech.commons.sqlbuild;

import java.util.*;

public class SQLBuildUtil{

    public static String stackPeek( Stack sqlStack ){
        String strBefore = "";
        try{
            strBefore = ( String )sqlStack.peek();
        }
        catch( EmptyStackException ex ){
        }
        return strBefore;
    }

}
