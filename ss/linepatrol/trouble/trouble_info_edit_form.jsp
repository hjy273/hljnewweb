<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linepatrol.trouble.services.TroubleConstant" %>
<html>
	<head>
		<title>故障派单</title>
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
					alert("故障名称不能为空!");
					$('troubleName').focus();
					return false;
				}
				var troubleStartTime = document.getElementById('troubleStartTime').value;
				if(troubleStartTime==""){
					alert("故障发生时间不能为空!");
					$('troubleStartTime').focus();
					return false;
				}
				var troubleSendMan =  document.getElementById('troubleSendMan').value;
				if(troubleSendMan==""){
					alert("故障派发人不能为空!");
					$('troubleSendMan').focus();
					return false;
				}
			var replyDeadline = document.getElementById('replyDeadline').value;
			if(replyDeadline==""){
				alert("故障反馈时限不能为空!");
				return false;
			}
				var eomsCodes =  document.getElementById('eomsCodes').value;
				if(eomsCodes==""){
					alert("eoms单号不能为空!");
					$('eomsCodes').focus();
					return false;
				}
				if(eomsCodes.indexOf("，")!=-1){
					//alert(eomsCodes);
					eomsCodes=eomsCodes.replace(/，/gi,",");
					//alert(eomsCodes);
					//return false;
				}
				var troubleRemark = document.getElementById('troubleRemark').value;
		  		if(valCharLength(troubleRemark) > 1024) {
		  			 alert("故障描述不能超过512个汉字或者1024个英文字符！")
		             return false;
		  		}
		  		
			var confirmResult = document.getElementById('confirmResult').value;
			if(confirmResult=="1"){
	  			var troubleAcceptTime = document.getElementById('troubleAcceptTime').value;
				if(troubleAcceptTime==""){
					alert("故障受理时间不能为空!");
					$('troubleAcceptTime').focus();
					return;
				}
			    var approvers = $('saveTroubleReply').approver.value;;
				if(approvers==""){
					alert("请选择审核人!");
		  			return ;
	  			}
				
				    $('subbtn').disabled=true; 
					$('saveTroubleReply').submit();
				
			}else{
				var approvers = $('saveTroubleReply').approver.value;
				if(approvers==""){
					alert("请选择审核人!");
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
	
	//查看隐患信息
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
		
		<template:titile value="平台核准 " />	
		<html:form action="/troubleReplyAction.do?method=dispatchApprove"
			styleId="saveTroubleReply" >
			<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				 <tr class=trwhite>
				    <td class="tdulleft">指派代维：</td>
				    <td class="tdulright" >
						<c:forEach items="${unitlist}" var="unit">
							<bean:write name="unit" property="contractorname"/><br/>
						</c:forEach>
					</td>
					 <td class="tdulleft">故障单号：</td>
				    <td class="tdulright">
				    	<c:out value="${trouble.troubleId}"></c:out>
				    </td>
				 </tr>
				  <tr class=trcolor >
				  	<td class="tdulleft">故障名称：</td>
				  	<td class="tdulright"><html:text property="troubleName" styleId="troubleName" value="${trouble.troubleName}" style="width:205"></html:text>&nbsp;&nbsp;<font color="red">*</font></td>
				    <td class="tdulleft" >故障发生时间：</td>
				    <td class="tdulright"><input name="troubleStartTime" id="troubleStartTime" class="Wdate" style="width:205"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate: '%y-%M-%d-%H-%m-%s' })" 
					 readonly value="<bean:write name="trouble" property="troubleStartTime" format="yyyy/MM/dd HH:mm:ss"/>" />
					 &nbsp;&nbsp;<font color="red">*</font></td>
					
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">是否为重大故障：</td>
				    <td class="tdulright">
					    <c:if test="${trouble.isGreatTrouble==0}">
					      <input type="radio" name="isGreatTrouble" value="1" onclick="changeReplyDeadline(this.value);"  >是&nbsp;&nbsp;
					       <input type="radio" name="isGreatTrouble" value="0" onclick="changeReplyDeadline(this.value);"  checked="checked">否
					    </c:if>
					    <c:if test="${trouble.isGreatTrouble==1}">
					      <input type="radio" name="isGreatTrouble" value="1"  onclick="changeReplyDeadline(this.value);" checked="checked">是&nbsp;&nbsp;
					       <input type="radio" name="isGreatTrouble" value="0" onclick="changeReplyDeadline(this.value);"  >否
					    </c:if>
				    </td>
				    <td class="tdulleft">故障反馈时限：</td>
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
				    <td class="tdulleft">故障派发人：</td>
				    <td class="tdulright"><html:text property="troubleSendMan" styleId="troubleSendMan" value="${trouble.troubleSendMan}" style="width:205" ></html:text>&nbsp;&nbsp;<font color="red">*</font></td>
				  </tr>
				   <tr class=trcolor >
				    <td class="tdulleft">平台：</td>
				    <td class="tdulright">
				    		<input type="text" name="connector" id="connector" value="${trouble.connector}"/>
				    	<!-- input type="radio" name="connector" value="1" onclick="judgeType(this.value)"  >城区平台&nbsp;&nbsp;
				        <input type="radio" name="connector" value="0" onclick="judgeType(this.value)" checked="checked">郊区平台
				    	 -->
				    </td>
				    <td class="tdulleft">平台电话：</td>
				    <td class="tdulright" >
				    	<input type="text" id="connectorTel" name="connectorTel" value="${trouble.connectorTel}" style="width:215px"/>
				    </td>
				  </tr>
				   <tr class=trwhite>
				    <td class="tdulleft">eoms单号：</td>
				    <td class="tdulright" colspan="3">
				    	<textarea name="eomsCodes" id="eomsCodes" rows="5" style="width:375px">${eoms}</textarea>
				    	<br/><font color="red">
				    	 * 填写说明：eoms单号之间使用英文状态下的”,“(逗号)分割</font>
				    </td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft">故障描述：</td>
				    <td class="tdulright" colspan="3"><html:textarea property="troubleRemark" styleId="troubleRemark" value="${trouble.troubleRemark}" rows="4" style="width:375px"></html:textarea></td>
				  </tr>
				   <tr class=trwhite>
				   		<td class="tdulleft">添加附件：</td>
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
				    <td class="tdulleft">故障反馈人：</td>
				    <td class="tdulright" >${replyman.userName}</td>
				    <td class="tdulleft">故障反馈单位：</td>
				    <td class="tdulright" >${contractor.contractorName}</td>
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">处理人角色：</td>
				    <td  class="tdulright">
					    <c:if test="${troubleReplyBean.confirmResult=='1'}">
					    主办
					    </c:if>
					    <c:if test="${troubleReplyBean.confirmResult=='0'}">
					    协办
					    </c:if>
					     <input type="hidden" name="confirmResult" id="confirmResult" value="${troubleReplyBean.confirmResult}"   />
				    </td>
				    <td class="tdulleft">故障派发时间：</td>
				    <td class="tdulright">
				    	<bean:write  name="trouble"  property="troubleSendTime" format="yyyy/MM/dd HH:mm:ss"/>
				    </td>
				  </tr>
				  <c:if test="${troubleReplyBean.confirmResult=='1'}">
				  <tbody id="y">
				   <tr class=trcolor>
				    <td class="tdulleft">阻断光缆段：</td>
				   <td class="tdulright"  colspan="3">
				    	<apptag:trunk id="trunk"  value="${troubleReplyBean.trunkids}" state="view"></apptag:trunk>
				    </td>
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">影响业务类型：</td>
				    <td class="tdulright" >
				    	<apptag:quickLoadList  style="width:130px" cssClass="select" keyValue="${troubleReplyBean.impressType}" id="impressTypeArray" name="impressTypeArray" listName="impress_type" type="look"></apptag:quickLoadList>
				    </td>
				    <td class="tdulleft">影响业务时段：</td>
				    <td class="tdulright" >
				    	<bean:write  name="troubleReplyBean" property="impressStartTime" format="HH:mm"/>
				    	-
				        <bean:write name="troubleReplyBean" property="impressEndTime"/>
					</td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft">故障设备所属地：</td>
				    <td class="tdulright">
				    	<apptag:quickLoadList  style="width:130px" keyValue="${troubleReplyBean.terminalAddress}" cssClass="radio" id="terminalAddress" name="terminalAddress" listName="terminal_address" type="look"></apptag:quickLoadList>
				    </td>
				    <td class="tdulleft"> 故障位置：</td>
				    <td class="tdulright" >
				   		 <a href="javascript:getXY();">查看</a>
				    </td>
				  </tr>
				  <tr class=trcolor>
					 		<td class="tdulleft">现场图片：</td>
							<td class="tdulright" colspan="3">
							 	<apptag:image entityId="${troubleReplyBean.id}" entityType="LP_TROUBLE_REPLY"></apptag:image>
							 </td>
					</tr>
				  <tr class=trwhite>
				    <td class="tdulleft">阻断地点描述： </td>
				    <td class="tdulright" colspan="3">${troubleReplyBean.brokenAddress}</td>
				  </tr>
				   <tr class=trcolor>
				    <td class="tdulleft">其它光缆段：</td>
				    <td class="tdulright" colspan="3">${troubleReplyBean.otherCable}</td>
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">故障类型：</td>
				    <td class="tdulright" colspan="3">
				    	<apptag:quickLoadList style="width:130px" keyValue="${troubleReplyBean.troubleType}" cssClass="select" id="troubleType" name="troubleType" listName="lp_trouble_type" type="look"></apptag:quickLoadList>
				    </td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft">阻断原因描述：</td>
				    <td class="tdulright" colspan="3">${troubleReplyBean.brokenReason}</td>
				  </tr>
				    <logic:notEmpty name="process">
					  <tr class=trwhite>
						  <td class="tdulleft">处理过程</td>
						  <td class="tdulright" colspan="3"> 
				    		<table width="100%" id="tab" align="center" cellpadding="0" cellspacing="0" border="0">
				    			<tr>
				    				<td>查看轨迹</td><td>出发时间</td><td>处理人员</td><td>设备编号</td><td>到达机房时间</td>
				    				<td>到达故障现场时间</td><td>找到故障点时间</td><td>撤离现场时间</td>
				    			</tr>
				    			    <bean:size id="prosize" name="process"/>
				    				<c:forEach items="${process}" var="proce" varStatus="loop">
				    					<tr id="del${loop.index}">
				    					     <c:if test="${loop.index==0}">
					    						<td rowspan="${prosize}"><a href="javascript:viewProcess();">查看</a></td>
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
				    <td class="tdulleft">故障发生时间：</td>
				    <td class="tdulright">
				    <bean:write  name="trouble" property="troubleStartTime" format="yyyy/MM/dd HH:mm:ss" />
				    </td>
				    <td class="tdulleft">故障受理时间：</td>
				    <td class="tdulright" ><input name="troubleAcceptTime" id="troubleAcceptTime" class="Wdate"  style="width:160"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate: '%y-%M-%d-%H-%m-%s'})" 
					value="<bean:write name="troubleReplyBean" property="troubleAcceptTime" format="yyyy/MM/dd HH:mm:ss"/>"
					readonly/>&nbsp;&nbsp;<font color="red">*</font></td>
				  </tr>
				  </tr>
				   <tr class=trcolor>
				    <td class="tdulleft">到达机房时间：</td>
				    <td class="tdulright"><bean:write name="troubleReplyBean" property="arriveTime" format="yyyy/MM/dd HH:mm:ss"/></td>
				    <td class="tdulleft">到达故障现场时间：</td>
				    <td class="tdulright" ><bean:write name="troubleReplyBean" property="arriveTroubleTime" format="yyyy/MM/dd HH:mm:ss"/></td>
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">判断故障点时间：</td>
				    <td class="tdulright"><bean:write name="troubleReplyBean" property="judgeTime" format="yyyy/MM/dd HH:mm:ss"/></td>
				    <td class="tdulleft">找到故障点时间：</td>
				    <td class="tdulright" colspan="3"><bean:write name="troubleReplyBean" property="findTroubleTime" format="yyyy/MM/dd HH:mm:ss"/></td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft">故障恢复时间：</td>
				    <td class="tdulright" ><bean:write name="troubleReplyBean" property="renewTime" format="yyyy/MM/dd HH:mm:ss"/></td>
					<td class="tdulleft">光缆接续完成时间：</td>
				    <td class="tdulright" ><bean:write name="troubleReplyBean" property="completeTime" format="yyyy/MM/dd HH:mm:ss"/></td>
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">撤离现场时间：</td>
				    <td class="tdulright" class="value"><bean:write name="troubleReplyBean" property="returnTime" format="yyyy/MM/dd HH:mm:ss"/></td>
				 	<td class="tdulleft">引发故障的隐患：</td>
				 	 <td class="tdulright">
							<c:if test="${not empty hidden}">
							   <a href="javascript:viewHideDangerForm('<bean:write name="hidden"  property="id"/>');"><bean:write name="hidden" property="name"/></a>
						   </c:if>
				    </td>
				  </tr>
				        <tr class=trcolor>
					 		<td colspan="4">					 			
					 						<apptag:materialselect label="使用材料" displayType="view" objectId="${troubleReplyBean.id}" useType="trouble" materialUseType="Use" ></apptag:materialselect>
					 		</td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td colspan="4">
					 						<apptag:materialselect label="回收材料" displayType="view" objectId="${troubleReplyBean.id}" useType="trouble" materialUseType="Recycle" ></apptag:materialselect>
					 		</td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td colspan="4">处理人员</td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft" >负责人：</td><td colspan="3" class="tdulright"><bean:write name="responsibles"/></td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td  class="tdulleft" >故障测试人员：</td><td colspan="3" class="tdulright"> <bean:write name="testmans"/></td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft" >现场接续抢修人员：</td><td colspan="3" class="tdulright"><bean:write name="mendmans"/></td>
					 	</tr>
				  <tr>
				    <td class="tdulleft">处理措施描述 ：</td>
				    <td class="tdulright" colspan="3">${troubleReplyBean.processRemark}</td>
				  </tr>
				   <tr class=trwhite>
					  <td class="tdulleft" >处理后数据记录：</td>
					  <td class="tdulright" colspan="3">${troubleReplyBean.processRecord}</td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft" height="20">遗留问题处理：</td>
				    <td class="tdulright" colspan="3">${troubleReplyBean.otherIssue}</td>
				  </tr>
						  <tr class=trcolor>
						    <td class="tdulleft" height="22">是否报案：</td>
						    <td class="tdulright" colspan="3" >
						    <c:if test="${troubleReplyBean.isReportCase=='1'}">
						    	是
						    </c:if>
						    <c:if test="${troubleReplyBean.isReportCase=='0'}">
						    	否
						    </c:if>
						    </td>
						  </tr>
						  <tr class=trcolor>
						    <td class="tdulleft" height="22">报案说明：</td>
						    <td class="tdulright" colspan="3" ><c:out value="${troubleReplyBean.reportCaseComment}"></c:out>
						  </tr>
						  <tr class=trcolor>
						    <td class="tdulleft" height="22">是否报险：</td>
						    <td class="tdulright" colspan="3" >
						    <c:if test="${troubleReplyBean.isReportDanger=='1'}">
						    	是
						    </c:if>
						    <c:if test="${troubleReplyBean.isReportDanger=='0'}">
						    	否
						    </c:if>
						    </td>
						  </tr>
				
				 </tbody>
				  </c:if>
				  	<tr class="trwhite">
				    <td class="tdulleft" height="17">备注：</td>
				    <td class="tdulright" colspan="3">${troubleReplyBean.replyRemark}</td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft" height="17">反馈附件：</td>
				    <td class="tdulright" colspan="3">
				    	 <apptag:upload state="look" cssClass="" entityId="${troubleReplyBean.id}" entityType="LP_TROUBLE_REPLY"/>
				    </td>
				  </tr>
				   <tr class=trwhite>
				    	<apptag:approverselect inputName="approver,mobiles" label="审核人"
				    	 approverType="approver" objectType="LP_TROUBLE_REPLY" objectId="${troubleReplyBean.id}" 
				    	  colSpan="3" spanId="approver" inputType="radio" notAllowName="reads"></apptag:approverselect>
				  </tr>
				  <tr class=trcolor>
				    	<apptag:approverselect inputName="reads,rmobiles" label="抄送人"  
				    	approverType="reader" objectType="LP_TROUBLE_REPLY" objectId="${troubleReplyBean.id}" 
				    	colSpan="3" spanId="reader" notAllowName="approver"></apptag:approverselect>
				  </tr>
				  <tr>
				  	<td colspan="4">
				  		<font color="red">必选项目若无内容描述时，请填写“无”</font>
				  	</td>
				  </tr>
				 <tr  align="center">
				    <td colspan="4">
				    	 <html:button property="action" styleClass="button" styleId="subbtn" onclick="checkAddInfo()">提交</html:button>
				    	 <input type="reset" class="button" value="重置"/>
				    	 <input type="button" class="button"  value="返回" onclick="javascript:history.go(-1)"/>
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
