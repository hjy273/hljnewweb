<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/wap/header.jsp"%>
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
			<input name="env" value="${env }" type="hidden" />
			<html:hidden property="sendtopic" name="bean" />
			<input type="hidden" name="sendacceptdeptid"
				value="<bean:write name="bean" property="subtaskid"/>" />
			<p>
				任务主题：
				<bean:write property="sendtopic" name="bean" />
				<br />
				工作单类别：
				<apptag:quickLoadList cssClass="" name="" listName="dispatch_task"
					keyValue="${bean.sendtype}" type="look"></apptag:quickLoadList>
				<apptag:quickLoadList cssClass="" name=""
					listName="dispatch_task_con" keyValue="${bean.sendtype}"
					type="look"></apptag:quickLoadList>
				<br />
				任务说明：
				<bean:write property="sendtext" name="bean" />
				<br />
				反馈要求：
				<bean:write property="replyRequest" name="bean" />
				<br />
				处理时限：
				<bean:write property="processterm" name="bean" />
			</p>
			<p>
				反馈结果：
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
				<br />
				反馈备注：
				<html:textarea property="replyremark" style="width:80%"
					title="请不要超过256个汉字或者512个英文字符，否则将截断。" rows="5" styleClass="textarea"></html:textarea>
				<br />
				审核人：
				<c:if test="${sessionScope.APPROVER_INPUT_NAME_MAP!=null}">
					<c:forEach var="oneItem" items="${sessionScope.APPROVER_INPUT_NAME_MAP}">
						<c:if test="${oneItem.key!='span_value'}">
							<input name="${oneItem.key }" value="${oneItem.value }"
								type="hidden" />
						</c:if>
						<c:if test="${oneItem.key=='span_value'}">
							${oneItem.value }
						</c:if>
					</c:forEach>
				</c:if>
				<br />
				抄送人：
				<c:if test="${sessionScope.READER_INPUT_NAME_MAP!=null}">
					<c:forEach var="oneItem" items="${sessionScope.READER_INPUT_NAME_MAP}">
						<c:if test="${oneItem.key!='span_value'}">
							<input name="${oneItem.key }" value="${oneItem.value }"
								type="hidden" />
						</c:if>
						<c:if test="${oneItem.key=='span_value'}">
							${oneItem.value }
						</c:if>
					</c:forEach>
				</c:if>
			</p>
			<p>
				<html:submit >提交</html:submit>
				<input type="button" value="返回待办"  onclick="goBack()">
			</p>
		</html:form>
	</body>
</html>
