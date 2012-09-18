<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>sendtask</title>
		<%@include file="/common/header.jsp"%>
	</head>
	<body>
		<logic:notEmpty name="process_history_list">
			<table border="1" cellpadding="1" cellspacing="0" width="100%"
				style="border-collapse: collapse;" class="tabout">
				<tr class=trcolor>
					<td style="text-align: center;">
						流程步骤
					</td>
					<td style="text-align: center;">
						流程处理人
					</td>
					<td style="text-align: center;">
						处理时间
					</td>
					<td style="text-align: center;">
						处理动作
					</td>
					<td style="text-align: center;">
						提交处理人
					</td>
				</tr>
				<logic:iterate id="oneProcessHistory" name="process_history_list"
					indexId="index">
					<tr class=trcolor>
						<td style="text-align: center;">
							<c:set var="count" value="${index+1}"></c:set>
							${count}
						</td>
						<td style="text-align: center;">
							<bean:write name="oneProcessHistory" property="operate_user_name" />
						</td>
						<td style="text-align: center;">
							<bean:write name="oneProcessHistory" property="handled_time_dis" />
						</td>
						<td style="text-align: center;">
							<bean:write name="oneProcessHistory" property="process_action" />
						</td>
						<td style="text-align: center;">
							<bean:write name="oneProcessHistory"
								property="next_operate_user_name" />
						</td>
					</tr>
				</logic:iterate>
			</table>
		</logic:notEmpty>
		<p align="center">
			<c:if test="${show_close=='1'}">
				<input type="button" onclick="javascript:closeProcessWin();"
					class="button" value="关闭" />
			</c:if>
		</p>
	</body>
</html>
