<%@ page contentType="text/html; charset=GBK" %>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<html>
<head>
	<title></title>
	
	<script type="text/javascript">
		function allStat(id){
			var url = '${ctx}/specialplanstat.do?method=viewPlansStat&id='+id;
			document.getElementById('fm').src = url;
		}
		function viewPlanListStat(id){
			var url = '${ctx}/specialplanstat.do?method=viewPlanListStat&id='+id;
			document.getElementById('fm').src = url;
		}
	</script>
</head>
<body> 
	<table border="0">
	    <tr>
	    	<td>
			    <input type="button" onclick="allStat('<%=request.getParameter("id")%>')" value="总体信息"/>
			    <input type="button" onclick="viewPlanListStat('<%=request.getParameter("id")%>')" value="计划统计列表"/>
			    <input type="button" onclick="history.back()" value="返回"/>
			</td>
	    </tr>
    </table>
    <table id="tb" border="0" cellspacing="0" cellpadding="0" height="95%" width="100%">
	    <tr>
	    	<td>
            	<iframe class="" id="fm" marginWidth="0" marginHeight="0" src="${ctx}/specialplanstat.do?method=viewPlansStat&id=<%=request.getParameter("id")%>" frameBorder=0 width="100%" scrolling=auto height="100%" />
	      	</td>
	    </tr>
	</table>
</body>
</html>