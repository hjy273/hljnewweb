<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<jsp:useBean id="userInfoBean" class="com.cabletech.baseinfo.beans.UserInfoBean" scope="request"/>
  <script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
  <script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
  <script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
  <link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">



<%
  String msg = "";
  if (request.getAttribute("innerMsg") != null) {
    msg = (String) request.getAttribute("innerMsg");
  }
%>
<%
  String beanRegion = "";
  String beanDepType = "";
  String beanDepV = "";
  beanRegion = userInfoBean.getRegionid(); //�û�����
  beanDepType = userInfoBean.getDeptype(); //�û���������
  beanDepV = userInfoBean.getDeptID(); //�û�����
  if (beanDepType == null)
    beanDepType = "1";
	UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    String type = userInfo.getDeptype();

    String regionType=userInfo.getType().substring(1,2);
    HashMap map=(HashMap)request.getSession().getAttribute("regionInfo");
    String regionId=userInfo.getRegionid();
    String regionName=(String)map.get(regionId);


%>
<input type="hidden" name="region" value="<%=beanRegion%>">
<input type="hidden" name="dep" value="<%=beanDepType%>">
<input type="hidden" name="depV" value="<%=beanDepV%>">
<script language="javascript">
//<!--
var usertype="<%=userInfo.getType()%>";
var indexGroup=0;

