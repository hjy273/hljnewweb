<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>故障基准指标</title>
		    <script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
			<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
		
	</head>

	<body>
		<template:titile value="添加故障基准指标" />
		<html:form action="/troubleQuotaAction.do?method=saveQuota"
			styleId="quotaInfo">
			<table width="70%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			  <tr>
			    <td colspan="4" bgcolor="#E6EFF7" height="25px" align="center">
			    	<font size="2px" style="font-weight: bold">一干故障指标</font>
			    </td>
			  </tr>
			  <tr  class=trwhite>
			    <td colspan="4">一干千公里阻断历时</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">基准值：</td>
			    <td class="tdulright"><html:text property="interdictionTimeNormValue" styleClass="required validate-integer-float-double"></html:text></td>
			    <td class="tdulleft">挑战值：</td>
			    <td class="tdulright"><html:text property="interdictionTimeDareValue" styleClass="required validate-integer-float-double"></html:text></td>
			  </tr>
			  <tr  class=trwhite>
			    <td colspan="4">一干千公里阻断次数</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">基准值：</td>
			    <td class="tdulright"><html:text property="interdictionTimesNormValue" styleClass="required validate-integer-float-double"></html:text></td>
			    <td class="tdulleft">挑战值：</td>
			    <td class="tdulright"><html:text property="interdictionTimesDareValue" styleClass="required validate-integer-float-double"></html:text></td>
			  </tr>
			  <tr  class=trwhite>
			    <td colspan="4">一干光缆平均抢修历时</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">基准值：</td>
			    <td class="tdulright"><html:text property="rtrTimeNormValue"  styleClass="required validate-integer-float-double"></html:text></td>
			    <td class="tdulleft">挑战值：</td>
			    <td class="tdulright"><html:text property="rtrTimeDareValue"  styleClass="required validate-integer-float-double"></html:text></td>
			  </tr>
			  <tr  class=trwhite>
			    <td colspan="4">一干单次抢修历时</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">基准值：</td>
			    <td class="tdulright" colspan="3"><html:text property="singleRtrTimeNormValue" styleClass="required validate-integer-float-double"></html:text></td>
			  
			  </tr>
			  <tr> 
			    <td colspan="4" height="25px" bgcolor="#E6EFF7" align="center">
			    	<font size="2px" style="font-weight: bold">城域网故障指标</font>
			    </td>
			  </tr>
			  <tr  class=trwhite>
			    <td colspan="4">城域网千公里阻断历时</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">基准值：</td>
			    <td class="tdulright"><html:text property="interdictionTimeNormValueCity" styleClass="required validate-integer-float-double"></html:text></td>
			    <td class="tdulleft">挑战值：</td>
			    <td class="tdulright"><html:text property="interdictionTimeDareValueCity"  styleClass="required validate-integer-float-double"></html:text></td>
			  </tr>
			  <tr  class=trwhite>
			    <td colspan="4">城域网千公里阻断次数</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">基准值：</td>
			    <td class="tdulright"><html:text property="interdictionTimesNormValueCity" styleClass="required validate-integer-float-double"></html:text></td>
			    <td class="tdulleft">挑战值：</td>
			    <td class="tdulright"><html:text property="interdictionTimesDareValueCity"  styleClass="required validate-integer-float-double"></html:text></td>
			  </tr>
			  <tr  class=trwhite>
			    <td colspan="4">城域网光缆平均抢修历时</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">基准值：</td>
			    <td class="tdulright"><html:text property="rtrTimeNormValueCity" styleClass="required validate-integer-float-double"></html:text></td>
			    <td class="tdulleft">挑战值：</td>
			    <td class="tdulright"><html:text property="rtrTimeDareValueCity"  styleClass="required validate-integer-float-double"></html:text></td>
			  </tr>
			  <tr  class=trwhite> 
			    <td colspan="4">城域网单次抢修历时</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">基准值：</td>
			    <td class="tdulright" colspan="3"><html:text property="singleRtrTimeNormValueCity" styleClass="required validate-integer-float-double"></html:text></td>
			  </tr>
			  <tr align="center">
				  <td colspan="4">
					  <html:submit property="action" styleClass="button" value="提交"></html:submit>
					  <html:reset property="action" styleClass="button">重置</html:reset>
				  </td>
			  </tr>
			</table>
		</html:form>
		<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('quotaInfo', {immediate : true, onFormValidate : formCallback});
		</script>
	</body>
</html>
