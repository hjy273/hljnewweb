<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://www.cabletech.com.cn/baseinfo" prefix="baseinfo"%>
<%@include file="/common/header.jsp"%>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript" type="text/javascript">
function checkAll(){  
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




	
	function del(id) {
		if (confirm("删除将不能恢复，请确认是否要删除该用户操作信息查询，按‘确定’删除？")) {
			window.location.href = '${ctx}/userlogs/userActionLogAction_del.jspx?ids=' + id;
		}
	}
	
	function batchDel(){
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
    if (confirm("删除将不能恢复，请确认是否要删除选中的用户操作信息查询，按‘确定’删除？")) {
			window.location.href = '${ctx}/userlogs/userActionLogAction_del.jspx?ids=' + idValue;
	}
  }

</script>
<template:titile value="用户操作信息查询" />
<br />
<s:form action="/userlogs/userActionLogAction_list.jspx" method="post">
<input name="is_query" value="1" type="hidden" />
	<table width="850" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
		<tr>
						<td class="tdulleft" style="width: 15%">
							操作时间：
						</td>
						<td class="tdulright" style="width: 35%">
							<input id="startTime" name="startTime" value="${startTime }"  class="Wdate " onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" type="text" style="width: 250;">
							至
							<input id="endTime" name="endTime" value="${endTime }" class="Wdate " onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" type="text" style="width: 250;">
						</td>
						<td class="tdulleft" style="width: 15%">
							操作人员：
						</td>
						<td class="tdulright" style="width: 35%">
							<input name="userName" value="${userName }" type="text" style="width: 250;">
						</td>
		</tr>
	</table>
	<div align="center">
		<input type="submit" class="button" value="查询">
	</div>
</s:form>
<baseinfo:user displayProperty="username" id="000000001250"></baseinfo:user>
<display:table name="sessionScope.RESULTS" id="row" pagesize="18" export="fasle" requestURI="${ctx }/userlogs/userActionLogAction_list.jspx">
    <display:column style="width:3%" media="html" title="<input type='checkbox' id='allbox' name='allbox' onclick='checkAll()'>" >         		   		
        <input type="checkbox" name="listbox" value="${row.id}" onclick="checkList()">
    </display:column> 
    <display:column property="personid" style="width:10%" title="人员名称"/>
   	<display:column property="operate_time" format="{0,date,yyyy-MM-dd HH:mm:ss}" style="width:10%" title="操作时间"/>
   	<display:column property="loginip" style="width:10%" title="登录IP" />
	<display:column property="recordid" style="width:10%" title="操作名称" />
	<display:column property="method_desc" style="width:10%" title="操作描述" />
	<display:column property="record" style="width:40%" maxLength="40" title="操作数据" />
   	<display:column media="html" title="操作" style="width:10%">

			<a href="javascript:del('${row.id}')">删除</a>

	</display:column>
</display:table>
<div align="left">

	<a href="javascript:void(0);" onclick="batchDel()">批量删除</a>\

</div>