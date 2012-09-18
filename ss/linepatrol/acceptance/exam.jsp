<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
	<script type="text/javascript">
		function check(){
			if(!checkAppraiseDailyValid()){
  				return false;
  			}
  			exam.submit();
		}
		
		function showOrHiddeInfo(){
			var infoDiv = document.getElementById("applyinfo");
			if(infoDiv.style.display=="block"){
				infoDiv.style.display="none";
			}else{
				infoDiv.style.display="block";
			}
		}
	</script>
</head>
<body>
	<template:titile value="验收交维考核评估" />
	<html:form action="/acceptanceExamAction.do?method=exam" styleId="exam">
		<input type="hidden" name="id" value="${apply.subflowId}">
		<div id="applyinfo" style="display: none;">
			<template:formTable namewidth="200" contentwidth="400">
				<template:formTr name="项目经理">
					<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="${apply.applicant}" />
				</template:formTr>
				<template:formTr name="工程名称">
					${apply.name}
				</template:formTr>
				<template:formTr name="验收交维编号">
					${apply.code}
				</template:formTr>
				<template:formTr name="申请时间">
					<bean:write name="apply" property="applyDate" format="yyyy/MM/dd"/>
				</template:formTr>
				<template:formTr name="验收资源类型">
					<c:choose>
						<c:when test="${apply.resourceType eq '1'}">
							光缆
						</c:when>
						<c:otherwise>
							管道
						</c:otherwise>
					</c:choose>
				</template:formTr>
				</template:formTable>
				<template:formTable namewidth="200" contentwidth="400">
				<tr>
					<td colspan=2>
						<c:choose>
							<c:when test="${apply.resourceType eq '1'}">
								<jsp:include page="cableList_inc.jsp" />
							</c:when>
							<c:otherwise>
								<jsp:include page="pipeList_inc.jsp" />
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</template:formTable>
		</div>
		<div align="right" style="height: 40px; line-height: 40px;"><a onclick="showOrHiddeInfo();" style="cursor: pointer;">显示/隐藏</a></div>
		
		
	</html:form>
	<apptag:appraiseDailyDaily contractorId="${apply.contractorId }" businessId="${apply.subflowId}" businessModule="acceptance" 
							displayType="input" tableClass="tabout" tableStyle="width:100%;"></apptag:appraiseDailyDaily>
	<apptag:appraiseDailySpecial contractorId="${apply.contractorId }" businessId="${apply.subflowId}" businessModule="acceptance" 
							displayType="input" tableClass="tabout" tableStyle="width:100%;"></apptag:appraiseDailySpecial>
	<table class="tdlist">
			<tr>
				<td class="tdlist" colspan=2>
					<html:button property="action" styleClass="button" styleId="subbtn"
							onclick="check()">提交</html:button>
					<input type="button" value="返回" class="button" onclick="history.back()" />
				</td>
			</tr>
		</table>
</body>
</html>