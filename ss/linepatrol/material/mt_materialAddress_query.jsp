<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<script language="javascript">
function toGetBack(){
        	try{
            	location.href = "/MtAddressAction.do?method=queryAddress";
                return true;
            }
            catch(e){
            	alert(e);
            }
        }
</script>
<template:titile value="��ѯ���ϴ�ŵص���Ϣ"/>
<html:form method="Post" action="/MTAddressAction.do?method=queryAddress">
  <template:formTable contentwidth="200" namewidth="200">
    <template:formTr name="��ŵص�����">
      <html:text property="address" styleClass="inputtext" style="width:205px" maxlength="200"/>
    </template:formTr>
      <template:formTr name="��ά��λ" isOdd="false">
        <html:select property="contractorid" styleClass="inputtext" style="width:205px">
          <html:option value="">����</html:option>
          <html:options collection="deptCollection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
     <template:formSubmit>
      <td>
        <html:submit styleClass="button">��ѯ</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">����</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
    