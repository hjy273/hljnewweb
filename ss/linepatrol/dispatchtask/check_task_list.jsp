<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<logic:notEmpty name="check_task_list">
	<table border="1" cellpadding="0" cellspacing="0" width="100%"
		style="border-collapse: collapse;">
		<tr>
			<td style="text-align:center;">
				��˴���
			</td>
			<td style="text-align:center;">
				�����
			</td>
			<td style="text-align:center;">
				���ʱ��
			</td>
			<td style="text-align:center;">
				��˽��
			</td>
			<td style="text-align:center;">
				����
			</td>
		</tr>
		<logic:iterate id="oneCheckTask" name="check_task_list" indexId="index">
			<bean:define id="checkId" name="oneCheckTask" property="checkid" />
			<tr>
				<td style="text-align:center;">
					<c:set var="count" value="${index+1}"></c:set>
					��${count}�����
				</td>
				<td style="text-align:center;">
					<bean:write name="oneCheckTask" property="checkmanname" />
				</td>
				<td style="text-align:center;">
					<bean:write name="oneCheckTask" property="checktime" />
				</td>
				<td style="text-align:center;">
					<bean:write name="oneCheckTask" property="validateresultlabel" />
				</td>
				<td style="text-align:center;">
					<a href="javascript:toViewOneCheckTask('${checkId }')"> �鿴 </a>
				</td>
			</tr>
		</logic:iterate>
	</table>
</logic:notEmpty>