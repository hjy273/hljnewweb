<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<script language="javascript" type="">
function toGetBack(){
        window.history.back();
}
</script><br>
<template:titile value="����SIM���Ž��ն���ʱ���б�"/>

<display:table name="sessionScope.historypercardinfo"   id="currentRowObject"  pagesize="16">
	 <display:column property="arrivetime" title="����ʱ��" sortable="true"/>
	 <display:column property="simid" title="SIM����" sortable="true"/>
</display:table>

<p>
<center>
<input type="button" class="button" onclick="toGetBack()" value="����" >
</center>
<p>