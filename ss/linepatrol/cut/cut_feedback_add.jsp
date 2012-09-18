<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linepatrol.cut.module.*" %>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>割接实施反馈</title>
		<script type="text/javascript" src="${ctx}/linepatrol/js/verify_material.js"></script>
		<script type="text/javascript">
		String.prototype.trim = function() {
			return this.replace(/^\s+|\s+$/g,"");
		};
			//当存在超时情况，显示超时原因
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
					if(getTrimValue("cancelCause").length==0){
						alert("取消割接原因不能为空！");
						getElement("cancelCause").focus();
						return;
					}
					if(valCharLength(getTrimValue("cancelCause"))>2048){
						alert("取消割接原因字数不能超过2048个字符！");
						document.getElementById("cancelCause").focus();
						return;
					}
					if(getTrimValue("approveId").length==0){
						alert("必须选择审核人！");
						return;
					}
					$("addCutFeedback").submit();
					return;
				}
				if(state=="2"){
					getCutTime();
					$("feedbackType").value=state;
					$("addCutFeedback").submit();
					return;
				}
				if(getTrimValue("bs").length==0){
					alert("影响基站数不能为空！");
					document.getElementById("bs").focus();
					return;
				}
				if(isInteger(getTrimValue("bs"))==false){
					alert("影响基站数必须为整数！");
					document.getElementById("bs").focus();
					return;
				}
				if(getTrimValue("td").length==0){
					alert("影响TD不能为空！");
					document.getElementById("td").focus();
					return;
				}
				if(isInteger(getTrimValue("td"))==false){
					alert("影响TD数必须为整数！");
					document.getElementById("td").focus();
					return;
				}
				if(getTrimValue("beginTime").length==0){
					alert("割接实际开始时间不能为空！");
					document.getElementById("beginTime").focus();
					return;
				}
				if(getTrimValue("endTime").length==0){
					alert("割接实际结束时间不能为空！");
					document.getElementById("endTime").focus();
					return;
				}
				
				if(document.getElementById('addCutFeedback').isTimeOut[0].checked==true){
					if(getTrimValue("timeOutCase").length==0){
						alert("超时原因不能为空！");
						getElement("timeOutCase").focus();
						return;
					}
					if(valCharLength(getTrimValue("timeOutCase"))>2048){
						alert("超时原因字数不能超过2048个字符！");
						getElement("timeOutCase").focus();
						return;
					}
				}
				if(getTrimValue("implementation").length==0){
					alert("实施方案不能为空！");
					document.getElementById("implementation").focus();
					return;
				}
				if(valCharLength(getTrimValue("implementation"))>2048){
					alert("实施方案字数不能超过2048个字符！");
					document.getElementById("implementation").focus();
					return;
				}
				if(getTrimValue("legacyQuestion").length==0){
					alert("遗留问题不能为空！");
					document.getElementById("legacyQuestion").focus();
					return;
				}
				if(valCharLength(getTrimValue("legacyQuestion"))>2048){
					alert("遗留问题字数不能超过2048个字符！");
					document.getElementById("legacyQuestion").focus();
					return;
				}
				if(getTrimValue("approveId").length==0){
					alert("必须选择审核人！");
					return;
				}
				if(verifyMaterial(document.getElementById('addCutFeedback'))==false){
					return;
				}
				getCutTime();
				$("feedbackType").value=state;
            	$("addCutFeedback").submit();
			}
			function getTrimValue(value){
				return document.getElementById(value).value.trim();
			}
			
			function getElement(value){
				return document.getElementById(value);
			}
			//判断是否为数字
			function valiD(id){
				var mysplit = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
				var obj = document.getElementById(id);
				if(mysplit.test(obj.value)){
					//return true;
				}
				else{
					obj.focus();
					return false;
				}
			}
			//判断只能是整数
			function isInteger(s){
				var number = "0123456789";
				for (i = 0; i < s.length;i++){   
       				var c = s.charAt(i);
       				if (number.indexOf(c) == -1) 
       				return false;
   				}
				return true
			}
			
			//判断字符长度
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
			function feedbackoperator(oper){
				var feedbacktable=document.getElementById("feedbacktable");
				var canceltable=document.getElementById("canceltable");
				var feedbacktablebutton=document.getElementById("feedbacktablebutton");
				var canceltablebutton=document.getElementById("canceltablebutton");
				var careRemark=document.getElementById("careRemark");
				var feedbackType=document.getElementById("feedbackType");
				if(oper=="1"){
					feedbacktable.style.display="block";
					feedbacktablebutton.style.display="block";
					canceltablebutton.style.display="none";
					canceltable.style.display="none";
					careRemark.style.display="block";
					feedbackType.value="0";
				}
				else{
					feedbacktable.style.display="none";
					feedbacktablebutton.style.display="none";
					canceltablebutton.style.display="block";
					canceltable.style.display="block";
					careRemark.style.display="none";
					feedbackType.value="1";
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
	<body>
		<template:titile value="实施反馈"/>
		<html:form action="/cutFeedbackAction.do?method=addCutFeedback" styleId="addCutFeedback" enctype="multipart/form-data">
			<table cellspacing="0" cellpadding="1" align="center" style="width:90%;border-bottom: 0px;" class="tabout">
				<tr class="trcolor">
					<td width="20%" class="tdulleft">
						流程操作
					</td>
					<td class="tdulright">
						<input type="radio" name="aaaa" onclick="feedbackoperator(1)" checked="checked">填写反馈
						<input type="radio" name="aaaa" onclick="feedbackoperator(0)">取消割接
					</td>
				</tr>
			</table>
			<%@include file="/linepatrol/cut/cut_apply_base.jsp" %>
			<table cellspacing="0" cellpadding="1" align="center" style="width:90%;border-top:0px;border-bottom: 0px;" class="tabout" id="feedbacktable">
				<tr style="display:none">
					<td>
						<input type="hidden" name="cutId" id="cutId" value="<c:out value='${cut.id }'/>" />
						<input type="hidden" name="feedbackType" value="1" id="feedbackType">
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">实际割接时间：</td>
					<td colspan="3" class="tdulright">
						<input name="beginTime" class="Wdate" id="beginTime" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate:'%y-%M-%d'})" readonly/>
						―
						<input name="endTime" class="Wdate" id="endTime" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'beginTime\')}'})" readonly onchange="getCutTime()"/>
						 <font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">是否中断业务：</td>
					<td class="tdulright" style="width:30%;">
						<input name="isInterrupt" type="radio" value="1" checked="checked" id="isInterruptYes" />是
						<input type="radio" name="isInterrupt" value="0" id="isInterruptNo"/>否
					</td>
					<td class="tdulleft" style="width:20%;">是否更新或增补标志牌：</td>
					<td class="tdulright" style="width:30%;">
						<input name="flag" type="radio" value="1" checked="checked" id="flagYes" />是
						<input type="radio" name="flag" value="0" id="flagNo" />否
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">影响2G基站数据量：</td>
					<td class="tdulright" style="width:30%;">
						<input type="text" name="bs" id="bs" class="inputtext" style="width:150px;" /> 个
						<font color="red">*</font>
					</td>
					<td class="tdulleft" style="width:20%;">影响TD站数量：</td>
					<td class="tdulright" style="width:30%;">
						<input type="text" name="td" id="td" class="inputtext" style="width:150px;" /> 个
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">割接时长：</td>
					<td class="tdulright" style="width:30%;">
						<input type="hidden" name="cutTime" id="cutTime">
						<span id="cutTimeShow"></span>&nbsp;小时
					</td>
					<td class="tdulleft" style="width:20%;">移动负责人：</td>
					<td class="tdulright" style="width:30%;">
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
						<apptag:materialselect label="割接实际用料" materialUseType="Use"/>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulright" colspan="4">
						<apptag:materialselect label="割接回收料入库" materialUseType="Recycle"/>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">是否超时：</td>
					<td class="tdulright" colspan="3">
						<input name="isTimeOut" type="radio" value="1" checked="checked" id="isTimeOutYes" onclick="hideTimeOutCase()" />是
						<input type="radio" name="isTimeOut" value="0" id="isTimeOutNo" onclick="hideTimeOutCase()" />否
					</td>
				</tr>
				<tr id="timeOutCaseCtr" class="trcolor">
					<td class="tdulleft" style="width:20%;">超时原因：</td>
					<td colspan="3" class="tdulright">
						<textarea name="timeOutCase" rows="3" id="timeOutCase" class="textarea"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">方案实施情况：</td>
					<td colspan="3" class="tdulright">
						<textarea name="implementation" rows="3" id="implementation" class="textarea"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">遗留问题：</td>
					<td colspan="3" class="tdulright">
						<textarea name="legacyQuestion" rows="3" id="legacyQuestion" class="textarea"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">反馈附件：</td>
					<td colspan="3" class="tdulright">
						<apptag:upload state="add"/>
					</td>
				</tr>
			</table>
			<table cellspacing="0" cellpadding="1" align="center" style="width:90%;border-top:0px;border-bottom: 0px;display: none;" class="tabout" id="canceltable">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">取消原因：</td>
					<td class="tdulright" colspan="3">
						<textarea name="cancelCause" id="cancelCause" class="textarea"  rows="3"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
			</table>
			<table cellspacing="0" cellpadding="1" align="center" style="width:90%;border-top:0px;border-bottom: 0px;" class="tabout">
				<tr>
					<td width="20%"></td>
					<td width="30%"></td>
					<td width="20%"></td>
					<td width="30%"></td>
				</tr>
				<tr class="trcolor">
					<apptag:approverselect label="审核人" inputName="approveId,mobiles"
							spanId="approverSpan" inputType="radio" colSpan="3" notAllowName="reader"
							approverType="approver" objectId="${cut.id }" objectType="LP_CUT" />
				</tr>
				<tr class="trcolor">
					<apptag:approverselect label="抄送人" inputName="reader,readerPhones"
							spanId="readerSpan" notAllowName="approveId" colSpan="3" 
							approverType="reader" objectId="${cut.id }" objectType="LP_CUT" />
				</tr>
			</table>
			<table cellspacing="0" cellpadding="1" align="center" style="width:90%;border-top:0px;border-bottom: 0px;display: none;" class="tabout" id="canceltablebutton">
				<tr class="trcolor">
					<td colspan="4" align="center" class="tdc">
						<html:button property="button" styleClass="button" onclick="checkForm(1)">提交</html:button> &nbsp;&nbsp;
						<html:reset property="action" styleClass="button">重写</html:reset>&nbsp;&nbsp;
						<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
					</td>
				</tr>
			</table>
			<table cellspacing="0" cellpadding="1" align="center" style="width:90%;border-top:0px;border-bottom: 0px;" class="tabout" id="feedbacktablebutton">
				<tr class="trcolor">
					<td colspan="4" align="center" class="tdc">
						<html:button property="button" styleClass="button" onclick="checkForm(0)">提交</html:button> &nbsp;&nbsp;
						<html:button property="action" styleClass="button" onclick="checkForm(2)">暂存</html:button>&nbsp;&nbsp;
						<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
					</td>
				</tr>
			</table>
			<table cellspacing="0" cellpadding="1" align="center" style="width:90%;border-top:0px;" class="tabout" id="careRemark">
				<tr class="trcolor">
					<td colspan="4">
						<font color="red">所有项均为必填项，若无内容描述请填写“无”</font>
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
