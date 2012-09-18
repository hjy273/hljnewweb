<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<script language=javascript src="${ctx}/js/validation/prototype.js" type=""></script>
<script language=javascript src="${ctx}/js/validation/effects.js" type=""></script>
<script language=javascript src="${ctx}/js/validation/validation_cn.js" type=""></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
<head>
<script language="javascript" type="">

</script>
</head>
<body>
	<template:titile value="编辑基站信息"/>
	<s:form action="stationAction_update" name="edit" method="post">
		<template:formTable contentwidth="300" namewidth="220">
			<c:if test="${LOGIN_USER.deptype!='1'}">
			<template:formTr name="基站坐标点">
			<s:select list="%{#request.pointList}" name="station.pointId" value="%{#request.station.pointId}" cssClass="inputtext" cssStyle="width:220"></s:select>
			</template:formTr>
			</c:if>
			<c:if test="${LOGIN_USER.deptype=='1'}">
			<input type="hidden" name="station.pointId" value="${station.pointId}"/>
			</c:if>
			<template:formTr name="基站编号">
				<input type="hidden" name="stataion.tid" value="${id}"/>
				<input type="text" name="station.code" readonly="readonly" class="inputtext required" value="${station.code }" style="width:220" maxlength="40"/>
			</template:formTr>
			<template:formTr name="基站名称">
				<input type="text" name="station.stationname" class="inputtext required" value="${station.stationname }" style="width:220" maxlength="40"/>
			</template:formTr>
			<template:formTr name="基站类型">
				<s:select list="%{#request.stationTypeList}" name="station.stationtype" value="%{#request.station.ststiontype}"  cssClass="inputtext" cssStyle="width:220">
				</s:select>
			</template:formTr>
			<template:formTr name="基站级别">
			<s:select list="%{#request.stationLevelList}" name="station.stationlevel" value="%{#request.station.ststionlevel}" cssClass="inputtext" cssStyle="width:220"></s:select>
			</template:formTr>
			<template:formTr name="地址">
				<input type="text" name="station.pos" value="${station.pos}" class="inputtext required" style="width:220" maxlength="40"/>
			</template:formTr>
			<template:formTr name="开通日期">
				<input name="station.opendate" value="${station.opendate }" readOnly class="date required" style="width:220" maxlength="40" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			</template:formTr>
			<c:if test="${LOGIN_USER.deptype!='1'}">
			<template:formTr name="巡检组">
				<input type="hidden" name="sc.tid" value="${sc.tid}"/>
				<input type="hidden" name="sc.scId" value="${sc.scId}"/>
				<s:select list="%{#request.patrolgroups}" name="sc.pgId"  value="%{#request.sc.pgId}" cssClass="inputtext" cssStyle="width:220"></s:select>
			</template:formTr>
			</c:if>
			<template:formSubmit>
		      	<td>
		      		<input type="submit" class="button" value="更新">
		      	</td>
		      	<td>
		      	  	<input type="button" class="button" onclick="history.back()" value="返回" >
		      	</td>
		    </template:formSubmit>
		</template:formTable>
		<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('edit', {immediate : true, onFormValidate : formCallback});
		</script>
	</s:form>
</body>
