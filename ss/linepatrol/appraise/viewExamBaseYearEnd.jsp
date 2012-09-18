<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<br>
<script type="text/javascript">

</script>
<body background="#FFFFFF">
<table border="1" align="center" width="100%" class="tabout">
	<tr class="trcolor">
		<td colspan="3" class="tdulright">${baseInfo['item'] }</td>
	</tr>
	<tr class="trcolor">
		<td colspan="3" class="tdulright">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${baseInfo['content'] }</td>
	</tr>
	<tr class="trcolor">
		<td colspan="5" class="tdulright">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${baseInfo['rule'] }</td>
	</tr><!-- 在查看考核依据窗口中填写得分，评分说明；然后回填到父窗口中
	<tr class="trcolor" height="20px">
		<td colspan="5" class="tdulright"><input type="hidden" id="weight" vlaue="${baseInfo['weight'] }" name="weight"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;得分：<input type="text" id="results" name="results" value="${baseInfo['weight']}" size='2' styleClass="inputtext validate-number"/></td>
	</tr>
	<tr class="trcolor" height="20px">
		<td colspan="5" class="tdulright">评分说明：<input type="text" id="remarks" name="remarks" style="width:500px;" value="" ></input></td>
	</tr> -->
	<tr style="font-weight: bold;text-align: center;">
	<td class="tdulright" width="70%">扣分说明</td>
	<td class="tdulright" width="10%">得分</td>
	<td class="tdulright" width="20%">考核人</td>
	
	</tr>
	<c:forEach var="appraiseDailyMarks" items="${baseInfo['appraiseDailyMarks']}">
		<tr class="trcolor">
			<td class="tdulright">&nbsp;&nbsp;&nbsp;&nbsp;${appraiseDailyMarks['remark']}</td>
			<td class="tdulright">&nbsp;&nbsp;&nbsp;&nbsp;${appraiseDailyMarks['markDeductions']}</td>
			<td class="tdulright">&nbsp;&nbsp;&nbsp;&nbsp;${appraiseDailyMarks['appraiser']}</td>
		</tr>
	</c:forEach>
</table>
<div align="center">
<input name="btnClose" value="关闭" class="button" type="button" onclick="closeviewExamBaseWin();" />
</div>
</body>
