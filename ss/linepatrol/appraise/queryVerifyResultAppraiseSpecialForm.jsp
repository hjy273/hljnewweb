<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<title>��άȷ�Ͻ����ѯ</title>
	<script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
	<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
	</head>
	<script type="text/javascript">
		function view(id){
			window.location.href="${ctx}/appraiseSpecialAction.do?method=viewAppraiseMonth&flag=view&resultId="+id;
		}
	function viewMark(mark){
	var actionUrl="/WebApp/linepatrol/appraise/mark.jsp?mark="+mark;
	viewMarkWin = new Ext.Window({
		layout : 'fit',
		width:450,height:200, 
		resizable:true,
		closeAction : 'close', 
		modal:false,
		autoScroll:true,
		autoLoad:{url: actionUrl,scripts:true}, 
		plain: false,
		title:"���ⷴ����ע" 
	});
	viewMarkWin.show(Ext.getBody());
}
function closeMark(){
	viewMarkWin.close();
}
function eidt(id) {
		window.location.href="${ctx}/appraiseSpecialAction.do?method=editAppraise&flag=edit&resultId="+id;
	}
	</script>
	<body>
		<template:titile value="��άȷ�Ͻ����ѯ" />
			<html:form action="/appraiseSpecialAction?method=queryVerifyResult" styleId="form">
			<template:formTable>
				<template:formTr name="��������">
					<html:text property="startDate" style="Wdate;width:135px" styleClass="inputtext required" styleId="startDate" onfocus="WdatePicker({dateFmt:'yyyy'})"></html:text>
					--
					<html:text property="endDate" style="Wdate;width:135px" styleClass="inputtext required" styleId="endDate" onfocus="WdatePicker({dateFmt:'yyyy',maxDate:'%y-%M',minDate:'#F{$dp.$D(\'startDate\')}'})"></html:text><font color="red">*</font>
				</template:formTr>
				</template:formTable>
				<div align="center"><input type="submit" value="��ѯ" class="button"/></div>
			</html:form>
		<c:if test="${appraiseResults != null}">
			<display:table name="sessionScope.appraiseResults" id="appraiseResults" pagesize="18" export="false" defaultsort="1" defaultorder="descending" sort="list"	>
				<logic:notEmpty name="appraiseResults">
				<display:column media="html" title="��ά��˾" sortable="true" >
					 	<c:out value="${contractor[appraiseResults.contractorId]}"></c:out>
					 </display:column>
				<display:column property="startDate" sortable="true" format="{0,date,yyyy-MM-dd}"
					title="���˿�ʼʱ��" />
				<display:column property="endDate" sortable="true" format="{0,date,yyyy-MM-dd}"
					title="���˽���ʱ��" />
				<display:column property="result" sortable="true" title="���˽��"
					 maxLength="18" />
				<display:column property="appraiser" title="������"
					 sortable="true" />
				<display:column media="html" title="ȷ�Ͻ��" sortable="true" >
					<c:if test="${appraiseResults.confirmResult eq '2'}">ȷ��ͨ��</c:if>
					<c:if test="${appraiseResults.confirmResult eq '3'}">ȷ�ϲ�ͨ��</c:if>
			</display:column>
			<display:column media="html" title="����">
					<a href="javascript:view('${appraiseResults.id}');">�鿴</a>
				</display:column>
				</logic:notEmpty>
			</display:table>
			<table border="0" cellpadding="0" cellspacing="0" width="100%"
				style="border: 0px">
				<tr>
					<td style="text-align: center;">
						<input name="action" class="button" value="����" onclick="history.back();"
							type="button" />
					</td>
				</tr>
			</table>
		</c:if>
	</body>
	<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
</html>
