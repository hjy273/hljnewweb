<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<title>�ƶ����Ϸ�����������</title>
		<style type="text/css">
			.tablelist{
				border-style:solid;
				border-color:#9A9A9A;
				border-width:1px 0 0 0;
				width:100%;
				border-collapse:collapse; 
				border-spacing:0; 
			}
			.tablelisttd{
				padding: 0; 
				border-style : solid;
				border-width: 0 0 1px 0;
				border-color : #9A9A9A;
			}
		</style>
		<script type="text/javascript">
		//window.location.href = "${ctx}/specialEndPlanAction.do?method=specialEndPlanForward&id="+spplanId+"&spEndId="+endId+"&type=view";
			function showSpecPlan(spplanId){
				var url = "${ctx}/specialEndPlanAction.do?method=specialEndPlanForward&id="+spplanId+"&type=view"+"&isApply=false&flag=safeguard";
				win = new Ext.Window({
					layout : 'fit',
					width:650,
					height:400,
					resizable:true,
					closeAction : 'close',
					modal:true,
					html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
					plain: true
				});
				win.show(Ext.getBody());
			}
			function viewPlanStat(id){
				window.location.href = '${ctx}/linepatrol/specialplan/stat/stat.jsp?type=002&id='+id;
			}
			function viewAllPlanStat(id){
				window.location.href = '${ctx}/linepatrol/specialplan/stat/allStat.jsp?id='+id;
			}
		</script>
		<table align="center" cellpadding="1" style="border-bottom:0px;"  cellspacing="0" class="tabout" width="90%">
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">�漰�����м̶Σ�</td>
				<td class="tdulright" colspan="3">
				<c:choose>
					<c:when test="${safeguardPlan.isAllNet==0 || safeguardPlan.isAllNet eq ''}">
					<apptag:trunk id="trunk" state="view" value="${sublineIds}" />
					</c:when>
					<c:otherwise>
					ȫ������
					</c:otherwise>
				</c:choose>
					<c:if test="${safeguardPlan.isAllNet==0 || safeguardPlan.isAllNet eq ''}">
						<apptag:trunk id="trunk" state="view" value="${sublineIds}" />
					</c:if>
					<c:if test="${safeguardPlan.isAllNet==1}">
						
					</c:if>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">�ƻ�������Ա��</td>
				<td class="tdulright"  style="width:30%;">
					<c:out value="${safeguardPlan.planResponder}"/> ��
				</td>
				<td class="tdulleft" style="width:20%;">�ƻ�����������</td>
				<td class="tdulright" style="width:30%;">
					<c:out value="${safeguardPlan.planRespondingUnit}"/> ��
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">�ƻ�Ͷ���豸����</td>
				<td class="tdulright" colspan="3">
					<c:out value="${safeguardPlan.equipmentNumber}"/> ��
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">��ע��</td>
				<td class="tdulright" colspan="3">
					<c:out value="${safeguardPlan.remark}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">����������</td>
				<td class="tdulright" colspan="3">
					<apptag:upload cssClass="" entityId="${safeguardPlan.id}" entityType="LP_SAFEGUARD_PLAN" state="look"> </apptag:upload>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft">
					��Ѳ�ƻ���
				</td>
				<td class="tdulright" colspan="3">
					<table class="tablelist">
					  <div id="spplanValue">
					  	<c:if test="${not empty safeguardSps}">
							<c:forEach items="${specialPlans}" var="specialPlan">
								<tr class="trcolor">
									<c:forEach items="${specialPlan}" var="sp">
										<td class="tablelisttd">${sp['plan_name']}&nbsp;&nbsp;</td>
										<td class="tablelisttd">${sp['start_date']} -- ${sp['end_date']}&nbsp;&nbsp;</td>
										<td class="tablelisttd">
											<a onclick='showSpecPlan("${sp['id']}")' style="color: blue;cursor: pointer;">�鿴</a>&nbsp;&nbsp;
										</td>
										<td class="tablelisttd">
											<c:if test="${sp['patrol_stat_state']=='1'}">
												<a onclick='viewPlanStat("${sp['id']}")' style="color: blue;cursor: pointer;">�鿴ͳ�ƽ��</a>&nbsp;&nbsp;
											</c:if>
											<c:if test="${sp['patrol_stat_state']!='1'}">
												
											</c:if>
										</td>
									</c:forEach>
								</tr>
							</c:forEach>
						</c:if>
					  </div>
					</table>
				</td>
			</tr>
			<c:if test="${not empty safeguardPlan.deadline }">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ܽ��ύʱ�ޣ�</td>
					<td class="tdulright" colspan="3">
						<bean:write name="safeguardPlan" property="deadline" format="yyyy/MM/dd HH:mm:ss"/>
					</td>
				</tr>
			</c:if>
			<tr class="trcolor">
				<apptag:approve displayType="view" labelClass="tdulleft" valueClass="tdulright" objectId="${safeguardPlan.id}" objectType="LP_SAFEGUARD_PLAN" colSpan="4" />
			</tr>
		</table>

