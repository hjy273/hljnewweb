<%@ page contentType="text/html; charset=GBK" %>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<html>
<head>
	<title></title>
	
	<script type="text/javascript">
		function allStat(id){
			var url = '${ctx}/specialplanstat.do?method=viewPlanStat&id='+id;
			document.getElementById('fm').src = url;
		}
		function viewSublineStat(id){
			var url = '${ctx}/specialplanstat.do?method=viewSublineStat&id='+id;
			document.getElementById('fm').src = url;
		}
		function viewAreaStat(id){
			var url = '${ctx}/specialplanstat.do?method=viewAreaStat&id='+id;
			document.getElementById('fm').src = url;
		}
		function viewPatrolDetail(id){
			var url = '${ctx}/specialplanstat.do?method=viewPatrolDetail&id='+id;
			document.getElementById('fm').src = url;
		}
		function viewPlan(id, type){
			var url = '${ctx}/specialplan.do?method=loadPlan&type=view&ptype='+type+'&isApply=false&id='+id;
			document.getElementById('fm').src = url;
		}
		function viewLeakDetail(id){
			var url = '${ctx}/specialplanstat.do?method=viewLeakDetail&id='+id;
			document.getElementById('fm').src = url;
		}
		function viewValidWatch(id){
			var url = '${ctx}/specialplanstat.do?method=viewValidWatch&id='+id;
			document.getElementById('fm').src = url;
		}
		function viewInvalidWatch(id){
			var url = '${ctx}/specialplanstat.do?method=viewInvalidWatch&id='+id;
			document.getElementById('fm').src = url;
		}
	</script>
</head>
<body> 
	<table border="0">
	    <tr>
	    	<td>
			    <input type="button" onclick="allStat('<%=request.getParameter("id")%>')" value="������Ϣ"/>
			    <input type="button" onclick="viewPlan('<%=request.getParameter("id")%>','<%=request.getParameter("type")%>')" value="�ƻ���Ϣ"/>
			    <input type="button" onclick="viewSublineStat('<%=request.getParameter("id")%>')" value="Ѳ���߶�"/>
			    <input type="button" onclick="viewPatrolDetail('<%=request.getParameter("id")%>')" value="Ѳ����ϸ"/>
			    <input type="button" onclick="viewLeakDetail('<%=request.getParameter("id")%>')" value="©����ϸ"/>
			    <input type="button" onclick="viewAreaStat('<%=request.getParameter("id")%>')" value="��������"/>
			    <input type="button" onclick="viewValidWatch('<%=request.getParameter("id")%>')" value="��Ч����"/>
			    <input type="button" onclick="viewInvalidWatch('<%=request.getParameter("id")%>')" value="��Ч����"/>
			    <input type="button" onclick="history.back()" value="����"/>
			</td>
	    </tr>
    </table>
    <table id="tb" border="0" cellspacing="0" cellpadding="0" height="95%" width="100%">
	    <tr>
	    	<td>
            	<iframe class="" id="fm" marginWidth="0" marginHeight="0" src="${ctx}/specialplanstat.do?method=viewPlanStat&id=<%=request.getParameter("id")%>" frameBorder=0 width="100%" scrolling=auto height="100%" />
	      	</td>
	    </tr>
	</table>
</body>
</html>