<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/wap/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>其他日常考核表</title>
	</head>
	<script type="text/javascript">
		function back() {
			window.location.href = "${ctx}/wap/appraiseDailyAction.do?method=index&&env=wap";
	}
	</script>
	<body>
	<div>
			<a class="dept1">${LOGIN_USER.userName} 您好！</a><a class="dept2"
				href="${ctx}/wap/navigation.jsp">功能导航</a><a class="dept"
				href="javascript:exitSys();">退出</a><br />
		</div>
		<html:form action="/wap/appraiseDailyAction.do?method=appraiseDaily" >
			<table width="100%" border="0" style="text-align:center;">
				<tr>
					<td width="40%">代维单位：</td>
					<td>
						<html:select property="contractorId" styleId="contractorId" style="60%">
						<html:options collection="cons" property="contractorID" labelProperty="contractorName"></html:options>
					</html:select>
					</td>
				</tr>
			</table>
			<div align="center"><input type="submit" value="确定"/>&nbsp;&nbsp;<input type="button" value="返回" onclick="back();" /></div>
			
		</html:form>
		
	</body>
</html>
