<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript" type="">
	function view(tid){
		window.location.href = '${ctx}/hiddTroubleAction_view.jspx?flag=view&tid='+tid;
	}
	goBack=function(){
		history.back();
	}
</script>
<logic:equal name="haveTown" value="false">
	<div class='title2'
		style='font-size: 14px; font-weight: 600; bottom: 1' align='center'>
		<FONT color="red"><bean:write name='month' /> </FONT> 各区县修缮迁改费用统计信息
	</div>
	<logic:notEqual value="0.0" name="labelTotal">
		<a
			href="${ctx}/flex/pie/Piechart.html?month=<bean:write name='month'/>">饼状图显示</a>
		<a
			href="${ctx}/flex/colum/ColumnChart.html?month=<bean:write name='month'/>">柱状图显示</a>
	</logic:notEqual>
	<hr width='100%' size='1'>
	<table id="row">
		<thead>
			<tr>
				<th>

				</th>
				<logic:present scope="request" name="townMap">
					<logic:iterate id="town" name="townMap">
						<th>
							<bean:write name="town" property="value" />
						</th>
					</logic:iterate>
				</logic:present>
				<th>
					合计
				</th>
			</tr>
		</thead>
		<tbody>
			<tr class="odd">
				<td>
					各区迁改费用
				</td>
				<logic:present scope="request" name="labelMap">
					<logic:iterate id="label" name="labelMap">
						<td>
							<logic:equal value="0.0" name="labelTotal">
								<bean:write name="label" property="value" />
							</logic:equal>
							<logic:notEqual value="0.0" name="labelTotal">
								<a
									href="statRemedyAction.do?method=statRemedyByTown&townId=<bean:write name='label' property='key'/>&month=<bean:write name='month'/>"
									style="color: bule;"> <bean:write name="label"
										property="value" /></a>元
							</logic:notEqual>
						</td>
					</logic:iterate>
				</logic:present>
				<td>
					<bean:write name="labelTotal" />元
				</td>
			</tr>
			<tr class="even">
				<td>
					占总费用比例
				</td>
				<logic:present scope="request" name="scaleMap">
					<logic:iterate id="scale" name="scaleMap">
						<td>
							<bean:write name="scale" property="value" />
						</td>
					</logic:iterate>
				</logic:present>
				<td>
					100%
				</td>
			</tr>
		</tbody>
	</table>
	<a href="${ctx}/statRemedyAction.do?method=exportStatByTown">导出为Excel文件</a>
</logic:equal>

<logic:equal name="haveTown" value="true">
	<div class='title2'
		style='font-size: 14px; font-weight: 600; bottom: 1' align='center'>
		<bean:write name='town' />
		<FONT color="red"><bean:write name='month' /> </FONT> 各修缮项修缮迁改费用统计信息
	</div>
	<hr width='100%' size='1'>
	<table id="row">
		<thead>
			<tr>
				<th>

				</th>
				<logic:present scope="request" name="itemMap">
					<logic:iterate id="item" name="itemMap">
						<th>
							<bean:write name="item" property="value" />
						</th>
					</logic:iterate>
				</logic:present>
				<th>
					合计
				</th>
			</tr>
		</thead>
		<tbody>
			<tr class="odd">
				<td>
					各区迁改费用
				</td>
				<logic:present scope="request" name="labelMap">
					<logic:iterate id="label" name="labelMap">
						<td>
							<bean:write name="label" property="value" />元
						</td>
					</logic:iterate>
				</logic:present>
				<td>
					<bean:write name="labelTotal" />元
				</td>
			</tr>
			<tr class="even">
				<td>
					占总费用比例
				</td>
				<logic:present scope="request" name="scaleMap">
					<logic:iterate id="scale" name="scaleMap">
						<td>
							<bean:write name="scale" property="value" />
						</td>
					</logic:iterate>
				</logic:present>
				<td>
					100%
				</td>
			</tr>
		</tbody>
	</table>
	<a href="${ctx}/statRemedyAction.do?method=exportStatDetailByTown">导出为Excel文件</a>
</logic:equal>
<p align="center">
	<input name="btnBack" value="返回" onclick="goBack();" type="button"
		class="button" />
</p>







