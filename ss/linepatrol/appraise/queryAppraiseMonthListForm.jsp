<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>月考核查询</title>
		<script type="text/javascript"
			src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
		<link type="text/css" rel="stylesheet"
			href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	</head>
	<script type="text/javascript">
		function getContractNO(){
			var appraiseMonth=$('appraiseTime').value;
			var contractorId=$('contractorId').value;
			$("contractNO").length=1;
			var url="${ctx}/appraiseMonthAction.do?method=getContractNO&appraiseTime="+appraiseMonth+"&contractorid="+contractorId;
			var myAjax = new Ajax.Request( url , {
        	    method: 'post',
        	    asynchronous: true,
        	    onComplete: setContractNO });
		}
		
		function setContractNO(response){
			var str=response.responseText;
			if(str==""){
				return true;
			}
			var optionlist=str.split(",");
			
			for(var i=0;i<optionlist.length;i++){
				$("contractNO").options[i+1]=new Option(optionlist[i],optionlist[i]);
			}
		}
		function view(id){
			window.location.href="${ctx}/appraiseMonthAction.do?method=viewAppraiseMonth&flag=view&resultId="+id;
		}
	</script>
	<body>
		<template:titile value="月考核评分查询" />
		<html:form action="/appraiseMonthAction.do?method=queryAppraiseList"
			styleId="queryTroubleInfo" >
			<template:formTable>
			<template:formTr name="考核月份">
					<html:text property="appraiseTime" style="Wdate;width:135px" styleClass="inputtext required" styleId="appraiseTime" onfocus="WdatePicker({dateFmt:'yyyy-MM',maxDate:'%y-%M',minDate:'${minDate}'})"></html:text><font color="red">*</font>
				</template:formTr>
				<template:formTr name="考核代维">
					<html:select property="contractorId" styleClass="inputtext required" styleId="contractorId" style="width:140px" onchange="getContractNO()">
						<html:option value="">全部</html:option>
						<html:options collection="cons" property="contractorID" labelProperty="contractorName"></html:options>
					</html:select>
				</template:formTr>
				<template:formTr name="标底包">
					<html:select property="contractNO" styleId="contractNO" styleClass="inputtext" style="width:140px">
					<html:option value="">全部</html:option>
				</html:select>
				</template:formTr>
				</template:formTable>
			<div align="center"><input type="submit" value="查询" class="button"/></div>
		</html:form>
		<c:if test="${appraiseResults != null}">
			<display:table name="sessionScope.appraiseResults" id="currentRowObject" pagesize="18" export="false" defaultsort="1" defaultorder="descending" sort="list"	>
				<logic:notEmpty name="appraiseResults">
				<bean:define id="id" name="currentRowObject" property="id"></bean:define>
				<display:column media="html" title="代维公司" sortable="true" >
					 	<c:out value="${contractor[currentRowObject.contractorId]}"></c:out>
					 </display:column>
				<display:column property="contractNO" title="标底包"
					 maxLength="9" sortable="true" />
				<display:column property="result" sortable="true" title="考核结果"
					 maxLength="18" />
				<display:column property="appraiseTime" sortable="true" format="{0,date,yyyy-MM-dd}"
					title="考核月份" />
				<display:column property="appraiser" title="考核人"
					 sortable="true" />
				<display:column media="html" title="操作">
					<a href="javascript:view('${id}')">查看</a>
				</display:column>
				</logic:notEmpty>
			</display:table>
			<table border="0" cellpadding="0" cellspacing="0" width="100%"
				style="border: 0px">
				<tr>
					<td style="text-align: center;">
						<input name="action" class="button" value="返回" onclick="history.back();"
							type="button" />
					</td>
				</tr>
			</table>
		</c:if>
	</body>
	<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('queryTroubleInfo', {immediate : true, onFormValidate : formCallback});
	</script>
</html>
