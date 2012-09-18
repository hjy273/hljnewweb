<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>

<jsp:useBean id="userInfoBean" class="com.cabletech.sysmanage.beans.UserInfoBean" scope="request"/>
<%
UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
String usertype = userInfo.getType();
List list=(List)request.getAttribute("grouplists");

  String relativeUsersStr = (String) request.getAttribute("rUsersStr");
  String relativeModulesStr = (String) request.getAttribute("rModuleStr");
  String value = (String) request.getAttribute("value");
  if (value.equals("") || value == null) {
    value = "";
  }
  String type = "";
  com.cabletech.baseinfo.beans.UsergroupBean ug = (com.cabletech.baseinfo.beans.UsergroupBean) request.getAttribute("UsergroupBean");
  if(ug !=null){
    if(ug.getRegionid().substring(2,6).equals("0000")){
      type=ug.getType()+"1";
    } else{
      type=ug.getType()+"2";
    }
  }
%>
<script language="javascript">
<!--
var value = "";
function isValidForm() {
    if(UsergroupBean.groupname.value.length == 0){
        alert("名称不能为空！");
        return false;
    }

    if(document.UsergroupBean.remark.value.length>=50){
        alert("备注不能超过50个字符！");
        document.UsergroupBean.remark.value=document.UsergroupBean.remark.value.substring(0,50);
        document.UsergroupBean.remark.focus();
        return false;
    }

    return true;
}

function saveValue(){
    UsergroupBean.users.value = userFrame.getUsers();
    UsergroupBean.menus.value = moduleFrame.getMenus();

    if(isValidForm()){
      //alert(UsergroupBean.users.value);
      UsergroupBean.submit();
    }
}
//载入权限
function loadmodule(userGroup){
    value = userGroup.value;
    if(value == ""){
      alert("请选择用户组");
    }else{
      url = "${ctx}/UsergroupAction.do?method=loadMouduleList&value="+value;
      self.location.replace(url);
    }
}
var enlargeFlag = 1;
var enlargeFlag2 = 1;

function makeSize(){
    if(enlargeFlag == 0){
        document.all.moduleDiv.style.display = "none";
        document.all.enlargeIcon.src = "${ctx}/images/icon_arrow_down.gif";
        document.all.enlargeIcon.alt = "查看";
        enlargeFlag = 1;
    }else{
        document.all.moduleDiv.style.display = "";
        document.all.enlargeIcon.src = "${ctx}/images/icon_arrow_up.gif";
        document.all.enlargeIcon.alt = "收起";
        enlargeFlag = 0;
    }
}

function makeSize2(){

    if(enlargeFlag2 == 0){

        document.all.userDiv.style.display = "none";

        document.all.enlargeIcon2.src = "${ctx}/images/icon_arrow_down.gif";
        document.all.enlargeIcon2.alt = "查看";
        enlargeFlag2 = 1;
    }else{

        document.all.userDiv.style.display = "";
        document.all.enlargeIcon2.src = "${ctx}/images/icon_arrow_up.gif";
        document.all.enlargeIcon2.alt = "收起";
        enlargeFlag2 = 0;
    }
}

