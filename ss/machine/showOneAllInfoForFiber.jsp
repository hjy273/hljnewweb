<!-- ȫ�����ݣ����Ĳ�������SDH�� -->
<%@include file="/common/header.jsp"%>

<html>
	<head>
		<title></title>
		
		<script type="text/javascript">
			function goBack(tid,type) {
				var url = "PollingTaskAction.do?method=back&tid="+tid+"&type="+type;
				window.location.href=url;
			}
		</script>
	</head>
	
	<body>
		<br>
		<template:titile value="���������豸Ѳ��˲�����"/>
		<template:formTable contentwidth="320" namewidth="280" th2="����">
			<template:formTr name="����">
				
				<logic:equal value="5" name="mobileTaskBean" property="machinetype">
					�⽻ά��
				</logic:equal>
			</template:formTr>
			
			<template:formTr name="����">
				<bean:write name="mobileTaskBean" property="title" scope="request"/>
			</template:formTr>
			
			<template:formTr name="��ά��˾">
				<bean:write name="mobileTaskBean" property="conname" scope="request"/>
			</template:formTr>
			
			<template:formTr name="ִ����">
				<bean:write name="mobileTaskBean" property="userconname" scope="request"/>
			</template:formTr>
			
			<template:formTr name="ִ������">
				<bean:write name="mobileTaskBean" property="executetime" scope="request"/>
			</template:formTr>
			
			<template:formTr name="�����">
				<bean:write name="mobileTaskBean" property="checkusername" scope="request"/>
			</template:formTr>
			
			<template:formTr name="��ע">
				<bean:write name="mobileTaskBean" property="remark" scope="request"/>
			</template:formTr>
			
			<tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                 </tr>
			
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
					<logic:equal value="1" name="bean" property="isColligation" scope="request">
							��
						</logic:equal>
						<logic:equal value="0" name="bean" property="isColligation" scope="request">
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
				
				<tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                 </tr>
				
				<template:formTr name="ά������ʦ�˲����">
					<bean:write name="pollingTaskBean" property="auditResult"/>
				</template:formTr>
				
				<template:formTr name="�˲鱸ע">
					<bean:write name="pollingTaskBean" property="checkRemark"/>
				</template:formTr>		
				
				<template:formSubmit>
					<tr>
						<td>
							<input type="hidden" name="tid" value="<bean:write name="tid"/>">
							<input type="hidden" name="pid" value="<bean:write name="pid"/>">
							<input type="hidden" name="type" value="<bean:write name="type"/>">
							<input type="button" value="����" class="button" onClick="goBack('<bean:write name="tid"/>','<bean:write name="type"/>');">
						</td>
					</tr>
				</template:formSubmit>
		</template:formTable>
	</body>
</html>