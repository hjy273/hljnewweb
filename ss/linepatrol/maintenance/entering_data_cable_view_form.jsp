<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/xtheme-gray.css'/>
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<script type='text/javascript'>
		var win;
		//数据分析
			function viewAnalyseData(id){
			 var cableLineName = document.getElementById('cableLineName').value;
			 var u="${ctx}/enteringCableDataAction.do?method=analyseDataForm&index="+id+"&cableLineName="+cableLineName+"&act=view";
			  win = new Ext.Window({
			  layout : 'fit',
			  width: document.body.clientWidth * 0.95 , 
			  height:390, 
			  resizable:true,
			  closeAction : 'close', 
			  modal:true,
			 // autoLoad:{url: '${ctx}/enteringCableDataAction.do?method=addChipDataForm',scripts:true}, 
			  html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src='+u+' />',
			  plain: true,
			  title:"数据分析" 
			 });
			  win.show(Ext.getBody());
			}
			  
			 function close(){
			  	win.close();
			 }
			 
			 exportList=function(id){
				var url="${ctx}/testApproveAction.do?method=exportTestCabelData&id="+id;
				self.location.replace(url);
			}
			
			exportAnalyseList=function(id){
			    var cableLineName = document.getElementById('cableLineName').value;
				var url="${ctx}/testApproveAction.do?method=exportAnaylseData&id="+id+"&cableLineName="+cableLineName;
				self.location.replace(url);
			}
	    </script>
	</head>
	<body>
		<template:titile value="中继段录入数据详细信息" />
		<table width="80%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			<input name="cableLineName" id="cableLineName" type="hidden" value="${line.cablelineName}"/>
		    <tr class=trcolor>
		      <td class="tdulleft">中继段：</td>
		      <td class="tdulright" colspan="3"><c:out value="${line.cablelineName}"></c:out></td>
		    </tr>
		    <tr class=trwhite>
		      <td class="tdulleft">测试端：</td>
		      <td class="tdulright" colspan="3">
		      	<c:if test="${data.factTestPort=='A'}">
					      A端
		      	</c:if>
		      	<c:if test="${data.factTestPort=='B'}">
					      B端
		      	</c:if>
		      </td>
		    </tr>
		    <tr class=trcolor>
		      <td class="tdulleft">测试时间：</td>
		      <td class="tdulright" colspan="3">
		       <bean:write name="data" property="factTestTime" format="yyyy/MM/dd"/>
		      </td>
		    </tr>
		    <tr>
		      <td class="tdulleft">测试地点：</td>
		      <td class="tdulright" colspan="3">
		        ${data.testAddress}
		      </td>
		    </tr>
		    <tr class=trwhite>
		      <td class="tdulleft">测试人员：</td>
		  	  <td class="tdulright" colspan="3">
		  	  	 <apptag:testman spanId="tester" hiddenId="testMan" state="view" value="${data.id}" tablename="cable"></apptag:testman>
			   </td>
		    </tr>
		    <tr class=trcolor>
		      <td height="25" colspan="4" bgcolor="#0099FF"><span class="STYLE1">测试参数设置</span></td>
		    </tr>
		    <tr class=trcolor>
		      <td class="tdulleft">测试仪表：</td>
		      <td class="tdulright" colspan="3">
		       ${data.testApparatus}
		      </td>
		    </tr>
		   <!--   <tr class=trcolor>
		      <td class="tdulleft">测试方法：</td>
		      <td class="tdulright" colspan="3">
		        	<c:if test="${data.testMethod=='2'}">
						      两点法
		        	</c:if>
		        	<c:if test="${data.testMethod=='0'}">
						   LSA
		        	</c:if>
		        	<c:if test="${data.testMethod=='4'}">
						     四点法
		        	</c:if>
		    </tr>-->
		    <tr class=trwhite>
		      <td class="tdulleft">测试波长(nm)：</td>
		      <td class="tdulright" colspan="3">
		        ${data.testWavelength}
		      </td>
		      
		    </tr>
		    <tr class=trcolor>
		    	<td class="tdulleft">所设折射率：</td>
		      <td colspan="3" class="tdulright">
		      		${data.testRefractiveIndex}
		      </td>
		    </tr>
		    <tr class=trwhite>
		      <td class="tdulleft"><div align="right">测试平均时间(s)：</div></td>
		      <td class="tdulright" colspan="3">
		   	        >=${data.testAvgTime}
		      </td>
		    </tr>
		    <tr class=trcolor>
		      <td height="25" colspan="4" bgcolor="#0099FF">测试数据 (<c:out value="${coreNumber}"></c:out>条纤芯)
		      </td>
		    </tr>
		    <tr class=trwhite>
			    <td height="25" colspan="4">
				    <div id="chipdata">
						 <table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
					          <td width="6%" height="20">纤序</td>
					          <td width="12%">衰减常数dB/km</td>
					          <td width="9%">是否合格</td>
					          <td width="10%">是否保存</td>
					           <td width="9%">是否在用</td>
					          <td width="20%">&nbsp;&nbsp;说明</td>
					          <td width="20%">附件</td>
					          <td width="13%">数据分析</td>
					        </tr>
					     <logic:iterate id="chip" name="planChips" >
					     	<bean:define id="obj" name="chip" property="value"></bean:define>
					     	<tr>
					     	    <td><bean:write name="obj" property="chipSeq"/></td>
							    <td><bean:write name="obj" property="attenuationConstant"/></td>
							    <td>
							    	<c:if test="${obj.isEligible=='1'}">合格</c:if>
							    	<c:if test="${obj.isEligible=='0'}">不合格</c:if>
							    </td>
							    <td>
							    	<c:if test="${obj.isSave=='1'}">保存</c:if>
							    	<c:if test="${obj.isSave=='0'}">未保存</c:if>
							    </td>
							    <td>
							    	<c:if test="${obj.isUsed=='1'}">在用</c:if>
							    	<c:if test="${obj.isUsed=='0'}">不在用</c:if>
							    </td>
							    <td><bean:write name="obj" property="testRemark"/></td>
							    <td>
							    	<apptag:upload state="look" cssClass="" entityId="${obj.id}" entityType="LP_TEST_CHIP_DATA" />
							    </td>
							    <td>
							       <c:if test="${not empty obj.coreData}">
								       <logic:notEmpty name="querycable">
								        <a href="javascript:exportAnalyseList('${obj.chipSeq}')">导出Excel</a>
								       </logic:notEmpty>
								       <a href="javascript:viewAnalyseData('<bean:write name="obj" property="chipSeq"/>')">数据分析</a>
								   </c:if>
								</td>
							</tr>
						</logic:iterate> 
						</table>
				    </div>
		      	</td>
			</tr>
		    <tr class=trcolor>
		      <td height="25" colspan="4" bgcolor="#0099FF">现场测试问题记录及跟踪
		      </td>
		    </tr>
		     <tr class=trwhite>
			    <td height="25" colspan="4">
				    <div id="problem">
				    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="10%" >序号</td>
							    <td width="35%">问题描述</td>
								<td width="35%">处理跟踪说明</td>
								<td width="10%">状态</td>
							</tr><%int i = 1; %>
					     <logic:iterate id="problem" name="cableProblems" > 
					     	<tr>
					     		<td><%=i%></td>
					     	    <td><bean:write name="problem" property="problemDescription"/></td>
							    <td><bean:write name="problem" property="processComment"/></td>
							    <td>
							    	<logic:equal value="0" property="problemState" name="problem">
							    	   未解决
							    	</logic:equal>
							    	<logic:equal value="1" property="problemState" name="problem">
							    	   已解决
							    	</logic:equal>
							    </td>
							</tr><%i++; %>
						</logic:iterate> 
						</table>
				    </div>
		      	</td>
			</tr>
			<logic:notEmpty name="querycable">
				<tr>
			    	<td colspan="4">
			    		<c:if test="${line.state eq '1'}">
						  <a href="javascript:exportList('${data.id}')">导出为Excel文件</a>
						</c:if>
			    	</td>
			    </tr>
			</logic:notEmpty>
		    <tr class=trcolor>
		      <td colspan="4" align="center">
		         <input type="button" class="button" onclick="javascript:history.back();" value="返回" />
		      </td>
		    </tr>
		  </table>
	</body>
</html>
