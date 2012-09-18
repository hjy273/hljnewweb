<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>��ѯ����ͳ��</title>
		<script type="text/javascript"
			src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
		<link type="text/css" rel="stylesheet"
			href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
		<script type="text/javascript">
		displayDateQueryCondition=function(value){
			var yearTr=document.getElementById("yearConditionTr");
			var monthTr=document.getElementById("monthConditionTr");
			var quarterTr=document.getElementById("quarterConditionTr");
			var otherTr=document.getElementById("otherConditionTr");
			if(value=="YEAR"){
				yearTr.style.display="";
				monthTr.style.display="none";
				quarterTr.style.display="none";
				otherTr.style.display="none";
				materialStatForm.year.value="";
				materialStatForm.month.value="";
				materialStatForm.quarters.value="";
				materialStatForm.beginDate.value="";
				materialStatForm.endDate.value="";
			}
			if(value=="MONTH"){
				yearTr.style.display="none";
				monthTr.style.display="";
				quarterTr.style.display="none";
				otherTr.style.display="none";
				materialStatForm.year.value="";
				materialStatForm.month.value="";
				materialStatForm.quarters.value="";
				materialStatForm.beginDate.value="";
				materialStatForm.endDate.value="";
			}
			if(value=="QUARTER"){
				yearTr.style.display="none";
				monthTr.style.display="none";
				quarterTr.style.display="";
				otherTr.style.display="none";
				materialStatForm.year.value="";
				materialStatForm.month.value="";
				materialStatForm.quarters.value="";
				materialStatForm.beginDate.value="";
				materialStatForm.endDate.value="";
			}
			if(value=="OTHER"){
				yearTr.style.display="none";
				monthTr.style.display="none";
				quarterTr.style.display="none";
				otherTr.style.display="";
				materialStatForm.year.value="";
				materialStatForm.month.value="";
				materialStatForm.quarters.value="";
				materialStatForm.beginDate.value="";
				materialStatForm.endDate.value="";
			}
		}
		</script>
	</head>
	<body>
		<br>
		<template:titile value="��ѯ����ͳ��" />
		<html:form action="/material_stat.do?method=statMaterial"
			styleId="materialStatForm">
			<template:formTable namewidth="150" contentwidth="500">
				<logic:equal value="1" name="LOGIN_USER" property="deptype">
					<template:formTr name="��ά��˾">
						<select class="inputtext" style="width: 205px;"
							name="contractorId">
							<option value="">
								����
							</option>
							<logic:present scope="session" name="contractor_list">
								<logic:iterate id="c" name="contractor_list">
									<option value="<bean:write name="c" property="contractorid"/>">
										<bean:write name="c" property="contractorname" />
									</option>
								</logic:iterate>
							</logic:present>
						</select>
					</template:formTr>
				</logic:equal>
				<template:formTr name="��ѯ��ʽ">
					<input name="queryMode" value="YEAR" type="radio"
						onclick="displayDateQueryCondition(this.value);" checked />
					����
					<input name="queryMode" value="MONTH" type="radio"
						onclick="displayDateQueryCondition(this.value);" />
					����
					<input name="queryMode" value="QUARTER" type="radio"
						onclick="displayDateQueryCondition(this.value);" />
					������
					<input name="queryMode" value="OTHER" type="radio"
						onclick="displayDateQueryCondition(this.value);" />
					�Զ�����
				</template:formTr>
				<template:formTr name="���" tagID="yearConditionTr">
					<input name="year" class="inputtext" style="width: 205px"
						onfocus="WdatePicker({dateFmt:'yyyy'})" readonly />
				</template:formTr>
				<template:formTr name="�·�" tagID="monthConditionTr"
					style="display:none;">
					<input name="month" class="inputtext" style="width: 205px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM'})" readonly />
				</template:formTr>
				<template:formTr name="����" tagID="quarterConditionTr"
					style="display:none;">
					<input type="checkbox" name="quarters" value="1" >��һ����</input>
					<input type="checkbox" name="quarters" value="2" >�ڶ�����</input>
					<input type="checkbox" name="quarters" value="3" >�ڶ�����</input>
					<input type="checkbox" name="quarters" value="4" >���ļ���</input>
				</template:formTr>
				<template:formTr name="��������" tagID="otherConditionTr"
					style="display:none;">
					<input name="beginDate" class="inputtext" style="width: 100px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly />
					-
					<input name="endDate" class="inputtext" style="width: 100px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly />
				</template:formTr>
			</template:formTable>
			<div align="center">
				<input type="hidden" name="isQuery" value="true" />
				<html:submit property="action" styleClass="button"> ��ѯ</html:submit>
				<html:reset property="action" styleClass="button">����</html:reset>
			</div>
		</html:form>
		<template:displayHide styleId="materialStatForm"></template:displayHide>
		
		<script type="text/javascript" language="javascript">
	exportList = function() {
		var url = "${ctx}/material_stat.do?method=exportMaterialStat";
		self.location.replace(url);
	}
</script>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
.subject {
	text-align: center;
}
</style>
	<c:if test="${result_map!=null}">
			<logic:notEmpty name="result_map" property="contractor_id_list">
				<logic:notEmpty name="result_map" property="material_id_list">
					<bean:define id="contractor_result_map" name="result_map"
						property="result_map"></bean:define>
					<table border="1" cellpadding="0" cellspacing="0" class="tabout"
						style="border-collapse: collapse">
						<tr>
							<td>
								��ά��˾
							</td>
							<logic:iterate id="oneMaterialName" name="result_map"
								property="material_name_list">
								<td>
									<bean:write name="oneMaterialName" />
								</td>
							</logic:iterate>
						</tr>
						<logic:iterate id="one_contractor_map" name="result_map"
							property="contractor_name_map">
							<bean:define id="one_contractor_result_map"
								name="contractor_result_map"
								property="${one_contractor_map.key}"></bean:define>
							<tr>
								<td>
									<bean:write name="one_contractor_map" property="value" />
								</td>
								<logic:iterate id="oneMaterialId" name="result_map"
									property="material_id_list">
									<logic:notEmpty name="one_contractor_result_map"
										property="${oneMaterialId}">
										<bean:define id="oneContractorUseMaterialNumber"
											name="one_contractor_result_map" property="${oneMaterialId}"></bean:define>
									</logic:notEmpty>
									<logic:empty name="one_contractor_result_map"
										property="${oneMaterialId}">
										<bean:define id="oneContractorUseMaterialNumber" value="0"></bean:define>
									</logic:empty>
									<td>
										<bean:write name="oneContractorUseMaterialNumber" />
									</td>
								</logic:iterate>
							</tr>
						</logic:iterate>
					</table>
				</logic:notEmpty>
			</logic:notEmpty>
		</c:if>
		<table border="0" cellpadding="0" cellspacing="0" width="100%"
			style="border: 0px">
			<tr>
				<td>
					<logic:notEmpty name="result_map">
						<a href="javascript:exportList()">����ΪExcel�ļ�</a>
					</logic:notEmpty>
				</td>
			</tr>
		</table>
	</body>
</html>
