<%@ include file="/common/header.jsp"%>

<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<script language="javascript" type="">
function toDelete(idValue,year,app){
  //�������Ҫ�ſ�
//	var nowdate = new Date();
//    var planbdate = new Date(year,1,1);
//    var planedate = new Date(year,12,31);
//
//    if(nowdate >= planbdate && nowdate <= planedate){
//    	alert("�üƻ�����ִ����,����ɾ��!");
//        return ;
//    }
//    if( nowdate >= planedate){
//    	alert("�üƻ�ִ���Ѿ�ִ�����,����ɾ��!");
//        return ;
//    }
//    if(app == "ͨ������"){
//    	alert("�üƻ�ִ���Ѿ�������,����ɾ��!");
//        return ;
//    }
//
    if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/YearMonthPlanAction.do?method=deleteYMPlan&id=" + idValue;
        self.location.replace(url);
    }
}

function toGetForm(idValue){

        var url = "${ctx}/YearMonthPlanAction.do?fID=1&method=loadYMPlanForm&id=" + idValue;
        self.location.replace(url);

}

function toEdit(idValue,year,app){
    //�������Ҫ�ſ�
//  		var nowdate = new Date();
//        var planbdate = new Date(year,1,1);
//        var planedate = new Date(year,12,31);
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

        var url = "${ctx}/YearMonthPlanAction.do?fID=1&method=loadYMPlan&id=" + idValue;
        self.location.replace(url);

}
</script>
<template:titile value="��ѯ�ƻ���Ϣ���"/>

<display:table name="sessionScope.queryresult" requestURI="${ctx}/YearMonthPlanAction.do?method=queryYMPlan"  id="currentRowObject"  pagesize="18">

	  <display:column property="planname" title="�ƻ�����" sortable="true"/>
     <display:column property="year" title="ִ�����" sortable="true"/>

	 <display:column property="approvestatus" title="�ƶ�����" sortable="true" />

  <display:column media="html" title="����">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("id");
  %>
    <a href="javascript:toGetForm('<%=id%>')">�鿴</a>
  </display:column>
  <apptag:checkpower thirdmould="20104" ishead="0">
	  	<display:column media="html" title="����">
		  <%
		    BasicDynaBean object1 = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
		    String id1 = (String) object1.get("id");
            String year = (String) object1.get("year");
            String approvestatus = (String) object1.get("approvestatus");
            if(approvestatus.equals("ͨ��")){
		  %>
		  --
		  <%}else{ %>
		    <a href="javascript:toEdit('<%=id1%>','<%=year%>','<%=approvestatus%>')">�޸�</a>
		  <%}%>
        </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="20105" ishead="0">
	  	<display:column media="html" title="����">
		  <%
		    BasicDynaBean object1 = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
		    String id1 = (String) object1.get("id");
            String year = (String) object1.get("year");
            String approvestatus = (String) object1.get("approvestatus");
		  if(approvestatus.equals("ͨ��")){
		  %>
		  --
		  <%}else{ %>
		   <a href="javascript:toDelete('<%=id1%>','<%=year%>','<%=approvestatus%>')">ɾ��</a>
		  <%}%>
        </display:column>
  </apptag:checkpower>

</display:table>
<!--�ɵĵ���excel����
<html:link action="/PlanAction.do?method=exportYearPlanResult">����ΪExcel�ļ�</html:link>
-->
<html:link action="/planExportAction.do?method=exportYearPlanResult&fID=1">����Excel����</html:link>
