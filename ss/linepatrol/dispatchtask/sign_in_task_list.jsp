<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<!--�Ѿ�ǩ�յ��ɵ���ϸ��Ϣ-->
<logic:notEmpty name="sign_in_task_list">
	<table border="1" cellpadding="0" cellspacing="0" width="100%"
		style="border-collapse: collapse;">
		<tr>
			<td style="text-align:center;">
				ǩ����
			</td>
			<td style="text-align:center;">
				ǩ��ʱ��
			</td>
			<td style="text-align:center;">
				ǩ�ս��
			</td>
			<td style="text-align:center;">
				����
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
					<a href="javascript:toViewOneSignInTask('${signInId }')"> �鿴 </a>
				</td>
			</tr>
		</logic:iterate>
	</table>
</logic:notEmpty>