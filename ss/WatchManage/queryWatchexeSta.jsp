<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo"%>
<script language="javascript" type="">
<!--

function GetSelectDatenew(strID) {
//    if(watchStaBean.begindate.value =="" || watchStaBean.begindate.value ==null){
//    	alert("还没有开始日期!");
//        watchStaBean.begindate.focus();
//        return false;
//    }
    document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    //开始时间
    var yb = watchStaBean.begindate.value.substring(0,4);
	var mb = parseInt(watchStaBean.begindate.value.substring(5,7) ,10);
	var db = parseInt(watchStaBean.begindate.value.substring(8,10),10);
    //结束时间
    var ye = watchStaBean.enddate.value.substring(0,4);
	var me = parseInt(watchStaBean.enddate.value.substring(5,7) ,10);
	var de = parseInt(watchStaBean.enddate.value.substring(8,10),10);
    if(yb == "" && ye != "" ) {
	  alert("请输入查询的开始时间!");
      //document.all.item(strID).value="";
      watchStaBean.begindate.focus();
      return false;
	}
	// 在开始和结束时间都输入的情况下做不能跨年度的判断
	if(yb != "" && ye != "") {
		//if(yb!=ye){
      	//	alert("查询时间段不能跨年度!");
      	//	document.all.item(strID).value="";
      	//	watchStaBean.enddate.focus();
      	//	return false;
    	//}
		if(ye < yb) {
			alert("查询结束日期不能小于开始日期!");
      		document.all.item(strID).value="";
      		watchExeQuery.enddate.focus();
      		return false;
		}
		if((ye == yb ) && (me < mb  || de < db)) {
      	 	alert("查询结束日期不能小于开始日期!");
     	 	document.all.item(strID).value="";
     	 	watchStaBean.enddate.focus();
      	 	return false;
        }
	}
//    if(mb!=me){
//      alert("不能跨月!");
//      document.all.item(strID).value="";
//      watchStaBean.enddate.focus();
//      return false;
//    }
     
	return true;
}


var flag = "0";
function settype(v){
  if(v == 1){
	flag = 1;
//    contractorTr.style.display = "";
    patrolTr.style.display = "none";
    watchTr.style.display = "none";

//    bDate.style.display = "";
//	eDate.style.display = "";
//    watchStaBean.executorid.value="";
//    watchStaBean.watchid.value="";
    watchStaBean.statype.value = "1"; //代维单位
	bDate.style.display = "";
	eDate.style.display = "";
	querynameTr.style.display = "none";
  }

  if(v == 2){
	flag = 2;
//    contractorTr.style.display = "none";
    patrolTr.style.display = "";
    watchTr.style.display = "none";
//    watchStaBean.contractorid.value="";
//
//	 bDate.style.display = "";
//	eDate.style.display = "";
//    watchStaBean.watchid.value="";
    watchStaBean.statype.value = "2"; //巡检员
	bDate.style.display = "";
	eDate.style.display = "";
	querynameTr.style.display = "none";
  }

  if(v == 3){
	flag = 3;
//    contractorTr.style.display = "none";
    patrolTr.style.display = "none";
    watchTr.style.display = "";
//    watchStaBean.contractorid.value="";
//    watchStaBean.executorid.value="";
//
//    bDate.style.display = "";
//	eDate.style.display = "";
    watchStaBean.statype.value = "3"; //外力盯防
	bDate.style.display = "";
	eDate.style.display = "";
	querynameTr.style.display = "none";
  }

 if(v == 4){
	flag = 4;
    patrolTr.style.display = "none";
    watchTr.style.display = "none";
    watchStaBean.statype.value = "4"; //盯防名称
	bDate.style.display = "none";
	eDate.style.display = "none";
	querynameTr.style.display = "";
  }

}

function makeSize(enlargeFlag){
	if(enlargeFlag == 0){

		mainSpan.style.display = "none";
		iframeDiv.style.height = "400";
	}else{
		mainSpan.style.display = "";
		iframeDiv.style.height = "320";
	}
}

