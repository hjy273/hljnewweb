<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<logic:notEmpty name="reply_task_list">
	<table border="1" cellpadding="0" cellspacing="0" width="100%"
		style="border-collapse: collapse;">
		<tr>
			<td style="text-align:center;">
				������
			</td>
			<td style="text-align:center;">
				����ʱ��
			</td>
			<td style="text-align:center;">
				�������
			</td>
			<td style="text-align:center;">
				�Ƿ�ʱ
			</td>
			<td style="text-align:center;">
				����
			</td>
		</tr>
		<logic:iterate id="oneReplyTask" name="reply_task_list">
			<bean:define id="replyId" name="oneReplyTask" property="replyid" />
			<bean:define id="isOutTime" name="oneReplyTask" property="is_out_time" />
			<tr>
				<td style="text-align:center;">
					<bean:write name="oneReplyTask" property="replyusername" />
				</td>
				<td style="text-align:center;">
					<bean:write name="oneReplyTask" property="replytime" />
				</td>
				<td style="text-align:center;">
					<bean:write name="oneReplyTask" property="replyresultlabel" />
				</td>
				<td style="text-align:center;">
					<c:if test="${isOutTime=='+'}">
						<font color="#339999">δ��ʱ</font>
					</c:if>
					<c:if test="${isOutTime=='-'}">
						<font color="#FF0000">��ʱ</font>
					</c:if>
				</td>
				<td style="text-align:center;">
					<a href="javascript:toViewOneReplyTask('${replyId }','<bean:write name="oneReplyTask" property="deptid" />')"> �鿴 </a>
				</td>
			</tr>
		</logic:iterate>
	</table>
</logic:notEmpty>