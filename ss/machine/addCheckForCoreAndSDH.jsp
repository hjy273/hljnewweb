<!-- �ƶ��˲���д�˲����ݣ����Ĳ�������SDH�� -->
<%@include file="/common/header.jsp"%>

<html>
	<head>
		<title></title>
		
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
		<template:titile value="��д���������豸Ѳ��˲�����"/>
		<template:formTable contentwidth="320" namewidth="280" th2="����">
			<template:formTr name="����">
				<logic:equal value="1" name="mobileTaskBean" property="machinetype" scope="request">
					���Ĳ�
				</logic:equal>
				
				<logic:equal value="2" name="mobileTaskBean" property="machinetype">
					�����SDH
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
			
			<template:formTr name="�豸�����Ƿ����">
						<logic:equal value="1" name="bean" property="isClean" scope="request">
							���
						</logic:equal>
						<logic:equal value="0" name="bean" property="isClean" scope="request">
							�����
						</logic:equal>
				</template:formTr>
				
				<template:formTr name="�豸�����¶�">
					<bean:write name="bean" property="obveTemperature"/>
				</template:formTr>
				
				<template:formTr name="���񶥶�ָʾ��״̬">
					<logic:equal value="1" name="bean" property="isToppilotlamp">
						��
					</logic:equal>
					<logic:equal value="0" name="bean" property="isToppilotlamp">
						����
					</logic:equal>
				</template:formTr>
				
				<template:formTr name="����ָʾ��״̬">
					<logic:equal value="1" name="bean" property="isVeneerpilotlamp">
						��
					</logic:equal>
					<logic:equal value="0" name="bean" property="isVeneerpilotlamp">
						����
					</logic:equal>
				</template:formTr>
				
				<template:formTr name="�豸�������Ƿ����">
					<logic:equal value="1" name="bean" property="isDustproofClean" scope="request">
						���
					</logic:equal>
					<logic:equal value="0" name="bean" property="isDustproofClean" scope="request">
						�����
					</logic:equal>
				</template:formTr>
				
				<template:formTr name="��������״̬">
					<bean:write name="bean" property="fanState"/>
				</template:formTr>
				
				<template:formTr name="�����������">
					<bean:write name="bean" property="machineFloorClean"/>
				</template:formTr>
				
				<template:formTr name="�����¶�">
					<bean:write name="bean" property="machineTemperature"/>
				</template:formTr>
				
				<template:formTr name="����ʪ��">
					<bean:write name="bean" property="machineHumidity"/>
				</template:formTr>
				
				<template:formTr name="DDF��/���߼����°���">
					<bean:write name="bean" property="ddfColligation"/>
				</template:formTr>
				
				<template:formTr name="β�˱���">
					<bean:write name="bean" property="fiberProtect"/>
				</template:formTr>
				
				<template:formTr name="DDF�����½�ͷ������">
					<bean:write name="bean" property="ddfCableFast"/>
				</template:formTr>
				
				<template:formTr name="ODF��β�˽�ͷ������">
					<bean:write name="bean" property="odfInterfacefast"/>
				</template:formTr>
				
				<template:formTr name="ODF/�豸��β�˱�ǩ�˲鲹��">
					<bean:write name="bean" property="odfLabelCheck"/>
				</template:formTr>
				
				<template:formTr name="DDF�����±�ǩ�˲鲹��">
					<bean:write name="bean" property="ddfCabelCheck"/>
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