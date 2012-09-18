<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>��������������</title>
	</head>
	<body>
		<table cellspacing="0" cellpadding="1" align="center" style="width:90%;" class="tabout">
			<tr style="display:none">
				<td>
					<fmt:formatDate  value="${cut.beginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatBeginTime"/>
					<fmt:formatDate  value="${cut.endTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatEndTime"/>
					<fmt:formatDate  value="${cut.replyBeginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatReplyBeginTime"/>
					<fmt:formatDate  value="${cut.replyEndTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatReplyEndTime"/>
					<fmt:formatDate  value="${cut.applyDate}" pattern="yyyy/MM/dd HH:mm:ss" var="formatApplyDate"/>
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
				<c:if test="${not empty cut.replyBeginTime}">
					<td class="tdulleft" style="width:20%;">����ʱ�䣺</td>
					<td class="tdulright" colspan="3"><c:out value="${formatReplyBeginTime}"/> - <c:out value="${formatReplyEndTime}"/></td>
				</c:if>
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
			<logic:notEmpty property="cancelUserId" name="cut">
				<tr class=trcolor>
					<td class="tdr">
						ȡ���ˣ�
					</td>
					<td class="tdl">
						<bean:write property="cancelUserName" name="cut" />
					</td>
					<td class="tdr">
						ȡ��ʱ�䣺
					</td>
					<td class="tdl">
						<bean:write property="cancelTimeDis" name="cut" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						ȡ��ԭ��
					</td>
					<td class="tdl" colspan="3">
						<bean:write property="cancelReason" name="cut" />
					</td>
				</tr>
			</logic:notEmpty>
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
			<c:if test="${not empty cut.deadline}">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�����ύʱ�ޣ�</td>
					<td colspan="3" class="tdulright">
						<bean:write name="cut" property="deadline" format="yyyy/MM/dd HH:mm:ss"/>
					</td>
				</tr>
			</c:if>
		</table>
	</body>
</html>
