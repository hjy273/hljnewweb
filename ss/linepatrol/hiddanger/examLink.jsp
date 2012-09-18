<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title></title>
		<link href="${ctx}/css/screen.css" rel="stylesheet" type="text/css">
		<script type='text/javascript'
			src='${ctx}/linepatrol/js/change_style.js'></script>
		<script type='text/javascript'
			src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<link rel='stylesheet' type='text/css'
			href='${ctx}/js/extjs/resources/css/ext-all.css' />

		<script type="text/javascript">
		function check(){
			if(!checkAppraiseDailyValid()){
  				return false;
  			}
			//processBar(form);
			$('subbtn').disabled=true; 
  			hiddangerExam.submit();
		}
		function showMap(){
			var url = '/WEBGIS/gisextend/igis.jsp?userid=${LOGIN_USER.userID}&actiontype=205&x=${registBean.x}&y=${registBean.y}&label=${registBean.name}';
			window.open(url,'map','width=800,height=600,scrollbars=yes,resizable=yes,toolbar=no,menubar=no');
		}
		function viewPlan(id){
			window.location.href = '${ctx}/specialplan.do?method=loadPlan&type=view&ptype=001&isApply=false&id='+id;
		}
		function viewPlanStat(id){
			window.location.href = '${ctx}/linepatrol/specialplan/stat/stat.jsp?type=001&id='+id;
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
	<body onload="changeStyle()">
		<template:titile value="考核评估" />
		<html:form action="/hiddangerExamAction.do?method=exam"
			styleId="hiddangerExam">
			<div id="applyinfo" style="display: none;">
				<template:formTable namewidth="200" contentwidth="500">
					<input type="hidden" name="id" value="${registBean.id}">
					<template:formTr name="隐患编号">
						<bean:write name="registBean" property="hiddangerNumber" />
					</template:formTr>
					<template:formTr name="登记单位">
						<bean:write name="registBean" property="createrDept" />
					</template:formTr>
					<template:formTr name="登记人">
						<apptag:assorciateAttr table="userinfo" label="username"
							key="userid" keyValue="${registBean.creater}" />
					</template:formTr>
					<template:formTr name="隐患位置">
						<bean:write name="registBean" property="x" />,<bean:write
							name="registBean" property="y" />
						<input type="button" onclick="showMap();" value="查看位置" />
					</template:formTr>
					<template:formTr name="隐患发现时间">
						<bean:write name="registBean" property="findTime" />
					</template:formTr>
					<template:formTr name="隐患发现方式">
						<bean:write name="registBean" property="findType" />
					</template:formTr>
					<template:formTr name="隐患上报时间">
						<bean:write name="registBean" property="createTime" />
					</template:formTr>
					<template:formTr name="发现隐患来源">
						<bean:write name="registBean" property="reporter" />
					</template:formTr>
					<template:formTr name="隐患处理单位">
						<apptag:assorciateAttr table="contractorinfo"
							label="contractorname" key="contractorid"
							keyValue="${registBean.treatDepartment}" />
					</template:formTr>
					<template:formTr name="隐患分类">
						<apptag:assorciateAttr table="troubletype" label="typename"
							key="id" keyValue="${registBean.type}" />
					</template:formTr>
					<template:formTr name="隐患名称">
						<apptag:assorciateAttr table="troublecode" label="troublename"
							key="id" keyValue="${registBean.code}" />
					</template:formTr>
					<c:if test="${!empty registBean.hiddangerLevel}">
						<template:formTr name="隐患等级">
							<bean:write name="registBean" property="hiddangerLevel" />级
				</template:formTr>
						<template:formTr name="隐患评级备注">
							<bean:write name="registBean" property="remark" />
						</template:formTr>
					</c:if>
					<c:if test="${!empty treatBean}">
						<template:formTr name="隐患地点描述">
							<bean:write name="treatBean" property="address" />
						</template:formTr>
						<template:formTr name="隐患点距光缆距离">
							<bean:write name="treatBean" property="distanceToCable" />
						</template:formTr>
						<template:formTr name="隐患盯防负责人">
							<bean:write name="treatBean" property="watchPrincipal" />
						</template:formTr>
						<template:formTr name="隐患盯防负责人电话">
							<bean:write name="treatBean" property="watchPrincipalPhone" />
						</template:formTr>
						<template:formTr name="隐患盯防实施人">
							<bean:write name="treatBean" property="watchActualizeMan" />
						</template:formTr>
						<template:formTr name="隐患盯防实施人电话">
							<bean:write name="treatBean" property="watchActualizeManPhone" />
						</template:formTr>
						<template:formTr name="隐患盯防解除时间">
							<bean:write name="treatBean" property="hiddangerRemoveTime" />
						</template:formTr>
						<template:formTr name="隐患消除时间">
							<bean:write name="treatBean" property="watchReliefTime" />
						</template:formTr>
						<template:formTr name="受影响光缆段">
							<apptag:trunk id="trunk" state="view"
								value="${treatBean.trunkIdsString}" />
						</template:formTr>
						<template:formTr name="受影响路由长度">
							<bean:write name="treatBean" property="impressLength" />
						</template:formTr>
						<template:formTr name="其他受影响光缆段">
							<bean:write name="treatBean" property="otherImpressCable" />
						</template:formTr>
						<template:formTr name="隐患盯防原因">
							<bean:write name="treatBean" property="watchReason" />
						</template:formTr>
						<template:formTr name="隐患盯防措施">
							<bean:write name="treatBean" property="watchMeasure" />
						</template:formTr>
						<template:formTr name="隐患盯防解除原因">
							<bean:write name="treatBean" property="watchReliefReason" />
						</template:formTr>
						<template:formTr name="备注">
							<bean:write name="treatBean" property="treatremark" />
						</template:formTr>
						<template:formTr name="附件">
							<apptag:upload state="look" cssClass=""
								entityId="${registBean.id}" entityType="LP_HIDDANGER_TREAT" />
						</template:formTr>
					</c:if>
					<c:if test="${!empty reportBean}">
						<template:formTr name="隐患地点描述">
							<bean:write name="reportBean" property="address" />
						</template:formTr>
						<template:formTr name="隐患点距光缆距离">
							<bean:write name="reportBean" property="distanceToCable" />米
			</template:formTr>
						<template:formTr name="隐患盯防负责人">
							<bean:write name="reportBean" property="watchPrincipal" />
						</template:formTr>
						<template:formTr name="隐患盯防负责人电话">
							<bean:write name="reportBean" property="watchPrincipalPhone" />
						</template:formTr>
						<template:formTr name="隐患盯防实施人">
							<bean:write name="reportBean" property="watchActualizeMan" />
						</template:formTr>
						<template:formTr name="隐患盯防实施人电话">
							<bean:write name="reportBean" property="watchActualizeManPhone" />
						</template:formTr>
						<template:formTr name="施工负责人">
							<bean:write name="reportBean" property="workPrincipal" />
						</template:formTr>
						<template:formTr name="施工负责人电话">
							<bean:write name="reportBean" property="workPrincipalPhone" />
						</template:formTr>
						<template:formTr name="施工单位">
							<bean:write name="reportBean" property="workDepart" />
						</template:formTr>
						<template:formTr name="施工阶段">
							<apptag:quickLoadList cssClass="input" style="width:200"
								name="workStage" listName="workstage" type="look"
								keyValue="${reportBean.workStage}" />
						</template:formTr>
						<template:formTr name="计划隐患消除时间">
							<bean:write name="reportBean" property="planReliefTime" />
						</template:formTr>
						<template:formTr name="盯防介入时间">
							<bean:write name="reportBean" property="watchBeginTime" />
						</template:formTr>
						<template:formTr name="是否签署安全协议">
							<c:choose>
								<c:when test="${reportBean.signSecurityProtocol eq '1'}">
						是
					</c:when>
								<c:otherwise>
						否
					</c:otherwise>
							</c:choose>
						</template:formTr>
						<template:formTr name="受影响光缆段">
							<apptag:trunk id="trunk" state="view"
								value="${reportBean.trunkIdsString}" />
						</template:formTr>
						<template:formTr name="其他受影响光缆段">
							<bean:write name="reportBean" property="otherImpressCable" />
						</template:formTr>
						<template:formTr name="隐患盯防原因">
							<bean:write name="reportBean" property="watchReason" />
						</template:formTr>
						<template:formTr name="隐患盯防措施">
							<bean:write name="reportBean" property="watchMeasure" />
						</template:formTr>
						<template:formTr name="备注">
							<bean:write name="reportBean" property="reportRemark" />
						</template:formTr>
						<template:formTr name="附件">
							<apptag:upload state="look" cssClass=""
								entityId="${registBean.id}" entityType="LP_HIDDANGER_REPORT" />
						</template:formTr>
					</c:if>
					<c:if test="${!empty closeBean}">
						<template:formTr name="隐患解除时间">
							<bean:write name="closeBean" property="reliefTime" />
						</template:formTr>
						<template:formTr name="隐患解除原因">
							<bean:write name="closeBean" property="reliefReason" />
						</template:formTr>
						<template:formTr name="备注">
							<bean:write name="closeBean" property="remark" />
						</template:formTr>
						<template:formTr name="盯防计划">
							<jsp:include page="statIframe.jsp" />
						</template:formTr>
					</c:if>
				</template:formTable>
			</div>
			<div align="right"
				style="height: 30px; line-height: 30px; width: 700px; margin: 0px auto;">
				<a onclick="showOrHiddeInfo();" style="cursor: pointer;">显示/隐藏</a>
			</div>
		</html:form>
		<apptag:appraiseDailyDaily businessId="${registBean.id}"
			contractorId="${registBean.treatDepartment}" tableClass=""
			businessModule="hiddanger" tableStyle="width:700px;border:0px;"></apptag:appraiseDailyDaily>
		<apptag:appraiseDailySpecial businessId="${registBean.id}"
			contractorId="${registBean.treatDepartment}" tableClass=""
			businessModule="hiddanger" tableStyle="width:700px;border:0px;"></apptag:appraiseDailySpecial>
		<table width="80%" border="0" align="center" cellpadding="3"
			cellspacing="0">
			<tr align="center">
				<td colspan="4">
					<html:button property="action" styleClass="button" styleId="subbtn"
						onclick="check()">提交</html:button>
					<input type="button" value="返回" class="button"
						onclick="javascript:history.back();" />
				</td>
			</tr>
		</table>
	</body>