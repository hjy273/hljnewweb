<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<%
    UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    String type = userInfo.getDeptype().trim();
%>
<script language="javascript">
<!--
var usertype="<%=type%>";
function getDep(){

    if(userInfoBean.deptype.value == null || userInfoBean.deptype.value == ""){

        deptType = "1";

    }else{

        deptType = userInfoBean.deptype.value;
    }

    var depV = userInfoBean.regionid.value;
    var URL = "getSelect.jsp?depType="+deptType+"&selectname=deptID&regionid=" + depV;

    hiddenInfoFrame.location.replace(URL);
}

function setDepType(deptType){

    userInfoBean.deptype.value = deptType;

    if(userInfoBean.regionid.value != null && userInfoBean.regionid.value != "0"){
        getDep();
    }
    if(deptType=='2'){ deptlabel.style.display="none";conlabel.style.display="";}
    else {deptlabel.style.display="";conlabel.style.display="none";}
}

function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
function getStringByteNumber(str){return str.replace(/[^\x00-\xff]/g,"**").length;}

function isValidForm() {
    var pv = /^\d{1,16}$/;
    var len=0;
    if(document.userInfoBean.userID.value!=""&&document.userInfoBean.userID.value.trim()==""){
        alert("用户ID不能为空格!! ");
        document.userInfoBean.userID.value="";
        document.userInfoBean.userID.focus();
        return false;
	}

    document.userInfoBean.userID.value=document.userInfoBean.userID.value.trim();

    if(document.userInfoBean.userName.value!=""&&document.userInfoBean.userName.value.trim()==""){
        alert("姓名不能为空格!! ");
        document.userInfoBean.userName.value="";
        document.userInfoBean.userName.focus();
        return false;
	}

    document.userInfoBean.userName.value=document.userInfoBean.userName.value.trim();

    if(document.userInfoBean.workID.value!=""&&document.userInfoBean.workID.value.trim()==""){
        alert("工号不能为空格!! ");
        document.userInfoBean.workID.value="";
        document.userInfoBean.workID.focus();
        return false;
	}

    document.userInfoBean.workID.value=document.userInfoBean.workID.value.trim();
    if(document.userInfoBean.deptype.value=="2") document.userInfoBean.deptID.value=document.userInfoBean.consid.value;
    else document.userInfoBean.deptID.value=document.userInfoBean.deptsid.value;
	return true;
}
//-->
function onChanges(){
  k=0;
  for( i=0;i<document.all.deptworkid.options.length;i++){
    if(document.all.deptworkid.options[i].text.substring(0,6)== document.all.rId.value||document.all.rId.value.substring(2,6)=="1111"||document.all.rId.value==""){
      k++;
      document.all.dId.options.length=k;
      document.all.dId.options[k-1].value=document.all.deptworkid.options[i].value;
      document.all.dId.options[k-1].text=document.all.deptworkid.options[i].text.substring(document.all.deptworkid.options[i].text.lastIndexOf("-")+1,document.all.deptworkid.options[i].text.length);
    }
  }
  if(k==0) document.all.dId.options.length=0;
  k=0;
  for( i=0;i<document.all.conworkid.options.length;i++){
    if(document.all.conworkid.options[i].text.substring(0,6)== document.all.rId.value||document.all.rId.value.substring(2,6)=="1111"||document.all.rId.value==""){
      k++;
      document.all.cId.options.length=k;
      document.all.cId.options[k-1].value=document.all.conworkid.options[i].value;
      document.all.cId.options[k-1].text=document.all.conworkid.options[i].text.substring(document.all.conworkid.options[i].text.lastIndexOf("-")+1,document.all.conworkid.options[i].text.length);
    }
  }
  if(k==0) document.all.cId.options.length=0;
  //onChangeDeptUser();
}
</script>

