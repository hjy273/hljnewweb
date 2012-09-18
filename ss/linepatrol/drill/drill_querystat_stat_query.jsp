<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>演练查询</title>
		<script type="text/javascript">
			function checkForm(){
				return true;
			}
		</script>
	</head>
	<body>
		<template:titile value="演练统计查询" />
		<!-- 查询页面 -->
		<html:form action="/queryStatAction.do?method=drillStat"
			onsubmit="return checkForm();" enctype="multipart/form-data"
			styleId="queryForm">
			<table border="1" align="center" cellpadding="1" cellspacing="0"
				class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width: 20%;">
						代维公司：
					</td>
					<td class="tdulright">
						<c:if test="${deptype=='1'}">
							<html:select property="conId" styleClass="inputtext" style="width:140px">
							<html:option value="">全部</html:option>
							<html:options collection="list" property="contractorID" labelProperty="contractorName"></html:options>
						</html:select>
						</c:if>
						<c:if test="${deptype=='2'}">
							<input type="hidden" value="${conId }" name="conId" />
							<c:out value="${conName}"></c:out>
						</c:if>
					</td>
				</tr>
				<template:formTr name="演练级别">
					<html:multibox property="levels" value="2" />一般演练
					<html:multibox property="levels" value="1" />重点演练
					<html:multibox property="levels" value="0" />自定义演练
				</template:formTr>
				<template:formTr name="演练时间">
					<html:text property="beginTime" styleClass="Wdate" style="width:102px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" readonly="true"/>
					 -- <html:text property="endTime" styleClass="Wdate validate-date-after-startTime" style="width:102px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'beginTime\')}'})" readonly="true"/>
				</template:formTr>
			</table>
			<div align="center" style="height: 40px">
				<input name="isQuery" value="true" type="hidden" />
				<html:submit property="action" styleClass="button">查询</html:submit>
				&nbsp;&nbsp;
				<html:reset property="action" styleClass="button">重写</html:reset>
				&nbsp;&nbsp;
				<html:button property="button" styleClass="button"
					onclick="javascript:history.go(-1);">返回</html:button>
			</div>
		</html:form>
		<template:displayHide styleId="queryForm"></template:displayHide>

		<!-- 查询结果 -->
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<script type="text/javascript">
			toViewDrill=function(taskId,planId,summaryId){
            	window.location.href = "${ctx}/queryStatAction.do?method=viewDrill&taskId="+taskId+"&planId="+planId+"&summaryId="+summaryId;
			}
		</script>
		<logic:notEmpty name="result">
			<display:table name="sessionScope.result" id="drill" pagesize="18">
				<display:column property="contractorname" title="代维单位"
					headerClass="subject" sortable="true" />
				<display:column property="drill_number" title="演练个数"
					headerClass="subject" sortable="true" />
				<display:column property="total_person_number" title="实际投入人数(人)"
					headerClass="subject" sortable="true" />
				<display:column property="total_car_number" title="实际投入车辆数(辆)"
					headerClass="subject" sortable="true" />
				<display:column property="total_equipment_number" title="实际投入设备数(套)"
					headerClass="subject" sortable="true" />
			</display:table>
			<br />
			<div style="height: 50px; text-align: center;">
				<html:button property="button" styleClass="button"
					onclick="javascript:history.go(-1);">返回</html:button>
			</div>
		</logic:notEmpty>
	</body>
</html>
