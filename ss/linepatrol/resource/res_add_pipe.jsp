<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>编辑管道信息</title>
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
		<script type="text/javascript">
		
			function judge(){
				var workName = document.getElementById('workName').value;
				if(workName==""){
					alert("工程名称不能为空!");
					return false;
				}
				var pipeAddress = document.getElementById('pipeAddress').value;
				if(pipeAddress==""){
					alert("管道地点不能为空!");
					return false;
				}
				var pipeLine = document.getElementById('pipeLine').value;
				if(pipeLine==""){
					alert("管道路由不能为空!");
					return false;
				}
				var pipeLengthChannel = document.getElementById('pipeLengthChannel').value;
				var pipeLengthHole = document.getElementById('pipeLengthHole').value;
				if(pipeLengthChannel=="" || pipeLengthHole==""){
					alert("管道规模不能为空!");
					return false;
				}
				var mobileScareChannel = document.getElementById('mobileScareChannel').value;
				var mobileLengthHole = document.getElementById('mobileLengthHole').value;
				if(mobileScareChannel=="" || mobileLengthHole==""){
					alert("移动规模不能为空!");
					return false;
				}
				var finishtime = document.getElementById('finishtime').value;
				if(finishtime==""){
					alert("交维日期不能为空!");
					return false;
				}
			}
		</script>
	</head>
	<body>
		<template:titile value="增加管道信息"/>
		<html:form action="/pipeAction.do?method=save" styleId="saveres_pipe" onsubmit="return judge()">
		<template:formTable contentwidth="300" namewidth="300">
			<template:formTr name="工程名称" isOdd="false">
				<input type="text" class="inputtext" style="width:200px" name="workName" id="workName"/>
				<font color="red">*</font>
			</template:formTr>
			<template:formTr name="区域" isOdd="true">
				<apptag:quickLoadList cssClass="" name="scetion" listName="terminal_address" type="radio"/>
			</template:formTr>
			<template:formTr name="管道地点" isOdd="false">
				<input type="text" class="inputtext" style="width:200px" name="pipeAddress" id="pipeAddress"/>
				<font color="red">*</font>
			</template:formTr>
			<template:formTr name="管道路由" isOdd="true">
				<input type="text" class="inputtext" style="width:200px"  name="pipeLine" id="pipeLine"/>
				<font color="red">*</font>
			</template:formTr>
			<template:formTr name="管道规模" isOdd="false">
				沟 <input type="text" class="inputtext validate-number" style="width:80px" name="pipeLengthChannel" id="pipeLengthChannel"/>
				孔 <input type="text" class="inputtext validate-number" style="width:80px" name="pipeLengthHole" id="pipeLengthHole"/>公里
				<font color="red">*</font>
			</template:formTr>
			<template:formTr name="移动规模" isOdd="true">
				沟 <input type="text" class="inputtext validate-number" style="width:80px" name="mobileScareChannel" id="mobileScareChannel"/>
				孔 <input type="text" class="inputtext validate-number" style="width:80px" name="mobileScareHole" id="mobileScareHole"/>公里
				<font color="red">*</font>
			</template:formTr>
			<template:formTr name="管道属性" isOdd="false">
				<apptag:quickLoadList cssClass="inputtext" style="width:200px" name="pipeType" keyValue="${pipe.pipeType}" listName="pipe_type" type="select"/>
			</template:formTr>
			<template:formTr name="产权属性" isOdd="true">
				<apptag:quickLoadList cssClass="inputtext" style="width:200px" name="routeRes" keyValue="${rpBean.routeRes}" listName="property_right" type="select"/>
			</template:formTr>
			<template:formTr name="交维日期" isOdd="false">
				<input type="text" class="Wdate" style="width:200px" name="finishTime" id="finishTime" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})"/>
				<font color="red">*</font>
			</template:formTr>
			<c:if test="${LOGIN_USER.deptype=='1'}">
			<template:formTr name="维护单位">
				<apptag:setSelectOptions columnName2="contractorid" columnName1="contractorname" valueName="contractor" tableName="contractorinfo"/>
				<html:select property="maintenanceId" styleClass="inputtext" style="width:200px">
					<html:option value="">请选择</html:option>
			      	<html:options collection="contractor" property="value" labelProperty="label"/>
			    </html:select>
			</template:formTr>
			</c:if>
			<template:formTr name="竣工图纸数量" isOdd="true">
				<input type="text" class="inputtext" style="width:200px" name="picture"/>
			</template:formTr>
			<template:formTr name="备注" isOdd="false">
				<input type="text" class="inputtext" style="width:200px" name="remark"/>
			</template:formTr>
		</template:formTable>
		<template:formSubmit >
			<input type="submit" class="button" name="submit" value="提交"/>
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