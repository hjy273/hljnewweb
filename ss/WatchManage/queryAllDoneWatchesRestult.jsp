<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<template:titile value="盯防施工统计及核查信息结果"/>
<html>
<script language="javascript" type="">

function sendToFinish(idValue){
	var url = "${ctx}/watchAction.do?method=loadForStepOne&id=" + idValue;
	self.location.replace(url);
}

function sendToCheck(idValue){
	var url = "${ctx}/watchAction.do?method=loadForStepTwo&id=" + idValue;
	self.location.replace(url);
}

function loadForm(idValue){
	var url = "${ctx}/watchAction.do?method=exportWatchConstructInfo&id=" + idValue;
	self.location.replace(url);
}

</script>
<body>
<display:table name="sessionScope.queryResult" requestURI="${ctx}/watchAction.do?method=loadAllDoneWatches" id="resultList"  pagesize="18">
	<display:column media="html" title="盯防名称" sortable="true">

    <% BasicDynaBean object = (BasicDynaBean)pageContext.findAttribute("resultList");
		String placename = "";
		String dealstatus = "0";
		String placenameTitle = "";
	   if(object.get("dealstatus") != null){
			dealstatus = (String)object.get("dealstatus");
			
			placenameTitle = (String)object.get("placename");
    	 	if(placenameTitle != null && placenameTitle.length() > 10) {
    			placename = placenameTitle.substring(0,10) + "...";
    	 	} else {
    			placename = placenameTitle;
    	 	}
	   }

	   String id = (String)object.get("placeid");

	   if(dealstatus.equals("1")){
    %>

    	<a href="javascript:sendToCheck('<%=id%>')" title="<%=placenameTitle %>"><%=placename %></a>



	<%}else if(dealstatus.equals("0")){%>

      <a href="javascript:sendToFinish('<%=id%>')" title="<%=placenameTitle %>"><%=placename %></a>


	<%}else {%>
	<p title="<%=placenameTitle %>"><%=placename %></p>
	<%}%>
	</display:column>
	
     <logic:equal value="group" name="PMType">
    	<display:column property="patrolname" title="负责巡检维护组" sortable="true"/>
    </logic:equal>
    <logic:notEqual value="group" name="PMType">
   	 <display:column property="patrolname" title="负责巡检人" sortable="true"/>
    </logic:notEqual>
	<display:column property="watchreason" title="隐患原因" sortable="true" maxLength="15"/>
	<display:column property="begindate" title="起始日期" sortable="true"/>
	<display:column property="enddate" title="终止日期" sortable="true"/>

	<display:column media="html" title="施工及核查报表" style="width:100px">

    <% BasicDynaBean object1 = (BasicDynaBean)pageContext.findAttribute("resultList");
       String dealstatus1 = (String)object1.get("dealstatus");
	   String id1 = (String)object1.get("placeid");

	if(dealstatus1.equals("0")){
    %>
    --
	<%}else{%>
	<a href="javascript:loadForm('<%=id1%>')">Excel报表</a>
	<%}%>
	</display:column>

</display:table>
</body>
</html>
