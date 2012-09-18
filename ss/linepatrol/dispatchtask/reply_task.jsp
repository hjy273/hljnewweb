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
				title:"�鿴ǩ����ϸ��Ϣ" 
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
		alert("û��ѡ������ˣ�");
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
				alert("��ѡ��Ҫ�ϴ��ĸ�����·����\n���û��Ҫ�ϴ��ĸ�����ɾ��!");
				pathText.focus();
				return false;
			}
		}
  if(valCharLength(form.replyremark.value)>1020){
    alert("������ע����̫�࣡���ܳ���510������");
    return false;
  }
}
       //�����Ƿ�����
	 function valiD(id){
      	var mysplit = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
        var obj = document.getElementById(id);
        if(mysplit.test(obj.value)){
          return true;
        }
        else{
        	alert("����д�����ֲ��Ϸ�,����������");
            obj.focus();
            obj.value = "0.00";
            return false;
        }
    }
	function toForward(id, subid) {
		//if(loginUserid != acceptuserid) {
		//	alert("�Բ�����������ָ�������˲����������Բ���ת��");
		//	return;
		//}
		var url = "${ctx}/ReplyAction.do?method=showTaskForward&id=" + id +"&subid="+subid;
    	window.location.href=url;
	}
		</script>
	</head>
	<body>
		<!--�����ɵ�-->
		<br>
		<template:titile value="�ɵ�����" />
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
						�ɵ���ˮ�ţ�
					</td>
					<td class="tdl" width="35%">
						<bean:write property="serialnumber" name="bean" />
					</td>
					<td class="tdr" width="15%">
						���������
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
						�ɵ�ʱ�䣺
					</td>
					<td class="tdl">
						<bean:write property="sendtime" name="bean" />
					</td>
					<td class="tdr">
						�ɵ����ţ�
					</td>
					<td class="tdl">
						<bean:write name="bean" property="senddeptname" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
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
						<bean:write property="sendtopic" name="bean" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						����˵����
					</td>
					<td class="tdl" colspan="3">
						<bean:write property="sendtext" name="bean" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						����Ҫ��
					</td>
					<td class="tdl" colspan="3">
						<bean:write property="replyRequest" name="bean" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
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
						���������
					</td>
					<td class="tdl" colspan="3">
						<select name="replyresult" style="width: 180" class="inputtext">
							<option value="00">
								δ���
							</option>
							<option value="03">
								ȫ�����
							</option>
							<option value="02">
								�������
							</option>
							<option value="01">
								�������
							</option>
						</select>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						������ע��
					</td>
					<td class="tdl" colspan="3">
						<html:textarea property="replyremark" style="width:80%"
							title="�벻Ҫ����256�����ֻ���512��Ӣ���ַ������򽫽ضϡ�" rows="5"
							styleClass="textarea"></html:textarea>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						����������
					</td>
					<td class="tdl" colspan="3">
						<apptag:upload cssClass="" entityId=""
							entityType="LP_SENDTASKREPLY" state="add" />
					</td>
				</tr>
				<c:if test="${sessionScope.LOGIN_USER.deptype=='1'}">
					<tr class=trcolor>
						<apptag:approverselect inputName="approverid" label="�����"
							inputType="radio" displayType="contractor"
							departId="${bean.senddeptid}"
							existValue="${bean.senduserid}--${bean.sendusername}"></apptag:approverselect>
					</tr>
					<tr class=trcolor>
						<apptag:approverselect inputName="readerid" label="������"
							displayType="contractor" departId="${bean.senddeptid}"
							notAllowName="approverid" spanId="readerSpan"></apptag:approverselect>
					</tr>
				</c:if>
				<c:if test="${sessionScope.LOGIN_USER.deptype=='2'}">
					<tr class=trcolor>
						<apptag:approverselect inputName="approverid" label="�����"
							inputType="radio"
							existValue="${bean.senduserid}--${bean.sendusername}"></apptag:approverselect>
					</tr>
					<tr class=trcolor>
						<apptag:approverselect inputName="readerid" label="������"
							notAllowName="approverid" spanId="readerSpan"></apptag:approverselect>
					</tr>
				</c:if>
			</table>
			<table align="center">
				<tr>
					<td>
						<html:submit styleClass="button">�ύ</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">����</html:reset>
					</td>
					<td>
						<input type="button" value="����" class="button" onclick="goBack()">
					</td>
				</tr>
			</table>
		</html:form>
		<script type="text/javascript">
		listSignInTask();
		</script>
	</body>
</html>
