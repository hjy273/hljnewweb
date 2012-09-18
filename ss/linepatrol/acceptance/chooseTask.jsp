<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	
	<title></title>
	<script type="text/javascript">
		function recordData(id, objectId, type){
			window.location.href = '${ctx}/acceptanceAction.do?method=recordData&id='+id+'&objectId='+objectId+'&type='+type;
		}
		function editData(id, objectId, type){
			window.location.href = '${ctx}/acceptanceAction.do?method=editData&id='+id+'&objectId='+objectId+'&type='+type;
		}
		function check(){
			if($('approver').value == ''){
				alert('核准人不能为空');
				return false;
			}
			return true;
		}
		function back(){
			window.location.href='${ctx}/acceptanceAction.do?method=findToDoWork';
		}
		function exportExcel(){
			window.location.href = '${ctx}/acceptanceAction.do?method=export';
		}
	</script>
</head>
<body>
	<template:titile value="验收交维数据录入" />
	<html:form action="/acceptanceAction.do?method=push" styleId="form" onsubmit="return check()">
		<input type="hidden" name="id" value="${apply.id}">
		<template:formTable namewidth="200" contentwidth="400">
			<template:formTr name="项目经理">
				<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="${apply.applicant}" />
			</template:formTr>
			<template:formTr name="工程名称">
				${apply.name}
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
			<template:formTr name="导出数据">
				<input type="button" class="button" value="导出" onclick="exportExcel();" />
			</template:formTr>	
			<tr class=trcolor>
				<apptag:approve tableClass="tdlist" labelClass="tdulleft" displayType="view" objectId="${apply.subflowId}" objectType="LP_ACCEPTANCE_APPROVE" />
			</tr>
		</template:formTable>
		<c:choose>
			<c:when test="${apply.resourceType eq '1'}">
				<display:table name="sessionScope.apply.cables" id="row"  pagesize="8" export="false" defaultsort="2" defaultorder="ascending">
					<display:column property="cableNo" title="光缆编号"/>
					<display:column property="trunk" title="光缆中继段"/>
					<display:column property="fibercoreNo" title="纤芯数"/>
					<display:column media="html" title="光缆级别">
						<apptag:quickLoadList style="width:130px" keyValue="${row.cableLevel}" cssClass="select" name="cableLevel" listName="cabletype" type="look" />
					</display:column>
					<display:column media="html" title="光缆长度">
						${row.cableLength}千米
					</display:column>
					<display:column media="html" title="操作">
						<c:choose>
							<c:when test="${row.isrecord eq '0'}">
								<a href="javascript:recordData('${apply.subflowId}','${row.id}','${type}')">录入数据</a>
							</c:when>
							<c:otherwise>
								<a href="javascript:editData('${apply.subflowId}','${row.id}','${type}')">修改数据</a>
							</c:otherwise>
						</c:choose>
					</display:column>
				</display:table>
			</c:when>
			<c:otherwise>
				<display:table name="sessionScope.apply.pipes" id="row"  pagesize="8" export="false" defaultsort="1" defaultorder="ascending">
					<display:column property="projectName" title="工程名称"/>
					<display:column property="pipeAddress" title="管道地点"/>
					<display:column property="pipeRoute" title="详细路由"/>
					<display:column property="builder" title="施工单位"/>
					<display:column media="html" title="产权属性">
						<apptag:quickLoadList style="width:130px" keyValue="${row.pipeProperty}" cssClass="select" name="pipeProperty" listName="property_right" type="look" />
					</display:column>
					<display:column media="html" title="管道属性">
						<apptag:quickLoadList style="width:130px" keyValue="${row.pipeType}" cssClass="select" name="pipeType" listName="pipe_type" type="look" />
					</display:column>
					<display:column media="html" title="操作">
						<c:choose>
							<c:when test="${row.isrecord eq '0'}">
								<a href="javascript:recordData('${apply.subflowId}','${row.id}','${type}')">录入数据</a>
							</c:when>
							<c:otherwise>
								<a href="javascript:editData('${apply.subflowId}','${row.id}','${type}')">修改数据</a>
							</c:otherwise>
						</c:choose>
					</display:column>
				</display:table>
			</c:otherwise>
		</c:choose>
		<apptag:approverselect label="核准人" inputName="approver" 
		 approverType="approver" objectType="LP_ACCEPTANCE_APPLY" objectId="${apply.id}" 
		 spanId="approverSpan" inputType="radio"/>
		<table class="tdlist">
			<tr><td class="tdlist">
				<input type="submit" value="提交" id="submit" class="button" />
				<input type="button" value="返回" class="button" onclick="back();"/>
			</td></tr>
		</table>
	</html:form>
</body>