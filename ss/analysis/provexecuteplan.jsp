<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'><%=session.getAttribute("year") %>年<%=session.getAttribute("month") %>月份巡检计划执行结果</div><hr width='100%' size='1'>
<display:table name="sessionScope.exeList"  id="currentRowObject"  pagesize="16">
  <display:column property="regionname" title="区域" maxLength="20"></display:column>
  <display:column property="planpoint" title="计划巡检点次"></display:column> 
  <display:column property="factpoint" title="实际巡检点次"></display:column>
  <display:column property="overallpatrolp" title="巡检率"></display:column>
  <display:column property="troublenum" title="隐患数量"/>
  <display:column media="html" title="考核结果" >
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