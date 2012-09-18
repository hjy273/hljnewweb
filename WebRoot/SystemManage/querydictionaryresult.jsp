<%@ page language="java" contentType="text/html; charset=GBK"%><%@ include file="/common/header.jsp"%>
<template:titile value="查询系统基本数据字典信息结果"/>

<script language="javascript">

function rowDelete(idValue){
	if(confirm("确定删除该纪录？")){
        var url = "${ctx}/dictionaryAction.do?method=deleteDictionary&id=" + idValue;
        self.location.replace(url);
	}
}
</script>
<display:table name="sessionScope.queryResult"  id="resultList" pagesize="15">
	<display:column property="value" title="数值"
        href="${ctx}/dictionaryAction.do?method=loadDictionary" paramId="id"   />
	<display:column property="lable" title="标签" />
	<display:column property="type" title="类型" />
	<display:column property="maxsize" title="最大长度" />
	<display:column property="tables" title="属主表" />
	<display:column property="state" title="状态" />
	<display:column property="remark" title="备注" />
	<display:column media="html" title="删除">
    <% BasicDynaBean object = (BasicDynaBean)pageContext.findAttribute("resultList");
       String rowid = (String)object.get("value");
    %>
	<a href="javascript:rowDelete('<%=rowid%>')">删除</a>
	</display:column>
</display:table>

