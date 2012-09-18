<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	<script type="text/javascript">
		
		var win;
		function showWin(url){
			win = new Ext.Window({
				layout : 'fit',
				width:600,
				height:400,
				resizable:true,
				closeAction : 'close',
				modal:true,
				html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
				plain: true
			});
			win.show(Ext.getBody());
		}
		function close(){
			win.close();
		}
		function viewCable(id){
			var url = '${ctx}/acceptanceAction.do?method=viewCable&id='+id;
			showWin(url);
		}
		function viewPipe(id){
			var url = '${ctx}/acceptanceAction.do?method=viewPipe&id='+id;
			showWin(url);
		}
		function setValue(obj){
			document.getElementById('submit').disabled = "disabled";
			var url = "${ctx}/acceptanceAction.do?method=setValue&value="+obj.value+"&type="+obj.checked;
			var myAjax = new Ajax.Request(
				url, 
				{method:"post", onComplete:callback, asynchronous:true}
			);
		}
		function callback(){
			document.getElementById('submit').disabled = "";
		}
	</script>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>

	<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
	
	
</head>
<body>
	<template:titile value="认领验收交维任务" />
	<html:form action="/acceptanceAction.do?method=claim" styleId="form">
		<input type="hidden" name="id" value="${apply.id}" />
		<template:formTable namewidth="200" contentwidth="400">
			<template:formTr name="项目经理">
				<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="${apply.applicant}" />
			</template:formTr>
			<template:formTr name="工程名称">
				${apply.name}
			</template:formTr>
			<template:formTr name="验收资源类型">
				<c:choose>
					<c:when test="${apply.resourceType eq '1'}">
						光缆
					</c:when>
					<c:otherwise>
						管道
					</c:otherwise>
				</c:choose>
			</template:formTr>
			<template:formTr name="备注">
				${apply.remark}
			</template:formTr>
			<template:formTr name="是否认领">
				<input type="checkbox" name="isClaim" id="isClaim" value="no"/>不认领<span style="color:red">（如果没有可认领验收资源，请将该选项选择上。然后提交）</span>
			</template:formTr>
		</template:formTable>
		<c:choose>
			<c:when test="${apply.resourceType eq '1'}">
				<display:table name="sessionScope.apply.cables" id="row" pagesize="10" export="false" sort="list" defaultsort="2" defaultorder="ascending">
					<c:set var="check" value="" />
					<c:forEach var="i" items="${choose}">
						<c:if test="${i eq row.id}">
							<c:set var="check" value="checked" />
						</c:if>
					</c:forEach>
					<display:column media="html" title="选择">
						<input type="checkbox" name="sel" id="${row.id}" value="${row.id}" ${check} onclick="setValue(this);"/>
					</display:column>
					<display:column property="cableNo" title="光缆编号" sortable="true"/>
					<display:column property="trunk" title="光缆中继段" sortable="true"/>
					<display:column property="fibercoreNo" title="纤芯数" sortable="true"/>
					<display:column media="html" title="光缆级别" sortable="true">
						<apptag:quickLoadList style="width:130px" keyValue="${row.cableLevel}" cssClass="select" name="cableLevel" listName="cabletype" type="look" />
					</display:column>
					<display:column media="html" title="光缆长度" sortable="true">
						${row.cableLength}千米
					</display:column>
					<display:column media="html" title="操作" sortable="true">
						<a href="javascript:viewCable('${row.id}')">查看</a>
					</display:column>
				</display:table>
			</c:when>
			<c:otherwise>
				<display:table name="sessionScope.apply.pipes" id="row" pagesize="10" export="false" sort="list" defaultsort="2" defaultorder="ascending">
					<c:set var="check" value="" />
					<c:forEach var="i" items="${choose}">
						<c:if test="${i eq row.id}">
							<c:set var="check" value="checked" />
						</c:if>
					</c:forEach>
					<display:column media="html" title="选择">
						<input type="checkbox" value="${row.id}" ${check} onclick="setValue(this)"/>
					</display:column>
					<display:column property="projectName" title="工程名称" sortable="true"/>
					<display:column property="pipeAddress" title="管道地点" sortable="true"/>
					<display:column property="pipeRoute" title="详细路由" sortable="true"/>
					<display:column property="builder" title="施工单位" sortable="true"/>
					<display:column media="html" title="产权属性" sortable="true">
						<apptag:quickLoadList style="width:130px" keyValue="${row.pipeProperty}" cssClass="select" name="pipeProperty" listName="property_right" type="look" />
					</display:column>
					<display:column media="html" title="管道属性" sortable="true">
						<apptag:quickLoadList style="width:130px" keyValue="${row.pipeType}" cssClass="select" name="pipeType" listName="pipe_type" type="look" />
					</display:column>
					<display:column media="html" title="操作" sortable="true">
						<a href="javascript:viewPipe('${row.id}')">查看</a>
					</display:column>
				</display:table>
			</c:otherwise>
		</c:choose>
		<table class="tdlist">
			<tr><td class="tdlist">
				<input type="submit" value="提交" id="submit" class="button" />
				<input type="button" value="返回" class="button" onclick="history.back();"/>
			</td></tr>
		</table>
	</html:form>
</body>
</html>