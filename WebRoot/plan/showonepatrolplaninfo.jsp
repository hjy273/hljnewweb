<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
	function addGoBack()
        {
        	var url = "<%=(String) request.getSession().getAttribute("previousURL")%>";
		    self.location.replace(url);
        }
</script>
		<title>巡检计划详细信息</title>
	</head>
	<body>
		<template:titile value="巡检计划详细信息" />
		<template:formTable namewidth="150" contentwidth="300" th2="内容">
			<template:formTr name="年份">
				<bean:write name="plan_bean" property="year" />
			</template:formTr>
			<template:formTr name="计划名称">
				<bean:write name="plan_bean" property="planname" />
			</template:formTr>
			<template:formTr name="计划类型">
				<logic:equal value="1" property="plantype" name="plan_bean">
						半年
					</logic:equal>
				<logic:equal value="2" property="plantype" name="plan_bean">
						季度
					</logic:equal>
				<logic:equal value="3" property="plantype" name="plan_bean">
						月度
					</logic:equal>
				<logic:equal value="4" property="plantype" name="plan_bean">
						自定义
					</logic:equal>
			</template:formTr>
			<template:formTr name="开始时间">
				<bean:write name="plan_bean" property="starttime" />
			</template:formTr>
			<template:formTr name="结束时间">
				<bean:write name="plan_bean" property="endtime" />
			</template:formTr>
			<template:formTr name="代维公司">
				<bean:write name="plan_bean" property="contractorname" />
			</template:formTr>
			<template:formTr name="巡检组">
				<bean:write name="plan_bean" property="patrolgroupname" />
			</template:formTr>
			<template:formTr name="巡检区县">
				<bean:write name="plan_bean" property="regionname" />
			</template:formTr>
			<template:formTr name="巡检资源">
				<logic:iterate id="resourceids" name="resouce_list">
					<bean:write name="resourceids" property="resource_type_lable" />--<bean:write
						name="resourceids" property="rs_name" />
					<br />
				</logic:iterate>
			</template:formTr>
			<template:formTr name="巡检模板">
	             <a href='${ctx }/wplanTemplateAction_view.jspx?flag=view&id=<bean:write name="plan_bean" property="templateid" />'><bean:write name="plan_bean" property="templatename" /></a>
			</template:formTr>
			<template:formSubmit>
				<td>
					<html:button property="action" styleClass="button"
						onclick="addGoBack()">返回	</html:button>
				</td>
			</template:formSubmit>
		</template:formTable>
	</body>
</html>
