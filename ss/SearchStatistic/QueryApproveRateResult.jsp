<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.statistics.beans.*"%>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" type="">
<!--
var enlargeFlag = 0;

function makeSize(){
	if(enlargeFlag == 0){
		parent.makeSize(0);
		enlargeIcon.src = "${ctx}/images/icon_arrow_down.gif";
		enlargeIcon.alt = "显示查询条件";
		enlargeFlag = 1;
	}else{
		parent.makeSize(1);
		enlargeIcon.src = "${ctx}/images/icon_arrow_up.gif";
		enlargeIcon.alt = "隐藏查询条件";
		enlargeFlag = 0;
	}
}

function toCloseThis(){
	try{
		parent.makeSize(1);
	}catch(e){}

	self.location.replace("${ctx}/common/blank.html");
}

function toExpotr(){
	self.location.replace("${ctx}/StatisticAction.do?method=ExportPlanAppRate");
}

//-->
</script><%
  ApproveRateBean appRate = (ApproveRateBean) request.getSession().getAttribute("queryresult");
  String flag = appRate.getIfhasrecord();
  if (flag.equals("0")) {
%>
<br>
<br>
<br>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2">没有计划信息可用，不能生成报表！</td>
  </tr>
</table>
<%} else {%>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2">      计划审批通过率报表
      &nbsp;&nbsp;
      <a href="javascript:makeSize()">
        <img id="enlargeIcon" src="${ctx}/images/icon_arrow_up.gif" width="14" height="16" border="0" alt="隐藏查询条件"></a>
    </td>
  </tr>
</table>
<br>
<table width="95%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#999999">
  <!-- 计划信息 -->
  <tr class=trwhite>
    <td width="15%" bgcolor="#FFFFFF" class=trcolor>代维单位</td>
    <td width="45%" bgcolor="#FFFFFF"><%=appRate.getContractorname() %>    </td>
    <td width="300" rowspan="9" bgcolor="#CCCCCC">
      <div>
        <img src="${ctx}/images/1px.gif" alt="">
        <br>
        <iframe name=treemenu border=0 marginWidth=0 marginHeight=0 src="${ctx}/ShowChart?PRate=<%=appRate.getApproverate()%>&LRate=<%=appRate.getUnapproverate() %>&appRateFlag=1" frameBorder=0 width="100%" scrolling=NO height="165">        </iframe>
      </div>
    </td>
  </tr>
  <tr class=trwhite>
    <td bgcolor="#FFFFFF" class=trcolor>考核对象</td>
    <td width="45%" bgcolor="#FFFFFF"><%=appRate.getApproveplantype() %>    </td>
  </tr>
  <tr class=trwhite>
    <td bgcolor="#FFFFFF" class=trcolor>考核标准</td>
    <td width="45%" bgcolor="#FFFFFF">
      <span class="trcolor"><%=appRate.getApprovetimes()%>      </span>
    </td>
  </tr>
  <tr class=trcolor>
    <td class=trcolor bgcolor="#FFFFFF">统计时间</td>
    <td bgcolor="#FFFFFF"><%=appRate.getStatistictime() %>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">计划数量</td>
    <td bgcolor="#FFFFFF"><%=appRate.getPlannum() %>      个
</td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">      满足指标
      <br>
      计划数量
</td>
    <td bgcolor="#FFFFFF"><%=appRate.getAppplannum() %>      个
</td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">      未满足指标
      <br>
      计划数量
</td>
    <td bgcolor="#FFFFFF"><%=appRate.getUnapproveplannum() %>      个
</td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">通过率</td>
    <td bgcolor="#FFFFFF"><%=appRate.getApproverate() %>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">返回率</td>
    <td bgcolor="#FFFFFF"><%=appRate.getUnapproverate() %>    </td>
  </tr>
</table>
<br>
<br>
<template:formSubmit>
  <td width="85">
    <html:button property="action" styleClass="button" onclick="toExpotr()">Excel报表</html:button>
  </td>
  <td width="85">
    <html:button property="action" styleClass="button" onclick="toCloseThis()">关闭窗口</html:button>
  </td>
</template:formSubmit>
<br>
<br>
<%
  }
      //System.out.println(flag+"!!!!!!!!!!!!!");
%>
