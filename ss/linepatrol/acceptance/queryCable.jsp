<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title></title>

		<script language="javascript" src="${ctx}/js/validation/prototype.js"
			type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js"
			type=""></script>
		<script language="javascript"
			src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css"
			rel="stylesheet" type="text/css">

		<script type='text/javascript'
			src='${ctx}/linepatrol/js/change_style.js'></script>

		<script type="text/javascript">
		function check(){
			if( ($('begintime').value == '' && $('endtime').value != '') ||
				($('begintime').value != '' && $('endtime').value == '') ){
				alert("日期段必须同时有开始日期和结束日期");
				return false;
			}
			if( ($('begintime').value != '' && $('endtime').value != '')
					&& ($('begintime').value == $('endtime').value) ){
				alert('开始日期和结束日期不能相等');
				return false;
			}
			return true;
		}
	</script>
	</head>
	<body onload="changeStyle()">
		<template:titile value="中继段查询" />
		<!-- 查询页面 -->
		<html:form action="/acceptanceQueryAction.do?method=queryFromCable"
			styleId="form" onsubmit="return check()">
			<template:formTable namewidth="150" contentwidth="400">
				<template:formTr name="中继段名称">
					<html:text property="name" styleClass="inputtext"
						style="width:120px" />
				</template:formTr>
				<template:formTr name="验收次数">
					<html:text property="times"
						styleClass="inputtext validate-int-range-1-100"
						style="width:120px" />
				</template:formTr>
				<template:formTr name="验收日期">
					<html:text property="begintime" styleClass="Wdate"
						style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})" /> 至
				<html:text property="endtime" styleClass="Wdate" style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:$('begintime')})" />
				</template:formTr>
				<template:formTr name="交维情况">
					<input type="radio" name="passed" value="" checked>全部
			    <input type="radio" name="passed" value="y">已交维
			    <input type="radio" name="passed" value="n">未交维
			</template:formTr>
				<template:formTr name="交资情况">
					<input type="radio" name="currentState" value="" checked>全部
			    <input type="radio" name="currentState" value="y">已交资
			    <input type="radio" name="currentState" value="n">未交资
			</template:formTr>
			</template:formTable>
			<div align="center">
				<input name="isQuery" value="true" type="hidden" />
				<html:submit property="action" styleClass="button">提交</html:submit>
			</div>
		</html:form>
		<script type="text/javascript">
		function formCallback(result, form) {
			window.status = "valiation callback for form '" + form.id + "': result = " + result;
		}

		var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
		<template:displayHide styleId="form"></template:displayHide>
		<script type="text/javascript" language="javascript">
		 	view=function(idValue){
         		window.location.href = "${ctx}/resAction.do?method=load&type=view&id="+idValue;
		 	}
		 	edit=function(idValue){
				window.location.href="${ctx}/resAction.do?method=load&type=edit&id="+idValue;
		 	}
		 	function excel(){
				window.location.href = '${ctx}/acceptanceAction.do?method=excel&type=session';
			}
		</script>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<logic:notEmpty name="result">
			<display:table name="sessionScope.result" id="currentRowObject"
				pagesize="15">
				<display:column media="html" title="中继段编号" sortable="true">
					<a href="javascript:view('${currentRowObject.kid}')">${currentRowObject.segmentid}</a>
				</display:column>
				<display:column property="assetno" title="资产编号" sortable="true" />
				<display:column property="segmentname" title="中继段名称" maxLength="10"
					sortable="true" />
				<display:column property="fiberType" title="纤芯型号" maxLength="10"
					sortable="true" />
				<display:column media="html" title="光缆级别" sortable="true">
					<c:out value="${cabletype[currentRowObject.cableLevel]}" />
				</display:column>
				<display:column media="html" title="产权属性" sortable="true">
					<c:out value="${property_right[currentRowObject.owner]}" />
				</display:column>
				<display:column media="html" sortable="true" title="敷设方式">
					<c:forEach var="s"
						items="${fn:split(currentRowObject.laytype,',')}">
					${layingmethod[s]} 
				</c:forEach>
				</display:column>
				<display:column property="grossLength" title="光缆长度"
					headerClass="subject" sortable="true" />
				<display:column property="producer" title="厂家" sortable="true" />
				<display:column media="html" title="操作">
					<a href="javascript:view('${currentRowObject.kid}')">查看</a>
				</display:column>
			</display:table>
			<table class="tdlist">
				<tr>
					<td class="tdlist">
						<input type="button" class="button" value="导出" onclick="excel()" />
						<input type="button" value="返回" class="button"
							onclick="javascript:history.back()">
					</td>
				</tr>
			</table>
		</logic:notEmpty>
	</body>
</html>