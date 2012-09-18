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
		<template:titile value="隐患查询" />
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
				<template:formTr name="登记名称" style="text-align:right;">
					<html:text property="name" styleClass="inputtext" name="queryBean"
						style="width:140px" />
				</template:formTr>
				<template:formTr name="隐患分类" style="text-align:right;">
					<apptag:setSelectOptions columnName1="typename" columnName2="id"
						tableName="troubletype" valueName="troubletype" />
					<html:select property="type" styleId="type" name="queryBean"
						styleClass="inputtext" style="width:140px"
						onchange="getTroubleCode(this.options[this.selectedIndex].value)">
						<html:option value="">不限</html:option>
						<html:options collection="troubletype" property="value"
							labelProperty="label" />
					</html:select>
				</template:formTr>
				<template:formTr name="隐患名称" style="text-align:right;">
					<html:select property="code" styleId="code" styleClass="inputtext"
						name="queryBean" style="width:140px">
						<html:option value="">不限</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="隐患处理单位" style="text-align:right;">
					<html:select property="treatDepartment" styleClass="inputtext"
						style="width:140px">
						<html:option value="">不限</html:option>
						<html:options collection="dept" property="value"
							labelProperty="label" />
					</html:select>
				</template:formTr>
				<template:formTr name="是否取消" style="text-align:right;">
					<html:select property="hideDangerState" styleClass="inputtext"
						style="width:140px">
						<html:option value="">不限</html:option>
						<html:option value="999">是</html:option>
						<html:option value="0">否</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="隐患级别" style="text-align:right;">
					<html:multibox property="hiddangerLevels" value="1" />1级
					<html:multibox property="hiddangerLevels" value="2" />2级
					<html:multibox property="hiddangerLevels" value="3" />3级
					<html:multibox property="hiddangerLevels" value="4" />4级
					<html:multibox property="hiddangerLevels" value="0" />忽略
					<!-- 		<html:select property="hiddangerLevel" styleClass="inputtext" style="width:140px">
					<html:option value="">不限</html:option>
					<html:option value="1">1级</html:option>
					<html:option value="2">2级</html:option>
					<html:option value="3">3级</html:option>
					<html:option value="4">4级</html:option>
					<html:option value="0">忽略</html:option>
				</html:select>
			-->
				</template:formTr>
				<template:formTr name="隐患状态" style="text-align:right;">
					<html:multibox property="hiddangerStates" value="'10'" />登记
					<html:multibox property="hiddangerStates" value="'20','30','40'" />评级
					<html:multibox property="hiddangerStates" value="'50'" />上报
					<html:multibox property="hiddangerStates" value="'51'" />上报审核
					<html:multibox property="hiddangerStates" value="'52'" />制定计划
					<html:multibox property="hiddangerStates" value="'60'" />计划审核
					<html:multibox property="hiddangerStates" value="'70'" />关闭
					<html:multibox property="hiddangerStates" value="'00'" />关闭审核
					<html:multibox property="hiddangerStates" value="'0'" />完成
					<!-- 		<html:select property="hiddangerState" styleClass="inputtext" style="width:140px">
					<html:option value="">不限</html:option>
					<html:option value="10">登记</html:option>
					<html:option value="20,30,40">评级</html:option>
					<html:option value="50">上报</html:option>
					<html:option value="51">上报审核</html:option>
					<html:option value="52">制定计划</html:option>
					<html:option value="60">计划审核</html:option>
					<html:option value="70">关闭</html:option>
					<html:option value="00">关闭审核</html:option>
					<html:option value="0">完成</html:option>
				</html:select>
				-->
				</template:formTr>
				<template:formTr name="登记时间" style="text-align:right;">
					<html:text property="begintime" styleClass="Wdate"
						styleId="begintime" name="queryBean" style="width:140px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endtime\')||\'%y-%M-%d\'}'})" /> 至
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
				<html:submit property="action" styleClass="button">查询</html:submit>
				<html:reset property="action" styleClass="button">重置</html:reset>
			</div>
		</html:form>
		<template:displayHide styleId="form" hide="隐藏" formDisplay=""
			display="更多条件"></template:displayHide>

		<!-- 查询结果 -->
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
			if(confirm("确定要取消该隐患流程吗？")){
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
				<display:column property="hiddangerNumber" title="隐患编号"
					sortable="true" />
				<display:column property="createrDept" title="登记单位" sortable="true" />
				<display:column property="name" title="登记名称" sortable="true" />
				<display:column media="html" title="分类" sortable="true">
					<c:out value="${types[row.type]}" />
				</display:column>
				<display:column media="html" title="名称" sortable="true">
					<c:out value="${codes[row.code]}" />
				</display:column>
				<display:column media="html" title="发现时间" sortable="true">
					<bean:write name="row" property="findTime"
						format="yy-MM-dd HH:mm:ss" />
				</display:column>
				<display:column property="reporter" title="报告人" maxLength="10"
					sortable="true" />
				<display:column media="html" title="处理单位" sortable="true">
					<c:out value="${depts[row.treatDepartment]}" />
				</display:column>
				<display:column media="html" title="状态" sortable="true">
					<c:if test="${row.hiddangerState eq '10'}">需要评级</c:if>
					<c:if test="${row.hiddangerState eq '20'}">需要上报</c:if>
					<c:if test="${row.hiddangerState eq '30'}">需要上报</c:if>
					<c:if test="${row.hiddangerState eq '40'}">需要自处理</c:if>
					<c:if test="${row.hiddangerState eq '50'}">需要审核</c:if>
					<c:if test="${row.hiddangerState eq '51'}">需要制定计划</c:if>
					<c:if test="${row.hiddangerState eq '52'}">需要审核计划</c:if>
					<c:if test="${row.hiddangerState eq '53'}">计划执行中</c:if>
					<c:if test="${row.hiddangerState eq '54'}">需要终止审核</c:if>
					<c:if test="${row.hiddangerState eq '60'}">需要关闭</c:if>
					<c:if test="${row.hiddangerState eq '70'}">需要关闭审核</c:if>
					<c:if test="${row.hiddangerState eq '00'}">需要考核评估</c:if>
					<c:if test="${row.hiddangerState eq '0'}">已完成</c:if>
				</display:column>
				<display:column media="html" title="登记时间" sortable="true">
					<bean:write name="row" property="createTime"
						format="yy-MM-dd HH:mm:ss" />
				</display:column>
				<display:column media="html" title="操作" sortable="true">
					<c:choose>
						<c:when test="${row.hiddangerState eq '52'}">
							<a href="javascript:viewPlan('${row.splanId}')">查看</a>
						</c:when>
						<c:otherwise>
							<a href="javascript:view('${row.id}')">查看</a>
						</c:otherwise>
					</c:choose>
					<c:if test="${row.edit}">
						<c:if test="${row.hiddangerState eq '10'}">
							<a href="javascript:editRegist('${row.id}')">修改</a>
						</c:if>
						<c:if test="${row.hiddangerLevel eq '1'}">
							<c:if test="${row.hiddangerState eq '50'}">
								<a href="javascript:editReport('${row.id}')">修改</a>
							</c:if>
						</c:if>
						<c:if
							test="${row.hiddangerLevel eq '2' || row.hiddangerLevel eq '3'}">
							<c:if test="${row.hiddangerState eq '51'}">
								<a href="javascript:editReport('${row.id}')">修改</a>
							</c:if>
						</c:if>
						<c:if test="${row.hiddangerState eq '52'}">
							<a href="javascript:editPlan('${row.splanId}','${row.id}')">修改</a>
						</c:if>
						<c:if test="${row.hiddangerState eq '70'}">
							<a href="javascript:editClose('${row.id}')">修改</a>
						</c:if>
					</c:if>
					<c:if test="${sessionScope.LOGIN_USER.deptype=='1'}">
						<c:if test="${row.hiddangerState eq '10'}">
						  	|<a href="javascript:toCancelForm('${row.id}')">取消</a>
						</c:if>
						<c:if test="${row.hiddangerState eq '20'}">
							|<a href="javascript:toCancelForm('${row.id}')">取消</a>
						</c:if>
						<c:if test="${row.hiddangerState eq '30'}">
							|<a href="javascript:toCancelForm('${row.id}')">取消</a>
						</c:if>
						<c:if test="${row.hiddangerState eq '40'}">
							|<a href="javascript:toCancelForm('${row.id}')">取消</a>
						</c:if>
					</c:if>
				</display:column>
			</display:table>
			<table style="border: 0px;">
				<tr>
					<td style="border: 0px; text-align: center;">
						<html:button property="action" styleClass="button"
							onclick="back()">返回</html:button>
					</td>
				</tr>
			</table>
		</logic:notEmpty>
	</body>