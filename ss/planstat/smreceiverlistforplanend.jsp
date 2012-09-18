<%@ page contentType="text/html; charset=GBK" %>
<%@page import="com.cabletech.commons.config.*"%>
<%@ taglib uri="/WEB-INF/extremecomponents.tld" prefix="ec" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="cabletechtag" prefix="apptag"%>
<%@ taglib uri="templatetag" prefix="template"%>
<link rel="stylesheet" href="${ctx}/css/extremecomponents.css" type="text/css" media="screen, print" />

<%
  GisConInfo gisip = GisConInfo.newInstance();
  String daysForPlanStart = gisip.getDaysfornoticeplanend() + "天前刚刚完成的计划列表";
%>

<html>

<head>
<title>eXtremeTest</title>

<style type="text/css">

BODY {
  PADDING-LEFT: 0px;PADDING-BOTTOM: 0px; MARGIN: 0px;PADDING-TOP: 0px;font-size:9pt;
  background-color: #EAE9E4;
  	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.eXtremeTable {
margin: 0;
padding: 0;
}

.eXtremeTable select {
font-family: Verdana;
font-size: 9px;
border: solid 1px #EEE;
width: 75px;
}

.eXtremeTable .tableRegion {
border: 1px solid silver;
padding: 2px;
font-family:Verdana;
font-size: 10px;
margin-top: 7px;
}

.eXtremeTable .filter {
background-color: #efefef;
}

.eXtremeTable .filter input {
font-family: Verdana;
font-size: 10px;
width: 100%;
display:none;
}

.eXtremeTable .filter select {
font-family: Verdana;
font-size: 9px;
border: solid 1px #EEE;
width: 100%;
}

.eXtremeTable .tableHeader {
background-image:url("../images/bg_th_main.gif");
background-color: #308dbb;
color: white;
font-family:Verdana;
font-size: 11px;
font-weight: bold;
text-align: left;
padding-right: 3px;
padding-left: 3px;
padding-top: 4;
padding-bottom: 4;
margin: 0;
border-right-style: solid;
border-right-width: 1px;
border-color: white;
}

.eXtremeTable .tableHeaderSort {
background-color: #3a95c2;
color: white;
font-family:Verdana;
font-size: 11px;
font-weight: bold;
text-align: left;
padding-right: 3px;
padding-left: 3px;
padding-top: 4;
padding-bottom: 4;
border-right-style: solid;
border-right-width: 1px;
border-color: white;
}

.eXtremeTable .odd a, .even a {
color: Black;
font-size: 10px;
}

.eXtremeTable .odd td, .eXtremeTable .even td {
padding-top: 2px;
padding-right: 3px;
padding-bottom: 2px;
padding-left: 3px;
vertical-align: middle;
font-family:Verdana;
font-size: 11px;
}

.eXtremeTable .odd {
background-color: #FFFFFF;
}

.eXtremeTable .even {
background-color: #dfe4e8;
}

.eXtremeTable .highlight td {
color: black;
font-size: 11px;
padding-top: 2px;
padding-right: 3px;
padding-bottom: 2px;
padding-left: 3px;
vertical-align: middle;
background-color: #fdecae;
}

.eXtremeTable .highlight a, .highlight a {
color: black;
font-size: 10px;
}

.eXtremeTable .toolbar {
background-color: #F4F4F4;
font-family:Verdana;
font-size: 9px;
margin-right: 1px;
border-right: 1px solid silver;
border-left: 1px solid silver;
border-top: 1px solid silver;
border-bottom: 1px solid silver;
}

.eXtremeTable .toolbar td {
color: #444444;
padding: 0px 3px 0px 3px;
text-align:center;
}

.eXtremeTable .separator {
width: 7px;
}

.eXtremeTable .statusBar {
background-color: #F4F4F4;
font-family:Verdana;
font-size: 10px;
}

.eXtremeTable .filterButtons {
background-color: #efefef;
text-align: right;
display:none;
}

.eXtremeTable .title {
color: #444444;
font-weight: bold;
font-family:Verdana;
font-size: 15px;
text-align: center;
vertical-align: middle;
horizontal-align: center;
align:center;
padding-left: 290px;
}

.eXtremeTable .title span {
margin-left: 7px;
}

.eXtremeTable .formButtons {
display: block;
margin-top: 10px;
margin-left: 5px;
}

.eXtremeTable .formButton {
cursor: pointer;
font-family:Verdana;
font-size:10px;
font-weight: bold;
background-color: #308dbb;
color: white;
margin-top: 5px;
border: outset 1px #333;
vertical-align: middle;
}

.eXtremeTable .tableTotal {
background-color: #FFFFFF;
border-top: solid 1px Silver;	
}

.eXtremeTable .tableTotalEmpty {
background-color: #FFFFFF;	
}

</style>

<script type="text/javascript">
    function send(){
       var thevalue="";
       var hasChoosed=0;
       var x=document.getElementsByName("choice_id").length; 
          var y=0; 
          if(x==1){
            if(ec.choice_id.checked==true){
                y++;
                thevalue = ec.choice_id.value; 
                if(confirm('确定发送短信吗？')){
		            ec.action="${ctx}/SMNoticePlanEndAction.do?method=sendMessage&strsimnumberlist=" + thevalue;
		            ec.submit();
			    }
            }else{
                alert("请选择消息发送对象");           
            }
          }else{
            for(var i=0;i<x;i++){
               if(ec.choice_id[i].checked==true){
                   hasChoosed = 1;
                   y++;
                   thevalue += ec.choice_id[i].value + "|";
               } 
            }
            if (hasChoosed == 0){
               alert("请选择消息发送对象");   
            }else{
               if(confirm('确定发送短信吗？')){
		           ec.action="${ctx}/SMNoticePlanEndAction.do?method=sendMessage&strsimnumberlist=" + thevalue;
		           ec.submit();
			   }
            }
          }
    }

</script>

</head>

<body style="margin:10px;">
    <div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'><%=daysForPlanStart%></div>
	<ec:table 
	items="smreceiverlist"
	var="mytable"
	action="${pageContext.request.contextPath}/SMNoticePlanEndAction.do?method=showNoticeList"
	imagePath="${pageContext.request.contextPath}/images/table/*.gif"
	width="95%"
	title=""
	rowsDisplayed="18"
	locale="zh_CN"
	>
	  <ec:row highlightRow="true">
	  <ec:column 
	  alias="checkbox"
	  title="选择"
	  headerStyle="text-align:center" 
	  filterable="false" 
	  viewsAllowed="html" 
	  sortable="false"
	  >
	  <input name="choice_id" type="checkbox"  value="${mytable.simnumber},${mytable.enddate},${mytable.planname}">
	</ec:column>
	<ec:column property="planname" title="计划名称"/>
	<ec:column property="begindate" title="开始日期"/>
	<ec:column property="enddate" title="结束日期"/>
	<ec:column property="simnumber" title="SIM卡号"/>
	  </ec:row>
	</ec:table>
	    <center><input type="button" value="发送信息"  onclick="send()"></center>

</body>
</html>