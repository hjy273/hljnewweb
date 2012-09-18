<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script type="text/javascript">
		function choose(){
			var flag = false;
			var html = "<table width=100% border=1>";
				html += "<tr>";
				html += "<td align='center' width='10%'>ѡ��</td>";
				html += "<td align='center' width='50%'>��������</td>";
				html += "<td align='center' width='15%'>�ο�����</td>";
				html += "<td align='center' width='25%'>���̷���</td>";
				html += "</tr>";
			var choosed = document.getElementsByName("projectid");
			for(var i=0 ; i<choosed.length ; i++){
				if(choosed[i].checked){
					flag = true;
					html += "<tr>";
					html += setReadOnly($(choosed[i].value).innerHTML);
					html += "<td align='center'><input type='text' name='project,"+choosed[i].value+"' id='project,"+choosed[i].value+"' value='"+choosed[i].feeValue+"' style='width:70px' project='true' onchange='caculate()' class='inputtext required validate-integer-float-double' /></td>";
					html += "</tr>";
				}
			}
			html += "</table>";
			if(flag){
				parent.$('project').innerHTML = html;
				parent.close();
			}else{
				alert('û��ѡ���κι���');
				return false;
			}
		}
		function setReadOnly(html){
			html = html.replace('{*}','checked=true onclick="return false"');
			//html = html.replace('{*}','onclick="return false"');
			return html;
		}
		function viewProject(id){
			var url = "${ctx}/project/remedy_apply.do?method=view&type=view&apply_id="+id;
			window.location.href = url;
		}
		function init(){
			var choosed = document.getElementsByName("projectid");
			var p = $('p').value;
			for(var i=0 ; i<choosed.length ; i++){
				if(p.indexOf(choosed[i].value) != -1){
					choosed[i].checked = true;
				}
			}
		}
	</script>
</head>
<body onload="init()">
	<template:titile value="����ѡ��" />
	<html:form action="/overHaulQueryAction.do?method=queryProject" styleId="form">
		<template:formTable namewidth="220" contentwidth="250">
			<html:hidden property="applyId" value="${OverHaulApply.id}"/>
			<input type="hidden" id="p" name="p" value="<%=request.getParameter("p") %>" />
			<template:formTr name="��������">
				<html:text property="projectName" style="width:100px"/>
			</template:formTr>
			<template:formTr name="����ʱ��">
				<html:text property="startTime" styleClass="Wdate" style="width:102px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" readonly="true"/>
				 -- <html:text property="endTime" styleClass="Wdate validate-date-after-startTime" style="width:102px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" readonly="true"/>
				 <html:submit property="action" styleClass="button">��ѯ</html:submit>
			</template:formTr>
			<template:formTr name="�����б�">
				<c:if test="${!empty projectList}">
					<table width=100% border=1>
						<tr>
							<td align="center" width="10%">ѡ��</td>
							<td align="center" width="45%">��������</td>
							<td align="center" width="20%">�ο�����</td>
						</tr>
						<c:forEach var="project" items="${projectList}">
						<tr id="${project.id}">
							<c:set var="check" value="false" />
							<c:set var="fee" value="" />
							<c:if test="${!empty OverHaulApply.overHaulProjects}">
								<c:forEach var="p" items="${OverHaulApply.overHaulProjects}">
									<c:if test="${p.projectId eq project.id}">
										<c:set var="check" value="true" />
										<c:set var="fee" value="${p.projectFee}" />
									</c:if>
								</c:forEach>
							</c:if>
							<td align="center"><input type="checkbox" name="projectid" {*} value="${project.id}" feeValue="${fee}" <c:if test="${check}">checked</c:if> /></td>
							<td><a href="javascript:viewProject('${project.id}')">${project.projectName}</a></td>
							<td>${project.totalFee}Ԫ<input type='hidden' name='projectref,${project.id}' id='projectref,${project.id}' value="${project.totalFee}"/></td>
						</tr>
						</c:forEach>
					</table>
				</c:if>
			</template:formTr>
			<template:formSubmit>
				<c:if test="${!empty projectList}">
					<td>
						<html:button property="action" styleClass="button" onclick="choose()">�ύ</html:button>
					</td>
				</c:if>
					<td>
						<html:button property="action" styleClass="button" onclick="parent.close()">�ر�</html:button>
					</td>
			</template:formSubmit>
		</template:formTable>
	</html:form>
</body>