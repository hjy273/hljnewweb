<%@page import="com.cabletech.baseinfo.beans.*"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<jsp:useBean id="userInfoBean" class="com.cabletech.baseinfo.beans.UserInfoBean" scope="request"/>
<%@include file="/common/header.jsp"%>

  <script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
  <script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
  <script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
  <link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">

<%
  String beanRegion = userInfoBean.getRegionid(); //用户区域
  String beanDepType = userInfoBean.getDeptype(); //用户部门类型
  String beanDepV = userInfoBean.getDeptID(); //用户部门
  String beanGroupId=userInfoBean.getUsergroupid();//用户组
  String isSuperviseUnit=userInfoBean.getIsSuperviseUnit();
  UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
  String type = userInfo.getDeptype();
  String isSupervise=userInfo.getIsSuperviseUnit();
    String regionType=userInfo.getType().substring(1,2);
    HashMap map=(HashMap)request.getSession().getAttribute("regionInfo");
    String regionId=userInfo.getRegionid();
    String regionName=(String)map.get(regionId);
%>
<input type="hidden" name="region" value="<%=beanRegion%>">
<input type="hidden" name="dep" value="<%=beanDepType%>">
<input type="hidden" name="depV" value="<%=beanDepV%>">
<input type="hidden" name="usergroups_id" value="<%=beanGroupId%>">
<script language="javascript">
<!--
var usertype="<%=userInfo.getType()%>";

function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
function getStringByteNumber(str){return str.replace(/[^\x00-\xff]/g,"**").length;}
function isPhone(phone){
	if(!/^[0-9,]/.test(phone)){
		return 1;
		}
		return 0;
	}
function isValidForm() {
    var len=0;
    if(document.userInfoBean.userID.value==""||document.userInfoBean.userID.value.trim()==""){
        alert("用户ID不能为空或者空格!! ");
        document.userInfoBean.userID.value="";
        document.userInfoBean.userID.focus();
        return false;
	}

    document.userInfoBean.userID.value=document.userInfoBean.userID.value.trim();

    if(document.userInfoBean.userName.value==""||document.userInfoBean.userName.value.trim()==""||document.userInfoBean.userName.value=="null"){
        alert("姓名不能为空或者空格或者null!! ");
        document.userInfoBean.userName.value="";
        document.userInfoBean.userName.focus();
        return false;
	}
	if(isPhone(document.userInfoBean.phone.value)||document.userInfoBean.phone.value==""){
		alert("电话号码只能为数字且多个号码用“,”分割并且不能为空！！！");
		document.userInfoBean.phone.value="";
		document.userInfoBean.phone.focus();
		return false;
	}
    document.userInfoBean.userName.value=document.userInfoBean.userName.value.trim();
    if(document.userInfoBean.deptype.value=="1") {
      document.userInfoBean.deptID.value=document.userInfoBean.deptsID.value;
    } else if(document.userInfoBean.deptype.value=="2"){
      document.userInfoBean.deptID.value=document.userInfoBean.consID.value;
     }else{
     // document.userInfoBean.deptID.value=document.userInfoBean.superconsID.value;
     }

    if(document.userInfoBean.deptID.value==""){;
        alert("部门不能为空!! ");
        document.userInfoBean.deptID.value="";
    //    document.userInfoBean.deptID.focus();
        return false;
	}

	if(document.userInfoBean.accountTerm.value==""){
		alert("账号期限不能为空!! ");
		return false;
	}
//	if(document.userInfoBean.passwordTerm.value==""){
//		alert("密码期限不能为空!! ");
//		return false;
//	}
	return true;
}

function getDepOnload(){

	var deptType = dep.value;
	var depV = region.value;

	var URL = "${ctx}/baseinfo/getSelectForEditUser.jsp?depType="+deptType+"&selectname=deptID&regionid=" + depV;

	//hiddenInfoFrame.location.replace(URL);
}

function preSet(){

	var depid = depV.value;

	for(var i = 0; i < userInfoBean.deptsID.options.length; i ++){

		if(userInfoBean.deptsID.options[i].value == depid){

			userInfoBean.deptsID.options[i].selected = true;
			break;
		}
	}

}

function getDep(){

	deptType = userInfoBean.deptype.value;

	var depV = userInfoBean.regionid.value;
	var URL = "${ctx}/baseinfo/getSelect.jsp?depType="+deptType+"&selectname=deptID&regionid=" + depV;

	//hiddenInfoFrame.location.replace(URL);
}

