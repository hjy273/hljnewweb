<%@include file="/common/header.jsp"%>
<logic:notEmpty name="auditing_list">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td>
				<strong>�����</strong>
			</td>
			<td>
				<strong>�������</strong>
			</td>
			<td>
				<strong>��˽��</strong>
			</td>
			<td>
				<strong>������</strong>
			</td>
		</tr>
		<logic:iterate id="one_auditing" name="auditing_list">
			<tr>
				<td>
					<bean:write name="one_auditing" property="auditing_person" />
				</td>
				<td>
					<bean:write name="one_auditing" property="auditing_date" />
				</td>
				<td>
					<logic:equal value="01" name="one_auditing" property="auditing_result">
						���ͨ��
					</logic:equal>
					<logic:equal value="02" name="one_auditing" property="auditing_result">
						��˲�ͨ��
					</logic:equal>
				</td>
				<td>
					<bean:write name="one_auditing" property="auditing_remark" />
				</td>
			</tr>
		</logic:iterate>
	</table>
</logic:notEmpty>
