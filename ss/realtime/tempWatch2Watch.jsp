<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.lineinfo.beans.GISTemppointbean"%>
<%
  GISTemppointbean temppbean = (GISTemppointbean) request.getAttribute("temppbean");
  String GPS = temppbean.getGps();
  String tempID = temppbean.getPointid();
  String pointname = temppbean.getPointname();
  String patrolname = temppbean.getPatrolname();
  String ctime = temppbean.getCreatetime();
  String sim = temppbean.getSim();
  String regionID = request.getParameter("regionID");
%>
<script language="javascript" type="">
<!--

function toLoadPoint(){
	var URL = "${ctx}/pointAction.do?method=loadPointForGis&id="+pointBean.pointID.value;
	//alert(URL);
	addPoint.location.replace(URL);

}

function toDelete(){

  idValue = tempID.value;

  if(confirm("确定删除该临时外力盯防点？")){

    //var url = "${ctx}/pointAction.do?method=deleteTempPoint4GIS&id=" + idValue;
    var url = "${ctx}/watchAction.do?method=setEditTempWatch4GIS&id=" + idValue;
    self.location.replace(url);
  }
}
function toMake(){

  idValue = tempID.value;
  gpsValue = gps.value;
  nameValue = pointname.value;

  var url = "${ctx}/watchAction.do?method=getTempWatch&sPID="+gpsValue+"&regionID="+<%=regionID%>+"&pointname="+nameValue+"&tempID="+idValue;
  self.location.replace(url);

}




//-->
</script><template:titile value="临时点信息"/>
<br>
<template:formTable>
  <template:formTr name="巡检维护组" >
    <input type="hidden" name="tempID" value="<%=tempID%>">
    <input type="text" name="patrolname" value="<%=patrolname%>" class="inputtext" style="width:110" disabled>
  </template:formTr>
  <template:formTr name="SIM卡号" isOdd="false">
    <input type="text" name="sim" value="<%=sim%>" class="inputtext" style="width:110" disabled>
  </template:formTr>
  <template:formTr name="采集时间">
    <input type="text" name="ctime" value="<%=ctime%>" class="inputtext" style="width:110" disabled>
  </template:formTr>
  <template:formTr name="名称" isOdd="false">
    <input type="text" name="pointname" value="<%=pointname%>" class="inputtext" style="width:110" disabled>
  </template:formTr>
  <template:formTr name="GPS坐标">
    <input type="text" name="gps" value="<%=GPS%>" class="inputtext" style="width:110" disabled>
  </template:formTr>
    <input type="hidden" name="regionID" value="<%=regionID%>">
  <template:formSubmit>
    <td>
      <html:button property="action" styleClass="lbutton" onclick="toDelete()">删除该临时外盯</html:button>
    </td>
    <td>
      <html:button property="action" styleClass="lbutton" onclick="toMake()">编辑该临时外盯</html:button>
    </td>
  </template:formSubmit>
</template:formTable>
<br>
<!--<iframe name="addPoint" marginWidth="0" marginHeight="0" src="${ctx}/WatchManage/addWatch.jsp?GPS=<%=GPS%>&tempID=<%=tempID%>&pointname=<%=pointname%>&regionID=<%=regionID%>" frameBorder=0 width="100%" scrolling=auto height="300"/>
<iframe name="addPoint" marginWidth="0" marginHeight="0" src="${ctx}/watchAction.do?method=getTempWatch&id=<%=GPS%>&tempID=<%=tempID%>&pointname=<%=pointname%>&regionID=<%=regionID%>" frameBorder=0 width="100%" scrolling=auto height="300"/>-->