function setDepType(deptType){
	userInfoBean.deptype.value = deptType;
	if(userInfoBean.regionid.value != null && userInfoBean.regionid.value != "0"){
		//getDep();
	}
    //if(usertype.substring(1,2)=="2")
    onChangeGroup();
    if(deptType=='1'){
      deptlabel.style.display="";
      conlabel.style.display="none";
     // superconlabel.style.display="none";
    }else if(deptType=='2'){
      deptlabel.style.display="none";
      conlabel.style.display="";
     // superconlabel.style.display="none";
    }else if(deptType=='3'){
      deptlabel.style.display="none";
      conlabel.style.display="none";
      //superconlabel.style.display="";
    }
}

function toGetBack(){
        var url = "${ctx}/userinfoAction.do?method=queryUserinfo&userName=";
        self.location.replace(url);

}

function onChangeGroup(){
  k=0;
  var value=document.userInfoBean.deptype.value;
  if(value=="3") value="2";
  for( i=0;i<document.userInfoBean.ugpworkid.options.length;i++){
    var v=document.userInfoBean.ugpworkid.options[i].value;
    if(document.userInfoBean.ugpworkid.options[i].text.substring(0,6)== document.all.rId.value
    ||document.all.rId.value.substring(2,6)=="1111"
    ||v.substring(0,v.indexOf("-"))==document.all.usergroups_id.value){
      //if(usertype.substring(1,2)=="1"||(usertype.substring(1,2)=="2"&&value==v.substring(v.lastIndexOf("-")+1,v.length))){
      if(value==v.substring(v.lastIndexOf("-")+1,v.length)||v.substring(0,v.indexOf("-"))==document.all.usergroups_id.value){
        k++;
        document.userInfoBean.uId.options.length=k;
        document.userInfoBean.uId.options[k-1].value=v.substring(0,v.indexOf("-"));
        document.userInfoBean.uId.options[k-1].text=document.userInfoBean.ugpworkid.options[i].text.substring(8,document.all.ugpworkid.options[i].text.length);
      }
    }
  }
  if(k==0)
    document.userInfoBean.uId.options.length=0;
  onChangeCon();
}
function onChangeCon(){
  k=0;
  for( i=0;i<document.all.deptworkid.options.length;i++){
    if(document.all.deptworkid.options[i].text.substring(0,6)== document.all.rId.value||document.all.rId.value.substring(2,6)=="1111"){
      k++;
      document.all.dId.options.length=k;
      document.all.dId.options[k-1].value=document.all.deptworkid.options[i].value;
      document.all.dId.options[k-1].text=document.all.deptworkid.options[i].text.substring(8,document.all.deptworkid.options[i].text.length);
    }
  }
  if(k==0) document.all.dId.options.length=0;
  k=0;
  for( i=0;i<document.all.conworkid.options.length;i++){
    if(document.all.conworkid.options[i].text.substring(0,6)== document.all.rId.value||document.all.rId.value.substring(2,6)=="1111"){
      k++;
      document.all.cId.options.length=k;
      document.all.cId.options[k-1].value=document.all.conworkid.options[i].value;
      document.all.cId.options[k-1].text=document.all.conworkid.options[i].text.substring(8,document.all.conworkid.options[i].text.length);
    }
  }
  if(k==0) document.all.cId.options.length=0;
}
function parseDate(date,sep){
  var d=date.split(sep);
  var selectDate=new Date();
  if(d[1].substring(0,1)=="0") d[1]=d[1].substring(1,2);
  if(d[2].substring(0,1)=="0") d[2]=d[2].substring(1,2);
  selectDate.setFullYear(parseInt(d[0]));
  selectDate.setMonth(parseInt(d[1])-1);
  selectDate.setDate(parseInt(d[2]));
  return selectDate;
}
function compareDate(date){
  var now=new Date();
  if(now>date) return true;
  else return false;
}
function vertifyDate(){
  if(document.userInfoBean.accountTerm.value!=""){
    var d=parseDate(document.userInfoBean.accountTerm.value,"/");
    if(compareDate(d)){
      alert("帐号期限不应该能选择过去的日期");
      document.userInfoBean.accountTerm.value="";
    }
  }
}
//-->
</script>
<apptag:checkpower thirdmould="70504" ishead="5">
  <jsp:forward page="/globinfo/powererror.jsp"/>
