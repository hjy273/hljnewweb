<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linepatrol.cut.module.*"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>�鿴�������</title>
		<script type="text/javascript">
			//�鿴����
			toViewFinishCut=function(cutId){
            	var url = "${ctx}/process_history.do?method=showProcessHistoryList&&object_type=lineCut&&is_close=0&&object_id="+cutId;
            	processWin = new Ext.Window({
				layout : 'fit',
				width:750,height:400, 
				resizable:true,
				closeAction : 'close', 
				modal:true,
				autoScroll:true,
				autoLoad:{url: url,scripts:true}, 
				plain: true,
				title:"�鿴������̴�����Ϣ" 
			});
			processWin.show(Ext.getBody());
			}
			closeProcessWin=function(){
				processWin.close();
			}
		</script>
	</head>
	<body>
		<template:titile value="�鿴�������"/>
		<html:form action="/cutAction.do?method=readReply" styleId="addCutApply" enctype="multipart/form-data">
			<jsp:include page="cut_apply_base.jsp"/>
			<input type="hidden" value="${cut.id }" name="cutId"/>
			<table cellspacing="0" cellpadding="1" style="width:90%;border-top:0px;" align="center" class="tabout">
				<tr class="trcolor">
					<td align="center">
						<c:if test="${not empty isread}">
							<html:submit property="button" styleClass="button">����</html:submit>&nbsp;&nbsp;
						</c:if>
						<html:button property="button" styleClass="button" onclick="toViewFinishCut('${cut.id}')">�鿴������ʷ</html:button>&nbsp;&nbsp;
						<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
