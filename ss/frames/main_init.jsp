<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo"%>
<%@page import="com.cabletech.commons.config.*"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.commons.beanutils.*" %>
<%@page import="com.cabletech.sysmanage.domainobjects.*"%>
<%
	UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
%>
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK">
<META NAME="DESCRIPTION" CONTENT="移动传输线路巡检管理系统">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Window-target" CONTENT="_top">

<!--  link href="../css/style.css" rel="stylesheet" type="text/css"-->
<!-- start -->
<link href="../css/main.css" rel="stylesheet" type="text/css" media="all"/>
    <script language="JavaScript" type="text/javascript" src="../js/nifty.js"></script>
    <script language="JavaScript" type="text/javascript">
       window.onload=function(){
            if(!NiftyCheck())
                return;
            Rounded("blockquote","tr bl","#ECF1F9","#CDFFAA","smooth border #88D84F");
            Rounded("div#outer-header", "all", "white", "#818EBD", "smooth border #434F7C");
            Rounded("div#footer", "all", "white", "#818EBD", "smooth border #434F7C");
        }
    </script>
   
	<script type="text/javascript">
		// Dojo configuration
		djConfig = {
			baseRelativePath: "../js/src",
			isDebug: true,
			bindEncoding: "UTF-8",
			debugAtAllCosts: true // not needed, but allows the Venkman debugger to work with the            includes
		};
	</script>
	
	<script type="text/javascript" src="../js/dojo.js"></script>
	<script type="text/javascript" src="../js/dojoRequire.js"></script>
    <script type="text/javascript" src="../js/optiontransferselect.js"></script>
	
    <script type="text/javascript">
	function selectOptionsExceptSome( type, ptn) {
	    var test = compile(ptn);
	    var objTargetElement = document.getElementById("tabMenuForm_favouriteTabMenu");
	    for (var i = 0; i < objTargetElement.length; i++) {
	        var opt = objTargetElement.options[i];
	        if ((type == 'key' && !test(opt.value)) ||
	              (type == 'text' && !test(opt.text))) {
	            opt.selected = true;
	        } else {
	            opt.selected = false;
	        }    
	    }
	    return false;
	}
    </script>
    <script>
    dojo.require("dojo.io.*");
    dojo.require("dojo.event.*");
    dojo.require("dojo.widget.*");
	dojo.require("dojo.widget.Button");
 	
</script> 
	<!-- end -->
<link href="../css/tab.css" rel="stylesheet" type="text/css">
<script LANGUAGE="JavaScript">
//sets the default display to tab 1
function init(){
tabContents.innerHTML = t1Contents.innerHTML;
}
//this is the tab switching function
var currentTab;
var tabBase;
var firstFlag = true;
function changeTabs(){
	if(firstFlag == true){
		currentTab = t1;
		tabBase = t1base;
		firstFlag = false;
	}
	if(window.event.srcElement.className == "tab"){
		currentTab.className = "tab";
		tabBase.style.backgroundColor = "white";
		currentTab = window.event.srcElement;
		tabBaseID = currentTab.id + "base";
		tabContentID = currentTab.id + "Contents";
		tabBase = document.all(tabBaseID);
		tabContent = document.all(tabContentID);
		currentTab.className = "selTab";
		tabBase.style.backgroundColor = "";
		tabContents.innerHTML = tabContent.innerHTML;
	}
}
</script>
<!--巡检人员树形结构-->
<link type="text/css" rel="stylesheet" href="../css/css4cnltreemenu.css" />
<script type="text/javascript" src="../js/js4cnltreemenu.js"></script>
</head>

<body BGCOLOR="#EAE9E4" onclick="changeTabs()" style="width:750px;overflow:scroll;overflow-x:hidden" onload="init()">
<div STYLE="position:absolute; top:5; height:350; width:750px; left:5; right:0
 border:none thin gray">
<table width="100%" CELLPADDING="0" CELLSPACING="0" bgcolor="#EAE9E4" STYLE="width:745; height:350">
<tr style="cursor:default">
<td ID="t1" CLASS="selTab" HEIGHT="25">最新信息</td>
<%
List favtabmenu = (List)session.getAttribute("tabmenu");
TabMenu tabmenu = null;
for(int i=0;i<favtabmenu.size();i++){
	tabmenu = (TabMenu)favtabmenu.get(i);
	out.print("<td ID=\"t"+(i+2)+"\" CLASS=\"tab\">"+tabmenu.getTabname()+"</td>\n");	
}
%>

<!-- 
<td ID="t2" CLASS="tab" >待办工作</td>
<td ID="t3" CLASS="tab">计划任务</td>
<td ID="t4" CLASS="tab">短信调度</td>
<td ID="t5" CLASS="tab">数据采集</td>
<td ID="t9" CLASS="tab">设&nbsp;&nbsp;&nbsp;置</td>
 -->

