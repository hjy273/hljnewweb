<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script type="text/javascript">
			downloadFile=function() {
				window.location.href = "EquipmentInfoAction.do?method=downTemplet";
			}
			
			checkInfo=function() {
				var layerEle = document.getElementById('layer');
				if(layerEle.value == 0) {
					alert("��ѡ������!");
					layerEle.focus();
					return;
				}
				
				var contractorIdEle = document.getElementById('contractorId');
				if(contractorIdEle.value == 0) {
					alert("��ѡ���ά��˾!");
					contractorIdEle.focus();
					return;
				}
				
				var fileEle = document.getElementById('file');
				if( fileEle.value.length==0 ) {
					alert("��ѡ����Ҫ����Ļ����豸��EXCEL�ļ�");
					return;
				}
				var filePath = fileEle.value;
				var startIndex = filePath.lastIndexOf(".");
				var endIndex = filePath.length;
				var fileType = filePath.substr(startIndex, endIndex);
				if(fileType != '.xls') {
					alert("��ѡ����ļ���ʽ����ȷ");
					return;
				}
				equipmentFormId.submit();
			}
		</script>
	</head>
	
	<body>
		<br>
		<template:titile value="���ӻ����豸"/>
		<html:form action="
" enctype="multipart/form-data" styleId="equipmentFormId" method="post">
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
				
				<template:formTr name="��ѡ��ģ��">
					<input type="file" name="file" style="width:245px" class="inputtext" id="file">
				</template:formTr>
				
				<template:formSubmit>
					<td>
						<input type="button" class="button" value="�ύ" onclick="checkInfo()">
					</td>
					<td>
						<input type="reset" class="button" value="����">
					</td>
					<td>
						<input type="button" class="button" value="ģ������" onclick="downloadFile()">
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
			
	</body>
</html>