<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>�޸Ĺ��Ϸ�����</title>
		<script language="javascript" src="${ctx}/linepatrol/js/verify_material.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
		<script type="text/javascript">
		function judgeType(obj){
			if(obj=="1"){				
				document.getElementById("y").style.display="";
				document.getElementById("attachspan").style.display="";
			}
			if(obj=="0"){
				document.getElementById("y").style.display="none";	
				document.getElementById("attachspan").style.display="none";			
			}
		}
		
		
		function checkAddInfo() {
			var confirmResult = document.getElementsByName("confirmResult");
			if(confirmResult[0].checked && confirmResult[0].value=="1"){
				var trunk = document.getElementById("trunk");
	  			if(trunk.value == "") {
	  				alert("��Ϲ��¶β���Ϊ��!");
	  				return;
	  			}
	  	    	//var troubleEndTime = document.getElementById("troubleEndTime").value;
	  	    //	var troubleEndTime = saveTroubleReply.troubleEndTime;
				//if(troubleEndTime.value.length == 0){
				//	alert("���Ͻ���ʱ�䲻��Ϊ��!");
					//troubleEndTime.focus();
					//return ;
			//	}
				
				var addr = saveTroubleReply.terminalAddress;
				for(var x=0;x<addr.length;x++){ 
			        if(addr[x].checked) {  //���ѡ��   
			           break;
			        } 
			        if(x==addr.length-1){
			        	alert("�����豸�����ز���Ϊ��!");
			        	return ;
			        }
				}
	  			
	  			//var impressEndTime = document.getElementById("impressEndTime").value;
	  			var impressEndTime = saveTroubleReply.impressEndTime;
				if(impressEndTime.value.length == 0){
					alert("Ӱ��ҵ��ʱ�β���Ϊ��!");
					impressEndTime.focus();
					return false;
				}
	  				
	  			var objs = document.getElementsByName('impressTypeArray');   
			    var ii=0;   
				for(var i=0;i<objs.length;i++){  
				    if(objs[i].checked==true){            
				         ii++;                   
				    }   
				} 
				 if(ii==0){ 
				    alert("Ӱ��ҵ�����Ͳ���Ϊ��!");
				    return;
				 }
	  				
	  			var troubleGpsX = saveTroubleReply.troubleGpsX;
	  			if(troubleGpsX.value.length == 0) {
	  				alert("��ѡ�����λ������!");
	  				troubleGpsX.focus();
	  				return;
	  			}
			    var brokenAddress = saveTroubleReply.brokenAddress;
	  			if(brokenAddress.value.length == 0) {
	  				alert("����д��ϵص�����!");
	  				brokenAddress.focus();
	  				return;
	  			}
	  			
	  			if(valCharLength(brokenAddress.value) > 512) {
	  			    alert("��ϵص��������ܳ���256�����ֻ���512��Ӣ���ַ�!")
	              	return ;
	  			}
	  			
	  			var brokenReason = saveTroubleReply.brokenReason;
	  			if(brokenReason.value.length == 0) {
	  				alert("����д���ԭ������!");
	  				brokenReason.focus();
	  				return;
	  			}
	  			
	  			var troubleAcceptTime = saveTroubleReply.troubleAcceptTime;
				if(troubleAcceptTime.value.length == 0){
					alert("��������ʱ�䲻��Ϊ��!");
					troubleAcceptTime.focus();
					return ;
				}
	  			
	  		var arriveTime = saveTroubleReply.arriveTime;
				if(arriveTime.value.length == 0){
					alert("�������ʱ�䲻��Ϊ��!");
					arriveTime.focus();
					return ;
				}
				
				var arriveTroubleTime = saveTroubleReply.arriveTroubleTime;
				if(arriveTroubleTime.value.length == 0){
					alert("��������ֳ�ʱ�䲻��Ϊ��!");
					arriveTroubleTime.focus();
					return ;
				}
				
				var judgeTime = saveTroubleReply.judgeTime;
				if(judgeTime.value.length == 0){
					alert("�жϹ��ϵ�ʱ�䲻��Ϊ��!");
					judgeTime.focus();
					return ;
				}
				
	  	
	  			var findTroubleTime = saveTroubleReply.findTroubleTime;
				if(findTroubleTime.value.length == 0){
					alert("�ҵ����ϵ�ʱ�䲻��Ϊ��!");
					findTroubleTime.focus();
					return ;
				}
				
				//var renewTime = document.getElementById("renewTime").value;
			     var renewTime = saveTroubleReply.renewTime;
				if(renewTime.value.length ==0 ){
					alert("���ϻָ�ʱ�䲻��Ϊ��!");
					renewTime.focus();
					return ;
				}
				//var completeTime = document.getElementById("completeTime").value;
				var completeTime = saveTroubleReply.completeTime;
				if(completeTime.value.length==0){
					alert("���½������ʱ�䲻��Ϊ��!");
					completeTime.focus();
					return ;
				}
				//var returnTime = document.getElementById("returnTime").value;
				var returnTime = saveTroubleReply.returnTime;
				if(returnTime.value.length==0){
					alert("�����ֳ�ʱ�䲻��Ϊ��!");
					returnTime.focus();
					return ;
				}
				
				var responsible = saveTroubleReply.responsible;
	  			if(responsible.value.length == 0) {
	  				alert("����д������!");
	  				responsible.focus();
	  				return;
	  			}
	  			var testman = saveTroubleReply.testman;
	  			if(testman.value.length == 0) {
	  				alert("����д���ϲ�����Ա!");
	  				testman.focus();
	  				return;
	  			}
	  			var mendman = saveTroubleReply.mendman;
	  			if(mendman.value.length == 0) {
	  				alert("����д�ֳ�����������Ա!");
	  				mendman.focus();
	  				return;
	  			}
	  			var processRecord = saveTroubleReply.processRecord;
	  			if(processRecord.value.length == 0) {
	  				alert("����д��������ݼ�¼!");
	  				processRecord.focus();
	  				return;
	  			}
	  			
	  			var processRemark = saveTroubleReply.processRemark;
	  			if(processRemark.value.length == 0) {
	  				alert("����д�����ʩ����!");
	  				processRemark.focus();
	  				return;
	  			}
	  			
	  			var otherIssue = saveTroubleReply.otherIssue;
	  			if(otherIssue.value.length == 0) {
	  				alert("����д�������⴦��!");
	  				otherIssue.focus();
	  				return;
	  			}
	  			
					
	  			var remark = saveTroubleReply.replyRemark;
	  			if(valCharLength(remark.value) > 1024) {
	  			    alert("��ע��Ϣ���ܳ���512�����ֻ���1024��Ӣ���ַ���")
	              	return ;
	  			}
	  			var reportCaseComment=saveTroubleReply.reportCaseComment;
	  			if(valCharLength(reportCaseComment.value) > 500) {
	  			    alert("����˵���������ܳ���500�����ֻ���1000��Ӣ���ַ�!")
	              	return ;
	  			}
				if(verifyMaterial(saveTroubleReply)){
				    $('subbtn').disabled=true; 
					saveTroubleReply.submit();
				}
			}else{
					
	  			var remark = saveTroubleReply.replyRemark;
	  			if(valCharLength(remark.value) > 1024) {
	  			    alert("��ע��Ϣ���ܳ���512�����ֻ���1024��Ӣ���ַ���")
	              	return ;
	  			}
	  			var reportCaseComment=saveTroubleReply.reportCaseComment;
	  			if(valCharLength(reportCaseComment.value) > 500) {
	  			    alert("����˵���������ܳ���500�����ֻ���1000��Ӣ���ַ�!")
	              	return ;
	  			}
	  			$('subbtn').disabled=true; 
	  			saveTroubleReply.submit();
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
	
	function save(){
  			var btnstate = $('tempstate');
  			btnstate.value="tempsaveReply";
  			var remark = saveTroubleReply.replyRemark;
	  			if(valCharLength(remark.value) > 1024) {
	  			    alert("��ע��Ϣ���ܳ���512�����ֻ���1024��Ӣ���ַ���")
	              	return ;
	  		}
	  		var reportCaseComment=saveTroubleReply.reportCaseComment;
	  		if(valCharLength(reportCaseComment.value) > 500) {
	  		    alert("����˵���������ܳ���500�����ֻ���1000��Ӣ���ַ�!")
	        	return ;
	  		}
	  		if(verifyMaterial(saveTroubleReply)){
					 $('savebtn').disabled=true; 
	  	            saveTroubleReply.submit();
			}
  		}
	 
    
     function getXY(){
    	var userid = document.getElementById("userid").value;
    	var URL="/WEBGIS/gisextend/igis.jsp?actiontype=202&userid="+userid+"&eid=troubleGpsX";
       	myleft=(screen.availWidth-500)/2;
		mytop=220;
		mywidth=650;
		myheight=420;
        window.open(URL,"getXY","height="+myheight+",width="+mywidth+",status=1,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft+",resizable=no");
    }
    
        function viewAddr(x,y,addr){
	    	var userid = document.getElementById("userid").value;
	    	var URL="/WEBGIS/gisextend/igis.jsp?userid="+userid+"&actiontype=205&x="+x+"&y="+y+"&label="+addr;
	    	//alert(URL);
	       	myleft=(screen.availWidth-500)/2;
			mytop=220;
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
		mytop=220;
		mywidth=670;
		myheight=470;
	    window.open(URL,"viewPro","height="+myheight+",width="+mywidth+",status=1,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft+",resizable=no");
	}
	
	function judgeCR(){
		var cr = document.getElementById('cr').value;
		judgeType(cr);
	}
	
	var win;
		function showWin(id){
			var url="${ctx}/trunkAction.do?method=updateTrunk&id="+id+"&type=cable";
			win = new Ext.Window({
				layout : 'fit',
				width:500,
				height:300, 
				resizable:true,
				closeAction : 'close',
				modal:true,
				html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
				plain: true
			});
			win.show(Ext.getBody());
		}
		function close(){
			win.close();
		}
	</script>
	</head>

	<body>
		<br>
		<c:if test="${troubleReplyBean.approveState=='00'}">
		<template:titile value="���ϵǼǷ���" />
		</c:if>
		<c:if test="${troubleReplyBean.approveState=='1'}">
		<template:titile value="�޸Ĺ��ϵǼǷ���" />
		</c:if>
		<html:form action="/troubleReplyAction.do?method=editTroubleReply"
			styleId="saveTroubleReply" enctype="multipart/form-data" >
			<input type="hidden" id="userid" value="<bean:write name="LOGIN_USER" property="userID"/>"/>
			<input type="hidden" id="id" name="id" value="<bean:write name="troubleReplyBean" property="id"/>" />
			<input type="hidden" id="unitid" name="unitid" value="<bean:write name="unitid"/>" />
			<input type="hidden" id="troubleid" name="troubleid" value="<bean:write name="trouble" property="id"/>" />
			<input type="hidden" name="replySubmitTime" value="<bean:write name="troubleReplyBean" property="replySubmitTime" format="yyyy/MM/dd HH:mm:ss"/>"/>
			<input type="hidden" id="notPassedNum" name="notPassedNum" value="<bean:write name="troubleReplyBean" property="notPassedNum"/>" />
			
			<input type="hidden" id="cr" value="${troubleReplyBean.confirmResult}"/>
			<table  width="90%" align="center" cellpadding="3" cellspacing="0" class="tabout">
				  <tr class=trwhite>
				    <td class="tdulleft">���ϵ���ţ�</td>
				    <td class="tdulright"><bean:write name="trouble" property="troubleId"/></td>
				    <td class="tdulleft">�����ɵ���λ��</td>
				    <td class="tdulright"><c:out value="${deptname}"></c:out></td>
				  </tr>
				  <tr class=trcolor>
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
				  <tr class=trcolor>
				    <td class="tdulleft">�����ɵ��ˣ�</td>
				    <td class="tdulright" colspan="3"><bean:write name="trouble" property="troubleSendMan"/></td>
				   
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">�����ɷ�ʱ�䣺</td>
				    <td class="tdulright" colspan="3">
				    	<fmt:formatDate value="${trouble.troubleSendTime}" var="date" pattern="yyyy/MM/dd HH:mm:ss"/>
				    	<bean:write  name="date" />
				    </td>
				  </tr>
				   <tr class=trcolor>
				  	  <td class="tdulleft">������</td>
				  	  <td class="tdulright" colspan="3"><apptag:upload cssClass="" entityId="${trouble.id}"
											entityType="LP_TROUBLE_INFO" state="look"></apptag:upload>
					  </td>
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">�����˽�ɫ��</td>
				    <td colspan="3" class="tdulright">
					     <c:if test="${troubleReplyBean.confirmResult=='1'}">
					      	<input type="radio" name="confirmResult" value="1" onclick="judgeType(this.value)"  checked="checked">����&nbsp;&nbsp;
					    	<input type="radio" name="confirmResult" value="0"  onclick="judgeType(this.value)" >Э��
					     </c:if>
					      <c:if test="${troubleReplyBean.confirmResult=='0'}">
					      	<input type="radio" name="confirmResult" value="1" onclick="judgeType(this.value)"  >����&nbsp;&nbsp;
					    	<input type="radio" name="confirmResult" value="0"  onclick="judgeType(this.value)" checked="checked">Э��
					     </c:if>
				    </td>
				  </tr>
				  <tbody id="y" style="display:none">
				  <tr class=trcolor>
				    <td class="tdulleft">��Ϲ��¶Σ�</td>
				   <td class="tdulright"  colspan="3">
				    	<apptag:trunk id="trunk"  value="${troubleReplyBean.trunkids}" state="edit"></apptag:trunk>&nbsp;&nbsp;<font color="red">*</font>
				    </td>
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">�����豸�����أ�</td>
				    <td class="tdulright" colspan="3">
				    	<apptag:quickLoadList style="width:130px" keyValue="${troubleReplyBean.terminalAddress}" cssClass="radio" id="terminalAddress" name="terminalAddress" listName="terminal_address" type="radio"></apptag:quickLoadList>
				    </td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft">Ӱ��ҵ��ʱ�Σ�</td>
				    <td class="tdulright" colspan="3" >
				    	<input name="impressStartTime" id="impressStartTime" class="Wdate"  style="width:75" 
				    	   value="<bean:write  name="troubleReplyBean" property="impressStartTime" format="HH:mm"/>"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm'})" readonly/>
				    	-
				    <input name="impressEndTime" id="impressEndTime" class="Wdate"  style="width:75"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm'})" readonly value="<bean:write name="troubleReplyBean" property="impressEndTime"/>"/>&nbsp;&nbsp;<font color="red">*</font>
					
					</td>
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">Ӱ��ҵ�����ͣ�</td>
				    <td class="tdulright" colspan="3">
				    	<apptag:quickLoadList  style="width:130px" cssClass="select" keyValue="${impressTypes}" id="impressTypeArray" name="impressTypeArray" listName="impress_type" type="checkbox"></apptag:quickLoadList>&nbsp;&nbsp;<font color="red">*</font>
				    </td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft"> ����λ�����꣺</td>
				    <td class="tdulright" colspan="3"><input id="troubleGpsX" name="troubleGpsX" value="${troublexy}" style="width:220px" type="text"/><input type="button" value="ѡ��" onclick="getXY();"/></td>
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">��ϵص������� </td>
				    <td class="tdulright" colspan="3"><html:textarea property="brokenAddress" style="width:355px;" styleClass="required"></html:textarea>&nbsp;&nbsp;<font color="red">*</font></td>
				  </tr>
				   <tr class=trcolor>
				    <td class="tdulleft">�������¶Σ�</td>
				    <td class="tdulright" colspan="3"><html:textarea property="otherCable" style="width:355px;"></html:textarea></td>
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">�������ͣ�</td>
				    <td class="tdulright" colspan="3">
				    	<apptag:quickLoadList style="width:130px" keyValue="${troubleReplyBean.troubleType}" cssClass="select" id="troubleType" name="troubleType" listName="lp_trouble_type" type="select"></apptag:quickLoadList>
				    </td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft">���ԭ��������</td>
				    <td class="tdulright" colspan="3"><html:textarea property="brokenReason" rows="3" style="width:355px;" styleClass="required"></html:textarea>&nbsp;&nbsp;<font color="red">*</font></td>
				  </tr>
				    <logic:notEmpty name="process">
					  <tr class=trwhite>
						  <td class="tdulleft">�������</td>
						  <td class="tdulright" colspan="3"> 
				    		<table width="100%" id="tab" align="center" cellpadding="0" cellspacing="0" border="1">
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
				    <td class="tdulright" ><input name="troubleAcceptTime" id="troubleAcceptTime" class="Wdate"  style="width:140px"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate: '%y-%M-%d-%H-%m-%s'})" 
					value="<bean:write name="troubleReplyBean" property="troubleAcceptTime" format="yyyy/MM/dd HH:mm:ss"/>"
					readonly/>&nbsp;&nbsp;<font color="red">*</font></td>
				  </tr>
				  </tr>
				   <tr class=trcolor>
				    <td class="tdulleft">�������ʱ�䣺</td>
				    <td class="tdulright"><input name="arriveTime" id="arriveTime" class="Wdate"  style="width:140px"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate: '%y-%M-%d-%H-%m-%s'})" readonly
					value="<bean:write name="troubleReplyBean" property="arriveTime" format="yyyy/MM/dd HH:mm:ss"/>"/>&nbsp;&nbsp;<font color="red">*</font></td>
				    <td class="tdulleft">��������ֳ�ʱ�䣺</td>
				    <td class="tdulright" ><input name="arriveTroubleTime" id="arriveTroubleTime" class="Wdate"  style="width:140px"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate: '%y-%M-%d-%H-%m-%s' })" readonly
					value="<bean:write name="troubleReplyBean" property="arriveTroubleTime" format="yyyy/MM/dd HH:mm:ss"/>"/>&nbsp;&nbsp;<font color="red">*</font></td>
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">�жϹ��ϵ�ʱ�䣺</td>
				    <td class="tdulright"><input name="judgeTime" id="judgeTime" class="Wdate"  style="width:140px"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate: '%y-%M-%d-%H-%m-%s' })" readonly
					value="<bean:write name="troubleReplyBean" property="judgeTime" format="yyyy/MM/dd HH:mm:ss"/>"/>&nbsp;&nbsp;<font color="red">*</font></td>
				    <td class="tdulleft">�ҵ����ϵ�ʱ�䣺</td>
				    <td class="tdulright" colspan="3"><input name="findTroubleTime" id="findTroubleTime" class="Wdate"  style="width:140px"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate: '%y-%M-%d-%H-%m-%s' })" readonly
					value="<bean:write name="troubleReplyBean" property="findTroubleTime" format="yyyy/MM/dd HH:mm:ss"/>"/>&nbsp;&nbsp;<font color="red">*</font></td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft">���ϻָ�ʱ�䣺</td>
				    <td class="tdulright" ><input name="renewTime" id="renewTime" class="Wdate" style="width:140px"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate: '%y-%M-%d-%H-%m-%s' })" readonly
					value="<bean:write name="troubleReplyBean" property="renewTime" format="yyyy/MM/dd HH:mm:ss"/>"/>&nbsp;&nbsp;<font color="red">*</font></td>
					<td class="tdulleft">���½������ʱ�䣺</td>
				    <td class="tdulright" ><input name="completeTime" id="completeTime" class="Wdate" style="width:140px"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate: '%y-%M-%d-%H-%m-%s' })" readonly
					value="<bean:write name="troubleReplyBean" property="completeTime" format="yyyy/MM/dd HH:mm:ss"/>"/>&nbsp;&nbsp;<font color="red">*</font></td>
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">�����ֳ�ʱ�䣺</td>
				    <td class="tdulright" class="value" colspan="3"><input name="returnTime" id="returnTime" class="Wdate"  style="width:140px"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate: '%y-%M-%d-%H-%m-%s' })" readonly
					value="<bean:write name="troubleReplyBean" property="returnTime" format="yyyy/MM/dd HH:mm:ss"/>"/>&nbsp;&nbsp;<font color="red">*</font></td>
				  </tr>
				  
				  <tr class=trcolor>
				    <td class="tdulright" colspan="4">
				     <c:if test="${not empty hidden}">
					    	<bean:define id="hidd" name="hidden" property="id"></bean:define>
					    	<apptag:hidedangerselect inputName="hiddentrouble"  accidents="${hidd}" ></apptag:hidedangerselect>
					     </c:if>
					     <c:if test="${empty hidden}">
				    	<apptag:hidedangerselect inputName="hiddentrouble"  ></apptag:hidedangerselect>
				    	</c:if>
				    </td>
				  </tr>
				  <tr><Td colspan="4"> <apptag:materialselect label="ʹ�ò���" objectId="${troubleReplyBean.id}" useType="trouble" materialUseType="Use" ></apptag:materialselect></Td></tr>
				  <tr class=trcolor><Td colspan="4"> <apptag:materialselect label="���ղ���" objectId="${troubleReplyBean.id}" useType="trouble" materialUseType="Recycle"></apptag:materialselect></Td></tr>
				 
					   <tr class="trwhite">
					    <td class="tdulleft" colspan="4">���ϴ�����Ա
					    	&nbsp;&nbsp;<font color="red">
					    		 *��д˵��:������绰֮��ʹ��","�ָ�,���������Ա֮��ʹ��";"�ָʹ��Ӣ��״̬�µĶ�����ֺ�,�磺����,13900000000;����,13511111111��
					    		 </font>
					    </td>
					  </tr>
					  <tr class=trcolor>
					    <td class="tdulleft" height="22">�����ˣ�</td>
					    <td class="tdulright" colspan="3" ><textarea name="responsible" rows="3" style="width:375px;" class="required">${responsibles}</textarea>&nbsp;&nbsp;<font color="red">*</font></td>
					  </tr>
					   <tr class=trwhite>
					    <td class="tdulleft">���ϲ�����Ա��</td>
					    <td class="tdulright" colspan="3" ><textarea name="testman" rows="3" style="width:375px;" class="required">${testmans}</textarea>&nbsp;&nbsp;<font color="red">*</font></td>
					  </tr>
					  <tr class=trcolor>
					    <td class="tdulleft">�ֳ�����������Ա��</td>
					    <td class="tdulright" colspan="3" ><textarea name="mendman"  rows="3" style="width:375px;" class="required">${mendmans}</textarea>&nbsp;&nbsp;<font color="red">*</font></td>
					  </tr>
					  <tr style="hight:20px;"><td colspan="4">&nbsp;</td></tr>
				  <tr class="trwhite">
				    <td class="tdulleft">�����ʩ���� ��</td>
				    <td class="tdulright" colspan="3"><html:textarea property="processRemark" rows="3" style="width:375px;" styleClass="required"></html:textarea>&nbsp;&nbsp;<font color="red">*</font></td>
				  </tr>
				   <tr class=trcolor>
					  <td class="tdulleft" >��������ݼ�¼��</td>
					  <td class="tdulright" colspan="3"><html:textarea property="processRecord" styleClass="required" rows="3" style="width:375px;"></html:textarea>&nbsp;&nbsp;<font color="red">*</font></td>
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft" height="20">�������⴦��</td>
				    <td class="tdulright" colspan="3"><html:textarea property="otherIssue" rows="3" style="width:375px;" styleClass="required"></html:textarea>&nbsp;&nbsp;<font color="red">*</font></td>
				  </tr>
						  <tr class=trcolor>
						    <td class="tdulleft" height="22">�Ƿ񱨰���</td>
						    <td class="tdulright" colspan="3" >
						    <c:if test="${troubleReplyBean.isReportCase=='1'}">
						    	<input name="isReportCase" value="0" type="radio"  />��
						    	<input name="isReportCase" value="1" type="radio" checked />��
						    </c:if>
						    <c:if test="${troubleReplyBean.isReportCase=='0'}">
						    	<input name="isReportCase" value="0" type="radio" checked />��
						    	<input name="isReportCase" value="1" type="radio" />��
						    </c:if>
						    </td>
						  </tr>
						  <tr class=trcolor>
						    <td class="tdulleft" height="22">����˵����</td>
						    <td class="tdulright" colspan="3" ><html:textarea property="reportCaseComment" styleClass="required" rows="3" style="width:375px;"></html:textarea></td>
						  </tr>
						  <tr class=trcolor>
						    <td class="tdulleft" height="22">�Ƿ��գ�</td>
						    <td class="tdulright" colspan="3" >
						    <c:if test="${troubleReplyBean.isReportDanger=='1'}">
						    	<input name="isReportDanger" value="0" type="radio"  />��
						    	<input name="isReportDanger" value="1" type="radio" checked />��
						    </c:if>
						    <c:if test="${troubleReplyBean.isReportDanger=='0'}">
						    	<input name="isReportDanger" value="0" type="radio" checked />��
						    	<input name="isReportDanger" value="1" type="radio" />��
						    </c:if>
						    </td>
						  </tr>
				 </tbody>
			      <tr>
				    <td class="tdulleft" height="17">��ע��</td>
				    <td class="tdulright" colspan="3"><html:textarea property="replyRemark" rows="4" style="width:375px;"></html:textarea></td>
				  </tr>
				   <tbody id="attachspan" style="display:none">
				     <tr class=trcolor>
					    <td class="tdulleft" height="17">������</td>
					    <td class="tdulright" colspan="3">
					    	 <apptag:upload state="edit" cssClass="" entityId="${troubleReplyBean.id}" entityType="LP_TROUBLE_REPLY"/>
					    </td>
					  </tr>
					  <tr class=trwhite>
						  	<td class="tdulleft">�������ϣ�</td>
						  	<td class="tdulright" colspan="3">
						  		<span id="updatetrunk">
						  		
						  		</span>
						  	</td>
					  </tr>
				   </tbody>
				   <logic:notEmpty name="approves"> 
					 <tr align="center">
					 	<td colspan="4" align="left">�����Ϣ</td>
					 </tr>
					  <tr class=trwhite>
					  	<td colspan="4">
						  	<table width="100%"><tr>
						 	<td width="7%">�����</td><td width="20%">���ʱ�� </td>
						 	<td width="8%">��˽�� </td><td width="35%">������ </td>
						 	<td width="30%">����</td>
						   </tr>
						 <c:forEach items="${approves}" var="approve" varStatus="loop">
						 	<c:if test="${loop.count%2==0 }"> <tr class=trcolor></c:if>
						    <c:if test="${loop.count%2==1 }"> <tr class=trwhite></c:if>
						 		<td><bean:write name="approve" property="username"/></td>
						 		<td><bean:write name="approve" property="approve_time"/></td>
						 		<td><bean:write name="approve" property="approve_result"/>&nbsp;&nbsp;</td>
						 		<td style="word-break:break-all;width:35%;"><bean:write name="approve" property="approve_remark"/></td>
						 		<bean:define id="appid" name="approve" property="id"></bean:define>
						 		<td>&nbsp;&nbsp;<apptag:upload state="look" cssClass="" entityId="${appid}" entityType="LP_APPROVE_INFO" /></td>
						 	</tr>
						 </c:forEach>
						 </table>
					 </td>
					 </tr>
					 </logic:notEmpty>
				  <tr>
				  	<td colspan="4">
				  		<font color="red">��ѡ��Ŀ������������ʱ������д���ޡ�</font>
				  	</td>
				  </tr>
				 <tr  align="center">
				    <td colspan="4">
				     	 <input type="hidden" id="tempstate" name="tempstate"/>
				    	 <html:button property="action" styleClass="button" styleId="savebtn" onclick="save();">�ݴ�</html:button>
				    	 <html:button property="action" styleClass="button" styleId="subbtn" onclick="checkAddInfo()">�ύ</html:button>
				    	 <input type="reset" class="button" value="����"/>
				    	 <input type="button" class="button"  value="����" onclick="javascript:history.go(-1)"/>
				    </td>
				  </tr>
				  </table>
		</html:form>
		<script type="text/javascript">
		   judgeCR();
		</script>
	<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			//var valid = new Validation('saveTroubleReply', {immediate : true, onFormValidate : formCallback});
			
				jQuery(function(){
	var jsUserName = "";
	// IE�����
	if(jQuery.browser.msie){
		jQuery("#trunk").get(0).onpropertychange = handle;
	}else {// ���������
		var intervalName; // ��ʱ�����
		jQuery("#trunk").get(0).addEventListener("input",handle,false);
		jQuery("#trunk").blur(function(){
			clearInterval(intervalName);
		});
	}
	// ����jsUserName input��ֵ
	function setJsUserName(){
		jQuery("#updatetrunk").val(jQuery(this).val());
	}
	// jsUserName input��ֵ�ı�ʱִ�еĺ���
	function handle(){
	        var trunks="";
			jQuery("#updatetrunk").empty();
			if(jQuery("#trunk").val() != jsUserName){
				if(jQuery("#trunk").val()!=null && jQuery("#trunk").val() !=""){
		            var arr=jQuery("#trunk").val().split(",");
		            var arrname = jQuery("#trunknames").val().split(",");
					for(var i = 0;i<arr.length;i++){
						trunks+=arrname[i]+"&nbsp;&nbsp;<a href='javascript:showWin("+arr[i]+")'>"+"   ���ϸ���"+"</a></br>";
					}
				}
			   jQuery("#updatetrunk").append(trunks);  
			   jsUserName = jQuery("#updatetrunk").val();
			}
			
	}
}); 
	</script>
	</body>
</html>
