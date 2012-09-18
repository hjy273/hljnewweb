<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<body>
	<template:titile value="查看一级菜单"/>
	<html:form styleId="form" method="Post" action="/MenuManageAction.do">
		<template:formTable contentwidth="300" namewidth="220">
			<template:formTr name="菜单id">
				${MenuBean.id}
			</template:formTr>
			<template:formTr name="菜单名称">
				${MenuBean.lablename}
			</template:formTr>
			<template:formTr name="业务类型">
				<html:select property="businessType" styleClass="inputtext" style="width:220" disabled="true">
		            <html:options collection="resource" property="value" labelProperty="label"/>
		        </html:select>
			</template:formTr>
			<template:formTr name="序号">
				${MenuBean.showno}
			</template:formTr>
			<template:formTr name="图片路径">
				${MenuBean.imgurl}
			</template:formTr>
			<template:formTr name="备注">
				${MenuBean.remark}
			</template:formTr>
			<template:formSubmit>
		      	  	<input type="button" class="button" onclick="history.back()" value="返回" >
		    </template:formSubmit>
		</template:formTable>
	</html:form>
</body>