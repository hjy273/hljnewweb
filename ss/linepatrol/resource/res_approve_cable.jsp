<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>查看光缆中继段信息</title>
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<template:titile value="查看光缆中继段信息"/>
		<template:formTable>
		<html:form action="/resAction.do?method=approve" styleId="saveres_cable">
			<input type="hidden" value="${type}" name="type" />
			<input type="hidden" value="${rs.kid}" name="id" />
			<template:formTr name="资产编号" isOdd="false">
				${rs.assetno}
			</template:formTr>
			<template:formTr name="光缆级别" isOdd="true">
				<apptag:quickLoadList cssClass="inputtext" keyValue="${rs.cableLevel}"  style="width:200px" name="cableLevel" listName="cabletype" type="look"/>
			</template:formTr>
			<template:formTr name="纤芯数量" isOdd="false">
				${rs.coreNumber }芯
			</template:formTr>
			<template:formTr name="区域" isOdd="true">
				<apptag:quickLoadList cssClass="" keyValue="${rs.scetion}" name="scetion"  listName="terminal_address" type="look"></apptag:quickLoadList>
			</template:formTr>
			<template:formTr name="中继段编号" isOdd="false">
				${rs.segmentid }
			</template:formTr>
			<template:formTr name="A端" isOdd="true">
				${rs.pointa }
			</template:formTr>
			<template:formTr name="Z端" isOdd="false">
			${rs.pointz }
			</template:formTr>
			<template:formTr name="中继段名称" isOdd="true">
			${rs.segmentname }
			</template:formTr>
			<template:formTr name="位置描述" isOdd="false">
			${rs.place }
			</template:formTr>
			<template:formTr name="纤芯型号" isOdd="true">
			${rs.fiberType }
			</template:formTr>
			<template:formTr name="光缆厂家" isOdd="false">
				${rs.producer }
			</template:formTr>
			<template:formTr name="敷设方式" isOdd="true">
			
			<apptag:quickLoadList cssClass="" keyValue="${rs.laytype}" style="width:200px" name="laytype" listName="layingmethod" type="look"/>
			</template:formTr>
			<template:formTr name="光缆长度" isOdd="false">
			${rs.grossLength }公里
			</template:formTr>
			<template:formTr name="盘留长度" isOdd="true">
			${rs.reservedLength }公里
			</template:formTr>
			<template:formTr name="施工单位" isOdd="true">
				${rs.builder}
			</template:formTr>
			<template:formTr name="工程名称" isOdd="true">
			${rs.projectName }
			</template:formTr>
			<template:formTr name="交维日期" isOdd="false">
			${rs.finishtime } 
			</template:formTr>
			<template:formTr name="交资情况" isOdd="true">
				<c:if test="${rs.currentState=='y'}">
					交资
				</c:if>
				<c:if test="${rs.currentState!='y'}">
					未交资
				</c:if>
			</template:formTr>
			
			<template:formTr name="是否有正式竣工图纸" isOdd="false">
			<c:if test="${rs.havePicture=='y'}">
					有
			 	</c:if>
				<c:if test="${rs.havePicture!='y'}">
					无
				</c:if>
			</template:formTr>
			<template:formTr name="交维方式" isOdd="true">
			<c:if test="${rs.isMaintenance=='y'}">
					验收交维
				</c:if>
				<c:if test="${rs.isMaintenance!='y'}">
					资料交维
				</c:if>
			</template:formTr>
			<template:formTr name="备注" isOdd="false">
				${rs.remark }
			</template:formTr>
			<template:formTr name="本次更新"  isOdd="false">
				<apptag:upload state="look" cssClass="" entityId="${rs.kid}" entityType="LP_ACCEPTANCE_CABLE" useable="0"/>
			</template:formTr>
			<template:formTr name="审核" isOdd="true">
				<input type="radio" name="approve"  value="1" checked/>通过
				<input type="radio" name="approve"  value="0"/>不通过
			</template:formTr>
			<template:formSubmit>
				<td>
					<input type="submit" class="button" name="submit" value="提 交"/>
					<input type="button" name="submit" class="button" onclick='history.go(-1)' value="返回"/>
				</td>
			</template:formSubmit>
		</html:form>
		</template:formTable>
	</body>
</html>