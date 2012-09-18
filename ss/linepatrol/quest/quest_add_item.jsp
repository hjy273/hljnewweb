<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>添加指标项</title>
		<link rel="STYLESHEET" type="text/css" href="/WebApp/js/dhtmlxTree/dhtmlxtree.css">
		<script src="/WebApp/js/dhtmlxTree/dhtmlxcommon.js"></script>
		<script src="/WebApp/js/dhtmlxTree/dhtmlxtree.js"></script>
		<script src="/WebApp/js/dhtmlxTree/ext/dhtmlxtree_json.js"></script>
		<script type="text/javascript">
			var tree;
			function loadTree(){
				var queryType = $('queryType').value;
				var existItemIds = $('itemIds').value;
				var queryString="?method=showAllQuestIssueItem&queryType="+queryType+"&&rnd="+Math.random()+"&existItemIds="+existItemIds;
				tree=new dhtmlXTreeObject("usertree","100%","100%",0);
				tree.dlmtr="~";
				tree.setImagePath("/WebApp/js/dhtmlxTree/imgs/csh_bluebooks/");
				tree.enableThreeStateCheckboxes(true);
				tree.enableCheckBoxes(1);
				tree.setDataMode("json");
				tree.loadJSON("/WebApp/questAction.do"+queryString);
			}
			function closeWin(){
				parent.win.close();
			}
			function checkForm(){
				var treeValue=tree.getAllChecked();
				$('selectValue').value=treeValue;
				if($('selectValue').value==""){
					alert("至少选择一个参评项！");
				}else{
					//getQuestIssueIds.submit();
					sendAjax(treeValue);
				}
			}

			function sendAjax(tv){
				jQuery.ajax({
				   type: "POST",
				   url: "questAction.do",
				   data: "method=getQuestIssueIds&selectValue="+tv,
				   async: false,
				   success: function(msg){
					   parent.document.getElementById('showItemDiv').innerHTML = msg;
					   parent.win.close();
				   }
				});
			}
			
		</script>
	</head>
	<body onload="loadTree()">
		<html:form action="/questAction.do?method=getQuestIssueIds" styleId="getQuestIssueIds">
			<input type="hidden" id="queryType" name="queryType" value="${queryType }"/>
			<input type="hidden" id="itemIds" name="itemIds" value="${itemIds }"/>
			<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="100%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">指标项：</td>
					<td class="tdulright" style="width:80%;" id="usertree">
						&nbsp;
						<input type="hidden" value="" name="selectValue" id="selectValue" />
					</td>
				</tr>
			</table><div align="center" style="height:40px">
				<html:button property="action" styleClass="button" onclick="checkForm()">提交</html:button> &nbsp;&nbsp;
				<html:button property="action" styleClass="button" onclick="closeWin()">关闭</html:button>
			</div>
		</html:form>
	</body>
</html>
