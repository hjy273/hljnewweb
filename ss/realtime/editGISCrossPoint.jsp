<%@page import="com.cabletech.lineinfo.beans.*"%>
<jsp:useBean id="GISCrossPointBean" class="com.cabletech.lineinfo.beans.GISCrossPointBean" scope="request"/>
<%@include file="/common/header.jsp"%>
<script language="javascript" type="">
<!--
function isValidForm() {
    //����У��
	if(document.GISCrossPointBean.crosspointname.value==""){
		alert("���Ʋ���Ϊ��!! ");
		return false;
	}
	return true;
}

function deleteCrossPoint() {

	if(confirm("ȷ��ɾ���ñ�ʾ�㣿")){
		document.location.replace("${ctx}/GISCrossPointAction.do?method=deleteGISCrossPoint&id="+ GISCrossPointBean.id.value);
	}


}

//-->
</script>
<%
String regionID = GISCrossPointBean.getRegionid();
%>
<template:titile value="�޸ı�ʶ����Ϣ"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/GISCrossPointAction.do?method=updateGISCrossPoint">
  <template:formTable>

    <template:formTr name="����">

	  <html:hidden property="id"/>
	  <html:hidden property="gpscoordinate"/>
	  <html:hidden property="status"/>

      <html:text property="crosspointname" styleClass="inputtext" style="width:110"/>
    </template:formTr>

    <template:formTr name="����" isOdd="false">
	  <html:select property="type" styleClass="inputtext" style="width:110">
        <!--<html:option value="1">�м̵�</html:option>
		<html:option value="2">��վ</html:option>
		<html:option value="6">����</html:option>-->
        <html:option value="3">�����ʾ��</html:option>
      </html:select>
	</template:formTr>

    <template:formTr name="����">
      <apptag:setSelectOptions valueName="regionCellection" tableName="region" columnName1="regionname" currentRegion="true" columnName2="regionid" regionID="<%=regionID%>"/>
      <html:select property="regionid" styleClass="inputtext" style="width:110">
        <html:options collection="regionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>

    <template:formTr name="��ע"  isOdd="false">
      <html:text property="remark" styleClass="inputtext" style="width:110"/>
    </template:formTr>

    <template:formSubmit>
      <td>
        <html:submit styleClass="button">����</html:submit>
      </td>
      <td>
        <html:button styleClass="button" property="action" onclick="deleteCrossPoint()">ɾ��</html:button>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>

