<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="com.cabletech.base.SysConstant"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>巡检系统</title>
<style type="text/css">

.Detailed_list_analysis{ width:100%; /* IE7+FF */ font-size:12px;  text-align:center; color:#10284B; margin-top:10px; margin-left:0%; margin-right:0%; border:1px solid #8DC5E2;  }
.Detailed_list_analysis_title{ height:24px!important; font-size:12px; color:#000000; text-align:center;}
.Detailed_list_analysis th{text-align:left; padding-left:20px; line-height:24px; color:#09235E; }
.Detailed_list_analysis,table.Detailed_list_analysis th,table.Detailed_list_analysis th{  border-collapse:collapse!important;}
.Detailed_list_analysis td{ border:1px solid #8DC5E2; height:24px; line-height:24px; }

</style>
		<link href="${ctx}/css/divstyle.css" rel="stylesheet" type="text/css">
		<script src="${ctx}/js/extjs/ux/ux-all-debug.js"
			type="text/javascript"></script>
		<script src="${ctx}/js/extjs/ux/RowLayout.js"
			type="text/javascript"></script>
		<script type='text/javascript' src='${ctx}/js/highchart/highcharts.js'></script>
		<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
		<script type="text/javascript">

Ext.onReady(function(){
	
	//人员流动率布局
	var  staff=new Ext.Panel({
	    id:"staff_id",
        title: '人员流动情况',
        columnWidth: .5,
        margins :5,
        border : false,
        contentEl:Ext.get('Staff')
    });
	//设备使用情况布局 
	var  equipment=new Ext.Panel({
        title: '设备使用情况',
        columnWidth: .5,
        margins :5,
        border : false,
        contentEl:Ext.get('Equipment')
    });
	var onepanel= new Ext.form.FieldSet({
		title: '相关情况',
        collapsible: true,
		layout:'column',
		autoHeight:true,
		items:[staff,equipment]
	});
	//维护专业布局 
	var  maintainBusiness=new Ext.form.FieldSet({  
        title: '维护专业',
        collapsible:true,
        autoHeight:true,
        contentEl:Ext.get('MaintainBusiness')
    });
	//代维公司搜索布局 
	var  maintainCompanySearch=new Ext.Panel({
        title: '代维公司',
        columnWidth: .5,
        margins :5,
        border : false,
        contentEl:Ext.get('MaintainCompanySearch')
    });
	//资质搜索布局 
	var  certificateSearch=new Ext.Panel({
        title: '资质',
        columnWidth: .5,
        margins :5,
        border : false,
        contentEl:Ext.get('CertificateSearch')
    });
	var towpanel= new Ext.form.FieldSet({
		title: '信息搜索',
		layout:'column',
		collapsible:true,
		items:[maintainCompanySearch,certificateSearch]
	});

var root = new Ext.tree.AsyncTreeNode({
		text : '人员名单',
		expanded : true,
		id : 'root'
	});
// 树右侧
   var lefttree = new Ext.tree.TreePanel({
		// 设置异步树节点的数据加载器
		loader : new Ext.tree.TreeLoader({
					baseAttrs : {// 设置子节点的基本属性
						cust : 'client'
					},
					dataUrl : '${ctx}/DesktopAction.do?method=getMenu'
				}),
		border : false,
		margins:5,
		padding:5,
		autoScroll : true,
		title : '人员名单',
		root : root,
		rootVisible : true
		//renderTo : 'eastpanel'
	});

	// 中心面板
	var centerpanel = new Ext.Panel({
		autoHeight:true,
		border : false,
		items:[onepanel,maintainBusiness,towpanel],
		renderTo:'centerpanel'
	});
	
	//生成---代维公司人员流动图表--add by wangjie
	percentagesChart();
	//生成---终端报表--add by wangjie
	terminalChart();
	//显示---维护专业情况表格--add by wangjie
	Ext.get("MaintainBusinessTable").setStyle("display","");
	//显示---资质搜索FORM--add by wangjie
	Ext.get("CertificateSearchForm").setStyle("display","");
	//显示---资质搜索FORM--add by wangjie
	Ext.get("MaintainCompanySearchForm").setStyle("display","");
	
	
	
	//代维公司搜索----区域
    maintainregioncombotree = new TreeComboField({
		width : 160,
		height : 100,
		allowBlank : true,
		leafOnly : false,
		renderTo : 'combotree_regionid',
		name : "regionname",
		hiddenName : "regionid",
		displayField : 'text',
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			autoHeight:true, 
			root : new Ext.tree.AsyncTreeNode({
				id : '000000',
				loader : new Ext.tree.TreeLoader({
					dataUrl : '${ctx}/common/externalresources_getRegionJson.jspx'
				})
			})
		})
	});//区域
	
	//代维公司搜索----上级公司
	combotreeparentcontractor = new TreeComboField({
		width : 160,
		allowBlank:true,
		leafOnly : false,
		height : 100,
		renderTo : 'combotree_parent_contractor',
		name : "patrolgroupname",
		hiddenName : "parentcontractorid",
		displayField : 'text',
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			autoHeight:true, 
			root : new Ext.tree.AsyncTreeNode({
				id : 'root',
				loader : new Ext.tree.TreeLoader({
							dataUrl : '${ctx}/common/externalresources_getContractorJson.jspx'
						})
			})
		})
	});//上级公司
	
	
	//资质搜索----代维公司
	combotreecontractor = new TreeComboField({
		width : 160,
		allowBlank:true,
		leafOnly : false,
		height : 100,
		renderTo : 'combotree_contractor',
		name : "patrolgroupname",
		hiddenName : "objectid",
		displayField : 'text',
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			autoHeight:true, 
			root : new Ext.tree.AsyncTreeNode({
				id : 'root',
				loader : new Ext.tree.TreeLoader({
							dataUrl : '${ctx}/common/externalresources_getContractorJson.jspx'
						})
			})
		})
	});//代维公司
	//资质类型
	var  apptitudetype = new Appcombox({
	    width : 160,
	   	hiddenName : 'certificatetype',
	   	emptyText : '请选择',
	   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=APTITUDETYPE',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:true,
	   	renderTo: 'apptitude_type'
	})

})


