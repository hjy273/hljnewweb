<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/xtheme-gray.css'/>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<title>故障反馈单信息</title>
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
	  
	         //历史流程
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
			  title:"流程历史" 
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
		<template:titile value="故障反馈单信息" />
		<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
		 <logic:notEmpty  name="bean">
		 	<input type="hidden" id="userid" value="<bean:write name="LOGIN_USER" property="userID"/>"/>
			<input type="hidden" id="regionid" value="<bean:write name="bean" property="region_id"/>"/>
			<input type="hidden" id="troubleid" value="<bean:write name="bean" property="tid"/>"/>
			<input type="hidden" id="replyid" name="replyid" value="<bean:write name="reply" property="id"/>"/>
			<input type="hidden" id="prounitid" name="prounitid" value="${prounit.id}"/>
				  <tr class=trcolor>
				    <td class="tdulleft">是否为重大故障：</td>
				    <td class="tdulright">
				    	<bean:write name="bean" property="is_great_trouble"/>
				    </td>
				    <td class="tdulleft">故障反馈时限：</td>
				    <td class="tdulright">
				    	<bean:write name="bean" property="reply_deadline"/>
				    </td>
				   </tr>
				   <tr class=trcolor>
				    <td class="tdulleft">反馈是否超时：</td>
				    <td class="tdulright" colspan="3">
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
				<tr class=trwhite>
					<td class="tdulleft">
						故障派单人：
					</td>
					<td class="tdulright">
						<bean:write name="bean" property="trouble_send_man" />
					</td>
					<td class="tdulleft">
						故障派发时间：
					</td>
					<td colspan="3" class="tdulright">
						<bean:write name="bean" property="trouble_send_time" />
					</td>
				</tr>
					
		</logic:notEmpty>
			<tr class=trcolor>
				<td class="tdulleft">
					处理人角色：
				</td>
				<td colspan="5" class="tdulright">
					<c:if test="${reply.confirmResult=='1'}">
						    		主办
						    	</c:if>
					<c:if test="${reply.confirmResult=='0'}">
						    		协办
						    	</c:if>
				</td>
			</tr>
		 <tr class=trwhite>
				    <td class="tdulleft">故障反馈人：</td>
				    <td class="tdulright" >${replyman}</td>
				    <td class="tdulleft">故障反馈单位：</td>
				    <td class="tdulright" colspan="3">${contraName}</td>
		   </tr>
			<c:if test="${reply.confirmResult=='0'}">
				<tr class=trcolor>
					<td class="tdulleft">
						备注：
					</td>
					<td class="tdulright" colspan="5" style="word-break:break-all;width:500px;" >
						<c:out value="${reply.replyRemark}"></c:out>
					</td>
				</tr>
			</c:if>
			<c:if test="${reply.confirmResult=='1'}">
			<tr class=trcolor>
				<td class="tdulleft">
					阻断光缆段：
				</td>
				<td class="tdulright" colspan="5">
					<apptag:trunk id="trunk" state="view"  value="${reply.trunkids}"/>
				</td>
			</tr>
			<tr class=trwhite>
				<td class="tdulleft">
					故障设备所属地：
				</td>
				<td class="tdulright" >
						<apptag:quickLoadList style="width:130px" keyValue="${reply.terminalAddress}" cssClass="select" name="terminalAddress" listName="terminal_address" type=""></apptag:quickLoadList>
				</td>
				  <td class="tdulleft">故障位置:</td>
				    <td class="tdulright" colspan="3">
				    	<a href="javascript:getXY();">查看</a>
				    </td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">
					阻断地点描述：
				</td>
				<td class="tdulright" colspan="5">
					<c:out value="${reply.brokenAddress}"></c:out>
				</td>
			</tr>
			<tr class=trwhite>
				<td class="tdulleft">
					影响业务时段：
				</td>
				<td class="tdulright" colspan="5">
					<c:out value="${reply.impressStartTime}"></c:out>
					-
					<c:out value="${reply.impressEndTime}"></c:out>
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft"> 
					影响业务类型：
				</td>
				<td class="tdulright" colspan="5">
					<apptag:quickLoadList  style="width:130px" cssClass="select" keyValue="${reply.impressType}" id="impressTypeArray" name="impressTypeArray" listName="impress_type" type="look"></apptag:quickLoadList>
				</td>
			</tr>
			 <tr class=trwhite>
				 <td class="tdulleft">其它光缆段：</td>
				 <td class="tdulright" colspan="5"><c:out value="${reply.otherCable}"></c:out></td>
			</tr>
				<tr class=trcolor>
					 		<td class="tdulleft">现场图片：</td>
							 <td class="tdulright" colspan="5">
							 	<apptag:image entityId="${reply.id}" entityType="LP_TROUBLE_REPLY"></apptag:image>
							 </td>
					 	</tr>
			<tr class=trwhite>
				<td class="tdulleft">
					故障类型：
				</td>
				<td class="tdulright" colspan="5">
					<apptag:quickLoadList style="width:130px" keyValue="${reply.troubleType}" cssClass="select" name="troubleType" listName="lp_trouble_type" type=""></apptag:quickLoadList>
				</td>
			</tr>
			
			<tr class=trcolor>
				<td class="tdulleft">
					阻断原因描述：
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
									处理过程
								</td>
							</tr>

							<tr>
								<td>
									查看轨迹
								</td>
								<td>
									出发时间
								</td>
								<td>
									处理人员
								</td>
								<td>
									设备编号
								</td>
								<td>
									到达机房时间
								</td>
								<td>
									到达故障现场时间
								</td>
								<td>
									找到故障点时间
								</td>
								<td>
									撤离现场时间
								</td>
							</tr>
						</logic:notEmpty>
						<bean:size id="prosize" name="process"/>
						<logic:iterate id="proce" name="process" indexId="len">
							<tr>
									<c:if test="${len==0}">
				    				   <td rowspan="${prosize}"><a href="javascript:viewProcess();">查看</a></td>
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
				<td class="tdulleft">故障发生时间：</td>
				<td class="tdulright">
					<bean:write  name="bean" property="trouble_start_time" format="yyyy/MM/dd HH:mm:ss" />
				</td>
				<td class="tdulleft">故障受理时间：</td>
			    <td class="tdulright" colspan="3"><bean:write name="reply" property="troubleAcceptTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">到达机房时间：</td>
				<td class="tdulright"><bean:write name="reply" property="arriveTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td class="tdulleft">到达故障现场时间：</td>
				<td class="tdulright" colspan="3"><bean:write name="reply" property="arriveTroubleTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr class=trwhite>
				<td class="tdulleft">判断故障点时间：</td>
				<td class="tdulright" ><bean:write name="reply" property="judgeTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td class="tdulleft">找到故障点时间：</td>
			    <td class="tdulright" colspan="3"><bean:write name="reply" property="findTroubleTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr class=trcolor>
			    <td class="tdulleft">故障恢复时间：</td>
				<td class="tdulright"><bean:write name="reply" property="renewTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td class="tdulleft">光缆接续完成时间：</td>
				<td class="tdulright" colspan="3"><bean:write name="reply" property="completeTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr class=trwhite>
				<td class="tdulleft">
					撤离现场时间：
				</td>
				<td class="tdulright">
					<bean:write name="reply" property="returnTime" format="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td class="tdulleft">
					引发故障的隐患：
				</td>
				<td class="tdulright" colspan="4">
					<c:if test="${not empty hidden}">
					   <a href="javascript:viewHideDangerForm('<bean:write name="hidden"  property="id"/>')"><bean:write name="hidden" property="name"/></a>
				   </c:if>
				</td>
			</tr>
			<tr class=trcolor>
				<td colspan="6">				
					<apptag:materialselect label="使用材料" displayType="view"
									objectId="${reply.id}" useType="trouble" materialUseType="Use"></apptag:materialselect>
				</td>
			</tr>
			<tr class=trcolor>
				<td colspan="6">					
								<apptag:materialselect label="回收材料" displayType="view"
									objectId="${reply.id}" useType="trouble"
									materialUseType="Recycle"></apptag:materialselect>
				</td>
			</tr>
						<tr class=trcolor>
							<td colspan="6">
								&nbsp;&nbsp;处理人员
							</td>
						</tr>
						<tr class=trwhite>
							<td class="tdulleft">
								负责人：
							</td>
							<td  colspan="5"><bean:write name="responsibles" /></td>
						</tr>
						<tr class=trcolor>
							<td class="tdulleft"> 故障测试人员：</td>
							<td colspan="5"> <bean:write name="testmans"/></td>
						</tr>
						<tr class=trwhite>
							<td class="tdulleft" >
								现场接续抢修人员：
							</td>
							<td colspan="5"><bean:write name="mendmans" /></td>
						</tr>
			<tr class=trcolor>
				<td class="tdulleft">
					处理措施描述：
				</td>
				<td class="tdulright" colspan="5" style="word-break:break-all;width:500px;" >
					<c:out value="${reply.processRemark}"></c:out>
				</td>
			</tr>
			<tr class=trwhite>
				<td class="tdulleft">
					处理后数据记录：
				</td>
				<td class="tdulright" colspan="5" style="word-break:break-all;width:500px;" >
					<c:out value="${reply.processRecord}"></c:out>
				</td>
			</tr>
			
			<tr class=trcolor>
				<td class="tdulleft">
					遗留问题处理：
				</td>
				<td class="tdulright" colspan="5" style="word-break:break-all;width:500px;" >
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
				<td class="tdulleft">
					备注：
				</td>
				<td class="tdulright" colspan="5" style="word-break:break-all;width:500px;" >
					<c:out value="${reply.replyRemark}"></c:out>
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">
					附件：
				</td>
				<td class="tdulright" colspan="5">
					<apptag:upload cssClass="" entityId="${reply.id}"
						entityType="LP_TROUBLE_REPLY" state="look"/>
				</td>
			</tr>
			<tr  class=trwhite>
				<td class="tdulleft">故障原因：</td>
			    <td class="tdulright" colspan="5"><c:out value=" ${troubleReasonName}"></c:out></td>
			</tr>
			<tr class=trcolor><td  class="tdulleft">审核不通过次数：</td><td class="tdulright" colspan="5"><c:out value="${reply.notPassedNum}"></c:out></td></tr>
      </c:if>
      			<logic:notEmpty name="approves"> 
					 		<tr class=trwhite align="center">
							 	<td colspan="6" align="left">审核详细信息</td>
						    </tr>
							<tr  class=trcolor>
								<td colspan="6">
							    <table width="100%">
							    <tr>
							 	<td width="10%">审核人</td><td width="17%">审核时间 </td>
							 	<td width="10%">审核结果 </td><td width="40%" >审核意见 </td><td width="18%">附件</td>
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
				    	  <html:submit value="已阅读" styleClass="button"></html:submit>
				    </c:if>
				    <html:button property="action" styleClass="button"
						onclick="javascript:viewHistoryDetail();">查看流程历史</html:button>
					<html:button property="action" styleClass="button"
						onclick="javascript:history.go(-1)">返回</html:button>
				</td>
			</tr>
		</table>
		 </html:form>
	</body>
</html>
