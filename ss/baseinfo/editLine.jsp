<%@page import="com.cabletech.baseinfo.beans.*"%>
<jsp:useBean id="lineBean" class="com.cabletech.baseinfo.beans.LineBean" scope="request"/>
<%@include file="/common/header.jsp"%>
<script language="javascript" type="">
<!--
function isValidForm(form) {
	if(form.lineName.value==""){
		alert("��·���Ʋ���Ϊ��!! ");
		return false;
	}
    if(trim(form.lineName.value)==""){
		alert("��·���Ʋ���Ϊ�ո�!! ");
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
    if(trim(form.ruleDeptID.value)==""){
		alert("�������Ų���Ϊ�ո�!! ");
		return false;
	}
	if(form.lineType.value==""){
		alert("��·������Ϊ��!! ");
		return false;
	}
    if(trim(form.lineType.value)==""){
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
    var url = '<%=(request.getAttribute("InitialURL") == null)? session.getAttribute("S_BACK_URL") : request.getAttribute("InitialURL")%>';
	self.location.replace(url);
}
//-->
</script>
<apptag:checkpower thirdmould="70904" ishead="3">
  <jsp:forward page="/globinfo/powererror.jsp"/>
</apptag:checkpower>
<br>
<template:titile value="�޸���·��Ϣ"/>
<html:form method="Post" onsubmit="return isValidForm(this)" action="/lineAction.do?method=updateLine">
  <template:formTable >
    <template:formTr name="��·����">
      <html:text property="lineName" styleClass="inputtext" style="width:160px" maxlength="50"/><font color="red"> *</font>
	  <html:hidden property="lineID" styleClass="inputtext" style="width:160px"/>
    </template:formTr>
    <!--
    <template:formTr name="���ܲ���" isOdd="false" style="display:none">
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
      <html:select property="ruleDeptID" styleClass="inputtext" style="width:160px">
        <html:options collection="deptCollection" property="value" labelProperty="label"/>
      </html:select><font color="red"> *</font>
    </template:formTr>


	<template:formTr name="��·����" isOdd="false">
    <apptag:setSelectOptions columnName1="name" columnName2="code" tableName="lineclassdic" valueName="linetypeColl"/>
      <html:select property="lineType" styleClass="inputtext" style="width:160px">
      <html:options collection="linetypeColl" property="value" labelProperty="label"/>
      </html:select><font color="red"> *</font>
    </template:formTr>
    <!--
    <template:formTr name="˵&nbsp;&nbsp;&nbsp;&nbsp;��">
      <html:text property="remark" styleClass="inputtext" style="width:160px" maxlength="45"/>
    </template:formTr>
    -->
    <template:formTr name="˵&nbsp;&nbsp;&nbsp;&nbsp;��">
      <html:textarea property="remark" styleClass="textarea" style="width:280px;" rows="2" cols=""></html:textarea>
    </template:formTr>

    <template:formSubmit>
      <td>
        <html:submit styleClass="button" >����</html:submit>
      </td>
      <td>
       <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="����" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
