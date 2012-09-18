<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<script type='text/javascript'>
		  var win;
			function addPlanTask(){
			 var times =document.getElementById('testTimes').value;
			 var u="${ctx}/testYearPlanAction.do?method=addYearPlanTask&testTimes="+times;
			  win = new Ext.Window({
			  layout : 'fit',
			  width:560,
			  height:410, 
			  resizable:true,
			  closeAction : 'close', 
			  modal:true,
		    //  autoLoad:{url: '${ctx}/testYearPlanAction.do?method=addYearPlanTask',scripts:true}, 
			 html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src='+u+' />',
			  plain: true,
			  title:"��ӱ���м̶�" 
			 });
			  win.show(Ext.getBody());
			}
			
			function editPlanTask(id){
			 var u="${ctx}/testYearPlanAction.do?method=editYearTaskForm&cableLevel="+id;
			  win = new Ext.Window({
			  layout : 'fit',
			  width:560,
			  height:410, 
			  resizable:true,
			  closeAction : 'close', 
			  modal:true,
		    //  autoLoad:{url: '${ctx}/testYearPlanAction.do?method=addYearPlanTask',scripts:true}, 
			 html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src='+u+' />',
			  plain: true,
			  title:"�޸ı���м̶�" 
			 });
			  win.show(Ext.getBody());
			}
			
			function deleteTask(idValue){    
	      	   if(confirm("ȷ��Ҫɾ����?")){
	       			var params = "&cableLevel="+idValue;
	  				var url = "${ctx}/testYearPlanAction.do?method=deleteTask"
	  				var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:chipcallback,asynchronous:true}); 
		      	}
		     }
		    
		    function chipcallback(originalRequest) {
		    		var rst = originalRequest.responseText;
		  		    $('yeartask').update(rst);
		    }
			
			
			function close(){
				win.close();
			}
			
			function checkAddInfo() {
				var planName =document.getElementById('planName').value;
				if(planName==""){
					alert("�ƻ����Ʋ���Ϊ��!");
					return false;
				}
				var year =document.getElementById('year').value;
				if(year==""){
					alert("�ƻ���ݲ���Ϊ��!");
					return false;
				}
				var times =document.getElementById('testTimes').value;
				if(times==""){
					alert("ÿ��Ĭ�ϲ��Դ�������Ϊ��!");
					return false;
				}
				var yeartask =document.getElementById("yeartask").innerText;
				/*if(yeartask==""){
					alert("�ƻ�������Ϊ��!");
					return false;
				}*/
				var approvers = document.getElementById("approver").value;
				if(approvers==""){
					alert("��ѡ����ƻ������!");
			  		return false;
		  		}
			//	return true;
		  }
		
		function saveTask(obj){
	           	obj.request({ 
					   onComplete: function(originalRequest){ 
						   var rst = originalRequest.responseText;
						   if(rst=="1"){
						   	 alert("���¼������ظ�!");
						   }else{
						   	 $('yeartask').update(rst);
						   	 window.close();
						   }
						}
					});
			}

	    </script>
	</head>

	<body>
		<template:titile value="�ƶ���ƻ�" />
		<html:form action="/testYearPlanAction.do?method=saveYearPlan"
			styleId="saveTestPlan" onsubmit="return checkAddInfo();">
			<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				    <tr class=trwhite style="height:30px">
				      <td class="tdulleft">��ά��λ��</td>
				      <td class="tdulright"><c:out value="${LOGIN_USER.deptName}"></c:out></td>
				      <td class="tdulleft">�ƻ��ƶ��ˣ�</td>
				      <td class="tdulright"><c:out value="${LOGIN_USER.userName}"></c:out></td>
				    </tr>
				    <tr class=trcolor>
				      <td class="tdulleft">�ƻ����ƣ�</td>
				      <td class="tdulright" colspan="3">
				          <html:text property="planName" styleId="planName" styleClass="required" style="width:225px"></html:text>&nbsp;&nbsp;<font color="red">*</font>
				      </td>
				    </tr>
				    <tr class=trwhite>
				      <td class="tdulleft">�ƻ���ݣ�</td>
				      <td  class="tdulright" colspan="3">
				      <input name="year" id="year" class="Wdate" style="width:70px"
					onfocus="WdatePicker({dateFmt:'yyyy',minDate: '%y'})" readonly/>
					&nbsp;&nbsp;<font color="red">*</font>
				    </td>
				    </tr>
				      <tr class=trcolor>
				      	<td class="tdulleft">ÿ��Ĭ�ϲ��Դ�����</td>
				      	<td  class="tdulright" colspan="3"> 
				      		<html:text property="testTimes" styleId="testTimes" styleClass="required" value="2" style="width:70px"></html:text>
				      		&nbsp;&nbsp;<font color="red">*</font>
				      	</td>
				      </tr>
				     <tr class=trwhite>
			             <td class="tdulleft">
			                                      ����м̶Σ�
			             </td>
			             <td class="tdulright" colspan="3"><input type="button" value="��ӱ���м̶�" onclick="addPlanTask();"/></td>
				    </tr>
				     <tr class=trcolor>
					     <td class="tdulleft">����м̶Σ� </td>
			             <td class="tdulright" colspan="3">
			                <div id="yeartask"></div>
			             </td>
				    </tr>
				     <tr class=trwhite>
				    	<apptag:approverselect inputName="approver,mobile" label="�����" colSpan="4" inputType="radio" notAllowName="reads"></apptag:approverselect>
				     </tr>
				      <tr class=trcolor>
				    	<apptag:approverselect inputName="reads,rmobiles" label="������" colSpan="4" spanId="reader" notAllowName="approver"></apptag:approverselect>
				     </tr>
				    <tr>
				      <td align="center" colspan="4">				       
				        <html:submit styleClass="button" value="�ύ"></html:submit>
				     </td>
				    </tr>
				  </table>
		</html:form>
	</body>
</html>
