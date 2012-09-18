<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
        var url = "${ctx}/countyinfoAction.do?method=deleteCounty&id=" + idValue;
        self.location.replace(url);
    }
}


function toEdit(idValue){
        var url = "${ctx}/countyinfoAction.do?method=loadCounty&id=" + idValue;
        self.location.replace(url);

}
goBack=function(){
	var url="${ctx}/linepatrol/remedy/queryCounty.jsp";
	self.location.replace(url);
}
</script>
<%
    BasicDynaBean object;
    String countyid;
%>
<div id="main">
	<div class="lbtitle">

		<div class="title2">
			<center>
				查询区县信息结果
			</center>
			<hr>
		</div>
	</div>
	<display:table name="sessionScope.queryresult"
		requestURI="${ctx}/countyinfoAction.do?method=queryCounty"
		pagesize="18" id="currentRowObject">
		<display:column property="town" title="区县名称" />
		<display:column property="regionid" title="所属区域" />
		<display:column property="remark" title="备注"
			style="word-break:break-all;" />

		<apptag:checkpower thirdmould="70404" ishead="0">
			<display:column media="html" title="操作">
				<%
				    object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				    countyid = "";
				    if (object != null)
				        countyid = String.valueOf(object.get("id"));
				%>
				<a href="javascript:toEdit('<%=countyid%>')">修改</a>
			</display:column>
		</apptag:checkpower>
		<apptag:checkpower thirdmould="70405" ishead="0">
			<display:column media="html" title="操作">
				<%
				    object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				    countyid = String.valueOf(object.get("id"));
				%>
				<a href="javascript:toDelete('<%=countyid%>')">删除</a>
			</display:column>
		</apptag:checkpower>
	</display:table>
	<logic:empty name="queryresult">
	</logic:empty>
	<p align="center">
		<input name="btnBack" value="返回" onclick="goBack();" type="button"
			class="button" />
	</p>