<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/regionAction.do?method=deleteRegion&id=" + idValue;
        self.location.replace(url);
    }
}


function toEdit(idValue){
        var url = "${ctx}/regionAction.do?method=loadRegion&id=" + idValue;
        self.location.replace(url);

}

</script>
<template:titile value="��ѯ������Ϣ���"/>
<display:table name="sessionScope.queryresult" pagesize="18" id="currentRowObject">

  <display:column property="regionid" title="������"/>
  <display:column property="regionname" title="��������"/>
  <display:column property="parentregionid" title="�ϼ�����"/>
  <display:column property="regiondes" title="����˵��"/>

<apptag:checkpower thirdmould="70104" ishead="0">

    <display:column media="html" title="����">
	  <%
	    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
	    String regionid  = "";
        if(object != null)
        regionid = (String) object.get("regionid");
	  %>
	    <a href="javascript:toEdit('<%=regionid%>')">�޸�</a>
  </display:column>
</apptag:checkpower>
<apptag:checkpower thirdmould="70105" ishead="0">

    <display:column media="html" title="����">
	  <%
	    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
	    String regionid = (String) object.get("regionid");
	  %>
	   <a href="javascript:toDelete('<%=regionid%>')">ɾ��</a>
  </display:column>
</apptag:checkpower>
</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
<html:link action="/regionAction.do?method=exportRegionResult">����ΪExcel�ļ�</html:link>
</logic:notEmpty>
