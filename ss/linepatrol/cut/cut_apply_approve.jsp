<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linepatrol.cut.module.*" %>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>割接申请审核</title>
		<script type="text/javascript">
			function checkForm(){
				if(getTrimValue("operator")=="approve"){
					if(submitForm1.approveResult[0].checked){
						if(getTrimValue("replyBeginTime").length==0){
							alert("批复开始时间不能为空！");
							return false;
						}
						if(getTrimValue("replyEndTime").length==0){
							alert("批复结束时间不能为空！");
							return false;
						}
						if(getTrimValue("deadline").length==0){
							alert("反馈提交时限时间不能为空！");
							return false;
						}
					}
					return true;
				}
				if(getTrimValue("operator")=="transfer"){
					if(getTrimValue("approvers").length==0){
						alert("转审人不能为空！");
						return false;
					}
					return true;
				}
				return false;
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
			//手机号码验证 
			String.prototype.isMobile = function() {  
				return (/^(?:13\d|15[289])-?\d{5}(\d{3}|\*{3})$/.test(this.trim()));  
			}
		</script>
	</head>
	<body>
		<template:titile value="割接申请审核"/>
		<html:form action="/cutAction.do?method=approveCutApply" onsubmit="return checkForm()" styleId="submitForm1" enctype="multipart/form-data">
			<table cellspacing="0" cellpadding="1" align="center" style="width:90%;border-top:0px;" class="tabout">
				<tr style="display:none">
					<td>
						<fmt:formatDate  value="${cut.beginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatBeginTime"/>
						<fmt:formatDate  value="${cut.endTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatEndTime"/>
						<fmt:formatDate  value="${cut.replyBeginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatReplyBeginTime"/>
						<fmt:formatDate  value="${cut.replyEndTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatReplyEndTime"/>
						<fmt:formatDate  value="${cut.applyDate}" pattern="yyyy/MM/dd HH:mm:ss" var="formatApplyDate"/>
						<input type="hidden" name="id" value="<c:out value='${cut.id }'/>" />
						<input type="hidden" value="${operator }" id="operator" name="operator"/>
					</td>
				</tr>
					<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">工单编号：</td>
					<td class="tdulright" style="width:30%;"><c:out value="${cut.workOrderId}"/></td>
					<td class="tdulleft" style="width:20%;">割接名称：</td>
					<td class="tdulright" style="width:30%;"><c:out value="${cut.cutName}"/></td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">割接级别： </td>
					<td class="tdulright" style="width:30%;">
						<c:if test="${cut.cutLevel=='1'}">一般割接</c:if>
						<c:if test="${cut.cutLevel=='2'}">紧急割接</c:if>
						<c:if test="${cut.cutLevel=='3'}">预割接</c:if>
					</td>
					<td class="tdulleft" style="width:20%;">现场负责人：</td>
					<td class="tdulright" style="width:30%;"><c:out value="${cut.liveChargeman}"/></td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">线维区域负责人：</td>
					<td class="tdulright" style="width:30%;"><c:out value="${cut.chargeMan}"/></td>
					<td class="tdulleft" style="width:20%;">预算金额：</td>
					<fmt:formatNumber value="${cut.budget}" pattern="#.##" var="budget"/>
					<td class="tdulright" style="width:30%;" colspan="3"><c:out value="${budget}"/> 元</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">是否有赔补：</td>
					<c:if test="${cut.isCompensation=='1'}">
						<td class="tdulright" style="width:30%;">是</td>
						<td class="tdulleft" style="width:20%;">赔补单位：</td>
						<td class="tdulright" style="width:30%;"><c:out value="${cut.compCompany}"/></td>
					</c:if>
					<c:if test="${cut.isCompensation=='0'}">
						<td colspan="3" class="tdulright" style="width:30%;">否</td>
					</c:if>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">割接地点：</td>
					<td colspan="3" class="tdulright">
						<c:out value="${cut.cutPlace}"/>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">割接申请时间：</td>
					<td class="tdulright" colspan="3"><c:out value="${formatBeginTime}"/> - <c:out value="${formatEndTime}"/></td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">中继段名称：</td>
					<td colspan="3" class="tdulright">
						<c:if test="${empty sublineIds}">
							无中继段
						</c:if>
						<c:if test="${not empty sublineIds}">
							<apptag:trunk id="trunk" state="view" value="${sublineIds}"/>
						</c:if>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">未交维中继段：</td>
					<td colspan="3" class="tdulright">
						<c:out value="${cut.otherImpressCable}"/>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">使用材料描述：</td>
					<td colspan="3" class="tdulright">
						<c:out value="${cut.useMateral}"/>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">割接原因：</td>
					<td colspan="3" class="tdulright">
						<c:out value="${cut.cutCause}"/>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">割接申请附件：</td>
					<td colspan="3" class="tdulright">
					<apptag:upload entityId="${cut.id}" entityType="LP_CUT" state="look"/>
					</td>
				</tr>
				<tr class="trcolor">
					<td colspan="4">
						<logic:notEmpty name="approve_info_list">
							<table border="1" cellpadding="0" cellspacing="0" width="100%"
								style="border-collapse: collapse;">
								<tr>
									<td style="text-align:center;">审核次数</td>
									<td style="text-align:center;">审核人</td>
									<td style="text-align:center;">审核时间</td>
									<td style="text-align:center;">审核结果</td>
									<td style="text-align:center;">审核意见</td>
									<td style="text-align:center;">附件</td>
								</tr>
								<logic:iterate id="oneApproveInfo" name="approve_info_list" indexId="index">
									<tr>
										<td style="text-align:center;">
											<c:set var="count" value="${index+1}"></c:set>
											第${count}次审核
										</td>
										<td style="text-align:center;">
											<bean:write name="oneApproveInfo" property="username" />
										</td>
										<td style="text-align:center;">
											<bean:write name="oneApproveInfo" property="approve_time" />
										</td>
										<td style="text-align:center;">
											<bean:define id="approvedis" name="oneApproveInfo" property="approve_result_dis"></bean:define>
											<logic:equal value="审核不通过" name="approvedis">
												<font color="red">
													<bean:write name="oneApproveInfo" property="approve_result_dis" />
												</font>
											</logic:equal>
											<logic:equal value="审核通过" name="approvedis">
												<font color="blue">
													<bean:write name="oneApproveInfo" property="approve_result_dis" />
												</font>
											</logic:equal>
											<logic:equal value="转审" name="approvedis">
												<font color="black">
													<bean:write name="oneApproveInfo" property="approve_result_dis" />
												</font>
											</logic:equal>
										</td>
										<td style="text-align:center;">
											<bean:write name="oneApproveInfo" property="approve_remark" />
										</td>
										<td style="text-align:center;">
											<bean:define id="approveid" name="oneApproveInfo" property="id"></bean:define>
											<apptag:upload entityId="${approveid}" entityType="LP_APPROVE_INFO" state="look"/>
										</td>
									</tr>
								</logic:iterate>
							</table>
						</logic:notEmpty>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">割接申请时间：</td>
					<td class="tdulright" colspan="3"><c:out value="${formatBeginTime}"/> - <c:out value="${formatEndTime}"/></td>
				</tr>
				<c:if test="${operator=='approve'}">
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">批复割接时间：</td>
						<td colspan="3" class="tdulright">
							<c:if test="${empty cut.replyBeginTime}">
								<input name="replyBeginTime" class="Wdate" id="replyBeginTime" style="width:150px;"
									onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'%y-%M-%d'})" readonly/>
								—
								<input name="replyEndTime" class="Wdate" id="replyEndTime" style="width:150px;"
									onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:$('replyBeginTime')})" readonly/>
							</c:if>
							<c:if test="${not empty cut.replyBeginTime}">
								<input name="replyBeginTime" class="Wdate" id="replyBeginTime" style="width:150px;"
									onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'%y-%M-%d'})" readonly value="${formatReplyBeginTime }"/>
								—
								<input name="replyEndTime" class="Wdate" id="replyEndTime" style="width:150px;"
									onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:$('replyBeginTime')})" readonly value="${formatReplyEndTime }"/>
							</c:if>
						</td>
					</tr>
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">反馈提交时限：</td>
						<td colspan="3" class="tdulright">
							<input name="deadline" class="Wdate" id="deadline" style="width:150px;"
									onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:$('replyBeginTime')})" readonly value="${deadline }"/>
						</td>
					</tr>
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">审核附件：</td>
						<td class="tdulright" colspan="3">
							<apptag:upload state="edit" entityId="${cut.id}" entityType="LP_APPROVE_INFO"/>
						</td>
					</tr>
					<tr class="trcolor">
						<apptag:approve labelClass="tdulleft" valueClass="tdulright" areaClass="textarea max-length-1024" />
					</tr>
					<tr class="trcolor">
						<td colspan="4" align="center" class="tdc">
							<html:submit property="action" styleClass="button">提交</html:submit>&nbsp;&nbsp;
							<html:reset property="action" styleClass="button">重写</html:reset>&nbsp;&nbsp;
							<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
						</td>
					</tr>
				</c:if>
				<c:if test="${operator=='transfer'}">
					<tr class="trcolor">
						<apptag:approverselect label="转审人" colSpan="4" inputType="radio" inputName="approvers,mobile" spanId="approverSpan" />
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
						<td colspan="4" align="center" class="tdc">
							<html:submit property="action" styleClass="button">提交</html:submit>&nbsp;&nbsp;
							<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
						</td>
					</tr>
				</c:if>
			</table>
		</html:form>
	</body>
</html>
