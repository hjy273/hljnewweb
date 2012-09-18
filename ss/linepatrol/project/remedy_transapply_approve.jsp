<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>������������</title>
		<link href="${ctx}/css/linepatrol_css.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/validate.js"></script>
		<script type="text/javascript">
	goBack = function() {
		var url = '${sessionScope.S_BACK_URL}';
		self.location.replace(url);
	}
</script>
	</head>
	<body>
		<br>
		<%
			Map applyMap = (Map) request.getAttribute("apply_map");
			pageContext.setAttribute("one_apply", applyMap.get("one_apply"));
			pageContext.setAttribute("apply_item_list", applyMap
					.get("apply_item_list"));
		%>
		<template:titile value="��������ת��" />
		<table width="98%" align="center" width="100%" border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse;">
			<html:form
				action="/project/remedy_apply_approve.do?method=approveApply"
				styleId="addForm">
				<input type="hidden" name="apply_id" value="${one_apply.id}">
				<tr class=trcolor>
					<td class="tdr" width="15%">
						��ţ�
					</td>
					<td class="tdl" width="35%">
						<bean:write property="remedyCode" name="one_apply" />
					</td>
					<td class="tdr" width="15%">
						ά����λ��
					</td>
					<td class="tdl" width="35%">
						<apptag:assorciateAttr table="contractorinfo"
							label="contractorname" key="contractorid"
							keyValue="${one_apply.contractorId}" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						��Ŀ���ƣ�
					</td>
					<td class="tdl">
						<bean:write property="projectName" name="one_apply" />
					</td>
					<td class="tdr">
						�����ص㣺
					</td>
					<td class="tdl">
						<bean:write property="remedyAddress" name="one_apply" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						����ʱ�䣺
					</td>
					<td class="tdl" colspan=3>
						<bean:write property="remedyDate" name="one_apply" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						ԭ��˵����
					</td>
					<td class="tdl">
						<bean:write property="remedyReason" name="one_apply" />
					</td>
					<td class="tdr">
						��������
					</td>
					<td class="tdl">
						<bean:write property="remedySolve" name="one_apply" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						������
					</td>
					<td colspan="3" class="tdl">
						<apptag:upload state="look" cssClass="" entityId="${one_apply.id}"
							entityType="LP_REMEDY" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						���������ܣ�
					</td>
					<td colspan="3" class="tdl" style="padding: 10px;">
						<table id="itemTable" width="100%" width="100%" border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse;">
							<tr>
								<td style="text-align: center; width: 300;">
									��Ŀ����
								</td>
								<!-- 
								<td rowspan="2" style="text-align: center; width: 100;">
									��λ
								</td>
								<td style="text-align: center; width: 100;">
									������
								</td>
								 -->
							</tr>
							<!-- 
							<tr>
								<td style="text-align: center; width: 150;">
									��Ŀ
								</td>
								<td style="text-align: center; width: 150;">
									���
								</td>
							</tr>
							 -->
							<logic:notEmpty name="apply_item_list">
								<logic:iterate id="oneApplyItem" name="apply_item_list">
									<tr>
										<td style="text-align: center; width: 150;">
											<bean:write name="oneApplyItem" property="itemname" />
										</td>
										<!-- 
										<td style="text-align: center; width: 150;">
											<bean:write name="oneApplyItem" property="typename" />
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
										 -->
									</tr>
								</logic:iterate>
							</logic:notEmpty>
						</table>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						���̷��úϼƣ�Ԫ����
					</td>
					<td colspan="3" class="tdl">
						<bean:write property="totalFee" name="one_apply" />
						Ԫ
					</td>
				</tr>
				<tr class=trcolor>
					<td colspan="4" style="padding:10px;" class="tdc">
						<apptag:materialselect label="ʹ�ò���" materialUseType="Use"
							objectId="${one_apply.id}" useType="project_remedy"
							displayType="view" />
						<!-- 
						<table border="0" cellpadding="0" cellspacing="0" width="100%">
							<tr class=trcolor>
								<td class="tdr">
									ʹ�ò��Ϻϼƣ�Ԫ����
								</td>
								<td class="tdl">
									<bean:write property="mtotalFee" name="one_apply" />Ԫ
								</td>
							</tr>
						</table>
						-->
					</td>
				</tr>
				<tr class=trcolor>
					<td colspan=4 style="padding: 10px;" class="tdc">
						<apptag:materialselect label="���ղ���" displayType="view"
							materialUseType="Recycle" objectId="${one_apply.id}"
							useType="project_remedy" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						���Ϸ��úϼƣ�Ԫ����
					</td>
					<td colspan="3" class="tdl">
						<bean:write property="mtotalFee" name="one_apply" />
						Ԫ
					</td>
				</tr>
				<input type="hidden" name="approveResult" value="2">
				<tr class="trcolor">
					<apptag:approverselect label="ת����" inputName="approver"
						spanId="approverSpan" inputType="radio" />
				</tr>
		</table>
		<table align="center" border="0">
			<tr>
				<td>
					<html:submit property="action" styleClass="button">�ύ</html:submit>
				</td>
				<td>
					<html:button property="action" styleClass="button"
						onclick="goBack();">����</html:button>
				</td>
				<input type="hidden" name="operator" value="transcation" />
			</tr>
		</table>
		</html:form>
		</table>
	</body>
	<script type="text/javascript">
	jQuery(document).ready(function() {
	jQuery("#addForm").bind("submit",function(){processBar(addForm);});
})
	</script>
</html>