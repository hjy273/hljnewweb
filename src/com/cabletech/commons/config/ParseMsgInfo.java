package com.cabletech.commons.config;

//写XML文件要用到
import java.io.*; //Java基础包，包含各种IO操作
import java.util.*; //Java基础包，包含各种标准数据结构操作
import javax.xml.parsers.*; //XML解析器接口

import org.w3c.dom.*; //XML的DOM实现

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
                    map.putAll(readXMLFile( map, files[i] ));//支持多个message文件。
                }
            }
        }
        catch( Exception e ){
            e.printStackTrace();
        }
    }


    /**
     *初始化ParseMsgInfo
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
            //为解析XML作准备，创建DocumentBuilderFactory实例,指定DocumentBuilder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = null;
            Document doc = null;

            db = dbf.newDocumentBuilder();
            //File file=new File(inFile);
            doc = db.parse( file );

            //下面是解析XML的全过程，比较简单，先取根元素"SystemMsgs"
            Element root = doc.getDocumentElement();
            //取"消息"元素列表
            NodeList msgs = root.getElementsByTagName( "Message" );
            for( int i = 0; i < msgs.getLength(); i++ ){
                //依次取每个"消息"元素
                Element msg = ( Element )msgs.item( i );
                //创建一个Message的Bean实例
                MsgInfo msgBean = new MsgInfo();

                msgBean.setId( msg.getAttribute( "id" ) );
                //取"Info"元素，下面类同
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
