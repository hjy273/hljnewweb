<%@ include file="/common/header.jsp"%>
<%@page import="org.apache.commons.beanutils.BasicDynaBean"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<br>
<display:table name="sessionScope.sublineInfoListNoPlan"   id="currentRowObject"  pagesize="16">
     <display:column property="sublinename" title="Ѳ���߶�����"/>
     <display:column property="linetype" title="������·����"/>
</display:table>