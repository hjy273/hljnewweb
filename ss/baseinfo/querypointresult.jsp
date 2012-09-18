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
    if(confirm("确定删除该纪录？")){
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
         if(pointstate[i].value=='是'){
           checkYes = checkYes + 1;
           
         }else{
           checkNo = checkNo +1;
         }
         idValue = idValue + pointbox[i].value +",";
      }
   }
   if(checkYes+checkNo==0){
     alert('请至少选择一项！');
     return false;
   }
   if(confirm("你选择了"+checkYes+"个是，"+checkNo+"个否，确定要修改吗？")){
       var url = "${ctx}/pointAction.do?method=PointState&id=" + idValue;
        self.location.replace(url);
    }
}
</script>
<br>
<template:titile value="查询巡检点信息结果"/>
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
  <display:column media="html" title="<input type='checkbox' id='allbox' name='allbox' onclick='checkAll()' >全选">
   <input type="checkbox" name="pointbox" value="<%=pointid %>" onclick="checkPoint()">
   <input type="hidden" name="pointstate" value="<%=state %>">
</display:column>
  <display:column property="pointname" title="巡检点名称"/>
  <display:column property="addressinfo" title="巡检点位置"/>
  <display:column property="sublineid" title="所属巡检段"/>
  <display:column property="isfocus" title="是否关键点"/>
  <display:column property="regionid" title="所属区域"/>
<display:column media="html" title="是否巡检点">
  <a href="javascript:toEditState('<%=pointid%>')"><%=state %></a>
</display:column>

  <apptag:checkpower thirdmould="71104" ishead="0">

  <display:column media="html" title="修改">
  
    <a href="javascript:toEdit('<%=pointid%>')">修改</a>
  </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="71105" ishead="0">

  </apptag:checkpower>

</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
<html:link action="/pointAction.do?method=exportPointResult">导出为Excel文件</html:link>
</logic:notEmpty>
<br>
<center>
<input type="button" value="批量设置" class="button" onclick="pointState()">
</center>
