<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>

<html>
<head>
	<script type="" language="javascript">
        function addGoBack()
        {
            try{
                location.href = "${ctx}/ConPersonAction.do?method=showConPerson";
                return true;
            }
            catch(e){
                alert(e);
            }
        }
    </script>

<title>��Ա��ϸ��Ϣ</title>
</head>
	<body>
		<logic:present name="personinfo">
			<template:titile value="��Ա��ϸ��Ϣ" />
			<br />
			<template:formTable th2="����">
				<template:formTr name="��������">
					<%=session.getAttribute("LOGIN_USER_REGION_NAME")%>
				</template:formTr>
				<template:formTr name="�ϼ���λ">
					<bean:write name="personinfo" property="contractorname" />
				</template:formTr>
				<template:formTr name="����">
					<bean:write name="personinfo" property="name" />
				</template:formTr>
				<template:formTr name="�Ա�">
					<bean:write name="personinfo" property="sex" />
				</template:formTr>
				<template:formTr name="�Ļ��̶�">
					<bean:write name="personinfo" property="culture" />
				</template:formTr>
				<template:formTr name="�̶��绰">
					<bean:write name="personinfo" property="phone" />
				</template:formTr>
				<template:formTr name="�ֻ�����">
					<bean:write name="personinfo" property="mobile" />
				</template:formTr>
				<template:formTr name="��ͥסַ">
					<bean:write name="personinfo" property="familyaddress" />
				</template:formTr>
				<template:formTr name="��������">
					<bean:write name="personinfo" property="postalcode" />
				</template:formTr>
				<template:formTr name="ְ��">
					<bean:write name="personinfo" property="jobinfo" />
				</template:formTr>
				<template:formTr name="���֤">
					<bean:write name="personinfo" property="identitycard" />
				</template:formTr>
				<template:formTr name="�Ƿ���ܶ���">
					<c:if test="${personinfo.issendsms=='1'}">
						��
					</c:if>
					<c:if test="${personinfo.issendsms!='1'}">
						��
					</c:if>
				</template:formTr>
				<template:formTr name="��פ����">
					<bean:write name="personinfo" property="residantarea" />
				</template:formTr>
				<template:formTr name="��ְʱ��">
					<bean:write name="personinfo" property="enterTime" format="yyyy/MM/dd HH:mm:ss"/>
				</template:formTr>
				<template:formTr name="��λְ��">
					<bean:write name="personinfo" property="postOffice" />
				</template:formTr>
				<template:formTr name="��������">
					<bean:write name="personinfo" property="workrecord" />
				</template:formTr>
				<template:formTr name="��ע��Ϣ">
					<bean:write name="personinfo" property="remark" />
				</template:formTr>

				<template:formSubmit>
					<td>
						<html:button property="action" styleClass="button" onclick="addGoBack()">����</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
		</logic:present>
	</body>
</html>


