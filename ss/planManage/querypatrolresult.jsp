<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
	<link rel="stylesheet" href="${ctx}/css/display_style.css" type="text/css"
	media="screen, print" />
<script language="javascript" type="text/javascript">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/batchPlan.do?method=delBatchPlan&id=" + idValue;
        self.location.replace(url);
    }
}
</script>
<body>
<%Date nowDate = new Date(); %>
	<template:titile value="��ѯ�ƻ���Ϣ���" />
	<display:table name="sessionScope.batch" id="currentRowObject" pagesize="18">
		<display:column property="batchname" title="�����ƻ�����" sortable="true" />
		<display:column property="startdate" title="��ʼ����" sortable="true" />
		<display:column property="enddate" title="��������" sortable="true" />
		<apptag:checkpower thirdmould="21605" ishead="0">
			<display:column media="html" title="����">
				<%
					DateUtil util = new DateUtil();
					BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
					String id = (String) object.get("id");
					String startdate = (String) object.get("startdate");
					String enddate = (String) object.get("enddate");
					Date sdate = util.parseDate(startdate);
					Date edate = util.parseDate(enddate);
					if(sdate.before(nowDate) && edate.after(nowDate)){
						out.print("--");
					}else if(sdate.before(nowDate) && edate.before(nowDate)){
						out.print("--");
					}else{
					
				%>
				<a
					href="javascript:toDelete('<%=id%>')">ɾ��</a>
			    <%} %>
			</display:column>
		</apptag:checkpower>

	</display:table>
	
</body>
