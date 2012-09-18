<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<html>
	<head>
		<title>�ƶ����Ϸ���</title>
		<style type="text/css">
			.tablelist {
				border-style:solid;
				border-color:#9A9A9A;
				border-width:1px 0px 0px 0px;
				width:100%;
				border-collapse:collapse; 
				border-spacing:0px; 
			}
			.tablelisttd{
				padding: 0px; 
				border-style : solid;
				border-width: 0 0 1px 0;
				border-color : #9A9A9A;
			}
		</style>
		<script type="text/javascript">
			function checkForm(state){
				if(state=="1"){
					if(valCharLength($("remark").value)>1024){
						alert("��ע���ܳ���1024���ַ���");
					    return;
					}
					$('saveflag').value="1";
					addsafeguardform.submit();
					return;
				}
				var isall = jQuery('input[name=isAllNet][checked]:radio').val();
				if(isall==0 && getTrimValue("trunk").length==0){
					alert("�м̶β���Ϊ�գ���ѡ���м̶Σ�");
					return;
				}	
				if(getTrimValue("planResponder").length==0){
					alert("�ƻ�������Ա������Ϊ�գ�");
					getElement("planResponder").focus();
					return;
				}
				if(valiD("planResponder")==false){
					alert("�ƻ�������Ա������Ϊ������");
					getElement("planResponder").focus();
					return;
				}
				if(getTrimValue("planRespondingUnit").length==0){
					alert("�ƻ���������������Ϊ�գ�");
					getElement("planRespondingUnit").focus();
					return;
				}
				if(valiD("planRespondingUnit")==false){
					alert("�ƻ���������������Ϊ������");
					getElement("planRespondingUnit").focus();
					return;
				}
				if(getTrimValue("equipmentNumber").length==0){
					alert("�ƻ�Ͷ���豸������Ϊ�գ�");
					getElement("equipmentNumber").focus();
					return;
				}
				if(valiD("equipmentNumber")==false){
					alert("�ƻ�Ͷ���豸������Ϊ������");
					getElement("equipmentNumber").focus();
					return;
				}
				if(getTrimValue("remark").length==0){
					alert("��ע����Ϊ�գ�");
					getElement("remark").focus();
					return;
				}
				if(valCharLength($("remark").value)>1024){
					alert("��ע���ܳ���1024���ַ���");
				    return;
				}
				if(document.getElementById("spplanValue").hasChildNodes()==false){
					alert("�����ƶ�һ��������Ѳ�ƻ���");
					return;
				}
				if(getTrimValue("approveId").length==0){
					alert("����ѡ��һ������ˣ�");
					return;
				}
				//$('tempEditFlag').vlaue="0";
				addsafeguardform.submit();
			}
			function getTrimValue(value){
				return document.getElementById(value).value.trim();
			}
			String.prototype.trim = function() {
				return this.replace(/^\s+|\s+$/g,"");
			}
			function getElement(value){
				return document.getElementById(value);
			}
			//�ж��Ƿ�Ϊ����
			function valiD(id){
				var mysplit = /^\d{1,20}$/;
				var obj = document.getElementById(id);
				if(mysplit.test(obj.value)){
					//return true;
				}
				else{
					obj.focus();
					return false;
				}
			}
			//�ж��ַ�����
			function valCharLength(Value){
				var j=0;
				var s = Value;
				for(var i=0; i<s.length; i++) {
					if (s.substr(i,1).charCodeAt(0)>255) {
						j  = j + 2;
					} else { 
						j++;
					}
				}
				return j;
			}
			//ɾ����Ѳ�ƻ���̬ˢ������
			function deleteProblem(businessId,idValue){    
	      		if(confirm("ȷ��Ҫɾ����?")){
	       			var params = "&spplanId="+idValue+"&businessId="+businessId;
	  				var url = "${ctx}/specialplan.do?method=deleteSpecialPlan&ptype=002";
	  				var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callback,asynchronous:true}); 
	      		}
	     	}
	     	function callback(originalRequest) {
				var rst=originalRequest.responseText;
		  		$('spplanValue').update(rst);
			}
			//��ʾ������Ѳ�ƻ�
			function showSpecPlan(spplanId){
				var url = "${ctx}/specialplan.do?method=loadPlan&ptype=002&id="+spplanId+"&type=view"+"&isApply=false&flag=safeguard";
				win = new Ext.Window({
					layout : 'fit',
					width:650,
					height:400,
					resizable:true,
					closeAction : 'close',
					modal:true,
					html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
					plain: true
				});
				win.show(Ext.getBody());
			}
			//�޸ı�����Ѳ�ƻ�
			function editSpecPlan(businessId,spplanId){
				var url = "${ctx}/specialplan.do?method=loadPlan&ptype=002&id="+spplanId+"&businessId="+businessId+"&type=edit"+"&isApply=false";
				win = new Ext.Window({
					layout : 'fit',
					width:650,
					height:400,
					resizable:true,
					closeAction : 'close',
					modal:true,
					html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
					plain: true
				});
				win.show(Ext.getBody());
			}
			//��ӱ�����Ѳ�ƻ�
			function addSpecPlan(){
				var safeguardName=document.getElementById("safeguardName").value;
				var planId=document.getElementById("id").value;
				var startDate=document.getElementById("startDate").value;
				var endDate=document.getElementById("endDate").value;
				var url = "${ctx}/specialplan.do?method=addFrom&ptype=002&isApply=false&pName="+safeguardName+"&id="+planId+"&startDate="+startDate+"&endDate="+endDate;
				win = new Ext.Window({
					layout : 'fit',
					width:650,
					height:400,
					resizable:true,
					closeAction : 'close',
					modal:true,
					html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
					plain: true
				});
				win.show(Ext.getBody());
			}
			//ҳ�����ʱ��̬������Ѳ�ƻ���Ϣ
			function loadData(){
				var businessId = document.getElementById("id").value;
				var spplanValue = document.getElementById("spplanValue");
				var url = 'safeguardPlanAction.do?method=loadDataSpPlan&businessId='+businessId+"&ptype=002";
				new Ajax.Request(url,{
			       	method:'post',
			       	evalScripts:true,asynchronous:false,
			       	onSuccess:function(transport){
			       		spplanValue.innerHTML=transport.responseText;
			       	},
			       	onFailure: function(){ alert('��������쳣......') }
			 	});
			}
		</script>
	</head>
	<body onload="loadData()">
		<template:titile value="�ƶ����Ϸ���"/>
		<jsp:include page="safeguard_task_base.jsp"></jsp:include>
		<html:form action="/safeguardPlanAction.do?method=addSafeguardPlan" enctype="multipart/form-data" styleId="addsafeguardform">
			<input type="hidden" name="safeguardId" id="safeguardId" value="${safeguardTask.id }"/>
			<input type="hidden" name="id" id="id" value="${safeguardPlan.id }"/>
			<input type="hidden" name="safeguardName" id="safeguardName" value="${safeguardTask.safeguardName }"/>
			<fmt:formatDate  value="${safeguardTask.startDate}" pattern="yyyy/MM/dd HH:mm:ss" var="formatStartDate"/>
			<fmt:formatDate  value="${safeguardTask.endDate}" pattern="yyyy/MM/dd HH:mm:ss" var="formatEndDate"/>
			<input type="hidden" name="startDate" id="startDate" value="${formatStartDate }"/>
			<input type="hidden" name="endDate" id="endDate" value="${formatEndDate }"/>
			<table align="center" style="border-top: 0px;" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�漰�����м̶Σ�</td>
					<td class="tdulright">
						<input type="radio" name="isAllNet" value="0" checked="checked"/>��ȫ������<input type="radio" name="isAllNet" value="1"/>ȫ������
						<font color="red">ȫ�����ϲ���Ҫѡ���м̶���Ϣ����ȫ��������Ҫѡ���漰�м̶���Ϣ</font><br>
						<apptag:trunk id="trunk" state="add" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ƻ�������Ա��</td>
					<td class="tdulright">
						<input type="text" id="planResponder" name="planResponder" class="inputtext" style="width:150px;" /> ��
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ƻ�����������</td>
					<td class="tdulright">
						<input type="text" id="planRespondingUnit" name="planRespondingUnit" class="inputtext" style="width:150px;" /> ��
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ƻ�Ͷ���豸����</td>
					<td class="tdulright">
						<input type="text" id="equipmentNumber" name="equipmentNumber" class="inputtext" style="width:150px;" /> ��
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">��ע��</td>
					<td class="tdulright">
						<textarea class="textarea" rows="3" id="remark" name="remark"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				
				<tr class="trcolor">
					<td class="tdulleft">��Ѳ�ƻ�</td>
					<td class="tdulright">
						<a style="cursor: pointer;color: blue;" onclick="addSpecPlan()">�����Ѳ�ƻ�</a><font color="red">*</font><br/>
						<div id="spplanValue"></div>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">����������</td>
					<td class="tdulright">
						<apptag:upload cssClass="" entityId="" entityType="LP_SAFEGUARD_PLAN" state="add"/>
					</td>
				</tr>
				<tr class="trcolor">
					<c:if test="${empty approveInfo}">
						<apptag:approverselect inputName="approveId,mobiles" colSpan="2" notAllowName="reader" inputType="radio" label="�����"/>
					</c:if>
					<c:if test="${not empty approveInfo}">
						<apptag:approverselect inputName="approveId,mobiles" colSpan="2" notAllowName="reader" inputType="radio" label="�����" existValue="${approveInfo[0]}--${approveInfo[1] }--${approveInfo[2] }"/>
					</c:if>
				</tr>
				<tr class="trcolor">
					<apptag:approverselect label="������" inputName="reader,readerPhones"
							spanId="readerSpan" notAllowName="approveId" colSpan="2" />
				</tr>
			</table>
			<div style="height:20px; margin-left: 5%; width: 95%; margin-top: 5px;">
				<font color="red">�������Ϊ���������������������д���ޡ�</font>
			</div>
			<div align="center" style="height:40px">
				<input type="hidden" name="saveflag" id="saveflag" value="0">
				<html:button property="action" styleClass="button" onclick="checkForm(0)">�ύ</html:button> &nbsp;&nbsp;
				<html:button property="action" styleClass="button" onclick="checkForm(1)">�ݴ�</html:button> &nbsp;&nbsp;
				<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
			</div>
		</html:form>
	</body>
</html>
