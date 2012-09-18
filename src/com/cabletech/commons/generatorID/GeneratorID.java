package com.cabletech.commons.generatorID;

public abstract class GeneratorID{

    public abstract java.lang.String getSeq( java.lang.String keyName, int iLength );


    /**
     * 取得缺省的序列，名称为default,长度为8
     * @return String
     */
    public java.lang.String getSeq(){
        return getSeq( "default", 8 );
    }


    /**
     * 一次性取多个序列
     * @param iTotal int
     * @param strTable String
     * @param iLength int
     * @return String[]
     */
    public java.lang.String[] getSeqs( int iTotal, java.lang.String strTable, int iLength ){
        String[] strSeqs = new String[iTotal];
        for( int i = 0; i < iTotal; i++ ){
            strSeqs[i] = getSeq( strTable, iLength );
        }
        return strSeqs;
    }

}
