<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
	<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	
	<script type="text/javascript">
		function getVal(name){
			var inputs = document.getElementsByName(name);
			for(var i=0 ; i<inputs.length ; i++){
				if(inputs[i].checked){
					return inputs[i].value;
				}
			}
		}
		function setVal(name, val){
			var inputs = document.getElementsByName(name);
			for(var i=0 ; i<inputs.length ; i++){
				if(inputs[i].value == val){
					inputs[i].checked = true;
				}else{
					inputs[i].checked = false;
				}
			}
		}
		function check(){
			if(getVal('isEligible0')==0 && $('eligibleReason0').value==''){
				alert('���ϲ��ϸ�ԭ���Ǳ�����');
				$('eligibleReason0').focus();
				return false;
			}
			if(getVal('isEligible1')==0 && $('eligibleReason1').value==''){
				alert('���¶˱𲻺ϸ�ԭ���Ǳ�����');
				$('eligibleReason1').focus();
				return false;
			}
			if(getVal('isEligible2')==0 && $('eligibleReason2').value==''){
				alert('���ڹ��ղ��ϸ�ԭ���Ǳ�����');
				$('eligibleReason2').focus();
				return false;
			}
			if(getVal('isEligible3')==0 && $('eligibleReason3').value==''){
				alert('���⹤�ղ��ϸ�ԭ���Ǳ�����');
				$('eligibleReason3').focus();
				return false;
			}
			if(getVal('isEligible4')==0 && $('eligibleReason4').value==''){
				alert('·��״�����ϸ�ԭ���Ǳ�����');
				$('eligibleReason4').focus();
				return false;
			}
			if(getVal('isEligible5')==0 && $('eligibleReason5').value==''){
				alert('����������ϸ�ԭ���Ǳ�����');
				$('eligibleReason5').focus();
				return false;
			}
			if(getVal('isEligible6')==0 && $('eligibleReason6').value==''){
				alert('��ǿоδ�ӵ�ԭ���Ǳ�����');
				$('eligibleReason6').focus();
				return false;
			}
			if(getVal('isEligible0')==1 && getVal('isEligible1')==1 && getVal('isEligible2')==1 && getVal('isEligible3')==1 && getVal('isEligible4')==1 && getVal('isEligible5')==1 && getVal('isEligible6')==1 ){
				setVal('result', '1');
			}else{
				setVal('result', '0');
			}
			return true;
		}
		function onSelect(obj){
			loadData(obj.value);
		}
		function loadData(section,sel){
			jQuery.ajax({
				   type: "POST",
				   url: "resAction.do",
				   data: "method=loadTown&scetion="+section+"&sel="+sel,
				   success: function(msg){
					 jQuery("#place_id").html(msg);
				   }
			});
		}
		function sendAjax(id){
			var response = '';
			jQuery.ajax({
			   type: "POST",
			   url: "acceptanceAction.do",
			   data: "method=validateCode&id="+id+"&cableId="+$('cableId').value,
			   async: false,
			   success: function(msg){
				   response = msg;
			   }
			});
			return response;
		}
		function validatecode(){
			var val = $('segmentid').value;
			if(val != ''){
				var msg = sendAjax(val);
				if(msg != ''){
					alert(msg);
				}
			}
		}
	</script>
