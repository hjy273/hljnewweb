<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script type="text/javascript">
			checkInfo=function() {	
				equipmentFormId.submit();
			}
		</script>
	</head>
	
	<body>
		<br>
		<%
			request.getSession().removeAttribute("eq_querysql");
		 %>
		<template:titile value="查询机房设备"/>
		<html:form action="EquipmentInfoAction.do?method=showQueryEqu" styleId="equipmentFormId" method="post">
			
			<template:formTable namewidth="150" contentwidth="250">
				<template:formTr name="类型">
					<select name="layer" style="width:245px;" class="inputtext" id="layer">
						<option value="0">
							请选择类型
						</option>
						<option value="1">
							核心层
						</option>
						<option value="2">
							接入层SDH
						</option>
						<option value="3">
							接入层微波
						</option>
						<option value="4">
							接入层FSO
						</option>
						<option value="5">
							光交维护
						</option>
					</select>
				</template:formTr>
				
				<template:formTr name="代维公司">
					<select class="inputtext" name="contractorId" style="width:245px;" id="contractorId">
						<option value="0">
							请选择代维
						</option>
						<logic:notEmpty name="conDeptList" scope="request">
							<logic:iterate id="conDeptListid" name="conDeptList"
								scope="request">
								<option
									value="<bean:write name="conDeptListid" property="contractorID"/>">
									<bean:write name="conDeptListid" property="contractorName" />
								</option>
							</logic:iterate>
						</logic:notEmpty>
					</select>
				</template:formTr>
				
				<template:formTr name="设备名称">
					<input type="text" name="equipmentName" style="width:245px" class="inputtext" id="equipmentName">
				</template:formTr>
				
				<template:formSubmit>
					<td>
						<input type="button" class="button" value="查询" onclick="checkInfo()">
					</td>
					<td>
						<input type="reset" class="button" value="重置">
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
			
	</body>
</html>