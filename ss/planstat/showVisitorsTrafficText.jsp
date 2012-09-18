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
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'>各模块访问情况显示</div><hr width='100%' size='1'>
<display:table name="sessionScope.visitorsTrafficList"   id="currentRowObject"  pagesize="18">
	 <display:column property="lablename" title="模块名称" sortable="true"/>
	 <% 
	 if (!logPathBean.getQueryType().equals("0")){
	 %>
	    <display:column property="visittimes" title="访问次数" sortable="true"/> 
	    <display:column media="html" title="操作">
		     <%
			    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
			    String mainmenuid = (String) object.get("mainmenuid");
			    String mainmenuname= (String) object.get("lablename");
	         %>
	         <a href="javascript:showSubmenuInfo('<%=mainmenuid%>','<%=mainmenuname%>')">详细信息</a>
	     </display:column>
	 <%}else{%>
        <display:column property="mvisittimes" title="移动公司访问次数" sortable="true"/> 
        <display:column property="cvisittimes" title="代维公司访问次数" sortable="true"/> 
        <display:column media="html" title="操作">
		     <%
			    Map object = (Map) pageContext.findAttribute("currentRowObject");
			    String mainmenuid = (String) object.get("mainmenuid");
			    String mainmenuname= (String) object.get("lablename");
	         %>
	         <a href="javascript:showSubmenuInfo('<%=mainmenuid%>','<%=mainmenuname%>')">详细信息</a>
	    </display:column>
     <%}%>
</display:table>