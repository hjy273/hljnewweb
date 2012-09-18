<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>材料盘点申请</title>
		<%
		    UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		    String deptid = user.getDeptID();
		%>
		<link href="${ctx}/css/linepatrol_css.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript"
			src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
  		function checkAddInfo() {
  			if(addMtUsedApplyForm.userids.value==""){
  				alert("没有选择审核人！");
  				return false;
  			}else{
  				addMtUsedApplyForm.submit();
  			}
  		}
  		function openWindow(materialId,seqId){
  			var url="${ctx}/materialUsedInfoAction.do?method=getMarterialInfos&&material_id="+materialId+"&&contractor_id=<%=deptid%>&&seq_id="+seqId;
  			window.open(url,"xx","height=600,width=800,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=yes");
  		}
  		</script>
		<link type="text/css" rel="stylesheet"
			href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
		<style>
table {
	font-size: 12px;
}
</style>
	</head>
	<body>
		<%
		    pageContext.setAttribute("detail_storage_map", request
		            .getAttribute("detail_storage_map"));
		    session.setAttribute("STOCK_LIST",null);
		%>
		<template:titile value="材料盘点申请材料信息" />
		<html:form method="post"
			action="/mtUsedAction?method=addMtUsedApplyForm"
			styleId="addMtUsedApplyForm">
			<table width="750" align="center" cellpadding="0"
				cellspacing="0" class="tabout" border="1" style="border-collapse:collapse;">
				<tr>
					<td colspan="2">
						<table width="100%">
							<tr>
								<td>
									材料名称
								</td>
								<td>
									上月库存
								</td>
								<td>
									本月新增
								</td>
								<td>
									本月使用
								</td>
								<td>
									系统盘点库存
								</td>
								<td>
									实际库存
								</td>
							</tr>
							<%
							    int i = 0;
							%>
							<logic:iterate id="oneMap" name="detail_storage_map">
								<tr>
									<td>
										<input id="materialId" name="materialId" type="hidden"
											value="<bean:write name="oneMap" property="key" />" />
										<bean:write name="oneMap" property="value.material_name" />
									</td>
									<td>
										<input id="lastMonthStock" name="lastMonthStock" type="hidden"
											value="<bean:write name="oneMap" property="value.last_month_storage" />" />
										<bean:write name="oneMap" property="value.last_month_storage" />
									</td>
									<td>
										<input id="newAddedStock" name="newAddedStock" type="hidden"
											value="<bean:write name="oneMap" property="value.storage_number" />" />
										<bean:write name="oneMap" property="value.storage_number" />
									</td>
									<td>
										<input id="newUsedStock" name=newUsedStock "" type="hidden"
											value="<bean:write name="oneMap" property="value.used_number" />" />
										<bean:write name="oneMap" property="value.used_number" />
									</td>
									<td>
										<bean:write name="oneMap" property="value.have_number" />
									</td>
									<td>
										<input id="realStock_<%=i%>" name="realStock" type="hidden"
											value="<bean:write name="oneMap" property="value.real_number" />" />
										<a href="javascript:openWindow('<bean:write name="oneMap" property="key" />','<%=i%>');">
											<span id="real_stock_<%=i%>"><bean:write name="oneMap"
													property="value.real_number" />
										</span> </a>
									</td>
								</tr>
								<%
								    i++;
								%>
							</logic:iterate>
						</table>
					</td>
				</tr>
				<tr>
					<td style="width: 150px; text-align: right;">
						备注：
					</td>
					<td style="width: 600px;">
						<textarea name="remark" cols="30" rows="5"></textarea>
					</td>
				</tr>
				 <apptag:approverselect label="审核人" inputName="userids"
					spanId="useridsSpan" inputType="radio" />
				<template:formSubmit>
					<input id="createtime" name="createtime" type="hidden"
						value="<%=(String) request.getAttribute("month")%>" />
					<td>
						<html:button property="action" styleClass="button"
							onclick="checkAddInfo();">提交</html:button>
					</td>
					<td>
						<html:reset styleClass="button">重置</html:reset>
					</td>
				</template:formSubmit>
			</table>
		</html:form>
	</body>
</html>
