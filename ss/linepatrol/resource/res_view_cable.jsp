<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>�鿴�����м̶���Ϣ</title>
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<template:titile value="�鿴�����м̶���Ϣ"/>
		<template:formTable>
			<template:formTr name="�ʲ����" isOdd="false">
				${rs.assetno }
			</template:formTr>
			<template:formTr name="MIS��">
				${rs.MIS}
			</template:formTr>
			<template:formTr name="���¼���" isOdd="true">
				<apptag:quickLoadList cssClass="inputtext" keyValue="${rs.cableLevel}"  style="width:200px" name="cableLevel" listName="cabletype" type="look"/>
			</template:formTr>
			<template:formTr name="��о����" isOdd="false">
				${rs.coreNumber }о
			</template:formTr>
			<template:formTr name="����" isOdd="true">
			
				<apptag:quickLoadList cssClass="" keyValue="${rs.scetion}" name="scetion"  listName="terminal_address" type="look"></apptag:quickLoadList>
			</template:formTr>
			<template:formTr name="�м̶α��" isOdd="false">
				${rs.segmentid }
			</template:formTr>
			<template:formTr name="A��" isOdd="true">
				${rs.pointa }
			</template:formTr>
			<template:formTr name="Z��" isOdd="false">
			${rs.pointz }
			</template:formTr>
			<template:formTr name="�м̶�����" isOdd="true">
			${rs.segmentname }
			</template:formTr>
			<template:formTr name="λ������" isOdd="false">
			${rs.place }
			</template:formTr>
			<template:formTr name="��о�ͺ�" isOdd="true">
			${rs.fiberType }
			</template:formTr>
			<template:formTr name="���³���" isOdd="false">
				${rs.producer }
			</template:formTr>
			<template:formTr name="���跽ʽ" isOdd="true">
			
			<apptag:quickLoadList cssClass="" keyValue="${rs.laytype}" style="width:200px" name="laytype" listName="layingmethod" type="look"/>
			</template:formTr>
			<template:formTr name="���³���" isOdd="false">
			${rs.grossLength }����
			</template:formTr>
			<template:formTr name="��������" isOdd="true">
			${rs.reservedLength }����
			</template:formTr>
			<template:formTr name="ʩ����λ" isOdd="true">
				${rs.builder}
			</template:formTr>
			<template:formTr name="��������" isOdd="true">
			${rs.projectName }
			</template:formTr>
			<template:formTr name="��ά����" isOdd="false">
			${rs.finishtime } 
			</template:formTr>
			<template:formTr name="�������" isOdd="true">
				<c:if test="${rs.currentState=='y'}">
					����
				</c:if>
				<c:if test="${rs.currentState!='y'}">
					δ����
				</c:if>
			</template:formTr>
			
			<template:formTr name="�Ƿ�����ʽ����ͼֽ" isOdd="false">
			<c:if test="${rs.havePicture=='y'}">
					��
			 	</c:if>
				<c:if test="${rs.havePicture!='y'}">
					��
				</c:if>
			</template:formTr>
			<template:formTr name="��ά��ʽ" isOdd="true">
			<c:if test="${rs.isMaintenance=='y'}">
					���ս�ά
				</c:if>
				<c:if test="${rs.isMaintenance!='y'}">
					���Ͻ�ά
				</c:if>
			</template:formTr>
			<template:formTr name="ά����λ">
				${contractor[rs.maintenanceId]}
			</template:formTr>
			<template:formTr name="��ע" isOdd="false">
			${rs.remark }
			</template:formTr>
			<template:formTr name="��ע2" isOdd="false">
			${rs.remark2 }
			</template:formTr>
			<template:formTr name="��ע3" isOdd="false">
			${rs.remark3 }
			</template:formTr>
			<template:formTr name="����" isOdd="false">
			<apptag:upload state="look" cssClass="" entityId="${rs.kid}" entityType="LP_ACCEPTANCE_CABLE" useable="1"/>
			</template:formTr>
		</template:formTable>
		<template:formSubmit >
			<input type="button" name="submit" class="button" onclick='history.go(-1)' value="����"/>
		</template:formSubmit>
		<template:cssTable/>
	</body>
</html>