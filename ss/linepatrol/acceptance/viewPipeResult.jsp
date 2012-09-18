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
	<template:titile value="管道验收数据" />
	<template:formTable namewidth="150" contentwidth="250">
		<template:formTr name="验收次数">
			第${pipeResult.times}次验收
		</template:formTr>
		<template:formTr name="验收结果">
			<c:choose>
				<c:when test="${pipeResult.result eq '1'}">
					通过
				</c:when>
				<c:otherwise>
					未通过
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="计划验收日期">
			<bean:write name="pipeResult" property="planDate" format="yyyy/MM/dd"/>
		</template:formTr>
		<template:formTr name="实际验收日期">
			<bean:write name="pipeResult" property="factDate" format="yyyy/MM/dd"/>
		</template:formTr>
		<template:formTr name="资料是否合格">
			<c:choose>
				<c:when test="${pipeResult.isEligible0 eq '1'}">
					合格
				</c:when>
				<c:otherwise>
					不合格
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="资料不合格原因">
			${pipeResult.eligibleReason0}
		</template:formTr>
		<template:formTr name="安装工艺是否合格">
			<c:choose>
				<c:when test="${pipeResult.isEligible1 eq '1'}">
					合格
				</c:when>
				<c:otherwise>
					不合格
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="安装工艺不合格原因">
			${pipeResult.eligibleReason1}
		</template:formTr>
		<template:formTr name="管孔试通是否合格">
			<c:choose>
				<c:when test="${pipeResult.isEligible2 eq '1'}">
					合格
				</c:when>
				<c:otherwise>
					不合格
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="管孔试通不合格原因">
			${pipeResult.eligibleReason2}
		</template:formTr>
		<template:formTr name="路由状况是否合格">
			<c:choose>
				<c:when test="${pipeResult.isEligible3 eq '1'}">
					合格
				</c:when>
				<c:otherwise>
					不合格
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formTr name="路由状况不合格原因">
			${pipeResult.eligibleReason3}
		</template:formTr>
		<template:formTr name="验收备注">
			${pipeResult.remark}
		</template:formTr>
		<template:formSubmit>
			<td>
				<input type="button" class="button" value="关闭" onclick="parent.close();"/>
			</td>
		</template:formSubmit>
	</template:formTable>
</body>
</html>