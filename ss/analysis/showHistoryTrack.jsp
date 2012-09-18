<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<%@page import="com.cabletech.commons.config.GisConInfo"%>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo" %>
<%@ page import = "com.cabletech.analysis.beans.HistoryWorkInfoConditionBean" %>

<%
	GisConInfo gisip = GisConInfo.newInstance();
    String regionName = (String)request.getSession().getAttribute( "LOGIN_USER_REGION_NAME" );
    String contractorName = (String)request.getSession().getAttribute( "LOGIN_USER_DEPT_NAME" );
    String strRangeID = (String)request.getSession().getAttribute( "TrackRangeID" );
    UserInfo userInfo = (UserInfo)request.getSession().getAttribute( "LOGIN_USER" );
    HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean)request.getSession().getAttribute( "HistoryWorkInfoConBean" );
    String beginDate = (bean.getStartDate()).replaceAll("/","");
    String endDate = (bean.getEndDate()).replaceAll("/","");
    //http://192.168.0.103:6001/WEBGIS/gisextend/igis.jsp?actiontype=102&userid=yyp&rangeid=12&startdate=20070601&enddate=20070630
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
    
	function changeTrack(regionid) {
        createXMLHttpRequest();
        var url="Http://<%=gisip.getServerip()%>:<%=gisip.getServerport()%>/WEBGIS/gisextend/igis.jsp?actiontype=102&userid=<%=userInfo.getUserID()%>&rangeid=" + regionid + "&startdate=<%=beginDate%>&enddate=<%=endDate%>";
        //xmlHttp.open("POST", url, true);
        //xmlHttp.onreadystatechange = startCallback;
        //xmlHttp.send(null);
        document.getElementById("FrameTrack").src=url;
    }
	function startCallback() {
        if (xmlHttp.readyState == 4) {
            var track_span = document.getElementById("track");
        	track_span.innerHTML = xmlHttp.responseText;
        }
    }
        
    function initRange(){
	    var strRangeID = '<%=strRangeID %>';
	    document.getElementById("rangeID").value= strRangeID;
	    //changeTrack(strRangeID);
	}
</script>
</head>
<body  onload="initRange()"> 
    <div align="center" width="100%">
	  <font size="3"><strong>历史巡检轨迹</strong></font>
	</div>
	<br>
	<table>
		<tr>
			<td>
			</td>
			<td width="20%">
				<select name="rangeID" styleClass="inputtext" style="width:200" id="rangeID"  onChange="changeTrack(this.value)">
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
	<div align="center" id="track" style="display:"> 
		<iframe id="FrameTrack" marginWidth="0" marginHeight="0" src="Http://<%=gisip.getServerip()%>:<%=gisip.getServerport()%>/WEBGIS/gisextend/igis.jsp?actiontype=102&userid=<%=userInfo.getUserID()%>&rangeid=<%=strRangeID%>&startdate=<%=beginDate%>&enddate=<%=endDate%>" frameBorder=2 width="100%" scrolling=auto height="100%"> </iframe>
	</div>
</body>
</html>