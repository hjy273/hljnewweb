<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'><%=session.getAttribute("year") %>��<%=session.getAttribute("month") %>�·�Ѳ��ƻ�ִ�н��</div><hr width='100%' size='1'>
<display:table name="sessionScope.exeList"  id="currentRowObject"  pagesize="16">
  <display:column property="regionname" title="����" maxLength="20"></display:column>
  <display:column property="planpoint" title="�ƻ�Ѳ����"></display:column> 
  <display:column property="factpoint" title="ʵ��Ѳ����"></display:column>
  <display:column property="overallpatrolp" title="Ѳ����"></display:column>
  <display:column property="troublenum" title="��������"/>
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