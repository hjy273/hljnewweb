<%@ page language="java" contentType="text/html; charset=GBK"%><%@ include file="/common/header.jsp"%>
<template:titile value="��ѯϵͳ���������ֵ���Ϣ���"/>

<script language="javascript">

function rowDelete(idValue){
	if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/dictionaryAction.do?method=deleteDictionary&id=" + idValue;
        self.location.replace(url);
	}
}
</script>
<display:table name="sessionScope.queryResult"  id="resultList" pagesize="15">
	<display:column property="value" title="��ֵ"
        href="${ctx}/dictionaryAction.do?method=loadDictionary" paramId="id"   />
	<display:column property="lable" title="��ǩ" />
	<display:column property="type" title="����" />
	<display:column property="maxsize" title="��󳤶�" />
	<display:column property="tables" title="������" />
	<display:column property="state" title="״̬" />
	<display:column property="remark" title="��ע" />
	<display:column media="html" title="ɾ��">
    <% BasicDynaBean object = (BasicDynaBean)pageContext.findAttribute("resultList");
       String rowid = (String)object.get("value");
    %>
	<a href="javascript:rowDelete('<%=rowid%>')">ɾ��</a>
	</display:column>
</display:table>

