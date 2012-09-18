<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.io.*;" %>
<%

		String filePath = request.getContextPath();
        String filep= request.getRealPath("\\");
        out.print(filep+"<br>");
		File file = new File("/${ctx}/demo/test.jsp");
        out.println(filePath+"\\test   "  +file.getCanonicalPath());
        out.println(file.getPath());
        out.println(file.getAbsolutePath());
        filep += "\\test";
        out.print(filep+"<br>");
        File f= new File("D:\\test\\");
        if(!f.exists()){
          f.mkdir();
          out.print("mu li create");
        }
        System.out.print(f.exists());
        if(f.isDirectory()){
          f.delete();
          out.println("good");
        }if(!f.exists()){
          f.mkdir();
          out.print("mu li create");
        }
%>
