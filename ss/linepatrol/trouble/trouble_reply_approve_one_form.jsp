<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>故障审核</title>
		  <script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
			<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
		
		<script type="text/javascript">
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
				mytop=120;
				mywidth=650;
				myheight=490;
		        window.open(URL,"getXY","height="+myheight+",width="+mywidth+",status=1,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft+",resizable=no");
		}
		
		function validadd(){
			var cr = document.getElementById('cr').value;
			var trunkids = document.getElementById('trunkids').value;
			var approveResult = document.getElementsByName("approveResult");
			if(approveResult[0].checked && approveResult[0].value=="1"){
				if(cr=='1'){
					var troubleReasonId = document.getElementById("troubleReasonId").value;
					if(troubleReasonId==""){
						alert("请选择故障原因!");
						return false;
					}
					if(trunkids!=""){
						if(confirm("光缆更新信息是否全部通过审核?")){
							return true;
						}else{
						   return false;
						}
					}
				}
			}
			    return true;
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
	   var win;
		function showWin(id){
		    var url="${ctx}/trunkAction.do?method=updateTrunkApprove&id="+id+"&type=cable";
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
		<template:titile value="故障审核入库" />
		<html:form action="/replyApproveAction.do?method=approveReply"
			styleId="approveReply"  onsubmit="return validadd();">
			<input type="hidden" value="${reply.trunkids}" id="trunkids" name="trunkids"/>
			<input type="hidden" id="userid" value="<bean:write name="LOGIN_USER" property="userID"/>"/>
			<input type="hidden" id="regionid" value="<bean:write name="bean" property="region_id"/>"/>
			<input type="hidden" name="troubleid" value="<bean:write name="bean" property="tid"/>"/>
			<input type="hidden" id="replyid" name="objectId" value="<bean:write name="reply" property="id"/>"/>
			<table width="90%"  border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				 <tr class=trcolor>
				    <td class="tdulleft">
				    	故障单编号：</td><td class="tdulright"><bean:write name="bean" property="trouble_id"/>
				    </td>
				    <td class="tdulleft">反馈人：</td><td class="tdulright"><bean:write name="bean" property="username"/></td>
				    </tr>
				   <tr class=trcolor>
				    <td class="tdulleft">反馈时间：</td><td class="tdulright" colspan="3"> <bean:write name="bean" property="reply_submit_time"/> </td>
				  </tr>
				  <tr>
				  	<td class="tdulleft" >故障派单单位：</td>
				    <td class="tdulright"><bean:write name="bean" property="deptname"/></td>
				    <td class="tdulleft">是否为重大故障：</td>
				    <td class="tdulright">
				    	<bean:write name="bean" property="is_great_trouble"/>
				    </td>
				  </tr>
				  <tr>
				    <td class="tdulleft">故障反馈时限：</td>
				    <td class="tdulright">
				    	<bean:write name="bean" property="reply_deadline"/>
				    </td>
				    <td class="tdulleft">反馈是否超时：</td>
				    <td class="tdulright">
				    	<c:if test="${time_length_sign=='+'}"><font color="#FF0000">超时</c:if>
				    	<c:if test="${time_length_sign=='-'}"><font color="#009999">提前</c:if>
				    	<c:if test="${time_length_hour!=0}">
				    		<c:out value="${time_length_hour}" />
				    		小时
				    	</c:if>
				    	<c:out value="${time_length_minute}" />
				    	分钟
				    	</font>
				    </td>
				  </tr>
				  </tr>
				  	<tr class=trwhite>
					 		<td class="tdulleft">故障派单人：</td><td class="tdulright"><bean:write name="bean" property="trouble_send_man"/></td>
					 		<td class="tdulleft">故障派发时间：</td><td class="tdulright" ><bean:write name="bean" property="trouble_send_time"/></td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">处理人角色：</td>
					 		<td class="tdulright" colspan="3">
					 			<c:if test="${reply.confirmResult=='1'}">
						    		主办
						    	</c:if>
						    	<c:if test="${reply.confirmResult=='0'}">
						    		协办
						    	</c:if>
					 		</td>
					 	</tr>
					 	<c:if test="${reply.confirmResult=='0'}">
					 	<tr class=trwhite>
					 		<td class="tdulleft">备注：</td>
					 		<td class="tdulright" colspan="3" style="word-break:break-all;width:500px;" >
					 			<c:out value="${reply.replyRemark}"></c:out>
					 		</td>
					 	</tr>
					 	</c:if>
					 	<c:if test="${reply.confirmResult=='1'}">
					 	 <tr class=trwhite>
						  	<td class="tdulright" colspan="4">
						  		<div id="showdetail" ><a href="javascript:viewReplyDetail('1');">查看详细信息</a></div>
						  		<div id="hiddendetail" style="display:none"><a href="javascript:viewReplyDetail('2');">隐藏详细信息</a></div>
						  	</td>
						  </tr>
				  	 <tbody id="replydetail" style="display:none">
				   <tr class=trcolor>
						<td class="tdulleft">
							故障设备所属地：
						</td>
						<td class="tdulright">
							<apptag:quickLoadList style="width:130px" keyValue="${reply.terminalAddress}" cssClass="select" name="terminalAddress" listName="terminal_address" type=""></apptag:quickLoadList>
						</td>
					    <td class="tdulleft">故障位置：</td>
					    <td class="tdulright">
					    	<a href="javascript:getXY();">查看</a>
					    </td>
					  </tr>
					 <tr class=trwhite>
						<td class="tdulleft">
							阻断光缆段：
						</td>
							<td colspan="5" class="tdulright">
					 			<apptag:trunk id="trunk" state="view"  value="${reply.trunkids}"/>
						</td>
					</tr>
					<tr class=trcolor> 
						<td class="tdulleft">
							影响业务时段：
						</td>
						<td class="tdulright" colspan="5">
							<c:out value="${reply.impressStartTime}"></c:out>
							-
							<c:out value="${reply.impressEndTime}"></c:out>
						</td>
					</tr>
					<tr class=trwhite>
						<td class="tdulleft">
							影响业务类型：
						</td>
						<td class="tdulright" colspan="5">
							<apptag:quickLoadList  style="width:130px" cssClass="select" keyValue="${reply.impressType}" id="impressTypeArray" name="impressTypeArray" listName="impress_type" type="look"></apptag:quickLoadList>
						</td>
					</tr>
					  <tr class=trcolor>
						    <td class="tdulleft">阻断地点描述：</td>
						 	<td class="tdulright" colspan="3"><c:out value="${reply.brokenAddress}"></c:out></td>
				     </tr>
					 <tr class=trwhite>
						 <td class="tdulleft">其它光缆段：</td>
						 <td class="tdulright" colspan="5"><c:out value="${reply.otherCable}"></c:out></td>
					</tr>
					<tr class=trcolor>
					 		<td class="tdulleft">现场图片:</td>
							 <td class="tdulright" colspan="3">
							 	<apptag:image entityId="${reply.id}" entityType="LP_TROUBLE_REPLY"></apptag:image>
							 </td>
					</tr>
					<tr class=trwhite>
				        <td class="tdulleft">
							故障类型：
						</td>
						<td class="tdulright" colspan="3">
						 <apptag:quickLoadList cssClass="select" keyValue="${reply.troubleType}"  id="troubleReasonId" name="troubleReasonId" listName="lp_trouble_type" type="look"></apptag:quickLoadList>
					     </td>
				     </tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">阻断原因描述：</td>
					 		<td class="tdulright" colspan="3" style="word-break:break-all;width:500px;" ><c:out value="${reply.brokenReason}"></c:out></td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td colspan="4">
					 			<table border="1" align="center" cellpadding="0" cellspacing="0" width="100%">
					 				<logic:notEmpty name="process">
					 				<tr>
					 					<td colspan="8">处理过程</td>
					 				</tr>
					 				
					 				<tr>
					 					<td>查看轨迹</td><td>出发时间</td><td>处理人员</td><td>设备编号</td><td>到达机房时间</td>
					 					<td>到达故障现场时间</td><td>找到故障点时间</td><td>撤离现场时间</td>
					 				</tr>
					 				</logic:notEmpty>
					 				<bean:size id="prosize" name="process"/>
					 				<logic:iterate id="proce" name="process" indexId="len">
				    				<tr>
				    				   <c:if test="${len==0}">
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
				    			</logic:iterate>
					 			</table>
					 		</td>
					 	</tr>
					 	 <tr class=trwhite>
						    <td class="tdulleft">故障发生时间：</td>
						    <td class="tdulright">
						   <bean:write name="bean" property="trouble_start_time"/>
						    </td>
						    <td class="tdulleft">故障受理时间：</td>
						    <td class="tdulright" ><bean:write name="reply" property="troubleAcceptTime" format="yyyy-MM-dd HH:mm:ss"/></td>
						  </tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">到达机房时间：</td>
					 		<td class="tdulright"><bean:write name="reply" property="arriveTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					 		<td class="tdulleft">到达故障现场时间：</td>
					 		<td class="tdulright" ><bean:write name="reply" property="arriveTroubleTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					 	</tr>
					 	 <tr class=trwhite>
					 		<td class="tdulleft">判断故障点时间：</td>
					 		<td class="tdulright"><bean:write name="reply" property="judgeTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					 		<td class="tdulleft">找到故障点时间：</td>
					 		<td class="tdulright" ><bean:write name="reply" property="findTroubleTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">故障恢复时间：</td>
					 		<td class="tdulright"><bean:write name="reply" property="renewTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					 		<td class="tdulleft">光缆接续完成时间：</td>
					 		<td class="tdulright" ><bean:write name="reply" property="completeTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">撤离现场时间：</td>
					 		<td class="tdulright"><bean:write name="reply" property="returnTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					 		<td class="tdulleft">引发故障的隐患：</td>
					 		<td class="tdulright" >
							<c:if test="${not empty hidden}">
								   <a href="javascript:viewHideDangerForm('<bean:write name="hidden"  property="id"/>');"><bean:write name="hidden" property="name"/></a>
							   </c:if>
					 		</td>
					 	</tr>
					 
					 	<tr class=trcolor>
					 		<td colspan="4">
					 			<apptag:materialselect label="使用材料" displayType="view" objectId="${reply.id}" useType="trouble" materialUseType="Use" ></apptag:materialselect>
					 		</td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td colspan="4">
					 			<apptag:materialselect label="回收材料" displayType="view" objectId="${reply.id}" useType="trouble" materialUseType="Recycle" ></apptag:materialselect>
					 		</td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulright" colspan="4">&nbsp;&nbsp;&nbsp;处理人员</td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">负责人：</td><td colspan="3" class="tdulright" ><bean:write name="responsibles"/></td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">故障测试人员：</td><td colspan="3" class="tdulright" ><bean:write name="testmans"/></td>
					 	</tr>
					 	<tr class=trcolor> 
					 		<td class="tdulleft">现场接续抢修人员：</Td><td colspan="3" class="tdulright" ><bean:write name="mendmans"/></td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">处理措施描述：</td><td style="word-break:break-all;width:500px;" class="tdulright" colspan="3"><c:out value="${reply.processRemark}"></c:out></td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">处理后数据记录：</td><td style="word-break:break-all;width:500px;" class="tdulright" colspan="3"><c:out value="${reply.processRecord}"></c:out></td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">遗留问题处理：</td>
					 		<td style="word-break:break-all;width:500px;" class="tdulright" colspan="3">
					 			<c:out value="${reply.otherIssue}"></c:out>
					 		</td>
					 	</tr>
						  <tr class=trcolor>
						    <td class="tdulleft" height="22">是否报案：</td>
						    <td class="tdulright" colspan="3" >
						    <c:if test="${reply.isReportCase=='1'}">
						    	是
						    </c:if>
						    <c:if test="${reply.isReportCase=='0'}">
						    	否
						    </c:if>
						    </td>
						  </tr>
						  <tr class=trcolor>
						    <td class="tdulleft" height="22">报案说明：</td>
						    <td class="tdulright" colspan="3" ><c:out value="${reply.reportCaseComment}"></c:out>
						  </tr>
						  <tr class=trcolor>
						    <td class="tdulleft" height="22">是否报险：</td>
						    <td class="tdulright" colspan="3" >
						    <c:if test="${reply.isReportDanger=='1'}">
						    	是
						    </c:if>
						    <c:if test="${reply.isReportDanger=='0'}">
						    	否
						    </c:if>
						    </td>
						  </tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">附件：</td>
					 		<td class="tdulright" colspan="5">
					 			<apptag:upload cssClass="" entityId="${reply.id}" entityType="LP_TROUBLE_REPLY" state="look"></apptag:upload>
					 		</td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">备注：</td>
					 		<td class="tdulright" colspan="3" style="word-break:break-all;width:500px;" >
					 			<c:out value="${reply.replyRemark}"></c:out>
					 		</td>
					 	</tr>
					 </tbody>
				</c:if>
					 <logic:notEmpty name="approves"> 
					 <tr align="center">
					 	<td colspan="4" align="left">审核信息</td>
					 </tr>
					  <tr class=trwhite>
					  	<td colspan="4">
					  	<table width="100%"><tr>
					 	<td width="7%">审核人</td><td width="15%">审核时间 </td><td width="8%">审核结果 </td>
					 	<td width="40%">审核意见 </td><td width="30%">附件</td>
					 </tr>
					 <c:forEach items="${approves}" var="approve" varStatus="loop">
					 	<c:if test="${loop.count%2==0}"> <tr class=trwhite></c:if>
					    <c:if test="${loop.count%2==1}"> <tr class=trcolor></c:if>
					    <tr>
					 		<td><bean:write name="approve" property="username"/></td>
					 		<td><bean:write name="approve" property="approve_time"/></td>
					 		<td><bean:write name="approve" property="approve_result"/></td>
					 		<td style="word-break:break-all;width:40%;"><bean:write name="approve" property="approve_remark"/> &nbsp;&nbsp;</td>
					 		<bean:define id="appid" name="approve" property="id"></bean:define>
					 		<td><apptag:upload state="look" cssClass="" entityId="${appid}" entityType="LP_APPROVE_INFO" /></td>
					 	</tr>
					 </c:forEach>
					 </table>
					 </td>
					 </tr>
					 </logic:notEmpty>
				 <tr>
					<td height="25" style="text-align: right;">
						审核结果：
					</td>
					<td colspan="3" style="text-align: left;">
						<input type="radio" name="approveResult" value="1" checked />
						通过
						<input type="radio" name="approveResult" value="0" />
						不通过
					</td>
				</tr>
				<tr>
					<td height="25" style="text-align: right;">
						审核意见：
					</td>
					<td colspan="3" style="text-align: left;">
						<textarea name="approveRemark" rows="6" class="max-length-256" style="width: 400px;"></textarea>
					</td>
				</tr>
				<input type="hidden" id="cr" name="cr" value="${reply.confirmResult}"/>
				 	<c:if test="${reply.confirmResult=='1'}">
					  <tr class=trcolor>
					    <td class="tdulleft">故障原因：</td>
					    <td class="tdulright" colspan="3">
					    	<logic:notEmpty name="bean" property="trouble_reason_id">
					    	  <bean:define id="troubleReason" name="bean" property="trouble_reason_id"></bean:define>
					    	</logic:notEmpty>
					   		<apptag:quickLoadList cssClass="select" keyValue="${reply.troubleType}"  id="troubleReasonId" name="troubleReasonId" listName="lp_trouble_type" isQuery="true" type="subset"></apptag:quickLoadList><font color="red">*</font>
					   </td>
					  </tr>
				  </c:if>
				  <tr class=trwhite style="width:35px;">
				    <td class="tdulleft">相关附件：</td>
				    <td class="tdulright" colspan="3">
				    	<apptag:upload state="add" cssClass="" />
				    </td>
				  </tr>
				  <tr class="trcolor">
				  	<td class="tdulleft">
				  		光缆资料：
				  	</td>
				  	<td class="tdulright" colspan="3">
					  	<c:forEach items="${cableList}" var="cable">
					  	<bean:write name="cable" property="segmentname"/> 
					  		<a href="javascript:showWin('<bean:write name="cable" property="kid"/>')">
					  			&nbsp;&nbsp;&nbsp; 资料审核
					  		</a><br/>
					  	</c:forEach>
				  	</td>
				  </tr>
				  <tr align="center" class=trcolor>
				    <td colspan="4">
				     <html:submit property="action" styleClass="button">提交</html:submit>
							<html:reset property="action" styleClass="button">重置</html:reset>
							<input type="button" value="返回" class="button" onclick="javascript:history.back();" />
				    </td>
				  </tr>
			</table>
		</html:form>
		<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('approveReply', {immediate : true, onFormValidate : formCallback});
	</script>
	</body>
</html>