</apptag:checkpower>
<br>
<template:titile value="修改用户信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/userinfoAction.do?method=updateUserinfo" styleId="edituserinfo">
  <template:formTable>
  <html:hidden property="deptID" value=""/>
    <template:formTr name="用户ID" isOdd="false">
      <c:out value="${userInfoBean.userID}"></c:out>
      <html:hidden property="userID" name="userInfoBean"/>
      <!--<html:text readonly="true" property="userID" styleClass="inputtext" style="width:200px" maxlength="20"/>-->
    </template:formTr>
    <template:formTr name="姓&nbsp;&nbsp;&nbsp;&nbsp;名">
      <html:text property="userName" styleClass="inputtext" style="width:200px" maxlength="20"/>&nbsp;&nbsp;<font color="red">*</font>
    </template:formTr>


<%
  if(userInfo.getType().equals("11")){
%>
        <logic:notEmpty name="regionlist">
          <template:formTr name="所属区域" isOdd="false">
            <select name="regionid" class="inputtext" style="width:200px" id="rId"  onchange="onChangeGroup()" >
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
<%
  }
  else{
%>
    <template:formTr name="所属区域" isOdd="false" style="display:none">
      <html:select property="regionid" styleId="rId" styleClass="inputtext" style="width:200px" onchange="getDep()">
        <html:option value="<%=regionId%>"><%=regionName %></html:option>
      </html:select>
    </template:formTr>
 <%} %>
    <template:formTr name="部门类别">
      <html:hidden property="deptype" value="1"/>
      <html:hidden property="isSuperviseUnit" value="0"/>
      <input type="radio" name="radiobutton" value="1" onclick="setDepType(1)">
      内部部门
      <input type="radio" name="radiobutton" value="1" onclick="setDepType(2)">
      代维单位
    
    </template:formTr>
 
        <logic:notEmpty name="deptlist">
          <template:formTr tagID="deptlabel" name="部&nbsp;&nbsp;&nbsp;&nbsp;门" >
            <select name="deptsID" class="inputtext" style="width:200px" id="dId"  onchange="//onChangeCon()" >
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
                <logic:iterate id="depid" name="deptlist">
                    <option value="<bean:write name="depid" property="deptid"/>"><bean:write name="depid" property="regionid"/>--<bean:write name="depid" property="deptname"/></option>
                </logic:iterate>
              </select>
          </logic:present>
        <logic:notEmpty name="conlist">
          <tr class=trcolor id=conlabel style=display:none ><td id="conLabel" class="tdulleft">代维单位：</td><td class="tdulright">
            <select name="consID" class="inputtext" style="width:200px" id="cId"  onchange="//onChangeCon()" >
              <logic:present name="conlist">
                <logic:iterate id="id" name="conlist">
                  <option value="<bean:write name="id" property="contractorid"/>">
                  <bean:write name="id" property="contractorname"/>
                  </option>
                </logic:iterate>
              </logic:present>
            </select>
          </td></tr>
        </logic:notEmpty>

          <logic:notEmpty name="superconList">
          <tr class=trwhite id=superconlabel style=display:none ><td id="superconLabel" class="tdulleft">监理单位：</td><td class="tdulright">
            <select name="superconsID" class="inputtext" style="width:200px" id="supercId"  onchange="//onChangeCon()" >
              <logic:present name="superconList">
                <logic:iterate id="id" name="superconList">
                  <option value="<bean:write name="id" property="contractorid"/>">
                  <bean:write name="id" property="contractorname"/>
                  </option>
                </logic:iterate>
              </logic:present>
            </select>
          </td></tr>  
        </logic:notEmpty>

       <logic:notEmpty name="ugplist">
          <template:formTr name="所 属 组">
            <select name="usergroupid" class="inputtext" style="width:200px" id="uId"  onchange="//onChangeCon()" >
              <logic:present name="ugplist">
                <logic:iterate id="id" name="ugplist">
                  <option value="<bean:write name="id" property="id"/>">
                  <bean:write name="id" property="groupname"/>
                  </option>
                </logic:iterate>
              </logic:present>
            </select>
          </template:formTr>
        </logic:notEmpty>

        <logic:present name="ugplist">
              <select name="ugpworkid"   style="display:none">
                <logic:iterate id="ugpid" name="ugplist">
                    <option value="<bean:write name="ugpid" property="id"/>--<bean:write name="ugpid" property="type" />"><bean:write name="ugpid" property="regionid"/>--<bean:write name="ugpid" property="groupname"/></option>
                </logic:iterate>
              </select>
          </logic:present>

          <logic:present name="conlist">
              <select name="conworkid"   style="display:none">
                <logic:iterate id="conid" name="conlist">
                    <option value="<bean:write name="conid" property="contractorid"/>"><bean:write name="conid" property="regionid"/>--<bean:write name="conid" property="contractorname"/></option>
                </logic:iterate>
              </select>
          </logic:present>
          
          
    <template:formTr name="工&nbsp;&nbsp;&nbsp;&nbsp;号" isOdd="false">
      <html:text property="workID" styleClass="inputtext" style="width:200px" maxlength="8"/>
    </template:formTr>
    <!--template:formTr name="密码">
      <html:text property="password" styleClass="inputtext" style="width:200px" maxlength="45"/>
    <-->

    <template:formTr name="帐号期限">
       <html:text readonly="true" onchange="vertifyDate()" property="accountTerm" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})"  style="width:200px" maxlength="45"/>&nbsp;&nbsp;<font color="red">*</font>
    </template:formTr>
    <!--template:formTr name="密码期限"-->
      <!--html:text property="passwordTerm" readonly="true" styleClass="inputtext" style="width:200px" maxlength="45"/>
    <!--/template:formTr-->
    <template:formTr name="账号状态" isOdd="false">
      <html:select property="accountState" styleClass="inputtext" style="width:200px">
        <html:option value="1">正常</html:option>
        <html:option value="2">暂停</html:option>
        <html:option value="3">停止</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="手&nbsp;机&nbsp;号">
    	<html:textarea property="phone" styleClass="inputtext required" style="width:200px;height:50px"></html:textarea>&nbsp;&nbsp;<font color="red">* 多个手机好采用半角逗号","分割。</font>
    </template:formTr>
    <template:formTr name="电子邮件" isOdd="false">
      <html:text property="email" styleClass="inputtext" style="width:200px" maxlength="30"/>
    </template:formTr>
    <template:formTr name="职&nbsp;&nbsp;&nbsp;&nbsp;位">
      <html:text property="position" styleClass="inputtext" style="width:200px" maxlength="10"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">更新</html:submit>
      </td>
      <td>
        <input type="button" class="button" onclick="javascript:history.back();" value="返回">
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
<script language="javascript">
<!--

