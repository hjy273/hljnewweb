
<div id="dialog" class="popup">
	<div class="popup-header">
		<h2>设置盯防计划结束时间</h2>
		<a href="javascript:;" onclick="jQuery.closePopupLayer('addCTask')" title="Close" class="close-link">X</a>
		<br/>
	</div>
	<div class="popup-body">
		<form id="editform" action="${ctx}/specialplan.do?method=setWatchEndDate" method="post">
			<table width="80%" align="center" border="1" class="tabout" cellpadding="0" cellspacing="1">
				<tr>
					<td class="tdulleft" style="width:30%">计划名称:</td>
					<td class="tdulright" style="width:70%">
						<input type="text" name="plnaName" value="${cableLevelName}"/>
						<input type="hidden" name="planId" value="${cableLevel}"/>
					</td>
				</tr>
				<tr>
					<td class="tdulleft">计划结束日期:</td>
					<td class="tdulright">
						${startDate}--
						<input type="text" name="endDate" readonly class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss'})" />
					</td>
				</tr>
			</table>
			<input type="button" name='submit' onclick="close()" value="确定"/>
		</form>
	</div>
</div>