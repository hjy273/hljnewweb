<%@include file="/common/header.jsp"%>
<html>
<head>
<script language="javascript">

	function addGoBack(){
		 var url = "${ctx}/datum/querycriterionfile.jsp";
      	self.location.replace(url);
	}
</script>
</head>
<body>
<template:titile value="修改技术规范" />
<html:form action="/DatumCriterionAction?method=updateDatumCriterion"  onsubmit="return validate(this);" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<html:hidden property="id"  name="datumcriterion"/>
<html:hidden property="adjunct"  name="datumcriterion"/>
<template:formTr name="文档名称">
<html:text property="documentname" name="datumcriterion" styleClass="inputtext" style="width:250;" maxlength="25" />
</template:formTr>
<template:formTr name="文档类型">
<apptag:setSelectOptions columnName1="typename" columnName2="code" tableName="documenttype" valueName="documenttypecoll"  condition="category='2' "/>
<html:select property="documenttype" name="datumcriterion" styleClass="inputtext" style="width:160">
  <html:options collection="documenttypecoll" property="value" labelProperty="label"/>
</html:select>
</template:formTr>
<template:formTr name="文档描述">
<html:textarea property="description" name="datumcriterion" cols="36" onkeyup="this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="描述信息最长250个汉字"/>
</template:formTr>
<template:formTr name="有效期">
<html:text property="validatetime" name="datumcriterion" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:250;" maxlength="26" />
</template:formTr>
<template:formTr name="附件">
	<logic:empty name="datumcriterion"  property="adjunct">
	<apptag:Attachment state="edit" fileIdList=""></apptag:Attachment>
     </logic:empty>
	<logic:notEmpty name="datumcriterion" property="adjunct">
	 	<bean:define id="temp" name="datumcriterion" property="adjunct" type="java.lang.String"/>
		<apptag:Attachment state="edit" fileIdList="<%=temp %>"></apptag:Attachment>
	</logic:notEmpty>
</template:formTr>

<template:formSubmit>
	<td>
		<html:button property="action" styleClass="button" onclick="addRow()">添加附件</html:button>
	</td>
	<td>
		<html:submit styleClass="button" >提交</html:submit>
	</td>
	<td>
		<html:reset styleClass="button">取消	</html:reset>
	</td>
	<td>
		<input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
	</td>
</template:formSubmit>
</template:formTable>
</html:form>
</body>
</html>