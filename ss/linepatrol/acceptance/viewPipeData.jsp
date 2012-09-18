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
	<template:titile value="�ܵ���������" />
	<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
		<tr class=trcolor>
			<td class="tdulleft">��ά���ƣ�</td>
		    <td class="tdulright"><apptag:assorciateAttr table="contractorinfo" label="contractorname" key="contractorid" keyValue="${rpBean.contractorId}" /></td>
			<td class="tdulleft">�������ƣ�</td>
			<td class="tdulright">${rpBean.workName}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">�ܵ��ص㣺</td>
			<td class="tdulright">${rpBean.pipeAddress}</td>
			<td class="tdulleft">��Ȩ���ԣ�</td>
			<td class="tdulright"><apptag:quickLoadList cssClass="input" style="width:150" name="routeRes" keyValue="${rpBean.routeRes}" listName="property_right" type="look"/></td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">��ϸ·�ɣ�</td>
			<td class="tdulright">${rpBean.pipeLine}</td>
			<td class="tdulleft">����ͼֽ��</td>
			<td class="tdulright">${rpBean.picture}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">�ܵ�������Ŀ����</td>
			<td class="tdulright">${rpBean.principle}</td>
			<td class="tdulleft">�ܵ����ԣ�</td>
			<td class="tdulright"><apptag:quickLoadList cssClass="input" style="width:150" name="pipeType" keyValue="${rpBean.pipeType}" listName="pipe_type" type="look"/></td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">���������</td>
			<td class="tdulright">
				<c:choose>
					<c:when test="${rpBean.isMaintenance eq 'y'}">
						�ѽ���
					</c:when>
					<c:otherwise>
						δ����
					</c:otherwise>
				</c:choose>
			</td>
			<td class="tdulleft">��ע��</td>
			<td class="tdulright" colspan=3>${rpBean.remark}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">�ƶ����ȣ�</td>
			<td class="tdulright" colspan=5>
				�������${rpBean.mobileScareChannel}
				&nbsp;&nbsp;&nbsp;&nbsp;�ף����${rpBean.mobileScareHole}
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">�ܵ����ȣ�</td>
			<td class="tdulright" colspan=5>
				�������${rpBean.pipeLengthChannel}
				&nbsp;&nbsp;&nbsp;&nbsp;�ף����${rpBean.pipeLengthHole}
			</td>
		</tr>
		<tr>
			<td class="tdulleft">���赥λ��</td>
			<td class="tdulright">${rpBean.buildUnit}</td>
			<td class="tdulleft">������Ա��</td>
			<td class="tdulright">${rpBean.buildAcceptance}</td>
		</tr>
		<tr>
			<td class="tdulleft">ʩ����λ��</td>
			<td class="tdulright">${rpBean.workUnit}</td>
			<td class="tdulleft">������Ա��</td>
			<td class="tdulright">${rpBean.workAcceptance}</td>
		</tr>
		<tr>
			<td class="tdulleft">����λ��</td>
			<td class="tdulright">${rpBean.surveillanceUnit}</td>
			<td class="tdulleft">������Ա��</td>
			<td class="tdulright">${rpBean.surveillanceAccept}</td>
		</tr>
		<tr>
			<td class="tdulleft">ά����λ��</td>
			<td class="tdulright">${rpBean.maintenceUnit}</td>
			<td class="tdulleft">������Ա��</td>
			<td class="tdulright">${rpBean.maintenceAcceptance}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">ֱͨ��С��������</td>
			<td class="tdulright">${rpBean.directSmallNumber}</td>
			<td class="tdulleft">ֱͨ���к�������</td>
			<td class="tdulright">${rpBean.directMiddleNumber}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">ֱͨ�ʹ��������</td>
			<td class="tdulright">${rpBean.directLargeNumber}</td>
			<td class="tdulleft">��ͨ��С��������</td>
			<td class="tdulright">${rpBean.threeSmallNumber}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">��ͨ���к�������</td>
			<td class="tdulright">${rpBean.threeMiddleNumber}</td>
			<td class="tdulleft">��ͨ�ʹ��������</td>
			<td class="tdulright">${rpBean.threeLargeNumber}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">��ͨ��С��������</td>
			<td class="tdulright">${rpBean.fourSmallNumber}</td>
			<td class="tdulleft">��ͨ���к�������</td>
			<td class="tdulright">${rpBean.fourMiddleNumber}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">��ͨ�ʹ��������</td>
			<td class="tdulright">${rpBean.fourLargelNumber}</td>
			<td class="tdulleft">��ǰ��С��������</td>
			<td class="tdulright">${rpBean.forntSmallNumber}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">��ǰ���к�������</td>
			<td class="tdulright">${rpBean.forntMiddleNumber}</td>
			<td class="tdulleft">��ǰ�ʹ��������</td>
			<td class="tdulright">${rpBean.forntLargeNumber}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">������С��������</td>
			<td class="tdulright">${rpBean.otherSmallNumber}</td>
			<td class="tdulleft">�������к�������</td>
			<td class="tdulright">${rpBean.otherMiddleNumber}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">�����ʹ��������</td>
			<td class="tdulright">${rpBean.otherLargeNumber}</td>
			<td class="tdulleft">С���ֿ׳ߴ�1��</td>
			<td class="tdulright">${rpBean.smallLength0}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">�к��ֿ׳ߴ�1��</td>
			<td class="tdulright">${rpBean.middleLength0}</td>
			<td class="tdulleft">����ֿ׳ߴ�1��</td>
			<td class="tdulright">${rpBean.largeLength0}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">С���ֿ׳ߴ�2��</td>
			<td class="tdulright">${rpBean.smallLength1}</td>
			<td class="tdulleft">�к��ֿ׳ߴ�2��</td>
			<td class="tdulright">${rpBean.middleLength1}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">����ֿ׳ߴ�2��</td>
			<td class="tdulright">${rpBean.largeLength1}</td>
			<td class="tdulleft">�����ֿ׳ߴ�1��</td>
			<td class="tdulright">${rpBean.otherLength0}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">�����ֿ׳ߴ�2��</td>
			<td class="tdulright">${rpBean.otherLength1}</td>
			<td class="tdulleft"></td>
			<td class="tdulright"></td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">�ֹܿ�����</td>
			<td class="tdulright">${rpBean.steelHoleNumber}</td>
			<td class="tdulleft">�ֹܹ�������</td>
			<td class="tdulright">${rpBean.steelLength}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">���Ϲܿ�����</td>
			<td class="tdulright">${rpBean.plasticHoleNumber}</td>
			<td class="tdulleft">���Ϲܹ�������</td>
			<td class="tdulright">${rpBean.plasticLength}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">ˮ��ܿ�����</td>
			<td class="tdulright">${rpBean.cementHoleNumber}</td>
			<td class="tdulleft">ˮ��ܹ�������</td>
			<td class="tdulright">${rpBean.cementLength}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">�����ܿ�����</td>
			<td class="tdulright">${rpBean.otherHoleNumber}</td>
			<td class="tdulleft">�����ܹ�������</td>
			<td class="tdulright">${rpBean.otherLength}</td>
		</tr>
		<tr>
			<td colspan=6>
				<display:table name="sessionScope.pipeResults" id="row"  pagesize="8" export="false">
					<display:column media="html" title="���մ���">
						��${row.times}������
					</display:column>
					<display:column media="html" title="���ս��">
						<c:choose>
							<c:when test="${row.result eq '1'}">
								ͨ��
							</c:when>
							<c:otherwise>
								δͨ��
							</c:otherwise>
						</c:choose>
					</display:column>
					<display:column media="html" title="�ƻ���������">
						<bean:write name="row" property="planDate" format="yy/MM/dd"/>
					</display:column>
					<display:column media="html" title="ʵ����������">
						<bean:write name="row" property="factDate" format="yy/MM/dd"/>
					</display:column>
					<display:column media="html" title="����">
						<a href="javascript:view('${row.id}')">�鿴</a
					</display:column>
				</display:table>
			</td>
		</tr>
		<tr>
			<td class="tdlist" colspan=6>
				<input type="button" class="button" value="����" onclick="history.back();"/>
			</td>
		</tr>
	</table>
</body>
</html>