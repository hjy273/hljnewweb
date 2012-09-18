<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<jsp:useBean id="userInfoBean" class="com.cabletech.baseinfo.beans.UserInfoBean" scope="request"/>
<%
UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
String usertype = userInfo.getType();
%>
<%
  String msg = "";
  if (request.getAttribute("innerMsg") != null) {
    msg = (String) request.getAttribute("innerMsg");
  }
%>
<script language="javascript" type="">
var usertype="<%=usertype%>";

function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}

function isValidForm() {
    var len=0;var str="";
    if(UsergroupBean.groupname.value.length == 0||document.UsergroupBean.groupname.value.trim()==""||document.UsergroupBean.groupname.value.trim()=="null"){
        alert("用户组名称不能为空或者空格或“null”字符串！");
        document.UsergroupBean.groupname.value="";
        document.UsergroupBean.groupname.focus();
        return false;
    }

    document.UsergroupBean.groupname.value=document.UsergroupBean.groupname.value.trim();
    if(document.UsergroupBean.remark.value.length>50){
        alert("备注不能超过50个字符！");
        document.UsergroupBean.remark.value=document.UsergroupBean.remark.value.substring(0,50);
        document.UsergroupBean.remark.focus();
        return false;
    }

    return true;
}

function saveValue(){

  if(userFrame.document.body.innerHTML!="")
    UsergroupBean.users.value = userFrame.getUsers();
  if(moduleFrame.document.body.innerHTML!="")
    UsergroupBean.menus.value = moduleFrame.getMenus();

    if(isValidForm())
        UsergroupBean.submit();
}

var enlargeFlag = 1;
var enlargeFlag2 = 1;

function makeSize(){
    if(enlargeFlag == 0){
        document.all.moduleDiv.style.display = "none";
        document.all.enlargeIcon.src = "${ctx}/images/icon_arrow_down.gif";
        document.all.enlargeIcon.alt = "收起";
        enlargeFlag = 1;
    }else{
        document.all.moduleDiv.style.display = "";
        document.all.enlargeIcon.src = "${ctx}/images/icon_arrow_up.gif";
        document.all.enlargeIcon.alt = "查看";
        enlargeFlag = 0;
    }
}

function makeSize2(){

    if(enlargeFlag2 == 0){

        document.all.userDiv.style.display = "none";

        document.all.enlargeIcon2.src = "${ctx}/images/icon_arrow_down.gif";
        document.all.enlargeIcon2.alt = "收起";
        enlargeFlag2 = 1;
    }else{

        document.all.userDiv.style.display = "";
        document.all.enlargeIcon2.src = "${ctx}/images/icon_arrow_up.gif";
        document.all.enlargeIcon2.alt = "查看";
        enlargeFlag2 = 0;
    }
}

function removeWelcomeStr1(){
    welcomeStr1.style.display = "none";
}

function removeWelcomeStr2(){
    welcomeStr2.style.display = "none";
}

function changeGroup(){
    k=0;
    for(i=0;i<document.all.rid.options.length;i++){
        //if(usertype=="11"&&document.all.typeId.options.selectedIndex==1&&document.all.rid.options[i].value.substring(2,6)!='0000')
        //   continue;
        //else if(usertype=="11"&&document.all.typeId.options.selectedIndex==1){
        if(usertype=="11"){
           regionid=document.all.rid.options[i].value;
           k++;
           document.all.regionid.options.length=k;
           document.all.regionid.options[k-1].value=document.all.rid.options[i].value;
           document.all.regionid.options[k-1].text=document.all.rid.options[i].text;
           //if(regionid==document.all.regionid.options[k-1].value)
           //  document.all.regionid.options[k-1].selected=true;
        }
        else if(document.all.rid.options[i].value=="")
           continue;
        else{
           k++;
           document.all.regionid.options.length=k;
           document.all.regionid.options[k-1].value=document.all.rid.options[i].value;
           document.all.regionid.options[k-1].text=document.all.rid.options[i].text;
           //if(regionid==document.all.regionid.options[k-1].value)
           //  document.all.regionid.options[k-1].selected=true;
        }
        if(k==0)
           document.all.regionidId.options.length=0;
    }
    regionChange();
}
function regionChange(){

  var regionid = document.all.regionidId.options[document.all.regionidId.options.selectedIndex].value;
  var type = document.all.typeId.value;
  var grade='2';

  if(regionid ==""&&usertype.substring(1,2)=="1"){
      welcomeStr1.style.display = "none";
      welcomeStr12.style.display = "";
      welcomeStr2.style.display = "none";
     welcomeStr22.style.display = "";
    document.all.moduleFrameId.src ="";
      document.all.userFramId.src ="";

  }else{
      welcomeStr1.style.display = "";
      welcomeStr12.style.display = "none";
      welcomeStr2.style.display = "";
      welcomeStr22.style.display = "none";

    if(regionid.substring(2,6)=='0000'){
      grade=1;
      document.all.moduleFrameId.src ="${ctx}/UsergroupAction.do?method=getModule2Edit&type=" + type;
    }else{
      document.all.moduleFrameId.src ="${ctx}/UsergroupAction.do?method=getModule2Edit&type=" + type + grade;
    }
    document.all.userFramId.src ="${ctx}/UsergroupAction.do?method=getUsers2Edit&type=" + type;

  }

}


