<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
<jsp:useBean id="selectForTag" class="com.cabletech.commons.tags.SelectForTag" scope="request"/>
<script language="javascript" type="text/javascript">
function isValidForm() {
    //����У��
	if(document.departBean.deptName.value==""){
		alert("�������Ʋ���Ϊ��!! ");
        document.departBean.deptName.focus();
		return false;
	}
    //��������һ�δ��룬���Կ��Ʋ������Ʋ���Ϊ�ո�.Add by Steven Mar 12,2007
    if(document.departBean.deptName.value.trim()==""){
		alert("�������Ʋ���Ϊ�ո�!! ");
        document.departBean.deptName.focus();
		return false;
	}
    if(valCharLength(document.departBean.deptName.value)>20){
      alert("������������̫�࣡���ܳ���10������");
      return false;
    }
	if(document.departBean.linkmanInfo.value==""){
		alert("��ϵ�˲���Ϊ��!! ");
        document.departBean.linkmanInfo.focus();
		return false;
	}
    //��������һ�δ��룬���Կ�����ϵ�˲���Ϊ�ո�.Add by Steven Mar 12,2007
    if(document.departBean.linkmanInfo.value.trim()==""){
		alert("��ϵ�˲���Ϊ�ո�!! ");
        document.departBean.linkmanInfo.focus();
		return false;
	}

	return true;
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

function getDep(){
	var depV = departBean.regionid.value;
	var URL = "getSelect.jsp?depType=1&selectname=parentID&regionid=" + depV;
	hiddenInfoFrame.location.replace(URL);
}
function toGetBack(){
 	var url="${ctx}/departAction.do?method=queryDepart";
	self.location.replace(url);
//window.history.go(-1);
}

function loadParent(sel){
	var URL = "${ctx}/departAction.do?method=loadParentDept&regionId=" + sel.value;
	var myAjax = new Ajax.Request( URL , {
        method: 'post',
        asynchronous: 'true',
        onSuccess:function(transport){
	        var result=transport.responseText
	        alert(result);
	        $("op").innerHTML=result;
        }
    });
}
</script>

<template:titile value="���Ӳ�����Ϣ"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/departAction.do?method=addDepart">
  <template:formTable contentwidth="200" namewidth="200">
    <template:formTr name="��������">
      <html:text property="deptName" styleClass="inputtext" style="width:160" maxlength="20"/>
    </template:formTr>
    <logic:equal value="11" name="LOGIN_USER" property="type">
      <template:formTr name="��������" >
        <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" order="regionid" />
        <html:select property="regionid" styleClass="inputtext" style="width:160">
          <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
       <template:formTr name="�ϼ�����">
       	<s:select list="%{#request.parentIdcon}" name="parentID" cssClass="inputtext" cssStyle="width:160"></s:select>
        
      </template:formTr>
    </logic:equal>
     <logic:equal value="12" name="LOGIN_USER" property="type">
      <template:formTr name="��������"  style="display:none">
        <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" order="regionid"/>
        <html:select property="regionid" styleClass="inputtext" style="width:160">
          <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
    </logic:equal>

    <logic:equal value="12" name="LOGIN_USER" property="type">
      <template:formTr name="�ϼ�����">
        <s:select list="%{#request.parentIdcon}" name="parentID" cssClass="inputtext" cssStyle="width:160"></s:select>
      </template:formTr>
    </logic:equal>

    <template:formTr name="�� ϵ ��" >
      <html:text property="linkmanInfo" styleClass="inputtext" style="width:160" maxlength="25"/>
    </template:formTr>
    <template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;ע" >
      <html:text property="remark" styleClass="inputtext" style="width:160" maxlength="10"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">����</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">ȡ��</html:reset>
      </td>
      <td>
        <input type="button" class="button" onclick="toGetBack()" value="����" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
