<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linepatrol.cut.module.*" %>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<html>
	<head>
		<title>割接验收</title>
		<script type="text/javascript">
			function checkForm(){
				if(document.getElementById("operater").value=="transfer"){
					if(document.getElementById("approvers").value.length==0){
						alert("转审人不能为空！");
						return false;
					}
					return true;
				}else{
					var result=document.getElementsByName("approveResult");
					for(var i=0;i<result.length;i++){
						if(result[i].checked){
							if(result[i].value=="1"){
								if(confirm("资料更新信息是否全部通过审核？")){
									return true;
								}else{
									return false;
								}
							}else{
								return true;
							}
						}
					}
				}
				return false;
			}
			var win;
			function toApproveData(id){
				var url = "${ctx}/trunkAction.do?method=updateTrunkApprove&id="+id+"&type=cable";
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
			function toViewSublineData(id){
				var url = "${ctx}/trunkAction.do?method=updateTrunkView&id="+id+"&type=cable";
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
		<template:titile value="割接验收结算审批"/>
		<html:form action="/cutAcceptanceAction.do?method=cutAcceptanceApprove" onsubmit="return checkForm()">
			<jsp:include page="cut_apply_base.jsp"/>
			<jsp:include page="cut_feedback_base.jsp"/>
			<jsp:include page="cut_acceptance_base.jsp"/>
			<table cellspacing="0" cellpadding="1" align="center" style="width:90%;border-top:0px;" class="tabout">
				<tr style="display:none">
					<td>
						<input type="hidden" name="cutId" value="<c:out value='${cut.id }'/>" />
						<input type="hidden" name="proposer" value="<c:out value='${cut.proposer }'/>" />
						<input type="hidden" name="id" value="<c:out value='${cutAcceptance.id }'/>" />
					</td>
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
										<c:if test="${operater=='approve'}">
											<td>
												<a onclick="toApproveData('${sub[0] }')" style="cursor: pointer;color: blue;">审批</a>
											</td>
										</c:if>
										<c:if test="${operater!='approve'}">
											<td>
												<a onclick="toViewSublineData('${sub[0] }')" style="cursor: pointer;color: blue;">查看</a>
											</td>
										</c:if>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${empty subline}">
							无资料更新
						</c:if>
					</td>
				</tr>
				<input type="hidden" value="${operater }" id="operater" name="operator" />
				<c:if test="${operater=='approve'}">
					<tr class="trcolor">
						<td style="width:20%" class="tdulleft">
							审核结果：
						</td>
						<td colspan="3" class="tdulright">
							<input type="radio" name="approveResult" value="1" checked />
							通过
							<input type="radio" name="approveResult" value="0" />
							不通过
						</td>
					</tr>
					<tr class="trcolor">
						<td style="width:20%" class="tdulleft">
							审核意见：
						</td>
						<td colspan="3" class="tdulright">
							<textarea name="approveRemark" rows="3" class="textarea"></textarea>
						</td>
					</tr>
					<tr class="trcolor">
						<td colspan="4" align="center">
							<html:submit property="action" styleClass="button">提交</html:submit> &nbsp;&nbsp;
							<html:reset property="action" styleClass="button">重写</html:reset>&nbsp;&nbsp;
							<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
						</td>
					</tr>
				</c:if>
				<c:if test="${operater=='transfer'}">
					<tr class="trcolor">
						<apptag:approverselect label="转审人" colSpan="4" inputType="radio" inputName="approvers,mobiles" spanId="approverSpan" 
								approverType="transfer" objectId="${cutFeedback.id }" objectType="LP_CUT_FEEDBACK" />
					</tr>
					<tr class="trcolor">
						<td height="25" style="text-align: right;" class="tdulleft" style="width:20%;">
							转审说明：<input type="hidden" name="approveResult" value="2" />
						</td>								   
						<td colspan="3" style="text-align: left;" class="tdulright">
							<textarea name="approveRemark" rows="6" style="width: 500px;"></textarea>
						</td>
					</tr>
					<tr class="trcolor">
						<td colspan="4" align="center">
							<html:submit property="action" styleClass="button">提交</html:submit>&nbsp;&nbsp;
							<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
						</td>
					</tr>
				</c:if>
			</table>
		</html:form>
	</body>
</html>
