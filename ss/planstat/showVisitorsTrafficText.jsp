<%@ include file="/common/header.jsp"%>
<%@ page import="org.apache.commons.beanutils.BasicDynaBean" %>
<%@ page import="com.cabletech.planstat.beans.LogPathBean" %>
<%@ page import="java.util.Map" %>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<%
  LogPathBean logPathBean =(LogPathBean)session.getAttribute("theTrafficBean");
  Map regionMap =(Map)application.getAttribute("regionMap");
  //System.out.println(" regionid "+logPathBean.getRegionID());
  //String regionname = (String) regionMap.get(logPathBean.getRegionID());
 %>
<script language="javascript" type="">
function showSubmenuInfo(mainMenuValue,mainMenuNameValue){
    var url = "${ctx}/LogPathAction.do?method=showSubMenuTraffic&mainmenuid=" + mainMenuValue + "&mainmenuname=" +mainMenuNameValue ;
    self.location.replace(url);
}
</script>

<br>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'>��ģ����������ʾ</div><hr width='100%' size='1'>
<display:table name="sessionScope.visitorsTrafficList"   id="currentRowObject"  pagesize="18">
	 <display:column property="lablename" title="ģ������" sortable="true"/>
	 <% 
	 if (!logPathBean.getQueryType().equals("0")){
	 %>
	    <display:column property="visittimes" title="���ʴ���" sortable="true"/> 
	    <display:column media="html" title="����">
		     <%
			    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
			    String mainmenuid = (String) object.get("mainmenuid");
			    String mainmenuname= (String) object.get("lablename");
	         %>
	         <a href="javascript:showSubmenuInfo('<%=mainmenuid%>','<%=mainmenuname%>')">��ϸ��Ϣ</a>
	     </display:column>
	 <%}else{%>
        <display:column property="mvisittimes" title="�ƶ���˾���ʴ���" sortable="true"/> 
        <display:column property="cvisittimes" title="��ά��˾���ʴ���" sortable="true"/> 
        <display:column media="html" title="����">
		     <%
			    Map object = (Map) pageContext.findAttribute("currentRowObject");
			    String mainmenuid = (String) object.get("mainmenuid");
			    String mainmenuname= (String) object.get("lablename");
	         %>
	         <a href="javascript:showSubmenuInfo('<%=mainmenuid%>','<%=mainmenuname%>')">��ϸ��Ϣ</a>
	    </display:column>
     <%}%>
</display:table>