<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script type='text/javascript' src='/WebApp/linepatrol/js/change_style.js'></script>
	
	<script language="JavaScript" src="/WebApp/js/dhtmlxTree/dhtmlxcommon.js"></script>
	<script language="JavaScript" src="/WebApp/js/dhtmlxTree/dhtmlxtree.js"></script>
	<script language="JavaScript" src="/WebApp/js/dhtmlxTree/ext/dhtmlxtree_json.js"></script>
	<link rel="STYLESHEET" type="text/css" href="/WebApp/js/dhtmlxTree/dhtmlxtree.css">
	
	<script type='text/javascript' src='/WebApp/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='/WebApp/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='/WebApp/js/extjs/resources/css/ext-all.css' />
	
	<script type="text/javascript">
		var win;
		function showWin(url){
			win = new Ext.Window({
				layout : 'fit',
				width:600,
				height:400,
				resizable:true,
				closeAction : 'close',
				modal:true,
				autoScroll:true,
				html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
				plain: true
			});
			win.show(Ext.getBody());
		}
		function close(){
			win.close();
		}
		function caculate(){
			var inps = document.getElementsByTagName("input");
			var nul = /^\s+$/;
			var num = /^\d*\.?\d*$/;
			var sum = parseFloat(0);
			for(var i=0 ; i<inps.length ; i++){
				if(inps[i].type == 'checkbox' && inps[i].checked){
					var val = inps[i].feeValue;
					if(val!='' && !nul.test(val) && num.test(val)){
						sum += parseFloat(val);
					}
				}
			}
			$('useFee').innerHTML = sum + "Ԫ";

			var remainFee = parseFloat($('fee').value) - parseFloat(sum);
			if(remainFee >= 0){
				$('remainFee').innerHTML = "<font color=blue>"+remainFee+"Ԫ</font>";
			}else{
				$('remainFee').innerHTML = "<font color=red>"+remainFee+"Ԫ</font>";
			}
		}
		function setVal(v){
			$('flag').value = v;
			$('checked').value = tree.getAllChecked();
		}
		var tree
		function showTree(){
			$("tree").innerHTML="";
			tree=new dhtmlXTreeObject("tree","100%","100%",0);
			tree.setImagePath("/WebApp/js/dhtmlxTree/imgs/csh_bluebooks/");
			tree.enableCheckBoxes(true);
			tree.enableThreeStateCheckboxes(true);
			tree.setOnClickHandler(onClick);
			eval('var json=${json}');
			tree.loadJSONObject(json);
		}
		function onClick(id){
			if(id.indexOf("cut.") != -1){
				var uid = (tree.getUserData(id, "uid"));
				var ids = uid.replace("cut,", "");
				viewCut(ids);
			}
			if(id.indexOf("project.") != -1){
				var uid = (tree.getUserData(id, "uid"));
				var ids = uid.replace("project,", "");
				viewProject(ids);
			}
		}
		function viewCut(id){
			var url = "/WebApp/cutQueryStatAction.do?method=viewQueryCut&type=win&cutId="+id;
			showWin(url);
		}
		function viewProject(id){
			var url = "/WebApp/project/remedy_apply.do?method=view&type=win&apply_id="+id;
			showWin(url);
		}
	</script>
</head>
<body onload="changeStyle();showTree()">
	<template:titile value="�������봦��" />
	<template:formTable namewidth="150" contentwidth="600">
		<html:form action="/overHaulAction.do?method=treat" styleId="form">
			<input type="hidden" name="flag" value=""/>
			<input type="hidden" name="taskId" value="${OverHaul.id}" />
			<input type="hidden" name="checked" />
			<template:formTr name="������Ŀ����">
				${OverHaul.projectName}
			</template:formTr>
			<template:formTr name="������">
				${OverHaul.projectCreator}
			</template:formTr>
			<template:formTr name="Ԥ�����">
				<div id="feefiv">${OverHaul.budgetFee}Ԫ</div>
				<input type="hidden" id="fee" value="${OverHaul.budgetFee}" />
			</template:formTr>
			<template:formTr name="�������ʼʱ��">
				<bean:write name="OverHaul" property="startTime" format="yyyy-MM-dd" />
			</template:formTr>
			<template:formTr name="�����������ʱ��">
				<bean:write name="OverHaul" property="endTime" format="yyyy-MM-dd" />
			</template:formTr>
			<template:formTr name="�������ע">
				${OverHaul.projectRemark}
			</template:formTr>
			<template:formTr name="��ӹ�����Ϣ">
				<div id="tree" />
			</template:formTr>
			<template:formTr name="Ŀǰ��Ŀ����">
				<div id="useFee">${OverHaul.useFee}Ԫ</div>
			</template:formTr>
			<template:formTr name="ʣ�����">
				<div id="remainFee">
					<c:choose>
						<c:when test="${OverHaul.remainFee >= 0}">
							<font color=blue>${OverHaul.remainFee}Ԫ</font>
						</c:when>
						<c:otherwise>
							<font color=red>${OverHaul.remainFee}Ԫ</font>
						</c:otherwise>
					</c:choose>
				</div>
			</template:formTr>
			<template:formSubmit>
				<td>
					<input type="submit" class="button" onclick="setVal('save')" value="������" onclick="tosave()" />
				</td>
				<td>
					<input type="submit" class="button" onclick="setVal('end')" value="��������" onclick="toend()" />
				</td>
				<td>
					<input type="button" class="button" value="����" onclick="history.back()" />
				</td>
			</template:formSubmit>
		</html:form>
	</template:formTable>
</body>
<script type="text/javascript">
	jQuery(document).ready(function() {
	jQuery("#form").bind("submit",function(){processBar(form);});
})
	</script>