function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
function getStringByteNumber(str){return str.replace(/[^\x00-\xff]/g,"**").length;}
function isUsername(userID){
	var username=userID;
	var re = /[!@#$%\^&\*\(\)]/g; 
	var err = "";
	while ((arr = re.exec(username)) != null)
       err += arr;
	if (err != "") {
		alert("�û�ID���ܺ���" + err.replace(/&/g, "&"));
		return 1;
	} else if (/ /g.test(username)) {
		alert("�û�ID���ܺ��пո�");
		return 1;
	}
	if(! /^.{3,20}$/.test( username ) ){
		alert("�û�ID����Ϊ��λ��");
		return 3;
	}
	if(! /^[\w_]*$/.test( username ) ){
		alert("�û�ID����ȷ��ֻ��������ĸ�����֡��»�����ɣ�");
		return 1;
	}
	if(! /^([a-z]|[A-Z])[0-9a-zA-Z_]{1,19}$/.test( username ) ){
		alert("�û�ID����ʼ�ַ�������Ӣ����ĸ��");
		return 2;
	}
	return 0;
}
function isPhone(phone){
	if(!/^[0-9,]/.test(phone)){
		return 1;
		}
		return 0;
	}
function isValidForm() {
    var pv = /^\d{1,16}$/;
    var len=0;
    var userID = userInfoBean.userID.value;
	var result=isUsername(userID);
	if(result!=0){
		return false;
	}
    if(document.userInfoBean.userID.value==""||document.userInfoBean.userID.value.trim()==""||document.userInfoBean.userID.value=="null"){
        alert("�û�ID����Ϊ�ջ��߿ո����null!! ");
        document.userInfoBean.userID.value="";
        document.userInfoBean.userID.focus();
        return false;
	}

    document.userInfoBean.userID.value=document.userInfoBean.userID.value.trim();

    if(document.userInfoBean.userName.value==""||document.userInfoBean.userName.value.trim()==""||document.userInfoBean.userName.value=="null"){
        alert("��������Ϊ�ջ��߿ո����null!! ");
        document.userInfoBean.userName.value="";
        document.userInfoBean.userName.focus();
        return false;
	}

    document.userInfoBean.userName.value=document.userInfoBean.userName.value.trim();
    if(document.userInfoBean.deptype.value=="1") {
      document.userInfoBean.deptID.value=document.userInfoBean.deptsID.value;
    } else if(document.userInfoBean.deptype.value=="2"){
      document.userInfoBean.deptID.value=document.userInfoBean.consID.value;
     }else{
      document.userInfoBean.deptID.value=document.userInfoBean.superconsID.value;
     }
    
    if(document.userInfoBean.deptID.value==""){
        alert("���Ų���Ϊ��!! ");
        document.userInfoBean.deptID.value="";
        document.userInfoBean.deptID.focus();
        return false;
	}
	if(document.userInfoBean.password.value==""||document.userInfoBean.password.value.trim()==""){
		alert("���벻��Ϊ�ջ��߿ո�!! ");
        document.userInfoBean.password.value="";
        document.userInfoBean.password.focus();
		return false;
	}
	if(isPhone(document.userInfoBean.phone.value)||document.userInfoBean.phone.value==""){
		alert("�绰����ֻ��Ϊ�����Ҷ�������á�,���ָ�Ҳ���Ϊ�գ�����");
		document.userInfoBean.phone.value="";
		document.userInfoBean.phone.focus();
		return false;
	}
    document.userInfoBean.password.value=document.userInfoBean.password.value.trim();

    if(document.userInfoBean.password.value.length <=5){
        alert("���볤�Ȳ���С��6λ!! ");
        document.userInfoBean.password.focus();
		return false;
    }
    obj = document.userInfoBean.password;
    if(pv.test(obj.value)){
       alert("���벻��Ϊ����,��������ֺ���ĸ��Ϸ�ʽ!!");
       return false;
    }
	if(document.userInfoBean.accountTerm.value==""){
		alert("�˺����޲���Ϊ��!! ");
		return false;
	}
/*
	if(document.userInfoBean.passwordTerm.value==""){
		alert("�������޲���Ϊ��!! ");
		return false;
	}
*/
	return true;
	//return false;
}

function getDepOnload(){

	var deptType = dep.value;
	var depV = region.value;

	var URL = "${ctx}/baseinfo/getSelectDept.jsp?depType="+deptType+"&selectname=deptID&regionid=" + depV;

	//hiddenInfoFrame.location.replace(URL);
}

function getDep(){

	deptType = userInfoBean.deptype.value;

	var depV = userInfoBean.regionid.value;
    var URL = "${ctx}/baseinfo/getSelectDept.jsp?depType="+deptType+"&selectname=deptID&regionid=" + depV;

    //hiddenInfoFrame.location.replace(URL);
}

function setDepType(deptType){
	userInfoBean.deptype.value = deptType;
	if(userInfoBean.regionid.value != null && userInfoBean.regionid.value != "0"){
		//getDep();
	}
    //if(usertype.substring(1,2)=="2")
    onChangeGroup();
    if(document.getElementById("deptlabel")!=null){
    	if(deptType=='1'){
    	  deptlabel.style.display="";
   	 	}else if(deptType=='2'){
    	  deptlabel.style.display="none";
    	}else if(deptType=='3'){
    	  deptlabel.style.display="none";
   	 	}
    }
     if(document.getElementById("conlabel")!=null){
     	if(deptType=='1'){
      		conlabel.style.display="none";
    	}else if(deptType=='2'){
    	  	conlabel.style.display="";
    	}else if(deptType=='3'){
      		conlabel.style.display="none";
    	}
    }
    if(document.getElementById("superconlabel")!=null){
    	if(deptType=='1'){
      		superconlabel.style.display="none";
    	}else if(deptType=='2'){
      		superconlabel.style.display="none";
   	 	}else if(deptType=='3'){
      		superconlabel.style.display="";
    	}
    }
}

function checkUserID(){

	var userID = userInfoBean.userID.value;
	   var result=isUsername(userID);
	if(result==0){
	var URL = "${ctx}/userinfoAction.do?method=ifUserIDValid&userID=" + userID;

	//alert(URL);
	innerMsgFrame.location.replace(URL);
	}
}
function toGetBack(){
       window.history.go(-1);
}

function onChangeGroup(){
  k=0;
  var value=document.userInfoBean.deptype.value+"#";
  if(value=="3#") value="2#";
  for( i=0;i<document.forms[0].ugpworkid.options.length;i++){
    var v=document.userInfoBean.ugpworkid.options[i].value+"#";
    //if(document.forms[0].ugpworkid.options[i].text.substring(0,6)== document.all.rId.value||document.all.rId.value.substring(2,6)=="1111"){
      //if(usertype.substring(1,2)=="1"||(usertype.substring(1,2)=="2"&&value==v.substring(v.lastIndexOf("-")+1,v.length))){
      if(value==v.substring(v.lastIndexOf("-")+1,v.length)){
        k++;
        document.userInfoBean.uId.options.length=k;
        document.userInfoBean.uId.options[k-1].value=document.forms[0].ugpworkid.options[i].value.substring(0,document.forms[0].ugpworkid.options[i].value.indexOf("-"));
        document.userInfoBean.uId.options[k-1].text=document.forms[0].ugpworkid.options[i].text.substring(8,document.all.ugpworkid.options[i].text.length);
      }
    //}
  }
  if(k==0)
    document.userInfoBean.uId.options.length=0;
  getDep();
  onChangeCon();
}
function onChangeCon(){
  k=0;
  for( i=0;i<document.all.deptworkid.options.length;i++){
  	//alert(document.all.deptworkid.options[i].text.substring(0,6) +"=="+document.all.rId.value +"  "+document.all.rId.value.substring(2,6));
    if(document.all.deptworkid.options[i].text.substring(0,6)== document.all.rId.value || document.all.rId.value.substring(2,6)=="1111" ){
      k++;
      document.all.dId.options.length=k;
      document.all.dId.options[k-1].value=document.all.deptworkid.options[i].value;
      document.all.dId.options[k-1].text=document.all.deptworkid.options[i].text.substring(8,document.all.deptworkid.options[i].text.length);
    	//alert(document.all.dId.options[k-1].value +"==="+document.all.deptworkid.options[i].value+" "+document.all.dId.options[k-1].text+"==="+document.all.deptworkid.options[i].text.substring(8,document.all.deptworkid.options[i].text.length))
    }
  }
  if(k==0) document.all.dId.options.length=0;
  k=0;
  for( i=0;i<document.all.conworkid.options.length;i++){
    if(document.all.conworkid.options[i].text.substring(0,6)== document.all.rId.value||document.all.rId.value.substring(2,6)=="1111"){
    	if(document.all.cId!=null){
      k++;
      document.all.cId.options.length=k;
      document.all.cId.options[k-1].value=document.all.conworkid.options[i].value;
      document.all.cId.options[k-1].text=document.all.conworkid.options[i].text.substring(8,document.all.conworkid.options[i].text.length);
    }
    }
  }if(document.all.cId!=null){
    if(k==0) document.all.cId.options.length=0;
  }
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
      alert("�ʺ����޲�Ӧ����ѡ���ȥ������");
      document.userInfoBean.accountTerm.value="";
    }
  }
}
//-->
</script>
<apptag:checkpower thirdmould="70502" ishead="5">
  <jsp:forward page="/globinfo/powererror.jsp"/>
