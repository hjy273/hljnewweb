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
		<title>�����������</title>
		<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
		<META HTTP-EQUIV="expires" CONTENT="0">
		<script type="text/javascript">
convertStrToDate = function (dateTimeStr, dateTimeSeperator, dateSeperator, timeSeperator) {
	var dateStr = "";
	var timeStr = "";
	var d = new Date();
	if (dateTimeSeperator == null || typeof (dateTimeSeperator) == "undefined") {
		dateTimeSeperator = " ";
	}
	if (dateSeperator == null || typeof (dateSeperator) == "undefined") {
		dateSeperator = "-";
	}
	if (timeSeperator == null || typeof (timeSeperator) == "undefined") {
		timeSeperator = ":";
	}
	if (dateTimeStr.indexOf(dateTimeSeperator) != -1) {
		dateStr = dateTimeStr.split(dateTimeSeperator)[0];
	} else {
		dateStr = dateTimeStr;
	}
	if (dateTimeStr.indexOf(dateTimeSeperator) != -1) {
		timeStr = dateTimeStr.split(dateTimeSeperator)[1];
	}
	try {
		if (dateStr.indexOf(dateSeperator) != -1) {
			var dateValues = dateStr.split(dateSeperator);
			d = new Date(parseInt(dateValues[0], 10), parseInt(dateValues[1], 10) - 1, parseInt(dateValues[2], 10));
		}
		if (timeStr != "" && timeStr.indexOf(timeSeperator) != -1) {
			var timeValues = timeStr.split(timeSeperator);
			d.setHours(parseInt(timeValues[0], 10));
			d.setMinutes(parseInt(timeValues[1], 10));
			d.setSeconds(parseInt(timeValues[2], 10));
		}
	}
	catch (e) {
		return -1;
	}
	return d;
};
compareTwoDate = function (date1, date2) {
	if (date1 < date2) {
		return -1;
	}
	if (date1 > date2) {
		return 1;
	}
	if (date1 == date2) {
		return 0;
	}
};
	goBack = function() {
		var url = "${sessionScope.S_BACK_URL}";
		location = url;
	}
</script>
		<script type="text/javascript">
			function checkForm(){
				year=submitForm1.dyear.value;
				month=submitForm1.dmonth.value;
				date=submitForm1.ddate.value;
				submitForm1.deadline.value=year+"/"+month+"/"+date+" ";
				submitForm1.deadline.value=submitForm1.deadline.value+submitForm1.dhour.value+":"+submitForm1.dminute.value+":"+submitForm1.dsecond.value;
				if(getTrimValue("operator")=="approve"){
					if(submitForm1.approveResult[0].checked){
	 if(convertStrToDate(submitForm1.deadline.value," ","/",":")==-1){
		alert("�ܽ��ύʱ��ʱ�䲻����YYYY/MM/DD hh:mm:ss�����ڸ�ʽ��");
		return false;
	 }
	 date=convertStrToDate(submitForm1.deadline.value," ","/",":");
	 if(parseInt(dyear, 10)!=terminalDate.getFullYear()||parseInt(dmonth,10)!=terminalDate.getMonth()+1||parseInt(ddate,10)!=terminalDate.getDate()){
		alert("�ܽ��ύʱ��ʱ�䲻�Ϸ���");
		return false;
	 }
					}
				}
				if(getTrimValue("operator")=="transfer"){
					if(getTrimValue("approvers").length==0){
						alert("ת���˲���Ϊ�գ�");
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
		</script>
	</head>
	<body>
		<c:if test="${operator=='approve'}">
			<template:titile value="�����������" />
		</c:if>
		<c:if test="${operator=='transfer'}">
			<template:titile value="��������ת��" />
		</c:if>
		<html:form action="/wap/drillPlanAction.do?method=approveDrillPlan"
			onsubmit="return checkForm()" styleId="submitForm1"
			enctype="multipart/form-data">
			<p>
				<jsp:include page="drill_plan_wap_base.jsp"></jsp:include>
				<input type="hidden" name="env" value="${env }" />
				<input type="hidden" name="taskId"
					value="<c:out value='${drillTask.id }' />" />
				<input type="hidden" name="id"
					value="<c:out value='${drillPlan.id }' />" />
				<input type="hidden" name="creator"
					value="<c:out value='${drillPlan.creator }' />" />
				<input type="hidden" name="contractorId"
					value="<c:out value='${drillPlan.contractorId }' />" />
				<input type="hidden" id="planCreateTime"
					value="<c:out value='${drillPlan.createTime }'/>" />
				<input type="hidden" id="planDeadlineTime"
					value="<c:out value='${drillTask.deadline }'/>" />
			</p>
			<c:if test="${operator=='approve'}">
				<p>
					<c:if test="${drillPlan.createTime>drillTask.deadline}">
						<font color="red">���������ύ��ʱ����ʱ<bean:write name="days"
								format="#.##" />Сʱ</font>
					</c:if>
					<c:if test="${drillPlan.createTime<=drillTask.deadline}">
						<font color="blue">���������ύ��ǰ����ǰ<bean:write name="days"
								format="#.##" />Сʱ</font>
					</c:if>
					<br />
					�ܽ��ύʱ�ޣ�
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
						<bean:write name='drillPlan' property='deadline'
							format='yyyy/MM/dd HH:mm:ss' />
					</c:if>
				</p>
				<p>
					��˽����
					<input name="approveResult" value="1" type="radio" checked />
					ͨ�����
					<input name="approveResult" value="0" type="radio" />
					δͨ�����
					<br />
					��˱�ע��
					<html:textarea property="approveRemark" title="" style="width:80%"
						rows="5" styleClass="textarea"></html:textarea>
				</p>
				<p>
					<input type="hidden" name="operator" id="operator" value="approve" />
					<html:submit property="action">���</html:submit>
					<html:reset property="action">��д</html:reset>
					<html:button property="button" onclick="goBack();">���ش���</html:button>
				</p>
			</c:if>
			<c:if test="${operator=='transfer'}">
				<p>
					ת���ˣ�
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
					ת��˵����
					<input name="approveResult" value="2" type="hidden" />
					<html:textarea property="approveRemark" title="" style="width:80%"
						rows="5" styleClass="textarea"></html:textarea>
				</p>
				<p>
					<input type="hidden" name="operator" id="operator"  value="transfer" />
					<html:submit property="action">ת��</html:submit>
					<html:button property="button" onclick="goBack();">���ش���</html:button>
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
