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
        	if (xmlHttp.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
                    var notepic_span = document.getElementById("notepic");
        			notepic_span.innerHTML = xmlHttp.responseText;
             } else { //ҳ�治����
                    alert("���������ҳ�����쳣��");
             }
           
        }
    }
    function overlib(str){
      //alert(str);
      return false;
      //�򿪴���
	}
	function nd(){
	//�رմ���
  	return false;
	}
    
</script>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'>����ʵʱ����ִ���������ͼ</div><hr width='100%' size='1'>
<br>
<div align="right" width="100%">
<%@include file="common.jsp" %>
</div>
<br>
<div align="center" id="notepic" style="display:">
<%@include file="notechartpic.jsp" %>

</div>
