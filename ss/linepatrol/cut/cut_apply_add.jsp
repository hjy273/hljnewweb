<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>��Ӹ������</title>
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
					alert("Ԥ�������Ϊ��Ч������֣�");
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
		</script>
	</head>
	<body>
		<template:titile value="�������"/>
		<html:form action="/cutAction.do?method=addCutApply" styleId="addCutApply" enctype="multipart/form-data">
			<table border="1" align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">������ţ�</td>
					<td class="tdulright">
						<input name="workOrderId" type="hidden" value="<c:out value='${workOrderId }'/>" />
						<b><c:out value="${workOrderId }"/></b>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">������ƣ�</td>
					<td class="tdulright">
						<input name="cutName" type="text" id="cutName" class="inputtext" style="width:150px;"/>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">��Ӽ���</td>
					<td class="tdulright">
						<select name="cutLevel" id="cutLevel" style="width:200" style="width:150px;">
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
					<td class="tdulleft" style="width:20%;">��ά�������ˣ�</td>
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
					<td class="tdulleft" style="width:20%;">Ԥ���</td>
					<td class="tdulright">
						<input name="budget" type="text" id="budget" class="inputtext" style="width:200" style="width:150px;" /> Ԫ
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�Ƿ����ⲹ��</td>
					<td class="tdulright">
						<input name="isCompensation" type="radio" checked="checked" onclick="hiddenPlace()" value="1" id="isCompensationYes" />��
						<input type="radio" name="isCompensation" onclick="hiddenPlace()"  value="0" />��
					</td>
				</tr>
				<tr id="compCompanyContr" class="trcolor">
					<td class="tdulleft" style="width:20%;">�ⲹ��λ��</td>
					<td class="tdulright">
						<input name="compCompany" type="text" id="compCompany" class="inputtext" style="width:150px;" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">��ӵص㣺</td>
					<td class="tdulright">
						<input name="cutPlace" type="text" id="cutPlace" class="inputtext" style="width:150px;" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�������ʱ�䣺</td>
					<td class="tdulright">
						<input name="beginTime" class="Wdate" id="beginTime" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'%y-%M-%d'})" readonly/>
						�D
						<input name="endTime" class="Wdate" id="endTime" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginTime\')}'})" readonly/>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�м̶Σ�</td>
					<td class="tdulright">
						<apptag:trunk id="trunk" state="add" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">δ��ά�м̶Σ�</td>
					<td class="tdulright">
						<textarea name="otherImpressCable" rows="3" id="otherImpressCable" class="textarea"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">ʹ�ò���������</td>
					<td class="tdulright">
						<textarea name="useMateral" rows="3" id="useMateral" class="textarea"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">���ԭ��</td>
					<td class="tdulright">
						<textarea name="cutCause" rows="3" id="cutCause" class="textarea"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<apptag:approverselect label="�����" inputName="approveId,mobile"
							spanId="approverSpan" inputType="radio" colSpan="4" notAllowName="reader" />
				</tr>
				<tr class="trcolor">
					<apptag:approverselect label="������" inputName="reader,readerPhones"
							spanId="readerSpan" notAllowName="approveId" colSpan="4" />
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">������븽����</td>
					<td class="tdulright">
						<apptag:upload state="add"/>
					</td>
				</tr>
			</table>
			<div style="height:20px; margin-left: 5%; width: 95%; margin-top: 5px;">
				<font color="red">�������Ϊ���������������������д���ޡ�</font>
			</div>
			<div align="center" style="height:40px">
				<input type="hidden" name="applyState" id="applyState" value="1"/>
				<input type="button" onclick="checkForm('0')" id="submit1"  class="button" value="�ύ">&nbsp;&nbsp;
				<input type="button" onclick="checkForm('1')" id="submit2" class="button" value="�ݴ�">&nbsp;&nbsp;
				<html:reset property="action" onclick="cancelApprove()" styleClass="button">��д</html:reset>
			</div>
		</html:form>
	</body>
</html>
