<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<body>
	<template:titile value="�鿴һ���˵�"/>
	<html:form styleId="form" method="Post" action="/MenuManageAction.do">
		<template:formTable contentwidth="300" namewidth="220">
			<template:formTr name="�˵�id">
				${MenuBean.id}
			</template:formTr>
			<template:formTr name="�˵�����">
				${MenuBean.lablename}
			</template:formTr>
			<template:formTr name="ҵ������">
				<html:select property="businessType" styleClass="inputtext" style="width:220" disabled="true">
		            <html:options collection="resource" property="value" labelProperty="label"/>
		        </html:select>
			</template:formTr>
			<template:formTr name="���">
				${MenuBean.showno}
			</template:formTr>
			<template:formTr name="ͼƬ·��">
				${MenuBean.imgurl}
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