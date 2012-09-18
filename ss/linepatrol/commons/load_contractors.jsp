<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link rel="STYLESHEET" type="text/css"
			href="${ctx}/js/dhtmlxTree/dhtmlxtree.css">
		<script src="${ctx}/js/dhtmlxTree/dhtmlxcommon.js"></script>
		<script src="${ctx}/js/dhtmlxTree/dhtmlxtree.js"></script>
		<script src="${ctx}/js/dhtmlxTree/ext/dhtmlxtree_json.js"></script>
		<script type="text/javascript">
		var tree;
	    hideLoadingImg=function(){
			//document.getElementById("btnSave").disable=false;
			//document.getElementById("btnSubmit").disable=false;
		}
	    loadTree=function(){
	    	//document.getElementById("btnSave").disable=true;
			//document.getElementById("btnSubmit").disable=true;
			var queryString="?method=showAllContractors&query_value=&exist_value=${exist_value}&rnd="+Math.random();
			tree=new dhtmlXTreeObject("usertree","100%","100%",0);
			tree.dlmtr="~";
			tree.setImagePath("${ctx}/js/dhtmlxTree/imgs/csh_bluebooks/");
			tree.enableThreeStateCheckboxes(true);
			tree.enableCheckBoxes(1);
			//tree.enableIEImageFix(true);
			tree.setDataMode("json");
			tree.loadJSON("${ctx}/load_contractors.do"+queryString,hideLoadingImg);
		}
		queryForm=function(){
			var form=document.forms[0];
			var queryString="?method=showAllContractors&query_value="+form.query_value.value+"&exist_users=&rnd="+Math.random();
			tree.destructor();
			tree=new dhtmlXTreeObject("usertree","100%","100%",0);
			tree.setImagePath("${ctx}/js/dhtmlxTree/imgs/csh_bluebooks/");
			tree.enableThreeStateCheckboxes(true);
			tree.enableCheckBoxes(1);
			//tree.enableIEImageFix(true);
			tree.setDataMode("json");
			tree.loadJSON("${ctx}/load_contractors.do"+queryString,hideLoadingImg);
		}
		finishForm=function(){
			var form=document.forms[0];
			var mids = tree.getAllChecked();
			var selectUsers=form.select_mans;
			selectUsers.value=mids;
			if(selectUsers.value==""){
				alert("请选择要添加的用户！");
			}else{
				form.submit();
			}
		}
		closeForm=function(){
			parent.document.getElementById("${span_id}HideDlg").click();
		}
		</script>
		<style>
		td{
			font-size:12px;
			line-heigh:30px;
			border-color:#A2D0FF;
		}	
		</style>
	</head>
	<body onload="loadTree();">
		<html:form method="post"
			action="/load_contractors.do?method=addContractors">
			<table width="100%" border="1" cellspacing="0" cellpadding="0"
				style="border-collapse: collapse; border-color: #A2D0FF;">
				<tr>
					<td width="25%" height="25"
						style="text-align: center; vertical-align: middle;">
						查询条件：
					</td>
					<td width="75%" style="text-align: left;padding-left:5px;">
						<input type="text" name="query_value" style="width: 180px;" />
						<input name="btnQuery" type="button" id="btnQuery" value="查询"
							onclick="queryForm();" />
					</td>
				</tr>
				<tr>
					<td style="text-align: center; vertical-align: top;padding-top:10px;">
						代维单位列表：
					</td>
					<td id="usertree" style="height: 270px; text-align: left;padding-top:10px;"></td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="25" colspan="2" style="text-align: center;">
						<input name="select_mans" value="" type="hidden" />
						<input type="hidden" name="input_name" value="${input_name}" />
						<input type="hidden" name="span_id" value="${span_id}" />
						<input name="btnFinish" type="button" id="btnFinish" value="完成"
							onclick="finishForm();" />
						<input name="btnClose" type="button" id="btnClose" value="关闭"
							onclick="closeForm();" />
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
