<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
	
	<script type="text/javascript">
	</script>
</head>
<body onload="changeStyle()">
	<template:titile value="������������" />
	<template:formTable namewidth="150" contentwidth="250">
		<template:formTr name="���մ���">
			��${cableResult.times}������
		</template:formTr>
		<template:formTr name="���ս��">
			<c:choose>
				<c:when test="${cableResult.result eq '1'}">
					ͨ��
				</c:when>
				<c:otherwise>
					δͨ��
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="�ƻ���������">
			<bean:write name="cableResult" property="planDate" format="yyyy/MM/dd"/>
		</template:formTr>
		<template:formTr name="ʵ����������">
			<bean:write name="cableResult" property="factDate" format="yyyy/MM/dd"/>
		</template:formTr>
		<template:formTr name="�����Ƿ�ϸ�">
			<c:choose>
				<c:when test="${cableResult.isEligible0 eq '1'}">
					�ϸ�
				</c:when>
				<c:otherwise>
					���ϸ�
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="���ϲ��ϸ�ԭ��">
			${cableResult.eligibleReason0}
		</template:formTr>
		<template:formTr name="���¶˱��Ƿ�ϸ�">
			<c:choose>
				<c:when test="${cableResult.isEligible1 eq '1'}">
					�ϸ�
				</c:when>
				<c:otherwise>
					���ϸ�
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="���¶˱𲻺ϸ�ԭ��">
			${cableResult.eligibleReason1}
		</template:formTr>
		<template:formTr name="���ڹ����Ƿ�ϸ�">
			<c:choose>
				<c:when test="${cableResult.isEligible2 eq '1'}">
					�ϸ�
				</c:when>
				<c:otherwise>
					���ϸ�
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="���ڹ��ղ��ϸ�ԭ��">
			${cableResult.eligibleReason2}
		</template:formTr>
		<template:formTr name="���⹤���Ƿ�ϸ�">
			<c:choose>
				<c:when test="${cableResult.isEligible3 eq '1'}">
					�ϸ�
				</c:when>
				<c:otherwise>
					���ϸ�
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="���⹤�ղ��ϸ�ԭ��">
			${cableResult.eligibleReason3}
		</template:formTr>
		<template:formTr name="·��״���Ƿ�ϸ�">
			<c:choose>
				<c:when test="${cableResult.isEligible4 eq '1'}">
					�ϸ�
				</c:when>
				<c:otherwise>
					���ϸ�
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="·��״�����ϸ�ԭ��">
			${cableResult.eligibleReason4}
		</template:formTr>
		<template:formTr name="��������Ƿ�ϸ�">
			<c:choose>
				<c:when test="${cableResult.isEligible5 eq '1'}">
					�ϸ�
				</c:when>
				<c:otherwise>
					���ϸ�
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="����������ϸ�ԭ��">
			${cableResult.eligibleReason5}
		</template:formTr>
		<template:formTr name="��ǿо�Ƿ�ӵ�">
			<c:choose>
				<c:when test="${cableResult.isEligible6 eq '1'}">
					�ϸ�
				</c:when>
				<c:otherwise>
					���ϸ�
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="��ǿо���ӵ�ԭ��">
			${cableResult.eligibleReason6}
		</template:formTr>
		<template:formTr name="���ձ�ע">
			${cableResult.remark}
		</template:formTr>
		<template:formSubmit>
			<td>
				<input type="button" class="button" value="�ر�" onclick="parent.close();"/>
			</td>
		</template:formSubmit>
	</template:formTable>
</body>
</html>