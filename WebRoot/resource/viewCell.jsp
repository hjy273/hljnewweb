<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<script language="javascript" type="text/javascript">
	var win;
	closeWin = function() {
		win.close();
	}
</script>
<body>
	<template:titile value="�鿴С��"/>
	<br />
	<table border="0" bgcolor="#FFFFFF" width="100%" align="center" cellpadding="0" cellspacing="0"><tr><td>
	<s:form action="cellAction_save" name="view" method="post">
		<table width="850" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					������վ��
				</td>
				<td class="tdulright"  style="width:35%">
					${BASESTATIONS[cell.parentId]}
				</td>
				<td class="tdulleft" style="width:15%">
					С���ţ�
				</td>
				<td class="tdulright" style="width:35%">
					${cell.cellCode}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					��Ԫ��ţ�
				</td>
				<td class="tdulright" style="width:35%">
					${cell.netElementCode }
				</td>
				<td class="tdulleft" style="width:15%">
					��Ԫ���ƣ�
				</td>
				<td class="tdulright" style="width:35%">
					${cell.netElementName }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					�������ƣ�
				</td>
				<td class="tdulright" style="width:35%">
					${cell.chineseName}
				</td>
				<td class="tdulleft" style="width:15%">
					С���㲥�ŵ���
				</td>
				<td class="tdulright" style="width:35%">
					${cell.broadcastChannel }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					��Ƶ����
				</td>
				<td class="tdulright" style="width:35%">
					${cell.carrierFrequencyNum}
				</td>
				<td class="tdulleft" style="width:15%">
					�Ƿ��´�ֱ��վ��
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${cell.isOwnRepeater=='y'}">��</c:if>
					<c:if test="${cell.isOwnRepeater!='y'}">��</c:if>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					�Ƿ��´����ţ�
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${cell.isOwnTma=='y'}">��</c:if>
					<c:if test="${cell.isOwnTma!='y'}">��</c:if>
				</td>
				<td class="tdulleft" style="width:15%">
					�Ƿ���GPRS��
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${cell.isOpenGPRS=='y'}">��</c:if>
					<c:if test="${cell.isOpenGPRS!='y'}">��</c:if>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					�Ƿ���EGPRS��
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${cell.isOpenEGPRS=='y'}">��</c:if>
					<c:if test="${cell.isOpenEGPRS!='y'}">��</c:if>
				</td>
				<td class="tdulleft" style="width:15%">
					����ʱ�䣺
				</td>
				<td class="tdulright" style="width:35%">
					<bean:write name="cell" property="anTime" format="yyyy-MM-dd" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					��Чȫ�����Ƶ�ʣ�
				</td>
				<td class="tdulright" style="width:35%">
					${cell.allRoundPower }
				</td>
				<td class="tdulleft" style="width:15%">
					����Ƶ�Σ�
				</td>
				<td class="tdulright" style="width:35%">
					${cell.bcchFreq }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					��̬PDCH����
				</td>
				<td class="tdulright" style="width:35%">
					${cell.staticPdchNum}
				</td>
				<td class="tdulleft" style="width:15%">
					��̬PDCH����
				</td>
				<td class="tdulright" style="width:35%">
					${cell.dynamicPdchNum}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					�Ƿ���Ƶ��
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${cell.isFrequencyHopping=='y'}">��</c:if>
					<c:if test="${cell.isFrequencyHopping!='y'}">��</c:if>
				</td>
				<td class="tdulleft" style="width:15%">
					��Ƶ���ͣ�
				</td>
				<td class="tdulright" style="width:35%">
					${cell.frequencyHoppingType}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					����BCF��
				</td>
				<td class="tdulright" style="width:35%">
					${cell.attachBCF}
				</td>
				<td class="tdulleft" style="width:15%">
					�Ƿ����С����
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${cell.isBoundaryCell=='y'}">��</c:if>
					<c:if test="${cell.isBoundaryCell!='y'}">��</c:if>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					���ǵĶԷ����м��绰���ţ�
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					${cell.coverThing}
				</td>
			</tr>
			
			
		</table>
		<table width="40%" border="0"  style="margin-top: 6px" align="center" cellpadding="0" cellspacing="0"><tr align="center">
		      	<td>
		      	  	<c:if test="${showClose=='0'}">
		      	  	<input type="button" class="button" onclick="history.back()" value="����" >
		      	  	</c:if>
		      	  	<c:if test="${showClose=='1'}">
		      	  	<input type="button" class="button" onclick="closeWin();" value="�ر�" >
		      	  	</c:if>
		      	</td>
			</tr></table>
	</s:form>
	</td></tr></table>
</body>