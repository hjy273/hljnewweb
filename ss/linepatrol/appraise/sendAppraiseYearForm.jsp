<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>�·����˽��</title>
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
		<link type="text/css" rel="stylesheet" href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	</head>
	<script type="text/javascript">
		function view(id){
			window.location.href="${ctx}/appraiseYearAction.do?method=viewAppraiseYear&id="+id;
		}	
		function checked(){
			for(i=0;i<document.getElementsByName('check').length;i++){         
			if(document.getElementsByName('check')(i).checked){         
			return true;         
			}
		}
		alert("��ѡ��Ҫ�·�������");
		return false; 
		}
	function checkAll() {
	var checkFlag=document.getElementById("checkall").checked;
	var inputs = document.getElementsByName("check");����
    if (checkFlag) {����������������
        for (var i = 0; i < inputs.length; i++) {��������������������
            if (inputs[i].type == "checkbox" && inputs[i].id != "CheckAll") {
                inputs[i].checked = true;��������������������
            }����������������
        }
        checkFlag = false;������������
    } else {����������������
        for (var i = 0; i < inputs.length; i++) {��������������������
            if (inputs[i].type == "checkbox" && inputs[i].id != "CheckAll") {
                inputs[i].checked = false;��������������������
            }����������������
        }
    }��������
}
	</script>
	<body>
		<template:titile value="�·����˽��" />
		<html:form action="/appraiseYearAction.do?method=querySendAppraise" styleId="form">
			<template:formTable>
				<template:formTr name="�������">
					<html:text property="appraiseTime" style="Wdate;width:140px;" styleId="appraiseTime" styleClass="inputtext required" onfocus="WdatePicker({dateFmt:'yyyy'})"></html:text><font color="red">*</font>
				</template:formTr>
				</template:formTable>
			<div align="center"><input type="submit" value="��ѯ" class="button"/></div>
		</html:form>
		<html:form action="/appraiseYearAction.do?method=sendAppraise" onsubmit="return checked();">
			<display:table name="sessionScope.appraiseYearResults" id="appraiseYearResults" pagesize="18" export="false" defaultsort="1" defaultorder="descending" sort="list"	>
				<logic:notEmpty name="appraiseYearResults">
				<display:column title="<input type='checkbox' name='checkall' onclick='checkAll();'/>" media="html">
				<div align="center"><input type="checkbox" name="check" value="${appraiseYearResults.id }" /></div>
				</display:column>
				<display:column media="html" title="��ά��˾" sortable="true" >
					 	<c:out value="${consMap[appraiseYearResults.contractorId]}"></c:out>
					 </display:column>
				<display:column property="contractNO" title="��װ�"
					 maxLength="9" sortable="true" />
				<display:column property="result" sortable="true" title="���˽��"
					 maxLength="18" />
				<display:column media="html" sortable="true" property="year"
					title="�������" >
					</display:column>
				<display:column property="appraiser" title="������"
					 sortable="true" />
				<display:column media="html" title="����">
					<a href="javascript:view('${appraiseYearResults.id }')">�鿴</a>
				</display:column>
				</logic:notEmpty>
			</display:table>
			<div align="center">
			<html:submit value="�·�" styleClass="button"></html:submit>
		</div>
		</html:form>
		<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
	</body>
</html>