document.userInfoBean.accountTerm.onpropertychange=function(){
  vertifyDate();
}
getDepOnload();

deptType = "<%=beanDepType%>";//userInfoBean.deptype.value;
if(deptType == "3"&&typeof(userInfoBean.radiobutton)!="undefined"&&userInfoBean.radiobutton.length>=2){
	userInfoBean.radiobutton[2].checked = true;
    setDepType(3);
}else if(deptType == "2"&&typeof(userInfoBean.radiobutton)!="undefined"&&userInfoBean.radiobutton.length>=2){
	userInfoBean.radiobutton[1].checked = true;
    setDepType(2);
}
else if(typeof(userInfoBean.radiobutton)!="undefined"&&userInfoBean.radiobutton.length>=2){
    setDepType(1);
}
if(deptType == "1"&&typeof(userInfoBean.radiobutton)!="undefined"&&userInfoBean.radiobutton.length>=2){
	userInfoBean.radiobutton[0].checked = true;
   // setDepType(1);
}
//if(usertype=="11")
//  setDepType(1);

var regionid=document.all.region.value;
var dept_id=document.all.depV.value;
var ugpid=document.all.usergroups_id.value;
for(i=0;i<document.all.regionid.options.length;i++){
  if(regionid==document.all.regionid.options[i].value)
    document.all.regionid.options[i].selected=true;
}
onChangeGroup();
for(i=0;i<document.all.usergroupid.options.length;i++){
  if(ugpid==document.all.usergroupid.options[i].value)
    document.all.usergroupid.options[i].selected=true;
}
for(i=0;i<document.all.deptsID.options.length&&document.all.deptype.value=="1";i++){
  if(dept_id==document.all.deptsID.options[i].value)
    document.all.deptsID[i].selected=true;
}
for(i=0;i<document.all.consID.options.length&&document.all.deptype.value=="2";i++){
  if(dept_id==document.all.consID.options[i].value)
    document.all.consID[i].selected=true;
}
//for(i=0;i<document.all.superconsID.options.length&&document.all.deptype.value=="3";i++){
 // if(dept_id==document.all.superconsID.options[i].value)
   // document.all.superconsID[i].selected=true;
//}
/-->
</script>

  <script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('edituserinfo', {immediate : true, onFormValidate : formCallback});
	  </script>
