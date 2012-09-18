<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title></title>

		<script type='text/javascript'
			src='${ctx}/linepatrol/js/change_style.js'></script>

		<script type='text/javascript'
			src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<link rel='stylesheet' type='text/css'
			href='${ctx}/js/extjs/resources/css/ext-all.css' />

		<script type="text/javascript">
		var win;
		function showWin(url){
			win = new Ext.Window({
				layout : 'fit',
				width:600,
				height:400,
				resizable:true,
				closeAction : 'close',
				modal:true,
				autoScroll:true,
				autoLoad:{url:url, scripts:true},
				plain: true
			});
			win.show(Ext.getBody());
		}
		function closeProcessWin(){
			win.close();
		}
		function his(id){
			var url = "${ctx}/process_history.do?method=showProcessHistoryList&object_type=overhaul&is_close=0&object_id="+id;
			showWin(url);
		}
		
		function checkForm(){
			if(!checkAppraiseDailyValid()){
  				return false;
  			}
  			form.submit();
		}
		
		function showOrHiddeInfo(){
			var infoDiv = document.getElementById("applyinfo");
			if(infoDiv.style.display=="block"){
				infoDiv.style.display="none";
			}else{
				infoDiv.style.display="block";
			}
		}
	</script>
	</head>
	<body onload="changeStyle()">
		<template:titile value="���޿���" />
		<html:form action="/overHaulExamAction.do?method=examOverHaul" styleId="form">
			<div id="applyinfo" style="display: none;">
			<template:formTable namewidth="200" contentwidth="400">
				<template:formTr name="������Ŀ����">
				${OverHaul.projectName}
			</template:formTr>
				<template:formTr name="������">
				${OverHaul.projectCreator}
			</template:formTr>
				<template:formTr name="Ԥ�����">
				${OverHaul.budgetFee}Ԫ
			</template:formTr>
				<template:formTr name="�������ʼʱ��">
					<bean:write name="OverHaul" property="startTime"
						format="yyyy-MM-dd" />
				</template:formTr>
				<template:formTr name="�����������ʱ��">
					<bean:write name="OverHaul" property="endTime" format="yyyy-MM-dd" />
				</template:formTr>
				<template:formTr name="��ά��˾">
				${cons}
			</template:formTr>
				<template:formTr name="�������ע">
				${OverHaul.projectRemark}
			</template:formTr>
				<template:formTr name="����״̬">
					<c:choose>
						<c:when test="${OverHaul.state eq '00'}">
						�ѽ���
					</c:when>
						<c:otherwise>
						δ����
					</c:otherwise>
					</c:choose>
				</template:formTr>
				<logic:notEmpty property="cancelUserId" name="OverHaul">
					<tr class=trcolor>
						<td class="tdr">
							ȡ���ˣ�
						</td>
						<td class="tdl">
							<bean:write property="cancelUserName" name="OverHaul" />
						</td>
					</tr>
					<tr class=trcolor>
						<td class="tdr">
							ȡ��ʱ�䣺
						</td>
						<td class="tdl">
							<bean:write property="cancelTimeDis" name="OverHaul" />
						</td>
					</tr>
					<tr class=trcolor>
						<td class="tdr">
							ȡ��ԭ��
						</td>
						<td class="tdl">
							<bean:write property="cancelReason" name="OverHaul" />
						</td>
					</tr>
				</logic:notEmpty>
				<template:formTr name="�����Ϣ">
					<table width=100% border=1>
						<tr>
							<td align='center' width='15%'>
								��ά
							</td>
							<td align='center' width='40%'>
								�������
							</td>
							<td align='center' width='15%'>
								��ӷ���
							</td>
							<td align='center' width='20%'>
								��������
							</td>
							<td align='center' width='10%'>
								״̬
							</td>
						</tr>
						<c:forEach var="apply" items="${OverHaul.applys}">
							<c:forEach var="cut" items="${apply.overHaulCuts}">
								<tr>
									<td>
										<apptag:assorciateAttr table="contractorinfo"
											label="contractorname" key="contractorid"
											keyValue="${apply.contractorId}" />
									</td>
									<td>
										${cut.cutName}
									</td>
									<td>
										${cut.cutFee}Ԫ
									</td>
									<td>
										<bean:write name="apply" property="createTime"
											format="yyyy-MM-dd" />
									</td>
									<td>
										<c:choose>
											<c:when test="${apply.state eq '40'}">
												<font color="blue">ͨ��</font>
											</c:when>
											<c:otherwise>
											--
										</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</c:forEach>
					</table>
				</template:formTr>
				<template:formTr name="������Ϣ">
					<table width=100% border=1>
						<tr>
							<td align='center' width='15%'>
								��ά
							</td>
							<td align='center' width='40%'>
								��������
							</td>
							<td align='center' width='15%'>
								���̷���
							</td>
							<td align='center' width='20%'>
								��������
							</td>
							<td align='center' width='10%'>
								״̬
							</td>
						</tr>
						<c:forEach var="apply" items="${OverHaul.applys}">
							<c:forEach var="project" items="${apply.overHaulProjects}">
								<tr>
									<td>
										<apptag:assorciateAttr table="contractorinfo"
											label="contractorname" key="contractorid"
											keyValue="${apply.contractorId}" />
									</td>
									<td>
										${project.projectName}
									</td>
									<td>
										${project.projectFee}Ԫ
									</td>
									<td>
										<bean:write name="apply" property="createTime"
											format="yyyy-MM-dd" />
									</td>
									<td>
										<c:choose>
											<c:when test="${apply.state eq '40'}">
												<font color="blue">ͨ��</font>
											</c:when>
											<c:otherwise>
											--
										</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</c:forEach>
					</table>
				</template:formTr>
				<template:formTr name="Ŀǰ��Ŀ����">
					<div id="useFee">
						${OverHaul.useFee}Ԫ
					</div>
				</template:formTr>
				<template:formTr name="ʣ�����">
					<div id="remainFee">
						<c:choose>
							<c:when test="${OverHaul.remainFee >= 0}">
								<font color=blue>${OverHaul.remainFee}Ԫ</font>
							</c:when>
							<c:otherwise>
								<font color=red>${OverHaul.remainFee}Ԫ</font>
							</c:otherwise>
						</c:choose>
					</div>
				</template:formTr>
			</template:formTable>
			</div>
		<div align="right" style="height: 30px; line-height: 30px; width:600px; margin:0px auto;"><a onclick="showOrHiddeInfo();" style="cursor: pointer;">��ʾ/����</a></div>
		</html:form>
		
		<apptag:appraiseDailyDaily businessId="${applyId}" contractorId="${contractorId}" businessModule="overHaul" tableStyle="width:600px;border-top: 0px;"></apptag:appraiseDailyDaily>
			<apptag:appraiseDailySpecial businessId="${applyId}" contractorId="${contractorId}" businessModule="overHaul" tableStyle="width:600px;border-top: 0px;"></apptag:appraiseDailySpecial>
			<template:formTable namewidth="200" contentwidth="400">
				<template:formSubmit>
					<c:if test="${type eq 'history'}">
						<td>
							<input type="button" class="button" value="������ʷ"
								onclick="his('${OverHaul.id}')" />
						</td>
					</c:if>
					<td>
						<html:button property="action" styleClass="button" styleId="subbtn"
							onclick="checkForm()">�ύ</html:button>
						<input type="button" class="button" value="����"
							onclick="history.back()" />
					</td>
				</template:formSubmit>
			</template:formTable>
	</body>