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
					alert("请选择类型!");
					layerEle.focus();
					return;
				}
				
				var contractorIdEle = document.getElementById('contractorId');
				if(contractorIdEle.value == 0) {
					alert("请选择代维公司!");
					contractorIdEle.focus();
					return;
				}
				
				var fileEle = document.getElementById('file');
				if( fileEle.value.length==0 ) {
					alert("请选择您要导入的机房设备的EXCEL文件");
					return;
				}
				var filePath = fileEle.value;
				var startIndex = filePath.lastIndexOf(".");
				var endIndex = filePath.length;
				var fileType = filePath.substr(startIndex, endIndex);
				if(fileType != '.xls') {
					alert("您选择的文件格式不正确");
					return;
				}
				equipmentFormId.submit();
			}
		</script>
	</head>
	
	<body>
		<br>
		<template:titile value="增加机房设备"/>
		<html:form action="
" enctype="multipart/form-data" styleId="equipmentFormId" method="post">
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
				
				<template:formTr name="请选择模板">
					<input type="file" name="file" style="width:245px" class="inputtext" id="file">
				</template:formTr>
				
				<template:formSubmit>
					<td>
						<input type="button" class="button" value="提交" onclick="checkInfo()">
					</td>
					<td>
						<input type="reset" class="button" value="重置">
					</td>
					<td>
						<input type="button" class="button" value="模板下载" onclick="downloadFile()">
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
			
	</body>
</html>