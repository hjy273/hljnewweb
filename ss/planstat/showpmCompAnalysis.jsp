<%@include file="/common/header.jsp"%>
<%
String compType = (String)request.getSession().getAttribute("CompType");

 %>
<script language="javascript" type="">
  function addGoBack()
  {
      var url="${ctx}/planstat/queryPmCompAnalysis.jsp";
      self.location.replace(url);
  }
</script>
<Br/>
<table class="ooib" id="obody" border="0" cellspacing="0" cellpadding="0" width="100%" height="93%">
	  <tr>
	    <td>
	     <div>
	         <img src="${ctx}/images/1px.gif" alt="">
	         <br>
	         <%if ("H".equals(compType)){%>
	             <iframe id=graphicFrame border=0 marginWidth=0 marginHeight=0 src="${ctx}/ShowPmCompHChart" frameBorder=0 width="100%" scrolling=auto height="100%">        </iframe>
	         <%}else if ("V".equals(compType)) {%>   
	             <iframe id=graphicFrame border=0 marginWidth=0 marginHeight=0 src="${ctx}/ShowPmCompVChart" frameBorder=0 width="100%" scrolling=auto height="100%">        </iframe>
	         <%}%>
	      </div>
	    </td>
	  </tr>
</table>

<template:formSubmit>
<tr>
    <td>
      <center>
      <html:button property="action" styleClass="button" onclick="addGoBack()"  >·µ»Ø</html:button>
      </center>
    </td>
  </tr>
</template:formSubmit>


