
<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.machine.beans.PollingContentBean;"%>
<html>
	<head>
		<title></title>
	
		<script type="text/javascript">
			function goback(id,type,flag) {
				var url = "PollingTaskAction.do?method=backToPrePage&id="+id+"&type="+type+"&flag="+flag;
				window.location.href=url;
			}		
		</script>	
	</head>
	<body>
		<br>
		<logic:present name="bean" scope="request">
		<template:titile value="���������豸��ϸ��Ϣ"/>
			<template:formTable namewidth="280" contentwidth="320" th2="����">
				<template:formTr name="ODF���ͼ�Ƿ����">
						<logic:equal value="1" name="bean" property="isUpdate" scope="request">
							��
						</logic:equal>
						<logic:equal value="0" name="bean" property="isUpdate" scope="request">
							��
						</logic:equal>
				</template:formTr>
				
				<template:formTr name="�������̶�">
						<logic:equal value="2" name="bean" property="isClean" scope="request">
							����
						</logic:equal>
						<logic:equal value="1" name="bean" property="isClean" scope="request">
							����
						</logic:equal>
						<logic:equal value="0" name="bean" property="isClean" scope="request">
							һ��
						</logic:equal>
				</template:formTr>
				
				<template:formTr name="�⽻���⻷�����̶�">
					<logic:equal value="2" name="bean" property="isFiberBoxClean" scope="request">
							����
						</logic:equal>
						<logic:equal value="1" name="bean" property="isFiberBoxClean" scope="request">
							����
						</logic:equal>
						<logic:equal value="0" name="bean" property="isFiberBoxClean" scope="request">
							һ��
						</logic:equal>
				</template:formTr>
				
				<template:formTr name="β���Ƿ��������">
					<logic:equal value="1" name="bean" property="isColligation">
						��
					</logic:equal>
					<logic:equal value="0" name="bean" property="isColligation">
						��
					</logic:equal>
				</template:formTr>
				
				<template:formTr name="���±�ʶ�ƺ˲鲹��">
					<logic:equal value="1" name="bean" property="isFiberCheck" scope="request">
						�Ѻ˲鲹��
					</logic:equal>
					<logic:equal value="0" name="bean" property="isFiberCheck" scope="request">
						δ�˲鲹��
					</logic:equal>
				</template:formTr>
				
				<template:formTr name="β�˱�ʶ�ƺ˲鲹��">
					<logic:equal value="1" name="bean" property="isTailFiberCheck" scope="request">
						�Ѻ˲鲹��
					</logic:equal>
					<logic:equal value="0" name="bean" property="isTailFiberCheck" scope="request">
						δ�˲鲹��
					</logic:equal>
				</template:formTr>
				
								
				<template:formSubmit>
					<td>
						<input type="button" value="����" class="button" onClick="goback('<bean:write name="tid"/>','<bean:write name="type"/>','<bean:write name="flag"/>')">
					</td>
				</template:formSubmit>
			</template:formTable>
			</logic:present>
	</body>
</html>