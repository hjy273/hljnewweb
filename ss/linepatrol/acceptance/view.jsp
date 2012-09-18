<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
	
	<script type="text/javascript">
		var win;
		function showWin(url){
			win = new Ext.Window({
				layout : 'fit',
				width:700,
				height:400,
				resizable:true,
				closeAction : 'close',
				modal:true,
				autoScroll:true,
				autoLoad:{url:url, scripts:true},
				plain: true
			});
			win.show(Ext.getBody());
		}
		function closeProcessWin(){
			win.close();
		}
		function his(id){
			var url = "${ctx}/process_history.do?method=showProcessHistoryList&object_type=inspection&is_close=0&object_id="+id;
			showWin(url);
		}
		function excel(id){
			window.location.href = '${ctx}/acceptanceAction.do?method=excel&type=nosession&id='+id;
		}
	</script>
</head>
<body>
	<template:titile value="验收交维" />
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
		<template:formTr name="备注">
				${apply.remark}
		</template:formTr>
			<logic:notEmpty property="cancelUserId" name="apply">
				<tr class=trcolor>
					<td class="tdr">
						取消人：
					</td>
					<td class="tdl">
						<bean:write property="cancelUserName" name="apply" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						取消时间：
					</td>
					<td class="tdl">
						<bean:write property="cancelTimeDis" name="apply" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						取消原因：
					</td>
					<td class="tdl">
						<bean:write property="cancelReason" name="apply" />
					</td>
				</tr>
			</logic:notEmpty>
		<template:formTr name="验收资源总数">
			${apply.resourceNumber}
		</template:formTr>
		<template:formTr name="资源已验收数">
			${apply.recordNumber} （资源通过验收数 ：${apply.passedNumber}，资源未通过验收数 ：${apply.notPassedNumber}）
		</template:formTr>
		<template:formTr name="资源待验收数">
			${apply.notRecordNumber}
		</template:formTr>
		<template:formTr name="分配代维列表">
			${apply.contractorNames}
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
	<apptag:appraiseDailyDaily businessId="${apply.subflowId}" contractorId="${apply.contractorId}" businessModule="acceptance" displayType="view" tableStyle="border-top: 0px; border-bottom: 0px;"></apptag:appraiseDailyDaily>
	<apptag:appraiseDailySpecial businessId="${apply.subflowId}" contractorId="${apply.contractorId}" businessModule="acceptance" displayType="view" tableStyle="border-top: 0px; border-bottom: 0px;"></apptag:appraiseDailySpecial>
	<table class="tdlist">
		<tr>
			<td class="tdlist">
				<c:if test="${iswin == 'finished'}">
					<input type="button" class="button" value="流程历史" onclick="his('${apply.id}')" />
				</c:if>
				<c:if test="${apply.resourceType eq '1' && apply.recordNumber != '0'}">
					<input type="button" class="button" value="导出" onclick="excel('${apply.id}')"/>
				</c:if>
				<input type="button" class="button" value="返回" onclick="history.back()" />
			</td>
		</tr>
	</table>
</body>
</html>