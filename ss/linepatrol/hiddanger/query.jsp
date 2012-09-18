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
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css"
			media="screen, print" />
		<link href="${ctx}/js/validation/styles/style_min.css"
			rel="stylesheet" type="text/css">
		<script type='text/javascript'
			src='${ctx}/linepatrol/js/change_style.js'></script>

		<script type="text/javascript">
		function getTroubleCode(typeId) {
			$("code").length = 1;
			var url = "${ctx}/hiddangerAction.do?method=getTroubleCodeSelection&typeId="
					+ typeId;
			var myAjax = new Ajax.Request(url, {
				method : 'get',
				asynchronous : 'true',
				onComplete : setTroubleCode
			});
		}
		function setTroubleCode(response) {
			var str = response.responseText;
			if (str == "")
				return true;
			var optionlist = str.split("&");
			for ( var i = 0; i < optionlist.length; i++) {
				var t = optionlist[i].split(",")[0];
				var v = optionlist[i].split(",")[1];
				$("code").options[i + 1] = new Option(v, t);
			}
		}
		</script>
	</head>
	<body
		onload="changeStyle();getTroubleCode($('type').options[$('type').selectedIndex].value)">
		<template:titile value="������ѯ" />
		<div style="text-align: center;">
			<iframe
				src="${ctx}/hiddangerAction.do?method=viewHideDangerProcess&&forward=query_hide_danger_state&&task_names=${task_names}"
				frameborder="0" id="processGraphFrame" height="100" scrolling="no"
				width="99%"></iframe>
		</div>
		<html:form action="/hiddangerQueryAction.do?method=query"
			styleId="form">
			<table id='formtable' width="90%" border="0" align="center"
				cellpadding="0" cellspacing="0" class="tabout">
				<tr>
					<td width="30%"></td>
					<td width="70%"></td>
				</tr>
				<template:formTr name="�Ǽ�����" style="text-align:right;">
					<html:text property="name" styleClass="inputtext" name="queryBean"
						style="width:140px" />
				</template:formTr>
				<template:formTr name="��������" style="text-align:right;">
					<apptag:setSelectOptions columnName1="typename" columnName2="id"
						tableName="troubletype" valueName="troubletype" />
					<html:select property="type" styleId="type" name="queryBean"
						styleClass="inputtext" style="width:140px"
						onchange="getTroubleCode(this.options[this.selectedIndex].value)">
						<html:option value="">����</html:option>
						<html:options collection="troubletype" property="value"
							labelProperty="label" />
					</html:select>
				</template:formTr>
				<template:formTr name="��������" style="text-align:right;">
					<html:select property="code" styleId="code" styleClass="inputtext"
						name="queryBean" style="width:140px">
						<html:option value="">����</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="��������λ" style="text-align:right;">
					<html:select property="treatDepartment" styleClass="inputtext"
						style="width:140px">
						<html:option value="">����</html:option>
						<html:options collection="dept" property="value"
							labelProperty="label" />
					</html:select>
				</template:formTr>
				<template:formTr name="�Ƿ�ȡ��" style="text-align:right;">
					<html:select property="hideDangerState" styleClass="inputtext"
						style="width:140px">
						<html:option value="">����</html:option>
						<html:option value="999">��</html:option>
						<html:option value="0">��</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="��������" style="text-align:right;">
					<html:multibox property="hiddangerLevels" value="1" />1��
					<html:multibox property="hiddangerLevels" value="2" />2��
					<html:multibox property="hiddangerLevels" value="3" />3��
					<html:multibox property="hiddangerLevels" value="4" />4��
					<html:multibox property="hiddangerLevels" value="0" />����
					<!-- 		<html:select property="hiddangerLevel" styleClass="inputtext" style="width:140px">
					<html:option value="">����</html:option>
					<html:option value="1">1��</html:option>
					<html:option value="2">2��</html:option>
					<html:option value="3">3��</html:option>
					<html:option value="4">4��</html:option>
					<html:option value="0">����</html:option>
				</html:select>
			-->
				</template:formTr>
				<template:formTr name="����״̬" style="text-align:right;">
					<html:multibox property="hiddangerStates" value="'10'" />�Ǽ�
					<html:multibox property="hiddangerStates" value="'20','30','40'" />����
					<html:multibox property="hiddangerStates" value="'50'" />�ϱ�
					<html:multibox property="hiddangerStates" value="'51'" />�ϱ����
					<html:multibox property="hiddangerStates" value="'52'" />�ƶ��ƻ�
					<html:multibox property="hiddangerStates" value="'60'" />�ƻ����
					<html:multibox property="hiddangerStates" value="'70'" />�ر�
					<html:multibox property="hiddangerStates" value="'00'" />�ر����
					<html:multibox property="hiddangerStates" value="'0'" />���
					<!-- 		<html:select property="hiddangerState" styleClass="inputtext" style="width:140px">
					<html:option value="">����</html:option>
					<html:option value="10">�Ǽ�</html:option>
					<html:option value="20,30,40">����</html:option>
					<html:option value="50">�ϱ�</html:option>
					<html:option value="51">�ϱ����</html:option>
					<html:option value="52">�ƶ��ƻ�</html:option>
					<html:option value="60">�ƻ����</html:option>
					<html:option value="70">�ر�</html:option>
					<html:option value="00">�ر����</html:option>
					<html:option value="0">���</html:option>
				</html:select>
				-->
				</template:formTr>
				<template:formTr name="�Ǽ�ʱ��" style="text-align:right;">
					<html:text property="begintime" styleClass="Wdate"
						styleId="begintime" name="queryBean" style="width:140px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endtime\')||\'%y-%M-%d\'}'})" /> ��
				<html:text property="endtime" styleId="endtime" styleClass="Wdate"
						style="width:140px" name="queryBean"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'#F{$dp.$D(\'begintime\')}',maxDate:'%y-%M-%d'})" />
				</template:formTr>
			</table>
			<logic:present name="queryCondition" property="taskStates">
				<logic:iterate id="oneTaskState" name="queryCondition"
					property="taskStates">
					<input id="taskStates_<bean:write name="oneTaskState" />"
						type="hidden" name="taskStates"
						value="<bean:write name="oneTaskState" />" />
				</logic:iterate>
			</logic:present>
			<div align="center">
				<input type="hidden" name="isQuery" value="true">
				<html:submit property="action" styleClass="button">��ѯ</html:submit>
				<html:reset property="action" styleClass="button">����</html:reset>
			</div>
		</html:form>
		<template:displayHide styleId="form" hide="����" formDisplay=""
			display="��������"></template:displayHide>

		<!-- ��ѯ��� -->
		<script type="text/javascript">
	function view(id) {
		window.location.href = '${ctx}/hiddangerQueryAction.do?method=view&id=' + id;
	}
	function viewPlan(id) {
		window.location.href = '${ctx}/specialplan.do?method=loadPlan&type=view&id=' + id;
	}
	function editPlan(id, hiddangerId) {
		window.location.href = '${ctx}/specialplan.do?method=loadPlan&type=edit&id='
				+ id + '&businessId=' + hiddangerId;
	}
	function editRegist(id) {
		window.location.href = '${ctx}/hiddangerAction.do?method=editRegistLink&id=' + id;
	}
	function editReport(id) {
		window.location.href = '${ctx}/hiddangerAction.do?method=editReportLink&id=' + id;
	}
	function editClose(id) {
		window.location.href = '${ctx}/hiddangerAction.do?method=editCloseLink&id=' + id;
	}
	function back() {
		window.location.href = "${ctx}/hiddangerQueryAction.do?method=queryLink";
	}
	toCancelForm=function(hideDangerId){
			var url;
			if(confirm("ȷ��Ҫȡ��������������")){
				url="${ctx}/hiddangerAction.do?method=cancelHideDangerForm";
				var queryString="hide_danger_id="+hideDangerId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			}
		}
