<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/xtheme-gray.css'/>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<title>���Ϸ�������Ϣ</title>
	 <script type="text/javascript">
		function getXY(){
	    	var userid = document.getElementById("userid").value;
	    	var pid = document.getElementById("replyid").value;
	    	var rid = document.getElementById("regionid").value;
	    	var URL="/WEBGIS/gisextend/igis.jsp?userid="+userid+"&actiontype=204&pid="+pid+"&rid="+rid;
	    	//alert(URL);
	       	myleft=(screen.availWidth-500)/2;
			mytop=100;
			mywidth=670;
			myheight=500;
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
	//�鿴������Ϣ
	viewHideDangerForm=function(id){
			var actionUrl="${ctx}/hiddangerQueryAction.do?method=view&param=window&&id="+id;
			//location=actionUrl+"&"+queryString+"&rnd="+Math.random();
			myleft=(screen.availWidth-650)/2;
			mytop=100
			mywidth=650;
			myheight=500;
			window.open(actionUrl,"read_hide_dangers","height="+myheight+",width="+mywidth+",status=1,resizable=no,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft+",resizable=yes");
		}
		
	   function viewProcess(){
			    var userid = document.getElementById("userid").value;
				var tid = document.getElementById("troubleid").value;
				var URL="/WEBGIS/gisextend/igis.jsp?userid="+userid+"&actiontype=206&tid="+tid;
			    myleft=(screen.availWidth-500)/2;
				mytop=100;
				mywidth=670;
				myheight=500;
			    window.open(URL,"viewPro","height="+myheight+",width="+mywidth+",status=1,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft+",resizable=no");
	  }
	  
	         //��ʷ����
			function viewHistoryDetail(){
			 var prounitid = document.getElementById('prounitid').value;
			 var u="${ctx}/process_history.do?method=showProcessHistoryList&object_type=trouble&is_close=0&object_id="+prounitid;
			  win = new Ext.Window({
			  layout : 'fit',
			  width: document.body.clientWidth * 0.55 , 
			  height:330, 
			  resizable:true,
			  closeAction : 'close', 
			  modal:true,
			  autoLoad:{url:u,scripts:true}, 
			 // html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src='+u+' />',
			  plain: true,
			  title:"������ʷ" 
			 });
			  win.show(Ext.getBody());
			}
			  
			 function closeProcessWin(){
			  	win.close();
			 }
	</script>
	</head>

	<body>
		<br>
	<html:form action="/troubleQueryStatAction.do?method=readReply">
		<template:titile value="���Ϸ�������Ϣ" />
		<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
		 <logic:notEmpty  name="bean">
		 	<input type="hidden" id="userid" value="<bean:write name="LOGIN_USER" property="userID"/>"/>
			<input type="hidden" id="regionid" value="<bean:write name="bean" property="region_id"/>"/>
			<input type="hidden" id="troubleid" value="<bean:write name="bean" property="tid"/>"/>
			<input type="hidden" id="replyid" name="replyid" value="<bean:write name="reply" property="id"/>"/>
			<input type="hidden" id="prounitid" name="prounitid" value="${prounit.id}"/>
				  <tr class=trcolor>
				    <td class="tdulleft">�Ƿ�Ϊ�ش���ϣ�</td>
				    <td class="tdulright">
				    	<bean:write name="bean" property="is_great_trouble"/>
				    </td>
				    <td class="tdulleft">���Ϸ���ʱ�ޣ�</td>
				    <td class="tdulright">
				    	<bean:write name="bean" property="reply_deadline"/>
				    </td>
				   </tr>
				   <tr class=trcolor>
				    <td class="tdulleft">�����Ƿ�ʱ��</td>
				    <td class="tdulright" colspan="3">
				    	<c:if test="${time_length_sign=='+'}"><font color="#FF0000">��ʱ</c:if>
				    	<c:if test="${time_length_sign=='-'}"><font color="#009999">��ǰ</c:if>
				    	<c:if test="${time_length_hour!=0}">
				    		<c:out value="${time_length_hour}" />
				    		Сʱ
				    	</c:if>
				    	<c:out value="${time_length_minute}" />
				    	����
				    	</font>
				    </td>
				  </tr>
				<tr class=trwhite>
					<td class="tdulleft">
						�����ɵ��ˣ�
					</td>
					<td class="tdulright">
						<bean:write name="bean" property="trouble_send_man" />
					</td>
					<td class="tdulleft">
						�����ɷ�ʱ�䣺
					</td>
					<td colspan="3" class="tdulright">
						<bean:write name="bean" property="trouble_send_time" />
					</td>
				</tr>
					
		</logic:notEmpty>
			<tr class=trcolor>
				<td class="tdulleft">
					�����˽�ɫ��
				</td>
				<td colspan="5" class="tdulright">
					<c:if test="${reply.confirmResult=='1'}">
						    		����
						    	</c:if>
					<c:if test="${reply.confirmResult=='0'}">
						    		Э��
						    	</c:if>
				</td>
			</tr>
		 <tr class=trwhite>
				    <td class="tdulleft">���Ϸ����ˣ�</td>
				    <td class="tdulright" >${replyman}</td>
				    <td class="tdulleft">���Ϸ�����λ��</td>
				    <td class="tdulright" colspan="3">${contraName}</td>
		   </tr>
			<c:if test="${reply.confirmResult=='0'}">
				<tr class=trcolor>
					<td class="tdulleft">
						��ע��
					</td>
					<td class="tdulright" colspan="5" style="word-break:break-all;width:500px;" >
						<c:out value="${reply.replyRemark}"></c:out>
					</td>
				</tr>
			</c:if>
			<c:if test="${reply.confirmResult=='1'}">
			<tr class=trcolor>
				<td class="tdulleft">
					��Ϲ��¶Σ�
				</td>
				<td class="tdulright" colspan="5">
					<apptag:trunk id="trunk" state="view"  value="${reply.trunkids}"/>
				</td>
			</tr>
			<tr class=trwhite>
				<td class="tdulleft">
					�����豸�����أ�
				</td>
				<td class="tdulright" >
						<apptag:quickLoadList style="width:130px" keyValue="${reply.terminalAddress}" cssClass="select" name="terminalAddress" listName="terminal_address" type=""></apptag:quickLoadList>
				</td>
				  <td class="tdulleft">����λ��:</td>
				    <td class="tdulright" colspan="3">
				    	<a href="javascript:getXY();">�鿴</a>
				    </td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">
					��ϵص�������
				</td>
				<td class="tdulright" colspan="5">
					<c:out value="${reply.brokenAddress}"></c:out>
				</td>
			</tr>
			<tr class=trwhite>
				<td class="tdulleft">
					Ӱ��ҵ��ʱ�Σ�
				</td>
				<td class="tdulright" colspan="5">
					<c:out value="${reply.impressStartTime}"></c:out>
					-
					<c:out value="${reply.impressEndTime}"></c:out>
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft"> 
					Ӱ��ҵ�����ͣ�
				</td>
				<td class="tdulright" colspan="5">
					<apptag:quickLoadList  style="width:130px" cssClass="select" keyValue="${reply.impressType}" id="impressTypeArray" name="impressTypeArray" listName="impress_type" type="look"></apptag:quickLoadList>
				</td>
			</tr>
			 <tr class=trwhite>
				 <td class="tdulleft">�������¶Σ�</td>
				 <td class="tdulright" colspan="5"><c:out value="${reply.otherCable}"></c:out></td>
			</tr>
				<tr class=trcolor>
					 		<td class="tdulleft">�ֳ�ͼƬ��</td>
							 <td class="tdulright" colspan="5">
							 	<apptag:image entityId="${reply.id}" entityType="LP_TROUBLE_REPLY"></apptag:image>
							 </td>
					 	</tr>
			<tr class=trwhite>
				<td class="tdulleft">
					�������ͣ�
				</td>
				<td class="tdulright" colspan="5">
					<apptag:quickLoadList style="width:130px" keyValue="${reply.troubleType}" cssClass="select" name="troubleType" listName="lp_trouble_type" type=""></apptag:quickLoadList>
				</td>
			</tr>
			
			<tr class=trcolor>
				<td class="tdulleft">
					���ԭ��������
				</td>
				<td class="tdulright" colspan="5" style="word-break:break-all;width:500px;" >
					<c:out value="${reply.brokenReason}"></c:out>
				</td>
			</tr>
			<tr class=trwhite>
				<td colspan="6">
					<table border="1" cellpadding="0" cellspacing="0" width="100%">
						<logic:notEmpty name="process">
							<tr>
								<td colspan="8">
									�������
								</td>
							</tr>

							<tr>
								<td>
									�鿴�켣
								</td>
								<td>
									����ʱ��
								</td>
								<td>
									������Ա
								</td>
								<td>
									�豸���
								</td>
								<td>
									�������ʱ��
								</td>
								<td>
									��������ֳ�ʱ��
								</td>
								<td>
									�ҵ����ϵ�ʱ��
								</td>
								<td>
									�����ֳ�ʱ��
								</td>
							</tr>
						</logic:notEmpty>
						<bean:size id="prosize" name="process"/>
						<logic:iterate id="proce" name="process" indexId="len">
							<tr>
									<c:if test="${len==0}">
				    				   <td rowspan="${prosize}"><a href="javascript:viewProcess();">�鿴</a></td>
				    				</c:if>
								<td>									
									<bean:write name="proce" property="start_time_ref" />
								</td>
								<td>
									<bean:write name="proce" property="arrive_proess_man" />
								</td>
								<td>
									<bean:write name="proce" property="arrive_terminal_id" />
								</td>
								<td>									
									<bean:write name="proce" property="arrive_time_ref" />
								</td>
								<td>								
									<bean:write name="proce" property="arrive_trouble_time_ref" />
								</td>
								<td>								
									<bean:write name="proce" property="find_trouble_time_ref" />
								</td>
								<td>									
									<bean:write name="proce" property="return_time_ref" />
								</td>
							</tr>
						</logic:iterate>
					</table>
				</td>
			</tr>
			 <tr class=trwhite>
				<td class="tdulleft">���Ϸ���ʱ�䣺</td>
				<td class="tdulright">
					<bean:write  name="bean" property="trouble_start_time" format="yyyy/MM/dd HH:mm:ss" />
				</td>
				<td class="tdulleft">��������ʱ�䣺</td>
			    <td class="tdulright" colspan="3"><bean:write name="reply" property="troubleAcceptTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">�������ʱ�䣺</td>
				<td class="tdulright"><bean:write name="reply" property="arriveTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td class="tdulleft">��������ֳ�ʱ�䣺</td>
				<td class="tdulright" colspan="3"><bean:write name="reply" property="arriveTroubleTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr class=trwhite>
				<td class="tdulleft">�жϹ��ϵ�ʱ�䣺</td>
				<td class="tdulright" ><bean:write name="reply" property="judgeTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td class="tdulleft">�ҵ����ϵ�ʱ�䣺</td>
			    <td class="tdulright" colspan="3"><bean:write name="reply" property="findTroubleTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr class=trcolor>
			    <td class="tdulleft">���ϻָ�ʱ�䣺</td>
				<td class="tdulright"><bean:write name="reply" property="renewTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td class="tdulleft">���½������ʱ�䣺</td>
				<td class="tdulright" colspan="3"><bean:write name="reply" property="completeTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr class=trwhite>
				<td class="tdulleft">
					�����ֳ�ʱ�䣺
				</td>
				<td class="tdulright">
					<bean:write name="reply" property="returnTime" format="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td class="tdulleft">
					�������ϵ�������
				</td>
				<td class="tdulright" colspan="4">
					<c:if test="${not empty hidden}">
					   <a href="javascript:viewHideDangerForm('<bean:write name="hidden"  property="id"/>')"><bean:write name="hidden" property="name"/></a>
				   </c:if>
				</td>
			</tr>
			<tr class=trcolor>
				<td colspan="6">				
					<apptag:materialselect label="ʹ�ò���" displayType="view"
									objectId="${reply.id}" useType="trouble" materialUseType="Use"></apptag:materialselect>
				</td>
			</tr>
			<tr class=trcolor>
				<td colspan="6">					
								<apptag:materialselect label="���ղ���" displayType="view"
									objectId="${reply.id}" useType="trouble"
									materialUseType="Recycle"></apptag:materialselect>
				</td>
			</tr>
						<tr class=trcolor>
							<td colspan="6">
								&nbsp;&nbsp;������Ա
							</td>
						</tr>
						<tr class=trwhite>
							<td class="tdulleft">
								�����ˣ�
							</td>
							<td  colspan="5"><bean:write name="responsibles" /></td>
						</tr>
						<tr class=trcolor>
							<td class="tdulleft"> ���ϲ�����Ա��</td>
							<td colspan="5"> <bean:write name="testmans"/></td>
						</tr>
						<tr class=trwhite>
							<td class="tdulleft" >
								�ֳ�����������Ա��
							</td>
							<td colspan="5"><bean:write name="mendmans" /></td>
						</tr>
			<tr class=trcolor>
				<td class="tdulleft">
					�����ʩ������
				</td>
				<td class="tdulright" colspan="5" style="word-break:break-all;width:500px;" >
					<c:out value="${reply.processRemark}"></c:out>
				</td>
			</tr>
			<tr class=trwhite>
				<td class="tdulleft">
					��������ݼ�¼��
				</td>
				<td class="tdulright" colspan="5" style="word-break:break-all;width:500px;" >
					<c:out value="${reply.processRecord}"></c:out>
				</td>
			</tr>
			
			<tr class=trcolor>
				<td class="tdulleft">
					�������⴦��
				</td>
				<td class="tdulright" colspan="5" style="word-break:break-all;width:500px;" >
					<c:out value="${reply.otherIssue}"></c:out>
				</td>
			</tr>
						  <tr class=trcolor>
						    <td class="tdulleft" height="22">�Ƿ񱨰���</td>
						    <td class="tdulright" colspan="3" >
						    <c:if test="${reply.isReportCase=='1'}">
						    	��
						    </c:if>
						    <c:if test="${reply.isReportCase=='0'}">
						    	��
						    </c:if>
						    </td>
						  </tr>
						  <tr class=trcolor>
						    <td class="tdulleft" height="22">����˵����</td>
						    <td class="tdulright" colspan="3" ><c:out value="${reply.reportCaseComment}"></c:out>
						  </tr>
						  <tr class=trcolor>
						    <td class="tdulleft" height="22">�Ƿ��գ�</td>
						    <td class="tdulright" colspan="3" >
						    <c:if test="${reply.isReportDanger=='1'}">
						    	��
						    </c:if>
						    <c:if test="${reply.isReportDanger=='0'}">
						    	��
						    </c:if>
						    </td>
						  </tr>
			<tr class=trwhite>
				<td class="tdulleft">
					��ע��
				</td>
				<td class="tdulright" colspan="5" style="word-break:break-all;width:500px;" >
					<c:out value="${reply.replyRemark}"></c:out>
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">
					������
				</td>
				<td class="tdulright" colspan="5">
					<apptag:upload cssClass="" entityId="${reply.id}"
						entityType="LP_TROUBLE_REPLY" state="look"/>
				</td>
			</tr>
			<tr  class=trwhite>
				<td class="tdulleft">����ԭ��</td>
			    <td class="tdulright" colspan="5"><c:out value=" ${troubleReasonName}"></c:out></td>
			</tr>
			<tr class=trcolor><td  class="tdulleft">��˲�ͨ��������</td><td class="tdulright" colspan="5"><c:out value="${reply.notPassedNum}"></c:out></td></tr>
      </c:if>
      			<logic:notEmpty name="approves"> 
					 		<tr class=trwhite align="center">
							 	<td colspan="6" align="left">�����ϸ��Ϣ</td>
						    </tr>
							<tr  class=trcolor>
								<td colspan="6">
							    <table width="100%">
							    <tr>
							 	<td width="10%">�����</td><td width="17%">���ʱ�� </td>
							 	<td width="10%">��˽�� </td><td width="40%" >������ </td><td width="18%">����</td>
							   </tr>
							 <c:forEach items="${approves}" var="approve"  varStatus="loop">
							 	<c:if test="${loop.count%2==0 }"> <tr class=trwhite></c:if>
							 	<c:if test="${loop.count%2==1 }"> <tr class=trcolor></c:if>
							 	<tr>
							 		<td><bean:write name="approve" property="username"/></td>
							 		<td><bean:write name="approve" property="approve_time"/></td>
							 		<td><bean:write name="approve" property="approve_result"/></td>
							 		<td style="word-break:break-all;width:350px;"><bean:write name="approve" property="approve_remark"/></td>
							 		<td><bean:define id="appid" name="approve" property="id"></bean:define>
										<apptag:upload cssClass="" entityId="${appid}"
											entityType="LP_APPROVE_INFO" state="look"></apptag:upload>
									</td>
									</tr>
							 </c:forEach>
							 	</table>
									</td>
							 	</tr>
				</logic:notEmpty>
			<tr align="center" class=trcolor>
				<td colspan="6">
			     	<c:if test="${isreaded=='false' && queryact=='no' && isread=='true'}">
				    	  <html:submit value="���Ķ�" styleClass="button"></html:submit>
				    </c:if>
				    <html:button property="action" styleClass="button"
						onclick="javascript:viewHistoryDetail();">�鿴������ʷ</html:button>
					<html:button property="action" styleClass="button"
						onclick="javascript:history.go(-1)">����</html:button>
				</td>
			</tr>
		</table>
		 </html:form>
	</body>
</html>
