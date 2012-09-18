<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title></title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
		<script type="text/javascript" language="javascript">
			function view(id){
				var url = "${ctx}/project/remedy_apply.do?method=view&&apply_id="+id;
				window.location.href = url;
			}
	//	//取消割接流程
	//	toCancelForm=function(applyId){
	//		var url;
	//		if(confirm("确定要取消该工程申请流程吗？")){
	//			url="${ctx}/project/remedy_apply.do?method=cancelRemedyForm";
	//			var queryString="apply_id="+applyId;
	//			//location=url+"&"+queryString+"&rnd="+Math.random();
	//			window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
	//		}
	//	}
		</script>
	</head>
	<body>
		<template:titile value="工程列表" />
		<logic:notEmpty name="result">
		<display:table name="sessionScope.result" id="row" pagesize="18">
			<bean:define id="sendUserId" name="row" property="creator" />
			<bean:define id="applyState" name="row" property="state" />
			<display:column property="remedyCode" sortable="true" title="编号" />
			<display:column property="projectName" sortable="true" title="项目名称"  />
			<display:column property="remedyDate" sortable="true" title="申请时间"  format="{0,date,yyyy-MM-dd}"/>
			<display:column property="totalFee" title="定额项合计金额" sortable="true"/>
			<display:column property="mtotalFee" title="使用材料合计金额" sortable="true" />
			<display:column media="html" title="操作">
				<a href="javascript:view('${row.id}')">查看</a>
			<!-- 	<c:if test="${sessionScope.LOGIN_USER.userID==sendUserId&&applyState!='999'}">
					| <a href="javascript:toCancelForm('${row.id}')">取消</a>
				</c:if>
				 -->
			</display:column>
		</display:table>
		</logic:notEmpty>
		<table border="0" cellpadding="0" cellspacing="0" width="100%"
			style="border: 0px">
			<tr>
				<td>
					<a href="#"></a>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">
				</td>
			</tr>
		</table>
	</body>
</html>