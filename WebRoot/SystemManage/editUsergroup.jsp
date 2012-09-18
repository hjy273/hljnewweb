<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>

<html>
<head>
<script language="JavaScript" src="${ctx}/js/dhtmlxTree/dhtmlxcommon.js"></script>
<script language="JavaScript" src="${ctx}/js/dhtmlxTree/dhtmlxtree.js"></script>
<script language="JavaScript" src="${ctx}/js/dhtmlxTree/ext/dhtmlxtree_json.js"></script>
<script language="JavaScript" src="${ctx}/js/prototype.js"></script>
<link rel="STYLESHEET" type="text/css" href="${ctx}/js/dhtmlxTree/dhtmlxtree.css">

<script language="javascript" type="">
function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}

function isValidForm() {
    if($("groupname").value.length == 0||$("groupname").value.trim()==""){
        alert("��ɫ���Ʋ���Ϊ�ջ��߿ո�");
        $("groupname").value="";
        $("groupname").focus();
        return false;
    }

    $("groupname").value=$("groupname").value.trim();
    if($("remark").value.length>=50){
        alert("��ע���ܳ���50���ַ���");
        $("remark").value=$("remark").value.substring(0,50);
        $("remark").focus();
        return false;
    }

    return true;
}


function toGetBack(){
    var url = "${ctx}/UsergroupAction.do?method=getUserGroups";
    self.location.replace(url);
}

function toSave(){
	$("menus").value = tree.getAllChecked();
	${"form"}.submit();
}
var enlargeFlag = 1; //moduleDiv����չ��
function makeSize(){
    if(enlargeFlag == 0){
		$("info").style.display="";
       	$("moduleDiv").style.display = "none";
		$("info").innerHTML='<img src="${ctx}/images2/indicator.gif">���ڼ��ء���';
        enlargeFlag = 1;
    }else{
		$("info").style.display="none";
        $("moduleDiv").style.display = "";
         enlargeFlag = 0;
    }
}
var userType=${UsergroupBean.type};
function changeGroup(){
	makeSize();
	userType=$("type").value
	init();
}
</script>

<body>
<template:titile value="���½�ɫ"/>
<html:form method="Post" styleId="form" action="/UsergroupAction.do?method=editUsergroup">
    <template:formTable contentwidth="400" namewidth="250">

    <input type="hidden" id="menus" name="menus" value="">
    <html:hidden property="id" />

    <template:formTr name="��ɫ����">
      <html:text property="groupname" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>

    <template:formTr name="�û�����" >
    	<apptag:setSelectOptions columnName2="code" columnName1="typename" valueName="typeCollection" tableName="depttype"/>
    	<html:select property="type" styleClass="inputtext" style="width:160" onchange="changeGroup()">
    		<html:options collection="typeCollection" property="value" labelProperty="label"/>
    	</html:select>
    	
         <span style="color:red">* ���ǲ��������������޸��û�����</span>
    </template:formTr>

    <template:formTr name="����"  tagID="regionidTr">
      <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" condition="substr(REGIONID,3,4) != '1111' " order="regionid"/>
      <html:select property="regionid" styleId="regionidId" styleClass="inputtext" style="width:160">
        <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>

    <select style="display:none" id="rid"><option></option></select>

    <template:formTr name="����ģ��">
        <div id="info"><img src="${ctx}/images2/indicator.gif">���ڼ��ء���</div>
        <div id ="moduleDiv" style="width:400; height:300; display:none"></div>
        <script>
        	function init(){
	        	var url = "${ctx}/UsergroupAction.do?method=getModule2Edit&id=${id}&userType="+userType;
	        	var myAjax = new Ajax.Request( url , {
	                    method: 'get',
	                    asynchronous: 'true',
	                    onComplete: showTree }
	            );
            }
        	function showTree(response){
        		$("info").innerText = "";
        		${"moduleDiv"}.innerHTML=""; //��ֹ�ٴμ���ʱ���ݲ��ܼ���
        		$("update").disabled = false;
				tree=new dhtmlXTreeObject("moduleDiv","100%","100%",0);
				tree.setImagePath("${ctx}/js/dhtmlxTree/imgs/csh_bluebooks/");
				tree.enableCheckBoxes(true);
				tree.enableThreeStateCheckboxes(true);
				eval("var json="+response.responseText);
				tree.loadJSONObject(json);
				makeSize();
			}
			init();
		</script>
    </template:formTr>

    <template:formTr name="��ע">
        <html:textarea property="remark" styleClass="inputtext" style="width:160;height:40"/>
    </template:formTr>

    <template:formSubmit>
        <html:button styleClass="button" styleId="update" disabled="true" property="action" onclick="toSave()">����</html:button>
        <input type="button" class="button" onclick="toGetBack()" value="����" >
    </template:formSubmit>

  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
</body>
</html>
