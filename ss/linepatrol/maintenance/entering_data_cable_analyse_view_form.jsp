<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<script type='text/javascript'>
	    </script>
	</head>
	<body>
		<html:form action="/enteringCableDataAction.do?method=addAnalyseData" styleId="analyseform">
		<table width="98%" border="1" align="center"  bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" bordercolor="#DBDBD8" style="border-collapse: collapse"> 
			<input type="hidden" name="chiseq" value="${chipdata.chipSeq}"/>
		    <tr>
		      <td align="right">中继段：</td>
		      <td  colspan="3"><c:out value="${trunkName}"></c:out></td>
		      <td align="right">纤序：</td>
		      <td ><c:out value="${chipdata.chipSeq}"></c:out></td>
		    </tr>
		    <tr>
		      <td align="right">测试端：</td>
		      <td  colspan="2">
		      	<c:if test="${chipdata.coreData.abEnd=='A'}">
			        A端			
		      	</c:if>
		      	<c:if test="${chipdata.coreData.abEnd=='B'}">
					B端
				</c:if>
		      </td>
		       <td align="right">测试基站：</td>
		       <td colspan="2" >
		     		${chipdata.coreData.baseStation}
		       </td>
		    </tr>
		      <tr>
			      <td align="right">记录日期：</td>
			      <td colspan="5">
			        <bean:write name="chipdata" property="coreData.testDate" format="yyyy/MM/dd"/>
			      </td>
		      </tr>
		    <tr >
		      <td height="25px" bgcolor="#E6EFF7" colspan="6" align="center"><font size="2px" style="font-weight: bold">分析文件记录</font></td>
		    </tr>
		    <tr >
		       <td colspan="3" align="center">保存文件名称</td>
		       <td  colspan="3" align="left">&nbsp;&nbsp;&nbsp;&nbsp;说明</td>
		    </tr>
		     <tr>
		       <td colspan="3">
		         <apptag:upload state="look" value="${chipdata.attachments}" cssClass="" entityId="${chipdata.id}" entityType="LP_TEST_CHIP_DATA"/>
		       </td>
		       <td colspan="3">
		       	  ${chipdata.coreData.fileRemark}
		       </td>
		    </tr>
		     <tr>
		      <td height="25"  bgcolor="#E6EFF7" colspan="6" align="center"><font size="2px" style="font-weight: bold">纤芯长度分析</font></td>
		    </tr>
		    <tr >
		       <td align="center">测试折射率</td>
		       <td align="center">测试脉宽</td>
		       <td align="center">芯长km</td>
		       <td align="center">是否有问题</td>
		       <td align="center">问题分析</td>
		       <td align="center">备注</td>
		    </tr>  
		    
		    <tr>
		       <td >
		       		&nbsp;&nbsp;${chipdata.corelength.refractiveIndex}
		       </td>
		       <td >
		       		${chipdata.corelength.pulseWidth}
		       </td>
		       <td >
		       		${chipdata.corelength.coreLength}
		       </td>
		       <td >
			     <c:if test="${chipdata.corelength.isProblem=='1'}">
			                      有
				 </c:if>
				  <c:if test="${chipdata.corelength.isProblem=='0'}">
			                    没有
				 </c:if>
		       </td>
		       <td >${chipdata.corelength.problemAnalyseLen}</td>
		       <td >${chipdata.corelength.lengremark}</td>
		    </tr>  
		    
		     <tr>
		      <td height="25"  bgcolor="#E6EFF7" colspan="6" align="center"><font size="2px" style="font-weight: bold">衰减常数分析</font></td>
		    </tr>
		    
		    <tr >
		       <td align="center">衰减常数dB/km</td>
		       <td align="center">是否合格</td>
		       <td align="center" colspan="2">问题分析</td>
		       <td align="center" colspan="2">备注</td>
		    </tr>
		     <tr>
		       <td >
		           &nbsp;&nbsp;${chipdata.decay.decayConstant}
		       </td>
		       <td >
		        <c:if test="${chipdata.decay.isStandardDec=='1'}">
			       	合格
		        </c:if>
		         <c:if test="${chipdata.decay.isStandardDec=='0'}">
				       不合格
		        </c:if>
		       </td>
		       <td colspan="2">${chipdata.decay.problemAnalyseDec}</td>
		       <td  colspan="2">${chipdata.decay.decayRemark}</td>
		    </tr>    
		    
		    <tr>
		      <td height="25" bgcolor="#E6EFF7" colspan="6" align="center"><font size="2px" style="font-weight: bold">成端损耗分析</font></td>
		    </tr>
		    
		    <tr >
		       <td align="center">成端损耗dB</td>
		       <td align="center">是否合格</td>
		       <td align="center" colspan="2">问题分析</td>
		       <td align="center" colspan="2">备注</td>
		    </tr>  
		    <tr >
		       <td >
		       		&nbsp;&nbsp;${chipdata.endwaste.endWaste}
		       </td>
		       <td>
		        <c:if test="${chipdata.endwaste.isStandardEnd=='1'}">
		        	合格				   
		        </c:if>
		         <c:if test="${chipdata.endwaste.isStandardEnd=='0'}">
				     不合格
		        </c:if>
		       </td>
		       <td colspan="2">${chipdata.endwaste.problemAnalyseEnd}</td>
		       <td colspan="2">${chipdata.endwaste.endRemark}</td>
		    </tr>    
		    
		    <tr>
		        <td height="25" bgcolor="#E6EFF7" colspan="6" align="center"><font size="2px" style="font-weight: bold">接头损耗分析</font>（记录分析存在问题或损值超过0.5dB的接头）</td>
		    </tr>
		    <tr>
		    	<td colspan="6">
		    	    <table border="1" width="100%" id="waste" align="center"  bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" bordercolor="#DBDBD8" style="border-collapse: collapse">
			    	    <tr>
					       <td align="center">序号</td>
					       <td align="center">接头位置</td>
					       <td align="center">损耗值dB</td>
					       <td align="center">问题分析</td>
					       <td align="center" colspan="2">备注</td>
					    </tr>  
					    <c:forEach items="${chipdata.conwaste}" var="conwa" varStatus="loop">
				    		<tr>
				    			<td>
				    				&nbsp;&nbsp;${conwa.orderNumber}
				    			</td>
				    			<td>
				    			  ${conwa.connectorStation}
				    			</td>
				    			<td>
				    				${conwa.waste}
				    			</td>
				    			<td>
				    				${conwa.problemAnalyse}
				    			</td>
				    			<td>
				    			   ${conwa.remark}
				    			</td>
				    	    </tr>
				    	</c:forEach>
		    	    </table>
		    	</td>
		    </tr>
		     <tr>
		      <td height="25"  bgcolor="#E6EFF7" colspan="6" align="center"><font size="2px" style="font-weight: bold">异常事件分析</font>（包括反射或非反射事件等）</td>
		    </tr>
		    <tr>
		    	<td colspan="6">
		    	    <table border="1" width="100%" id="exec" align="center"  bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" bordercolor="#DBDBD8" style="border-collapse: collapse">
			    	    <tr >
					       <td align="center">序号</td>
					       <td align="center">事件位置</td>
					       <td align="center">损耗值dB</td>
					       <td align="center">问题分析</td>
					       <td align="center" colspan="2">备注</td>
					    </tr> 
					     <c:forEach items="${chipdata.exceptionEvent}" var="event" varStatus="loop">
				    		<tr>
				    			<input type="hidden" name="proid" id="proid" value=""/>
				    			<td>
				    				&nbsp;&nbsp;${event.orderNumberExe}
				    			</td>
				    			<td>
				    			   ${event.eventStation}
				    			</td>
				    			<td>
				    				${event.wasteExe}
				    			</td>
				    			<td>
				    				${event.problemAnalyseExe}
				    			</td>
				    			<td>
				    			    ${event.remarkExe}
				    			</td>
				    	    </tr>
				    	</c:forEach>
		    	    </table>
		    	</td>
		    </tr>
		  
		    <tr>
		      <td height="25"  bgcolor="#E6EFF7" colspan="6" align="center"><font size="2px" style="font-weight: bold">其他分析</font></td>
		    </tr>
		    <tr >
		       <td align="center" colspan="2">分析简述</td>
		       <td align="center" colspan="2">分析结果</td>
		       <td align="center" colspan="2">说明</td>
		    </tr>  
		     <tr>
		       <td colspan="2">
		       		&nbsp;&nbsp;${chipdata.otherAnalyse.analyseOther}
		       </td>
		       <td colspan="2">
		       		${chipdata.otherAnalyse.analyseResultOther}
		       </td>
		       <td colspan="2">
		       		${chipdata.otherAnalyse.remarkOther}
		       </td>
		    </tr>  
		    <tr class=trcolor>
		      <td colspan="6" align="center">
		        <input type="button" class="button" onclick="javascript:parent.close();" value="返回" />
		      </td>
		    </tr>
		  </table>
		</html:form>
	</body>
</html>
