<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'>
${patrol_name }<bean:write name="queryCon" property="endYear"/>��<bean:write name="queryCon" property="endMonth"/>�·�Ѳ��ƻ�ִ�н��</div><hr width='100%' size='1'>
<display:table name="planexceute"  id="currentRowObject" >
	<display:column property="planname" title="�ƻ�����" 
		sortable="true">
	</display:column>
	<display:column property="executorid" title="Ѳ����" sortable="true"></display:column>
  <display:column property="planpoint" title="�ƻ�Ѳ����" sortable="true"></display:column> 
  <display:column property="factpoint" title="ʵ��Ѳ����" sortable="true"></display:column>
  <display:column property="patrolp" title="Ѳ����(%)" sortable="true"></display:column>
  <display:column media="html" title="���˽��" >
  	<%
  		BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
  		String examineresult = object.get("examineresult").toString();
  		int i = Integer.parseInt(examineresult);
  		String path =(String)application.getAttribute("ctx");
  		switch(i) {
  			case 1 : out.print("<img src=\""+path+"/images/1.jpg\"/>"); break; 
  			case 2 : out.print("<img src=\""+path+"/images/2.jpg\"/>"); break;
  			case 3 : out.print("<img src=\""+path+"/images/3.jpg\"/>"); break;
  			case 4 : out.print("<img src=\""+path+"/images/4.jpg\"/>"); break;
  			case 5 : out.print("<img src=\""+path+"/images/5.jpg\"/>"); break;
  			default: out.print("<img src=\""+path+"/images/0.jpg\"/>");
		}
  	 %>
  </display:column>
</display:table>
<div align="center">
<input name="btnClose" value="�ر�" class="button" type="button" onclick="closePlanExecuteResultWin();" />
</div>
