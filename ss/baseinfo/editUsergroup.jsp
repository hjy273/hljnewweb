<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<jsp:useBean id="userInfoBean" class="com.cabletech.baseinfo.beans.UserInfoBean" scope="request"/>
<%
UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
String usertype = userInfo.getType();

String relativeUsersStr = (String)request.getAttribute("rUsersStr");
%>
<%
  String msg = "";
  if (request.getAttribute("innerMsg") != null) {
    msg = (String) request.getAttribute("innerMsg");
  }
%>
<html>
	<head>

<script language="javascript" type="text/javascript">

function isValidForm() {
    if(document.UsergroupBean.groupname.value.length == 0||document.UsergroupBean.groupname.value==""||document.UsergroupBean.groupname.value=="null"){
        alert("用户组名称不能为空或者空格或者null！");
        document.UsergroupBean.groupname.value="";
        document.UsergroupBean.groupname.focus();
        return false;
    }
    document.UsergroupBean.groupname.value=document.UsergroupBean.groupname.value;
    if(document.UsergroupBean.remark.value.length>=50){
        alert("备注不能超过50个字符！");
        document.UsergroupBean.remark.value=document.UsergroupBean.remark.value.substring(0,50);
        document.UsergroupBean.remark.focus();
        return false;
    }
	return true;
}

function saveValue(){
    document.UsergroupBean.menus.value = document.getElementById('moduleFrameId').contentWindow.getMenus();
    document.UsergroupBean.users.value = document.getElementById('userFrameId').contentWindow.getUsers();
    if(isValidForm()){
        document.UsergroupBean.submit();
    }
}

var enlargeFlag = 1;
var enlargeFlag2 = 1;

function makeSize(){
    if(enlargeFlag == 0){
    	document.getElementById("moduleDiv").style.display = "none";
        document.getElementById("enlargeIcon").src = "${ctx}/images/icon_arrow_down.gif";
        document.getElementById("enlargeIcon").alt = "收起";
        enlargeFlag = 1;
    }else{
    	document.getElementById("moduleDiv").style.display = "";
        document.getElementById("enlargeIcon").src = "${ctx}/images/icon_arrow_up.gif";
        document.getElementById("enlargeIcon").alt = "查看";
        enlargeFlag = 0;
    }
}

function makeSize2(){
    if(enlargeFlag2 == 0){
    	document.getElementById("userDiv").style.display = "none";
    	document.getElementById("enlargeIcon2").src = "${ctx}/images/icon_arrow_down.gif";
    	document.getElementById("enlargeIcon2").alt = "收起";
        enlargeFlag2 = 1;
    }else{
    	document.getElementById("userDiv").style.display = "";
    	document.getElementById("enlargeIcon2").src = "${ctx}/images/icon_arrow_up.gif";
    	document.getElementById("enlargeIcon2").alt = "查看";
        enlargeFlag2 = 0;
    }
}

function prepareData(){
    prepareUser();
    prepareModule();
}

function prepareUser(){
    var usersStr ;
    if(typeof(document.getElementById("uStr").value)=="undefined"){
       //alert("undefined");
       return;
    }else
       usersStr = document.getElementById("uStr").value;
    if (usersStr == null || usersStr == "null" || usersStr == ""){
        return;
    }else{
        usersArr = usersStr.split("&");
    }
    if(typeof(document.frames["userFrameId"].document.getElementById('usercheckbox').length)=="undefined"){
      for(var k = 0; k < usersArr.length; k++){
          if(document.frames["userFrameId"].document.getElementById('usercheckbox').value == usersArr[k]){
            document.frames["userFrameId"].document.getElementById('usercheckbox').checked = true;
            break;
          }
        }
    }else{
      for(var i = 0; i < document.frames["userFrameId"].document.getElementById('usercheckbox').length; i++){
        for(var k = 0; k < usersArr.length; k++){
          if(document.frames["userFrameId"].document.getElementById('usercheckbox')[i].value == usersArr[k]){
            document.frames["userFrameId"].document.getElementById('usercheckbox')[i].checked = true;
            break;
          }
        }
      }
    }
}

function prepareModule(){
    var moduleStr = mStr.value;
    if (moduleStr == null || moduleStr == "null" || moduleStr == ""){
        return;
    }else{
        moduleArr = moduleStr.split("&");
    }

    for(var i = 0; i < document.getElementById("moduleFrameId").menucheckbox.length; i++){
        for(var k = 0; k < moduleArr.length; k++){
            if(document.getElementById("moduleFrameId").menucheckbox[i].value == moduleArr[k]){
                document.getElementById("moduleFrameId").menucheckbox[i].checked = true;
                break;
            }
        }
    }
}

function removeWelcomeStr1(){
	document.getElementById("welcomeStr1").style.display = "none";
}

function removeWelcomeStr2(){
	document.getElementById("welcomeStr2").style.display = "none";
}

function toGetBack(){
	var url = "${ctx}/UsergroupAction.do?method=getUserGroups";
	self.location.replace(url);
}
/*
function init(){
    for(i=0;i<document.getElementById("regionid").length;i++){
    	document.getElementById("rid").options.length=i;
    	document.getElementById("rid").options[i]=new Option(document.getElementById("regionid").options[i].text,document.getElementById("regionid").options[i].value);
    }
}*/

