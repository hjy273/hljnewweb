<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo"%>
<script language="javascript" type="">
<!--
function GetSelectDatenew(strID) {
//    if(watchExeQuery.begindate.value =="" || watchExeQuery.begindate.value ==null){
//    	alert("还没有开始日期!");
//        watchExeQuery.begindate.focus();
//        return false;
//    }
    document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    //开始时间
    var yb = watchExeQuery.begindate.value.substring(0,4);
	var mb = parseInt(watchExeQuery.begindate.value.substring(5,7) ,10);
	var db = parseInt(watchExeQuery.begindate.value.substring(8,10),10);
    //结束时间
    var ye = watchExeQuery.enddate.value.substring(0,4);
	var me = parseInt(watchExeQuery.enddate.value.substring(5,7) ,10);
	var de = parseInt(watchExeQuery.enddate.value.substring(8,10),10);
    if(yb == "" && ye != "" ) {
	  alert("请输入查询的开始时间!");
      //document.all.item(strID).value="";
  	    watchExeQuery.begindate.focus();
  	    return false;
	}
	// 在开始和结束时间都输入的情况下做不能跨年度的判断
//	if(yb != "" && ye != "") {
	//	if(yb!=ye){
    //  		alert("查询时间段不能跨年度!");
    //  		document.all.item(strID).value="";
    //  		watchExeQuery.enddate.focus();
    //  		return false;
    //	}
		if(ye < yb) {
			alert("查询结束日期不能小于开始日期!");
      		document.all.item(strID).value="";
      		watchExeQuery.enddate.focus();
      		return false;
		}
		if((ye == yb ) && (me < mb  || de < db)) {
			alert("查询结束日期不能小于开始日期!");
      		document.all.item(strID).value="";
      		watchExeQuery.enddate.focus();
      		return false;
		}
//	}
    
//    if(mb!=me){
//      alert("不能跨月!");
//      document.all.item(strID).value="";
//      watchExeQuery.enddate.focus();
//      return false;
//    }
     
	return true;
}
function addGoBack()
        {
        	try{
            	location.href = "${ctx}/watchAction.do?method=queryTempWatch";
                return true;
            }
            catch(e){
            	alert(e);
            }
        }

function addSub() {
	//开始时间
    var yb = watchExeQuery.begindate.value.substring(0,4);
	var mb = parseInt(watchExeQuery.begindate.value.substring(5,7) ,10);
	var db = parseInt(watchExeQuery.begindate.value.substring(8,10),10);
    //结束时间
    var ye = watchExeQuery.enddate.value.substring(0,4);
	var me = parseInt(watchExeQuery.enddate.value.substring(5,7) ,10);
	var de = parseInt(watchExeQuery.enddate.value.substring(8,10),10);
	if(yb == "" && ye != "" ) {
	  alert("请输入查询的开始时间!");
      //document.all.item(strID).value="";
  	    watchExeQuery.begindate.focus();
  	    return false;
	}
	if(yb != "" && ye != "") {
		if(ye < yb) {
			alert("查询结束日期不能小于开始日期!");
      		document.all.item(strID).value="";
      		watchExeQuery.enddate.focus();
      		return false;
		}
		if((ye == yb ) && (me < mb  || de < db)) {
     		alert("查询结束日期不能小于开始日期!");
      		document.all.item(strID).value="";
      		watchExeQuery.enddate.focus();
      		return false;
    	}
	}
	
	
    
//    if(mb!=me){
//      alert("不能跨月!");
//      document.all.item(strID).value="";
//      watchExeQuery.enddate.focus();
//      return false;
//    }
     
	return true;
}
//-->
</script>
<Br/><Br/><Br/>
<template:titile value="外力盯防临时点查询"/>
<html:form action="/WatchExecuteQueryAction.do?method=queryAllTempWatchPoint" onsubmit="return addSub()">
  <template:formTable contentwidth="200" namewidth="200" >


  <%
    UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
        "LOGIN_USER");
    if (userinfo.getRegionID().substring( 2, 6 ).equals( "0000" )) {
  %>
    <template:formTr name="所属区域">
      <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid"/>
      <html:select property="regionid" styleClass="inputtext" style="width:180">
        <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
<%}%>

    <template:formTr name="盯防状态">
      <select name="bedited" class="inputtext" style="width:180px">
        <option value="">不限</option>
        <option value="1">已制定</option>
        <option value="0">未制定</option>
      </select>
    </template:formTr>
	<template:formTr name="开始日期" isOdd="false">
      <html:text property="begindate" styleClass="inputtext" size="25" maxlength="45"/>
      <apptag:date property="begindate"/>
    </template:formTr>

    <template:formTr name="结束日期"   tagID="eDate" >
        <html:text property="enddate"  value="" styleClass="inputtext" style="width:160" readonly="true"/>
        <input type='button' value='▼' id='btn'  onclick="GetSelectDatenew('enddate')" style="font:'normal small-caps 6pt serif';" >
    </template:formTr>

    <template:formSubmit>
      <td>
        <html:submit styleClass="button" >查询</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">重置</html:reset>
      </td>
    </template:formSubmit>

  </template:formTable>
</html:form>
