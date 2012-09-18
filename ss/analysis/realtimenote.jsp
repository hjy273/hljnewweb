<%@ include file="/common/header.jsp"%>

<%
	String rangeid = (String)request.getAttribute("rangeid");
	if(rangeid !=null && "".equals(rangeid)){
		rangeid = "null";
	}
 %>
<script language="javascript">

	var xmlHttp;
	var value;
    function createXMLHttpRequest() {
        if (window.ActiveXObject) {
           xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        } 
        else if (window.XMLHttpRequest) {
           xmlHttp = new XMLHttpRequest();                
        }
    }
	function go1(select) {
        createXMLHttpRequest();
        var regionid = select.value;
        value = select.value;
        var url="${ctx}/RealTimeNoteAction.do?method=getNoteNumInfo&range="+regionid+"&s=true";
       
        xmlHttp.open("get", url, true);
        xmlHttp.onreadystatechange = startCallback;
        xmlHttp.send(null);
        
    }
	function startCallback() {
        if (xmlHttp.readyState == 4) {
        	  if (xmlHttp.status == 200) { // 信息已经成功返回，开始处理信息
                    var notediv = document.getElementById("notediv");
        			notediv.innerHTML = xmlHttp.responseText;
              } else { //页面不正常
                    alert("您所请求的页面有异常。");
              }
        }
    }
    function go(select) {
    	var regionid = select.value;
    	var url="${ctx}/RealTimeNoteAction.do?method=getNoteNumInfo&range="+regionid+"&s=true";
    	self.location.replace(url);
    }
    
</script>
<br>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'>今日实时任务执行情况</div><hr width='100%' size='1'>
<div align="right" width="100%">
<%@include file="common.jsp" %>
</div>
<br>
<div id="notediv">
<%@include file="displaynote.jsp" %>
</div>
<script language="javascript">
	var slt=document.getElementById("rangeId");
	for(var i=0;i<slt.options.length;i++){
		if(slt.options[i].value==<%=rangeid%>){
			slt.options[i].selected=true;
		}
	}
</script>
