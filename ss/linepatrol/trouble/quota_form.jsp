<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>���ϻ�׼ָ��</title>
		    <script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
			<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
		
	</head>

	<body>
		<template:titile value="��ӹ��ϻ�׼ָ��" />
		<html:form action="/troubleQuotaAction.do?method=saveQuota"
			styleId="quotaInfo">
			<table width="70%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			  <tr>
			    <td colspan="4" bgcolor="#E6EFF7" height="25px" align="center">
			    	<font size="2px" style="font-weight: bold">һ�ɹ���ָ��</font>
			    </td>
			  </tr>
			  <tr  class=trwhite>
			    <td colspan="4">һ��ǧ���������ʱ</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">��׼ֵ��</td>
			    <td class="tdulright"><html:text property="interdictionTimeNormValue" styleClass="required validate-integer-float-double"></html:text></td>
			    <td class="tdulleft">��սֵ��</td>
			    <td class="tdulright"><html:text property="interdictionTimeDareValue" styleClass="required validate-integer-float-double"></html:text></td>
			  </tr>
			  <tr  class=trwhite>
			    <td colspan="4">һ��ǧ������ϴ���</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">��׼ֵ��</td>
			    <td class="tdulright"><html:text property="interdictionTimesNormValue" styleClass="required validate-integer-float-double"></html:text></td>
			    <td class="tdulleft">��սֵ��</td>
			    <td class="tdulright"><html:text property="interdictionTimesDareValue" styleClass="required validate-integer-float-double"></html:text></td>
			  </tr>
			  <tr  class=trwhite>
			    <td colspan="4">һ�ɹ���ƽ��������ʱ</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">��׼ֵ��</td>
			    <td class="tdulright"><html:text property="rtrTimeNormValue"  styleClass="required validate-integer-float-double"></html:text></td>
			    <td class="tdulleft">��սֵ��</td>
			    <td class="tdulright"><html:text property="rtrTimeDareValue"  styleClass="required validate-integer-float-double"></html:text></td>
			  </tr>
			  <tr  class=trwhite>
			    <td colspan="4">һ�ɵ���������ʱ</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">��׼ֵ��</td>
			    <td class="tdulright" colspan="3"><html:text property="singleRtrTimeNormValue" styleClass="required validate-integer-float-double"></html:text></td>
			  
			  </tr>
			  <tr> 
			    <td colspan="4" height="25px" bgcolor="#E6EFF7" align="center">
			    	<font size="2px" style="font-weight: bold">����������ָ��</font>
			    </td>
			  </tr>
			  <tr  class=trwhite>
			    <td colspan="4">������ǧ���������ʱ</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">��׼ֵ��</td>
			    <td class="tdulright"><html:text property="interdictionTimeNormValueCity" styleClass="required validate-integer-float-double"></html:text></td>
			    <td class="tdulleft">��սֵ��</td>
			    <td class="tdulright"><html:text property="interdictionTimeDareValueCity"  styleClass="required validate-integer-float-double"></html:text></td>
			  </tr>
			  <tr  class=trwhite>
			    <td colspan="4">������ǧ������ϴ���</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">��׼ֵ��</td>
			    <td class="tdulright"><html:text property="interdictionTimesNormValueCity" styleClass="required validate-integer-float-double"></html:text></td>
			    <td class="tdulleft">��սֵ��</td>
			    <td class="tdulright"><html:text property="interdictionTimesDareValueCity"  styleClass="required validate-integer-float-double"></html:text></td>
			  </tr>
			  <tr  class=trwhite>
			    <td colspan="4">����������ƽ��������ʱ</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">��׼ֵ��</td>
			    <td class="tdulright"><html:text property="rtrTimeNormValueCity" styleClass="required validate-integer-float-double"></html:text></td>
			    <td class="tdulleft">��սֵ��</td>
			    <td class="tdulright"><html:text property="rtrTimeDareValueCity"  styleClass="required validate-integer-float-double"></html:text></td>
			  </tr>
			  <tr  class=trwhite> 
			    <td colspan="4">����������������ʱ</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">��׼ֵ��</td>
			    <td class="tdulright" colspan="3"><html:text property="singleRtrTimeNormValueCity" styleClass="required validate-integer-float-double"></html:text></td>
			  </tr>
			  <tr align="center">
				  <td colspan="4">
					  <html:submit property="action" styleClass="button" value="�ύ"></html:submit>
					  <html:reset property="action" styleClass="button">����</html:reset>
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
