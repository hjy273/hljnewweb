<%@include file="/common/header.jsp"%>
<%@page language="java" contentType="text/html; charset=GBK"%>
<%@page import="com.cabletech.linepatrol.cut.module.*" %>

<html>
	<head>
		<title>�޸ĸ��ʵʩ����</title>
		<script type="text/javascript" src="${ctx}/linepatrol/js/verify_material.js"></script>
		<script type="text/javascript">
		String.prototype.trim = function() {
			return this.replace(/^\s+|\s+$/g,"");
		};
			//�����ڳ�ʱ�������ʾ��ʱԭ��
			function hideTimeOutCase(){
				var timeOutCaseCtr=document.getElementById("timeOutCaseCtr");
				var isTimeOutYes=document.getElementById("isTimeOutYes");
				if(isTimeOutYes.checked==true){
					timeOutCaseCtr.style.display="block";
				}else{
					timeOutCaseCtr.style.display="none";
				}
			}
			function checkForm(state){
				if(state=="1"){
					$('saveflag').value="1";
					document.getElementById('submitForm1').submit();
					return false;
				}
				if(getTrimValue("bs").length==0){
					alert("Ӱ���վ������Ϊ�գ�");
					return false;
					document.getElementById("bs").focus();
				}
				if(isInteger(getTrimValue("bs"))==false){
					alert("Ӱ���վ������Ϊ������");
					document.getElementById("bs").focus();
					return false;
				}
				if(getTrimValue("td").length==0){
					alert("Ӱ��TD����Ϊ�գ�");
					document.getElementById("td").focus();
					return false;
				}
				if(isInteger(getTrimValue("td"))==false){
					alert("Ӱ��TD������Ϊ������");
					document.getElementById("td").focus();
					return false;
				}
				if(getTrimValue("beginTime").length==0){
					alert("���ʵ�ʿ�ʼʱ�䲻��Ϊ�գ�");
					document.getElementById("endTime").focus();
					return false;
				}
				if(getTrimValue("endTime").length==0){
					alert("���ʵ�ʽ���ʱ�䲻��Ϊ�գ�");
					document.getElementById("endTime").focus();
					return false;
				}
				if(document.getElementById('submitForm1').isTimeOut[0].checked==true){
					if(getTrimValue("timeOutCase").length==0){
						alert("��ʱԭ����Ϊ�գ�");
						getElement("timeOutCase").focus();
						return false;
					}
					if(valCharLength(getTrimValue("timeOutCase"))>2048){
						alert("��ʱԭ���������ܳ���2048���ַ���");
						getElement("timeOutCase").focus();
						return false;
					}
				}
				if(getTrimValue("implementation").length==0){
					alert("ʵʩ��������Ϊ�գ�");
					document.getElementById("implementation").focus();
					return false;
				}
				if(valCharLength(getTrimValue("implementation"))>2048){
					alert("ʵʩ�����������ܳ���2048���ַ���");
					document.getElementById("implementation").focus();
					return false;
				}
				if(getTrimValue("legacyQuestion").length==0){
					alert("�������ⲻ��Ϊ�գ�");
					document.getElementById("legacyQuestion").focus();
					return false;
				}
				if(valCharLength(getTrimValue("legacyQuestion"))>2048){
					alert("���������������ܳ���2048���ַ���");
					document.getElementById("legacyQuestion").focus();
					return false;
				}
				if(getTrimValue("approveId").length==0){
					alert("����ѡ������ˣ�");
					return false;
				}
				getCutTime();
				if(verifyMaterial(document.getElementById('submitForm1'))==false){
					return false;
				}
				document.getElementById('submitForm1').submit();
			}
			function getTrimValue(v){
				return document.getElementById(v).value.trim();
			}
			function getElement(value){
				return document.getElementById(value);
			}
			//�ж��Ƿ�Ϊ����
			function valiD(id){
				var mysplit = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
				var obj = document.getElementById(id);
				if(mysplit.test(obj.value)){
					//return true;
				}
				else{
					alert("����д�����ֲ��Ϸ�,����������");
					obj.focus();
					//obj.value = "0.00";
					return false;
				}
			}
			//�ж�ֻ��������
			function isInteger(s){
				var number = "0123456789";
				for (i = 0; i < s.length;i++){   
       				var c = s.charAt(i);
       				if (number.indexOf(c) == -1) 
       				return false;
   				}
				return true
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
			function selectDefault(){
				var selectId=document.getElementById("mobileChargeMan");
				var actValue=document.getElementById("mobileChargeManInput").value;
				for (i=0;i<selectId.length;i++)
				if(selectId.options[i].value == actValue){
					selectId.options[i].selected=true;
					break; 
				}
			}
			function getCutTime(){
				var strBeginDate=getTrimValue("beginTime");
			
				var startYear=strBeginDate.substring(0,4);
				var startMonth=strBeginDate.substring(5,7);
				var startDay=strBeginDate.substring(8,10);
				var startHours=strBeginDate.substring(11,13);
				var startMinutes=strBeginDate.substring(14,16);
				var startSeconds=strBeginDate.substring(17,19);
				beginDate=new Date(startYear,startMonth,startDay,startHours,startMinutes,startSeconds);
				
				var strEndDate=getTrimValue("endTime");
				
				var endYear=strEndDate.substring(0,4);
				var endMonth=strEndDate.substring(5,7);
				var endDay=strEndDate.substring(8,10);
				var endHours=strEndDate.substring(11,13);
				var endMinutes=strEndDate.substring(14,16);
				var endSeconds=strEndDate.substring(17,19);
				endDate=new Date(endYear,endMonth,endDay,endHours,endMinutes,endSeconds);
				var hours = ((endDate-beginDate)/60/60/1000).toFixed(2);
				getElement("cutTime").value=hours;
				getElement("cutTimeShow").innerHTML=hours;
			}
		</script>
	</head>
	<body onload="selectDefault()">
		<template:titile value="�޸�ʵʩ����"/>
		<html:form action="/cutFeedbackAction.do?method=editCutFeedback" styleId="submitForm1" enctype="multipart/form-data">
			<jsp:include page="cut_apply_base.jsp"/>
			<table cellspacing="0" cellpadding="1" align="center" style="width:90%;border-top:0px;" class="tabout">
				<tr style="display:none">
					<td>
						<input type="hidden" name="id" value="<c:out value='${cutFeedback.id }'/>" />
						<input type="hidden" name="cutId" value="<c:out value='${cutFeedback.cutId }'/>" />
						<input type="hidden" name="unapproveNumber" value="<c:out value='${cutFeedback.unapproveNumber }'/>" />
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">ʵ�ʸ��ʱ�䣺</td>
					<td colspan="3" class="tdulright">
						<fmt:formatDate  value="${cutFeedback.beginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatBeginTime"/>
						<fmt:formatDate  value="${cutFeedback.endTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatEndTime"/>
						<input name="beginTime" class="Wdate" id="beginTime" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate:'%y-%M-%d'})" readonly value="<c:out value='${formatBeginTime }' />" onchange="getCutTime()"/>
						�D
						<input name="endTime" class="Wdate" id="endTime" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'beginTime\')}'})" readonly value="<c:out value='${formatEndTime }' />" onchange="getCutTime()"/>
						<font color="red">*</font>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�Ƿ��ж�ҵ��</td>
					<td class="tdulright" style="width:30%;">
						<input name="isInterrupt" type="radio" value="1" checked="checked" id="isInterruptYes" />��
						<input type="radio" name="isInterrupt" value="0" id="isInterruptNo"/>��
					</td>
					<td class="tdulleft" style="width:20%;">�Ƿ���»�������־�ƣ�</td>
					<td class="tdulright" style="width:30%;">
						<input name="flag" type="radio" value="1" checked="checked" id="flagYes" />��
						<input type="radio" name="flag" value="0" id="flagNo" />��
					</td>
				</tr>
				<tr class="trcolor">
					<fmt:formatNumber value="${cutFeedback.bs}" pattern="#.#" var="bs"/>
					<td class="tdulleft" style="width:20%;">Ӱ��2G��վ��������</td>
					<td class="tdulright" style="width:30%;">
						<input type="text" name="bs" id="bs" class="inputtext" value="<c:out value='${bs }' />" style="width:150px;" />
						<font color="red">*</font>
					</td>
					<fmt:formatNumber value="${cutFeedback.td}" pattern="#.#" var="td"/>
					<td class="tdulleft" style="width:20%;">Ӱ��TDվ������</td>
					<td class="tdulright" style="width:30%;">
						<input type="text" name="td" name="td" id="td" class="inputtext" value="<c:out value='${td }' />" style="width:150px;" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<fmt:formatNumber value="${cutFeedback.cutTime}" pattern="#.##" var="cutTime"/>
					<td class="tdulleft" style="width:20%;">���ʱ����</td>
					<td class="tdulright" style="width:30%;">
						<span id="cutTimeShow"><c:out value='${cutTime }' /></span>&nbsp;Сʱ
						<input type="hidden" name="cutTime" class="inputtext" id="cutTime" value="<c:out value='${cutTime }' />" />
					</td>
					<td class="tdulleft" style="width:20%;">�ƶ������ˣ�</td>
					<td class="tdulright" style="width:30%;" colspan="3">
						<input type="hidden" value="${cutFeedback.mobileChargeMan}" id="mobileChargeManInput">
						<select name="mobileChargeMan" id="mobileChargeMan" style="width:150px;" class="inputtext">
							<c:forEach items="${mobiles}" var="userInfo">
								<option value="${userInfo.userName }">
									<c:out value="${userInfo.userName }"></c:out>
								</option>
							</c:forEach>
						</select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulright" colspan="4">
						<apptag:materialselect label="���ʵ������" materialUseType="Use" objectId="${cut.id }" useType="cut" />
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulright" colspan="4">
						<apptag:materialselect label="��ӻ��������" materialUseType="Recycle" objectId="${cut.id }" useType="cut" />
					</td>
				</tr>
				<c:if test="${cutFeedback.isTimeOut=='1' }">
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">�Ƿ�ʱ��</td>
						<td class="tdulright" colspan="3">
							<input name="isTimeOut" type="radio" value="1" checked="checked" id="isTimeOutYes" onclick="hideTimeOutCase()" />��
							<input type="radio" name="isTimeOut" value="0" id="isTimeOutNo" onclick="hideTimeOutCase()" />��
						</td>
					</tr>
					<tr id="timeOutCaseCtr" class="trcolor">
						<td class="tdulleft" style="width:20%;">��ʱԭ��</td>
						<td colspan="3" class="tdulright">
							<textarea name="timeOutCase" rows="3" id="timeOutCase" class="textarea"><c:out value="${cutFeedback.timeOutCase }"/></textarea>
							<font color="red">*</font>
						</td>
					</tr>
				</c:if>
				<c:if test="${cutFeedback.isTimeOut=='0' }">
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">�Ƿ�ʱ��</td>
						<td class="tdulright" colspan="3">
							<input name="isTimeOut" type="radio" value="1" id="isTimeOutYes" onclick="hideTimeOutCase()" />��
							<input type="radio" name="isTimeOut" value="0"checked="checked" id="isTimeOutNo" onclick="hideTimeOutCase()" />��
						</td>
					</tr>
					<tr id="timeOutCaseCtr" class="trcolor" style="display:none;">
						<td class="tdulleft" style="width:20%;">��ʱԭ��</td>
						<td colspan="3" class="tdulright">
							<textarea name="timeOutCase" rows="3" id="timeOutCase" class="textarea"><c:out value="${cutFeedback.timeOutCase }"/></textarea>
							<font color="red">*</font>
						</td>
					</tr>
				</c:if>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">����ʵʩ�����</td>
					<td colspan="3" class="tdulright">
						<textarea name="implementation" rows="3" id="implementation" class="textarea"><c:out value="${cutFeedback.implementation }"/></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�������⣺</td>
					<td colspan="3" class="tdulright">
						<textarea name="legacyQuestion" rows="3" id="legacyQuestion" class="textarea"><c:out value="${cutFeedback.legacyQuestion }"/></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<apptag:approverselect label="�����" inputName="approveId,mobiles"
							spanId="approverSpan" inputType="radio" notAllowName="reader" 
							approverType="approver" objectId="${cutFeedback.id }" objectType="LP_CUT_FEEDBACK" />
				</tr>
				<tr class="trcolor">
					<apptag:approverselect label="������" inputName="reader,readerPhones"
							spanId="readerSpan" notAllowName="approveId"
							approverType="reader" objectId="${cutFeedback.id }" objectType="LP_CUT_FEEDBACK" />
				</tr>
				<tr class="trcolor">
					<td align="center" class="tdulleft" style="width:20%;">����������</td>
					<td colspan="3"  class="tdulright">
						<apptag:upload entityId="${cutFeedback.id}" entityType="LP_CUT_FEEDBACK" state="edit"/>
					</td>
				</tr>
				<tr class="trcolor">
					<td colspan="4" align="center" class="tdc">
						<input type="hidden" id="saveflag" name="saveflag" value="0">
						<html:button property="action" styleClass="button" onclick="checkForm(0)">�ύ</html:button> &nbsp;&nbsp;
						<html:button property="action" styleClass="button" onclick="checkForm(1)">�ݴ�</html:button>&nbsp;&nbsp;
						<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
					</td>
				</tr>
				<tr class="trcolor">
					<td colspan="4">
						<font color="red">�������Ϊ���������������������д���ޡ�</font>
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
