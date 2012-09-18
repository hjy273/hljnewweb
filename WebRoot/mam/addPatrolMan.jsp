<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<link href="${ctx}/js/extjs/resources/css/ext-all.css" rel="stylesheet"
	type="text/css">
<script src="${ctx}/js/extjs/adapter/ext/ext-base.js"
	type="text/javascript"></script>
<script src="${ctx}/js/extjs/ext-all.js" type="text/javascript"></script>
<script language="javascript" src="${ctx}/js/prototype.js" type=""></script>
<script type="text/javascript">
var tree;
Ext.onReady(function() {
  	Ext.BLANK_IMAGE_URL = "${ctx}/js/extjs/resources/images/default/s.gif";
	tree = new Ext.tree.TreePanel({
		id:'check_id',
		width : 180,
		applyTo : 'tree-div',
		autoScroll:true,
		//height : parent.document.documentElement.scrollHeight - 10,
		margins : '0 2 0 0',
		root : new Ext.tree.AsyncTreeNode({
					id : "root",
					text : "所有人员",
					iconCls : 'treeroot-icon',
					expanded : true,
					loader : new Ext.tree.TreeLoader({
								dataUrl : '${ctx}/ConPersonAction.do?method=getPerson'

							})
				})
	});
});
</script>
<jsp:useBean id="selectForTag"
	class="com.cabletech.commons.tags.SelectForTag" scope="request" />
<%
    String condition = "";
    UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
    //市移动
    if (userinfo.getDeptype().equals("1")
            && !userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE regionid IN ('" + userinfo.getRegionID()
                + "') AND state IS NULL";
    }
    //市代维
    if (userinfo.getDeptype().equals("2")
            && !userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE state IS NULL  and contractorid='" + userinfo.getDeptID()
                + "' ";
    }
    //省移动
    if (userinfo.getDeptype().equals("1")
            && userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE state IS NULL";
    }
    //省代维
    if (userinfo.getDeptype().equals("2")
            && userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE contractorid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
                + userinfo.getDeptID() + "')";
    }
    List deptCollection = selectForTag.getSelectForTag("contractorname", "contractorid",
            "contractorinfo", condition);
    request.setAttribute("deptCollection", deptCollection);
%>
<script language="javascript" type="">
<!--
var regx=/^\d{11}$/;
function isValidForm() {
	if(document.patrolBean.patrolName.value==""||document.patrolBean.patrolName.value.trim()==""){
		alert("巡检维护组不能为空或者空格!! ");
        document.patrolBean.patrolName.focus();
		return false;
	}
	if(document.patrolBean.maintenanceArea.value==""||document.patrolBean.maintenanceArea.value.trim()==""){
		alert("维护区域不能为空或者空格!! ");
        document.patrolBean.maintenanceArea.focus();
		return false;
	}
	if(document.patrolBean.principal.value==""||document.patrolBean.principal.value.trim()==""){
		alert("负责人不能为空或者空格!! ");
        document.patrolBean.principal.focus();
		return false;
	}
	if(document.patrolBean.phone.value==""||document.patrolBean.phone.value.trim()==""){
		alert("联系电话不能为空或者空格!! ");
        document.patrolBean.phone.focus();
		return false;
	}
     if(!regx.test(document.patrolBean.phone.value)){
		alert("联系电话必须为11位数字!! ");
		document.patrolBean.phone.focus();
		return false;
	}
	if(document.patrolBean.address.value==""||document.patrolBean.address.value.trim()==""){
		alert("驻地地址不能为空或者空格!! ");
        document.patrolBean.address.focus();
		return false;
	}
	if(judgeExist()){
    	alert("该代维单位下已经存在重名的巡检组！");
    	return false;
    }
	var mds="";
	var checkedNodes=tree.getChecked();
	if(checkedNodes==""){
		alert("没有选择巡检组的人员！");
		return false;
	}
	for (var i = 0; i < checkedNodes.length; i++) {
		if(judgeExistOtherPatrolman(checkedNodes[i].id)){
			alert("选择的代维单位人员已经被分配过巡检组！");
			return false;
		}
		if (checkedNodes[i].isLeaf()) {
			mds += checkedNodes[i].id;
		}
		if(i<checkedNodes.length-1){
			mds+=",";
		}
	}
	document.patrolBean.patrolmanSon.value=mds;
	//刷新SMS缓存
	//top.bottomFrame.freshSmsCache();
	return true;
}
		judgeExistOtherPatrolman=function(personId){
			var flag=false;
			var queryString="patrolmanSon="+personId+"&patrolID=-1&rnd="+Math.random();
			var actionUrl="${ctx}/patrolAction.do?method=judgeExistOtherPatrolman&&"+queryString;
			new Ajax.Request(actionUrl, 
				{
					method:"post", 
					onSuccess:function (transport) {
						if(transport.responseText=='1'){
							flag=true;
						}
					}, onFailure:function () {
					}, 
					evalScripts:true, asynchronous:false
				}
			);
			return flag;
		}
    	judgeExist=function(){
			var flag=false;
			var patrolName=patrolBean.patrolName.value;
			var parentID=patrolBean.parentID.value;
			var queryString="patrolName="+patrolName+"&parentID="+parentID+"&patrolID=-1&rnd="+Math.random();
			var actionUrl="${ctx}/patrolAction.do?method=judgePatrolmanExist&&"+queryString;
			new Ajax.Request(actionUrl, 
				{
					method:"post", 
					onSuccess:function (transport) {
						if(transport.responseText=='1'){
							flag=true;
						}
					}, onFailure:function () {
					}, 
					evalScripts:true, asynchronous:false
				}
			);
			return flag;
		}

//-->
</script>
<template:titile value="增加巡检维护组信息" />
<html:form onsubmit="return isValidForm()" method="Post"
	action="/patrolAction.do?method=addPatrolMan">
	<template:formTable contentwidth="300" namewidth="200">
		<template:formTr name="巡检维护组" isOdd="">
			<html:text property="patrolName" styleClass="inputtext"
				style="width:200" />
			<font color="#FF0000">*</font>
		</template:formTr>
		<template:formTr name="所属单位">
			<html:select property="parentID" styleClass="inputtext"
				style="width:200">
				<html:options collection="deptCollection" property="value"
					labelProperty="label" />
			</html:select>
			<font color="#FF0000">*</font>
		</template:formTr>
		<template:formTr name="维护区域">
			<html:text property="maintenanceArea" styleClass="inputtext"
				style="width:200" />
			<font color="#FF0000">*</font>
		</template:formTr>
		<template:formTr name="人员组成">
			<div id="tree-div" style="height: 300; width: 100%"></div>
			<html:hidden property="patrolmanSon" />
			<font color="#FF0000">*</font>
		</template:formTr>
		<template:formTr name="负责人">
			<html:text property="principal" styleClass="inputtext"
				style="width:200" />
			<font color="#FF0000">*</font>
		</template:formTr>
		<template:formTr name="联系电话">
			<html:text property="phone" styleClass="inputtext" style="width:200" />
			<font color="#FF0000">*</font>
		</template:formTr>
		<template:formTr name="驻地地址">
			<html:text property="address" styleClass="inputtext"
				style="width:200" />
			<font color="#FF0000">*</font>
		</template:formTr>
		<template:formTr name="驻地电话">
			<html:text property="workPhone" styleClass="inputtext"
				style="width:200" />
		</template:formTr>
		<template:formSubmit>
			<td>
				<html:submit styleClass="button">增加</html:submit>
			</td>
			<td>
				<html:reset styleClass="button">取消</html:reset>
			</td>
		</template:formSubmit>
	</template:formTable>
</html:form>