function init(){
  for(i=0;i<document.all.regionid.length;i++){
      document.all.rid.options.length=i+1;
      document.all.rid.options[i]=new Option(document.all.regionid.options[i].text,document.all.regionid.options[i].value);
  }
  if(usertype.substring(1,2)=="2") regionChange();
}

</script>
<body onload="init()">
<template:titile value="增加用户组"/>
<span id="msgSpan"><font color="red"><%=msg%></font></span>
<html:form method="Post" action="/UsergroupAction.do?method=addUsergroup">

    <input type="hidden" name="users" value="">
    <input type="hidden" name="menus" value="">

  <template:formTable>
    <template:formTr name="用户组名称">
      <html:text property="groupname" styleClass="inputtext" style="width:160px" maxlength="45"/>&nbsp;&nbsp;<font color="red">*</font>
    </template:formTr>
    <template:formTr name="用户组类型" isOdd="false">
      <html:select property="type"  styleId="typeId" onchange="changeGroup()" styleClass="inputtext" style="width:160px">
              <html:option value="1">移动	</html:option>
              <html:option value="2">代维	</html:option>
      </html:select>
    </template:formTr>
<%
if(usertype.substring(1,2).equals("1")){
%>
     <template:formTr name="用户组区域" isOdd="false">
      <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" condition="substr(REGIONID,3,4) != '1111' " order="regionid"/>
      <html:select property="regionid"  styleId="regionidId" styleClass="inputtext" style="width:160px" onchange="regionChange()">
        <html:option value="">请选择区域 </html:option>
        <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
<%
}
else{
%>
     <template:formTr name="用户组区域" isOdd="false" style="display:none">
      <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" condition="substr(REGIONID,3,4) != '1111' " order="regionid"/>
      <html:select property="regionid"  styleId="regionidId" styleClass="inputtext" style="width:160px" onchange="regionChange()">
        <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
      </html:select>
     </template:formTr>
<%
}
%>

    <select style="display:none" id="rid"><option></option></select>

    <template:formTr name="管理模块">
      <a href="javascript:makeSize()">
        <img id="enlargeIcon" src="${ctx}/images/icon_arrow_down.gif" width="14px" height="16px" border="0" alt="查看"></a>
        <div id = "welcomeStr1" style="display:none"> 资料载入中，请稍候 ... </div>
        <div id = "welcomeStr12" style="display:"> 选择区域后载入资料 ... </div>
        <div id ="moduleDiv" style="height:300px;display:none">
          <iframe name=moduleFrame id="moduleFrameId" border=0 marginWidth=0 marginHeight=0 src="" frameBorder=0 width="100%" scrolling="yes" height="100%">
            </iframe>
        </div>

    </template:formTr>

    <template:formTr name="下属用户" isOdd="false">
        <a href="javascript:makeSize2()">
        <img id="enlargeIcon2" src="${ctx}/images/icon_arrow_down.gif" width="14px" height="16px" border="0" alt="查看"></a>
        <div id = "welcomeStr2" style="display:none"> 资料载入中，请稍候 ... </div>
        <div id = "welcomeStr22" style="display:"> 选择区域后载入资料 ... </div>

        <div id ="userDiv" style="height:300px;display:none">
          <iframe name=userFrame id="userFramId" border=0 marginWidth=0 marginHeight=0 src="" frameBorder=0 width="100%" scrolling="yes" height="100%">
            </iframe>
        </div>

    </template:formTr>

    <template:formTr name="备注">
<!--      <html:text property="remark" styleClass="inputtext" style="width:160;" maxlength="50"/>-->
        <html:textarea property="remark" styleClass="inputtext" style="width:250px;height:40px"/>
    </template:formTr>

    <template:formSubmit>
      <td>
        <html:button styleClass="button" property="action" onclick="saveValue()">增加</html:button>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>

</html:form>

<iframe name="hiddenInfoFrame" style="display:none"></iframe>
</body>
