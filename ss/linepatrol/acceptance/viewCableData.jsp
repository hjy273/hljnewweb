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
			url = '${ctx}/acceptanceAction.do?method=viewCableResult&id='+id;
			showWin(url);
		}
	</script>
</head>
<body>
	<template:titile value="光缆验收数据" />
	<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
		<tr class=trcolor>
			<td class="tdulleft">代维名称：</td>
		    <td class="tdulright"><apptag:assorciateAttr table="contractorinfo" label="contractorname" key="contractorid" keyValue="${rcBean.contractorId}" /></td>
			<td class="tdulleft"></td>
		    <td class="tdulright"></td>
		</tr>
		<tr class=trcolor>
		    <td class="tdulleft">工程名称：</td>
		    <td class="tdulright">${rcBean.projectName}</td>
		    <td class="tdulleft">资产编号：</td>
		    <td class="tdulright">${rcBean.assetno}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">光缆编号：</td>
			<td class="tdulright">${rcBean.segmentid}</td>
			<td class="tdulleft">A端：</td>
			<td class="tdulright">${rcBean.pointa}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">Z端：</td>
			<td class="tdulright">${rcBean.pointz}</td>
			<td class="tdulleft">光缆中继段：</td>
			<td class="tdulright">${rcBean.segmentname}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">纤芯数：</td>
			<td class="tdulright">${rcBean.coreNumber}</td>
			<td class="tdulleft">光缆长度(千米)：</td>
			<td class="tdulright">${rcBean.grossLength}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">施工单位：</td>
			<td class="tdulright">${rcBean.builder}</td>
			<td class="tdulleft">光缆级别：</td>
			<td class="tdulright"><apptag:quickLoadList cssClass="select" keyValue="${rcBean.cableLevel}"  name="cableLevel" listName="cabletype" type="look" style="width:150"/></td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">正式竣工图纸：</td>
			<td class="tdulright">
				<c:choose>
					<c:when test="${rcBean.havePicture eq 'y'}">
						有
					</c:when>
					<c:otherwise>
						无
					</c:otherwise>
				</c:choose>
			</td>
			<td class="tdulleft">区域：</td>
			<td class="tdulright">
				<apptag:quickLoadList name="scetion" listName="terminal_address" keyValue="${rcBean.scetion}" type="look"></apptag:quickLoadList>
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">纤芯型号：</td>
			<td class="tdulright">${rcBean.fiberType}</td>
			<td class="tdulleft">地点：</td>
			<td class="tdulright">
				<apptag:assorciateAttr table="dictionary_formitem" label="lable" key="code" condition="assortment_id='town'" keyValue="${rcBean.place}" />
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">盘留长度(千米)：</td>
			<td class="tdulright">${rcBean.reservedLength}</td>
			<td class="tdulleft">光缆厂家：</td>
			<td class="tdulright">${rcBean.producer}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">交资情况：</td>
			<td class="tdulright">
				<c:choose>
					<c:when test="${rcBean.currentState eq 'y'}">
						已交资
					</c:when>
					<c:otherwise>
						未交资
					</c:otherwise>
				</c:choose>
			</td>
			<td class="tdulleft">测试折射率：</td>
			<td class="tdulright">${rcBean.refactiveIndex}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">铺设方式：</td>
			<td class="tdulright" colspan=3><apptag:quickLoadList cssClass="input" keyValue="${rcBean.laytype}" name="laytype" listName="layingmethod" type="look"/></td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">备注：</td>
			<td class="tdulright" colspan=3>${rcBean.remark}</td>
		</tr> 
		<tr>
			<td class="tdulleft">建设单位：</td>
			<td class="tdulright">${rcBean.buildUnit}</td>
			<td class="tdulleft">验收人员：</td>
			<td class="tdulright">${rcBean.buildAcceptance}</td>
		</tr>
		<tr>
			<td class="tdulleft">施工单位：</td>
			<td class="tdulright">${rcBean.workUnit}</td>
			<td class="tdulleft">验收人员：</td>
			<td class="tdulright">${rcBean.workAcceptance}</td>
		</tr>
		<tr>
			<td class="tdulleft">监理单位：</td>
			<td class="tdulright">${rcBean.surveillanceUnit}</td>
			<td class="tdulleft">验收人员：</td>
			<td class="tdulright">${rcBean.surveillanceAccept}</td>
		</tr>
		<tr>
			<td class="tdulleft">维护单位：</td>
			<td class="tdulright">${rcBean.maintenceUnit}</td>
			<td class="tdulleft">验收人员：</td>
			<td class="tdulright">${rcBean.maintenceAcceptance}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">是否TD：</td>
			<td class="tdulright">
				<c:choose>
					<c:when test="${rcBean.isTd eq '1'}">
						是
					</c:when>
					<c:otherwise>
						否
					</c:otherwise>
				</c:choose>
			</td>
			<td class="tdulleft">网通管道(千米)：</td>
			<td class="tdulright">${rcBean.pipeLength0}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">联建管道(千米)：</td>
			<td class="tdulright">${rcBean.pipeLength1}</td>
			<td class="tdulleft">自建管道(千米)：</td>
			<td class="tdulright">${rcBean.pipeLength2}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">北信管道(千米)：</td>
			<td class="tdulright">${rcBean.pipeLength3}</td>
			<td class="tdulleft">歌华管道(千米)：</td>
			<td class="tdulright">${rcBean.pipeLength4}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">架空(千米)：</td>
			<td class="tdulright">${rcBean.trunkmentLength0}</td>
			<td class="tdulleft">管道(千米):</td>
			<td class="tdulright">${rcBean.trunkmentLength1}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">直埋(千米)：</td>
			<td class="tdulright">${rcBean.trunkmentLength2}</td>
			<td class="tdulleft">其他(千米)：</td>
			<td class="tdulright">${rcBean.trunkmentLength3}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">电力杆1：</td>
			<td class="tdulright" colspan=3>
				${rcBean.e1Length}千米
				${rcBean.e1Number}根
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">电力杆2：</td>
			<td class="tdulright" colspan=3>
				${rcBean.e2Length}千米 
				${rcBean.e2Number}根
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">电力杆3：</td>
			<td class="tdulright" colspan=3>
				${rcBean.e3Length}千米
				${rcBean.e3Number}根
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">通信杆1：</td>
			<td class="tdulright" colspan=3>
				${rcBean.t1Length}千米
				${rcBean.t1Number}根
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">通信杆2：</td>
			<td class="tdulright" colspan=3>
				${rcBean.t2Length}千米
				${rcBean.t2Number}根
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">通信杆3：</td>
			<td class="tdulright" colspan=3>
				${rcBean.t3Length}千米 
				${rcBean.t3Number}根
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">其他1：</td>
			<td class="tdulright" colspan=3>
				${rcBean.other1}千米 
				${rcBean.other1Number}根
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">其他2：</td>
			<td class="tdulright" colspan=3>
				${rcBean.other2}千米 
				${rcBean.other2Number}根
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">人手井：</td>
			<td class="tdulright" colspan=3>
				${rcBean.jNumber}
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">图纸：</td>
			<td class="tdulright" colspan=3>
				<apptag:upload state="look" cssClass="" entityId="${rcBean.cableId}" entityType="LP_ACCEPTANCE_CABLE" useable="0"/>
			</td>
		</tr>
		<tr>
			<td colspan=4>
				<display:table name="sessionScope.cableResults" id="row"  pagesize="8" export="false">
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