<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>添加割接申请</title>
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
			function checkForm(state){
				if(state=='1'){
					$("addCutApply").submit();
					return;
				}
				if(getTrimValue("cutName").length==0){
					alert("割接名称不能为空！");
					getElement("cutName").focus();
					return;
				}
				if(getTrimValue("budget").length==0){
					alert("预算金额不能为空！");
					getElement("budget").focus();
					return;
				}
				if(valiD("budget")==false){
					alert("预算金额必须为有效金额数字！");
					getElement("budget").focus();
					return;
				}
				if(getElement("isCompensationYes").checked==true){
					if(getTrimValue("compCompany").length==0){
						alert("赔补单位不能为空！");
						getElement("compCompany").focus();
						return;
					}
				}
				if(getTrimValue("cutPlace").length==0){
					alert("割接地点不能为空！");
					getElement("cutPlace").focus();
					return;
				}
				if(getTrimValue("beginTime").length==0){
					alert("申请开始时间不能为空！");
					getElement("beginTime").focus();
					return;
				}
				if(getTrimValue("endTime").length==0){
					alert("申请结束时间不能为空！");
					getElement("endTime").focus();
					return;
				}
				if(getTrimValue("trunk").length==0){
					alert("中继段不能为空，请选择中继段！");
					choose();
				    return;
				}				
				if(getTrimValue("otherImpressCable").length==0){
					alert("未交维中继段不能为空！");
					getElement("otherImpressCable").focus();
					return;
				}
				if(valCharLength(getTrimValue("otherImpressCable"))>2048){
					alert("未交维中继段字数不能超过2048个字符！");
					document.getElementById("otherImpressCable").focus();
					return;
				}
				if(getTrimValue("useMateral").length==0){
					alert("使用材料描述不能为空！");
					getElement("useMateral").focus();
					return;
				}
				if(valCharLength(getTrimValue("useMateral"))>2048){
					alert("使用材料描述字数不能超过2048个字符！");
					document.getElementById("useMateral").focus();
					return;
				}
				if(getTrimValue("cutCause").length==0){
					alert("割接原因不能为空！");
					getElement("cutCause").focus();
					return;
				}
				if(valCharLength(getTrimValue("cutCause"))>2048){
					alert("割接原因字数不能超过2048个字符！");
					document.getElementById("cutCause").focus();
					return;
				}
				if(getTrimValue("approveId").length==0){
					alert("至少选择一个审核人！");
					return;
				}
				$('submit1').disabled = "disabled";
				$("applyState").value=state;
            	$("addCutApply").submit();
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
				var mysplit = /^\d{1,20}[\.]{0,1}\d{0,2}$/;
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
		</script>
	</head>
	<body>
		<template:titile value="割接申请"/>
		<html:form action="/cutAction.do?method=addCutApply" styleId="addCutApply" enctype="multipart/form-data">
			<table border="1" align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">工单编号：</td>
					<td class="tdulright">
						<input name="workOrderId" type="hidden" value="<c:out value='${workOrderId }'/>" />
						<b><c:out value="${workOrderId }"/></b>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">割接名称：</td>
					<td class="tdulright">
						<input name="cutName" type="text" id="cutName" class="inputtext" style="width:150px;"/>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">割接级别：</td>
					<td class="tdulright">
						<select name="cutLevel" id="cutLevel" style="width:200" style="width:150px;">
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
						<select name="liveChargeman" style="width:150px;">
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
						<select name="chargeMan" style="width:150px;">
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
						<input name="budget" type="text" id="budget" class="inputtext" style="width:200" style="width:150px;" /> 元
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">是否有赔补：</td>
					<td class="tdulright">
						<input name="isCompensation" type="radio" checked="checked" onclick="hiddenPlace()" value="1" id="isCompensationYes" />是
						<input type="radio" name="isCompensation" onclick="hiddenPlace()"  value="0" />否
					</td>
				</tr>
				<tr id="compCompanyContr" class="trcolor">
					<td class="tdulleft" style="width:20%;">赔补单位：</td>
					<td class="tdulright">
						<input name="compCompany" type="text" id="compCompany" class="inputtext" style="width:150px;" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">割接地点：</td>
					<td class="tdulright">
						<input name="cutPlace" type="text" id="cutPlace" class="inputtext" style="width:150px;" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">割接申请时间：</td>
					<td class="tdulright">
						<input name="beginTime" class="Wdate" id="beginTime" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'%y-%M-%d'})" readonly/>
						―
						<input name="endTime" class="Wdate" id="endTime" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginTime\')}'})" readonly/>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">中继段：</td>
					<td class="tdulright">
						<apptag:trunk id="trunk" state="add" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">未交维中继段：</td>
					<td class="tdulright">
						<textarea name="otherImpressCable" rows="3" id="otherImpressCable" class="textarea"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">使用材料描述：</td>
					<td class="tdulright">
						<textarea name="useMateral" rows="3" id="useMateral" class="textarea"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">割接原因：</td>
					<td class="tdulright">
						<textarea name="cutCause" rows="3" id="cutCause" class="textarea"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<apptag:approverselect label="审核人" inputName="approveId,mobile"
							spanId="approverSpan" inputType="radio" colSpan="4" notAllowName="reader" />
				</tr>
				<tr class="trcolor">
					<apptag:approverselect label="抄送人" inputName="reader,readerPhones"
							spanId="readerSpan" notAllowName="approveId" colSpan="4" />
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">割接申请附件：</td>
					<td class="tdulright">
						<apptag:upload state="add"/>
					</td>
				</tr>
			</table>
			<div style="height:20px; margin-left: 5%; width: 95%; margin-top: 5px;">
				<font color="red">所有项均为必填项，若无内容描述请填写“无”</font>
			</div>
			<div align="center" style="height:40px">
				<input type="hidden" name="applyState" id="applyState" value="1"/>
				<input type="button" onclick="checkForm('0')" id="submit1"  class="button" value="提交">&nbsp;&nbsp;
				<input type="button" onclick="checkForm('1')" id="submit2" class="button" value="暂存">&nbsp;&nbsp;
				<html:reset property="action" onclick="cancelApprove()" styleClass="button">重写</html:reset>
			</div>
		</html:form>
	</body>
</html>
