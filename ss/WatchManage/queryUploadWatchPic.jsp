<%@include file="/common/header.jsp"%>
<html>
<head>

</head>
<body>
<template:titile value="��Ӷ���ͼͼƬ��Ϣ" />
<html:form action="/WatchPicAction?method=addWatchPic"  onsubmit="return validate(this);" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<template:formTr name="����">
<apptag:Attachment state="add" fileIdList=""></apptag:Attachment>
</template:formTr>

<template:formSubmit>
	<td>
		<html:button property="action" styleClass="button" onclick="addRow()">��Ӹ���</html:button>
	</td>
	<td>
		<html:submit styleClass="button" >�ϴ�</html:submit>
	</td>
	<td>
		<html:reset styleClass="button">ȡ��	</html:reset>
	</td>
	<td>
		<html:button property="action" styleClass="button" onclick="addGoBack()">����</html:button>
	</td>
</template:formSubmit>
</template:formTable>
</html:form>
</body>
<script type="text/javascript">
function validate(theForm){
		if(fileNum == 0 ){
			alert("��������Ϊ�գ�");
			return false;
		}
	}
</script>
</html>