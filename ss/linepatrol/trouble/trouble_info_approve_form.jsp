<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title></title>
		    <script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
	<link type="text/css" rel="stylesheet"
		href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
		
		<script type="text/javascript">
		    toViewForm=function(idValue){
	           // window.location.href = "${ctx}/troubleReplyAction.do?method=viewReplyInfo&replyid="+idValue;
	            window.location.href = "${ctx}/troubleQueryStatAction.do?method=viewTroubleReply&replyid="+idValue;
			}
			//ƽ̨���
		   toEditDispatchForm=function(idValue,prounit){
               window.location.href = "${ctx}/troubleReplyAction.do?method=editDispatch&replyid="+idValue+"&processUnit="+prounit;
		   }
		</script>
	</head>

	<body>
		<br>
		 <template:titile value="ƽ̨��׼" />
		<html:form action="/replyApproveAction.do?method=saveTroubleReply"
			styleId="ApproveReply">
			<input type="hidden" id="troubleid" name="troubleid" value="<bean:write name="trouble" property="id"/>" />
			<table  width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
		    	<tr class=trwhite>
				    <td class="tdulleft">���ϵǼ��ˣ�</td>
				    <td class="tdulright" >
						<c:out value="${sendman}"></c:out>
					</td>
					<td class="tdulleft">���ϵǼǵ�λ��</td>
				    <td class="tdulright">
				        <c:out value="${deptName}"></c:out>
				    </td>
				  </tr> 
				 <tr class=trcolor>
				    <td class="tdulleft">ָ�ɴ�ά��</td>
				    <td class="tdulright">
						<c:forEach items="${unitlist}" var="unit">
							<bean:write name="unit" property="contractorname"/><br/>
						</c:forEach>
					</td>
					<td class="tdulleft">���ϵ��ţ�</td>
				    <td class="tdulright">
				    	<c:out value="${trouble.troubleId}"></c:out>
				    </td>
				  </tr>
			      <tr class=trwhite>
				    <td class="tdulleft">�������ƣ�</td>
				    <td class="tdulright" colspan="3">
				    	<bean:write name="trouble" property="troubleName" />
					</td>
				  </tr>
			 	  <tr class=trcolor>
				    <td class="tdulleft">���Ϸ���ʱ�䣺</td>
				    <td class="tdulright">
				    	<bean:write name="trouble" property="troubleStartTime" format="yyyy/MM/dd HH:mm:ss"/>
					</td>
				    <td class="tdulleft">�����ɷ�ʱ�䣺</td>
				    <td class="tdulright">
				    	<bean:write name="trouble" property="troubleSendTime" format="yyyy/MM/dd HH:mm:ss"/>
					</td>
				  </tr>
				  
				  <tr class=trwhite>
				    <td class="tdulleft">�Ƿ�Ϊ�ش���ϣ�</td>
				    <td class="tdulright">
				    	<c:if test="${trouble.isGreatTrouble=='0'}">
				    		��
				    	</c:if>
				    	<c:if test="${trouble.isGreatTrouble=='1'}">
				    		��
				    	</c:if>
				    </td>
				    <td class="tdulleft">���Ϸ���ʱ�ޣ�</td>
				    <td class="tdulright">
				    	<bean:write name="trouble" property="replyDeadline" format="yyyy/MM/dd HH:mm:ss"/>
				    </td>
				  </tr>
				  <tr>
				    <td class="tdulleft">�����ɷ��ˣ�</td>
				    <td class="tdulright" colspan="3"><bean:write name="trouble" property="troubleSendMan"/></td>
				  </tr>
				  
				  <tr class=trcolor>
				    <td class="tdulleft">ƽ̨��</td>
				    <td class="tdulright">
				      ${trouble.connector}
				    </td>
				    <td class="tdulleft">ƽ̨�绰��</td>
				    <td class="tdulright"><bean:write name="trouble" property="connectorTel"/></td>
				  </tr>
				    <tr class=trwhite>
				         <td class="tdulleft">eoms����������</td>
						  <td class="tdulright" colspan="3">
						  	${eomsnum}��
					     </td>
				     </tr>
				   <tr class=trcolor>
				         <td class="tdulleft">eoms���ţ�</td>
						  <td class="tdulright" colspan="3"  style="word-break:break-all;width:60%;">
						  	${eoms}
					     </td>
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">����������</td>
				    <td class="tdulright" colspan="3" style="word-break:break-all;width:500px;" ><bean:write name="trouble" property="troubleRemark"/></td>
				  </tr>
				   <tr class=trcolor>
				    <td class="tdulleft">������</td>
				    <td class="tdulright" colspan="3" >
				    	 <apptag:upload state="look" cssClass="" entityId="${trouble.id}" entityType="LP_TROUBLE_INFO"/>
				    </td>
				  </tr>
				  <tr><td colspan="4">
				 <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
				 	<tr>
				 		<td align="center" style="font-weight: bold;">��ά��˾</td>
				 		<td align="center" style="font-weight: bold;">����ʱ��</td>
				 		<td align="center" style="font-weight: bold;">�����˽�ɫ</td>
				 		<td align="center" style="font-weight: bold;">���״̬</td>
				 		<!-- td align="center" style="font-weight: bold;">��ע</td -->
				 		<td align="center" style="font-weight: bold;">����</td>
				 	</tr>
				 	<c:forEach items="${replys}" var="reply" varStatus="varloop">
				 		<c:if test="${varloop.index%2==0}">
				 		  <tr class=trcolor>
				 		</c:if>
				 		<c:if test="${varloop.index%2!=0}">
				 		  <tr class=trwhite>
				 		</c:if>
							<td class="tdlist"><bean:write name="reply" property="contractorname"/></td>
							<td class="tdlist">&nbsp;
				    			<bean:write name="reply" property="reply_submit_time" format="yyyy/MM/dd HH:mm:ss"/>
							</td>
							<td class="tdlist">
						    	<bean:write name="reply" property="confirm_result"/>
							</td>
							<td class="tdlist"> &nbsp;
						    	<bean:write name="reply" property="approve_state"/>
							</td>
							<!-- td class="tdlist" style="word-break:break-all;width:220px;" >
							&nbsp;<--bean:write name="reply" property="reply_remark" --/></td -->
							<td class="tdlist">&nbsp;  
							     <bean:define id="rid" property="id" name="reply"></bean:define>
							      <bean:define id="cid" property="contractorid" name="reply"></bean:define>
								 <a href="javascript:toEditDispatchForm('<%=rid%>','<%=cid%>')">��׼</a>|
								 <a href="javascript:toViewForm('<%=rid%>')">�鿴</a>
							</td>
				 		</tr>
				 	</c:forEach>
				 </table>
				 </td>
				 </tr>
				 <tr class=trcolor >
				    <td align="center" colspan="4">
				    	<input type="button" value="����" class="button" onclick="javascript:history.back();" />
				    </td>
				  </tr>
			 </table>
		</html:form>
		
	</body>
</html>
