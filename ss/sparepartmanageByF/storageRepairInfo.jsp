<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript" src="./js/prototype.js"></script>
    <script type="text/javascript" src="./js/json2.js"></script>
	<script type="text/javascript">
		function goback(){
			history.back();
		}
	</script>
	<body>
		<br>
		<div id="paretpart">
			<template:titile value="�ƶ���˾���" />
			<html:form action="/SparepartStorageAction.do?method=ageinToStorage"
				styleId="addForm">
				<template:formTable namewidth="150" contentwidth="350">
					<input type="hidden" name="tid" id="tid" value="<bean:write name="storage" property="tid"/>"/>
					<input type="hidden" name="sparePartId" id="sparePartId" value="<bean:write name="storage" property="sparePartId"/>"/>
					<input type="hidden" name="serialNumber" id="serialNumber" value="<bean:write name="storage" property="serialNumber"/>"/>
					<template:formTr name="��������">
	         			<bean:write name="SparepartBaseInfoBean" property="productFactory"/>
	         		</template:formTr>	         		
	         		<template:formTr name="��ⱸ��">
	         			<bean:write name="SparepartBaseInfoBean" property="sparePartName"/>
	         		</template:formTr>
					<template:formTr name="�����ͺ�" >
	         			<bean:write name="SparepartBaseInfoBean" property="sparePartModel"/>
	         		</template:formTr>
					<template:formTr name="�������к�">
						<bean:write name="storage" property="serialNumber"/>
					</template:formTr>
					
					<template:formTr name="�������ڲ���">
						<input name="departId" value="<%=(String)request.getAttribute("user_dept_id") %>" type="hidden" />
						<%=(String)request.getAttribute("user_dept_name") %>
					</template:formTr>
						<html:hidden property="storageNumber" styleClass="inputtext" value="1" name="stNum"/>
					<template:formTr name="������">
						<%=(String) request.getAttribute("user_name")%>
						<input name="storagePerson" type="hidden"
							value="<%=(String) request.getAttribute("user_id")%>"
							class="inputtext" style="width:250;"/>
					</template:formTr>
					<template:formTr name="����λ��">
	         			<bean:write name="positon" />
					</template:formTr>
					<template:formTr name="��ⱸע">
						<bean:write name="storage" property="storageRemark"/>
					</template:formTr>
	
					<template:formSubmit>
						<td>
							<input type="button" class="button" value="����"  onclick="goback();">
						</td>
											
					</template:formSubmit>
				</template:formTable>
			</html:form>
		</div>
		
	</body>
</html>
