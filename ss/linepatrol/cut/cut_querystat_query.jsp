<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>��������ѯ</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
		<script type="text/javascript">
			function judgeWhetherChecked(value){
				for(var i=0; i<value.length; i++){
					if(value[i].checked==true){
						return true;
					}
				}
				return false;
			}
			function submitForm(){
				var flag = true;
				/*var cutLevels=document.getElementsByName("cutLevels");
				var cableLevels=document.getElementsByName("cabletype");
				var states=document.getElementsByName("states");
				if(!judgeWhetherChecked(cutLevels)){
					alert("��Ӽ�������ѡ��һ�");
					flag=false;
					return;
				}
				if(!judgeWhetherChecked(cableLevels)){
					alert("���¼�������ѡ��һ�");
					flag=false;
					return;
				}
				if(!judgeWhetherChecked(states)){
					alert("���״̬����ѡ��һ�");
					flag=false;
					return;
				}
				if(document.getElementById("beginTime").value.length==0){
					alert("��ʼʱ�䲻��Ϊ�գ�");
					document.getElementById("beginTime").focus();
					flag=false;
					return;
				}
				if(document.getElementById("endTime").value.length==0){
					alert("����ʱ�䲻��Ϊ�գ�");
					document.getElementById("endTime").focus();
					flag=false;
					return;
				}*/
				if(flag==true){
					document.getElementById('submitForm1').submit();
				}
			}
		</script>
	</head>
	<body>
		<c:if test="${sessionScope.operator=='query'}">
			<template:titile value="��������ѯ" />
		</c:if>
		<c:if test="${sessionScope.operator=='stat'}">
			<template:titile value="���ͳ�Ʋ�ѯ" />
		</c:if>
		<div style="text-align: center;">
			<iframe
				src="${ctx}/cutAction.do?method=viewCutProcess&&forward=query_cut_state&&task_names=${task_names }"
				frameborder="0" id="processGraphFrame" height="75" scrolling="no"
				width="95%"></iframe>
		</div>
		<html:form action="/cutQueryStatAction.do?method=queryCutInfo"
			styleId="submitForm1">
			<table border="1" align="center" cellspacing="0" cellpadding="1"
				class="tabout" width="80%">
				<c:if test="${sessionScope.depttype=='1'}">
				<tr class="trcolor">
					<td class="tdulleft" style="width: 20%;">
						��ά��˾��
					</td>
					<td class="tdulright">
						<html:select property="contractorId" styleClass="inputtext" style="width:140px">
							<html:option value="">ȫ��</html:option>
							<html:options property="contractorid" labelProperty="contractorname" collection="list"/>
						</html:select>
					</td>
				</tr>
				</c:if>
				<c:if test="${sessionScope.operator=='query'}">
					<tr class="trcolor">
						<td class="tdulleft" style="width: 20%;">
							�Ƿ�ȡ����
						</td>
						<td class="tdulright">
							<select name="cancelState" class="inputtext" style="width:140px">
								<option value="">����</option>
								<option value="999">��</option>
								<option value="0">��</option>
							</select>
						</td>
					</tr>
				</c:if>
				<tr class="trcolor">
					<td class="tdulleft" style="width: 20%;">
						��Ӽ���
					</td>
					<td class="tdulright">
						<html:multibox property="cutLevels" value="1" />һ����
						<html:multibox property="cutLevels" value="2" />�������
						<html:multibox property="cutLevels" value="3" />Ԥ���
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width: 20%;">
						���¼���
					</td>
					<td class="tdulright">
						<apptag:quickLoadList cssClass="input" name="cabletype"
							listName="cabletype" keyValue="${cableLevels}" type="checkbox" />
					</td>
				</tr>
				
				<c:if test="${sessionScope.operator=='query'}">
					<tr class="trcolor">
						<td class="tdulleft" style="width: 20%;">
						<input type="hidden" value="${operator}" name="operator" />
							״̬��
						</td>
						<td class="tdulright">
							<html:multibox property="states" value="1" />�������
							<html:multibox property="states" value="2" />�������δͨ��
							<html:multibox property="states" value="3" />������
							<html:multibox property="states" value="4" />���������
							<br />
							<html:multibox property="states" value="5" />�������δͨ��
							<html:multibox property="states" value="6" />�����ս���
							<html:multibox property="states" value="7" />���մ����
							<br />
							<html:multibox property="states" value="8" />�������δͨ��
							<html:multibox property="states" value="9" />����
							<html:multibox property="states" value="0" />���
						</td>
					</tr>
				</c:if>
				<tr class="trcolor">
					<td class="tdulleft" style="width: 20%;">
						�Ƿ��ж�ҵ��
					</td>
					<td class="tdulright">
						<html:radio property="isInterrupt" value="" />ȫ��
						<html:radio property="isInterrupt" value="1" />��
						<html:radio property="isInterrupt" value="0" />��
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width: 20%;">
						�Ƿ�ʱ��
					</td>
					<td class="tdulright">
						<html:radio property="isTimeOut" value="" />ȫ��
						<html:radio property="isTimeOut" value="1" />��
						<html:radio property="isTimeOut" value="0" />��
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width: 20%;">
						ѡ��ʱ��Σ�
					</td>
					<td class="tdulright">
						<html:select property="timeType" styleId="timeType">
							<html:option value="1">����ʱ��</html:option>
							<html:option value="2">ʵ�ʿ�ʼʱ��</html:option>
							<html:option value="3">����ʱ��</html:option>
						</html:select>
						<html:text property="beginTime" styleId="beginTime" styleClass="Wdate" style="width: 150" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss'})" readonly="true" />
						-
						<html:text property="endTime" styleId="endTime" styleClass="Wdate validate-date-after-startTime" style="width:150" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginTime\')}'})" readonly="true"/>
					</td>
				</tr>
			</table>
			<logic:present name="queryCondition" property="taskStates">
				<logic:iterate id="oneTaskState" name="queryCondition"
					property="taskStates">
					<input id="taskStates_<bean:write name="oneTaskState" />"
						type="hidden" name="taskStates"
						value="<bean:write name="oneTaskState" />" />
				</logic:iterate>
			</logic:present>
			<div align="center">
				<input type="hidden" name="isQuery" value="true" />
				<html:button property="action" styleClass="button"
					onclick="submitForm()">��ѯ</html:button>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<html:reset property="action" styleClass="button">��д</html:reset>
			</div>
		</html:form>
		<template:displayHide styleId="submitForm1"></template:displayHide>
		<c:if test="${sessionScope.operator=='query'}">
			<logic:notEmpty name="result">
				<script type="text/javascript">
			exportList=function(){
				var url="${ctx}/cutQueryStatAction.do?method=exportCutList";
				self.location.replace(url);
			};
			toViewForm=function(cutId){
            	window.location.href = "${ctx}/cutQueryStatAction.do?method=viewQueryCut&cutId="+cutId;
			};
		</script>
				<%
					BasicDynaBean object = null;
							Object cutId = null;
							Object workorder_id = null;
				%>
				<display:table name="sessionScope.result" id="cut" pagesize="18">
					<display:column title="������" media="html" sortable="true">
						<%
							object = (BasicDynaBean) pageContext.findAttribute("cut");
											if (object != null) {
												cutId = object.get("id");
												workorder_id = object.get("workorder_id");
											}
						%>
						<a href="javascript:toViewForm('<%=cutId%>')"><%=workorder_id%></a>
					</display:column>
					<display:column property="cut_name" title="�������"
						headerClass="subject" sortable="true" />
					<display:column property="cut_level" title="��Ӽ���"
						headerClass="subject" sortable="true" />
					<display:column property="contractorname" title="��ά��λ"
						headerClass="subject" sortable="true" />
					<display:column property="apply_date" title="����ʱ��"
						headerClass="subject" sortable="true" />
					<display:column property="feedback_hours" title="������(Сʱ)"
						headerClass="subject" sortable="true" />
					<display:column property="contractorname" title="������"
						headerClass="subject" sortable="true" />
					<display:column property="cut_state" title="���״̬"
						headerClass="subject" sortable="true" />
				</display:table>
				<p>
					<html:link action="/cutQueryStatAction.do?method=exportCutList">����ΪExcel�ļ�</html:link>
				</p>
				<div style="height: 50px; text-align: center;">
					<html:button property="button" styleClass="button"
						onclick="javascript:history.go(-1);">����</html:button>
				</div>
			</logic:notEmpty>
		</c:if>
		<c:if test="${sessionScope.operator=='stat'}">
			<logic:notEmpty name="result">
				<display:table name="sessionScope.result" id="cut" pagesize="18">
					<display:column property="contractorname" title="��ά��λ"
						headerClass="subject" sortable="true" />
					<display:column property="cut_number" title="�������"
						headerClass="subject" sortable="true" />
					<display:column property="total_budget" title="��ӷ���(Ԫ)"
						headerClass="subject" sortable="true" />
					<display:column property="total_time" title="���ʱ��(Сʱ)"
						headerClass="subject" sortable="true" />
					<display:column property="total_bs" title="Ӱ���վ��"
						headerClass="subject" sortable="true" />
				</display:table>
				<p>
					<html:link action="/cutQueryStatAction.do?method=exportCutStat">����ΪExcel�ļ�</html:link>
				</p>
				<br />
				<br />
				<div style="height: 50px; text-align: center;">
					<html:button property="button" styleClass="button"
						onclick="javascript:history.go(-1);">����</html:button>
				</div>
			</logic:notEmpty>
		</c:if>
	</body>
</html>
