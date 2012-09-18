<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<logic:notEmpty name="reply_task_list">
	<table border="1" cellpadding="0" cellspacing="0" width="100%"
		style="border-collapse: collapse;">
		<tr>
			<td style="text-align:center;">
				反馈人
			</td>
			<td style="text-align:center;">
				反馈时间
			</td>
			<td style="text-align:center;">
				反馈结果
			</td>
			<td style="text-align:center;">
				是否超时
			</td>
			<td style="text-align:center;">
				操作
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
						<font color="#339999">未超时</font>
					</c:if>
					<c:if test="${isOutTime=='-'}">
						<font color="#FF0000">超时</font>
					</c:if>
				</td>
				<td style="text-align:center;">
					<a href="javascript:toViewOneReplyTask('${replyId }','<bean:write name="oneReplyTask" property="deptid" />')"> 查看 </a>
				</td>
			</tr>
		</logic:iterate>
	</table>
</logic:notEmpty>