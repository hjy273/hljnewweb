<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title></title>
		<script type="text/javascript" defer="defer"
			src="<%=request.getContextPath()%>/js/WdatePicker/WdatePicker.js"></script>
	</head>
	<script type="text/javascript">
	function GetSelectDateTHIS(strID) {
		document.all.item(strID).value = getPopDate(document.all.item(strID).value);
		//开始时间
		var yb = queryForm2.beginTime.value.substring(0, 4);
		var mb = parseInt(queryForm2.beginTime.value.substring(5, 7), 10);
		var db = parseInt(queryForm2.beginTime.value.substring(8, 10), 10);
		//结束时间
		var ye = queryForm2.endTime.value.substring(0, 4);
		var me = parseInt(queryForm2.endTime.value.substring(5, 7), 10);
		var de = parseInt(queryForm2.endTime.value.substring(8, 10), 10);
		if (yb == "" && ye != "") {
			alert("请输入查询的开始时间!");
			queryForm2.beginTime.focus();
			return false;
		}
		// 在开始和结束时间都输入的情况下做不能跨年度的判断
		if (yb != "" && ye != "") {
			//if(yb!=ye){
			//	alert("查询时间段不能跨年度!");
			//	document.all.item(strID).value="";
			//	queryForm2.endTime.focus();
			//	return false;
			//}
			if (ye < yb) {
				alert("查询结束日期不能小于开始日期!");
				document.all.item(strID).value = "";
				queryForm2.endTime.focus();
				return false;
			}
			if ((ye == yb) && (me < mb || de < db)) {
				alert("查询结束日期不能小于开始日期!");
				document.all.item(strID).value = "";
				queryForm2.endTime.focus();
				return false;
			}
		}
		return true;
	}
</script>
	<body>
		<template:titile value="查询材料申请" />
		<html:form action="/material_apply.do?method=queryMaterialApplyList">
			<template:formTable namewidth="150" contentwidth="300">
				<template:formTr name="材料来源">
					<select name="type" class="inputtext" style="width: 215px"
						id="typeID">
						<option value="">
							不限
						</option>
						<option value="1">
							新增材料
						</option>
						<option value="0">
							利旧材料
						</option>
						<option value="2">
							自购材料
						</option>
					</select>
				</template:formTr>
				<template:formTr name="开始时间">
					<input id="begin" type="text" name="beginTime" readonly="readonly"
						class="inputtext" style="width: 215px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
				</template:formTr>
				<template:formTr name="结束时间">
					<input id="end" type="text" name="endTime" readonly="readonly"
						class="inputtext" style="width: 215px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit property="action" styleClass="button">查询</html:submit>
					</td>
					<td>
						<html:reset property="action" styleClass="button">重置</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
