
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<logic:equal value="SECTION" scope="session" name="type">
<display:table name="sessionScope.noteNum"   id="currentRowObject"  pagesize="14">
     <display:column property="titlename" title="����" sortable="true"/>
     <display:column property="total" title="����" />
	 <display:column property="patrol" title="Ѳ��" />
	 <display:column property="watch" title="����"  />
	 <display:column property="collect" title="�ɼ�" /> 
	 <display:column property="trouble" title="����"  /> 
	 <display:column property="other" title="����"/> 
</display:table>
</logic:equal>
<logic:equal value="sim" scope="session" name="type">
<display:table name="sessionScope.noteNum"   id="currentRowObject"  pagesize="14"> 
     <display:column property="titlename" title="Ѳ��ά����" sortable="true"/>
	 <display:column property="simid" title="SIM����" />
     <display:column property="firsttime" title="��һ��ʱ��" />
	 <display:column property="lasttime" title="���һ��ʱ��" />
     <display:column property="total" title="����" />
	 <display:column property="patrol" title="Ѳ��" />
	 <display:column property="watch" title="����"  />
	 <display:column property="collect" title="�ɼ�" /> 
	 <display:column property="trouble" title="����"  /> 
	 <display:column property="other" title="����"  /> 
</display:table>
</logic:equal>
