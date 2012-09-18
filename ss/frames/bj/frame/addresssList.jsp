<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="cabletechtag" prefix="apptag"%>
<%@ taglib uri="/WEB-INF/tlds/ctags.tld" prefix="ct" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>通讯录</title>
 <script type="text/javascript" language="javascript">
	function addressList(){
		   var name = document.getElementById("user_name").value;
		   var departName = document.getElementById("depart_name").value;
		   var mobile = document.getElementById("user_mobile").value;
		   URL="${ctx}/addressListAction.do?method=showAddrList&&depart_name="+departName+"&&user_name="+name+"&&user_mobile="+mobile;
		   location.replace(URL);
	}
 </script>
		<style type="text/css">
body {
font: normal 12px auto "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;

}
.text {
	font-size: 18px;
	font-weight: bold;
	text-align: center;
}


#mytable {
width: 100%;
padding: 0;
margin: 0;
}

th {
font: bold 12px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
color: #4f6b72;
filter:progid:DXImageTransform.Microsoft.Gradient(startColorStr='#FFFFFF', endColorStr='#5baddf', gradientType='0');

letter-spacing: 2px;
text-transform: uppercase;
text-align: center;
padding: 6px 6px 6px 12px;
background: #5baddf  no-repeat;
}


td {
border-right: 1px solid #C1DAD7;
border-left: 1px solid #C1DAD7;
border-top: 1px solid #C1DAD7;
border-bottom: 1px solid #C1DAD7;
font-size:12px;
padding: 1px 1px 1px 1px;
}

</style>
	</head>
	<body>
		<form id="addressForm" name="addressForm" method="get"
			action="">
			<div class="text">通 讯 录</div>
			<table width="90%" id="mytable" border="0" cellspacing="0" cellpadding="0"
				class="list">
				<tr>
					<td align="center">
						
					<select name="depart_name" id="depart_name">
		<option value="" selected="selected">不限</option>
		<c:forEach items="${deptList}" var="dept">
			<option value="${dept}">${dept}</option>
		</c:forEach>

			</select></td>
					<td align="center"><input name="user_name" id="user_name" value="${user_name}"
							type="text" /></td>
					<td align="center"><input name="user_mobile" id="user_mobile" value="${user_mobile}"
							type="text" /></td>
					<td align="center"><input type="button" name="button" id="button" value="查 询"
								class="button" onclick="addressList();" /></td>
				</tr>
				<tr>
					<th scope="col">
						单位
					</th>
					<th scope="col">
						姓名
					</th>
					<th scope="col">
						电话
					</th>
					<th scope="col">
						职务
					</th>
				</tr>
				<c:forEach items="${addrList}" var="user">
					<tr height="25px">
						<td class="row">
							&nbsp;
							<bean:write name="user" property="deptname" />
						</td>
						<td class="row">
							&nbsp;
							<bean:write name="user" property="name" />
						</td>
						<td class="row">
							&nbsp;
							<bean:write name="user" property="phone" />
						</td>
						<td class="row">
							&nbsp;
							<bean:write name="user" property="position" />
						</td>
					</tr>
				</c:forEach>
				<tr align="center">
					<th colspan="4">
						<input type="button" class="button" value="关  闭"
							onclick="javascript:window.close();" />
						</td>
				</tr>
			</table>
		</form>
	</body>
</html>