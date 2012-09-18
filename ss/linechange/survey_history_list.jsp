<%@include file="/common/header.jsp"%>
<logic:notEmpty name="survey_history_list">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td colspan="5" style="text-align:left;">
				<strong>勘查审定历史</strong>
			</td>
		</tr>
		<tr>
			<td>
				<strong>勘查审定人</strong>
			</td>
			<td>
				<strong>勘查审定日期</strong>
			</td>
			<td>
				<strong>勘查审定结果</strong>
			</td>
			<td>
				<strong>勘查审定备注</strong>
			</td>
			<td>
				<strong>勘查审定附件</strong>
			</td>
		</tr>
		<logic:iterate id="one_auditing" name="survey_history_list">
			<tr>
				<td>
					<bean:write name="one_auditing" property="approver" />
				</td>
				<td>
					<bean:write name="one_auditing" property="approvedate" />
				</td>
				<td>
					<bean:write name="one_auditing" property="approveresult" />
				</td>
				<td>
					<bean:write name="one_auditing" property="approveremark" />
				</td>
				<td>
					<logic:empty name="one_auditing" property="surveydatum">
						<apptag:listAttachmentLink fileIdList="" />
					</logic:empty>
					<logic:notEmpty name="one_auditing" property="surveydatum">
						<bean:define id="temp" name="one_auditing" property="surveydatum"
							type="java.lang.String" />
						<apptag:listAttachmentLink fileIdList="<%=temp%>" />
					</logic:notEmpty>
				</td>
			</tr>
		</logic:iterate>
	</table>
</logic:notEmpty>

