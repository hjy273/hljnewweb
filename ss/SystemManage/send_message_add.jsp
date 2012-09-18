<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
	String.prototype.Trim = function() {
		var m = this.match(/^\s*(\S+(\s+\S+)*)\s*$/);
		return (m == null) ? "" : m[1];
	}
	//手机号码验证 
	String.prototype.isMobile = function() {
		var reg=/^1[0-9]{10}$/;
		return reg.test(this.Trim());
	}
	String.prototype.time=function(){
		var reg=/^[0-9]+$/;
		return (reg.test(this));
	}
	changeCycle=function(value){
		weekDaySpan.style.display='none';
		dateSpan.style.display='none';
		if(value=='1'){
			weekDaySpan.style.display='';
		}
		if(value=='2'){
			dateSpan.style.display='';
		}
	}
	checkForm=function(){
		var form1=document.forms[0];
		if(form1.mobileIds.value==""&&form1.otherMobiles.value==""){
			alert("没有选择短信接收人或者接收人没有手机号码！");
			return false;
		}
		if(form1.sendContent.value==""){
			alert("没有输入手机短信内容！");
			return false;
		}
		var mobilesStr=document.getElementById("otherMoblies").value;
		if(mobilesStr!=""){
			if(mobilesStr.split(",").length>0){
				var mobiles;
				if(mobilesStr.indexOf(",")==-1){
					mobiles=mobilesStr;
					if(!mobiles.isMobile()){
						alert("请输入正确的手机号码！");
						return false;
					}
				}else{
					mobiles=mobilesStr.split(",");
					for(var index=0;index<mobiles.length;index++){
						if(!mobiles[index].isMobile()){
							alert("请输入正确的手机号码！");
							return false;
							break;
						}
					}
				}
			}else{
				if(!mobilesStr.isMobile()){
					alert("请输入正确的手机号码！");
					return false;
				}
			}
		}
		if(form1.sendMethod[1].checked){
		if(document.getElementById("sendTimeSpace")!=null){
			if(!document.getElementById("sendTimeSpace").value.time()){
				alert("时间间隔为整数！");
				document.getElementById("sendTimeSpace").focus();
				return false;
			}
		}
		}
		if(form1.otherMobiles.value!=""){
			if(form1.mobileIds.value!=""){
				form1.mobileIds.value+=",";
			}
			form1.mobileIds.value+=form1.otherMobiles.value;
		}
		if(form1.sendMethod[2].checked){
			form1.inputDate[0].value="0";
			form1.inputDate[1].value=form1.inputTime.value.split(":")[1];
			form1.inputDate[2].value=form1.inputTime.value.split(":")[0];
			var sendCycleRule="";
			if(form1.sendCycle.value=="0"){
				sendCycleRule="每日"+form1.inputTime.value;
			}
			if(form1.sendCycle.value=="1"){
				var weekDayValue="";
				sendCycleRule="每"
				for(i=0;i<form1.weekDay.length;i++){
					if(form1.weekDay[i].checked){
						weekDayValue+=form1.weekDay[i].value+",";
						sendCycleRule+=form1.weekDay[i].varComment+",";
					}
				}
				form1.inputDate[5].value=weekDayValue.substring(0,weekDayValue.length-1);
				sendCycleRule=sendCycleRule.substring(0,sendCycleRule.length-1);
				sendCycleRule+=form1.inputTime.value;
			}
			if(form1.sendCycle.value=="2"){
				form1.inputDate[3].value=form1.selectDate.value;
				sendCycleRule="每月"+form1.selectDate.options[form1.selectDate.options.selectedIndex].varComment+form1.inputTime.value;
			}
			for(i=0;i<form1.inputDate.length;i++){
				//alert("inputDate["+i+"]="+form1.inputDate[i].value);
			}
			form1.sendCycleRule.value=sendCycleRule;
		}
		return true;
	}
