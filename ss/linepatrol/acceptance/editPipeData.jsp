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
		function setResult(val){
			if(val=='1'){
				document.getElementsByName("isEligible0")[0].checked=true;
				document.getElementsByName("isEligible1")[0].checked=true;
				document.getElementsByName("isEligible2")[0].checked=true;
				document.getElementsByName("isEligible3")[0].checked=true;
			}else{
				document.getElementsByName("isEligible0")[1].checked=true;
				document.getElementsByName("isEligible1")[1].checked=true;
				document.getElementsByName("isEligible2")[1].checked=true;
				document.getElementsByName("isEligible3")[1].checked=true;
			}
		}
		function check(){
			if(getVal('isEligible0')==0 && $('eligibleReason0').value==''){
				alert('���ϲ��ϸ�ԭ���Ǳ�����');
				$('eligibleReason0').focus();
				return false;
			}
			if(getVal('isEligible1')==0 && $('eligibleReason1').value==''){
				alert('��װ���ղ��ϸ�ԭ���Ǳ�����');
				$('eligibleReason1').focus();
				return false;
			}
			if(getVal('isEligible2')==0 && $('eligibleReason2').value==''){
				alert('�ܿ���ͨ���ϸ�ԭ���Ǳ�����');
				$('eligibleReason2').focus();
				return false;
			}
			if(getVal('isEligible3')==0 && $('eligibleReason3').value==''){
				alert('·��״�����ϸ�ԭ���Ǳ�����');
				$('eligibleReason3').focus();
				return false;
			}
			if(getVal('isEligible0')==1 && getVal('isEligible1')==1 && getVal('isEligible2')==1 && getVal('isEligible3')==1 ){
				setVal('result', '1');
			}else{
				setVal('result', '0');
			}
			return true;
		}
		jQuery(document).ready(function() {
			jQuery("#form").bind("submit",function(){
				processBar();
			});
		});
	</script>
