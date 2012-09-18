<%@include file="/common/header.jsp"%>
<%
  String regionID = request.getParameter("regionID");
%>
<script language="javascript" type="">
<!--

var iteName = "";
var rowArr = new Array();//一行的信息
var infoArr = new Array();//所有的信息

    //初始化数组
    function initArray(conid,conname,pid,pname){
        rowArr[0] = conid;
        rowArr[1] = conname;
        rowArr[2] = pid;
        rowArr[3] = pname;
        infoArr[infoArr.length] = rowArr;
        rowArr = new Array();
        return true;
    }
    function onSelectChange(objId ) //自动设置保护对象名称选项
    {
        var slt1Obj=document.getElementById(objId);
        var slt2Obj=document.getElementById("pId");
        i=0;
        for(j= 0; j< this.infoArr.length; j++ )
        {
            if(slt1Obj.value == infoArr[j][0])
            {
                i++;
            }
        }
        slt2Obj.options.length = i;
        k=0;
        for(j =0;j<this.infoArr.length;j++)
        {
            if(slt1Obj.value == infoArr[j][0])
                {
                    slt2Obj.options[k].text=this.infoArr[j][3];
                    slt2Obj.options[k].value=this.infoArr[j][2];
                    k++;
                }
      }
        return true;
    }

    function bodyOnload(){
        onSelectChange("conId" ) ;
    }

function getTimeWin(objName){

    iteName = objName;

    showx = event.screenX - event.offsetX - 4 - 210 ;
    showy = event.screenY - event.offsetY + 18;

    var timeWin = window.showModalDialog("${ctx}/common/time.html",self, "dialogWidth:255px; dialogHeight:285px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; status:no; directories:yes;scrollbars:no;Resizable=no;help:no");

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
    //watchBean.endpointid.disabled = flag;

}

function toSetVis(visStr){

    for(var i = 0; i < toHideTr.length; i ++){

        toHideTr[i].style.display = visStr;
    }

}

function isPosInteger(inputVal) {
var oneChar
    inputStr=inputVal.toString()
    for (var i=0;i<inputStr.length;i++) {
        oneChar=inputStr.charAt(i)
        if (oneChar<"0" || oneChar>"9") {
            return false
        }
    }
    return true
}


function isValidForm() {
  var btime = watchBean.orderlyBeginTime.value;
    var etime = watchBean.orderlyEndTime.value;
    var b = btime.substring(0,2);
    var e = etime.substring(0,2);
    var s = Math.abs(e-b);
    if( b > e ){
      s = 24-s;
    }


    if(watchBean.placeName.value==""){
        alert("名称不能为空! ");
        watchBean.placeName.focus();
        return false;
    }


    if (isNaN(watchBean.watchwidth.value)){
        alert("盯防范围应该为数字型! ");
        watchBean.watchwidth.focus();
        return false;
    }

    if(watchBean.principal.value==""){
        alert("盯防负责人/组不能为空! ");
        watchBean.principal.focus();
        return false;
    }

    if(watchBean.beginDate.value==""){
        alert("盯防开始日期不能为空! ");
        watchBean.beginDate.focus();
        return false;
    }




    if(watchBean.patrolTime.value==""){
        alert("盯防开始时间不能为空! ");
        watchBean.patrolTime.focus();
        return false;
    }
    if(watchBean.orderlyBeginTime.value==""){
        alert("值班开始时间不能为空! ");
        watchBean.orderlyBeginTime.focus();
        return false;
    }
    if(watchBean.orderlyEndTime.value==""){
        alert("值班结束时间不能为空! ");
        watchBean.orderlyEndTime.focus();
        return false;
    }

    if(watchBean.error.value==""){
        alert("值班时间间隔不能为空! ");
        watchBean.error.focus();
        return false;
    }
    if (isNaN(watchBean.error.value)){
        alert("值班时间间隔应该为数字型! ");
        watchBean.error.focus();
        return false;
    }
    if(!isPosInteger(watchBean.error.value)){
      alert("值班时间间隔只能是整数!");
      watchBean.error.focus();
      return false;
    }
    if(watchBean.error.value > s){
      alert("值班时间不能大于总值班时间");
      watchBean.error.focus();
      return false;
    }
        if(watchBean.involvedsegmentlist.value == ""){
      alert("受影响光缆不能为空");
      watchBean.error.focus();
      return false;
    }
        if(watchBean.involvedlinenumber.value == ""){
      alert("受影响光缆数不能为空");
      watchBean.error.focus();
      return false;
    }
    return true;
}
function toClearEndDate(){
    watchBean.endDate.value = "待定";
}

