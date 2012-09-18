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
<template:titile value="查询区域信息"/>
<html:form method="Post" action="/regionAction.do?method=queryRegion">
  <template:formTable contentwidth="200" namewidth="200">
    <template:formTr name="区域编码" >
      <html:text property="regionID" styleClass="inputtext" style="width:160" maxlength="6"/>
    </template:formTr>
    <template:formTr name="区域名称">
      <html:text property="regionName" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
    <template:formTr name="上级区域" >
      <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" columnName2="regionid" region="true" order="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
      <html:select property="parentregionid" styleClass="inputtext" style="width:160">
        <html:option value="">不限</html:option>
        <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr><!--
    <template:formTr name="状态">
      <html:select property="state" styleClass="inputtext" style="width:160">
        <html:option value="">不限</html:option>
        <html:option value="1">正常</html:option>
        <html:option value="2">暂停</html:option>
        <html:option value="3">停止</html:option>
      </html:select>
    </template:formTr>-->
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">查询</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
      <td>
        <input type="button" class="button" onclick="toGetBack()" value="返回" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
