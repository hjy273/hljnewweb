<%@include file="/common/header.jsp"%>
<logic:notEmpty name="list">
	<select id="station_id" class="inputtext" name="station_id"
		style="width:250;">
		<option value="">
			����
		</option>
		<logic:iterate id="item" name="list">
			<option value="<bean:write name="item" property="tid" />">
				<bean:write name="item" property="station_name" />
			</option>
		</logic:iterate>
	</select>
</logic:notEmpty>
<logic:empty name="list">
	<select id="station_id" class="inputtext" name="station_id"
		style="width:250;">
		<option value="">
			����
		</option>
	</select>
</logic:empty>