</apptag:checkpower>
<br>
<template:titile value="�����û���Ϣ"/>
<span id="msgSpan"><font color="red"><%=msg%></font></span>
<html:form onsubmit="return isValidForm()" method="Post" action="/userinfoAction.do?method=addUserinfo" styleId="adduserinfo">
  <template:formTable >
  <html:hidden property="deptID" value=""/>
    <template:formTr name="��&nbsp;��&nbsp;ID">
      <html:text property="userID"  styleClass="inputtext required" style="width:200px" maxlength="20"/>&nbsp;&nbsp;<font color="red">*</font>
      <input type="button" value=" �Ƿ���� " onclick="checkUserID()" class="button">
    </template:formTr>
    <template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��" isOdd="false">
      <html:text property="userName" styleClass="inputtext required" style="width:200px" maxlength="20"/>&nbsp;&nbsp;<font color="red">*</font>
    </template:formTr>
	<template:formTr name="�������">
      <html:hidden property="deptype" value="1"/>
      <html:hidden property="isSuperviseUnit" value="0"/>
      <%if(!"2".equals(type)){%>
      <input type="radio" name="radiobutton" value="1" checked onclick="setDepType(1)">
      �ڲ�����
      <%}%>
      <input type="radio" name="radiobutton" value="1" onclick="setDepType(2)">
      ��ά��λ
     
    </template:formTr>
<%
  if(userInfo.getType().equals("11")){
%>
		 <logic:notEmpty name="regionlist">
          <template:formTr name="��������">
          	
            <select name="regionid" class="inputtext" style="width:200px" id="rId"  onchange="onChangeCon()" >
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
    <template:formTr name="��������" isOdd="false" style="display:none">
      <html:select property="regionid" styleId="rId" styleClass="inputtext" style="width:200px" onchange="getDep()">
        <html:option value="<%=regionId%>"><%=regionName %></html:option>
      </html:select>
    </template:formTr>
<%
  }
//if(userInfo.getType().equals("11")){ onChangeGroup();
%>
       

<%
//}
//else{
%><!--
    <template:formTr name="�� �� ��">
      <apptag:setSelectOptions valueName="groupCellection" tableName="usergroupmaster" columnName1="groupname" region="true" columnName2="id"/>
      <html:select property="usergroupid" styleId="uId" styleClass="inputtext" style="width:200px" onchange="onChangeCon()">
        <html:options collection="groupCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
