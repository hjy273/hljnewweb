<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<br>
<script type="text/javascript">

//old js invoke method: saveRemark('${param.ruleId}',$('remark').value,'${param.tableId}','${param.appraiseType}',$('markDeduction').value);
//now is: saveRemark('${param.ruleId}',document.getElementById('remark').value,'${param.tableId}','${param.appraiseType}',document.getElementById('markDeduction').value)
	function test(){
		alert("ruleid:"+${param.ruleId});
		alert("remark:"+document.getElementById("remark").value);
		alert("tableId:"+${param.tableId});
		//alert(${param.appraiseType});
		alert($('markDeduction').value);
	}
</script>
<body background="#FFFFFF">
<c:if test="${param.appraiseType=='YearEnd'&&param.flag=='1'}">
得分：<input type="text" name="markDeduction" id="markDeduction" value="0"><br />
</c:if>
<c:if test="${param.appraiseType!='YearEnd'||param.flag=='2'}">
	<input type="hidden" name="markDeduction" id="markDeduction" value=""/>
</c:if>
扣分说明：
	<textarea rows="5" cols="59" id="remark" name="remark"> ${param.remark}</textarea>
<div align="center">
<input name="btnClose" value="保存" class="button" type="button" onclick="saveRemark('${param.ruleId}',document.getElementById('remark').value,'${param.tableId}','${param.appraiseType}',document.getElementById('markDeduction').value)" />
<c:if test="${param.remark!=''}">
	<input name="btnClose" value="关闭" class="button" type="button" onclick="closeRemark();" />
</c:if>
</div>
</body>
