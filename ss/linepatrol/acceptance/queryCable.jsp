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
				alert("���ڶα���ͬʱ�п�ʼ���ںͽ�������");
				return false;
			}
			if( ($('begintime').value != '' && $('endtime').value != '')
					&& ($('begintime').value == $('endtime').value) ){
				alert('��ʼ���ںͽ������ڲ������');
				return false;
			}
			return true;
		}
	</script>
	</head>
	<body onload="changeStyle()">
		<template:titile value="�м̶β�ѯ" />
		<!-- ��ѯҳ�� -->
		<html:form action="/acceptanceQueryAction.do?method=queryFromCable"
			styleId="form" onsubmit="return check()">
			<template:formTable namewidth="150" contentwidth="400">
				<template:formTr name="�м̶�����">
					<html:text property="name" styleClass="inputtext"
						style="width:120px" />
				</template:formTr>
				<template:formTr name="���մ���">
					<html:text property="times"
						styleClass="inputtext validate-int-range-1-100"
						style="width:120px" />
				</template:formTr>
				<template:formTr name="��������">
					<html:text property="begintime" styleClass="Wdate"
						style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})" /> ��
				<html:text property="endtime" styleClass="Wdate" style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:$('begintime')})" />
				</template:formTr>
				<template:formTr name="��ά���">
					<input type="radio" name="passed" value="" checked>ȫ��
			    <input type="radio" name="passed" value="y">�ѽ�ά
			    <input type="radio" name="passed" value="n">δ��ά
			</template:formTr>
				<template:formTr name="�������">
					<input type="radio" name="currentState" value="" checked>ȫ��
			    <input type="radio" name="currentState" value="y">�ѽ���
			    <input type="radio" name="currentState" value="n">δ����
			</template:formTr>
			</template:formTable>
			<div align="center">
				<input name="isQuery" value="true" type="hidden" />
				<html:submit property="action" styleClass="button">�ύ</html:submit>
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
				<display:column media="html" title="�м̶α��" sortable="true">
					<a href="javascript:view('${currentRowObject.kid}')">${currentRowObject.segmentid}</a>
				</display:column>
				<display:column property="assetno" title="�ʲ����" sortable="true" />
				<display:column property="segmentname" title="�м̶�����" maxLength="10"
					sortable="true" />
				<display:column property="fiberType" title="��о�ͺ�" maxLength="10"
					sortable="true" />
				<display:column media="html" title="���¼���" sortable="true">
					<c:out value="${cabletype[currentRowObject.cableLevel]}" />
				</display:column>
				<display:column media="html" title="��Ȩ����" sortable="true">
					<c:out value="${property_right[currentRowObject.owner]}" />
				</display:column>
				<display:column media="html" sortable="true" title="���跽ʽ">
					<c:forEach var="s"
						items="${fn:split(currentRowObject.laytype,',')}">
					${layingmethod[s]} 
				</c:forEach>
				</display:column>
				<display:column property="grossLength" title="���³���"
					headerClass="subject" sortable="true" />
				<display:column property="producer" title="����" sortable="true" />
				<display:column media="html" title="����">
					<a href="javascript:view('${currentRowObject.kid}')">�鿴</a>
				</display:column>
			</display:table>
			<table class="tdlist">
				<tr>
					<td class="tdlist">
						<input type="button" class="button" value="����" onclick="excel()" />
						<input type="button" value="����" class="button"
							onclick="javascript:history.back()">
					</td>
				</tr>
			</table>
		</logic:notEmpty>
	</body>
</html>