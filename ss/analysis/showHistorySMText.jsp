<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<%@ page import = "com.cabletech.analysis.beans.HistoryWorkInfoConditionBean" %>
<%
 String strRangeID = (String)request.getSession().getAttribute( "SMRangeID" );
 String regionName = (String)request.getSession().getAttribute( "LOGIN_USER_REGION_NAME" );
 String contractorName = (String)request.getSession().getAttribute( "LOGIN_USER_DEPT_NAME" );
 HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean)request.getSession().getAttribute("HistoryWorkInfoConBean");
 
%>
<script language="javascript" type="">
  var req;
  var which;
  var abc;

  function retrieveURL(url,rangID) {
    document.getElementById("FrmSMByRange").style.display = "none";
    if (window.XMLHttpRequest) { // Non-IE browsers
      req = new XMLHttpRequest();
      req.onreadystatechange = processStateChange;
      try {
        req.open("GET", url, true);
      } catch (e) {
        alert(e);
      }
      req.send(null);
    } else if (window.ActiveXObject) { // IE
      req = new ActiveXObject("Microsoft.XMLHTTP");
      if (req) {
        req.onreadystatechange = processStateChange;
        req.open("GET", url, true);
        req.send();
      }
    }
    
  }

  function processStateChange() {
    if (req.readyState == 4) { // Complete
      if (req.status == 200) { // OK response
        if (document.getElementById("smSpan").style.display == "none"){
            document.getElementById("FrmSMByRange").style.display = "block";
        }
        document.getElementById("smSpan").height="100%";
        document.getElementById("smSpan").width="100%";
        document.getElementById("smSpan").innerHTML = req.responseText;
      } else {
        alert("Problem: " + req.statusText);
      }
    }
  }
  function initRange(){
     var strRangeID = '<%=strRangeID %>';
     document.getElementById("rangeID").value= strRangeID;
  }

</script>
<br>
<body onload="initRange()">
   <div align="center" width="100%">
     <font size="3"><strong><%=bean.getStartDate()%>至<%=bean.getEndDate()%>任务执行情况表</strong></font>
   </div>
   <br>
<table>
	<tr>
		<td>
		</td>
		<td width="20%">
			<select name="rangeID" styleClass="inputtext" style="width:200" id="rangeID" onChange="retrieveURL('${ctx}/WorkInfoHistoryAction.do?method=getSMInfoByChangeRange&flagGraphic=0&rangeID=' + this.value,this.value);">
			  <logic:equal value="11" name="LOGIN_USER" property="type">
			  	<option value="11">全省区域</option>
			  </logic:equal>
			  <logic:equal value="12" name="LOGIN_USER" property="type">
			  	<option value="12"><%=regionName %></option>
			  </logic:equal>        
			  <logic:equal value="22" name="LOGIN_USER" property="type">
			  	<option value="22"><%=contractorName %></option>
			     </logic:equal>
			    	<logic:present name="rangeinfo">
			       <logic:iterate id="rangeinfoId" name="rangeinfo">
			         <option value="<bean:write name="rangeinfoId" property="rangeid"/>">
			              <bean:write name="rangeinfoId" property="rangename"/>
			         </option>
			       </logic:iterate>
			     </logic:present>
			</select> 		
		</td>
	</tr>
</table>
<span id="smSpan"></span> 
<div>
    <iframe id="FrmSMByRange" src="${ctx}/analysis/showHistorySMInfoText.jsp" marginWidth="0" marginHeight="0" frameBorder=0 width="100%" scrolling=auto height="100%"> </iframe>
</div>
</body>






