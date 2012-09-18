<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
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
	
	<script type="text/javascript">
		var win;
		function showWin(url){
			win = new Ext.Window({
				layout : 'fit',
				width:600,
				height:400,
				resizable:true,
				closable:true,
				closeAction : 'close',
				modal:true,
				html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
				plain: true
			});
			win.show(Ext.getBody());
		}
		function close(){
			win.close();
		}
		function setAllotInfo(id, obj){
			var val = obj.options[obj.selectedIndex].value;
			var url = "${ctx}/acceptanceAction.do?method=saveSpecify&objectId="+id+'&contractorId='+val;
			sendAjax(url);
		}
		function setValue(ids){
			$('chooseContractor').value = ids;
		}
		function setName(name){
			var divs = document.getElementsByTagName('div');
			for(var i = 0 ; i < divs.length ; i++){
				if(divs[i].name == 'choose'){
					divs[i].innerHTML = name;
				}
			}
		}
		function eraserAllotInfo(){
			var objs = document.getElementsByName('allotInfo');
			for(var i=0;i<objs.length;i++){
				objs[i].value = '';
			}
			setName('');
		}
		function choose(){
			var url = "${ctx}/acceptanceAction.do?method=changeType&type=2";
			sendAjax(url);
			
			hiddenDiv('specify', false);
			hiddenDiv('choose', true);
			eraserAllotInfo();
			showWin('${ctx}/linepatrol/acceptance/contractorSelect.jsp?ids='+$('chooseContractor').value);
		}
		function specify(){
			var url = "${ctx}/acceptanceAction.do?method=changeType&type=1";
			sendAjax(url);
			
			hiddenDiv('specify', true);
			hiddenDiv('choose', false);
			eraserAllotInfo();
			setOptionNull();
		}
		function sendAjax(url){
			$('submit').disabled = "disabled";
			var myAjax = new Ajax.Request(
				url, 
				{method:"post", onComplete:callback, asynchronous:true}
			);
		}
		function callback(){
			$('submit').disabled = "";
		}
		function setOptionNull(){
			var objs = document.getElementsByName('deptOptions');
			for(var i=0;i<objs.length;i++){
				objs[i].options[0].selected = true;
			}
		}
		function hiddenDiv(name, flag){
			var divs = document.getElementsByTagName('div');
			for(var i = 0 ; i < divs.length ; i++){
				var display = '';
				if(flag)
					display = "";
				else
					display = "none";
				if(divs[i].name == name){
					divs[i].style.display = display;
				}
			}
		}
		function init(){
			var types = document.getElementsByName('type');
			for(var i=0 ; i<types.length ; i++){
				if(types[i].checked){
					if(types[i].value == 1){
						hiddenDiv('specify', true);
						hiddenDiv('choose', false);
						eraserAllotInfo();
					}else{
						hiddenDiv('specify', false);
						hiddenDiv('choose', true);
						eraserAllotInfo();

						setName('${names}');
						setValue('${ids}');
					}
				}
			}
		}
		function viewCable(id){
			var url = '${ctx}/acceptanceAction.do?method=viewCable&id='+id;
			showWin(url);
		}
		function viewPipe(id){
			var url = '${ctx}/acceptanceAction.do?method=viewPipe&id='+id;
			showWin(url);
		}
	</script>
