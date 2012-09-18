<%@ page language="java" pageEncoding="GBK"%>
<%@include file="/wap/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>功能导航</title>

	</head>
	<body>
		<div>
			<a class="dept1">${LOGIN_USER.userName} 您好！</a><a class="dept2"
				href="${ctx}/wap/navigation.jsp">功能导航</a><a class="dept"
				href="javascript:exitSys();">退出</a><br />
		</div>
		<table width="100%" cellpadding="0" cellspacing="1"	style="text-align: center;">
			<tr>
				<td height="60px" valign="bottom">
					<a href="${ctx}/wap/index.do?method=indexInfo&&env=wap"><img
							src="${ctx}/images/wap/xjjk.png" border="0" /> <br />信息栏</a>
				</td>
				<td height="60px" valign="bottom">
					<a href="${ctx}/wap/patrolmonitor.do?method=pmindex&&env=wap"><img
							src="${ctx}/images/wap/xjjk.png" border="0" /> <br />巡检监控</a>
				</td>
				<td height="60px" valign="bottom">
					<a href="${ctx }/wap/hiddangerAction.do?method=index&&env=wap"><img
							src="${ctx}/images/wap/yhdf.png" border="0" /> <br />隐患盯防</a>
				</td>
			</tr>
			<tr>
				<td height="60px" valign="bottom">
					<a href="${ctx }/wap/safeguardTaskAction.do?method=index&&env=wap"><img
							src="${ctx}/images/wap/txbz.png" border="0" /> <br />通讯保障</a>
				</td>
				<td height="60px" valign="bottom">
					<a href="${ctx }/wap/cutAction.do?method=index&&env=wap"><img src="${ctx}/images/wap/xlgj.png" border="0" /> <br />线路割接</a>
				</td>
				<td height="60px" valign="bottom">
					<a href="${ctx }/wap/troubleAction.do?method=index&&env=wap"><img
							src="${ctx}/images/wap/gzcl.png" border="0" /> <br />故障处理</a>
				</td>
			</tr>
			<tr>
				<td height="60px" valign="bottom">
					<a href="${ctx }/wap/task.do?method=index&&env=wap"><img
							src="${ctx}/images/wap/rwpd.png" border="0" /> <br />任务派单</a>
				</td>
				<td height="60px" valign="bottom">
					<a href="${ctx}/wap/resourceAction.do?method=index&&env=wap"><img
							src="${ctx}/images/wap/zlcx.png" border="0" /> <br />资料查询</a>
				</td>
				<td height="60px" valign="bottom">
					<a href="${ctx }/wap/index.do?method=queryAddressBook&&env=wap"><img
							src="${ctx}/images/wap/txl.png" border="0" /> <br />通讯录</a>
				</td>
			</tr>
			<tr>
				<td height="60px" valign="bottom">
					<a
						href="${ctx }/wap/showTroubleQuotaAction.do?method=queryQuota&&env=wap"><img
							src="${ctx}/images/wap/gzzb.png" border="0" /> <br />维护指标</a>
				</td>
				<td height="60px" valign="bottom">
					<a
						href="${ctx }/wap/appraiseDailyAction.do?method=index&&env=wap"><img
							src="${ctx}/images/wap/gzcl.png" border="0" /> <br />日常考核</a>
				</td>
				<td height="60px" valign="bottom">
				</td>
			</tr>
		</table>
	</body>
</html>