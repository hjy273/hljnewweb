<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script type="text/javascript">
	function open_notify(NOTICE_ID, FORMAT) {
		URL = "${ctx}/NoticeAction.do?method=showNotice&id=" + NOTICE_ID
				+ "&preview=true";
		myleft = (screen.availWidth - 650) / 2;
		mytop = 100
		mywidth = 650;
		myheight = 500;
		if (FORMAT == "1") {
			myleft = 0;
			mytop = 0
			mywidth = screen.availWidth - 10;
			myheight = screen.availHeight - 40;
		}
		style = "height="
				+ myheight
				+ ",width="
				+ mywidth
				+ ",status=1,resizable=no,toolbar=no,menubar=no,location=no,scrollbars=yes,top="
				+ mytop + ",left=" + myleft + ",resizable=yes";
		window.open(URL, "read_news", style);
	}
	//-->
</script>
<style>
body,html {
	margin: 0px;
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

.title_right {
	width: 175px;
	height: 30px;
	float: right;
	background-image: url(../image/gg_right.jpg);
}

.title_text {
	font-weight: bold;
	height: 50px;
	line-height: 50px;
	text-align: center;
	font-size: 24px;
	margin-left: 5%;
	margin-right: 5%;
	border-bottom: 1px #3088AA solid;
	color: #BFAF2B;
}

.content {
	margin-left: 5%;
	margin-right: 5%;
	padding-top: 10px;
	padding-bottom: 10px;
	line-height: 24px;
	color: #016C96;
	border-bottom: 1px #3088AA solid;
}

.content_time {
	margin-left: 5%;
	margin-right: 5%;
	height: 20px;;
	line-height: 24px;
	text-align: center;
	color: #016C96;
}

.meet_time {
	margin-left: 5%;
	margin-right: 5%;
	height: 20px;;
	line-height: 24px;
	text-align: left;
	font-weight: bold;
	color: #016C96;
}

.Accessories {
	margin-left: 5%;
	margin-right: 5%;
	line-height: 24px;
	text-align: left;
	color: #016C96;
	border-bottom: 1px #3088AA solid;
	height: 80px;
}

.Close {
	margin-left: 5%;
	margin-right: 5%;
	line-height: 24px;
	text-align: center;
	color: #016C96;
	font-size: 14px;
	font-weight: bold;
}

.table1 {
	border-collapse: collapse;
	border-color: #3088AA;
}

.table1.td {
	border-color: #3088AA;
	border-collapse: collapse;
}
</style>
<body class="" topmargin="5">
	<div class="gg_Framework">
		<div class="title_bg">
			<div class="title_left">
				主页 > 会议信息列表
			</div>
			<div class="title_right"></div>
		</div>
		<div class="title_text">
			会议信息列表
		</div>
		<div class="content">
			本日有${list_size }次会议。
		</div>
		<c:if test="${noticelist!=null}">
			<c:forEach var="oneMeet" items="${noticelist}">
				<div class="content">
					<table border="1" width="90%" cellpadding="1" cellspacing="0"
						class="table1">
						<tr>
							<td width="15%">
								会议时间：
							</td>
							<td width="35%">
								<bean:write name="oneMeet" property="meetTime" />
								到
								<bean:write name="oneMeet" property="meetEndTime" />
							</td>
							<td width="15%">
								会议地点：
							</td>
							<td width="35%">
								<bean:write name="oneMeet" property="meetAddress" />
							</td>
						</tr>
						<tr>
							<td>
								与会人员
							</td>
							<td colspan="3">
								<bean:write name="oneMeet" property="meetPersonName" />
							</td>
						</tr>
						<tr>
							<td>
								会议标题
							</td>
							<td colspan="3">
								<bean:write name="oneMeet" property="title" />
							</td>
						</tr>
						<tr>
							<td>
								会议内容
							</td>
							<td colspan="3">
								${oneMeet.contentString}
							</td>
						</tr>
						<tr>
							<td>
								附件
							</td>
							<td colspan="3">
								<apptag:upload state="look" entityId="${notice.id}"
									entityType="NOTICE_CLOB"></apptag:upload>
							</td>
						</tr>
					</table>
				</div>
			</c:forEach>
		</c:if>
		<div class="Close">
			【
			<a href="javascript:window.close();">关闭窗口</a>】
		</div>
	</div>
</body>
</html>

