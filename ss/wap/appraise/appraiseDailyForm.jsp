<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/wap/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>�����ճ����˱�</title>
	</head>
	<script type="text/javascript">
		function back() {
			window.location.href = "${ctx}/wap/appraiseDailyAction.do?method=index&&env=wap";
	}
	</script>
	<body>
	<div>
			<a class="dept1">${LOGIN_USER.userName} ���ã�</a><a class="dept2"
				href="${ctx}/wap/navigation.jsp">���ܵ���</a><a class="dept"
				href="javascript:exitSys();">�˳�</a><br />
		</div>
		<html:form action="/wap/appraiseDailyAction.do?method=appraiseDaily" >
			<table width="100%" border="0" style="text-align:center;">
				<tr>
					<td width="40%">��ά��λ��</td>
					<td>
						<html:select property="contractorId" styleId="contractorId" style="60%">
						<html:options collection="cons" property="contractorID" labelProperty="contractorName"></html:options>
					</html:select>
					</td>
				</tr>
			</table>
			<div align="center"><input type="submit" value="ȷ��"/>&nbsp;&nbsp;<input type="button" value="����" onclick="back();" /></div>
			
		</html:form>
		
	</body>
</html>
