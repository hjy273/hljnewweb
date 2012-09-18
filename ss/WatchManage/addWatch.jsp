<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo"%>

<%
  String GPS = request.getParameter("sPID");
  String regionID = request.getParameter("regionID");
  System.out.println("hello:" + regionID);
  String name = request.getParameter("pointname");
  String tempID = request.getParameter("tempID");
  String ss = (String)session.getAttribute("gpscoordinate");
  String rr = (String)session.getAttribute("regionname");
  String x = (String)session.getAttribute("x");
  String y = (String)session.getAttribute("y");
  //System.out.println("        gpscoordinate="+GPS+"  regionID="+regionID+"  name="+name+"  tempID="+tempID);
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
    function onSelectChange(objId ) //
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

     //结束日期必须大于等于开始日期
    var yb = watchBean.beginDate.value.substring(0,4);
    var mb = parseInt(watchBean.beginDate.value.substring(5,7) ,10) - 1;
    var db = parseInt(watchBean.beginDate.value.substring(8,10),10);
    var bdate = new Date(yb,mb,db);	  

    var ye = watchBean.endDate.value.substring(0,4);
    var me = parseInt(watchBean.endDate.value.substring(5,7) ,10) - 1;
    var de = parseInt(watchBean.endDate.value.substring(8,10),10);
    var edate = new Date(ye,me,de);
    if(edate < bdate){
      alert("结束日期不能小于开始日期!");
      return false;
    }
    //结束时间必须大于等于开始时间
    var hb = parseInt(watchBean.orderlyBeginTime.value.substring(0,2),10);
	//var hb = parseInt(watchBean.orderlyBeginTime.value.substring(0,2),10) +  parseInt(watchBean.error.value,10);
    var mb = parseInt(watchBean.orderlyBeginTime.value.substring(3,5) ,10) ;
    var sb = parseInt(watchBean.orderlyBeginTime.value.substring(6,8),10);
    var db = new Date();
    db.setHours(hb, mb, sb);
    var he = parseInt(watchBean.orderlyEndTime.value.substring(0,2),10);
    var me = parseInt(watchBean.orderlyEndTime.value.substring(3,5) ,10);
    var se = parseInt(watchBean.orderlyEndTime.value.substring(6,8),10);
    var de = new Date();
    de.setHours(he, me, se);
    if(de < db){
        alert("值班结束日期不能小于开始日期!");
        return false;
    }

    if(watchBean.placeName.value==""){
        alert("盯防名称不能为空! ");
        watchBean.placeName.focus();
        return false;
    }


    if (isNaN(watchBean.watchwidth.value)){
        alert("盯防范围应该为数字型! ");
        watchBean.watchwidth.focus();
        return false;
    } 

	if(parseInt(watchBean.watchwidth.value) > 300){
        alert("盯防范围不能超过300米! ");
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

	// 取得系统日期
	var d = new Date();
	var s = '';
	s += d.getYear() + "/"; 
    if((d.getMonth() + 1) > 9) {
		s += (d.getMonth() + 1) + "/";            // 获取月份。
	} else {
		s += "0" + (d.getMonth() + 1) + "/";            // 获取月份。
	}	
   	// 获取日。
	if(d.getDate() > 9) {
		s += d.getDate();
	 } else {
		s = s + "0" + d.getDate()
	 }
	 if(s > watchBean.beginDate.value) {
		alert("开始日期不能小于当前日期!");
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
        alert("信息发送间隔不能为空! ");
        watchBean.error.focus();
        return false;
    }

	if(watchBean.error.value=="0"){
        alert("信息发送间隔不能为0! ");
        watchBean.error.focus();
        return false;
    }

    if (isNaN(watchBean.error.value)){
        alert("信息发送间隔应该为数字型! ");
        watchBean.error.focus();
        return false;
    }
    if(!isPosInteger(watchBean.error.value)){
      alert("信息发送间隔只能是整数!");
      watchBean.error.focus();
      return false;
    }
    if(watchBean.error.value > s){
      alert("信息发送间隔不能大于值班时间");
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

function countNum(){
  //alert(watchBean.involvedsegmentlist.value);
  if(watchBean.involvedlinenumber.value!=watchBean.involvedsegmentlist.length){
    if(watchBean.involvedsegmentlist.length==undefined){
      if(watchBean.involvedsegmentlist.value=="null"){
        watchBean.involvedlinenumber.value = 0;
      }else{
        watchBean.involvedlinenumber.value = 1;
      }
    }else{
      watchBean.involvedlinenumber.value = watchBean.involvedsegmentlist.length;
    }
  }
}

function toEditCable(fID) {
    //fID 1,增加 2,修改;
    var pageName = "${ctx}/WatchManage/getWatchRelativeCable4GIS.jsp?fID="+fID+"&regionID=<%=regionID%>";

    var pointsPop = window.open(pageName,'pointsPop','width=400,height=300,scrollbars=yes');
    //,resizable=yes,,status=yes
    pointsPop.focus();
}
function addGoBack()
        {
        	try{
            	location.href = "${ctx}/WatchExecuteQueryAction.do?method=queryAllTempWatchPoint";
                return true;
            }
            catch(e){
            	alert(e);
            }
        }
//-->
</script>
  <%
    UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
        "LOGIN_USER");
    if (userinfo.getDeptype().equals("1")) {
  %>
<logic:present name="coninfo">
  <logic:iterate id="coninfoId" name="coninfo">
<script type="" language="javascript">
                  initArray("<bean:write name="coninfoId" property="contractorid"/>","<bean:write name="coninfoId" property="contractorname"/>",
                  "<bean:write name="coninfoId" property="patrolid"/>","<bean:write name="coninfoId" property="patrolname"/>");
                  </script>
  </logic:iterate>
</logic:present>
<body onload="bodyOnload()">
  <%}else{
  %>
<body>
<%}%>
<template:titile value="增加外力盯防现场信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/watchAction.do?method=addWatch">
  <template:formTable contentwidth="250" namewidth="250">
    <template:formTr name="盯防名称" isOdd="false">
      <html:text property="placeName" styleClass="inputtext" style="width:160" maxlength="45"/>
      <html:hidden property="startpointid"/>
    </template:formTr>
  <%
        System.out.println(" get userinfo.getDeptype() = "+userinfo.getDeptype());
    if (userinfo.getDeptype().equals("1")) {
  %>
    <template:formTr name="代维单位" isOdd="false">
      <apptag:setSelectOptions valueName="deptCollection" tableName="contractorinfo" columnName1="contractorname" columnName2="contractorid" region="true" regionID="<%=regionID%>"/>
      <html:select property="contractorid" styleClass="inputtext" styleId="conId" style="width:160" onchange="onSelectChange(id)">
        <html:options collection="deptCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <input type="hidden" name="regionID" value="<%=regionID%>">
    <input type="hidden" name="tempID" value="<%=tempID%>">
    <logic:equal value="group" name="PMType">
      <template:formTr name="盯防负责组" isOdd="false">
        <apptag:setSelectOptions valueName="patrolCollection" tableName="patrolmaninfo" columnName1="patrolname" columnName2="patrolid" region="true" regionID="<%=regionID%>"/>
        <html:select property="principal" styleClass="inputtext" styleId="pId" style="width:160">
          <html:options collection="patrolCollection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
    </logic:equal>
    <logic:notEqual value="group" name="PMType">
      <template:formTr name="盯防负责人" isOdd="false">
        <apptag:setSelectOptions valueName="patrolCollection" tableName="patrolmaninfo" columnName1="patrolname" columnName2="patrolid" region="true" regionID="<%=regionID%>"/>
        <html:select property="principal" styleClass="inputtext" styleId="pId" style="width:160">
          <html:options collection="patrolCollection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
    </logic:notEqual>
  <%
    } if (userinfo.getDeptype().equals("2")) {

      String contractorid = (String)session.getAttribute("cid");
  %>
    <input type="hidden" name="contractorid" value="<%=contractorid%>">
  <%
    String sql = "select * from patrolmaninfo where state is null and  parentid = '" + contractorid + "'";
    java.sql.ResultSet rs = null;
    com.cabletech.commons.hb.QueryUtil util = new com.cabletech.commons.hb.QueryUtil();
    rs = util.executeQuery(sql);
  %>
  <input type="hidden" name="contractorid"  value="<%=contractorid%>">
    <logic:equal value="group"   name="PMType">
            <template:formTr name="盯防负责组">
              <html:select property="principal" styleClass="inputtext" style="width:160">
              <%while (rs.next()) {      %>
                <html:option value="<%=rs.getString(1)%>"><%=rs.getString(2)%>        </html:option>
              <%}  rs.close();    %>
              </html:select>
            </template:formTr>
    </logic:equal>
    <logic:notEqual value="group" name="PMType">
            <template:formTr name="盯防负责人">
              <html:select property="principal" styleClass="inputtext" style="width:160">
              <%while (rs.next()) {      %>
                <html:option value="<%=rs.getString(1)%>"><%=rs.getString(2)%>        </html:option>
              <%}  rs.close();    %>
              </html:select>
            </template:formTr>
    </logic:notEqual>
  <%}  %>
    <template:formTr name="所属区域" isOdd="false">
      <%if(ss!=null){%>
      	<%=rr%>
      	<html:hidden property="gpscoordinate" styleClass="inputtext" style="width:160" value="<%=ss%>" readonly="true"/>
      	<html:hidden property="innerregion"  value="<%=rr%>"/>
      <%}else{%>
      <html:text property="innerregion" styleClass="inputtext" style="width:160"  maxlength="45"/>
      <%}%>
    </template:formTr>
    <template:formTr name="工地位置" isOdd="false">
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
        <html:text property="placetype" styleClass="inputtext" style="width:160" value=""/>
      </template:formTr>
    </logic:notEqual>
    <template:formTr name="盯防级别" isOdd="false">
      <html:select property="dangerlevel" styleClass="inputtext" style="width:160">
        <html:option value="紧急">紧急</html:option>
        <html:option value="重要">重要</html:option>
        <html:option value="一般">一般</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="隐患原因">
      <html:text property="watchreason" styleClass="inputtext" style="width:160" maxlength="60"/>
    </template:formTr>
    <template:formTr name="经度" isOdd="false">
    	<%=x %>
      <%if(ss!=null){%>
      <html:hidden property="gpscoordinate" styleClass="inputtext" style="width:160" value="<%=ss%>" readonly="true"/>
      <%}else{%>
      <html:hidden property="gpscoordinate" styleClass="inputtext" style="width:160" value="<%=GPS%>" readonly="true" />
      <%}%>
    </template:formTr>
    <template:formTr name="纬度" isOdd="false"><%=y %></template:formTr>
    <template:formTr name="盯防半径">
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
      <template:formTr name="受影响光缆名称">
        <span id = "listSpan"><br>
          <table>
          <%
          String [][] kidArr = (String[][])request.getAttribute("tempArr");
          if( kidArr != null){
            for(int i = 0; i < kidArr.length; i++){
              %>
              <tr>
                <td>
                  <input type= "hidden" name="involvedsegmentlist" value="<%=kidArr[i][0]%>">
                  <input type="text" name ="cablename" value="<%=kidArr[i][1]%>" style = "border:0;background-color:transparent;width:160;font-size:9pt"  readonly>
                </td>
              </tr>
              <%
              }
            } else {%>
            <input type= "hidden" name="involvedsegmentlist" value="null">
            <%}
            %>
            </table>
        </span>
        <br>
        <br>
        <span id="toEditSubListSpan"><a href="javascript:toEditCable(2)">添加编辑受影响光缆</a></span>
      </template:formTr>
      <template:formTr name="受影响光缆总段数" isOdd="false">
        <html:text property="involvedlinenumber" styleClass="inputtext" style="width:160"  onclick="countNum()"/>
      </template:formTr>
    </logic:equal>
    <logic:notEqual value="noShow" name="ShowFIB">    </logic:notEqual>
    <logic:equal value="show" name="ShowFIB">
	    <template:formSubmit>
	      <td>
	        <html:submit styleClass="button" onmouseover="countNum()">增加</html:submit>
	      </td>    
	      <td>
	      <%if(ss!=null){%>
	        <html:reset styleClass="button">重置</html:reset>
	      <%}else{%>
	        <html:button property="action"styleClass="button"  onclick="self.close()">取消</html:button>
	      <%}%>
	      </td>
	      <td>
	        <input type="button" class="button" value="返回" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"/>
	      </td>
	    </template:formSubmit>
    </logic:equal> 
    <logic:notEqual value="show" name="ShowFIB">
    	<template:formSubmit>
	      <td>
	        <html:submit styleClass="button">增加</html:submit>
	      </td>    
	      <td>
	      <%if(ss!=null){%>
	        <html:reset styleClass="button">重置</html:reset>
	      <%}else{%>
	        <html:button property="action"styleClass="button"  onclick="self.close()">取消</html:button>
	      <%}%>
	      </td>
	      <td>
	        <html:button property="action" styleClass="button" onclick="addGoBack()" >返回</html:button>
	      </td>
	    </template:formSubmit>
    </logic:notEqual>
  </template:formTable>
</html:form>
<iframe id="hiddenFrame" style="display:none"></iframe>
</body>
