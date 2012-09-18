<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<%@page import="java.util.Date" %>
<jsp:useBean id="statdao" class="com.cabletech.statistics.dao.StatDao" scope="request"/>
<%
List groupCollection = statdao.getPatrolManInfo(request);   
request.setAttribute("pglist",groupCollection);
%>
 
<%
String condition="";
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
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
List deptCollection = statdao.getSelectForTag("contractorname","contractorid","contractorinfo",condition);
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
  if(trim(document.patrolSonBean.patrolName.value)==""){
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
<script language="javascript" src="${ctx}/js/validation/prototype.js"
			type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js"
			type=""></script>
		<script language="javascript"
			src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css"
			rel="stylesheet" type="text/css">

<br>
<template:titile value="����Ѳ��Ա��Ϣ"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/patrolSonAction.do?method=addPatrolManSon" styleId="form">
  <template:formTable>
    <template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��" isOdd="">
      <html:text property="patrolName" styleClass="inputtext" style="width:200px" maxlength="10"/>&nbsp;&nbsp;<font color="red">*</font>
      <!-- ����-->
      <html:hidden property="age"/>
      <!-- ���� -->
      <html:hidden property="regionID"/>
    </template:formTr>
    <template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��">
      <html:select property="sex" styleClass="inputtext" style="width:200px">
        <html:option value="1">��</html:option>
        <html:option value="2">Ů</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="��λ����" isOdd="false" style="display:none">
      <html:select property="parentID" styleClass="inputtext" style="display:none">
        <html:options collection="deptCollection" property="value" labelProperty="label"/>
      </html:select>

    </template:formTr>

    <logic:equal value="group" name="PMType">
      <template:formTr name="Ѳ��ά����">
        <html:select property="parent_patrol" styleClass="inputtext" style="width:200px">

          <logic:present name="pglist">
            <logic:iterate id="pgId" name="pglist">
              <option value="<bean:write name="pgId" property="patrolid"/>"><bean:write name="pgId" property="patrolname"/></option>
            </logic:iterate>
          </logic:present>
        </html:select>
      </template:formTr>
    </logic:equal>

    <template:formTr name="������Ϣ" isOdd="false">
      <html:text property="jobInfo" styleClass="inputtext" style="width:200px" maxlength="50"/>
    </template:formTr>
    <template:formTr name="����״̬">
      <html:select property="jobState" styleClass="inputtext" style="width:200px">
        <html:option value="1">�ڸ�</html:option>
        <html:option value="2">�ݼ�</html:option>
        <html:option value="3">��ְ</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="�̶��绰" isOdd="false">
      <html:text property="phone" styleClass="inputtext validate-phone" style="width:200px" maxlength="12"/>
    </template:formTr>
    <template:formTr name="�ƶ��绰">
      <html:text property="mobile" styleClass="inputtext validate-mobile-phone" style="width:200px" maxlength="12"/>
    </template:formTr>
    <template:formTr name="��������" isOdd="false">
      <html:text readonly="true" property="birthday" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:200px" maxlength="12"/>
    </template:formTr>
    <template:formTr name="����״��">
      <html:select property="isMarriage" styleClass="inputtext" style="width:200px">
        <html:option value="0">δ��</html:option>
        <html:option value="1">�ѻ�</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="�Ļ��̶�" isOdd="false">
      <html:select property="culture" styleClass="inputtext" style="width:200px">
        <html:option value="4">���м�����</html:option>
        <html:option value="1">����/��ר</html:option>
        <html:option value="2">��ר</html:option>
        <html:option value="3">����/��������</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="��������">
      <html:text property="postalcode" styleClass="inputtext validate-zip" style="width:200px" maxlength="6"/>
    </template:formTr>
    <template:formTr name="���֤��" isOdd="false">
      <html:text property="identityCard" styleClass="inputtext validate-id-number" style="width:200px" maxlength="18"/>
    </template:formTr>
    <template:formTr name="����Χ">
      <!-- �ĵ���Ϊ������ʾ.Add by Steven Mar 13,2007 -->
      <textarea cols="" rows="2" name="familyAddress" style="width:280px" class="textarea" ></textarea>
    </template:formTr>
    <template:formTr name="��������" isOdd="false">
      <!-- �ĵ���Ϊ������ʾ.Add by Steven Mar 13,2007 -->
      <textarea cols="" rows="2" name="workRecord" style="width:280px;" class="textarea" ></textarea>
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
<script type="text/javascript">
		function formCallback(result, form) {
			window.status = "valiation callback for form '" + form.id + "': result = " + result;
		}

		var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>