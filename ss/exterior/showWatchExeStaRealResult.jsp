<%@ page language="java" contentType="text/html; charset=gbk"
	pageEncoding="gbk"%>
<%@ taglib uri="http://displaytag.sourceforge.net/" prefix="display"%>
<link rel="stylesheet" href="${ctx}/css/forOtherSystem.css"
	type="text/css" media="screen, print" />
<display:table name="sessionScope.watchExeStaResultList"
	id="currentRowObject" pagesize="16">
	 <display:column property="regionName" title="����" sortable="true"/>
	 <display:column property="contractorName" title="��ά��˾" sortable="true"/>
	 <display:column property="watchSize" title="��������" sortable="true"/>
	 <display:column property="infoNeeded" title="����������Ϣ��" sortable="true"/>
	 <display:column property="infoDone" title="ִ����Ϣ��" sortable="true"/>
	 <display:column property="watchExecuterate" title="�����" sortable="true"/>
	 <display:column property="alertCount" title="��������" sortable="true"/>
</display:table>