<td  width="240">&nbsp;&nbsp;&nbsp;</td>
</tr><tr>
<td ID="t1base" STYLE="height:2; border-left:solid thin white"></td>
<%
TabMenu tabmenubase = null;
for(int i=0;i<favtabmenu.size();i++){
	tabmenu = (TabMenu)favtabmenu.get(i);
	out.print("<td ID=\"t"+(i+2)+"base\" STYLE=\"height:2; background-color:white\"></td>\n");
}
 %>
<td ID="t9base" STYLE="height:2; background-color:white"></td>
<td ID="t7base" STYLE="height:2; background-color:white; border-right:solid thin white"></td>
</tr>
<tr>
<td HEIGHT="*" COLSPAN="7" ID="tabContents"
STYLE="border-left:solid thin white;border-bottom:solid thin white;border-right:
solid thin white">
正在加载信息....</td></tr></table>
</div>

<div CLASS="conts" style="width:600px" ID="t1Contents">
<script>
function open_notify(NOTICE_ID,FORMAT)
{
 URL="${ctx}/NoticeAction.do?method=showNotice&id="+NOTICE_ID;
 myleft=(screen.availWidth-650)/2;
 mytop=100
 mywidth=650;
 myheight=500;
 if(FORMAT=="1")
 {
    myleft=0;
    mytop=0
    mywidth=screen.availWidth-10;
    myheight=screen.availHeight-40;
 }
 window.open(URL,"read_news","height="+myheight+",width="+mywidth+",status=1,resizable=no,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft+",resizable=yes");
}
</script>
<fieldset style="height:100px"><legend><a href="${ctx}/specialplan.do?method=addFrom&ptype=001&isApply=true&pName=2009年元旦保障计划">公告信息</a></legend>
<ul  style="margin:5 0 0 40px;">
<%=session.getAttribute("noticeli") %>
<div align="right"><a href="${ctx}/NoticeAction.do?method=getAllNotice" >更多</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
</ul>
</fieldset>
<fieldset style="height:100px"><legend><a href="#">在线巡检人员(2小时内)</a></legend>
<div class="CNLTreeMenu" id="CNLTreeMenu3">
<ul>
  <%=session.getAttribute("onlineman")%>
</ul>
<script type="text/javascript">
<!--
var MyCNLTreeMenu=new CNLTreeMenu("CNLTreeMenu3","li");
MyCNLTreeMenu.InitCss("Opened","Closed","Child","../images/s.gif");
-->
</script>
</div>
</fieldset>
</div>



<div CLASS="conts" style="width:600px" ID="t2Contents">
<fieldset style="height:100px"><legend><a href="#">正在执行计划</a></legend>
<table width="100%" border="1" cellpadding="1" cellspacing="0">
<tr style="border-bottom:thin; ">
<th width="30%">计划名称</th><th width="20%">巡检组</th><th width="15%">开始时间</th><th width="15%">结束时间</th><th width="20%">工作情况</th>
</tr>
<%
	List pl = (List)session.getAttribute("ProgressPlan");
	if(pl != null){
	for(int i=0;i<pl.size();i++){
		Map map = (Map)pl.get(i);
		String percent = map.get("percentage").toString();
		percent = percent.substring(0,percent.length()-1);
%>
<tr>
<td><%=map.get("planname") %></td><td><%=map.get("patrolname") %></td><td><%=map.get("begindate") %></td><td><%=map.get("enddate") %></td><td><img src='../images/percent.jpg' width='<%=percent %>' height='15'><%= map.get("percentage")%></td>
</tr>
<%
	}
	}
 %>
</table>
</fieldset>
<fieldset style="height:100px"><legend><a href="#">已经完成计划</a></legend>
<table width="100%" border="1" cellpadding="1" cellspacing="0" >
<tr style="border-bottom:thin; ">
<th width="30%">计划名称</th><th width="20%">巡检组</th><th width="15%">开始时间</th><th width="15%">结束时间</th><th width="20%">工作情况</th>
</tr>
<%
	List fl = (List)session.getAttribute("FulfillPlan");
	if(fl != null){
	for(int i=0;i<fl.size();i++){
		DynaBean bean = (DynaBean)fl.get(i);
	    String patrolp = bean.get("patrolp").toString();
	    patrolp = patrolp.substring(0,patrolp.length()-1);
%><tr>
	<td><%=bean.get("planname") %></td><td><%=bean.get("patrolname") %></td><td><%=bean.get("begindate") %></td><td><%=bean.get("enddate") %></td><td><img src='../images/percent.jpg' width='<%=patrolp %>' height='15'><%=bean.get("patrolp") %></td>
	</tr>
<%
	}
	}
 %>
</table>
</fieldset>
<fieldset style="height:100px"><legend><a href="#">即将开始计划</a></legend>
<table width="100%" border="1" cellpadding="1" cellspacing="0">
<tr style="border-bottom:thin; ">
<th width="30%">计划名称</th><th width="20%">巡检组</th><th width="15%">开始时间</th><th width="15%">结束时间</th><th width="20%">工作情况</th>
</tr>
<%
	List sl =(List)session.getAttribute("ShallStartPlan");
	if(sl != null){
	for(int i=0;i<sl.size();i++){
		DynaBean bean = (DynaBean)sl.get(i);
%><tr>
	<td><%=bean.get("planname") %></td><td><%=bean.get("patrolname") %></td><td><%=bean.get("begindate") %></td><td><%=bean.get("enddate") %></td><td><img src='../images/percent.jpg' width='0' height='15'>0%</td>
	</tr>
<%
	}
	}
 %>
</table>
</fieldset>

</div>

<div CLASS="conts" style="width:600px" ID="t3Contents">
<p align="center">Tab3的内容 </p>
</div>

<div CLASS="conts" style="width:600px" ID="t4Contents">
<p align="center">Tab4的内容 </p>
</div>

<div CLASS="conts" style="width:600px" ID="t5Contents">
<p align="center">Tab5的内容 </p>
</div>

<div CLASS="conts" style="width:600px" ID="t9Contents">

<p align="center">
<br>
<div align="center" style="color:red">修改设置后，重新登陆方能立即生效</div>
<form id="tabMenuForm" name="tabMenuForm" action="${ctx}/SetupDesktopAction.do?method=setupDeskTop" method="post" target="hiddenInfoFrame" onsubmit="return false">
<table class="wwFormTable" align="center">
    <tr>
    <td>
		<table border="0">
		<tr>
			<td>
			<label for="leftTitle">已选择的项目</label><br />
			<select name="favouriteTabMenu" 
				size="15" 
				id="tabMenuForm_favouriteTabMenu" 
				multiple="multiple">
    			<option value="-1">最新信息</option>
    			<%=session.getAttribute("favstr") %>
    			<!-- 
    			<option value="popeye">计划任务</option>
    			<option value="mockeyMouse">短信调度</option> -->
			</select>
			<input type="button"  onclick="moveOptionUp(document.getElementById('tabMenuForm_favouriteTabMenu'), 'key', '-1');" value="^"/>
			<input name="button" type="button" onclick="moveOptionDown(document.getElementById('tabMenuForm_favouriteTabMenu'), 'key', '-1');" value="v"/>
			</td>
			<td valign="middle" align="center">
			<input type="button"  value="&lt;-" onclick="moveSelectedOptions(document.getElementById('tabMenuForm_notFavouriteTabMenu'), document.getElementById('tabMenuForm_favouriteTabMenu'), false, '-1', '')" /><br /><br />
			<input type="button" value="-&gt;" onclick="moveSelectedOptions(document.getElementById('tabMenuForm_favouriteTabMenu'), document.getElementById('tabMenuForm_notFavouriteTabMenu'), false, '-1', '')" /><br /><br />
			<input type="button" value="&lt;&lt;--" onclick="moveAllOptions(document.getElementById('tabMenuForm_notFavouriteTabMenu'), document.getElementById('tabMenuForm_favouriteTabMenu'), false, '-1', '')" /><br /><br />
			<input type="button" value="--&gt;&gt;" onclick="moveAllOptions(document.getElementById('tabMenuForm_favouriteTabMenu'), document.getElementById('tabMenuForm_notFavouriteTabMenu'), false, '-1', '')" /><br /><br />	
			<input type="button"  value="&lt;选择&gt;" onclick="selectAllOptionsExceptSome(document.getElementById('tabMenuForm_favouriteTabMenu'), 'key', '-1');selectAllOptionsExceptSome(document.getElementById('tabMenuForm_notFavouriteTabMenu'), 'key', '-1');" /><br /><br />
			</td>
			<td>
			<label for="rightTitle">未选择项目</label><br />
			<select name="notFavouriteTabMenu"
				size="15"		
				multiple="multiple"
				id="tabMenuForm_notFavouriteTabMenu"
			>
    			<%=session.getAttribute("nonfavstr") %>
				 <!--  <option value="donaldDuck">待办事宜</option>
				<option value="atomicAnt">数据采集</option> -->
			</select>
			<input type="button" onclick="moveOptionDown(document.getElementById('tabMenuForm_notFavouriteTabMenu'), 'key', '-1');" value="v"/>
			<input type="button" onclick="moveOptionUp(document.getElementById('tabMenuForm_notFavouriteTabMenu'), 'key', '-1');" value="^"/>
			</td>
		</tr>
		</table>

	</td>
	</tr>
    <tr>
    <td colspan="2"><div align="center"><input type="submit"  id="tabMenuForm_0" onclick="selectOptionsExceptSome('key', '-1');" value="保存设置"/></div>
	</td>
	</tr>
</table>
</form>
 </p>
</div>
<script type="text/javascript">
dojo.require("dojo.event.*");
	var containingForm = document.getElementById("tabMenuForm");
		dojo.event.connect(containingForm, "onsubmit", 
			function(evt) {
				var selectObj = document.getElementById("tabMenuForm_favouriteTabMenu");
					selectAllOptionsExceptSome(selectObj, "key", "-1");
			});
</script>

</BODY>
</html>

<iframe name="hiddenInfoFrame" style="display:none">
</iframe>
