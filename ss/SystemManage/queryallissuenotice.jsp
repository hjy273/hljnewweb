<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script type="text/javascript">
<!--

function open_notify(NOTICE_ID,FORMAT)
{
 URL="${ctx}/NoticeAction.do?method=showNotice&id="+NOTICE_ID+"&preview=true";
 myleft=(screen.availWidth-650)/2;
 mytop=100
 mywidth=650;
 myheight=500;
 if(FORMAT=="1")
 {
    myleft=0;
    mytop=0
    mywidth=screen.availWidth-10;
    myheight=screen.availHeight-40;
 }
 window.open(URL,"read_news","height="+myheight+",width="+mywidth+",status=1,resizable=no,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft+",resizable=yes");
}
//-->
</script>
<template:titile value="������Ϣ"/>

<display:table name="sessionScope.noticelist"  id="currentRowObject" pagesize="18">
	<display:column property="title" title="����" style="width:45%" maxLength="30"/>
	<display:column property="issueperson" title="������" style="width:10%" maxLength="8" />
	<display:column property="issuedate" title="��������" style="width:15%" maxLength="20"></display:column>
	<display:column media="html" title="����">
		<a href="javascript:open_notify('${currentRowObject.id}')">�鿴</a>

  </display:column>
</display:table>
</h1>
</body>
</html>