//代维公司人员流动图表--add by wangjie
function percentagesChart(){
	var chart = new Highcharts.Chart({
		chart: {
				renderTo: 'percentagesChart_container',
				defaultSeriesType: 'column',
				height:220
		},
		title: {
				text: ''
		},
		xAxis: {
				categories: [
					<c:forEach var="item" items="${psls}">
                  		'${item.CONTRACTORNAME}',
	    			</c:forEach>
				]
		},
		yAxis: {
				min: 0,
				title: {
					text: '百分比(%)'
				}
		},
		legend: {
				 layout: 'horizontal',
				 backgroundColor: '#FFFFFF',
				align: 'right',
				verticalAlign: 'top',
				floating: true,
				shadow: true
		},
		tooltip: {
				formatter: function() {
					return ''+this.x +': '+ this.y +'%';
				}
		},
		plotOptions: {
				column: {
					pointPadding: 0.2,
					borderWidth: 0
				}
		},
		credits: {
					enabled: false
		},
		series: [
                    {
             			name: '流动率',
              			data: [
              			   <c:forEach var="item" items="${psls}">
      								${item.PERCENTAGE},
      					   </c:forEach>
      					]},	
				]
		});
}

//终端报表--add by wangjie
function terminalChart(){
	var chart = new Highcharts.Chart({
		chart: {
				renderTo: 'terminalChart_container',
				plotBackgroundColor: null,
				plotBorderWidth: null,
				plotShadow: false,
				height:220
		},
		title: {
				text: ''
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
							<c:forEach var="item" items="${tels}">
                  				['${item.contractorname }', Math.round(100*(${item.count}/${tecount}))/1],
	    					</c:forEach>
						]
					}]
		});
}

