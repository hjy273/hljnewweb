<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>sendtask</title>
	</head>
	<body>
		<!--显示派单反馈的详细信息-->
		<c:if test="${process_list!=null}">
			<table width="90%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr class=trcolor>
					<td class="tdl" colspan="4" style="padding-left:10px;">
						<a href="javascript:showAllProcessHistory()">全部展开/全部关闭</a>
					</td>
				</tr>
				<logic:iterate id="oneProcessUser" name="process_list"
					indexId="index">
					<tr class=trcolor>
						<td class="tdl" colspan="4" style='padding: 10px;'>
							<bean:write name="oneProcessUser" property="username" />
							流程历史信息
							<a
								href="javascript:showOneProcessHistory('${index }','<bean:write name="oneProcessUser" property="id" />')">展开/关闭</a>
						</td>
					</tr>
					<tr class=trcolor
						varId="<bean:write name="oneProcessUser" property="id" />"
						id="processHistoryTr${index }" style="display: none;">
						<input name="processHistoryTrName" value="processHistoryTr${index }" type="hidden" />
						<td id="processHistoryDiv${index }" class="tdl" colspan="4"
							style='padding: 10px;'>
						</td>
					</tr>
				</logic:iterate>
			</table>
		</c:if>
		<p align="center">
			<input type="button" onclick="javascript: closeProcessWin();"
				class="button" value="关闭" />
		</p>
	</body>
</html>
