<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>验收修缮申请</title>
		<link href="${ctx}/css/linepatrol_css.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/tableoperate.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/validate.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/prototype.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/remedy/remedy_operate.js"></script>
		<script type="text/javascript">
		var regx=/^\d+(\.\d+)?$/
		submitCheck=function(){
			var addForm=document.getElementById("addForm");
			var flag=judgeEmptyValue(addForm.totalFee,"合计");
			flag=flag&&judgeEmptyValue(addForm.nextProcessMan,"提交结算人");
			flag=flag&&judgeValueIsFloat(addForm.totalFee,"合计必须为数字！");
			
			if(addForm.remark.value!=""){
				flag=flag&&judgeValueLength(addForm.remark,1000,"验收备注");
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
			int i = 0;
			Map applyMap = (Map) request.getAttribute("apply_map");
			pageContext.setAttribute("one_apply", applyMap.get("one_apply"));
			pageContext.setAttribute("reason_file_list", applyMap
					.get("reason_file_list"));
			pageContext.setAttribute("solve_file_list", applyMap
					.get("solve_file_list"));
			pageContext.setAttribute("approve_list", applyMap
					.get("approve_list"));
			pageContext
					.setAttribute("approve_map", applyMap.get("approve_map"));
			pageContext.setAttribute("apply_item_list", applyMap
					.get("apply_item_list"));
			pageContext.setAttribute("apply_material_list", applyMap
					.get("apply_material_list"));
		%>
		<template:titile value="验收修缮申请" />
		<html:form action="/remedy_apply_check.do?method=checkApply"
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
								<td>
									项目名称
								</td>
								<td>
									<bean:write property="projectName" name="one_apply" />
								</td>
								<td>
									维护单位
								</td>
								<td>
									<bean:write property="contractorName" name="one_apply" />
								</td>
							</tr>
							<tr>
								<td>
									发生地点
								</td>
								<td>
									<bean:write property="remedyAddress" name="one_apply" />
								</td>
								<td>
									申请时间
								</td>
								<td>
									<bean:write property="remedyDate" name="one_apply" />
								</td>
							</tr>
							<tr>
								<td>
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
						<select id="sItemType" name="sItemType"
							style="width: 125; display: none;">
							<logic:notEmpty name="item_type_list">
								<logic:iterate id="oneItemType" name="item_type_list">
									<option
										value="<bean:write property="typename" name="oneItemType" />"
										varId="<bean:write property="id" name="oneItemType" />"
										varRefId="<bean:write property="remedyitemid" name="oneItemType" />"
										varUnit="<bean:write property="unit" name="oneItemType" />"
										varUnitPrice="<bean:write property="price" name="oneItemType" />">
										<bean:write property="typename" name="oneItemType" />
									</option>
								</logic:iterate>
							</logic:notEmpty>
						</select>
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
									预算费用（元）
								</td>
								<td rowspan="2" style="text-align: center; width: 50;">
									操作
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
								<%
									i = 1;
								%>
								<logic:iterate id="oneApplyItem" name="apply_item_list">
									<tr id="<%=i%>">
										<td style="text-align: center; width: 150;">
											<input id="itemId_<%=i%>" name="itemId" type="hidden"
												value="<bean:write name="oneApplyItem" property="item_id" />" />
											<input id="itemTypeId_<%=i%>" name="itemTypeId" type="hidden"
												value="<bean:write name="oneApplyItem" property="remedyitemtypeid" />" />
											<select id="itemName_<%=i%>" name="itemName"
												style="width: 125;"
												onchange="changeItemType('itemName','sItemType','itemType','<%=i%>');calculateOne('<%=i%>');calculateAll();">
												<logic:notEmpty name="item_list">
													<logic:iterate id="oneItem" name="item_list">
														<option
															value="<bean:write property="itemname" name="oneItem" />"
															varId="<bean:write property="id" name="oneItem" />">
															<bean:write property="itemname" name="oneItem" />
														</option>
													</logic:iterate>
												</logic:notEmpty>
											</select>
										</td>
										<td style="text-align: center; width: 150;">
											<select id="itemType_<%=i%>" name="itemType"
												style="width: 125;"
												onchange="displayUnit('itemType','<%=i%>');calculateOne('<%=i%>');calculateAll();">
												<option value="">
													请选择
												</option>
											</select>
										</td>
										<td style="text-align: center;">
											<input id="itemUnitPrice_<%=i%>" name="itemUnitPrice"
												type="hidden"
												value="<bean:write name="oneApplyItem" property="price" />" />
											<span id="itemUnitPriceDis_<%=i%>"><bean:write
													name="oneApplyItem" property="price" /> </span>
										</td>
										<td style="text-align: center;">
											<input id="itemUnit_<%=i%>" name="itemUnit" type="hidden"
												value="<bean:write name="oneApplyItem" property="item_unit" />" />
											<span id="itemUnitDis_<%=i%>"><bean:write
													name="oneApplyItem" property="item_unit" /> </span>
										</td>
										<td style="text-align: center;">
											<input id="itemWorkNumber_<%=i%>" name="itemWorkNumber"
												type="text" style="width: 80;"
												value="<bean:write name="oneApplyItem" property="remedyload" />"
												onblur="calculateOne('<%=i%>');calculateAll();" />
										</td>
										<td style="text-align: center;">
											<input id="itemFee_<%=i%>" name="itemFee" type="hidden"
												value="<bean:write name="oneApplyItem" property="remedyfee" />" />
											<span id="itemFeeDis_<%=i%>"><bean:write
													name="oneApplyItem" property="remedyfee" /> </span>
										</td>
										<td style="text-align: center;">
											<input id="btnDel" name="btnDel" value="删除" type="button"
												onclick="deleteTbRow('itemTable','sampleItemTable',<%=i%>);calculateAll();" />
										</td>
									</tr>
									<%
										i++;
									%>
								</logic:iterate>
								<%
									i = 1;
								%>
								<logic:iterate id="oneItem" name="apply_item_list">
									<script type="text/javascript">
										if(typeof(document.forms[0].elements["itemFee"].length)=="undefined"){
											document.forms[0].elements["itemName"].value='<bean:write name="oneItem" property="itemname" />';
											changeItemType('itemName','sItemType','itemType','1');
											document.forms[0].elements["itemType"].value='<bean:write name="oneItem" property="typename" />';
											displayUnit('itemType','1');
										}else{
											document.forms[0].elements["itemName"][<%=(i - 1)%>].value='<bean:write name="oneItem" property="itemname" />';
											changeItemType('itemName','sItemType','itemType','<%=i%>');
											document.forms[0].elements["itemType"][<%=(i - 1)%>].value='<bean:write name="oneItem" property="typename" />';
											displayUnit('itemType','<%=i%>');
										}
									</script>
									<%
										i++;
									%>
								</logic:iterate>
							</logic:notEmpty>
						</table>
						<table id="sampleItemTable" border="0" cellpadding="0"
							cellspacing="0" width="100%" style="display: none;">
							<tr>
								<td style="text-align: center;">
									<input id="sample_itemId_row0" name="sample_itemId" value=""
										type="hidden" />
									<input id="sample_itemTypeId_row0" name="sample_itemTypeId"
										value="" type="hidden" />
									<select id="sample_itemName_row0" name="sample_itemName"
										style="width: 125;"
										onchange="changeItemType('itemName','sItemType','itemType','row0');calculateOne('row0');calculateAll();">
										<option value="">
											请选择
										</option>
										<logic:notEmpty name="item_list">
											<logic:iterate id="oneItem" name="item_list">
												<option
													value="<bean:write property="itemname" name="oneItem" />"
													varId="<bean:write property="id" name="oneItem" />">
													<bean:write property="itemname" name="oneItem" />
												</option>
											</logic:iterate>
										</logic:notEmpty>
									</select>
								</td>
								<td style="text-align: center;">
									<select id="sample_itemType_row0" name="sample_itemType"
										style="width: 125;"
										onchange="displayUnit('itemType','row0');calculateOne('row0');calculateAll();">
										<option value="">
											请选择
										</option>
									</select>
								</td>
								<td style="text-align: center;">
									<input id="sample_itemUnitPrice_row0"
										name="sample_itemUnitPrice" type="hidden" value="" />
									<span id="sample_itemUnitPriceDis_row0"></span>
								</td>
								<td style="text-align: center;">
									<input id="sample_itemUnit_row0" name="sample_itemUnit"
										type="hidden" value="" />
									<span id="sample_itemUnitDis_row0"></span>
								</td>
								<td style="text-align: center;">
									<input id="sample_itemWorkNumber_row0"
										name="sample_itemWorkNumber" type="text" style="width: 80;"
										onblur="calculateOne('row0');calculateAll();" />
								</td>
								<td style="text-align: center;">
									<input id="sample_itemFee_row0" name="sample_itemFee"
										type="hidden" />
									<span id="sample_itemFeeDis_row0"></span>
								</td>
								<td style="text-align: center;">
									<input id="btnDel" name="btnDel" value="删除" type="button"
										onclick="deleteTbRow('itemTable','sampleItemTable',row0);calculateAll();" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input id="btnAdd" name="btnAdd" value="添加定额项" type="button"
							onclick="addTbRow('itemTable','sampleItemTable');" class="button" />
					</td>
				</tr>
				<template:formTr name="合计（元）" style="background-color:#FFFFFF;">
					<html:hidden property="totalFee" name="one_apply"
						styleClass="inputtext" />
					<span id="total_fee"><bean:write property="totalFee"
							name="one_apply" />元 </span>
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
						<select id="sMaterialMode" name="sMaterialMode"
							style="width: 95; display: none;">
							<logic:notEmpty name="material_model_list">
								<logic:iterate id="oneMaterialMode" name="material_model_list">
									<option
										value="<bean:write property="id" name="oneMaterialMode" />"
										varRefId="<bean:write property="typeid" name="oneMaterialMode" />">
										<bean:write property="modelname" name="oneMaterialMode" />
									</option>
								</logic:iterate>
							</logic:notEmpty>
						</select>
						<select id="sMaterial" name="sMaterial"
							style="width: 95; display: none;">
							<logic:notEmpty name="material_list">
								<logic:iterate id="oneMaterial" name="material_list">
									<option value="<bean:write property="id" name="oneMaterial" />"
										varRefId="<bean:write property="modelid" name="oneMaterial" />">
										<bean:write property="name" name="oneMaterial" />
									</option>
								</logic:iterate>
							</logic:notEmpty>
						</select>
						<select id="sMaterialStorageAddr" name="sMaterialStorageAddr"
							style="width: 95; display: none;">
							<logic:notEmpty name="material_storage_list">
								<logic:iterate id="oneMaterialStorage"
									name="material_storage_list">
									<option
										value="<bean:write property="addressid" name="oneMaterialStorage" />"
										varRefId="<bean:write property="materialid" name="oneMaterialStorage" />"
										varOldStorage="<bean:write property="oldstock" name="oneMaterialStorage" />"
										varNewStorage="<bean:write property="newstock" name="oneMaterialStorage" />">
										<bean:write property="address" name="oneMaterialStorage" />
									</option>
								</logic:iterate>
							</logic:notEmpty>
						</select>
						<table id="materialTable" border="0" cellpadding="0"
							cellspacing="0" width="100%">
							<tr>
								<td colspan="3" style="text-align: center; width: 300;">
									材料
								</td>
								<td rowspan="2" style="text-align: center; width: 100;">
									存放地点
								</td>
								<td rowspan="2" style="text-align: center; width: 100;">
									材料库存类型
								</td>
								<td rowspan="2" style="text-align: center; width: 100;">
									库存量
								</td>
								<td rowspan="2" style="text-align: center; width: 100;">
									使用数量
								</td>
								<td rowspan="2" style="text-align: center; width: 50;">
									操作
								</td>
							</tr>
							<tr>
								<td style="text-align: center; width: 100;">
									类型
								</td>
								<td style="text-align: center; width: 100;">
									规格
								</td>
								<td style="text-align: center; width: 100;">
									名称
								</td>
							</tr>
							<logic:notEmpty name="apply_material_list">
								<%
									i = 1;
								%>
								<logic:iterate id="oneApplyMaterial" name="apply_material_list">
									<tr id="<%=i%>">
										<td style="text-align: center; width: 100;">
											<select id="materialType_<%=i%>" name="materialType"
												style="width: 95;"
												onchange="changeMaterialMode('materialType','sMaterialMode','materialMode','<%=i%>');">
												<option value="">
													请选择
												</option>
												<logic:notEmpty name="material_type_list">
													<logic:iterate id="oneMaterialType"
														name="material_type_list">
														<option
															value="<bean:write property="id" name="oneMaterialType" />">
															<bean:write property="typename" name="oneMaterialType" />
														</option>
													</logic:iterate>
												</logic:notEmpty>
											</select>
										</td>
										<td style="text-align: center; width: 100;">
											<select id="materialMode_<%=i%>" name="materialMode"
												style="width: 95;"
												onchange="changeMaterial('materialMode','sMaterial','material','<%=i%>');">
												<option value="">
													请选择
												</option>
											</select>
										</td>
										<td style="text-align: center; width: 100;">
											<select id="material_<%=i%>" name="material"
												style="width: 95;"
												onchange="changeMaterialStorage('material','sMaterialStorageAddr','materialStorageAddr','<%=i%>');">
												<option value="">
													请选择
												</option>
											</select>
										</td>
										<td style="text-align: center; width: 100;">
											<select id="materialStorageAddr_<%=i%>"
												name="materialStorageAddr" style="width: 95;"
												onchange="changeMaterialStorageType('<%=i%>');">
												<option value="">
													请选择
												</option>
											</select>
										</td>
										<td style="text-align: center; width: 100;">
											<select id="materialStorageType_<%=i%>"
												name="materialStorageType" style="width: 95;"
												onchange="changeMaterialStorageNumber('materialStorageType','<%=i%>');">
												<option value="">
													请选择
												</option>
												<option value="0">
													利旧材料
												</option>
												<option value="1">
													新增材料
												</option>
											</select>
										</td>
										<td style="text-align: center; width: 100;">
											<input id="materialStorageNumber_<%=i%>"
												name="materialStorageNumber" type="hidden" />
											<span id="materialStorageNumberDis_<%=i%>"></span>
										</td>
										<td style="text-align: center; width: 100;">
											<input id="materialUseNumber_<%=i%>" name="materialUseNumber"
												type="text" style="width: 80;"
												onblur="compareStorageNumber('<%=i%>');" />
										</td>
										<td style="text-align: center;">
											<input id="btnDel" name="btnDel" value="删除" type="button"
												onclick="deleteTbRow('materialTable','sampleMaterialTable',<%=i%>);calculateAll();" />
										</td>
									</tr>
									<%
										i++;
									%>
								</logic:iterate>
								<%
									i = 1;
								%>
								<logic:iterate id="oneApplyMaterial" name="apply_material_list">
									<script type="text/javascript">
										if(typeof(document.forms[0].elements["materialStorageNumber"].length)=="undefined"){
											document.forms[0].elements["materialType"].value='<bean:write name="oneApplyMaterial" property="typeid" />';
											changeMaterialMode('materialType','sMaterialMode','materialMode','1');
											document.forms[0].elements["materialMode"].value='<bean:write name="oneApplyMaterial" property="modelid" />';
											changeMaterial('materialMode','sMaterial','material','1');
											document.forms[0].elements["material"].value='<bean:write name="oneApplyMaterial" property="materialid" />';
											changeMaterialStorage('material','sMaterialStorageAddr','materialStorageAddr','1');
											document.forms[0].elements["materialStorageAddr"].value='<bean:write name="oneApplyMaterial" property="addressid" />';
											onchange="changeMaterialStorageType('1');"
											document.forms[0].elements["materialStorageType"].value='<bean:write name="oneApplyMaterial" property="storagetype" />';
											changeMaterialStorageNumber('materialStorageType','1');
											document.forms[0].elements["materialStorageNumber"].value='<bean:write name="oneApplyMaterial" property="storage_number" />';
											document.forms[0].elements["materialUseNumber"].value='<bean:write name="oneApplyMaterial" property="use_number" />';
										}else{
											document.forms[0].elements["materialType"][<%=(i - 1)%>].value='<bean:write name="oneApplyMaterial" property="typeid" />';
											changeMaterialMode('materialType','sMaterialMode','materialMode','<%=i%>');
											document.forms[0].elements["materialMode"][<%=(i - 1)%>].value='<bean:write name="oneApplyMaterial" property="modelid" />';
											changeMaterial('materialMode','sMaterial','material','<%=i%>');
											document.forms[0].elements["material"][<%=(i - 1)%>].value='<bean:write name="oneApplyMaterial" property="materialid" />';
											changeMaterialStorage('material','sMaterialStorageAddr','materialStorageAddr','<%=i%>');
											document.forms[0].elements["materialStorageAddr"][<%=(i - 1)%>].value='<bean:write name="oneApplyMaterial" property="addressid" />';
											onchange="changeMaterialStorageType('<%=(i - 1)%>');"
											document.forms[0].elements["materialStorageType"][<%=(i - 1)%>].value='<bean:write name="oneApplyMaterial" property="storagetype" />';
											changeMaterialStorageNumber('materialStorageType','<%=i%>');
											document.forms[0].elements["materialStorageNumber"][<%=(i - 1)%>].value='<bean:write name="oneApplyMaterial" property="storage_number" />';
											document.forms[0].elements["materialUseNumber"][<%=(i - 1)%>].value='<bean:write name="oneApplyMaterial" property="use_number" />';
										}
									</script>
									<%
										i++;
									%>
								</logic:iterate>
							</logic:notEmpty>
						</table>
						<table id="sampleMaterialTable" border="0" cellpadding="0"
							cellspacing="0" width="100%" style="display: none;">
							<tr>
								<td style="text-align: center;">
									<select id="sample_materialType_row0"
										name="sample_materialType" style="width: 95;"
										onchange="changeMaterialMode('materialType','sMaterialMode','materialMode','row0');">
										<option value="">
											请选择
										</option>
										<logic:notEmpty name="material_type_list">
											<logic:iterate id="oneMaterialType" name="material_type_list">
												<option
													value="<bean:write property="id" name="oneMaterialType" />">
													<bean:write property="typename" name="oneMaterialType" />
												</option>
											</logic:iterate>
										</logic:notEmpty>
									</select>
								</td>
								<td style="text-align: center;">
									<select id="sample_materialMode_row0"
										name="sample_materialMode" style="width: 95;"
										onchange="changeMaterial('materialMode','sMaterial','material','row0');">
										<option value="">
											请选择
										</option>
									</select>
								</td>
								<td style="text-align: center;">
									<select id="sample_material_row0" name="sample_material"
										style="width: 95;"
										onchange="changeMaterialStorage('material','sMaterialStorageAddr','materialStorageAddr','row0');">
										<option value="">
											请选择
										</option>
									</select>
								</td>
								<td style="text-align: center;">
									<select id="sample_materialStorageAddr_row0"
										name="sample_materialStorageAddr" style="width: 95;"
										onchange="changeMaterialStorageType('row0');">
										<option value="">
											请选择
										</option>
									</select>
								</td>
								<td style="text-align: center;">
									<select id="sample_materialStorageType_row0"
										name="sample_materialStorageType" style="width: 95;"
										onchange="changeMaterialStorageNumber('materialStorageType','row0');">
										<option value="">
											请选择
										</option>
										<option value="0">
											利旧材料
										</option>
										<option value="1">
											新增材料
										</option>
									</select>
								</td>
								<td style="text-align: center;">
									<input id="sample_materialStorageNumber_row0"
										name="sample_materialStorageNumber" type="hidden" />
									<span id="sample_materialStorageNumberDis_row0"></span>
								</td>
								<td style="text-align: center;">
									<input id="sample_materialUseNumber_row0"
										name="sample_materialUseNumber" type="text" style="width: 80;"
										onblur="compareStorageNumber('row0');" />
								</td>
								<td style="text-align: center;">
									<input id="btnDel" name="btnDel" value="删除" type="button"
										onclick="deleteTbRow('materialTable','sampleMaterialTable',row0);" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input id="btnAddMaterial" name="btnAddMaterial" value="添加材料项"
							type="button"
							onclick="addTbRow('materialTable','sampleMaterialTable');"
							class="button" />
					</td>
				</tr>
				<logic:notEmpty name="approve_map">
					<tr>
						<td colspan="2">
							<b>审批信息</b>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table border="0" cellpadding="0" cellspacing="0" width="100%">
								<logic:iterate id="oneApproveMap" name="approve_map">
									<bean:define id="approveKey" name="oneApproveMap"
										property="key" />
									<bean:define id="approveList" name="oneApproveMap"
										property="value" />
									<logic:notEmpty name="approveKey">
										<logic:notEqual value="000" name="approveKey">
											<tr>
												<td align="center" style="width: 30%">
													<logic:equal value="101" name="approveKey">
													监理审批意见：
												</logic:equal>
													<logic:equal value="102" name="approveKey">
													区域审核人审批意见：
												</logic:equal>
													<logic:equal value="103" name="approveKey">
													室经理审批意见：
												</logic:equal>
													<logic:equal value="104" name="approveKey">
													中心经理/副经理审批意见：
												</logic:equal>
													<logic:equal value="105" name="approveKey">
													公司领导审批意见：
												</logic:equal>
												</td>
												<td style="width: 70%">
													<table width="100%">
														<logic:iterate id="oneApprove" name="oneApproveMap"
															property="value">
															<tr>
																<bean:define id="applyResult" name="oneApprove"
																	property="approve_result" />
																<logic:equal value="审批通过" name="applyResult">
																	<td>
																		<font color="blue">审批通过</font>
																	</td>
																</logic:equal>
																<logic:equal value="审批不通过" name="applyResult">
																	<td>
																		<font color="red">审批不通过</font>
																	</td>
																</logic:equal>
															</tr>
															<tr>
																<td>
																	<bean:write name="oneApprove" property="remark" />
																</td>
															</tr>
															<tr>
																<td align="right">
																	<bean:write name="oneApprove" property="username" />
																	&nbsp;&nbsp;&nbsp;&nbsp;
																	<bean:write name="oneApprove" property="assessdate" />
																</td>
															</tr>
														</logic:iterate>
													</table>
												</td>
											</tr>
										</logic:notEqual>
									</logic:notEmpty>
								</logic:iterate>
							</table>
						</td>
					</tr>
				</logic:notEmpty>
				<template:formTr name="验收备注" style="background-color:#FFFFFF;">
					<html:textarea property="remark" styleClass="inputtextarea"
						style="width:215;" rows="5" />
				</template:formTr>
				<template:formTr name="提交结算人" style="background-color:#FFFFFF;">
					<logic:notEmpty name="next_process_man_list">
						<select name="nextProcessMan" style="width: 215;">
							<logic:iterate id="oneProcessMan" name="next_process_man_list">
								<option
									value="<bean:write property="userid" name="oneProcessMan" />">
									<bean:write property="username" name="oneProcessMan" />
								</option>
							</logic:iterate>
						</select>
					</logic:notEmpty>
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:button property="action" styleClass="button"
							onclick="submitCheck();">验收</html:button>
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
