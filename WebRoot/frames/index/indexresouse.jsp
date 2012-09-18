<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="com.cabletech.base.SysConstant"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>巡检系统</title>
		<style type="text/css">
</style>
		<link href="${ctx}/css/divstyle.css" rel="stylesheet" type="text/css">
		<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
		<script src="${ctx}/js/extjs/ux/ux-all-debug.js"
			type="text/javascript"></script>
		<script src="${ctx}/js/extjs/ux/RowLayout.js" type="text/javascript"></script>
		<script type='text/javascript' src='${ctx}/js/highchart/highcharts.js'></script>
		<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
		<script type="text/javascript">
	Ext.onReady(function() {
		//代维单位维护资源统计
		var maintainresouse_group1 = new Ext.form.FieldSet({
			title : '基站 直放站 资源统计',
			collapsible : true,
			contentEl : Ext.get('maintainresouse_group1'),
			renderTo: 'fieldSet1'
		});
		var maintainresouse_group2 = new Ext.form.FieldSet({
			title : '铁塔 直放站 资源统计',
			collapsible : true,
			contentEl : Ext.get('maintainresouse_group2'),
			renderTo: 'fieldSet3'
		});
		//信息搜索
		var searchpanel = new Ext.form.FieldSet({
			title : '信息搜索',
			layout : 'column',
			collapsible : true,
			contentEl : Ext.get('searchpanel'),
			renderTo: 'fieldSet2'
		});
		//基站级别
		var  basestation_level = new Appcombox({
			width : 150,
			hiddenName : 'bsLevel',
			hiddenId : 'bsLevel',
			emptyText : '请选择',
			dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=basestation_level',
	   		dataCode : 'CODEVALUE',
	   		dataText : 'LABLE',
			allowBlank:true,
			renderTo: 'basestaion_basestation_level'
		});
	
//基站图表
basestationchart();
//铁塔图表
outdoorfacilitieschart();
//直放站图表
repeaterchart();
//室内图表
indooroverridechart();
//基站县区
regionBaseStation();
//铁塔县区
regionOutdoor();
//直放站县区
regionRepeater();
//室内区县县区
regionIndoor();
//基站搜索
Ext.get("BaseStationForm").setStyle("display","");
//铁塔搜索
Ext.get("outdoorForm").setStyle("display","");
//直放站搜索
Ext.get("repeaterForm").setStyle("display","");
//室内搜索
Ext.get("indoorForm").setStyle("display","");
})
	
//基站
function basestationchart(){
	var chart = new Highcharts.Chart({
					chart: {
						renderTo: 'pic1',
						plotBackgroundColor: null,
						plotBorderWidth: null,
						plotShadow: false,
						height:200
					},
					title: {
						text: '基站'
					},
					tooltip: {
						formatter: function() {
							return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
						}
					},
					plotOptions: {
						pie: {
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: true,
								color: '#000000',
								connectorColor: '#000000',
								formatter: function() {
									return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
								}
							}
						}
					},
					credits: {
						enabled: false
					},
				    series: [{
						type: 'pie',
						name: 'Browser share',
						data: [
								<c:forEach var="item" items="${bsls}">
                  					['${item.contractorname }', Math.round(100*(${item.count}/${bscount}))/1],
	    						</c:forEach>
						]
					}]
				});
}


//直放站
function repeaterchart(){
	var chart = new Highcharts.Chart({
					chart: {
						renderTo: 'pic2',
						plotBackgroundColor: null,
						plotBorderWidth: null,
						plotShadow: false,
						height:200
					},
					title: {
						text: '直放站'
					},
					tooltip: {
						formatter: function() {
							return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
						}
					},
					plotOptions: {
						pie: {
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: true,
								color: '#000000',
								connectorColor: '#000000',
								formatter: function() {
									return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
								}
							}
						}
					},
					credits: {
						enabled: false
					},
				    series: [{
						type: 'pie',
						name: 'Browser share',
						data: [
								<c:forEach var="item" items="${rels}">
                  					['${item.contractorname }', Math.round(100*(${item.count}/${recount}))/1],
	    						</c:forEach>
						]
					}]
				});
}


