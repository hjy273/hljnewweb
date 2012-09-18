<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title></title>

		<script language="javascript" src="${ctx}/js/validation/prototype.js"
			type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js"
			type=""></script>
		<script language="javascript"
			src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css"
			rel="stylesheet" type="text/css">

		<script type='text/javascript'
			src='${ctx}/linepatrol/js/change_style.js'></script>

		<script type="text/javascript">
	function getTroubleCode(typeId) {
		$("code").length = 1;
		var url = "${ctx}/hiddangerAction.do?method=getTroubleCodeSelection&typeId="
				+ typeId;
		var myAjax = new Ajax.Request(url, {
			method : 'get',
			asynchronous : 'true',
			onComplete : setTroubleCode
		});
	}
	function setTroubleCode(response) {
		var str = response.responseText;
		if (str == "")
			return true;
		var optionlist = str.split("&");
		for ( var i = 1; i <= optionlist.length; i++) {
			var t = optionlist[i].split(",")[0];
			var v = optionlist[i].split(",")[1];
			$("code").options[i] = new Option(v, t);
		}
	}
</script>
	</head>
	<body onload="changeStyle()">
		<template:titile value="隐患统计" />

		<!-- 查询页面 -->
		<html:form action="/hiddangerQueryAction.do?method=queryStat"
			styleId="form">
			<template:formTable namewidth="150" contentwidth="400">
				<template:formTr name="登记名称">
					<html:text property="name" styleClass="inputtext"
						style="width:140px" />
				</template:formTr>
				<template:formTr name="隐患分类">
					<apptag:setSelectOptions columnName1="typename" columnName2="id"
						tableName="troubletype" valueName="troubletype" />
					<html:select property="type" styleClass="inputtext"
						style="width:140px"
						onchange="getTroubleCode(this.options[this.selectedIndex].value)">
						<html:option value="">不限</html:option>
						<html:options collection="troubletype" property="value"
							labelProperty="label" />
					</html:select>
				</template:formTr>
				<template:formTr name="隐患名称">
					<html:select property="code" styleClass="inputtext"
						style="width:140px">
						<html:option value="">不限</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="统计时间">
					<html:text property="begintime" styleClass="Wdate"
						style="width:140px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})" /> 至
				<html:text property="endtime" styleClass="Wdate" style="width:140px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})" />
				</template:formTr>
			</template:formTable>
			<div align="center">
				<input type="hidden" name="isQuery" value="true" />
				<html:submit property="action" styleClass="button">查询</html:submit>
				<html:reset property="action" styleClass="button">重置</html:reset>
			</div>
		</html:form>
		<template:displayHide styleId="form"></template:displayHide>
		<!-- 查询结果 -->
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<script type="text/javascript">
	function view(id) {
		window.location.href = '${ctx}/hiddangerAction.do?method=viewHiddangerForStat&id=' + id;
	}
</script>
		<logic:notEmpty name="result">
			<display:table name="sessionScope.result" id="row" pagesize="18"
				sort="list">
				<display:column property="hiddangerNumber" title="隐患编号"
					sortable="true" />
				<display:column property="createrDept" title="登记单位" sortable="true" />
				<display:column media="csv excel xml html" title="登记人"
					sortable="true">
					<c:out value="${users[row.creater]}" />
				</display:column>
				<display:column property="name" title="登记名称" sortable="true" />
				<display:column media="csv excel xml html" title="分类"
					sortable="true">
					<c:out value="${types[row.type]}" />
				</display:column>
				<display:column media="csv excel xml html" title="名称"
					sortable="true">
					<c:out value="${codes[row.code]}" />
				</display:column>
				<display:column media="csv excel xml html" title="处理单位"
					sortable="true">
					<c:out value="${depts[row.treatDepartment]}" />
				</display:column>
				<display:column media="csv excel xml html" title="登记时间"
					sortable="true">
					<bean:write name="row" property="createTime"
						format="yyyy-MM-dd HH:mm:ss" />
				</display:column>
				<display:column media="html" title="操作" sortable="true">
					<a href="javascript:view('${row.id}')">查看</a>
				</display:column>
			</display:table>
			<table class="tdlist">
				<tr>
					<td class="tdlist">
						<html:button property="action" styleClass="button"
							onclick="history.back()">返回</html:button>
					</td>
				</tr>
			</table>
		</logic:notEmpty>
	</body>