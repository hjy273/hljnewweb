<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>���ռ�����ͳ��</title>
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
			window.location.href="${ctx}/appraiseYearEndAction.do?method=viewAppraiseMonth&flag=view&resultId="+id;
		}	
	</script>
	<body>
		<template:titile value="���ռ�����ͳ��" />
		<html:form action="/appraiseYearEndAction.do?method=queryAppraiseStat" styleId="form">
			<template:formTable>
				<template:formTr name="�������">
					<html:text property="appraiseTime" style="Wdate;width:140px;" styleId="appraiseTime" styleClass="inputtext required" onfocus="WdatePicker({dateFmt:'yyyy'})"></html:text>
				</template:formTr>
				</template:formTable>
			<div align="center"><input type="submit" value="��ѯ" class="button"/></div>
		</html:form>
				<logic:notEmpty name="stat">
			<table style="width:100%;" class="tabout" align="center">
		<tr class="trcolor" align="center">
						<td style="text-align: center; vertical-align: middle;" width="20%">�����</td>
						<td style="text-align: center; vertical-align: middle;" width="20%">���˽��</td>
						<td style="text-align: center; vertical-align: middle;" width="20%">����ʱ��</td>
						<td style="text-align: center; vertical-align: middle;" width="20%">������</td>
					</tr>
			<c:forEach var="yearEndStatMap" items="${stat}">
				<tr class="trcolorgray">
					<td colspan="4">${yearEndStatMap['contractorName']}</td>
				</tr>
				<c:forEach var="appraiseYearResult" items="${yearEndStatMap['yearEndList']}">
					<tr class="trcolor" >
						<td style="text-align: center; vertical-align: middle;" ><a href="javascript:view('${appraiseYearResult.id}');">${appraiseYearResult.contractNO }</a></td>
						<td style="text-align: center; vertical-align: middle;" >${appraiseYearResult.result}</td>
						<td style="text-align: center; vertical-align: middle;" >${appraiseYearResult.appraiseDate }</td>
						<td style="text-align: center; vertical-align: middle;" >${appraiseYearResult.appraiser }</td>
					</c:forEach>
					</tr>
					<tr class="trcolor" align="center">
						<td style="text-align: center; vertical-align: middle;" colspan="4">ƽ���֣�${yearEndStatMap['avgResult']}</td>
			</c:forEach>
		</table>
		</logic:notEmpty>
			<table border="0" cellpadding="0" cellspacing="0" width="100%"
				style="border: 0px">
				<tr>
					<td style="text-align: center;">
						<input name="action" class="button" value="����" onclick="history.back();"
							type="button" />
					</td>
				</tr>
			</table>
		<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
	</body>
</html>
