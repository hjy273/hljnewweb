<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<SCRIPT language=JavaScript src="${ctx}/common/PopupDlg.js" type=""></SCRIPT>
<SCRIPT language=javascript src="${ctx}/common/Comm.js" type=""></SCRIPT>
<html>
	<head>
		<title>sendtask</title>
		<link rel='stylesheet' type='text/css'
			href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript'
			src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<script type="text/javascript">
	showProcessHistoryDetail = function(rowId, subProcessId) {
		var url = "${ctx}/process_history.do?method=showProcessHistoryList";
		var queryString = "object_type=sendtask&&show_close=0&&object_id="
				+ subProcessId;
		var actionUrl = url + "&" + queryString + "&rnd=" + Math.random();
		var myAjax = new Ajax.Updater("processHistoryDiv" + rowId, actionUrl, {
			method : "post",
			evalScripts : true
		});
	}
	showOneProcessHistory = function(rowId, subProcessId) {
		var hTr = document.getElementById("processHistoryTr" + rowId);
		if (hTr.style.display == "") {
			hTr.style.display = "none";
		} else {
			hTr.style.display = "";
		}
		showProcessHistoryDetail(rowId, subProcessId);
	}
	var flag = false;
	showAllProcessHistory = function() {
		var hTrInput = document.getElementsByName("processHistoryTrName");
		if (typeof (hTrInput) != "undefined") {
			if (typeof (hTrInput.length) != "undefined") {
				for (i = 0; i < hTrInput.length; i++) {
					var hTr = document.getElementById(hTrInput[i].value);
					if (flag) {
						hTr.style.display = "none";
					} else {
						hTr.style.display = "";
						subProcessId = hTr.varId;
						showProcessHistoryDetail(i, subProcessId);
					}
				}
			} else {
				var hTr = document.getElementById(hTrInput.value);
				if (flag) {
					hTr.style.display == "none";
				} else {
					hTr.style.display == "";
					subProcessId = hTr.varId;
					showProcessHistoryDetail(0, subProcessId);
				}
			}
		} else {
		}
		flag = !flag;
	}
