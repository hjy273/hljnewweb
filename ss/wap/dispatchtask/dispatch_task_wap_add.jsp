<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/wap/header.jsp"%>
<%@page import="com.cabletech.commons.util.DateUtil,java.util.GregorianCalendar" %>
		<% 
		GregorianCalendar calendar=new GregorianCalendar(); 
		int year=calendar.get(GregorianCalendar.YEAR); 
		int month=calendar.get(GregorianCalendar.MONTH)+1;
		int date=calendar.get(GregorianCalendar.DATE);
		DateUtil.DateToTimeString(calendar.getTime(),"yyyy-MM-dd HH:mm:ss")
		 %>
<html>
	<head>
		<title>sendtask</title>
		<script type="text/javascript" language="javascript">
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
	function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
       //�����Ƿ�����
	 function valiD(id){
      	var mysplit = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
        var obj = document.getElementById(id);
        if(mysplit.test(obj.value)){
          return true;
        }
        else{
        	alert("����д�����ֲ��Ϸ�,����������");
            obj.focus();
            obj.value = "0.00";
            return false;
        }
    }

 //ѡ������
    function valCharLength(Value){
      var j=0;
      var s = Value;
      for   (var   i=0;   i<s.length;   i++)
      {
              if   (s.substr(i,1).charCodeAt(0)>255)   j   =   j   +   2;
              else   j++
      }
      return j;
    }

	function checkSub() {
	 var year=DispatchTaskBean.year.value;
	 var month=DispatchTaskBean.month.value;
	 var date=DispatchTaskBean.date.value;
	 DispatchTaskBean.acceptdeptid.value=DispatchTaskBean.dept_id.value;
	 DispatchTaskBean.acceptuserid.value=DispatchTaskBean.user_id.value;
	 DispatchTaskBean.mobileId.value=DispatchTaskBean.mobile_id.value;
	 DispatchTaskBean.processterm.value=year+"/"+month+"/"+date+" ";
	 DispatchTaskBean.processterm.value=DispatchTaskBean.processterm.value+DispatchTaskBean.hour.value+":"+DispatchTaskBean.minute.value+":"+DispatchTaskBean.second.value;
	 if(DispatchTaskBean.sendtype.value==null||DispatchTaskBean.sendtype.value==""||DispatchTaskBean.sendtype.value.trim()==""){
        alert("�����빤�����");
        return;
      }
	  
      if(DispatchTaskBean.processterm.value == null || DispatchTaskBean.processterm.value == ""){
      	alert("�����봦�����ޣ�");
        return;
      }
	 if(convertStrToDate(DispatchTaskBean.processterm.value," ","/",":")==-1){
		alert("�������޲�����YYYY/MM/DD hh:mm:ss�����ڸ�ʽ��");
		return;
	 }
	 var terminalDate=convertStrToDate(DispatchTaskBean.processterm.value," ","/",":");
	 if(parseInt(year, 10)!=terminalDate.getFullYear()||parseInt(month,10)!=terminalDate.getMonth()+1||parseInt(date,10)!=terminalDate.getDate()){
		alert("�������޲��Ϸ���");
		return;
	 }
	 var nowDate=convertStrToDate("<%=DateUtils.DateToString(calendar.getTime(),"yyyy-MM-dd HH:mm:ss")%>");
	 if(compareTwoDate(terminalDate,nowDate)<=0){
		alert("����Ȩ�޲������ڵ�ǰ���ڣ�");
		return;
	 }
		
      if(valCharLength(DispatchTaskBean.sendtext.value)>1020){
        alert("����˵������̫�࣡���ܳ���510������");
        return;
      }
      if(DispatchTaskBean.sendtopic.value==null||DispatchTaskBean.sendtopic.value==""||DispatchTaskBean.sendtopic.value.trim()==""){
        alert("�������������⣡");
        return;
      }
      if(DispatchTaskBean.acceptuserid.value==null||DispatchTaskBean.acceptuserid.value==""||DispatchTaskBean.acceptuserid.value.trim()==""){
		alert("��ѡ�������ˣ�");
        return;
	  }
      if((DispatchTaskBean.sendtext.value==null||DispatchTaskBean.sendtext.value==""||DispatchTaskBean.sendtext.value.trim()=="")){
        alert("����������˵����");
        return;
      }
        DispatchTaskBean.submit();
	}
		goBackHome=function(){
			var url="${ctx}/wap/login.do?method=index&&env=wap";
			location=url;
		}
		</script>
	</head>
	<body>
		<template:titile value="ָ������" />
		<html:form action="/dispatchtask/dispatch_task.do?method=dispatchTask"
			styleId="addApplyForm" onsubmit="return validate(this);"
			enctype="multipart/form-data">
			<html:hidden property="env" value="${env}" />
			<p>
				�������
				<select name="sendtype" style="width: 100px;">
					<logic:notEmpty name="dispatch_task_type_list">
						<logic:iterate id="oneDispatchTaskType"
							name="dispatch_task_type_list">
							<option
								value="<bean:write name="oneDispatchTaskType" property="code" />">
								<bean:write name="oneDispatchTaskType" property="lable" />
							</option>
						</logic:iterate>
					</logic:notEmpty>
				</select>
				<input type="hidden" name="acceptdeptid" value="" />
				<input type="hidden" name="acceptuserid" value="" />
				<input type="hidden" name="mobileId" value="" />
				<br />
				�����ˣ�
				<c:if test="${sessionScope.INPUT_NAME_MAP!=null}">
					<c:forEach var="oneItem" items="${sessionScope.INPUT_NAME_MAP}">
						<c:if test="${oneItem.key!='span_value'}">
							<input name="${oneItem.key }" value="${oneItem.value }"
								type="hidden" />
						</c:if>
						<c:if test="${oneItem.key=='span_value'}">
							${oneItem.value }
						</c:if>
					</c:forEach>
				</c:if>
				<br/>
				�������⣺
				<html:text property="sendtopic" style="width:80%"
					styleClass="inputtext" maxlength="100" />
				<br />
				����˵����
				<html:textarea property="sendtext" alt=" ����˵���510�����֣�"
					style="width:80%" rows="5" styleClass="textarea"></html:textarea>
				<br />
				����Ҫ��
				<html:textarea property="replyRequest" alt="����Ҫ���510�����֣�"
					style="width:80%" rows="5" styleClass="textarea"></html:textarea>
				<br />
				����ʱ�ޣ�
				<br/>
				<input type="hidden" id="protimeid" name="processterm" value=""
					style="width: 80%" />
				<select name="year">
					<c:forEach var="dYear" begin="<%=year %>" end="<%=year+5 %>" step="1">
						<option value="${dYear }">${dYear }</option>
					</c:forEach>
				</select>
				<select name="month">
					<c:forEach var="dMonth" begin="1" end="12" step="1">
						<option value="${dMonth }">${dMonth }</option>
					</c:forEach>
				</select>
				<select name="date">
					<c:forEach var="dDate" begin="1" end="31" step="1">
						<option value="${dDate }">${dDate }</option>
					</c:forEach>
				</select>
				<br/>
				<select name="hour">
					<c:forEach var="dHour" begin="0" end="23" step="1">
						<option value="${dHour }">${dHour }</option>
					</c:forEach>
				</select>
				<select name="minute">
					<c:forEach var="dMinute" begin="0" end="55" step="5">
						<option value="${dMinute }">${dMinute }</option>
					</c:forEach>
				</select>
				<select name="second">
					<c:forEach var="dSecond" begin="0" end="55" step="5">
						<option value="${dSecond }">${dSecond }</option>
					</c:forEach>
				</select>
			</p>
			<p>
				<html:button onclick="checkSub()" property="action">�ύ</html:button>
				<input type="button" onclick="goBackHome()" value="����ҳ">
			</p>
		</html:form>
		<script type="text/javascript">
		for(i=0;i<document.forms[0].month.length;i++){
			if(document.forms[0].month.options[i].value=="<%=month%>"){
				document.forms[0].month.options[i].selected=true;
			}
		}
		for(i=0;i<document.forms[0].date.length;i++){
			if(document.forms[0].date.options[i].value=="<%=date%>"){
				document.forms[0].date.options[i].selected=true;
			}
		}
		</script>
	</body>
</html>
