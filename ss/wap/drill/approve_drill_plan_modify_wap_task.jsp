<%@include file="/wap/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%
	GregorianCalendar calendar = new GregorianCalendar();
	int year = calendar.get(GregorianCalendar.YEAR);
	int month = calendar.get(GregorianCalendar.MONTH) + 1;
	int date = calendar.get(GregorianCalendar.DATE);
%>
<html>
	<head>
		<title>演练方案变更审核</title>
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
			<template:titile value="演练方案变更审核" />
		</c:if>
		<c:if test="${operator=='transfer'}">
			<template:titile value="演练方案变更转审" />
		</c:if>
		<html:form
			action="/wap/drillPlanModifyAction.do?method=approveDrillPlanModify"
			onsubmit="return checkForm()" styleId="submitForm1"
			enctype="multipart/form-data">
			<p>
				<jsp:include page="drill_plan_wap_base.jsp"></jsp:include>
			</p>
			<p>
				<jsp:include page="drill_plan_modify_wap_base.jsp"></jsp:include>
				<input type="hidden" name="env" value="${env }" />
				<input type="hidden" name="id" value="${drillPlanModify.id }" />
				<input type="hidden" name="planId"
					value="${drillPlanModify.planId }" />
				<input type="hidden" name="taskId" value="${drillPlan.taskId }" />
				<input type="hidden" name="conId" value="${drillPlan.contractorId }" />
				<input type="hidden" name="modifyMan"
					value="${drillPlanModify.modifyMan }" />
				<fmt:formatDate value="${drillPlanModify.nextStartTime}"
					pattern="yyyy/MM/dd HH:mm:ss" var="formatNextStartTime" />
				<fmt:formatDate value="${drillPlanModify.nextEndTime}"
					pattern="yyyy/MM/dd HH:mm:ss" var="formatNextEndTime" />
				<input type="hidden" name="nextStartTime"
					value="${formatNextStartTime }" />
				<input type="hidden" name="nextEndTime"
					value="${formatNextEndTime }" />
			</p>
			<c:if test="${operator=='approve'}">
				<p>
					总结提交时限：
					<c:if test="${empty drillPlan.deadline}">
						<br />
						<input name="deadline" value="" type="hidden" />
						<select name="dyear">
							<c:forEach var="dYear" begin="<%=year %>" end="<%=year+5 %>"
								step="1">
								<option value="${dYear }">
									${dYear }
								</option>
							</c:forEach>
						</select>
						<select name="dmonth">
							<c:forEach var="dMonth" begin="1" end="12" step="1">
								<option value="${dMonth }">
									${dMonth }
								</option>
							</c:forEach>
						</select>
						<select name="ddate">
							<c:forEach var="dDate" begin="1" end="31" step="1">
								<option value="${dDate }">
									${dDate }
								</option>
							</c:forEach>
						</select>
						<br />
						<select name="dhour">
							<c:forEach var="dHour" begin="0" end="23" step="1">
								<option value="${dHour }">
									${dHour }
								</option>
							</c:forEach>
						</select>
						<select name="dminute">
							<c:forEach var="dMinute" begin="0" end="55" step="5">
								<option value="${dMinute }">
									${dMinute }
								</option>
							</c:forEach>
						</select>
						<select name="dsecond">
							<c:forEach var="dSecond" begin="0" end="55" step="5">
								<option value="${dSecond }">
									${dSecond }
								</option>
							</c:forEach>
						</select>
					</c:if>
					<c:if test="${not empty drillPlan.deadline}">
						<bean:write name='drillPlan' property='deadline' format='yyyy/MM/dd HH:mm:ss'/>
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
					<input type="hidden" name="operator" value="approve" />
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
					<input type="hidden" name="operator" value="transfer" />
					<html:submit property="action">转审</html:submit>
					<html:button property="button" onclick="goBack();">返回待办</html:button>
				</p>
			</c:if>
		</html:form>
		<script type="text/javascript">
		for(i=0;i<document.forms[0].dmonth.length;i++){
			if(document.forms[0].dmonth.options[i].value=="<%=month%>"){
				document.forms[0].dmonth.options[i].selected=true;
			}
		}
		for(i=0;i<document.forms[0].ddate.length;i++){
			if(document.forms[0].ddate.options[i].value=="<%=date%>"){
				document.forms[0].ddate.options[i].selected=true;
			}
		}
		</script>
	</body>
</html>
