package com.cabletech.commons.config;

//дXML�ļ�Ҫ�õ�
import java.io.*; //Java����������������IO����
import java.util.*; //Java���������������ֱ�׼���ݽṹ����
import javax.xml.parsers.*; //XML�������ӿ�

import org.w3c.dom.*; //XML��DOMʵ��

public class ParseMsgInfo{

    private static ParseMsgInfo me;
    private HashMap map;

    private ParseMsgInfo(){
        try{
            map = new HashMap();
            File[] files = MessageConfig.getMessageFile();
            for( int i = 0; i < files.length; i++ ){
            	String fileName = files[i].getName();
                boolean b = files[i].isDirectory();
                if( !b && fileName.contains(".xml")){
                    map.putAll(readXMLFile( map, files[i] ));//֧�ֶ��message�ļ���
                }
            }
        }
        catch( Exception e ){
            e.printStackTrace();
        }
    }


    /**
     *��ʼ��ParseMsgInfo
     * @return
     */
    public static ParseMsgInfo getInstance(){
        if( me == null ){
            me = new ParseMsgInfo();
        }
        return me;
    }


    public HashMap getMsgs(){
        return map;
    }


    private HashMap readXMLFile( HashMap msg_Map, File file ) throws Exception{

        try{
            //Ϊ����XML��׼��������DocumentBuilderFactoryʵ��,ָ��DocumentBuilder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = null;
            Document doc = null;

            db = dbf.newDocumentBuilder();
            //File file=new File(inFile);
            doc = db.parse( file );

            //�����ǽ���XML��ȫ���̣��Ƚϼ򵥣���ȡ��Ԫ��"SystemMsgs"
            Element root = doc.getDocumentElement();
            //ȡ"��Ϣ"Ԫ���б�
            NodeList msgs = root.getElementsByTagName( "Message" );
            for( int i = 0; i < msgs.getLength(); i++ ){
                //����ȡÿ��"��Ϣ"Ԫ��
                Element msg = ( Element )msgs.item( i );
                //����һ��Message��Beanʵ��
                MsgInfo msgBean = new MsgInfo();

                msgBean.setId( msg.getAttribute( "id" ) );
                //ȡ"Info"Ԫ�أ�������ͬ
                NodeList names = msg.getElementsByTagName( "info" );
                if( names.getLength() == 1 ){
                    Element e = ( Element )names.item( 0 );
                    Text t = ( Text )e.getFirstChild();
                    msgBean.setInfo( t.getNodeValue() );
                }

                NodeList ages = msg.getElementsByTagName( "link" );
                if( ages.getLength() == 1 ){
                    Element e = ( Element )ages.item( 0 );
                    Text t = ( Text )e.getFirstChild();
                    msgBean.setLink( t.getNodeValue() );
                }
                msg_Map.put( msgBean.getId(), msgBean );
                //System.out.println(msgBean.toString());
            }
            return msg_Map;
        }
        catch( Exception ex ){
            ex.printStackTrace();
            //System.out.println( "Error: " + ex.getMessage() );
            return null;
        }
    }

}
