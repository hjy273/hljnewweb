<%@include file="/common/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>查看修缮申请</title>
		<link href="${ctx}/css/linepatrol_css.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript">
		addGoBackMod=function() {
			var url = "<%=session.getAttribute("S_BACK_URL")%>";
			self.location.replace(url);
		}
		</script>

	</head>
	<body>
		<br>
		<%
		    Map applyMap = (Map) request.getAttribute("apply_map");
		    pageContext.setAttribute("one_apply", applyMap.get("one_apply"));
		    pageContext.setAttribute("reason_file_list", applyMap.get("reason_file_list"));
		    pageContext.setAttribute("solve_file_list", applyMap.get("solve_file_list"));
		    pageContext.setAttribute("approve_list", applyMap.get("approve_list"));
		    pageContext.setAttribute("approve_map", applyMap.get("approve_map"));
		    pageContext.setAttribute("check_list", applyMap.get("check_list"));
		    pageContext.setAttribute("square_list", applyMap.get("square_list"));
		    pageContext.setAttribute("apply_item_list", applyMap.get("apply_item_list"));
		    pageContext.setAttribute("apply_material_list", applyMap.get("apply_material_list"));
		%>
		<template:titile value="查看修缮申请" />
		<table width="98%" bgcolor="#FFFFFF" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr>
				<td colspan="2">
					<table width="100%">
						<tr>
							<td colspan="4">
								编号：
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
								预算费用（元）
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
			<template:formTr name="合计（元）" style="background-color:#FFFFFF;">
				<bean:write property="totalFee" name="one_apply" />元
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
							<td style="text-align: center; width: 225;">
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
							<td style="text-align: center; width: 75;">
								现有库存数量
							</td>
							 -->
							 <td style="text-align: center; width: 75;">
								单价
							</td>
							<td style="text-align: center; width: 75;">
								使用数量
							</td>
							<td style="text-align: center; width: 75;">
								总价
							</td>
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
									<td style="text-align: center;">
										<bean:write property="stock_number" name="oneApplyMaterial" />
									</td>
									 -->
									 <td style="text-align: center;">
										<bean:write property="price" name="oneApplyMaterial" />
									</td>
									<td style="text-align: center;">
										<bean:write property="use_number" name="oneApplyMaterial" />
									</td>
									<td style="text-align: center;">
										<bean:write property="totalprice" name="oneApplyMaterial" />
									</td>
								</tr>
							</logic:iterate>
						</logic:notEmpty>
					</table>
				</td>
			</tr>
			<template:formTr name="申请状态" style="background-color:#FFFFFF;">
				<bean:write property="statusName" name="one_apply" />
			</template:formTr>
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
								<bean:define id="approveKey" name="oneApproveMap" property="key" />
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
			<template:formSubmit>
				<td>
					<input type="button" value="返回" class="button"
						onclick="addGoBackMod();">
				</td>
			</template:formSubmit>
		</table>
	</body>
</html>
