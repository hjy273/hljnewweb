<!-- �ƶ��˲���д�˲����ݣ�FSO�� -->
<%@include file="/common/header.jsp"%>

<html>
	<head>
		<script type="text/javascript">
			 function valCharLength(Value){
		      var j=0;
		      var s = Value;
		      for   (var   i=0;   i<s.length;   i++)
		      {
		        if   (s.substr(i,1).charCodeAt(0)>255)   j   =   j   +   2;
		        else   j++
		      }
		      return j;
		    }
			
			function checkInfo() {
				var auditResultEle = document.getElementById('auditResult');
				if(auditResultEle.value.length == 0) {
					alert("������˲����");
					auditResultEle.focus();
					return false;
				}
				if(valCharLength(auditResultEle.value)>400) {
					alert("������ĺ˲�������ݹ���������������");
					auditResultEle.focus();
					return false;
				}
			
				var checkRemarkEle = document.getElementById('checkRemark');
				if(valCharLength(checkRemarkEle.value)>400)  {
					alert("������ĺ˲鱸ע���ݹ���������������");
					checkRemarkEle.focus();
					return false;
				}
			}
			
			function backForCheck(tid,type) {
				var url = "PollingTaskAction.do?method=backForCheck&tid="+tid+"&type="+type;
				window.location.href=url;
			}
		</script>
	</head>
	
	<body>
		<br>
		<template:titile value="��ӵ��������豸�˲�����"/>
		<template:formTable contentwidth="320" namewidth="280" th2="����">
			<template:formTr name="����">
				�����FSO
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
                 
             <template:formTr name="�豸�����Ƿ����">
				<logic:equal value="1" name="bean" property="isClean">
					���
				</logic:equal>
				<logic:equal value="0" name="bean" property="isClean">
					�����
				</logic:equal>
			</template:formTr>
			
			<template:formTr name="�豸�����¶�">
				<bean:write name="bean" property="obveTemperature"/>
			</template:formTr>
			
			<template:formTr name="�豸ָʾ��״̬">
				<logic:equal value="1" name="bean" property="isMachinePilotlamp">
						��
					</logic:equal>
					<logic:equal value="0" name="bean" property="isMachinePilotlamp">
						����
					</logic:equal>
			</template:formTr>
				
			<template:formTr name="�⹦�ʲ�ѯ">
					<bean:write name="bean" property="searchLightPower"/>
			</template:formTr>
			
			<template:formTr name="β��/��Դ�߰���">
					<bean:write name="bean" property="powerColligation"/>
			</template:formTr>
			
			<template:formTr name="��Դ��/β�˱�ǩ�˲鲹��">
					<bean:write name="bean" property="powerLabelCheck"/>
			</template:formTr>
		</template:formTable>
		<hr>
		<template:formTable contentwidth="320" namewidth="280">
			<form action="PollingTaskAction.do?method=addCheck" method="post" onsubmit="return checkInfo();">
				<template:formTr name="ά������ʦ�˲����">
					<textarea  cols="36" rows="4" name="auditResult" id="auditResult" 
								title="�벻Ҫ����200�����ֻ���400��Ӣ���ַ�!" style="width:270"
								class="textarea"></textarea>
				</template:formTr>
				
				<template:formTr name="��ע">
					<textarea  cols="36" rows="4" name="checkRemark" id="checkRemark" 
								title="�벻Ҫ����200�����ֻ���400��Ӣ���ַ�!" style="width:270"
								class="textarea"></textarea>
				</template:formTr>
				
				<template:formSubmit>
					<tr>
						<td>
							<input type="hidden" name="tid" value="<bean:write name="tid"/>">
							<input type="hidden" name="pid" value="<bean:write name="pid"/>">
							<input type="hidden" name="type" value="<bean:write name="type"/>">
							<input type="submit" value="�ύ" class="button">
							<input type="button" value="����" class="button" onclick="backForCheck('<bean:write name="tid"/>','<bean:write name="type"/>');">
							<input type="reset" value="����" class="button">
						</td>
					</tr>
				</template:formSubmit>
			</form>
		</template:formTable>
	</body>
</html>