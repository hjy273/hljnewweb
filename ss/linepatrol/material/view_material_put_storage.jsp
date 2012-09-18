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
		<template:titile value="�鿴������ⵥ" />
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
						<b>�����:</b>
					</td>
					<td width="35%" class="tdl">
						<bean:write name="bean" property="creatorName" />
					</td>
					<td width="15%" class="tdr">
						<b>���ʱ��:</b>
					</td>
					<td width="35%" class="tdl">
						<bean:write name="bean" property="createDate" />
					</td>
				</tr>
				<tr class="trcolor">
					<td height="25" class="tdr">
						<b>������Դ:</b>
					</td>
					<td colspan="3" class="tdl">
						<bean:define id="state" name="applyBean" property="type"
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
					<td class="tdr">
						<b>������:</b>
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
									���Ϲ��
								</th>
								<th width="15%" style="text-align:center">
									��������
								</th>
								<th width="20%" style="text-align:center">
									��������
								</th>
								<th width="10%" style="text-align:center">
									��λ
								</th>
								<th width="10%" style="text-align:center">
									��������
								</th>
								<th width="10%" style="text-align:center">
									�������
								</th>
								<th width="20%" style="text-align:center">
									��ŵ�ַ
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
				value="�ر�">
		</p>
		<script type="text/javascript">
		listApproveInfo();
		</script>
	</body>
</html>
