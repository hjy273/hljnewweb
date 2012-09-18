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

<title>人员详细信息</title>
</head>
	<body>
		<logic:present name="personinfo">
			<template:titile value="人员详细信息" />
			<br />
			<template:formTable th2="内容">
				<template:formTr name="所属区域">
					<%=session.getAttribute("LOGIN_USER_REGION_NAME")%>
				</template:formTr>
				<template:formTr name="上级单位">
					<bean:write name="personinfo" property="contractorname" />
				</template:formTr>
				<template:formTr name="姓名">
					<bean:write name="personinfo" property="name" />
				</template:formTr>
				<template:formTr name="性别">
					<bean:write name="personinfo" property="sex" />
				</template:formTr>
				<template:formTr name="文化程度">
					<bean:write name="personinfo" property="culture" />
				</template:formTr>
				<template:formTr name="固定电话">
					<bean:write name="personinfo" property="phone" />
				</template:formTr>
				<template:formTr name="手机号码">
					<bean:write name="personinfo" property="mobile" />
				</template:formTr>
				<template:formTr name="家庭住址">
					<bean:write name="personinfo" property="familyaddress" />
				</template:formTr>
				<template:formTr name="邮政编码">
					<bean:write name="personinfo" property="postalcode" />
				</template:formTr>
				<template:formTr name="职务">
					<bean:write name="personinfo" property="jobinfo" />
				</template:formTr>
				<template:formTr name="身份证">
					<bean:write name="personinfo" property="identitycard" />
				</template:formTr>
				<template:formTr name="是否接受短信">
					<c:if test="${personinfo.issendsms=='1'}">
						是
					</c:if>
					<c:if test="${personinfo.issendsms!='1'}">
						否
					</c:if>
				</template:formTr>
				<template:formTr name="常驻区域">
					<bean:write name="personinfo" property="residantarea" />
				</template:formTr>
				<template:formTr name="入职时间">
					<bean:write name="personinfo" property="enterTime" format="yyyy/MM/dd HH:mm:ss"/>
				</template:formTr>
				<template:formTr name="岗位职责">
					<bean:write name="personinfo" property="postOffice" />
				</template:formTr>
				<template:formTr name="工作经历">
					<bean:write name="personinfo" property="workrecord" />
				</template:formTr>
				<template:formTr name="备注信息">
					<bean:write name="personinfo" property="remark" />
				</template:formTr>

				<template:formSubmit>
					<td>
						<html:button property="action" styleClass="button" onclick="addGoBack()">返回</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
		</logic:present>
	</body>
</html>


