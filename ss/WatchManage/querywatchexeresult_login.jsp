<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"><tr><td valign="middle" align="center" class="title2">��ǰ����ִ�ж�����Ϣ</td></tr></table>

<display:table name="sessionScope.queryResult_THREE"  id="resultList" pagesize="18">
	<logic:equal value="group" name="PMType">
    	<display:column property="patrol" title="Ѳ��ά����"  sortable="true"/>
	</logic:equal>
    <logic:notEqual value="group" name="PMType">
    	<display:column property="patrol" title="ִ��Ѳ��ά����"  sortable="true"/>
    </logic:notEqual>

	<display:column property="sim" title="�豸�ֻ�����"  sortable="true"/>
	<display:column property="exetime" title="ִ��ʱ��"  sortable="true"/>
	<display:column property="watch" title="��������"  sortable="true"/>
	<display:column property="place" title="�����ص�"  sortable="true"/>

</display:table>

</body>
</html>