function init(usertype){
	var id = document.getElementById("id").value;
	var type =document.getElementById("typeId").value;
    var grade = 2;
    var regionid = document.getElementById("regionidId").options[document.getElementById("regionidId").options.selectedIndex].value;
    /* var regionid = document.getElementById("regionidId").options[document.getElementById("regionidId").options.selectedIndex].value;
    document.getElementById("welcomeStr1").style.display="";
    document.getElementById("welcomeStr2").style.display="";
      
    k=0;
    for(i=0;i<document.getElementById("rid").options.length;i++){
        if(document.getElementById("rid").options[i].value==""){
           continue;
        }else{
           k++;
           document.getElementById("regionid").options.length=k;
           document.getElementById("regionid").options[k-1].value=document.getElementById("rid").options[i].value;
           document.getElementById("regionid").options[k-1].text=document.getElementById("rid").options[i].text;
           if(regionid==document.getElementById("regionid").options[k-1].value){
        	   document.getElementById("regionid").options[k-1].selected=true;
           }
        }
        if( k==0 ){
        	document.getElementById("regionidId").options.length=0;
        }
    } */
	
    if(regionid.substring(2,6)=='0000'){
      	grade=1;
      	document.getElementById("moduleFrameId").src="${ctx}/UsergroupAction.do?method=getModule2Edit&id="+id+"&functionFlag=e&type="+type;//${ctx}/UsergroupAction.do?method=getModule2Edit&functionFlag=e
    }else{
    	document.getElementById("moduleFrameId").src="${ctx}/UsergroupAction.do?method=getModule2Edit&id="+id+"&functionFlag=e&type="+type+grade;//${ctx}/UsergroupAction.do?method=getModule2Edit&functionFlag=e
    }
    document.getElementById("userFrameId").src ="${ctx}/UsergroupAction.do?method=getUsers2Edit&id="+id+"&functionFlag=e&type="+type;//${ctx}/UsergroupAction.do?method=getUsers2Edit&functionFlag=e
    if(usertype.substring(1,2)=="2") 
    	document.getElementById("regionidTr").style.display="none";
    else 
    	document.getElementById("regionidTr").style.display="";
}
</script>
</head>
<body onload="init('<%=usertype%>')">
<template:titile value="更新用户组"/>
<span id="msgSpan"><font color="red"><%=msg%></font></span>
<html:form method="Post" action="/UsergroupAction.do?method=editUsergroup">
    <input type="hidden" name="users" value="">
    <input type="hidden" name="menus" value="">
    <html:hidden property="id" styleId="id" />
    <template:formTable contentwidth="400" namewidth="250">
    	<template:formTr name="用户组名称">
        	<html:text property="groupname" styleClass="inputtext" style="width:160px" maxlength="45"/>&nbsp;&nbsp;<font color="red">*</font>
    	</template:formTr>
	    <template:formTr name="用户组类型" isOdd="false">
	        <select name="type" id="typeId" Class="inputtext" style="width:160px" onchange="init('<%=usertype%>')">
	            <logic:equal value="1" name="UsergroupBean"  property="type">
	                <option value="1" selected="selected">移动</option>
	                <option value="2">代维</option>
	            </logic:equal>
	            <logic:equal value="2" name="UsergroupBean"  property="type">
	                <option value="1" >移动</option>
	                <option value="2" selected="selected">代维</option>
	            </logic:equal>
	        </select>
	    </template:formTr>
	    <template:formTr name="所属区域" isOdd="false" tagID="regionidTr">
	        <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" condition="substr(REGIONID,3,4) != '1111' " order="regionid"/>
	        <html:select property="regionid" styleId="regionidId"  onchange="init('<%=usertype%>')" styleClass="inputtext" style="width:160px">
	        	<html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
	        </html:select>
	    </template:formTr>
    	<select style="display:none" id="rid"><option></option></select>
	    <template:formTr name="管理模块">
	        <a href="javascript:makeSize()"><img id="enlargeIcon" src="${ctx}/images/icon_arrow_down.gif" width="14px" height="16px" border="0" alt="查看"></a>
        	<div id = "welcomeStr1" style="display:"> 资料载入中，请稍候 ... </div>
	        <div id ="moduleDiv" style="height:300px;display:none">
	            <iframe name=moduleFrame  id="moduleFrameId" border=0 marginWidth=0 marginHeight=0 src="" frameBorder=0 width="100%" scrolling="yes" height="100%"></iframe>
	        </div>
	    </template:formTr>
	    <template:formTr name="下属用户" isOdd="false">
	        <a href="javascript:makeSize2()"><img id="enlargeIcon2" src="${ctx}/images/icon_arrow_down.gif" width="14px" height="16px" border="0" alt="查看"></a>
	        <div id = "welcomeStr2" style="display:"> 资料载入中，请稍候 ... </div>
	        <div id ="userDiv" style="height:300px;display:none">
	          <iframe name=userFrame  id="userFrameId" border=0 marginWidth=0 marginHeight=0 src="" frameBorder=0 width="100%" scrolling="yes" height="100%">
	            </iframe>
	        </div>
	    </template:formTr>
	    <template:formTr name="备注">
	        <html:textarea property="remark" styleClass="inputtext" style="width:160px;height:40px"/>
	    </template:formTr>
	    <template:formSubmit>
	        <td>
	        	<html:button styleClass="button" property="action" onclick="return saveValue()">更新</html:button>
	        </td>
	        <td>
	        	<input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="返回" >
	        </td>
	    </template:formSubmit>
    </template:formTable>
</html:form>
<input type="hidden" id="uStr" name="uStr" value="<%=relativeUsersStr%>">
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
</body>
</html>