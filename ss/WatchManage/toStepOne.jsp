<%@page import="com.cabletech.watchinfo.beans.*"%>
<%@include file="/common/header.jsp"%>
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
    watchBean.endDate.value = "����";
}

function preSetEndDate(){
    if(watchBean.endDate.value == ""){
        watchBean.endDate.value = "����";
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
        lableSpan.innerHTML = "��ʾ������Ϣ";
    }else{
        showflag = 1;
        basicInfoSpan.style.display = "";
        lableSpan.innerHTML = "���ػ�����Ϣ";
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
function isValidForm() {
    if(document.getElementById("eid").value==""){
        alert("ʩ����Ϣ����Ϊ��! ");
		document.getElementById("eid").focus();
        return false;
    }
    return true;

}
//
function toGetBack(){
        var url = "${ctx}/watchAction.do?method=loadAllDoneWatches";
        self.location.replace(url);

}

//-->
</script>
<template:titile value="ʩ����Ϣ"/>
<br>
<a href="javascript:showhideSpan()">  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <span id="lableSpan">��ʾ������Ϣ</span>
</a>
<br>
<span id="basicInfoSpan" style="display:none"> 
<html:form method="Post" action="/watchAction.do?method=updateWatch"> 
	<template:formTable contentwidth="250" namewidth="250"> 
	<template:formTr name="��������" isOdd="false"> 
		<bean:write name="watchBean" property="placeName"/> 
		<html:hidden property="placeID"/> <html:hidden property="regionID"/> 
	</template:formTr> 
	<template:formTr name="����">
		<bean:write name="watchBean" property="x"/> 
	</template:formTr> 
	<template:formTr name="γ��">
		<bean:write name="watchBean" property="y"/>
	</template:formTr> 
	<template:formTr name="�����뾶" isOdd="false"> 
		<bean:write name="watchBean" property="watchwidth"/>&nbsp;��		
	</template:formTr> 
<% WatchBean bean = (WatchBean) request.getAttribute("watchBean");
  com.cabletech.commons.hb.QueryUtil util = new com.cabletech.commons.hb.QueryUtil();
    String sql = "select * from patrolmaninfo where  state is null and patrolid = '" + bean.getPrincipal() + "'";
    java.sql.ResultSet rs = null;
    rs = util.executeQuery(sql); %>
   <logic:equal value="group" name="PMType">
           <template:formTr name="����������">
          
            <%while (rs.next()) { %>
           		<%=rs.getString(2)%>
            <%}%> 
        </template:formTr>
   </logic:equal>
   <logic:notEqual value="group" name="PMType">
           <template:formTr name="����������">
              <%while (rs.next()) { %>
           		<%=rs.getString(2)%>
            <%}%> 
            </template:formTr>
   </logic:notEqual>


	<template:formTr name="��ʼ����" isOdd="false"> 
		<bean:write name="watchBean" property="beginDate"/> 
	</template:formTr> 
	<template:formTr name="��������"> 
		<bean:write name="watchBean" property="endDate"/> 
	</template:formTr> 
	<template:formTr name="�ƻ�Ѳ��ʱ��" style="display:none"> 
		<html:text property="patrolTime" styleClass="inputtext" style="width:260" maxlength="45" value="2005/01/01"/> 
	</template:formTr> 
	<template:formTr name="ֵ�࿪ʼʱ��" isOdd="false"> 
		<bean:write name="watchBean" property="orderlyBeginTime"/> 		
	</template:formTr> 
	<template:formTr name="ֵ�����ʱ��"> 
		<bean:write name="watchBean" property="orderlyEndTime"/> 
	</template:formTr> 
	<template:formTr name="��Ϣ���ͼ��" isOdd="false"> 
		<bean:write name="watchBean" property="error"/>&nbsp;Сʱ 
	</template:formTr> </template:formTable> 
	</html:form> 
	</span>
<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
ʩ��������Ϣ
<br>
<html:form method="Post" onsubmit="countNum()" action="/watchAction.do?method=FinishStepOne" >
  <template:formTable contentwidth="250" namewidth="250">
    <template:formTr name="��������" isOdd="false">   
      <bean:write name="watchBean" property="placeName"/>
      <html:hidden property="placeID"/>
      <html:hidden property="regionID"/>
    </template:formTr>
    <template:formTr name="����">
		<bean:write name="watchBean" property="x"/> 
	</template:formTr> 
	<template:formTr name="γ��">
		<bean:write name="watchBean" property="y"/>
	</template:formTr> 
    <template:formTr name="�����뾶" isOdd="false" style="display:none">
    	<bean:write name="watchBean" property="watchwidth"/>
      &nbsp;
      (��λ����)
    </template:formTr>
    <logic:equal value="group" name="PMType">
         <template:formTr name="��������ά����" style="display:none">
          <apptag:setSelectOptions valueName="patrolCollection" tableName="patrolmaninfo" columnName1="patrolname" columnName2="patrolid" region="true"/>
          <html:select property="principal" styleClass="inputtext" style="width:260">
            <html:options collection="patrolCollection" property="value" labelProperty="label"/>
          </html:select>
        </template:formTr>
    </logic:equal>
    <logic:notEqual value="group" name="PMType">
         <template:formTr name="��������ά����" style="display:none">
          <apptag:setSelectOptions valueName="patrolCollection" tableName="patrolmaninfo" columnName1="patrolname" columnName2="patrolid" region="true"/>
          <html:select property="principal" styleClass="inputtext" style="width:260">
            <html:options collection="patrolCollection" property="value" labelProperty="label"/>
          </html:select>
        </template:formTr>
    </logic:notEqual>

    <template:formTr name="��ʼ����" isOdd="false" style="display:none">
      <html:text property="beginDate" styleClass="inputtext" style="width:260" maxlength="45"/>
      <apptag:date property="beginDate"/>
    </template:formTr>
    <template:formTr name="��������" style="display:none">
      <html:text property="endDate" styleClass="inputtext" style="width:260" maxlength="45"/>
      <apptag:date property="endDate"/>
      &nbsp;&nbsp;
      <input type="button" value="����" onclick="toClearEndDate()">
    </template:formTr>
    <template:formTr name="�ƻ�Ѳ��ʱ��" style="display:none">
      <html:text property="patrolTime" styleClass="inputtext" style="width:260" maxlength="45" value="2005/01/01"/>
      <apptag:date property="patrolTime"/>
    </template:formTr>
    <template:formTr name="ֵ�࿪ʼʱ��" isOdd="false" style="display:none">
      <html:text property="orderlyBeginTime" styleClass="inputtext" style="width:260" maxlength="45" readonly="true"/>
      <input type='button' value="��" id='btn' onclick="getTimeWin('orderlybegintime')" style="font:'normal small-caps 6pt serif';">
    </template:formTr>
    <template:formTr name="ֵ�����ʱ��" style="display:none">
      <html:text property="orderlyEndTime" styleClass="inputtext" style="width:260" maxlength="45" readonly="true"/>
      <input type='button' value="��" id='btn' onclick="getTimeWin('orderlyEndTime')" style="font:'normal small-caps 6pt serif';">
    </template:formTr>
    <template:formTr name="��Ϣ���ͼ��" isOdd="false" style="display:none">
      <html:text property="error" styleClass="inputtext" style="width:260" maxlength="45"/>
      &nbsp;
      (��λ��Сʱ)
    </template:formTr>
    <template:formTr name="��ά��λ" isOdd="false" style="display:none">
      <apptag:setSelectOptions valueName="deptCollection" tableName="contractorinfo" columnName1="contractorname" columnName2="contractorid" region="true"/>
      <html:select property="contractorid" styleClass="inputtext" style="width:160">
        <html:options collection="deptCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="��������" isOdd="false">
      <bean:write name="watchBean" property="innerregion"/>
    </template:formTr>
    <template:formTr name="����λ��" isOdd="false">
    	<bean:write name="watchBean" property="watchplace"/>
    </template:formTr>
    <template:formTr name="��������" isOdd="false">
    	<bean:write name="watchBean" property="placetype"/>
    </template:formTr>
    <template:formTr name="��������" isOdd="false">
    	<bean:write name="watchBean" property="dangerlevel"/>   
    </template:formTr>
    <template:formTr name="����ԭ��" isOdd="false">
    	<bean:write name="watchBean" property="watchreason"/>     
    </template:formTr>

    <logic:equal value="show" name="ShowFIB">
      <template:formTr name="��Ӱ������ܶ���" isOdd="false">
      	<bean:write name="watchBean" property="involvedlinenumber"/>       
      </template:formTr>
    </logic:equal>

   <logic:notEqual value="noShow" name="ShowFIB">
   </logic:notEqual>




    <template:formTr name="�������ش������" isOdd="false">
      <textarea name="endwatchinfo" id="eid" class="textarea" rows="5" style="width:280"></textarea>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button" onclick="return isValidForm()">����</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">����</html:reset>
      </td>
      <td>
        <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="����">
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
