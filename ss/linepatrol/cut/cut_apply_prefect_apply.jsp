<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>���Ƹ������</title>
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
					$('applyState').value="1";
					$("perfectCutApply").submit();
					return;
				}
				$('applyState').value="0";
				if(getTrimValue("cutName").length==0){
					alert("������Ʋ���Ϊ�գ�");
					getElement("cutName").focus();
					return;
				}
				if(getTrimValue("budget").length==0){
					alert("Ԥ�����Ϊ�գ�");
					getElement("budget").focus();
					return;
				}
				if(valiD("budget")==false){
					alert("Ԥ�������Ϊ���֣�");
					getElement("budget").focus();
					return;
				}
				if(getElement("isCompensationYes").checked==true){
					if(getTrimValue("compCompany").length==0){
						alert("�ⲹ��λ����Ϊ�գ�");
						getElement("compCompany").focus();
						return;
					}
				}
				if(getTrimValue("cutPlace").length==0){
					alert("��ӵص㲻��Ϊ�գ�");
					getElement("cutPlace").focus();
					return;
				}
				if(getTrimValue("beginTime").length==0){
					alert("���뿪ʼʱ�䲻��Ϊ�գ�");
					getElement("beginTime").focus();
					return;
				}
				if(getTrimValue("endTime").length==0){
					alert("�������ʱ�䲻��Ϊ�գ�");
					getElement("endTime").focus();
					return;
				}
				if(getTrimValue("trunk").length==0){
					alert("�м̶β���Ϊ�գ���ѡ���м̶Σ�");
					choose();
				    return;
				}		
				
				if(getTrimValue("otherImpressCable").length==0){
					alert("δ��ά�м̶β���Ϊ�գ�");
					getElement("otherImpressCable").focus();
					return;
				}
				
				if(valCharLength(getTrimValue("otherImpressCable"))>2048){
					alert("δ��ά�м̶��������ܳ���2048���ַ���");
					document.getElementById("otherImpressCable").focus();
					return;
				}
				if(getTrimValue("useMateral").length==0){
					alert("ʹ�ò�����������Ϊ�գ�");
					getElement("useMateral").focus();
					return;
				}
				if(valCharLength(getTrimValue("useMateral"))>2048){
					alert("ʹ�ò��������������ܳ���2048���ַ���");
					document.getElementById("useMateral").focus();
					return;
				}
				if(getTrimValue("cutCause").length==0){
					alert("���ԭ����Ϊ�գ�");
					getElement("cutCause").focus();
					return;
				}
				if(valCharLength(getTrimValue("cutCause"))>2048){
					alert("���ԭ���������ܳ���2048���ַ���");
					document.getElementById("cutCause").focus();
					return;
				}
				if(getTrimValue("approveId").length==0){
					alert("����ѡ��һ������ˣ�");
					return;
				}
				$("perfectCutApply").submit();
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
		<template:titile value="���Ƹ������"/>
		<html:form action="/cutAction.do?method=addCutApply" styleId="perfectCutApply" enctype="multipart/form-data">
			<table border="1" align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">������ţ�</td>
					<td class="tdulright">
						<c:out value="${cut.workOrderId }"/>
						<input type="hidden" value="${cut.workOrderId }" name="workOrderId">
						<input type="hidden" value="${cut.id }" name="id">
						<!-- <input type="hidden" name="applyState" value="${cut.applyState }" > -->
						<html:hidden property="applyState" styleId="applyState"/>
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
						<select name="cutLevel" id="cutLevel"  style="width:150px;" class="inputtext">
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
						<select name="liveChargeman" id="liveChargeman"  style="width:150px;" class="inputtext">
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
						<select name="chargeMan" id="chargeMan"  style="width:150px;" class="inputtext">
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
						<input name="budget" type="text" id="budget" value="${cut.budget }" class="inputtext"  style="width:150px;" /> Ԫ
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
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss'})" readonly value="${formatBeginTime }"/>
						�D
						<input name="endTime" class="Wdate" id="endTime" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss'})" readonly value="${formatEndTime }"/>
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
						<textarea id="otherImpressCable" name="otherImpressCable" rows="3" class="textarea"><c:out value="${cut.otherImpressCable}"/></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">ʹ�ò���������</td>
					<td class="tdulright">
						<textarea id="useMateral" name="useMateral" class="textarea" rows="3"><c:out value="${cut.useMateral}"/></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">���ԭ��</td>
					<td class="tdulright">
						<textarea id="cutCause" name="cutCause" rows="3" id="cutCause required" class="textarea"><c:out value="${cut.cutCause}"/></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<c:if test="${empty approveInfoId}">
					<tr class="trcolor">
						<apptag:approverselect label="�����" inputName="approveId,mobile"
								spanId="approverSpan" inputType="radio" notAllowName="reader"/>
					</tr>	
				</c:if>
				<c:if test="${not empty approveInfoId}">
					<tr class="trcolor">
						<apptag:approverselect label="�����" inputName="approveId,mobile"
								spanId="approverSpan" inputType="radio" existValue="${approveInfoId}--${approveInfoName}--${approveInfoPhone}" notAllowName="reader"/>
					</tr>	
				</c:if>
				<c:if test="${empty readerInfoId}">
					<tr class="trcolor">
						<apptag:approverselect label="������" inputName="reader,readerPhones"
								spanId="readerSpan" notAllowName="approveId" />
					</tr>
				</c:if>
				<c:if test="${not empty readerInfoId}">
					<tr class="trcolor">
						<apptag:approverselect label="������" inputName="reader,readerPhones"
								spanId="readerSpan" notAllowName="approveId" existValue="${readerInfoId }--${readerInfoName }--${readerInfoPhone }" />
					</tr>
				</c:if>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">������븽����</td>
					<td class="tdulright">
						<apptag:upload entityId="${cut.id}" entityType="LP_CUT" state="edit"/>
					</td>
				</tr>
				<tr class="trcolor">
					<td colspan="2">
						<font color="red">�������Ϊ���������������������д���ޡ�</font>
					</td>
				</tr>
			</table>
			<table style="width:90%">
			 	<tr class="trcolor">
					<td colspan="2" valign="middle" class="tdc">
						<input type="hidden" name="applyState" id="applyState" value="0"/>
						<input type="button" onclick="checkForm('0')" class="button" value="�ύ">&nbsp;&nbsp;
						<input type="button" onclick="checkForm('1')" class="button" value="�ݴ�">&nbsp;&nbsp;
						<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
