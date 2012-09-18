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
				alert('�����������ʱ�䲻��Ϊ��');
				$('hiddangerRemoveTime').focus();
				return false;
			}
			if($('watchReliefTime').value == ''){
				alert('��������ʱ�䲻��Ϊ��');
				$('watchReliefTime').focus();
				return false;
			}
			if($('trunknames').value == ''){
				alert('��Ӱ����¶β���Ϊ��');
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
	<template:titile value="��������" />
	<html:form action="/hiddangerTreatAction.do?method=treat" styleId="form" onsubmit="return check();" enctype="multipart/form-data">
		<input type="hidden" name="hiddangerId" value="<bean:write name="registBean" property="id" />">
		<template:formTable namewidth="200" contentwidth="400">
			<template:formTr name="�������">
				<bean:write name="registBean" property="hiddangerNumber" />
			</template:formTr>
			<template:formTr name="�Ǽ�����">
				<bean:write name="registBean" property="name" />
			</template:formTr>
			<template:formTr name="�Ǽǵ�λ">
				<bean:write name="registBean" property="createrDept" />
			</template:formTr>
			<template:formTr name="�Ǽ���">
				<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="${registBean.creater}" />
			</template:formTr>
			<template:formTr name="����λ��">
				<bean:write name="registBean" property="x" />,<bean:write name="registBean" property="y" /><input type="button" onclick="showMap();" value="�鿴λ��" />
			</template:formTr>
			<template:formTr name="��������ʱ��">
				<bean:write name="registBean" property="findTime" />
			</template:formTr>
			<template:formTr name="�������ַ�ʽ">
				<bean:write name="registBean" property="findType" />
			</template:formTr>
			<template:formTr name="�����ϱ�ʱ��">
				<bean:write name="registBean" property="createTime" />
			</template:formTr>
			<template:formTr name="����������Դ">
				<bean:write name="registBean" property="reporter" />
			</template:formTr>
			<template:formTr name="��������λ">
				<apptag:assorciateAttr table="contractorinfo" label="contractorname" key="contractorid" keyValue="${registBean.treatDepartment}" />
			</template:formTr>
			<template:formTr name="��������">
				<apptag:assorciateAttr table="troubletype" label="typename" key="id" keyValue="${registBean.type}" />
			</template:formTr>
			<template:formTr name="��������">
				<apptag:assorciateAttr table="troublecode" label="troublename" key="id" keyValue="${registBean.code}" />
			</template:formTr>
			<template:formTr name="�����ȼ�">
				<bean:write name="registBean" property="hiddangerLevel" />��
			</template:formTr>
			<template:formTr name="����������ע">
				<bean:write name="registBean" property="remark" />
			</template:formTr>
			<template:formTr name="�����ص�����">
				<html:text property="address" styleClass="inputtext required" style="width:140px"/>
			</template:formTr>
			<template:formTr name="���������¾���">
				<html:text property="distanceToCable" styleClass="inputtext required validate-number" style="width:140px"/>  ��
			</template:formTr>
			<template:formTr name="��������������">
				<html:select property="tempOptions" styleClass="inputtext required" style="width:140px" onchange="setWatchPrincipal(this,'watchPrincipal','watchPrincipalPhone');">
			      	<html:options collection="principal" property="value" labelProperty="label"/>
			    </html:select>
				<html:hidden property="watchPrincipal" styleId="watchPrincipal" styleClass="inputtext required" style="width:140px"/>
			</template:formTr>
			<template:formTr name="�������������˵绰">
				<html:text property="watchPrincipalPhone" styleId="watchPrincipalPhone" styleClass="inputtext required validate-pattern-/^\d+(,\d+)*$/" style="width:140px"/>
			</template:formTr>
			<template:formTr name="��������ʵʩ��">
				<html:select property="tempOptions" styleClass="inputtext required" style="width:140px" onchange="setWatchPrincipal(this,'watchActualizeMan','watchActualizeManPhone');">
			      	<html:options collection="principal" property="value" labelProperty="label"/>
			    </html:select>
				<html:hidden property="watchActualizeMan" styleId="watchActualizeMan" styleClass="inputtext required" style="width:140px"/>
			</template:formTr>
			<template:formTr name="��������ʵʩ�˵绰">
				<html:text property="watchActualizeManPhone" styleId="watchActualizeManPhone" styleClass="inputtext required validate-pattern-/^\d+(,\d+)*$/" style="width:140px"/>
			</template:formTr>
			<template:formTr name="�����������ʱ��">
				<html:text property="hiddangerRemoveTime" styleId="hiddangerRemoveTime" styleClass="Wdate" style="width:140px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate:'%y-%M-%d'})"/>
			</template:formTr>
			<template:formTr name="��������ʱ��">
				<html:text property="watchReliefTime" styleId="watchReliefTime" styleClass="Wdate" style="width:140px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate:'%y-%M-%d'})"/>
			</template:formTr>
			<template:formTr name="��Ӱ����¶�">
				<apptag:trunk id="trunk" rows="4" cols="45"/>
			</template:formTr>
			<template:formTr name="��Ӱ��·�ɳ���">
				<html:text property="impressLength" styleClass="inputtext required validate-number" style="width:140px"/>  ��
			</template:formTr>
			<template:formTr name="������Ӱ����¶�">
				<html:textarea rows="4" cols="45" property="otherImpressCable" styleClass="inputtextarea required"/>
			</template:formTr>
			<template:formTr name="��������ԭ��">
				<html:textarea rows="4" cols="45" property="watchReason" styleClass="inputtextarea required"/>
			</template:formTr>
			<template:formTr name="����������ʩ">
				<html:textarea rows="4" cols="45" property="watchMeasure" styleClass="inputtextarea required"/>
			</template:formTr>
			<template:formTr name="�����������ԭ��">
				<html:textarea rows="4" cols="45" property="watchReliefReason" styleClass="inputtextarea required"/>
			</template:formTr>
			<template:formTr name="��ע">
				<html:textarea rows="4" cols="45" property="treatremark" styleClass="inputtextarea"/>
			</template:formTr>
			<template:formTr name="����">
				<apptag:upload state="add" cssClass=""/>
			</template:formTr>
			<template:formSubmit>
				<td>
					<html:submit property="action" styleClass="button">�ύ</html:submit>
				</td>
				<td>
					<html:button property="action" styleClass="button" onclick="history.back()">����</html:button>
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