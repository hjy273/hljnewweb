<%@page contentType="text/html; charset=GBK"%>
<%
	String errmsgFlag = "2";
	String errmsg = "";
	if (request.getAttribute("errmsg") != null) {
		errmsgFlag = (String) request.getAttribute("errmsg");
	}

	if (errmsgFlag.equals("2")) {
		errmsg = "���ܳ���Ŀǰϵͳά������Ҫ�������˺�Ŀǰ״̬Ϊ\"��ͣ\"�����Ժ����Ի�����ϵϵͳ����Ա��";
	}

	if (errmsgFlag.equals("3")) {
		errmsg = "�����˺�Ŀǰ״̬Ϊ\"ֹͣ\"������ʹ�ø�ϵͳ������ϵϵͳ����Ա��";
	}
%>
<html>
	<head>
		<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<title>...::::��ӭʹ���ƶ�������·Ѳ�����ϵͳ::::...</title>
		<link href="css/login.css" rel="stylesheet" type="text/css">
	</head>
	<script language="javascript" src="js/no_right.js"
		type="text/javascript"></script>
	<script language="JavaScript">
	function openwin() {
		newwin = window
				.open(
						"",
						"newwindow",
						"toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");

		//loginBean.target = "newwindow";
		//loginBean.submit();

		newwin.moveTo(0, 0);
		newwin.resizeTo(screen.width, screen.height);

		//zsh 2004-10-22
		newwin.focus();
		//closes.Click();
	}

	//-->
</script>
	<body onload="loginBean.userid.focus()">
		<div class="login_bg">
			<div class="login_content">
				<div class="login_mc"></div>
				<form id="loginForm" name="loginForm" method="post"
					action="${ctx}/frames/login.do?method=login">
					<div class="login">
						<div class="login_left">
							<img src="images/user.jpg" width="57" height="16" />
							<label>
								<input type="text" name="userid" id="userid" class="login_input"
									style="width: 70px;" />
							</label>
							<img src="images/mm.jpg" width="49" height="16" />
							<label>
								<input type="password" name="password" id="password"
									class="login_input" style="width: 70px;" />
							</label>
						</div>
						<div class="login_right">
							<label>
								<input type="image" name="imageField" id="imageField"
									src="images/arrow_dl.jpg" />
							</label>
							<label>
								<input type="image" name="imageField2" id="imageField2"
									src="images/arrow_cz.jpg" />
							</label>
						</div>
					</div>
				</form>
				<div class="login_text">
					<font style="font-color: #FF0000;">
						<strong>��¼������ʾ��Ϣ��<%=errmsg%></strong>
					</font>
				</div>
				<div class="login_text">
					��Ȩ���С��й��ƶ�ͨ�ż��ű�����˾
				</div>
			</div>
		</div>
	</body>
</html>

