<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<logic:notEmpty name="refuse_confirm_task_list">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td style="text-align:center;">
				��ǩȷ�ϴ���
			</td>
			<td style="text-align:center;">
				��ǩȷ����
			</td>
			<td style="text-align:center;">
				��ǩȷ��ʱ��
			</td>
			<td style="text-align:center;">
				��ǩȷ�Ͻ��
			</td>
			<td style="text-align:center;">
				����
			</td>
		</tr>
		<logic:iterate id="oneRefuseConfirmTask" name="refuse_confirm_task_list" indexId="index">
			<bean:define id="oneRefuseConfirmId" name="oneRefuseConfirmTask" property="confirmid" />
			<tr>
				<td style="text-align:center;">
					<c:set var="count" value="${index+1}"></c:set>
					��${count}�ξ�ǩȷ��
				</td>
				<td style="text-align:center;">
					<bean:write name="oneRefuseConfirmTask" property="confirmusername" />
				</td>
				<td style="text-align:center;">
					<bean:write name="oneRefuseConfirmTask" property="confirmtime" />
				</td>
				<td style="text-align:center;">
					<bean:write name="oneRefuseConfirmTask" property="resultlabel" />
				</td>
				<td style="text-align:center;">
					<a href="javascript:('${oneRefuseConfirmId }')"> �鿴 </a>
				</td>
			</tr>
		</logic:iterate>
	</table>
</logic:notEmpty>