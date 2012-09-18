<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linepatrol.cut.module.*"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%
	GregorianCalendar calendar = new GregorianCalendar();
	int year = calendar.get(GregorianCalendar.YEAR);
	int month = calendar.get(GregorianCalendar.MONTH) + 1;
	int date = calendar.get(GregorianCalendar.DATE);
%>
<html>
	<head>
		<title>����������</title>
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
			function checkForm(){
				var year=submitForm1.byear.value;
				var month=submitForm1.bmonth.value;
				var date=submitForm1.bdate.value;
				submitForm1.replyBeginTime.value=year+"/"+month+"/"+date+" ";
				submitForm1.replyBeginTime.value=submitForm1.replyBeginTime.value+submitForm1.bhour.value+":"+submitForm1.bminute.value+":"+submitForm1.bsecond.value;
				year=submitForm1.eyear.value;
				month=submitForm1.emonth.value;
				date=submitForm1.edate.value;
				submitForm1.replyEndTime.value=year+"/"+month+"/"+date+" ";
				submitForm1.replyEndTime.value=submitForm1.replyEndTime.value+submitForm1.ehour.value+":"+submitForm1.eminute.value+":"+submitForm1.esecond.value;
				year=submitForm1.dyear.value;
				month=submitForm1.dmonth.value;
				date=submitForm1.ddate.value;
				submitForm1.deadline.value=year+"/"+month+"/"+date+" ";
				submitForm1.deadline.value=submitForm1.deadline.value+submitForm1.dhour.value+":"+submitForm1.dminute.value+":"+submitForm1.dsecond.value;
				if(getTrimValue("operator")=="approve"){
					if(submitForm1.approveResult[0].checked){
	 if(convertStrToDate(submitForm1.replyBeginTime.value," ","/",":")==-1){
		alert("������ʼʱ�䲻����YYYY/MM/DD hh:mm:ss�����ڸ�ʽ��");
		return false;
	 }
	 if(convertStrToDate(submitForm1.replyEndTime.value," ","/",":")==-1){
		alert("��������ʱ�䲻����YYYY/MM/DD hh:mm:ss�����ڸ�ʽ��");
		return false;
	 }
	 if(convertStrToDate(submitForm1.deadline.value," ","/",":")==-1){
		alert("�����ύʱ��ʱ�䲻����YYYY/MM/DD hh:mm:ss�����ڸ�ʽ��");
		return false;
	 }
	 var date=convertStrToDate(submitForm1.replyBeginTime.value," ","/",":");
	 if(parseInt(byear, 10)!=terminalDate.getFullYear()||parseInt(bmonth,10)!=terminalDate.getMonth()+1||parseInt(bdate,10)!=terminalDate.getDate()){
		alert("������ʼʱ�䲻�Ϸ���");
		return false;
	 }
	 date=convertStrToDate(submitForm1.replyEndTime.value," ","/",":");
	 if(parseInt(eyear, 10)!=terminalDate.getFullYear()||parseInt(emonth,10)!=terminalDate.getMonth()+1||parseInt(edate,10)!=terminalDate.getDate()){
		alert("��������ʱ�䲻�Ϸ���");
		return false;
	 }
	 date=convertStrToDate(submitForm1.deadline.value," ","/",":");
	 if(parseInt(dyear, 10)!=terminalDate.getFullYear()||parseInt(dmonth,10)!=terminalDate.getMonth()+1||parseInt(ddate,10)!=terminalDate.getDate()){
		alert("�����ύʱ��ʱ�䲻�Ϸ���");
		return false;
	 }
					}
					return true;
				}
				if(getTrimValue("operator")=="transfer"){
					if(getTrimValue("approvers").length==0){
						alert("ת���˲���Ϊ�գ�");
						return false;
					}
					return true;
				}
				return false;
			}
			function getTrimValue(value){
				return document.getElementById(value).value.trim();
			}
			String.prototype.trim = function() {
				return this.replace(/^\s+|\s+$/g,"");
			}
			function getElement(value){
				return document.getElementById(value);
			}
			//�ֻ�������֤ 
			String.prototype.isMobile = function() {  
				return (/^(?:13\d|15[289])-?\d{5}(\d{3}|\*{3})$/.test(this.trim()));  
			}
		</script>
	</head>
	<body>
		<template:titile value="����������" />
		<html:form action="/wap/cutAction.do?method=approveCutApply"
			onsubmit="return checkForm()" styleId="submitForm1"
			enctype="multipart/form-data">
			<fmt:formatDate value="${cut.beginTime}"
				pattern="yyyy/MM/dd HH:mm:ss" var="formatBeginTime" />
			<fmt:formatDate value="${cut.endTime}" pattern="yyyy/MM/dd HH:mm:ss"
				var="formatEndTime" />
			<fmt:formatDate value="${cut.replyBeginTime}"
				pattern="yyyy/MM/dd HH:mm:ss" var="formatReplyBeginTime" />
			<fmt:formatDate value="${cut.replyEndTime}"
				pattern="yyyy/MM/dd HH:mm:ss" var="formatReplyEndTime" />
			<fmt:formatDate value="${cut.applyDate}"
				pattern="yyyy/MM/dd HH:mm:ss" var="formatApplyDate" />
			<input type="hidden" name="id" value="<c:out value='${cut.id }'/>" />
			<input type="hidden" name="env" value="${env }" />
			<input type="hidden" value="${operator }" id="operator"
				name="operator" />
			<p>
				������ƣ�
				<c:out value="${cut.cutName}" />
				<br />
				��Ӽ���
				<c:if test="${cut.cutLevel=='1'}">һ����</c:if>
				<c:if test="${cut.cutLevel=='2'}">�������</c:if>
				<c:if test="${cut.cutLevel=='3'}">Ԥ���</c:if>
				<br />
				�ֳ������ˣ�
				<c:out value="${cut.liveChargeman}" />
				<br />
				��ά�������ˣ�
				<c:out value="${cut.chargeMan}" />
				<br />
				Ԥ���
				<fmt:formatNumber value="${cut.budget}" pattern="#.##" var="budget" />
				<c:out value="${budget}" />
				Ԫ �Ƿ����ⲹ��
				<c:if test="${cut.isCompensation=='1'}">
				��
				</c:if>
				<c:if test="${cut.isCompensation=='0'}">
				��
				</c:if>
				<br />
				<c:if test="${cut.isCompensation=='1'}">
					�ⲹ��λ��
					<c:out value="${cut.compCompany}" />
				</c:if>
				<br />
				��ӵص㣺
				<c:out value="${cut.cutPlace}" />
				<br />
				�������ʱ�䣺
				<c:out value="${formatBeginTime}" />
				-
				<c:out value="${formatEndTime}" />
			</p>
			<c:if test="${operator=='approve'}">
				<p>
					<c:if test="${empty cut.replyBeginTime}">
						������ӿ�ʼʱ�䣺
						<br/>
						<select name="byear">
							<c:forEach var="dYear" begin="<%=year %>" end="<%=year+5 %>"
								step="1">
								<option value="${dYear }">
									${dYear }
								</option>
							</c:forEach>
						</select>
						<select name="bmonth">
							<c:forEach var="dMonth" begin="1" end="12" step="1">
								<option value="${dMonth }">
									${dMonth }
								</option>
							</c:forEach>
						</select>
						<select name="bdate">
							<c:forEach var="dDate" begin="1" end="31" step="1">
								<option value="${dDate }">
									${dDate }
								</option>
							</c:forEach>
						</select>
						<br />
						<select name="bhour">
							<c:forEach var="dHour" begin="0" end="23" step="1">
								<option value="${dHour }">
									${dHour }
								</option>
							</c:forEach>
						</select>
						<select name="bminute">
							<c:forEach var="dMinute" begin="0" end="55" step="5">
								<option value="${dMinute }">
									${dMinute }
								</option>
							</c:forEach>
						</select>
						<select name="bsecond">
							<c:forEach var="dSecond" begin="0" end="55" step="5">
								<option value="${dSecond }">
									${dSecond }
								</option>
							</c:forEach>
						</select>
						<br />
						������ӽ���ʱ�䣺
						<br/>
						<select name="eyear">
							<c:forEach var="dYear" begin="<%=year %>" end="<%=year+5 %>"
								step="1">
								<option value="${dYear }">
									${dYear }
								</option>
							</c:forEach>
						</select>
						<select name="emonth">
							<c:forEach var="dMonth" begin="1" end="12" step="1">
								<option value="${dMonth }">
									${dMonth }
								</option>
							</c:forEach>
						</select>
						<select name="edate">
							<c:forEach var="dDate" begin="1" end="31" step="1">
								<option value="${dDate }">
									${dDate }
								</option>
							</c:forEach>
						</select>
						<br />
						<select name="ehour">
							<c:forEach var="dHour" begin="0" end="23" step="1">
								<option value="${dHour }">
									${dHour }
								</option>
							</c:forEach>
						</select>
						<select name="eminute">
							<c:forEach var="dMinute" begin="0" end="55" step="5">
								<option value="${dMinute }">
									${dMinute }
								</option>
							</c:forEach>
						</select>
						<select name="esecond">
							<c:forEach var="dSecond" begin="0" end="55" step="5">
								<option value="${dSecond }">
									${dSecond }
								</option>
							</c:forEach>
						</select>
						<br/>
					</c:if>
					<c:if test="${not empty cut.replyBeginTime}">
						<input name="replyBeginTime" value="${formatReplyBeginTime }"
							type="hidden" />
						<input name="replyEndTime" value="" type="hidden" />
						������ӿ�ʼʱ�䣺
						<br/>
						${formatReplyBeginTime }
						<br />
						������ӽ���ʱ�䣺
						<br/>
						${formatReplyEndTime }
						<br/>
					</c:if>
					�����ύʱ�ޣ�
					<br/>
					<input name="deadline" value="${deadline }" type="hidden" />
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
					<html:submit property="action">�ύ</html:submit>
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
					<html:submit property="action">�ύ</html:submit>
					<html:button property="button" onclick="goBack();">���ش���</html:button>
				</p>
			</c:if>
		</html:form>
		<script type="text/javascript">
		if(typeof(document.forms[0].bmonth)!="undefined"){
			for(i=0;i<document.forms[0].bmonth.length;i++){
				if(document.forms[0].bmonth.options[i].value=="<%=month%>"){
					document.forms[0].bmonth.options[i].selected=true;
				}
			}
		}
		if(typeof(document.forms[0].bdate)!="undefined"){
			for(i=0;i<document.forms[0].bdate.length;i++){
				if(document.forms[0].bdate.options[i].value=="<%=date%>"){
					document.forms[0].bdate.options[i].selected=true;
				}
			}
		}
		if(typeof(document.forms[0].emonth)!="undefined"){
			for(i=0;i<document.forms[0].emonth.length;i++){
				if(document.forms[0].emonth.options[i].value=="<%=month%>"){
					document.forms[0].emonth.options[i].selected=true;
				}
			}
		}
		if(typeof(document.forms[0].edate)!="undefined"){
			for(i=0;i<document.forms[0].edate.length;i++){
				if(document.forms[0].edate.options[i].value=="<%=date%>"){
					document.forms[0].edate.options[i].selected=true;
				}
			}
		}
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
