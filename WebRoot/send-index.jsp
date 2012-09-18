<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>铁塔系统</title>
		<script type="text/javascript">
	
</script>
	</head>
	<body class="easyui-layout">
		<div id="topregion" region="north"
			style="height: 30px; padding: 0px; overflow: hidden;">
			<div class="top">
				<div class="top_left">
					<div class="top_sys_name">
						通用派单系统
					</div>
				</div>
			</div>
		</div>
		<div region="west" split="true"
			style="width: 15%; height: 550px; padding: 0px; overflow: hidden; float: left"
			title="属性操作">
			<div title="桌面服务" style="overflow: hidden; height: 150px">
				<ul id="tt1" class="easyui-tree">
					<li>
						<ul>
							<li>
								<span><a href="${ctx}/desktopAction_show.jspx"
									target="contentdiv">桌面服务测试</a> </span>
							</li>
						</ul>
					</li>
				</ul>
			</div>
			<div title="公告管理" style="overflow: hidden; height: 150px">
				<ul id="tt1" class="easyui-tree">
					<li>
						<ul>
							<li>
								<span><a href="${ctx}/NoticeAction.do?method=addForm"
									target="contentdiv">添加公告</a> </span>
							</li>
							<li>
								<span><a href="${ctx}/NoticeAction.do?method=queryNotice"
									target="contentdiv">公告列表</a> </span>
							</li>
						</ul>
					</li>
				</ul>
			</div>
			<div title="通用派单" style="overflow: hidden; height: 150px">
				<ul id="tt1" class="easyui-tree">
					<li>
						<ul>
							<li>
								<span><a href="${ctx}/sendTaskAction_input.jspx"
									target="contentdiv">创建派单</a> </span>
							</li>
							<li>
								<span><a href="${ctx}/sendTaskAction_list.jspx"
									target="contentdiv">查询派单</a> </span>
							</li>
							<li>
								<span><a href="${ctx}/sendTaskAction_waitHandleList.jspx"
									target="contentdiv">待办派单</a> </span>
							</li>
							<li>
								<span><a href="${ctx}/sendTaskAction_exportXml.jspx"
									target="contentdiv">导出派单</a> </span>
							</li>
							<li>
								<span><a
									href="${ctx}/sendTaskPhotoAction_photoQuery.jspx"
									target="contentdiv">照片查询</a> </span>
							</li>
							<li>
								<span><a
									href="${ctx}/sendTaskWorkStatisticAction_workStatistic.jspx"
									target="contentdiv">工作量统计</a> </span>
							</li>
						</ul>
					</li>
				</ul>
			</div>
			<div title="故障管理" style="overflow: hidden; height: 350px">
				<ul id="tt1" class="easyui-tree">
					<li>
						<ul>
							<li>
								<span><a
									href="${ctx}/faultAlertAction_list.jspx?parameter.isQuery="
									target="contentdiv">故障告警单列表</a> </span>
							</li>
							<li>
								<span><a
									href="${ctx}/faultAlertAction_unDispatchedList.jspx?parameter.isQuery="
									target="contentdiv">未派单故障告警单列表</a> </span>
							</li>
							<li>
								<span><a
									href="${ctx}/faultDispatchAction_list.jspx?parameter.isQuery="
									target="contentdiv">故障派单列表</a> </span>
							</li>
							<li>
								<span><a
									href="${ctx}/faultDispatchAction_waitHandledList.jspx?parameter.isQuery="
									target="contentdiv">待办故障派单列表</a> </span>
							</li>
							<!-- 
							<li>
								<span><a href="${ctx}/sendTaskAction_list.jspx"
									target="contentdiv">查询派单</a> </span>
							</li>
							<li>
								<span><a href="${ctx}/sendTaskAction_waitHandleList.jspx"
									target="contentdiv">待办派单</a> </span>
							</li>
							<li>
								<span><a
									href="${ctx}/sendTaskPhotoAction_photoQuery.jspx"
									target="contentdiv">照片查询</a> </span>
							</li>
							<li>
								<span><a
									href="${ctx}/sendTaskWorkStatisticAction_workStatistic.jspx"
									target="contentdiv">工作量统计</a> </span>
							</li>
							 -->
						</ul>
					</li>
				</ul>
			</div>
		</div>
		<div id="mainregion" region="center"
			style="width: 85%; overflow: hidden; padding: 0px;">
			<iframe name="contentdiv" id="ifrMain" frameborder="0"
				scrolling="yes"
				style="width: 95%; height: 500; overflow: hidden; padding: 10px;"
				src="">
			</iframe>
		</div>
	</body>
</html>