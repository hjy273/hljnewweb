<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript" type="text/javascript">
	var win;
	function view(id) {
		window.location.href = '${ctx}/indoorOverRideAction_view.jspx?flag=view&id=' + id;
	}
	function edit(id) {
		window.location.href = '${ctx}/patrolManager/indoorOverRideAction_view.jspx?id=' + id;
	}
	function del(id) {
		//if (confirm("删除将不能恢复，请确认是否要删除该信息，按‘确定’删除？")) {
		window.location.href = '${ctx}/patrolManager/patrolItemAction_deleteLogic.jspx?ids=' + id;
		//}
	}
	function startUsing(id) {
		//if (confirm("删除将不能恢复，请确认是否要删除该信息，按‘确定’删除？")) {
		window.location.href = '${ctx}/patrolManager/patrolItemAction_startUsing.jspx?ids=' + id;
		//}
	}
	
<c:if test="${isProvince=='true'}">	
Ext.onReady(function() {
     patrolregioncombotree = new TreeComboField({
		width : 180,
		maxHeight : 100,
		allowBlank : true,
		renderTo : 'combotree_regionid',
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
					dataUrl : '${ctx}/common/externalresources_getRegionJson.jspx'
				})

			})
		})
	});
	patrolregioncombotree.setComboValue("${regionId}","${regionName}");
})
</c:if>	
	
function checkAll(){  
 //alert('tt');
    var allbox = document.getElementById("allbox").checked;
    var listbox = document.getElementsByName("listbox");
    for(var i=0;i<listbox.length;i++){
       listbox[i].checked=allbox;
    }
}

function checkList(){
    var allbox = document.getElementById("allbox");
    var listbox = document.getElementsByName("listbox");
    var checked = true;
    for(var i=0;i<listbox.length;i++){
      if(!listbox[i].checked){
         checked = false;
         break;
      }
    }
    allbox.checked= checked;
}

function deleteLogicSum(tablename,type){
    var listbox = document.getElementsByName("listbox");
    var checkedNum = 0;
    var idValue ='';
    for(var i=0;i<listbox.length;i++){
      if(listbox[i].checked){
         checkedNum = checkedNum + 1;
         idValue = idValue+listbox[i].value+','
      }
    }
    if(checkedNum==0){
       alert('请至少选择一条记录！');
       return false;
    }
     var con;
    // con= confirm("确定要删除选中项吗?"); 
	//if(con == true){
    window.location.href = "${ctx}/patrolManager/patrolItemAction_deleteLogic.jspx?ids="+idValue+"&businessType=${businessType }";
    //}
}
function startUsingSum(tablename,type){
    var listbox = document.getElementsByName("listbox");
    var checkedNum = 0;
    var idValue ='';
    for(var i=0;i<listbox.length;i++){
      if(listbox[i].checked){
         checkedNum = checkedNum + 1;
         idValue = idValue+listbox[i].value+','
      }
    }
    if(checkedNum==0){
       alert('请至少选择一条记录！');
       return false;
    }
     var con;
    // con= confirm("确定要删除选中项吗?"); 
	//if(con == true){
    window.location.href = "${ctx}/patrolManager/patrolItemAction_startUsing.jspx?ids="+idValue+"&businessType=${businessType }";
    //}
}

function deletePhysicsSum(tablename,type){
    var listbox = document.getElementsByName("listbox");
    var checkedNum = 0;
    var idValue ='';
    for(var i=0;i<listbox.length;i++){
      if(listbox[i].checked){
         checkedNum = checkedNum + 1;
         idValue = idValue+listbox[i].value+','
      }
    }
    if(checkedNum==0){
       alert('请至少选择一条记录！');
       return false;
    }
     var con;
     con= confirm("确定要删除选中项吗?物理删除可能会导致部分数据的统计无法查询到"); 
	//if(con == true){
    //    window.location.href = "${ctx}/patrolManager/patrolItemAction_deletePhysics.jspx?ids="+idValue+"&businessType=${businessType }";
    //}
}



