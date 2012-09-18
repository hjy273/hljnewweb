<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>公司列表</title>
		<script type="text/javascript">
			function setDefaultValue(){
				var companyIdOrg = $('companyIdOrg').value;
				if(companyIdOrg!=""){
					var companyIds = companyIdOrg.split(",");
					for(var i=0;i<companyIds.length;i++){
						var companyIdVal=companyIds[i];
						var companyIdSel=document.getElementsByName('companyIdSel');
						for(var j=0;j<companyIdSel.length;j++){
							if(companyIdVal==companyIdSel[j].value){
								companyIdSel[j].checked=true;
								continue;
							}
						}
					}
				}
			}
			function checkForm(){
				addCompanyFinish.submit();
			}
			function closeWindow(){
				parent.win.close();
			}
		</script>
	</head>
	<body onload="setDefaultValue()">
		<html:form action="/questAction.do?method=addCompanyFinish" enctype="multipart/form-data" styleId="addCompanyFinish">
			<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<input type="hidden" name="companyIdOrg" id="companyIdOrg" value="${companyId }"/>
					<td class="tdulleft" style="width:20%;">公司列表：</td>
					<td class="tdulright">
						<c:forEach items="${companys}" var="company">
							<input type="checkbox" name="companyIdSel" value="${company.id }"/>${company.object }<br/>
						</c:forEach>
					</td>
				</tr>
			</table>
			<div align="center" style="height:40px">
				<html:button property="action" styleClass="button" onclick="checkForm()">提交</html:button> &nbsp;&nbsp;
				<html:button property="action" styleClass="button" onclick="closeWindow()">关闭</html:button>
			</div>
		</html:form>
	</body>
</html>
