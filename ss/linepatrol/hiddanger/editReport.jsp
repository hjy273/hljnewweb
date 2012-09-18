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
			if($('planReliefTime').value == ''){
				alert('计划隐患消除时间');
				$('planReliefTime').focus();
				return false;
			}
			if($('watchBeginTime').value == ''){
				alert('盯防介入时间');
				$('watchBeginTime').focus();
				return false;
			}
			if($('watchBeginTime').value > $('planReliefTime').value){
				alert('盯防介入时间应该小于计划隐患消除时间');
				return false;
			}
			if($('trunknames').value == ''){
				alert('受影响光缆段不能为空');
				
				return false;
			}
			processBar(form);
			return true;
		}
		function setWatchPrincipal(obj,name,mobile){
			var label = obj.options[obj.selectedIndex].innerHTML;
			var val = obj.options[obj.selectedIndex].value;
			$(name).value = label;
			$(mobile).value = val;
		}
		function showMap(){
			var url = '/WEBGIS/gisextend/igis.jsp?userid=${LOGIN_USER.userID}&actiontype=205&x=${registBean.x}&y=${registBean.y}&label=${registBean.name}';
			window.open(url,'map','width=800,height=600,scrollbars=yes,resizable=yes,toolbar=no,menubar=no');
		}
	</script>
</head>
<body onload="changeStyle();">
	<template:titile value="隐患上报" />
	<html:form action="/hiddangerReportAction.do?method=editReport" styleId="form" onsubmit="return check();" enctype="multipart/form-data">
		<html:hidden property="id" />
		<html:hidden property="hiddangerId" />
		<html:hidden property="authorId" />
		<template:formTable namewidth="200" contentwidth="400">
			<template:formTr name="隐患编号">
				<bean:write name="registBean" property="hiddangerNumber" />
			</template:formTr>
			<template:formTr name="登记名称">
				<bean:write name="registBean" property="name" />
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
			<template:formTr name="附件">
				<apptag:upload state="edit" cssClass="" entityId="${registBean.id}" entityType="LP_HIDDANGER_REPORT"/>
			</template:formTr>
			<template:formTr name="隐患地点描述">
				<html:text property="address" styleClass="inputtext required" style="width:200"/>
			</template:formTr>
			<template:formTr name="隐患点距光缆距离">
				<html:text property="distanceToCable" styleClass="inputtext required validate-number" style="width:200"/>  米
			</template:formTr>
			<template:formTr name="隐患盯防负责人">
				<html:select property="tempWatchPrincipal" styleClass="inputtext required" style="width:200" onchange="setWatchPrincipal(this,'watchPrincipal','watchPrincipalPhone');">
			      	<html:options collection="principal" property="value" labelProperty="label"/>
			    </html:select>
				<html:hidden property="watchPrincipal" styleId="watchPrincipal" styleClass="inputtext required" style="width:200"/>
			</template:formTr>
			<template:formTr name="隐患盯防负责人电话">
				<html:text property="watchPrincipalPhone" styleId="watchPrincipalPhone" styleClass="inputtext validate-pattern-/^\d+(,\d+)*$/" style="width:200"/>
			</template:formTr>
			<template:formTr name="隐患盯防实施人">
				<html:select property="tempWatchActualizeMan" styleClass="inputtext required" style="width:200" onchange="setWatchPrincipal(this,'watchActualizeMan','watchActualizeManPhone');">
			      	<html:options collection="principal" property="value" labelProperty="label"/>
			    </html:select>
				<html:hidden property="watchActualizeMan" styleId="watchActualizeMan" styleClass="inputtext required" style="width:200"/>
			</template:formTr>
			<template:formTr name="隐患盯防实施人电话">
				<html:text property="watchActualizeManPhone" styleId="watchActualizeManPhone" styleClass="inputtext validate-pattern-/^\d+(,\d+)*$/" style="width:200"/>
			</template:formTr>
			<template:formTr name="施工负责人">
				<html:text property="workPrincipal" styleClass="inputtext required" style="width:200"/>
			</template:formTr>
			<template:formTr name="施工负责人电话">
				<html:text property="workPrincipalPhone" styleClass="inputtext" style="width:200"/>
			</template:formTr>
			<template:formTr name="施工单位">
				<html:text property="workDepart" styleClass="inputtext required" style="width:200"/>
			</template:formTr>
			<template:formTr name="施工阶段">
				<apptag:quickLoadList cssClass="input" name="workStage" listName="workstage" type="select" keyValue="${reportBean.workStage}"/>
			</template:formTr>
			<template:formTr name="盯防介入时间">
				<html:text property="watchBeginTime" styleId="watchBeginTime" styleClass="Wdate" style="width:200" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1'})"/>
			</template:formTr>
			<template:formTr name="计划隐患消除时间">
				<html:text property="planReliefTime" styleId="planReliefTime" styleClass="Wdate" style="width:200" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1'})"/>
			</template:formTr>
			<template:formTr name="是否签署安全协议">
				<html:radio property="signSecurityProtocol" value="1" />是
				<html:radio property="signSecurityProtocol" value="0" />否
			</template:formTr>
			<template:formTr name="受影响光缆段">
				<apptag:trunk id="trunk" rows="4" cols="45" state="edit" value="${reportBean.trunkIdsString}" />
			</template:formTr>
			<template:formTr name="受影响路由长度">
				<html:text property="impressLength" styleClass="inputtext required validate-number" style="width:200"/>  米
			</template:formTr>
			<template:formTr name="其他受影响光缆段">
				<html:textarea rows="4" cols="45" property="otherImpressCable" styleClass="inputtextarea required"/>
			</template:formTr>
			<template:formTr name="隐患盯防原因">
				<html:textarea rows="4" cols="45" property="watchReason" styleClass="inputtextarea required"/>
			</template:formTr>
			<template:formTr name="隐患盯防措施">
				<html:textarea rows="4" cols="45" property="watchMeasure" styleClass="inputtextarea required"/>
			</template:formTr>
			<template:formTr name="备注">
				<html:textarea rows="4" cols="45" property="reportRemark" styleClass="inputtextarea"/>
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
