<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript">
		 function GetSelectDateTHIS(strID) {
	    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
	    	return ;
		 }
	</script>
	<body>
		<br>
		<template:titile value="按条件查询备件申请信息" />
		<html:form action="/SparepartApplyAction.do?method=doQueryForApply"
			styleId="queryForm">
			<template:formTable namewidth="200" contentwidth="200">
				<html:hidden property="reset_query" value="1" />

				<template:formTr name="备件序列号">
					<input name="serial_number" class="inputtext" type="text"
						style="width: 250;" maxlength="25" />
				</template:formTr>
				
				<template:formTr name="审核结果:">
					<select name="auditing_result" class="inputtext"
						style="width: 250;">
						<option value="">
							全部
						</option>
						<option value="03">
							待审核
						</option>
						<option value="01">
							审核通过
						</option>
						<option value="02">
							审核不通过
						</option>
					</select>
				</template:formTr>

				<template:formTr name="申请使用位置">
					<input name="storage_position" class="inputtext" type="text"
						style="width: 250;" maxlength="25" />
				</template:formTr>

				<template:formTr name="申请开始时间">
					<input id="begin" type="text" readonly="readonly" name="begintime"
						class="inputtext" style="width: 220" />
					<INPUT TYPE='BUTTON' VALUE='日期' readonly="readonly" ID='btn'
						onclick="GetSelectDateTHIS('begin')"
						STYLE="font: 'normal small-caps 6pt serif';">
				</template:formTr>
				<template:formTr name="申请结束时间">
					<input id="end" type="text" name="endtime" readonly="readonly"
						class="inputtext" style="width: 220" />
					<INPUT TYPE='BUTTON' VALUE='日期' ID='btn'
						onclick="GetSelectDateTHIS('end')"
						STYLE="font: 'normal small-caps 6pt serif';">
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit property="action" styleClass="button">查询</html:submit>
					</td>
					<td>
						<html:reset property="action" styleClass="button">取消</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
