<%@ include file="/common/header.jsp"%>
<%@ page import = "com.cabletech.analysis.util.ShowHistoryCurveChart" %>
<%@ page import = "com.cabletech.analysis.beans.HistoryWorkInfoConditionBean" %>
<%@ page import = "java.io.PrintWriter" %>
<script language="JavaScript" src="../js/overlib.js"></script>
<%
    HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean)request.getSession().getAttribute( "HistoryWorkInfoConBean" );
    String strRangeID = bean.getRangeID();
    String regionName = (String)request.getSession().getAttribute( "LOGIN_USER_REGION_NAME" );
    String contractorName = (String)request.getSession().getAttribute( "LOGIN_USER_DEPT_NAME" );
    session.setAttribute("givenDate","0");
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
    
	function changeCurve(regionid) {
        createXMLHttpRequest();
        var url="${ctx}/WorkInfoHistoryAction.do?method=getOnlineNumForDaysByChange&selectedRangeID=" + regionid;
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
    
	function getOnlineHurveBean() {
        if (xmlHttp.readyState == 4) {
            strDate = xmlHttp.responseText;
            //alert("hello:" + strDate);
	        ouroverlib(strDate,STICKY, MOUSEOFF);
	        //ouroverlib('Setting size and posiztion!', STICKY, CAPTION,'Sticky!',HEIGHT, 100,WIDTH,120,LEFT);
        }
    }
        
    function initRange(){
	    var strRangeID = '<%=strRangeID %>';
	    document.getElementById("rangeID").value= strRangeID;
	}

	function overlib(strDate){
	    var flag = false;	
		createXMLHttpRequest();
		var url="${ctx}/WorkInfoHistoryAction.do?method=getOnlineInfo&strDate=" + strDate + "&rangeID=" + document.getElementById("rangeID").value;
	    if ( strDate != null){
	       strDate = null;
	    }
        xmlHttp.open("GET", url, false);
        xmlHttp.onreadystatechange = function() {
          if (xmlHttp.readyState == 4) {
            strDate = xmlHttp.responseText;
            flag = true;
          }
        };
        xmlHttp.send(null);
	    //ouroverlib('Setting size and posiztion!', STICKY, '','Sticky!',HEIGHT, 100,WIDTH,120,LEFT);
	    if (flag == true){
	       ouroverlib(STICKY,HEIGHT, 50,WIDTH,50,LEFT);
	    }
      	return true;
      	//打开窗口
	}
</script>
</head>
<body  onload="initRange()"> 
    <div align="center" width="100%">
	  <font size="3"><strong><%=bean.getStartDate()%>至<%=bean.getEndDate()%>在线人数历史曲线</strong></font>
	</div>
	</br>
	  <table>
		<tr>
			<td>
			</td>
			<td width="20%">
				<select name="rangeID" styleClass="inputtext" style="width:200" id="rangeID"  onChange="changeCurve(this.value)">
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
	</div>
	</br>
	</br>
	</br>
	</br>
	<div align="center" id="pic">
       <%@ include file="commonHistoryCurve.jsp"%>
    </div>
</body>
</html>