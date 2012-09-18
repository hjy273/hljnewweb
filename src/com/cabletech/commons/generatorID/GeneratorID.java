package com.cabletech.commons.generatorID;

public abstract class GeneratorID{

    public abstract java.lang.String getSeq( java.lang.String keyName, int iLength );


    /**
     * ȡ��ȱʡ�����У�����Ϊdefault,����Ϊ8
     * @return String
     */
    public java.lang.String getSeq(){
        return getSeq( "default", 8 );
    }


    /**
     * һ����ȡ�������
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
