<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
	
	<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
	
	<script type="text/javascript">
		var win;
		function showWin(url){
			win = new Ext.Window({
				layout : 'fit',
				width:600,
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
		function showMap(){
			var url = '/WEBGIS/gisextend/igis.jsp?userid=${LOGIN_USER.userID}&actiontype=205&x=${registBean.x}&y=${registBean.y}&label=${registBean.name}';
			window.open(url,'map','width=800,height=600,scrollbars=yes,resizable=yes,toolbar=no,menubar=no');
		}
		function viewPlan(id){
			window.location.href = '${ctx}/specialplan.do?method=loadPlan&type=view&ptype=001&isApply=false&id='+id;
		}
		function his(id){
			var url = "${ctx}/process_history.do?method=showProcessHistoryList&object_type=hiddtroubleWatch&is_close=0&object_id="+id;
			showWin(url);
		}
		function viewPlanStat(id){
			window.location.href = '${ctx}/linepatrol/specialplan/stat/stat.jsp?type=001&id='+id;
		}
	</script>
</head>
<body onload="changeStyle()"> 
	<template:titile value="隐患查看" />
	<html:form action="/hiddangerAction.do" styleId="form">
		<template:formTable namewidth="200" contentwidth="500">
			<template:formTr name="隐患编号">
				<bean:write name="registBean" property="hiddangerNumber" />
			</template:formTr>
			<template:formTr name="登记单位">
				<bean:write name="registBean" property="createrDept" />
			</template:formTr>
			<template:formTr name="登记人">
				<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="${registBean.creater}" />
			</template:formTr>
			<template:formTr name="隐患位置">
				<bean:write name="registBean" property="x" />,<bean:write name="registBean" property="y" /><input type="button" onclick="showMap();" value="查看位置" />
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
				<apptag:assorciateAttr table="contractorinfo" label="contractorname" key="contractorid" keyValue="${registBean.treatDepartment}" />
			</template:formTr>
			<template:formTr name="隐患分类">
			    <apptag:assorciateAttr table="troubletype" label="typename" key="id" keyValue="${registBean.type}" />
			</template:formTr>
			<template:formTr name="隐患名称">
				<apptag:assorciateAttr table="troublecode" label="troublename" key="id" keyValue="${registBean.code}" />
			</template:formTr>
			<c:if test="${!empty registBean.hiddangerLevel}">
				<template:formTr name="隐患等级">
					<c:choose>
						<c:when test="${registBean.hiddangerLevel eq '0'}">
							忽略
						</c:when>
						<c:otherwise>
							<bean:write name="registBean" property="hiddangerLevel" />级
						</c:otherwise>
					</c:choose>
				</template:formTr>
				<template:formTr name="隐患评级备注">
					<bean:write name="registBean" property="remark" />
				</template:formTr>
			</c:if>
			<logic:notEmpty property="cancelUserId" name="registBean">
				<tr class=trcolor>
					<td class="tdr">
						取消人：
					</td>
					<td class="tdl">
						<bean:write property="cancelUserName" name="registBean" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						取消时间：
					</td>
					<td class="tdl">
						<bean:write property="cancelTime" name="registBean" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						取消原因：
					</td>
					<td class="tdl">
						<bean:write property="cancelReason" name="registBean" />
					</td>
				</tr>
			</logic:notEmpty>
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
				<apptag:trunk id="trunk" state="view" value="${treatBean.trunkIdsString}" />
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
				<apptag:upload state="look" cssClass="" entityId="${registBean.id}" entityType="LP_HIDDANGER_TREAT"/>
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
				<apptag:quickLoadList cssClass="input" style="width:200" name="workStage" listName="workstage" type="look" keyValue="${reportBean.workStage}"/>
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
				<apptag:trunk id="trunk" state="view" value="${reportBean.trunkIdsString}" />
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
				<apptag:upload state="look" cssClass="" entityId="${registBean.id}" entityType="LP_HIDDANGER_REPORT"/>
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
		<apptag:appraiseDailyDaily contractorId="${registBean.treatDepartment}" businessId="${registBean.id }" businessModule="hiddanger" 
				displayType="view" tableStyle="width:700px; border-top: 0px;"></apptag:appraiseDailyDaily>
				<apptag:appraiseDailySpecial contractorId="${registBean.treatDepartment}" businessId="${registBean.id }" businessModule="hiddanger" 
				displayType="view" tableStyle="width:700px; border-top: 0px;"></apptag:appraiseDailySpecial>
		<c:if test="${iswin eq 'window'}">
		
		</c:if>
		<c:if test="${iswin eq 'finished'}">
			<template:formSubmit>
				<td>
					<input type="button" class="button" value="流程历史" onclick="his('${registBean.id}')" />
				</td>
				<td>
					<input type="button" class="button" value="返回" onclick="history.back()" />
				</td>
			</template:formSubmit>
		</c:if>
		<c:if test="${empty iswin}">
			<template:formSubmit>
				<td>
					<input type="button" class="button" value="返回" onclick="history.back()" />
				</td>
			</template:formSubmit>
		</c:if>
		</template:formTable>
	</html:form>
</body>