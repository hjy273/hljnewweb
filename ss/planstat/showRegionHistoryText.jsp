<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />

<br>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'>�����м��ƶ���˾<%=(String)session.getAttribute("mystatdate") %>���ŷ���ͳ��</div><hr width='100%' size='1'>
<display:table name="sessionScope.regionhistoryinfo"   id="currentRowObject"  pagesize="18">
     <display:column property="regionname" title="����" sortable="true"/>
     <display:column property="dailykm" title="Ѳ�����(KM)" sortable="true"/>
     <display:column property="totalnum" title="����" sortable="true" />
	 <display:column property="patrolnum" title="Ѳ��" sortable="true" href="${ctx}/RealTimeAction.do?method=showRegionHistoryPerReg&handlestate=4,7,8,10,12" paramId="regionid" paramProperty="regionid"/>
	 <display:column property="watchnum" title="����" sortable="true" href="${ctx}/RealTimeAction.do?method=showRegionHistoryPerReg&handlestate=6,9,11" paramId="regionid" paramProperty="regionid"/>
	 <display:column property="collectnum" title="�ɼ�" sortable="true" href="${ctx}/RealTimeAction.do?method=showRegionHistoryPerReg&handlestate=3" paramId="regionid" paramProperty="regionid"/> 
	 <display:column property="troublenum" title="����" sortable="true" href="${ctx}/RealTimeAction.do?method=showRegionHistoryPerReg&handlestate=20" paramId="regionid" paramProperty="regionid"/> 
	 <display:column property="othernum" title="����" sortable="true" href="${ctx}/RealTimeAction.do?method=showRegionHistoryPerReg&handlestate=0,1,2,13,14,15,16,19" paramId="regionid" paramProperty="regionid"/> 
</display:table>