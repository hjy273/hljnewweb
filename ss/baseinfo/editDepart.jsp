<%@page import="com.cabletech.baseinfo.beans.*"%>
<jsp:useBean id="departBean" class="com.cabletech.baseinfo.beans.DepartBean" scope="request"/>
<%@include file="/common/header.jsp"%>
<!-- ZSH 2004-10-13 -->
<script language="javascript">
<!--

function String.prototype.trim1(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}

function isValidForm() {
    //部门校验
	if(document.departBean.deptName.value==""||document.departBean.deptName.value.trim1()==""||document.departBean.deptName.value.trim1()=="null"){
		alert("部门名称不能为空或者空格或者null!! ");
		return false;
	}
    
    if(valCharLength(document.departBean.deptName.value)>20){
      alert("部门名称字数太多！不能超过10个汉字");
      return false;
    }
	if(document.departBean.linkmanInfo.value==""||document.departBean.linkmanInfo.value=="null"||document.departBean.linkmanInfo.value.trim1()==""||document.departBean.linkmanInfo.value.trim1()=="null"){
		alert("联系人不能为空或者null!! ");
		return false;
	}
    //新增以下一段代码，用以控制联系人不能为空格.Add by Steven Mar 12,2007
    if(trim(document.departBean.linkmanInfo.value)==""){
		alert("联系人不能为空格!! ");
        document.departBean.linkmanInfo.focus();
		return false;
	}
	//新增以下一段代码，用以控制部门名称不能为空格.Add by Steven Mar 12,2007
    if(trim(document.departBean.deptName.value)==""){
		alert("部门名称不能为空格!! ");
        document.departBean.deptName.focus();
		return false;
	}
	return true;
}
function valCharLength(Value){
      var j=0;
      var s = Value;
      for   (var   i=0;   i<s.length;   i++)
      {
              if   (s.substr(i,1).charCodeAt(0)>255)   j   =   j   +   2;
              else   j++
      }
      return j;
    }

function getDep(){

	var depV = departBean.regionid.value;
	var URL = "getSelect.jsp?depType=1&selectname=parentID&regionid=" + depV;

	hiddenInfoFrame.location.replace(URL);
}

function toGetBack(){
       var url="${ctx}/departAction.do?method=queryDepart";
       self.location.replace(url);
 // window.history.go(-1);
}
function seleOnCh(){
	temp = departBean.parentID.selectedIndex;
    //alert(temp);
}
function check(){
  	tem = departBean.parentID.options[departBean.parentID.selectedIndex].value;
    deptid = departBean.deptID.value;
    //alert (tem  +" "+deptid +"  "+temp);
    if(tem ==deptid){
    	alert("对不起，您选择的上级部门不正确！！");
        departBean.parentID.selectedIndex = temp;
    }

}

//-->
</script>
<apptag:checkpower thirdmould="70204" ishead="5">
  <jsp:forward page="/globinfo/powererror.jsp"/>
</apptag:checkpower>
<br>
<template:titile value="修改部门信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/departAction.do?method=updateDepart">
  <template:formTable >
   
    <template:formTr name="部门名称">
    	 <html:hidden property="deptID"/>
      <html:text property="deptName" styleClass="inputtext" style="width:160px" maxlength="20"/> <font color="red"> *</font>
    </template:formTr>
    <logic:equal value="11" name="LOGIN_USER" property="type">
      <template:formTr name="所属区域">
        <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" order="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
        <html:select property="regionid" disabled="true" styleClass="inputtext" style="width:160px">
          <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
      <!--
      <template:formTr name="所属区域" isOdd="false">
        <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid"/>
        <html:select property="regionid" styleClass="inputtext" style="width:160px">
          <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
      -->

    </logic:equal>
    <template:formTr name="上级部门">
      <%
     	DepartBean bean =(DepartBean)request.getAttribute("departBean");
      	String parentid = bean.getParentID();
      	if("0000".equals(parentid)){
        %>
        <apptag:setSelectOptions valueName="deptCollection" tableName="deptinfo" columnName1="deptname"  columnName2="deptid" condition="parentid like '%0000' and deptID!='${departBean.deptID}'"/>
        <span id="selectSpan">
          <html:select property="parentID" styleClass="inputtext" style="width:160px">
         	 <html:option value="00000000">无</html:option>
            <html:options collection="deptCollection" property="value" labelProperty="label"/>
          </html:select><font color="red"> *</font>
        </span>
        <% }else{
        %>
        <apptag:setSelectOptions valueName="deptCollection" tableName="deptinfo" columnName1="deptname" columnName2="deptid" region="true" />
        <span id="selectSpan">
          <html:select property="parentID" onchange="check()"  onfocus=" seleOnCh()" styleClass="inputtext" style="width:160px">
          <html:option value="00000000">无</html:option>
            <html:options collection="deptCollection" property="value" labelProperty="label"/>
          </html:select><font color="red"> *</font>
        </span>
       <%}%>
    </template:formTr>
    <template:formTr name="联 系 人" isOdd="false">
      <html:text property="linkmanInfo" styleClass="inputtext" style="width:160px" maxlength="25"/> <font color="red"> *</font>
    </template:formTr>
    <!-- zsh New add 2004-10-13
    <template:formTr name="状态">
      <html:select property="state" styleClass="inputtext" style="width:160px">
        <html:option value="1">正常</html:option>
        <html:option value="2">暂停</html:option>
        <html:option value="3">停止</html:option>
      </html:select>
    </template:formTr>-->
    <template:formTr name="备&nbsp;&nbsp;&nbsp;&nbsp;注" isOdd="false">
      <html:text property="remark" styleClass="inputtext" style="width:160px" maxlength="10"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button" >更新</html:submit>
      </td>
      <td>
        <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="返回" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<template:cssTable/>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
