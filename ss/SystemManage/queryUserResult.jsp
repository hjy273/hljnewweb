<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<br>
<template:titile value="查询用户结果"/>
<display:table name="sessionScope.useronlineinfo"   pagesize="18" >
	<display:column property="username" title="用户名"  />
	<display:column property="loginip" title="登录ip"   />
	<display:column property="logintime" title="登录时间"    />
	<display:column property="logouttime" title="退出时间"    />
	<display:column property="onlinetime" title="在线时长(秒)"    />
</display:table>
<html:link action="/userinfoAction.do?method=exportUserOnlineTime">导出为Excel文件</html:link>
<%
DynaBean record;
List list = (List) request.getSession().getAttribute("useronlineinfo");
Iterator iter = list.iterator();
int sum = 0;
        while( iter.hasNext() ){
            record = ( DynaBean )iter.next();
            if( record.get( "onlinetime" ) != null ){
              sum += Integer.parseInt(record.get( "onlinetime" ).toString());
            }
}
%>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
累计登录次数：<%=list.size()%> 次&nbsp;&nbsp;&nbsp;&nbsp;累计登录时间：<%=sum%> 秒

