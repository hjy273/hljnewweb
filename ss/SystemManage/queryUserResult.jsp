<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<br>
<template:titile value="��ѯ�û����"/>
<display:table name="sessionScope.useronlineinfo"   pagesize="18" >
	<display:column property="username" title="�û���"  />
	<display:column property="loginip" title="��¼ip"   />
	<display:column property="logintime" title="��¼ʱ��"    />
	<display:column property="logouttime" title="�˳�ʱ��"    />
	<display:column property="onlinetime" title="����ʱ��(��)"    />
</display:table>
<html:link action="/userinfoAction.do?method=exportUserOnlineTime">����ΪExcel�ļ�</html:link>
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
�ۼƵ�¼������<%=list.size()%> ��&nbsp;&nbsp;&nbsp;&nbsp;�ۼƵ�¼ʱ�䣺<%=sum%> ��

