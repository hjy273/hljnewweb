<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script type="text/javascript">
<!--
function toDelete(idValue){

    if(confirm("确定删除该纪录？")){
        var url = "${ctx}/NoticeAction.do?method=delNotice&id=" + idValue;
        self.location.replace(url);
   }
}
function toCancel(idValue){
        var url = "${ctx}/NoticeAction.do?method=cancelNoticeMeet&id=" + idValue;
        self.location.replace(url);

}

function toEdit(idValue){
        var url = "${ctx}/NoticeAction.do?method=editForm&id=" + idValue;
        self.location.replace(url);

}
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
<template:titile value="发布信息列表"/>

<display:table name="sessionScope.noticelist"  id="currentRowObject" pagesize="18">
	<display:column property="title" title="标题" style="width:45%" maxLength="30"/>
	<display:column property="issueperson" title="发布人" style="width:10%" maxLength="8" />
	<display:column property="isissue" title="是否发布" style="width:10%"/>
	<display:column property="issuedate" title="发布日期" style="width:15%" maxLength="20"></display:column>
	<display:column media="html" title="操作">
		<a href="javascript:open_notify('${currentRowObject.id}')">预览</a>
		<a href="javascript:toEdit('${currentRowObject.id}')">修改</a>
		<a href="javascript:toDelete('${currentRowObject.id}')">删除</a>
		<c:if test="${currentRowObject.isCanceled=='0'&&currentRowObject.type=='B21'}">
		<!-- <a href="javascript:toCancel('${currentRowObject.id}')">取消</a> -->
		</c:if>
    </display:column>
</display:table>
</h1>
</body>
</html>

