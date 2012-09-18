<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>故障考核</title>
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
				alert("请选择一个考核评分！");
				return;
			}
  			var remark = document.getElementById("entityRemark").value;
  			if(valCharLength(remark) > 1024) {
  				alert("备注信息不能超过512个汉字或者1024个英文字符！")
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
	
	//查看轨迹回放
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
		<template:titile value="故障考核" />
		<html:form action="/troubleExamAction.do?method=saveEvaluate"
			styleId="saveEvaluate">
			<input type="hidden" name="troubleid" id="troubleid" value="<bean:write name="bean" property="tid"/>"/>
			<input type="hidden" id="replyid" name="replyid" value="<bean:write name="reply" property="id"/>"/>
			<input type="hidden" id="userid" value="<bean:write name="LOGIN_USER" property="userID"/>"/>
			<input type="hidden" id="regionid" value="<bean:write name="bean" property="region_id"/>"/>
			<table width="80%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				 <tr class=trcolor>
				    <td colspan="2">
				    	<span>故障单编号：<bean:write name="bean" property="trouble_id"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>				   
				    </td>
				    <td>反馈人：<bean:write name="bean" property="username"/></td>
				    <td>反馈时间： <bean:write name="bean" property="reply_submit_time"/> </td>
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
				    <td class="tdulright" colspan="3">
				    	<bean:write name="bean" property="reply_deadline"/>
				    </td>
				  </tr>
				  <tr class=trwhite>
				  	
				  	<td class="tdulleft">故障发生时间：</td>
				  	<td class="tdulright"><bean:write name="bean" property="trouble_start_time"/></td>
				    <td class="tdulleft">故障派发时间：</td>
				    <td class="tdulright"><bean:write name="bean" property="trouble_send_time"/></td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft">故障位置：</td>
				    <td class="tdulright" colspan="3">
				    	<a href="javascript:getXY();">查看</a>
				    </td>
				  </tr>
				  <tr class=trwhite>
				  	<td colspan="6">
				  		<div id="showdetail" ><a href="javascript:viewReplyDetail('1');">查看详细信息</a></div>
				  		<div id="hiddendetail" style="display:none"><a href="javascript:viewReplyDetail('2');">隐藏详细信息</a></div>
				  	</td>
				  </tr>
				 <tbody id="replydetail" style="display:none">
					 	<tr class=trcolor>
					 		<td class="tdulleft">故障派单人：</td><td class="tdulright" colspan="3"><bean:write name="bean" property="trouble_send_man"/></td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">故障派发时间：</td>
					 		<td class="tdulright"><bean:write name="bean" property="trouble_send_time"/></td>
					 		<td class="tdulleft">处理人角色：</td>
					 		<td class="tdulright">
					 			<c:if test="${reply.confirmResult=='1'}">
						    		主办
						    	</c:if>
						    	<c:if test="${reply.confirmResult=='0'}">
						    		协办
						    	</c:if>
					 		</td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">阻断光缆段：</td>
					 		<td class="tdulright" colspan="3">
					 			<apptag:trunk id="trunk" state="view"  value="${reply.trunkids}"/>
					 		</td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">故障设备所属地：</td>
					 		<td class="tdulright" colspan="3">
					 			<apptag:quickLoadList style="width:130px" keyValue="${reply.terminalAddress}" cssClass="select" name="terminalAddress" listName="terminal_address" type=""></apptag:quickLoadList>
					 		</td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">影响业务时段：</td>
					 		<td class="tdulright" colspan="3"><c:out value="${reply.impressStartTime}"></c:out>-<c:out value="${reply.impressEndTime}"></c:out></td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">影响业务类型：</td>
					 		<td class="tdulright" colspan="3"><c:out value="${reply.impressType}"></c:out></td>
					 	</tr>
					    <tr class=trcolor>
					 		<td class="tdulleft">其它光缆段：</td>
					 		<td class="tdulright" colspan="3"><c:out value="${reply.otherCable}"></c:out></td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">现场图片：</td>
							 <td class="tdulright" colspan="3">
							 	<apptag:image entityId="${reply.id}" entityType="LP_TROUBLE_REPLY"></apptag:image>
							 </td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">故障类型：</td>
					 		<td class="tdulright" colspan="3"> <apptag:quickLoadList cssClass="select" keyValue="${reply.troubleType}"  id="troubleReasonId" name="troubleReasonId" listName="lp_trouble_type" type="look"></apptag:quickLoadList></td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">阻断原因描述：</td>
					 		<td class="tdulright" colspan="3" style="word-break:break-all;width:500px;" >
					 			<c:out value="${reply.brokenReason}"></c:out>
					 		</td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td colspan="4">
					 			<table border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
					 				<logic:notEmpty name="process">
					 				<tr>
					 					<td class="tdulleft" colspan="8">处理过程</td>
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
						    <td class="tdulleft">故障发生时间：</td>
						    <td class="tdulright">
						   <bean:write name="bean" property="trouble_start_time"/>
						    </td>
						    <td class="tdulleft">故障受理时间：</td>
						    <td class="tdulright" ><bean:write name="reply" property="troubleAcceptTime" format="yyyy-MM-dd HH:mm:ss"/></td>
						  </tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">到达机房时间：</td>
					 		<td class="tdulright"><bean:write name="reply" property="arriveTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					 		<td class="tdulleft">到达故障现场时间：</td>
					 		<td class="tdulright" ><bean:write name="reply" property="arriveTroubleTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					 	</tr>
					 	 	<tr class=trcolor>
					 		<td class="tdulleft">判断故障点时间：</td>
					 		<td class="tdulright"><bean:write name="reply" property="judgeTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					 		<td class="tdulleft">找到故障点时间：</td>
					 		<td class="tdulright" ><bean:write name="reply" property="findTroubleTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">故障恢复时间：</td>
					 		<td class="tdulright"><bean:write name="reply" property="renewTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					 		<td class="tdulleft">光缆接续完成时间：</td>
					 		<td class="tdulright" ><bean:write name="reply" property="completeTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">撤离现场时间：</td>
					 		<td class="tdulright"><bean:write name="reply" property="returnTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					 		<td class="tdulleft">
								引发故障的隐患：
							</td>
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
					 	<tr class=trwhite>
					 		<td class="tdulleft">处理措施描述：</td>
					 		<td class="tdulright" colspan="3" style="word-break:break-all;width:500px;" >
					 			<c:out value="${reply.processRemark}"></c:out>
					 		</td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">处理后数据记录：</td>
					 		<td class="tdulright" colspan="3" style="word-break:break-all;width:500px;" >
					 			<c:out value="${reply.processRecord}"></c:out>
					 		</td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">遗留问题处理：</td>
					 		<td class="tdulright" colspan="3" style="word-break:break-all;width:500px;" >
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
					 		<td class="tdulleft">备注：</td>
					 		<td class="tdulright" colspan="3" style="word-break:break-all;width:500px;" >
					 			<c:out value="${reply.replyRemark}"></c:out>
					 		</td>
					 	</tr>
					 	<tr class=trcolor>
					 		<td class="tdulleft">附件：</td><td class="tdulright" colspan="3"><apptag:upload cssClass="" entityId="${reply.id}" entityType="LP_TROUBLE_REPLY" state="look"></apptag:upload></td>
					 	</tr>
					 	<tr class=trwhite>
					 		<td class="tdulleft">故障原因：</td>
					 		<td class="tdulright" colspan="3"><c:out value=" ${troubleReasonName}"></c:out></td>
					 	</tr>
					 	<tr class=trcolor><td class="tdulleft">审核不通过次数：</td><td class="tdulright" colspan="3"><c:out value="${reply.notPassedNum}"></c:out></td></tr>
					 	<logic:notEmpty name="approves"> 
					 		<tr class=trwhite align="center">
							 	<td colspan="4" align="left">审核详细信息</td>
						    </tr>
							<tr  class=trcolor>
							<td colspan="4">
					     	<table width="100%"><tr>
							 	<td>审核人</td><td>审核时间 </td><td>审核结果 </td><td>审核意见 </td><td>附件</td>
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
				<!-- 代维单位日常考核 -->
				<apptag:appraiseDailyDaily businessId="${replyid }" contractorId="${contractorId }" businessModule="trouble" 
						displayType="input" tableStyle="width:80%; border-top: 0px;"></apptag:appraiseDailyDaily>
				<apptag:appraiseDailySpecial businessId="${replyid }" contractorId="${contractorId }" businessModule="trouble" 
						displayType="input" tableStyle="width:80%; border-top: 0px;"></apptag:appraiseDailySpecial>
				 <br/><table width="80%"   border="0" align="center" cellpadding="3" cellspacing="0">
				  <tr align="center">
				    <td colspan="4">
				    <html:button property="action" styleClass="button" styleId="subbtn"
							onclick="checkAddInfo()">提交</html:button>
							<html:reset property="action" styleClass="button">重置</html:reset>
							<input type="button" value="返回" class="button" onclick="javascript:history.back();"/>
				    </td>
				  </tr>
			</table>
		</html:form>
	</body>
</html>