<br>
<template:titile value="查询用户信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/userinfoAction.do?method=queryUserinfo">
  <template:formTable >
    <html:hidden property="deptID" value=""/>
    <template:formTr name="用&nbsp;户&nbsp;ID" isOdd="false">
      <html:text property="userID" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>
    <template:formTr name="姓&nbsp;&nbsp;&nbsp;&nbsp;名">
      <html:text property="userName" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>
    <%//if(!userInfo.getType().substring(1,2).equals("2")){%>
        <logic:notEmpty name="regionlist">
          <template:formTr name="所属区域" isOdd="false">
            <select name="regionid" class="inputtext" style="width:200px" id="rId"  onchange="onChanges()" >
              <option value="">不限</option>
              <logic:present name="regionlist">
                <logic:iterate id="reginfoId" name="regionlist">
                  <option value="<bean:write name="reginfoId" property="regionid"/>">
                  <bean:write name="reginfoId" property="regionname"/>
                  </option>
                </logic:iterate>
              </logic:present>
            </select>
          </template:formTr>
        </logic:notEmpty>

    <template:formTr name="部门类别">
      <html:hidden property="deptype" value=""/>
      <%
          if(!"2".equals(type)){
        %>
        <input type="radio" name="radiobutton"  value="1" onclick="setDepType(1)">
        内部部门
        <%}%>
      <input type="radio" name="radiobutton"   value="1" onclick="setDepType(2)">
      代维单位
    </template:formTr>

        <logic:notEmpty name="deptlist">
          <template:formTr tagID="deptlabel" name="部&nbsp;&nbsp;&nbsp;&nbsp;门" isOdd="false">
            <select name="deptsid" class="inputtext" style="width:200px" id="dId"  onchange="//onChangeCon()" >
              <option value="">不限</option>
              <logic:present name="deptlist">
                <logic:iterate id="id" name="deptlist">
                  <option value="<bean:write name="id" property="deptid"/>">
                  <bean:write name="id" property="deptname"/>
                  </option>
                </logic:iterate>
              </logic:present>
            </select>
          </template:formTr>
        </logic:notEmpty>

          <logic:present name="deptlist">
              <select name="deptworkid"   style="display:none">
                <option value="">不限</option>
                <logic:iterate id="depid" name="deptlist">
                    <option value="<bean:write name="depid" property="deptid"/>"><bean:write name="depid" property="regionid"/>--<bean:write name="depid" property="deptname"/></option>
                </logic:iterate>
              </select>
          </logic:present>

        <logic:notEmpty name="conlist">
          <template:formTr tagID="conlabel" style="display:none" name="代维单位" isOdd="false">
            <select name="consid" class="inputtext" style="width:200px" id="cId"  onchange="//onChangeCon()" >
              <option value="">不限</option>
              <logic:present name="conlist">
                <logic:iterate id="id" name="conlist">
                  <option value="<bean:write name="id" property="deptid"/>">
                  <bean:write name="id" property="deptname"/>
                  </option>
                </logic:iterate>
              </logic:present>
            </select>
          </template:formTr>
        </logic:notEmpty>

          <logic:present name="conlist">
              <select name="conworkid"   style="display:none">
                <option value="">不限</option>
                <logic:iterate id="conid" name="conlist">
                    <option value="<bean:write name="conid" property="deptid"/>"><bean:write name="conid" property="regionid"/>--<bean:write name="conid" property="deptname"/></option>
                </logic:iterate>
              </select>
          </logic:present>
    <%//}%>
    <template:formTr name="工&nbsp;&nbsp;&nbsp;&nbsp;号">
      <html:text property="workID" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>
    <template:formTr name="账号状态" isOdd="false">
      <html:select property="accountState" styleClass="inputtext" style="width:200px">
        <html:option value="">不限</html:option>
        <html:option value="1">正常</html:option>
        <html:option value="2">暂停</html:option>
        <html:option value="3">停止</html:option>
      </html:select>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">查询</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
      <td>
        <input type="button" class="button" onclick="window.history.go(-1);" value="返回" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
<script language="javascript">
<!--

deptType = userInfoBean.deptype.value;

if(deptType == "2"){
	userInfoBean.radiobutton.checked =true;
}
else if(deptType=="1")userInfoBean.radiobutton[0].checked = true;
if(usertype=="2") {setDepType(2);userInfoBean.radiobutton.checked =true;}

/-->
</script>
