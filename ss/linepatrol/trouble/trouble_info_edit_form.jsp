<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linepatrol.trouble.services.TroubleConstant" %>
<html>
	<head>
		<title>�����ɵ�</title>
		    <script language="javascript" src="${ctx}/linepatrol/js/verify_material.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validate.js" type=""></script>
			<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
		
		<script type="text/javascript">
		function checkAddInfo() {
			var troubleName =document.getElementById('troubleName').value;
				if(troubleName==""){
					alert("�������Ʋ���Ϊ��!");
					$('troubleName').focus();
					return false;
				}
				var troubleStartTime = document.getElementById('troubleStartTime').value;
				if(troubleStartTime==""){
					alert("���Ϸ���ʱ�䲻��Ϊ��!");
					$('troubleStartTime').focus();
					return false;
				}
				var troubleSendMan =  document.getElementById('troubleSendMan').value;
				if(troubleSendMan==""){
					alert("�����ɷ��˲���Ϊ��!");
					$('troubleSendMan').focus();
					return false;
				}
			var replyDeadline = document.getElementById('replyDeadline').value;
			if(replyDeadline==""){
				alert("���Ϸ���ʱ�޲���Ϊ��!");
				return false;
			}
				var eomsCodes =  document.getElementById('eomsCodes').value;
				if(eomsCodes==""){
					alert("eoms���Ų���Ϊ��!");
					$('eomsCodes').focus();
					return false;
				}
				if(eomsCodes.indexOf("��")!=-1){
					//alert(eomsCodes);
					eomsCodes=eomsCodes.replace(/��/gi,",");
					//alert(eomsCodes);
					//return false;
				}
				var troubleRemark = document.getElementById('troubleRemark').value;
		  		if(valCharLength(troubleRemark) > 1024) {
		  			 alert("�����������ܳ���512�����ֻ���1024��Ӣ���ַ���")
		             return false;
		  		}
		  		
			var confirmResult = document.getElementById('confirmResult').value;
			if(confirmResult=="1"){
	  			var troubleAcceptTime = document.getElementById('troubleAcceptTime').value;
				if(troubleAcceptTime==""){
					alert("��������ʱ�䲻��Ϊ��!");
					$('troubleAcceptTime').focus();
					return;
				}
			    var approvers = $('saveTroubleReply').approver.value;;
				if(approvers==""){
					alert("��ѡ�������!");
		  			return ;
	  			}
				
				    $('subbtn').disabled=true; 
					$('saveTroubleReply').submit();
				
			}else{
				var approvers = $('saveTroubleReply').approver.value;
				if(approvers==""){
					alert("��ѡ�������!");
		  			return ;
	  			}
	  			
	  			$('subbtn').disabled=true; 
	  			$('saveTroubleReply').submit();
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
			var tid = document.getElementById("troubleId").value;
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
  		function changeReplyDeadline(troubleType){
  			var troubleSendTime=convertStrToDate("<%=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())%>");
  			var deadlineSelect=document.forms[0].elements["deadline_select"];
  			var deadline="";
  			for(i=0;i<deadlineSelect.options.length;i++){
  				if(troubleType==deadlineSelect.options[i].value){
  					deadline=deadlineSelect.options[i].text;
  					break;
  				}
  			}
  			troubleSendTime.setTime(troubleSendTime.getTime()+parseFloat(deadline)*3600*1000);
  			$('saveTroubleReply').replyDeadline.value=troubleSendTime.getYear()+"/"+(troubleSendTime.getMonth()+1)+"/"+troubleSendTime.getDate()+" "+troubleSendTime.getHours()+":"+troubleSendTime.getMinutes()+":"+troubleSendTime.getSeconds();
  		}
	</script>
	</head>

	<body>
		
		<template:titile value="ƽ̨��׼ " />	
		<html:form action="/troubleReplyAction.do?method=dispatchApprove"
			styleId="saveTroubleReply" >
			<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				 <tr class=trwhite>
				    <td class="tdulleft">ָ�ɴ�ά��</td>
				    <td class="tdulright" >
						<c:forEach items="${unitlist}" var="unit">
							<bean:write name="unit" property="contractorname"/><br/>
						</c:forEach>
					</td>
					 <td class="tdulleft">���ϵ��ţ�</td>
				    <td class="tdulright">
				    	<c:out value="${trouble.troubleId}"></c:out>
				    </td>
				 </tr>
				  <tr class=trcolor >
				  	<td class="tdulleft">�������ƣ�</td>
				  	<td class="tdulright"><html:text property="troubleName" styleId="troubleName" value="${trouble.troubleName}" style="width:205"></html:text>&nbsp;&nbsp;<font color="red">*</font></td>
				    <td class="tdulleft" >���Ϸ���ʱ�䣺</td>
				    <td class="tdulright"><input name="troubleStartTime" id="troubleStartTime" class="Wdate" style="width:205"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate: '%y-%M-%d-%H-%m-%s' })" 
					 readonly value="<bean:write name="trouble" property="troubleStartTime" format="yyyy/MM/dd HH:mm:ss"/>" />
					 &nbsp;&nbsp;<font color="red">*</font></td>
					
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">�Ƿ�Ϊ�ش���ϣ�</td>
				    <td class="tdulright">
					    <c:if test="${trouble.isGreatTrouble==0}">
					      <input type="radio" name="isGreatTrouble" value="1" onclick="changeReplyDeadline(this.value);"  >��&nbsp;&nbsp;
					       <input type="radio" name="isGreatTrouble" value="0" onclick="changeReplyDeadline(this.value);"  checked="checked">��
					    </c:if>
					    <c:if test="${trouble.isGreatTrouble==1}">
					      <input type="radio" name="isGreatTrouble" value="1"  onclick="changeReplyDeadline(this.value);" checked="checked">��&nbsp;&nbsp;
					       <input type="radio" name="isGreatTrouble" value="0" onclick="changeReplyDeadline(this.value);"  >��
					    </c:if>
				    </td>
				    <td class="tdulleft">���Ϸ���ʱ�ޣ�</td>
				    <td class="tdulright">
				    	<span id="deadlineSpan" style="display:none;">
				        <c:if test="${deadline_map!=null}">
				        	<select name="deadline_select">
							<c:forEach items="${deadline_map}" var="oneDeadline">
								<option value="<c:out value="${oneDeadline.key}" />"><c:out value="${oneDeadline.value}" /></option>
							</c:forEach>
							</select>
						</c:if>
						</span>
						<fmt:formatDate value="${trouble.replyDeadline}" var="replyDeadline" pattern="yyyy/MM/dd HH:mm:ss"/>
						<input name="replyDeadline" id="replyDeadline" class="Wdate" style="width:155px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss' })" 
					 	readonly value="${replyDeadline }" />
				    </td>
				  </tr>
				  <tr>
				    <td class="tdulleft">�����ɷ��ˣ�</td>
				    <td class="tdulright"><html:text property="troubleSendMan" styleId="troubleSendMan" value="${trouble.troubleSendMan}" style="width:205" ></html:text>&nbsp;&nbsp;<font color="red">*</font></td>
				  </tr>
				   <tr class=trcolor >
				    <td class="tdulleft">ƽ̨��</td>
				    <td class="tdulright">
				    		<input type="text" name="connector" id="connector" value="${trouble.connector}"/>
				    	<!-- input type="radio" name="connector" value="1" onclick="judgeType(this.value)"  >����ƽ̨&nbsp;&nbsp;
				        <input type="radio" name="connector" value="0" onclick="judgeType(this.value)" checked="checked">����ƽ̨
				    	 -->
				    </td>
				    <td class="tdulleft">ƽ̨�绰��</td>
				    <td class="tdulright" >
				    	<input type="text" id="connectorTel" name="connectorTel" value="${trouble.connectorTel}" style="width:215px"/>
				    </td>
				  </tr>
				   <tr class=trwhite>
				    <td class="tdulleft">eoms���ţ�</td>
				    <td class="tdulright" colspan="3">
				    	<textarea name="eomsCodes" id="eomsCodes" rows="5" style="width:375px">${eoms}</textarea>
				    	<br/><font color="red">
				    	 * ��д˵����eoms����֮��ʹ��Ӣ��״̬�µġ�,��(����)�ָ�</font>
				    </td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft">����������</td>
				    <td class="tdulright" colspan="3"><html:textarea property="troubleRemark" styleId="troubleRemark" value="${trouble.troubleRemark}" rows="4" style="width:375px"></html:textarea></td>
				  </tr>
				   <tr class=trwhite>
				   		<td class="tdulleft">��Ӹ�����</td>
				   		<td class="tdulright"  colspan="3">
				   		  <apptag:upload state="edit" entityType="LP_TROUBLE_INFO" entityId="${trouble.id}" cssClass=""/>
				   		</td>
				   </tr>
				  <tr>
				  	<td colspan="4">
				  	</td>
				  </tr>
			<input type="hidden" id="replyid" name="replyid" value="<bean:write name="troubleReplyBean" property="id"/>"/>
			<input type="hidden" id="regionid" value="<bean:write name="trouble" property="regionID"/>"/>
			<input type="hidden" id="userid" value="<bean:write name="LOGIN_USER" property="userID"/>"/>
			<input type="hidden" id="id" name="id" value="<bean:write name="troubleReplyBean" property="id"/>" />
			<input type="hidden" id="unitid" name="unitid" value="<bean:write name="unitid"/>" />
			<input type="hidden" id="troubleId" name="troubleId" value="<bean:write name="trouble" property="id"/>" />
			<input type="hidden" name="replySubmitTime" value="<bean:write name="troubleReplyBean" property="replySubmitTime" format="yyyy/MM/dd HH:mm:ss"/>"/>
			<input type="hidden" id="notPassedNum" name="notPassedNum" value="<bean:write name="troubleReplyBean" property="notPassedNum"/>" />
			<input type="hidden" id="replyManId" name="replyManId" value="<bean:write name="troubleReplyBean" property="replyManId"/>" />
			<input type="hidden" id="contractorId" name="contractorId" value="<bean:write name="troubleReplyBean" property="contractorId"/>" />
			
				  <tr class=trcolor>
				    <td class="tdulleft">���Ϸ����ˣ�</td>
				    <td class="tdulright" >${replyman.userName}</td>
				    <td class="tdulleft">���Ϸ�����λ��</td>
				    <td class="tdulright" >${contractor.contractorName}</td>
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">�����˽�ɫ��</td>
				    <td  class="tdulright">
					    <c:if test="${troubleReplyBean.confirmResult=='1'}">
					    ����
					    </c:if>
					    <c:if test="${troubleReplyBean.confirmResult=='0'}">
					    Э��
					    </c:if>
					     <input type="hidden" name="confirmResult" id="confirmResult" value="${troubleReplyBean.confirmResult}"   />
				    </td>
				    <td class="tdulleft">�����ɷ�ʱ�䣺</td>
				    <td class="tdulright">
				    	<bean:write  name="trouble"  property="troubleSendTime" format="yyyy/MM/dd HH:mm:ss"/>
				    </td>
				  </tr>
				  <c:if test="${troubleReplyBean.confirmResult=='1'}">
				  <tbody id="y">
				   <tr class=trcolor>
				    <td class="tdulleft">��Ϲ��¶Σ�</td>
				   <td class="tdulright"  colspan="3">
				    	<apptag:trunk id="trunk"  value="${troubleReplyBean.trunkids}" state="view"></apptag:trunk>
				    </td>
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">Ӱ��ҵ�����ͣ�</td>
				    <td class="tdulright" >
				    	<apptag:quickLoadList  style="width:130px" cssClass="select" keyValue="${troubleReplyBean.impressType}" id="impressTypeArray" name="impressTypeArray" listName="impress_type" type="look"></apptag:quickLoadList>
				    </td>
				    <td class="tdulleft">Ӱ��ҵ��ʱ�Σ�</td>
				    <td class="tdulright" >
				    	<bean:write  name="troubleReplyBean" property="impressStartTime" format="HH:mm"/>
				    	-
				        <bean:write name="troubleReplyBean" property="impressEndTime"/>
					</td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft">�����豸�����أ�</td>
				    <td class="tdulright">
				    	<apptag:quickLoadList  style="width:130px" keyValue="${troubleReplyBean.terminalAddress}" cssClass="radio" id="terminalAddress" name="terminalAddress" listName="terminal_address" type="look"></apptag:quickLoadList>
				    </td>
				    <td class="tdulleft"> ����λ�ã�</td>
				    <td class="tdulright" >
				   		 <a href="javascript:getXY();">�鿴</a>
				    </td>
				  </tr>
				  <tr class=trcolor>
					 		<td class="tdulleft">�ֳ�ͼƬ��</td>
							<td class="tdulright" colspan="3">
							 	<apptag:image entityId="${troubleReplyBean.id}" entityType="LP_TROUBLE_REPLY"></apptag:image>
							 </td>
					</tr>
				  <tr class=trwhite>
				    <td class="tdulleft">��ϵص������� </td>
				    <td class="tdulright" colspan="3">${troubleReplyBean.brokenAddress}</td>
				  </tr>
				   <tr class=trcolor>
				    <td class="tdulleft">�������¶Σ�</td>
				    <td class="tdulright" colspan="3">${troubleReplyBean.otherCable}</td>
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">�������ͣ�</td>
				    <td class="tdulright" colspan="3">
				    	<apptag:quickLoadList style="width:130px" keyValue="${troubleReplyBean.troubleType}" cssClass="select" id="troubleType" name="troubleType" listName="lp_trouble_type" type="look"></apptag:quickLoadList>
				    </td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft">���ԭ��������</td>
				    <td class="tdulright" colspan="3">${troubleReplyBean.brokenReason}</td>
				  </tr>
				    <logic:notEmpty name="process">
					  <tr class=trwhite>
						  <td class="tdulleft">�������</td>
						  <td class="tdulright" colspan="3"> 
				    		<table width="100%" id="tab" align="center" cellpadding="0" cellspacing="0" border="0">
				    			<tr>
				    				<td>�鿴�켣</td><td>����ʱ��</td><td>������Ա</td><td>�豸���</td><td>�������ʱ��</td>
				    				<td>��������ֳ�ʱ��</td><td>�ҵ����ϵ�ʱ��</td><td>�����ֳ�ʱ��</td>
				    			</tr>
				    			    <bean:size id="prosize" name="process"/>
				    				<c:forEach items="${process}" var="proce" varStatus="loop">
				    					<tr id="del${loop.index}">
				    					     <c:if test="${loop.index==0}">
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
				    			</c:forEach>
				    		</table>
				      </td>
				      </tr>
				  </logic:notEmpty>
				
				   <tr class=trwhite>
				    <td class="tdulleft">���Ϸ���ʱ�䣺</td>
				    <td class="tdulright">
				    <bean:write  name="trouble" property="troubleStartTime" format="yyyy/MM/dd HH:mm:ss" />
				    </td>
				    <td class="tdulleft">��������ʱ�䣺</td>
				    <td class="tdulright" ><input name="troubleAcceptTime" id="troubleAcceptTime" class="Wdate"  style="width:160"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate: '%y-%M-%d-%H-%m-%s'})" 
					value="<bean:write name="troubleReplyBean" property="troubleAcceptTime" format="yyyy/MM/dd HH:mm:ss"/>"
					readonly/>&nbsp;&nbsp;<font color="red">*</font></td>
				  </tr>
				  </tr>
				   <tr class=trcolor>
				    <td class="tdulleft">�������ʱ�䣺</td>
				    <td class="tdulright"><bean:write name="troubleReplyBean" property="arriveTime" format="yyyy/MM/dd HH:mm:ss"/></td>
				    <td class="tdulleft">��������ֳ�ʱ�䣺</td>
				    <td class="tdulright" ><bean:write name="troubleReplyBean" property="arriveTroubleTime" format="yyyy/MM/dd HH:mm:ss"/></td>
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">�жϹ��ϵ�ʱ�䣺</td>
				    <td class="tdulright"><bean:write name="troubleReplyBean" property="judgeTime" format="yyyy/MM/dd HH:mm:ss"/></td>
				    <td class="tdulleft">�ҵ����ϵ�ʱ�䣺</td>
				    <td class="tdulright" colspan="3"><bean:write name="troubleReplyBean" property="findTroubleTime" format="yyyy/MM/dd HH:mm:ss"/></td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft">���ϻָ�ʱ�䣺</td>
				    <td class="tdulright" ><bean:write name="troubleReplyBean" property="renewTime" format="yyyy/MM/dd HH:mm:ss"/></td>
					<td class="tdulleft">���½������ʱ�䣺</td>
				    <td class="tdulright" ><bean:write name="troubleReplyBean" property="completeTime" format="yyyy/MM/dd HH:mm:ss"/></td>
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">�����ֳ�ʱ�䣺</td>
				    <td class="tdulright" class="value"><bean:write name="troubleReplyBean" property="returnTime" format="yyyy/MM/dd HH:mm:ss"/></td>
				 	<td class="tdulleft">�������ϵ�������</td>
				 	 <td class="tdulright">
							<c:if test="${not empty hidden}">
							   <a href="javascript:viewHideDangerForm('<bean:write name="hidden"  property="id"/>');"><bean:write name="hidden" property="name"/></a>
						   </c:if>
				    </td>
				  </tr>
				        <tr class=trcolor>
					 		<td colspan="4">					 			
					 						<apptag:materialselect label="ʹ�ò���" displayType="view" objectId="${troubleReplyBean.id}" useType="trouble" materialUseType="Use" ></apptag:materialselect>
					 		</td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td colspan="4">
					 						<apptag:materialselect label="���ղ���" displayType="view" objectId="${troubleReplyBean.id}" useType="trouble" materialUseType="Recycle" ></apptag:materialselect>
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
				  <tr>
				    <td class="tdulleft">�����ʩ���� ��</td>
				    <td class="tdulright" colspan="3">${troubleReplyBean.processRemark}</td>
				  </tr>
				   <tr class=trwhite>
					  <td class="tdulleft" >��������ݼ�¼��</td>
					  <td class="tdulright" colspan="3">${troubleReplyBean.processRecord}</td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft" height="20">�������⴦��</td>
				    <td class="tdulright" colspan="3">${troubleReplyBean.otherIssue}</td>
				  </tr>
						  <tr class=trcolor>
						    <td class="tdulleft" height="22">�Ƿ񱨰���</td>
						    <td class="tdulright" colspan="3" >
						    <c:if test="${troubleReplyBean.isReportCase=='1'}">
						    	��
						    </c:if>
						    <c:if test="${troubleReplyBean.isReportCase=='0'}">
						    	��
						    </c:if>
						    </td>
						  </tr>
						  <tr class=trcolor>
						    <td class="tdulleft" height="22">����˵����</td>
						    <td class="tdulright" colspan="3" ><c:out value="${troubleReplyBean.reportCaseComment}"></c:out>
						  </tr>
						  <tr class=trcolor>
						    <td class="tdulleft" height="22">�Ƿ��գ�</td>
						    <td class="tdulright" colspan="3" >
						    <c:if test="${troubleReplyBean.isReportDanger=='1'}">
						    	��
						    </c:if>
						    <c:if test="${troubleReplyBean.isReportDanger=='0'}">
						    	��
						    </c:if>
						    </td>
						  </tr>
				
				 </tbody>
				  </c:if>
				  	<tr class="trwhite">
				    <td class="tdulleft" height="17">��ע��</td>
				    <td class="tdulright" colspan="3">${troubleReplyBean.replyRemark}</td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft" height="17">����������</td>
				    <td class="tdulright" colspan="3">
				    	 <apptag:upload state="look" cssClass="" entityId="${troubleReplyBean.id}" entityType="LP_TROUBLE_REPLY"/>
				    </td>
				  </tr>
				   <tr class=trwhite>
				    	<apptag:approverselect inputName="approver,mobiles" label="�����"
				    	 approverType="approver" objectType="LP_TROUBLE_REPLY" objectId="${troubleReplyBean.id}" 
				    	  colSpan="3" spanId="approver" inputType="radio" notAllowName="reads"></apptag:approverselect>
				  </tr>
				  <tr class=trcolor>
				    	<apptag:approverselect inputName="reads,rmobiles" label="������"  
				    	approverType="reader" objectType="LP_TROUBLE_REPLY" objectId="${troubleReplyBean.id}" 
				    	colSpan="3" spanId="reader" notAllowName="approver"></apptag:approverselect>
				  </tr>
				  <tr>
				  	<td colspan="4">
				  		<font color="red">��ѡ��Ŀ������������ʱ������д���ޡ�</font>
				  	</td>
				  </tr>
				 <tr  align="center">
				    <td colspan="4">
				    	 <html:button property="action" styleClass="button" styleId="subbtn" onclick="checkAddInfo()">�ύ</html:button>
				    	 <input type="reset" class="button" value="����"/>
				    	 <input type="button" class="button"  value="����" onclick="javascript:history.go(-1)"/>
				    </td>
				  </tr>
				  </table>
		</html:form>
		<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			//var valid = new Validation('saveTroubleInfo', {immediate : true, onFormValidate : formCallback});
		</script>
	</body>
</html>
