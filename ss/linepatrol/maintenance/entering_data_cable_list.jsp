<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<script type='text/javascript'>
		  var win;
			function toEnteringCableForm(planid,lineid){
			  var url = "${ctx}/enteringCableDataAction.do?method=addEnteringCableForm&planid="+planid+"&lineid="+lineid;
			  win = new Ext.Window({
			  layout : 'fit',
			  width: document.body.clientWidth * 0.9 , 
              height: 450 , 
			  resizable:true,
			  closeAction : 'close', 
			  modal:true,
			  html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src='+url+' />',
			  plain: true,
			  title:"¼���м̶β�������" 
			 });
			  win.show(Ext.getBody());
			}
			
			function editEnteringCableForm(planid,lineid){
			  var url = "${ctx}/enteringCableDataAction.do?method=editEnteringCableForm&planid="+planid+"&lineid="+lineid;
			  win = new Ext.Window({
			  layout : 'fit',
			//  width:620,
			  //height:450, 
			  width: document.body.clientWidth * 0.9 , 
              height: 450, 
			  resizable:true,
			  closeAction : 'close', 
			  modal:true,
			  html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src='+url+'></iframe>',
			  plain: true,
			  title:"�޸��м̶β�������" 
			 });
			  win.show(Ext.getBody());
			}
			 function close(){
			  	win.close();
			 }
		
	     function  goBack(){
			var url="${ctx}/testPlanAction.do?method=getActWorkForm";
			self.location.replace(url);
		}
		function checkadd(){
			var approvers = document.getElementById("approvers").value;
			if(approvers==""){
				alert("��ѡ��¼�����������!");
		  		return ;
	  		}
	  		var allenter = document.getElementById('allenter').value;
	  		if(allenter=='false'){
	  			if(!confirm("���ݻ�����δ¼���,�Ƿ�Ҫ�ύ���?")){
	  			   return;
	  			}
	  		}
	  		processBar(approve);
	  		$(approve).submit();
		}
	    </script>
	</head>
     <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
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
	 %><template:titile value="${plan.testPlanName}¼���������"/>
	       <table width="80%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				    <tr class=trwhite>
				      <td class="tdulleft">�ƻ����ƣ�</td>
				      <td class="tdulright">
				         <c:out value="${plan.testPlanName}"></c:out>
				      </td>
				      <td colspan="2">&nbsp;</td>
				    </tr>
				    <tr class=trcolor>
				      <td class="tdulleft">�ƻ����ͣ�</td>
				      <td class="tdulright" colspan="3">
				     	 <c:if test="${plan.testPlanType=='1'}">���˲���</c:if>
				         <c:if test="${plan.testPlanType=='2'}">�ӵص������</c:if>
				         <c:if test="${plan.testPlanType=='3'}"> �������׾�Ե�������</c:if>
				     </td>
				    </tr>
				    <tr class=trwhite>
				      <td class="tdulleft">�ƻ��������ڣ�</td>
				      <td class="tdulright" colspan="3">
				      <bean:write name="plan" property="testBeginDate" format="yyyy-MM-dd"/>
				          ��
				        <bean:write name="plan" property="testEndDate" format="yyyy-MM-dd"/>
				     </td>
				    </tr>
				   <tr class=trcolor>
				      <td class="tdulleft">��ά��λ��</td>
				      <td class="tdulright"><c:out value="${contraName}"></c:out></td>
				      <td class="tdulleft">�ƻ��ƶ��ˣ�</td>
				      <td class="tdulright"><c:out value="${userName}"></c:out></td>
				    </tr>
				    <tr class=trwhite>
				      <td class="tdulleft">��ע��</td>
				      <td class="tdulright" colspan="3">				       
				           <c:out value="${plan.testPlanRemark}"></c:out>  
				     </td>
				    </tr>
				    </table>
		   <display:table name="sessionScope.lines"  id="currentRowObject" pagesize="13" >
		    	<display:column value="<%=i%>" title="���" style="3%"></display:column>
				<display:column property="segmentname" sortable="true" title="�м̶�"  maxLength="30" style="width:17%"/>
				<display:column property="finishtime" sortable="true" title="��άʱ��"  style="width:10%"/>
				<display:column property="cableline_test_port" sortable="true" title="�ƻ����Զ�" style="width:7%"/>
				<display:column property="plan_date"  sortable="true" style="width:10%" title="�ƻ�����ʱ��" maxLength="25" />
				<display:column property="username" sortable="true" title="�ƻ�������" style="width:12%" maxLength="8"/>
				<logic:equal value="1" name="currentRowObject" property="state">
				<display:column property="isok" sortable="true" title="�Ƿ�ϸ�" style="width:8%" maxLength="20"/>
				</logic:equal>
				<logic:notEqual value="1" name="currentRowObject" property="state">
				<display:column value="" sortable="true" title="�Ƿ�ϸ�" style="width:8%" maxLength="20"/>
				</logic:notEqual>
				<display:column property="iswritestate" sortable="true" title="�Ƿ�¼��" style="width:8%" maxLength="20"/>
				<display:column media="html" title="����" >
				<% i++;
					object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
	           		 if(object != null) {
		      	 		lineid = object.get("lineid");
		      	 		planid = object.get("test_plan_id");
		      	 		state = object.get("state");
		      	 		
				} %>
	            	<% if(state.equals("0")){ %>
	            		<a href="javascript:toEnteringCableForm('<%=planid%>','<%=lineid%>')">¼���������</a>
	            	<%} 
	            	 if(state.equals("1") || state.equals("2")){ %>
	            	 <a href="javascript:editEnteringCableForm('<%=planid%>','<%=lineid%>')">�޸Ĳ�������</a>
	            	<%} %>
            </display:column>
			</display:table><br/>
					<html:form action="/enteringCableDataAction.do?method=saveEnteringApprove" styleId="approve">
					<input type="hidden" value="${dataid}" name="dataid"/>
					<input type="hidden" value="${plan.id}" name="planid"/>
					<input type="hidden" value="${allenter}" name="allenter" id="allenter"/>
						<div align="left" style="height:39px">
							   &nbsp;&nbsp;<apptag:approverselect inputName="approvers,mobiles" label="�����" 
							   inputType="radio" notAllowName="reads" approverType="approver" 
				    	       objectType="LP_TEST_DATA" objectId="${dataid}" 
							   ></apptag:approverselect><br/>
				    	       &nbsp;&nbsp;<apptag:approverselect inputName="reads,rmobiles" label="������"
				    	        colSpan="2" spanId="reader" notAllowName="approvers"   approverType="reader"
				    	        objectType="LP_TEST_DATA" objectId="${dataid}" 
				    	        ></apptag:approverselect>
						</div>
					         <div align="center">
						        <input type="button" class="button" value="�ύ" onclick="checkadd();"/>
						         <input type="button" class="button" value="����" onclick="goBack();"/>
						     </div>
				    </html:form>
		 </table>
	</body>
</html>
