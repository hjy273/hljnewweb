<%@include file="/common/header.jsp"%>
<%
  String regionID = request.getParameter("regionID");
%>
<script language="javascript" type="">
<!--

var iteName = "";
var rowArr = new Array();//һ�е���Ϣ
var infoArr = new Array();//���е���Ϣ

    //��ʼ������
    function initArray(conid,conname,pid,pname){
        rowArr[0] = conid;
        rowArr[1] = conname;
        rowArr[2] = pid;
        rowArr[3] = pname;
        infoArr[infoArr.length] = rowArr;
        rowArr = new Array();
        return true;
    }
    function onSelectChange(objId ) //�Զ����ñ�����������ѡ��
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
        alert("���Ʋ���Ϊ��! ");
        watchBean.placeName.focus();
        return false;
    }


    if (isNaN(watchBean.watchwidth.value)){
        alert("������ΧӦ��Ϊ������! ");
        watchBean.watchwidth.focus();
        return false;
    }

    if(watchBean.principal.value==""){
        alert("����������/�鲻��Ϊ��! ");
        watchBean.principal.focus();
        return false;
    }

    if(watchBean.beginDate.value==""){
        alert("������ʼ���ڲ���Ϊ��! ");
        watchBean.beginDate.focus();
        return false;
    }




    if(watchBean.patrolTime.value==""){
        alert("������ʼʱ�䲻��Ϊ��! ");
        watchBean.patrolTime.focus();
        return false;
    }
    if(watchBean.orderlyBeginTime.value==""){
        alert("ֵ�࿪ʼʱ�䲻��Ϊ��! ");
        watchBean.orderlyBeginTime.focus();
        return false;
    }
    if(watchBean.orderlyEndTime.value==""){
        alert("ֵ�����ʱ�䲻��Ϊ��! ");
        watchBean.orderlyEndTime.focus();
        return false;
    }

    if(watchBean.error.value==""){
        alert("ֵ��ʱ��������Ϊ��! ");
        watchBean.error.focus();
        return false;
    }
    if (isNaN(watchBean.error.value)){
        alert("ֵ��ʱ����Ӧ��Ϊ������! ");
        watchBean.error.focus();
        return false;
    }
    if(!isPosInteger(watchBean.error.value)){
      alert("ֵ��ʱ����ֻ��������!");
      watchBean.error.focus();
      return false;
    }
    if(watchBean.error.value > s){
      alert("ֵ��ʱ�䲻�ܴ�����ֵ��ʱ��");
      watchBean.error.focus();
      return false;
    }
        if(watchBean.involvedsegmentlist.value == ""){
      alert("��Ӱ����²���Ϊ��");
      watchBean.error.focus();
      return false;
    }
        if(watchBean.involvedlinenumber.value == ""){
      alert("��Ӱ�����������Ϊ��");
      watchBean.error.focus();
      return false;
    }
    return true;
}
function toClearEndDate(){
    watchBean.endDate.value = "����";
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
<template:titile value="�������������ֳ���Ϣ"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/watchAction.do?method=addWatch4GIS">
  <template:formTable >
    <template:formTr name="��������">
      <html:text property="placeName" styleClass="inputtext" style="width:160" maxlength="45"/>
      <html:hidden property="startpointid"/>
    </template:formTr>
   <%if(regionID!=null){%> <input type="hidden" name="regionID" value="<%=regionID%>"><%}%>
    <template:formTr name="��ά��λ" isOdd="false">
      <apptag:setSelectOptions valueName="deptCollection" tableName="contractorinfo" columnName1="contractorname" columnName2="contractorid" region="true" regionID="<%=regionID%>"/>
      <html:select property="contractorid" styleClass="inputtext" styleId="conId" style="width:160" onchange="onSelectChange(id)">
        <html:options collection="deptCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>

    <logic:equal value="group" name="PMType">
            <template:formTr name="����������">
              <apptag:setSelectOptions valueName="patrolCollection" tableName="patrolmaninfo" columnName1="patrolname" columnName2="patrolid" region="true" regionID="<%=regionID%>"/>
              <html:select property="principal" styleClass="inputtext" style="width:160" styleId="pId" >
                <html:options collection="patrolCollection" property="value" labelProperty="label"/>
              </html:select>
            </template:formTr>
    </logic:equal>
    <logic:notEqual value="group" name="PMType">
            <template:formTr name="����������">
              <apptag:setSelectOptions valueName="patrolCollection" tableName="patrolmaninfo" columnName1="patrolname" columnName2="patrolid" region="true" regionID="<%=regionID%>"/>
              <html:select property="principal" styleClass="inputtext" style="width:160" styleId="pId" >
                <html:options collection="patrolCollection" property="value" labelProperty="label"/>
              </html:select>
            </template:formTr>
    </logic:notEqual>


    <template:formTr name="��������" isOdd="false">
      <html:text property="innerregion" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
    <template:formTr name="����λ��">
      <html:text property="watchplace" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
 <logic:equal value="show" name="ShowFIB">

    <template:formTr name="��������">
      <html:select property="placetype" styleClass="inputtext" style="width:160">
          <html:option value="��¥��">��¥��</html:option>
          <html:option value="��·����">��·����</html:option>
          <html:option value="����ʩ��">����ʩ��</html:option>
          <html:option value="���¹ܵ��޽�">���¹ܵ��޽�</html:option>
          <html:option value="���ݲ�">���ݲ�</html:option>
          <html:option value="��·����">��·����</html:option>
          <html:option value="���³���������">���³���������</html:option>
          <html:option value="��վװ��">��վװ��</html:option>
      </html:select>
    </template:formTr>
 </logic:equal>
 <logic:notEqual value="show" name="ShowFIB">

    <template:formTr name="��������">
    <html:text property="placetype" styleClass="inputtext" style="width:160" value="" />

      <!--
      <html:select property="placetype" styleClass="inputtext" style="width:160">
          <html:option value="��¥��">��¥��</html:option>
          <html:option value="��·����">��·����</html:option>
          <html:option value="����ʩ��">����ʩ��</html:option>
          <html:option value="���¹ܵ��޽�">���¹ܵ��޽�</html:option>
          <html:option value="���ݲ�">���ݲ�</html:option>
          <html:option value="��·����">��·����</html:option>
          <html:option value="��վװ��">��վװ��</html:option>
      </html:select>
      -->
    </template:formTr>
 </logic:notEqual>
    <template:formTr name="��������">
      <html:select property="dangerlevel" styleClass="inputtext" style="width:160">
        <html:option value="����">����</html:option>
        <html:option value="��Ҫ">��Ҫ</html:option>
        <html:option value="һ��">һ��</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="����ԭ��" isOdd="false">
      <html:text property="watchreason" styleClass="inputtext" style="width:160" maxlength="60"/>
    </template:formTr>
    <template:formTr name="GPS����">
      <html:text property="gpscoordinate" styleClass="inputtext" style="width:160" maxlength="17"/>
    </template:formTr>
    <template:formTr name="�����뾶" isOdd="false">
      <html:text property="watchwidth" styleClass="inputtext" style="width:160" value="50"/>
      &nbsp;
      (��λ����)
    </template:formTr>
    <template:formTr name="��ʼ����" isOdd="false">
      <html:text property="beginDate" styleClass="inputtext" style="width:160" maxlength="45" readonly="true"/>
      <apptag:date property="beginDate"/>
    </template:formTr>
    <template:formTr name="��������">
      <html:text property="endDate" styleClass="inputtext" style="width:160" maxlength="45" readonly="true"/>
      <apptag:date property="endDate"/>
      &nbsp;&nbsp;
      <input type="button" value="����" onclick="toClearEndDate()">
    </template:formTr>
    <template:formTr name="�ƻ�Ѳ��ʱ��" style="display:none">
      <html:text property="patrolTime" styleClass="inputtext" style="width:160" maxlength="45" value="2005/01/01"/>
      <apptag:date property="patrolTime"/>
    </template:formTr>
    <template:formTr name="ֵ�࿪ʼʱ��" isOdd="false">
      <html:text property="orderlyBeginTime" styleClass="inputtext" style="width:160" maxlength="45" readonly="true"/>
      <input type='button' value="��" id='btn' onclick="getTimeWin('orderlybegintime')" style="font:'normal small-caps 6pt serif';">
    </template:formTr>
    <template:formTr name="ֵ�����ʱ��">
      <html:text property="orderlyEndTime" styleClass="inputtext" style="width:160" maxlength="45" readonly="true"/>
      <input type='button' value="��" id='btn' onclick="getTimeWin('orderlyEndTime')" style="font:'normal small-caps 6pt serif';">
    </template:formTr>
    <template:formTr name="��Ϣ���ͼ��" isOdd="false">
      <html:text property="error" styleClass="inputtext" style="width:160" maxlength="45"/>
      &nbsp;
      (��λ��Сʱ)
    </template:formTr>
       <logic:equal value="show" name="ShowFIB">
    <template:formTr name="��Ӱ���������" isOdd="false">
      <apptag:setSelectOptions valueName="cableCollection" tableName="repeatersection" columnName1="segmentname" columnName2="kid"/>
      <html:select property="involvedsegmentlist" styleClass="multySelect" style="width:260" size="10" multiple="true">
        <html:options collection="cableCollection" property="value" labelProperty="label"/>
      </html:select>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <span style="color:red">(��סCtr��,��ѡ�����)</span>
    </template:formTr>
    <template:formTr name="��Ӱ������ܶ���" isOdd="false">
      <html:text property="involvedlinenumber" styleClass="inputtext" style="width:160" onclick="countNum()"/>
    </template:formTr>
   </logic:equal>

   <logic:notEqual value="noShow" name="ShowFIB">
   </logic:notEqual>
    <template:formSubmit>
      <td >
        <html:submit styleClass="button" onclick="setMapRefresh('RefreshWatchPoint')">����</html:submit>
      </td>
      <td >
        <html:button property="action"styleClass="button"  onclick="self.close()">ȡ��</html:button>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe id="hiddenFrame" style="display:none">
</iframe>