function setMapRefresh(oncetool){
  //    opener.parent.Map.frmMapOpt.OnceTool.value=oncetool;
  //    opener.parent.Map.frmMapOpt.submit();
}

function countNum(){

    var k = 0;
    for(var i = 0; i < watchBean.involvedsegmentlist.options.length; i++){

        if(watchBean.involvedsegmentlist.options[i].selected == true){
            k++;
        }

    }

    watchBean.involvedlinenumber.value = k;

}
//-->
</script>
<logic:present name="coninfo">
  <logic:iterate id="coninfoId" name="coninfo">
<script type="" language="javascript">
initArray("<bean:write name="coninfoId" property="contractorid"/>","<bean:write name="coninfoId" property="contractorname"/>",
"<bean:write name="coninfoId" property="patrolid"/>","<bean:write name="coninfoId" property="patrolname"/>");
</script>
  </logic:iterate>
</logic:present>
<body onload="bodyOnload()">
<template:titile value="增加外力盯防现场信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/watchAction.do?method=addWatch4GIS">
  <template:formTable >
    <template:formTr name="盯防名称">
      <html:text property="placeName" styleClass="inputtext" style="width:160" maxlength="45"/>
      <html:hidden property="startpointid"/>
    </template:formTr>
   <%if(regionID!=null){%> <input type="hidden" name="regionID" value="<%=regionID%>"><%}%>
    <template:formTr name="代维单位" isOdd="false">
      <apptag:setSelectOptions valueName="deptCollection" tableName="contractorinfo" columnName1="contractorname" columnName2="contractorid" region="true" regionID="<%=regionID%>"/>
      <html:select property="contractorid" styleClass="inputtext" styleId="conId" style="width:160" onchange="onSelectChange(id)">
        <html:options collection="deptCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>

    <logic:equal value="group" name="PMType">
            <template:formTr name="盯防负责组">
              <apptag:setSelectOptions valueName="patrolCollection" tableName="patrolmaninfo" columnName1="patrolname" columnName2="patrolid" region="true" regionID="<%=regionID%>"/>
              <html:select property="principal" styleClass="inputtext" style="width:160" styleId="pId" >
                <html:options collection="patrolCollection" property="value" labelProperty="label"/>
              </html:select>
            </template:formTr>
    </logic:equal>
    <logic:notEqual value="group" name="PMType">
            <template:formTr name="盯防负责人">
              <apptag:setSelectOptions valueName="patrolCollection" tableName="patrolmaninfo" columnName1="patrolname" columnName2="patrolid" region="true" regionID="<%=regionID%>"/>
              <html:select property="principal" styleClass="inputtext" style="width:160" styleId="pId" >
                <html:options collection="patrolCollection" property="value" labelProperty="label"/>
              </html:select>
            </template:formTr>
    </logic:notEqual>


    <template:formTr name="所属区域" isOdd="false">
      <html:text property="innerregion" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
    <template:formTr name="工地位置">
      <html:text property="watchplace" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
 <logic:equal value="show" name="ShowFIB">

    <template:formTr name="工地类型">
      <html:select property="placetype" styleClass="inputtext" style="width:160">
          <html:option value="拆建楼房">拆建楼房</html:option>
          <html:option value="道路改造">道路改造</html:option>
          <html:option value="地铁施工">地铁施工</html:option>
          <html:option value="地下管道修建">地下管道修建</html:option>
          <html:option value="房屋拆建">房屋拆建</html:option>
          <html:option value="公路扩建">公路扩建</html:option>
          <html:option value="光缆承载物质量">光缆承载物质量</html:option>
          <html:option value="基站装修">基站装修</html:option>
      </html:select>
    </template:formTr>
 </logic:equal>
 <logic:notEqual value="show" name="ShowFIB">

    <template:formTr name="工地类型">
    <html:text property="placetype" styleClass="inputtext" style="width:160" value="" />

      <!--
      <html:select property="placetype" styleClass="inputtext" style="width:160">
          <html:option value="拆建楼房">拆建楼房</html:option>
          <html:option value="道路改造">道路改造</html:option>
          <html:option value="地铁施工">地铁施工</html:option>
          <html:option value="地下管道修建">地下管道修建</html:option>
          <html:option value="房屋拆建">房屋拆建</html:option>
          <html:option value="公路扩建">公路扩建</html:option>
          <html:option value="基站装修">基站装修</html:option>
      </html:select>
      -->
    </template:formTr>
 </logic:notEqual>
    <template:formTr name="盯防级别">
      <html:select property="dangerlevel" styleClass="inputtext" style="width:160">
        <html:option value="紧急">紧急</html:option>
        <html:option value="重要">重要</html:option>
        <html:option value="一般">一般</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="隐患原因" isOdd="false">
      <html:text property="watchreason" styleClass="inputtext" style="width:160" maxlength="60"/>
    </template:formTr>
    <template:formTr name="GPS坐标">
      <html:text property="gpscoordinate" styleClass="inputtext" style="width:160" maxlength="17"/>
    </template:formTr>
    <template:formTr name="盯防半径" isOdd="false">
      <html:text property="watchwidth" styleClass="inputtext" style="width:160" value="50"/>
      &nbsp;
      (单位：米)
    </template:formTr>
    <template:formTr name="开始日期" isOdd="false">
      <html:text property="beginDate" styleClass="inputtext" style="width:160" maxlength="45" readonly="true"/>
      <apptag:date property="beginDate"/>
    </template:formTr>
    <template:formTr name="结束日期">
      <html:text property="endDate" styleClass="inputtext" style="width:160" maxlength="45" readonly="true"/>
      <apptag:date property="endDate"/>
      &nbsp;&nbsp;
      <input type="button" value="待定" onclick="toClearEndDate()">
    </template:formTr>
    <template:formTr name="计划巡检时间" style="display:none">
      <html:text property="patrolTime" styleClass="inputtext" style="width:160" maxlength="45" value="2005/01/01"/>
      <apptag:date property="patrolTime"/>
    </template:formTr>
    <template:formTr name="值班开始时间" isOdd="false">
      <html:text property="orderlyBeginTime" styleClass="inputtext" style="width:160" maxlength="45" readonly="true"/>
      <input type='button' value="▼" id='btn' onclick="getTimeWin('orderlybegintime')" style="font:'normal small-caps 6pt serif';">
    </template:formTr>
    <template:formTr name="值班结束时间">
      <html:text property="orderlyEndTime" styleClass="inputtext" style="width:160" maxlength="45" readonly="true"/>
      <input type='button' value="▼" id='btn' onclick="getTimeWin('orderlyEndTime')" style="font:'normal small-caps 6pt serif';">
    </template:formTr>
    <template:formTr name="信息发送间隔" isOdd="false">
      <html:text property="error" styleClass="inputtext" style="width:160" maxlength="45"/>
      &nbsp;
      (单位：小时)
    </template:formTr>
       <logic:equal value="show" name="ShowFIB">
    <template:formTr name="受影响光缆名称" isOdd="false">
      <apptag:setSelectOptions valueName="cableCollection" tableName="repeatersection" columnName1="segmentname" columnName2="kid"/>
      <html:select property="involvedsegmentlist" styleClass="multySelect" style="width:260" size="10" multiple="true">
        <html:options collection="cableCollection" property="value" labelProperty="label"/>
      </html:select>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <span style="color:red">(按住Ctr键,可选择多项)</span>
    </template:formTr>
    <template:formTr name="受影响光缆总段数" isOdd="false">
      <html:text property="involvedlinenumber" styleClass="inputtext" style="width:160" onclick="countNum()"/>
    </template:formTr>
   </logic:equal>

   <logic:notEqual value="noShow" name="ShowFIB">
   </logic:notEqual>
    <template:formSubmit>
      <td >
        <html:submit styleClass="button" onclick="setMapRefresh('RefreshWatchPoint')">增加</html:submit>
      </td>
      <td >
        <html:button property="action"styleClass="button"  onclick="self.close()">取消</html:button>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe id="hiddenFrame" style="display:none">
</iframe>