//铁塔
function outdoorfacilitieschart(){
	var chart = new Highcharts.Chart({
					chart: {
						renderTo: 'pic3',
						plotBackgroundColor: null,
						plotBorderWidth: null,
						plotShadow: false,
						height:200
					},
					title: {
						text: '铁塔'
					},
					tooltip: {
						formatter: function() {
							return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
						}
					},
					plotOptions: {
						pie: {
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: true,
								color: '#000000',
								connectorColor: '#000000',
								formatter: function() {
									return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
								}
							}
						}
					},
					credits: {
						enabled: false
					},
				    series: [{
						type: 'pie',
						name: 'Browser share',
						data: [
								<c:forEach var="item" items="${ouls}">
                  					['${item.contractorname }', Math.round(100*(${item.count}/${oucount}))/1],
	    						</c:forEach>
						]
					}]
				});
}



//室内
function indooroverridechart(){
	var chart = new Highcharts.Chart({
					chart: {
						renderTo: 'pic4',
						plotBackgroundColor: null,
						plotBorderWidth: null,
						plotShadow: false,
						height:200
					},
					title: {
						text: '室内分布'
					},
					tooltip: {
						formatter: function() {
							return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
						}
					},
					plotOptions: {
						pie: {
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: true,
								color: '#000000',
								connectorColor: '#000000',
								formatter: function() {
									return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
								}
							}
						}
					},
					credits: {
						enabled: false
					},
				    series: [{
						type: 'pie',
						name: 'Browser share',
						data: [
								<c:forEach var="item" items="${inls}">
                  					['${item.contractorname }', Math.round(100*(${item.count}/${incount}))/1],
	    						</c:forEach>
						]
					}]
				});
}
	
	
//基站县区
function regionBaseStation(){
	var patrolregioncombotree = new TreeComboField({
		width : 150,
		maxHeight : 100,
		allowBlank : true,
		renderTo : 'basestation_combotree_patrolregiondiv',
		name : "regionname",
		hiddenName : "district",
		hiddenId: "district",
		displayField : 'text',
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			root : new Ext.tree.AsyncTreeNode({
				id : '000000',
				loader : new Ext.tree.TreeLoader({
					dataUrl : '${ctx}/TowerPatrolinfo.do?method=getPatrolRegion'
				})

			})
		})
	});
}

//基站查询
function queryBaseStation(){
	window.location.href="${ctx}/baseStationAction_query.jspx?is_query=1&&stationCode="+jQuery("#stationCode").val()+"&bsLevel="+jQuery("#bsLevel").val()+"&district="+jQuery("#district").val()+"&address="+jQuery("#address").val();
};


//铁塔县区
function regionOutdoor(){
	var patrolregioncombotree = new TreeComboField({
		width : 150,
		maxHeight : 100,
		allowBlank : true,
		renderTo : 'outdoor_combotree_patrolregiondiv',
		name : "regionname",
		hiddenName : "district",
		displayField : 'text',
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			root : new Ext.tree.AsyncTreeNode({
				id : '000000',
				loader : new Ext.tree.TreeLoader({
					dataUrl : '${ctx}/TowerPatrolinfo.do?method=getPatrolRegion'
				})

			})
		})
	});
}
//直放站县区
function regionRepeater(){
	Ext.BLANK_IMAGE_URL = "${ctx}/js/extjs/resources/images/default/s.gif";
	var  patrolregioncombotree = new TreeComboField({
		width : 150,
		maxHeight : 100,
		renderTo : 'repeater_combotree_patrolregiondiv',
		name : "regionname",
		hiddenName : "district",
		displayField : 'text',
		forceSelection : true,
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			root : new Ext.tree.AsyncTreeNode({
				id : '000000',
				loader : new Ext.tree.TreeLoader({
					dataUrl : '${ctx}/TowerPatrolinfo.do?method=getPatrolRegion'
				})

			})
		})
	});
}

