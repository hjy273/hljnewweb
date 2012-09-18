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
	if(confirm("ȷ��Ҫ���ȸ��ͻ���")){
		window.location.href='${ctx}/oeAttemperTaskAction_redeploy.jspx?id='+id+'&responseTime='+$('responseTime').value;
		}
	}
</script>
<body>
<template:titile value="�ϵ��վ����"/>
<br/>
		<table width="850" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					�ϵ��վ��Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width:85%">e
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					վַ��ţ�
				</td>
				<td class="tdulright" style="width:35%">
					${OEATTEMPERTASK.stationCode}
				</td>
				<td class="tdulleft" style="width:15%">
					��վ���ƣ�
				</td>
				<td class="tdulright" style="width:35%">
					${OEATTEMPERTASK.stationName}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					��վ��ַ��
				</td>
				<td class="tdulright" style="width:35%">
					${OEATTEMPERTASK.address}
				</td>
				<td class="tdulleft" style="width:15%">
					��վ����
				</td>
				<td class="tdulright" style="width:35%">
					${STATIONLEVELS[OEATTEMPERTASK.bsLevel]}
				</td>
			</tr>
			<tr class="trwhite">
				
				<td class="tdulleft" style="width:15%">
					�ϵ�ʱ�䣺
				</td>
				<td class="tdulright" style="width:35%">
					${OEATTEMPERTASK.blackoutTime}
				</td>
				<td class="tdulleft" style="width:15%">
					�ϵ�ԭ��
				</td>
				<td class="tdulright" style="width:35%">
					${OEATTEMPERTASK.blackoutReason}
				</td>
			</tr>
		</table>
	<h3>������Դ�豸�б�</h3>
	<display:table name="sessionScope.RESULTLIST" id="row"  pagesize="5" export="fasle">
		<display:column property="equName" title="�豸����"  maxLength="15" sortable="true"/>
		<display:column property="equModel" title="�豸�ͺ�"  maxLength="15" sortable="true"/>
		<display:column property="equNum" title="����"  maxLength="15" sortable="true"/>
		<display:column property="equCapacity" title="����" maxLength="15" sortable="true"/>
		<display:column property="equEnableDate" title="��������" format="{0,date,yyyy��MM��dd��}" maxLength="15" sortable="true"/>
	</display:table>
	<s:form action="oeAttemperTaskAction_viewRedeploy" id="radius" name="radius" method="post">
	<div align="center" >
	<input name="id" value="${OEATTEMPERTASK.id}" type="hidden"/>
		������Χ�뾶��<input type="text" class="inputtext required validate-number" style="width:150px" name="radius" />&nbsp;<span style="color:red">&nbsp;��</span>&nbsp;<input type="submit"  class="button" value="����" />
		<br />
	</div>
	<div align="center">
	�ͻ���Ӧʱ�䣺<input type="text" name="responseTime" class="inputtext required validate-number" value="0"  style="width:150px" />&nbsp;<span style="color:red">��λ��Сʱ��&nbsp;&nbsp;&nbsp;</span>
	</div>
	<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('radius', {immediate : true, onFormValidate : formCallback});
		</script>
	</s:form>
	<h3>�����ͻ��б�</h3>
	<display:table name="sessionScope.OILENGINES" id="rowoil"  pagesize="5" export="fasle">
		<display:column property="oilEngineCode" title="�ͻ����"  maxLength="15" sortable="true"/>
		<display:column property="producer" title="�ͻ�����"  maxLength="15" sortable="true"/>
		<display:column property="oilEngineModel" title="�ͻ��ͺ�"  maxLength="15" sortable="true"/>
		<display:column property="oilEngineType" title="�ͻ�����"  maxLength="15" sortable="true"/>
		<display:column property="oilType" title="��������"  maxLength="15" sortable="true"/>
		<display:column property="oilEnginePhase" title="�ͻ�����"  maxLength="15" sortable="true"/>
		<display:column property="oilEngineWeight" title="�ͻ�����"  maxLength="15" sortable="true"/>
		<display:column property="pationPower" title="�����"  maxLength="15" sortable="true"/>
		<display:column property="address" title="���ڵ�"  maxLength="15" sortable="true"/>
		<display:column property="phone" title="��ϵ�绰"  maxLength="15" sortable="true"/>
		<display:column title="����">
			<a href="javascript:redeploy('${rowoil.id}')">����</a>
		</display:column>
	</display:table>
	<iframe src="${URL}gisextend/igis.jsp?actiontype=201&x=${longitude}&y=${latitude}&dist=${RADIUS}&userid=${LOGIN_USER.userID}" width="100%" height="50%" scrolling="auto"/>
	
</body>
