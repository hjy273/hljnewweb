<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<jsp:useBean id="sublineBean" class="com.cabletech.baseinfo.beans.SublineBean" scope="request"/>
<script language="javascript">
<!--
function isValidForm() {

	if(document.sublineBean.subLineName.value==""){
		alert("���Ʋ���Ϊ��!! ");
		return false;
	}

	if(document.sublineBean.lineID.value==""){
		alert("������·����Ϊ��!! ");
		return false;
	}
    if(document.sublineBean.patrolid.value==""){
		alert("����ά���鲻��Ϊ��!! ");
		return false;
	}

	return true;
}

function toEditCable(fID) {
	//fID 1,���� 2,�޸�;
	var pageName = "${ctx}/baseinfo/getSublineRelativeCable.jsp?fID="+fID;

	var pointsPop = window.open(pageName,'pointsPop','width=400,height=300,scrollbars=yes');
	//,resizable=yes,,status=yes
	pointsPop.focus();
}
function changeRegion(){
    //alert(sublineBean.rId.value)
    var rId = sublineBean.rId.value;
    var URL = "${ctx}/sublineAction.do?method=loadSublineForm&regionid="+rId;
    location.replace(URL);
}

//-->
</script>
<%

   //List lineCollection = (List)request.getAttribute("lineCollection");
   //List deptCollection = (List)request.getAttribute("deptCollection");
   //System.out.println(lineCollection.size()+" "+lineCollection);

%>
<body>

<template:titile value="����Ѳ���߶���Ϣ"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/sublineAction.do?method=addSubline">
  <template:formTable contentwidth="300" namewidth="200">
    <template:formTr name="Ѳ���߶�����">
      <html:text property="subLineName" styleClass="inputtext" style="width:200" maxlength="50"/>
      <html:hidden property="checkPoints" value="0"/>
    </template:formTr>
    <template:formTr name="��������" >
      <apptag:setSelectOptions valueName="regionCollection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" order="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
      <html:select property="regionID" styleClass="inputtext_c" styleId="rId" style="width:200" onchange="changeRegion()">
        <html:options collection="regionCollection" property="value" labelProperty="label"/>
      </html:select>
     </template:formTr>
    <template:formTr name="������·" >

      <html:select property="lineID" styleClass="inputtext" style="width:200">
        <html:options collection="lineCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="��������">

      <html:select property="ruleDeptID" styleClass="inputtext" style="width:200">
        <html:options collection="deptCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="��·����" >
      <html:select property="lineType" styleClass="inputtext" style="width:200">
        <html:option value="00">ֱ��</html:option>
        <html:option value="01">�ܿ�</html:option>
        <html:option value="02">�ܵ�</html:option>
		<html:option value="04">��ǽ</html:option>
		<html:option value="03">���</html:option>
      </html:select>
    </template:formTr>

    <logic:equal value="group" name="PMType">
    	<template:formTr name="Ѳ��ά����">

	      <html:select property="patrolid" styleClass="inputtext" style="width:200">

	        <html:options collection="patrolCollection" property="value" labelProperty="label"/>
	      </html:select>
	    </template:formTr>
    </logic:equal>
    <logic:notEqual value="group" name="PMType">
    	<template:formTr name="Ѳ��ά����">

	      <html:select property="patrolid" styleClass="inputtext" style="width:200">

	        <html:options collection="patrolCollection" property="value" labelProperty="label"/>
	      </html:select>
	    </template:formTr>
    </logic:notEqual>

    <logic:equal value="show" name="ShowFIB">
      <template:formTr name="��Ӧ����"  >
        <span id = "listSpan">��ѡ���Ӧ����<br></span>
          <br>
          <br>
          <span id="toEditSubListSpan"><a href="javascript:toEditCable(1)">��ӱ༭��Ӧ����</a></span>
      </template:formTr>
    </logic:equal>
   <logic:notEqual value="noShow" name="ShowFIB">
   </logic:notEqual>
	<template:formTr name="˵��">
      <html:text property="remark" styleClass="inputtext" style="width:200" maxlength="45"/>
    </template:formTr>

    <template:formSubmit>
      <td>
        <html:submit styleClass="button">����</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">ȡ��</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
</body>
