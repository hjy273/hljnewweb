<%@ include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo" %>
<script language="javascript" type="">
<!--
function GetSelectDatenew(strID) {
//    if(watchBean.beginDate.value =="" || watchBean.beginDate.value ==null){
//    	alert("还没有开始日期!");
//        watchBean.beginDate.focus();
//        return false;
//    }
    document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    //开始时间
    var yb = watchBean.beginDate.value.substring(0,4);
	var mb = parseInt(watchBean.beginDate.value.substring(5,7) ,10);
	var db = parseInt(watchBean.beginDate.value.substring(8,10),10);
    //结束时间
    var ye = watchBean.endDate.value.substring(0,4);
	var me = parseInt(watchBean.endDate.value.substring(5,7) ,10);
	var de = parseInt(watchBean.endDate.value.substring(8,10),10);
    if(yb == "" && ye != "" ) {
	  alert("请输入查询的开始时间!");
      //document.all.item(strID).value="";
      watchBean.beginDate.focus();
      return false;
	}
	// 在开始和结束时间都输入的情况下做不能跨年度的判断
	if(yb != "" && ye != "") {
		//if(yb!=ye){
      	//	alert("查询时间段不能跨年度!");
      	//	document.all.item(strID).value="";
      	//	watchBean.endDate.focus();
      	//	return false;
    	//}
		if(ye < yb) {
			alert("查询结束日期不能小于开始日期!");
      		document.all.item(strID).value="";
      		watchExeQuery.endDate.focus();
      		return false;
		}
		if((ye == yb ) && (me < mb  || de < db)) {
      		alert("查询结束日期不能小于开始日期!");
      		document.all.item(strID).value="";
      		watchBean.endDate.focus();
      		return false;
    	}
	}
//    if(mb!=me){
//      alert("不能跨月!");
//      document.all.item(strID).value="";
//      watchBean.endDate.focus();
//      return false;
//    }
	return true;
}
var iteName = "";

function getTimeWin(objName){

    iteName = objName;

    showx = event.screenX - event.offsetX - 4 - 210 ;
    showy = event.screenY - event.offsetY + 18;

    var timeWin = window.showModalDialog("${ctx}/common/time.html",self, "dialogWidth:235px; dialogHeight:265px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; status:no; directories:yes;scrollbars:no;Resizable=no;help:no");

}

function setSelecteTime(time) {
    document.all.item(iteName).value = time;
}

function toLoadPoint(){

    opSelect(true);

    var desPage = "${ctx}/pointAction.do?method=loadPoint4Watch&id=" + watchBean.lid.value;
    //alert(desPage);

    hiddenFrame.location.replace(desPage);

}

function opSelect(flag){

    watchBean.startpointid.disabled = flag;
    watchBean.endpointid.disabled = flag;

}

function toSetVis(visStr){

    for(var i = 0; i < toHideTr.length; i ++){

        toHideTr[i].style.display = visStr;
    }

}

function toClearEndDate(){
    watchBean.endDate.value = "待定";
}
function addGoBack()
        {
            try{
                location.href = "${ctx}/watchAction.do?method=queryWatch";
                return true;
            }
            catch(e){
                alert(e);
            }
        }
///////////////////////////////示例代码////////////////////////////
//区域和代维
    function onChangeCon(){
          k=0;
          for( i=0;i<document.all.workID.options.length;i++){
             if(document.all.workID.options[i].text.substring(0,6)== document.all.rId.value){
                 k++;
                document.all.cId.options.length=k;
                document.all.cId.options[k-1].value=document.all.workID.options[i].value;
                document.all.cId.options[k-1].text=document.all.workID.options[i].text.substring(8,document.all.workID.options.length);
             }
           }
           if(k==0){
             document.all.cId.options.length=1;
             document.all.cId.options[0].value="";
             document.all.cId.options[0].text="无";
           }
           onChangeDeptUser();
    }

