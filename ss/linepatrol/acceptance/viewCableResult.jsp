<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
	
	<script type="text/javascript">
	</script>
</head>
<body onload="changeStyle()">
	<template:titile value="光缆验收数据" />
	<template:formTable namewidth="150" contentwidth="250">
		<template:formTr name="验收次数">
			第${cableResult.times}次验收
		</template:formTr>
		<template:formTr name="验收结果">
			<c:choose>
				<c:when test="${cableResult.result eq '1'}">
					通过
				</c:when>
				<c:otherwise>
					未通过
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="计划验收日期">
			<bean:write name="cableResult" property="planDate" format="yyyy/MM/dd"/>
		</template:formTr>
		<template:formTr name="实际验收日期">
			<bean:write name="cableResult" property="factDate" format="yyyy/MM/dd"/>
		</template:formTr>
		<template:formTr name="资料是否合格">
			<c:choose>
				<c:when test="${cableResult.isEligible0 eq '1'}">
					合格
				</c:when>
				<c:otherwise>
					不合格
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="资料不合格原因">
			${cableResult.eligibleReason0}
		</template:formTr>
		<template:formTr name="光缆端别是否合格">
			<c:choose>
				<c:when test="${cableResult.isEligible1 eq '1'}">
					合格
				</c:when>
				<c:otherwise>
					不合格
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="光缆端别不合格原因">
			${cableResult.eligibleReason1}
		</template:formTr>
		<template:formTr name="室内工艺是否合格">
			<c:choose>
				<c:when test="${cableResult.isEligible2 eq '1'}">
					合格
				</c:when>
				<c:otherwise>
					不合格
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="室内工艺不合格原因">
			${cableResult.eligibleReason2}
		</template:formTr>
		<template:formTr name="室外工艺是否合格">
			<c:choose>
				<c:when test="${cableResult.isEligible3 eq '1'}">
					合格
				</c:when>
				<c:otherwise>
					不合格
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="室外工艺不合格原因">
			${cableResult.eligibleReason3}
		</template:formTr>
		<template:formTr name="路由状况是否合格">
			<c:choose>
				<c:when test="${cableResult.isEligible4 eq '1'}">
					合格
				</c:when>
				<c:otherwise>
					不合格
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="路由状况不合格原因">
			${cableResult.eligibleReason4}
		</template:formTr>
		<template:formTr name="测试情况是否合格">
			<c:choose>
				<c:when test="${cableResult.isEligible5 eq '1'}">
					合格
				</c:when>
				<c:otherwise>
					不合格
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="测试情况不合格原因">
			${cableResult.eligibleReason5}
		</template:formTr>
		<template:formTr name="加强芯是否接地">
			<c:choose>
				<c:when test="${cableResult.isEligible6 eq '1'}">
					合格
				</c:when>
				<c:otherwise>
					不合格
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="加强芯不接地原因">
			${cableResult.eligibleReason6}
		</template:formTr>
		<template:formTr name="验收备注">
			${cableResult.remark}
		</template:formTr>
		<template:formSubmit>
			<td>
				<input type="button" class="button" value="关闭" onclick="parent.close();"/>
			</td>
		</template:formSubmit>
	</template:formTable>
</body>
</html>