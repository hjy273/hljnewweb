<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>


<%
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
String strRegion = userinfo.getRegionID();
%>
<script language="javascript" type="">
<!--
function isValidForm() {

	if(document.contractorBean.contractorName.value==""){
		alert("�ⲿ��λ���Ʋ���Ϊ��!! ");
		document.contractorBean.contractorName.focus();
		return false;
	}
	if(isNaN(document.contractorBean.pactTerm.value) || document.contractorBean.pactTerm.value == "0"){
		alert("��ͬ����Ӧ��Ϊ��0������!! ");
		return false;
	}
	if(document.contractorBean.principalInfo.value==""){
		alert("������A����Ϊ��!! ");
		document.contractorBean.principalInfo.focus();
		return false;
	}
	if(document.contractorBean.principalB.value==""){
		alert("������B����Ϊ��!! ");
		document.contractorBean.principalB.focus();
		return false;
	}
    if(document.contractorBean.linkmanInfo.value==""){
		alert("��ϵ�绰����Ϊ��!! ");
		document.contractorBean.linkmanInfo.focus();
		return false;
	}
	if(document.contractorBean.workPhone.value==""){
		alert("�칫�绰����Ϊ��!! ");
		document.contractorBean.workPhone.focus();
		return false;
	}
    if(document.contractorBean.fax.value==""){
		alert("���治��Ϊ��!! ");
		document.contractorBean.fax.focus();
		return false;
	}
	if(document.contractorBean.email.value==""){
		alert("Email����Ϊ��!! ");
		document.contractorBean.email.focus();
		return false;
	}
	if(document.contractorBean.address.value==""){
		alert("��λ��ַ����Ϊ��!! ");
		document.contractorBean.address.focus();
		return false;
	}
	if(document.contractorBean.maintenanceArea.value==""){
		alert("ά��������������Ϊ��!! ");
		document.contractorBean.maintenanceArea.focus();
		return false;
	}
    if(document.contractorBean.pactBeginDate.value==""){
		alert("ǩԼʱ�䲻��Ϊ��!! ");
		document.contractorBean.pactBeginDate.focus();
		return false;
	}
    if(document.contractorBean.pactTerm.value==""){
		alert("��ͬ���޲���Ϊ�ո�!! ");
		document.contractorBean.pactTerm.focus();
		return false;
	}
    strRemark = document.contractorBean.remark.value;
    if (strRemark.length>100){
      alert("���ݹ�����˵����ֻ������100���ַ�");
      document.contractorBean.remark.focus();
      return false;
    }

	return true;
}

function getDep(){

	var depV = contractorBean.regionid.value;
	var URL = "getSelectForCon.jsp?depType=2&selectname=parentcontractorid&regionid=" + depV;

	hiddenInfoFrame.location.replace(URL);
}
function addGoBack()
        {
        	try{
            	location.href = "${ctx}/contractorAction.do?method=queryContractor&contractorName=&linkmanInfo=";
                return true;
            }
            catch(e){
            	alert(e);
            }
        }


//-->
</script>

<body>
<template:titile value="���ӵ�λ��Ϣ"/>

