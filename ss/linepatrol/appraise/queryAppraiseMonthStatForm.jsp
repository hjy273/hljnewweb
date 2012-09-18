<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>月考核表统计</title>
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
		<link type="text/css" rel="stylesheet" href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	</head>
	<script type="text/javascript">
	function view(id){
			window.location.href="${ctx}/appraiseMonthAction.do?method=viewAppraiseMonth&flag=view&resultId="+id;
		}
	</script>
	<body>
		<template:titile value="月考核评分统计" />
		<html:form action="/appraiseMonthAction.do?method=queryAppraiseStat"
			styleId="form" >
			<template:formTable>
				<template:formTr name="时间段">
					<html:text property="fromDate" styleId="fromDate" style="Wdate;width:135px" styleClass="inputtext required"  onfocus="WdatePicker({dateFmt:'yyyy-MM'})"></html:text>
					--
					<html:text property="toDate" styleId="toDate"  style="Wdate;width:135px" styleClass="inputtext required"
						onfocus="WdatePicker({dateFmt:'yyyy-MM',minDate:'#F{$dp.$D(\'fromDate\')}'})" /><font color="red">*</font>
				</template:formTr>
				<template:formTr name="考核分数">
					<html:text property="score" styleClass="required validate-number" style="width:135px"></html:text><font color="red">*</font>&nbsp;&nbsp;<html:radio property="scope" styleId="scope" value=">=">大于等于</html:radio>&nbsp;&nbsp;<html:radio property="scope" styleId="scope" value="<=">小于等于</html:radio>
				</template:formTr>
				<template:formTr name="考核代维">
					<html:select property="contractorId" styleClass="inputtext required" styleId="contractorId" style="width:140px" onchange="getContractNO()">
						<html:option value="">全部</html:option>
						<html:options collection="cons" property="contractorID" labelProperty="contractorName"></html:options>
					</html:select>
				</template:formTr>
				</template:formTable>
			<div align="center"><input type="submit" value="查询" class="button"/></div>
		</html:form>
		<logic:notEmpty name="stat">
		<table style="width:100%;" class="tabout" align="center">
			<c:forEach var="monthStatMap" items="${stat}">
				<tr class="trcolor">
					<td colspan="5">${monthStatMap['contractorName']}</td>
				</tr>
				<tr class="trcolor" align="center">
					<td style="text-align: center; vertical-align: middle;" width="20%">考核月份</td>
					<td style="text-align: center; vertical-align: middle;" width="20%">标底包</td>
					<td style="text-align: center; vertical-align: middle;" width="20%">考核结果</td>
					<td style="text-align: center; vertical-align: middle;" width="20%">考核时间</td>
					<td style="text-align: center; vertical-align: middle;" width="20%">考核人</td>
				</tr>
				<c:forEach var="conStatMap" items="${monthStatMap['conStatList']}">
					<c:forEach var="appraiseResult" items="${conStatMap['monthList']}">
						<tr class="trcolor" align="center">
							<td style="text-align: center; vertical-align: middle;textTransform" ><a href="javascript:view('${appraiseResult.id}');">${appraiseResult.appraiseTime }</a></td>
							<td style="text-align: center; vertical-align: middle;" >${appraiseResult.contractNO }</td>
							<td style="text-align: center; vertical-align: middle;" >${appraiseResult.result }</td>
							<td style="text-align: center; vertical-align: middle;" >${appraiseResult.appraiseDate }</td>
							<td style="text-align: center; vertical-align: middle;" >${appraiseResult.appraiser }</td>
						</tr>
					</c:forEach>
					<tr class="trcolor" align="center">
						<td style="text-align: center; vertical-align: middle;" colspan="5">平均分：${conStatMap['avgResult']}&nbsp;&nbsp;&nbsp;&nbsp;总分：${conStatMap['sumResult']}</td>
					</tr>
				</c:forEach>
			</c:forEach>
		</table>
		</logic:notEmpty>
	</body>
	<script type="text/javascript">
		function formCallback(result, form) {
			window.status = "valiation callback for form '" + form.id + "': result = " + result;
		}

		var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
</html>
