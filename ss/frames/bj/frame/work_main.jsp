<%@ page language="java" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<link type="text/css" rel="stylesheet" href="../css/css.css" />
<style>
body{
text-align:left;
margin:0;
padding:0;
font:normal 12px/150% simsun;
color:#000;
}

</style>
<title>Main</title>
<script type="">
</script>
</head>
<%@ page import="com.cabletech.menu.domainobjects.*" %>
<%@ page import="com.cabletech.menu.services.*" %>
<%@ page import="com.cabletech.baseinfo.domainobjects.*" %>
<%@ page import="java.util.*" %>
<% 
	String mainmenuid = request.getParameter("mainmodulemenu_id");
	String submenuid = request.getParameter("submenu_id");
	String name = request.getParameter("name"); 
	name = new String( name.getBytes( "GBK" ), "UTF-8" );
	
	HashMap secondlyMenu=(HashMap)session.getAttribute("MENU_SECONDLYMENU");
	Vector v = null;
	if(mainmenuid !=null && !"".equals(mainmenuid)){
		v=(Vector)secondlyMenu.get(mainmenuid);
	    //���Ϊnull�����������˵������ڴ�֮�У���Ҫ�����ݿ����װ��
	    if(v==null){
	        MenuService service=new MenuService();
	        //��õ�ǰ��½�û���UserID
	        UserInfo userinfo=(UserInfo)session.getAttribute("LOGIN_USER");
	        List usergroups = (List)session.getAttribute("USERGROUP");
	        if(usergroups != null){
	        	v=service.getSecondlyMenu(mainmenuid,usergroups);
	        }else{
	        	v=service.getSecondlyMenu(mainmenuid,userinfo);
	        }
	        //�����ݿ��ö����˵���Ȼ����뻺��
	        secondlyMenu.put(mainmenuid,v);
	    }
	}
	if(v.size()<=1){
		SecondlyMenu menu= (SecondlyMenu)v.get(0);
		submenuid=menu.getId();
	}
	System.out.println("submenuid: "+submenuid);
	
 %>
<frameset cols="200,*">
		<frame src="../../submenu_tree.jsp?id=<%=mainmenuid%>&name=<%=name%>" scrolling="no" noresize="noresize" frameborder="0">
		<frame src="${ctx}/frames/thirdmenu.jsp?id=<%=submenuid%>" name="mainFrame" id="mainFrame" scrolling="no" noresize="noresize" frameborder="0" >
</frameset>
<body>
 
</body>
</html>
