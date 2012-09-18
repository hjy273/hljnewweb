package com.cabletech.commons.generatorID.impl;

import org.apache.commons.lang.math.*;
import com.cabletech.commons.generatorID.*;
import com.cabletech.utils.*;

public class RandIDImpl extends GeneratorID{
    public java.lang.String getSeq( java.lang.String keyName, int iLength ){
        long id = RandomUtils.nextLong();
        String strSeq = StringUtil.lpad( String.valueOf( id ), iLength, "0" );
        return strSeq;
    }

}
