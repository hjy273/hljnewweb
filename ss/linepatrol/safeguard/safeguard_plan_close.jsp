<%@include file="/common/header.jsp"%>
<html>
<head>
</head>
<script type="text/javascript">
	function loadData(){
		var businessId=document.getElementById("businessId").value;
		addSpPlan(businessId);
		parent.close();
	}
	function addSpPlan(businessId){
		//alert(spid);
		var spplanValue = parent.document.getElementById("spplanValue");
		//alert(spplanValue.id);
		var url = 'safeguardPlanAction.do?method=loadDataSpPlan&businessId='+businessId;
		new Ajax.Request(url,{
	       	method:'post',
	       	evalScripts:true,asynchronous:false,
	       	onSuccess:function(transport){
	       		//alert(transport.responseText);
	       		//var span = parent.document.createElement("div");
	       		//span.id=spid;
	       		spplanValue.innerHTML=transport.responseText;
				//spplanValue.appendChild(span);
				//alert(transport.responseText+"     "+span);
	       	},
	       	onFailure: function(){ alert('请求服务异常......') }
	 	});
	}
</script>
<body onload="loadData()">
	<input type="hidden" value="${businessId }" id="businessId">
</body>
</html>
