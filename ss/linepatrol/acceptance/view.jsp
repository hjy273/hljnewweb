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
	<template:titile value="���ս�ά" />
	<template:formTable namewidth="200" contentwidth="400">
		<template:formTr name="��Ŀ����">
			<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="${apply.applicant}" />
		</template:formTr>
		<template:formTr name="��������">
			${apply.name}
		</template:formTr>
		<template:formTr name="���ս�ά���">
			${apply.code}
		</template:formTr>
		<template:formTr name="����ʱ��">
			<bean:write name="apply" property="applyDate" format="yyyy/MM/dd"/>
		</template:formTr>
		<template:formTr name="������Դ����">
			<c:choose>
				<c:when test="${apply.resourceType eq '1'}">
					����
				</c:when>
				<c:otherwise>
					�ܵ�
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="��ע">
				${apply.remark}
		</template:formTr>
			<logic:notEmpty property="cancelUserId" name="apply">
				<tr class=trcolor>
					<td class="tdr">
						ȡ���ˣ�
					</td>
					<td class="tdl">
						<bean:write property="cancelUserName" name="apply" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						ȡ��ʱ�䣺
					</td>
					<td class="tdl">
						<bean:write property="cancelTimeDis" name="apply" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						ȡ��ԭ��
					</td>
					<td class="tdl">
						<bean:write property="cancelReason" name="apply" />
					</td>
				</tr>
			</logic:notEmpty>
		<template:formTr name="������Դ����">
			${apply.resourceNumber}
		</template:formTr>
		<template:formTr name="��Դ��������">
			${apply.recordNumber} ����Դͨ�������� ��${apply.passedNumber}����Դδͨ�������� ��${apply.notPassedNumber}��
		</template:formTr>
		<template:formTr name="��Դ��������">
			${apply.notRecordNumber}
		</template:formTr>
		<template:formTr name="�����ά�б�">
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
					<input type="button" class="button" value="������ʷ" onclick="his('${apply.id}')" />
				</c:if>
				<c:if test="${apply.resourceType eq '1' && apply.recordNumber != '0'}">
					<input type="button" class="button" value="����" onclick="excel('${apply.id}')"/>
				</c:if>
				<input type="button" class="button" value="����" onclick="history.back()" />
			</td>
		</tr>
	</table>
</body>
</html>