//    代维单位和巡检人
    function onChangeDeptUser()
    {
      var iArray = document.all.userID.options;   //所隐藏的数组
      var iIndex = 0;
      var iStart = 0; //
      var iEnd = iArray.length - 1;
      var iCount = 0;
      var iSeek = document.all.cId.value;       // 所要查找的值,上级菜单的值
      while(iSeek != ""){
           iCount++;
         iIndex =parseInt( ( iStart + iEnd )/2);

         if( iArray[iIndex].text.substring(8,18) < iSeek ){ //iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
                iStart = iIndex;
         }else if( iArray[iIndex].text.substring(8,18) > iSeek ){//iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
                    iEnd = iIndex;
         }else{
             if(iIndex == 0){
                break;
            }else{
                if(iArray[iIndex-1].text.substring(8,18) == iSeek){ //iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
                       iEnd = iIndex;
                }else{
                    break;
                }
            }
        }
        if(iIndex==iEnd-1 && iArray[iEnd].text.substring(8,18) != iSeek){ //iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
            index = iArray.length;
            document.all.uId.options.length=0;
            break;
        }
        if(iIndex==iStart && iArray[iEnd].text.substring(8,18) != iSeek){ //iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
            index = iArray.length;
            document.all.uId.options.length=0;
            break;
        }
      }

      k=1;
      ////iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
      //=document.all.cId.value 所要查找的值,上级菜单的值
      for( i=iIndex;i<iArray.length && iArray[i].text.substring(8,18)==document.all.cId.value;i++){
          k++;
          document.all.uId.options.length=k;   //document.all.uId.options 要设置的select

          document.all.uId.options[0].value="";
          document.all.uId.options[0].text="不限";

          document.all.uId.options[k-1].value=document.all.userID.options[i].value;
          document.all.uId.options[k-1].text=document.all.userID.options[i].text.substring(20);
      }
      if(k==1){
        document.all.uId.options.length=1;
        document.all.uId.options[0].value="";
        document.all.uId.options[0].text="无";
      }
    }

function addSub() {
	//开始时间
    var yb = watchBean.beginDate.value.substring(0,4);
	var mb = parseInt(watchBean.beginDate.value.substring(5,7) ,10);
	var db = parseInt(watchBean.beginDate.value.substring(8,10),10);
    //结束时间
    var ye = watchBean.endDate.value.substring(0,4);
	var me = parseInt(watchBean.endDate.value.substring(5,7) ,10);
	var de = parseInt(watchBean.endDate.value.substring(8,10),10);
    if(yb == "" && ye != "" ) {
	  alert("请输入查询的开始时间!");
      //document.all.item(strID).value="";
      watchBean.beginDate.focus();
      return false;
	}
	// 在开始和结束时间都输入的情况下做不能跨年度的判断
	if(yb != "" && ye != "") {
		//if(yb!=ye){
      	//	alert("查询时间段不能跨年度!");
      	//	document.all.item(strID).value="";
      	//	watchBean.endDate.focus();
      	//	return false;
    	//}
		if(ye < yb) {
			alert("查询结束日期不能小于开始日期!");
      		document.all.item(strID).value="";
      		watchExeQuery.endDate.focus();
      		return false;
		}
		if((ye == yb ) && (me < mb  || de < db)) {
     		alert("查询结束日期不能小于开始日期!");
      		document.all.item(strID).value="";
      		watchBean.endDate.focus();
      		return false;
    	}
	}
	return true;
}
//////////////////////////////////////////////////////////////////////////////////////////////////
</script>
<Br/>
<%
UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
if(userinfo.getRegionID().substring( 2, 6 ).equals( "0000" )){%>
<body onload="onChangeCon()">
<% }else{%>
<body>
<% }%>
<template:titile value="查询外力盯防点信息"/>

