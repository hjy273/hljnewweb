<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/linepatrol/css/button.css"
	type="text/css" media="screen, print" />
<html>
	<head>
		<title>sendtask</title>
		<script type="" language="javascript">
		toUrl=function(btn,taskName){
			var url="${ctx}/testPlanAction.do?method=getActWorkForm&&task_name="+taskName;
			parent.location=url;
		}
		</script>
	</head>
	<body>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<input type="button" name="btnCreateTestPlan" class="button_not_hits"
						disabled="disabled" value="�ƶ����Լƻ�" />
				</td>
				<td style="text-align:center">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>

				    <c:if test="${LOGIN_USER.deptype=='1'}">
					 <input type="button" name="btnApproveTestPlan" class="button_not_click"
						style="cursor: hand;" value="��˲��Լƻ�(${wait_approve_plan_num }��)"

					<input type="button" name="btnApproveTestPlan" class="button_not_click"
						style="cursor: hand;" value="����˲��Լƻ�(${wait_approve_plan_num }��)"

						onclick="toUrl(this,'approve_test_plan_task');" />
					</c:if>
					 <c:if test="${LOGIN_USER.deptype=='2'}">
					 <input type="button" name="btnApproveTestPlan" class="button_not_click" disabled="disabled"
						style="cursor: hand;" value="��˲��Լƻ�(${wait_approve_plan_num }��)"
						onclick="toUrl(this,'approve_test_plan_task');" />
					</c:if>
				</td>
				<td style="text-align:center">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>

					   <c:if test="${LOGIN_USER.deptype=='1'}">
						<input type="button" name="btnRecordData" class="button_not_click" disabled="disabled"
							style="cursor: hand;" value="¼���������(${wait_record_num }��)"
							onclick="toUrl(this,'record_test_data_task');" />
						</c:if>
						 <c:if test="${LOGIN_USER.deptype=='2'}">
						<input type="button" name="btnRecordData" class="button_not_click"
							style="cursor: hand;" value="¼���������(${wait_record_num }��)"
							onclick="toUrl(this,'record_test_data_task');" />
						</c:if>
				</td>
				<td style="text-align:center">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>

				      <c:if test="${LOGIN_USER.deptype=='1'}">
					     <input type="button" name="btnApproveRecordData" class="button_not_click"
						style="cursor: hand;" value="��˲�������(${wait_approve_record_num }��)"

					<input type="button" name="btnApproveRecordData" class="button_not_click"
						style="cursor: hand;" value="����˲�������(${wait_approve_record_num }��)"

						onclick="toUrl(this,'approve_test_data_task');" />
					 </c:if>
					  <c:if test="${LOGIN_USER.deptype=='2'}">
					     <input type="button" name="btnApproveRecordData" class="button_not_click" disabled="disabled"
						style="cursor: hand;" value="��˲�������(${wait_approve_record_num }��)"
						onclick="toUrl(this,'approve_test_data_task');" />
					 </c:if>
				</td>
				<td style="text-align:center">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>

				       <c:if test="${LOGIN_USER.deptype=='1'}">
					 	<input type="button" name="btnEvaluate" class="button_not_click"
							style="cursor: hand;" value="��������(${wait_evaluate_num }��)"
							onclick="toUrl(this,'evaluate_task');" />
						</c:if>
					  <c:if test="${LOGIN_USER.deptype=='2'}">
					 	<input type="button" name="btnEvaluate" class="button_not_click"  disabled="disabled"
							style="cursor: hand;" value="��������(${wait_evaluate_num }��)"
							onclick="toUrl(this,'evaluate_task');" />
						</c:if>
				</td>
			</tr>
		</table>
		<logic:notEmpty name="task_name">
			<logic:equal value="approve_test_plan_task" name="task_name">
				<script type="text/javascript">
				btnApproveTestPlan.className="button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="record_test_data_task" name="task_name">
				<script type="text/javascript">
				btnRecordData.className="button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="approve_test_data_task" name="task_name">
				<script type="text/javascript">
				btnApproveRecordData.className="button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="evaluate_task" name="task_name">
				<script type="text/javascript">
				btnEvaluate.className="button_clicked";
				</script>
			</logic:equal>
		</logic:notEmpty>
	</body>
</html>
