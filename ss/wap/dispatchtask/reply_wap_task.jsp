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
			<input name="env" value="${env }" type="hidden" />
			<html:hidden property="sendtopic" name="bean" />
			<input type="hidden" name="sendacceptdeptid"
				value="<bean:write name="bean" property="subtaskid"/>" />
			<p>
				�������⣺
				<bean:write property="sendtopic" name="bean" />
				<br />
				���������
				<apptag:quickLoadList cssClass="" name="" listName="dispatch_task"
					keyValue="${bean.sendtype}" type="look"></apptag:quickLoadList>
				<apptag:quickLoadList cssClass="" name=""
					listName="dispatch_task_con" keyValue="${bean.sendtype}"
					type="look"></apptag:quickLoadList>
				<br />
				����˵����
				<bean:write property="sendtext" name="bean" />
				<br />
				����Ҫ��
				<bean:write property="replyRequest" name="bean" />
				<br />
				����ʱ�ޣ�
				<bean:write property="processterm" name="bean" />
			</p>
			<p>
				���������
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
				<br />
				������ע��
				<html:textarea property="replyremark" style="width:80%"
					title="�벻Ҫ����256�����ֻ���512��Ӣ���ַ������򽫽ضϡ�" rows="5" styleClass="textarea"></html:textarea>
				<br />
				����ˣ�
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
				�����ˣ�
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
				<html:submit >�ύ</html:submit>
				<input type="button" value="���ش���"  onclick="goBack()">
			</p>
		</html:form>
	</body>
</html>
