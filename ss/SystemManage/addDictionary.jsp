<%@ include file="/common/header.jsp"%>
<html>
<head>
<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
<script language="javascript"	src="${ctx}/js/validation/validation_cn.js" type=""></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" 	type="text/css">
</head>
<body>
<template:titile value="添加数据字典信息"/>
<form Id="addDict" method="Post" action="${ctx}/dictionary.do?method=saveDict" >
    <template:formTable>
		<template:formTr name="字典类型">
			 <apptag:setSelectOptions columnName2="assortment_id" columnName1="assortment_desc" valueName="assortCell" tableName="dictionary_assortment"/>
			 <select name="assortmentId"  class="required" style="width:200px">
			 	<c:forEach var="bean" items="${assortCell}">
			        		<option value="${bean['value']}">${bean['label']}</option>
			    </c:forEach>
			 </select>
        </template:formTr>

		<template:formTr name="父类型">
			 <apptag:setSelectOptions columnName2="id" columnName1="lable" valueName="dictCell" order="assortment_id,sort" tableName="dictionary_formitem"/>
			 <select property="parentId" styleClass="required" style="width:200px">
			 	<option value="">无</option>
			 	<c:forEach var="bean" items="${dictCell}">
			        		<option value="${bean['value']}">${bean['label']}</option>
			    </c:forEach>
			 </select>
        </template:formTr>

        <template:formTr name="编号" >
        	<input type="text" name="code" class="required " style="width:200px" maxlength="3"/>
        </template:formTr>
        
        <template:formTr name="名称" >
        	<input type="text" name="lable" class="required " style="width:200px" maxlength="20"/>
        </template:formTr>

 		<template:formTr name="排序 " >
 		<input type="text" name="sort" class="required " style="width:200px" maxlength="3"/>
        </template:formTr>
       
        <template:formSubmit>
            <td >
            	<input type="submit" class="button" value="保   存"></input>
            </td>
            <td >
            	<input type="reset" class="button" value="重   置"></input>
            </td>
        </template:formSubmit>

   </template:formTable>
   <template:cssTable></template:cssTable>
</form>
<script type="text/javascript">
		function formCallback(result, form) {
			window.status = "valiation callback for form '" + form.id + "': result = " + result;
		}

		var valid = new Validation('addDict', {immediate : true, onFormValidate : formCallback});
</script>
</body>
</html>


