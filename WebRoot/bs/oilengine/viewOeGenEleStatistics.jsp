<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript" src="${ctx}/js/wdatePicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/js/wdatePicker/skin/WdatePicker.css">
<head>
<script language="javascript" type="">

</script>
</head>
<body>
	<template:titile value="�鿴�ͻ�����ͳ����Ϣ"/>
	<br />
		<table width="850" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					�ͻ���ţ�
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.oilEngineCode}
				</td>
				<td class="tdulleft" style="width:15%">
					�ϵ��վ��
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.stationName}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					�������ȣ�
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.startLongitude}
				</td>
				<td class="tdulleft" style="width:15%">
					����γ�ȣ�
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.startLatitude}
				</td>
			</tr>
			<tr class="trwhite">
				
				<td class="tdulleft" style="width:15%">
					����ʱ�䣺
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.setoutTime}
				</td>
				<td class="tdulleft" style="width:15%">
					����ʱ�䣺
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.genEleTime}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					��ʼ������
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.startDegree}  ��
				</td>
				<td class="tdulleft" style="width:15%">
					����������
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.endDegree}  ��
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					����ʱ�䣺
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.endTime}
				</td>
				<td class="tdulleft" style="width:15%">
					�ͺ�����
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.consumeQuantity}  ��
				</td>
			</tr>
			
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					���ﾭ�ȣ�
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.endLongitude}
				</td>
				<td class="tdulleft" style="width:15%">
					����γ�ȣ�
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.endLatitude}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					��������
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.endDegree-OEGENELE.startDegree}  ��
				</td>
				<td class="tdulleft" style="width:15%">
					����ʱ�䣺
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.eleTime}  ����
				</td>
			</tr>
			
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					�ͻ����ʱ�䣺
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.restoreTime}
				</td>
				<td class="tdulleft" style="width:15%">
					������
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.remark}
				</td>
			</tr>
		</table>
			<table width="40%" border="0"  style="margin-top: 6px" align="center" cellpadding="0" cellspacing="0"><tr align="center">
		      	<td>
		      	  	<input type="button" class="button" onclick="history.back()" value="����" >
		      	</td>
		    </tr></table>
</body>
