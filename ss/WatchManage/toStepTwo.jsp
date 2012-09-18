<%@page import="com.cabletech.watchinfo.beans.*"%>
<%@page import="java.util.*"%>

<%@ include file="/common/header.jsp"%>
<%
Vector vct = new Vector();
vct = (Vector) request.getSession().getAttribute("watchSubList");
//System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%" + vct.size());
%>
<script language="javascript" type="">
<!--

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

function preSetEndDate(){
	if(watchBean.endDate.value == ""){
		watchBean.endDate.value = "待定";
	}
}

function loadForm(){
	var url = "${ctx}/watchAction.do?method=loadWatchAsObj&id=" + watchBean.placeID.value;
	self.location.replace(url);
}


var showflag = -1;
function showhideSpan(){

	if(showflag == 1){
		showflag = -1;
		basicInfoSpan.style.display = "none";
		lableSpan.innerHTML = "显示基本信息";
	}else{
		showflag = 1;
		basicInfoSpan.style.display = "";
		lableSpan.innerHTML = "隐藏基本信息";
	}
}

var showflag_one = -1;
function showhideSpanOne(){

	if(showflag_one == 1){
		showflag_one = -1;
		stepOneInfoSpan.style.display = "none";
		lableSpan_one.innerHTML = "显示施工工地信息";
	}else{
		showflag_one = 1;
		stepOneInfoSpan.style.display = "";
		lableSpan_one.innerHTML = "隐藏施工工地信息";
	}
}

function countNum(){

	var k = 0;

	for(var i = 0; i < watchBean[1].involvedsegmentlist.options.length; i++){

		if(watchBean[1].involvedsegmentlist.options[i].selected == true){
			k++;
		}

	}

	watchBean[1].involvedlinenumber.value = k;
}


function loadSubWatch(id) {
	var pageName = "${ctx}/watchAction.do?method=loadSubWatch&id="+id;

	var taskPop = window.open(pageName,'taskPop','width=436,height=360,scrollbars=yes');

	taskPop.focus();
}

function toGetBack(){
        var url = "${ctx}/watchAction.do?method=loadAllDoneWatches";
        self.location.replace(url);

}
//-->
function isValidForm() {
    if(document.getElementById("cid").value==""){
        alert("核查信息不能为空! ");
        return false;
    }
    return true;

}

</script>

<template:titile value="核查信息"/>
<br>
<a href = "javascript:showhideSpan()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="lableSpan">显示基本信息</span></a>
<br><span id = "basicInfoSpan" style="display:none">

<html:form   method="Post"
    action="/watchAction.do?method=updateWatch" >
    <template:formTable contentwidth="250" namewidth="300">
        <template:formTr name="盯防名称"   isOdd="false" >
            <bean:write name="watchBean" property="placeName"/>
			<html:hidden property="placeID"/>
			<html:hidden property="regionID"/>
        </template:formTr>

		<template:formTr name="经度">
			<bean:write name="watchBean" property="x"/> 
		</template:formTr> 
		<template:formTr name="纬度">
			<bean:write name="watchBean" property="y"/>
		</template:formTr> 

        <template:formTr name="盯防半径"  isOdd="false" >
        	<bean:write name="watchBean" property="watchwidth"/>&nbsp;米
        </template:formTr>

 <%
  WatchBean bean = (WatchBean) request.getAttribute("watchBean");
  com.cabletech.commons.hb.QueryUtil util = new com.cabletech.commons.hb.QueryUtil();
    String sql = "select * from patrolmaninfo where state is null and patrolid = '" + bean.getPrincipal() + "'";
    java.sql.ResultSet rs = null;
    rs = util.executeQuery(sql);
  %>
  <logic:equal value="group" name="PMType">
  		<template:formTr name="盯防负责组">
		       <%while (rs.next()) { %>
           		<%=rs.getString(2)%>
            <%}%> 
		</template:formTr>
  </logic:equal>
  <logic:notEqual value="group" name="PMType">
  	<template:formTr name="盯防负责人">
		     
		      <%while (rs.next()) { %>
           		<%=rs.getString(2)%>
            <%}%> 
	</template:formTr>
  </logic:notEqual>


        <template:formTr name="开始日期"  isOdd="false" >
        	<bean:write name="watchBean" property="beginDate"/>
        </template:formTr>

        <template:formTr name="结束日期" >
        	<bean:write name="watchBean" property="endDate"/>
        </template:formTr>

        <template:formTr name="计划巡检时间" style="display:none" >
            <html:text property="patrolTime" styleClass="inputtext"  style="width:260"  maxlength="45" value="2005/01/01"/>
        </template:formTr>

        <template:formTr name="值班开始时间"  isOdd="false" >
        	<bean:write name="watchBean" property="orderlyBeginTime"/>
        </template:formTr>

        <template:formTr name="值班结束时间">
       		<bean:write name="watchBean" property="orderlyEndTime"/>
        </template:formTr>

        <template:formTr name="信息发送间隔"  isOdd="false" >
        	<bean:write name="watchBean" property="error"/>&nbsp;小时
        </template:formTr>
   </template:formTable>
</html:form>

