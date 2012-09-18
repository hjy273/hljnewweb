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
		//if (confirm("ɾ�������ָܻ�����ȷ���Ƿ�Ҫɾ������Ϣ������ȷ����ɾ����")) {
		window.location.href = '${ctx}/patrolManager/patrolItemAction_deleteLogic.jspx?ids=' + id;
		//}
	}
	function startUsing(id) {
		//if (confirm("ɾ�������ָܻ�����ȷ���Ƿ�Ҫɾ������Ϣ������ȷ����ɾ����")) {
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
       alert('������ѡ��һ����¼��');
       return false;
    }
     var con;
    // con= confirm("ȷ��Ҫɾ��ѡ������?"); 
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
       alert('������ѡ��һ����¼��');
       return false;
    }
     var con;
    // con= confirm("ȷ��Ҫɾ��ѡ������?"); 
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
       alert('������ѡ��һ����¼��');
       return false;
    }
     var con;
     con= confirm("ȷ��Ҫɾ��ѡ������?����ɾ�����ܻᵼ�²������ݵ�ͳ���޷���ѯ��"); 
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
<template:titile value="Ѳ������Ϣ��ѯ" />
<br />
<s:form action="patrolManager/patrolItemAction_query.jspx" method="post">
<input name="is_query" value="1" type="hidden" />
	<table width="850" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tabout">
		<tr>
						<td class="xianshi1">
							רҵ��
						</td>
						<td class="xianshi2">
							<apptag:dynLabel dicType="businesstype" objType="dic" id="${businessType }"></apptag:dynLabel>
						</td>
						<c:if test="${isProvince=='true'}">
						<td class="xianshi1">
							����
						</td>
						<td class="xianshi2">
							<div id="combotree_regionid" style="width: 180;"></div>
						</td>
						</c:if>
						<td class="xianshi1">
						    <input type="hidden" id="showAll"  name="showAll" value="${showAll}">
							��ʾ�������ݣ������������ݣ�<input <c:if test="${showAll=='1' }">checked</c:if> onclick="toggleShowAll(this);" type="checkbox">
						</td>
						<td>
							<input type="submit" class="button" value="��ѯ">
						</td>
					</tr>
	</table>
</s:form>
<display:table name="sessionScope.RESULTLIST" id="row" pagesize="18" export="fasle" requestURI="${ctx }/patrolItemAction_query.jspx">
	<display:column style="width:3%" media="html" title="<input type='checkbox' id='allbox' name='allbox' onclick='checkAll()'>" >         		   		
        <input type="checkbox" name="listbox" value="${row.id}" onclick="checkList()">
    </display:column> 
    <display:column property="businesstypename" style="width:160px;" title="רҵ" sortable="true" />
   	<display:column style="width:7%" property="itemname" title="ά������" sortable="true" />
   	<display:column property="subitem_name" title="ά�������Ŀ" sortable="true" />
   	<display:column style="width:4%" title="����" sortable="true">
   	<c:if test="${row.cycle=='year'}">
   	��
   	</c:if>
   	<c:if test="${row.cycle=='quarter'}">
   	��
   	</c:if>
   	<c:if test="${row.cycle=='month'}">
   	��
   	</c:if>
   	<c:if test="${row.cycle=='week'}">
   	��
   	</c:if>
   	<c:if test="${row.cycle=='custom'}">
   	�Զ���
   	</c:if>
   	</display:column>
   	<display:column title="��������" style="width:8%" sortable="true">
   	<c:if test="${row.input_type=='NUM'}">
   	��ֵ
   	</c:if>
   	<c:if test="${row.input_type=='TEXT'}">
   	�ı�
   	</c:if>
   	<c:if test="${row.input_type=='CHOOSE'}">
   	��ѡ
   	</c:if>
   	<c:if test="${row.input_type=='GROUP'}">
   	��ѡ
   	</c:if>
   	<c:if test="${row.input_type=='BATTERY'}">
   	���ز���
   	</c:if>
   	<c:if test="${row.input_type=='STATION_ANTENNA'}">
   	���߲���
   	</c:if>
   	<c:if test="${row.input_type=='INDOOR_COVERAGE'}">
   	�����źŲ���
   	</c:if>
   	<c:if test="${row.input_type=='WLAN'}">
   	WLAN�����źŲ���
   	</c:if>
   	</display:column>
   	<display:column property="QUALITY_STANDARD" style="width:12%;" title="������׼" sortable="true" />
   	<display:column property="VALUE_SCOPE" style="width:12%;" title="ֵ��Χ" sortable="true" />
   	<display:column property="DEFAULT_VALUE" style="width:6%;" title="Ĭ��ֵ" sortable="true" />
   	<display:column property="EXCEPTION_VALUE" style="width:6%;" title="�쳣״̬" sortable="true" />
   	<display:column media="html" title="����" style="width:60px;">
   		<c:if test="${row.is_forbidden_state=='N' }">
			<a href="javascript:del('${row.id}')">����</a>
		</c:if>
		<c:if test="${row.is_forbidden_state=='Y' }">
			<a href="javascript:startUsing('${row.id}')">����</a>
		</c:if>
	</display:column>
</display:table>
<div align="left">
	<a href="javascript:void(0);" onclick="deleteLogicSum()">��������</a>&nbsp;
	<a href="javascript:void(0);" onclick="startUsingSum()">��������</a>&nbsp;
    <!-- 
    <a href="javascript:void(0);" onclick="deletePhysicsSum()">��������ɾ��</a>&nbsp;
     -->
	<a href="${ctx}/patrolItemAction_export.jspx">����Excel�ļ�</a>
</div>