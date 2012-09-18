<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>�༭�ܵ���Ϣ</title>
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
		<script type="text/javascript">
		
			function judge(){
				var workName = document.getElementById('workName').value;
				if(workName==""){
					alert("�������Ʋ���Ϊ��!");
					return false;
				}
				var pipeAddress = document.getElementById('pipeAddress').value;
				if(pipeAddress==""){
					alert("�ܵ��ص㲻��Ϊ��!");
					return false;
				}
				var pipeLine = document.getElementById('pipeLine').value;
				if(pipeLine==""){
					alert("�ܵ�·�ɲ���Ϊ��!");
					return false;
				}
				var pipeLengthChannel = document.getElementById('pipeLengthChannel').value;
				var pipeLengthHole = document.getElementById('pipeLengthHole').value;
				if(pipeLengthChannel=="" || pipeLengthHole==""){
					alert("�ܵ���ģ����Ϊ��!");
					return false;
				}
				var mobileScareChannel = document.getElementById('mobileScareChannel').value;
				var mobileLengthHole = document.getElementById('mobileLengthHole').value;
				if(mobileScareChannel=="" || mobileLengthHole==""){
					alert("�ƶ���ģ����Ϊ��!");
					return false;
				}
				var finishtime = document.getElementById('finishtime').value;
				if(finishtime==""){
					alert("��ά���ڲ���Ϊ��!");
					return false;
				}
			}
		</script>
	</head>
	<body>
		<template:titile value="���ӹܵ���Ϣ"/>
		<html:form action="/pipeAction.do?method=save" styleId="saveres_pipe" onsubmit="return judge()">
		<template:formTable contentwidth="300" namewidth="300">
			<template:formTr name="��������" isOdd="false">
				<input type="text" class="inputtext" style="width:200px" name="workName" id="workName"/>
				<font color="red">*</font>
			</template:formTr>
			<template:formTr name="����" isOdd="true">
				<apptag:quickLoadList cssClass="" name="scetion" listName="terminal_address" type="radio"/>
			</template:formTr>
			<template:formTr name="�ܵ��ص�" isOdd="false">
				<input type="text" class="inputtext" style="width:200px" name="pipeAddress" id="pipeAddress"/>
				<font color="red">*</font>
			</template:formTr>
			<template:formTr name="�ܵ�·��" isOdd="true">
				<input type="text" class="inputtext" style="width:200px"  name="pipeLine" id="pipeLine"/>
				<font color="red">*</font>
			</template:formTr>
			<template:formTr name="�ܵ���ģ" isOdd="false">
				�� <input type="text" class="inputtext validate-number" style="width:80px" name="pipeLengthChannel" id="pipeLengthChannel"/>
				�� <input type="text" class="inputtext validate-number" style="width:80px" name="pipeLengthHole" id="pipeLengthHole"/>����
				<font color="red">*</font>
			</template:formTr>
			<template:formTr name="�ƶ���ģ" isOdd="true">
				�� <input type="text" class="inputtext validate-number" style="width:80px" name="mobileScareChannel" id="mobileScareChannel"/>
				�� <input type="text" class="inputtext validate-number" style="width:80px" name="mobileScareHole" id="mobileScareHole"/>����
				<font color="red">*</font>
			</template:formTr>
			<template:formTr name="�ܵ�����" isOdd="false">
				<apptag:quickLoadList cssClass="inputtext" style="width:200px" name="pipeType" keyValue="${pipe.pipeType}" listName="pipe_type" type="select"/>
			</template:formTr>
			<template:formTr name="��Ȩ����" isOdd="true">
				<apptag:quickLoadList cssClass="inputtext" style="width:200px" name="routeRes" keyValue="${rpBean.routeRes}" listName="property_right" type="select"/>
			</template:formTr>
			<template:formTr name="��ά����" isOdd="false">
				<input type="text" class="Wdate" style="width:200px" name="finishTime" id="finishTime" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})"/>
				<font color="red">*</font>
			</template:formTr>
			<c:if test="${LOGIN_USER.deptype=='1'}">
			<template:formTr name="ά����λ">
				<apptag:setSelectOptions columnName2="contractorid" columnName1="contractorname" valueName="contractor" tableName="contractorinfo"/>
				<html:select property="maintenanceId" styleClass="inputtext" style="width:200px">
					<html:option value="">��ѡ��</html:option>
			      	<html:options collection="contractor" property="value" labelProperty="label"/>
			    </html:select>
			</template:formTr>
			</c:if>
			<template:formTr name="����ͼֽ����" isOdd="true">
				<input type="text" class="inputtext" style="width:200px" name="picture"/>
			</template:formTr>
			<template:formTr name="��ע" isOdd="false">
				<input type="text" class="inputtext" style="width:200px" name="remark"/>
			</template:formTr>
		</template:formTable>
		<template:formSubmit >
			<input type="submit" class="button" name="submit" value="�ύ"/>
		</template:formSubmit>
		</html:form>
		<template:cssTable/>
	</body>
	<script type="text/javascript">
		function formCallback(result, form) {
			window.status = "valiation callback for form '" + form.id + "': result = " + result;
		}

		var valid = new Validation('saveres_pipe', {immediate : true, onFormValidate : formCallback});
	</script>
</html>