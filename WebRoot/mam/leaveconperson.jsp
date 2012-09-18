<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<script language="javascript" src="${ctx}/js/validation/prototype.js"
	type=""></script>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<script type="text/javascript">
<!--
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
//-->
</script>
<html>
	<head>
		<script type="text/javascript" language="javascript">
		var enterTime=convertStrToDate("${personinfo.enterTime}"," ","/");
    	function valiSub(){
        	if(conPersonBean.leaveTime.value=="" || conPersonBean.leaveTime.value==null||conPersonBean.leaveTime.value.trim()=="" || conPersonBean.leaveTime.value.trim()==null){
            	alert("离职时间不能为空或者空格！");
            	return false;
        	}
        	var leaveTime=convertStrToDate(conPersonBean.leaveTime.value," ","/");
        	if(compareTwoDate(enterTime,leaveTime)>0){
        		alert("离职时间不能小于入职时间！");
        		return false;
        	}
       		conPersonBean.submit();
    	}
    	function addGoBack()
        {
            try{
                location.href = "${ctx}/ConPersonAction.do?method=showConPerson";
                return true;
            }
            catch(e){
                alert(e);
            }
        }
    </script>
	</head>
	<body>
		<template:titile value="填写离职人员信息" />
		<html:form action="/ConPersonAction?method=leaveConPerson"
			styleId="addtoosbaseFormId">
			<template:formTable contentwidth="200" namewidth="300">
				<template:formTr name="离职时间">
					<input type="hidden" name="id" value="${id }" />
					<html:text property="leaveTime" styleClass="inputtext"
						readonly="true"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate:'%y-%M-%d'})" />
					<font color="#FF0000">*</font>
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:button property="action" styleClass="button"
							onclick="valiSub()">提交</html:button>
					</td>
					<td>
						<input type="button" class="button" onclick="addGoBack()"
							value="返回">
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>


