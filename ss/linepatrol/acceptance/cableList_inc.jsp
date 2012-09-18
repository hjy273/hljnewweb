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
	<display:column property="cableNo" title="���±��"/>
	<display:column property="trunk" title="�����м̶�"/>
	<display:column property="fibercoreNo" title="��о��"/>
	<display:column media="html" title="���¼���">
		<apptag:quickLoadList style="width:130px" keyValue="${row.cableLevel}" cssClass="select" name="cableLevel" listName="cabletype" type="look" />
	</display:column>
	<display:column media="html" title="���³���">
		${row.cableLength}ǧ��
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
		<a href="javascript:viewCable('${row.id}')">����</a>
			<c:if test="${row.isrecord eq '1'}">
				<a href="javascript:view('${row.id}')">��ϸ</a>
			</c:if>
	</display:column>
</display:table>