</script>
		<title>发送短信</title>
	</head>
	<body>
		<template:titile value="发送短信" />
		<html:form action="/sendmessage/send_message.do?method=sendMessage" styleId="form1"
			onsubmit="return checkForm();">
			<table width="90%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<apptag:processorselect
					inputName="departIds,acceptUserIds,mobileIds" exceptSelf="false"
					labelName="短信接收人" displayType="mobile_contractor" colSpan="1" spanId="acceptUser"></apptag:processorselect>
				<tr class=trcolor>
					<td class="tdr">
						其他短信接收号码：
					</td>
					<td class="tdl">
						<input name="otherMobiles" value="" id="otherMoblies" type="text"
							style="width: 300px" />
						<br />
						<font color="#FF0000">请用英文半角“,”分隔</font>"
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						短信发送方式：
					</td>
					<td class="tdl">
						<input name="sendMethod" value="0" type="radio" checked
							onclick="timeInputSpan.style.display='none';timeSpaceTr.style.display='none';cycleTr.style.display='none';timeValueTr.style.display='none';" />
						即时发送
						<input name="sendMethod" value="1" type="radio"
							onclick="timeInputSpan.style.display='';timeSpaceTr.style.display='';cycleTr.style.display='none';timeValueTr.style.display='none';" />
						定时发送
						<input name="sendMethod" value="2" type="radio"
							onclick="timeInputSpan.style.display='none';timeSpaceTr.style.display='none';cycleTr.style.display='';timeValueTr.style.display='';" />
						固定周期发送
					</td>
				</tr>
				<tr class=trcolor id="timeInputSpan" style="display: none;">
					<td class="tdr">
						短信发送时间：
					</td>
					<td>
						<input type="text" id="protimeid" name="beginDate" value=""
							readonly="readonly" class="Wdate" style="width: 150px"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1'})" />
						到
						<input type="text" id="protimeid" name="endDate" value=""
							readonly="readonly" class="Wdate" style="width: 150px"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:$('beginDate')})" />
					</td>
				</tr>
				<tr class=trcolor id="timeSpaceTr" style="display: none;">
					<td class="tdr">
						短信发送间隔：
					</td>
					<td class="tdl">
						<input name="sendTimeSpace" id="sendTimeSpace" value="" type="text"
							style="width: 100px" />
						<input name="sendIntervalType" value="-1" type="radio" checked />
						单次
						<input name="sendIntervalType" value="0" type="radio" />
						秒
						<input name="sendIntervalType" value="1" type="radio" />
						分
						<input name="sendIntervalType" value="2" type="radio" />
						时
						<input name="sendIntervalType" value="3" type="radio" />
						天
					</td>
				</tr>
				<tr class=trcolor id="cycleTr" style="display: none;">
					<td class="tdr">
						发送周期：
					</td>
					<td>
						<select name="sendCycle" onchange="changeCycle(this.value);">
							<option value="0">
								每天
							</option>
							<option value="1">
								每周
							</option>
							<option value="2">
								每月
							</option>
						</select>
					</td>
				</tr>
				<tr class=trcolor id="timeValueTr" style="display: none;">
					<td class="tdr">
						指定周期中的固定时间：
					</td>
					<td class="tdl">
						<input name="sendCycleRule" value="" type="hidden" />
						<input name="inputDate" value="" type="hidden" /><!-- 秒 -->
						<input name="inputDate" value="" type="hidden" /><!-- 分 -->
						<input name="inputDate" value="" type="hidden" /><!-- 时 -->
						<input name="inputDate" value="" type="hidden" /><!-- 日 -->
						<input name="inputDate" value="" type="hidden" /><!-- 月 -->
						<input name="inputDate" value="" type="hidden" /><!-- 周 -->
						<input name="inputDate" value="" type="hidden" /><!-- 年 -->
						<span id="weekDaySpan" style="display: none;"> <input
								name="weekDay" value="2" varComment="周一" type="checkbox"> 星期一 <input
								name="weekDay" value="3" varComment="周二" type="checkbox"> 星期二 <input
								name="weekDay" value="4" varComment="周三" type="checkbox"> 星期三 <input
								name="weekDay" value="5" varComment="周四" type="checkbox"> 星期四<br /> <input
								name="weekDay" value="6" varComment="周五" type="checkbox"> 星期五 <input
								name="weekDay" value="7" varComment="周六" type="checkbox"> 星期六 <input
								name="weekDay" value="1" varComment="周日" type="checkbox"> 星期日&nbsp; </span>
						<span id="dateSpan" style="display: none;">每月 <select
								name="selectDate">
								<c:forEach begin="1" end="31" var="i" step="1">
									<option value="<c:out value="${i}" />" varComment="${i }日">
										<c:out value="${i}" />
									</option>
								</c:forEach>
								<option value="L" varComment="月末">月末</option>
							</select>日&nbsp; </span>
						<input type="text" id="" name="inputTime" value=""
							readonly="readonly" class="Wdate" style="width: 50px"
							onfocus="WdatePicker({dateFmt:'HH:mm'})" />点
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						短信内容：
					</td>
					<td class="tdl">
						<html:textarea property="sendContent" style="width:80%" title=""
							rows="5" styleClass="textarea" />
					</td>
				</tr>
			</table>
			<table width="90%" border="0" align="center" cellpadding="3"
				cellspacing="0">
				<tr class=trwhite heigth="40">
					<td colspan="2" style="text-align: center">
						<html:submit property="action" styleClass="button">提交</html:submit>
						<html:reset property="action" styleClass="button">取消</html:reset>
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>


