<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>修改割接申请</title>
		<script type="text/javascript" language="javascript">
			function hiddenPlace(){
				var compCompanyContr=getElement("compCompanyContr");
				var isCompensationYes=getElement("isCompensationYes");
				if(isCompensationYes.checked==true){
					compCompanyContr.style.display="block";
				}else{
					compCompanyContr.style.display="none";
				}
			}
			function checkForm(){
				if(getTrimValue("cutName").length==0){
					alert("割接名称不能为空！");
					getElement("cutName").focus();
					return false;
				}
				if(getTrimValue("budget").length==0){
					alert("预算金额不能为空！");
					getElement("budget").focus();
					return false;
				}
				if(valiD("budget")==false){
					alert("预算金额必须为有效金额数字！");
					getElement("budget").focus();
					return false;
				}
				if(getElement("isCompensationYes").checked==true){
					if(getTrimValue("compCompany").length==0){
						alert("赔补单位不能为空！");
						getElement("compCompany").focus();
						return false;
					}
				}
				if(getTrimValue("cutPlace").length==0){
					alert("割接地点不能为空！");
					getElement("cutPlace").focus();
					return false;
				}
				if(getTrimValue("beginTime").length==0){
					alert("申请开始时间不能为空！");
					getElement("beginTime").focus();
					return false;
				}
				if(getTrimValue("endTime").length==0){
					alert("申请结束时间不能为空！");
					getElement("endTime").focus();
					return false;
				}
				if(getTrimValue("trunk").length==0){
					alert("中继段不能为空，请选择中继段！");
					choose();
				    return false;
				}				
				if(getTrimValue("otherImpressCable").length==0){
					alert("未交维中继段不能为空！");
					getElement("otherImpressCable").focus();
					return false;
				}
				if(valCharLength(getTrimValue("otherImpressCable"))>2048){
					alert("未交维中继段字数不能超过2048个字符！");
					document.getElementById("otherImpressCable").focus();
					return false;
				}
				if(getTrimValue("useMateral").length==0){
					alert("使用材料描述不能为空！");
					getElement("useMateral").focus();
					return false;
				}
				if(valCharLength(getTrimValue("useMateral"))>2048){
					alert("使用材料描述字数不能超过2048个字符！");
					document.getElementById("useMateral").focus();
					return false;
				}
				if(getTrimValue("cutCause").length==0){
					alert("割接原因不能为空！");
					getElement("cutCause").focus();
					return false;
				}
				if(valCharLength(getTrimValue("cutCause"))>2048){
					alert("割接原因字数不能超过2048个字符！");
					document.getElementById("cutCause").focus();
					return false;
				}
				if(getTrimValue("approveId").length==0){
					alert("至少选择一个审核人！");
					return false;
				}
			}
			function getTrimValue(value){
				return document.getElementById(value).value.trim();
			}
			String.prototype.trim = function() {
				return this.replace(/^\s+|\s+$/g,"");
			}
			function getElement(value){
				return document.getElementById(value);
			}
			function cancelApprove(){
				document.getElementById("approverSpan").innerHTML="";
			}
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
			function selectDefault(selectOption,actValue){
				var selectOption=document.getElementById(selectOption);
				var actValue=document.getElementById(actValue).value;
				for (i=0;i<selectOption.length;i++)
				if(selectOption.options[i].value == actValue){
					selectOption.options[i].selected=true;
					break; 
				}
			}
			function onloadData(){
				selectDefault("cutLevel","cutLevelValue");
				selectDefault("liveChargeman","liveChargemanValue");
				selectDefault("chargeMan","chargeManValue");
			}
		</script>
	</head>
	<body onload="onloadData()">
		<template:titile value="修改割接申请"/>
		<html:form action="/cutAction.do?method=editCutApply" styleId="addCutApply" onsubmit="return checkForm();" enctype="multipart/form-data">
			<input type="hidden" name="deadline" value="<bean:write name='cut' property='deadline' format='yyyy/MM/dd HH:mm:ss' />" />
			<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">工单编号：</td>
					<td class="tdulright">
						<c:out value="${cut.workOrderId }"/>
						<input type="hidden" value="${cut.workOrderId }" name="workOrderId">
						<input type="hidden" value="${cut.id }" name="id">
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">割接名称：</td>
					<td class="tdulright">
						<input name="cutName" type="text" id="cutName" class="inputtext" style="width:150px;" value="${cut.cutName }" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">割接级别：</td>
					<td class="tdulright">
						<input type="hidden" id="cutLevelValue" value="${cut.cutLevel }">
						<select name="cutLevel" id="cutLevel" style="width:150px;" class="inputtext">
							<option value="1">一般割接</option>
							<option value="2">紧急割接</option>
							<option value="3">预割接</option>
						</select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">现场负责人：</td>
					<td class="tdulright">
						<input type="hidden" id="liveChargemanValue" value="${cut.liveChargeman }">
						<select name="liveChargeman" id="liveChargeman" style="width:150px;" class="inputtext">
							<c:forEach items="${cons}" var="con">
								<option value="${con.name }">
									<c:out value="${con.name }"></c:out>
								</option>
							</c:forEach>
						</select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">线维区域负责人：</td>
					<td class="tdulright">
						<input type="hidden" id="chargeManValue" value="${cut.chargeMan }">
						<select name="chargeMan" id="chargeMan" style="width:150px;" class="inputtext">
							<c:forEach items="${mobiles}" var="mobile">
								<option value="${mobile.userName }">
									<c:out value="${mobile.userName }"></c:out>
								</option>
							</c:forEach>
						</select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">预算金额：</td>
					<td class="tdulright">
						<input name="budget" type="text" id="budget" value="${cut.budget }" class="inputtext" style="width:150px;" /> 元
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">是否有赔补：</td>
					<td class="tdulright">
						<c:if test="${cut.isCompensation==1}">
							<input name="isCompensation" type="radio" checked="checked" onclick="hiddenPlace()" value="1" id="isCompensationYes" />是
							<input type="radio" name="isCompensation" onclick="hiddenPlace()"  value="0" />否
						</c:if>
						<c:if test="${cut.isCompensation==0}">
							<input name="isCompensation" type="radio" onclick="hiddenPlace()" value="1" id="isCompensationYes" />是
							<input type="radio" name="isCompensation" checked="checked" onclick="hiddenPlace()"  value="0" />否
						</c:if>
					</td>
				</tr>
				<c:if test="${cut.isCompensation==1}">
					<tr id="compCompanyContr" class="trcolor">
						<td class="tdulleft" style="width:20%;">赔补单位：</td>
						<td class="tdulright">
							<input name="compCompany" type="text" id="compCompany" class="inputtext" style="width:150px;" value="${cut.compCompany }" />
							<font color="red">*</font>
						</td>
					</tr>
				</c:if>
				<c:if test="${cut.isCompensation==0}">
					<tr id="compCompanyContr" class="trcolor" style="display: none;">
						<td class="tdulleft" style="width:20%;">赔补单位：</td>
						<td class="tdulright">
							<input name="compCompany" type="text" id="compCompany" class="inputtext" style="width:150px;" value="${cut.compCompany }" />
							<font color="red">*</font>
						</td>
					</tr>
				</c:if>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">割接地点：</td>
					<td class="tdulright">
						<input name="cutPlace" type="text" id="cutPlace" class="inputtext" style="width:150px;" value="${cut.cutPlace }" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">割接申请时间：</td>
					<td class="tdulright">
						<fmt:formatDate  value="${cut.beginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatBeginTime"/>
						<fmt:formatDate  value="${cut.endTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatEndTime"/>
						<fmt:formatDate  value="${cut.replyBeginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatReplyBeginTime"/>
						<fmt:formatDate  value="${cut.replyEndTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatReplyEndTime"/>
						<input type="hidden" value="${formatReplyBeginTime }" name="replyBeginTime">
						<input type="hidden" value="${formatReplyEndTime }" name="replyEndTime">
						<input type="hidden" name="unapproveNumber" value="${cut.unapproveNumber }">
						<input name="beginTime" class="Wdate" id="beginTime" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'%y-%M-%d'})" readonly value="${formatBeginTime }"/>
						―
						<input name="endTime" class="Wdate" id="endTime" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginTime\')}'})" readonly value="${formatEndTime }"/>
							<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">中继段：</td>
					<td class="tdulright">
						<apptag:trunk id="trunk" state="edit" value="${sublineIds}" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">未交维中继段：</td>
					<td class="tdulright">
						<textarea name="otherImpressCable" rows="3" class="textarea"><c:out value="${cut.otherImpressCable}"/></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">使用材料描述：</td>
					<td class="tdulright">
						<textarea name="useMateral" rows="3" class="textarea"><c:out value="${cut.useMateral}"/></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">割接原因：</td>
					<td class="tdulright">
						<textarea name="cutCause" rows="3" id="cutCause required" class="textarea"><c:out value="${cut.cutCause}"/></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">附件：</td>
					<td class="tdulright">
						<apptag:upload entityId="${cut.id}" entityType="LP_CUT" state="edit"/>
					</td>
				</tr>
				<tr class="trcolor">
					<apptag:approverselect label="审核人" inputName="approveId,mobile"
							spanId="approverSpan" inputType="radio" colSpan="4" notAllowName="reader" 
							approverType="approver" objectId="${cut.id }" objectType="LP_CUT" />
				</tr>
				<tr class="trcolor">
					<apptag:approverselect label="抄送人" inputName="reader,readerPhones"
							spanId="readerSpan" notAllowName="approveId" colSpan="4" 
							approverType="reader" objectId="${cut.id }" objectType="LP_CUT" />
				</tr>
			</table>
			<div style="height:20px; margin-left: 5%; width: 95%; margin-top: 5px;">
				<font color="red">所有项均为必填项，若无内容描述请填写“无”</font>
			</div>	
			<div align="center" style="height:40px">
				<html:submit property="action" styleClass="button">提交</html:submit> &nbsp;&nbsp;
				<html:reset property="action" onclick="cancelApprove()" styleClass="button">重写</html:reset>&nbsp;&nbsp;
				<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
			</div>
		</html:form>
	</body>
</html>
