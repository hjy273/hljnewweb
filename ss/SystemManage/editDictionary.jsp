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
<template:titile value="修改数据字典信息" />
<form Id="eidtDict" method="Post"
	action="${ctx}/dictionary.do?method=saveDict"><template:formTable>
	<template:formTr name="字典类型">
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


	<template:formTr name="父类型">
		<apptag:setSelectOptions columnName2="id" columnName1="lable" valueName="dictConnection" order="assortment_id,sort"
			tableName="dictionary_formitem" />
		<html:select property="parentId" name="dict" style="width:200px" styleClass="inputtext">
			<html:option value="">无</html:option>
			<html:options collection="dictConnection" property="value"
				labelProperty="label" />
		</html:select>
	</template:formTr>
	<template:formTr name="编号">
		<html:text property="code" name="dict" styleClass="required "
			style="width:200px" maxlength="2" />
		<span style="color: red">* 请不要轻易修改如果修改会导致以前的数据不能够使用</span>
	</template:formTr>

	<template:formTr name="名称">
		<html:text property="lable" name="dict" styleClass="required "
			style="width:200px" maxlength="25" />
	</template:formTr>

	<template:formTr name="排序">
		<html:text property="sort" name="dict" styleClass="required "
			style="width:200px" maxlength="2" />
	</template:formTr>

	<template:formSubmit>
		<td><html:submit styleClass="button" value="保   存"></html:submit>
		</td>
		<td><html:reset styleClass="button" value="重   置"></html:reset>
		</td>
		<td>
			<input type="hidden" name="operator" value="edit" />
			<html:button property="action" styleClass="button" onclick="addGoBack()">返回</html:button>
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
