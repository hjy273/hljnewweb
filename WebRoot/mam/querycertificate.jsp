<%@ page language="java" contentType="text/html; charset=GBK"%><%@include
	file="/common/header.jsp"%>

<jsp:useBean id="selectForTag"
	class="com.cabletech.commons.tags.SelectForTag" scope="request" />


<html>
	<head>
		<script type="" language="javascript">
        function addGoBack()
        {
            try{
                location.href = "${ctx}/CertificateAction.do?method=showCertificate";
                return true;
            }
            catch(e){
                alert(e);
            }
        }
    </script>
		<title>��������ѯ��ά������Ϣ</title>
	</head>
	<body>
		<template:titile value="�������Ҵ�ά������Ϣ" />
		<html:form action="/CertificateAction?method=doQuery"
			styleId="queryForm2">
			<template:formTable namewidth="200" contentwidth="200">
				<template:formTr name="֤����">
					<html:text property="certificatecode" styleClass="inputtext"
						style="width:180;" maxlength="20" />
				</template:formTr>
				<template:formTr name="֤������">
					<html:text property="certificatename" styleClass="inputtext"
						style="width:180;" maxlength="25" />
				</template:formTr>
				<template:formTr name="֤������">
					<apptag:quickLoadList name="certificatetype" listName="DWZZ"
						type="select" isRegion="false" />
				</template:formTr>
				<logic:equal value="1" name="LOGIN_USER" property="deptype">
					<template:formTr name="��ά��λ">
						<apptag:setSelectOptions valueName="deptCollection"
							tableName="contractorinfo" columnName1="contractorname"
							columnName2="contractorid" region="true" />
						<html:select property="objectid" styleClass="inputtext"
							style="width:200" alt="��ά��λ">
							<html:option value="">����</html:option>
							<html:options collection="deptCollection" property="value"
								labelProperty="label" />
						</html:select>
						<input type="hidden" name="objecttype" value="CONTRACTORINFO" />
					</template:formTr>
				</logic:equal>
				<logic:equal value="2" name="LOGIN_USER" property="deptype">
					<template:formTr name="��ά��λ">
						<select name="objectid" Class="inputtext" style="width: 200">
							<option  value="${LOGIN_USER.deptID }">
								${LOGIN_USER.deptName }
							</option>
						</select>
						<input type="hidden" name="objecttype" value="CONTRACTORINFO" />
					</template:formTr>
				</logic:equal>
				<template:formSubmit>
					<td>
						<html:submit property="action" styleClass="button">����</html:submit>
					</td>
					<td>
						<html:reset property="action" styleClass="button">ȡ��</html:reset>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="addGoBack()">����</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>


