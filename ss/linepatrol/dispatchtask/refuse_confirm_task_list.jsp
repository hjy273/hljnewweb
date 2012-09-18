<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<logic:notEmpty name="refuse_confirm_task_list">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td style="text-align:center;">
				拒签确认次数
			</td>
			<td style="text-align:center;">
				拒签确认人
			</td>
			<td style="text-align:center;">
				拒签确认时间
			</td>
			<td style="text-align:center;">
				拒签确认结果
			</td>
			<td style="text-align:center;">
				操作
			</td>
		</tr>
		<logic:iterate id="oneRefuseConfirmTask" name="refuse_confirm_task_list" indexId="index">
			<bean:define id="oneRefuseConfirmId" name="oneRefuseConfirmTask" property="confirmid" />
			<tr>
				<td style="text-align:center;">
					<c:set var="count" value="${index+1}"></c:set>
					第${count}次拒签确认
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
					<a href="javascript:('${oneRefuseConfirmId }')"> 查看 </a>
				</td>
			</tr>
		</logic:iterate>
	</table>
</logic:notEmpty>