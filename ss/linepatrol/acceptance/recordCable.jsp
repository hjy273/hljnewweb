<%@include file="/common/header.jsp"%>
<html>
<head>
	<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
	<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
	
	<script type="text/javascript">
		function check(){
			if($('planAcceptanceTime').value == ''){
				alert('计划验收日期不能为空');
				return false;
			}
			var lays = document.getElementsByName('layingmethod');
			var num = 0;
			for(var i = 0 ; i < lays.length ; i++){
				if(lays[i].checked){
					num++;
				}
			}
			if(num == 0){
				alert('铺设方式至少选择一项');
				return false;
			}
			return true;
		}
	</script>
</head>
<body onload="changeStyle()">
	<template:titile value="光缆录入" />
	<template:formTable>
		<html:form action="/acceptanceCableAction.do?method=recordCable" styleId="form" onsubmit="return check();">
			<template:formTr name="光缆编号">
				<html:text property="cableNo" styleClass="inputtext required" style="width:200px"/>
			</template:formTr>
			<template:formTr name="资产编号">
				<html:text property="sid" styleClass="inputtext" style="width:200px"/>
			</template:formTr>
			<template:formTr name="工程名称">
				<html:text property="issueNumber" styleClass="inputtext required" style="width:200px"/>
			</template:formTr>
			<template:formTr name="计划验收日期">
				<html:text property="planAcceptanceTime" styleId="planAcceptanceTime" styleClass="Wdate" style="width:200px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'%y-%M-%d'})"/>
			</template:formTr>
			<template:formTr name="A端">
				<html:text property="a" styleClass="inputtext required" style="width:200px"/>
			</template:formTr>
			<template:formTr name="Z端">
				<html:text property="z" styleClass="inputtext required" style="width:200px"/>
			</template:formTr>
			<template:formTr name="光缆中继段">
				<html:text property="trunk" styleClass="inputtext required" style="width:200px"/>
			</template:formTr>
			<template:formTr name="铺设方式">
				<apptag:quickLoadList cssClass="input" name="layingmethod" listName="layingmethod" type="checkbox"/>
			</template:formTr>
			<template:formTr name="纤芯数">
				<html:text property="fibercoreNo" styleClass="inputtext required validate-number" style="width:200px"/>
			</template:formTr>
			<template:formTr name="光缆级别">
				<apptag:quickLoadList cssClass="input" style="width:200px" name="cableLevel" listName="cabletype" type="select"/>
			</template:formTr>
			<template:formTr name="光缆长度（千米）">
				<html:text property="cableLength" styleClass="inputtext required validate-number" maxlength="12" style="width:200px"/>
			</template:formTr>
			<template:formTr name="施工单位">
				<html:text property="builder" styleClass="inputtext required" style="width:200px"/>
			</template:formTr>
			<template:formTr name="施工单位联系方式">
				<html:text property="builderPhone" styleClass="inputtext required" style="width:200px"/>
			</template:formTr>
			<template:formTr name="工程中心项目经理">
				<html:text property="prcpm" styleClass="inputtext required" style="width:200px"/>
			</template:formTr>
			<template:formTr name="备注">
				<html:textarea rows="4" cols="35" property="remark" styleClass="required"/>
			</template:formTr>
			<template:formSubmit>
				<td>
					<input type="submit" class="button" value="添加" />
				</td>
			</template:formSubmit>
		</html:form>
	</template:formTable>
	<script type="text/javascript">
		function formCallback(result, form) {
			window.status = "valiation callback for form '" + form.id + "': result = " + result;
		}

		var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
</body>
</html>