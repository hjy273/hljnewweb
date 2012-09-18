<%@ include file="/common/header.jsp"%>
<html>
	<script language="javascript" type="text/javascript">
	function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
	function isValidForm() {
		var regx=/^[0-9]*$/;
		if(document.UserLinkInfoBean.orderNumberStr.value ==""||document.UserLinkInfoBean.orderNumberStr.value.trim()==""){
			alert("��ʾ��Ų���Ϊ��!! ");
			return false;
		}
		if(document.UserLinkInfoBean.linkName.value ==""||document.UserLinkInfoBean.linkName.value.trim()==""){
			alert("�������Ʋ���Ϊ��!! ");
			return false;
		}
		if(document.UserLinkInfoBean.linkAddress.value ==""||document.UserLinkInfoBean.linkAddress.value.trim()==""){
			alert("���ӵ�ַ����Ϊ��!! ");
			return false;
		}
		if(!regx.test(document.UserLinkInfoBean.orderNumberStr.value)){
			alert("��ʾ��ű���Ϊ����!! ");
			return false;
		}
		return true;
	}
	//-->
</script>
	<script type="text/javascript" src="${ctx}/js/ckeditor/ckeditor.js"></script>
	<body>
		<c:if test="${link_type=='0' }">
			<template:titile value="�޸Ĺ���������Ϣ" />
		</c:if>
		<c:if test="${link_type=='1' }">
			<template:titile value="�޸ĳ���������Ϣ" />
		</c:if>
		<html:form action="/user_link.do?method=updateUserLink" method="post"
			onsubmit="return isValidForm();">
			<template:formTable>
				<template:formTr name="��ʾ���" isOdd="false">
					<html:hidden property="id" name="user_link_info_bean" />
					<html:text property="orderNumberStr" name="user_link_info_bean"
						styleClass="inputtext" style="width:300" />&nbsp;&nbsp;<font
						color="red">*</font>
				</template:formTr>
				<template:formTr name="��������" isOdd="true">
					<html:text property="linkName" name="user_link_info_bean"
						styleClass="inputtext" style="width:300" />&nbsp;&nbsp;<font
						color="red">*</font>
				</template:formTr>
				<template:formTr name="���ӵ�ַ" isOdd="true">
					<html:text name="user_link_info_bean" property="linkAddress" styleClass="inputtext"
						style="width:300" />&nbsp;&nbsp;<font color="red">*</font>
					<input name="linkType" value="${user_link_info_bean.linkType }"
						type="hidden" />
				</template:formTr>
				<template:formSubmit>
					<td colspan="text-align:center">
						<html:submit property="action2" styleClass="button">����</html:submit>
						<html:reset styleClass="button">����</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
