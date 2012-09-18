<!-- 代维填写巡检内容（光交维护） -->
<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.machine.beans.PollingContentBean;"%>

<html>
	<head>
		<title></title>
		
		<script type="text/javascript">
			function checkInfo() {
				addContentFormId.submit();
			}
			
			function goBack(type,tid) {
				var url = "PollingTaskAction.do?method=gobackToPrePageForRestore&type="+type+"&tid="+tid;
				window.location.href=url;
			}
		</script>
	</head>
	
	<body>
		<br>
		<template:titile value="添加单个光交设备巡检内容"/>
		<template:formTable contentwidth="320" namewidth="280" th2="内容">
			<template:formTr name="类型">
				<logic:equal value="1" name="mobileTaskBean" property="machinetype" scope="request">
					核心层
				</logic:equal>
				
				<logic:equal value="2" name="mobileTaskBean" property="machinetype">
					接入层SDH
				</logic:equal>
				<logic:equal value="5" name="mobileTaskBean" property="machinetype">
					光交
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
		</template:formTable>
		
		<hr>
		<template:formTable contentwidth="320" namewidth="280">
			<html:form action="PollingConFiberAction.do?method=addPollingConFiber"
			styleId="addContentFormId">
				<template:formTr name="ODF面板图是否更新">
					<select  class="inputtext" style="width: 200px;" name="isUpdate">
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</template:formTr>
				
				<template:formTr name="箱内清洁程度">
					<select  class="inputtext" style="width: 200px;" name="isClean">
						<option value="2">优秀</option>
						<option value="1">良好</option>
						<option value="0">一般</option>
					</select>
				</template:formTr>
				
				<template:formTr name="光交箱外环境清洁程度">
					<select  class="inputtext" style="width: 200px;" name="isFiberBoxClean">
						<option value="2">优秀</option>
						<option value="1">良好</option>
						<option value="0">一般</option>
					</select>
				</template:formTr>
				
				<template:formTr name="尾纤是否绑扎整齐">
					<select  class="inputtext" style="width: 200px;" name="isColligation">
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</template:formTr>
				
				<template:formTr name="光缆标识牌核查补贴">
					<select  class="inputtext" style="width: 200px;" name="isFiberCheck">
						<option value="1">已核查补贴</option>
						<option value="0">未核查补贴</option>
					</select>
				</template:formTr>
				
				<template:formTr name="尾纤标识牌核查补贴">
					<select  class="inputtext" style="width: 200px;" name="isTailFiberCheck">
						<option value="1">已核查补贴</option>
						<option value="0">未核查补贴</option>
					</select>
				</template:formTr>
				
				<template:formSubmit>
					<td>
						<input type="hidden" value="<bean:write name="pid" scope="request"/>" name="pid">
						<input type="hidden" value="<bean:write name="type" scope="request"/>" name="type">
						<input type="hidden" value="<bean:write name="tid" scope="request"/>" name="tid">
						<html:button property="action" styleClass="button"
							onclick="checkInfo()">提交</html:button>
					</td>
					<td>
						<input type="button" value="返回" onClick="goBack('<bean:write name="mobileTaskBean" property="machinetype"/>','<bean:write name="tid"/>')" class="button">
					</td>
					
					<td>
						<html:reset property="action" styleClass="button"
							onclick="">重置	</html:reset>
					</td>
				</template:formSubmit>
				
			</html:form>
			
		</template:formTable>
	</body>
</html>