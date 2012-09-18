<%@ include file="/common/header.jsp"%>
<%@ page import = "com.cabletech.analysis.beans.HistoryWorkInfoConditionBean" %>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />

<%
    HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean)request.getSession().getAttribute( "HistoryWorkInfoConBean" );
    //String strRangeID = bean.getRangeID();
    String strRangeID = (String)request.getSession().getAttribute( "SMGraphicRangeID" );
    String regionName = (String)request.getSession().getAttribute( "LOGIN_USER_REGION_NAME" );
    String contractorName = (String)request.getSession().getAttribute( "LOGIN_USER_DEPT_NAME" );
%>

<html>
<head>
<script language="javascript">
	var xmlHttp;
    function createXMLHttpRequest() {
        if (window.ActiveXObject) {
           xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        } 
        else if (window.XMLHttpRequest) {
           xmlHttp = new XMLHttpRequest();                
        }
    }
    
	function changeSMGraphic(regionid) {
        createXMLHttpRequest();
        var url="${ctx}/WorkInfoHistoryAction.do?method=getSMInfoByChangeRange&flagGraphic=1&rangeID=" + regionid;
        xmlHttp.open("POST", url, true);
        xmlHttp.onreadystatechange = startCallback;
        xmlHttp.send(null);
    }
	function startCallback() {
        if (xmlHttp.readyState == 4) {
            var pic_span = document.getElementById("pic");
        	pic_span.innerHTML = xmlHttp.responseText;
        }
    }
    function initRange(){
	    var strRangeID = '<%=strRangeID %>';
	    document.getElementById("rangeID").value= strRangeID;
	    //changeCurve(strRangeID);
	}
</script>
</head>
<body  onload="initRange()">
   <div align="center" width="100%">
     <font size="3"><strong><%=bean.getStartDate()%>至<%=bean.getEndDate()%>任务执行图例</strong></font>
   </div>
	<table>
		<tr>
			<td>
			</td>
			<td width="20%">
				<select name="rangeID" styleClass="inputtext" style="width:200" id="rangeID"  onChange="changeSMGraphic(this.value)">
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
	<div align="center" id="pic" style="display:">
       <%@ include file="commonHistorySMGraphic.jsp"%>
    </div>
</body>
</html>