<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<style type="text/css">
		a:link{color:#2764B5;text-decoration:none}
		a:visited{color:#2764B5;text-decoration:none}
		a:active{color:#2764B5;text-decoration:underline}
		a:hover{color:#FF9900;text-decoration:none}
		a.tt:link{color:#FFFFFF;text-decoration:none}
		a.tt:visited{color:#FFFFFF;text-decoration:none}
		a.tt:active{color:#FFFFFF;text-decoration:underline}
		a.tt:hover{color:#000000;text-decoration:none}
		</style>
		<script language="JavaScript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
		queryForm=function(){
			var form=document.forms[0];
			form.action="${ctx}/load_hide_dangers.do?method=loadHideDangers";
			form.submit();
		}
		viewForm=function(id){
			var actionUrl="${ctx}/hiddangerQueryAction.do?method=view&param=window&&id="+id;
			//location=actionUrl+"&"+queryString+"&rnd="+Math.random();
			myleft=(screen.availWidth-650)/2;
			mytop=100
			mywidth=650;
			myheight=500;
			window.open(actionUrl,"read_hide_dangers","height="+myheight+",width="+mywidth+",status=1,resizable=no,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft+",resizable=yes");
		}
		finishForm=function(){
			var form=document.forms[0];
			form.action="${ctx}/load_hide_dangers.do?method=addHideDangers";
			form.submit();
		}
		closeForm=function(){
			parent.document.getElementById("${span_id}HideDlg").click();
		}
		</script>
		<style>
td {
	font-size: 12px;
	line-heigh: 30px;
	border-color: #A2D0FF;
}
</style>
	</head>
	<body bgcolor="">
		<html:form method="post"
			action="/load_hide_dangers.do?method=addHideDangers">
			<table width="100%" border="1" cellspacing="0" cellpadding="0"
				style="border-collapse: collapse; border-color: #A2D0FF;">
				<tr>
					<td width="25%" height="25"
						style="text-align: center; vertical-align: middle;">
						查询条件：
					</td>
					<td width="75%" style="text-align: left; padding-left: 5px;">
						<input type="hidden" name="input_name"
							value="<c:out value="${input_name}" />" />
						<input type="hidden" name="accidents"
							value="<c:out value="${accidents}" />" />
						<input type="hidden" name="span_id"
							value="<c:out value="${span_id}" />" />
						<input type="text" name="query_value" style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<td width="25%" height="25"
						style="text-align: center; vertical-align: middle;">
						查询时间段：
					</td>
					<td width="75%" style="text-align: left; padding-left: 5px;">
						<input type="text" id="begin_time" name="begin_time" value="${begin_time}"
							readonly="readonly" class="Wdate"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})"
							style="width: 90" />
						至
						<input type="text" id="end_time" name="end_time" value="${end_time}"
							readonly="readonly" class="Wdate"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})"
							style="width: 90" />
					</td>
				</tr>
				<tr>
					<td colspan="2" height="25" style="text-align: center;">
						<input name="btnQuery" type="button" id="btnQuery" value="查询"
							onclick="queryForm();" />
					</td>
				</tr>
				<tr>
					<td height="25"
						style="text-align: center; vertical-align: top; padding-top: 10px;">
						隐患列表：
					</td>
					<td
						style="height: 270px; text-align: left; vertical-align: top; padding-top: 10px;">
						<logic:present name="hide_danger_list">
							<logic:iterate id="one_hide_danger" name="hide_danger_list">
								<logic:equal value="1" name="one_hide_danger"
									property="is_checked">
									<input type="radio" name="hide_danger"
										value="<bean:write name="one_hide_danger" property="id" />-<bean:write name="one_hide_danger" property="name" />"
										checked />
								</logic:equal>
								<logic:equal value="0" name="one_hide_danger"
									property="is_checked">
									<input type="radio" name="hide_danger"
										value="<bean:write name="one_hide_danger" property="id" />-<bean:write name="one_hide_danger" property="name" />" />
								</logic:equal>
								<a href="javascript:viewForm('<bean:write name="one_hide_danger" property="id" />');">
									<bean:write name="one_hide_danger" property="hide_danger_info" />
								</a>
								<br />
							</logic:iterate>
						</logic:present>
					</td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="25" colspan="2" style="text-align: center;">
						<input name="btnFinish" type="button" id="btnFinish" value="完成"
							onclick="finishForm();" />
						<input name="btnClose" type="button" id="btnClose" value="关闭"
							onclick="closeForm();" />
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