function checkForm(){
  if(flag == 2) {
	// 盯防名称
	watchStaBean.queryname.value = "";
	// 外力盯防
	watchStaBean.watchid.value= "";
  }
  if(flag == 3) {
	// 盯防名称
	watchStaBean.queryname.value = "";
	// 巡检维护组
	watchStaBean.executorid.value = "";
  	return true;
  }

  if(flag == 4) {
	// 时间
	watchStaBean.begindate.value = "";
	watchStaBean.enddate.value = "";
	// 外力盯防
	watchStaBean.watchid.value= "";
	// 巡检维护组
	watchStaBean.executorid.value = "";
	
  }

//if(flag != 4) {
	if(watchStaBean.begindate.value=="" && watchStaBean.enddate.value==""){
    if(confirm("您将查询本月盯防信息！")){
      return true;
    }else{
      return false;
    }
//  }
}
  if(!addSub()) {
    return false;
  }
  return true;
}

function toExpotr(){
  var regionid = document.all.rId.value;
  var contractorid = watchStaBean.contractorid.value;
  var executorid = watchStaBean.executorid.value;
  var watchid = watchStaBean.watchid.value;
  var begindate = watchStaBean.begindate.value;
  var enddate = watchStaBean.enddate.value;
  var ss = "${ctx}/WatchExeStaAction.do?method=exportWatchList&contractorid="+contractorid+"&executorid="+executorid+"&watchid="+watchid+"&begindate="+begindate+"&enddate="+enddate+"&regionid="+regionid;
  if(flag == 3)
  	self.location.replace(ss);
  else{
	    if(begindate == "" && enddate == ""){
	      if(confirm("将导出本月盯防信息！")){
	        self.location.replace(ss);
	      }
	    }else
	    self.location.replace(ss);
	  }
 }
//区域和代维
    function onChangeCon(){
          k=0;
          for( i=0;i<document.all.workID.options.length;i++){
             if(document.all.workID.options[i].text.substring(0,6)== document.all.rId.value){
                k++;
                document.all.cId.options.length=k;
                document.all.cId.options[k-1].value=document.all.workID.options[i].value;
                document.all.cId.options[k-1].text=document.all.workID.options[i].text.substring(8);
             }
           }
           if(k==0){
             document.all.cId.options.length=1;
             document.all.cId.options[0].value="";
             document.all.cId.options[0].text="无";
           }
           onChangeDeptUser();
           onChangeWatch();
    }
    //区域和盯防
    function onChangeWatch(){
          k=1;
          for( i=0;i<document.all.wID.options.length;i++){
             if(document.all.wID.options[i].text.substring(0,6)== document.all.rId.value){
                 k++;
                document.all.wId.options.length=k;
                document.all.wId.options[0].value="";
                document.all.wId.options[0].text="不限";
                document.all.wId.options[k-1].value=document.all.wID.options[i].value;
                document.all.wId.options[k-1].text=document.all.wID.options[i].text.substring(20);
             }
           }
           if(k==1){
             document.all.wId.options.length=1;
             document.all.wId.options[0].value="";
             document.all.wId.options[0].text="无外力盯防";
           }
    }
    function onChangeDeptWatch(){
          k=1;
          for( i=0;i<document.all.wID.options.length;i++){
             if(document.all.wID.options[i].text.substring(8,18)== document.all.cId.value){
                 k++;
                document.all.wId.options.length=k;
                document.all.wId.options[0].value="";
                document.all.wId.options[0].text="不限";
                document.all.wId.options[k-1].value=document.all.wID.options[i].value;
                document.all.wId.options[k-1].text=document.all.wID.options[i].text.substring(20);
             }
           }
           if(k==1){
             document.all.wId.options.length=1;
             document.all.wId.options[0].value="";
             document.all.wId.options[0].text="无外力盯防";
           }
    }
