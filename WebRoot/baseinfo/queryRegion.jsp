<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<script language="javascript">
<!--
function toGetBack(){
        	try{
            	location.href = "${ctx}/baseinfo/queryregionresult.jsp";
                return true;
            }
            catch(e){
            	alert(e);
            }
        }

//-->
</script>
<template:titile value="��ѯ������Ϣ"/>
<html:form method="Post" action="/regionAction.do?method=queryRegion">
  <template:formTable contentwidth="200" namewidth="200">
    <template:formTr name="�������" >
      <html:text property="regionID" styleClass="inputtext" style="width:160" maxlength="6"/>
    </template:formTr>
    <template:formTr name="��������">
      <html:text property="regionName" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
    <template:formTr name="�ϼ�����" >
      <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" columnName2="regionid" region="true" order="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
      <html:select property="parentregionid" styleClass="inputtext" style="width:160">
        <html:option value="">����</html:option>
        <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr><!--
    <template:formTr name="״̬">
      <html:select property="state" styleClass="inputtext" style="width:160">
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
      <td>
        <input type="button" class="button" onclick="toGetBack()" value="����" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
