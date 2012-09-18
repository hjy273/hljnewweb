<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
		<link rel='stylesheet' type='text/css'
			href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript'
			src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<script src="${ctx}/js/validation/prototype.js" type="text/javascript"></script>
<script src="${ctx}/js/validation/effects.js" type="text/javascript"></script>
<script src="${ctx}/js/validation/validation_cn.js" type="text/javascript"></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="text/javascript">
	function query(id){
		window.location.href='${ctx}/oeAttemperTaskAction_viewRedeploy.jspx?id='+id+'&radius='+$('radius').value;
	}
	function redeploy(id){
	if(confirm("确定要调度该油机？")){
		window.location.href='${ctx}/oeAttemperTaskAction_redeploy.jspx?id='+id+'&responseTime='+$('responseTime').value;
		}
	}
</script>
<body>
<template:titile value="断电基站调度"/>
<br/>
		<table width="850" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					断电基站信息
				</td>
				<td class="tdulright" colspan="3" style="width:85%">e
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					站址编号：
				</td>
				<td class="tdulright" style="width:35%">
					${OEATTEMPERTASK.stationCode}
				</td>
				<td class="tdulleft" style="width:15%">
					基站名称：
				</td>
				<td class="tdulright" style="width:35%">
					${OEATTEMPERTASK.stationName}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					基站地址：
				</td>
				<td class="tdulright" style="width:35%">
					${OEATTEMPERTASK.address}
				</td>
				<td class="tdulleft" style="width:15%">
					基站级别：
				</td>
				<td class="tdulright" style="width:35%">
					${STATIONLEVELS[OEATTEMPERTASK.bsLevel]}
				</td>
			</tr>
			<tr class="trwhite">
				
				<td class="tdulleft" style="width:15%">
					断电时间：
				</td>
				<td class="tdulright" style="width:35%">
					${OEATTEMPERTASK.blackoutTime}
				</td>
				<td class="tdulleft" style="width:15%">
					断电原因：
				</td>
				<td class="tdulright" style="width:35%">
					${OEATTEMPERTASK.blackoutReason}
				</td>
			</tr>
		</table>
	<h3>所属电源设备列表：</h3>
	<display:table name="sessionScope.RESULTLIST" id="row"  pagesize="5" export="fasle">
		<display:column property="equName" title="设备名称"  maxLength="15" sortable="true"/>
		<display:column property="equModel" title="设备型号"  maxLength="15" sortable="true"/>
		<display:column property="equNum" title="数量"  maxLength="15" sortable="true"/>
		<display:column property="equCapacity" title="容量" maxLength="15" sortable="true"/>
		<display:column property="equEnableDate" title="启用日期" format="{0,date,yyyy年MM月dd日}" maxLength="15" sortable="true"/>
	</display:table>
	<s:form action="oeAttemperTaskAction_viewRedeploy" id="radius" name="radius" method="post">
	<div align="center" >
	<input name="id" value="${OEATTEMPERTASK.id}" type="hidden"/>
		搜索范围半径：<input type="text" class="inputtext required validate-number" style="width:150px" name="radius" />&nbsp;<span style="color:red">&nbsp;米</span>&nbsp;<input type="submit"  class="button" value="搜索" />
		<br />
	</div>
	<div align="center">
	油机响应时间：<input type="text" name="responseTime" class="inputtext required validate-number" value="0"  style="width:150px" />&nbsp;<span style="color:red">单位（小时）&nbsp;&nbsp;&nbsp;</span>
	</div>
	<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('radius', {immediate : true, onFormValidate : formCallback});
		</script>
	</s:form>
	<h3>空闲油机列表：</h3>
	<display:table name="sessionScope.OILENGINES" id="rowoil"  pagesize="5" export="fasle">
		<display:column property="oilEngineCode" title="油机编号"  maxLength="15" sortable="true"/>
		<display:column property="producer" title="油机厂商"  maxLength="15" sortable="true"/>
		<display:column property="oilEngineModel" title="油机型号"  maxLength="15" sortable="true"/>
		<display:column property="oilEngineType" title="油机类型"  maxLength="15" sortable="true"/>
		<display:column property="oilType" title="油料类型"  maxLength="15" sortable="true"/>
		<display:column property="oilEnginePhase" title="油机相数"  maxLength="15" sortable="true"/>
		<display:column property="oilEngineWeight" title="油机重量"  maxLength="15" sortable="true"/>
		<display:column property="pationPower" title="额定功率"  maxLength="15" sortable="true"/>
		<display:column property="address" title="所在地"  maxLength="15" sortable="true"/>
		<display:column property="phone" title="联系电话"  maxLength="15" sortable="true"/>
		<display:column title="操作">
			<a href="javascript:redeploy('${rowoil.id}')">调度</a>
		</display:column>
	</display:table>
	<iframe src="${URL}gisextend/igis.jsp?actiontype=201&x=${longitude}&y=${latitude}&dist=${RADIUS}&userid=${LOGIN_USER.userID}" width="100%" height="50%" scrolling="auto"/>
	
</body>
