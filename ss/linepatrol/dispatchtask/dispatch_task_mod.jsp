<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<SCRIPT language=JavaScript src="${ctx}/common/PopupDlg.js" type=""></SCRIPT>
<SCRIPT language=javascript src="${ctx}/common/Comm.js" type=""></SCRIPT>
<html>
	<head>
		<title>sendtask</title>
		<script type="" language="javascript">
		goBack=function(){
			var url="${sessionScope.S_BACK_URL}";
			location=url;
		}
	function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
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


    function onSubmitClick(){
	 DispatchTaskBean.acceptdeptid.value=DispatchTaskBean.dept_id.value;
	 DispatchTaskBean.acceptuserid.value=DispatchTaskBean.user_id.value;
	 DispatchTaskBean.mobileId.value=DispatchTaskBean.mobile_id.value;
      if(DispatchTaskBean.sendtype.value==null||DispatchTaskBean.sendtype.value==""||DispatchTaskBean.sendtype.value.trim()==""){
        alert("请输入工作类别！");
        return;
      }
	  
      if(DispatchTaskBean.begintime.value == null || DispatchTaskBean.begintime.value == ""){
      	alert("请输入处理期限的日期！");
        return;
      }
      if(DispatchTaskBean.time.value == null || DispatchTaskBean.time.value == ""){
      	alert("请输入处理期限的时间！");
        return;
      }
		
	
      if(valCharLength(DispatchTaskBean.sendtext.value)>1020){
        alert("任务说明字数太多！不能超过510个汉字");
        return;
      }
      if(DispatchTaskBean.sendtopic.value==null||DispatchTaskBean.sendtopic.value==""||DispatchTaskBean.sendtopic.value.trim()==""){
        alert("请输入任务主题！");
        return;
      }
      if(DispatchTaskBean.acceptuserid.value==null||DispatchTaskBean.acceptuserid.value==""||DispatchTaskBean.acceptuserid.value.trim()==""){
		alert("请选择受理人！");
        return;
	  }
      if((DispatchTaskBean.sendtext.value==null||DispatchTaskBean.sendtext.value==""||DispatchTaskBean.sendtext.value.trim()=="")){
        alert("请输入任务说明！");
        return;
      }
		var allCheck = document.getElementsByName('ifCheck');
		var length = allCheck.length;
		
		for(var i = 0; i < length; i++) {
			var index = i + 1;
			var pathText = document.getElementById('uploadFile[' + index + '].file');
			var path = pathText.value;
			if(path.length == 0) {
				alert("请选择要上传的附件的路径，\n如果没有要上传的附件请删除!");
				pathText.focus();
				return false;
			}
		}

    	DispatchTaskBean.processterm.value = DispatchTaskBean.begintime.value + " " + DispatchTaskBean.time.value;
        DispatchTaskBean.submit();
    }
		</script>
	</head>
	<body>
		<!--修改显示-->
		<br>
		<template:titile value="修改派单" />
		<html:form
			action="/dispatchtask/dispatch_task.do?method=updateDispatchTask"
			styleId="addApplyForm" enctype="multipart/form-data">
			<html:hidden property="id" name="bean" />
			<html:hidden property="result" name="bean" />
			<html:hidden property="sendacce" name="bean" />
			<html:hidden property="processterm" value="" />
			<table width="90%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr class=trcolor>
					<td class="tdr" width="15%">
						派单流水号：
					</td>
					<td class="tdl" width="35%">
						<bean:write name="bean" property="serialnumber" />
						<input type="hidden" name="acceptdeptid"
							value="<bean:write name="bean" property="acceptdeptid"/>" />
						<input type="hidden" name="acceptuserid"
							value="<bean:write name="bean" property="acceptuserid"/>" />
						<input type="hidden" name="mobileId" value="" />
					</td>
					<td class="tdr" width="15%">
						工作类别：
					</td>
					<td class="tdl" width="35%">
						<c:if test="${sessionScope.LOGIN_USER.deptype=='1'}">
							<apptag:quickLoadList cssClass="inputtext" name="sendtype"
								listName="dispatch_task" type="select" />
						</c:if>
						<c:if test="${sessionScope.LOGIN_USER.deptype=='2'}">
							<apptag:quickLoadList cssClass="inputtext" name="sendtype"
								listName="dispatch_task_con" type="select" />
						</c:if>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						派单部门：
					</td>
					<td class="tdl">
						<bean:write name="bean" property="senddeptname" />
					</td>
					<td class="tdr">
						派单人：
					</td>
					<td class="tdl">
						<bean:write name="bean" property="sendusername" />
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
						<html:text property="sendtopic" name="bean" style="width:82%"
							styleClass="inputtext" maxlength="100" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						任务说明：
					</td>
					<td class="tdl" colspan="3">
						<html:textarea property="sendtext" alt=" 任务说明最长510个汉字！"
							name="bean" style="width:82%" rows="5" styleClass="textarea"></html:textarea>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						反馈要求：
					</td>
					<td class="tdl" colspan="3">
						<html:textarea property="replyRequest" alt="反馈要求最长510个汉字！"
							name="bean" style="width:82%" rows="5" styleClass="textarea"></html:textarea>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						处理时限：
					</td>
					<td class="tdl" colspan="3">
						<bean:define id="temp" name="bean" property="processterm"
							type="java.lang.String" />
						<input type="text" id="protimeid" name="begintime"
							value="<%=temp.substring(0, 10)%>" class="Wdate"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1'})"
							style="width: 100" />
						<html:text value="<%=temp.substring(11, 16)%>" property="time"
							styleClass="Wdate" style="width:60" maxlength="45"
							onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" readonly="true" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						任务附件：
					</td>
					<td class="tdl" colspan="3">
						<apptag:upload cssClass="" entityId="${bean.id }"
							entityType="LP_SENDTASK" state="edit" />
					</td>
				</tr>
			</table>
			<table align="center" style="border:0px;">
				<tr>
					<td class="tdc" style="border:0px;">
						<input type="hidden" id="maxSeq" value="0">
						<html:button styleClass="button" onclick="onSubmitClick()"
							property="action">提交</html:button>
						<html:reset styleClass="button">重置</html:reset>
						<input type="button" class="button" onclick="goBack()" value="返回">
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
