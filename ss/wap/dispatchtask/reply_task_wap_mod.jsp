<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/wap/header.jsp"%>
<html>
	<head>
		<title>sendtask</title>
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
 function toUpForm(id,workstate,subid){
    //if((workstate !="������" || workstate == "����֤")){
	 	var url = "${ctx}/ReplyAction.do?method=showOneToUp&id=" + id+"&subid="+subid;
	    window.location.href=url;
  	//}else
   // alert("���ɵ��浵,�����޸�!");
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
		</script>
	</head>
	<body>
		<!--��ʾ���޸ĵ��ɵ�������ϸ��Ϣ-->
		<br>
		<template:titile value="�޸��ɵ�����" />
		<html:form action="/dispatchtask/reply_task.do?method=updateReplyTask"
			styleId="addApplyForm" onsubmit="return validate(this)"
			enctype="multipart/form-data">
			<input name="env" value="${env }" type="hidden" />
			<html:hidden property="sendtopic" name="bean" />
			<input type="hidden" name="sendacceptdeptid"
				value="<bean:write name="bean" property="subtaskid"/>" />
			<input type="hidden" name="id"
				value="<bean:write  property="id" name="replybean"/>" />
			<input type="hidden" name="replyid"
				value="<bean:write  property="id" name="replybean"/>" />
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
				<html:select property="replyresult"
					style="width:100" styleClass="inputtext">
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
				</html:select>
				<br />
				����ʱ�䣺
				<bean:write property="replytime" name="replybean" />
				<input name="replytime" type="hidden"
					value="<bean:write property="replytime" name="replybean"/>" />
				<br />
				������ע��
				<html:textarea property="replyremark" name="replybean"
					style="width:80%" title="�벻Ҫ����256�����ֻ���512��Ӣ���ַ������򽫽ضϡ�" rows="5"
					styleClass="textarea"></html:textarea>
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
				<html:submit>�ύ</html:submit>
				<input type="button" onclick="goBack()" value="���ش���">
			</p>
		</html:form>
		<script type="text/javascript">
		for(i=0;i<document.forms[0].replyresult.options.length;i++){
			if(document.forms[0].replyresult.options[i].value=="${replybean.replyresult}"){
				document.forms[0].replyresult.options[i].selected=true;
				break;
			}
		}
		</script>
	</body>
</html>
