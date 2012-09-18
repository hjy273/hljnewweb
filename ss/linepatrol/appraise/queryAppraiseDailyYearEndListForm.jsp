<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>年终检查评分查询</title>
<script type="text/javascript" 	src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
			<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
</head>
<script type="text/javascript">
	function getContractNO(){
			var year=$('year').value;
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
	function view(id,contractorId) {
		window.location.href="${ctx}/appraiseDailyYearEndAction.do?method=viewAppraiseDailyYearEnd&id="+id+"&contractorId="+contractorId;
	}
</script>
<body>
	<template:titile value="年终检查评分查询" />
	<html:form action="/appraiseDailyYearEndAction.do?method=queryAppraiseDailyYearEndList" styleId="form" >
			<template:formTable>
				<template:formTr name="考核年份">
					<html:text property="year" style="Wdate;width:140px;" styleId="year" styleClass="inputtext required" onfocus="WdatePicker({dateFmt:'yyyy',maxDate:'${lastYear}'})"></html:text><font color="red">*</font>
				</template:formTr>
				<template:formTr name="考核代维">
					<html:select property="contractorId" styleClass="inputtext" styleId="contractorId" style="width:145px" onchange="getContractNO();">
						<html:option value="">请选择</html:option>
						<html:options collection="cons" property="contractorID" labelProperty="contractorName"></html:options>
					</html:select>
				</template:formTr>
				<template:formTr name="标包号">
					<html:select property="contractNo" styleId="contractNO" style="width:145px" styleClass="inputtext">
						<html:option value="">请选择</html:option>
					</html:select>
				</template:formTr>
				</template:formTable>
			<div align="center"><input type="submit" value="查询" class="button"/></div>
		</html:form>
	<c:if test="${appraiseDailys != null}">
		<display:table name="sessionScope.appraiseDailys"
			id="appraiseDaily" pagesize="18" export="false" defaultsort="1"
			defaultorder="descending" sort="list">
			
			<display:column media="html" title="代维公司" sortable="true">
				<c:out value="${contractor[appraiseDaily.contractorId]}"></c:out>
			</display:column>
			<display:column property="contractNo" title="标底包" maxLength="9"
				sortable="true" />
			<display:column media="html" title="年份"
				sortable="true" >
					<c:out value="${appraiseDailyBean.year}"></c:out>
				</display:column>
			<display:column property="appraiseDate" sortable="true"
				format="{0,date,yyyy-MM-dd}" title="考核时间" />
			<display:column media="html" title="考核人" sortable="true" >
				<c:out value="${userIdAndName[appraiseDaily.appraiser]}"></c:out>
			</display:column>
			<display:column media="html" title="操作">
				<a href="javascript:view('${appraiseDaily.id}','${appraiseDaily.contractorId}')">查看</a>
			</display:column>
		</display:table>
	</c:if>
</body>
<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
</html>
