<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<script language="javascript" type="">
function toGetBack(){
        window.history.back();
}
</script><br>
<template:titile value="具体SIM卡号接收短信时间列表"/>

<display:table name="sessionScope.historypercardinfo"   id="currentRowObject"  pagesize="16">
	 <display:column property="arrivetime" title="接收时间" sortable="true"/>
	 <display:column property="simid" title="SIM卡号" sortable="true"/>
</display:table>

<p>
<center>
<input type="button" class="button" onclick="toGetBack()" value="返回" >
</center>
<p>