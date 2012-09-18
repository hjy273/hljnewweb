 <%@ page import="java.io.*"%>
  <%@ page import="java.util.*"%>
  <%@ page import="com.cabletech.commons.config.*"%>
 <%
      String logPaht = "";
      String filePath = ConfigPathUtil.getClassPathConfigFile(
                          "log4j.properties" );
        Properties prop = new Properties();
        try{
           out.println( "log4j.properties 位置：" +filePath );
            FileInputStream fis = new FileInputStream( filePath );
            prop.load( fis );
            logPaht = prop.getProperty( "log4j.appender.R.File" ) ;
        }catch(Exception e){
          out.println(e);
        }
        File file = new java.io.File( logPaht );
        if( file.exists() ){
            FileInputStream fInputStream = null;
            OutputStream output = null;
            try{
                fInputStream = new FileInputStream( logPaht );
                //获得文件的长度
                long fileSize = file.length();
                //设置输出格式
                response.addHeader( "content-type", "application/x-msdownload;" );
                response.addHeader( "Content-Disposition", "attachment; filename=weblog.log" );
                response.addHeader( "content-length", Long.toString( fileSize ) );
                output = response.getOutputStream();
                byte[] b = new byte[1024 * 100];
                int j = 0;
                while( ( j = fInputStream.read( b ) ) > 0 ){
                    output.write( b );
                }
                output.close();
                fInputStream.close();
               
            }
            catch( Exception e ){
               
                output.close();
                fInputStream.close();
            
            }
        }
        else{
           out.println("没有找到");
        }
        %>