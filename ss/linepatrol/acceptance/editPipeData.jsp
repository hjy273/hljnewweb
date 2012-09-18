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
				alert('资料不合格原因是必填项');
				$('eligibleReason0').focus();
				return false;
			}
			if(getVal('isEligible1')==0 && $('eligibleReason1').value==''){
				alert('安装工艺不合格原因是必填项');
				$('eligibleReason1').focus();
				return false;
			}
			if(getVal('isEligible2')==0 && $('eligibleReason2').value==''){
				alert('管孔试通不合格原因是必填项');
				$('eligibleReason2').focus();
				return false;
			}
			if(getVal('isEligible3')==0 && $('eligibleReason3').value==''){
				alert('路由状况不合格原因是必填项');
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
	<template:titile value="录入管道验收数据" />
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
				<td class="tdulleft">工程名称：</td>
				<td class="tdulright" colspan=3>
					<html:text property="workName" styleClass="inputtext required" style="width:130px" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">管道地点：</td>
				<td class="tdulright"><html:text property="pipeAddress" styleClass="inputtext required" style="width:130px"/></td>
				<td class="tdulleft">产权属性：</td>
				<td class="tdulright"><apptag:quickLoadList cssClass="input" style="width:130px" name="routeRes" keyValue="${rpBean.routeRes}" listName="property_right" type="select"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">详细路由：</td>
				<td class="tdulright"><html:text property="pipeLine" styleClass="inputtext required" style="width:130px"/></td>
				<td class="tdulleft">竣工图纸：</td>
				<td class="tdulright"><html:text property="picture" styleClass="inputtext required validate-number" style="width:130px"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">管道中心项目经理：</td>
				<td class="tdulright"><html:text property="principle" styleClass="inputtext required" style="width:130px"/></td>
				<td class="tdulleft">管道属性：</td>
				<td class="tdulright"><apptag:quickLoadList cssClass="input" style="width:130px" name="pipeType" keyValue="${rpBean.pipeType}" listName="pipe_type" type="select"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">交资情况：</td>
				<td class="tdulright">
					<html:radio property="isMaintenance" value="y" styleClass="validate-one-required" />已交资
					<html:radio property="isMaintenance" value="n" styleClass="validate-one-required" />未交资
				</td>
				<td class="tdulleft">备注：</td>
				<td class="tdulright" colspan=3><html:textarea style="width:250;height:50" property="remark" styleClass="required"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">移动长度：</td>
				<td class="tdulright" colspan=3>
					沟（公里）<html:text property="mobileScareChannel"  styleClass="inputtext required validate-number" style="width:130px"/>
					&nbsp;&nbsp;&nbsp;&nbsp;孔（公里）<html:text property="mobileScareHole" styleClass="inputtext required validate-number" style="width:130px"/>
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">管道长度：</td>
				<td class="tdulright" colspan=3>
					沟（公里）<html:text property="pipeLengthChannel" styleClass="inputtext required validate-number" style="width:130px"/>
					&nbsp;&nbsp;&nbsp;&nbsp;孔（公里）<html:text property="pipeLengthHole" styleClass="inputtext required validate-number" style="width:130px"/>
				</td>
			</tr>
			<tr>
				<td class="tdulleft">建设单位：</td>
				<td class="tdulright"><html:text property="buildUnit" styleClass="inputtext required max-length-100" style="width:130px" /></td>
				<td class="tdulleft">验收人员：</td>
				<td class="tdulright"><html:text property="buildAcceptance" styleClass="inputtext required max-length-50" style="width:130px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">施工单位：</td>
				<td class="tdulright"><html:text property="workUnit" styleClass="inputtext required max-length-100" style="width:130px" /></td>
				<td class="tdulleft">验收人员：</td>
				<td class="tdulright"><html:text property="workAcceptance" styleClass="inputtext required max-length-50" style="width:130px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">监理单位：</td>
				<td class="tdulright"><html:text property="surveillanceUnit" styleClass="inputtext required max-length-100" style="width:130px" /></td>
				<td class="tdulleft">验收人员：</td>
				<td class="tdulright"><html:text property="surveillanceAccept" styleClass="inputtext required max-length-50" style="width:130px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">维护单位：</td>
				<td class="tdulright"><html:text property="maintenceUnit" styleClass="inputtext required max-length-100" style="width:130px" /></td>
				<td class="tdulleft">验收人员：</td>
				<td class="tdulright"><html:text property="maintenceAcceptance" styleClass="inputtext required max-length-50" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">直通型小号数量：</td>
				<td class="tdulright"><html:text property="directSmallNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">直通型中号数量：</td>
				<td class="tdulright"><html:text property="directMiddleNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">直通型大号数量：</td>
				<td class="tdulright"><html:text property="directLargeNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">三通型小号数量：</td>
				<td class="tdulright"><html:text property="threeSmallNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">三通型中号数量：</td>
				<td class="tdulright"><html:text property="threeMiddleNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">三通型大号数量：</td>
				<td class="tdulright"><html:text property="threeLargeNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">四通型小号数量：</td>
				<td class="tdulright"><html:text property="fourSmallNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">四通型中号数量：</td>
				<td class="tdulright"><html:text property="fourMiddleNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">四通型大号数量：</td>
				<td class="tdulright"><html:text property="fourLargelNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">局前型小号数量：</td>
				<td class="tdulright"><html:text property="forntSmallNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">局前型中号数量：</td>
				<td class="tdulright"><html:text property="forntMiddleNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">局前型大号数量：</td>
				<td class="tdulright"><html:text property="forntLargeNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">其他型小号数量：</td>
				<td class="tdulright"><html:text property="otherSmallNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">其他型中号数量：</td>
				<td class="tdulright"><html:text property="otherMiddleNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">其他型大号数量：</td>
				<td class="tdulright"><html:text property="otherLargeNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">小号手孔尺寸1：</td>
				<td class="tdulright"><html:text property="smallLength0" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">中号手孔尺寸1：</td>
				<td class="tdulright"><html:text property="middleLength0" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">大号手孔尺寸1：</td>
				<td class="tdulright"><html:text property="largeLength0" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">小号手孔尺寸2：</td>
				<td class="tdulright"><html:text property="smallLength1" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">中号手孔尺寸2：</td>
				<td class="tdulright"><html:text property="middleLength1" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">大号手孔尺寸2：</td>
				<td class="tdulright"><html:text property="largeLength1" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">其他手孔尺寸1：</td>
				<td class="tdulright"><html:text property="otherLength0" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">其他手孔尺寸2：</td>
				<td class="tdulright"><html:text property="otherLength1" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft"></td>
				<td class="tdulright"></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">钢管孔数：</td>
				<td class="tdulright"><html:text property="steelHoleNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">钢管公里数：</td>
				<td class="tdulright"><html:text property="steelLength" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">塑料管孔数：</td>
				<td class="tdulright"><html:text property="plasticHoleNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">塑料管公里数：</td>
				<td class="tdulright"><html:text property="plasticLength" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">水泥管孔数：</td>
				<td class="tdulright"><html:text property="cementHoleNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">水泥管公里数：</td>
				<td class="tdulright"><html:text property="cementLength" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">其他管孔数：</td>
				<td class="tdulright"><html:text property="otherHoleNumber" styleClass="inputtext required max-length-12" style="width:130px" /></td>
				<td class="tdulleft">其他管公里数：</td>
				<td class="tdulright"><html:text property="otherLength" styleClass="inputtext required max-length-12" style="width:130px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">验收次数：</td>
				<td class="tdulright">第${rpBean.times}次验收</td>
				<td class="tdulleft">验收结果：</td>
				<td class="tdulright">
					<html:radio property="result" value="1" onclick="setResult(this.value)" styleClass="validate-one-required"/>通过
					<html:radio property="result" value="0" onclick="setResult(this.value)" styleClass="validate-one-required"/>未通过
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
				<td class="tdulleft">安装工艺是否合格：</td>
				<td class="tdulright">
					<html:radio property="isEligible1" value="1" styleClass="validate-one-required"/>合格
					<html:radio property="isEligible1" value="0" styleClass="validate-one-required"/>不合格
				</td>
				<td class="tdulleft">安装工艺不合格原因：</td>
				<td class="tdulright"><html:textarea property="eligibleReason1" styleClass="inputtext" style="width:250px;height:50px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">管孔试通是否合格：</td>
				<td class="tdulright">
					<html:radio property="isEligible2" value="1" styleClass="validate-one-required"/>合格
					<html:radio property="isEligible2" value="0" styleClass="validate-one-required"/>不合格
				</td>
				<td class="tdulleft">管孔试通不合格原因：</td>
				<td class="tdulright"><html:textarea property="eligibleReason2" styleClass="inputtext" style="width:250px;height:50px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">路由状况是否合格：</td>
				<td class="tdulright">
					<html:radio property="isEligible3" value="1" styleClass="validate-one-required"/>合格
					<html:radio property="isEligible3" value="0" styleClass="validate-one-required"/>不合格
				</td>
				<td class="tdulleft">路由状况不合格原因：</td>
				<td class="tdulright"><html:textarea property="eligibleReason3" styleClass="inputtext" style="width:250px;height:50px" /></td>
			</tr>
			<tr>
				<td class="tdulleft">验收备注：</td>
				<td class="tdulright" colspan=3><html:textarea property="resultRemark" styleClass="inputtext required" style="width:250px;height:50px" /></td>
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
</body>
</html>