</head>
<body>
	<template:titile value="¼�������������" />
	<html:form action="/acceptanceRcAction.do?method=saveCableData" styleId="form" onsubmit="return check();">
		<table  width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			<input type="hidden" name="id" value="${id}" />
			<input type="hidden" name="objectId" value="${objectId}" />
			<html:hidden property="cableId" styleId="cableId" value="${rcBean.cableId}" />
			<html:hidden property="cableAddOneId" value="${rcBean.cableAddOneId}" />
			<html:hidden property="payCableId" value="${rcBean.payCableId}" />
			<html:hidden property="cableResultId" value="${rcBean.cableResultId}" />
			<html:hidden property="times" value="${rcBean.times}" />
			<tr class=trcolor>
			    <td class="tdulleft">���±�ţ�</td>
				<td class="tdulright"><html:text property="segmentid" styleId="segmentid" styleClass="inputtext required" style="width:130px" onchange="validatecode()"/></td>
			    <td class="tdulleft">�ʲ���ţ�</td>
			    <td class="tdulright"><html:text property="assetno" styleClass="inputtext" style="width:130px"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">�������ƣ�</td>
			    <td class="tdulright"><html:text property="projectName" styleClass="inputtext required" style="width:130px"/></td>
				<td class="tdulleft">A�ˣ�</td>
				<td class="tdulright"><html:text property="pointa" styleClass="inputtext required" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">Z�ˣ�</td>
				<td class="tdulright"><html:text property="pointz" styleClass="inputtext required" style="width:130px" /></td>
				<td class="tdulleft">�����м̶Σ�</td>
				<td class="tdulright"><html:text property="segmentname" styleClass="inputtext required" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">��о����</td>
				<td class="tdulright"><html:text property="coreNumber" styleClass="inputtext required" style="width:130px" /></td>
				<td class="tdulleft">���³���(ǧ��)��</td>
				<td class="tdulright"><html:text property="grossLength" styleClass="inputtext required" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">ʩ����λ��</td>
				<td class="tdulright"><html:text property="builder" styleClass="inputtext required" style="width:130px" /></td>
				<td class="tdulleft">���¼���</td>
				<td class="tdulright"><apptag:quickLoadList cssClass="select" keyValue="${rcBean.cableLevel}"  name="cableLevel" listName="cabletype" type="select" style="width:130px"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">��ʽ����ͼֽ��</td>
				<td class="tdulright">
					<html:radio property="havePicture" value="y" styleClass="validate-one-required" />��
					<html:radio property="havePicture" value="n" styleClass="validate-one-required" />��
				</td>
				<td class="tdulleft">����</td>
				<td class="tdulright">
					<apptag:quickLoadList cssClass="1" name="scetion" keyValue="${rcBean.scetion}" onClick="onSelect(this)" listName="terminal_address" type="radio"/>
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">��о�ͺţ�</td>
				<td class="tdulright"><html:text property="fiberType" styleClass="inputtext required" style="width:130px" /></td>
				<td class="tdulleft">�ص㣺</td>
				<td class="tdulright">
				<html:select styleId="place_id" styleClass="inputtext required" style="width:130px" property="place">
				</html:select>
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">��������(ǧ��)��</td>
				<td class="tdulright"><html:text property="reservedLength" styleClass="inputtext required" style="width:130px" /></td>
				<td class="tdulleft">���³��ң�</td>
				<td class="tdulright"><html:text property="producer" styleClass="inputtext required" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">���������</td>
				<td class="tdulright">
					<html:radio property="currentState" value="y" styleClass="validate-one-required" />�ѽ���
					<html:radio property="currentState" value="n" styleClass="validate-one-required" />δ����
				</td>
				<td class="tdulleft">���������ʣ�</td>
				<td class="tdulright"><html:text property="refactiveIndex" styleClass="inputtext required" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">���跽ʽ��</td>
				<td class="tdulright" colspan=3><apptag:quickLoadList cssClass="input" keyValue="${rcBean.laytype}" name="laytypes" listName="layingmethod" type="checkbox"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">��ע��</td>
				<td class="tdulright" colspan=3><html:textarea style="width:250;height:50" property="remark" styleClass="inputtext required"/></td>
			</tr> 
			<tr>
				<td class="tdulleft">���赥λ��</td>
				<td class="tdulright"><html:text property="buildUnit" styleClass="inputtext required" style="width:130px" /></td>
				<td class="tdulleft">������Ա��</td>
				<td class="tdulright"><html:text property="buildAcceptance" styleClass="inputtext required" style="width:130px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">ʩ����λ��</td>
				<td class="tdulright"><html:text property="workUnit" styleClass="inputtext required" style="width:130px" /></td>
				<td class="tdulleft">������Ա��</td>
				<td class="tdulright"><html:text property="workAcceptance" styleClass="inputtext required" style="width:130px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">����λ��</td>
				<td class="tdulright"><html:text property="surveillanceUnit" styleClass="inputtext required" style="width:130px" /></td>
				<td class="tdulleft">������Ա��</td>
				<td class="tdulright"><html:text property="surveillanceAccept" styleClass="inputtext required" style="width:130px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">ά����λ��</td>
				<td class="tdulright"><html:text property="maintenceUnit" styleClass="inputtext required" style="width:130px" /></td>
				<td class="tdulleft">������Ա��</td>
				<td class="tdulright"><html:text property="maintenceAcceptance" styleClass="inputtext required" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">�Ƿ�TD��</td>
				<td class="tdulright">
					<html:radio property="isTd" value="1" styleClass="validate-one-required" />��
					<html:radio property="isTd" value="0" styleClass="validate-one-required" />��
				</td>
				<td class="tdulleft">��ͨ�ܵ�(ǧ��)��</td>
				<td class="tdulright"><html:text property="pipeLength0" styleClass="inputtext required validate-number" style="width:130px"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">�����ܵ�(ǧ��)��</td>
				<td class="tdulright"><html:text property="pipeLength1" styleClass="inputtext required validate-number" style="width:130px"/></td>
				<td class="tdulleft">�Խ��ܵ�(ǧ��)��</td>
				<td class="tdulright"><html:text property="pipeLength2" styleClass="inputtext required validate-number" style="width:130px"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">���Źܵ�(ǧ��)��</td>
				<td class="tdulright"><html:text property="pipeLength3" styleClass="inputtext required validate-number" style="width:130px"/></td>
				<td class="tdulleft">�軪�ܵ�(ǧ��)��</td>
				<td class="tdulright"><html:text property="pipeLength4" styleClass="inputtext required validate-number" style="width:130px"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">�ܿ�(ǧ��)��</td>
				<td class="tdulright"><html:text property="trunkmentLength0" styleClass="inputtext required validate-number" style="width:130px"/></td>
				<td class="tdulleft">�ܵ�(ǧ��):</td>
				<td class="tdulright"><html:text property="trunkmentLength1" styleClass="inputtext required validate-number" style="width:130px"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">ֱ��(ǧ��)��</td>
				<td class="tdulright"><html:text property="trunkmentLength2" styleClass="inputtext required validate-number" style="width:130px"/></td>
				<td class="tdulleft">����(ǧ��)��</td>
				<td class="tdulright"><html:text property="trunkmentLength3" styleClass="inputtext required validate-number" style="width:130px"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">������1��</td>
				<td class="tdulright" colspan=3>
					<html:text property="e1Length" styleClass="inputtext required validate-number" style="width:130px"/>ǧ��
					<html:text property="e1Number" styleClass="inputtext required validate-number" style="width:130px"/>��
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">������2��</td>
				<td class="tdulright" colspan=3>
					<html:text property="e2Length" styleClass="inputtext required validate-number" style="width:130px"/>ǧ�� 
					<html:text property="e2Number" styleClass="inputtext required validate-number" style="width:130px"/>��
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">������3��</td>
				<td class="tdulright" colspan=3>
					<html:text property="e3Length" styleClass="inputtext required validate-number" style="width:130px"/>ǧ��
					<html:text property="e3Number" styleClass="inputtext required validate-number" style="width:130px"/>��
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">ͨ�Ÿ�1��</td>
				<td class="tdulright" colspan=3>
					<html:text property="t1Length" styleClass="inputtext required validate-number" style="width:130px"/>ǧ��
					<html:text property="t1Number" styleClass="inputtext required validate-number" style="width:130px"/>��
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">ͨ�Ÿ�2��</td>
				<td class="tdulright" colspan=3>
					<html:text property="t2Length" styleClass="inputtext required validate-number" style="width:130px"/>ǧ��
					<html:text property="t2Number" styleClass="inputtext required validate-number" style="width:130px"/>��
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">ͨ�Ÿ�3��</td>
				<td class="tdulright" colspan=3>
					<html:text property="t3Length" styleClass="inputtext required validate-number" style="width:130px"/>ǧ�� 
					<html:text property="t3Number" styleClass="inputtext required validate-number" style="width:130px"/>��
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">����1��</td>
				<td class="tdulright" colspan=3>
					<html:text property="other1" styleClass="inputtext required validate-number" style="width:130px"/>ǧ�� 
					<html:text property="other1Number" styleClass="inputtext required validate-number" style="width:130px"/>��
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">����2��</td>
				<td class="tdulright" colspan=3>
					<html:text property="other2" styleClass="inputtext required validate-number" style="width:130px"/>ǧ�� 
					<html:text property="other2Number" styleClass="inputtext required validate-number" style="width:130px"/>��
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">���־���</td>
				<td class="tdulright" colspan=3>
					<html:text property="jNumber" styleClass="inputtext required validate-number" style="width:130px"/>
				</td>
			</tr>
			<tr>
				<td class="tdulleft">���մ�����</td>
				<td class="tdulright">��${rcBean.times}������</td>
				<td class="tdulleft">���ս����</td>
				<td class="tdulright">
					<html:radio property="result" value="1" styleClass="validate-one-required" />ͨ��
					<html:radio property="result" value="0" styleClass="validate-one-required" />δͨ��
				</td>
			</tr>
			<tr>
				<td class="tdulleft">�ƻ��������ڣ�</td>
				<td class="tdulright"><html:text property="planDate" styleId="planDate" styleClass="Wdate required" style="width:130px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',maxDate:'%y-%M-%d'})"/><font color="red">*</font></td>
				<td class="tdulleft">ʵ���������ڣ�</td>
				<td class="tdulright"><html:text property="factDate" styleId="factDate" styleClass="Wdate required" style="width:130px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',maxDate:'%y-%M-%d'})"/><font color="red">*</font></td>
			</tr>
			<tr>
				<td class="tdulleft">�����Ƿ�ϸ�</td>
				<td class="tdulright">
					<html:radio property="isEligible0" value="1" styleClass="validate-one-required"/>�ϸ�
					<html:radio property="isEligible0" value="0" styleClass="validate-one-required"/>���ϸ�
				</td>
				<td class="tdulleft">���ϲ��ϸ�ԭ��</td>
				<td class="tdulright"><html:textarea property="eligibleReason0" styleClass="inputtext" style="width:250px;height:50px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">���¶˱��Ƿ�ϸ�</td>
				<td class="tdulright">
					<html:radio property="isEligible1" value="1" styleClass="validate-one-required"/>�ϸ�
					<html:radio property="isEligible1" value="0" styleClass="validate-one-required"/>���ϸ�
				</td>
				<td class="tdulleft">���¶˱𲻺ϸ�ԭ��</td>
				<td class="tdulright"><html:textarea property="eligibleReason1" styleClass="inputtext" style="width:250px;height:50px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">���ڹ����Ƿ�ϸ�</td>
				<td class="tdulright">
					<html:radio property="isEligible2" value="1" styleClass="validate-one-required"/>�ϸ�
					<html:radio property="isEligible2" value="0" styleClass="validate-one-required"/>���ϸ�
				</td>
				<td class="tdulleft">���ڹ��ղ��ϸ�ԭ��</td>
				<td class="tdulright"><html:textarea property="eligibleReason2" styleClass="inputtext" style="width:250px;height:50px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">���⹤���Ƿ�ϸ�</td>
				<td class="tdulright">
					<html:radio property="isEligible3" value="1" styleClass="validate-one-required"/>�ϸ�
					<html:radio property="isEligible3" value="0" styleClass="validate-one-required"/>���ϸ�
				</td>
				<td class="tdulleft">���⹤�ղ��ϸ�ԭ��</td>
				<td class="tdulright"><html:textarea property="eligibleReason3" styleClass="inputtext" style="width:250px;height:50px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">·��״���Ƿ�ϸ�</td>
				<td class="tdulright">
					<html:radio property="isEligible4" value="1" styleClass="validate-one-required"/>�ϸ�
					<html:radio property="isEligible4" value="0" styleClass="validate-one-required"/>���ϸ�
				</td>
				<td class="tdulleft">·��״�����ϸ�ԭ��</td>
				<td class="tdulright"><html:textarea property="eligibleReason4" styleClass="inputtext" style="width:250px;height:50px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">��������Ƿ�ϸ�</td>
				<td class="tdulright">
					<html:radio property="isEligible5" value="1" styleClass="validate-one-required"/>�ϸ�
					<html:radio property="isEligible5" value="0" styleClass="validate-one-required"/>���ϸ�
				</td>
				<td class="tdulleft">����������ϸ�ԭ��</td>
				<td class="tdulright"><html:textarea property="eligibleReason5" styleClass="inputtext" style="width:250px;height:50px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">��ǿо�Ƿ�ӵأ�</td>
				<td class="tdulright">
					<html:radio property="isEligible6" value="1" styleClass="validate-one-required"/>�ϸ�
					<html:radio property="isEligible6" value="0" styleClass="validate-one-required"/>���ϸ�
				</td>
				<td class="tdulleft">��ǿоδ�ӵ�ԭ��</td>
				<td class="tdulright"><html:textarea property="eligibleReason6" styleClass="inputtext" style="width:250px;height:50px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">ͼֽ��</td>
				<td class="tdulright"><apptag:upload state="edit" cssClass="" entityId="${rcBean.cableId}" entityType="LP_ACCEPTANCE_CABLE" useable="0"/></td>
				<td class="tdulleft">���ձ�ע��</td>
				<td class="tdulright"><html:textarea property="resultRemark" styleClass="inputtext required" style="width:250px;height:50px" /></td>
			</tr>
		</table>
		<template:formSubmit>
			<td>
				<html:submit property="action" styleClass="button">�ύ</html:submit>
			</td>
			<td>
				<input type="button" class="button" value="����" onclick="history.back();"/>
			</td>
		</template:formSubmit>
	</html:form>
	<script type="text/javascript">
		function formCallback(result, form) {
			window.status = "valiation callback for form '" + form.id + "': result = " + result;
		}

		var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			loadData('${rcBean.scetion}','${rcBean.place}');
			jQuery("#form").bind("submit",function(){
				processBar();
			});
		});
	</script>
</body>
</html>