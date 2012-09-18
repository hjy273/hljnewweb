<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<title>专项考核查询</title>
	<script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
	<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	</head>
	<script type="text/javascript">
		function getTableOption(){
			var startDate=$('startDate').value;
			var endDate=$('endDate').value;
			if(endDate==''||startDate==''){
				return false;
			}
			$("tableId").length=1;
			var url="${ctx}/appraiseSpecialAction.do?method=getTableOption&startDate="+startDate+"-01-01&endDate="+endDate+"-12-31";
			var myAjax = new Ajax.Request( url , {
        	    method: 'post',
        	    asynchronous: true,
        	    onComplete: setTableOption });
		}
		
		function setTableOption(response){
			var str=response.responseText;
			if(str==""){
				return true;
			}
			var optionlist=str.split(";");
			$("tableId").options[i]=new Option("","不限");
			for(var i=0;i<optionlist.length-1;i++){
				$("tableId").options[i+1]=new Option(optionlist[i].split(":")[1],optionlist[i].split(":")[0]);
			}
		}
		function view(id){
			window.location.href="${ctx}/appraiseSpecialAction.do?method=viewAppraiseMonth&flag=view&resultId="+id;
		}
	</script>
	<body>
		<template:titile value="专项考核查询" />
			<html:form action="/appraiseSpecialAction?method=queryAppraiseList" styleId="form">
			<template:formTable>
				<template:formTr name="考核日期">
					<html:text property="startDate" style="Wdate;width:135px" styleClass="inputtext required" styleId="startDate" onfocus="WdatePicker({dateFmt:'yyyy'})" onchange="getTableOption()"></html:text>
					--
					<html:text property="endDate" style="Wdate;width:135px" styleClass="inputtext required" styleId="endDate" onfocus="WdatePicker({dateFmt:'yyyy',maxDate:'%y-%M',minDate:'#F{$dp.$D(\'startDate\')}'})" onchange="getTableOption()"></html:text><font color="red">*</font>
				</template:formTr>
				<template:formTr name="考核表">
					<html:select property="tableId" styleId="tableId" styleClass="inputtext" style="width:290px">
					<html:option value="">不限</html:option>
				</html:select>
				</template:formTr>
				<template:formTr name="考核代维">
					<html:select property="contractorId" styleClass="inputtext required" styleId="contractorId" style="width:290px" >
						<html:option value="">不限</html:option>
						<html:options collection="cons" property="contractorID" labelProperty="contractorName"></html:options>
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
				<display:column property="startDate" sortable="true" format="{0,date,yyyy-MM-dd}"
					title="考核开始时间" />
				<display:column property="endDate" sortable="true" format="{0,date,yyyy-MM-dd}"
					title="考核结束时间" />
				<display:column property="result" sortable="true" title="考核结果"
					 maxLength="18" />
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

			var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
</html>