//    代维单位和巡检人
    function onChangeDeptUser()
    {
      var iArray = document.all.userID.options;   //所隐藏的数组
      var iIndex = 0;
      var iStart = 0; //
      var iEnd = iArray.length - 1;
      //alert("iend:" + iEnd);
      var iCount = 0;
      var iSeek = document.all.cId.value;       // 所要查找的值,上级菜单的值
      //alert("iseek:" + iSeek);
      while(iSeek != ""){
         iCount++;
         iIndex =parseInt( ( iStart + iEnd )/2);
         //alert("iindex:" + iIndex);
         //alert("value of iIndex:" + iArray[iIndex].text.substring(8,18));
         //alert("iseek:" + iSeek);
         if( iArray[iIndex].text.substring(8,18) < iSeek ){ //iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
                iStart = iIndex;
                //alert("istart:" + iStart);
                
         }else if( iArray[iIndex].text.substring(8,18) > iSeek ){//iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
                    iEnd = iIndex;
                //alert("iend:" + iEnd);
         }else{
             if(iIndex == 0){
                //alert("iIndex=0");
                break;
            }else{
                if(iArray[iIndex-1].text.substring(8,18) == iSeek){ //iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
                       iEnd = iIndex;
                       //alert("it is equal and iend is iIndex:" + iEnd);
                }else{
                    //alert("break");
                    break;
                }
            }
        }
        //alert("hello");
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
	if(flag == 4) {
		return true;
	}
	//开始时间
    var yb = watchStaBean.begindate.value.substring(0,4);
	var mb = parseInt(watchStaBean.begindate.value.substring(5,7) ,10);
	var db = parseInt(watchStaBean.begindate.value.substring(8,10),10);
    //结束时间
    var ye = watchStaBean.enddate.value.substring(0,4);
	var me = parseInt(watchStaBean.enddate.value.substring(5,7) ,10);
	var de = parseInt(watchStaBean.enddate.value.substring(8,10),10);
    if(yb == "" && ye != "" ) {
	  alert("请输入查询的开始时间!");
      //document.all.item(strID).value="";
      watchStaBean.begindate.focus();
      return false;
	}
	// 在开始和结束时间都输入的情况下做不能跨年度的判断
	if(yb != "" && ye != "") {
		//if(yb!=ye){
      	//	alert("查询时间段不能跨年度!");
      	//	document.all.item(strID).value="";
      	//	watchStaBean.enddate.focus();
      	//	return false;
    	//}
		if(ye < yb) {
			alert("查询结束日期不能小于开始日期!");
      		document.all.item(strID).value="";
      		watchExeQuery.enddate.focus();
      		return false;
		}
		if((ye == yb ) && (me < mb  || de < db)) {
     		alert("查询结束日期不能小于开始日期!");
      		document.all.item(strID).value="";
      		watchStaBean.enddate.focus();
      		return false;
    	}
	}
	return true;
}
 function doReset() {

	if(document.getElementById("cId") != null) {
		document.getElementById("cId").value = "";
	}
	if(document.getElementById("workID") != null) {
		document.getElementById("workID").value = "";
	}
	if(document.getElementById("uId") != null) {
		document.getElementById("uId").value = "";
	}
	if(document.getElementById("userID") != null) {
		document.getElementById("userID").value = "";
	}
	if(document.getElementById("wId") != null) {
		document.getElementById("wId").value = "";
	}
	if(document.getElementById("wID") != null) {
		document.getElementById("wID").value = "";
	}
	if(document.getElementById("begindate") != null) {
		document.getElementById("begindate").value = "";
	}
	if(document.getElementById("enddate") != null) {
		document.getElementById("enddate").value = "";
	}
	if(document.getElementById("queryname") != null) {
		document.getElementById("queryname").value = "";
	}
 }
//////////////////////////////////////////////////////////////////////////////////////////////////
</script>
<Br/>
<%
UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
"LOGIN_USER");
if(userinfo.getRegionID().substring( 2, 6 ).equals( "0000" )){%>
<body onload="onChangeCon()">
<% }else{%>
<body>
<% }%>
<span id="mainSpan" style="display:">
  <template:titile value="外力盯防执行情况统计"/>
  <html:form  action="/WatchExeStaAction.do?method=getWatchSta" target="dataFormFrame">
    <template:formTable contentwidth="200" namewidth="200">

    <logic:notEmpty name="reginfo">
          <template:formTr name="所属区域" isOdd="false">
            <select name="regionid" class="inputtext" style="width:180" id="rId" onchange="onChangeCon()">
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

      <template:formTr name="统计类型" isOdd="false">

        <logic:equal value="group" name="PMType">
          	<input type="radio" checked="true" name="type" value="2" onclick="settype(2)"/> 巡检维护组
        </logic:equal>
        <logic:notEqual value="group" name="PMType">
          	<input type="radio" name="type" value="2" onclick="settype(2)"/> 巡检人
        </logic:notEqual>
        <input type="radio" name="type" value="3" onclick="settype(3)"/> 外力盯防
        <input type="radio" name="type" value="4" onclick="settype(4)"/> 盯防名称
        <html:hidden property="statype"/>
      </template:formTr>

        <logic:notEmpty name="coninfo">
          <template:formTr name="代维单位" tagID="contractorTr">
            <select name="contractorid" class="inputtext" style="width:180px" id="cId" onchange="onChangeDeptUser(),onChangeDeptWatch()">
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
              <select name="workID" style="display:none" id="workID">
                <logic:iterate id="coninfoId" name="coninfo">
                    <option value="<bean:write name="coninfoId" property="contractorid"/>"><bean:write name="coninfoId" property="regionid"/>--<bean:write name="coninfoId" property="contractorname"/></option>
                </logic:iterate>
              </select>
          </logic:present>

        </logic:notEmpty>
    <logic:equal value="group" name="PMType">
    <template:formTr name="巡检维护组" isOdd="false" tagID="patrolTr" style="display:none">
      <select name="executorid" class="inputtext" style="width:180px" id="uId">
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
    <template:formTr name="巡 检 人" isOdd="false" tagID="patrolTr" style="display:none">
      <select name="executorid" class="inputtext" style="width:180px" id="uId">
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
              <select name="userID" id="userID" style="display:none">
                <logic:iterate id="uinfoId" name="uinfo">
                    <option value="<bean:write name="uinfoId" property="patrolid"/>"><bean:write name="uinfoId" property="regionid"/>--<bean:write name="uinfoId" property="parentid"/>--<bean:write name="uinfoId" property="patrolname"/></option>
                </logic:iterate>
              </select>
              </logic:present>


    <template:formTr name="外力盯防" tagID="watchTr" style="display:none">
      <select name="watchid" class="inputtext" style="width:180px" id="wId">
        <option value="">不限</option>
        <logic:present name="watchinfo">
          <logic:iterate id="watchinfoId" name="watchinfo">
            <option value="<bean:write name="watchinfoId" property="placeid"/>">
              <bean:write name="watchinfoId" property="placename"/>
            </option>
          </logic:iterate>
        </logic:present>
      </select>
    </template:formTr>


    <logic:present name="watchinfo">
      <select name="wID" id="wID" style="display:none">
        <logic:iterate id="watchinfoId" name="watchinfo">
          <option value="<bean:write name="watchinfoId" property="placeid"/>"><bean:write name="watchinfoId" property="regionid"/>--<bean:write name="watchinfoId" property="contractorid"/>--<bean:write name="watchinfoId" property="placename"/></option>
        </logic:iterate>
      </select>
    </logic:present>

	<template:formTr name="开始日期" isOdd="false" style="display:" tagID="bDate">
      <html:text property="begindate" styleClass="inputtext" size="25" maxlength="45" />
      <apptag:date property="begindate"/>
    </template:formTr>

    <template:formTr name="结束日期"   tagID="eDate" style="display:">
        <html:text property="enddate"  value="" styleClass="inputtext" style="width:160" readonly="true"/>
        <input type='button' value='▼' id='btn'  onclick="GetSelectDatenew('enddate')" style="font:'normal small-caps 6pt serif';" >
    </template:formTr>
    
    <template:formTr name="盯防名称"   tagID="querynameTr" style="display:none">
        <html:text property="queryname"  value="" styleClass="inputtext" style="width:160"/>  
    </template:formTr>


        <template:formSubmit>
          <td>
            <html:submit styleClass="button" onclick="return checkForm()">查询</html:submit>
          </td>
          <td>
        
            <input type="button" value="重置" class="button" onClick="doReset()"/>
          </td>

        </template:formSubmit>
    </template:formTable>
  </html:form>
  <script language="javascript" type="">
	settype(2);
	
  </script>
</span>
<iframe name="hiddenInfoFrame"  style="display:none">
</iframe>
<div id="iframeDiv" style="height:320">
  <iframe name=dataFormFrame border=0 marginWidth=0 marginHeight=0 src="${ctx}/common/blank.html" frameBorder=0 width="100%" scrolling="auto" height="100%">  </iframe>
</div>
