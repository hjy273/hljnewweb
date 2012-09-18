<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title></title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
		<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>

		<script type="text/javascript">
		function check(){
			if( ($('begintime').value == '' && $('endtime').value != '') ||
				($('begintime').value != '' && $('endtime').value == '') ){
				alert("日期段必须同时有开始日期和结束日期");
				return false;
			}
			if( ($('begintime').value != '' && $('endtime').value != '')
					&& ($('begintime').value == $('endtime').value) ){
				alert('开始日期和结束日期不能相等');
				return false;
			}
			return true;
		}
		//取消流程
		toCancelForm=function(applyId){
			var url;
			if(confirm("确定要取消该验收交维流程吗？")){
				url="${ctx}/acceptanceAction.do?method=cancelAcceptanceForm";
				var queryString="apply_id="+applyId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			};
		};
	</script>
	</head>
	<body onload="changeStyle()">
		<template:titile value="验收交维查询" />
		<div style="text-align:center;">
			<iframe
				src="${ctx}/acceptanceAction.do?method=viewAcceptanceProcess&&task_name=${task_names }&&forward=query_acceptance_state"
				frameborder="0" id="processGraphFrame" height="100" scrolling="no"
				width="95%"></iframe>
		</div>
		<!-- 查询页面 -->
		<html:form action="/acceptanceQueryAction.do?method=query"
			styleId="form" onsubmit="return check()">
			<template:formTable namewidth="150" contentwidth="400">
				<template:formTr name="申请名称">
					<html:text property="name" styleClass="inputtext required"
						style="width:120px" />
				</template:formTr>
				<template:formTr name="代维单位">
					<c:if test="${deptype=='1'}">
						<html:select property="contractorId" styleId="contractorId" styleClass="inputtext" style="width:140px">
							<html:option value="">全部</html:option>
							<html:options collection="list" property="contractorID" labelProperty="contractorName"></html:options>
						</html:select>
					</c:if>
					<c:if test="${deptype=='2'}">
						<input type="hidden" value="${sessionScope.conId }" name="contractorId" />
						<c:out value="${sessionScope.conName}"></c:out>
					</c:if>
				</template:formTr>
				<template:formTr name="是否取消">
					<html:select property="processState" styleClass="inputtext" style="width:140px">
						<html:option value="">不限</html:option>
						<html:option value="999">是</html:option>
						<html:option value="0">否</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="验收资源类型">
					<html:radio property="resourceType" value="" />全部
			    <html:radio property="resourceType" value="1" />光缆
			    <html:radio property="resourceType" value="2" />管道
			</template:formTr>
				<template:formTr name="验收交维状态">
					<html:multibox property="acceptanceState" value="10" />需要核准申请
				<html:multibox property="acceptanceState" value="20" />需要认领任务
				<html:multibox property="acceptanceState" value="30" />需要核准任务
				<html:multibox property="acceptanceState" value="40" />需要录入数据
				<html:multibox property="acceptanceState" value="00" />完成
			</template:formTr>
				<template:formTr name="申请时间">
					<html:text property="begintime" styleId="begintime" styleClass="Wdate"
						style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})" /> 至
				<html:text property="endtime" styleId="endtime" styleClass="Wdate" style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'begintime\')}'})" />
				</template:formTr>
			</template:formTable>
			<logic:present name="queryCondition" property="taskStates">
				<logic:iterate id="oneTaskState" name="queryCondition"
					property="taskStates">
					<input id="taskStates_<bean:write name="oneTaskState" />"
						type="hidden" name="taskStates"
						value="<bean:write name="oneTaskState" />" />
				</logic:iterate>
			</logic:present>
			<div align="center">
				<input name="isQuery" value="true" type="hidden">
				<html:submit property="action" styleClass="button">查 询</html:submit>
			</div>
		</html:form>
		<template:displayHide styleId="form"></template:displayHide>
		
		<script type="text/javascript">
		function view(id){
			window.location.href = '${ctx}/acceptanceQueryAction.do?method=view&id='+id;
		}
		</script>
		<logic:notEmpty name="result">
			<!-- 查询结果 -->
			<display:table name="sessionScope.result" id="row" pagesize="18" export="false">
				<bean:define id="sendUserId" name="row" property="applicant" />
				<bean:define id="applyState" name="row" property="processState"></bean:define>
				<display:column property="name" title="名称" />
				<display:column property="code" title="验收交维编号" />
				<display:column media="html" title="项目经理">
					<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="${row.applicant}" />
				</display:column>
				<display:column media="html" title="申请时间">
					<bean:write name="row" property="applyDate" format="yyyy/MM/dd" />
				</display:column>
				<display:column media="html" title="验收资源类型">
					<c:choose>
						<c:when test="${row.resourceType eq '1'}">光缆</c:when>
						<c:otherwise>管道</c:otherwise>
					</c:choose>
				</display:column>
				<display:column media="csv excel xml html" title="状态">
					<c:if test="${row.processState eq '10'}">需要核准申请</c:if>
					<c:if test="${row.processState eq '20'}">需要认领任务</c:if>
					<c:if test="${row.processState eq '30'}">需要核准任务</c:if>
					<c:if test="${row.processState eq '40'}">需要录入数据</c:if>
					<c:if test="${row.processState eq '46'}">需要复验核准</c:if>
					<c:if test="${row.processState eq '00'}">已完成</c:if>
					<c:if test="${row.processState eq '999'}">已取消任务</c:if>
				</display:column>
				<display:column media="html" title="操作">
					<a href="javascript:view('${row.id}')">查看</a>
					<c:if test="${sessionScope.LOGIN_USER.deptype=='1' && applyState=='10'}">
						<a href="javascript:toCancelForm('${row.id }')">取消</a>
					</c:if>
				</display:column>
			</display:table>
			<table class="tdlist">
				<tr>
					<td class="tdlist">
						<input type="button" class="button" value="返回"
							onclick="history.back()" />
					</td>
				</tr>
			</table>
		</logic:notEmpty>
	</body>
</html>