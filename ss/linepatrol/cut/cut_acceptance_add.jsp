<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linepatrol.cut.module.*" %>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<html>
	<head>
		<title>�������</title>
		<script type="text/javascript">
			function checkForm(){
				if(document.getElementById("actualValue").value==""){
					alert("ʵ��ʹ�ý���Ϊ�գ�");
					document.getElementById("actualValue").focus();
					return;
				}
				if(valiD("actualValue")==false){
					alert("ʵ��ʹ�ý�����Ϊ���֣�");
					return;
				}
				if(document.getElementById("approveId").value==""){
					alert("����ѡ������ˣ�");
					return;
				}
				addAcceptanceForm.submit();
			}
			//�ж��Ƿ�Ϊ����
			function valiD(id){
				var mysplit = /^\d{1,20}[\.]{0,1}\d{0,2}$/;
				var obj = document.getElementById(id);
				if(mysplit.test(obj.value)){
					//return true;
				}
				else{
					//alert("����д�����ֲ��Ϸ�,����������");
					obj.focus();
					//obj.value = "0.00";
					return false;
				}
			}
			
			function deleteProblem(){    
	      		//if(confirm("ȷ��Ҫɾ����?")){
      			//spplanId=idValue;
       			var params = "";
  				var url = "${ctx}/cutAcceptanceAction.do?method=judgeFiles"
  				var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callback,asynchronous:false}); 
	      		//}
	     	}
	     	function callback(originalRequest) {
				var rst = originalRequest.responseText;
				if(rst!="file"){
					if(confirm("��δ���ά��ͼֽ���Ƿ�ȷ���ύ���㣿")){
						addAcceptanceForm.submit();
					}
					return;
				}else{
					addAcceptanceForm.submit();
				}
			}
			//toUpdateData=function(id){
            	//window.location.href = "${ctx}/trunkAction.do?method=updateTrunk&id="+id+"&type=cable";
			//}
			
			var win;
			function toUpdateData(id){
				var url = "${ctx}/trunkAction.do?method=updateTrunk&id="+id+"&type=cable";
				win = new Ext.Window({
					layout : 'fit',
					width:500,
					height:300, 
					resizable:true,
					closeAction : 'close',
					modal:true,
					html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
					plain: true
				});
				win.show(Ext.getBody());
			}
		</script>
	</head>
	<body>
		<template:titile value="������ս���"/>
		<html:form action="/cutAcceptanceAction.do?method=addCutAcceptance" styleId="addAcceptanceForm" enctype="multipart/form-data">
			<jsp:include page="cut_apply_base.jsp"/>
			<jsp:include page="cut_feedback_base.jsp"/>
			<table cellspacing="0" cellpadding="1" align="center" style="width:90%;border-top:0px;" class="tabout">
				<tr style="display:none">
					<td>
						<input type="hidden" name="cutId" value="<c:out value='${cut.id }'/>" />
					</td>
				</tr>		
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">ʵ��ʹ�ý�</td>
					<td class="tdulright" colspan="3"><input type="text" name="actualValue" id="actualValue" class="inputtext" /> Ԫ<font color="red">*</font></td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">���ϸ��£�</td>
					<td class="tdulright" colspan="3">
						<c:if test="${not empty subline}">
							<table>
								<c:forEach items="${subline}" var="sub">
									<tr>
										<td>
											<c:out value="${sub[1]}"></c:out>&nbsp;&nbsp;
										</td>
										<td>
											<a onclick="toUpdateData('${sub[0] }')" style="cursor: pointer;color: blue;">��������</a>
										</td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${empty subline}">
							�����ϸ���
						</c:if>
					</td>
				</tr>
				<!-- <tr class="trcolor">
					<td class="tdulleft" style="width:20%;">ά��ͼֽ��</td>
					<td colspan="3" class="tdulright">
						<apptag:upload state="add"/>
					</td>
				</tr> -->
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
					<td colspan="4">
						<font color="red">�������Ϊ���������������������д���ޡ�</font>
					</td>
				</tr>
			</table>
			<table style="width:90%;">
				<tr class="trcolor">
					<td colspan="4" align="center">
						<html:button property="action" styleClass="button" onclick="checkForm()">�ύ</html:button> &nbsp;&nbsp;
						<html:reset property="action" styleClass="button">��д</html:reset>&nbsp;&nbsp;
						<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
					</td>
				</tr>
			</table>	
		</html:form>
	</body>
</html>
