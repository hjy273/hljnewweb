<%@ page language="java" pageEncoding="GBK"%>
<%@include file="/wap/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>���ܵ���</title>

	</head>
	<body>
		<div>
			<a class="dept1">${LOGIN_USER.userName} ���ã�</a><a class="dept2"
				href="${ctx}/wap/navigation.jsp">���ܵ���</a><a class="dept"
				href="javascript:exitSys();">�˳�</a><br />
		</div>
		<table width="100%" cellpadding="0" cellspacing="1"	style="text-align: center;">
			<tr>
				<td height="60px" valign="bottom">
					<a href="${ctx}/wap/index.do?method=indexInfo&&env=wap"><img
							src="${ctx}/images/wap/xjjk.png" border="0" /> <br />��Ϣ��</a>
				</td>
				<td height="60px" valign="bottom">
					<a href="${ctx}/wap/patrolmonitor.do?method=pmindex&&env=wap"><img
							src="${ctx}/images/wap/xjjk.png" border="0" /> <br />Ѳ����</a>
				</td>
				<td height="60px" valign="bottom">
					<a href="${ctx }/wap/hiddangerAction.do?method=index&&env=wap"><img
							src="${ctx}/images/wap/yhdf.png" border="0" /> <br />��������</a>
				</td>
			</tr>
			<tr>
				<td height="60px" valign="bottom">
					<a href="${ctx }/wap/safeguardTaskAction.do?method=index&&env=wap"><img
							src="${ctx}/images/wap/txbz.png" border="0" /> <br />ͨѶ����</a>
				</td>
				<td height="60px" valign="bottom">
					<a href="${ctx }/wap/cutAction.do?method=index&&env=wap"><img src="${ctx}/images/wap/xlgj.png" border="0" /> <br />��·���</a>
				</td>
				<td height="60px" valign="bottom">
					<a href="${ctx }/wap/troubleAction.do?method=index&&env=wap"><img
							src="${ctx}/images/wap/gzcl.png" border="0" /> <br />���ϴ���</a>
				</td>
			</tr>
			<tr>
				<td height="60px" valign="bottom">
					<a href="${ctx }/wap/task.do?method=index&&env=wap"><img
							src="${ctx}/images/wap/rwpd.png" border="0" /> <br />�����ɵ�</a>
				</td>
				<td height="60px" valign="bottom">
					<a href="${ctx}/wap/resourceAction.do?method=index&&env=wap"><img
							src="${ctx}/images/wap/zlcx.png" border="0" /> <br />���ϲ�ѯ</a>
				</td>
				<td height="60px" valign="bottom">
					<a href="${ctx }/wap/index.do?method=queryAddressBook&&env=wap"><img
							src="${ctx}/images/wap/txl.png" border="0" /> <br />ͨѶ¼</a>
				</td>
			</tr>
			<tr>
				<td height="60px" valign="bottom">
					<a
						href="${ctx }/wap/showTroubleQuotaAction.do?method=queryQuota&&env=wap"><img
							src="${ctx}/images/wap/gzzb.png" border="0" /> <br />ά��ָ��</a>
				</td>
				<td height="60px" valign="bottom">
					<a
						href="${ctx }/wap/appraiseDailyAction.do?method=index&&env=wap"><img
							src="${ctx}/images/wap/gzcl.png" border="0" /> <br />�ճ�����</a>
				</td>
				<td height="60px" valign="bottom">
				</td>
			</tr>
		</table>
	</body>
</html>