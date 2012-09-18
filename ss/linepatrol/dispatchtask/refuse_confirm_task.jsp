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
			var url="${ctx}/dispatchtask/sign_in_task.do?method=refuseDispatchTaskForm";
			var queryString="dispatch_id=${dispatch_id}";
			location=url+"&"+queryString+"&rnd="+Math.random();
		}
		listRefuseConfirmTask=function(signInId){
			var url="${ctx}/dispatchtask/sign_in_task.do?method=queryForRefuseConfirmTask";
			var queryString="sign_in_id="+signInId;
			var actionUrl=url+"&"+queryString+"&rnd="+Math.random();
			var myAjax=new Ajax.Updater(
				"refuseConfirmTaskDiv",actionUrl,{
					method:"post",
					evalScripts:true
				}
			);
		}
		var win1;
		viewOneRefuseConfirmTask=function(refuseId){
			var actionUrl="${ctx}/dispatchtask/sign_in_task.do?method=viewRefuseConfirmTask";
			var queryString="refuse_id="+refuseId+"&dispatch_id=${dispatch_id}";
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
				title:"�鿴���񵥾�ǩȷ����ϸ��Ϣ" 
			});
			win1.show(Ext.getBody());
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


function validate(form){
  if(valCharLength(form.confirmRemark.value)>1020){
    alert("��˱�ע����̫�࣡���ܳ���510������");
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
}
		</script>
	</head>
	<body>
		<!--��ʾ�ɵ���֤����ϸ��Ϣ,������֤-->
		<template:titile value="���񵥾�ǩȷ��" />
		<html:form action="/dispatchtask/sign_in_task.do?method=refuseConfirmTask"
			styleId="addApplyForm" onsubmit="return validate(this);"
			enctype="multipart/form-data">
			<html:hidden property="sendtopic" name="bean" />
			<input type="hidden" name="dispatch_id" value="${dispatch_id }" />
			<input type="hidden" name="sendtaskid" value="${dispatch_id }" />
			<input type="hidden" name="sendacceptdeptid"
				value="<bean:write name="bean" property="subtaskid"/>" />
			<input type="hidden" name="signInId"
				value="<bean:write name="endorsebean" property="id"/>" />
			<input type="hidden" name="refuseUserId"
				value="<bean:write name="endorsebean" property="userid"/>" />
			<table width="90%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr class=trcolor>
					<td class="tdr" width="15%" >
						��ǩ�ˣ�
					</td>
					<td class="tdl" width="35%">
						<bean:write name="endorsebean" property="endorseusername" />
					</td>
					<td class="tdr" width="15%">
						��ǩʱ�䣺
					</td>
					<td class="tdl" width="35%">
						<bean:write property="time" name="endorsebean" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						��ע��
					</td>
					<td class="tdl" colspan="3">
						<bean:write property="remark" name="endorsebean" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdl"  colspan="4" id="refuseConfirmTaskDiv">
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						��ǩȷ�Ͻ����
					</td>
					<td class="tdl" colspan="3">
						<input name="confirmResult" value="0" type="radio" checked />
						ͨ��
						<input name="confirmResult" value="1" type="radio" />
						δͨ��
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" id="remarkLabel" >
						��ǩȷ�ϱ�ע��
					</td>
					<td class="tdl" colspan="3">
						<html:textarea property="confirmRemark"
							title="�벻Ҫ����256�����ֻ���512��Ӣ���ַ������򽫽ضϡ�" style="width:80%" rows="5"
							styleClass="textarea"></html:textarea>
					</td>
				</tr>
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
						<input type="button" value="����" class="button" onclick="goBack();">
					</td>
				</tr>
			</table>
		</html:form>
		<script type="text/javascript">
		listRefuseConfirmTask('${endorsebean.id}');
		</script>
	</body>
</html>
