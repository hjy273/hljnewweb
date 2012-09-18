<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
	</head>
	<link href="${ctx}/css/linepatrol_css.css" rel="stylesheet"
		type="text/css">
	<body>
		<logic:equal value="3" name="LOGIN_USER" property="deptype">
			<template:titile value="监理审批材料申请单" />
		</logic:equal>
		<logic:equal value="1" name="LOGIN_USER" property="deptype">
			<template:titile value="移动审批材料申请单" />
		</logic:equal>
		<html:form
			action="/LinePatrolManagerAction?method=addLinePatrolAssessInfo"
			styleId="myform">
			<input type="hidden" name="materialaddid"
				value="<bean:write name="bean" property="id"/>" />
			<input type="hidden" name="title"
				value="<bean:write name="bean" property="title"/>" />
			<input type="hidden" name="userid" value="" />
			<center>
				<table width="90%" border="0" cellpadding="0" cellspacing="0"
					class="tabout">
					<tr class="trcolor">
						<td colspan="4" class="tdc">
							<b><bean:write name="bean" property="title" /> </b>
						</td>
					</tr>
					<tr class=trcolor>
						<td width="15%" class="tdr">
							<b>申 请 人:</b>
						</td>
						<td class="tdl">
							<bean:write name="bean" property="cerator" />
						</td>
						<td width="15%" class="tdr">
							<b>申请时间:</b>
						</td>
						<td class="tdl">
							<bean:write name="bean" property="createdata" />
						</td>
					</tr>
					<tr class=trcolor>
						<td height="25" class="tdr">
							<b>材料来源:</b>
						</td>
						<td colspan="3" class="tdl">
							<bean:define id="state" name="bean" property="state"
								type="java.lang.String" />
							<logic:equal value="1" name="state">
               新增材料
             </logic:equal>
							<logic:equal value="0" name="state">
               利旧材料
             </logic:equal>
							<logic:equal value="2" name="state">
              自购材料
             </logic:equal>
						</td>
					</tr>
					<tr class=trcolor>
						<td height="25" class="tdr">
							<b>用途信息:</b>
						</td>
						<td colspan="3" class="tdl">
							<bean:write name="bean" property="remark" />
						</td>
					</tr>
					<tr class=trcolor>
						<td class="tdr">
							<b>申请材料:</b>
						</td>
						<td colspan="3" class="tdl">
						</td>
					</tr>
					<tr class=trcolor>
						<td colspan="4" class="tdl" style="padding: 10px;">
							<table width="100%" id="queryID" border="1" cellpadding="0"
								cellspacing="0" style="border-collapse: collapse;">
								<tr>
									<th width="20%" align="center">
										材料类型
									</th>
									<th width="20%" align="center">
										材料规格
									</th>
									<th width="20%" align="center">
										材料名称
									</th>
									<th width="10%" align="center">
										材料单位
									</th>
									<th width="10%" align="center">
										申请数量
									</th>
									<th width="20%" align="center">
										存放地址
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
									<td><%=materialid[i]%>--<%=typename[i]%></td>
									<td><%=modelname[i]%></td>
									<td><%=typename[i]%></td>
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
							<td colspan="4" class="tdl" style="padding: 10px">
								<table width="100%" border="1" align="center" cellpadding="0"
									cellspacing="0" style="border-collapse: collapse;">
									<tr>
										<td height="25" width="15%" class="tdr">
											<b>移动审批:</b>
										</td>
										<td width="35%" class="tdl">
											<%=lastate[i]%>
										</td>
										<td width="15%" class="tdr">
											<b>移动审批时间:</b>
										</td>
										<td width="35%" class="tdl">
											<%=lassessdate[i]%>
										</td>
									</tr>
									<tr>
										<td class="tdr">
											<b>移动审批单位:</b>
										</td>
										<td class="tdl">
											<%=lcontractorname[i]%>
										</td>
										<td class="tdr">
											<b>移动审批人:</b>
										</td>
										<td class="tdl">
											<%=lassesor[i]%>
										</td>
									</tr>
									<tr>
										<td height="25" class="tdr">
											<b>移动审批意见:</b>
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
					<tr class=trcolor>
						<td class="tdr">
							<b>审批单位:</b>
						</td>
						<td class="tdl">
							<bean:write name="deptname" />
						</td>
						<td class="tdr">
							<b>审批人:</b>
						</td>
						<td class="tdl">
							<bean:write name="username" />
						</td>
					</tr>
					<tr class=trcolor>
						<td height="25" class="tdr">
							<b>审批结果: </b>
						</td>
						<td colspan="5" class="tdl">
							<input value="1" type="radio" name="state" checked>
							通过审批
							<input value="0" type="radio" name="state">
							不予通过
						</td>
					</tr>
					<tr class=trcolor>
						<td height="25" class="tdr">
							<b>审批意见:</b>
						</td>
						<td colspan="3" class="tdl">
							<textarea rows="" cols="135" Class="inputtextarea" name="remark"></textarea>
						</td>
					</tr>
				</table>
			</center>
			<p align="center">
				<input type="submit" class="button" value="保存">
				<!-- <input type="button" class="button"
					onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"
					value="返回">-->
					<input type="button" class="button" onclick="history.back()" value="返回"/>
			</p>
		</html:form>
		<script language="JavaScript">
	function moveOption(e1, e2) {
		try {
			for ( var i = 0; i < e1.options.length; i++) {
				if (e1.options[i].selected) {
					var e = e1.options[i];
					e2.options.add(new Option(e.text, e.value));
					e1.remove(i);
					i = i - 1;
				}
			}
			document.myform.userids.value = getvalue(document.myform.list2);
		} catch (e) {
		}
	}
	function getvalue(geto) {
		var allvalue = "";
		for ( var i = 0; i < geto.options.length; i++) {
			if (i == geto.options.length - 1) {
				allvalue += "'" + geto.options[i].value + "'";
			} else {
				allvalue += "'" + geto.options[i].value + "',";
			}
		}
		return allvalue;
	}
	function moveAllOption(e1, e2) {
		try {
			for ( var i = 0; i < e1.options.length; i++) {
				var e = e1.options[i];
				e2.options.add(new Option(e.text, e.value));
				e1.remove(i);
				i = i - 1;
			}
			document.myform.userids.value = getvalue(document.myform.list2);
		} catch (e) {

		}
	}
	//-->
</script>
	</body>
</html>
