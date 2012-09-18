<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
	<script type="text/javascript">
		exportList=function(){
			var url="<%=request.getContextPath()%>/MonthStateAction.do?method=exportMaterialMonthStat";
			self.location.replace(url);
		}
	</script>
	<head>
		<%
			List Intendance = (List) request.getSession().getAttribute("Intendance");
			List RegionPrincipal = (List) request.getSession().getAttribute("RegionPrincipal");
			List MaterialCount = (List) request.getSession().getAttribute("MaterialCount");
			List baseInfo = (List) request.getSession().getAttribute("baseInfo");
			List MaterialName = (List) request.getSession().getAttribute("MaterialName");
			int count = MaterialName.size();
		%>
		<script type="text/javascript" language="javascript">
			function test(count){
				var tableStyle = document.getElementById("tableStyle");
				tableStyle.style.width=800+150*count;
			}
		</script>
	</head>
	<body onload="test(<%=count %>)">
		<br />
		<template:titile value="材料使用月统计报表" />
		<div id="tableStyle">
		<table cellpadding="1" cellspacing="1" class="tabout" bgcolor="white">
			<tr class="trcolor" style="background-color:#F0EBD6">
				<td width="98" style="text-align:center;">审批表编号</td>
				<td width="98" style="text-align:center;">项目名称</td>
				<td width="98" style="text-align:center;">项目负责人</td>
				<td width="98" style="text-align:center;">发生地点</td>
				<td width="98" style="text-align:center;">维护时间</td>
				<td width="98" style="text-align:center;">区域负责人</td>
				<td width="98" style="text-align:center;">监理单位</td>
				<td width="98" style="text-align:center;">代维单位</td>
				<logic:iterate id="name" name="MaterialName">
					<td width="148" style="text-align:center;">
						<bean:write name='name' property='material_name' />
					</td>
				</logic:iterate>
			</tr>
			<%
				System.out.println(baseInfo.size() + " baseInfo.size();");
				for (int i = 0; i < baseInfo.size(); i++) {
			%>
			<tr	class="<%if(i%2==0){out.print("trcolor");}else{out.print("trwhite");} %>" height="25">
				<td>
					<%=((BasicDynaBean) baseInfo.get(i)).get("remedycode").toString()%>
				</td>
				<td>
					<%=((BasicDynaBean) baseInfo.get(i)).get("projectname").toString()%>
				</td>
				<td>
					<%=((BasicDynaBean) baseInfo.get(i)).get("creator").toString()%>
				</td>
				<td>
					<%=((BasicDynaBean) baseInfo.get(i)).get("remedyaddress").toString()%>
				</td>
				<td>
					<%=((BasicDynaBean) baseInfo.get(i)).get("remedydate").toString()%>
				</td>
				<%
					if (RegionPrincipal.size() == 0) {
				%>
				<td>
					空
				</td>
				<%
					} else {
				%>
				<td>
					<%=RegionPrincipal.get(i).toString()%>
				</td>
				<%
					}
				%>
				<%
					if (Intendance.size() == 0) {
				%>
				<td>
					空
				</td>
				<%
					} else {
				%>
				<td>
					<%=Intendance.get(i).toString()%>
				</td>
				<%
					}
				%>
				<td>
					<%=((BasicDynaBean) baseInfo.get(i)).get("contractorname").toString()%>
				</td>
				<%
					int num = 0;
						for (int j = 0; j < MaterialName.size(); j++) {//3
							List list = (List) MaterialCount.get(i);
							//System.out.println("*******************************"+i);
							for (int a = 0; a < list.size(); a++) {//1,2
								HashMap map = (HashMap) list.get(a);
								Object materId = map.get(((BasicDynaBean) MaterialName
										.get(j)).get("materialid").toString());
								//  System.out.println(materId+" materId=========");
								if (materId == null) {
									if (list.size() < MaterialName.size()) {
										int sum = (num + list.size())
												- MaterialName.size();
										//System.out.println("sum=== "+sum+" num:"+num);
										if (sum >= 0) {
											continue;
										}
										num++; // System.out.println("m============ "+num);
				%>
				<td>0</td>
				<%
					}
				%>
				<%
					} else {
				%>
				<td>
					<%=map.get(((BasicDynaBean) MaterialName.get(j))
					.get("materialid").toString()).toString()%>
				</td>
				<%
					}
				%>
				<%
					}
				%>
				<%
					}
				%>
			</tr>
			<%
				}
			%>
		</table>
	</div>
	<table border="0" cellpadding="15" cellspacing="0" width="100%" style="border: 0px">
		<tr>
			<td style="text-align: center;">
				<input name="action" class="buttondl" value="导出为Excel"
					onclick="exportList()" type="button" />
			</td>
		</tr>
	</table>
	</body>
</html>
