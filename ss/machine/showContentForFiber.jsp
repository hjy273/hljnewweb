
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
		<template:titile value="单个机房设备详细信息"/>
			<template:formTable namewidth="280" contentwidth="320" th2="内容">
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
					<logic:equal value="1" name="bean" property="isColligation">
						是
					</logic:equal>
					<logic:equal value="0" name="bean" property="isColligation">
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
				
								
				<template:formSubmit>
					<td>
						<input type="button" value="返回" class="button" onClick="goback('<bean:write name="tid"/>','<bean:write name="type"/>','<bean:write name="flag"/>')">
					</td>
				</template:formSubmit>
			</template:formTable>
			</logic:present>
	</body>
</html>