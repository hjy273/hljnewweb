<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<br>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'>����ʵʱ���ŷ������</div><hr width='100%' size='1'>
<display:table name="sessionScope.realtimeinfo"   id="currentRowObject"  pagesize="18">
	 <logic:equal value="group" name="PMType">
       <display:column property="name" title="Ѳ��ά����" sortable="true"/>
     </logic:equal>
     <logic:notEqual value="group" name="PMType">
       <display:column property="name" title="Ѳ��Ա" sortable="true"/>
     </logic:notEqual>
	 <display:column property="simnumber" title="SIM����" sortable="true"/>
     <display:column property="minarrivetime" title="��һ��ʱ��" sortable="true"/>
	 <display:column property="arrivetime" title="���һ��ʱ��" sortable="true"/>
     <display:column property="total" title="����" sortable="true" />
	 <display:column property="patrol" title="Ѳ��" sortable="true" href="${ctx}/RealTimeAction.do?method=showRealTimePerCard&handlestate=4,7,8,10,12" paramId="simnumber" paramProperty="simnumber"/>
	 <display:column property="watch" title="����" sortable="true" href="${ctx}/RealTimeAction.do?method=showRealTimePerCard&handlestate=6,9,11" paramId="simnumber" paramProperty="simnumber"/>
	 <display:column property="collect" title="�ɼ�" sortable="true" href="${ctx}/RealTimeAction.do?method=showRealTimePerCard&handlestate=3" paramId="simnumber" paramProperty="simnumber"/> 
	 <display:column property="trouble" title="����" sortable="true" href="${ctx}/RealTimeAction.do?method=showRealTimePerCard&handlestate=20" paramId="simnumber" paramProperty="simnumber"/> 
	 <display:column property="other" title="����" sortable="true" href="${ctx}/RealTimeAction.do?method=showRealTimePerCard&handlestate=0,1,2,13,14,15,16,19" paramId="simnumber" paramProperty="simnumber"/> 

</display:table>