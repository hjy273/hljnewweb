<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
       var url = "${ctx}/PipeSegmentAction.do?method=deletePipeSegment&id=" + idValue;
        self.location.replace(url);
    }
     //confirm("����ɾ���ü�¼!");

}


function toEdit(idValue){
        var url = "${ctx}/PipeSegmentAction.do?method=editPipeSegmentForm&id=" + idValue;
        self.location.replace(url);

}

</script>
<template:titile value="��ѯ�ܵ�����Ϣ���"/>
<display:table name="sessionScope.pipeSegmentList" requestURI="${ctx}/PipeSegmentAction.do?method=queryPipeSegment&isReturn=false" id="currentRowObject" pagesize="18">

 c.id,c.pipeno,ct.contractorname,c.pipename,c.county,c.area,c.pipehole,c.pipetype,c.
  <display:column property="pipeno" style="width:10%" title="�ܵ��α��"/>
  <display:column property="pipename" style="width:15%" title="�ܵ�������"/>
  <display:column property="contractorname" style="width:10%" title="��ά��λ"/>
 <display:column property="county" style="width:10%" title="��������"/>

<display:column property="pipehole" style="width:10%" title="�ܿ���"/>
<display:column property="pipetype" style="width:10%" title="�ܿ׹��"/>
<display:column property="subpipehole" style="width:10%" title="�ӹ�����"/>
<display:column property="unuserpipe" style="width:10%" title="δ���ӹ���"/>

  <apptag:checkpower thirdmould="70204" ishead="0">

  <display:column media="html" style="width:10%" title="�޸�">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = "";
    
    if(object != null)
     id = (String) object.get("id");
  %>
    <a href="javascript:toEdit('<%=id%>')">�޸�</a>
  </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="70205" ishead="0">

  <display:column media="html" style="width:10%" title="ɾ��">
    <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = "";
    
    if(object != null)
     id = (String) object.get("id");
  %>
    <a href="javascript:toDelete('<%=id%>')">ɾ��</a>
  </display:column>
  </apptag:checkpower>

</display:table>
<logic:empty name="pipeSegmentList">
</logic:empty>
<logic:notEmpty name="pipeSegmentList">
<html:link action="/PipeSegmentAction.do?method=exportPipeSegmentResult">����ΪExcel�ļ�</html:link>
</logic:notEmpty>
