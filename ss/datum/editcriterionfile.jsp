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
<template:titile value="�޸ļ����淶" />
<html:form action="/DatumCriterionAction?method=updateDatumCriterion"  onsubmit="return validate(this);" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<html:hidden property="id"  name="datumcriterion"/>
<html:hidden property="adjunct"  name="datumcriterion"/>
<template:formTr name="�ĵ�����">
<html:text property="documentname" name="datumcriterion" styleClass="inputtext" style="width:250;" maxlength="25" />
</template:formTr>
<template:formTr name="�ĵ�����">
<apptag:setSelectOptions columnName1="typename" columnName2="code" tableName="documenttype" valueName="documenttypecoll"  condition="category='2' "/>
<html:select property="documenttype" name="datumcriterion" styleClass="inputtext" style="width:160">
  <html:options collection="documenttypecoll" property="value" labelProperty="label"/>
</html:select>
</template:formTr>
<template:formTr name="�ĵ�����">
<html:textarea property="description" name="datumcriterion" cols="36" onkeyup="this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="������Ϣ�250������"/>
</template:formTr>
<template:formTr name="��Ч��">
<html:text property="validatetime" name="datumcriterion" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:250;" maxlength="26" />
</template:formTr>
<template:formTr name="����">
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
		<html:button property="action" styleClass="button" onclick="addRow()">��Ӹ���</html:button>
	</td>
	<td>
		<html:submit styleClass="button" >�ύ</html:submit>
	</td>
	<td>
		<html:reset styleClass="button">ȡ��	</html:reset>
	</td>
	<td>
		<input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
	</td>
</template:formSubmit>
</template:formTable>
</html:form>
</body>
</html>