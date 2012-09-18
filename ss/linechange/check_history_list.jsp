<%@include file="/common/header.jsp"%>
<logic:notEmpty name="check_history_list">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td colspan="5" style="text-align:left;">
				<strong>施工验收历史</strong>
			</td>
		</tr>
		<tr>
			<td>
				<strong>验收负责人</strong>
			</td>
			<td>
				<strong>验收日期</strong>
			</td>
			<td>
				<strong>验收结果</strong>
			</td>
			<td>
				<strong>验收备注</strong>
			</td>
			<td>
				<strong>验收附件</strong>
			</td>
		</tr>
		<logic:iterate id="one_auditing" name="check_history_list">
			<tr>
				<td>
					<bean:write name="one_auditing" property="checkperson" />
				</td>
				<td>
					<bean:write name="one_auditing" property="check_date" />
				</td>
				<td>
					<bean:write name="one_auditing" property="checkresult" />
				</td>
				<td>
					<bean:write name="one_auditing" property="checkremark" />
				</td>
				<td>
					<logic:empty name="one_auditing" property="checkdatum">
						<apptag:listAttachmentLink fileIdList="" />
					</logic:empty>
					<logic:notEmpty name="one_auditing" property="checkdatum">
						<bean:define id="temp" name="one_auditing" property="checkdatum"
							type="java.lang.String" />
						<apptag:listAttachmentLink fileIdList="<%=temp%>" />
					</logic:notEmpty>
				</td>
			</tr>
		</logic:iterate>
	</table>
</logic:notEmpty>

