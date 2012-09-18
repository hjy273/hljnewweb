<%@include file="/common/header.jsp"%>
<html>
<head>

</head>
<body>
<template:titile value="添加盯防图图片信息" />
<html:form action="/WatchPicAction?method=addWatchPic"  onsubmit="return validate(this);" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<template:formTr name="附件">
<apptag:Attachment state="add" fileIdList=""></apptag:Attachment>
</template:formTr>

<template:formSubmit>
	<td>
		<html:button property="action" styleClass="button" onclick="addRow()">添加附件</html:button>
	</td>
	<td>
		<html:submit styleClass="button" >上传</html:submit>
	</td>
	<td>
		<html:reset styleClass="button">取消	</html:reset>
	</td>
	<td>
		<html:button property="action" styleClass="button" onclick="addGoBack()">返回</html:button>
	</td>
</template:formSubmit>
</template:formTable>
</html:form>
</body>
<script type="text/javascript">
function validate(theForm){
		if(fileNum == 0 ){
			alert("附件不能为空！");
			return false;
		}
	}
</script>
</html>