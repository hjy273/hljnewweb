<%@include file="/wap/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>演练总结审核</title>
		<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
		<META HTTP-EQUIV="expires" CONTENT="0">
		<script type="text/javascript">
			function checkForm(){
				if(getTrimValue("operator")=="transfer"){
					if(getTrimValue("approvers").length==0){
						alert("转审人不能为空！");
						return false;
					}
					return true;
				}
				return true;
			}
			function getTrimValue(value){
				return document.getElementById(value).value.trim();
			}
			String.prototype.trim = function() {
				return this.replace(/^\s+|\s+$/g,"");
			}
	goBack = function() {
		var url = "${sessionScope.S_BACK_URL}";
		location = url;
	}
		</script>
	</head>
	<body>
		<c:if test="${operator=='approve'}">
			<template:titile value="演练总结审核" />
		</c:if>
		<c:if test="${operator=='transfer'}">
			<template:titile value="演练总结转审" />
		</c:if>
		<html:form
			action="/wap/drillSummaryAction.do?method=approveDrillSummary"
			onsubmit="return checkForm()" styleId="submitForm1"
			enctype="multipart/form-data">
			<input type="hidden" name="env" value="${env }" />
			<input type="hidden" name="id"
				value="<c:out value='${drillSummary.id }' />" />
			<input type="hidden" name="creator"
				value="<c:out value='${drillSummary.creator }' />" />
			<input type="hidden" name="operator"
				value="<c:out value='${operator }'/>" />
			<p>
				<jsp:include page="drill_summary_wap_base.jsp"></jsp:include>
			</p>
			<c:if test="${operator=='approve'}">
				<p>
					<c:if test="${drillSummary.createTime>drillPlan.deadline}">
						<font color="red">演练总结提交超时，超时<bean:write name="days"
								format="#.##" />小时</font>
					</c:if>
					<c:if test="${drillSummary.createTime<=drillPlan.deadline}">
						<font color="blue">演练总结提交提前，提前<bean:write name="days"
								format="#.##" />小时</font>
					</c:if>
				</p>
				<p>
					审核结果：
					<input name="approveResult" value="1" type="radio" checked />
					通过审核
					<input name="approveResult" value="0" type="radio" />
					未通过审核
					<br />
					审核备注：
					<html:textarea property="approveRemark" title="" style="width:80%"
						rows="5" styleClass="textarea"></html:textarea>
				</p>
				<p>
					<html:submit property="action">审核</html:submit>
					<html:reset property="action">重写</html:reset>
					<html:button property="button" onclick="goBack();">返回待办</html:button>
				</p>
			</c:if>
			<c:if test="${operator=='transfer'}">
				<p>
					转审人：
					<c:if test="${sessionScope.APPROVER_INPUT_NAME_MAP!=null}">
						<c:forEach var="oneItem"
							items="${sessionScope.APPROVER_INPUT_NAME_MAP}">
							<c:if test="${oneItem.key!='span_value'}">
								<input name="${oneItem.key }" value="${oneItem.value }"
									type="hidden" />
							</c:if>
							<c:if test="${oneItem.key=='span_value'}">
							${oneItem.value }
						</c:if>
						</c:forEach>
					</c:if>
					<br />
					转审说明：
					<input name="approveResult" value="2" type="hidden" />
					<html:textarea property="approveRemark" title="" style="width:80%"
						rows="5" styleClass="textarea"></html:textarea>
				</p>
				<p>
					<html:submit property="action">转审</html:submit>
					<html:button property="button" onclick="goBack();">返回待办</html:button>
				</p>
			</c:if>
		</html:form>
	</body>
</html>
