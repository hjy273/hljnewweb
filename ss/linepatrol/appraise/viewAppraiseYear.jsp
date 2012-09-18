<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>年度考核查看</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
	</head>
	<script type="text/javascript">
		function getResult(type){
			var result=document.getElementById(type).value*document.getElementById(type+"Weight").value/100;
			document.getElementById(type+"Result").value=result;
			var totle=document.getElementById("monthResult").value*1+document.getElementById("specialResult").value*1+document.getElementById("troubleResult").value*1+document.getElementById("yearendResult").value*1;
			document.getElementById("totle").innerHTML=totle;
			document.getElementById("result").value=totle;
		}
		function viewBase(type){
			var actionUrl="/WebApp/appraiseYearAction.do?method=appraiseYearViewBase&type="+type;
			if(type=="00"){
			var actionUrl="/WebApp/appraiseYearAction.do?method=statYearQuota&year="+${appraiseYearResult.year};
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
function his(id){
			var url = "/WebApp/appraiseYearAction.do?method=viewVerifyHistory&resultId="+id;
			showWin(url);
		}
		var win;
		function showWin(url){
			win = new Ext.Window({
				layout : 'fit',
				width:600,
				height:300,
				resizable:true,
				closeAction : 'close',
				modal:true,
				autoScroll:true,
				autoLoad:{url:url, scripts:true},
				plain: true
			});
			win.show(Ext.getBody());
		}
		function closeProcessWin(){
			win.close();
		}
	</script>
	<body>
	<c:if test="${flag=='verify'}">
		<template:titile value="代维考核确认" />
	</c:if>
	<c:if test="${flag==null||flag==''}">
		<template:titile value="年度考核查看" />
	</c:if>
		
		<br>
			<table border="0"  align="center" cellpadding="3" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulright" style="text-align:center;">被考核代维：${contractorName}</td>
					<td class="tdulright" style="text-align:center;">考核年份：${appraiseYearResult.year}年</td>
					<td class="tdulright" style="text-align:center;">标包：${appraiseYearResult.contractNO}包</td>
					<td class="tdulright" style="text-align:center;">考核人：${appraiseYearResult.appraiser}</td>
					<td class="tdulright" style="text-align:center;">考核时间：${appraiseYearResult.appraiseDate}</td>
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
					<td class="tdulright" style="text-align:center;">${appraiseYearResult.month}</td>
					<td class="tdulright" style="text-align:center;">${appraiseYearResult.monthWeight}</td>
					<td class="tdulright" style="text-align:center;">${appraiseYearResult.monthResult}</td>
					<td class="tdulright" style="text-align:center;"><a href="javascript:viewBase('1');">评分依据</a></td>
				</tr>
				<tr class="trcolor">
					<td class="tdulright" style="text-align:center;">专项考核</td>
					<td class="tdulright" style="text-align:center;">${appraiseYearResult.special}</td>
					<td class="tdulright" style="text-align:center;">${appraiseYearResult.specialWeight }</td>
					<td class="tdulright" style="text-align:center;">${appraiseYearResult.specialResult}</td>
					<td class="tdulright" style="text-align:center;"><a href="javascript:viewBase('2');">评分依据</a></td>
				</tr>
				<tr class="trcolor">
					<td class="tdulright" style="text-align:center;">故障指标考核</td>
					<td class="tdulright" style="text-align:center;">${appraiseYearResult.trouble}</td>
					<td class="tdulright" style="text-align:center;">${appraiseYearResult.troubleWeight}</td>
					<td class="tdulright" style="text-align:center;">${appraiseYearResult.troubleResult}</td>
					<td class="tdulright" style="text-align:center;"><a href="javascript:viewBase('00');">评分依据</a></td>
				</tr>
				<tr class="trcolor">
					<td class="tdulright" style="text-align:center;">年终检查</td>
					<td class="tdulright" style="text-align:center;">${appraiseYearResult.yearend}</td>
					<td class="tdulright" style="text-align:center;">${appraiseYearResult.yearendWeight}</td>
					<td class="tdulright" style="text-align:center;">${appraiseYearResult.yearendResult}</td>
					<td class="tdulright" style="text-align:center;"><a href="javascript:viewBase('4');">评分依据</a></td>
				</tr>
				<tr class="trcolor">
					<td class="tdulright" style="text-align:center;">合计</td>
					<td colspan="4" id="totle" style="text-align:center;">${appraiseYearResult.result}</td>
				</tr>
			</table>
			<c:if test="${flag=='verify'}">
			<br/>	
			<html:form action="/appraiseYearAction.do?method=verifyAppraise"  method="post">
				<input type="hidden" name="id" id="id" value="${appraiseYearResult.id}"/>
				&nbsp;&nbsp;&nbsp;&nbsp;有无异议： 
				<div>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="confirmResult" value="2" checked="checked">无异议</input>
					<input type="radio" name="confirmResult" value="3">有异议</input>
				</div>
				&nbsp;&nbsp;&nbsp;&nbsp;问题反馈备注：<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea rows="6" cols="120" name="mark"></textarea>
				<br/>
				<br/>		
				<div align="center">
			<input type="submit" class="button" value="确定"/>&nbsp;&nbsp;
			<input type="button" class="button" onclick="history.back()" value="返回"/>
		</div>
	</html:form>
	</c:if>
		<c:if test="${flag==null||flag==''}">
			<br>
			<div align="center">
			<input type="button" class="button" value="确认历史" onclick="his('${appraiseYearResult.id}')" />
			<input type="button" value="返回" class="button" onclick="javascript:history.back();"/></div>
		</c:if>
	</body>
</html>
