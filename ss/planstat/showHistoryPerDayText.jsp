<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<%
   String historydate = (String)session.getAttribute("historydate");
   String conname = (String)session.getAttribute("mycontractorname");
%>
<br>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'><%=conname%><%=historydate %>���ŷ������</div><hr width='100%' size='1'>
<display:table name="sessionScope.historyperdayinfo"   id="currentRowObject"  pagesize="15">
	 <logic:equal value="group" name="PMType">
       <display:column property="patrolname" title="Ѳ��ά����" sortable="true"/>
     </logic:equal>
     <logic:notEqual value="group" name="PMType">
       <display:column property="patrolname" title="Ѳ��Ա" sortable="true"/>
     </logic:notEqual>
	 <display:column property="simid" title="SIM����" sortable="true"/>
     <display:column property="firstdate" title="����ʱ��" sortable="true" />
     <display:column property="lastdate" title="ĩ��ʱ��" sortable="true" />
     <display:column property="totalnum" title="����" sortable="true" />
	 <display:column property="patrolnum" title="Ѳ��" sortable="true" href="${ctx}/RealTimeAction.do?method=showHistoryPerCard&handlestate=4,7,8,10,12" paramId="simid" paramProperty="simid"/>
	 <display:column property="watchnum" title="����" sortable="true" href="${ctx}/RealTimeAction.do?method=showHistoryPerCard&handlestate=6,9,11" paramId="simid" paramProperty="simid"/>
	 <display:column property="collectnum" title="�ɼ�" sortable="true" href="${ctx}/RealTimeAction.do?method=showHistoryPerCard&handlestate=3" paramId="simid" paramProperty="simid"/> 
	 <display:column property="troublenum" title="����" sortable="true" href="${ctx}/RealTimeAction.do?method=showHistoryPerCard&handlestate=20" paramId="simid" paramProperty="simid"/> 
	 <display:column property="othernum" title="����" sortable="true" href="${ctx}/RealTimeAction.do?method=showHistoryPerCard&handlestate=0,1,2,13,14,15,16,19" paramId="simid" paramProperty="simid"/> 
	 <display:column property="dailykm" title="���" sortable="true" />
	 <display:column property="avgsendtime" title="���ʱ��" sortable="true" />
	 <display:column property="avgsenddistance" title="�������" sortable="true" />
</display:table>

 <div align="center">
 	  <table width="620" border="0" align="center">
        <tr style="color:red">
          <td width="7%" scope="col" valign="top">˵����</td>
             <logic:equal value="group" name="PMType"> 
	            <td width="94%" scope="col">
	              1�����:ָѲ��ά�����ھ���ĳһ���Ѳ�������(��λ:����),������ʾ�����������ŷ��͵�ľ�����ܺ�.<br>
	              2�����ʱ��:ָѲ��ά�����ھ���ĳһ�췢�Ͷ��ŵ�ƽ�����ʱ��(��λ:��),������ʾÿ���೤ʱ�䷢��һ������.<br> 
	              3���������:ָѲ��ά�����ھ���ĳһ�췢�Ͷ��ŵ�ƽ���������(��λ:��),������ʾÿ����Զ���뷢��һ������.<br> 
			    </td>
			 </logic:equal>
			 <logic:notEqual value="group" name="PMType"> 
	            <td width="94%" scope="col">
	              1�����:ָѲ��Ա�ھ���ĳһ���Ѳ�������(��λ:����),������ʾ�����������ŷ��͵�ľ�����ܺ�.<br>
	              2�����ʱ��:ָѲ��Ա�ھ���ĳһ�췢�Ͷ��ŵ�ƽ�����ʱ��(��λ:��),������ʾÿ���೤ʱ�䷢��һ������.<br> 
	              3���������:ָѲ��Ա�ھ���ĳһ�췢�Ͷ��ŵ�ƽ���������(��λ:��),������ʾÿ����Զ���뷢��һ������.<br> 
			    </td>
			 </logic:notEqual>
        </tr>
      </table>
   </div>