</head>
<body onload="init()">
	<template:titile value="核准申请" />
	<html:form action="/acceptanceAction.do?method=allot" styleId="form">
		<template:formTable namewidth="200" contentwidth="400">
			<input type="hidden" name="id" value="${id}" />
			<input type="hidden" name="chooseContractor" id="chooseContractor" />
			<template:formTr name="项目经理">
				<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="${apply.applicant}" />
			</template:formTr>
			<template:formTr name="工程名称">
				${apply.name}
			</template:formTr>
			<template:formTr name="验收资源类型">
				<c:choose>
					<c:when test="${apply.resourceType eq '1'}">
						光缆
					</c:when>
					<c:otherwise>
						管道
					</c:otherwise>
				</c:choose>
			</template:formTr>
			<template:formTr name="核准类型">
				<input type="radio" value="1" name="type" onclick="specify()" <c:if test="${type eq '1'}">checked</c:if> />指定代维
				<input type="radio" value="2" name="type" onclick="choose()" <c:if test="${type eq '2'}">checked</c:if> />选择代维
			</template:formTr>
			<template:formTr name="备注">
				<html:textarea property="remark" styleClass="inputtext" style="width:400px;height:70px" />
			</template:formTr>
			<tr>
				<td colspan=2>
					<c:choose>
						<c:when test="${apply.resourceType eq '1'}">
							<display:table name="sessionScope.apply.cables" id="row" export="false" pagesize="10" sort="list" defaultsort="1" defaultorder="ascending">
								<display:column property="cableNo" title="光缆编号" sortable="true"/>
								<display:column property="trunk" title="光缆中继段" sortable="true"/>
								<display:column property="fibercoreNo" title="纤芯数" sortable="true"/>
								<display:column media="html" title="光缆级别" sortable="true">
									<apptag:quickLoadList style="width:130px" keyValue="${row.cableLevel}" cssClass="select" name="cableLevel" listName="cabletype" type="look" />
								</display:column>
								<display:column media="html" title="光缆长度" sortable="true">
									${row.cableLength}千米
								</display:column>
								<display:column media="html" title="操作" sortable="true">
									<a href="javascript:viewCable('${row.id}')">查看</a>
								</display:column>
								<display:column media="html" title="代维单位" sortable="true">
									<div name="specify">
										<select name="deptOptions" onchange="setAllotInfo('${row.id}',this)">
											<option value="">请选择</option>
											<c:forEach var="dept" items="${dept}">
												<option value="${dept.contractorid}" <c:if test="${specify[row.id] eq dept.contractorid}">selected</c:if> >${dept.contractorname}</option>
											</c:forEach>
										</select>
									</div>
									<div name="choose" />
								</display:column>
							</display:table>
						</c:when>
						<c:otherwise>
							<display:table name="sessionScope.apply.pipes" id="row" export="false" pagesize="10" sort="list" defaultsort="1" defaultorder="ascending">
								<display:column property="projectName" title="工程名称" sortable="true"/>
								<display:column property="pipeAddress" title="管道地点" sortable="true"/>
								<display:column property="pipeRoute" title="详细路由" sortable="true"/>
								<display:column property="builder" title="施工单位" sortable="true"/>
								<display:column media="html" title="产权属性" sortable="true">
									<apptag:quickLoadList style="width:130px" keyValue="${row.pipeProperty}" cssClass="select" name="pipeProperty" listName="property_right" type="look" />
								</display:column>
								<display:column media="html" title="管道属性" sortable="true">
									<apptag:quickLoadList style="width:130px" keyValue="${row.pipeType}" cssClass="select" name="pipeType" listName="pipe_type" type="look" />
								</display:column>
								<display:column media="html" title="操作" sortable="true">
									<a href="javascript:viewPipe('${row.id}')">查看</a>
								</display:column>
								<display:column media="html" title="代维单位" sortable="true">
									<div name="specify">
										<select name="deptOptions" onchange="setAllotInfo('${row.id}',this)">
											<option value="">请选择</option>
											<c:forEach var="dept" items="${dept}">
												<option value="${dept.contractorid}">${dept.contractorname}</option>
											</c:forEach>
										</select>
									</div>
									<div name="choose" />
								</display:column>
							</display:table>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</template:formTable>
		<table class="tdlist">
			<tr>
				<td class="tdlist">
					<html:submit property="action" styleId="submit" styleClass="button">提交</html:submit>
					<input type="button" class="button" value="返回" onclick="history.back()" />
				</td>
			</tr>
		</table>
	</html:form>
	<script type="text/javascript">
		function formCallback(result, form) {
			window.status = "valiation callback for form '" + form.id + "': result = " + result;
		}

		var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
</body>