function prepareData(){

    prepareUser();
    prepareModule();
}
function prepareUser(){

    var usersStr = uStr.value;
    if (usersStr == null || usersStr == "null" || usersStr == ""){
        return;
    }else{
        usersArr = usersStr.split("&");
    }
    if(typeof(userFrame.usercheckbox.length)=="undefined"){
      for(var k = 0; k < usersArr.length; k++){
          if(userFrame.usercheckbox.value == usersArr[k]){
            userFrame.usercheckbox.checked = true;
            break;
          }
        }
    }else{
      for(var i = 0; i < userFrame.usercheckbox.length; i++){
        for(var k = 0; k < usersArr.length; k++){
          if(userFrame.usercheckbox[i].value == usersArr[k]){
            userFrame.usercheckbox[i].checked = true;
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

    for(var i = 0; i < moduleFrame.menucheckbox.length; i++){
        for(var k = 0; k < moduleArr.length; k++){
            if(moduleFrame.menucheckbox[i].value == moduleArr[k]){
                moduleFrame.menucheckbox[i].checked = true;
                break;
            }
        }
    }
}
function removeWelcomeStr1(){
    welcomeStr1.style.display = "none";
}

function removeWelcomeStr2(){
    welcomeStr2.style.display = "none";
}

function toGetBack(){
        var url = "${ctx}/UsergroupAction.do?method=getUserGroups";
        self.location.replace(url);

}

//-->
</script>
<body>
<template:titile value="用户组权限分配"/>
<html:form method="Post" action="/UsergroupAction.do?method=editPurview">
  <input type="hidden" name="users" value="">
  <input type="hidden" name="menus" value="">
  <html:hidden property="id"/>
  <template:formTable contentwidth="400" namewidth="250">
  <%
  if(usertype.equals("11")){
  %>
    <template:formTr name="用户组名称">
      <html:select property="id" styleId="groupname" styleClass="inputtext" style="width:160" onchange="javascript:loadmodule(this)">
        <html:option value="">请选择用户组名称</html:option>
 <%
    for(int i=0;i<list.size();i++){
        BasicDynaBean object = (BasicDynaBean)list.get(i);
        String id = (String) object.get("id");

        String groupname=(String) object.get("groupname");
        String grouptype=(String) object.get("type");
        String regionId=(String) object.get("regionid");
        //if(!regionId.substring(2).equals("0000")&&grouptype.equals("2")) continue;
%>
        <html:option value="<%=id%>"><%=groupname%></html:option>
<%
    }
 %>
      </html:select>
      <!--html:text property="groupname" styleClass="inputtext" style="width:160" maxlength="45"/-->
    </template:formTr>
  <%
  }
  else{
  %>
    <template:formTr name="用户组名称">
      <apptag:setSelectOptions valueName="groupNameCellection" tableName="usergroupmaster" columnName1="groupname" region="true" columnName2="id"/>
      <html:select property="id" styleId="groupname" styleClass="inputtext" style="width:160" onchange="javascript:loadmodule(this)">
        <html:option value="">请选择用户组名称</html:option>
        <html:options collection="groupNameCellection" property="value" labelProperty="label"/>
      </html:select>
      <!--html:text property="groupname" styleClass="inputtext" style="width:160" maxlength="45"/-->
    </template:formTr>
  <%
  }
  %>

        <logic:present name="UsergroupBean" >
            <tr class=trwhite ><td class="tdulleft">用户组类型：</td><td class="tdulright">
                <select name="type" id="typeId" Class="inputtext" disabled="disabled" style="width:160" onchange="onload()">
                    <logic:equal value="1" name="UsergroupBean"  property="type">
                        <option value="1" selected="selected">移动用户组</option>
                        <option value="2">代维用户组</option>
                    </logic:equal>
                    <logic:notEqual value="1" name="UsergroupBean"  property="type">
                        <option value="1" >移动用户组</option>
                        <option value="2" selected="selected">代维用户组</option>
                    </logic:notEqual>

                </select>
            </td></tr>
        </logic:present>

    <template:formTr name="所属区域" >
      <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
      <html:select property="regionid" styleId="regionidId" styleClass="inputtext" style="width:160" disabled="true">
        <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="管理模块">
    <%if (!value.equals("") && value != null) {    %>
      <a href="javascript:makeSize()">
        <img id="enlargeIcon" src="${ctx}/images/icon_arrow_down.gif" width="14" height="16" border="0" alt="查看">
      </a>
      <div id="welcomeStr1" style="display:">资料载入中，请稍候 ...</div>

      <div id="moduleDiv" style="height:300;display:none">
        <iframe name=moduleFrame border=0 marginWidth=0 marginHeight=0 src="${ctx}/UsergroupAction.do?method=getModule2Edit&functionFlag=e&type=<%=type%>" frameBorder=0 width="100%" scrolling="yes" height="100%">        </iframe>
      </div>
    <%} else {    %>
      <a href="javascript:alert('请选择用户组')">
        <img id="enlargeIcon" src="${ctx}/images/icon_arrow_down.gif" width="14" height="16" border="0">
      </a>
    <%}    %>
    </template:formTr>
    <template:formTr name="下属用户" >
    <%if (!value.equals("") && value != null) {    %>
      <a href="javascript:makeSize2()">
        <img id="enlargeIcon2" src="${ctx}/images/icon_arrow_down.gif" width="14" height="16" border="0" alt="查看">
      </a>
      <div id="welcomeStr2" style="display:">资料载入中，请稍候 ...</div>
      <div id="userDiv" style="height:300;display:none">
        <iframe name=userFrame border=0 marginWidth=0 marginHeight=0 src="${ctx}/UsergroupAction.do?method=getUsers2Edit&functionFlag=e&type=<%=type%>" frameBorder=0 width="100%" scrolling="yes" height="100%">        </iframe>
      </div>
    <%} else {    %>
      <a href="javascript:alert('请选择用户组')">
        <img id="enlargeIcon" src="${ctx}/images/icon_arrow_down.gif" width="14" height="16" border="0">
      </a>
    <%}    %>
    </template:formTr>
    <template:formTr name="备注">
<!--      <html:text property="remark" styleClass="inputtext" style="width:160;" maxlength="50"/>-->
        <html:textarea property="remark" styleClass="inputtext" style="width:160;height:40"/>
    </template:formTr>
    <template:formSubmit>
    <%if (!value.equals("") && value != null) {    %>
      <td>
        <html:button styleClass="button" property="action" onclick="saveValue()">更新</html:button>
      </td>
    <%}    %>
      <td>
        <input type="button" class="button" onclick="toGetBack()" value="返回">
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<input type="hidden" id="uStr" name="uStr" value="<%=relativeUsersStr%>">
<input type="hidden" id="mStr" name="mStr" value="<%=relativeModulesStr%>">
<iframe name="hiddenInfoFrame" style="display:none"></iframe>

