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
				alert('资料不合格原因是必填项');
				$('eligibleReason0').focus();
				return false;
			}
			if(getVal('isEligible1')==0 && $('eligibleReason1').value==''){
				alert('光缆端别不合格原因是必填项');
				$('eligibleReason1').focus();
				return false;
			}
			if(getVal('isEligible2')==0 && $('eligibleReason2').value==''){
				alert('室内工艺不合格原因是必填项');
				$('eligibleReason2').focus();
				return false;
			}
			if(getVal('isEligible3')==0 && $('eligibleReason3').value==''){
				alert('室外工艺不合格原因是必填项');
				$('eligibleReason3').focus();
				return false;
			}
			if(getVal('isEligible4')==0 && $('eligibleReason4').value==''){
				alert('路由状况不合格原因是必填项');
				$('eligibleReason4').focus();
				return false;
			}
			if(getVal('isEligible5')==0 && $('eligibleReason5').value==''){
				alert('测试情况不合格原因是必填项');
				$('eligibleReason5').focus();
				return false;
			}
			if(getVal('isEligible6')==0 && $('eligibleReason6').value==''){
				alert('加强芯未接地原因是必填项');
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
	<template:titile value="录入光缆验收数据" />
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
			    <td class="tdulleft">光缆编号：</td>
				<td class="tdulright"><html:text property="segmentid" styleId="segmentid" styleClass="inputtext required" style="width:130px" onchange="validatecode()"/></td>
			    <td class="tdulleft">资产编号：</td>
			    <td class="tdulright"><html:text property="assetno" styleClass="inputtext" style="width:130px"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">工程名称：</td>
			    <td class="tdulright"><html:text property="projectName" styleClass="inputtext required" style="width:130px"/></td>
				<td class="tdulleft">A端：</td>
				<td class="tdulright"><html:text property="pointa" styleClass="inputtext required" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">Z端：</td>
				<td class="tdulright"><html:text property="pointz" styleClass="inputtext required" style="width:130px" /></td>
				<td class="tdulleft">光缆中继段：</td>
				<td class="tdulright"><html:text property="segmentname" styleClass="inputtext required" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">纤芯数：</td>
				<td class="tdulright"><html:text property="coreNumber" styleClass="inputtext required" style="width:130px" /></td>
				<td class="tdulleft">光缆长度(千米)：</td>
				<td class="tdulright"><html:text property="grossLength" styleClass="inputtext required" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">施工单位：</td>
				<td class="tdulright"><html:text property="builder" styleClass="inputtext required" style="width:130px" /></td>
				<td class="tdulleft">光缆级别：</td>
				<td class="tdulright"><apptag:quickLoadList cssClass="select" keyValue="${rcBean.cableLevel}"  name="cableLevel" listName="cabletype" type="select" style="width:130px"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">正式竣工图纸：</td>
				<td class="tdulright">
					<html:radio property="havePicture" value="y" styleClass="validate-one-required" />有
					<html:radio property="havePicture" value="n" styleClass="validate-one-required" />无
				</td>
				<td class="tdulleft">区域：</td>
				<td class="tdulright">
					<apptag:quickLoadList cssClass="1" name="scetion" keyValue="${rcBean.scetion}" onClick="onSelect(this)" listName="terminal_address" type="radio"/>
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">纤芯型号：</td>
				<td class="tdulright"><html:text property="fiberType" styleClass="inputtext required" style="width:130px" /></td>
				<td class="tdulleft">地点：</td>
				<td class="tdulright">
				<html:select styleId="place_id" styleClass="inputtext required" style="width:130px" property="place">
				</html:select>
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">盘留长度(千米)：</td>
				<td class="tdulright"><html:text property="reservedLength" styleClass="inputtext required" style="width:130px" /></td>
				<td class="tdulleft">光缆厂家：</td>
				<td class="tdulright"><html:text property="producer" styleClass="inputtext required" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">交资情况：</td>
				<td class="tdulright">
					<html:radio property="currentState" value="y" styleClass="validate-one-required" />已交资
					<html:radio property="currentState" value="n" styleClass="validate-one-required" />未交资
				</td>
				<td class="tdulleft">测试折射率：</td>
				<td class="tdulright"><html:text property="refactiveIndex" styleClass="inputtext required" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">铺设方式：</td>
				<td class="tdulright" colspan=3><apptag:quickLoadList cssClass="input" keyValue="${rcBean.laytype}" name="laytypes" listName="layingmethod" type="checkbox"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">备注：</td>
				<td class="tdulright" colspan=3><html:textarea style="width:250;height:50" property="remark" styleClass="inputtext required"/></td>
			</tr> 
			<tr>
				<td class="tdulleft">建设单位：</td>
				<td class="tdulright"><html:text property="buildUnit" styleClass="inputtext required" style="width:130px" /></td>
				<td class="tdulleft">验收人员：</td>
				<td class="tdulright"><html:text property="buildAcceptance" styleClass="inputtext required" style="width:130px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">施工单位：</td>
				<td class="tdulright"><html:text property="workUnit" styleClass="inputtext required" style="width:130px" /></td>
				<td class="tdulleft">验收人员：</td>
				<td class="tdulright"><html:text property="workAcceptance" styleClass="inputtext required" style="width:130px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">监理单位：</td>
				<td class="tdulright"><html:text property="surveillanceUnit" styleClass="inputtext required" style="width:130px" /></td>
				<td class="tdulleft">验收人员：</td>
				<td class="tdulright"><html:text property="surveillanceAccept" styleClass="inputtext required" style="width:130px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">维护单位：</td>
				<td class="tdulright"><html:text property="maintenceUnit" styleClass="inputtext required" style="width:130px" /></td>
				<td class="tdulleft">验收人员：</td>
				<td class="tdulright"><html:text property="maintenceAcceptance" styleClass="inputtext required" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">是否TD：</td>
				<td class="tdulright">
					<html:radio property="isTd" value="1" styleClass="validate-one-required" />是
					<html:radio property="isTd" value="0" styleClass="validate-one-required" />否
				</td>
				<td class="tdulleft">网通管道(千米)：</td>
				<td class="tdulright"><html:text property="pipeLength0" styleClass="inputtext required validate-number" style="width:130px"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">联建管道(千米)：</td>
				<td class="tdulright"><html:text property="pipeLength1" styleClass="inputtext required validate-number" style="width:130px"/></td>
				<td class="tdulleft">自建管道(千米)：</td>
				<td class="tdulright"><html:text property="pipeLength2" styleClass="inputtext required validate-number" style="width:130px"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">北信管道(千米)：</td>
				<td class="tdulright"><html:text property="pipeLength3" styleClass="inputtext required validate-number" style="width:130px"/></td>
				<td class="tdulleft">歌华管道(千米)：</td>
				<td class="tdulright"><html:text property="pipeLength4" styleClass="inputtext required validate-number" style="width:130px"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">架空(千米)：</td>
				<td class="tdulright"><html:text property="trunkmentLength0" styleClass="inputtext required validate-number" style="width:130px"/></td>
				<td class="tdulleft">管道(千米):</td>
				<td class="tdulright"><html:text property="trunkmentLength1" styleClass="inputtext required validate-number" style="width:130px"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">直埋(千米)：</td>
				<td class="tdulright"><html:text property="trunkmentLength2" styleClass="inputtext required validate-number" style="width:130px"/></td>
				<td class="tdulleft">其他(千米)：</td>
				<td class="tdulright"><html:text property="trunkmentLength3" styleClass="inputtext required validate-number" style="width:130px"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">电力杆1：</td>
				<td class="tdulright" colspan=3>
					<html:text property="e1Length" styleClass="inputtext required validate-number" style="width:130px"/>千米
					<html:text property="e1Number" styleClass="inputtext required validate-number" style="width:130px"/>根
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">电力杆2：</td>
				<td class="tdulright" colspan=3>
					<html:text property="e2Length" styleClass="inputtext required validate-number" style="width:130px"/>千米 
					<html:text property="e2Number" styleClass="inputtext required validate-number" style="width:130px"/>根
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">电力杆3：</td>
				<td class="tdulright" colspan=3>
					<html:text property="e3Length" styleClass="inputtext required validate-number" style="width:130px"/>千米
					<html:text property="e3Number" styleClass="inputtext required validate-number" style="width:130px"/>根
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">通信杆1：</td>
				<td class="tdulright" colspan=3>
					<html:text property="t1Length" styleClass="inputtext required validate-number" style="width:130px"/>千米
					<html:text property="t1Number" styleClass="inputtext required validate-number" style="width:130px"/>根
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">通信杆2：</td>
				<td class="tdulright" colspan=3>
					<html:text property="t2Length" styleClass="inputtext required validate-number" style="width:130px"/>千米
					<html:text property="t2Number" styleClass="inputtext required validate-number" style="width:130px"/>根
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">通信杆3：</td>
				<td class="tdulright" colspan=3>
					<html:text property="t3Length" styleClass="inputtext required validate-number" style="width:130px"/>千米 
					<html:text property="t3Number" styleClass="inputtext required validate-number" style="width:130px"/>根
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">其他1：</td>
				<td class="tdulright" colspan=3>
					<html:text property="other1" styleClass="inputtext required validate-number" style="width:130px"/>千米 
					<html:text property="other1Number" styleClass="inputtext required validate-number" style="width:130px"/>根
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">其他2：</td>
				<td class="tdulright" colspan=3>
					<html:text property="other2" styleClass="inputtext required validate-number" style="width:130px"/>千米 
					<html:text property="other2Number" styleClass="inputtext required validate-number" style="width:130px"/>根
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">人手井：</td>
				<td class="tdulright" colspan=3>
					<html:text property="jNumber" styleClass="inputtext required validate-number" style="width:130px"/>
				</td>
			</tr>
			<tr>
				<td class="tdulleft">验收次数：</td>
				<td class="tdulright">第${rcBean.times}次验收</td>
				<td class="tdulleft">验收结果：</td>
				<td class="tdulright">
					<html:radio property="result" value="1" styleClass="validate-one-required" />通过
					<html:radio property="result" value="0" styleClass="validate-one-required" />未通过
				</td>
			</tr>
			<tr>
				<td class="tdulleft">计划验收日期：</td>
				<td class="tdulright"><html:text property="planDate" styleId="planDate" styleClass="Wdate required" style="width:130px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',maxDate:'%y-%M-%d'})"/><font color="red">*</font></td>
				<td class="tdulleft">实际验收日期：</td>
				<td class="tdulright"><html:text property="factDate" styleId="factDate" styleClass="Wdate required" style="width:130px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',maxDate:'%y-%M-%d'})"/><font color="red">*</font></td>
			</tr>
			<tr>
				<td class="tdulleft">资料是否合格：</td>
				<td class="tdulright">
					<html:radio property="isEligible0" value="1" styleClass="validate-one-required"/>合格
					<html:radio property="isEligible0" value="0" styleClass="validate-one-required"/>不合格
				</td>
				<td class="tdulleft">资料不合格原因：</td>
				<td class="tdulright"><html:textarea property="eligibleReason0" styleClass="inputtext" style="width:250px;height:50px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">光缆端别是否合格：</td>
				<td class="tdulright">
					<html:radio property="isEligible1" value="1" styleClass="validate-one-required"/>合格
					<html:radio property="isEligible1" value="0" styleClass="validate-one-required"/>不合格
				</td>
				<td class="tdulleft">光缆端别不合格原因：</td>
				<td class="tdulright"><html:textarea property="eligibleReason1" styleClass="inputtext" style="width:250px;height:50px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">室内工艺是否合格：</td>
				<td class="tdulright">
					<html:radio property="isEligible2" value="1" styleClass="validate-one-required"/>合格
					<html:radio property="isEligible2" value="0" styleClass="validate-one-required"/>不合格
				</td>
				<td class="tdulleft">室内工艺不合格原因：</td>
				<td class="tdulright"><html:textarea property="eligibleReason2" styleClass="inputtext" style="width:250px;height:50px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">室外工艺是否合格：</td>
				<td class="tdulright">
					<html:radio property="isEligible3" value="1" styleClass="validate-one-required"/>合格
					<html:radio property="isEligible3" value="0" styleClass="validate-one-required"/>不合格
				</td>
				<td class="tdulleft">室外工艺不合格原因：</td>
				<td class="tdulright"><html:textarea property="eligibleReason3" styleClass="inputtext" style="width:250px;height:50px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">路由状况是否合格：</td>
				<td class="tdulright">
					<html:radio property="isEligible4" value="1" styleClass="validate-one-required"/>合格
					<html:radio property="isEligible4" value="0" styleClass="validate-one-required"/>不合格
				</td>
				<td class="tdulleft">路由状况不合格原因：</td>
				<td class="tdulright"><html:textarea property="eligibleReason4" styleClass="inputtext" style="width:250px;height:50px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">测试情况是否合格：</td>
				<td class="tdulright">
					<html:radio property="isEligible5" value="1" styleClass="validate-one-required"/>合格
					<html:radio property="isEligible5" value="0" styleClass="validate-one-required"/>不合格
				</td>
				<td class="tdulleft">测试情况不合格原因：</td>
				<td class="tdulright"><html:textarea property="eligibleReason5" styleClass="inputtext" style="width:250px;height:50px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">加强芯是否接地：</td>
				<td class="tdulright">
					<html:radio property="isEligible6" value="1" styleClass="validate-one-required"/>合格
					<html:radio property="isEligible6" value="0" styleClass="validate-one-required"/>不合格
				</td>
				<td class="tdulleft">加强芯未接地原因：</td>
				<td class="tdulright"><html:textarea property="eligibleReason6" styleClass="inputtext" style="width:250px;height:50px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">图纸：</td>
				<td class="tdulright"><apptag:upload state="edit" cssClass="" entityId="${rcBean.cableId}" entityType="LP_ACCEPTANCE_CABLE" useable="0"/></td>
				<td class="tdulleft">验收备注：</td>
				<td class="tdulright"><html:textarea property="resultRemark" styleClass="inputtext required" style="width:250px;height:50px" /></td>
			</tr>
		</table>
		<template:formSubmit>
			<td>
				<html:submit property="action" styleClass="button">提交</html:submit>
			</td>
			<td>
				<input type="button" class="button" value="返回" onclick="history.back();"/>
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