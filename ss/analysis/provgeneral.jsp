<%@ include file="/common/header.jsp"%>
<%@ page import = "com.cabletech.analysis.util.AssessResultChart" %>
<%@ page import = "java.io.PrintWriter" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.Iterator" %>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'><%=session.getAttribute("year") %>年<%=session.getAttribute("month") %>月份统计详细信息</div><hr width='100%' size='1'>
<br>
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0"  bgcolor="#EAE9E4" >
  <!-- 计划信息 -->
  <tr>
    <td width="60%" valign="top">
	<table width="100%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#999999">
	<tr>
	 <td bgcolor="#FFFFFF" colspan="6" align="center">各地市考核结果</td>
	</tr>
	<%
		List whole = (List)session.getAttribute("whole");
		AssessResultChart genChart = new AssessResultChart();
		String filename = genChart.generatePieChart(session, new PrintWriter(out),300,250);
		String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filename;
		int size = whole.size();
		for(int i=0;i<size;){
			BasicDynaBean bean1 = (BasicDynaBean)whole.get(i);
	 %>
	<tr>
	 	<td width="10%" class=trcolor align="center"><%=bean1.get("regionname")%></td>
    	<td width="20%" bgcolor="#FFFFFF">
    	<%
				String examineresult = (String)bean1.get("examineresult");
				int level = Integer.parseInt(examineresult);
				String path =(String)application.getAttribute("ctx");
		  		switch(level) {
			  		case 1 : out.print("<img src=\""+path+"/images/1.jpg\"/>"); break; 
		  			case 2 : out.print("<img src=\""+path+"/images/2.jpg\"/>"); break;
		  			case 3 : out.print("<img src=\""+path+"/images/3.jpg\"/>"); break;
		  			case 4 : out.print("<img src=\""+path+"/images/4.jpg\"/>"); break;
		  			case 5 : out.print("<img src=\""+path+"/images/5.jpg\"/>"); break;
		  			default: out.print("<img src=\""+path+"/images/0.jpg\"/>");
				}
		%>
		</td>
    	<%
    		if(i+1<size){
    			BasicDynaBean bean2 = (BasicDynaBean)whole.get(i+1);
    	 %>
		<td width="10%" class=trcolor align="center"><%=bean2.get("regionname")%></td>
    	<td width="20%" bgcolor="#FFFFFF">
		<%
				examineresult = (String)bean2.get("examineresult");
				level = Integer.parseInt(examineresult);
				String path =(String)application.getAttribute("ctx");
		  		switch(level) {
			  		case 1 : out.print("<img src=\""+path+"/images/1.jpg\"/>"); break; 
		  			case 2 : out.print("<img src=\""+path+"/images/2.jpg\"/>"); break;
		  			case 3 : out.print("<img src=\""+path+"/images/3.jpg\"/>"); break;
		  			case 4 : out.print("<img src=\""+path+"/images/4.jpg\"/>"); break;
		  			case 5 : out.print("<img src=\""+path+"/images/5.jpg\"/>"); break;
		  			default: out.print("<img src=\""+path+"/images/0.jpg\"/>");
				}
		%>
		</td>
    	<%	}else{ %>
    	<td width="10%" class=trcolor align="center"></td>
    	<td width="20%" bgcolor="#FFFFFF"></td>	
    	<%  }%>
    	
    	<%
    		if(i+2<size){
    			BasicDynaBean bean3 = (BasicDynaBean)whole.get(i+2);
    	 %>
		<td width="10%" class=trcolor align="center"><%=bean3.get("regionname")%></td>
    	<td width="20%" bgcolor="#FFFFFF">
		<%
				examineresult = (String)bean3.get("examineresult");
				level = Integer.parseInt(examineresult);
				String path =(String)application.getAttribute("ctx");
		  		switch(level) {
			  		case 1 : out.print("<img src=\""+path+"/images/1.jpg\"/>"); break; 
		  			case 2 : out.print("<img src=\""+path+"/images/2.jpg\"/>"); break;
		  			case 3 : out.print("<img src=\""+path+"/images/3.jpg\"/>"); break;
		  			case 4 : out.print("<img src=\""+path+"/images/4.jpg\"/>"); break;
		  			case 5 : out.print("<img src=\""+path+"/images/5.jpg\"/>"); break;
		  			default: out.print("<img src=\""+path+"/images/0.jpg\"/>");
				}
		%>
		</td>
    	<%	}else{ %>
    	<td width="10%" class=trcolor align="center"></td>
    	<td width="20%" bgcolor="#FFFFFF"></td>	
    	<%  }%>
    	
	</tr>
	<%
			i +=3;
		} 
	%>
	</table>
	</td>
  </tr>
  <tr>
	<td width="40%" rowspan="6" align="left">
	<%
	if(filename.indexOf("_nodata") != -1 || filename.indexOf("_error") != -1){
	%>
	<img src="${ctx}/images/public_nodata_500x300.png" width=300 height=250 border=0>
	<%
	}else{
	
	%>
	<img src="<%= graphURL %>" width="300" height="250" border=0 usemap="#<%= filename %>">
	<%
	}
	%>
	</td>
  </tr>
</table>