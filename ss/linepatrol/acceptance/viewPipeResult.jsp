<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
	
	<script type="text/javascript">
		function back(){
			parent.close();
		}
	</script>
</head>
<body onload="changeStyle()">
	<template:titile value="�ܵ���������" />
	<template:formTable namewidth="150" contentwidth="250">
		<template:formTr name="���մ���">
			��${pipeResult.times}������
		</template:formTr>
		<template:formTr name="���ս��">
			<c:choose>
				<c:when test="${pipeResult.result eq '1'}">
					ͨ��
				</c:when>
				<c:otherwise>
					δͨ��
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="�ƻ���������">
			<bean:write name="pipeResult" property="planDate" format="yyyy/MM/dd"/>
		</template:formTr>
		<template:formTr name="ʵ����������">
			<bean:write name="pipeResult" property="factDate" format="yyyy/MM/dd"/>
		</template:formTr>
		<template:formTr name="�����Ƿ�ϸ�">
			<c:choose>
				<c:when test="${pipeResult.isEligible0 eq '1'}">
					�ϸ�
				</c:when>
				<c:otherwise>
					���ϸ�
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="���ϲ��ϸ�ԭ��">
			${pipeResult.eligibleReason0}
		</template:formTr>
		<template:formTr name="��װ�����Ƿ�ϸ�">
			<c:choose>
				<c:when test="${pipeResult.isEligible1 eq '1'}">
					�ϸ�
				</c:when>
				<c:otherwise>
					���ϸ�
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="��װ���ղ��ϸ�ԭ��">
			${pipeResult.eligibleReason1}
		</template:formTr>
		<template:formTr name="�ܿ���ͨ�Ƿ�ϸ�">
			<c:choose>
				<c:when test="${pipeResult.isEligible2 eq '1'}">
					�ϸ�
				</c:when>
				<c:otherwise>
					���ϸ�
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="�ܿ���ͨ���ϸ�ԭ��">
			${pipeResult.eligibleReason2}
		</template:formTr>
		<template:formTr name="·��״���Ƿ�ϸ�">
			<c:choose>
				<c:when test="${pipeResult.isEligible3 eq '1'}">
					�ϸ�
				</c:when>
				<c:otherwise>
					���ϸ�
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="·��״�����ϸ�ԭ��">
			${pipeResult.eligibleReason3}
		</template:formTr>
		<template:formTr name="���ձ�ע">
			${pipeResult.remark}
		</template:formTr>
		<template:formSubmit>
			<td>
				<input type="button" class="button" value="�ر�" onclick="parent.close();"/>
			</td>
		</template:formSubmit>
	</template:formTable>
</body>
</html>