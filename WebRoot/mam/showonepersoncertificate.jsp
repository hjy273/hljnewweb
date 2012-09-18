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
                location.href = "${ctx}/PersonCertificateAction.do?method=showCertificate";
                return true;
            }
            catch(e){
                alert(e);
            }
        }
    </script>

		<title>代维人员证书详细信息</title>
	</head>
	<body>
		<logic:present name="certificateinfo">
			<template:titile value="代维人员证书信息" />
			<br />
			<template:formTable namewidth="150" contentwidth="300" th2="内容">
				<template:formTr name="代维单位">
					<bean:write name="certificateinfo" property="contractorname" />
				</template:formTr>
				<template:formTr name="代维人员">
					<bean:write name="certificateinfo" property="contractorpersonname" />
				</template:formTr>
				<template:formTr name="证书编号">
					<bean:write name="certificateinfo" property="certificatecode" />
				</template:formTr>
				<template:formTr name="证书名称">
					<bean:write name="certificateinfo" property="certificatename" />
				</template:formTr>
				<template:formTr name="发证机关">
					<bean:write name="certificateinfo"
						property="licenceissuingauthority" />
				</template:formTr>
				<template:formTr name="证书类型">
					<bean:write name="certificateinfo" property="certificatetypename" />
				</template:formTr>
				<template:formTr name="发证日期">
					<bean:write name="certificateinfo" property="issauthoritydate"
						format="yyyy/MM/dd" />
				</template:formTr>
				<template:formTr name="有效期">
					<bean:write name="certificateinfo" property="validperiod" format="yyyy/MM/dd" />
				</template:formTr>
				<template:formTr name="附件">
					<bean:define name="certificateinfo" property="id" id="cid" />
					<apptag:upload state="look" entityId="${cid}"
						entityType="CERTIFICATE"></apptag:upload>
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:button property="action" styleClass="button"
							onclick="addGoBack()">返回	</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
		</logic:present>
	</body>
</html>


