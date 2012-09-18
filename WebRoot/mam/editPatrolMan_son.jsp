<%@page import="com.cabletech.baseinfo.beans.*"%>
<jsp:useBean id="patrolmanBean" class="com.cabletech.mam.beans.PatrolManBean" scope="request"/>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<%@page import="java.util.Date" %>
<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
<jsp:useBean id="selectForTag" class="com.cabletech.commons.tags.SelectForTag" scope="request"/>
<%
String condition="";
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
//���ƶ�
if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE regionid IN ('"+userinfo.getRegionID()+"') AND state IS NULL";
}
//�д�ά
if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL  and parentid='"+userinfo.getDeptID()+"' ";
}
//ʡ�ƶ�
if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL";
}
//ʡ��ά
if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE parentid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"+userinfo.getDeptID()+"')";
}
List groupCollection = selectForTag.getSelectForTag("patrolname","patrolid","patrolmaninfo",condition);
request.setAttribute("groupCollection",groupCollection);
%>
<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<script src="${ctx}/common/validator.js" type=text/javascript></script>
<script language="javascript" type="">
<!--
function isValidForm() {

  if(document.patrolSonBean.patrolName.value==""){
    alert("��������Ϊ��!! ");
    document.patrolSonBean.patrolName.focus();
    return false;
  }
  //��������һ�δ��룬���Կ�����������Ϊ�ո�.Add by Steven Mar 13,2007
  if(document.patrolSonBean.patrolName.value.trim()==""){
    alert("��������Ϊ�ո�!! ");
    document.patrolSonBean.patrolName.focus();
    return false;
  }

  str1 = document.patrolSonBean.phone.value;
  //��������һ�δ��룬���Կ��Ƶ绰�����ʽ.Add by Steven Mar 13,2007
  if (CheckTEL(str1)!=true){
    document.patrolSonBean.phone.focus();
    return false;
  }
  /*
  if (escape(str1).indexOf("%u")!=-1) {
    alert("�绰�����в��ܺ��к���!");
    document.patrolSonBean.phone.focus();
    return false;
  }*/
  str2 = document.patrolSonBean.mobile.value;
  //��������һ�δ��룬���Կ����ֻ������ʽ.Add by Steven Mar 13,2007
  if (ChkMobilePhone(str2)!=true){
    document.patrolSonBean.mobile.focus();
    return false;
  }

  ////��������һ�δ��룬���Կ���ѡ��ĳ�������.Add by Steven Mar 13,2007
  date = new Date();
  intYear = parseInt(date.getYear());
  choosedate = document.patrolSonBean.birthday.value;
  if ((choosedate!="" || choosedate!=null) && parseInt(choosedate.substring(0,4))>=intYear){
    alert("�Ƿ�ѡ�񣬳�����ݲ����ܵ��ڻ��ߴ��ڵ�ǰϵͳ���");
    document.patrolSonBean.birthday.value="";
    return false;
  }
  str3 = document.patrolSonBean.postalcode.value;
  if (checkPostcode(str3)!=1){
    document.patrolSonBean.postalcode.focus();
    return false;
  }
  str4 = document.patrolSonBean.identityCard.value;
  //��������һ�δ��룬���Կ������֤�����ʽ.Add by Steven Mar 13,2007
  if (checkIDCard(str4)!=1){
    document.patrolSonBean.identityCard.focus();
    return false;
  }
  str5 = document.patrolSonBean.familyAddress.value;
  if (str5.length>20){
    alert("���ݹ���������Χ��ֻ������20���ַ�");
    document.patrolSonBean.familyAddress.focus();
    return false;
  }
  str6 = document.patrolSonBean.workRecord.value;
  if (str6.length>1024){
    alert("���ݹ���������������ֻ������1024���ַ�");
    document.patrolSonBean.workRecord.focus();
    return false;
  }
  return true;
}


function toGetBack(){
  var url = "${ctx}/patrolSonAction.do?method=queryPatrolSon";
  self.location.replace(url);

}

