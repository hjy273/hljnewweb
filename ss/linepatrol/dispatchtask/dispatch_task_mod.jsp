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
        alert("�����빤�����");
        return;
      }
	  
      if(DispatchTaskBean.begintime.value == null || DispatchTaskBean.begintime.value == ""){
      	alert("�����봦�����޵����ڣ�");
        return;
      }
      if(DispatchTaskBean.time.value == null || DispatchTaskBean.time.value == ""){
      	alert("�����봦�����޵�ʱ�䣡");
        return;
      }
		
	
      if(valCharLength(DispatchTaskBean.sendtext.value)>1020){
        alert("����˵������̫�࣡���ܳ���510������");
        return;
      }
      if(DispatchTaskBean.sendtopic.value==null||DispatchTaskBean.sendtopic.value==""||DispatchTaskBean.sendtopic.value.trim()==""){
        alert("�������������⣡");
        return;
      }
      if(DispatchTaskBean.acceptuserid.value==null||DispatchTaskBean.acceptuserid.value==""||DispatchTaskBean.acceptuserid.value.trim()==""){
		alert("��ѡ�������ˣ�");
        return;
	  }
      if((DispatchTaskBean.sendtext.value==null||DispatchTaskBean.sendtext.value==""||DispatchTaskBean.sendtext.value.trim()=="")){
        alert("����������˵����");
        return;
      }
		var allCheck = document.getElementsByName('ifCheck');
		var length = allCheck.length;
		
		for(var i = 0; i < length; i++) {
			var index = i + 1;
			var pathText = document.getElementById('uploadFile[' + index + '].file');
			var path = pathText.value;
			if(path.length == 0) {
				alert("��ѡ��Ҫ�ϴ��ĸ�����·����\n���û��Ҫ�ϴ��ĸ�����ɾ��!");
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
		<!--�޸���ʾ-->
		<br>
		<template:titile value="�޸��ɵ�" />
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
						�ɵ���ˮ�ţ�
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
						�������
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
						�ɵ����ţ�
					</td>
					<td class="tdl">
						<bean:write name="bean" property="senddeptname" />
					</td>
					<td class="tdr">
						�ɵ��ˣ�
					</td>
					<td class="tdl">
						<bean:write name="bean" property="sendusername" />
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
						<html:text property="sendtopic" name="bean" style="width:82%"
							styleClass="inputtext" maxlength="100" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						����˵����
					</td>
					<td class="tdl" colspan="3">
						<html:textarea property="sendtext" alt=" ����˵���510�����֣�"
							name="bean" style="width:82%" rows="5" styleClass="textarea"></html:textarea>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						����Ҫ��
					</td>
					<td class="tdl" colspan="3">
						<html:textarea property="replyRequest" alt="����Ҫ���510�����֣�"
							name="bean" style="width:82%" rows="5" styleClass="textarea"></html:textarea>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						����ʱ�ޣ�
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
						���񸽼���
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
							property="action">�ύ</html:button>
						<html:reset styleClass="button">����</html:reset>
						<input type="button" class="button" onclick="goBack()" value="����">
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
