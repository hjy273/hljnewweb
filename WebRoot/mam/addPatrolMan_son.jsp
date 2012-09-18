<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<%@page import="java.util.Date" %>
<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
<jsp:useBean id="selectForTag" class="com.cabletech.commons.tags.SelectForTag" scope="request"/>


<%
String condition="";
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
List groupCollection = selectForTag.getPatrolManInfo(userinfo);
request.setAttribute("pglist",groupCollection);
//���ƶ�
if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE regionid IN ('"+userinfo.getRegionID()+"') AND state IS NULL";
}
//�д�ά
if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL  and contractorid='"+userinfo.getDeptID()+"' ";
}
//ʡ�ƶ�
if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL";
}
//ʡ��ά
if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE contractorid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"+userinfo.getDeptID()+"')";
}
List deptCollection = selectForTag.getSelectForTag("contractorname","contractorid","contractorinfo",condition);
request.setAttribute("deptCollection",deptCollection);
%>
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
    alert("�绰�����в��ܺ��к���!!");
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

//-->
</script>
<template:titile value="����Ѳ��Ա��Ϣ"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/patrolSonAction.do?method=addPatrolManSon">
  <template:formTable contentwidth="300" namewidth="200">
    <template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��" isOdd="">
      <html:text property="patrolName" styleClass="inputtext" style="width:200" maxlength="10"/>
      <!-- ����-->
      <html:hidden property="age"/>
      <!-- ���� -->
      <html:hidden property="regionID"/>
    </template:formTr>
    <template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��">
      <html:select property="sex" styleClass="inputtext" style="width:200">
        <html:option value="1">��</html:option>
        <html:option value="2">Ů</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="��λ����"  style="display:none">
      <html:select property="parentID" styleClass="inputtext" style="display:none">
        <html:options collection="deptCollection" property="value" labelProperty="label"/>
      </html:select>

    </template:formTr>

    <logic:equal value="group" name="PMType">
      <template:formTr name="Ѳ��ά����">
        <html:select property="parent_patrol" styleClass="inputtext" style="width:200">

          <logic:present name="pglist">
            <logic:iterate id="pgId" name="pglist">
              <option value="<bean:write name="pgId" property="patrolid"/>"><bean:write name="pgId" property="patrolname"/></option>
            </logic:iterate>
          </logic:present>
        </html:select>
      </template:formTr>
    </logic:equal>

    <template:formTr name="������Ϣ" >
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
      <html:text property="postalcode" styleClass="inputtext" style="width:200" maxlength="6"/>
    </template:formTr>
    <template:formTr name="���֤��" >
      <html:text property="identityCard" styleClass="inputtext" style="width:200" maxlength="18"/>
    </template:formTr>
    <template:formTr name="����Χ">
      <!-- �ĵ���Ϊ������ʾ.Add by Steven Mar 13,2007 -->
      <textarea cols="" rows="2" name="familyAddress" style="width:280;" class="textarea" ></textarea>
    </template:formTr>
    <template:formTr name="��������" >
      <!-- �ĵ���Ϊ������ʾ.Add by Steven Mar 13,2007 -->
      <textarea cols="" rows="2" name="workRecord" style="width:280;" class="textarea" ></textarea>
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
