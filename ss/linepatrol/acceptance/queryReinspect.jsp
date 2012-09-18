<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>

	<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
	<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">

	<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />

	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
	
	<script type="text/javascript">
		function check(){
			if( ($('begintime').value == '' && $('endtime').value != '') ||
				($('begintime').value != '' && $('endtime').value == '') ){
				alert("日期段必须同时有开始日期和结束日期");
				return false;
			}
			if( ($('begintime').value != '' && $('endtime').value != '')
					&& ($('begintime').value == $('endtime').value) ){
				alert('开始日期和结束日期不能相等');
				return false;
			}
			return true;
		}
	</script>
</head>
<body onload="changeStyle()">
	<template:titile value="复验资源查询" />
	<html:form action="/acceptanceReinspectAction.do?method=filterInspect" styleId="form" onsubmit="return check()">
		<template:formTable namewidth="150" contentwidth="380">
			<template:formTr name="工程名称">
				<html:text property="issueNumber" styleClass="inputtext validate-int-range-1-100" style="width:130px"/>
			</template:formTr>
			<template:formTr name="验收时间">
					<html:text property="begintime" styleClass="Wdate"
						style="width:130px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})"
						styleId="begintime" /> 至
					<html:text property="endtime" styleClass="Wdate" style="width:130px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})"
						styleId="endtime" />
				</template:formTr>
			<%
				String type = request.getParameter("type");
				request.setAttribute("type", type);
			%>
			<input type="hidden" name="type" value="${type}" />
			<c:choose>
				<c:when test="${type eq 'cable'}">
					<template:formTr name="工程中心项目经理">
						<html:text property="prcpm" styleClass="inputtext" style="width:130px"/>
					</template:formTr>
					<template:formTr name="光缆级别">
						<apptag:quickLoadList cssClass="input" isQuery="true" style="width:130px" name="cableLevel" listName="cabletype" type="select"/>
					</template:formTr>
				</c:when>
				<c:otherwise>
					<template:formTr name="管道中心项目经理">
						<html:text property="pcpm" styleClass="inputtext" style="width:130px"/>
					</template:formTr>
				</c:otherwise>
			</c:choose>
			
			<template:formSubmit>
				<td>
					<html:submit property="action" styleClass="button">查询</html:submit>
				</td>
			</template:formSubmit>
		</template:formTable>
	</html:form>
	<script type="text/javascript">
		function formCallback(result, form) {
			window.status = "valiation callback for form '" + form.id + "': result = " + result;
		}

		var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
</body>
</html>