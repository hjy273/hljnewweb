<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<body>
	<template:titile value="�鿴�����˵�"/>
	<html:form styleId="form" method="Post" action="/MenuManageAction.do">
		<template:formTable contentwidth="300" namewidth="220">
			<template:formTr name="�˵�id">
				${MenuBean.id}
			</template:formTr>
			<template:formTr name="�˵�����">
				${MenuBean.lablename}
			</template:formTr>
			<template:formTr name="��һ���˵�">
				<html:select property="parentid" styleClass="inputtext" style="width:220" disabled="true">
		            <html:options collection="parentList" property="value" labelProperty="label"/>
		        </html:select>
			</template:formTr>
			<template:formTr name="��ַ">
				${MenuBean.hrefurl}
			</template:formTr>
			<template:formTr name="�Ƿ����">
				<html:select property="type" styleClass="inputtext" style="width:220" disabled="true">
		            <html:options collection="typeList" property="value" labelProperty="label"/>
		        </html:select>
			</template:formTr>
			<template:formTr name="Ȩ��">
				${MenuBean.power}
			</template:formTr>
			<template:formTr name="��ע">
				${MenuBean.remark}
			</template:formTr>
			<template:formSubmit>
		      	  	<input type="button" class="button" onclick="history.back()" value="����" >
		    </template:formSubmit>
		</template:formTable>
	</html:form>
</body>