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
			if($('hiddangerRemoveTime').value == ''){
				alert('隐患盯防解除时间不能为空');
				$('hiddangerRemoveTime').focus();
				return false;
			}
			if($('watchReliefTime').value == ''){
				alert('隐患消除时间不能为空');
				$('watchReliefTime').focus();
				return false;
			}
			if($('trunknames').value == ''){
				alert('受影响光缆段不能为空');
				$('trunknames').focus();
				return false;
			}
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
<body onload="changeStyle()">
	<template:titile value="隐患处理" />
	<html:form action="/hiddangerTreatAction.do?method=treat" styleId="form" onsubmit="return check();" enctype="multipart/form-data">
		<input type="hidden" name="hiddangerId" value="<bean:write name="registBean" property="id" />">
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
			<template:formTr name="隐患地点描述">
				<html:text property="address" styleClass="inputtext required" style="width:140px"/>
			</template:formTr>
			<template:formTr name="隐患点距光缆距离">
				<html:text property="distanceToCable" styleClass="inputtext required validate-number" style="width:140px"/>  米
			</template:formTr>
			<template:formTr name="隐患盯防负责人">
				<html:select property="tempOptions" styleClass="inputtext required" style="width:140px" onchange="setWatchPrincipal(this,'watchPrincipal','watchPrincipalPhone');">
			      	<html:options collection="principal" property="value" labelProperty="label"/>
			    </html:select>
				<html:hidden property="watchPrincipal" styleId="watchPrincipal" styleClass="inputtext required" style="width:140px"/>
			</template:formTr>
			<template:formTr name="隐患盯防负责人电话">
				<html:text property="watchPrincipalPhone" styleId="watchPrincipalPhone" styleClass="inputtext required validate-pattern-/^\d+(,\d+)*$/" style="width:140px"/>
			</template:formTr>
			<template:formTr name="隐患盯防实施人">
				<html:select property="tempOptions" styleClass="inputtext required" style="width:140px" onchange="setWatchPrincipal(this,'watchActualizeMan','watchActualizeManPhone');">
			      	<html:options collection="principal" property="value" labelProperty="label"/>
			    </html:select>
				<html:hidden property="watchActualizeMan" styleId="watchActualizeMan" styleClass="inputtext required" style="width:140px"/>
			</template:formTr>
			<template:formTr name="隐患盯防实施人电话">
				<html:text property="watchActualizeManPhone" styleId="watchActualizeManPhone" styleClass="inputtext required validate-pattern-/^\d+(,\d+)*$/" style="width:140px"/>
			</template:formTr>
			<template:formTr name="隐患盯防解除时间">
				<html:text property="hiddangerRemoveTime" styleId="hiddangerRemoveTime" styleClass="Wdate" style="width:140px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate:'%y-%M-%d'})"/>
			</template:formTr>
			<template:formTr name="隐患消除时间">
				<html:text property="watchReliefTime" styleId="watchReliefTime" styleClass="Wdate" style="width:140px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate:'%y-%M-%d'})"/>
			</template:formTr>
			<template:formTr name="受影响光缆段">
				<apptag:trunk id="trunk" rows="4" cols="45"/>
			</template:formTr>
			<template:formTr name="受影响路由长度">
				<html:text property="impressLength" styleClass="inputtext required validate-number" style="width:140px"/>  米
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
			<template:formTr name="隐患盯防解除原因">
				<html:textarea rows="4" cols="45" property="watchReliefReason" styleClass="inputtextarea required"/>
			</template:formTr>
			<template:formTr name="备注">
				<html:textarea rows="4" cols="45" property="treatremark" styleClass="inputtextarea"/>
			</template:formTr>
			<template:formTr name="附件">
				<apptag:upload state="add" cssClass=""/>
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