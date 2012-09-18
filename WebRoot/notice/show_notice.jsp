<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>查看公告通知</title>
		<style>
body,html {
	margin: 5px;
	width: 99%;
}

.gg_Framework {
	margin: 5px 2%;
	width: 98%;
	background-color: #F6FCFF;
	border: 1px #016C96 solid;
	font-size: 12px;
}

.title_bg {
	height: 30px;
	background-color: #016C96;
	margin: 1px;
	text-align: center;
	font-size: 14px;
	font-weight: bold;
}

.title_left {
	width: 60%;
	float: left;
	font-size: 12px;
	line-height: 30px;
	color: #FFFFFF;
	text-align: left;
	padding-left: 10px;
	font-weight: normal;
	letter-spacing: 2px;
}

.title_text {
	font-weight: bold;
	height: 40px;
	text-align: center;
	font-size: 20px;
	margin-left: 5%;
	margin-right: 5%;
	border-bottom: 1px #3088AA solid;
	color: #BFAF2B;
}

.content_time {
	margin-left: 5%;
	margin-right: 5%;
	height: 10px;;
	line-height: 15px;
	text-align: center;
	color: #016C96;
}

.meet {
	margin-left: 5%;
	margin-right: 5%;
	text-align: left;
	font-weight: bold;
	color: #016C96;
}

.content {
	line-height: 20px;
	color: #016C96;
}

.Accessories {
	height: 20px;
	text-align: left;
	color: #016C96;
}

.Close {
	margin-left: 5%;
	margin-right: 5%;
	line-height: 20px;
	text-align: center;
	color: #016C96;
	font-size: 12px;
	font-weight: bold;
}
</style>
	</head>

	<body class="" topmargin="5">
		<table class="gg_Framework">
			<tr class="title_bg">
				<td class="title_left" colspan="3">
					主页 >
					<apptag:dynLabel objType="dic" id="${notice.type}" dicType="INFORMATION"></apptag:dynLabel>
					> 正文
				</td>
			</tr>
			<tr>
				<td colspan="3" class="title_text">
					<bean:write name="notice" property="title" />
				</td>
			</tr>
			<tr class="content_time">
				<td width="44%">
					发布人：
					<bean:write name="notice" property="issueperson" />
				</td>
				<td width="55%">
					&nbsp;发布时间：
					<fmt:formatDate value="${notice.issuedate}"
						pattern="yyyy-MM-dd HH:mm" />
				</td>
				<td width="1%">
					<!-- 
					发布对象：
					 -->
				</td>
			</tr>
			<logic:equal value="B21" name="notice" property="type">
				<tr class="meet">
					<td colspan="3">
						会议时间：
						<fmt:formatDate value="${notice.meetTime}"
							pattern="yyyy-MM-dd HH:mm:ss" />
						到
						<fmt:formatDate value="${notice.meetEndTime}"
							pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				<tr class="meet">
					<td colspan="3" class="meet_time">
						会议地点：
						<bean:write name="notice" property="meetAddress" />
					</td>
				</tr>
			</logic:equal>
			<tr height="10px">
				<td colspan="3"></td>
			</tr>
			<tr class="content">
				<td colspan="3" style="height: 400px;" valign="top">
					<c:out value="${notice.contentString}" escapeXml="false"></c:out>
					<apptag:image entityId="${notice.id}" entityType="NOTICE_CLOB"></apptag:image>
				</td>
			</tr>
			<tr height="10px">
				<td colspan="3"></td>
			</tr>
			<tr>
				<td colspan="3" class="Accessories">
					<apptag:upload state="look" entityId="${notice.id}"
						entityType="NOTICE_CLOB"></apptag:upload>
				</td>
			</tr>
			<c:if test="${model=='brower'}">
				<tr class="Close">
					<td colspan="3">
						【
						<a href="javascript:window.close();">关闭窗口</a>】
					</td>
				</tr>
			</c:if>
			<c:if test="${model=='wap'}">
				<tr class="Close">
					<td colspan="3">
						【
						<a href="javascript:history.back();">返回</a>】
					</td>
				</tr>
			</c:if>
		</table>

	</body>
</html>