<html:form onsubmit="return isValidForm()" method="Post" action="/contractorAction.do?method=addContractor">
  <template:formTable >
    <template:formTr name="��λ����">
      <html:text property="contractorName" styleClass="inputtext" style="width:160px" maxlength="40"/><font color="red"> *</font>
    </template:formTr>
     <template:formTr name="����">
      <html:text property="alias" styleClass="inputtext" style="width:160px"/>
    </template:formTr>
    <!--<template:formTr name="��λ����" isOdd="false">
      <html:select property="depttype" styleClass="inputtext" style="width:160px">
      <html:option value="2">��ά��λ</html:option>
      <html:option value="3">������λ</html:option>
      </html:select>
    </template:formTr>-->
    <input type="hidden" name="depttype" value="2"/>
    <logic:equal value="11" name="LOGIN_USER" property="type">
      <template:formTr name="��������" isOdd="false" style="display:none">
        <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true"  columnName2="regionid" order="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
        <html:select property="regionid" styleClass="inputtext" style="width:160px">
          <html:options  collection="parentRegionCellection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
    </logic:equal>
    <!--
    <template:formTr name="�ϼ���λ">11
      <apptag:setSelectOptions valueName="parentcontractorCollection" tableName="contractorinfo" columnName1="contractorname" columnName2="contractorid" region="true"/>
      <span id="selectSpan">
        <html:select property="parentcontractorid" styleClass="inputtext" style="width:160px">
          <html:option value="0">��
          </html:option>
          <html:options collection="parentcontractorCollection" property="value" labelProperty="label"/>
        </html:select>
      </span>
    </template:formTr>-->
    <logic:equal value="11" name="LOGIN_USER" property="type">
    	 <template:formTr name="�ϼ���λ" style="display:none">
		      	 <html:select property="parentcontractorid" styleClass="inputtext" style="width:160px">
		          	<html:option value="0">�� </html:option>
		         </html:select>
		 </template:formTr>
    </logic:equal>

    <logic:equal value="12" name="LOGIN_USER" property="type">
         <template:formTr name="��������" isOdd="false" style="display:none">
           <html:select property="regionid" styleClass="inputtext" style="width:160px" >
              <html:option value="<%=strRegion%>"></html:option>
           </html:select>
         </template:formTr>
    	 <template:formTr name="�ϼ���λ">
		      <apptag:setSelectOptions valueName="parentcontractorCollection" tableName="contractorinfo" columnName1="contractorname" columnName2="contractorid"  condition=" SUBSTR(REGIONID,3,4)='0000' " />
		          <html:select property="parentcontractorid" styleClass="inputtext" style="width:160px">
		          	<html:option value="0">��  </html:option>
		          <html:options collection="parentcontractorCollection" property="value" labelProperty="label"/>
		        </html:select><font color="red"> *</font>
		    </template:formTr>
    </logic:equal>
    <!--
    <logic:equal value="1" name="LOGIN_USER" property="deptype">
    	 <template:formTr name="�ϼ���λ">
		      <apptag:setSelectOptions valueName="parentcontractorCollection" tableName="contractorinfo" columnName1="contractorname" columnName2="contractorid"  condition=" SUBSTR(REGIONID,3,4)='0000' " />
		          <html:select property="parentcontractorid" styleClass="inputtext" style="width:160px">
		          	<html:option value="0">��  </html:option>
		          <html:options collection="parentcontractorCollection" property="value" labelProperty="label"/>
		        </html:select>
		    </template:formTr>
    </logic:equal>
    -->
    <logic:equal value="2" name="LOGIN_USER" property="deptype">
    	 <template:formTr name="�ϼ���λ">
         	<select name="parentcontractorid" Class="inputtext" style="width:160px">
            	<option value="<bean:write name="LOGIN_USER_DEPT_ID"/>"><bean:write name="LOGIN_USER_DEPT_NAME"/></option>
         	</select><font color="red"> *</font>
		    </template:formTr>
    </logic:equal>
	<template:formTr name="������A" isOdd="false">
      <html:text property="principalInfo" styleClass="inputtext" style="width:160px" maxlength="25"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="������B" >
      <html:text property="principalB" styleClass="inputtext" style="width:160px" maxlength="25"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="��ϵ�绰" >
      <html:text property="linkmanInfo" styleClass="inputtext" style="width:160px" maxlength="25"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="�칫�绰" >
      <html:text property="workPhone" styleClass="inputtext" style="width:160px" maxlength="25"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="����" >
      <html:text property="fax" styleClass="inputtext" style="width:160px" maxlength="25"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="Email" >
      <html:text property="email" styleClass="inputtext" style="width:160px" maxlength="25"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="��λ��ַ" >
      <html:text property="address" styleClass="inputtext" style="width:160px" maxlength="100"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="ά����������" >
      <html:text property="maintenanceArea" styleClass="inputtext" style="width:160px" maxlength="512"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="ǩԼʱ��" isOdd="false">
      <html:text readonly="true" property="pactBeginDate" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})"  style="width:160px" maxlength="45"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="��ͬ����">
      <html:text property="pactTerm" styleClass="inputtext" style="width:160px" maxlength="10"/><font color="red"> *</font>

      ��
</template:formTr>
    <!--
	<template:formTr name="״̬" isOdd="false">
      <html:select property="state" styleClass="inputtext" style="width:160px">
        <html:option value="1">����</html:option>
        <html:option value="2">��ͣ</html:option>
        <html:option value="3">ֹͣ</html:option>
      </html:select>
    </template:formTr>-->
    <template:formTr name="��ע" isOdd="false">
      <textarea cols="" rows="2" name="remark" style="width:280px;" class="textarea" ></textarea>
    </template:formTr>
    <!--
    <template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;ע" isOdd="false">
      <html:text property="remark" styleClass="inputtext" style="width:160px" maxlength="10"/>
    </template:formTr>
    -->
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">����</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">ȡ��</html:reset>
      </td>
      <td>
        <html:button property="action" styleClass="button" onclick="addGoBack()" >����</html:button>
      </td>

    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
</body>