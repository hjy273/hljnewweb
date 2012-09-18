<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<script type='text/javascript'>
			 function closewin(){
			 	 parent.close();
			 }
			 function view(planid,lineid){
				window.location.href="${ctx}/testApproveAction.do?method=viewCableData&planid="+planid+"&lineid="+lineid;
			}
	    </script>
	</head>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	<body>
	<%
		BasicDynaBean object=null;
		Object lineid=null;	
		Object planid = null;	
		Object state="";
		int i = 1;
	 %>
		   <display:table name="sessionScope.list"  id="currentRowObject" pagesize="12" >
		    				<display:column value="<%=i%>" title="序号" ></display:column>
							<display:column property="segmentname" sortable="true" title="中继段"  maxLength="20"/>
							<display:column property="finishtime" sortable="true" title="交维时间" />
							<display:column property="cableline_test_port" sortable="true" title="测试端" />
							<display:column property="plan_date"  sortable="true" style="width:15%" title="测试时间" />
							<display:column property="username" sortable="true" title="测试人" />
							<logic:equal value="1" name="currentRowObject" property="state">
							 <display:column property="isok" sortable="true" title="是否合格" />
							</logic:equal>
							<logic:notEqual value="1" name="currentRowObject" property="state">
							 <display:column value="" sortable="true" title="是否合格" />
							</logic:notEqual>
				            <display:column property="iswritestate" sortable="true" title="是否录入" />
							<display:column media="html" title="操作" >
							<% i++;
								object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
				           		 if(object != null) {
					      	 		lineid = object.get("lineid");
					      	 		planid = object.get("test_plan_id");
					      	 		state = object.get("state");
							} %>
							<logic:notEmpty name="data">
							 <% if(state.equals("1")){ %>
								 <a href="javascript:view('<%=planid%>','<%=lineid%>')">查看</a>  
							 <%} %>
						   </logic:notEmpty>
						  </display:column>
			</display:table>
			<logic:notEmpty name="dataApproves"> 
			    <table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
					 		<tr class=trcolor>
							   	<td class="tdulleft" colspan="2">数据录入审核次数:</td>
							   	<td class="tdulright" colspan="2"><c:out value="${data.approveTimes}"></c:out></td>
						   	</tr>
					 		<tr class=trwhite align="center">
							 	<td colspan="4" align="left">数据录入审核详细信息</td>
						    </tr>
							<tr  class=trcolor>
							 	<td width="10%">审核人</td><td width="20%">审核时间 </td><td width="10%">审核结果 </td><td width="60%">审核意见 </td>
							 </tr>
							 <c:forEach items="${dataApproves}" var="approve"  varStatus="loop">
							 	<c:if test="${loop.count%2==0 }"> <tr class=trwhite></c:if>
							 	<c:if test="${loop.count%2==1 }"> <tr class=trcolor></c:if>
							 		<td><bean:write name="approve" property="username"/></td>
							 		<td><bean:write name="approve" property="approve_time"/></td>
							 		<td><bean:write name="approve" property="approve_result"/></td>
							 		<td>
							 			<bean:write name="approve" property="approve_remark"/>
							 		</td>
							 	</tr>
							 </c:forEach>
				</table>
			</logic:notEmpty>
		         <div align="center">
			        <input type="button" class="button" value="返回" onclick="closewin();"/>
			     </div>
		 </table>
	</body>
</html>
