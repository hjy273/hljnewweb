<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.lineinfo.beans.GISTemppointbean"%>
<!--<SCRIPT LANGUAGE="JavaScript" src="\WEBGIS\js\function.js" type=""></SCRIPT>-->
<%
  GISTemppointbean temppbean = (GISTemppointbean) request.getAttribute("temppbean");
  String GPS = temppbean.getGps();
  String tempID = temppbean.getPointid();
  String pointname = temppbean.getPointname();
  String patrolname = temppbean.getPatrolname();
  String ctime = temppbean.getCreatetime();
  String sim = temppbean.getSim();
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

	if(confirm("ȷ��ɾ������ʱ�㣿")){

        //var url = "${ctx}/pointAction.do?method=deleteTempPoint4GIS&id=" + idValue;
		var url = "${ctx}/pointAction.do?method=setEditTempPoint4GIS&id=" + idValue;
        self.location.replace(url);       
	}

}

//-->
</script><template:titile value="��ʱ����Ϣ"/>
<br>
<template:formTable>
  <template:formTr name="Ѳ��Ա" >
    <input type="hidden" name="tempID" value="<%=tempID%>">
    <input type="text" name="patrolname" value="<%=patrolname%>" class="inputtext" style="width:110" disabled>
  </template:formTr>
  <template:formTr name="SIM����" isOdd="false">
    <input type="text" name="sim" value="<%=sim%>" class="inputtext" style="width:110" disabled>
  </template:formTr>
  <template:formTr name="�ɼ�ʱ��">
    <input type="text" name="ctime" value="<%=ctime%>" class="inputtext" style="width:110" disabled>
  </template:formTr>
  <template:formTr name="����" isOdd="false">
    <input type="text" name="ctime" value="<%=pointname%>" class="inputtext" style="width:110" disabled>
  </template:formTr>
  <template:formTr name="GPS����">
    <input type="text" name="ctime" value="<%=GPS%>" class="inputtext" style="width:110" disabled>
  </template:formTr>
  <template:formSubmit>
    <td>
      <html:button property="action" styleClass="lbutton" onclick="toDelete()">ɾ������ʱ��</html:button>
    </td>
  </template:formSubmit>
</template:formTable>
<br>
<iframe name="my_addPoint" marginWidth="0" marginHeight="0" src="/WEBGIS/addPointForGis.jsp?GPS=<%=GPS%>&tempID=<%=tempID%>&pointname=<%=pointname%>" frameBorder=0 width="100%" scrolling=NO height="100%"/>
