<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linepatrol.cut.module.*" %>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>����������</title>
		<script type="text/javascript">
			function checkForm(){
				if(getTrimValue("operator")=="approve"){
					if(submitForm1.approveResult[0].checked){
						if(getTrimValue("replyBeginTime").length==0){
							alert("������ʼʱ�䲻��Ϊ�գ�");
							return false;
						}
						if(getTrimValue("replyEndTime").length==0){
							alert("��������ʱ�䲻��Ϊ�գ�");
							return false;
						}
						if(getTrimValue("deadline").length==0){
							alert("�����ύʱ��ʱ�䲻��Ϊ�գ�");
							return false;
						}
					}
					return true;
				}
				if(getTrimValue("operator")=="transfer"){
					if(getTrimValue("approvers").length==0){
						alert("ת���˲���Ϊ�գ�");
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
			//�ֻ�������֤ 
			String.prototype.isMobile = function() {  
				return (/^(?:13\d|15[289])-?\d{5}(\d{3}|\*{3})$/.test(this.trim()));  
			}
		</script>
	</head>
	<body>
		<template:titile value="����������"/>
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
					<td class="tdulleft" style="width:20%;">������ţ�</td>
					<td class="tdulright" style="width:30%;"><c:out value="${cut.workOrderId}"/></td>
					<td class="tdulleft" style="width:20%;">������ƣ�</td>
					<td class="tdulright" style="width:30%;"><c:out value="${cut.cutName}"/></td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">��Ӽ��� </td>
					<td class="tdulright" style="width:30%;">
						<c:if test="${cut.cutLevel=='1'}">һ����</c:if>
						<c:if test="${cut.cutLevel=='2'}">�������</c:if>
						<c:if test="${cut.cutLevel=='3'}">Ԥ���</c:if>
					</td>
					<td class="tdulleft" style="width:20%;">�ֳ������ˣ�</td>
					<td class="tdulright" style="width:30%;"><c:out value="${cut.liveChargeman}"/></td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">��ά�������ˣ�</td>
					<td class="tdulright" style="width:30%;"><c:out value="${cut.chargeMan}"/></td>
					<td class="tdulleft" style="width:20%;">Ԥ���</td>
					<fmt:formatNumber value="${cut.budget}" pattern="#.##" var="budget"/>
					<td class="tdulright" style="width:30%;" colspan="3"><c:out value="${budget}"/> Ԫ</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�Ƿ����ⲹ��</td>
					<c:if test="${cut.isCompensation=='1'}">
						<td class="tdulright" style="width:30%;">��</td>
						<td class="tdulleft" style="width:20%;">�ⲹ��λ��</td>
						<td class="tdulright" style="width:30%;"><c:out value="${cut.compCompany}"/></td>
					</c:if>
					<c:if test="${cut.isCompensation=='0'}">
						<td colspan="3" class="tdulright" style="width:30%;">��</td>
					</c:if>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">��ӵص㣺</td>
					<td colspan="3" class="tdulright">
						<c:out value="${cut.cutPlace}"/>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�������ʱ�䣺</td>
					<td class="tdulright" colspan="3"><c:out value="${formatBeginTime}"/> - <c:out value="${formatEndTime}"/></td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�м̶����ƣ�</td>
					<td colspan="3" class="tdulright">
						<c:if test="${empty sublineIds}">
							���м̶�
						</c:if>
						<c:if test="${not empty sublineIds}">
							<apptag:trunk id="trunk" state="view" value="${sublineIds}"/>
						</c:if>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">δ��ά�м̶Σ�</td>
					<td colspan="3" class="tdulright">
						<c:out value="${cut.otherImpressCable}"/>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">ʹ�ò���������</td>
					<td colspan="3" class="tdulright">
						<c:out value="${cut.useMateral}"/>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">���ԭ��</td>
					<td colspan="3" class="tdulright">
						<c:out value="${cut.cutCause}"/>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">������븽����</td>
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
									<td style="text-align:center;">��˴���</td>
									<td style="text-align:center;">�����</td>
									<td style="text-align:center;">���ʱ��</td>
									<td style="text-align:center;">��˽��</td>
									<td style="text-align:center;">������</td>
									<td style="text-align:center;">����</td>
								</tr>
								<logic:iterate id="oneApproveInfo" name="approve_info_list" indexId="index">
									<tr>
										<td style="text-align:center;">
											<c:set var="count" value="${index+1}"></c:set>
											��${count}�����
										</td>
										<td style="text-align:center;">
											<bean:write name="oneApproveInfo" property="username" />
										</td>
										<td style="text-align:center;">
											<bean:write name="oneApproveInfo" property="approve_time" />
										</td>
										<td style="text-align:center;">
											<bean:define id="approvedis" name="oneApproveInfo" property="approve_result_dis"></bean:define>
											<logic:equal value="��˲�ͨ��" name="approvedis">
												<font color="red">
													<bean:write name="oneApproveInfo" property="approve_result_dis" />
												</font>
											</logic:equal>
											<logic:equal value="���ͨ��" name="approvedis">
												<font color="blue">
													<bean:write name="oneApproveInfo" property="approve_result_dis" />
												</font>
											</logic:equal>
											<logic:equal value="ת��" name="approvedis">
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
					<td class="tdulleft" style="width:20%;">�������ʱ�䣺</td>
					<td class="tdulright" colspan="3"><c:out value="${formatBeginTime}"/> - <c:out value="${formatEndTime}"/></td>
				</tr>
				<c:if test="${operator=='approve'}">
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">�������ʱ�䣺</td>
						<td colspan="3" class="tdulright">
							<c:if test="${empty cut.replyBeginTime}">
								<input name="replyBeginTime" class="Wdate" id="replyBeginTime" style="width:150px;"
									onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'%y-%M-%d'})" readonly/>
								��
								<input name="replyEndTime" class="Wdate" id="replyEndTime" style="width:150px;"
									onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:$('replyBeginTime')})" readonly/>
							</c:if>
							<c:if test="${not empty cut.replyBeginTime}">
								<input name="replyBeginTime" class="Wdate" id="replyBeginTime" style="width:150px;"
									onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'%y-%M-%d'})" readonly value="${formatReplyBeginTime }"/>
								��
								<input name="replyEndTime" class="Wdate" id="replyEndTime" style="width:150px;"
									onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:$('replyBeginTime')})" readonly value="${formatReplyEndTime }"/>
							</c:if>
						</td>
					</tr>
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">�����ύʱ�ޣ�</td>
						<td colspan="3" class="tdulright">
							<input name="deadline" class="Wdate" id="deadline" style="width:150px;"
									onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:$('replyBeginTime')})" readonly value="${deadline }"/>
						</td>
					</tr>
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">��˸�����</td>
						<td class="tdulright" colspan="3">
							<apptag:upload state="edit" entityId="${cut.id}" entityType="LP_APPROVE_INFO"/>
						</td>
					</tr>
					<tr class="trcolor">
						<apptag:approve labelClass="tdulleft" valueClass="tdulright" areaClass="textarea max-length-1024" />
					</tr>
					<tr class="trcolor">
						<td colspan="4" align="center" class="tdc">
							<html:submit property="action" styleClass="button">�ύ</html:submit>&nbsp;&nbsp;
							<html:reset property="action" styleClass="button">��д</html:reset>&nbsp;&nbsp;
							<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
						</td>
					</tr>
				</c:if>
				<c:if test="${operator=='transfer'}">
					<tr class="trcolor">
						<apptag:approverselect label="ת����" colSpan="4" inputType="radio" inputName="approvers,mobile" spanId="approverSpan" />
					</tr>
					<tr class="trcolor">
						<td height="25" style="text-align: right;" class="tdulleft" style="width:20%;">
							ת��˵����<input type="hidden" name="approveResult" value="2" />
						</td>
						<td colspan="3" style="text-align: left;" class="tdulright">
							<textarea name="approveRemark" rows="6" style="width: 500px;"></textarea>
						</td>
					</tr>
					<tr class="trcolor">
						<td colspan="4" align="center" class="tdc">
							<html:submit property="action" styleClass="button">�ύ</html:submit>&nbsp;&nbsp;
							<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
						</td>
					</tr>
				</c:if>
			</table>
		</html:form>
	</body>
</html>
