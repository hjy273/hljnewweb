<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>

<script language="javascript" type="">

function isValidForm(form) {
	if(form.lineName.value==""){
		alert("���Ʋ���Ϊ��!! ");
		return false;
	}
    if(trim(form.lineName.value)==""){
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
 	var url="${ctx}/lineAction.do?method=queryLine&lineName=";
	self.location.replace(url);
}

//-->
</script>
<apptag:checkpower thirdmould="70902" ishead="5">
  <jsp:forward page="/globinfo/powererror.jsp"/>
</apptag:checkpower>
<template:titile value="������·��Ϣ"/>
<html:form onsubmit="return isValidForm(this)" method="Post" action="/lineAction.do?method=addLine">
  <template:formTable contentwidth="200" namewidth="200">
    <template:formTr name="��·����">
      <html:text property="lineName" styleClass="inputtext" style="width:160" maxlength="50" title="��·�������50������"/>
    </template:formTr>

  <!--
     <template:formTr name="���ܲ���" >
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


    <template:formTr name="��������" >
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

    <template:formTr name="˵&nbsp;&nbsp;&nbsp;&nbsp;��" >
      <textarea cols="" rows="2" name="remark" style="width:280;height:80" class="textarea" ></textarea>
    </template:formTr>
    <!--
    <template:formTr name="˵&nbsp;&nbsp;&nbsp;&nbsp;��" >
      <html:text property="remark" styleClass="inputtext" style="width:160" maxlength="45"/>
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
        <html:button  property="button" styleClass="button" onclick="toGetBack()">����</html:button>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>