<%@ include file="/common/header.jsp"%>
<html>
<head>
<script language="javascript" src="${ctx}/js/validation/prototype.js"	type=""></script>
<script language="javascript" src="${ctx}/js/validation/effects.js"	type=""></script>
<script language="javascript"
	src="${ctx}/js/validation/validation_cn.js" type=""></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript">
function addGoBack(){
    location.href = "${ctx}/dictionary.do?method=queryDictionary";
}
</script>
</head>
<body>
<template:titile value="�޸������ֵ���Ϣ" />
<form Id="eidtDict" method="Post"
	action="${ctx}/dictionary.do?method=saveDict"><template:formTable>
	<template:formTr name="�ֵ�����">
		<input type="hidden" name="id" value="${dict.id}" />
		<apptag:setSelectOptions columnName2="assortment_id"
			columnName1="assortment_desc" valueName="assortCell"
			tableName="dictionary_assortment" />
		<html:select property="assortmentId" name="dict" styleClass="required"
			style="width:200px">
			<html:options collection="assortCell" property="value"
				labelProperty="label" />
		</html:select>
	</template:formTr>


	<template:formTr name="������">
		<apptag:setSelectOptions columnName2="id" columnName1="lable" valueName="dictConnection" order="assortment_id,sort"
			tableName="dictionary_formitem" />
		<html:select property="parentId" name="dict" style="width:200px" styleClass="inputtext">
			<html:option value="">��</html:option>
			<html:options collection="dictConnection" property="value"
				labelProperty="label" />
		</html:select>
	</template:formTr>
	<template:formTr name="���">
		<html:text property="code" name="dict" styleClass="required "
			style="width:200px" maxlength="2" />
		<span style="color: red">* �벻Ҫ�����޸�����޸Ļᵼ����ǰ�����ݲ��ܹ�ʹ��</span>
	</template:formTr>

	<template:formTr name="����">
		<html:text property="lable" name="dict" styleClass="required "
			style="width:200px" maxlength="25" />
	</template:formTr>

	<template:formTr name="����">
		<html:text property="sort" name="dict" styleClass="required "
			style="width:200px" maxlength="2" />
	</template:formTr>

	<template:formSubmit>
		<td><html:submit styleClass="button" value="��   ��"></html:submit>
		</td>
		<td><html:reset styleClass="button" value="��   ��"></html:reset>
		</td>
		<td>
			<input type="hidden" name="operator" value="edit" />
			<html:button property="action" styleClass="button" onclick="addGoBack()">����</html:button>
		</td>
	</template:formSubmit>

</template:formTable> <template:cssTable></template:cssTable></form>
<script type="text/javascript">
	function formCallback(result, form) {
		window.status = "valiation callback for form '" + form.id
				+ "': result = " + result;
	}

	var valid = new Validation('eidtDict', {
		immediate : true,
		onFormValidate : formCallback
	});
</script>
</body>
</html>
