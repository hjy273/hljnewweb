<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>结算修缮申请</title>
		<link href="${ctx}/css/linepatrol_css.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/validate.js"></script>
		<script type="text/javascript">
		submitSquare=function(){
			var addForm=document.getElementById("addForm");
			var flag;
			if(addForm.remark.value!=""){
				flag=judgeValueLength(addForm.remark,1000,"结算备注");
			}else{
				flag=true;
			}
			if(flag){
				addForm.submit();
			}
		}
		goBack=function(){
			var url = '<%=(String) request.getSession().getAttribute("S_BACK_URL")%>';
			self.location.replace(url);
		}
		</script>
	</head>
	<body>
		<br>
		<%
		    Map applyMap = (Map) request.getAttribute("apply_map");
		    pageContext.setAttribute("one_apply", applyMap.get("one_apply"));
		    pageContext.setAttribute("one_balance", applyMap.get("one_balance"));
		    pageContext.setAttribute("reason_file_list", applyMap.get("reason_file_list"));
		    pageContext.setAttribute("solve_file_list", applyMap.get("solve_file_list"));
		    pageContext.setAttribute("approve_list", applyMap.get("approve_list"));
		    pageContext.setAttribute("approve_map", applyMap.get("approve_map"));
		    pageContext.setAttribute("check_list", applyMap.get("check_list"));
		    pageContext.setAttribute("apply_item_list", applyMap.get("balance_item_list"));
		    pageContext.setAttribute("apply_material_list", applyMap.get("balance_material_list"));
		%>
		<template:titile value="结算修缮申请" />
		<html:form action="/remedy_apply_square.do?method=squareApply"
			styleId="addForm">
			<table width="750" bgcolor="#FFFFFF" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr>
					<td colspan="2">
						<table width="100%">
							<tr>
								<td colspan="4">
									编号：
									<input name="apply_id"
										value="<bean:write property="id" name="one_apply" />"
										type="hidden" />
									<input name="applyState"
										value="<bean:write property="state" name="one_apply" />"
										type="hidden" />
									<bean:write property="remedyCode" name="one_apply" />
								</td>
							</tr>
							<tr>
								<td width="15%" align="center">
									项目名称
								</td>
								<td width="35%">
									<bean:write property="projectName" name="one_apply" />
								</td>
								<td width="15%" align="center">
									维护单位
								</td>
								<td width="35%">
									<bean:write property="contractorName" name="one_apply" />
								</td>
							</tr>
							<tr>
								<td align="center">
									发生地点
								</td>
								<td>
									<bean:write property="remedyAddress" name="one_apply" />
								</td>
								<td align="center">
									申请时间
								</td>
								<td>
									<bean:write property="remedyDate" name="one_apply" />
								</td>
							</tr>
							<tr>
								<td align="center">
									区县
								</td>
								<td colspan="3">
									<bean:write property="town" name="one_apply" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table id="itemTable" border="0" cellpadding="0" cellspacing="0"
							width="100%">
							<tr>
								<td colspan="2" style="text-align: center; width: 300;">
									项目名称
								</td>
								<td rowspan="2" style="text-align: center; width: 100;">
									单价（元）
								</td>
								<td rowspan="2" style="text-align: center; width: 100;">
									单位
								</td>
								<td rowspan="2" style="text-align: center; width: 100;">
									工作量
								</td>
								<td rowspan="2" style="text-align: center; width: 100;">
									结算费用（元）
								</td>
							</tr>
							<tr>
								<td style="text-align: center; width: 150;">
									项目
								</td>
								<td style="text-align: center; width: 150;">
									类别
								</td>
							</tr>
							<logic:notEmpty name="apply_item_list">
								<logic:iterate id="oneApplyItem" name="apply_item_list">
									<tr>
										<td style="text-align: center; width: 150;">
											<bean:write name="oneApplyItem" property="itemname" />
										</td>
										<td style="text-align: center; width: 150;">
											<bean:write name="oneApplyItem" property="typename" />
										</td>
										<td style="text-align: center; width: 100;">
											<bean:write name="oneApplyItem" property="price" />
										</td>
										<td style="text-align: center; width: 100;">
											<bean:write name="oneApplyItem" property="item_unit" />
										</td>
										<td style="text-align: center; width: 100;">
											<bean:write name="oneApplyItem" property="remedyload" />
										</td>
										<td style="text-align: center; width: 100;">
											<bean:write name="oneApplyItem" property="remedyfee" />
										</td>
									</tr>
								</logic:iterate>
							</logic:notEmpty>
						</table>
					</td>
				</tr>
				<template:formTr name="合计" style="background-color:#FFFFFF;">
					<bean:write property="totalFee" name="one_balance" />元
				</template:formTr>
				<template:formTr name="原因说明" style="background-color:#FFFFFF;">
					<bean:write property="remedyReason" name="one_apply" />
					<logic:empty name="reason_file_list">
						<apptag:listAttachmentLink fileIdList="" />
					</logic:empty>
					<logic:notEmpty name="reason_file_list">
						<bean:define id="temp" name="reason_file_list"
							type="java.lang.String" />
						<br>
						<apptag:listAttachmentLink fileIdList="<%=temp%>" />
					</logic:notEmpty>
				</template:formTr>
				<template:formTr name="处理方案" style="background-color:#FFFFFF;">
					<bean:write property="remedySolve" name="one_apply" />
					<logic:empty name="solve_file_list">
						<apptag:listAttachmentLink fileIdList="" />
					</logic:empty>
					<logic:notEmpty name="solve_file_list">
						<bean:define id="temp" name="solve_file_list"
							type="java.lang.String" />
						<br>
						<apptag:listAttachmentLink fileIdList="<%=temp%>" />
					</logic:notEmpty>
				</template:formTr>
				<tr>
					<td colspan="2">
						<table id="materialTable" border="0" cellpadding="0"
							cellspacing="0" width="100%">
							<tr>
								<td style="text-align: center; width: 300;">
									材料
								</td>
								<td style="text-align: center; width: 100;">
									存放地点
								</td>
								<td style="text-align: center; width: 100;">
									材料库存类型
								</td>
								<!-- 
								<td style="text-align: center; width: 75;">
									原有库存数量
								</td>
								 -->
								<td style="text-align: center; width: 75;">
									使用数量
								</td>
								<!-- 
								<td style="text-align: center; width: 100;">
									库存是否充足
								</td>
								 -->
							</tr>
							<logic:notEmpty name="apply_material_list">
								<logic:iterate id="oneApplyMaterial" name="apply_material_list">
									<tr>
										<td style="text-align: center;">
											<bean:write property="material_name" name="oneApplyMaterial" />
										</td>
										<td style="text-align: center;">
											<bean:write property="address" name="oneApplyMaterial" />
										</td>
										<td style="text-align: center;">
											<bean:write property="storage_type" name="oneApplyMaterial" />
										</td>
										<!-- 
										<td style="text-align: center;">
											<bean:write property="storage_number" name="oneApplyMaterial" />
										</td>
										 -->
										<td style="text-align: center;">
											<bean:write property="use_number" name="oneApplyMaterial" />
										</td>
										<!-- 
										<td style="text-align: center;">
											<logic:equal value="+" property="is_enough_material"
												name="oneApplyMaterial">是</logic:equal>
											<logic:equal value="-" property="is_enough_material"
												name="oneApplyMaterial">
												<font color="#FF0000">否</font>
											</logic:equal>
										</td>
										 -->
									</tr>
								</logic:iterate>
							</logic:notEmpty>
						</table>
					</td>
				</tr>
				<logic:notEmpty name="check_list">
					<tr>
						<td style="text-align: right;">
							验收信息：
						</td>
						<td style="text-align: center;">
							<table border="0" cellpadding="0" cellspacing="0" width="100%">
								<logic:iterate id="oneCheck" name="check_list">
									<tr>
										<td style="text-align: left;">
											<bean:write name="oneCheck" property="remark" />
										</td>
									</tr>
									<tr>
										<td style="text-align: right;">
											<bean:write name="oneCheck" property="username" />
											<bean:write name="oneCheck" property="acceptdate" />
										</td>
									</tr>
								</logic:iterate>
							</table>
						</td>
					</tr>
				</logic:notEmpty>
				<template:formTr name="结算备注" style="background-color:#FFFFFF;">
					<html:textarea property="remark" styleClass="inputtextarea"
						style="width:215;" rows="5" />
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:button property="action" styleClass="button"
							onclick="submitSquare();">结算</html:button>
					</td>
					<td>
						<html:reset property="action" styleClass="button">重置</html:reset>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="goBack();">返回</html:button>
					</td>
				</template:formSubmit>
			</table>
		</html:form>
	</body>
</html>
