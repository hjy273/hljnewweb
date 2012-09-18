<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>sendtask</title>
		<link rel='stylesheet' type='text/css'
			href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript'
			src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<script type="" language="javascript">
		goBack=function(){
			var url="${ctx}/dispatchtask/check_task.do?method=checkDispatchTaskForm";
			var queryString="dispatch_id=${dispatch_id}";
			location=url+"&"+queryString+"&rnd="+Math.random();
		}
		listSignInTask=function(){
			var url="${ctx}/dispatchtask/sign_in_task.do?method=queryForSignInTask";
			var queryString="dispatch_id=${dispatch_id}&sub_id=${bean.subtaskid}";
			var actionUrl=url+"&"+queryString+"&rnd="+Math.random();
			var myAjax=new Ajax.Updater(
				"signInTaskDiv",actionUrl,{
					method:"post",
					evalScripts:true
				}
			);
		}
		listCheckTask=function(replyId){
			var url="${ctx}/dispatchtask/check_task.do?method=queryForCheckTask";
			var queryString="reply_id="+replyId;
			var actionUrl=url+"&"+queryString+"&rnd="+Math.random();
			var myAjax=new Ajax.Updater(
				"checkTaskDiv",actionUrl,{
					method:"post",
					evalScripts:true
				}
			);
		}
		var win;
		var win1;
		toViewOneSignInTask=function(signInTaskId){
			var actionUrl="${ctx}/dispatchtask/sign_in_task.do?method=viewSignInTask";
			var queryString="sign_in_id="+signInTaskId+"&dispatch_id=${dispatch_id}";
			//location=actionUrl+"&"+queryString+"&rnd="+Math.random();
			win = new Ext.Window({
				layout : 'fit',
				width:650,height:400, 
				resizable:true,
				closeAction : 'close', 
				modal:true,
				autoScroll:true,
				autoLoad:{url: actionUrl+"&"+queryString+"&rnd="+Math.random(),scripts:true}, 
				plain: true,
				title:"�鿴ǩ����ϸ��Ϣ" 
			});
			win.show(Ext.getBody());
		}
		toViewOneCheckTask=function(checkTaskId){
			var actionUrl="${ctx}/dispatchtask/check_task.do?method=viewCheckTask";
			var queryString="check_id="+checkTaskId+"&dispatch_id=${dispatch_id}";
			//location=actionUrl+"&"+queryString+"&rnd="+Math.random();
			win1 = new Ext.Window({
				layout : 'fit',
				width:650,height:400, 
				resizable:true,
				closeAction : 'close', 
				modal:true,
				autoScroll:true,
				autoLoad:{url: actionUrl+"&"+queryString+"&rnd="+Math.random(),scripts:true}, 
				plain: true,
				title:"�鿴���񵥷��������ϸ��Ϣ" 
			});
			win1.show(Ext.getBody());
		}
		closeWin=function(){
			win.close();
		}
		closeWin1=function(){
			win1.close();
		}
function valCharLength(Value){
      var j=0;
      var s = Value;
      for   (var   i=0;   i<s.length;   i++)
      {
              if   (s.substr(i,1).charCodeAt(0)>255)   j   =   j   +   2;
              else   j++
      }
      return j;
    }


		</script>
	</head>
	<body>
		<!--��ʾ�ɵ���֤����ϸ��Ϣ,������֤-->
		<template:titile value="���񵥷����鿴" />
		<html:form action="/dispatchtask/check_task.do?method=checkReadTask"
			styleId="addApplyForm" onsubmit="return true;"
			enctype="multipart/form-data">
			<html:hidden property="sendtopic" name="bean" />
			<input type="hidden" name="dispatch_id" value="${dispatch_id }" />
			<input type="hidden" name="sendacceptdeptid"
				value="<bean:write name="bean" property="subtaskid"/>" />
			<input type="hidden" name="replyid"
				value="<bean:write name="replybean" property="id"/>" />
			<input type="hidden" name="replyuserid"
				value="<bean:write name="replybean" property="replyuserid"/>" />
			<table width="90%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr class=trcolor>
					<td colspan="4"  id="signInTaskDiv" class="tdl"
						style='padding-left: 10px; padding-right: 10px; padding-top: 10px; padding-bottom: 10px'></td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" width="15%" >
						�����ˣ�
					</td>
					<td class="tdl" width="35%">
						<bean:write name="replybean" property="replyusername" />
					</td>
					<td class="tdr" width="15%">
						���������
					</td>
					<td class="tdl" width="35%">
						<c:if test="${replybean.replyresult=='00'}">δ���</c:if>
						<c:if test="${replybean.replyresult=='01'}">�������</c:if>
						<c:if test="${replybean.replyresult=='02'}">�������</c:if>
						<c:if test="${replybean.replyresult=='03'}">ȫ�����</c:if>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						����ʱ�䣺
					</td>
					<td class="tdl">
						<bean:write property="replytime" name="replybean" />
					</td>
					<td class="tdr">
						�Ƿ�ʱ��
					</td>
					<td class="tdl">
						<c:if test="${replybean.replyTimeSign=='-'}">
							<font color="#FF0000">��ʱ${replybean.replyTimeLength }</font>
						</c:if>
						<c:if test="${replybean.replyTimeSign=='+'}">
							<font color="#339999">��ǰ${replybean.replyTimeLength }</font>
						</c:if>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						������ע��
					</td>
					<td class="tdl" colspan="3">
						<bean:write property="replyremark" name="replybean" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						����������
					</td>
					<td class="tdl" colspan="3">
						<apptag:upload cssClass="" entityId="${replybean.id}"
							entityType="LP_SENDTASKREPLY" state="look" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdl" colspan="4" id="checkTaskDiv" 
						style='padding-left: 10px; padding-right: 10px; padding-top: 10px; padding-bottom: 10px'>
					</td>
				</tr>
			</table>
			<table align="center">
				<tr>
					<td>
						<html:submit styleClass="button">���Ķ�</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">����</html:reset>
					</td>
					<td>
						<input type="button" value="����" class="button" onclick="goBack();">
					</td>
				</tr>
			</table>
		</html:form>
		<script type="text/javascript">
		listSignInTask();
		listCheckTask('${replybean.id}');
		</script>
	</body>
</html>