</script>
		<script type="" language="javascript">
		var win;
		var win1;
		var processWin;
		goBack=function(){
			var url="${sessionScope.S_BACK_URL}";
			location=url;
		}
		toViewProcessHistory=function(){
			var actionUrl="${ctx}/dispatchtask/dispatch_task.do?method=viewProcessHistory";
			var queryString="dispatch_id=${dispatch_id}";
			//location=actionUrl+"&"+queryString+"&rnd="+Math.random();
			processWin = new Ext.Window({
				layout : 'fit',
				width:650,height:400, 
				resizable:true,
				closeAction : 'close', 
				modal:true,
				autoScroll:true,
				autoLoad:{url: actionUrl+"&"+queryString+"&rnd="+Math.random(),scripts:true}, 
				plain: true,
				title:"�鿴�ɵ�������ʷ��Ϣ" 
			});
			processWin.show(Ext.getBody());
		}
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
		toViewOneReplyTask=function(replyTaskId,contractorId){
			var actionUrl="${ctx}/dispatchtask/reply_task.do?method=viewReplyTask";
			var queryString="reply_id="+replyTaskId+"&contractorId="+contractorId+"&dispatch_id=${dispatch_id}";
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
				title:"�鿴������ϸ��Ϣ" 
			});
			win.show(Ext.getBody());;
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
				title:"�鿴��֤��ϸ��Ϣ" 
			});
			win1.show(Ext.getBody());
		}
		listSignInTask=function(){
			var url="${ctx}/dispatchtask/sign_in_task.do?method=queryForSignInTask";
			var queryString="dispatch_id=${dispatch_id}";
			var actionUrl=url+"&"+queryString+"&rnd="+Math.random();
			var myAjax=new Ajax.Updater(
				"signInTaskDiv",actionUrl,{
					method:"post",
					evalScripts:true
				}
			);
		}
		listReplyTask=function(){
			var url="${ctx}/dispatchtask/reply_task.do?method=queryForReplyTask";
			var queryString="dispatch_id=${dispatch_id}";
			var actionUrl=url+"&"+queryString+"&rnd="+Math.random();
			var myAjax=new Ajax.Updater(
				"replyTaskDiv",actionUrl,{
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
		closeWin=function(){
			win.close();
		}
		closeWin1=function(){
			win1.close();
		}
		closeProcessWin=function(){
			processWin.close();
		}
		</script>
	</head>
	<body>
		<!--��ʾһ���ɵ���ϸ��Ϣҳ��-->
		<br>
		<template:titile value="�ɵ���ϸ��Ϣ" />
		<table width="90%" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class=trcolor>
				<td class="tdr" width="15%">
					�ɵ���ˮ�ţ�
				</td>
				<td class="tdl" width="35%">
					<bean:write name="bean" property="serialnumber" />
				</td>
				<td class="tdr" width="15%">
					�������
				</td>
				<td class="tdl" width="35%">
					<apptag:quickLoadList cssClass="" name="" listName="dispatch_task"
						keyValue="${bean.sendtype}" type="look"></apptag:quickLoadList>
					<apptag:quickLoadList cssClass="" name=""
						listName="dispatch_task_con" keyValue="${bean.sendtype}"
						type="look"></apptag:quickLoadList>
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					�ɵ�ʱ�䣺
				</td>
				<td class="tdl">
					<bean:write name="bean" property="sendtime" />
				</td>
				<td class="tdr">
					�ɵ����ţ�
				</td>
				<td class="tdl">
					<bean:write name="bean" property="senddeptname" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					�� �� �ˣ�
				</td>
				<td class="tdl">
					<bean:write name="bean" property="sendusername" />
				</td>
				<td class="tdr">
					�����ź������ˣ�
				</td>
				<td class="tdl">
					<logic:present name="bean" property="acceptUserList">
						<logic:iterate id="oneAcceptUser" name="bean"
							property="acceptUserList">
								���ţ�<bean:write name="oneAcceptUser" property="departname" />
								�û���<bean:write name="oneAcceptUser" property="username" />
							<br />
						</logic:iterate>
					</logic:present>
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					�������⣺
				</td>
				<td class="tdl" colspan="3">
					<bean:write name="bean" property="sendtopic" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					����˵����
				</td>
				<td class="tdl" colspan="3">
					<div>
						<bean:write name="bean" property="sendtext" />
					</div>
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					����Ҫ��
				</td>
				<td class="tdl" colspan="3">
					<bean:write property="replyRequest" name="bean" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					����ʱ�ޣ�
				</td>
				<td class="tdl" colspan="3">
					<bean:write property="processterm" name="bean" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					���񸽼���
				</td>
				<td class="tdl" colspan="3">
					<apptag:upload cssClass="" entityId="${bean.id}"
						entityType="LP_SENDTASK" state="look" />
				</td>
			</tr>
			<logic:notEmpty property="cancelUserName" name="bean">
				<tr class=trcolor>
					<td class="tdr">
						ȡ���ˣ�
					</td>
					<td class="tdl">
						<bean:write property="cancelUserName" name="bean" />
					</td>
					<td class="tdr">
						ȡ��ʱ�䣺
					</td>
					<td class="tdl">
						<bean:write property="cancelTime" name="bean" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						ȡ��ԭ��
					</td>
					<td class="tdl" colspan="3">
						<bean:write property="cancelReason" name="bean" />
					</td>
				</tr>
			</logic:notEmpty>
			<tr class=trcolor>
				<td colspan="4" class="tdl" style='padding: 10px;text-align:center;'>
					<table border="1" cellpadding="0" cellspacing="0" style="border-collapse:collapse;width:100%">
						<tr>
							<td style="text-align: center;">
								������
							</td>
							<td style="text-align: center;">
								������
							</td>
							<td style="text-align: center;">
								����״̬
							</td>
						</tr>
						<logic:notEmpty name="bean" property="acceptUserList">
							<logic:iterate id="oneAcceptUser" name="bean" property="acceptUserList">
								<tr>
									<td style="text-align: center;">
										<bean:write property="departname" name="oneAcceptUser" />
									</td>
									<td style="text-align: center;">
										<bean:write property="username" name="oneAcceptUser" />
									</td>
									<td style="text-align: center;">
										<logic:equal value="sign_in_task" property="task_state" name="oneAcceptUser">
											ǩ����
										</logic:equal>
										<logic:equal value="tranfer_sign_in_task" property="task_state" name="oneAcceptUser">
											ǩ����
										</logic:equal>
										<logic:equal value="refuse_confirm_task" property="task_state" name="oneAcceptUser">
											��ǩȷ����
										</logic:equal>
										<logic:equal value="reply_task" property="task_state" name="oneAcceptUser">
											������
										</logic:equal>
										<logic:equal value="check_task" property="task_state" name="oneAcceptUser">
											���������
										</logic:equal>
										<logic:empty property="task_state" name="oneAcceptUser">
											�Ѱ��
										</logic:empty>
									</td>
								</tr>
							</logic:iterate>
						</logic:notEmpty>
					</table>
				</td>
			</tr>
			<tr class=trcolor>
				<td colspan="4" id="signInTaskDiv" class="tdl"
					style='padding-left: 10px; padding-right: 10px; padding-top: 10px; padding-bottom: 10px'></td>
			</tr>
			<tr class=trcolor>
				<td colspan="4" id="replyTaskDiv" class="tdl"
					style='padding-left: 10px; padding-right: 10px; padding-top: 10px; padding-bottom: 10px'></td>
			</tr>
		</table>
		<p align="center">
			<input type="button" value="�鿴������ʷ" class="lbutton"
				onclick="toViewProcessHistory();" >
			<input type="button" value="����" class="button" onclick="goBack();" >
		</p>
		<script type="text/javascript">
	listSignInTask();
	listReplyTask();
</script>
	</body>
</html>
