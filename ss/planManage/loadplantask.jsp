<%@include file="/common/header.jsp"%>
<script language="javascript" type="text/javascript">
<!--
	function next(){
		if(!validate()){
			alert("��ѡ��һ��ģ��");
			return false;
		}
		taskBean.submit();
		
	}
	function validate(){
		var isChecked = false;
		if(document.getElementById("stencil")!=null){
		if(document.all.stencil.checked){
			isChecked = true;
		}
		for(i=0;i<document.all.stencil.length;i++){
			if(document.all.stencil(i).checked){
				isChecked = true;
			}
		}
		}
		return isChecked;
	}
//-->
</script>
<body onload="">
<br>
<template:titile value="����Ѳ��ƻ�"/>
<html:form action="/TaskAction?method=toLoadTaskStencil" onsubmit="return checkInput()">
  <input  id="deptname" type="hidden"  name="regionid" value="<bean:write name="LOGIN_USER_DEPT_NAME"/>"/>

  <template:formTable>  
    <template:formTr name="�ƻ�ģ��" >
    	<table name="tasklisttable" border="0" cellspacing="0" cellpadding="5">
    		<logic:present name="stencilList">
			  <logic:iterate id="sid" name="stencilList" indexId="index">
			  	<input type="radio" name="stencil" id="stencil" value="<bean:write name="sid" property="stencilid"/>"/>
			  	<input name="stencilname" type="text" value="<bean:write name="sid" property="stencilname"/>"  class="inputtext" style="border:0;background-color:transparent;width:370px;"  readonly ><br/>
			  </logic:iterate>
			</logic:present>
		</table>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:button property="action" styleClass="button" onclick="return next()">����</html:button>
      </td>
      <td>
        <html:button property="action" styleClass="button" onclick="history.back(-1)">����</html:button>
      </td>
     
    </template:formSubmit>
  </template:formTable>
</html:form>
</body>
