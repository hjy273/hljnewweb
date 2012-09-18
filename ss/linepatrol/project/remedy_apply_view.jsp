<%@include file="/common/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>�鿴��������</title>
		<link href="${ctx}/css/linepatrol_css.css" rel="stylesheet"
			type="text/css">

		<script type='text/javascript'
			src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<link rel='stylesheet' type='text/css'
			href='${ctx}/js/extjs/resources/css/ext-all.css' />

		<script type="text/javascript">
			var win;
			function showWin(url){
				win = new Ext.Window({
					layout : 'fit',
					width:600,
					height:400,
					resizable:true,
					closeAction : 'close',
					modal:true,
					autoScroll:true,
					autoLoad:{url:url, scripts:true},
					plain: true
				});
				win.show(Ext.getBody());
			}
			function closeProcessWin(){
				win.close();
			}
			addGoBackMod=function() {
				var url = "<%=session.getAttribute("S_BACK_URL")%>";
		self.location.replace(url);
		//history.back();
	}
	function his(id) {
		var url = "${ctx}/process_history.do?method=showProcessHistoryList&object_type=project&is_close=0&object_id="
				+ id;
		showWin(url);
	}
</script>
	</head>
	<body>
		<br>
		<%
			Map applyMap = (Map) request.getAttribute("apply_map");
			pageContext.setAttribute("one_apply", applyMap.get("one_apply"));
			//pageContext.setAttribute("approve_list", applyMap.get("approve_list"));
			pageContext.setAttribute("apply_item_list", applyMap
					.get("apply_item_list"));
		%>
		<template:titile value="�鿴��������" />
		<table width="98%" align="center" border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse;">
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
					���������ܣ�
				</td>
				<td colspan="3" class="tdl" style="padding:10px;">
					<table id="itemTable" width="100%" border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse;">
						<tr class=trcolor>
							<td style="text-align: center;">
								��Ŀ����
							</td>
						</tr>
						<!-- 
						<tr class=trcolor>
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
								<tr class=trcolor>
									<td style="text-align: center; width: 150;">
										<bean:write name="oneApplyItem" property="itemname" />
									</td>
									<!-- 
									<td style="text-align: center; width: 150;">
										<bean:write name="oneApplyItem" property="typename" />
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
				<td colspan=4 style="padding:10px;" class="tdc">
					<apptag:materialselect label="ʹ�ò���" materialUseType="Use"
						objectId="${one_apply.id}" useType="project_remedy"
						displayType="view" />
				</td>
			</tr>
			<tr class=trcolor>
				<td colspan=4 style="padding:10px;" class="tdc">
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
			<tr class=trcolor>
				<td class="tdr">
					������
				</td>
				<td colspan="3" class="tdl">
					<apptag:upload state="look" cssClass="" entityId="${one_apply.id}"
						entityType="LP_REMEDY" />
				</td>
			</tr>
			<logic:notEmpty property="cancelUserId" name="one_apply">
				<tr class=trcolor>
					<td class="tdr">
						ȡ���ˣ�
					</td>
					<td class="tdl">
						<bean:write property="cancelUserName" name="one_apply" />
					</td>
					<td class="tdr">
						ȡ��ʱ�䣺
					</td>
					<td class="tdl">
						<bean:write property="cancelTime" name="one_apply" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						ȡ��ԭ��
					</td>
					<td class="tdl" colspan="3">
						<bean:write property="cancelReason" name="one_apply" />
					</td>
				</tr>
			</logic:notEmpty>
			<tr class=trcolor>
					<apptag:approve labelClass="tdr" displayType="view" colSpan="4"
						objectId="${one_apply.id}" objectType="LP_REMEDY_APPROVE" />
			</tr>
		</table>
		<apptag:appraiseDailyDaily businessId="${one_apply.id}" contractorId="${one_apply.contractorId}" businessModule="project" tableStyle="width:98%;border-top:0px;" displayType="view"></apptag:appraiseDailyDaily>
		<apptag:appraiseDailySpecial businessId="${one_apply.id}" contractorId="${one_apply.contractorId}" businessModule="project" tableStyle="width:98%;border-top:0px;" displayType="view"></apptag:appraiseDailySpecial>
			<table align="center" border="0">
				<tr>
				<c:if test="${type eq 'win'}">
					<td>
						<input type="button" value="�ر�" class="button"
							onclick="parent.close();">
					</td>
				</c:if>
				<c:if test="${type eq 'view'}">
					<td>
						<input type="button" value="����" class="button"
							onclick="addGoBackMod();">
					</td>
				</c:if>
				<c:if test="${empty type}">
					<td>
						<input type="button" class="button" value="������ʷ"
							onclick="his('${one_apply.id}')" />
					</td>
					<td>
						<input type="button" value="����" class="button"
							onclick="addGoBackMod();">
					</td>
				</c:if>
			</tr>
		</table>
	</body>
</html>
