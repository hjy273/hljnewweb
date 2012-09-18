<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<html>
	<head>
		<title>临时割接申请列表</title>
		<script type="text/javascript">
			//查看申请
			toAddApplyCut=function(cutId){
            	window.location.href = "${ctx}/cutAction.do?method=addCutApplyForm&cutId="+cutId;
			}
			//删除申请
			toDeleteApplyCut=function(cutId){
            	window.location.href = "${ctx}/cutAction.do?method=deleteTempSaveCut&cutId="+cutId;
			}
		//取消割接流程
		toCancelForm=function(cutId){
			var url;
			if(confirm("确定要取消该割接流程吗？")){
				url="${ctx}/cutAction.do?method=cancelCutForm";
				var queryString="cutId="+cutId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			}
		}
		</script>
	</head>
	<body>
		<template:titile value="临时割接申请列表"/>
		<display:table name="sessionScope.list" id="cut" pagesize="18">
		<logic:notEmpty name="list">
			<bean:define id="sendUserId" name="cut"
				property="proposer" />
			<display:column property="workorder_id" title="工单号" headerClass="subject"  sortable="true"/>
			<display:column property="cut_name" title="割接名称" headerClass="subject"  sortable="true"/>
			<display:column property="charge_man" title="区域负责人" headerClass="subject"  sortable="true"/>
			<display:column property="cut_level" title="割接级别" headerClass="subject"  sortable="true"/>
			<display:column property="apply_date" title="申请时间" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="操作" >
				<a href="javascript:toAddApplyCut('<bean:write name="cut" property="id"/>')">完善申请</a> | 
				<a href="javascript:toDeleteApplyCut('<bean:write name="cut" property="id"/>')">删除</a>
					<c:if test="${sessionScope.LOGIN_USER.userID==sendUserId}">
						| <a href="javascript:toCancelForm('<bean:write name="cut" property="id"/>')">取消</a>
					</c:if>
			</display:column>
			</logic:notEmpty>
		</display:table>
	</body>
</html>
