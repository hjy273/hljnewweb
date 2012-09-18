<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>查询年度考核</title>
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
		<link type="text/css" rel="stylesheet" href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	</head>
	<script type="text/javascript">
		function getContractNO(){
			var year=$('appraiseTime').value;
			var contractorId=$('contractorId').value;
			$("contractNO").length=1;
			var url="${ctx}/appraiseMonthAction.do?method=getContractNO&appraiseTime="+year+"&contractorid="+contractorId;
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
		function viewYearResult(id){
			window.location.href="${ctx}/appraiseYearAction.do?method=viewAppraiseYear&id="+id;
		}
	</script>
	<body>
		<template:titile value="查询年度考核" />
		<html:form action="/appraiseYearAction.do?method=queryAppraiseYear" styleId="form">
			<template:formTable>
				<template:formTr name="考核年份">
					<html:text property="appraiseTime" style="Wdate;width:140px;" styleId="appraiseTime" styleClass="inputtext required" onfocus="WdatePicker({dateFmt:'yyyy'})"></html:text><font color="red">*</font>
				</template:formTr>
				<template:formTr name="考核代维">
					<html:select property="contractorId" styleClass="inputtext" styleId="contractorId" style="width:145px" onchange="getContractNO();">
						<html:option value="">请选择</html:option>
						<html:options collection="cons" property="contractorID" labelProperty="contractorName"></html:options>
					</html:select>
				</template:formTr>
				<template:formTr name="标包号">
					<html:select property="contractNO" styleId="contractNO" style="width:145px" styleClass="inputtext">
						<html:option value="">全部</html:option>
					</html:select>
				</template:formTr>
				</template:formTable>
			<div align="center"><input type="submit" value="查询" class="button"/></div>
		</html:form>
		<logic:notEmpty name="appraiseYearResults">
			<display:table name="sessionScope.appraiseYearResults" id="appraiseYearResult" pagesize="18" export="false" defaultsort="1"
			defaultorder="descending" sort="list">
				<display:column media="html" title="代维单位" sortable="true">
					<c:out value="${consMap[appraiseYearResult.contractorId]}"></c:out>
				</display:column>
				<display:column property="contractNO" title="标包号"></display:column>
				<display:column property="year" title="考核年份"></display:column>
				<display:column property="result" title="得分"></display:column>
				<display:column property="appraiser" title="考核人"></display:column>
				<display:column property="appraiseDate" title="考核时间" format="{0,date,yyyy-MM-dd}">
				</display:column>
				<display:column media="html" title="操作">
					<a href="javascript:viewYearResult('${appraiseYearResult.id }');">查看</a>
				</display:column>
			</display:table>
		</logic:notEmpty>
	</body>
	<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
</html>
