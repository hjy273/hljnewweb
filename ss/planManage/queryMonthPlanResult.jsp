<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<script language="javascript" type="">
function toDelete(idValue,year,month,app){
//�������Ҫ�ſ�
//  		var nowdate = new Date();
//        var planbdate = new Date(year,month,1);
//        var planedate = new Date(year,month,30);
//
//        if(nowdate >= planbdate && nowdate <= planedate){
//        	alert("�üƻ�����ִ����,����ɾ��!");
//            return ;
//        }
//        if( nowdate >= planedate){
//        	alert("�üƻ�ִ���Ѿ�ִ�����,����ɾ��!");
//            return ;
//        }
//    	if(app == "ͨ������"){
//        	alert("�üƻ�ִ���Ѿ�������,����ɾ��!");
//            return ;
//        }
    if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/YearMonthPlanAction.do?method=deleteYMPlan&id=" + idValue;
        self.location.replace(url);
    }
}

function toEdit(idValue,year,month,app){
  	   //�������Ҫ�ſ�
//  		var nowdate = new Date();
//        var planbdate = new Date(year,month,1);
//        var planedate = new Date(year,month,30);
//
//        if(nowdate >= planbdate && nowdate <= planedate){
//        	alert("�üƻ�����ִ����,�����޸�!");
//            return ;
//        }
//        if( nowdate >= planedate){
//        	alert("�üƻ�ִ���Ѿ�ִ�����,�����޸�!");
//            return ;
//        }
//    	if(app == "ͨ������"){
//        	alert("�üƻ�ִ���Ѿ�������,�����޸�!");
//            return ;
//        }

        var url = "${ctx}/YearMonthPlanAction.do?fID=2&method=loadYMPlan&id=" + idValue;
        self.location.replace(url);

}

function toGetForm(idValue){
        var url = "${ctx}/YearMonthPlanAction.do?fID=2&method=loadYMPlanForm&id=" + idValue;
        self.location.replace(url);

}

function toEdit(idValue){
        var url = "${ctx}/YearMonthPlanAction.do?fID=2&method=loadYMPlan&id=" + idValue;
        self.location.replace(url);

}

</script><br>
<template:titile value="�ƻ���ѯ���"/>

<display:table name="sessionScope.queryresult" requestURI="${ctx}/YearMonthPlanAction.do?method=queryYMPlan"  id="currentRowObject"  pagesize="18">

	  <display:column property="planname" title="�ƻ�����" sortable="true"/>
     <display:column property="year" title="ִ�����" sortable="true"/>
	 <display:column property="month" title="ִ���¶�" sortable="true"/>

	 <display:column property="approvestatus" title="�ƶ�����" sortable="true"/>
  <display:column media="html" title="����">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("id");
  %>
    <a href="javascript:toGetForm('<%=id%>')">�鿴</a>
  </display:column>
  <apptag:checkpower thirdmould="20204" ishead="0">
  		<display:column media="html" title="����">
		  <%
		    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
		    String id = (String) object.get("id");
            String year = (String) object.get("year");
            String month = (String) object.get("month");
            String approvestatus = (String) object.get("approvestatus");
            if(approvestatus.equals("ͨ��")){
		  %>
		   --
		  <%}else{ %>
		    <a href="javascript:toEdit('<%=id%>','<%=year%>','<%=month%>','<%=approvestatus%>')">�޸�</a>
		   <%} %>
		  </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="20205" ishead="0">
  		<display:column media="html" title="����">
		  <%
		    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
		    String id = (String) object.get("id");
            String year = (String) object.get("year");
            String month = (String) object.get("month");
            String approvestatus = (String) object.get("approvestatus");
		  	if(approvestatus.equals("ͨ��")){
		  %>
		   --
		  <%}else{ %>
			<a href="javascript:toDelete('<%=id%>','<%=year%>','<%=month%>','<%=approvestatus%>')">ɾ��</a>
		  <%} %>
		  </display:column>
  </apptag:checkpower>


</display:table>
<html:link action="/PlanAction.do?method=exportMonthPlanResult">����ΪExcel�ļ�</html:link>
