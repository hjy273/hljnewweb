<%@include file="/common/header.jsp"%>

<script>
function open_notify(NOTICE_ID,FORMAT)
{
 URL="${ctx}/NoticeAction.do?method=showNotice&id="+NOTICE_ID;
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
</script>
<fieldset><legend><a href="#">公告信息</a></legend>
<ul>
<%=request.getAttribute("notice") %>
</ul>
<div align="right"><a href="javascript:window.history.go(-1)" >返回</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
</fieldset>