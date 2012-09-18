<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<html>
	<head>
		<title>验收报告</title>
		<link href="${ctx}/css/linepatrol_css.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript">
			exportList=function(){
		     	var url="${ctx}/remedyReportAction.do?method=exportReport";
				self.location.replace(url);
			}
			toViewForm=function(idValue){
			 	var url = "<%=request.getContextPath()%>/remedy_apply.do?method=view&&power=52004&&to_page=view_square_remedy_apply&&apply_id="+idValue;
			    self.location.replace(url);
			}  
			goBack=function(){
				history.back();
			}
		</script>
	</head>
	<body>
		<br />
		<%
			List list = (List) request.getAttribute("list");
		%>
		<template:titile value="验收报告" />
		<table width="750" bgcolor="#FFFFFF" align="center" cellpadding="0"
			cellspacing="0" class="tabout">
			<logic:notEmpty name="list">
				<tr>
					<td align="center">
						施工单位：
					</td>
					<td><%=list.get(0)%></td>
					<td align="center">
						施工月份：
					</td>
					<td><%=list.get(2)%></td>
					<td align="center">
						维护区域：
					</td>
					<td><%=list.get(1)%></td>
				</tr>
				<tr>
					<td align="center">
						序号
					</td>
					<td align="center">
						审批表编号
					</td>
					<td align="center">
						工程项目名称
					</td>
					<td align="center">
						结算费用
					</td>
					<td align="center" colspan="2">
						备注说明
					</td>
				</tr>
				<%
					pageContext.setAttribute("data_list", list.get(4));
					int i = 1;
				%>
				<logic:iterate id="oneData" name="data_list">
					<tr>
						<td align="center"><%=i%></td>
						<td align="center">
							<a
								href="javascript:toViewForm('<bean:write property="id" name="oneData" />')">
								<bean:write property="remedycode" name="oneData" /> </a>
						</td>
						<td align="center">
							<bean:write property="projectname" name="oneData" />
						</td>
						<td align="center">
							<bean:write property="totalfee" name="oneData" />
						</td>
						<td align="center" colspan="2">
							<bean:write property="remark" name="oneData" />
						</td>
					</tr>
					<%
						i++;
					%>
				</logic:iterate>
				<tr>
					<td colspan="3" align="center">
						合计
					</td>
					<td colspan="3"><%=list.get(3)%></td>
				</tr>
			</logic:notEmpty>
		</table>
		<br />
		&nbsp;&nbsp;&nbsp;&nbsp;
		<logic:notEmpty name="list">
			<a href="javascript:exportList()">导出为Excel文件</a>
		</logic:notEmpty>
		<p align="center">
			<input name="btnBack" value="返回" onclick="goBack();" type="button"
				class="button" />
		</p>
	</body>