</head>
<body>
	<template:titile value="¼��ܵ���������" />
	<html:form action="/acceptanceRpAction.do?method=savePipeData" styleId="form" onsubmit="return check();">
		<table  width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			<input type="hidden" name="id" value="${id}" />
			<input type="hidden" name="objectId" value="${objectId}" />
			<html:hidden property="payPipeId" value="${rpBean.payPipeId}" />
			<html:hidden property="pipeAddOneId" value="${rpBean.pipeAddOneId}" />
			<html:hidden property="pipeId" value="${rpBean.pipeId}" />
			<html:hidden property="pipeResultId" value="${rpBean.pipeResultId}" />
			<html:hidden property="times" value="${rpBean.times}" />
			<tr class=trcolor>
				<td class="tdulleft">�������ƣ�</td>
				<td class="tdulright" colspan=3>
					<html:text property="workName" styleClass="inputtext required" style="width:130px" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">�ܵ��ص㣺</td>
				<td class="tdulright"><html:text property="pipeAddress" styleClass="inputtext required" style="width:130px"/></td>
				<td class="tdulleft">��Ȩ���ԣ�</td>
				<td class="tdulright"><apptag:quickLoadList cssClass="input" style="width:130px" name="routeRes" keyValue="${rpBean.routeRes}" listName="property_right" type="select"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">��ϸ·�ɣ�</td>
				<td class="tdulright"><html:text property="pipeLine" styleClass="inputtext required" style="width:130px"/></td>
				<td class="tdulleft">����ͼֽ��</td>
				<td class="tdulright"><html:text property="picture" styleClass="inputtext required validate-number" style="width:130px"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">�ܵ�������Ŀ����</td>
				<td class="tdulright"><html:text property="principle" styleClass="inputtext required" style="width:130px"/></td>
				<td class="tdulleft">�ܵ����ԣ�</td>
				<td class="tdulright"><apptag:quickLoadList cssClass="input" style="width:130px" name="pipeType" keyValue="${rpBean.pipeType}" listName="pipe_type" type="select"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">���������</td>
				<td class="tdulright">
					<html:radio property="isMaintenance" value="y" styleClass="validate-one-required" />�ѽ���
					<html:radio property="isMaintenance" value="n" styleClass="validate-one-required" />δ����
				</td>
				<td class="tdulleft">��ע��</td>
				<td class="tdulright" colspan=3><html:textarea style="width:250;height:50" property="remark" styleClass="required"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">�ƶ����ȣ�</td>
				<td class="tdulright" colspan=3>
					�������<html:text property="mobileScareChannel"  styleClass="inputtext required validate-number" style="width:130px"/>
					&nbsp;&nbsp;&nbsp;&nbsp;�ף����<html:text property="mobileScareHole" styleClass="inputtext required validate-number" style="width:130px"/>
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">�ܵ����ȣ�</td>
				<td class="tdulright" colspan=3>
					�������<html:text property="pipeLengthChannel" styleClass="inputtext required validate-number" style="width:130px"/>
					&nbsp;&nbsp;&nbsp;&nbsp;�ף����<html:text property="pipeLengthHole" styleClass="inputtext required validate-number" style="width:130px"/>
				</td>
			</tr>
			<tr>
				<td class="tdulleft">���赥λ��</td>
				<td class="tdulright"><html:text property="buildUnit" styleClass="inputtext required max-length-100" style="width:130px" /></td>
				<td class="tdulleft">������Ա��</td>
				<td class="tdulright"><html:text property="buildAcceptance" styleClass="inputtext required max-length-50" style="width:130px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">ʩ����λ��</td>
				<td class="tdulright"><html:text property="workUnit" styleClass="inputtext required max-length-100" style="width:130px" /></td>
				<td class="tdulleft">������Ա��</td>
				<td class="tdulright"><html:text property="workAcceptance" styleClass="inputtext required max-length-50" style="width:130px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">����λ��</td>
				<td class="tdulright"><html:text property="surveillanceUnit" styleClass="inputtext required max-length-100" style="width:130px" /></td>
				<td class="tdulleft">������Ա��</td>
				<td class="tdulright"><html:text property="surveillanceAccept" styleClass="inputtext required max-length-50" style="width:130px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">ά����λ��</td>
				<td class="tdulright"><html:text property="maintenceUnit" styleClass="inputtext required max-length-100" style="width:130px" /></td>
				<td class="tdulleft">������Ա��</td>
				<td class="tdulright"><html:text property="maintenceAcceptance" styleClass="inputtext required max-length-50" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">ֱͨ��С��������</td>
				<td class="tdulright"><html:text property="directSmallNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">ֱͨ���к�������</td>
				<td class="tdulright"><html:text property="directMiddleNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">ֱͨ�ʹ��������</td>
				<td class="tdulright"><html:text property="directLargeNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">��ͨ��С��������</td>
				<td class="tdulright"><html:text property="threeSmallNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">��ͨ���к�������</td>
				<td class="tdulright"><html:text property="threeMiddleNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">��ͨ�ʹ��������</td>
				<td class="tdulright"><html:text property="threeLargeNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">��ͨ��С��������</td>
				<td class="tdulright"><html:text property="fourSmallNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">��ͨ���к�������</td>
				<td class="tdulright"><html:text property="fourMiddleNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">��ͨ�ʹ��������</td>
				<td class="tdulright"><html:text property="fourLargelNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">��ǰ��С��������</td>
				<td class="tdulright"><html:text property="forntSmallNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">��ǰ���к�������</td>
				<td class="tdulright"><html:text property="forntMiddleNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">��ǰ�ʹ��������</td>
				<td class="tdulright"><html:text property="forntLargeNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">������С��������</td>
				<td class="tdulright"><html:text property="otherSmallNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">�������к�������</td>
				<td class="tdulright"><html:text property="otherMiddleNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">�����ʹ��������</td>
				<td class="tdulright"><html:text property="otherLargeNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">С���ֿ׳ߴ�1��</td>
				<td class="tdulright"><html:text property="smallLength0" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">�к��ֿ׳ߴ�1��</td>
				<td class="tdulright"><html:text property="middleLength0" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">����ֿ׳ߴ�1��</td>
				<td class="tdulright"><html:text property="largeLength0" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">С���ֿ׳ߴ�2��</td>
				<td class="tdulright"><html:text property="smallLength1" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">�к��ֿ׳ߴ�2��</td>
				<td class="tdulright"><html:text property="middleLength1" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">����ֿ׳ߴ�2��</td>
				<td class="tdulright"><html:text property="largeLength1" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">�����ֿ׳ߴ�1��</td>
				<td class="tdulright"><html:text property="otherLength0" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">�����ֿ׳ߴ�2��</td>
				<td class="tdulright"><html:text property="otherLength1" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft"></td>
				<td class="tdulright"></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">�ֹܿ�����</td>
				<td class="tdulright"><html:text property="steelHoleNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">�ֹܹ�������</td>
				<td class="tdulright"><html:text property="steelLength" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">���Ϲܿ�����</td>
				<td class="tdulright"><html:text property="plasticHoleNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">���Ϲܹ�������</td>
				<td class="tdulright"><html:text property="plasticLength" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">ˮ��ܿ�����</td>
				<td class="tdulright"><html:text property="cementHoleNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">ˮ��ܹ�������</td>
				<td class="tdulright"><html:text property="cementLength" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">�����ܿ�����</td>
				<td class="tdulright"><html:text property="otherHoleNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">�����ܹ�������</td>
				<td class="tdulright"><html:text property="otherLength" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">���մ�����</td>
				<td class="tdulright">��${rpBean.times}������</td>
				<td class="tdulleft">���ս����</td>
				<td class="tdulright">
					<html:radio property="result" value="1" onclick="setResult(this.value)" styleClass="validate-one-required"/>ͨ��
					<html:radio property="result" value="0" onclick="setResult(this.value)" styleClass="validate-one-required"/>δͨ��
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
				<td class="tdulleft">��װ�����Ƿ�ϸ�</td>
				<td class="tdulright">
					<html:radio property="isEligible1" value="1" styleClass="validate-one-required"/>�ϸ�
					<html:radio property="isEligible1" value="0" styleClass="validate-one-required"/>���ϸ�
				</td>
				<td class="tdulleft">��װ���ղ��ϸ�ԭ��</td>
				<td class="tdulright"><html:textarea property="eligibleReason1" styleClass="inputtext" style="width:250px;height:50px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">�ܿ���ͨ�Ƿ�ϸ�</td>
				<td class="tdulright">
					<html:radio property="isEligible2" value="1" styleClass="validate-one-required"/>�ϸ�
					<html:radio property="isEligible2" value="0" styleClass="validate-one-required"/>���ϸ�
				</td>
				<td class="tdulleft">�ܿ���ͨ���ϸ�ԭ��</td>
				<td class="tdulright"><html:textarea property="eligibleReason2" styleClass="inputtext" style="width:250px;height:50px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">·��״���Ƿ�ϸ�</td>
				<td class="tdulright">
					<html:radio property="isEligible3" value="1" styleClass="validate-one-required"/>�ϸ�
					<html:radio property="isEligible3" value="0" styleClass="validate-one-required"/>���ϸ�
				</td>
				<td class="tdulleft">·��״�����ϸ�ԭ��</td>
				<td class="tdulright"><html:textarea property="eligibleReason3" styleClass="inputtext" style="width:250px;height:50px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">���ձ�ע��</td>
				<td class="tdulright" colspan=3><html:textarea property="resultRemark" styleClass="inputtext required" style="width:250px;height:50px" /></td>
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
</body>
</html>