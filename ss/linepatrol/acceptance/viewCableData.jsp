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
	<template:titile value="������������" />
	<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
		<tr class=trcolor>
			<td class="tdulleft">��ά���ƣ�</td>
		    <td class="tdulright"><apptag:assorciateAttr table="contractorinfo" label="contractorname" key="contractorid" keyValue="${rcBean.contractorId}" /></td>
			<td class="tdulleft"></td>
		    <td class="tdulright"></td>
		</tr>
		<tr class=trcolor>
		    <td class="tdulleft">�������ƣ�</td>
		    <td class="tdulright">${rcBean.projectName}</td>
		    <td class="tdulleft">�ʲ���ţ�</td>
		    <td class="tdulright">${rcBean.assetno}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">���±�ţ�</td>
			<td class="tdulright">${rcBean.segmentid}</td>
			<td class="tdulleft">A�ˣ�</td>
			<td class="tdulright">${rcBean.pointa}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">Z�ˣ�</td>
			<td class="tdulright">${rcBean.pointz}</td>
			<td class="tdulleft">�����м̶Σ�</td>
			<td class="tdulright">${rcBean.segmentname}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">��о����</td>
			<td class="tdulright">${rcBean.coreNumber}</td>
			<td class="tdulleft">���³���(ǧ��)��</td>
			<td class="tdulright">${rcBean.grossLength}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">ʩ����λ��</td>
			<td class="tdulright">${rcBean.builder}</td>
			<td class="tdulleft">���¼���</td>
			<td class="tdulright"><apptag:quickLoadList cssClass="select" keyValue="${rcBean.cableLevel}"  name="cableLevel" listName="cabletype" type="look" style="width:150"/></td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">��ʽ����ͼֽ��</td>
			<td class="tdulright">
				<c:choose>
					<c:when test="${rcBean.havePicture eq 'y'}">
						��
					</c:when>
					<c:otherwise>
						��
					</c:otherwise>
				</c:choose>
			</td>
			<td class="tdulleft">����</td>
			<td class="tdulright">
				<apptag:quickLoadList name="scetion" listName="terminal_address" keyValue="${rcBean.scetion}" type="look"></apptag:quickLoadList>
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">��о�ͺţ�</td>
			<td class="tdulright">${rcBean.fiberType}</td>
			<td class="tdulleft">�ص㣺</td>
			<td class="tdulright">
				<apptag:assorciateAttr table="dictionary_formitem" label="lable" key="code" condition="assortment_id='town'" keyValue="${rcBean.place}" />
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">��������(ǧ��)��</td>
			<td class="tdulright">${rcBean.reservedLength}</td>
			<td class="tdulleft">���³��ң�</td>
			<td class="tdulright">${rcBean.producer}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">���������</td>
			<td class="tdulright">
				<c:choose>
					<c:when test="${rcBean.currentState eq 'y'}">
						�ѽ���
					</c:when>
					<c:otherwise>
						δ����
					</c:otherwise>
				</c:choose>
			</td>
			<td class="tdulleft">���������ʣ�</td>
			<td class="tdulright">${rcBean.refactiveIndex}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">���跽ʽ��</td>
			<td class="tdulright" colspan=3><apptag:quickLoadList cssClass="input" keyValue="${rcBean.laytype}" name="laytype" listName="layingmethod" type="look"/></td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">��ע��</td>
			<td class="tdulright" colspan=3>${rcBean.remark}</td>
		</tr> 
		<tr>
			<td class="tdulleft">���赥λ��</td>
			<td class="tdulright">${rcBean.buildUnit}</td>
			<td class="tdulleft">������Ա��</td>
			<td class="tdulright">${rcBean.buildAcceptance}</td>
		</tr>
		<tr>
			<td class="tdulleft">ʩ����λ��</td>
			<td class="tdulright">${rcBean.workUnit}</td>
			<td class="tdulleft">������Ա��</td>
			<td class="tdulright">${rcBean.workAcceptance}</td>
		</tr>
		<tr>
			<td class="tdulleft">����λ��</td>
			<td class="tdulright">${rcBean.surveillanceUnit}</td>
			<td class="tdulleft">������Ա��</td>
			<td class="tdulright">${rcBean.surveillanceAccept}</td>
		</tr>
		<tr>
			<td class="tdulleft">ά����λ��</td>
			<td class="tdulright">${rcBean.maintenceUnit}</td>
			<td class="tdulleft">������Ա��</td>
			<td class="tdulright">${rcBean.maintenceAcceptance}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">�Ƿ�TD��</td>
			<td class="tdulright">
				<c:choose>
					<c:when test="${rcBean.isTd eq '1'}">
						��
					</c:when>
					<c:otherwise>
						��
					</c:otherwise>
				</c:choose>
			</td>
			<td class="tdulleft">��ͨ�ܵ�(ǧ��)��</td>
			<td class="tdulright">${rcBean.pipeLength0}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">�����ܵ�(ǧ��)��</td>
			<td class="tdulright">${rcBean.pipeLength1}</td>
			<td class="tdulleft">�Խ��ܵ�(ǧ��)��</td>
			<td class="tdulright">${rcBean.pipeLength2}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">���Źܵ�(ǧ��)��</td>
			<td class="tdulright">${rcBean.pipeLength3}</td>
			<td class="tdulleft">�軪�ܵ�(ǧ��)��</td>
			<td class="tdulright">${rcBean.pipeLength4}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">�ܿ�(ǧ��)��</td>
			<td class="tdulright">${rcBean.trunkmentLength0}</td>
			<td class="tdulleft">�ܵ�(ǧ��):</td>
			<td class="tdulright">${rcBean.trunkmentLength1}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">ֱ��(ǧ��)��</td>
			<td class="tdulright">${rcBean.trunkmentLength2}</td>
			<td class="tdulleft">����(ǧ��)��</td>
			<td class="tdulright">${rcBean.trunkmentLength3}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">������1��</td>
			<td class="tdulright" colspan=3>
				${rcBean.e1Length}ǧ��
				${rcBean.e1Number}��
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">������2��</td>
			<td class="tdulright" colspan=3>
				${rcBean.e2Length}ǧ�� 
				${rcBean.e2Number}��
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">������3��</td>
			<td class="tdulright" colspan=3>
				${rcBean.e3Length}ǧ��
				${rcBean.e3Number}��
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">ͨ�Ÿ�1��</td>
			<td class="tdulright" colspan=3>
				${rcBean.t1Length}ǧ��
				${rcBean.t1Number}��
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">ͨ�Ÿ�2��</td>
			<td class="tdulright" colspan=3>
				${rcBean.t2Length}ǧ��
				${rcBean.t2Number}��
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">ͨ�Ÿ�3��</td>
			<td class="tdulright" colspan=3>
				${rcBean.t3Length}ǧ�� 
				${rcBean.t3Number}��
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">����1��</td>
			<td class="tdulright" colspan=3>
				${rcBean.other1}ǧ�� 
				${rcBean.other1Number}��
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">����2��</td>
			<td class="tdulright" colspan=3>
				${rcBean.other2}ǧ�� 
				${rcBean.other2Number}��
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">���־���</td>
			<td class="tdulright" colspan=3>
				${rcBean.jNumber}
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">ͼֽ��</td>
			<td class="tdulright" colspan=3>
				<apptag:upload state="look" cssClass="" entityId="${rcBean.cableId}" entityType="LP_ACCEPTANCE_CABLE" useable="0"/>
			</td>
		</tr>
		<tr>
			<td colspan=4>
				<display:table name="sessionScope.cableResults" id="row"  pagesize="8" export="false">
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