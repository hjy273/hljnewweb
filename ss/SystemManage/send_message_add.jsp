<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
	String.prototype.Trim = function() {
		var m = this.match(/^\s*(\S+(\s+\S+)*)\s*$/);
		return (m == null) ? "" : m[1];
	}
	//�ֻ�������֤ 
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
			alert("û��ѡ����Ž����˻��߽�����û���ֻ����룡");
			return false;
		}
		if(form1.sendContent.value==""){
			alert("û�������ֻ��������ݣ�");
			return false;
		}
		var mobilesStr=document.getElementById("otherMoblies").value;
		if(mobilesStr!=""){
			if(mobilesStr.split(",").length>0){
				var mobiles;
				if(mobilesStr.indexOf(",")==-1){
					mobiles=mobilesStr;
					if(!mobiles.isMobile()){
						alert("��������ȷ���ֻ����룡");
						return false;
					}
				}else{
					mobiles=mobilesStr.split(",");
					for(var index=0;index<mobiles.length;index++){
						if(!mobiles[index].isMobile()){
							alert("��������ȷ���ֻ����룡");
							return false;
							break;
						}
					}
				}
			}else{
				if(!mobilesStr.isMobile()){
					alert("��������ȷ���ֻ����룡");
					return false;
				}
			}
		}
		if(form1.sendMethod[1].checked){
		if(document.getElementById("sendTimeSpace")!=null){
			if(!document.getElementById("sendTimeSpace").value.time()){
				alert("ʱ����Ϊ������");
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
				sendCycleRule="ÿ��"+form1.inputTime.value;
			}
			if(form1.sendCycle.value=="1"){
				var weekDayValue="";
				sendCycleRule="ÿ"
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
				sendCycleRule="ÿ��"+form1.selectDate.options[form1.selectDate.options.selectedIndex].varComment+form1.inputTime.value;
			}
			for(i=0;i<form1.inputDate.length;i++){
				//alert("inputDate["+i+"]="+form1.inputDate[i].value);
			}
			form1.sendCycleRule.value=sendCycleRule;
		}
		return true;
	}
</script>
		<title>���Ͷ���</title>
	</head>
	<body>
		<template:titile value="���Ͷ���" />
		<html:form action="/sendmessage/send_message.do?method=sendMessage" styleId="form1"
			onsubmit="return checkForm();">
			<table width="90%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<apptag:processorselect
					inputName="departIds,acceptUserIds,mobileIds" exceptSelf="false"
					labelName="���Ž�����" displayType="mobile_contractor" colSpan="1" spanId="acceptUser"></apptag:processorselect>
				<tr class=trcolor>
					<td class="tdr">
						�������Ž��պ��룺
					</td>
					<td class="tdl">
						<input name="otherMobiles" value="" id="otherMoblies" type="text"
							style="width: 300px" />
						<br />
						<font color="#FF0000">����Ӣ�İ�ǡ�,���ָ�</font>"
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						���ŷ��ͷ�ʽ��
					</td>
					<td class="tdl">
						<input name="sendMethod" value="0" type="radio" checked
							onclick="timeInputSpan.style.display='none';timeSpaceTr.style.display='none';cycleTr.style.display='none';timeValueTr.style.display='none';" />
						��ʱ����
						<input name="sendMethod" value="1" type="radio"
							onclick="timeInputSpan.style.display='';timeSpaceTr.style.display='';cycleTr.style.display='none';timeValueTr.style.display='none';" />
						��ʱ����
						<input name="sendMethod" value="2" type="radio"
							onclick="timeInputSpan.style.display='none';timeSpaceTr.style.display='none';cycleTr.style.display='';timeValueTr.style.display='';" />
						�̶����ڷ���
					</td>
				</tr>
				<tr class=trcolor id="timeInputSpan" style="display: none;">
					<td class="tdr">
						���ŷ���ʱ�䣺
					</td>
					<td>
						<input type="text" id="protimeid" name="beginDate" value=""
							readonly="readonly" class="Wdate" style="width: 150px"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1'})" />
						��
						<input type="text" id="protimeid" name="endDate" value=""
							readonly="readonly" class="Wdate" style="width: 150px"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:$('beginDate')})" />
					</td>
				</tr>
				<tr class=trcolor id="timeSpaceTr" style="display: none;">
					<td class="tdr">
						���ŷ��ͼ����
					</td>
					<td class="tdl">
						<input name="sendTimeSpace" id="sendTimeSpace" value="" type="text"
							style="width: 100px" />
						<input name="sendIntervalType" value="-1" type="radio" checked />
						����
						<input name="sendIntervalType" value="0" type="radio" />
						��
						<input name="sendIntervalType" value="1" type="radio" />
						��
						<input name="sendIntervalType" value="2" type="radio" />
						ʱ
						<input name="sendIntervalType" value="3" type="radio" />
						��
					</td>
				</tr>
				<tr class=trcolor id="cycleTr" style="display: none;">
					<td class="tdr">
						�������ڣ�
					</td>
					<td>
						<select name="sendCycle" onchange="changeCycle(this.value);">
							<option value="0">
								ÿ��
							</option>
							<option value="1">
								ÿ��
							</option>
							<option value="2">
								ÿ��
							</option>
						</select>
					</td>
				</tr>
				<tr class=trcolor id="timeValueTr" style="display: none;">
					<td class="tdr">
						ָ�������еĹ̶�ʱ�䣺
					</td>
					<td class="tdl">
						<input name="sendCycleRule" value="" type="hidden" />
						<input name="inputDate" value="" type="hidden" /><!-- �� -->
						<input name="inputDate" value="" type="hidden" /><!-- �� -->
						<input name="inputDate" value="" type="hidden" /><!-- ʱ -->
						<input name="inputDate" value="" type="hidden" /><!-- �� -->
						<input name="inputDate" value="" type="hidden" /><!-- �� -->
						<input name="inputDate" value="" type="hidden" /><!-- �� -->
						<input name="inputDate" value="" type="hidden" /><!-- �� -->
						<span id="weekDaySpan" style="display: none;"> <input
								name="weekDay" value="2" varComment="��һ" type="checkbox"> ����һ <input
								name="weekDay" value="3" varComment="�ܶ�" type="checkbox"> ���ڶ� <input
								name="weekDay" value="4" varComment="����" type="checkbox"> ������ <input
								name="weekDay" value="5" varComment="����" type="checkbox"> ������<br /> <input
								name="weekDay" value="6" varComment="����" type="checkbox"> ������ <input
								name="weekDay" value="7" varComment="����" type="checkbox"> ������ <input
								name="weekDay" value="1" varComment="����" type="checkbox"> ������&nbsp; </span>
						<span id="dateSpan" style="display: none;">ÿ�� <select
								name="selectDate">
								<c:forEach begin="1" end="31" var="i" step="1">
									<option value="<c:out value="${i}" />" varComment="${i }��">
										<c:out value="${i}" />
									</option>
								</c:forEach>
								<option value="L" varComment="��ĩ">��ĩ</option>
							</select>��&nbsp; </span>
						<input type="text" id="" name="inputTime" value=""
							readonly="readonly" class="Wdate" style="width: 50px"
							onfocus="WdatePicker({dateFmt:'HH:mm'})" />��
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						�������ݣ�
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
						<html:submit property="action" styleClass="button">�ύ</html:submit>
						<html:reset property="action" styleClass="button">ȡ��</html:reset>
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>


