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

		<title>��ά��Ա֤����ϸ��Ϣ</title>
	</head>
	<body>
		<logic:present name="certificateinfo">
			<template:titile value="��ά��Ա֤����Ϣ" />
			<br />
			<template:formTable namewidth="150" contentwidth="300" th2="����">
				<template:formTr name="��ά��λ">
					<bean:write name="certificateinfo" property="contractorname" />
				</template:formTr>
				<template:formTr name="��ά��Ա">
					<bean:write name="certificateinfo" property="contractorpersonname" />
				</template:formTr>
				<template:formTr name="֤����">
					<bean:write name="certificateinfo" property="certificatecode" />
				</template:formTr>
				<template:formTr name="֤������">
					<bean:write name="certificateinfo" property="certificatename" />
				</template:formTr>
				<template:formTr name="��֤����">
					<bean:write name="certificateinfo"
						property="licenceissuingauthority" />
				</template:formTr>
				<template:formTr name="֤������">
					<bean:write name="certificateinfo" property="certificatetypename" />
				</template:formTr>
				<template:formTr name="��֤����">
					<bean:write name="certificateinfo" property="issauthoritydate"
						format="yyyy/MM/dd" />
				</template:formTr>
				<template:formTr name="��Ч��">
					<bean:write name="certificateinfo" property="validperiod" format="yyyy/MM/dd" />
				</template:formTr>
				<template:formTr name="����">
					<bean:define name="certificateinfo" property="id" id="cid" />
					<apptag:upload state="look" entityId="${cid}"
						entityType="CERTIFICATE"></apptag:upload>
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:button property="action" styleClass="button"
							onclick="addGoBack()">����	</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
		</logic:present>
	</body>
</html>


