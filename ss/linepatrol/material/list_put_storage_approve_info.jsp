<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<logic:notEmpty name="approve_info_list">
	<table border="1" cellpadding="0" cellspacing="0" width="100%"
		style="border-collapse: collapse;">
		<tr>
			<td style="text-align:center;">
				审核次数
			</td>
			<td style="text-align:center;">
				审核人
			</td>
			<td style="text-align:center;">
				审核时间
			</td>
			<td style="text-align:center;">
				审核结果
			</td>
			<td style="text-align:center;">
				审核意见
			</td>
		</tr>
		<logic:iterate id="oneApproveInfo" name="approve_info_list" indexId="index">
			<tr>
				<td style="text-align:center;">
					<c:set var="count" value="${index+1}"></c:set>
					第${count}次审核
				</td>
				<td style="text-align:center;">
					<bean:write name="oneApproveInfo" property="username" />
				</td>
				<td style="text-align:center;">
					<bean:write name="oneApproveInfo" property="approve_time" />
				</td>
				<td style="text-align:center;">
					<bean:write name="oneApproveInfo" property="approve_result_dis" />
				</td>
				<td style="text-align:center;">
					<bean:write name="oneApproveInfo" property="approve_remark" />
				</td>
			</tr>
		</logic:iterate>
	</table>
</logic:notEmpty>