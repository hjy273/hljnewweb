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
					text : "������Ա",
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
    //���ƶ�
    if (userinfo.getDeptype().equals("1")
            && !userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE regionid IN ('" + userinfo.getRegionID()
                + "') AND state IS NULL";
    }
    //�д�ά
    if (userinfo.getDeptype().equals("2")
            && !userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE state IS NULL  and contractorid='" + userinfo.getDeptID()
                + "' ";
    }
    //ʡ�ƶ�
    if (userinfo.getDeptype().equals("1")
            && userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE state IS NULL";
    }
    //ʡ��ά
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
		alert("Ѳ��ά���鲻��Ϊ�ջ��߿ո�!! ");
        document.patrolBean.patrolName.focus();
		return false;
	}
	if(document.patrolBean.maintenanceArea.value==""||document.patrolBean.maintenanceArea.value.trim()==""){
		alert("ά��������Ϊ�ջ��߿ո�!! ");
        document.patrolBean.maintenanceArea.focus();
		return false;
	}
	if(document.patrolBean.principal.value==""||document.patrolBean.principal.value.trim()==""){
		alert("�����˲���Ϊ�ջ��߿ո�!! ");
        document.patrolBean.principal.focus();
		return false;
	}
	if(document.patrolBean.phone.value==""||document.patrolBean.phone.value.trim()==""){
		alert("��ϵ�绰����Ϊ�ջ��߿ո�!! ");
        document.patrolBean.phone.focus();
		return false;
	}
     if(!regx.test(document.patrolBean.phone.value)){
		alert("��ϵ�绰����Ϊ11λ����!! ");
		document.patrolBean.phone.focus();
		return false;
	}
	if(document.patrolBean.address.value==""||document.patrolBean.address.value.trim()==""){
		alert("פ�ص�ַ����Ϊ�ջ��߿ո�!! ");
        document.patrolBean.address.focus();
		return false;
	}
	if(judgeExist()){
    	alert("�ô�ά��λ���Ѿ�����������Ѳ���飡");
    	return false;
    }
	var mds="";
	var checkedNodes=tree.getChecked();
	if(checkedNodes==""){
		alert("û��ѡ��Ѳ�������Ա��");
		return false;
	}
	for (var i = 0; i < checkedNodes.length; i++) {
		if(judgeExistOtherPatrolman(checkedNodes[i].id)){
			alert("ѡ��Ĵ�ά��λ��Ա�Ѿ��������Ѳ���飡");
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
	//ˢ��SMS����
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
<template:titile value="����Ѳ��ά������Ϣ" />
<html:form onsubmit="return isValidForm()" method="Post"
	action="/patrolAction.do?method=addPatrolMan">
	<template:formTable contentwidth="300" namewidth="200">
		<template:formTr name="Ѳ��ά����" isOdd="">
			<html:text property="patrolName" styleClass="inputtext"
				style="width:200" />
			<font color="#FF0000">*</font>
		</template:formTr>
		<template:formTr name="������λ">
			<html:select property="parentID" styleClass="inputtext"
				style="width:200">
				<html:options collection="deptCollection" property="value"
					labelProperty="label" />
			</html:select>
			<font color="#FF0000">*</font>
		</template:formTr>
		<template:formTr name="ά������">
			<html:text property="maintenanceArea" styleClass="inputtext"
				style="width:200" />
			<font color="#FF0000">*</font>
		</template:formTr>
		<template:formTr name="��Ա���">
			<div id="tree-div" style="height: 300; width: 100%"></div>
			<html:hidden property="patrolmanSon" />
			<font color="#FF0000">*</font>
		</template:formTr>
		<template:formTr name="������">
			<html:text property="principal" styleClass="inputtext"
				style="width:200" />
			<font color="#FF0000">*</font>
		</template:formTr>
		<template:formTr name="��ϵ�绰">
			<html:text property="phone" styleClass="inputtext" style="width:200" />
			<font color="#FF0000">*</font>
		</template:formTr>
		<template:formTr name="פ�ص�ַ">
			<html:text property="address" styleClass="inputtext"
				style="width:200" />
			<font color="#FF0000">*</font>
		</template:formTr>
		<template:formTr name="פ�ص绰">
			<html:text property="workPhone" styleClass="inputtext"
				style="width:200" />
		</template:formTr>
		<template:formSubmit>
			<td>
				<html:submit styleClass="button">����</html:submit>
			</td>
			<td>
				<html:reset styleClass="button">ȡ��</html:reset>
			</td>
		</template:formSubmit>
	</template:formTable>
</html:form>