</script>
		<logic:notEmpty name="result">
			<display:table name="sessionScope.result" id="row" pagesize="18"
				export="false" sort="list">
				<c:if test="${row.creater!=null}">
					<bean:define id="sendUserId" name="row" property="creater" />
				</c:if>
				<c:if test="${row.creater==null}">
					<bean:define id="sendUserId" value="" />
				</c:if>
				<bean:define id="applyState" name="row" property="hiddangerState"></bean:define>
				<display:column property="hiddangerNumber" title="�������"
					sortable="true" />
				<display:column property="createrDept" title="�Ǽǵ�λ" sortable="true" />
				<display:column property="name" title="�Ǽ�����" sortable="true" />
				<display:column media="html" title="����" sortable="true">
					<c:out value="${types[row.type]}" />
				</display:column>
				<display:column media="html" title="����" sortable="true">
					<c:out value="${codes[row.code]}" />
				</display:column>
				<display:column media="html" title="����ʱ��" sortable="true">
					<bean:write name="row" property="findTime"
						format="yy-MM-dd HH:mm:ss" />
				</display:column>
				<display:column property="reporter" title="������" maxLength="10"
					sortable="true" />
				<display:column media="html" title="����λ" sortable="true">
					<c:out value="${depts[row.treatDepartment]}" />
				</display:column>
				<display:column media="html" title="״̬" sortable="true">
					<c:if test="${row.hiddangerState eq '10'}">��Ҫ����</c:if>
					<c:if test="${row.hiddangerState eq '20'}">��Ҫ�ϱ�</c:if>
					<c:if test="${row.hiddangerState eq '30'}">��Ҫ�ϱ�</c:if>
					<c:if test="${row.hiddangerState eq '40'}">��Ҫ�Դ���</c:if>
					<c:if test="${row.hiddangerState eq '50'}">��Ҫ���</c:if>
					<c:if test="${row.hiddangerState eq '51'}">��Ҫ�ƶ��ƻ�</c:if>
					<c:if test="${row.hiddangerState eq '52'}">��Ҫ��˼ƻ�</c:if>
					<c:if test="${row.hiddangerState eq '53'}">�ƻ�ִ����</c:if>
					<c:if test="${row.hiddangerState eq '54'}">��Ҫ��ֹ���</c:if>
					<c:if test="${row.hiddangerState eq '60'}">��Ҫ�ر�</c:if>
					<c:if test="${row.hiddangerState eq '70'}">��Ҫ�ر����</c:if>
					<c:if test="${row.hiddangerState eq '00'}">��Ҫ��������</c:if>
					<c:if test="${row.hiddangerState eq '0'}">�����</c:if>
				</display:column>
				<display:column media="html" title="�Ǽ�ʱ��" sortable="true">
					<bean:write name="row" property="createTime"
						format="yy-MM-dd HH:mm:ss" />
				</display:column>
				<display:column media="html" title="����" sortable="true">
					<c:choose>
						<c:when test="${row.hiddangerState eq '52'}">
							<a href="javascript:viewPlan('${row.splanId}')">�鿴</a>
						</c:when>
						<c:otherwise>
							<a href="javascript:view('${row.id}')">�鿴</a>
						</c:otherwise>
					</c:choose>
					<c:if test="${row.edit}">
						<c:if test="${row.hiddangerState eq '10'}">
							<a href="javascript:editRegist('${row.id}')">�޸�</a>
						</c:if>
						<c:if test="${row.hiddangerLevel eq '1'}">
							<c:if test="${row.hiddangerState eq '50'}">
								<a href="javascript:editReport('${row.id}')">�޸�</a>
							</c:if>
						</c:if>
						<c:if
							test="${row.hiddangerLevel eq '2' || row.hiddangerLevel eq '3'}">
							<c:if test="${row.hiddangerState eq '51'}">
								<a href="javascript:editReport('${row.id}')">�޸�</a>
							</c:if>
						</c:if>
						<c:if test="${row.hiddangerState eq '52'}">
							<a href="javascript:editPlan('${row.splanId}','${row.id}')">�޸�</a>
						</c:if>
						<c:if test="${row.hiddangerState eq '70'}">
							<a href="javascript:editClose('${row.id}')">�޸�</a>
						</c:if>
					</c:if>
					<c:if test="${sessionScope.LOGIN_USER.deptype=='1'}">
						<c:if test="${row.hiddangerState eq '10'}">
						  	|<a href="javascript:toCancelForm('${row.id}')">ȡ��</a>
						</c:if>
						<c:if test="${row.hiddangerState eq '20'}">
							|<a href="javascript:toCancelForm('${row.id}')">ȡ��</a>
						</c:if>
						<c:if test="${row.hiddangerState eq '30'}">
							|<a href="javascript:toCancelForm('${row.id}')">ȡ��</a>
						</c:if>
						<c:if test="${row.hiddangerState eq '40'}">
							|<a href="javascript:toCancelForm('${row.id}')">ȡ��</a>
						</c:if>
					</c:if>
				</display:column>
			</display:table>
			<table style="border: 0px;">
				<tr>
					<td style="border: 0px; text-align: center;">
						<html:button property="action" styleClass="button"
							onclick="back()">����</html:button>
					</td>
				</tr>
			</table>
		</logic:notEmpty>
	</body>