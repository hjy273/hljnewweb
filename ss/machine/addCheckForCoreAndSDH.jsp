<!-- 移动核查填写核查内容（核心层与接入层SDH） -->
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
					alert("请输入核查情况");
					auditResultEle.focus();
					return false;
				}
				if(valCharLength(auditResultEle.value)>400) {
					alert("你输入的核查情况内容过长，请重新输入");
					auditResultEle.focus();
					return false;
				}
			
				var checkRemarkEle = document.getElementById('checkRemark');
				if(valCharLength(checkRemarkEle.value)>400)  {
					alert("你输入的核查备注内容过长，请重新输入");
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
		<template:titile value="填写单个机房设备巡检核查内容"/>
		<template:formTable contentwidth="320" namewidth="280" th2="内容">
			<template:formTr name="类型">
				<logic:equal value="1" name="mobileTaskBean" property="machinetype" scope="request">
					核心层
				</logic:equal>
				
				<logic:equal value="2" name="mobileTaskBean" property="machinetype">
					接入层SDH
				</logic:equal>
			</template:formTr>
			
			<template:formTr name="主题">
				<bean:write name="mobileTaskBean" property="title" scope="request"/>
			</template:formTr>
			
			<template:formTr name="代维公司">
				<bean:write name="mobileTaskBean" property="conname" scope="request"/>
			</template:formTr>
			
			<template:formTr name="执行人">
				<bean:write name="mobileTaskBean" property="userconname" scope="request"/>
			</template:formTr>
			
			<template:formTr name="执行日期">
				<bean:write name="mobileTaskBean" property="executetime" scope="request"/>
			</template:formTr>
			
			<template:formTr name="检查人">
				<bean:write name="mobileTaskBean" property="checkusername" scope="request"/>
			</template:formTr>
			
			<template:formTr name="备注">
				<bean:write name="mobileTaskBean" property="remark" scope="request"/>
			</template:formTr>
			
			<tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                 </tr>
			
			<template:formTr name="设备表面是否清洁">
						<logic:equal value="1" name="bean" property="isClean" scope="request">
							清洁
						</logic:equal>
						<logic:equal value="0" name="bean" property="isClean" scope="request">
							不清洁
						</logic:equal>
				</template:formTr>
				
				<template:formTr name="设备表面温度">
					<bean:write name="bean" property="obveTemperature"/>
				</template:formTr>
				
				<template:formTr name="机柜顶端指示灯状态">
					<logic:equal value="1" name="bean" property="isToppilotlamp">
						亮
					</logic:equal>
					<logic:equal value="0" name="bean" property="isToppilotlamp">
						不亮
					</logic:equal>
				</template:formTr>
				
				<template:formTr name="单板指示灯状态">
					<logic:equal value="1" name="bean" property="isVeneerpilotlamp">
						亮
					</logic:equal>
					<logic:equal value="0" name="bean" property="isVeneerpilotlamp">
						不亮
					</logic:equal>
				</template:formTr>
				
				<template:formTr name="设备防尘网是否清洁">
					<logic:equal value="1" name="bean" property="isDustproofClean" scope="request">
						清洁
					</logic:equal>
					<logic:equal value="0" name="bean" property="isDustproofClean" scope="request">
						不清洁
					</logic:equal>
				</template:formTr>
				
				<template:formTr name="风扇运行状态">
					<bean:write name="bean" property="fanState"/>
				</template:formTr>
				
				<template:formTr name="机房地面清洁">
					<bean:write name="bean" property="machineFloorClean"/>
				</template:formTr>
				
				<template:formTr name="机房温度">
					<bean:write name="bean" property="machineTemperature"/>
				</template:formTr>
				
				<template:formTr name="机房湿度">
					<bean:write name="bean" property="machineHumidity"/>
				</template:formTr>
				
				<template:formTr name="DDF架/走线架线缆绑扎">
					<bean:write name="bean" property="ddfColligation"/>
				</template:formTr>
				
				<template:formTr name="尾纤保护">
					<bean:write name="bean" property="fiberProtect"/>
				</template:formTr>
				
				<template:formTr name="DDF架线缆接头检查紧固">
					<bean:write name="bean" property="ddfCableFast"/>
				</template:formTr>
				
				<template:formTr name="ODF架尾纤接头检查紧固">
					<bean:write name="bean" property="odfInterfacefast"/>
				</template:formTr>
				
				<template:formTr name="ODF/设备侧尾纤标签核查补贴">
					<bean:write name="bean" property="odfLabelCheck"/>
				</template:formTr>
				
				<template:formTr name="DDF架线缆标签核查补贴">
					<bean:write name="bean" property="ddfCabelCheck"/>
				</template:formTr>
		</template:formTable>
		<hr>
		<template:formTable contentwidth="320" namewidth="280">
			<form action="PollingTaskAction.do?method=addCheck" method="post" onsubmit="return checkInfo();">
				<template:formTr name="维护工程师核查情况">
					<textarea  cols="36" rows="4" name="auditResult" id="auditResult" 
								title="请不要超过200个汉字或者400个英文字符!" style="width:270"
								class="textarea"></textarea>
				</template:formTr>
				
				<template:formTr name="备注">
					<textarea  cols="36" rows="4" name="checkRemark" id="checkRemark" 
								title="请不要超过200个汉字或者400个英文字符!" style="width:270"
								class="textarea"></textarea>
				</template:formTr>
				
				<template:formSubmit>
					<tr>
						<td>
							<input type="hidden" name="tid" value="<bean:write name="tid"/>">
							<input type="hidden" name="pid" value="<bean:write name="pid"/>">
							<input type="hidden" name="type" value="<bean:write name="type"/>">
							<input type="submit" value="提交" class="button">
							<input type="button" value="返回" class="button" onclick="backForCheck('<bean:write name="tid"/>','<bean:write name="type"/>');">
							<input type="reset" value="重置" class="button">
						</td>
					</tr>
				</template:formSubmit>
			</form>
		</template:formTable>
		
	</body>
</html>