<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<br>
<script type="text/javascript">

</script>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<body background="#FFFFFF">
	<display:table name="sessionScope.confirmResults" id="currentRowObject" pagesize="18" export="false" defaultorder="descending" sort="list">
		<display:column title="ȷ����" property="confirmer"></display:column>
		<display:column media="html" title="ȷ�Ͻ��">
			<c:if test="${currentRowObject.confirmResult=='2'}">
				ȷ��ͨ��
			</c:if>
			<c:if test="${currentRowObject.confirmResult=='3'}">
				ȷ�ϲ�ͨ��
			</c:if>
		</display:column>
		<display:column title="��������" property="result" maxLength="16" style="width:36%;"></display:column>
		<display:column title="ȷ��ʱ��" property="confirmDate" ></display:column>
	</display:table>
<div align="center">
<input name="btnClose" value="�ر�" class="button" type="button" onclick="closeProcessWin();" />
</div>
</body>
