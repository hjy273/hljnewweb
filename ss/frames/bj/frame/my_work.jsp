<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="com.cabletech.commons.web.WebAppUtils"%>
<%
com.cabletech.commons.config.GisConInfo gisConfig=com.cabletech.commons.config.GisConInfo.newInstance();
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link type="text/css" rel="stylesheet" href="../css/load.css" />
<style type="text/css">
<!--
.Frame { width: 98%; margin-left: 10px;margin-right: 10px; }
.Frame ul {  margin:auto; }
.Frame ul li { float: left; width: 110px; display: inline; }
.work_content{ width:80%;  height:90px;  border:#F0EDC6 solid 1px; background-color:#FAFAF2; margin:10px;}
.text{ width:100%; margin:auto; font-size:14px; color:#636306; text-align:center; margin-top:6px;cursor: pointer;}
.work_content ul{ margin:auto; height:60px;}
.work_content ul li{ margin:auto; list-style:none; overflow:hidden; float:left; width:110px; display:inline;}
.text_default{text-align:center;font-size:12px;	color:#636306;	text-decoration: none;}
.text_red{text-align:center;color:red ;font-size:12px;text-decoration: none;}
.text a{color:#636306;text-decoration: none;	}
.img{ width:48px; height:48px; margin:0px auto; margin-top:3px;}
legend{ color:#3366ff; font-size:14px;font-weight: bold;}
-->
</style>
<script type="text/javascript" language="javascript"
	src="${ctx}/js/prototype.js"></script>
<script type="text/javascript">
		toUrl = function(oneLevelId, twoLevelId, name) {
			url = "${ctx}/frames/bj/frame/work_main.jsp?mainmodulemenu_id=" + oneLevelId + "&submenu_id="
				+ twoLevelId+"&name=" + name ;
			self.location.replace(url);
		};
		togisUrl = function(url){
			self.location.replace(url);
		};
	 	//ajax加载待办工单
		function getUndoSheet(){
		    $("divundosheet").innerHTML = "数据加载中........<img src='${ctx}/images/onloading.gif' border='0'/>";
		     var pars = Math.random();
		     new Ajax.Request('${ctx}/frames/wait_process_work.do?method=showWaitProcessWorkNum&isdowork=0',
		      {
		        method:"post",
		        parameters:pars,
		        evalScripts:true,
		        onSuccess: function(transport){
		          var response = transport.responseText || "加载数据为空！";
		          $("divundosheet").innerHTML = response;
		        },
		        onFailure: function(){  $("divundosheet").innerHTML = "加载失败。。。。。"; }
		      });
		 };
		 showTroubleQuota=function(divId,guideType){
			var url="${ctx}/troubleQuotaAction.do?method=showTroubleQuotaInfo";
			var queryString="guideType="+guideType;
			var actionUrl=url+"&&"+queryString+"&&rnd="+Math.random();
			new Ajax.Request(actionUrl,{
				method:"get",
				evalScripts:true,
		        onSuccess: function(transport){
		          var response = transport.responseText;
		          $(divId).innerHTML = response;
		        }
			});
		 };
		 
		 function openwinYG() {
			var url="${ctx}/wap/mainGuideBarChartAction.do?method=generateChart&width=520&heigth=280&CategoryType=10";
			window.open (url, "一干指标值", "toolbar=no, menubar=no,height=320,width=620, scrollbars=yes, resizable=yes, location=no, status=no");
			}
		function openwinCity(CategoryType) {
			var url="${ctx}/wap/normGuideBarChartAction.do?method=generateChart&width=520&heigth=280&CategoryType="+CategoryType;
			window.open (url, "城域网指标值", "toolbar=no, menubar=no, height=320,width=620,scrollbars=yes, resizable=yes, location=no, status=no");
			}
		function openwinCityYear(CategoryType) {
			var url="${ctx}/wap/yearGuideBarChartAction.do?method=generateChart&width=520&heigth=280&CategoryType="+CategoryType;
			window.open (url, "城域网年指标值", "toolbar=no,height=320,width=620, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no");
			}	
		function openwinYiganFiveYear() {
			var url="${ctx}/wap/yearGuideBarChartAction.do?method=generateChart&width=520&heigth=280&CategoryType=100";
			window.open (url, "一干指标值", "toolbar=no, menubar=no,height=320,width=620, scrollbars=yes, resizable=yes, location=no, status=no");
			}								
		</script>
<title>Main</title>
</head>
<body onload="getUndoSheet()">
	<table width="99%" align="center" cellpadding="0" cellspacing="1">
		<tr>
			<!-- 左边 -->
			<td width="280" valign="top">
				<!-- 常用链接 -->
				<div class="Framework">
					<div class="title_bg">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td style="font-size: 12px;"><font
									style="font-weight: bold; color: green">一干指标值&nbsp; <!-- <a style="color:#000000;" onclick="openwinYiganFiveYear();" href="#">年1</a> -->
								</font>
								<!--<img src="../images/link.jpg" /> --></td>
								<td>&nbsp;</td>
								<td align="right"><img src="../images/right.jpg" /></td>
							</tr>
							<tr>
								<td></td>
							</tr>
						</table>
					</div>
					<!--div class="link" id="mainTroubleQuotaTd"><img src="${ctx}/wap/mainGuideBarChartAction.do?method=generateChart&width=380&heigth=180" width="250px" height="150px"/></div-->
					<div class="link" id="mainTroubleQuotaTd"></div>
					<span class="l4"></span> <span class="l3"></span> <span class="l2"></span>
					<span class="l1"></span>
				</div> <!-- 常用链接 --> <br />
				<div class="Framework">
					<div class="title_bg">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td style="font-size: 12px;"><font
									style="font-weight: bold; color: green">城域网指标值&nbsp; <!-- <a style="color:#000000;" onclick="openwinCityYearFl21();" href="#">年1</a>
								<a style="color:#000000;" onclick="openwinCityYearFl22();" href="#">年2</a>
								<a style="color:#000000;" onclick="openwinCityYearFl23();" href="#">年3</a> -->
								</font>
								<!--<img src="../images/link.jpg" /> --></td>
								<td>&nbsp;</td>
								<td align="right"><img src="../images/right.jpg" /></td>
							</tr>
							<tr>
								<td id=""></td>
							</tr>
						</table>
					</div>
					<!--div class="link" id="cityTroubleQuotaTd"><img src="${ctx}/wap/normGuideBarChartAction.do?method=generateChart&width=340&heigth=330" width="250px" height="280px"/></div-->
					<div class="link" id="cityTroubleQuotaTd"></div>
					<span class="l4"></span> <span class="l3"></span> <span class="l2"></span>
					<span class="l1"></span>
				</div></td>
			<!-- 中间 -->
			<td width="1"></td>
			<td valign="top">
				<div class="Framework">
					<div class="title_bg">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td align="left"><img src="../images/dbgz.jpg" /></td>
								<td>&nbsp;</td>
								<td></td>
							</tr>
						</table>
					</div>
					<div class="Announcement" id="divundosheet">
						
					</div>
					<br>
					<span class="l4"></span> <span class="l3"></span> <span class="l2"></span>
					<span class="l1"></span>
				</div></td>
			<!-- 右边 -->
			<td width="1"></td>
			<td width="280" valign="top">
				<div class="Framework">
					<div class="xjjc">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><a
									href="/<%=gisConfig.getServerapp() %>/<%=gisConfig.getGisUrl() %>${LOGIN_USER.userID}&init=yes&regionID=${LOGIN_USER.regionid}"
									target="_blank"><img src="../images/xjjc_text.jpg"
										border="0" />
								</a></td>
							</tr>
						</table>
					</div>
				</div>

				<div class="Framework">
					<div class="title_bg">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><img src="../images/Online.jpg" /></td>

								<td class="td"></td>
								<td align="right"><img src="../images/right_arow.gif"
									style="cursor: pointer;" onclick="javascript:reload()" alt="刷新" />
								</td>
							</tr>
						</table>
					</div>
					<span class="my_mail">
						<link rel='stylesheet' type='text/css'
							href='${ctx}/js/extjs/resources/css/ext-all.css' /> <script
							type='text/javascript'
							src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script> <script
							type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
						<script type="text/javascript">
						Ext.BLANK_IMAGE_URL = '${ctx}/js/extjs/resources/images/default/tree/s.gif';
					</script> <script type="text/javascript">
						var tree;
						Ext.onReady(function(){
						//创建根节点
							var root = new Ext.tree.AsyncTreeNode({
								text : '当前在线巡检员(<%=WebAppUtils.online_period%>分钟内)',
								expanded : true,//设置根节点默认是展开的
								id : 'root'
							});
						//创建Tree面板组件
							tree = new Ext.tree.TreePanel({
						//设置异步树节点的数据加载器
								loader : new Ext.tree.TreeLoader({
									baseAttrs : {//设置子节点的基本属性
										cust : 'client'
									},
									dataUrl : '${ctx}/onLineAction.do?method=loadTree'
								}),
								width : 280,
								height: 400,
								border : false,
								autoScroll : true,
								applyTo : 'online',
								root : root
							});
									
						});
						reload=function(){
							tree.root.reload();
						}
					</script>
						<div id="online"></div> </span> <span class="l4"></span> <span class="l3"></span>
					<span class="l2"></span> <span class="l1"></span>
				</div></td>
		</tr>
	</table>
	<script type="text/javascript">
		showTroubleQuota("mainTroubleQuotaTd","1"); //文本显示一干指标值
		showTroubleQuota("cityTroubleQuotaTd","0"); //文本显示城域网指标值
		</script>
</body>
</html>

