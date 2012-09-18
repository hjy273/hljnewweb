<%@include file="/common/header.jsp"%>
<script type="text/javascript">
	function view(id){
		var url = '${ctx}/acceptanceAction.do?method=viewPipeData&id=${apply.id}&pipeId='+id;
		showWin1(url);
		//window.location.href = url;
	}
	function viewPipe(id){
		var url = '${ctx}/acceptanceAction.do?method=viewPipe&id='+id;
		showWin1(url);
	}
	var win1;
	function showWin1(url){
		win1 = new Ext.Window({
			layout : 'fit',
			width:700,
			height:400,
			resizable:true,
			closable:true,
			closeAction : 'close',
			modal:true,
			html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
			plain: true
		});
		win1.show(Ext.getBody());
	}
	function close(){
		win1.close();
	}
</script>
<display:table name="sessionScope.apply.pipes" id="row"  pagesize="10" export="false" defaultsort="8" defaultorder="ascending">
	
	<display:column property="pipeAddress" title="管道地点"/>
	<display:column property="pipeRoute" title="详细路由"/>
	<display:column property="pipeLength0" title="沟公里"/>
	<display:column property="pipeLength1" title="孔公里"/>
	<display:column property="builder" title="施工单位"/>
	<display:column media="html" title="管道属性">
		<apptag:quickLoadList style="width:130px" keyValue="${row.pipeType}" cssClass="select" name="pipeType" listName="pipe_type" type="look" />
	</display:column>
	<display:column media="html" title="产权属性">
		<apptag:quickLoadList style="width:130px" keyValue="${row.pipeProperty}" cssClass="select" name="pipeProperty" listName="property_right" type="look" />
	</display:column>
	<display:column media="html" title="是否录入数据">
		<c:choose>
			<c:when test="${row.isrecord eq '1'}">
				<font color="blue">是</font>
			</c:when>
			<c:otherwise>
				<font color="red">否</font>
			</c:otherwise>
		</c:choose>
	</display:column>
	<display:column media="html" title="是否通过验收">
		<c:choose>
			<c:when test="${row.ispassed eq '1'}">
				<font color="blue">是</font>
			</c:when>
			<c:otherwise>
				<font color="red">否</font>
			</c:otherwise>
		</c:choose>
	</display:column>
	<display:column media="html" title="代维名称">
		<c:choose>
			<c:when test="${empty row.contractorId}">
				未分配代维
			</c:when>
			<c:otherwise>
				<apptag:assorciateAttr table="contractorinfo" label="contractorname" key="contractorid" keyValue="${row.contractorId}" />
			</c:otherwise>
		</c:choose>
	</display:column>
	<display:column media="html" title="状态">
		<c:choose>
			<c:when test="${row.isrecord eq '1'}">
				已录入
			</c:when>
			<c:otherwise>
				未录入
			</c:otherwise>
		</c:choose>
	</display:column>
	<display:column media="html" title="操作">
	<a href="javascript:viewPipe('${row.id}')">基本</a>
			<c:if test="${row.isrecord eq '1'}">
				<a href="javascript:view('${row.id}')">详细</a>
			</c:if>
	</display:column>
</display:table>