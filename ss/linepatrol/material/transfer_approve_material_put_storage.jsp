<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript">
		checkForm=function(){
			var form=document.forms[0];
			if(form.approverId.value==""){
				alert("没有选择转审人！");
				return false;
			}
			return true;
		}
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
		<template:titile value="移动转批材料入库单" />
		<html:form action="/material_put_storage.do?method=approveMaterialPutStorage"
			onsubmit="return checkForm();">
			<center>
				<table width="90%" border="0" cellpadding="0" cellspacing="0"
					class="tabout">
					<tr class="trcolor">
						<td colspan="4" class="tdc">
							<b><bean:write name="applyBean" property="title" /> </b>
						</td>
					</tr>
					<tr class=trcolor>
						<td width="15%" class="tdr">
							<b>入库 人:</b>
						</td>
						<td class="tdl">
							<bean:write name="bean" property="creatorName" />
							<html:hidden property="title" name="applyBean" />
							<html:hidden property="creator" name="bean" />
							<input name="applyId"
								value="<bean:write name="applyBean" property="id" />" type="hidden" />
							<input name="putStorageId"
								value="<bean:write name="bean" property="id" />" type="hidden" />
							<input name="contractorId"
								value="<bean:write name="bean" property="contractorId" />" type="hidden" />
						</td>
						<td width="15%" class="tdr">
							<b>入库时间:</b>
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
					<tr class=trcolor>
						<td height="25" class="tdr">
							<b>用途信息:</b>
						</td>
						<td colspan="3" class="tdl">
							<bean:write name="applyBean" property="remark" />
						</td>
					</tr>
					<tr class=trcolor>
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
										入库数量
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
							id="putStorageApproveInfoTd">
						</td>
					</tr>
					<tr class=trcolor>
						<td class="tdr">
							<b>审核单位:</b>
						</td>
						<td class="tdl">
							${sessionScope.LOGIN_USER.deptName }
						</td>
						<td class="tdr">
							<b>审核人:</b>
						</td>
						<td class="tdl">
							${sessionScope.LOGIN_USER.userName }
						</td>
					</tr>
					<tr class=trcolor>
						<apptag:approverselect inputName="approverId" label="转审人"
							inputType="radio" colSpan="4" exceptSelf="true"
							objectId="${bean.id}" objectType="LP_MT_NEW"
							approverType="transfer"></apptag:approverselect>
					</tr>
					<tr class=trcolor>
						<td height="25" class="tdr">
							<b>转审意见:</b>
						</td>
						<td colspan="3" class="tdl">
							<html:hidden property="approveResult" value="2" />
							<textarea rows="" cols="135" Class="inputtextarea"
								name="approveRemark"></textarea>
						</td>
					</tr>
				</table>
			</center>
			<p align="center">
				<input type="submit" class="button" value="提交">
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
