<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/CableSegmentAction.do?method=delCableSegment&kid=" + idValue;
        self.location.replace(url);
    }
//confirm("����ɾ���ü�¼!");
}

function toEdit(idValue,type){
        var url = "${ctx}/CableSegmentAction.do?method=loadEditForm&kid=" + idValue +"&t="+type;
        self.location.replace(url);

}


</script>
<template:titile value="������Ϣһ����"/>
<display:table name="sessionScope.cablelist" id="currentRowObject"  pagesize="16">
  <display:column property="segmentid"  title="����ID"/>
  <display:column property="segmentname" maxLength="10" title="������"/>
  <display:column property="segmentdesc" maxLength="15" title="���¶���"/>
  <display:column property="pointa" maxLength="4" title="A��"/>
  <display:column property="pointz" maxLength="4" title="Z��"/>

  <display:column media="html" title="�鿴">
             <%
                BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
                String kid = (String) object.get("kid");
            %>
            <a href="javascript:toEdit('<%=kid%>','r')">�鿴</a>
  </display:column>
  <apptag:checkpower thirdmould="72104" ishead="0">
        <display:column media="html"  title="�޸�">
             <%
                BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
                String kid = (String) object.get("kid");
            %>
            <a href="javascript:toEdit('<%=kid%>','e')">�޸�</a>
        </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="72105" ishead="0">

        <display:column media="html"  title="ɾ��">
         <%
            BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
            String kid = (String) object.get("kid");
        %>
        <a href="javascript:toDelete('<%=kid%>')">ɾ��</a>
        </display:column>
  </apptag:checkpower>
</display:table>
<html:link action="/CableSegmentAction.do?method=exportCableSegment">����ΪExcel�ļ�</html:link>
