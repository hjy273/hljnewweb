
<%@page import="org.springframework.web.context.request.SessionScope"%>
<%@page import="org.apache.commons.lang.StringUtils"%><%@include file="/common/header.jsp"%>
<%@page import="java.util.Date" %>
<%@page import="com.cabletech.commons.util.DateUtil" %>
<html>
	<head>
		<title></title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<script type='text/javascript'
			src='${ctx}/linepatrol/js/change_style.js'></script>
		<script type="text/javascript">
		function check(){
			if( ($('begintime').value == '' && $('endtime').value != '') ||
				($('begintime').value != '' && $('endtime').value == '') ){
				alert("���ڶα���ͬʱ�п�ʼ���ںͽ�������");
				return false;
			}
			if( ($('begintime').value != '' && $('endtime').value != '')
					&& ($('begintime').value == $('endtime').value) ){
				alert('��ʼ���ںͽ������ڲ������');
				return false;
			}
			return true;
		}
		function isAcceptanceState(state){
			if(state=='1'){
				document.getElementById("pass").style.display="";
			}else{
				document.getElementById("pass").style.display="none";
			}
		}
	</script>
	<style type="text/css">
		.Content{ border:1px #000000 solid;}

		.table_1{  border:1px #000000 solid;}
		.table_1_tr1{ border-bottom:1px #000000 solid;}
		
		.table_2{  border:0px #000000 solid;}
		.table_2 td{ border-bottom:1px #000000 solid; border-right:1px #000000 solid;}
		.table_2_td{ border-top:1px #000000 solid; border-left:1px #000000 solid; }
		.table_2_left{border-left:1px #000000 solid; }
	</style>
	</head>
	<body onload="changeStyle()">
		<template:titile value="�ܵ�ͳ��" />
		<!-- ��ѯҳ�� -->
		<html:form action="/acceptancePipeStatAction.do?method=pipeStatList"
			styleId="form" onsubmit="return check()">
			<template:formTable namewidth="150" contentwidth="400">
				<template:formTr name="��Ŀ����">
					<html:text property="projectName" styleClass="inputtext"
						style="width:120px" />
				</template:formTr>
				<template:formTr name="��Ŀ����">
					<html:text property="pcpm" styleClass="inputtext"
						style="width:120px" />
				</template:formTr>
				<template:formTr name="�������">
				<html:radio property="isRecord" value="" onclick="isAcceptanceState(this.value);" >ȫ��</html:radio>
				<html:radio property="isRecord" value="0" onclick="isAcceptanceState(this.value);">δ����</html:radio>
				<html:radio property="isRecord" value="1" onclick="isAcceptanceState(this.value);">����</html:radio>
			</template:formTr>
			<tbody id="pass" style="display:none">
			<template:formTr name="�Ƿ�ͨ��">
				<html:radio property="ispassed" value="">ȫ��</html:radio>
				<html:radio property="ispassed" value="1">ͨ��</html:radio>
				<html:radio property="ispassed" value="0">δͨ��</html:radio>
			</template:formTr>
			</tbody>
				<template:formTr name="�ܵ�����">
					<apptag:quickLoadList cssClass="input" style="width:200px"
						name="pipeType" listName="pipe_type" type="select" isQuery="true" />
				</template:formTr>
				<template:formTr name="��Ȩ����">
					<apptag:quickLoadList cssClass="input" style="width:200px"
						name="pipeProperty" listName="property_right" type="select"
						isQuery="true" />
				</template:formTr>
				<template:formTr name="��ά��˾" isOdd="false">
					<apptag:setSelectOptions valueName="connCollection"
						tableName="contractorinfo" columnName1="contractorname"
						region="true" columnName2="contractorid" order="contractorname" />
					<html:select property="contractorid" styleClass="inputtext"
						styleId="rId" style="width:200px">
						<html:option value="">����</html:option>
						<html:options collection="connCollection" property="value"
							labelProperty="label" />
					</html:select>
				</template:formTr>
				<template:formTr name="����ʱ��">
					<html:text property="begintime" styleClass="Wdate"
						style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})" /> ��
				<html:text property="endtime" styleClass="Wdate" style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})" />
				</template:formTr>
			</template:formTable>
			<div align="center">
				<input name="isQuery" value="true" type="hidden">
				<html:submit property="action" styleClass="button">�� ѯ</html:submit>
			</div>
		</html:form>
		<template:displayHide styleId="form"></template:displayHide>
		
		<logic:notEmpty name="list">
			<!-- ��ѯ��� -->
			<%
				List<Object> list = (List<Object>)request.getSession().getAttribute("list");
				//ͳ��������Ϣ
				List<Object> totalList = (List<Object>)list.get(0);
				//��ϸ��Ϣ�б�
				List<Object> detailList = (List<Object>)list.get(1);
				request.setAttribute("totalList", totalList);
				request.setAttribute("detailList", detailList);
			%>
			<table cellspacing="0" cellpadding="0" class="Content">
				<tr>
					<td style="padding: 0px; margin: 0px;">
						<table cellspacing="0" cellpadding="0" style="border: 0px;" class="table_1">
							<tr>
								<td colspan="5" class="table_1_tr1">
									<b>������Ϣ</b>
								</td>
							</tr>
							<tr>
								<td class="table_1_tr1">
									��Ŀ������<%=totalList.get(0) %>
								</td>
								<td class="table_1_tr1">
									����ͨ��������<%=totalList.get(1) %>
								</td>
								<td class="table_1_tr1">
									����ͨ���ʣ�<%=totalList.get(2) %> %
								</td>
								<td class="table_1_tr1">
									����������<%=totalList.get(3) %>
								</td>
								<td class="table_1_tr1">
									�׹�������<%=totalList.get(4) %>
								</td>
							</tr>
							<tr>
								<td class="table_1_tr1">
									������ͨ���ʣ�<%=totalList.get(5) %> %
								</td>
								<td class="table_1_tr1" colspan="4">
									�׹���ͨ���ʣ�<%=totalList.get(6) %> %
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td style="padding: 0px; margin: 0px;">
						<%
							for(int i = 0; i < detailList.size(); i++) {
								List<Object> oneDetailList = (List<Object>)detailList.get(i);
								//��ϸ��Ϣ��ͳ��
								DynaBean detailStat = (DynaBean)oneDetailList.get(0);
								//�ܵ���ϸ��Ϣ�б�
								List<DynaBean> detailInfoList = (List<DynaBean>)oneDetailList.get(1);
								request.setAttribute("detailStat", detailStat);
								request.setAttribute("detailInfoList", detailInfoList);
						%>
							<table cellspacing="0" cellpadding="0" style="border: 0px;" class="table_1_tr1">
								<tr>
									<td colspan="5" class="table_1_tr1">
										<b><bean:write name="detailStat" property="name"/></b>
									</td>
								</tr>
								<tr>
									<td class="table_1_tr1">
										��Ŀ����<bean:write name="detailStat" property="pcpm"/>
									</td>
									<td class="table_1_tr1">
										�Ƿ�ͨ�����գ�<bean:write name="detailStat" property="ispassed"/>
									</td>
									<td class="table_1_tr1">
										��������ʱ�䣺<bean:write name="detailStat" property="applydate"/>
									</td>
									<td class="table_1_tr1">
										�ϸ�������<bean:write name="detailStat" property="ispassednum"/>
									</td>
									<td class="table_1_tr1">
										δ�ϸ�������<bean:write name="detailStat" property="unpassednum"/>
									</td>
								</tr>
								<tr>
									<td class="table_1_tr1">
										����������<bean:write name="detailStat" property="pitchlength"/>
									</td>
									<td class="table_1_tr1" colspan="4">
										�׹�������<bean:write name="detailStat" property="holelength"/>
									</td>
								</tr>
								<tr>
									<td colspan="5" style="padding: 0px; margin: 0px;">
										<table cellspacing="0" cellpadding="0" style="border: 0px;" class="table_2">
											<tr>
												<td>�ܵ��ص�</td>
												<td>��Ȩ����</td>
												<td>�ܵ�����</td>
												<td>��ϸ·��</td>
												<td>�ܵ���ģ</td>
												<td>�ƻ�ʱ��</td>
												<td>����ʱ��</td>
												<td>���մ�ά</td>
												<td>�Ƿ�����</td>
												<td>�������</td>
											</tr>
											<c:forEach items="${detailInfoList}" var="detail">
											<%		
													DynaBean detail= (DynaBean)pageContext.findAttribute("detail");
													String isrecord = (String)detail.get("isrecord");
													request.setAttribute("isrecord", isrecord);
													String flag="a";
													String planDate=(String)detail.get("plan_date");
													String applyDate=(String)detail.get("apply_date");
													if(StringUtils.isNotBlank(planDate)&&StringUtils.isNotBlank(applyDate)){
													Date plan=DateUtil.strToDate(planDate);
													Date apply=DateUtil.strToDate(applyDate);
													if(apply.before(plan)){
														flag="p";
													}
													}
													request.setAttribute("flag",flag);
													%>
												<tr>
													<td>
														<c:if test="${flag=='a'}">
															<font color="red">
														</c:if> 
														<bean:write name="detail" property="pipe_address"/>
															<c:if test="${flag=='a'}">
															</font>
														</c:if> 
														</td>
													<td><bean:write name="detail" property="pipe_property"/></td>
													<td><bean:write name="detail" property="pipe_type"/></td>
													<td><bean:write name="detail" property="pipe_route"/></td>
													<td><bean:write name="detail" property="gdgm"/></td>
													<td><bean:write name="detail" property="plan_date"/></td>
													<td>
														<c:if test="${flag=='a'}">
															<font color="red">
														</c:if> 
														<bean:write name="detail" property="apply_date"/>
															<c:if test="${flag=='a'}">
															</font>
														</c:if> 
													</td>
													<td><bean:write name="detail" property="contractorid"/></td>
													<td><bean:write name="detail" property="isrecord"/></td>
													<td><c:if test="${isrecord=='����'}"><bean:write name="detail" property="ispassed"/></c:if>
														<c:if test="${isrecord!='����'}">��</c:if>
													</td>
												</tr>
											</c:forEach>
										</table>
									</td>
								</tr>
								<tr><td height="20"></td></tr>
							</table>
						<%
							}
						%>
					</td>
				</tr>
			</table>
		</logic:notEmpty>
		
		<!-- ��ѯ�б� -->
		<logic:notEmpty name="list">
			<!-- ��ѯ��� -->
			<table class="tdlist">
				<tr>
					<td class="tdlist">
						<input type="button" class="button" value="����"
							onclick="history.back()" />
					</td>
				</tr>
			</table>
		</logic:notEmpty>
	</body>
</html>