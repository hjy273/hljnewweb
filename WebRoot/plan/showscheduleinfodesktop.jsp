<%@include file="/common/header.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<html>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
		media="screen, print" />
	<head>
		<title>Ѳ��ƻ�������Ϣ��</title>
			<script type="text/javascript" language="javascript">
    function addGoBack()
        {
            try
			{
                location.href = "${ctx}/TowerPatrolinfo.do?method=showPlanInfo";
                return true;
            }
            catch(e){
                alert(e);
            }
        }
 	function loadPatrolPlanDetail(id){
 		var url = "${ctx}/TowerPatrolinfo.do?method=showOnePlanInfo&planid="+id;
    	window.location.href=url;
 	}
	function loadPatrolEndDetail(id){
		var url = "${ctx}/TowerPatrolinfo.do?method=loadPatrolEndDetail&planid="+id;
		window.location.href=url;
	}
	function loadLosePatrolDetail(id){
		var url = "${ctx}/TowerPatrolinfo.do?method=loadLosePatrolDetail&planid="+id;
		window.location.href=url;
	}
</script>
	</head>
	<body>
		<display:table name="sessionScope.QUERYREUSLT" id="currentRowObject"
			pagesize="10">

			<display:column media="html" title="�ƻ�����" sortable="true">
				<%
					DynaBean bean = (DynaBean) pageContext
							.findAttribute("currentRowObject");
					String id = bean.get("id").toString();
					String planname = bean.get("plan_name").toString();
				%>
				<a style="text-decoration: underline; color: blue;"
					href="javascript:;"
					onclick="loadPatrolPlanDetail('<%=id%>');return false;"><%=planname%></a>
			</display:column>
			<display:column property="regionname" title="Ѳ������" maxLength="18" />
			<display:column title="��ά��λ" property="contractorname" />
			<display:column property="planstate" title="Ѳ�������" />
		</display:table>
	</body>
</html>