--><%
//}
%>
          <logic:present name="ugplist">
              <select name="ugpworkid"   style="display:none">
                <logic:iterate id="ugpid" name="ugplist">
                    <option value="<bean:write name="ugpid" property="id"/>--<bean:write name="ugpid" property="type" />"><bean:write name="ugpid" property="regionid"/>--<bean:write name="ugpid" property="groupname"/></option>
                </logic:iterate>
              </select>
          </logic:present>
<%
//if(userInfo.getType().equals("")){
%>
<!--
    <template:formTr name="�������" style="display:none">
      <html:hidden property="deptype" value="1"/>
    </template:formTr>
-->
<%
//}
//else{
%>
    
<%
//}
//if(userInfo.getType().equals("11")){
%>
        <logic:notEmpty name="deptlist">
          <template:formTr tagID="deptlabel" name="��&nbsp;&nbsp;&nbsp;&nbsp;��">
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
          <tr class=trcolor id=conlabel style=display:none ><td id="conLabel" class="tdulleft">��ά��λ��</td><td class="tdulright">
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
          <tr class=trcolor id=superconlabel style=display:none ><td id="superconLabel" class="tdulleft">����λ��</td><td class="tdulright">
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
        

          <logic:present name="conlist">
              <select name="conworkid"   style="display:none">
                <logic:iterate id="conid" name="conlist">
                    <option value="<bean:write name="conid" property="contractorid"/>"><bean:write name="conid" property="regionid"/>--<bean:write name="conid" property="contractorname"/></option>
                </logic:iterate>
              </select>
          </logic:present>
<%
//}
//else{
%>
<%
//}
%>
	 <logic:notEmpty name="ugplist">
          <template:formTr name="�� �� ��" isOdd="false">
            <select name="usergroupid" class="inputtext" style="width:200px" id="uId"  >
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
    <template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��">
      <html:text property="workID" styleClass="inputtext" style="width:200px" maxlength="7"/>
    </template:formTr>
    <template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��" isOdd="false">
      <html:text property="password" styleClass="inputtext required" style="width:200px" maxlength="16"/>&nbsp;&nbsp;<font color="red">*</font>
    </template:formTr>
    <template:formTr name="�˺�����">
      <html:text readonly="true" onchange="vertifyDate()" property="accountTerm" styleClass="Wdate required" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})"  style="width:200px" maxlength="45"/>&nbsp;&nbsp;<font color="red">*</font>
    </template:formTr>
    <!--template:formTr name="��������" isOdd="false"-->
      <!--html:text readonly="true" property="passwordTerm" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:200px" maxlength="45"/>
    <!--/template:formTr-->
    <template:formTr name="�˺�״̬">
      <html:select property="accountState" styleClass="inputtext" style="width:200px">
        <html:option value="1">����</html:option>
        <html:option value="2">��ͣ</html:option>
        <html:option value="3">ֹͣ</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="��&nbsp;��&nbsp;��" isOdd="false">
      <html:textarea property="phone" styleClass="inputtext required" style="width:200px;height:50px"></html:textarea>&nbsp;&nbsp;<font color="red">* ����ֻ��ò��ð�Ƕ���","�ָ</font>
    </template:formTr>
    <template:formTr name="�����ʼ�">
      <html:text property="email" styleClass="inputtext" style="width:200px" maxlength="30"/>
    </template:formTr>
    <template:formTr name="ְ&nbsp;&nbsp;&nbsp;&nbsp;λ" isOdd="false">
      <html:text property="position" styleClass="inputtext" style="width:200px" maxlength="20"/>
    </template:formTr>
    <template:formSubmit>
      <td >
        <html:submit styleClass="button">����</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">����</html:reset>
      </td>
      <td>
        <input type="button" class="button" onclick="toGetBack()" value="����" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<template:cssTable/>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
<iframe name="innerMsgFrame" style="display:none"></iframe>
<script language="javascript">
<!--

document.userInfoBean.accountTerm.onpropertychange=function(){
  vertifyDate();
}
getDepOnload();

deptType = userInfoBean.deptype.value;

if(deptType == "2"&&typeof(userInfoBean.radionbutton)!="undefined"&&userInfoBean.radionbutton.length==2){
	userInfoBean.radiobutton[1].checked = true;
}

if(usertype=="11")
  setDepType(1);
onChangeGroup();
//-->
</script>
	   <script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('adduserinfo', {immediate : true, onFormValidate : formCallback});
	  </script>
