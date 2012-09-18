<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script type="text/javascript">
			function addData(){
				var comIds=document.getElementById('comIds').value;
				var companyNames=document.getElementById('companyNames').value;
				parent.$('comIds').value = comIds;
				parent.$('companyInfo').innerHTML = companyNames;
				parent.win.close();
			}
		</script>
	</head>
	<body onload="addData()">
		<input type="hidden" id="comIds" value="${comIds}">
		<input type="hidden" id="companyNames" value="${companyNames}">
	</body>
</html>