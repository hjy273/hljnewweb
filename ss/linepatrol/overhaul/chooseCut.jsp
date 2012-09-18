<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script type="text/javascript">
		function choose(){
			var flag = false;
			var html = "<table width=100% border=1>";
				html += "<tr>";
				html += "<td align='center' width='10%'>选择</td>";
				html += "<td align='center' width='50%'>割接名称</td>";
				html += "<td align='center' width='15%'>参考费用</td>";
				html += "<td align='center' width='25%'>割接费用</td>";
				html += "</tr>";
			var choosed = document.getElementsByName("cutid");
			for(var i=0 ; i<choosed.length ; i++){
				if(choosed[i].checked){
					flag = true;
					html += "<tr>";
					html += setReadOnly($(choosed[i].value).innerHTML);
					html += "<td align='center'><input type='text' name='cut,"+choosed[i].value+"' cut='true' id='cut,"+choosed[i].value+"' value='"+choosed[i].feeValue+"' style='width:70px' onchange='caculate()' class='inputtext required validate-integer-float-double' /></td>";
					html += "</tr>";
				}
			}
			html += "</table>";
			
			if(flag){
				parent.$('cut').innerHTML = html;
				parent.close();
			}else{
				alert('没有选择任何割接');
				return false;
			}
		}
		function setReadOnly(html){
			html = html.replace('{*}','checked=true onclick="return false"');
			return html;
		}
		function viewCut(id){
			var url = "${ctx}/cutQueryStatAction.do?method=viewQueryCut&type=view&cutId="+id;
			window.location.href = url;
		}
		function init(){
			var choosed = document.getElementsByName("cutid");
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
	<template:titile value="割接选择" />
	<html:form action="/overHaulQueryAction.do?method=queryCut" styleId="form">
		<template:formTable namewidth="220" contentwidth="250">
			<html:hidden property="applyId" value="${OverHaulApply.id}"/>
			<input type="hidden" id="p" name="p" value="<%=request.getParameter("p") %>" />
			<template:formTr name="割接名称">
				<html:text property="projectName" style="width:100px"/>
			</template:formTr>
			<template:formTr name="割接时间">
				<html:text property="startTime" styleClass="Wdate" style="width:102px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" readonly="true"/>
				 -- <html:text property="endTime" styleClass="Wdate validate-date-after-startTime" style="width:102px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" readonly="true"/>
				 <html:submit property="action" styleClass="button">查询</html:submit>
			</template:formTr>
			<template:formTr name="割接列表">
				<c:if test="${!empty cutList}">
					<table width=100% border=0>
						<tr>
							<td align="center" width="10%">选择</td>
							<td align="center" width="45%">割接名称</td>
							<td align="center" width="20%">参考费用</td>
						</tr>
						<c:forEach var="cut" items="${cutList}">
							<c:set var="check" value="false" />
							<c:set var="fee" value="" />
							<c:if test="${!empty OverHaulApply.overHaulCuts}">
								<c:forEach var="c" items="${OverHaulApply.overHaulCuts}">
									<c:if test="${c.cutId eq cut.id}">
										<c:set var="fee" value="${c.cutFee}" />
									</c:if>
								</c:forEach>
							</c:if>
							<tr id="${cut.id}">
								<td align="center"><input type="checkbox" {*} name="cutid" value="${cut.id}" feeValue="${fee}" /></td>
								<td><a href="javascript:viewCut('${cut.id}')">${cut.cutName}</a></td>
								<td>${cut.budget}元<input type='hidden' name='cutref,${cut.id}' id='cutref,${cut.id}' value='${cut.budget}' /></td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</template:formTr>
			<template:formSubmit>
				<c:if test="${!empty cutList}">
					<td>
						<html:button property="action" styleClass="button" onclick="choose()">提交</html:button>
					</td>
				</c:if>
					<td>
						<html:button property="action" styleClass="button" onclick="parent.close()">关闭</html:button>
					</td>
			</template:formSubmit>
		</template:formTable>
	</html:form>
</body>