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
		<template:titile value="��д�����⽻�豸Ѳ��˲�����"/>
		<template:formTable contentwidth="320" namewidth="280" th2="����">
			<template:formTr name="����">
				<logic:equal value="1" name="mobileTaskBean" property="machinetype" scope="request">
					���Ĳ�
				</logic:equal>
				
				<logic:equal value="2" name="mobileTaskBean" property="machinetype">
					�����SDH
				</logic:equal>
				
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
				
				
		</template:formTable>
		<hr>
		<template:formTable contentwidth="320" namewidth="280">
			<form action="PollingTaskAction.do?method=addCheck" method="post" onSubmit="return checkInfo();">
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
							<input type="button" value="����" class="button" onClick="backForCheck('<bean:write name="tid"/>','<bean:write name="type"/>');">
							<input type="reset" value="����" class="button">
						</td>
					</tr>
				</template:formSubmit>
			</form>
		</template:formTable>
		
	</body>
</html>