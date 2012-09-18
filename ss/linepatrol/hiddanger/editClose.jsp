<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
	<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
	
	<script type="text/javascript">
		function check(){
			if($('reliefTime').value == ''){
				alert("隐患解除时间不能为空");
				return false;
			}
			processBar(form);
			return true;
		}
		function showMap(){
			var url = '/WEBGIS/gisextend/igis.jsp?userid=${LOGIN_USER.userID}&actiontype=205&x=${registBean.x}&y=${registBean.y}&label=${registBean.name}';
			window.open(url,'map','width=800,height=600,scrollbars=yes,resizable=yes,toolbar=no,menubar=no');
		}
		function viewPlan(id){
			window.location.href = '${ctx}/specialplan.do?method=loadPlan&type=view&ptype=001&isApply=false&id='+id;
		}
		function viewPlanStat(id){
			window.location.href = '${ctx}/linepatrol/specialplan/stat/stat.jsp?type=001&id='+id;
		}
	</script>
</head>
<body onload="changeStyle()">
	<template:titile value="隐患关闭" />
	<html:form action="/hiddangerCloseAction.do?method=editClose" styleId="form" onsubmit="return check();">
		<html:hidden property="id" />
		<html:hidden property="hiddangerId" />
		<html:hidden property="approveTimes" />
		<template:formTable namewidth="200" contentwidth="500">
			<input type="hidden" name="hiddangerId" value="<bean:write name="registBean" property="id" />">
			<template:formTr name="隐患编号">
				<bean:write name="registBean" property="hiddangerNumber" />
			</template:formTr>
			<template:formTr name="登记单位">
				<bean:write name="registBean" property="createrDept" />
			</template:formTr>
			<template:formTr name="登记人">
				<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="${registBean.creater}" />
			</template:formTr>
			<template:formTr name="隐患位置">
				<bean:write name="registBean" property="x" />,<bean:write name="registBean" property="y" /><input type="button" onclick="showMap();" value="查看位置" />
			</template:formTr>
			<template:formTr name="隐患发现时间">
				<bean:write name="registBean" property="findTime" />
			</template:formTr>
			<template:formTr name="隐患发现方式">
				<bean:write name="registBean" property="findType" />
			</template:formTr>
			<template:formTr name="隐患上报时间">
				<bean:write name="registBean" property="createTime" />
			</template:formTr>
			<template:formTr name="发现隐患来源">
				<bean:write name="registBean" property="reporter" />
			</template:formTr>
			<template:formTr name="隐患处理单位">
				<apptag:assorciateAttr table="contractorinfo" label="contractorname" key="contractorid" keyValue="${registBean.treatDepartment}" />
			</template:formTr>
			<template:formTr name="隐患分类">
			    <apptag:assorciateAttr table="troubletype" label="typename" key="id" keyValue="${registBean.type}" />
			</template:formTr>
			<template:formTr name="隐患名称">
				<apptag:assorciateAttr table="troublecode" label="troublename" key="id" keyValue="${registBean.code}" />
			</template:formTr>
			<template:formTr name="隐患等级">
				<bean:write name="registBean" property="hiddangerLevel" />级
			</template:formTr>
			<template:formTr name="隐患评级备注">
				<bean:write name="registBean" property="remark" />
			</template:formTr>
			<template:formTr name="隐患地点描述">
				<bean:write name="reportBean" property="address" />
			</template:formTr>
			<template:formTr name="隐患点距光缆距离">
				<bean:write name="reportBean" property="distanceToCable" />米
			</template:formTr>
			<template:formTr name="隐患盯防负责人">
				<bean:write name="reportBean" property="watchPrincipal" />
			</template:formTr>
			<template:formTr name="隐患盯防负责人电话">
				<bean:write name="reportBean" property="watchPrincipalPhone" />
			</template:formTr>
			<template:formTr name="隐患盯防实施人">
				<bean:write name="reportBean" property="watchActualizeMan" />
			</template:formTr>
			<template:formTr name="隐患盯防实施人电话">
				<bean:write name="reportBean" property="watchActualizeManPhone" />
			</template:formTr>
			<template:formTr name="施工负责人">
				<bean:write name="reportBean" property="workPrincipal" />
			</template:formTr>
			<template:formTr name="施工负责人电话">
				<bean:write name="reportBean" property="workPrincipalPhone" />
			</template:formTr>
			<template:formTr name="施工单位">
				<bean:write name="reportBean" property="workDepart" />
			</template:formTr>
			<template:formTr name="施工阶段">
				<apptag:quickLoadList cssClass="input" style="width:200" name="workStage" listName="workstage" type="look" keyValue="${reportBean.workStage}"/>
			</template:formTr>
			<template:formTr name="计划隐患消除时间">
				<bean:write name="reportBean" property="planReliefTime" />
			</template:formTr>
			<template:formTr name="盯防介入时间">
				<bean:write name="reportBean" property="watchBeginTime" />
			</template:formTr>
			<template:formTr name="是否签署安全协议">
				<c:choose>
					<c:when test="${reportBean.signSecurityProtocol eq '1'}">
						是
					</c:when>
					<c:otherwise>
						否
					</c:otherwise>
				</c:choose>
			</template:formTr>
			<template:formTr name="受影响光缆段">
				<apptag:trunk id="trunk" state="view" value="${reportBean.trunkIdsString}" />
			</template:formTr>
			<template:formTr name="其他受影响光缆段">
				<bean:write name="reportBean" property="otherImpressCable" />
			</template:formTr>
			<template:formTr name="隐患盯防原因">
				<bean:write name="reportBean" property="watchReason" />
			</template:formTr>
			<template:formTr name="隐患盯防措施">
				<bean:write name="reportBean" property="watchMeasure" />
			</template:formTr>
			<template:formTr name="备注">
				<bean:write name="reportBean" property="reportRemark" />
			</template:formTr>
			<template:formTr name="附件">
				<apptag:upload state="edit" cssClass="" entityId="${registBean.id}" entityType="LP_HIDDANGER_REPORT"/>
			</template:formTr>
			<template:formTr name="隐患解除时间">
				<html:text property="reliefTime" styleId="reliefTime" styleClass="Wdate required" style="width:200" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1',maxDate:'%y-%M-%d'})" /><font color="red">*</font>
			</template:formTr>
			<template:formTr name="隐患解除原因">
				<html:textarea property="reliefReason" styleClass="inputtextarea required" rows="4" cols="45" /><font color="red">*</font>
			</template:formTr>
			<template:formTr name="备注">
				<html:textarea property="remark" styleClass="inputtextarea" rows="4" cols="45" />
			</template:formTr>
			<template:formTr name="盯防计划">
				<jsp:include page="statIframe.jsp" />
			</template:formTr>
			<template:formSubmit>
				<td>
					<html:submit property="action" styleClass="button">提交</html:submit>
				</td>
				<td>
					<html:button property="action" styleClass="button" onclick="history.back()">返回</html:button>
				</td>
			</template:formSubmit>
		</template:formTable>
		<jsp:include page="note.jsp" />
	</html:form>
	
	<script type="text/javascript">
		function formCallback(result, form) {
			window.status = "valiation callback for form '" + form.id + "': result = " + result;
		}

		var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
</body>
