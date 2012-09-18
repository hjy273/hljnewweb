<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linepatrol.cut.module.*" %>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<html>
	<head>
		<title>验收结算</title>
		<script type="text/javascript">
			function checkForm(){
				if(document.getElementById("actualValue").value.length==0){
					alert("实际使用金额不能为空！");
					document.getElementById("actualValue").focus();
					return false;
				}
				if(valiD("actualValue")==false){
					alert("实际使用金额必须为数字！");
					return false;
				}
				if(document.getElementById("approveId").value.length==0){
					alert("必须选择审核人！");
					return false;
				}
				return true;
				//var oldfile = document.getElementById("oldfile").innerHTML;
				//var newfile = document.getElementById("newfile").innerHTML;
				//if(oldfile=="" && newfile==""){
				//	if(confirm("您未添加维护图纸，是否确定提交结算？")){
					//	return true;
				//	}
				//	return false;
				//}
				//deleteProblem();
			}
			//判断是否为数字
			function valiD(id){
				var mysplit = /^\d{1,20}[\.]{0,1}\d{0,2}$/;
				var obj = document.getElementById(id);
				if(mysplit.test(obj.value)){
					//return true;
				}
				else{
					//alert("你填写的数字不合法,请重新输入");
					obj.focus();
					//obj.value = "0.00";
					return false;
				}
			}
			/*function deleteProblem(){    
       			var params = "";
  				var url = "${ctx}/cutAcceptanceAction.do?method=judgeFiles"
  				var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callback,asynchronous:false}); 
	     	}
	     	function callback(originalRequest) {
				var rst = originalRequest.responseText;
				if(document.getElementById("oldfile").innerHTML==null){
					if(rst!="file"){
						alert("必须添加维护图纸！");
						return;
					}
				}else{
					editAcceptanceForm.submit();
				}
			}*/
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
		<template:titile value="编辑验收结算"/>
		<html:form action="/cutAcceptanceAction.do?method=editCutAcceptance" styleId="editAcceptanceForm" onsubmit="return checkForm()" enctype="multipart/form-data">
			<jsp:include page="cut_apply_base.jsp"/>
			<jsp:include page="cut_feedback_base.jsp"/>
			<table cellspacing="0" cellpadding="1" align="center" style="width:90%;border-top:0px;" class="tabout">
				<tr style="dipaly:none">
					<td>
						<input type="hidden" name="cutId" value="<c:out value='${cut.id }'/>" />
						<input type="hidden" name="id" value="<c:out value='${cutAcceptance.id }'/>" />
					</td>
				</tr>		
				<tr class="trcolor">
					<fmt:formatNumber value="${cutAcceptance.actualValue}" pattern="#.##" var="actualValue"/>
					<td class="tdulleft" style="width:20%;">实际使用金额：</td>
					<td class="tdulright" colspan="3"><input type="text" name="actualValue" value="<c:out value='${actualValue }'/>" class="inputtext" /> 元<font color="red">*</font></td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">资料更新：</td>
					<td class="tdulright" colspan="3">
						<c:if test="${not empty subline}">
							<table>
								<c:forEach items="${subline}" var="sub">
									<tr>
										<td>
											<c:out value="${sub[1]}"></c:out>&nbsp;&nbsp;
										</td>
										<td>
											<a onclick="toUpdateData('${sub[0] }')" style="cursor: pointer;color: blue;">更新资料</a>
										</td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${empty subline}">
							无资料更新
						</c:if>
					</td>
				</tr>
				<!-- <tr class="trcolor">
					<td class="tdulleft" style="width:20%;">维护图纸：</td>
					<td colspan="3" class="tdulright">
						<apptag:upload cssClass="" entityId="${cutAcceptance.id}" entityType="LP_CUT_ACCEPTANCE" state="edit"/>
					</td>
				</tr> -->
				<tr class="trcolor">
					<apptag:approverselect label="审核人" inputName="approveId,mobiles"
							spanId="approverSpan" inputType="radio" notAllowName="reader" 
							approverType="approver" objectId="${cutAcceptance.id }" objectType="LP_CUT_ACCEPTANCE" />
				</tr>
				<tr class="trcolor">
					<apptag:approverselect label="抄送人" inputName="reader,readerPhones"
							spanId="readerSpan" notAllowName="approveId" 
							approverType="reader" objectId="${cutAcceptance.id }" objectType="LP_CUT_ACCEPTANCE" />
				</tr>
				<tr class="trcolor">
					<td colspan="4" align="center">
						<html:submit property="action" styleClass="button">提交</html:submit> &nbsp;&nbsp;
						<html:reset property="action" styleClass="button">重写</html:reset>&nbsp;&nbsp;
						<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
