<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript">
	checkValid=function(addForm){
		return true;
	}
	addGoBack=function(){
		var url = "${ctx}/SparepartStorageAction.do?method=doQueryForRestored";
		self.location.replace(url);
	}
	</script>
	<body>
		<br/>
		<template:titile value="�鿴���������Ϣ" />
			<template:formTable namewidth="150" contentwidth="350" th1="��Ŀ" th1="����">
				<template:formTr name="��ⱸ��">
					<bean:write name="one_storage" property="sparePartName" />
				</template:formTr>
				<template:formTr name="�������к�">
					<bean:write name="one_storage" property="serialNumber" />
				</template:formTr>
				<template:formTr name="����λ��">
					<bean:write name="one_storage" property="storagePosition" />
				</template:formTr>
				<template:formTr name="�������ڲ���">
					<bean:write name="one_storage" property="departName" />
				</template:formTr>
				<template:formTr name="������ǰ����λ��">
					<bean:write name="one_storage" property="storagePosition" />
				</template:formTr>
				<logic:notEmpty name="taken_out_position">
					<template:formTr name="������Դ">
						<%=(String)request.getAttribute("taken_out_position") %>
					</template:formTr>
				</logic:notEmpty>
				<logic:notEmpty name="init_storage_position">
					<template:formTr name="����������λ��">
						<%=(String)request.getAttribute("init_storage_position") %>
					</template:formTr>
				</logic:notEmpty>
				<template:formTr name="�������">
					<bean:write name="one_storage" property="storageNumber" />
				</template:formTr>
				<template:formTr name="����״̬">
					<logic:equal value="01" name="one_storage" property="sparePartState">
						<img src="${ctx}/images/sparepartstate/01.gif" alt="�ƶ�����">
					</logic:equal>
					<logic:equal value="02" name="one_storage" property="sparePartState">
						<img src="${ctx}/images/sparepartstate/02.gif" alt="��ά����">
					</logic:equal>
					<logic:equal value="03" name="one_storage" property="sparePartState">
						<img src="${ctx}/images/sparepartstate/03.gif" alt="����">
					</logic:equal>
					<logic:equal value="04" name="one_storage" property="sparePartState">
						<img src="${ctx}/images/sparepartstate/04.gif" alt="����">
					</logic:equal>
					<logic:equal value="05" name="one_storage" property="sparePartState">
						<img src="${ctx}/images/sparepartstate/05.gif" alt="����">
					</logic:equal>
				</template:formTr>
				<template:formTr name="������">
					<bean:write name="one_storage" property="storagePerson" />
				</template:formTr>
				<template:formTr name="��ע">
					<bean:write name="one_storage" property="storageRemark" />
				</template:formTr>
				
				<template:formSubmit>
					<td>
						<html:button property="action" styleClass="button"
							onclick="addGoBack()">����</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
			
			<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px;text-align: center;" >
				<tr>
					<td >˵����
						<img src="${ctx}/images/sparepartstate/01.gif">��ʾ���ƶ����á�״̬��
						<img src="${ctx}/images/sparepartstate/02.gif">��ʾ����ά���á�״̬��
						<img src="${ctx}/images/sparepartstate/03.gif">��ʾ�����С�״̬��
						<img src="${ctx}/images/sparepartstate/04.gif">��ʾ�����ޡ�״̬��
						<img src="${ctx}/images/sparepartstate/05.gif">��ʾ�����ϡ�״̬��
					</td>
				</tr>
			</table>
	</body>
</html>
