<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
	
	<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
	<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	<script type="text/javascript">

	</script>
	<script type="text/javascript">
		function view(id){
			var url = '${ctx}/overHaulAction.do?method=view&id='+id;
			window.location.href = url;
		}
		//ȡ������
		toCancelForm=function(overHaulId){
			var url;
			if(confirm("ȷ��Ҫȡ���ô�����Ŀ������")){
				url="${ctx}/overHaulAction.do?method=cancelOverHaulForm";
				var queryString="overhaul_id="+overHaulId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			}
		}
	</script>
</head>
<body onload="changeStyle()">
	<!-- ������ѯ��ʾ -->
	<template:titile value="���޲�ѯ" />
	<div style="text-align:center;">
		<iframe src="${ctx}/overHaulAction.do?method=processMap&&forward=query_overhaul_state&task_name=${task_names}"
			frameborder="0" id="processGraphFrame" height="70" scrolling="no" width="99%" ></iframe>
	</div>
	<html:form action="/overHaulQueryAction.do?method=result" styleId="form" style="display:">
		<template:formTable namewidth="150" contentwidth="350">
			<template:formTr name="������Ŀ����">
				<html:text property="projectName" styleId="projectName" styleClass="inputtext" style="width:100px"/>
			</template:formTr>
			<template:formTr name="��ά��λ">
				<c:if test="${deptype=='1'}">
					<html:select property="contractorId" styleId="contractorId" styleClass="inputtext" style="width:140px">
						<html:option value="">ȫ��</html:option>
						<html:options collection="list" property="contractorID" labelProperty="contractorName"></html:options>
					</html:select>
				</c:if>
				<c:if test="${deptype=='2'}">
					<input type="hidden" value="${sessionScope.conId }" name="contractorId" />
					<c:out value="${sessionScope.conName}"></c:out>
				</c:if>
			</template:formTr>
			<template:formTr name="�Ƿ�ȡ��">
				<html:select property="state" styleClass="inputtext" style="width:140px">
					<html:option value="">����</html:option>
					<html:option value="999">��</html:option>
					<html:option value="0">��</html:option>
				</html:select>
			</template:formTr>
			<template:formTr name="���ã�Ԫ��">
				<html:text property="minBudgetFee" styleId="minBudgetFee" styleClass="inputtext validate-integer-float-double" style="width:102px"/>
				 -- <html:text property="maxBudgetFee" styleId="maxBudgetFee" styleClass="inputtext validate-integer-float-double great-than-minBudgetFee" style="width:102px"/>
			</template:formTr>
			<template:formTr name="��������ʱ��">
				<html:text property="startTime" styleClass="Wdate" style="width:102px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" readonly="true"/>
				 -- <html:text property="endTime" styleClass="Wdate validate-date-after-startTime" style="width:102px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'startTime\')}'})" readonly="true"/>
			</template:formTr>
		</template:formTable>	
		<logic:present name="queryCondition" property="taskStates">
			<logic:iterate id="oneTaskState" name="queryCondition"
				property="taskStates">
				<input id="taskStates_<bean:write name="oneTaskState" />"
					type="hidden" name="taskStates"
					value="<bean:write name="oneTaskState" />" />
			</logic:iterate>
		</logic:present>
		<div align="center">
			<input name="isQuery" value="true" type="hidden"/>
			<html:submit property="action" styleClass="button">��ѯ</html:submit>
			<html:reset property="action" styleClass="button">����</html:reset>
		</div>	
	</html:form>
	<script type="text/javascript">
		function formCallback(result, form) {
			window.status = "valiation callback for form '" + form.id + "': result = " + result;
		}
		var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>

	<!-- ����/��ʾ -->
	<template:displayHide styleId="form"></template:displayHide>
	<logic:notEmpty name="result">
	<!-- ��ѯ�����ʾ -->
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<display:table name="sessionScope.result" id="row" pagesize="18" export="false" defaultsort="4" defaultorder="descending" sort="list">
		<display:setProperty name="sort.amount" value="list"/>
		<display:column property="projectName" title="��Ŀ����" sortable="true"/>
		<display:column property="projectCreator" title="������" sortable="true"/>
		<display:column property="budgetFee" title="Ԥ����ã�Ԫ��" sortable="true"/>
		<display:column property="useFee" title="Ŀǰ��Ŀʹ�÷��ã�Ԫ��" sortable="true"/>
		<display:column property="startTime" title="��ʼʱ��" sortable="true" format="{0,date,yyyy-MM-dd}"/>
		<display:column property="endTime" title="����ʱ��" sortable="true" format="{0,date,yyyy-MM-dd}"/>
		<display:column media="html" title="����">
			<a href="javascript:view('${row.id}')">�鿴</a>
			<c:if test="${sessionScope.LOGIN_USER.deptype=='1'&&row.state!='999'}">
						| <a href="javascript:toCancelForm('${row.id}')">ȡ��</a>
					</c:if>
		</display:column>
	</display:table>
	<table class="tdlist">
		<tr><td class="tdlist">
			<html:button property="action" styleClass="button" onclick="history.back()">����</html:button>
		</td></tr>
	</table>
	</logic:notEmpty>
</body>