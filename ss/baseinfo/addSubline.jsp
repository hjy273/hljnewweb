<%@include file="/common/header.jsp"%>
<jsp:useBean id="sublineBean" class="com.cabletech.baseinfo.beans.SublineBean" scope="request"/>
<script language="javascript">
<!--
function isValidForm() {

	if(document.sublineBean.subLineName.value==""){
		alert("Ѳ���߶����Ʋ���Ϊ��!! ");
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
	var c=document.sublineBean.cablename;
	/*if(typeof(c)=="undefined"){
		alert("��Ӧ���²���Ϊ��! ");
		return false;
	}*/
	return true;
}

function toEditCable(fID) {
	//fID 1,���� 2,�޸�;
	var form1=document.forms[0];
	var pageName = "${ctx}/baseinfo/getSublineRelativeCable.jsp?fID="+fID;
	pageName+="&&patrol_id="+form1.patrolid.value;
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
<br>
<template:titile value="����Ѳ���߶���Ϣ"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/sublineAction.do?method=addSubline">
  <template:formTable>
    <template:formTr name="Ѳ���߶�����">
      <html:text property="subLineName" styleClass="inputtext" style="width:200px" maxlength="50"/><font color="red"> *</font>
      <html:hidden property="checkPoints" value="0"/>
    </template:formTr>
    <template:formTr name="��������" isOdd="false">
      <apptag:setSelectOptions valueName="regionCollection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" order="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
      <html:select property="regionID" styleClass="inputtext" styleId="rId" style="width:200px" onchange="changeRegion()">
        <html:options collection="regionCollection" property="value" labelProperty="label"/>
      </html:select><font color="red"> *</font>
     </template:formTr>
    <template:formTr name="������·" isOdd="false">

      <html:select property="lineID" styleClass="inputtext" style="width:200px">
        <html:options collection="lineCollection" property="value" labelProperty="label"/>
      </html:select><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="��������">

      <html:select property="ruleDeptID" styleClass="inputtext" style="width:200px">
        <html:options collection="deptCollection" property="value" labelProperty="label"/>
      </html:select><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="��·����" isOdd="false">
      <apptag:quickLoadList cssClass="inputtext" name="lineType" listName="layingmethod"  style="width:200px" type="select"/>
      <font color="red"> *</font>
    </template:formTr>

    <logic:equal value="group" name="PMType">
    	<template:formTr name="Ѳ��ά����">

	      <html:select property="patrolid" styleClass="inputtext" style="width:200px">

	        <html:options collection="patrolCollection" property="value" labelProperty="label"/>
	      </html:select><font color="red"> *</font>
	    </template:formTr>
    </logic:equal>
    <logic:notEqual value="group" name="PMType">
    	<template:formTr name="Ѳ��ά����">

	      <html:select property="patrolid" styleClass="inputtext" style="width:200px">

	        <html:options collection="patrolCollection" property="value" labelProperty="label"/>
	      </html:select><font color="red"> *</font>
	    </template:formTr>
    </logic:notEqual>

    <logic:equal value="show" name="ShowFIB">
      <template:formTr name="��Ӧ����"  isOdd="false">
        <span id = "listSpan">��ѡ���Ӧ����<br></span>
          <br>
          <span id="toEditSubListSpan"><a href="javascript:toEditCable(1)">��ӱ༭��Ӧ����</a></span>
      </template:formTr>
    </logic:equal>
   <logic:notEqual value="noShow" name="ShowFIB">
   </logic:notEqual>
	<template:formTr name="˵��">
      <html:text property="remark" styleClass="inputtext" style="width:200px" maxlength="45"/>
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
