<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<script language="javascript" type="">

function onChangeDept(){
      k=1;
      for( i=0;i<document.all.workID.options.length;i++){
             if(document.all.workID.options[i].text.substring(0,6)== document.all.rId.value){
                k++;
                document.all.dId.options.length=k;
                document.all.dId.options[0].value="";
                document.all.dId.options[0].text="����";

                document.all.dId.options[k-1].value=document.all.workID.options[i].value;
                document.all.dId.options[k-1].text=document.all.workID.options[i].text.substring(8,document.all.workID.options.length);
             }
      }
      if(k==0)
        document.all.dId.options.length=0;

    }
function getDep(){

	var depV = departBean.regionid.value;
	var URL = "getSelect.jsp?depType=1&selectname=parentID&regionid=" + depV;

	hiddenInfoFrame.location.replace(URL);
}
function toGetBack(){
       window.history.go(-1);
}
//-->
</script>
<apptag:checkpower thirdmould="70203" ishead="5">
  <jsp:forward page="/globinfo/powererror.jsp"/>
</apptag:checkpower>
<br>
<%
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
if(userinfo.getType().equals("11")){%>
<body onload="onChangeDept()">
<% }else{%>
<body>
<% }%>
<template:titile value="��ѯ������Ϣ"/>
<html:form method="Post" action="/departAction.do?method=queryDepart">
  <template:formTable >
    <template:formTr name="��������">
      <html:text property="deptName" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>

    <logic:equal value="11" name="LOGIN_USER" property="type">
          <logic:notEmpty name="reginfo">
            <template:formTr name="��������" isOdd="false">
              <select name="regionid" class="inputtext" style="width:200px" id="rId" onclick="onChangeDept()" >
                <option value="">����</option>
                <logic:present name="reginfo">
                  <logic:iterate id="reginfoId" name="reginfo">
                    <option value="<bean:write name="reginfoId" property="regionid"/>">
                         <bean:write name="reginfoId" property="regionname"/>
                    </option>
                  </logic:iterate>
                </logic:present>
              </select>
            </template:formTr>
        </logic:notEmpty>
    </logic:equal>
      <!--
      <template:formTr name="��������" isOdd="false">
        <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid"/>
        <html:select property="regionid" styleClass="inputtext" style="width:160px">
          <html:option value="">����</html:option>
          <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
      -->


    <logic:notEmpty name="deptinfo">
          <template:formTr name="�ϼ�����" tagID="deptTr">
            <select name="parentID" class="inputtext" style="width:200px" id="dId">
              <option value="">����</option>
              <logic:present name="deptinfo">
                <logic:iterate id="deptinfoId" name="deptinfo">
                  <option value="<bean:write name="deptinfoId" property="deptid"/>">
                  <bean:write name="deptinfoId" property="deptname"/></option>
                </logic:iterate>
              </logic:present>
            </select>
          </template:formTr>

          <logic:present name="deptinfo">
              <select name="workID" style="display:none">
                <logic:iterate id="deptinfoId" name="deptinfo">
                    <option value="<bean:write name="deptinfoId" property="deptid"/>"><bean:write name="deptinfoId" property="regionid"/>--<bean:write name="deptinfoId" property="deptname"/></option>
                </logic:iterate>
              </select>
          </logic:present>
    </logic:notEmpty>

    <!--
    <template:formTr name="�ϼ�����">
      <apptag:setSelectOptions valueName="deptCollection" tableName="deptinfo" columnName1="deptname" columnName2="deptid" region="true"/>
      <span id="selectSpan">
        <html:select property="parentID" styleClass="inputtext" style="width:160px">
          <html:option value="">����</html:option>
          <html:options collection="deptCollection" property="value" labelProperty="label"/>
        </html:select>
      </span>
    </template:formTr>
    -->
    <template:formTr name="�� ϵ ��" isOdd="false">
      <html:text property="linkmanInfo" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>
    <!-- zsh New add 2004-10-13
    <template:formTr name="״̬">
      <html:select property="state" styleClass="inputtext" style="width:200px">
        <html:option value="">����</html:option>
        <html:option value="1">����</html:option>
        <html:option value="2">��ͣ</html:option>
        <html:option value="3">ֹͣ</html:option>
      </html:select>
    </template:formTr>-->
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">��ѯ</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">ȡ��</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<template:cssTable/>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
