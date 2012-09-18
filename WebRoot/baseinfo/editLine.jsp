<%@page import="com.cabletech.baseinfo.beans.*"%>
<jsp:useBean id="lineBean" class="com.cabletech.baseinfo.beans.LineBean" scope="request"/>
<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
<script language="javascript" type="">
<!--
function isValidForm(form) {
	if(form.lineName.value==""){
		alert("���Ʋ���Ϊ��!! ");
		return false;
	}
    if(form.lineName.value.trim()==""){
		alert("���Ʋ���Ϊ�ո�!! ");
		return false;
	}
    if(valCharLength(form.lineName.value)>100){
      alert("��·���ƹ��������ܴ���50�����֣���������д��");
      return false;
    }
    if(form.ruleDeptID.value==""){
		alert("�������Ų���Ϊ��!! ");
		return false;
	}
    if(form.ruleDeptID.value.trim()==""){
		alert("�������Ų���Ϊ�ո�!! ");
		return false;
	}
	if(form.lineType.value==""){
		alert("��·������Ϊ��!! ");
		return false;
	}
    if(form.lineType.value.trim()==""){
		alert("��·������Ϊ�ո�!! ");
		return false;
	}
    strRemark = form.remark.value;
    if (strRemark.length>45){
      alert("���ݹ�����˵����ֻ������45���ַ�");
      form.remark.focus();
      return false;
    }

}
function valCharLength(Value){
  var j=0;
  var s = Value;
  for   (var   i=0;   i<s.length;   i++)
  {
    if   (s.substr(i,1).charCodeAt(0)>255)   j   =   j   +   2;
    else   j++
  }
  return j;
}
function toGetBack(){
 	//var url="${ctx}/lineAction.do?method=queryLine&lineName=";  
    var url = '<%=(request.getAttribute("InitialURL") == null)? session.getAttribute("previousURL") : request.getAttribute("InitialURL")%>';
	self.location.replace(url);
}
//-->
</script>
<apptag:checkpower thirdmould="70904" ishead="3">
  <jsp:forward page="/globinfo/powererror.jsp"/>
</apptag:checkpower>
<template:titile value="�޸���·��Ϣ"/>
<html:form method="Post" onsubmit="return isValidForm(this)" action="/lineAction.do?method=updateLine">
  <template:formTable contentwidth="200" namewidth="200">
    <template:formTr name="��·����">
      <html:text property="lineName" styleClass="inputtext" style="width:160" maxlength="50"/>
	  <html:hidden property="lineID" styleClass="inputtext" style="width:160"/>
    </template:formTr>
    <!--
    <template:formTr name="���ܲ���"  style="display:none">
      <select name="ruleDeptID" class="inputtext" style="width:180px" id="uId">
        <logic:present name="deptinfo_line">
          <logic:iterate id="deptinfo_lineId" name="deptinfo_line">
            <option value="<bean:write name="deptinfo_lineId" property="deptid"/>">
              <bean:write name="deptinfo_lineId" property="deptname"/>
            </option>
          </logic:iterate>
        </logic:present>
      </select>
    </template:formTr>
    -->

    <html:hidden property="regionid"/>
    <template:formTr name="��������">
      <apptag:setSelectOptions valueName="deptCollection" tableName="deptinfo" columnName1="deptname" columnName2="deptid" region="true"/>
      <html:select property="ruleDeptID" styleClass="inputtext" style="width:160">
        <html:options collection="deptCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>


	<template:formTr name="��·����" >
    <apptag:setSelectOptions columnName1="name" columnName2="code" tableName="lineclassdic" valueName="linetypeColl"/>
      <html:select property="lineType" styleClass="inputtext" style="width:160">
      <html:options collection="linetypeColl" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <!--
    <template:formTr name="˵&nbsp;&nbsp;&nbsp;&nbsp;��">
      <html:text property="remark" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
    -->
    <template:formTr name="˵&nbsp;&nbsp;&nbsp;&nbsp;��">
      <html:textarea property="remark" styleClass="textarea" style="width:280;" rows="2" cols=""></html:textarea>
    </template:formTr>

    <template:formSubmit>
      <td>
        <html:submit styleClass="button" >����</html:submit>
      </td>
      <td>
        <input type="button" class="button" onclick="toGetBack()" value="����" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
