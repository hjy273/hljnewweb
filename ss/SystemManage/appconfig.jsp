<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.commons.web.WebAppUtils" %>
<style>
span{ color:red; }
div{color:blue;}
</style>
<script type="text/javascript">
	var delay = 500;
	function saveApprovergroup(){
		var id = jQuery("#approverGroup").val()
		jQuery.ajax({
		   type: "POST",
		   url: "${ctx}/appconfig.do",
		   data: "method=saveAppConfig&id=approverGroupId&value="+id,
		   success: function(msg){
			   jQuery("#approverGroupId_state").text(msg);
			   setTimeout(function(){jQuery("#approverGroupId_state").text("")},delay);
		   }
		 });
	}
	function save_online_period(){
		var id = jQuery("#online_period").val();
		var url1="${ctx}/appconfig.do";
		var url2="method=saveAppConfig&id=online_period&value="+id;
		var obj_msg = jQuery("#online_period_state");
		save(url1,url2,obj_msg);
	}
	function save(url1,url2,msg_obj){
		
		jQuery.ajax({
		   type: "POST",
		   url: url1,
		   data: url2,
		   success: function(msg){
			   msg_obj.text(msg);
			   setTimeout(function(){msg_obj.text("")},delay);
		   }
		 });
	}

</script>
<html>
	<head>
	<script type="text/javascript" language="javascript">
    </script>
	</head>
	<body>
		<template:titile value="编辑系 统配置信息" />
		
			<template:formTable>
				<template:formTr name="审核人抄送人设置">
					<div><c:out value="${appconfigs['approverGroupId'].remark}"/></div>
					<apptag:setSelectOptions columnName2="id" columnName1="groupname" valueName="usergroups" condition="type='1'" tableName="usergroupmaster"/>
			        <select name="approverGroupId" id="approverGroup" class="inputtext" style="width:200px"  onchange="saveApprovergroup(this)">
			        	<c:forEach var="bean" items="${usergroups}">
			        		<option value="${bean['value']}">${bean['label']}</option>
			        	</c:forEach>
			        </select>
			        
			       <span id="approverGroupId_state" style="width:200px"></span>
				</template:formTr>
				<template:formTr name="巡检人员在线时长设置">
					<div><c:out value="${appconfigs['online_period'].remark}"/></div>
					<input type="text" name="online_period" id="online_period" value="<%=WebAppUtils.online_period%>" class="inputtext" style="width:200px" onchange="save_online_period()"/>
					<span id="online_period_state" style="width:200px"></span>
				</template:formTr>
			</template:formTable>
			<div align="left" style="color:red">修改数据后会自动保存</div>
		<template:cssTable></template:cssTable>
	</body>
	<script type="text/javascript">
	jQuery(document).ready(function() {
		    jQuery("#approverGroup option[value='<%=WebAppUtils.approverGroupId%>']").attr('selected', 'selected');
		});
	</script>
</html>


