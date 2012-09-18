<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>生成故障指标</title>
		<script language="javascript" type="text/javascript">
		function checkAddInfo() {
				var month = document.getElementById("month").value;
				if(month==""){
					alert("统计月份不能为空!");
					return false;
				}
				return true;
		}
		reGenerate=function(inputMonth,isNotFinished){
			document.getElementById("month").value=inputMonth;
			document.getElementById("guideType").value="${guide.guideType}";
			document.getElementById("isReCreate").value="1";
			if(!isNotFinished){
				if(!confirm("是否需要重新生成该月的故障指标？")){
					return false;
				}
			}
			statTroubleInfo.submit();
		};
		getTroubleQuota=function(inputMonth){
			if("${guide.guideType}"!=""){
				document.getElementById("guideType").value="${guide.guideType}";
			}else{
				document.getElementById("guideType").value=statTroubleInfo.guideType.value;
			}
			document.getElementById("month").value=inputMonth;
			statTroubleInfo.submit();
		};
		exportList=function(){
			var guideType = document.getElementById("guideTypehidd").value;
			var month = document.getElementById("monthhidd").value;
			var url="${ctx}/troubleQuotaAction.do?method=exportTroubleQuotaList&guideType="+guideType+"&month="+month;
			self.location.replace(url);
		};
		changeType=function(gt){
			var url="${ctx}/troubleQuotaAction.do?method=createTroubleQuotaForm&guideType="+gt.value;
			self.location.replace(url);
		};
		//modify by xueyh 2011/04/13 for add reviseMaintenanceLength
		function revise(reviseTroubleTimes,reviseInterdictionTime,reviseMaintenanceLength,id){
			document.getElementById("isReCreate").value="0";
			var url="${ctx}/troubleQuotaAction.do?method=updateRevise&reviseTroubleTimes="+reviseTroubleTimes
			+"&reviseInterdictionTime=" +reviseInterdictionTime
			+"&id="+id
			+"&reviseMaintenanceLength="+reviseMaintenanceLength ;
			//alert("&reviseMaintenanceLength="+reviseMaintenanceLength);
			jQuery.ajax({
				type: "post",
				url: url,
			  	async: false,
			  	dataType:"text",
			  	success:function(msg){
				  var data = msg.split(";");
				  var id = data[0];
				  var values = data[1].split(",");
				  var standard_times = values[0];
				  var standard_time = values[2];
				  jQuery('#standard_times_'+id).html(standard_times);
				  jQuery('#standard_time_'+id).html(standard_time);
				 
			  }
			});
			
		}
		
		
		function callback(originalRequest) {
				var rst = originalRequest.responseText;
				var interdictionName='InterdictionTime'+rst;
				var troubleName='TroubleTimes'+rst;
				if($(troubleName).value==0){
					document.getElementById(rst).value=0;
				}
				else{
					document.getElementById(rst).value=$(interdictionName).value/$(troubleName).value;
				}
		}
		</script>
	</head>

	<body>
		<template:titile value="生成月故障指标" />
		<html:form action="/troubleQuotaAction.do?method=createQuota"
			styleId="statTroubleInfo" onsubmit="return checkAddInfo()">
			<table width="50%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			      <tr  class=trwhite>
				    <td class="tdulleft" width="20%">统计指标：</td>
				    <td class="tdulright">
				     <select name="guideType" id="guideType" onChange="changeType(this)" style="width:155px">
				     	<c:if test="${guideType=='1'}">
							<option value="1" selected>一干故障指标</option>
							<option value="0">城域网故障指标</option>
						</c:if>
						<c:if test="${guideType=='0'}">
							<option value="1">一干故障指标</option>
							<option value="0" selected>城域网故障指标</option>
						</c:if>
						<c:if test="${guideType!='0' && guideType!='1'}">
							<option value="1">一干故障指标</option>
							<option value="0" selected>城域网故障指标</option>
						</c:if>
				     </select>
				    </td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft" width="20%">统计月份：</td>
				    <td class="tdulright"><input name="month" id="month" class="Wdate" value="${month}" style="width:150px"
					onfocus="WdatePicker({dateFmt:'yyyy/MM',maxDate: '%y-%M'})" readonly/>
					</td>
				  </tr>
			     <tr class=trwhite>
					<td colspan="6" style="text-align:center;"><!-- modify by xueyh 20110412 for isCreate value=1,可以解决重复生成的bug -->
						<input name="isReCreate" id="isReCreate" value="0"	 type="hidden" />
						<input name="action" class="button" value="生成报表"	 type="submit" />
					</td>
				 </tr>
			</table>
		</html:form>
		<c:if test="${not empty exist_month_list}">
			<table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				<tr class=trcolor>
					<td>
						<c:forEach var="oneMonth" items="${exist_month_list}">
							<logic:equal value="0" name="oneMonth" property="finish_state">
								<bean:define id="bgColor" value="#FF0000"></bean:define>
							</logic:equal>
							<logic:equal value="1" name="oneMonth" property="finish_state">
								<bean:define id="bgColor" value="#00FF00"></bean:define>
							</logic:equal>
							<font style="background-color:${bgColor }">
							<a href="javascript:getTroubleQuota('<bean:write name="oneMonth" property="stat_month" />')">
							<bean:write name="oneMonth" property="stat_month" />
							</a>
							</font>
							&nbsp;&nbsp;
						</c:forEach>
					</td>
				</tr>
			</table> 
		</c:if>
		<c:if test="${not empty monthquota}">
			<table width="100%" border="1" align="center"  bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" bordercolor="#000" style="border-collapse: collapse"> 
				<input type="hidden" name="guideTypehidd" id="guideTypehidd" value="${guide.guideType}"/>
				<input type="hidden" name="monthhidd" id="monthhidd" value="${month}"/>
				<tr>
					<td colspan="17" align="center">
						<font size="2px" style="font-weight: bold">
							${month}
							<c:if test="${guide.guideType=='1'}">
								一干故障指标
							</c:if>
							<c:if test="${guide.guideType=='0'}">
								城域网故障指标
							</c:if>
						</font>
					</td>
			   </tr>
				<tr  bgcolor="#cccccc" height="30px">
				    <td rowspan="2"><font size="2px" style="font-weight: bold">序号</font></td>
				    <td rowspan="2" align="center"><font size="2px" style="font-weight: bold">代维</font></td>
				    <td colspan="3"><font size="2px" style="font-weight: bold">千公里阻断次数(次)</font></td>
				    <td colspan="3" ><font size="2px" style="font-weight: bold">千公里阻断时长(小时)</font></td>
				    <td colspan="2"><font size="2px" style="font-weight: bold">光缆每次故障平均历时(小时)</font></td>
				    <!-- td rowspan="2"><font size="2px" style="font-weight: bold">维护长度(公里)</font></td -->
				    <td colspan="2"><font size="2px" style="font-weight: bold">维护长度(公里)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">${guide.interdictionTimeNormValue}小时/千公里</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">${guide.interdictionTimesNormValue}次/千公里</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">抢修及时率</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">反馈及时率</font></td>
				    <c:if test="${guide.guideType=='0'}">
				    <td rowspan="2"><font size="2px" style="font-weight: bold">单次抢修历时超出指标值的故障次数</font></td>
				    </c:if>
			   </tr>
			   <tr bgcolor="#cccccc" height="30px">
				    <td align="center" ><font size="2px" style="font-weight: bold">指标</font></td>
				    <td align="center"><font size="2px" style="font-weight: bold">故障次数</font></td>
				    <td align="center"><font size="2px" style="font-weight: bold">修正值</font></td>
				    <td align="center"><font size="2px" style="font-weight: bold">指标</font></td>
				    <td align="center"><font size="2px" style="font-weight: bold">故障总时长</font></td>
				    <td align="center"><font size="2px" style="font-weight: bold">修正值</font></td>
				    <td align="center"><font size="2px" style="font-weight: bold">指标</font></td>
				    <td align="center"><font size="2px" style="font-weight: bold">故障平均时长</font></td>				    
				    <!--  -->
				    <td align="center"><font size="2px" style="font-weight: bold">已有值</font></td>
				    <td align="center"><font size="2px" style="font-weight: bold">修正值</font></td>
				    
			  </tr>
			  	<bean:size id="quotaSize" name="monthquota" />
				<c:forEach items="${monthquota}" var="quota" varStatus="loop">
					<bean:define id="reviseTroubleTimes" name="quota" property="revise_trouble_times"></bean:define>
					<bean:define id="reviseInterdictionTime"  name="quota" property="revise_interdiction_time"></bean:define>
					<bean:define id="reviseMaintenanceLength" name="quota" property="revise_maintenance_length"></bean:define>
					<bean:define id="id" name="quota" property="id"></bean:define>
					<tr>
					    <td align="center"><c:if test="${loop.index+1!=quotaSize}">${loop.index+1}</c:if></td>
					    <td align="center"><bean:write name="quota" property="contractorname"/></td>
					    <td align="center" id="standard_times_${id}"><bean:write name="quota" property="standard_times"/></td>
					    <td align="center"><bean:write name="quota" property="trouble_times"/></td>
					    <td align="center"><input type="text" id="TroubleTimes${id}" value="${reviseTroubleTimes}" size="5%" onblur="javascript:revise(this.value,'','','${id}');"/></td>
					    <td align="center" id="standard_time_${id}"><bean:write name="quota" property="standard_time"/></td>
					    <td align="center"><bean:write name="quota" property="interdiction_time"/></td>
					    <td align="center"><input type="text" id="InterdictionTime${id}" value="${reviseInterdictionTime}" size="5%" onblur="javascript:revise('',this.value,'','${id}');"/></td>
					    <td align="center">${guide.rtrTimeNormValue}</td>
					    <td align="center" >
					    	<c:if test="${reviseTroubleTimes!=0}">
					    	<input type="text" id="${id}" value="${reviseInterdictionTime/reviseTroubleTimes}" size="6%" disabled="disabled"/>
					    		
					    	</c:if>
					    	<c:if test="${reviseTroubleTimes==0}">
					    		<input type="text" id="${id}" value="0" disabled="disabled" size="6%"/>
					    	</c:if>
					    </td>
					    <td align="center"><bean:write name="quota" property="maintenance_length"/></td>
					     <td align="center"><input type="text" id="MaintenanceLength${id}" value="${reviseMaintenanceLength}" size="5%" onblur="javascript:revise('','',this.value,'${id}');"/></td>
					    <td align="center"><bean:write name="quota" property="standard_time"/></td>
					    <td align="center"><bean:write name="quota" property="standard_times"/></td>
					    <td align="center"><bean:write name="quota" property="rtr_in_time"/></td>
					    <td align="center"><bean:write name="quota" property="feedback_in_time"/></td>
					    <c:if test="${guide.guideType=='0'}">
					    <td align="center"><bean:write name="quota" property="city_area_out_standard_number"/></td>
					    </c:if>
				  </tr>
			  </c:forEach>
			  <tr><td colspan="17">
			  	<input name="btnReGenerate" value="重新生成" class="button" type="button" onclick="reGenerate('${month}',${is_not_finished });" />
			  </td></tr>
		 </table>
		 <a href="javascript:exportList()">导出为Excel文件</a>
	 </c:if>
	</body>
	
</html>
