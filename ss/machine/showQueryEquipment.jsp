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
		<template:titile value="��ѯ�����豸"/>
		<html:form action="EquipmentInfoAction.do?method=showQueryEqu" styleId="equipmentFormId" method="post">
			
			<template:formTable namewidth="150" contentwidth="250">
				<template:formTr name="����">
					<select name="layer" style="width:245px;" class="inputtext" id="layer">
						<option value="0">
							��ѡ������
						</option>
						<option value="1">
							���Ĳ�
						</option>
						<option value="2">
							�����SDH
						</option>
						<option value="3">
							�����΢��
						</option>
						<option value="4">
							�����FSO
						</option>
						<option value="5">
							�⽻ά��
						</option>
					</select>
				</template:formTr>
				
				<template:formTr name="��ά��˾">
					<select class="inputtext" name="contractorId" style="width:245px;" id="contractorId">
						<option value="0">
							��ѡ���ά
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
				
				<template:formTr name="�豸����">
					<input type="text" name="equipmentName" style="width:245px" class="inputtext" id="equipmentName">
				</template:formTr>
				
				<template:formSubmit>
					<td>
						<input type="button" class="button" value="��ѯ" onclick="checkInfo()">
					</td>
					<td>
						<input type="reset" class="button" value="����">
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
			
	</body>
</html>