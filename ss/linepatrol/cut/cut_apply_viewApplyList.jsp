<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<html>
	<head>
		<title>显示割接申请列表</title>
		<script type="text/javascript">
			//查看申请
			toViewCutForm=function(cutId){
            	window.location.href = "${ctx}/cutAction.do?method=viewCut&cutId="+cutId;
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
		<template:titile value="割接申请列表"/>
		<display:table name="sessionScope.list" id="cut" pagesize="18">
			<display:column property="workorder_id" title="工单号" headerClass="subject"  sortable="true"/>
			<display:column property="cut_level" title="割接级别" headerClass="subject"  sortable="true"/>
			<display:column property="cut_begintime" title="割接开始时间" headerClass="subject"  sortable="true"/>
			<display:column property="cut_endtime" title="割接结束时间" headerClass="subject"  sortable="true"/>
			<display:column property="cut_builder" title="施工单位" headerClass="subject"  sortable="true"/>
			<display:column property="cut_state" title="割接状态" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="操作" >
				<a href="javascript:toViewCutForm('<bean:write name="cut" property="id"/>')">查看</a>
					<c:if test="${sessionScope.LOGIN_USER.userID==sendUserId&&cut.cut_state!='999'}">
						| <a href="javascript:toCancelForm('${cut.id }')">取消</a>
					</c:if>
			</display:column>
		</display:table>
	</body>
</html>
