<%@include file="/common/header.jsp"%>
<script type="text/javascript">
	function view(id){
		var url = '${ctx}/acceptanceAction.do?method=viewCableData&id=${apply.id}&cableId='+id;
		showWin1(url);
		//window.location.href = url;
	}
	function viewCable(id){
		var url = '${ctx}/acceptanceAction.do?method=viewCable&id='+id;
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
<display:table name="sessionScope.apply.cables" id="row" pagesize="10" export="false" defaultsort="8" defaultorder="ascending">
	<display:column property="cableNo" title="光缆编号"/>
	<display:column property="trunk" title="光缆中继段"/>
	<display:column property="fibercoreNo" title="纤芯数"/>
	<display:column media="html" title="光缆级别">
		<apptag:quickLoadList style="width:130px" keyValue="${row.cableLevel}" cssClass="select" name="cableLevel" listName="cabletype" type="look" />
	</display:column>
	<display:column media="html" title="光缆长度">
		${row.cableLength}千米
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
		<a href="javascript:viewCable('${row.id}')">基本</a>
			<c:if test="${row.isrecord eq '1'}">
				<a href="javascript:view('${row.id}')">详细</a>
			</c:if>
	</display:column>
</display:table>