<html:form  method="Post"
    action="/watchAction.do?method=queryWatch" onsubmit="return addSub()" >
    <template:formTable contentwidth="200" namewidth="200">


        <template:formTr name="盯防名称" isOdd="false" >
            <html:text property="placeName" styleClass="inputtext" style="width:160" maxlength="45"/>
        </template:formTr>

        <logic:notEmpty name="reginfo">
          <template:formTr name="所属区域" isOdd="false">
            <select name="regionID" class="inputtext" style="width:180px" id="rId"  onchange="onChangeCon()" >
              <logic:present name="reginfo">
                <logic:iterate id="reginfoId" name="reginfo">
                  <option value="<bean:write name="reginfoId" property="regionid"/>">
                   <bean:write name="reginfoId" property="regionname"/>
                  </option>
                </logic:iterate>
              </logic:present>
            </select>
          </template:formTr>
        </logic:notEmpty>


        <logic:notEmpty name="coninfo">
          <template:formTr name="代维单位" tagID="conTr">
            <select name="contractorid" class="inputtext" style="width:180px" id="cId" onchange="onChangeDeptUser()">
              <option value="">不限</option>
              <logic:present name="coninfo">
                <logic:iterate id="coninfoId" name="coninfo">
                  <option value="<bean:write name="coninfoId" property="contractorid"/>">
                  <bean:write name="coninfoId" property="contractorname"/></option>
                </logic:iterate>
              </logic:present>
            </select>
          </template:formTr>

          <logic:present name="coninfo">
              <select name="workID"   style="display:none">
                <logic:iterate id="coninfoId" name="coninfo">
                    <option value="<bean:write name="coninfoId" property="contractorid"/>"><bean:write name="coninfoId" property="regionid"/>--<bean:write name="coninfoId" property="contractorname"/></option>
                </logic:iterate>
              </select>
          </logic:present>

        </logic:notEmpty>

    <logic:equal value="group" name="PMType">
    <template:formTr name="巡检维护组" isOdd="false">
      <select name="principal" class="inputtext" style="width:180px" id="uId">
        <option value="">不限</option>
        <logic:present name="uinfo">
          <logic:iterate id="uinfoId" name="uinfo">
            <option value="<bean:write name="uinfoId" property="patrolid"/>">
              <bean:write name="uinfoId" property="patrolname"/>
            </option>
          </logic:iterate>
        </logic:present>
      </select>
    </template:formTr>
    </logic:equal>

    <logic:notEqual value="group" name="PMType">
    <template:formTr name="巡 检 人" isOdd="false">
      <select name="principal" class="inputtext" style="width:180px" id="uId">
        <option value="">不限</option>
        <logic:present name="uinfo">
          <logic:iterate id="uinfoId" name="uinfo">
            <option value="<bean:write name="uinfoId" property="patrolid"/>">
              <bean:write name="uinfoId" property="patrolname"/>
            </option>
          </logic:iterate>
        </logic:present>
      </select>
    </template:formTr>
    </logic:notEqual>

          <logic:present name="uinfo">
              <select name="userID"   style="display:none">
                <logic:iterate id="uinfoId" name="uinfo">
                    <option value="<bean:write name="uinfoId" property="patrolid"/>"><bean:write name="uinfoId" property="regionid"/>--<bean:write name="uinfoId" property="parentid"/>--<bean:write name="uinfoId" property="patrolname"/></option>
                </logic:iterate>
              </select>
          </logic:present>

	<template:formTr name="开始日期" >
      <html:text property="beginDate" styleClass="inputtext" size="25" maxlength="45"/>
      <apptag:date property="begindate"/>
    </template:formTr>

    <template:formTr name="结束日期"   tagID="eDate" >
        <html:text property="endDate"  value="" styleClass="inputtext" style="width:160" readonly="true"/>
        <input type='button' value='▼' id='btn'  onclick="GetSelectDatenew('enddate')" style="font:'normal small-caps 6pt serif';" >
    </template:formTr>


        <template:formSubmit>
            <td >
                <html:submit styleClass="button" >查询</html:submit>
            </td>
            <td >
                <html:reset styleClass="button"  >重置</html:reset>
            </td>

        </template:formSubmit>

   </template:formTable>
</html:form>
