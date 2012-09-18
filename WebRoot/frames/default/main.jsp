<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>首页</title>
		<link href="${ctx}/frames/default/css/style.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/js/jQuery/layout/jquery.layout.css"
			rel="stylesheet" type="text/css" />
		<link
			href="${ctx }/js/jQuery/jqueryui/css/redmond/jquery-ui-1.8.16.custom.css"
			type="text/css" rel="stylesheet" />
		<link href="${ctx }/js/jQuery/ztree/css/zTreeStyle/zTreeStyle.css"
			type="text/css" rel="stylesheet" />
		<script type="text/javascript"
			src="${ctx}/js/jQuery/jquery-1.7.1.min.js"></script>
		<script type="text/javascript"
			src="${ctx}/js/jQuery/layout/jquery.layout-latest.js"></script>
		<script type="text/javascript"
			src="${ctx}/js/jQuery/jqueryui/jquery-ui-1.8.16.custom.min.js"></script>
		<script type="text/javascript"
			src="${ctx}/js/jQuery/jqueryui/zh/jquery.ui.datepicker-zh-CN.js"></script>
		<script type="text/javascript"
			src="${ctx}/js/jQuery/ztree/jquery.ztree.core-3.0.js"></script>
		<script type="text/javascript" src="${ctx}/js/highchart/highcharts.js"></script>
		<script type="text/javascript">
	//简单在线人员树样式	
var setting = {
				view: {
					showLine: false,
					showIcon: true
				},
				data: {
						simpleData: {
						enable: true
					}
				}
			};