</script>

	</head>

	<body style="margin:5px;padding:5px;border: none;">
	<div id='centerpanel' style="width:85%;float: left;">
	</div>
	<div id='eastpanel' style="width:12%;float:right">
	</div>
	<!--人员流动率 -->
    <div id='Staff'>
    	<!-- 人员流动率报表容器 add by wangjie -->
     	<div id='percentagesChart_container'></div>
    </div>
    
    <!--设备使用情况 -->
    <div id='Equipment'>
    	<!-- 设备使用情况报表容器 add by wangjie -->
     	<div id='terminalChart_container'></div>
    </div>
    <!--维护专业 -->
    <div id='MaintainBusiness' >
        <!-- 维护专业 明细表 add by wangjie -->
    	<table id="MaintainBusinessTable" cellspacing="0" cellpadding="0" border="0" style="display:none" class="Detailed_list_analysis">
    		<tr>
				<td style="background-color:#eee">
    				代维公司
    			</td>
    			<td style="background-color:#eee">
     				维护专业
    			</td>
    			<td style="background-color:#eee">
     				维护资质
    			</td>
    			<td style="background-color:#eee">
    	 			维护组
    			</td>
   			</tr>
   			<c:forEach var="item" items="${rsls}">
    		<tr>
				<td>
    				${item.contractorname}
    			</td>
    			<td>
     				${item.resource_id}
    			</td>
    			<td>
     				${item.certificatename}
    			</td>
   				<td>
    	 			${item.patrolmancount}
    			</td>
   			</tr>
   			</c:forEach>
		</table>
		<!-- end table -->
    </div>
    <!--代维公司搜索-->
    <div id='MaintainCompanySearch' >
        <!--代维公司搜索 FORM add by wangjie-->
        <div id="MaintainCompanySearchForm" style="display:none">
    	<html:form method="Post" action="/contractorAction.do?method=queryContractor">
			<template:formTable contentwidth="220" namewidth="220">
			<template:formTr name="单位名称">
				<html:text property="contractorName" styleClass="inputtext" style="width:160" maxlength="45" />
			</template:formTr>
			<template:formTr name="组织类型">
			<select name="organizationType" class="inputtext" style="width: 160">
				<option value="">
					不限
				</option>
				<option value="1">
					独立法人
				</option>
				<option value="2">
					分支机构
				</option>
			</select>
			</template:formTr>
			<template:formTr name="所属区域">
				<div id="combotree_regionid"></div>
			</template:formTr>
			<template:formTr name="上级公司">
				<div id="combotree_parent_contractor"></div>
			</template:formTr>
			<template:formTr name="合作专业">
				<c:forEach var="res" items="${resources}" varStatus="stauts">
					<html:checkbox property="resourcesId" value="${res.code}">${res.resourcename}</html:checkbox>
					<c:if test="${(stauts.index+1)%2==0}">
						<br>
					</c:if>
				</c:forEach>
			</template:formTr>
			<template:formSubmit>
			<td>
				<html:submit styleClass="button">查询</html:submit>
			</td>
			<td>
				<html:reset styleClass="button">取消</html:reset>
					</td>
					<td>
						<html:button property="action" styleClass="button" onclick="addGoBack()">返回</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
		</div>
		<!-- end form -->
    </div>
     <!--资质搜索-->
    <div id='CertificateSearch' >
    	 <!-- 资质搜索FORM add by wangjie-->
    	<html:form action="/CertificateAction?method=doQuery" styleId="queryForm2">
			<table id="CertificateSearchForm" style="display:none"  border="0" align="center" cellpadding="0" cellspacing="0" class="tabout">
				<tr class="trcolor">
					<td class="tdulleft">证书编号：</td><td class="tdulright">
						<html:text property="certificatecode"  maxlength="20" />
					</td>
				</tr>
				<tr class="trwhite"><td class="tdulleft">证书名称：</td><td class="tdulright"><html:text property="certificatename" maxlength="25" />
					<br></td>
				</tr>
				<tr class="trcolor"><td class="tdulleft">证书类型：</td><td class="tdulright">
					<div id="apptitude_type"></div>
				</tr>
				<logic:equal value="1" name="LOGIN_USER" property="deptype">
				<tr class="trwhite"><td class="tdulleft">代维单位：</td><td class="tdulright">
						<div id="combotree_contractor"></div>
						<input type="hidden" name="objecttype" value="CONTRACTORINFO" />
				</td>
				</tr>
				</logic:equal>
				<logic:equal value="2" name="LOGIN_USER" property="deptype">
					<tr class="trwhite"><td class="tdulleft">代维单位：</td><td class="tdulright">
						<select name="objectid" Class="inputtext" style="width: 200">
							<option  value="${LOGIN_USER.deptID }">
								${LOGIN_USER.deptName }
							</option>
						</select>
						<input type="hidden" name="objecttype" value="CONTRACTORINFO" />
						</td>
					</tr>
				</logic:equal>
				<tr class="trwhite"><td class="tdulleft" colspan="2" style="text-align:center;">
						<html:submit property="action" styleClass="button">查询</html:submit>
				</tr>
			</table>
		</html:form>
		<!-- end form -->
    </div>

	</body>
</html>