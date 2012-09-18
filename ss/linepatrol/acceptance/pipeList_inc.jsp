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
	
	<display:column property="pipeAddress" title="�ܵ��ص�"/>
	<display:column property="pipeRoute" title="��ϸ·��"/>
	<display:column property="pipeLength0" title="������"/>
	<display:column property="pipeLength1" title="�׹���"/>
	<display:column property="builder" title="ʩ����λ"/>
	<display:column media="html" title="�ܵ�����">
		<apptag:quickLoadList style="width:130px" keyValue="${row.pipeType}" cssClass="select" name="pipeType" listName="pipe_type" type="look" />
	</display:column>
	<display:column media="html" title="��Ȩ����">
		<apptag:quickLoadList style="width:130px" keyValue="${row.pipeProperty}" cssClass="select" name="pipeProperty" listName="property_right" type="look" />
	</display:column>
	<display:column media="html" title="�Ƿ�¼������">
		<c:choose>
			<c:when test="${row.isrecord eq '1'}">
				<font color="blue">��</font>
			</c:when>
			<c:otherwise>
				<font color="red">��</font>
			</c:otherwise>
		</c:choose>
	</display:column>
	<display:column media="html" title="�Ƿ�ͨ������">
		<c:choose>
			<c:when test="${row.ispassed eq '1'}">
				<font color="blue">��</font>
			</c:when>
			<c:otherwise>
				<font color="red">��</font>
			</c:otherwise>
		</c:choose>
	</display:column>
	<display:column media="html" title="��ά����">
		<c:choose>
			<c:when test="${empty row.contractorId}">
				δ�����ά
			</c:when>
			<c:otherwise>
				<apptag:assorciateAttr table="contractorinfo" label="contractorname" key="contractorid" keyValue="${row.contractorId}" />
			</c:otherwise>
		</c:choose>
	</display:column>
	<display:column media="html" title="״̬">
		<c:choose>
			<c:when test="${row.isrecord eq '1'}">
				��¼��
			</c:when>
			<c:otherwise>
				δ¼��
			</c:otherwise>
		</c:choose>
	</display:column>
	<display:column media="html" title="����">
	<a href="javascript:viewPipe('${row.id}')">����</a>
			<c:if test="${row.isrecord eq '1'}">
				<a href="javascript:view('${row.id}')">��ϸ</a>
			</c:if>
	</display:column>
</display:table>