jQuery(window).load(function () {
	jQuery('body').css('visibility','visible');// 在这里写你的代码...
	setTimeout(function(){
		jQuery('#loading-mask').remove();
	},50);
	});	
	jQuery(function(){
		     //左侧日历
			$('#div_agenda').datepicker({
			});
			bodyLayout=$('body').layout({ applyDefaultStyles: true });
			//信息tab页
			jQuery('#tab_notic').tabs();
			//图表tab页
			jQuery('#tab_chart').tabs();
			 //转换成js对象
			jsonobj=$.parseJSON('${sessionScope.onlinemanJson}');
			zTree=jQuery.fn.zTree.init($("#stafftree"), setting, jsonobj);
			zTree.expandAll(true);
			var olnodes=zTree.getNodesByParamFuzzy("STATUS", "1", null);
			var nolnodes=zTree.getNodesByParamFuzzy("STATUS", "0", null);
			if(olnodes){
			for(i=0;i<olnodes.length;i++){
				olnodes[i].icon='${ctx}/frames/default/image/zx.png';
				zTree.updateNode(olnodes[i]);
			}
			}
			if(nolnodes){
			for(i=0;i<nolnodes.length;i++){
				nolnodes[i].icon='${ctx}/frames/default/image/fzx.png';
				zTree.updateNode(nolnodes[i]);
			}
			}
			
			//根据类型寻找代维公司
			var tn=zTree.getNodesByParam('OBJTYPE','CONTRACTOR');
			//如果能找到代维公司
			if(tn){
			for(i=0;i<tn.length;i++)
			{
			//代维人员数量
			allxjzcount=0
			//代维在线人员数量
			zxrycount=0
			//代维公司巡检组
			tcn=tn[i].childs;
			//是否有巡检组
			if(tcn){
			for(j=0;j<tcn.length;j++){
			if(tcn[j].childs){
			allxjzcount+=tcn[j].childs.length;
			zxrycount+=zTree.getNodesByParam('STATUS','1',tcn[j]).length;
			}
			}
			//重命名代维公司名称
			tn[i].TEXT = tn[i].TEXT+'('+zxrycount+'/'+allxjzcount+')';
			//更新代维节点
			zTree.updateNode(tn[i]);
			}
			}
			}
				
			//获取计划图形
			getplanchart();
			getonlinechart(${sessionScope.onlineman},${sessionScope.nolineman},${sessionScope.nomanrate},${sessionScope.olmanrate});
	    });
	    jQuery(function(){
	    	$.get("/WebApp/frames/wait_handle_work.do?method=getWaitHandleWorkHtml", function (data,success){
	    		document.getElementById("task_list_line").innerHTML=data;
	    	})
	    });
	   
	   var FORMAT = "2";
	   
	//查看公告
	function viewNotice(NOTICE_ID){
		URL="${ctx}/NoticeAction.do?method=showNotice&id="+NOTICE_ID+"&preview=true";
 		myleft=(screen.availWidth-650)/2;
 		mytop=100
 		mywidth=650;
 		myheight=500;
 		if(FORMAT=="1"){
    		myleft=0;
    		mytop=0
    		mywidth=screen.availWidth-10;
    		myheight=screen.availHeight-40;
 		}
 		window.open(URL,"read_news","height="+myheight+",width="+mywidth+",status=1,resizable=no,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft+",resizable=yes");
	}    
	
	//查看公告列表
	function listNotice(){
		var URL="${ctx}/NoticeAction.do?method=queryAllIssueNotice";
		window.location.href = URL;
	}
	//获取计划巡检数
	function getplanchart() {
		columnchart = new Highcharts.Chart({
			chart: {
				renderTo: 'chart-1',
				defaultSeriesType: 'column'
			},
			title: {
				text: '代维巡检情况'
			},
			xAxis: {
				categories: [${sessionScope.chartname}]
			},
			yAxis: {
				min: 0,
				title: {
					text: '计划巡检率（%）'
				},
				stackLabels: {
					enabled: true,
					style: {
						fontWeight: 'bold',
						color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
					}
				}
			},
	         credits: {
				enabled: false
			},
			legend: {
				layout: 'vertical',
				backgroundColor: '#FFFFFF',
				align: 'left',
				verticalAlign: 'top',
				x: 100,
				y: 70,
				floating: true,
				shadow: true

			},
			tooltip: {
				formatter: function() {
					return ''+
						this.series.name  +': '+ this.y + '%';

				}
			},
			plotOptions: {
				column: {
					column: {
						pointPadding: 0.2,
						borderWidth: 0
					}
				}
			},
			series: [
		  				<c:forEach var="item" items="${sessionScope.businesstype}">
	                    	{
	             				name: '${item.key}',
	              			data: [
								<c:forEach var="itemlist" items="${sessionScope.list}">
									<c:if test="${itemlist.business_type==item.key}">
									${itemlist.rate},
									</c:if>
								</c:forEach>
	      					]},
						</c:forEach>
					]
		});	
	}
	// 获取代维人员
	function getonlinechart(onlineman,nolineman, norate,yesrate) {
		var	chart = new Highcharts.Chart({
			chart: {
				renderTo: 'chart-2',
				plotBackgroundColor: null,
				plotBorderWidth: null,
				plotShadow: false
			},
			title: {
				text: '代维单位人员统计'
			},
			xAxis: {
			//	categories: ['在线人员', '非在线人员']
				categories: [${sessionScope.name}]
			},
			yAxis: {
				min: 0,
				title: {
					text: '代维单位人员数量'
				},
				stackLabels: {
					enabled: true,
					style: {
						fontWeight: 'bold',
						color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
					}
				}
			},
			tooltip: {
				formatter: function() {
					var s;
					if (this.point.name) { // the pie chart
						s = ''+
							this.point.name +': '+ this.y +'人';
					} else {
						s = ''+
							this.series.name  +': '+ this.y+'人';
					}
					return s;
				}
			},
	         credits: {
				enabled: false
			},
		    series: [{
				type: 'column',
				name: '代维单位人员数量',
		    	data: [${sessionScope.onlineman}]
			}
			]
		});

	}
	
</script>
	</head>
	<body >
		<div class="ui-layout-center">
			<div class="main_left">
				<div id="main_left_top" class="main_left_top">
					<div class="main_div_head">
						<table style="border: 0px; width: 100%; padding-left: 3px;"
							cellpadding="0" cellspacing="0">
							<tr>
								<td style="width: 20px">
									<img src="${ctx }/frames/default/image/hy_icon.png" />
								</td>
								<td>
									会议日程
								</td>
							</tr>
						</table>
					</div>
					<div id="div_agenda" style="width: 99%; height: 98%;"></div>
				</div>
				<div id="main_left_bottom" class="main_left_bottom">
					<div id="tab_notic" style="width: 99%; height: 99%">
						<ul>
							<c:forEach var="item" items="${sessionScope.informationtype}">
								<li>
									<a href="#tabs-${item.CODEVALUE}">${item.LABLE}</a>
								</li>
							</c:forEach>
							<div id="main_left_bottom_more"
								style="margin-top: 15px; text-align: right;">
								<a href="javascript:listNotice();"
									style="text-decoration: none;">更多...</a>
							</div>
						</ul>
						<c:forEach var="item" items="${sessionScope.informationtype}">
							<div id="tabs-${item.CODEVALUE}">
								<c:forEach var="itemtype"
									items="${sessionScope.informationList}">
									<c:if test="${item.CODEVALUE==itemtype.TYPE }">
										<li class="info_li" onclick="viewNotice('${itemtype.ID}')">
											${itemtype.ISSUEDATE} ${itemtype.TITLE}
										</li>
									</c:if>
								</c:forEach>
							</div>
						</c:forEach>

					</div>
				</div>
			</div>
			<div class="main_center">
				<div id="main_center_top" class="main_center_top">
					<div id="tab_chart" style="width: 99%; height: 96%">
						<ul>
							<li>
								<a href="#chart-1">巡检情况</a>
							</li>
							<li>
								<a href="#chart-2">代维人员情况</a>
							</li>
						</ul>
						<div id="chart-1"
							style="height: 260px; width: 700px; margin: 0 auto"></div>
						<div id="chart-2"
							style="height: 260px; width: 720px; margin: 0 auto"></div>
					</div>
				</div>
				<div id="main_center_bottom" class="main_center_bottom">
					<div class="main_div_head">
						<table style="border: 0px; width: 100%; padding-left: 3px;"
							cellpadding="0" cellspacing="0">
							<tr>
								<td style="width: 20px">
									<img src="${ctx }/frames/default/image/dbrw_icon.png" />
								</td>
								<td>
									待办任务
								</td>
							</tr>
						</table>
					</div>


					<div id="task_list" class="task_list">
						<c:forEach var="oneBusinessTypeWaitHandledTasks"
							items="${sessionScope.taskList}" varStatus="loop"> 
							
							<ul class="task_listrow${loop.count%2}">
								<li class="task_li_item">
									<ul style="text-align: left;padding-left: 0px">
										<li class="task_li_item">
											${oneBusinessTypeWaitHandledTasks.value.business_type_name}
										</li>
									</ul>
								</li>
								<li class="task_li_subItem">
									<c:forEach var="oneBusinessTypeOneWorkflowWaitHandledTasks"
										items="${oneBusinessTypeWaitHandledTasks.value }">
										<c:if
											test="${oneBusinessTypeOneWorkflowWaitHandledTasks.key!='business_type_name' }">
									<a
												href="${ctx }${oneBusinessTypeOneWorkflowWaitHandledTasks.value.url}" style="TEXT-DECORATION: none">
												${oneBusinessTypeOneWorkflowWaitHandledTasks.key}&nbsp;&nbsp;. . . . . . . . . . . . . . . . . . . . . . . . .&nbsp;&nbsp;
											
												${oneBusinessTypeOneWorkflowWaitHandledTasks.value.wait_handled_number}
											
									
									条待办
									</a>
									</c:if>
									</c:forEach>
								</li>
							</ul>
						</c:forEach>
						
						<br />
						<span id="task_list_line"></span>
					</div>
					
				</div>
			</div>
		</div>
		<div class="ui-layout-east panel_boder" style="overflow-x: hidden;"> 
			<div class="main_div_head" >
				<table style="border: 0px; width: 100%; padding-left: 3px;"
					cellpadding="0" cellspacing="0">
					<tr>
						<td style="width: 20px">
							<img src="${ctx }/frames/default/image/zxxj_icon.png" />
						</td>
						<td>
							在线人员
						</td>
					</tr>
				</table>
			</div>
			<div id="stafftree" class="ztree"></div>
		</div>
	</body>
</html>
