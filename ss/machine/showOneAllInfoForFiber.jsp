<!-- 全部内容（核心层与接入层SDH） -->
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
		<template:titile value="单个机房设备巡检核查内容"/>
		<template:formTable contentwidth="320" namewidth="280" th2="内容">
			<template:formTr name="类型">
				
				<logic:equal value="5" name="mobileTaskBean" property="machinetype">
					光交维护
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
			
			<template:formTr name="ODF面板图是否更新">
						<logic:equal value="1" name="bean" property="isUpdate" scope="request">
							是
						</logic:equal>
						<logic:equal value="0" name="bean" property="isUpdate" scope="request">
							否
						</logic:equal>
				</template:formTr>
				
				<template:formTr name="箱内清洁程度">
						<logic:equal value="2" name="bean" property="isClean" scope="request">
							优秀
						</logic:equal>
						<logic:equal value="1" name="bean" property="isClean" scope="request">
							良好
						</logic:equal>
						<logic:equal value="0" name="bean" property="isClean" scope="request">
							一般
						</logic:equal>
				</template:formTr>
				
				<template:formTr name="光交箱外环境清洁程度">
					<logic:equal value="2" name="bean" property="isFiberBoxClean" scope="request">
							优秀
						</logic:equal>
						<logic:equal value="1" name="bean" property="isFiberBoxClean" scope="request">
							良好
						</logic:equal>
						<logic:equal value="0" name="bean" property="isFiberBoxClean" scope="request">
							一般
						</logic:equal>
				</template:formTr>
				
				<template:formTr name="尾纤是否绑扎整齐">
					<logic:equal value="1" name="bean" property="isColligation" scope="request">
							是
						</logic:equal>
						<logic:equal value="0" name="bean" property="isColligation" scope="request">
							否
						</logic:equal>
				</template:formTr>
				
				<template:formTr name="光缆标识牌核查补贴">
					<logic:equal value="1" name="bean" property="isFiberCheck" scope="request">
						已核查补贴
					</logic:equal>
					<logic:equal value="0" name="bean" property="isFiberCheck" scope="request">
						未核查补贴
					</logic:equal>
				</template:formTr>
				
				<template:formTr name="尾纤标识牌核查补贴">
					<logic:equal value="1" name="bean" property="isTailFiberCheck" scope="request">
						已核查补贴
					</logic:equal>
					<logic:equal value="0" name="bean" property="isTailFiberCheck" scope="request">
						未核查补贴
					</logic:equal>
				</template:formTr>	
				
				<tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                 </tr>
				
				<template:formTr name="维护工程师核查情况">
					<bean:write name="pollingTaskBean" property="auditResult"/>
				</template:formTr>
				
				<template:formTr name="核查备注">
					<bean:write name="pollingTaskBean" property="checkRemark"/>
				</template:formTr>		
				
				<template:formSubmit>
					<tr>
						<td>
							<input type="hidden" name="tid" value="<bean:write name="tid"/>">
							<input type="hidden" name="pid" value="<bean:write name="pid"/>">
							<input type="hidden" name="type" value="<bean:write name="type"/>">
							<input type="button" value="返回" class="button" onClick="goBack('<bean:write name="tid"/>','<bean:write name="type"/>');">
						</td>
					</tr>
				</template:formSubmit>
		</template:formTable>
	</body>
</html>