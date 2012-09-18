<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<html>
	<head>
		<title>�ƶ����Ϸ���</title>
		<style type="text/css">
			.tablelist{
				border-style:solid;
				border-color:#9A9A9A;
				border-width:1px 0 0 0;
				width:100%;
				border-collapse:collapse; 
				border-spacing:0; 
			}
			.tablelisttd{
				padding: 0; 
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
					editsafeguardform.submit();
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
				//value){
					alert("�����ƶ�һ��������Ѳ�ƻ���");
					return;
				}
				if(getTrimValue("approveId").length==0){
					alert("����ѡ��һ������ˣ�");
					return;
				}
				editsafeguardform.submit();
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
			function addSpecPlan1(){
				var safeguardName=document.getElementById("safeguardName").value;
				var planId=document.getElementById("id").value;
				var startDate=document.getElementById("startDate").value;
				var endDate=document.getElementById("endDate").value;
				window.open("${ctx}/specialplan.do?method=addFrom&ptype=002&isApply=false&pName="+safeguardName+"&id="+planId+"&startDate="+startDate+"&endDate="+endDate);
			}
			function addSpecPlan(){
				var safeguardName=document.getElementById("safeguardName").value;
				var planId=document.getElementById("id").value;
				var startDate=document.getElementById("startDate").value;
				var endDate=document.getElementById("endDate").value;
				var url = "${ctx}/specialplan.do?method=addFrom&ptype=002&isApply=false&pName="+safeguardName+"&id="+planId+"&startDate="+startDate+"&endDate="+endDate;
				//var url = '${ctx}/hiddangerAction.do?method=viewHiddanger&id='+id;
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
			var spplanId="";
			function deleteProblem(businessId,idValue){    
	      		if(confirm("ȷ��Ҫɾ����?")){
	      			spplanId=idValue;
	       			var params = "&spplanId="+idValue+"&businessId="+businessId;
	  				var url = "${ctx}/specialplan.do?method=deleteSpecialPlan&ptype=002";
	  				var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callback,asynchronous:true}); 
	      		}
	     	}
	     	function callback(originalRequest) {
				var rst=originalRequest.responseText;
		  		$('spplanValue').update(rst);
			}
			
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
		</script>
	</head>
	<body onload="loadData()">
		<c:if test="${empty flag}">
			<template:titile value="�޸ı��Ϸ���"/>
		</c:if>
		<c:if test="${not empty flag}">
			<template:titile value="��ӱ��Ϸ���"/>
		</c:if>
		<jsp:include page="safeguard_task_base.jsp"></jsp:include>
		<html:form action="/safeguardPlanAction.do?method=editSafeguardPlan" styleId="editsafeguardform" enctype="multipart/form-data">
			<input type="hidden" value="${safeguardPlan.id }" id="id" name="id"/>
			<input type="hidden" value="${saveoreditflag }" name="saveoreditflag"/>
			<input type="hidden" name="safeguardName" id="safeguardName" value="${safeguardTask.safeguardName }"/>
			<fmt:formatDate  value="${safeguardTask.startDate}" pattern="yyyy/MM/dd HH:mm:ss" var="formatStartDate"/>
			<fmt:formatDate  value="${safeguardTask.endDate}" pattern="yyyy/MM/dd HH:mm:ss" var="formatEndDate"/>
			<input type="hidden" name="startDate" id="startDate" value="${formatStartDate }"/>
			<input type="hidden" name="endDate" id="endDate" value="${formatEndDate }"/>
			<input type="hidden" value="${safeguardPlan.safeguardId }" name="safeguardId"/>
			<input type="hidden" value="${safeguardPlan.contractorId }" name="contractorId"/>
			<input type="hidden" value="${safeguardPlan.auditingNum }" name="auditingNum"/>
			<input type="hidden" value="${safeguardPlan.specialPlanId }" name="specialPlanId"/>
			<input type="hidden" value="<bean:write name='safeguardPlan' property='deadline'  format='yyyy/MM/dd HH:mm:ss'/>" name="deadline"/>
			<table align="center" cellpadding="1" cellspacing="0" style="border-top:0px;" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�漰�����м̶Σ�</td>
					<td class="tdulright">
						<c:if test="${safeguardPlan.isAllNet==0 || safeguardPlan.isAllNet==null}">
							<input type="radio" id="isallnet_id" name="isAllNet" value="0" checked="checked"/>��ȫ������<input type="radio" id="isallnet_id"  name="isAllNet" value="1"/>ȫ������
						</c:if>
						<c:if test="${safeguardPlan.isAllNet==1}">
							<input type="radio" id="isallnet_id"  name="isAllNet" value="0"/>��ȫ������<input type="radio" id="isallnet_id"  name="isAllNet" value="1"  checked="checked"/>ȫ������
						</c:if>
						<font color="red">ȫ�����ϲ���Ҫѡ���м̶���Ϣ����ȫ��������Ҫѡ���漰�м̶���Ϣ</font><br>
						<apptag:trunk id="trunk" state="edit" value="${sublineIds }" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ƻ�������Ա��</td>
					<td class="tdulright">
						<input type="text" id="planResponder" value="${safeguardPlan.planResponder }" name="planResponder" class="inputtext" style="width:150px;" /> ��
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ƻ�����������</td>
					<td class="tdulright">
						<input type="text" id="planRespondingUnit" value="${safeguardPlan.planRespondingUnit }" name="planRespondingUnit" class="inputtext" style="width:150px;" /> ��
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ƻ�Ͷ���豸����</td>
					<td class="tdulright">
						<input type="text" id="equipmentNumber" value="${safeguardPlan.equipmentNumber }" name="equipmentNumber" class="inputtext" style="width:150px;" /> ��
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">��ע��</td>
					<td class="tdulright">
						<textarea class="textarea" rows="3" id="remark" name="remark"><c:out value="${safeguardPlan.remark }"/></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				
				<tr class="trcolor">
					<td class="tdulleft">
						��Ѳ�ƻ�
					</td>
					<td class="tdulright" colspan="3">
						<a style="cursor: pointer;color: blue;" onclick="addSpecPlan()">�����Ѳ�ƻ�</a><font color="red">*</font><br/>
						<div id="spplanValue">
						</div>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">����������</td>
					<td class="tdulright">
						<apptag:upload cssClass="" entityId="${safeguardPlan.id }" entityType="LP_SAFEGUARD_PLAN" state="edit"/>
					</td>
				</tr>
				<c:if test="${not empty approveMan}">
					<tr class="trcolor">
						<apptag:approverselect inputName="approveId,mobiles" 
								colSpan="2" inputType="radio" label="�����" notAllowName="reader"
								approverType="approver" objectId="${safeguardPlan.id }" objectType="LP_SAFEGUARD_PLAN" />
					</tr>
					<tr class="trcolor"">
						<apptag:approverselect label="������" inputName="reader,readerPhones"
								spanId="readerSpan" notAllowName="approveId" colSpan="2" 
								approverType="reader" objectId="${safeguardPlan.id }" objectType="LP_SAFEGUARD_PLAN" />
					</tr>
				</c:if>
				<c:if test="${empty approveMan}">
					<tr class="trcolor">
						<apptag:approverselect inputName="approveId,mobiles" colSpan="2" notAllowName="reader" 
							inputType="radio" label="�����" existValue="${approveInfo[0]}--${approveInfo[1]}--${approveInfo[2]}"/>
					</tr>
					<tr class="trcolor"">
						<apptag:approverselect label="������" inputName="reader,readerPhones"
								spanId="readerSpan" notAllowName="approveId" colSpan="2" 
								approverType="reader" />
					</tr>
				</c:if>
			</table>
			<div style="height:20px; margin-left: 5%; width: 95%; margin-top: 5px;">
				<font color="red">�������Ϊ���������������������д���ޡ�</font>
			</div>
			<div align="center" style="height:40px">
				<input type="hidden" name="saveflag" id="saveflag" value="0">
				<html:button property="action" styleClass="button" onclick="checkForm(0)">�ύ</html:button> &nbsp;&nbsp;
				<html:button property="action" styleClass="button" onclick="checkForm(1)">�ݴ�</html:button>&nbsp;&nbsp;
				<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
			</div>
		</html:form>
	</body>
</html>
