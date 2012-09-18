<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.watchinfo.beans.*"%>
<script language="javascript" type="">
<!--

function loadWatch(idValue){
	var url = "${ctx}/watchAction.do?method=loadWatch&id=" + idValue;
	self.location.replace(url);
}

function toExpotr(){
	var url = "${ctx}/watchAction.do?method=exportWatchInfo";
	self.location.replace(url);
}

function RedirectPage(){
	var url = "${ctx}/watchAction.do?method=queryWatch";
	self.location.replace(url);
}

//-->
</script>
<%
	WatchBean bean = (WatchBean) request.getSession().getAttribute("watchBean");

%>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2">      基础盯防信息报表
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </td>
  </tr>
</table>
<br>
<table width="95%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#999999">
  <!-- 计划信息 -->
  <tr bgcolor="#FFFFFF">
    <td width="11%" class=trcolor rowspan="14" align="center">盯防信息</td>
    <td class=trcolor width="14%">盯防名称</td>
    <td><%=bean.getPlaceName() %>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">所属区域</td>
    <td bgcolor="#FFFFFF"><%=bean.getInnerregion()%>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">工地类型</td>
    <td bgcolor="#FFFFFF"><%=bean.getPlacetype()%>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">盯防级别</td>
    <td bgcolor="#FFFFFF"><%=bean.getDangerlevel()%>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">工地位置</td>
    <td bgcolor="#FFFFFF"><%=bean.getWatchplace()%>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">隐患原因</td>
    <td bgcolor="#FFFFFF"><%=bean.getWatchreason()%>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">经纬度</td>
    <td bgcolor="#FFFFFF">经度：<%=bean.getX()%> <br> 纬度：<%=bean.getY()%>  </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">盯防范围</td>
    <td bgcolor="#FFFFFF"><%=bean.getWatchwidth() %>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">盯防负责组</td>
    <td bgcolor="#FFFFFF"><%=bean.getPrincipal() %>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">开始日期</td>
    <td bgcolor="#FFFFFF"><%=bean.getBeginDate() %>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">结束日期</td>
    <td bgcolor="#FFFFFF"><%=bean.getEndDate() %>    </td>
  </tr>
  <tr class=trwhite>
    <td bgcolor="#FFFFFF" class=trcolor>值班开始时间</td>
    <td bgcolor="#FFFFFF"><%=bean.getOrderlyBeginTime() %>    </td>
  </tr>
  <tr class=trwhite>
    <td bgcolor="#FFFFFF" class=trcolor>值班结束时间</td>
    <td bgcolor="#FFFFFF"><%=bean.getOrderlyEndTime() %>    </td>
  </tr>
  <tr class=trwhite>
    <td bgcolor="#FFFFFF" class=trcolor>值班时间间隔</td>
    <td bgcolor="#FFFFFF"><%=bean.getError() %>    </td>
  </tr>
  <tr bgcolor="#FFFFFF" class=trwhite>
    <td rowspan="2" align="center" class=trcolor>领导批示</td>
    <td height="63" colspan="2">&nbsp;</td>
  </tr>
</table>
<br>
<br>
<template:formSubmit>
  <td width="85">
    <html:button property="action" styleClass="button" onclick="toExpotr()">Excel报表</html:button>
  </td>
  <td width="85">
  	<input type="button" class="button" value="返回" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"/>
  </td>
</template:formSubmit>
<br>
<br>
