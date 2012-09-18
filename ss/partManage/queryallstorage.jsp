<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<%
    UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    String type = userInfo.getDeptype().trim();
%>
<script>
function valiDigit(id){
  var obj = document.getElementById(id);
  var mysplit = /^\d{1,6}$/;
  if(mysplit.test(obj.value)){
    return true;
  }
  else{
    alert("你填写的不是数字,请重新输入");
    obj.focus();
    obj.value=0;
  }
}
function onChangeContractor(){
  k=0;
  for( i=0;i<document.all.conworkId.options.length;i++){
    if(document.all.conworkId.options[i].text.substring(0,document.all.conworkId.options[i].text.indexOf("-"))== document.all.rId.value){
      k++;
      document.all.cId.options.length=k;
      document.all.cId.options[k-1].value=document.all.conworkId.options[i].value;
      document.all.cId.options[k-1].text=document.all.conworkId.options[i].text.substring(document.all.conworkId.options[i].text.lastIndexOf("-")+1,document.all.conworkId.options[i].text.length);
    }
  }
  if(k==0)
    document.all.cId.options.length=0;
  k=0;
  for( i=0;i<document.all.deptworkId.options.length;i++){
    if(document.all.deptworkId.options[i].text.substring(0,document.all.deptworkId.options[i].text.indexOf("-"))== document.all.rId.value){
      k++;
      document.all.dId.options.length=k;
      document.all.dId.options[k-1].value=document.all.deptworkId.options[i].value;
      document.all.dId.options[k-1].text=document.all.deptworkId.options[i].text.substring(document.all.deptworkId.options[i].text.lastIndexOf("-")+1,document.all.deptworkId.options[i].text.length);
    }
  }
  if(k==0)
    document.all.dId.options.length=0;
  onChangeMinterial();
}
function onChangeMinterial(){
  k=1;
  document.all.mId.options.length=0;
  for( i=0;i<document.all.mworkId.options.length;i++){
    if(document.all.mworkId.options[i].text.substring(0,document.all.mworkId.options[i].text.indexOf("-"))== document.all.rId.value){
      if(document.all.mId.options.length!=0&&document.all.mId.options[document.all.mId.options.length-1].value==document.all.mworkId.options[i].value)
        continue;
      k++;
      document.all.mId.options.length=k;
      document.all.mId.options[k-1].value=document.all.mworkId.options[i].value;
      document.all.mId.options[k-1].text=document.all.mworkId.options[i].text.substring(document.all.mworkId.options[i].text.lastIndexOf("-")+1,document.all.mworkId.options[i].text.length);
    }
  }
  if(k==1){
    document.all.mId.options.length=1;
  }
    document.all.mId.options[0].value="";
    document.all.mId.options[0].text="所有材料名称";
  onChangeType();
}
function onChangeType(){
  k=1;
  for( i=0;i<document.all.tworkId.options.length;i++){
    if(document.all.tworkId.options[i].text.substring(0,document.all.tworkId.options[i].text.indexOf("-"))== document.all.mId.value){
      k++;
      document.all.tId.options.length=k;
      document.all.tId.options[k-1].value=document.all.tworkId.options[i].value;
      document.all.tId.options[k-1].text=document.all.tworkId.options[i].text.substring(document.all.tworkId.options[i].text.lastIndexOf("-")+1,document.all.tworkId.options[i].text.length);
    }
  }
  if(k==1){
    document.all.tId.options.length=1;
  }
    document.all.tId.options[0].value="";
    document.all.tId.options[0].text="所有材料类型";
}
function isValidForm() {
    var pv = /^\d{1,16}$/;
    var len=0;

	return true;
}
function setDepType(deptType){
    PartRequisition.deptype.value=deptType;
    if(deptType=='2'){ deptlabel.style.display="none";conlabel.style.display="";}
    else {deptlabel.style.display="none";conlabel.style.display="none";}
}
</script>
<template:titile value="查询所有材料库存信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/PartStorageAction.do?method=showAllStorage">
  <html:hidden property="querytype" value="1"/>
  <template:formTable contentwidth="300" namewidth="200">
    <logic:notEmpty name="regionlist">
      <template:formTr name="所属区域">
        <select name="regionid" class="inputtext" style="width:180px" id="rId"  onchange="onChangeContractor();" >
          <logic:present name="regionlist">
            <logic:iterate id="regionId" name="regionlist">
              <option value="<bean:write name="regionId" property="regionid"/>">
                <bean:write name="regionId" property="regionname"/>
              </option>
            </logic:iterate>
          </logic:present>
        </select>
      </template:formTr>
    </logic:notEmpty>
    <template:formTr name="部门类别" isOdd="false">
      <html:hidden property="deptype" value=""/>
      <%
          if(!"2".equals(type)){
        %>
        <input type="radio" name="radiobutton" value="1" onclick="setDepType(1)">
        内部部门
        <%}%>
        <input type="radio" name="radiobutton" value="1" onclick="setDepType(2)" checked="checked">
        代维单位
    </template:formTr>
    <logic:notEmpty name="conlist">
      <template:formTr name="代维单位" tagID="conlabel">
        <select name="contractorid" class="inputtext" style="width:180px" id="cId"  onchange="onChangeMinterial();" >
          <logic:present name="conlist">
            <logic:iterate id="contractorId" name="conlist">
              <option value="<bean:write name="contractorId" property="contractorid"/>">
                <bean:write name="contractorId" property="contractorname"/>
              </option>
            </logic:iterate>
          </logic:present>
        </select>
      </template:formTr>
    </logic:notEmpty>
    <logic:present name="conlist">
      <select name="conworkId"   style="display:none;width:180">
        <logic:iterate id="contractorId" name="conlist">
          <option value="<bean:write name="contractorId" property="contractorid"/>"><bean:write name="contractorId" property="regionid"/>--<bean:write name="contractorId" property="contractorname"/></option>
        </logic:iterate>
      </select>
    </logic:present>
    <logic:notEmpty name="deptlist">
      <template:formTr name="移动公司" style="display:none" tagID="deptlabel">
        <select name="deptid" class="inputtext" style="width:180px" id="dId"  onchange="onChangeCon()" >
          <logic:present name="deptlist">
            <logic:iterate id="deptid" name="deptlist">
              <option value="<bean:write name="deptid" property="deptid"/>">
                <bean:write name="deptid" property="deptname"/>
              </option>
            </logic:iterate>
          </logic:present>
        </select>
      </template:formTr>
    </logic:notEmpty>
    <logic:present name="deptlist">
      <select name="deptworkId"   style="display:none;width:180">
        <logic:iterate id="deptid" name="deptlist">
          <option value="<bean:write name="deptid" property="deptid"/>"><bean:write name="deptid" property="regionid"/>--<bean:write name="deptid" property="deptname"/></option>
        </logic:iterate>
      </select>
    </logic:present>
    <template:formTr name="材料名称"  >
      <select name="name" id="mId"  class="inputtext" style="width:180" onchange="onChangeType();">
        <option value="">所有材料名称</option>
        <logic:present name="nameList">
          <logic:iterate id="nameListId" name="nameList">
            <option value="<bean:write name="nameListId" property="name"/>"><bean:write name="nameListId" property="name"/></option>
          </logic:iterate>
        </logic:present>
      </select>
    </template:formTr>
    <logic:present name="nameList">
      <select name="mworkId"   style="display:none;width:180">
        <logic:iterate id="nameListId" name="nameList">
          <option value="<bean:write name="nameListId" property="name"/>"><bean:write name="nameListId" property="regionid"/>--<bean:write name="nameListId" property="name"/></option>
        </logic:iterate>
      </select>
    </logic:present>
    <template:formTr name="材料类型" >
      <select name="type" id="tId" style="width:180" class="inputtext"  >
        <option value="">所有材料类型</option>
        <logic:present name="typeList">
          <logic:iterate id="typeListId" name="typeList">
            <option value="<bean:write name="typeListId" property="type"/>"><bean:write name="typeListId" property="type"/></option>
          </logic:iterate>
        </logic:present>
      </select>
    </template:formTr>
    <logic:present name="typeList">
      <select name="tworkId"   style="display:none;width:180">
        <logic:iterate id="typeListId" name="typeList">
          <option value="<bean:write name="typeListId" property="type"/>"><bean:write name="typeListId" property="name"/>--<bean:write name="typeListId" property="type"/></option>
        </logic:iterate>
      </select>
    </logic:present>
    <template:formTr name="新材料库存" >
      <b>大于&nbsp;&nbsp;</b><input   type="text" name="newlownumber" id="newlow" class="inputtext" size="10" maxlength="6"   onkeyup="valiDigit(id)"/>
      <b>小于&nbsp;&nbsp;</b><input   type="text" name="newhignumber" id="newhig" class="inputtext" size="10" maxlength="6"   onkeyup="valiDigit(id)"/>
    </template:formTr >
    <template:formTr name="旧材料库存" >
       <b>大于&nbsp;&nbsp;</b><input   type="text" name="oldlownumber" id="oldlow" class="inputtext" size="10" maxlength="6"  onkeyup="valiDigit(id)"/>
       <b>小于&nbsp;&nbsp;</b><input   type="text" name="oldhignumber" id="oldhig" class="inputtext" size="10" maxlength="6"   onkeyup="valiDigit(id)"/>
    </template:formTr >
    <template:formSubmit>
    <td>
      <html:submit styleClass="button">查询</html:submit>
    </td>
    <td>
      <html:reset styleClass="button">取消</html:reset>
    </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
<script language="javascript">onChangeContractor();</script>
