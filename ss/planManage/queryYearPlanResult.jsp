<%@ include file="/common/header.jsp"%>

<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<script language="javascript" type="">
function toDelete(idValue,year,app){
  //待部署后要放开
//	var nowdate = new Date();
//    var planbdate = new Date(year,1,1);
//    var planedate = new Date(year,12,31);
//
//    if(nowdate >= planbdate && nowdate <= planedate){
//    	alert("该计划正在执行中,不能删除!");
//        return ;
//    }
//    if( nowdate >= planedate){
//    	alert("该计划执行已经执行完毕,不能删除!");
//        return ;
//    }
//    if(app == "通过审批"){
//    	alert("该计划执行已经过审批,不能删除!");
//        return ;
//    }
//
    if(confirm("确定删除该纪录？")){
        var url = "${ctx}/YearMonthPlanAction.do?method=deleteYMPlan&id=" + idValue;
        self.location.replace(url);
    }
}

function toGetForm(idValue){

        var url = "${ctx}/YearMonthPlanAction.do?fID=1&method=loadYMPlanForm&id=" + idValue;
        self.location.replace(url);

}

function toEdit(idValue,year,app){
    //待部署后要放开
//  		var nowdate = new Date();
//        var planbdate = new Date(year,1,1);
//        var planedate = new Date(year,12,31);
//
//        if(nowdate >= planbdate && nowdate <= planedate){
//        	alert("该计划正在执行中,不能修改!");
//            return ;
//        }
//        if( nowdate >= planedate){
//        	alert("该计划执行已经执行完毕,不能修改!");
//            return ;
//        }
//    	if(app == "通过审批"){
//        	alert("该计划执行已经过审批,不能修改!");
//            return ;
//        }

        var url = "${ctx}/YearMonthPlanAction.do?fID=1&method=loadYMPlan&id=" + idValue;
        self.location.replace(url);

}
</script>
<template:titile value="查询计划信息结果"/>

<display:table name="sessionScope.queryresult" requestURI="${ctx}/YearMonthPlanAction.do?method=queryYMPlan"  id="currentRowObject"  pagesize="18">

	  <display:column property="planname" title="计划名称" sortable="true"/>
     <display:column property="year" title="执行年度" sortable="true"/>

	 <display:column property="approvestatus" title="移动审批" sortable="true" />

  <display:column media="html" title="操作">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("id");
  %>
    <a href="javascript:toGetForm('<%=id%>')">查看</a>
  </display:column>
  <apptag:checkpower thirdmould="20104" ishead="0">
	  	<display:column media="html" title="操作">
		  <%
		    BasicDynaBean object1 = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
		    String id1 = (String) object1.get("id");
            String year = (String) object1.get("year");
            String approvestatus = (String) object1.get("approvestatus");
            if(approvestatus.equals("通过")){
		  %>
		  --
		  <%}else{ %>
		    <a href="javascript:toEdit('<%=id1%>','<%=year%>','<%=approvestatus%>')">修改</a>
		  <%}%>
        </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="20105" ishead="0">
	  	<display:column media="html" title="操作">
		  <%
		    BasicDynaBean object1 = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
		    String id1 = (String) object1.get("id");
            String year = (String) object1.get("year");
            String approvestatus = (String) object1.get("approvestatus");
		  if(approvestatus.equals("通过")){
		  %>
		  --
		  <%}else{ %>
		   <a href="javascript:toDelete('<%=id1%>','<%=year%>','<%=approvestatus%>')">删除</a>
		  <%}%>
        </display:column>
  </apptag:checkpower>

</display:table>
<!--旧的导出excel方法
<html:link action="/PlanAction.do?method=exportYearPlanResult">导出为Excel文件</html:link>
-->
<html:link action="/planExportAction.do?method=exportYearPlanResult&fID=1">导出Excel报表</html:link>