function toggleShowAll(obj){
  if(obj.checked){
    jQuery("#showAll").val("1");
  }else{
	jQuery("#showAll").val("0");
  }
}
	
	
</script>
<template:titile value="巡检项信息查询" />
<br />
<s:form action="patrolManager/patrolItemAction_query.jspx" method="post">
<input name="is_query" value="1" type="hidden" />
	<table width="850" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tabout">
		<tr>
						<td class="xianshi1">
							专业：
						</td>
						<td class="xianshi2">
							<apptag:dynLabel dicType="businesstype" objType="dic" id="${businessType }"></apptag:dynLabel>
						</td>
						<c:if test="${isProvince=='true'}">
						<td class="xianshi1">
							区域：
						</td>
						<td class="xianshi2">
							<div id="combotree_regionid" style="width: 180;"></div>
						</td>
						</c:if>
						<td class="xianshi1">
						    <input type="hidden" id="showAll"  name="showAll" value="${showAll}">
							显示所有数据（包括作废数据）<input <c:if test="${showAll=='1' }">checked</c:if> onclick="toggleShowAll(this);" type="checkbox">
						</td>
						<td>
							<input type="submit" class="button" value="查询">
						</td>
					</tr>
	</table>
</s:form>
<display:table name="sessionScope.RESULTLIST" id="row" pagesize="18" export="fasle" requestURI="${ctx }/patrolItemAction_query.jspx">
	<display:column style="width:3%" media="html" title="<input type='checkbox' id='allbox' name='allbox' onclick='checkAll()'>" >         		   		
        <input type="checkbox" name="listbox" value="${row.id}" onclick="checkList()">
    </display:column> 
    <display:column property="businesstypename" style="width:160px;" title="专业" sortable="true" />
   	<display:column style="width:7%" property="itemname" title="维护对象" sortable="true" />
   	<display:column property="subitem_name" title="维护检测项目" sortable="true" />
   	<display:column style="width:4%" title="周期" sortable="true">
   	<c:if test="${row.cycle=='year'}">
   	年
   	</c:if>
   	<c:if test="${row.cycle=='quarter'}">
   	季
   	</c:if>
   	<c:if test="${row.cycle=='month'}">
   	月
   	</c:if>
   	<c:if test="${row.cycle=='week'}">
   	周
   	</c:if>
   	<c:if test="${row.cycle=='custom'}">
   	自定义
   	</c:if>
   	</display:column>
   	<display:column title="输入类型" style="width:8%" sortable="true">
   	<c:if test="${row.input_type=='NUM'}">
   	数值
   	</c:if>
   	<c:if test="${row.input_type=='TEXT'}">
   	文本
   	</c:if>
   	<c:if test="${row.input_type=='CHOOSE'}">
   	单选
   	</c:if>
   	<c:if test="${row.input_type=='GROUP'}">
   	多选
   	</c:if>
   	<c:if test="${row.input_type=='BATTERY'}">
   	蓄电池测试
   	</c:if>
   	<c:if test="${row.input_type=='STATION_ANTENNA'}">
   	天线测量
   	</c:if>
   	<c:if test="${row.input_type=='INDOOR_COVERAGE'}">
   	天线信号测试
   	</c:if>
   	<c:if test="${row.input_type=='WLAN'}">
   	WLAN天线信号测试
   	</c:if>
   	</display:column>
   	<display:column property="QUALITY_STANDARD" style="width:12%;" title="质量标准" sortable="true" />
   	<display:column property="VALUE_SCOPE" style="width:12%;" title="值域范围" sortable="true" />
   	<display:column property="DEFAULT_VALUE" style="width:6%;" title="默认值" sortable="true" />
   	<display:column property="EXCEPTION_VALUE" style="width:6%;" title="异常状态" sortable="true" />
   	<display:column media="html" title="操作" style="width:60px;">
   		<c:if test="${row.is_forbidden_state=='N' }">
			<a href="javascript:del('${row.id}')">作废</a>
		</c:if>
		<c:if test="${row.is_forbidden_state=='Y' }">
			<a href="javascript:startUsing('${row.id}')">启用</a>
		</c:if>
	</display:column>
</display:table>
<div align="left">
	<a href="javascript:void(0);" onclick="deleteLogicSum()">批量作废</a>&nbsp;
	<a href="javascript:void(0);" onclick="startUsingSum()">批量启用</a>&nbsp;
    <!-- 
    <a href="javascript:void(0);" onclick="deletePhysicsSum()">批量物理删除</a>&nbsp;
     -->
	<a href="${ctx}/patrolItemAction_export.jspx">导出Excel文件</a>
</div>