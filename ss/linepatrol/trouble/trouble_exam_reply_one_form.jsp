<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>���Ͽ���</title>
		    <script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
			<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
			<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
			<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
			<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
		<script type="text/javascript">
		function checkAddInfo() {
			/*
			var score = document.getElementsByName("entityScore");
			var len = score.length;
			var num=0;
			for(var i = 0;i<len;i++){
				if(score[i].checked==true){
				  num++;
				}
			}
			if(num==0){
				alert("��ѡ��һ���������֣�");
				return;
			}
  			var remark = document.getElementById("entityRemark").value;
  			if(valCharLength(remark) > 1024) {
  				alert("��ע��Ϣ���ܳ���512�����ֻ���1024��Ӣ���ַ���")
              	return;
  			}
  			*/
  			if(!checkAppraiseDailyValid()){
  				return;
  			}
  			$('subbtn').disabled=true; 
  			saveEvaluate.submit();
  		}
  		
		function viewReplyDetail(obj){
			if(obj=="1"){
				document.getElementById("replydetail").style.display="";
				document.getElementById("showdetail").style.display="none";
				document.getElementById("hiddendetail").style.display="";
			}
			if(obj=="2"){
				document.getElementById("replydetail").style.display="none";
				document.getElementById("showdetail").style.display="";
				document.getElementById("hiddendetail").style.display="none";
			}
		}
		
		function valCharLength(Value){
		     var j=0;
		     var s = Value;
		     for(var i=0; i<s.length; i++) {
				if (s.substr(i,1).charCodeAt(0)>255) {
					j  = j + 2;
				} else { 
					j++;
				}
		     }
		     return j;
		}
		
		function getXY(){
	    	var userid = document.getElementById("userid").value;
	    	var pid = document.getElementById("replyid").value;
	    	var rid = document.getElementById("regionid").value;
	    	var URL="/WEBGIS/gisextend/igis.jsp?userid="+userid+"&actiontype=204&pid="+pid+"&rid="+rid;
	    	//alert(URL);
	       	myleft=(screen.availWidth-500)/2;
			mytop=120;
			mywidth=670;
			myheight=490;
	        window.open(URL,"getXY","height="+myheight+",width="+mywidth+",status=1,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft+",resizable=no");
	    }
	    function viewAddr(x,y,addr){
	    	var userid = document.getElementById("userid").value;
	    	var URL="/WEBGIS/gisextend/igis.jsp?userid="+userid+"&actiontype=205&x="+x+"&y="+y+"&label="+addr;
	    	//alert(URL);
	       	myleft=(screen.availWidth-500)/2;
			mytop=150;
			mywidth=670;
			myheight=470;
	        window.open(URL,"getXY","height="+myheight+",width="+mywidth+",status=1,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft+",resizable=no");
	}
	
	//�鿴�켣�ط�
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
	</script>
	<style type="text/css">
		.label{
			width:30%;
			text-align: right;
		}
		.value{
			width:70%;
			text-align:left;
		}
		.textArea{
			width:400px;
		}
		
	</style>
	</head>

	<body>
		<br>
		<template:titile value="���Ͽ���" />
		<html:form action="/troubleExamAction.do?method=saveEvaluate"
			styleId="saveEvaluate">
			<input type="hidden" name="troubleid" id="troubleid" value="<bean:write name="bean" property="tid"/>"/>
			<input type="hidden" id="replyid" name="replyid" value="<bean:write name="reply" property="id"/>"/>
			<input type="hidden" id="userid" value="<bean:write name="LOGIN_USER" property="userID"/>"/>
			<input type="hidden" id="regionid" value="<bean:write name="bean" property="region_id"/>"/>
			<table width="80%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				 <tr class=trcolor>
				    <td colspan="2">
				    	<span>���ϵ���ţ�<bean:write name="bean" property="trouble_id"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>				   
				    </td>
				    <td>�����ˣ�<bean:write name="bean" property="username"/></td>
				    <td>����ʱ�䣺 <bean:write name="bean" property="reply_submit_time"/> </td>
				  </tr>
				  <tr>
				  	<td class="tdulleft" >�����ɵ���λ��</td>
				    <td class="tdulright"><bean:write name="bean" property="deptname"/></td>
				    <td class="tdulleft">�Ƿ�Ϊ�ش���ϣ�</td>
				    <td class="tdulright">
				    	<bean:write name="bean" property="is_great_trouble"/>
				    </td>
				  </tr>
				  <tr>
				    <td class="tdulleft">���Ϸ���ʱ�ޣ�</td>
				    <td class="tdulright" colspan="3">
				    	<bean:write name="bean" property="reply_deadline"/>
				    </td>
				  </tr>
				  <tr class=trwhite>
				  	
				  	<td class="tdulleft">���Ϸ���ʱ�䣺</td>
				  	<td class="tdulright"><bean:write name="bean" property="trouble_start_time"/></td>
				    <td class="tdulleft">�����ɷ�ʱ�䣺</td>
				    <td class="tdulright"><bean:write name="bean" property="trouble_send_time"/></td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft">����λ�ã�</td>
				    <td class="tdulright" colspan="3">
				    	<a href="javascript:getXY();">�鿴</a>
				    </td>
				  </tr>
				  <tr class=trwhite>
				  	<td colspan="6">
				  		<div id="showdetail" ><a href="javascript:viewReplyDetail('1');">�鿴��ϸ��Ϣ</a></div>
				  		<div id="hiddendetail" style="display:none"><a href="javascript:viewReplyDetail('2');">������ϸ��Ϣ</a></div>
				  	</td>
				  </tr>
				 <tbody id="replydetail" style="display:none">
					 	<tr class=trcolor>
					 		<td class="tdulleft">�����ɵ��ˣ�</td><td class="tdulright" colspan="3"><bean:write name="bean" property="trouble_send_man"/></td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">�����ɷ�ʱ�䣺</td>
					 		<td class="tdulright"><bean:write name="bean" property="trouble_send_time"/></td>
					 		<td class="tdulleft">�����˽�ɫ��</td>
					 		<td class="tdulright">
					 			<c:if test="${reply.confirmResult=='1'}">
						    		����
						    	</c:if>
						    	<c:if test="${reply.confirmResult=='0'}">
						    		Э��
						    	</c:if>
					 		</td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">��Ϲ��¶Σ�</td>
					 		<td class="tdulright" colspan="3">
					 			<apptag:trunk id="trunk" state="view"  value="${reply.trunkids}"/>
					 		</td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">�����豸�����أ�</td>
					 		<td class="tdulright" colspan="3">
					 			<apptag:quickLoadList style="width:130px" keyValue="${reply.terminalAddress}" cssClass="select" name="terminalAddress" listName="terminal_address" type=""></apptag:quickLoadList>
					 		</td>
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
					 		<td class="tdulleft">�������¶Σ�</td>
					 		<td class="tdulright" colspan="3"><c:out value="${reply.otherCable}"></c:out></td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">�ֳ�ͼƬ��</td>
							 <td class="tdulright" colspan="3">
							 	<apptag:image entityId="${reply.id}" entityType="LP_TROUBLE_REPLY"></apptag:image>
							 </td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">�������ͣ�</td>
					 		<td class="tdulright" colspan="3"> <apptag:quickLoadList cssClass="select" keyValue="${reply.troubleType}"  id="troubleReasonId" name="troubleReasonId" listName="lp_trouble_type" type="look"></apptag:quickLoadList></td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">���ԭ��������</td>
					 		<td class="tdulright" colspan="3" style="word-break:break-all;width:500px;" >
					 			<c:out value="${reply.brokenReason}"></c:out>
					 		</td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td colspan="4">
					 			<table border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
					 				<logic:notEmpty name="process">
					 				<tr>
					 					<td class="tdulleft" colspan="8">�������</td>
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
				    					      <bean:write name="proce" property="arrive_trouble_time_ref"/> &nbsp; 
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
						   <bean:write name="bean" property="trouble_start_time"/>
						    </td>
						    <td class="tdulleft">��������ʱ�䣺</td>
						    <td class="tdulright" ><bean:write name="reply" property="troubleAcceptTime" format="yyyy-MM-dd HH:mm:ss"/></td>
						  </tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">�������ʱ�䣺</td>
					 		<td class="tdulright"><bean:write name="reply" property="arriveTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					 		<td class="tdulleft">��������ֳ�ʱ�䣺</td>
					 		<td class="tdulright" ><bean:write name="reply" property="arriveTroubleTime" format="yyyy-MM-dd HH:mm:ss"/></td>
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
					 	<tr class=trcolor>
					 		<td class="tdulleft">�����ֳ�ʱ�䣺</td>
					 		<td class="tdulright"><bean:write name="reply" property="returnTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					 		<td class="tdulleft">
								�������ϵ�������
							</td>
					 		<td class="tdulright" >
					 			<c:if test="${not empty hidden}">
								   <a href="javascript:viewHideDangerForm('<bean:write name="hidden"  property="id"/>');"><bean:write name="hidden" property="name"/></a>
							   </c:if>
					 		</td>
					 	</tr>
					 	<tr class=trcolor>
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
					 		<td class="tdulright" colspan="3" style="word-break:break-all;width:500px;" >
					 			<c:out value="${reply.processRemark}"></c:out>
					 		</td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">��������ݼ�¼��</td>
					 		<td class="tdulright" colspan="3" style="word-break:break-all;width:500px;" >
					 			<c:out value="${reply.processRecord}"></c:out>
					 		</td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">�������⴦��</td>
					 		<td class="tdulright" colspan="3" style="word-break:break-all;width:500px;" >
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
					 		<td class="tdulleft">��ע��</td>
					 		<td class="tdulright" colspan="3" style="word-break:break-all;width:500px;" >
					 			<c:out value="${reply.replyRemark}"></c:out>
					 		</td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">������</td><td class="tdulright" colspan="3"><apptag:upload cssClass="" entityId="${reply.id}" entityType="LP_TROUBLE_REPLY" state="look"></apptag:upload></td>
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
					     	<table width="100%"><tr>
							 	<td>�����</td><td>���ʱ�� </td><td>��˽�� </td><td>������ </td><td>����</td>
							 </tr>
							 <c:forEach items="${approves}" var="approve"  varStatus="loop">
							 	<c:if test="${loop.count%2==0 }"> <tr class=trwhite></c:if>
							 	<c:if test="${loop.count%2==1 }"> <tr class=trcolor></c:if>
							 		<td><bean:write name="approve" property="username"/></td>
							 		<td><bean:write name="approve" property="approve_time"/></td>
							 		<td><bean:write name="approve" property="approve_result"/></td>
							 		<td style="word-break:break-all;width:350px;"><bean:write name="approve" property="approve_remark"/></td>
							 		<bean:define id="appid" name="approve" property="id"></bean:define>
							 		<td><apptag:upload state="look" cssClass="" entityId="${appid}" entityType="LP_APPROVE_INFO" /></td>
							 	</tr>
							 </c:forEach>
							 </table>
							 </td>
							 </tr>
						 </logic:notEmpty>
					 	</tbody>
					</table>
				 </div>
				<!-- ��ά��λ�ճ����� -->
				<apptag:appraiseDailyDaily businessId="${replyid }" contractorId="${contractorId }" businessModule="trouble" 
						displayType="input" tableStyle="width:80%; border-top: 0px;"></apptag:appraiseDailyDaily>
				<apptag:appraiseDailySpecial businessId="${replyid }" contractorId="${contractorId }" businessModule="trouble" 
						displayType="input" tableStyle="width:80%; border-top: 0px;"></apptag:appraiseDailySpecial>
				 <br/><table width="80%"   border="0" align="center" cellpadding="3" cellspacing="0">
				  <tr align="center">
				    <td colspan="4">
				    <html:button property="action" styleClass="button" styleId="subbtn"
							onclick="checkAddInfo()">�ύ</html:button>
							<html:reset property="action" styleClass="button">����</html:reset>
							<input type="button" value="����" class="button" onclick="javascript:history.back();"/>
				    </td>
				  </tr>
			</table>
		</html:form>
	</body>
</html>
