<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
	<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	
	<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
	
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
	
	<script type='text/javascript'>
		var win;
		function showWin(url){
			win = new Ext.Window({
				layout : 'fit',
				width:500,
				height:300, 
				resizable:true,
				closeAction : 'close',
				modal:true,
				html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
				plain: true
			});
			win.show(Ext.getBody());
		}
		function close(){
			initOptions();
			win.close();
		}
		function addType(){
			showWin('${ctx}/linepatrol/resource/datumTypeAdd.jsp');
		}		    
		function more(){
			window.location.href = '${ctx}/linepatrol/resource/fileDetail.jsp';
		}
	</script>
</head>
<body onload="changeStyle();">
	<template:titile value="更新维护资料" />
	<template:formTable>
		<html:form action="/datumAction.do?method=update" styleId="form">
			<html:hidden property="id" value="${datumInfo.id}"/>
			<template:formTr name="分类">
				<apptag:assorciateAttr table="datum_type" label="name" key="id" keyValue="${datumInfo.typeId}" />
			</template:formTr>
			<template:formTr name="名称">
				${datumInfo.name}
			</template:formTr>
			<template:formTr name="说明">
				<html:textarea property="info" styleClass="inputtext required" style="width:250px;height:50px" value="${datumInfo.info}"/>
			</template:formTr>
			<template:formTr name="资料历史">
				<c:choose>
					<c:when test="${empty addOnes}">
						无历史资料
					</c:when>
					<c:otherwise>
						<table width="100%">
							<tr>
								<td align="center"><b>名称</b></td>
								<td align="center"><b>更新人</b></td>
								<td align="center"><b>更新日期</b></td>
								<td align="center"><b>状态</b></td>
							</tr>
							<c:forEach var="f" items="${addOnes}" varStatus="status">
								<c:if test="${status.index < 3}">
									<tr>
										<td align="center"><a href="${ctx}/downloadAction.do?fileid=${f.FILEID}">${f.ORIGINALNAME}</a></td>
										<td align="center">
											<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="${f.UPLOADER}" />
										</td>
										<td align="center">
											<bean:write name="f" property="ONCREATE" format="yyyy/MM/dd" />
										</td>
										<td align="center">
											<c:choose>
												<c:when test="${f.IS_USABLE eq '1'}">
													入库
												</c:when>
												<c:otherwise>
													新增
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</c:if>
							</c:forEach>
								<tr>
									<td colspan=3></td>
									<td align="right"><a href="javascript:more();">更多..</a></td>
								</tr>
						</table>
					</c:otherwise>
				</c:choose>
			</template:formTr>
			<template:formTr name="资料">
				<apptag:upload state="edit" entityId="${datumInfo.id}" cssClass="" entityType="LP_DATUM" useable=""/>
			</template:formTr>
			<template:formSubmit>
				<td>
					<html:submit property="action" styleClass="button">提交</html:submit>
				</td>
				<td>
					<input type="button" class="button" value="返回" onclick="history.back()" />
				</td>
			</template:formSubmit>
		</html:form>
	</template:formTable>
	<script type="text/javascript">
		function formCallback(result, form) {
			window.status = "valiation callback for form '" + form.id + "': result = " + result;
		}

		var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
</body>
</html>