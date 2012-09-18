<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>��ѯͳ��</title>
		<script type="text/javascript">
			/*function showQuery(queryForm){
				if(queryForm.style.display=="none"){
					queryForm.style.display="";
					showSpan.innerText="�� ��";
				}else{
					queryForm.style.display="none";
					showSpan.innerText="�� ʾ";
				}
			}*/
			//��ʾһ���ɵ���ϸ��Ϣ
		 	toViewForm=function (dispatchId){
		 		var url = "${ctx}/dispatchtask/dispatch_task.do?method=viewDispatchTask&dispatch_id="+dispatchId+"&rnd="+Math.random();
		    	location=url;
		 	}
		 	
		</script>
	</head>
	<body>
		<!--������ѯ��ʾ-->
		<template:titile value="�����ɵ���ѯ" />
		<div style="text-align: center;">
			<iframe
				src="${ctx}/dispatchtask/dispatch_task.do?method=viewDispatchTaskProcess&&forward=query_dispatch_task_state&&task_names=${task_names }"
				frameborder="0" id="processGraphFrame" height="70" scrolling="no"
				width="95%"></iframe>
		</div>
		<html:form
			action="/dispatchtask/query_dispatch_task.do?method=queryDispatchTaskTotal"
			styleId="queryForm" style="display:">
			<template:formTable>
				<template:formTr name="��������">
					<html:text property="sendtopic" styleClass="inputtext"
						style="width:200px;" value="${queryCondi.sendtopic}" />
				</template:formTr>
				<template:formTr name="���������">
					<html:select property="sendtype" styleId="sendtype" style="width: 200px" styleClass="inputtext">
						<html:option value="">����</html:option>
						<html:options property="code" labelProperty="lable" collection="dispatch_task_type_list"/>
					</html:select>
				</template:formTr>
				<template:formTr name="�ɵ�����">
					<html:select property="senddeptid" styleId="deptid" style="width:200px" styleClass="inputtext">
						<html:option value="">����</html:option>
						<html:options property="deptid" labelProperty="deptname" collection="deptinfo"/>
					</html:select>
				</template:formTr>
				<template:formTr name="�Ƿ�ȡ��">
					<html:select property="workstate" styleClass="inputtext" style="width:200px">
						<html:option value="">����</html:option>
						<html:option value="888">��</html:option>
						<html:option value="0">��</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="�� �� ��">
					<html:text property="sendusername" styleClass="inputtext"
						style="width:200px" value="${queryCondi.sendusername}" />
				</template:formTr>
				<template:formTr name="������">
					<html:select property="acceptdeptid" styleId="acceptdeptid" style="width:200px" styleClass="inputtext">
						<html:option value="">����</html:option>
						<html:options property="deptid" labelProperty="deptname" collection="deptinfo"/>
					</html:select>
				</template:formTr>
				<template:formTr name="ͳ�ƿ�ʼʱ��">
					<html:text property="begintime" styleId="begintime" style="width:150px" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',maxDate:'#F{$dp.$D(\'endtime\')||\'%y-%M-%d\'}'})" readonly="true"></html:text>
					--
					<html:text property="endtime" styleId="endtime" style="width:150px" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'begintime\')}',maxDate:'%y-%M-%d'})" readonly="true"></html:text>
				</template:formTr>
			</template:formTable>
			<logic:present name="queryCondi" property="taskStates">
				<logic:iterate id="oneTaskState" name="queryCondi"
					property="taskStates">
					<input id="taskStates_<bean:write name="oneTaskState" />"
						type="hidden" name="taskStates"
						value="<bean:write name="oneTaskState" />" />
				</logic:iterate>
			</logic:present>
			<div align="center">
				<input name="isQuery" value="true" type="hidden" />
				<html:submit property="action" styleClass="button">��ѯ</html:submit>
				<html:reset property="action" styleClass="button">����</html:reset>
			</div>
		</html:form>
		<template:displayHide styleId="queryForm"></template:displayHide>
		<!-- %@include file="/common/listhander.jsp"%-->
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<logic:notEmpty name="TOTAL_LIST">
			<display:table name="sessionScope.TOTAL_LIST"
				requestURI="${ctx}/dispatchtask/query_dispatch_task.do?method=queryDispatchTaskTotal"
				id="currentRowObject" pagesize="20" style="width:100%">
				<bean:define id="dispatchId" name="currentRowObject" property="id" />
				<bean:define id="serialNumber" name="currentRowObject"
					property="serialnumber" />
				<bean:define id="sendTopic" name="currentRowObject"
					property="sendtopic" />
				<display:column media="html" title="��ˮ��" sortable="true"
					style="width:120px">
					<a href="javascript:toViewForm('${dispatchId }')">${serialNumber}</a>
				</display:column>
				<display:column media="html" title="����" sortable="true" style="padding-left:5px;">
					<a href="javascript:toViewForm('${dispatchId }')">${sendTopic }</a>
				</display:column>
				<display:column property="sendtypelabel" title="����"
					style="width:80px" sortable="true" />
				<display:column property="departname" title="�ɵ�����"
					style="width:150px" sortable="true" />
				<display:column property="username" title="�ɵ���" style="width:60px"
					sortable="true" maxWords="8"/>
				<display:column property="processterm" title="��������"
					style="width:140px" sortable="true" />
			</display:table>
			<html:link
				action="/dispatchtask/query_dispatch_task.do?method=exportTotalResult">����ΪExcel�ļ�</html:link>
		</logic:notEmpty>
	</body>
	<script type="text/javascript">
		jQuery(document).ready(function() {
		    jQuery("#sendtype option[value='${queryCondi.sendtype}']").attr('selected', 'selected');
		    jQuery("#deptid option[value='${queryCondi.senddeptid}']").attr('selected', 'selected');
		    jQuery("#acceptdeptid option[value='${queryCondi.acceptdeptid}']").attr('selected', 'selected');
		});
	</script>
</html>
