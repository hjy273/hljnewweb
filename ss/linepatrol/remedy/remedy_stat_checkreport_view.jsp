<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<html>
	<head>
		<title>���ձ���</title>
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
		<template:titile value="���ձ���" />
		<table width="750" bgcolor="#FFFFFF" align="center" cellpadding="0"
			cellspacing="0" class="tabout">
			<logic:notEmpty name="list">
				<tr>
					<td align="center">
						ʩ����λ��
					</td>
					<td><%=list.get(0)%></td>
					<td align="center">
						ʩ���·ݣ�
					</td>
					<td><%=list.get(2)%></td>
					<td align="center">
						ά������
					</td>
					<td><%=list.get(1)%></td>
				</tr>
				<tr>
					<td align="center">
						���
					</td>
					<td align="center">
						��������
					</td>
					<td align="center">
						������Ŀ����
					</td>
					<td align="center">
						�������
					</td>
					<td align="center" colspan="2">
						��ע˵��
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
						�ϼ�
					</td>
					<td colspan="3"><%=list.get(3)%></td>
				</tr>
			</logic:notEmpty>
		</table>
		<br />
		&nbsp;&nbsp;&nbsp;&nbsp;
		<logic:notEmpty name="list">
			<a href="javascript:exportList()">����ΪExcel�ļ�</a>
		</logic:notEmpty>
		<p align="center">
			<input name="btnBack" value="����" onclick="goBack();" type="button"
				class="button" />
		</p>
	</body>