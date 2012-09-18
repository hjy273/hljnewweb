<%@include file="/common/header.jsp"%>
<c:if test="${sessionScope.data!=null}">
	<c:forEach var="oneData" items="${sessionScope.data}" varStatus="status">
		<tr id="import_rows${status.index}">
			<td align="center">
				<bean:write name="oneData" property="material_type_name" />
				<input name="typename" type="hidden"
					value="<bean:write name="oneData" property="material_type_id" />" />
			</td>
			<td align="center">
				<bean:write name="oneData" property="material_model_name" />
				<input name="modelname" type="hidden"
					value="<bean:write name="oneData" property="material_model_id" />" />
			</td>
			<td align="center">
				<bean:write name="oneData" property="material_name" />
				<input name="materialId" type="hidden"
					value="<bean:write name="oneData" property="material_material_id" />" />
			</td>
			<td align="center">
				<bean:write name="oneData" property="material_unit_name" />
			</td>
			<td align="center">
				<input name="count" type="text" size="6"
					value="<bean:write name="oneData" property="material_apply_number" />" />
			</td>
			<td align="center">
				<bean:write name="oneData" property="material_address_name" />
				<input name="addressId" type="hidden"
					value="<bean:write name="oneData" property="material_address_id" />" />
			</td>
			<td>
				<input type="button" value="É¾³ý" onclick="deleteRow1('import_rows${status.index}');errorTd.innerHTML='';">
			</td>
		</tr>
	</c:forEach>
</c:if>
<c:if test="${sessionScope.error!=null&&sessionScope.error!=''}">
	<tr>
		<td colspan="7" id="errorTd">
			<font color="#FF0000">${sessionScope.error }</font>
		</td>
	</tr>
</c:if>
