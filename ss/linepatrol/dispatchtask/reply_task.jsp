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
		var win;
		listSignInTask=function(){
			var url="${ctx}/dispatchtask/sign_in_task.do?method=queryForSignInTask";
			var queryString="dispatch_id=${bean.id}&sub_id=${bean.subtaskid}";
			var actionUrl=url+"&"+queryString+"&rnd="+Math.random();
			var myAjax=new Ajax.Updater(
				"signInTaskDiv",actionUrl,{
					method:"post",
					evalScripts:true
				}
			);
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
				title:"查看签收详细信息" 
			});
			win.show(Ext.getBody());
		}
		closeWin=function(){
			win.close();
		}
		goBack=function(){
			var url="${sessionScope.S_BACK_URL}";
			location=url;
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
function validate(form){
	if(form.approverid.value==""){
		alert("没有选择审核人！");
		return false;
	}
	var allCheck = document.getElementsByName('ifCheck');
		var length = allCheck.length;
		
		for(var i = 0; i < length; i++) {
			//var index = i + 1;
			var index = allCheck[i].value;
			var pathText = document.getElementById('uploadFile[' + index + '].file');
			var path = pathText.value;
			if(path.length == 0) {
				alert("请选择要上传的附件的路径，\n如果没有要上传的附件请删除!");
				pathText.focus();
				return false;
			}
		}
  if(valCharLength(form.replyremark.value)>1020){
    alert("反馈备注字数太多！不能超过510个汉字");
    return false;
  }
}
       //检验是否数字
	 function valiD(id){
      	var mysplit = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
        var obj = document.getElementById(id);
        if(mysplit.test(obj.value)){
          return true;
        }
        else{
        	alert("你填写的数字不合法,请重新输入");
            obj.focus();
            obj.value = "0.00";
            return false;
        }
    }
	function toForward(id, subid) {
		//if(loginUserid != acceptuserid) {
		//	alert("对不起，这个任务的指定处理人不能您，所以不能转发");
		//	return;
		//}
		var url = "${ctx}/ReplyAction.do?method=showTaskForward&id=" + id +"&subid="+subid;
    	window.location.href=url;
	}
		</script>
	</head>
	<body>
		<!--反馈派单-->
		<br>
		<template:titile value="派单反馈" />
		<html:form action="/dispatchtask/reply_task.do?method=replyTask"
			styleId="addApplyForm" onsubmit="return validate(this)"
			enctype="multipart/form-data">
			<html:hidden property="sendtopic" name="bean" />
			<input type="hidden" name="sendacceptdeptid"
				value="<bean:write name="bean" property="subtaskid"/>" />
			<table width="90%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr class=trcolor>
					<td class="tdr" width="15%">
						派单流水号：
					</td>
					<td class="tdl" width="35%">
						<bean:write property="serialnumber" name="bean" />
					</td>
					<td class="tdr" width="15%">
						工作单类别：
					</td>
					<td class="tdl" width="35%">
						<apptag:quickLoadList cssClass="" name="" listName="dispatch_task"
							keyValue="${bean.sendtype}" type="look"></apptag:quickLoadList>
						<apptag:quickLoadList cssClass="" name="" listName="dispatch_task_con"
							keyValue="${bean.sendtype}" type="look"></apptag:quickLoadList>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						派单时间：
					</td>
					<td class="tdl">
						<bean:write property="sendtime" name="bean" />
					</td>
					<td class="tdr">
						派单部门：
					</td>
					<td class="tdl">
						<bean:write name="bean" property="senddeptname" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						派 单 人：
					</td>
					<td class="tdl">
						<bean:write name="bean" property="sendusername" />
					</td>
					<td class="tdr">
						受理部门和受理人：
					</td>
					<td class="tdl">
						<logic:present name="bean" property="acceptUserList">
							<logic:iterate id="oneAcceptUser" name="bean"
								property="acceptUserList">
								部门：<bean:write name="oneAcceptUser" property="departname" />
								用户：<bean:write name="oneAcceptUser" property="username" />
								<br />
							</logic:iterate>
						</logic:present>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						任务主题：
					</td>
					<td class="tdl" colspan="3">
						<bean:write property="sendtopic" name="bean" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						任务说明：
					</td>
					<td class="tdl" colspan="3">
						<bean:write property="sendtext" name="bean" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						反馈要求：
					</td>
					<td class="tdl" colspan="3">
						<bean:write property="replyRequest" name="bean" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						处理时限：
					</td>
					<td class="tdl" colspan="3">
						<bean:write property="processterm" name="bean" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						任务附件：
					</td>
					<td class="tdl" colspan="3">
						<apptag:upload cssClass="" id="sendTaskIdAddReply"
							entityId="${bean.id}" entityType="LP_SENDTASK" state="look" />
					</td>
				</tr>
				<tr class=trcolor>
					<td colspan="4" id="signInTaskDiv" class="tdl"
						style='padding: 10px;'></td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						反馈结果：
					</td>
					<td class="tdl" colspan="3">
						<select name="replyresult" style="width: 180" class="inputtext">
							<option value="00">
								未完成
							</option>
							<option value="03">
								全部完成
							</option>
							<option value="02">
								基本完成
							</option>
							<option value="01">
								部分完成
							</option>
						</select>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						反馈备注：
					</td>
					<td class="tdl" colspan="3">
						<html:textarea property="replyremark" style="width:80%"
							title="请不要超过256个汉字或者512个英文字符，否则将截断。" rows="5"
							styleClass="textarea"></html:textarea>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						反馈附件：
					</td>
					<td class="tdl" colspan="3">
						<apptag:upload cssClass="" entityId=""
							entityType="LP_SENDTASKREPLY" state="add" />
					</td>
				</tr>
				<c:if test="${sessionScope.LOGIN_USER.deptype=='1'}">
					<tr class=trcolor>
						<apptag:approverselect inputName="approverid" label="审核人"
							inputType="radio" displayType="contractor"
							departId="${bean.senddeptid}"
							existValue="${bean.senduserid}--${bean.sendusername}"></apptag:approverselect>
					</tr>
					<tr class=trcolor>
						<apptag:approverselect inputName="readerid" label="抄送人"
							displayType="contractor" departId="${bean.senddeptid}"
							notAllowName="approverid" spanId="readerSpan"></apptag:approverselect>
					</tr>
				</c:if>
				<c:if test="${sessionScope.LOGIN_USER.deptype=='2'}">
					<tr class=trcolor>
						<apptag:approverselect inputName="approverid" label="审核人"
							inputType="radio"
							existValue="${bean.senduserid}--${bean.sendusername}"></apptag:approverselect>
					</tr>
					<tr class=trcolor>
						<apptag:approverselect inputName="readerid" label="抄送人"
							notAllowName="approverid" spanId="readerSpan"></apptag:approverselect>
					</tr>
				</c:if>
			</table>
			<table align="center">
				<tr>
					<td>
						<html:submit styleClass="button">提交</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">重置</html:reset>
					</td>
					<td>
						<input type="button" value="返回" class="button" onclick="goBack()">
					</td>
				</tr>
			</table>
		</html:form>
		<script type="text/javascript">
		listSignInTask();
		</script>
	</body>
</html>
