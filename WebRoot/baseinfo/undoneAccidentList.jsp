<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"><tr><td valign="middle" align="center" class="title2">障碍/隐患信息</td></tr></table>

<display:table name="sessionScope.queryresult_ONE" pagesize="2" id="currentRowObject">
  <display:column property="type" title="类型" sortable="true"/>
  <display:column property="sendtime" title="上报时间" sortable="true"/>
  <display:column property="reason" title="障碍原因" sortable="true" />
  <display:column property="emlevel" title="严重程度" sortable="true" />
  <display:column property="subline" title="巡检段" sortable="true" />
  <display:column property="point" title="巡检点" sortable="true" />
  <display:column property="contractor" title="代维单位" sortable="true" />

</display:table>
