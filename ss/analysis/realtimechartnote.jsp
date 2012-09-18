<%@ include file="/common/header.jsp"%>

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
	function go(select) {
        createXMLHttpRequest();
        var regionid = select.value;
        var url="${ctx}/RealTimeNoteAction.do?method=showChartNoteInfo&range="+regionid+"&s=true";
        xmlHttp.open("GET", url, true);
        xmlHttp.onreadystatechange = startCallback;
        xmlHttp.send(null);
    }
	function startCallback() {
        if (xmlHttp.readyState == 4) {
        	if (xmlHttp.status == 200) { // 信息已经成功返回，开始处理信息
                    var notepic_span = document.getElementById("notepic");
        			notepic_span.innerHTML = xmlHttp.responseText;
             } else { //页面不正常
                    alert("您所请求的页面有异常。");
             }
           
        }
    }
    function overlib(str){
      //alert(str);
      return false;
      //打开窗口
	}
	function nd(){
	//关闭窗口
  	return false;
	}
    
</script>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'>今日实时任务执行情况条形图</div><hr width='100%' size='1'>
<br>
<div align="right" width="100%">
<%@include file="common.jsp" %>
</div>
<br>
<div align="center" id="notepic" style="display:">
<%@include file="notechartpic.jsp" %>

</div>
