<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>�鿴�����ɵ�</title>
		
		<script type="text/javascript">
			function getXY(){
		    	var userid = document.getElementById("userid").value;
		    	var pid = document.getElementById("replyid").value;
		    	var rid = document.getElementById("regionid").value;
		    	var URL="/WEBGIS/gisextend/igis.jsp?userid="+userid+"&actiontype=204&pid="+pid+"&rid="+rid;
		    	//alert(URL);
		       	myleft=(screen.availWidth-500)/2;
				mytop=120;
				mywidth=650;
				myheight=490;
		        window.open(URL,"getXY","height="+myheight+",width="+mywidth+",status=1,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft+",resizable=no");
		    }
			      
		  function viewAddr(x,y,addr){
		    	var userid = document.getElementById("userid").value;
		    	var URL="/WEBGIS/gisextend/igis.jsp?userid="+userid+"&actiontype=205&x="+x+"&y="+y+"&label="+addr;
		    	//alert(URL);
		       	myleft=(screen.availWidth-500)/2;
				mytop=220;
				mywidth=650;
				myheight=420;
		        window.open(URL,"getXY","height="+myheight+",width="+mywidth+",status=1,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft+",resizable=no");
		 }
		 
		 	function viewProcess(){
			    var userid = document.getElementById("userid").value;
				var tid = document.getElementById("troubleid").value;
				var URL="/WEBGIS/gisextend/igis.jsp?userid="+userid+"&actiontype=206&tid="+tid;
			    myleft=(screen.availWidth-500)/2;
				mytop=120;
				mywidth=670;
				myheight=490;
			    window.open(URL,"viewPro","height="+myheight+",width="+mywidth+",status=1,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft+",resizable=no");
			}
      </script>
	</head>

	<body>
		<br>
		<template:titile value="�����ɵ���ϸ��Ϣ" />
			<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout" >
			<input type="hidden" id="userid" value="<bean:write name="LOGIN_USER" property="userID"/>"/>
			<input type="hidden" id="regionid" value="<bean:write name="troubleinfo" property="regionID"/>"/>
			<input type="hidden" id="troubleid" value="<bean:write name="troubleinfo" property="id"/>"/>
				 <tr class=trcolor>
				    <td class="tdulleft">ָ�ɴ�ά��</td>
				    <td class="tdulright" >
						<c:forEach items="${unitlist}" var="unit">
							<bean:write name="unit" property="contractorname"/><br/>
						</c:forEach>
					</td>
					 <td class="tdulleft">���ϵ��ţ�</td>
				    <td class="tdulright">
				    	<c:out value="${troubleinfo.troubleId}"></c:out>
				    </td>
				 </tr>
				 
				  <tr class=trwhite>
				  	 <td class="tdulleft">�������ƣ�</td>
				    <td class="tdulright" colspan="3">
				    	<c:out value="${troubleinfo.troubleName}"></c:out>
				    </td>
				    
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft">�Ƿ�Ϊ�ش���ϣ�</td>
				    <td class="tdulright">
				    	<c:if test="${troubleinfo.isGreatTrouble=='0'}">
				    		��
				    	</c:if>
				    	<c:if test="${troubleinfo.isGreatTrouble=='1'}">
				    		��
				    	</c:if>
				    </td>
				    <td class="tdulleft">���Ϸ���ʱ�ޣ�</td>
				    <td class="tdulright">
				    	<fmt:formatDate value="${troubleinfo.replyDeadline}" var="startTime" pattern="yyyy/MM/dd HH:mm:ss"/>
				    </td>
				  </tr>
				  <tr>
				    <td class="tdulleft">�����ɷ��ˣ�</td>
				    <td class="tdulright" colspan="3"><bean:write name="troubleinfo" property="troubleSendMan"/></td>
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">ƽ̨��</td>
				    <td class="tdulright">
				      ${troubleinfo.connector}
				    </td>
				    <td class="tdulleft">ƽ̨�绰��</td>
				    <td class="tdulright" ><bean:write property="connectorTel" name="troubleinfo"/></td>
				  </tr>
				   <tr class=trcolor>
				         <td class="tdulleft">�����ɷ�ʱ�䣺</td>
						  <td class="tdulright" colspan="3">
						  	<bean:write property="troubleSendTime" name="troubleinfo" format="yyyy/MM/dd HH:mm:ss"/>
					     </td>
				  </tr>
				    <tr class=trwhite>
				         <td class="tdulleft">eoms���ţ�</td>
						  <td class="tdulright" colspan="3" style="word-break:break-all;width:60%;">
						  	${eoms}
					     </td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft">����������</td>
				    <td class="tdulright" colspan="3" style="word-break:break-all;width:500px;">
				    	<bean:write property="troubleRemark" name="troubleinfo"/>
				    </td>
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">������</td>
				    <td class="tdulright" colspan="3" >
				    	 <apptag:upload state="look" cssClass="" entityId="${troubleinfo.id}" entityType="LP_TROUBLE_INFO"/>
				    </td>
				  </tr>
			<logic:notEmpty property="cancelUserId" name="troubleinfo">
				<tr class=trcolor>
					<td class="tdr">
						ȡ���ˣ�
					</td>
					<td class="tdl">
						<bean:write property="cancelUserName" name="troubleinfo" />
					</td>
					<td class="tdr">
						ȡ��ʱ�䣺
					</td>
					<td class="tdl">
						<bean:write property="cancelTimeDis" name="troubleinfo" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						ȡ��ԭ��
					</td>
					<td class="tdl" colspan="3">
						<bean:write property="cancelReason" name="troubleinfo" />
					</td>
				</tr>
			</logic:notEmpty>
			<logic:notEmpty name="reply">
					<input type="hidden" id="replyid" value="<bean:write name="reply" property="id"/>"/>
					 	<tr class=trcolor>
					 		<td class="tdulleft">�豸���ƣ�</td>
					 		<td class="tdulright" colspan="3"><c:out value="${reply.terminalInfo}"></c:out></td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">�����豸�����أ�</td>
					 		<td class="tdulright">
					 			<apptag:quickLoadList style="width:130px" keyValue="${reply.terminalAddress}" cssClass="select" name="terminalAddress" listName="terminal_address" type=""></apptag:quickLoadList>
					 		</td>
					 		 <td class="tdulleft">����λ�ã�</td>
							 <td class="tdulright" >
							    	<a href="javascript:getXY();">�鿴</a>
							</td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">��ϵص�������</td>
					 		<td class="tdulright" colspan="3"><c:out value="${reply.brokenAddress}"></c:out></td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">Ӱ��ҵ��ʱ�Σ�</td>
					 		<td class="tdulright" colspan="3"><c:out value="${reply.impressStartTime}"></c:out>-<c:out value="${reply.impressEndTime}"></c:out></td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">Ӱ��ҵ�����ͣ�</td>
					 		<td class="tdulright" colspan="3"><c:out value="${reply.impressType}"></c:out></td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">��Ϲ��¶Σ�</td>
					 		<td class="tdulright" colspan="3">
					 			<apptag:trunk id="trunk" state="view"  value="${reply.trunkids}"/>
					 		</td>
					 	</tr>
					    <tr class=trwhite>
					 		<td class="tdulleft">�������¶Σ�</td>
					 		<td class="tdulright" colspan="3"><c:out value="${reply.otherCable}"></c:out></td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">�������ͣ�</td>
					 		<td class="tdulright" colspan="3">
					 		 <apptag:quickLoadList cssClass="select" keyValue="${reply.troubleType}"  id="troubleReasonId" name="troubleReasonId" listName="lp_trouble_type" type="look"></apptag:quickLoadList>
					 		</td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">�ֳ�ͼƬ��</td>
							 <td class="tdulright" colspan="3">
							 	<apptag:image entityId="${reply.id}" entityType="LP_TROUBLE_REPLY"></apptag:image>
							 </td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">���ԭ��������</td>
					 		<td class="tdulright" colspan="3" style="word-break:break-all;width:500px;">
					 			<c:out value="${reply.brokenReason}"></c:out>
					 		</td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td colspan="4">
					 			<table border="1"  cellpadding="0" cellspacing="0" width="100%">
					 				<logic:notEmpty name="process">
					 				<tr>
					 					<td align="left" colspan="8">�������</td>
					 				</tr>
					 				
					 				<tr>
					 					<td>�鿴�켣</td><td>����ʱ��</td><td>������Ա</td><td>�豸���</td><td>�������ʱ��</td>
					 					<td>��������ֳ�ʱ��</td><td>�ҵ����ϵ�ʱ��</td><td>�����ֳ�ʱ��</td>
					 				</tr>
					 				</logic:notEmpty>
					 				<bean:size id="prosize" name="process"/>
					 				<logic:iterate id="proce" name="process" indexId="len">
				    				<tr>
				    						<c:if test="${len==0}">
				    				         <td rowspan="${prosize}"><a href="javascript:viewProcess();">�鿴</a></td>
				    				       </c:if>
				    					<td>
				    						<bean:write name="proce" property="start_time_ref"/>&nbsp;
				    					</td>
				    					<td><bean:write name="proce" property="arrive_proess_man"/>&nbsp;</td>
				    					<td><bean:write name="proce" property="arrive_terminal_id"/>&nbsp;</td>
				    					<td>
				    						<bean:write name="proce" property="arrive_time_ref"/>&nbsp;
				    					</td>
				    					<td>
				    					      <bean:write name="proce" property="arrive_trouble_time_ref"/>&nbsp; 
				    					</td>
				    					<td>
				    						<bean:write name="proce" property="find_trouble_time_ref"/>&nbsp;
				    					</td>
				    					<td>
				    						<bean:write name="proce" property="return_time_ref"/>&nbsp;
				    					</td>
				    				</tr>
				    			</logic:iterate>
					 			</table>
					 		</td>
					 	</tr>
					 	 <tr class=trcolor>
						    <td class="tdulleft">���Ϸ���ʱ�䣺</td>
						    <td class="tdulright">
						    <bean:write  name="troubleinfo" property="troubleStartTime" format="yyyy/MM/dd HH:mm:ss" />
						    </td>
						    <td class="tdulleft">��������ʱ�䣺</td>
						    <td class="tdulright" ><bean:write name="reply" property="troubleAcceptTime" format="yyyy-MM-dd HH:mm:ss"/></td>
						  </tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">�������ʱ�䣺</td>
					 		<td class="tdulright"><bean:write name="reply" property="arriveTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					 		<td class="tdulleft">��������ֳ�ʱ�䣺</td>
					 		<td class="tdulright"><bean:write name="reply" property="arriveTroubleTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					 	</tr>
					 	 <tr class=trcolor>
					 		<td class="tdulleft">�жϹ��ϵ�ʱ�䣺</td>
					 		<td class="tdulright"><bean:write name="reply" property="judgeTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					 		<td class="tdulleft">�ҵ����ϵ�ʱ�䣺</td>
					 		<td class="tdulright" ><bean:write name="reply" property="findTroubleTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">���ϻָ�ʱ�䣺</td>
					 		<td class="tdulright"><bean:write name="reply" property="renewTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					 		<td class="tdulleft">���½������ʱ�䣺</td>
					 		<td class="tdulright" ><bean:write name="reply" property="completeTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">�����ֳ�ʱ�䣺</td>
					 		<td class="tdulright"><bean:write name="reply" property="returnTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					 		<td class="tdulleft">
								�������ϵ�������
							</td>
					 		<td class="tdulright" >
					 			<c:forEach items="${hiddens}" var="hidden" >
									<bean:write name="hidden" property="name"/>&nbsp;&nbsp;&nbsp;
								</c:forEach>
					 		</td>
					 	</tr>
					 
					 	<tr class=trwhite>
					 		<td colspan="4">
					 				<apptag:materialselect label="ʹ�ò���" displayType="view" objectId="${reply.id}" useType="trouble" materialUseType="Use" ></apptag:materialselect>
					 		</td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td colspan="4">					 			
					 				<apptag:materialselect label="���ղ���" displayType="view" objectId="${reply.id}" useType="trouble" materialUseType="Recycle" ></apptag:materialselect>
					 		</td>
					 	</tr>
					 	<tr class=trwhite>
								 		<td colspan="4">������Ա</td>
								 	</tr>
								 	<tr class=trcolor>
								 		<td class="tdulleft" >�����ˣ�</td><td colspan="3" class="tdulright"><bean:write name="responsibles"/></td>
								 	</tr>
								 	<tr class=trwhite>
								 		<td  class="tdulleft" >���ϲ�����Ա��</td><td colspan="3" class="tdulright"> <bean:write name="testmans"/></td>
								 	</tr>
								 	<tr class=trcolor>
								 		<td class="tdulleft" >�ֳ�����������Ա��</td><td colspan="3" class="tdulright"><bean:write name="mendmans"/></td>
								 	</tr>
					 	
					 	<tr class=trwhite>
					 		<td class="tdulleft">�����ʩ������</td>
					 		<td class="tdulright" colspan="3" style="word-break:break-all;width:500px;">
					 			<c:out value="${reply.processRemark}"></c:out>
					 		</td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">��������ݼ�¼��</td>
					 		<td class="tdulright" colspan="3" style="word-break:break-all;width:500px;">
					 			<c:out value="${reply.processRecord}"></c:out>
					 		</td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">�������⴦��</td>
					 		<td class="tdulright" colspan="3" style="word-break:break-all;width:500px;">
					 			<c:out value="${reply.otherIssue}"></c:out>
					 		</td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">��ע��</td>
					 		<td class="tdulright" colspan="3" style="word-break:break-all;width:500px;">
					 			<c:out value="${reply.replyRemark}"></c:out>
					 		</td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">������</td>
					 		<td class="tdulright" colspan="3">
					 		   <apptag:upload state="look" cssClass="" entityId="${reply.id}" entityType="LP_TROUBLE_REPLY"/>
					 		</td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">����ԭ��</td>
					 		<td class="tdulright" colspan="3"><c:out value=" ${troubleReasonName}"></c:out></td>
					 	</tr>
					 	<tr class=trcolor><td class="tdulleft">��˲�ͨ��������</td><td class="tdulright" colspan="3"><c:out value="${reply.notPassedNum}"></c:out></td></tr>
					 	<logic:notEmpty name="approves"> 
					 		<tr class=trwhite align="center">
							 	<td colspan="4" align="left">�����ϸ��Ϣ</td>
						    </tr>
							<tr  class=trcolor>
								<td colspan="4">
							    <table width="100%">
							    <tr>
							 	<td width="10%">�����</td><td width="17%">���ʱ�� </td>
							 	<td width="10%">��˽�� </td><td width="40%" >������ </td><td width="18%">����</td>
							   </tr>
							 <c:forEach items="${approves}" var="approve"  varStatus="loop">
							 	<c:if test="${loop.count%2==0 }"> <tr class=trwhite></c:if>
							 	<c:if test="${loop.count%2==1 }"> <tr class=trcolor></c:if>
							 		<td><bean:write name="approve" property="username"/></td>
							 		<td><bean:write name="approve" property="approve_time"/></td>
							 		<td><bean:write name="approve" property="approve_result"/></td>
							 		<td style="word-break:break-all;width:350px;"><bean:write name="approve" property="approve_remark"/></td>
							 		<td><bean:define id="appid" name="approve" property="id"></bean:define>
										<apptag:upload cssClass="" entityId="${appid}"
											entityType="LP_APPROVE_INFO" state="look" />
									</td>
									</tr>
							 </c:forEach>
							 	</table>
									</td>
							 	</tr>
						 </logic:notEmpty>
			</logic:notEmpty>
			<logic:notEmpty name="evaluate">
				<tr class=trwhite>
					<td class="tdulleft">���˵÷֣�</td>
					<td class="tdulright" >
						 <c:out value="${evaluate.entityScore}"></c:out>
					</td>
					<td class="tdulleft">����ʱ�䣺</td>
					<td class="tdulright" >
						 <bean:write name="evaluate" property="evaluaterDate" format="yyyy/MM/dd HH:mm:ss"/>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdulleft">���˱�ע��</td>
					<td class="tdulright" colspan="3" style="word-break:break-all;width:500px;">
						 <c:out value="${evaluate.entityRemark}"></c:out>
					</td>
				</tr>
			</logic:notEmpty>
			 <tr class=trwhite align="center">
				    <td colspan="4">
				    	<input type="button" class="button" value="����" onclick="javascript:history.go(-1);"/>
				    </td>
		    </tr>
		</table>
			
	</body>
</html>
