<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		
	</head>
	<body>
			<logic:iterate id="mt" name="marterinfos" offset="0" length="1">
				<table align="center">
					<tr height="40">
						<td>
							<font size="3" style="font-weight: bold"><bean:write
									name="mt" property="name" />材料信息列表</font>
						</td>
					</tr>
				</table>
				<input type="hidden"
					value="<bean:write name="mt" property="baseid"/>" id="mtid"
					name="mtid">
				<input type="hidden" id="seq_id" name="seq_id"
					value="<%=(String) request.getAttribute("seq_id")%>" />
			</logic:iterate>
			<logic:notEmpty name="marterinfos" scope="request">
				<table border="1" width="95%" align="center" bgcolor="#FFFFFF"
					cellpadding="0" cellspacing="0" bordercolor="#000000"
					style="border-collapse: collapse">
					<tr height="30px;" align="center"
						style="text-align: center; line-height: 30px;">
						<th>
							材料地址
						</th>
						<th>
							新增库存
						</th>
						<th>
							利旧库存
						</th>
					</tr>
					<logic:iterate id="mtinfo" name="marterinfos">
						<tr>
							<td>
								<bean:write name="mtinfo" property="address" />
							</td>
							<td>
								<bean:write name="mtinfo" property="newstock" /> 
							</td>
							<td>
								<bean:write name="mtinfo" property="oldstock" /> 
							</td>
						</tr>
					</logic:iterate>
				</table>
				</logic:notEmpty>
				<table align="center">
					<tr height="55px">
						<td>
							<html:button property="action" styleClass="button"
							onclick="javascript:window.close();">关闭</html:button>
						</td>
					</tr>
				</table>
	</body>
</html>
