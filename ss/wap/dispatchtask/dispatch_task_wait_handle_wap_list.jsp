<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/wap/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<html>
	<head>
		<title>sendtask</title>
		<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
		<META HTTP-EQUIV="expires" CONTENT="0">
		<script type="" language="javascript">
		toSignInForm=function(dispatchId,subTaskId){
			var url="${ctx}/wap/dispatchtask/sign_in_task.do?method=signInTaskForm&&env=wap";
			var queryString="dispatch_id="+dispatchId+"&send_accept_dept_id="+subTaskId;
			location=url+"&"+queryString+"&rnd="+Math.random();
		}
		toRefuseConfirmForm=function(dispatchId,subTaskId){
			var url="${ctx}/wap/dispatchtask/sign_in_task.do?method=refuseDispatchTaskForm&&env=wap";
			var queryString="dispatch_id="+dispatchId+"&send_accept_dept_id="+subTaskId;
			location=url+"&"+queryString+"&rnd="+Math.random();
		}
		toCheckForm=function(dispatchId){
			var url="${ctx}/wap/dispatchtask/check_task.do?method=checkDispatchTaskForm&&env=wap";
			var queryString="dispatch_id="+dispatchId;
			location=url+"&"+queryString+"&rnd="+Math.random();
		}
		goBack=function(){
			history.back();
		}
		</script>
		<logic:equal value="1" name="LOGIN_USER" property="deptype">
		<script type="text/javascript">
		toTransferDispatchForm=function(dispatchId,subTaskId){
			var url="${ctx}/wap/dispatchtask/sign_in_task.do?method=transferDispatchTaskForm&&env=wap";
			var queryString="dispatch_id="+dispatchId+"&send_accept_dept_id="+subTaskId;
			var form1=document.forms["toProcessorUrlForm"];
			form1.action_url.value=url+"&"+queryString+"&rnd="+Math.random();
			form1.except_self.value="true";
			form1.display_type.value="mobile";
			form1.input_type.value="radio";
			form1.input_name.value="acceptdeptid,user,mobileId,acceptuserid";
			form1.submit();
		}
		toReplyForm=function(dispatchId,subTaskId,existValue,sendDepartId){
			var url="${ctx}/wap/dispatchtask/reply_task.do?method=replyTaskForm&&env=wap";
			var queryString="dispatch_id="+dispatchId+"&send_accept_dept_id="+subTaskId;
			var form1=document.forms["toApproverUrlForm"];
			form1.action_url.value=url+"&"+queryString+"&rnd="+Math.random();
			form1.display_type.value="contractor";
			form1.exist_value.value=existValue;
			form1.depart_id.value=sendDepartId;
			form1.approver_input_name.value="approverid";
			form1.reader_input_name.value="readerid";
			form1.submit();
		}
		toUpdateReplyForm=function(dispatchId,replyId,sendDepartId){
			var url="${ctx}/wap/dispatchtask/reply_task.do?method=updateReplyTaskForm&&env=wap";
			var queryString="dispatch_id="+dispatchId+"&reply_id="+replyId;
			var form1=document.forms["toApproverUrlForm"];
			form1.action_url.value=url+"&"+queryString+"&rnd="+Math.random();
			form1.display_type.value="contractor";
			form1.object_id.value=replyId;
			form1.object_type.value="LP_SENDTASKREPLY";
			form1.approver_input_name.value="approverid";
			form1.reader_input_name.value="readerid";
			form1.depart_id.value=sendDepartId;
			form1.submit();
		}
		</script>
		</logic:equal>
		<logic:equal value="2" name="LOGIN_USER" property="deptype">
		<script type="text/javascript">
		toTransferDispatchForm=function(dispatchId,subTaskId){
			var url="${ctx}/wap/dispatchtask/sign_in_task.do?method=transferDispatchTaskForm&&env=wap";
			var queryString="dispatch_id="+dispatchId+"&send_accept_dept_id="+subTaskId;
			var form1=document.forms["toProcessorUrlForm"];
			form1.action_url.value=url+"&"+queryString+"&rnd="+Math.random();
			form1.except_self.value="true";
			form1.display_type.value="contractor";
			form1.input_type.value="radio";
			form1.input_name.value="acceptdeptid,user,mobileId,acceptuserid";
			form1.submit();
		}
		toReplyForm=function(dispatchId,subTaskId,existValue,sendDepartId){
			var url="${ctx}/wap/dispatchtask/reply_task.do?method=replyTaskForm&&env=wap";
			var queryString="dispatch_id="+dispatchId+"&send_accept_dept_id="+subTaskId;
			var form1=document.forms["toApproverUrlForm"];
			form1.action_url.value=url+"&"+queryString+"&rnd="+Math.random();
			form1.display_type.value="mobile";
			form1.exist_value.value=existValue;
			form1.approver_input_name.value="approverid";
			form1.reader_input_name.value="readerid";
			form1.submit();
		}
		toUpdateReplyForm=function(dispatchId,replyId,sendDepartId){
			var url="${ctx}/wap/dispatchtask/reply_task.do?method=updateReplyTaskForm&&env=wap";
			var queryString="dispatch_id="+dispatchId+"&reply_id="+replyId;
			var form1=document.forms["toApproverUrlForm"];
			form1.action_url.value=url+"&"+queryString+"&rnd="+Math.random();
			form1.display_type.value="mobile";
			form1.object_id.value=replyId;
			form1.object_type.value="LP_SENDTASKREPLY";
			form1.approver_input_name.value="approverid";
			form1.reader_input_name.value="readerid";
			form1.depart_id.value=sendDepartId;
			form1.submit();
		}
		</script>
		</logic:equal>
	</head>
	<body>
		<div>
			<a class="dept1">${LOGIN_USER.userName} ���ã�</a><a class="dept2"
				href="${ctx}/wap/navigation.jsp">���ܵ���</a><a class="dept"
				href="javascript:exitSys();">�˳�</a><br /><br />
		</div>
		<!--��ʾ�����ɵ�ҳ��-->
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<logic:notEmpty name="task_name">
			<logic:equal value="sign_in_task" name="task_name">
				<template:titile
					value="��ǩ�������ɵ��б� (<font color='red'>${sessionScope.DISPATCH_TASK_LIST_NUM}������</font>)" />
			</logic:equal>
			<logic:equal value="tranfer_sign_in_task" name="task_name">
				<template:titile
					value="��ת��ǩ�������ɵ��б� (<font color='red'>${sessionScope.DISPATCH_TASK_LIST_NUM}������</font>)" />
			</logic:equal>
			<logic:equal value="refuse_confirm_task" name="task_name">
				<template:titile
					value="����ǩȷ�������ɵ��б� (<font color='red'>${sessionScope.DISPATCH_TASK_LIST_NUM}������</font>)" />
			</logic:equal>
			<logic:equal value="reply_task" name="task_name">
				<template:titile
					value="�����������ɵ��б� (<font color='red'>${sessionScope.DISPATCH_TASK_LIST_NUM}������</font>)" />
			</logic:equal>
			<logic:equal value="check_task" name="task_name">
				<template:titile
					value="����֤�����ɵ��б� (<font color='red'>${sessionScope.DISPATCH_TASK_LIST_NUM}������</font>)" />
			</logic:equal>
		</logic:notEmpty>
		<logic:empty name="task_name">
			<template:titile
				value="���������ɵ��б� (<font color='red'>${sessionScope.DISPATCH_TASK_LIST_NUM}������</font>)" />
		</logic:empty>
		<logic:notEmpty name="DISPATCH_TASK_LIST">
			<%
			List list=(List)session.getAttribute("DISPATCH_TASK_LIST");
			DynaBean bean;
			for(int i=0;list!=null&&i<list.size();i++){
				bean=(DynaBean)list.get(i);
			 %>
				<p>
					<%=(String)bean.get("sendtopic") %>&nbsp;&nbsp;<%=(String)bean.get("sendtypelabel") %><br/>
					<%
					if("sign_in_task".equals(bean.get("flow_state"))||"tranfer_sign_in_task".equals(bean.get("flow_state"))){
					 %>
						<a
							href="javascript:toSignInForm('<%=(String)bean.get("id") %>','<%=(String)bean.get("subid") %>')">ǩ��</a>
					<%
					}
					if("sign_in_task".equals(bean.get("flow_state"))){
					 %>
						<a
							href="javascript:toTransferDispatchForm('<%=(String)bean.get("id") %>','<%=(String)bean.get("subid") %>')">ת��</a>
					<%
					}
					if("refuse_confirm_task".equals(bean.get("flow_state"))){
					 %>
						<a
							href="javascript:toRefuseConfirmForm('<%=(String)bean.get("id") %>','<%=(String)bean.get("subid") %>')">��ǩȷ��</a>
					<%
					}
					if("reply_task".equals(bean.get("flow_state"))){
						if("0".equals(bean.get("exist_reply"))){
					%>
							<a
								href="javascript:toReplyForm('<%=(String)bean.get("id") %>','<%=(String)bean.get("subid") %>','<%=(String)bean.get("senduserid") %>','<%=(String)bean.get("senddeptid") %>')">����</a>
					<%
						}
						if("1".equals(bean.get("exist_reply"))){
					%>
							<a
								href="javascript:toUpdateReplyForm('<%=(String)bean.get("id") %>','<%=(String)bean.get("reply_id") %>','<%=(String)bean.get("senddeptid") %>')">�޸ķ���</a>
					<%
						}
					}
					if("check_task".equals(bean.get("flow_state"))){
					 %>
						<a href="javascript:toCheckForm('<%=(String)bean.get("id") %>')">���</a>
					<%
					}
					 %>
				</p>
			<%
			}
			 %>
		</logic:notEmpty>
		<p>
			<input type="button" value="����" onclick="goBack();">
		</p>
		<div style="display:none;">
			<form id="toProcessorUrlForm" method="post"
				action="${ctx}/wap/load_processors.do?method=loadWapDeparts">
				<input name="display_type" value="contractor" type="hidden" />
				<input name="exist_value" value="" type="hidden" />
				<input name="except_self" value="" type="hidden" />
				<input name="input_type" value="checkbox" />
				<input name="action_url" value="" type="hidden" />
				<input name="input_name" value="dept_id,user,mobile_id,user_id"
					type="hidden" />
			</form>
			<form id="toApproverUrlForm" method="post"
				action="${ctx}/wap/load_approvers.do?method=loadWapApprovers">
				<input name="display_type" value="contractor" type="hidden" />
				<input name="exist_value" value="" type="hidden" />
				<input name="object_id" value="" type="hidden" />
				<input name="object_type" value="" type="hidden" />
				<input name="except_self" value="" type="hidden" />
				<input name="action_url" value="" type="hidden" />
				<input name="depart_id" value="" type="hidden" />
				<input name="approver_input_name" value="" type="hidden" />
				<input name="reader_input_name" value="" type="hidden" />
			</form>
		</div>
	</body>
</html>
