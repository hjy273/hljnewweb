<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>


	</head>

	<body>
		<template:titile value="�鿴�������뵥" />
		<link href="${ctx}/css/linepatrol_css.css" rel="stylesheet"
			type="text/css">
		<html:form
			action="/LinePatrolManagerAction?method=addLinePatrolAssessInfo"
			styleId="addForm">
			<input type="hidden" name="materialaddid"
				value="<bean:write name="bean" property="id"/>" />
			<input type="hidden" name="userid" value="" />
			<center>
			<table width="90%" border="0" cellpadding="0"
				cellspacing="0" class="tabout">
				<tr class="trcolor">
					<td colspan="4" class="tdc">
						<b><bean:write name="bean" property="title" />
						</b>
					</td>
				</tr>
				<tr class="trcolor">
					<td width="15%" class="tdr">
						<b>�� �� ��:</b>
					</td>
					<td width="35%" class="tdl">
						<bean:write name="bean" property="cerator" />
					</td>
					<td width="15%" class="tdr">
						<b>����ʱ��:</b>
					</td>
					<td width="35%" class="tdl">
						<bean:write name="bean" property="createdata" />
					</td>
				</tr>
				<tr class="trcolor">
					<td height="25" class="tdr">
						<b>������Դ:</b>
					</td>
					<td colspan="3" class="tdl">
						<bean:define id="state" name="bean" property="state"
							type="java.lang.String" />
						<logic:equal value="1" name="state">
               ��������
             </logic:equal>
						<logic:equal value="0" name="state">
               ���ɲ���
             </logic:equal>
						<logic:equal value="2" name="state">
              �Թ�����
             </logic:equal>
					</td>
				</tr>
				<tr class="trcolor">
					<td height="25" class="tdr">
						<b>��;��Ϣ:</b>
					</td>
					<td colspan="3" class="tdl">
						<bean:write name="bean" property="remark" />
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdr">
						<b>�������:</b>
					</td>
					<td colspan="3" class="tdl">
					</td>
				</tr>
				<tr class=trcolor>
					<td colspan="4" class="tdl" style="padding:10px;">
						<table width="100%" id="queryID" border="1"
							cellpadding="0" cellspacing="0"
							style="border-collapse: collapse;">
							<tr>
								<th width="20%" align="center">
									��������
								</th>
								<th width="20%" align="center">
									���Ϲ��
								</th>
								<th width="20%" align="center">
									��������
								</th>
								<th width="10%" align="center">
									���ϵ�λ
								</th>
								<th width="10%" align="center">
									��������
								</th>
								<th width="20%" align="center">
									��ŵ�ַ
								</th>

							</tr>
							<bean:define id="count" name="bean" property="count"
								type="java.lang.String[]" />
							<bean:define id="materialid" name="bean" property="materialid"
								type="java.lang.String[]" />
							<bean:define id="addressid" name="bean" property="addressid"
								type="java.lang.String[]" />
							<bean:define id="modelname" name="bean" property="modelname"
								type="java.lang.String[]" />
							<bean:define id="modelunit" name="bean" property="modelunit"
								type="java.lang.String[]" />
							<bean:define id="typename" name="bean" property="typename"
								type="java.lang.String[]" />
							<%
								for (int i = 0; i < count.length; i++) {
							%>
							<tr>
								<td><%=typename[i]%></td>
								<td><%=modelname[i]%></td>
								<td><%=materialid[i]%></td>
								<td><%=modelunit[i]%></td>
								<td><%=count[i]%></td>
								<td><%=addressid[i]%></td>

							</tr>
							<%
								}
							%>
						</table>
					</td>
				</tr>
				<logic:present name="lbean">
					<bean:define id="lastate" name="lbean" property="astate"
						type="java.lang.String[]" />
					<bean:define id="laremark" name="lbean" property="aremark"
						type="java.lang.String[]" />
					<bean:define id="lcontractorname" name="lbean"
						property="contractorname" type="java.lang.String[]" />
					<bean:define id="lassesor" name="lbean" property="assesor"
						type="java.lang.String[]" />
					<bean:define id="lassessdate" name="lbean" property="assessdate"
						type="java.lang.String[]" />
					<%
						for (int i = 0; i < lastate.length; i++) {
					%>
					<tr class=trcolor>
						<td colspan="4" class="tdl" style="padding:10px">
							<table width="100%" border="1" align="center"
							cellpadding="0" cellspacing="0"
							style="border-collapse: collapse;">
								<tr>
									<td height="25" width="15%" class="tdr">
										<b>�ƶ�����:</b>
									</td>
									<td class="tdl">
										<%=lastate[i]%>
									</td>
									<td width="15%" class="tdr">
										<b>�ƶ�����ʱ��:</b>
									</td>
									<td class="tdl">
										<%=lassessdate[i]%>
									</td>
								</tr>
								<tr>
									<td class="tdr">
										<b>�ƶ�������λ:</b>
									</td>
									<td class="tdl">
										<%=lcontractorname[i]%>
									</td>
									<td class="tdr">
										<b>�ƶ�������:</b>
									</td>
									<td class="tdl">
										<%=lassesor[i]%>
									</td>
								</tr>
								<tr>
									<td height="25" class="tdr">
										<b>�ƶ��������:</b>
									</td>
									<td colspan="3" class="tdl">
										<%
											String remark = laremark[i];
														if (remark == null) {
															remark = "";
														}
										%>
										<%=remark%>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<%
						}
					%>
				</logic:present>
			</table>
			</center>
			<p align="center">
			<!-- 	<input type="button" class="button"
					onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"
					value="����">
					-->
					<input type="button" class="button" onclick="history.back()" value="����"/>
			</p>
		</html:form>
	</body>
</html>
