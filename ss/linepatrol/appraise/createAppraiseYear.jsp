<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>年度考核评分</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
	</head>
	<script type="text/javascript">
	var number = /^[0-9]+(.[0-9]{1,20})?$/;
		function getResult(type){
			var result=document.getElementById(type).value*document.getElementById(type+"Weight").value/100;
			document.getElementById(type+"Result").value=result;
			setResult();
		}
		function setResult(){
			var totle=document.getElementById("monthResult").value*1+document.getElementById("specialResult").value*1+document.getElementById("troubleResult").value*1+document.getElementById("yearendResult").value*1;
			document.getElementById("totle").innerHTML=totle;
			document.getElementById("result").value=totle;
		}
		function viewBase(type){
			var actionUrl="/WebApp/appraiseYearAction.do?method=appraiseYearViewBase&type="+type;
		if(type=="00"){
			var actionUrl="/WebApp/appraiseYearAction.do?method=statYearQuota&year="+${appraiseResultBean.appraiseTime};
			}
			viewBaseWin = new Ext.Window({
				layout : 'fit',
				width:550,height:300, 
				resizable:true,
				closeAction : 'close', 
				modal:false,
				autoScroll:true,
				autoLoad:{url: actionUrl,scripts:true}, 
				plain: false,
				title:"考核依据" 
			});
			viewBaseWin.show(Ext.getBody());
		}
		function closeViewBase(){
			viewBaseWin.close();
		}		
		function checkNumber(object){
			if(!number.test(object)){
				alert("请填写数字！");
			}
		}
	</script>
	<body>
		<template:titile value="年度考核评分" />
		<html:form action="/appraiseYearResultAction.do?method=saveAppraiseYear" styleId="form">
			<div align="center">
			<input type="hidden" name="contractorId" value="${appraiseResultBean.contractorId}" />
			<input type="hidden" name="year" value="${appraiseResultBean.appraiseTime}" />
			<input type="hidden" name="contractNO" value="${appraiseResultBean.contractNO}" />
			<input type="hidden" name="appraiser" value="${appraiser}" />
			<input type="hidden" name="confirmResult" value="0" />
			<table border="0"  align="center" cellpadding="3" cellspacing="0" class="tabout" width="100%">
				<tr class="trcolor">
					<td class="tdulright" style="text-align:center;">被考核代维：${contractorName}</td>
					<td class="tdulright" style="text-align:center;">考核年份：${appraiseResultBean.appraiseTime}年</td>
					<td class="tdulright" style="text-align:center;">标包：${appraiseResultBean.contractNO}包</td>
					<td class="tdulright" style="text-align:center;">考核人：${appraiser}</td>
					<td class="tdulright" style="text-align:center;">考核时间：${appraiseDate}</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulright" style="text-align:center;width:20%;" >考核类型</td>
					<td class="tdulright" style="text-align:center;width:20%;" >得分</td>
					<td class="tdulright" style="text-align:center;width:20%;">权重（%）</td>
					<td class="tdulright" style="text-align:center;width:20%;">最后得分</td>
					<td class="tdulright" style="text-align:center;width:20%;">评分依据</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulright" style="text-align:center;">月度考核</td>
					<td class="tdulright" style="text-align:center;"><input name="month" id="month" value="${results['1']}" readonly="readonly" size="30%"/></td>
					<td class="tdulright" style="text-align:center;"><input name="monthWeight" id="monthWeight" value="0" onblur="getResult('month');checkNumber(this.value);" size="30%"/></td>
					<td class="tdulright" style="text-align:center;"><input name="monthResult" id="monthResult" value="0" onblur="checkNumber(this.value);setResult();" size="30%"/></td>
					<td class="tdulright" style="text-align:center;"><a href="javascript:viewBase('1');">评分依据</a></td>
				</tr>
				<tr class="trcolor">
					<td class="tdulright" style="text-align:center;">专项考核</td>
					<td class="tdulright" style="text-align:center;"><input name="special" id="special" value="${results['2']}" readonly="readonly" size="30%"/></td>
					<td class="tdulright" style="text-align:center;"><input name="specialWeight" id="specialWeight" value="0" onblur="getResult('special');checkNumber(this.value);" size="30%"/></td>
					<td class="tdulright" style="text-align:center;"><input name="specialResult" id="specialResult" value="0" onblur="checkNumber(this.value);setResult();" size="30%"/></td>
					<td class="tdulright" style="text-align:center;"><a href="javascript:viewBase('2');">评分依据</a></td>
				</tr>
				<tr class="trcolor">
					<td class="tdulright" style="text-align:center;">故障指标考核</td>
					<td class="tdulright" style="text-align:center;"><input name="trouble" id="trouble" value="0" size="30%"/></td>
					<td class="tdulright" style="text-align:center;"><input name="troubleWeight" id="troubleWeight" value="0" onblur="getResult('trouble');checkNumber(this.value);" size="30%"/></td>
					<td class="tdulright" style="text-align:center;"><input name="troubleResult" id="troubleResult" value="0" onblur="checkNumber(this.value);setResult();" size="30%"/></td>
					<td class="tdulright" style="text-align:center;"><a href="javascript:viewBase('00');">评分依据</a></td>
				</tr>
				<tr class="trcolor">
					<td class="tdulright" style="text-align:center;">年终检查</td>
					<td class="tdulright" style="text-align:center;"><input name="yearend" id="yearend" value="${results['4']}" readonly="readonly" size="30%"/></td>
					<td class="tdulright" style="text-align:center;"><input name="yearendWeight" id="yearendWeight" value="0" onblur="getResult('yearend');checkNumber(this.value);" size="30%"/></td>
					<td class="tdulright" style="text-align:center;"><input name="yearendResult" id="yearendResult" value="0" onblur="checkNumber(this.value);setResult();" size="30%"/></td>
					<td class="tdulright" style="text-align:center;"><a href="javascript:viewBase('4');">评分依据</a></td>
				</tr>
				<tr class="trcolor">
					<td class="tdulright" style="text-align:center;">合计</td>
					<input id="result" name="result" value="0" type="hidden" />
					<td colspan="4" id="totle" style="text-align:center;"></td>
				</tr>
			</table>
			</div>
			<div align="center">
			<c:if test="${flag==edit}">
				
			</c:if>
			<input type="submit" value="生成" class="button"/>
			<input type="button" value="返回" class="button" onclick="javascript:history.back();"/></div>
		</html:form>
		<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
	</body>
</html>