//-->
</script>
<template:titile value="�޸�Ѳ��Ա��Ϣ"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/patrolSonAction.do?method=updatePatrolSon">
  <template:formTable contentwidth="300" namewidth="200">

    <html:hidden  property="patrolID" />
    <template:formTr name="��  ��">
      <html:text property="patrolName" styleClass="inputtext" style="width:200" maxlength="10"/>
      <!-- ����-->
      <html:hidden property="age"/>
    </template:formTr>
    <template:formTr name="��  ��" >
      <html:select property="sex" styleClass="inputtext" style="width:200">
        <html:option value="1">��</html:option>
        <html:option value="2">Ů</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="��������">
      <apptag:setSelectOptions valueName="regionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" order="regionid"  condition="substr(REGIONID,3,4) != '1111' "/>
      <html:select property="regionID" styleClass="inputtext" style="width:200">
        <html:options collection="regionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <logic:equal value="1" name="LOGIN_USER" property="deptype">
      <template:formTr name="��λ����" >
        <apptag:setSelectOptions valueName="deptCollection" tableName="contractorinfo" columnName1="contractorname" columnName2="contractorid" region="true"/>
        <html:select property="parentID" styleClass="inputtext" style="width:200">
          <html:options collection="deptCollection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
    </logic:equal>
    <logic:equal value="2" name="LOGIN_USER" property="deptype">
      <template:formTr name="��λ����">
        <select name="parentID" Class="inputtext" style="width:200">
          <option value="${LOGIN_USER.deptID }">${LOGIN_USER.deptName }</option>
        </select>
      </template:formTr>
    </logic:equal>
    <logic:equal value="group" name="PMType">
      <template:formTr name="Ѳ��ά����">

        <html:select property="parent_patrol" styleClass="inputtext" style="width:200">
          <html:options collection="groupCollection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
    </logic:equal>

    <template:formTr name="������Ϣ"  >
      <html:text property="jobInfo" styleClass="inputtext" style="width:200" maxlength="50"/>
    </template:formTr>
    <template:formTr name="����״̬">
      <html:select property="jobState" styleClass="inputtext" style="width:200">
        <html:option value="1">�ڸ�</html:option>
        <html:option value="2">�ݼ�</html:option>
        <html:option value="3">��ְ</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="�̶��绰" >
      <html:text property="phone" styleClass="inputtext" style="width:200" maxlength="12"/>
    </template:formTr>
    <template:formTr name="�ƶ��绰">
      <html:text property="mobile" styleClass="inputtext" style="width:200" maxlength="12"/>
    </template:formTr>
    <template:formTr name="��������" >
      <html:text readonly="true" property="birthday" styleClass="inputtext" style="width:200" maxlength="12"/>
      <apptag:date property="birthday"/>
    </template:formTr>
    <template:formTr name="����״��">
      <html:select property="isMarriage" styleClass="inputtext" style="width:200">
        <html:option value="0">δ��</html:option>
        <html:option value="1">�ѻ�</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="�Ļ��̶�" >
      <html:select property="culture" styleClass="inputtext" style="width:200">
        <html:option value="4">���м�����</html:option>
        <html:option value="1">����/��ר</html:option>
        <html:option value="2">��ר</html:option>
        <html:option value="3">����/��������</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="��������">
      <html:hidden property="state"/>
      <html:text property="postalcode" styleClass="inputtext" style="width:200" maxlength="6"/>
    </template:formTr>
    <template:formTr name="���֤��" >
      <html:text property="identityCard" styleClass="inputtext" style="width:200" maxlength="18"/>
    </template:formTr>

   <template:formTr name="����Χ">
      <!-- �ĵ���Ϊ������ʾ.Add by Steven Mar 16,2007 -->
      <html:textarea property="familyAddress" styleClass="textarea" style="width:280;" rows="2" cols=""></html:textarea>
   </template:formTr>
    <template:formTr name="��������" >
      <!-- �ĵ���Ϊ������ʾ.Add by Steven Mar 16,2007 -->
      <html:textarea property="workRecord" styleClass="textarea" style="width:280;" rows="2" cols="" ></html:textarea>
    </template:formTr>

    <html:hidden property="state"/>
    <html:hidden property="remark"/>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">����</html:submit>
      </td>
      <td>
        <input type="button" class="button" onclick="javascript:history.back()" value="����" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
