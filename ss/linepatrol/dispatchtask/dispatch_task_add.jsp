<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<SCRIPT language=JavaScript src="${ctx}/common/PopupDlg.js" type=""></SCRIPT>
<SCRIPT language=javascript src="${ctx}/common/Comm.js" type=""></SCRIPT>
<html>
	<head>
		<title>sendtask</title>
		<script type="" language="javascript">
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

 //选择日期
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

	function checkSub() {
	 DispatchTaskBean.acceptdeptid.value=DispatchTaskBean.dept_id.value;
	 DispatchTaskBean.acceptuserid.value=DispatchTaskBean.user_id.value;
	 DispatchTaskBean.mobileId.value=DispatchTaskBean.mobile_id.value;
	 if(DispatchTaskBean.sendtype.value==null||DispatchTaskBean.sendtype.value==""||DispatchTaskBean.sendtype.value.trim()==""){
        alert("请输入工作类别！");
		document.getElementById("sendtype").focus();

        return;
		
      }
	  
      if(DispatchTaskBean.begintime.value == null || DispatchTaskBean.begintime.value == ""){
      	alert("请输入处理期限的日期！");
		document.getElementById("protimeid").focus();
        return;
      }
      if(DispatchTaskBean.time.value == null || DispatchTaskBean.time.value == ""){
      	alert("请输入处理期限的时间！");
		document.getElementById("time").focus();
        return;
      }
		
	  var d = new Date();
	  var s = '';
	  s += d.getYear() + "/"; 
      if((d.getMonth() + 1) > 9) {
		s += (d.getMonth() + 1) + "/";            // 获取月份。
	  } else {
		s += "0" + (d.getMonth() + 1) + "/";            // 获取月份。
	  }
	  
   	  // 获取日。
	  if(d.getDate() > 9) {
			s += d.getDate();
	  } else {
			s = s + "0" + d.getDate()
	  }

	  if(s > DispatchTaskBean.begintime.value) {
		alert("处理期限不能小于当前时间!");
		document.getElementById("protimeid").focus();
			return;
	  } 
// 时间的判断
	  if(s == DispatchTaskBean.begintime.value) {
	  		 var hour=d.getHours(); 
			 var minute=d.getMinutes(); 
			 var second=d.getSeconds(); 
			 if(hour > 9) {
			 	timestr = hour + ":";
			 } else {
			 	timestr = "0" + hour + ":";
			 }

             if(minute > 9) {
			 	timestr += minute + ":";
			 } else {
			 	timestr += "0" + minute + ":";
			 }

			if(second > 9) {
			 	timestr += second ;
			 } else {
			 	timestr += "0" + second ;
			 }
			 if(timestr > DispatchTaskBean.time.value) {
				document.getElementById("time").focus();
				alert("处理期限不能小于当前时间!");
				return;
			 }
	   }

      if(valCharLength(DispatchTaskBean.sendtext.value)>1020){
		document.getElementById("sendtext").focus();
        alert("任务说明字数太多！不能超过510个汉字");
        return;
      }
      if(DispatchTaskBean.sendtopic.value==null||DispatchTaskBean.sendtopic.value==""||DispatchTaskBean.sendtopic.value.trim()==""){
		document.getElementById("sendtopic").focus();
        alert("请输入任务主题！");
        return;
      }
      if(DispatchTaskBean.acceptuserid.value==null||DispatchTaskBean.acceptuserid.value==""||DispatchTaskBean.acceptuserid.value.trim()==""){
		alert("请选择受理人！");
        return;
	  }
      if((DispatchTaskBean.sendtext.value==null||DispatchTaskBean.sendtext.value==""||DispatchTaskBean.sendtext.value.trim()=="")){
		document.getElementById("sendtext").focus();
        alert("请输入任务说明！");
        return;
      }
	  var allCheck = document.getElementsByName('ifCheck');
	  var length = allCheck.length;
	  //alert(length);
	  for(var i = 0; i < length; i++) {		
		//var index = i + 1;
		var index = allCheck[i].value;
		//alert(index)
		var pathText = document.getElementById('uploadFile[' + index + '].file');
		//alert(pathText);
		var path = pathText.value;
		//alert(path);
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
		<br>
		<template:titile value="指派任务" />
		<html:form action="/dispatchtask/dispatch_task.do?method=dispatchTask"
			styleId="addApplyForm" onsubmit="return validate(this);"
			enctype="multipart/form-data">
			<html:hidden property="processterm" value="" />
			<table id="tableID" width="90%" border="0" align="center"
				cellpadding="3" cellspacing="0" class="tabout">
				<tr class=trcolor>
					<td class="tdr" width="15%">
						工作类别：
					</td>
					<td class="tdl" colspan="3">
						<c:if test="${sessionScope.LOGIN_USER.deptype=='1'}">
							<apptag:quickLoadList cssClass="inputtext" name="sendtype"
								id="sendtype" listName="dispatch_task" type="select" />
						</c:if>
						<c:if test="${sessionScope.LOGIN_USER.deptype=='2'}">
							<apptag:quickLoadList cssClass="inputtext" name="sendtype"
								id="sendtype" listName="dispatch_task_con" type="select" />
						</c:if>
						<input type="hidden" name="acceptdeptid" value="" />
						<input type="hidden" name="acceptuserid" value="" />
						<input type="hidden" name="mobileId" value="" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" width="15%">
						派单部门：
					</td>
					<td class="tdl" width="35%">
						<bean:write name="LOGIN_USER" property="deptName" />
					</td>
					<td class="tdr" width="15%">
						派单人：
					</td>
					<td class="tdl" width="35%">
						<bean:write name="LOGIN_USER" property="userName" />
					</td>
				</tr>
				<c:if test="${sessionScope.LOGIN_USER.deptype=='1'}">
					<apptag:processorselect inputName="dept_id,user,mobile_id,user_id"
						spanId="userSpan" />
				</c:if>
				<c:if test="${sessionScope.LOGIN_USER.deptype=='2'}">
					<apptag:processorselect inputName="dept_id,user,mobile_id,user_id"
						spanId="userSpan" displayType="mobile" inputType="radio" />
				</c:if>
				<tr class=trcolor>
					<td class="tdr">
						任务主题：
					</td>
					<td class="tdl" colspan="3">
						<html:text property="sendtopic" style="width:82%"
							styleId="sendtopic" styleClass="inputtext" maxlength="100" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						任务说明：
					</td>
					<td class="tdl" colspan="3">
						<html:textarea property="sendtext" alt=" 任务说明最长510个汉字！"
							styleId="sendtext" style="width:82%" rows="5"
							styleClass="textarea"></html:textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						反馈要求：
					</td>
					<td class="tdl" colspan="3">
						<html:textarea property="replyRequest" alt="反馈要求最长510个汉字！"
							styleId="replyRequest" style="width:82%" rows="5"
							styleClass="textarea"></html:textarea>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						处理时限：
					</td>
					<td class="tdl" colspan="3">
						<input type="text" id="protimeid" name="begintime" value=""
							readonly="readonly" class="Wdate"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})"
							style="width: 100" />
						<html:text property="time" styleClass="Wdate" styleId="time"
							onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" style="width:60"
							maxlength="45" readonly="true" />
						<font color="red">*</font>

					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						任务附件：
					</td>
					<td class="tdl" colspan="3">
						<!-- <table id="uploadID" width=520 border="0" align="left" cellpadding="3" cellspacing="0" >
					          <tbody></tbody>
	                     </table> -->
						<apptag:upload cssClass="" entityId="" entityType="LP_SENDTASK"
							state="add" />
					</td>
				</tr>
			</table>
			<table align="center" style="border: 0px;">
				<tr height="50">
					<td class="tdc" style="border: 0px;">
						<html:button styleClass="button" onclick="checkSub()"
							property="action">提交</html:button>
						<html:reset styleClass="button">重置</html:reset>
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