</span>
<br>
<a href = "javascript:showhideSpanOne()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="lableSpan_one">显示施工工地信息</span></a>
<br>
<span id = "stepOneInfoSpan" style="display:none">
<html:form method="Post" onsubmit="countNum()" action="/watchAction.do?method=FinishStepOne" >
    <template:formTable contentwidth="250" namewidth="300">

	    <template:formTr name="盯防名称"   isOdd="false" >
			<bean:write name="watchBean" property="placeName"/>
			<html:hidden property="placeID"/>
			<html:hidden property="regionID"/>
			<html:hidden property="dealstatus"/>
        </template:formTr>

		<template:formTr name="区域"  isOdd="false" >
             <bean:write name="watchBean" property="innerregion"/>
        </template:formTr>

		<template:formTr name="工地位置"  isOdd="false" >
             <bean:write name="watchBean" property="watchplace"/>
        </template:formTr>

		<template:formTr name="工地类型"  isOdd="false" >
             <bean:write name="watchBean" property="placetype"/>
        </template:formTr>

		<template:formTr name="盯防级别"  isOdd="false" >
           <bean:write name="watchBean" property="dangerlevel"/>
		 
        </template:formTr>

		<template:formTr name="隐患原因"  isOdd="false" >
             <bean:write name="watchBean" property="watchreason"/>
        </template:formTr>

	    <template:formTr name="结束工地处理情况"  isOdd="false" >
              <bean:write name="watchBean" property="endwatchinfo"/>
        </template:formTr>


    <logic:equal value="show" name="ShowFIB">
	    <template:formTr name="受影响光缆总段数"  isOdd="false">
	   		 <bean:write name="watchBean" property="involvedlinenumber"/>
        </template:formTr>
    </logic:equal>

   <logic:notEqual value="noShow" name="ShowFIB">
   </logic:notEqual>

   </template:formTable>
</html:form>
</span>
<!-- 核查信息 -->
<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;施工工地核查信息
<br>

<html:form method="Post" onsubmit="countNum()" action="/watchAction.do?method=FinishStepTwo" >
    <template:formTable contentwidth="250" namewidth="300">

	    <template:formTr name="盯防名称"   isOdd="false" >
            <bean:write name="watchBean" property="placeName"/>
			<html:hidden property="placeID"/>
			<html:hidden property="regionID"/>
			<html:hidden property="dealstatus"/>
        </template:formTr>

		 <logic:equal value="group"  name="PMType">
        		        <template:formTr name="盯防负责维护组"  style="display:none">
						      <apptag:setSelectOptions valueName="patrolCollection" tableName="patrolmaninfo" columnName1="patrolname" columnName2="patrolid" region="true"/>
							  <html:select property="principal" styleClass="inputtext" style="width:260">
				                <html:options collection="patrolCollection" property="value" labelProperty="label"/>
				             </html:select>
						</template:formTr>
        </logic:equal>
        <logic:notEqual value="group" name="PMTYpe">
        		        <template:formTr name="盯防负责维护人"  style="display:none">
						      <apptag:setSelectOptions valueName="patrolCollection" tableName="patrolmaninfo" columnName1="patrolname" columnName2="patrolid" region="true"/>
							  <html:select property="principal" styleClass="inputtext" style="width:260">
				                <html:options collection="patrolCollection" property="value" labelProperty="label"/>
				             </html:select>
						</template:formTr>
        </logic:notEqual>

   <logic:notEqual value="noShow" name="ShowFIB">
   </logic:notEqual>

		<template:formTr name="线路是否现场检查"  isOdd="false" >
			<html:select property="ifcheckintime" styleClass="inputtext" style="width:260">
				<html:option value="1">是</html:option>
				<html:option value="-1">否</html:option>
			</html:select>
        </template:formTr>

		<template:formTr name="检查结果"  isOdd="false" >
             <input type="text" name="checkresult" id="cid" class="inputtext" style="width:260">
        </template:formTr>
        <template:formTr name="是否完成盯防"  isOdd="false">
          <input type="radio" name="ifcheckdone" checked="checked" value="no">否
          <input type="radio" name="ifcheckdone" value="yes">是
        </template:formTr>
<logic:equal value="show" name="ShowFIB">
		<template:formTr name="具体光缆信息"  isOdd="false" >

		<!-- 详细信息 -->
<%
  if (vct.size() > 0) {
    out.println("<table id=\"tasklisttable\" name=\"tasklisttable\" border=\"0\" cellspacing=\"2\">");
	out.println("<tr valign=\"middle\"><td></td></tr>");
    for (int i = 0; i < vct.size(); i++) {
      Vector oneVct = (Vector)vct.get(i);
%>
  <tr valign="middle">
	<td width="9%">
		<a href="javascript:loadSubWatch('<%=(String)oneVct.get(0)%>')" >填写</a>
	</td>
	<td width="43%">
		<%=(String)oneVct.get(1)%>
	</td>
    <td width="10%">
      <%=(String)oneVct.get(3)%>
    </td>
	<td width="8%">
      <%=(String)oneVct.get(4)%>
    </td>
	<td width="10%">
      <%=(String)oneVct.get(5)%>
    </td>

    <td width="20%">
      <%=(String)oneVct.get(2)%>
    </td>

  </tr>
<%
  }
      out.println("</table>");
  } else {
%>
  <table id="errMsgTable" name="tasklisttable" border="0" cellspacing="0" style="display:">
    <tr valign="middle">
	<td>
		没有光缆信息可用
	</td>
    </tr>
  </table>
  <table id="tasklisttable" name="tasklisttable" border="0" cellspacing="0" cellpadding="5">
    <tr valign="middle">
      <td>      </td>
    </tr>
  </table>
<%}%>

		<!-- 详细信息 -->

        </template:formTr>
</logic:equal>
        <template:formSubmit>
            <td >
                <html:submit styleClass="button" onclick="return isValidForm()">更新</html:submit>
            </td>
            <td>
              <html:reset styleClass="button">重置</html:reset>
            </td>
            <td >
              <input type="button" class="button" onclick="toGetBack()" value="返回" >
            </td>
        </template:formSubmit>

   </template:formTable>
</html:form>

