<%@ page language="java" pageEncoding="GBK"%>
<%@include file="/wap/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>address book</title>
<style type="text/css">
	.contacts{
		font-size:14px;
		width:60%;
	}
	.dept3{
		font-size:14px;
		width:40%;
	}
</style>
<script type="text/javascript">
	function back() {
		location = "${ctx}/wap/index.do?method=queryAddressBook";
	}
</script>
</head>
<body>
		<div>
			<a class="dept1">${LOGIN_USER.userName} 您好！</a><a class="dept2"
				href="${ctx}/wap/navigation.jsp">功能导航</a><a class="dept"
				href="javascript:exitSys();">退出</a><br /><br />
		</div>
		<table>
	<%
	List address = (List)session.getAttribute("addrList");
	int size = address.size();
	for(int i=0;i<size;i++){
		DynaBean dynaBean= (DynaBean)address.get(i);
	%>
	<tr>
		<td class="contacts">
			<%=dynaBean.get("name") %><br>
			<% 
				String temp = (String)dynaBean.get("phone");
				if(temp.indexOf(",")!=-1){
					String [] phoneArray = temp.split(",");
					for(String phone :phoneArray){
						out.print("<a href=\"wtai://wp/mc;"+phone+"\">"+phone+"</a><br>");
					}
				}else{
					out.print("<a href=\"wtai://wp/mc;"+temp+"\">"+temp+"</a><br>");
				}
				
			%>
		</td>
		<td class="dept3" valign="bottom">
			<%=dynaBean.get("deptname") %>
		</td>
	</tr>
	<%} %>
</table>
<center>
				<input type="button" value="返回" onclick="back();" />
			</center>
</body>
</html>