<%@ include file="/common/header.jsp"%>

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
	//watchBean.endpointid.disabled = flag;

}

function toSetVis(visStr){

	for(var i = 0; i < toHideTr.length; i ++){

		toHideTr[i].style.display = visStr;
	}

}

function parseTime(time,sep){
  var t=time.split(sep);
  if(t[0].charAt(0)=='0') t[0]=t[0].substring(1,t[0].length);
  var selectTime=new Date();
  selectTime.setHours(parseInt(t[0]));
  selectTime.setMinutes(parseInt(t[1]));
  selectTime.setSeconds(parseInt(t[2]));
  return selectTime;
}
function compareTime(time1,time2){
  if(time1>time2) return true;
  else return false;
}
function vertifyTime(time1,time2){
  var t1=parseTime(time1,":");
  var t2=parseTime(time2,":");
  if(compareTime(t1,t2)){
    alert("开始时间必须小于结束时间！");
    document.SMSCenterTime.endtime.value="";
  }
}

//-->
</script>
<br>
<template:titile value="设置短信中心活动时段"/>

<html:form method="Post"  action="/SMSTimeAction.do?method=setTime" >
    <template:formTable contentwidth="300" namewidth="200">

        <template:formTr name="开始时间"  isOdd="false" >
            <html:text property="begintime" styleClass="inputtext" size="25" maxlength="45" readonly="true"/>
            <input type='button' value='▼' id='btn'  onclick="getTimeWin('begintime')" style="font:'normal small-caps 6pt serif';" >
        </template:formTr>

        <template:formTr name="结束时间">
            <html:text property="endtime" styleClass="inputtext" size="25" maxlength="45" readonly="true"/>
            <input type='button' value='▼' id='btn'  onclick="getTimeWin('endtime')" style="font:'normal small-caps 6pt serif';" >
        </template:formTr>

        <template:formSubmit >
            <td >
                <html:submit styleClass="button" >提交</html:submit>
            </td>
            <td >
                <html:reset styleClass="button"  >取消</html:reset>
            </td>
        </template:formSubmit>

   </template:formTable>
</html:form>

<iframe id="hiddenFrame" style="display:none">
</iframe>
<script language="javascript">
document.SMSCenterTime.begintime.onpropertychange=function(){
  var time1=document.SMSCenterTime.begintime.value;
  var time2=document.SMSCenterTime.endtime.value;
  vertifyTime(time1,time2);
}
document.SMSCenterTime.endtime.onpropertychange=function(){
  var time1=document.SMSCenterTime.begintime.value;
  var time2=document.SMSCenterTime.endtime.value;
  vertifyTime(time1,time2);
}
</script>
