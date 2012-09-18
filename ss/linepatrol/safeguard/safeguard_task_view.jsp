<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<html>
	<head>
		<title>���������ɵ�</title>
		<script type="text/javascript">
			//�鿴����
			toViewFinishSafeguard=function(conId){
            	var url = "${ctx}/process_history.do?method=showProcessHistoryList&&object_type=safeguard&&is_close=0&&object_id="+conId;
            	processWin = new Ext.Window({
				layout : 'fit',
				width:750,height:400, 
				resizable:true,
				closeAction : 'close', 
				modal:true,
				autoScroll:true,
				autoLoad:{url: url,scripts:true}, 
				plain: true,
				title:"�鿴�������̴�����Ϣ" 
			});
			processWin.show(Ext.getBody());
			}
			closeProcessWin=function(){
				processWin.close();
			}
		</script>
	</head>
	<body>
		<template:titile value="���������ɵ�"/>
		<table border="1" align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">�����������ƣ�</td>
				<td class="tdulright">
					<c:out value="${safeguardTask.safeguardName}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">����ʱ�䣺</td>
				<td class="tdulright">
					<fmt:formatDate  value="${safeguardTask.startDate}" pattern="yyyy/MM/dd HH:mm:ss" var="formatStartDate"/>
					<fmt:formatDate  value="${safeguardTask.endDate}" pattern="yyyy/MM/dd HH:mm:ss" var="formatEndDate"/>
					<c:out value="${formatStartDate}"/> - <c:out value="${formatEndDate}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">�����ύʱ�ޣ�</td>
				<td class="tdulright">
					<bean:write name="safeguardTask" property="deadline" format="yyyy/MM/dd HH:mm:ss"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">���ϼ���</td>
				<td class="tdulright">
					<c:if test="${safeguardTask.level=='4'}">�ؼ�</c:if>
					<c:if test="${safeguardTask.level=='1'}">һ��</c:if>
					<c:if test="${safeguardTask.level=='2'}">����</c:if>
					<c:if test="${safeguardTask.level=='3'}">����</c:if>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">���ϵص㣺</td>
				<td class="tdulright">
					<c:out value="${safeguardTask.region}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">����Ҫ��</td>
				<td class="tdulright">
					<c:out value="${safeguardTask.requirement}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">���ϱ�ע��</td>
				<td class="tdulright">
					<c:out value="${safeguardTask.remark }"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">���񸽼���</td>
				<td class="tdulright">
					<apptag:upload cssClass="" entityId="${safeguardTask.id}" entityType="LP_SAFEGUARD_TASK" state="look"/>
				</td>
			</tr>
			<c:if test="${safeguardTask.cancelUserId!=null&&safeguardTask.cancelUserId!=''}">
				<tr class=trcolor>
					<td class="tdr">
						ȡ���ˣ�
					</td>
					<td class="tdl">
						${safeguardTask.cancelUserName}
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						ȡ��ʱ�䣺
					</td>
					<td class="tdl">
						${safeguardTask.cancelTimeDis}
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						ȡ��ԭ��
					</td>
					<td class="tdl">
						${safeguardTask.cancelReason}
					</td>
				</tr>
			</c:if>
		</table>
		<br/>
		<div align="center" style="height:40px">
			<html:button property="button" styleClass="lbutton" onclick="toViewFinishSafeguard('${conId}')">�鿴������ʷ</html:button>&nbsp;&nbsp;
			<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
		</div>
	</body>
</html>
