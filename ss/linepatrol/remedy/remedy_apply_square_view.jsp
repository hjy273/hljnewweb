<%@include file="/common/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>�鿴���ɽ�����Ϣ</title>
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
		    pageContext.setAttribute("one_balance", applyMap.get("one_balance"));
		    pageContext.setAttribute("reason_file_list", applyMap.get("reason_file_list"));
		    pageContext.setAttribute("solve_file_list", applyMap.get("solve_file_list"));
		    pageContext.setAttribute("approve_list", applyMap.get("approve_list"));
		    pageContext.setAttribute("approve_map", applyMap.get("approve_map"));
		    pageContext.setAttribute("check_list", applyMap.get("check_list"));
		    pageContext.setAttribute("square_list", applyMap.get("square_list"));
		    pageContext.setAttribute("apply_item_list", applyMap.get("balance_item_list"));
		    pageContext.setAttribute("apply_material_list", applyMap.get("balance_material_list"));
		%>
		<template:titile value="�鿴���ɽ�����Ϣ" />
		<table width="750" bgcolor="#FFFFFF" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr>
				<td colspan="4">
					��ţ�
					<bean:write property="remedyCode" name="one_apply" />
				</td>
			</tr>
			<tr>
				<td width="15%" align="center">
					��Ŀ����
				</td>
				<td width="35%">
					<bean:write property="projectName" name="one_apply" />
				</td>
				<td width="15%" align="center">
					ά����λ
				</td>
				<td width="35%">
					<bean:write property="contractorName" name="one_apply" />
				</td>
			</tr>
			<tr>
				<td align="center">
					�����ص�
				</td>
				<td>
					<bean:write property="remedyAddress" name="one_apply" />
				</td>
				<td align="center">
					����ʱ��
				</td>
				<td>
					<bean:write property="remedyDate" name="one_apply" />
				</td>
			</tr>
			<tr>
				<td align="center">
					����
				</td>
				<td colspan="3">
					<bean:write property="town" name="one_apply" />
				</td>
			</tr>
		</table>
		<table width="750" style="border-top: 0px;" bgcolor="#FFFFFF"
			align="center" cellpadding="3" cellspacing="0" class="tabout">
			<tr>
				<td colspan="2">
					<table id="itemTable" border="0" cellpadding="0" cellspacing="0"
						width="100%">
						<tr>
							<td colspan="2" style="text-align: center; width: 300;">
								��Ŀ����
							</td>
							<td rowspan="2" style="text-align: center; width: 100;">
								���ۣ�Ԫ��
							</td>
							<td rowspan="2" style="text-align: center; width: 100;">
								��λ
							</td>
							<td rowspan="2" style="text-align: center; width: 100;">
								������
							</td>
							<td rowspan="2" style="text-align: center; width: 100;">
								������ã�Ԫ��
							</td>
						</tr>
						<tr>
							<td style="text-align: center; width: 150;">
								��Ŀ
							</td>
							<td style="text-align: center; width: 150;">
								���
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
			<template:formTr name="�ϼƣ�Ԫ��" style="background-color:#FFFFFF;">
				<bean:write property="totalFee" name="one_balance" />Ԫ
			</template:formTr>
			<template:formTr name="ԭ��˵��" style="background-color:#FFFFFF;">
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
			<template:formTr name="������" style="background-color:#FFFFFF;">
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
								����
							</td>
							<td style="text-align: center; width: 100;">
								��ŵص�
							</td>
							<td style="text-align: center; width: 100;">
								���Ͽ������
							</td>
							<!-- 
							<td style="text-align: center; width: 75;">
								ԭ�п������
							</td>
							<td style="text-align: center; width: 75;">
								���п������
							</td>
							 -->
							<td style="text-align: center; width: 75;">
								ʹ������
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
										<bean:write property="use_number" name="oneApplyMaterial" />
									</td>
								</tr>
							</logic:iterate>
						</logic:notEmpty>
					</table>
				</td>
			</tr>
			<template:formTr name="����״̬" style="background-color:#FFFFFF;">
				<bean:write property="statusName" name="one_apply" />
			</template:formTr>
			<logic:notEmpty name="check_list">
				<tr>
					<td style="text-align: right;">
						������Ϣ��
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
			<logic:notEmpty name="square_list">
				<tr>
					<td style="text-align: right;">
						������Ϣ��
					</td>
					<td style="text-align: center;">
						<table border="0" cellpadding="0" cellspacing="0" width="100%">
							<logic:iterate id="oneSquare" name="square_list">
								<tr>
									<td style="text-align: left;">
										<bean:write name="oneSquare" property="remark" />
									</td>
								</tr>
								<tr>
									<td style="text-align: right;">
										<bean:write name="oneSquare" property="username" />
										<bean:write name="oneSquare" property="acceptdate" />
									</td>
								</tr>
							</logic:iterate>
						</table>
					</td>
				</tr>
			</logic:notEmpty>
			<template:formSubmit>
				<td>
					<input type="button" value="����" class="button"
						onclick="addGoBackMod();">
				</td>
			</template:formSubmit>
		</table>
	</body>
</html>
