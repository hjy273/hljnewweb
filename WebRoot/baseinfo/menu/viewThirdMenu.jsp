<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<body>
	<template:titile value="查看三级菜单"/>
	<html:form styleId="form" method="Post" action="/MenuManageAction.do">
		<template:formTable contentwidth="300" namewidth="220">
			<template:formTr name="菜单id">
				${MenuBean.id}
			</template:formTr>
			<template:formTr name="菜单名称">
				${MenuBean.lablename}
			</template:formTr>
			<template:formTr name="上一级菜单">
				<html:select property="parentid" styleClass="inputtext" style="width:220" disabled="true">
		            <html:options collection="parentList" property="value" labelProperty="label"/>
		        </html:select>
			</template:formTr>
			<template:formTr name="地址">
				${MenuBean.hrefurl}
			</template:formTr>
			<template:formTr name="是否可用">
				<html:select property="type" styleClass="inputtext" style="width:220" disabled="true">
		            <html:options collection="typeList" property="value" labelProperty="label"/>
		        </html:select>
			</template:formTr>
			<template:formTr name="权限">
				${MenuBean.power}
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