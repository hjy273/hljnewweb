<%@include file="/common/header.jsp"%>
<%
String sPID = request.getParameter("sPID");
String tPID = request.getParameter("tPID");
%>
<script language="javascript" type="">
<!--

function toMove(){
	var URL = "${ctx}/GisPointAction.do?method=moveToAnotherSubline&sPID="+sPID.value+"&tPID="+tPID.value+"&newSubline="+pointBean.subLineID.value;
	//alert(URL);
	if(confirm("确定将该部分巡检点分配到选定的巡检段吗？")){
		self.location.replace(URL);
	}

}

//-->
</script><template:titile value="移动部分巡检点至新线段"/>
<br>
<input type="hidden" name="sPID" value="<%=sPID%>">
<input type="hidden" name="tPID" value="<%=tPID%>">
<html:form method="Post" action="/pointAction.do?method=updatePointForGisWatch">
<template:formTable>

    <template:formTr name="移动至：">
      <apptag:setSelectOptions valueName="sublineCollection" tableName="sublineinfo" columnName1="sublinename" columnName2="sublineid" region="true"/>
      <html:select property="subLineID" styleClass="inputtext" style="width:110">
        <html:options collection="sublineCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>

  <template:formSubmit>
    <td>
      <html:button property="action" styleClass="lbutton" onclick="toMove()">确定</html:button>
    </td>
  </template:formSubmit>
</template:formTable>
</html:form>
