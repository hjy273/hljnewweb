<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://www.cabletech.com.cn/baseinfo" prefix="baseinfo"%>
<%@include file="/common/header.jsp"%>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript" type="text/javascript">
	var win;
	function view(id) {
		window.location.href = '${ctx}/indoorOverRideAction_through.jspx?flag=view&id=' + id;
	}
	function edit(id) {
		window.location.href = '${ctx}/res/indoorOverRideAction_view.jspx?id=' + id;
	}
	function del(id) {
		if (confirm("删除将不能恢复，请确认是否要删除该基站，按‘确定’删除？")) {
			window.location.href = '${ctx}/res/indoorOverRideAction_delete.jspx?id=' + id;
		}
	}
	
	
	Ext.onReady(function() {
		patrolregioncombotree = new TreeComboField({
			width : 220,
			height : 100,
			allowBlank : true,
			leafOnly : false,
			renderTo : 'combotree_regionid',
			name : "regionname",
			hiddenName : "regionId",
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
		});
		patrolregioncombotree.setComboValue("${regionId}","${regionName}");
		
		
		
		
	Ext.BLANK_IMAGE_URL = "${ctx}/js/extjs/resources/images/default/s.gif";
	patrolgroupcombotree = new TreeComboField({
		width : 220,
		height:100,
		allowBlank:true,
		leafOnly : true,
		renderTo : 'combotree_patrolgroupdiv',
		name : "patrolgroupname",
		hiddenId : "patrolmanId",
		hiddenName : "patrolmanId",
		displayField : 'text',
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			autoHeight:true, 
			root : new Ext.tree.AsyncTreeNode({
				id : 'root',
				loader : new Ext.tree.TreeLoader({
							dataUrl : '${ctx}/common/externalresources_getPatrolmanJson.jspx'
						})
			})
		})
	});//维护组
	patrolgroupcombotree.setComboValue('${patrolmanId}','<baseinfo:patrolman displayProperty="patrolname" id="${patrolmanId}"></baseinfo:patrolman>');
		
		
	})
	
	
	
	
</script>
<template:titile value="室内分布信息查询" />
<br />
<s:form action="/res/indoorOverRideAction_query.jspx" method="post">
<input name="is_query" value="1" type="hidden" />
	<table width="850" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tabout">
		<tr>
			<td class="tdulleft" style="width: 15%">
				分布系统名称：
			</td>
			<td class="tdulright" style="width: 35%">
				<input name="distributeSystem" value="${distributeSystem }" type="text" style="width: 220;">
			</td>
			<td class="tdulleft" style="width: 15%">
				区域：
			</td>
			<td class="tdulright" style="width: 35%">
				<div id="combotree_regionid" style="width: 180;"></div>
			</td>
		</tr>
		<tr>
			<td class="tdulleft" style="width: 15%">
				维护组：
			</td>
			<td class="tdulright" style="width: 35%">
				<div id="combotree_patrolgroupdiv" style="width: 180;"></div>
			</td>
			<td colspan="3" style="width: 50%;text-align:center;">
				<input type="submit" class="button" value="查询">
			</td>
		</tr>
	</table>
</s:form>
<display:table name="sessionScope.RESULTLIST" id="row" pagesize="18" export="fasle" requestURI="${ctx }/res/indoorOverRideAction_query.jspx">
   	<display:column property="distributesystem" title="分布系统名称" sortable="true" />
   	<display:column property="regionname" title="区域" sortable="true" />
    <display:column property="maintenance_name" title="维护单位" sortable="true" />
   	<display:column property="PATROLNAME" title="维护组" sortable="true" />
   	<display:column property="coverageareaname" title="覆盖区域" sortable="true" />
   	<display:column property="overridebodytypename" title="覆盖类型" sortable="true" />
   	<display:column property="blendingname" title="合成方式" sortable="true" />
   	<display:column property="source" title="信源方式" sortable="true" />
   	<display:column property="station_code" title="所属站号" sortable="true" />
   	<display:column property="floorcount" title="楼层数" sortable="true" />
   	<display:column property="createtime" format="{0,date,yyyy-MM-dd}" title="入网时间" sortable="true" />
   	<display:column property="address" title="地址" sortable="true" />
   	<display:column media="html" title="操作" style="width:100px;">
		<apptag:privilege operation="view"><a href="javascript:view('${row.id}')">查看</a></apptag:privilege>
		<apptag:privilege operation="edit"><a href="javascript:edit('${row.id}')">修改</a></apptag:privilege>
		<apptag:privilege operation="del"><a href="javascript:del('${row.id}')">删除</a></apptag:privilege>
	</display:column>
</display:table>
<div align="left">
	<a href="${ctx}/indoorOverRideAction_export.jspx">导出Excel文件</a>
</div>