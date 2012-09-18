<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>

<%
		java.util.Enumeration e = request.getParameterNames();
		String pressUrl = "";
		while (e.hasMoreElements()) {
			String prams = (String) e.nextElement().toString();
			if(prams.startsWith("d-")){ 
				pressUrl = prams+"="+request.getParameter(prams);
			} 
		}
		
		String queryAction = (String)request.getSession().getAttribute("S_BACK_URL"); 
				
		if(queryAction.indexOf("&d-")!=-1){ 
			String[] arr = queryAction.split("&"); 
			queryAction = "";
			for(int i=0;i<arr.length;i++){
				if(!arr[i].startsWith("d-")){ 
					queryAction += arr[i] + "&";
				}
			}
		}  
		
		queryAction = queryAction+pressUrl;
		
		request.getSession().setAttribute("S_BACK_URL", queryAction);
		
		//System.out.println("##########"+queryAction);
%>

<script language="javascript">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/pointAction.do?method=deletePoint&id=" + idValue;
        self.location.replace(url);
    }
}
function toEdit(idValue){
        var url = "${ctx}/pointAction.do?method=loadPoint&id=" + idValue;
        self.location.replace(url);

}
function toEditState(idValue){
        var url = "${ctx}/pointAction.do?method=PointState&id=" + idValue;
        self.location.replace(url);

}

function checkAll(){
   var checked= document.getElementById("allbox").checked;
   var pointbox = document.getElementsByName("pointbox");
   for(var i=0;i<pointbox.length;i++){
      pointbox[i].checked=checked;
   }
}
function checkPoint(){
   var allbox= document.getElementById("allbox");
    var pointbox = document.getElementsByName("pointbox");
    var checked=true;
   for(var i=0;i<pointbox.length;i++){
      if(!pointbox[i].checked){
        checked= false;
        break;
      }
   }
   allbox.checked=checked;
}

function pointState(){
   var idValue=""; 
   var checkYes=0;
   var checkNo=0;
   var pointbox = document.getElementsByName("pointbox"); 
   var pointstate = document.getElementsByName("pointstate");
   for (var i=0;i<pointbox.length;i++){
      if(pointbox[i].checked){
         if(pointstate[i].value=='��'){
           checkYes = checkYes + 1;
           
         }else{
           checkNo = checkNo +1;
         }
         idValue = idValue + pointbox[i].value +",";
      }
   }
   if(checkYes+checkNo==0){
     alert('������ѡ��һ�');
     return false;
   }
   if(confirm("��ѡ����"+checkYes+"���ǣ�"+checkNo+"����ȷ��Ҫ�޸���")){
       var url = "${ctx}/pointAction.do?method=PointState&id=" + idValue;
        self.location.replace(url);
    }
}
</script>
<br>
<template:titile value="��ѯѲ�����Ϣ���"/>
<display:table name="sessionScope.queryresult" requestURI="${ctx}/pointAction.do?method=queryPoint" id="currentRowObject"  pagesize="18">
 <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String pointid = "";
    String state = "" ;
    if(object != null){
	     pointid = (String) object.get("pointid");
	     state = (String) object.get("state");
     }
  %>
  <display:column media="html" title="<input type='checkbox' id='allbox' name='allbox' onclick='checkAll()' >ȫѡ">
   <input type="checkbox" name="pointbox" value="<%=pointid %>" onclick="checkPoint()">
   <input type="hidden" name="pointstate" value="<%=state %>">
</display:column>
  <display:column property="pointname" title="Ѳ�������"/>
  <display:column property="addressinfo" title="Ѳ���λ��"/>
  <display:column property="sublineid" title="����Ѳ���"/>
  <display:column property="isfocus" title="�Ƿ�ؼ���"/>
  <display:column property="regionid" title="��������"/>
<display:column media="html" title="�Ƿ�Ѳ���">
  <a href="javascript:toEditState('<%=pointid%>')"><%=state %></a>
</display:column>

  <apptag:checkpower thirdmould="71104" ishead="0">

  <display:column media="html" title="�޸�">
  
    <a href="javascript:toEdit('<%=pointid%>')">�޸�</a>
  </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="71105" ishead="0">

  </apptag:checkpower>

</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
<html:link action="/pointAction.do?method=exportPointResult">����ΪExcel�ļ�</html:link>
</logic:notEmpty>
<br>
<center>
<input type="button" value="��������" class="button" onclick="pointState()">
</center>
