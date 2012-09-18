<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>������Ŀ�ճ����˲�ѯ</title>
<script type="text/javascript" 	src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
			<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
</head>
<script type="text/javascript">
	function getContractNO() {
		var appraiseMonth = $('appraiseTime').value;
		var contractorId = $('contractorId').value;
		$("contractNO").length = 1;
		var url = "${ctx}/appraiseMonthAction.do?method=getContractNO&appraiseTime="
				+ appraiseMonth + "&contractorid=" + contractorId;
		var myAjax = new Ajax.Request(url, {
			method : 'post',
			asynchronous : true,
			onComplete : setContractNO
		});
	}

	function setContractNO(response) {
		var str = response.responseText;
		if (str == "") {
			return true;
		}
		var optionlist = str.split(",");

		for ( var i = 0; i < optionlist.length; i++) {
			$("contractNO").options[i + 1] = new Option(optionlist[i],
					optionlist[i]);
		}
	}
	function view(id,contractorId) {
		window.location.href="${ctx}/appraiseDailyOtherAction.do?method=viewAppraiseDaily&id="+id+"&contractorId="+contractorId;
	}
</script>
<body>
	<template:titile value="������Ŀ�ճ�����" />
	<html:form
		action="/appraiseDailyOtherAction.do?method=queryAppraiseDailyOtherList"
		styleId="queryAppraiseDailyOther">
		<template:formTable>
			<template:formTr name="��ѯ�·�">
				<input type="text" name="appraiseTime" style="Wdate;width:135px"
					class="inputtext required" value="${appraiseTime}" onchange="getContractNO()"
					id="appraiseTime"
					onfocus="WdatePicker({dateFmt:'yyyy-MM',maxDate:'%y-%M',minDate:'${minDate}'})"></input><font color="red">*</font>
			</template:formTr>
			<template:formTr name="���˴�ά">
				<html:select property="contractorId" styleClass="inputtext required"
					styleId="contractorId" style="width:140px"
					onchange="getContractNO()">
					<html:option value="">ȫ��</html:option>
					<html:options collection="cons" property="contractorID"
						labelProperty="contractorName"></html:options>
				</html:select>
			</template:formTr>
			<template:formTr name="��װ�">
				<select name="contractNO" id="contractNO"
					class="inputtext" style="width:140px">
					<option value="">ȫ��</option>
				</select>
			</template:formTr>
		</template:formTable>
		<div align="center">
			<input type="submit" value="��ѯ" class="button" />
		</div>
	</html:form>
	<c:if test="${appraiseDailys != null}">
		<display:table name="sessionScope.appraiseDailys"
			id="appraiseDaily" pagesize="18" export="false" defaultsort="1"
			defaultorder="descending" sort="list">
			
			<display:column media="html" title="��ά��˾" sortable="true">
				<c:out value="${contractor[appraiseDaily.contractorId]}"></c:out>
			</display:column>
			<display:column property="contractNo" title="��װ�" maxLength="9"
				sortable="true" />
			<display:column property="appraiseDate" sortable="true"
				format="{0,date,yyyy-MM-dd}" title="��������" />
			<display:column media="html" title="������" sortable="true" >
				<c:out value="${userIdAndName[appraiseDaily.appraiser]}"></c:out>
			</display:column>
			<display:column media="html" title="����">
				<a href="javascript:view('${appraiseDaily.id}','${appraiseDaily.contractorId}')">�鿴</a>
			</display:column>
		</display:table>
	</c:if>
</body>
<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('queryAppraiseDailyOther', {immediate : true, onFormValidate : formCallback});
	</script>
</html>
