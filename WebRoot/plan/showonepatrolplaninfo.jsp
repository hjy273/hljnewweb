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
		<title>Ѳ��ƻ���ϸ��Ϣ</title>
	</head>
	<body>
		<template:titile value="Ѳ��ƻ���ϸ��Ϣ" />
		<template:formTable namewidth="150" contentwidth="300" th2="����">
			<template:formTr name="���">
				<bean:write name="plan_bean" property="year" />
			</template:formTr>
			<template:formTr name="�ƻ�����">
				<bean:write name="plan_bean" property="planname" />
			</template:formTr>
			<template:formTr name="�ƻ�����">
				<logic:equal value="1" property="plantype" name="plan_bean">
						����
					</logic:equal>
				<logic:equal value="2" property="plantype" name="plan_bean">
						����
					</logic:equal>
				<logic:equal value="3" property="plantype" name="plan_bean">
						�¶�
					</logic:equal>
				<logic:equal value="4" property="plantype" name="plan_bean">
						�Զ���
					</logic:equal>
			</template:formTr>
			<template:formTr name="��ʼʱ��">
				<bean:write name="plan_bean" property="starttime" />
			</template:formTr>
			<template:formTr name="����ʱ��">
				<bean:write name="plan_bean" property="endtime" />
			</template:formTr>
			<template:formTr name="��ά��˾">
				<bean:write name="plan_bean" property="contractorname" />
			</template:formTr>
			<template:formTr name="Ѳ����">
				<bean:write name="plan_bean" property="patrolgroupname" />
			</template:formTr>
			<template:formTr name="Ѳ������">
				<bean:write name="plan_bean" property="regionname" />
			</template:formTr>
			<template:formTr name="Ѳ����Դ">
				<logic:iterate id="resourceids" name="resouce_list">
					<bean:write name="resourceids" property="resource_type_lable" />--<bean:write
						name="resourceids" property="rs_name" />
					<br />
				</logic:iterate>
			</template:formTr>
			<template:formTr name="Ѳ��ģ��">
	             <a href='${ctx }/wplanTemplateAction_view.jspx?flag=view&id=<bean:write name="plan_bean" property="templateid" />'><bean:write name="plan_bean" property="templatename" /></a>
			</template:formTr>
			<template:formSubmit>
				<td>
					<html:button property="action" styleClass="button"
						onclick="addGoBack()">����	</html:button>
				</td>
			</template:formSubmit>
		</template:formTable>
	</body>
</html>
