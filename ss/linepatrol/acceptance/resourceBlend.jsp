<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
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
		function setAllotInfo(applyid,rsid,oldConId, obj){
			var val = obj.options[obj.selectedIndex].value;
			var url = "${ctx}/resourceBlendAction.do?method=saveChange&applyid="+applyid+"&objectId="+rsid+"&oldconid="+oldConId+"&newconid="+val;
			sendAjax(url);
		}
		function setValue(ids){
			$('chooseContractor').value = ids;
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
<body>
	<template:titile value="������Դ����" />
	<div id="tip" style="color:red">˵����<br>
		1����ѯ�ĵ�����Դ�������Ѿ����䲢�ȴ����յĹ��£��ܵ���Դ��<br>
		2�����԰��ؼ���ģ������.abcde
	</div>
	<form action="${ctx}/resourceBlendAction.do?method=searchResource" method="post" id="form">
		<template:formTable>
			<template:formTr name="��Դ����">
				<input type="radio" value="1" name="resourceType" <c:if test="${resourceType eq '1'}">checked</c:if> />����
				<input type="radio" value="2" name="resourceType" <c:if test="${resourceType eq '2'}">checked</c:if> />�ܵ�
			</template:formTr>
			<template:formTr name="���չ�������" style="trwhite">	
				<input type="text" name="applyName" class="inputtext required" value="${applyName}"></input>
			</template:formTr>
			<template:formTr name="��Դ��Ϣ">
				<input type="text" name="resourceName" class="inputtext" value="${resourceName}"></input>
				<span id="tip" style="color:red">����Ϊ����ʱ������������±�Ż�������ƣ�Ϊ�ܵ�ʱ����������ܵ��ص��ܵ�·��</span>
			</template:formTr>
		</template:formTable>
		<table style="border:0px">
			<tr>
				<td style="border:0px;text-align:center;">
					<html:submit property="action" styleId="search" styleClass="button">����</html:submit>
				</td>
			</tr>
		</table>
	</form>
	<c:if test="${searchResult !=null}">
	<form action="${ctx}/resourceBlendAction.do?method=blendResource" method="post"  >
		<display:table name="sessionScope.searchResult" id="row" export="false" pagesize="15" sort="list" defaultsort="1" defaultorder="ascending">
			<display:column property="applyname" title="��������" sortable="true"/>
			<display:column property="code" title="�����������" sortable="true"/>
			<display:column property="pcmanager" title="��Ŀ����" sortable="true"/>
			<display:column property="address" title="��ַ·����Ϣ" sortable="true"/>
			<display:column property="contractorname" title="���մ�ά" sortable="true"/>
			<display:column media="html" title="�鿴" sortable="true">
				<c:if test="${resourceType eq '1'}">
				<a href="javascript:viewCable('${row.rsid}')">�鿴</a>
				</c:if>
				<c:if test="${resourceType eq '2'}">
				<a href="javascript:viewPipe('${row.rsid}')">�鿴</a>
				</c:if>
			</display:column>
			<display:column media="html" title="��ά��λ" sortable="true">
				<div id="specify">
				<select name="deptOptions" onchange="setAllotInfo('${row.applyid}','${row.rsid}','${row.contractorid}',this)">
					<option value="">��ѡ��</option>
					<c:forEach var="dept" items="${allDept}">
					<option value="${dept.contractorid}">${dept.contractorname}</option>
					</c:forEach>
				</select>
				</div>
			</display:column>
		</display:table>
		<div id="submitbutton" style="width:100%;text-align: center">
		<html:submit property="action" styleId="submit" styleClass="button">�ύ</html:submit>
		</div>
	</form>
	</c:if>
	<script type="text/javascript">
		function formCallback(result, form) {
			window.status = "valiation callback for form '" + form.id + "': result = " + result;
		}

		var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
	<template:cssTable></template:cssTable>
</body>