//室内区县
function regionIndoor(){
	Ext.BLANK_IMAGE_URL = "${ctx}/js/extjs/resources/images/default/s.gif";
    var  patrolregioncombotree = new TreeComboField({
		width : 180,
		maxHeight : 100,
		allowBlank : true,
		renderTo : 'indoor_combotree_patrolregiondiv',
		name : "regionname",
		hiddenName : "regionId",
		cls:"required",
		displayField : 'text',
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			root : new Ext.tree.AsyncTreeNode({
				id : '000000',
				loader : new Ext.tree.TreeLoader({
					dataUrl : '${ctx}/TowerPatrolinfo.do?method=getPatrolRegion'
				})

			})
		})
	});

}




	
	
	
</script>

	</head>

	<body style="width: 99%; margin: 5px; padding: 5px;">
		<div id="fieldSet1" style="width: 99%;">
		</div>
		<div id="fieldSet3" style="width: 99%;">
		</div>
		<div id="fieldSet2" style="width: 99%;">
		</div>
		<!--维护资源统计  -->
		<div id='maintainresouse_group1' style="width: 100%; margin: 5px; padding: 5px;">
			<div id='pic1' style="width: 49%; float: left;">
				 <!-- 基站图表 -->
			</div>
			<div id='pic3' style="width: 49%; float: left;">
				 <!-- 直放站图表 -->
			</div>
		</div>
		<div id='maintainresouse_group2' style="width: 100%; margin: 5px; padding: 5px;">
			<div id='pic2' style="width: 49%; float: right;">
				 <!-- 铁塔图表 -->
			</div>
			<div id='pic4' style="width: 49%; float: right;">
				 <!-- 室内图表 -->
			</div>
		</div>
		<!--信息搜索Panle  -->
		<div id='searchpanel' style="width: 100%; margin: 5px; padding: 5px;">
			<div id='search1' style="width: 49%; float: left;">
				<!--此处放基站搜索 -->
					<table id="BaseStationForm" style="display:none;" width="70%" border="0" align="center" cellpadding="0" cellspacing="0" class="tabout">
						<tr class="trcolor">
							<td colspan="2" class="Module_title3 Module3_text">基站信息搜索</td>
						</tr>
						<tr class="trcolor">
						<td class="tdulleft">站址编号：</td><td class="tdulright">
							<input type="text" class="inputtext" style="width:150px" id="stationCode" name="stationCode" value="${baseStation.stationCode}"/>
							</td>
						</tr>
						<tr class="trwhite"><td class="tdulleft">基站地址：</td><td class="tdulright">
							<input type="text" class="inputtext" style="width:150px" id="address"  name="address" alt="支持模糊搜索" value ="${baseStation.address}"/>
							</td>
						</tr>
						<tr class="trcolor"><td class="tdulleft">基站级别：</td><td class="tdulright">
							<div id="basestaion_basestation_level"></div>
						</tr>
						<tr class="trwhite"><td class="tdulleft">所属区县：</td><td class="tdulright">
							<div id="basestation_combotree_patrolregiondiv" style="width: 150;"></div>
						</td>
						<tr class="trwhite"><td class="tdulleft" colspan="2" style="text-align:center;">
							<input type="button" onclick="queryBaseStation()" class="button" value="查询"/>
						</tr>
					</table>
			</div>
			<div id='search2' style="width: 49%; float: right;margin:5px;">
			
				<!--此处放铁塔搜索 -->
				  <form action="${ctx }/outdoorFacilitiesAction_query.jspx" method="post">
 					<table id="outdoorForm" style="display:none;" width="70%" border="0" align="center" cellpadding="0" cellspacing="0" class="tabout">
						<tr class="trcolor">
							<td colspan="2" class="Module_title3 Module3_text">铁塔信息搜索</td>
						</tr>
						<tr class="trcolor">
						<td class="tdulleft">铁塔编号：</td><td class="tdulright">
							<input type="text" class="inputtext" style="width:150px" name="towerCode"/>
							</td>
						</tr>
						<tr class="trwhite"><td class="tdulleft">铁塔名称：</td><td class="tdulright">
							<input type="text" class="inputtext" style="width:150px" name="towerName"/>
							</td>
						</tr>
						<tr class="trcolor"><td class="tdulleft">原名称：</td><td class="tdulright">
							<input type="text" class="inputtext" style="width:150px" name="oldName"/>
						</tr>
						<tr class="trwhite"><td class="tdulleft">所属区县：</td><td class="tdulright">
						    <div id="outdoor_combotree_patrolregiondiv" style="width: 150;"></div>
							<div id="" style="width: 150;"></div>
						</td>
						<tr class="trcolor"><td class="tdulleft">基站编号：</td><td class="tdulright">
							<input type="text" class="inputtext" style="width:150px" name="stationCode"/>
						</tr>
						<tr class="trwhite"><td class="tdulleft">基站名称：</td><td class="tdulright">
							<input type="text" class="inputtext" style="width:150px" name="stationName"/>
						</tr>
						<tr class="trwhite"><td class="tdulleft" colspan="2" style="text-align:center;">
							<input type="submit" class="button" value="查询"/>
						</tr>
					</table>
				</form>
			</div>
			<div id='search3' style="width: 49%; float: left;;margin:5px;">
				<!--此处放直放站搜索 -->
				  <form action="${ctx }/repeaterAction_query.jspx" method="post">
				    <input name="is_query" value="1" type="hidden" />
 					<table id="repeaterForm" style="display:none;" width="70%" border="0" align="center" cellpadding="0" cellspacing="0" class="tabout">
						<tr class="trcolor">
							<td colspan="2" class="Module_title3 Module3_text">直放站信息搜索</td>
						</tr>
						<tr class="trcolor">
						<td class="tdulleft">直放站号：</td><td class="tdulright">
							<input type="text" class="inputtext" style="width:150px" name="repeaterType"/>
							</td>
						</tr>
						<tr class="trwhite"><td class="tdulleft">直放站名称：</td><td class="tdulright">
							<input type="text" class="inputtext" style="width:150px" name="repeaterName"/>
							</td>
						</tr>
						<tr class="trcolor"><td class="tdulleft">安装位置：</td><td class="tdulright">
							<input type="text" class="inputtext" style="width:150px" name="installPlace"/>
						</tr>
						<tr class="trwhite"><td class="tdulleft">所属区县：</td><td class="tdulright">
						    <div id="repeater_combotree_patrolregiondiv" style="width: 150;"></div>
						 </td>
						</tr>
						<tr class="trwhite"><td class="tdulleft" colspan="2" style="text-align:center;">
							<input type="submit"  class="button" value="查询"/>
						</tr>
					</table>
				</form>
			</div>
			<div id='search4' style="width: 49%; float: right;;margin:5px;">
				<!--此处室内分布搜索 -->
				  <form action="${ctx }/indoorOverRideAction_query.jspx" method="post">
				    <input name="is_query" value="1" type="hidden" />
 					<table id="indoorForm" style="display:none;" width="70%" border="0" align="center" cellpadding="0" cellspacing="0" class="tabout">
						<tr class="trcolor">
							<td colspan="2" class="Module_title3 Module3_text">室内分布信息搜索</td>
						</tr>
						<tr class="trcolor">
						<td class="tdulleft">分布系统名称：</td><td class="tdulright">
							<input type="text" class="inputtext" style="width:150px" name="distributeSystem"/>
							</td>
						</tr>
						<tr class="trwhite"><td class="tdulleft">所属区县：</td><td class="tdulright">
						    <div id="indoor_combotree_patrolregiondiv" style="width: 150;"></div>
						 </td>
						</tr>
						<tr class="trwhite"><td class="tdulleft" colspan="2" style="text-align:center;">
							<input type="submit"  class="button" value="查询"/>
						</tr>
					</table>
				</form>
			</div>
			<div id='search5' style="width: 49%; float: left;">
				<!--此处线路搜索 -->
			</div>
		</div>

	</body>
</html>