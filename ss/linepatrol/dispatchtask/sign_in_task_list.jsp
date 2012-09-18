<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<!--已经签收的派单详细信息-->
<logic:notEmpty name="sign_in_task_list">
	<table border="1" cellpadding="0" cellspacing="0" width="100%"
		style="border-collapse: collapse;">
		<tr>
			<td style="text-align:center;">
				签收人
			</td>
			<td style="text-align:center;">
				签收时间
			</td>
			<td style="text-align:center;">
				签收结果
			</td>
			<td style="text-align:center;">
				操作
			</td>
		</tr>
		<logic:iterate id="oneSignInTask" name="sign_in_task_list">
			<bean:define id="signInId" name="oneSignInTask" property="signinid" />
			<tr>
				<td style="text-align:center;">
					<bean:write name="oneSignInTask" property="signinusername" />
				</td>
				<td style="text-align:center;">
					<bean:write name="oneSignInTask" property="signintime" />
				</td>
				<td style="text-align:center;">
					<bean:write name="oneSignInTask" property="resultlabel" />
				</td>
				<td style="text-align:center;">
					<a href="javascript:toViewOneSignInTask('${signInId }')"> 查看 </a>
				</td>
			</tr>
		</logic:iterate>
	</table>
</logic:notEmpty>