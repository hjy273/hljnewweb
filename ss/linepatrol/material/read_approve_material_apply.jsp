<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript">
		listApproveInfo=function(){
			var url="${ctx}/material_apply.do?method=queryMaterialApplyApproveInfoList";
			var queryString="apply_id=${bean.id}";
			var actionUrl=url+"&&"+queryString+"&&rnd="+Math.random();
			var myAjax=new Ajax.Updater(
				"approveInfoTd",actionUrl,{
					method:"post",
					evalScripts:true
				}
			);
		}
		</script>
	</head>
	<link href="${ctx}/css/linepatrol_css.css" rel="stylesheet"
		type="text/css">
	<body>
		<template:titile value="移动批阅材料申请单" />
		<html:form action="/material_apply.do?method=readApproveMaterialApply">
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
							<bean:write name="bean" property="creatorName" />
							<html:hidden property="title" name="bean" />
							<html:hidden property="creator" name="bean" />
							<input name="applyId"
								value="<bean:write name="bean" property="id" />" type="hidden" />
							<input name="contractorId"
								value="<bean:write name="bean" property="contractorId" />" type="hidden" />
						</td>
						<td width="15%" class="tdr">
							<b>申请时间:</b>
						</td>
						<td class="tdl">
							<bean:write name="bean" property="createDate" />
						</td>
					</tr>
					<tr class=trcolor>
						<td height="25" class="tdr">
							<b>材料来源:</b>
						</td>
						<td colspan="3" class="tdl">
							<bean:define id="state" name="bean" property="type"
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
									<th width="20%" style="text-align:center">
										材料类型
									</th>
									<th width="20%" style="text-align:center">
										材料规格
									</th>
									<th width="20%" style="text-align:center">
										材料名称
									</th>
									<th width="10%" style="text-align:center">
										材料单位
									</th>
									<th width="10%" style="text-align:center">
										申请数量
									</th>
									<th width="20%" style="text-align:center">
										存放地址
									</th>
								</tr>
								<logic:notEmpty name="bean" property="count">
									<bean:define id="count" name="bean" property="count"
										type="java.lang.String[]" />
									<bean:define id="materialid" name="bean"
										property="materialName" type="java.lang.String[]" />
									<bean:define id="addressid" name="bean" property="addressName"
										type="java.lang.String[]" />
									<bean:define id="modelname" name="bean"
										property="materialModelName" type="java.lang.String[]" />
									<bean:define id="modelunit" name="bean" property="materialUnit"
										type="java.lang.String[]" />
									<bean:define id="typename" name="bean"
										property="materialTypeName" type="java.lang.String[]" />
									<%
										for (int i = 0; i < count.length; i++) {
									%>
									<tr>
										<td><%=materialid[i]%></td>
										<td><%=modelname[i]%></td>
										<td><%=typename[i]%></td>
										<td><%=modelunit[i]%></td>
										<td><%=count[i]%></td>
										<td><%=addressid[i]%></td>
									</tr>
									<%
										}
									%>
								</logic:notEmpty>
							</table>
						</td>
					</tr>
					<tr class=trcolor>
						<td colspan="4" class="tdl" style="padding: 10px"
							id="approveInfoTd">
						</td>
					</tr>
				</table>
			</center>
			<p align="center">
				<input type="submit" class="button" value="已阅读">
			<!-- <input type="button" class="button"
					onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"
					value="返回">
					-->
					<input type="button" class="button" onclick="history.back()" value="返回"/>
			</p>
		</html:form>
		<script type="text/javascript">
		listApproveInfo();
		</script>
	</body>
</html>
