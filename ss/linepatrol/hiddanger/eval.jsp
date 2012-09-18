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
			var objs = document.getElementsByName('hiddangerLevel');
			for(var i = 0 ; i < objs.length ; i++){
				if(objs[i].checked && objs[i].value == '0' && $('remark').value == ''){
					alert('当忽略隐患时候，备注是必填项');
					return false;
				}
			}
			processBar(form);
			return true;
		}
		function showMap(){
			var url = '/WEBGIS/gisextend/igis.jsp?userid=${LOGIN_USER.userID}&actiontype=205&x=${registBean.x}&y=${registBean.y}&label=${registBean.name}';
			window.open(url,'map','width=800,height=600,scrollbars=yes,resizable=yes,toolbar=no,menubar=no');
		}
	</script>
</head>
<body onload="changeStyle()">
	<template:titile value="隐患评级" />
	<html:form action="/hiddangerAction.do?method=eval" styleId="form" onsubmit="return check()">
		<html:hidden property="id"/>
		<template:formTable namewidth="150" contentwidth="350">
			<template:formTr name="隐患编号">
				<bean:write name="registBean" property="hiddangerNumber" />
			</template:formTr>
			<template:formTr name="登记单位">
				<bean:write name="registBean" property="createrDept" />
			</template:formTr>
			<template:formTr name="登记人">
				<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="${registBean.creater}" />
			</template:formTr>
			<template:formTr name="登记名称">
				<bean:write name="registBean" property="name" />
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
			<template:formTr name="隐患分类">
				<apptag:assorciateAttr table="troubletype" label="typename" key="id" keyValue="${registBean.type}" />
			</template:formTr>
			<template:formTr name="隐患名称">
				<apptag:assorciateAttr table="troublecode" label="troublename" key="id" keyValue="${registBean.code}" />
			</template:formTr>
			<template:formTr name="发现隐患来源">
				<bean:write name="registBean" property="reporter" />
			</template:formTr>
			<template:formTr name="隐患处理单位">
				<apptag:assorciateAttr table="contractorinfo" label="contractorname" key="contractorid" keyValue="${registBean.treatDepartment}" />
			</template:formTr>
			<template:formTr name="级别">
				<html:radio property="hiddangerLevel" value="1" />1级
				<html:radio property="hiddangerLevel" value="2" />2级
				<html:radio property="hiddangerLevel" value="3" />3级
				<html:radio property="hiddangerLevel" value="4" />4级
				<html:radio property="hiddangerLevel" value="0" />忽略
			</template:formTr>
			<template:formTr name="备注">
				<html:textarea rows="4" cols="45" property="remark" />
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
	</html:form>
	<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
	
</body>