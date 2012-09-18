<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script type="text/javascript">
		queryForm=function(){
			var form=document.forms[0];
			form.action="${ctx}/load_approvers.do?method=loadApprovers";
			form.submit();
		}
		finishForm=function(){
			var form=document.forms[0];
			form.action="${ctx}/load_approvers.do?method=addApprovers";
			form.submit();
		}
		closeForm=function(){
			parent.document.getElementById("${span_id}HideDlg").click();
		}
		</script>
		<style>
		td{
			font-size:12px;
			line-heigh:30px;
			border-color:#A2D0FF;
		}	
		</style>
	</head>
	<body>
		<html:form method="post"
			action="/load_approvers.do?method=addApprovers">
			<table width="100%" border="1" cellspacing="0" cellpadding="0"
				style="border-collapse: collapse; border-color: #A2D0FF;">
				<tr>
					<td width="25%" height="25"
						style="text-align: center; vertical-align: middle;">
						查询条件：
					</td>
					<td width="75%" style="text-align: left;padding-left:5px;">
						<input type="hidden" name="except_self"
							value="<c:out value="${except_self}" />" />
						<input type="hidden" name="input_name"
							value="<c:out value="${input_name}" />" />
						<input type="hidden" name="input_type"
							value="<c:out value="${input_type}" />" />
						<input type="hidden" name="not_allow_value"
							value="<c:out value="${not_allow_value}" />" />
						<input type="hidden" name="display_type"
							value="<c:out value="${display_type}" />" />
						<input type="hidden" name="depart_id"
							value="<c:out value="${depart_id}" />" />
						<input type="hidden" name="span_id"
							value="<c:out value="${span_id}" />" />
						<input type="text" name="query_value" style="width: 180px;" />
						<input name="btnQuery" type="button" id="btnQuery" value="查询"
							onclick="queryForm();" />
					</td>
				</tr>
				<tr>
					<td height="25" style="text-align: center; vertical-align: top;padding-top:10px;">
						人员列表：
					</td>
					<td style="height: 270px; text-align: left;vertical-align: top;padding-top:10px;">
						<logic:present name="approver_list">
							<logic:iterate id="one_approver" name="approver_list">
							<logic:equal value="1X" name="one_approver" property="is_checked">
								<input type="<c:out value="${input_type}" />" name="approver" checked
									value="<bean:write name="one_approver" property="userid" />-<bean:write name="one_approver" property="username" />-<bean:write name="one_approver" property="phone" />" />
							</logic:equal>
							<logic:equal value="0X" name="one_approver" property="is_checked">
								<input type="<c:out value="${input_type}" />" name="approver"
									value="<bean:write name="one_approver" property="userid" />-<bean:write name="one_approver" property="username" />-<bean:write name="one_approver" property="phone" />" />
							</logic:equal>
								&nbsp;
								<bean:write name="one_approver" property="username" />
								<logic:notEmpty name="one_approver" property="position">
								--职位：<bean:write name="one_approver" property="position" />
								</logic:notEmpty>
								<br />
							</logic:iterate>
						</logic:present>
					</td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="25" colspan="2" style="text-align: center;">
						<input name="btnFinish" type="button" id="btnFinish" value="完成"
							onclick="finishForm();" />
						<input name="btnClose" type="button" id="btnClose" value="关闭"
							onclick="closeForm();" />
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
