<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript">
		listApproveInfo=function(){
			var url="${ctx}/material_put_storage.do?method=queryMaterialPutStorageApproveInfoList";
			var queryString="put_storage_id=${bean.id}";
			var actionUrl=url+"&&"+queryString+"&&rnd="+Math.random();
			var myAjax=new Ajax.Updater(
				"putStorageApproveInfoTd",actionUrl,{
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
		<template:titile value="查看材料入库单" />
		<center>
			<table width="90%" border="0" cellpadding="0" cellspacing="0"
				class="tabout">
				<tr class="trcolor">
					<td colspan="4" class="tdc">
						<b><bean:write name="applyBean" property="title" /> </b>
					</td>
				</tr>
				<tr class="trcolor">
					<td width="15%" class="tdr">
						<b>入库人:</b>
					</td>
					<td width="35%" class="tdl">
						<bean:write name="bean" property="creatorName" />
					</td>
					<td width="15%" class="tdr">
						<b>入库时间:</b>
					</td>
					<td width="35%" class="tdl">
						<bean:write name="bean" property="createDate" />
					</td>
				</tr>
				<tr class="trcolor">
					<td height="25" class="tdr">
						<b>材料来源:</b>
					</td>
					<td colspan="3" class="tdl">
						<bean:define id="state" name="applyBean" property="type"
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
				<tr class="trcolor">
					<td class="tdr">
						<b>入库材料:</b>
					</td>
					<td colspan="3" class="tdl">
					</td>
				</tr>
				<tr class=trcolor>
					<td colspan="4" class="tdl" style="padding: 10px;">
						<table width="100%" id="queryID" border="1" cellpadding="0"
							cellspacing="0" style="border-collapse: collapse;">
							<tr>
								<th width="15%" style="text-align:center">
									材料规格
								</th>
								<th width="15%" style="text-align:center">
									材料类型
								</th>
								<th width="20%" style="text-align:center">
									材料名称
								</th>
								<th width="10%" style="text-align:center">
									单位
								</th>
								<th width="10%" style="text-align:center">
									申请数量
								</th>
								<th width="10%" style="text-align:center">
									入库数量
								</th>
								<th width="20%" style="text-align:center">
									存放地址
								</th>
							</tr>
							<logic:notEmpty name="bean" property="count">
								<bean:define id="count" name="bean" property="count"
									type="java.lang.String[]" />
								<bean:define id="applyCount" name="bean" property="count"
									type="java.lang.String[]" />
								<bean:define id="materialid" name="bean" property="materialName"
									type="java.lang.String[]" />
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
									<td style="text-align: center"><%=modelname[i]%></td>
									<td style="text-align: center"><%=typename[i]%></td>
									<td style="text-align: center"><%=materialid[i]%></td>
									<td style="text-align: center"><%=modelunit[i]%></td>
									<td style="text-align: center"><%=applyCount[i]%></td>
									<td style="text-align: center"><%=count[i]%></td>
									<td style="text-align: center"><%=addressid[i]%></td>
								</tr>
								<%
									}
								%>
							</logic:notEmpty>
						</table>
					</td>
				</tr>
				<tr class=trcolor>
					<td colspan="4" id="putStorageApproveInfoTd" class="tdl" style="padding: 10px">
					</td>
				</tr>
			</table>
		</center>
		<p align="center">
			<input type="button" class="button"
				onclick="closeWin();"
				value="关闭">
		</p>
		<script type="text/javascript">
		listApproveInfo();
		</script>
	</body>
</html>
