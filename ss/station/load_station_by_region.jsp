<%@include file="/common/header.jsp"%>
<logic:notEmpty name="list">
	<logic:iterate id="item" name="list">
		<tr class="trcolor">
			<td align="right" class="tdulleft">
				<input name="station_in_plan"
					value="<bean:write property="tid" name="item" />" type="checkbox" />
			</td>
			<td align="left" class="tdulright">
				<bean:write property="station_name" name="item" />
			</td>
		</tr>
	</logic:iterate>
	<script type="text/javascript">
	var formInput=document.forms[0];
	if(typeof(formInput.station_id)!="undefined"){
		if(typeof(formInput.station_id.length)=="undefined"){
			if(typeof(formInput.station_in_plan.length)=="undefined"){
				if(formInput.station_in_plan.value==formInput.station_id.value){
					formInput.station_in_plan.checked=true;
				}
			}else{
				for(i=0;i<formInput.station_in_plan.length;i++){
					if(formInput.station_in_plan[i].value==formInput.station_id.value){
						formInput.station_in_plan[i].checked=true;
					}
				}
			}
		}else{
			for(j=0;j<formInput.station_id.length;j++){
				if(typeof(formInput.station_in_plan.length)=="undefined"){
					if(formInput.station_in_plan.value==formInput.station_id[j].value){
						formInput.station_in_plan.checked=true;
					}
				}else{
					for(i=0;i<formInput.station_in_plan.length;i++){
						if(formInput.station_in_plan[i].value==formInput.station_id[j].value){
							formInput.station_in_plan[i].checked=true;
						}
					}
				}
			}
		}
	}
	</script>
</logic:notEmpty>
<logic:empty name="list">
	<tr class="trcolor">
		<td align="center" colspan="2" class="tdulright">
			没有找到数据！
		</td>
	</tr>
</logic:empty>
