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
		<title>按条件查询代维资质信息</title>
	</head>
	<body>
		<template:titile value="条件查找代维资质信息" />
		<html:form action="/CertificateAction?method=doQuery"
			styleId="queryForm2">
			<template:formTable namewidth="200" contentwidth="200">
				<template:formTr name="证书编号">
					<html:text property="certificatecode" styleClass="inputtext"
						style="width:180;" maxlength="20" />
				</template:formTr>
				<template:formTr name="证书名称">
					<html:text property="certificatename" styleClass="inputtext"
						style="width:180;" maxlength="25" />
				</template:formTr>
				<template:formTr name="证书类型">
					<apptag:quickLoadList name="certificatetype" listName="DWZZ"
						type="select" isRegion="false" />
				</template:formTr>
				<logic:equal value="1" name="LOGIN_USER" property="deptype">
					<template:formTr name="代维单位">
						<apptag:setSelectOptions valueName="deptCollection"
							tableName="contractorinfo" columnName1="contractorname"
							columnName2="contractorid" region="true" />
						<html:select property="objectid" styleClass="inputtext"
							style="width:200" alt="代维单位">
							<html:option value="">所有</html:option>
							<html:options collection="deptCollection" property="value"
								labelProperty="label" />
						</html:select>
						<input type="hidden" name="objecttype" value="CONTRACTORINFO" />
					</template:formTr>
				</logic:equal>
				<logic:equal value="2" name="LOGIN_USER" property="deptype">
					<template:formTr name="代维单位">
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
						<html:submit property="action" styleClass="button">查找</html:submit>
					</td>
					<td>
						<html:reset property="action" styleClass="button">取消</html:reset>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="addGoBack()">返回</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>


