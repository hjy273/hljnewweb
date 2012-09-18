<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>�޸ĸ������</title>
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
					alert("������Ʋ���Ϊ�գ�");
					getElement("cutName").focus();
					return false;
				}
				if(getTrimValue("budget").length==0){
					alert("Ԥ�����Ϊ�գ�");
					getElement("budget").focus();
					return false;
				}
				if(valiD("budget")==false){
					alert("Ԥ�������Ϊ��Ч������֣�");
					getElement("budget").focus();
					return false;
				}
				if(getElement("isCompensationYes").checked==true){
					if(getTrimValue("compCompany").length==0){
						alert("�ⲹ��λ����Ϊ�գ�");
						getElement("compCompany").focus();
						return false;
					}
				}
				if(getTrimValue("cutPlace").length==0){
					alert("��ӵص㲻��Ϊ�գ�");
					getElement("cutPlace").focus();
					return false;
				}
				if(getTrimValue("beginTime").length==0){
					alert("���뿪ʼʱ�䲻��Ϊ�գ�");
					getElement("beginTime").focus();
					return false;
				}
				if(getTrimValue("endTime").length==0){
					alert("�������ʱ�䲻��Ϊ�գ�");
					getElement("endTime").focus();
					return false;
				}
				if(getTrimValue("trunk").length==0){
					alert("�м̶β���Ϊ�գ���ѡ���м̶Σ�");
					choose();
				    return false;
				}				
				if(getTrimValue("otherImpressCable").length==0){
					alert("δ��ά�м̶β���Ϊ�գ�");
					getElement("otherImpressCable").focus();
					return false;
				}
				if(valCharLength(getTrimValue("otherImpressCable"))>2048){
					alert("δ��ά�м̶��������ܳ���2048���ַ���");
					document.getElementById("otherImpressCable").focus();
					return false;
				}
				if(getTrimValue("useMateral").length==0){
					alert("ʹ�ò�����������Ϊ�գ�");
					getElement("useMateral").focus();
					return false;
				}
				if(valCharLength(getTrimValue("useMateral"))>2048){
					alert("ʹ�ò��������������ܳ���2048���ַ���");
					document.getElementById("useMateral").focus();
					return false;
				}
				if(getTrimValue("cutCause").length==0){
					alert("���ԭ����Ϊ�գ�");
					getElement("cutCause").focus();
					return false;
				}
				if(valCharLength(getTrimValue("cutCause"))>2048){
					alert("���ԭ���������ܳ���2048���ַ���");
					document.getElementById("cutCause").focus();
					return false;
				}
				if(getTrimValue("approveId").length==0){
					alert("����ѡ��һ������ˣ�");
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
		<template:titile value="�޸ĸ������"/>
		<html:form action="/cutAction.do?method=editCutApply" styleId="addCutApply" onsubmit="return checkForm();" enctype="multipart/form-data">
			<input type="hidden" name="deadline" value="<bean:write name='cut' property='deadline' format='yyyy/MM/dd HH:mm:ss' />" />
			<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">������ţ�</td>
					<td class="tdulright">
						<c:out value="${cut.workOrderId }"/>
						<input type="hidden" value="${cut.workOrderId }" name="workOrderId">
						<input type="hidden" value="${cut.id }" name="id">
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">������ƣ�</td>
					<td class="tdulright">
						<input name="cutName" type="text" id="cutName" class="inputtext" style="width:150px;" value="${cut.cutName }" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">��Ӽ���</td>
					<td class="tdulright">
						<input type="hidden" id="cutLevelValue" value="${cut.cutLevel }">
						<select name="cutLevel" id="cutLevel" style="width:150px;" class="inputtext">
							<option value="1">һ����</option>
							<option value="2">�������</option>
							<option value="3">Ԥ���</option>
						</select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ֳ������ˣ�</td>
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
					<td class="tdulleft" style="width:20%;">��ά�������ˣ�</td>
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
					<td class="tdulleft" style="width:20%;">Ԥ���</td>
					<td class="tdulright">
						<input name="budget" type="text" id="budget" value="${cut.budget }" class="inputtext" style="width:150px;" /> Ԫ
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�Ƿ����ⲹ��</td>
					<td class="tdulright">
						<c:if test="${cut.isCompensation==1}">
							<input name="isCompensation" type="radio" checked="checked" onclick="hiddenPlace()" value="1" id="isCompensationYes" />��
							<input type="radio" name="isCompensation" onclick="hiddenPlace()"  value="0" />��
						</c:if>
						<c:if test="${cut.isCompensation==0}">
							<input name="isCompensation" type="radio" onclick="hiddenPlace()" value="1" id="isCompensationYes" />��
							<input type="radio" name="isCompensation" checked="checked" onclick="hiddenPlace()"  value="0" />��
						</c:if>
					</td>
				</tr>
				<c:if test="${cut.isCompensation==1}">
					<tr id="compCompanyContr" class="trcolor">
						<td class="tdulleft" style="width:20%;">�ⲹ��λ��</td>
						<td class="tdulright">
							<input name="compCompany" type="text" id="compCompany" class="inputtext" style="width:150px;" value="${cut.compCompany }" />
							<font color="red">*</font>
						</td>
					</tr>
				</c:if>
				<c:if test="${cut.isCompensation==0}">
					<tr id="compCompanyContr" class="trcolor" style="display: none;">
						<td class="tdulleft" style="width:20%;">�ⲹ��λ��</td>
						<td class="tdulright">
							<input name="compCompany" type="text" id="compCompany" class="inputtext" style="width:150px;" value="${cut.compCompany }" />
							<font color="red">*</font>
						</td>
					</tr>
				</c:if>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">��ӵص㣺</td>
					<td class="tdulright">
						<input name="cutPlace" type="text" id="cutPlace" class="inputtext" style="width:150px;" value="${cut.cutPlace }" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�������ʱ�䣺</td>
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
						�D
						<input name="endTime" class="Wdate" id="endTime" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginTime\')}'})" readonly value="${formatEndTime }"/>
							<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�м̶Σ�</td>
					<td class="tdulright">
						<apptag:trunk id="trunk" state="edit" value="${sublineIds}" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">δ��ά�м̶Σ�</td>
					<td class="tdulright">
						<textarea name="otherImpressCable" rows="3" class="textarea"><c:out value="${cut.otherImpressCable}"/></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">ʹ�ò���������</td>
					<td class="tdulright">
						<textarea name="useMateral" rows="3" class="textarea"><c:out value="${cut.useMateral}"/></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">���ԭ��</td>
					<td class="tdulright">
						<textarea name="cutCause" rows="3" id="cutCause required" class="textarea"><c:out value="${cut.cutCause}"/></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">������</td>
					<td class="tdulright">
						<apptag:upload entityId="${cut.id}" entityType="LP_CUT" state="edit"/>
					</td>
				</tr>
				<tr class="trcolor">
					<apptag:approverselect label="�����" inputName="approveId,mobile"
							spanId="approverSpan" inputType="radio" colSpan="4" notAllowName="reader" 
							approverType="approver" objectId="${cut.id }" objectType="LP_CUT" />
				</tr>
				<tr class="trcolor">
					<apptag:approverselect label="������" inputName="reader,readerPhones"
							spanId="readerSpan" notAllowName="approveId" colSpan="4" 
							approverType="reader" objectId="${cut.id }" objectType="LP_CUT" />
				</tr>
			</table>
			<div style="height:20px; margin-left: 5%; width: 95%; margin-top: 5px;">
				<font color="red">�������Ϊ���������������������д���ޡ�</font>
			</div>	
			<div align="center" style="height:40px">
				<html:submit property="action" styleClass="button">�ύ</html:submit> &nbsp;&nbsp;
				<html:reset property="action" onclick="cancelApprove()" styleClass="button">��д</html:reset>&nbsp;&nbsp;
				<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
			</div>
		</html:form>
	</body>
</html>
