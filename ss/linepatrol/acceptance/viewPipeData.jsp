<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	
	<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
	
	<script type="text/javascript">	
		var win;
		function showWin(url){
			win = new Ext.Window({
				layout : 'fit',
				width:600,
				height:400,
				resizable:true,
				closeAction : 'close',
				modal:true,
				html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
				plain: true
			});
			win.show(Ext.getBody());
		}
		function close(){
			win.close();
		}
		function view(id){
			url = '${ctx}/acceptanceAction.do?method=viewPipeResult&id='+id;
			showWin(url);
		}
	</script>
</head>
<body>
	<template:titile value="管道验收数据" />
	<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
		<tr class=trcolor>
			<td class="tdulleft">代维名称：</td>
		    <td class="tdulright"><apptag:assorciateAttr table="contractorinfo" label="contractorname" key="contractorid" keyValue="${rpBean.contractorId}" /></td>
			<td class="tdulleft">工程名称：</td>
			<td class="tdulright">${rpBean.workName}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">管道地点：</td>
			<td class="tdulright">${rpBean.pipeAddress}</td>
			<td class="tdulleft">产权属性：</td>
			<td class="tdulright"><apptag:quickLoadList cssClass="input" style="width:150" name="routeRes" keyValue="${rpBean.routeRes}" listName="property_right" type="look"/></td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">详细路由：</td>
			<td class="tdulright">${rpBean.pipeLine}</td>
			<td class="tdulleft">竣工图纸：</td>
			<td class="tdulright">${rpBean.picture}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">管道中心项目经理：</td>
			<td class="tdulright">${rpBean.principle}</td>
			<td class="tdulleft">管道属性：</td>
			<td class="tdulright"><apptag:quickLoadList cssClass="input" style="width:150" name="pipeType" keyValue="${rpBean.pipeType}" listName="pipe_type" type="look"/></td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">交资情况：</td>
			<td class="tdulright">
				<c:choose>
					<c:when test="${rpBean.isMaintenance eq 'y'}">
						已交资
					</c:when>
					<c:otherwise>
						未交资
					</c:otherwise>
				</c:choose>
			</td>
			<td class="tdulleft">备注：</td>
			<td class="tdulright" colspan=3>${rpBean.remark}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">移动长度：</td>
			<td class="tdulright" colspan=5>
				沟（公里）${rpBean.mobileScareChannel}
				&nbsp;&nbsp;&nbsp;&nbsp;孔（公里）${rpBean.mobileScareHole}
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">管道长度：</td>
			<td class="tdulright" colspan=5>
				沟（公里）${rpBean.pipeLengthChannel}
				&nbsp;&nbsp;&nbsp;&nbsp;孔（公里）${rpBean.pipeLengthHole}
			</td>
		</tr>
		<tr>
			<td class="tdulleft">建设单位：</td>
			<td class="tdulright">${rpBean.buildUnit}</td>
			<td class="tdulleft">验收人员：</td>
			<td class="tdulright">${rpBean.buildAcceptance}</td>
		</tr>
		<tr>
			<td class="tdulleft">施工单位：</td>
			<td class="tdulright">${rpBean.workUnit}</td>
			<td class="tdulleft">验收人员：</td>
			<td class="tdulright">${rpBean.workAcceptance}</td>
		</tr>
		<tr>
			<td class="tdulleft">监理单位：</td>
			<td class="tdulright">${rpBean.surveillanceUnit}</td>
			<td class="tdulleft">验收人员：</td>
			<td class="tdulright">${rpBean.surveillanceAccept}</td>
		</tr>
		<tr>
			<td class="tdulleft">维护单位：</td>
			<td class="tdulright">${rpBean.maintenceUnit}</td>
			<td class="tdulleft">验收人员：</td>
			<td class="tdulright">${rpBean.maintenceAcceptance}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">直通型小号数量：</td>
			<td class="tdulright">${rpBean.directSmallNumber}</td>
			<td class="tdulleft">直通型中号数量：</td>
			<td class="tdulright">${rpBean.directMiddleNumber}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">直通型大号数量：</td>
			<td class="tdulright">${rpBean.directLargeNumber}</td>
			<td class="tdulleft">三通型小号数量：</td>
			<td class="tdulright">${rpBean.threeSmallNumber}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">三通型中号数量：</td>
			<td class="tdulright">${rpBean.threeMiddleNumber}</td>
			<td class="tdulleft">三通型大号数量：</td>
			<td class="tdulright">${rpBean.threeLargeNumber}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">四通型小号数量：</td>
			<td class="tdulright">${rpBean.fourSmallNumber}</td>
			<td class="tdulleft">四通型中号数量：</td>
			<td class="tdulright">${rpBean.fourMiddleNumber}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">四通型大号数量：</td>
			<td class="tdulright">${rpBean.fourLargelNumber}</td>
			<td class="tdulleft">局前型小号数量：</td>
			<td class="tdulright">${rpBean.forntSmallNumber}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">局前型中号数量：</td>
			<td class="tdulright">${rpBean.forntMiddleNumber}</td>
			<td class="tdulleft">局前型大号数量：</td>
			<td class="tdulright">${rpBean.forntLargeNumber}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">其他型小号数量：</td>
			<td class="tdulright">${rpBean.otherSmallNumber}</td>
			<td class="tdulleft">其他型中号数量：</td>
			<td class="tdulright">${rpBean.otherMiddleNumber}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">其他型大号数量：</td>
			<td class="tdulright">${rpBean.otherLargeNumber}</td>
			<td class="tdulleft">小号手孔尺寸1：</td>
			<td class="tdulright">${rpBean.smallLength0}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">中号手孔尺寸1：</td>
			<td class="tdulright">${rpBean.middleLength0}</td>
			<td class="tdulleft">大号手孔尺寸1：</td>
			<td class="tdulright">${rpBean.largeLength0}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">小号手孔尺寸2：</td>
			<td class="tdulright">${rpBean.smallLength1}</td>
			<td class="tdulleft">中号手孔尺寸2：</td>
			<td class="tdulright">${rpBean.middleLength1}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">大号手孔尺寸2：</td>
			<td class="tdulright">${rpBean.largeLength1}</td>
			<td class="tdulleft">其他手孔尺寸1：</td>
			<td class="tdulright">${rpBean.otherLength0}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">其他手孔尺寸2：</td>
			<td class="tdulright">${rpBean.otherLength1}</td>
			<td class="tdulleft"></td>
			<td class="tdulright"></td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">钢管孔数：</td>
			<td class="tdulright">${rpBean.steelHoleNumber}</td>
			<td class="tdulleft">钢管公里数：</td>
			<td class="tdulright">${rpBean.steelLength}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">塑料管孔数：</td>
			<td class="tdulright">${rpBean.plasticHoleNumber}</td>
			<td class="tdulleft">塑料管公里数：</td>
			<td class="tdulright">${rpBean.plasticLength}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">水泥管孔数：</td>
			<td class="tdulright">${rpBean.cementHoleNumber}</td>
			<td class="tdulleft">水泥管公里数：</td>
			<td class="tdulright">${rpBean.cementLength}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">其他管孔数：</td>
			<td class="tdulright">${rpBean.otherHoleNumber}</td>
			<td class="tdulleft">其他管公里数：</td>
			<td class="tdulright">${rpBean.otherLength}</td>
		</tr>
		<tr>
			<td colspan=6>
				<display:table name="sessionScope.pipeResults" id="row"  pagesize="8" export="false">
					<display:column media="html" title="验收次数">
						第${row.times}次验收
					</display:column>
					<display:column media="html" title="验收结果">
						<c:choose>
							<c:when test="${row.result eq '1'}">
								通过
							</c:when>
							<c:otherwise>
								未通过
							</c:otherwise>
						</c:choose>
					</display:column>
					<display:column media="html" title="计划验收日期">
						<bean:write name="row" property="planDate" format="yy/MM/dd"/>
					</display:column>
					<display:column media="html" title="实际验收日期">
						<bean:write name="row" property="factDate" format="yy/MM/dd"/>
					</display:column>
					<display:column media="html" title="操作">
						<a href="javascript:view('${row.id}')">查看</a
					</display:column>
				</display:table>
			</td>
		</tr>
		<tr>
			<td class="tdlist" colspan=6>
				<input type="button" class="button" value="返回" onclick="history.back();"/>
			</td>
		</tr>
	</table>
</body>
</html>