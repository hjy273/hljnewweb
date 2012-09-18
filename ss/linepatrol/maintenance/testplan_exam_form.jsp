<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>���˲��Լƻ�</title>
		<link rel='stylesheet' type='text/css'
			href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript'
			src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<script type='text/javascript'>
		  var win;
			function viewPalnDetail(){
			  var planid = document.getElementById("planid").value;
			  url="${ctx}/testPlanAction.do?method=viewPalnDetail&planId="+planid;
			  win = new Ext.Window({
			  layout : 'fit',
			  width: document.body.clientWidth * 0.9 , 
              height: 390 , 
			  resizable:true,
			  closeAction : 'close', 
			  modal:true,
			  html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src='+url+' />',
			  plain: true
			 });
			  win.show(Ext.getBody());
			}
			 function close(){
			  	win.close();
			 }
			 
		 function checkAddInfo() {
			if(!checkAppraiseDailyValid()){
  				return;
  			}
  			//processBar(saveEvaluate);
  			saveEvaluate.submit();
  		}
  		
  		function showOrHiddeInfo(){
			var infoDiv = document.getElementById("applyinfo");
			if(infoDiv.style.display=="block"){
				infoDiv.style.display="none";
			}else{
				infoDiv.style.display="block";
			}
		}
	</script>
		<style type="text/css">
.label {
	width: 30%;
	text-align: right;
}

.value {
	width: 70%;
	text-align: left;
}

.textArea {
	width: 400px;
}
</style>
	</head>

	<body>
		<template:titile value="���˲��Լƻ�" />
		<div id="applyinfo" style="display: none;">
		<table width="80%" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class=trcolor>
				<td class="tdulleft">
					��ά��λ��
				</td>
				<td class="tdulright">
					<bean:write name="plan" property="contractorname" />
				</td>
				<td class="tdulleft">
					�ƻ��ƶ��ˣ�
				</td>
				<td class="tdulright">
					<bean:write name="plan" property="username" />
				</td>
			</tr>
			<tr class=trwhite>
				<td class="tdulleft">
					�ƻ����ƣ�
				</td>
				<td class="tdulright">
					<bean:write name="plan" property="test_plan_name" />
				</td>
				<td colspan="2">
					<a href="javascript:viewPalnDetail();">�鿴�ƻ���ϸ��Ϣ</a>
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">
					�ƻ����ͣ�
				</td>
				<td class="tdulright" colspan="3">
					<bean:write name="plan" property="plantype" />
				</td>
			</tr>
			<tr class=trwhite>
				<td class="tdulleft">
					�ƻ��������ڣ�
				</td>
				<td class="tdulright" colspan="3">
					<bean:write name="plan" property="plantime" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">
					��ע��
				</td>
				<td class="tdulright" colspan="3"
					style="word-break: break-all; width: 500px;">
					<bean:write name="plan" property="test_plan_remark" />
				</td>
			</tr>
			<tr class=trwhite>
				<td class="tdulleft">
					��������������
				</td>
				<td class="tdulright" colspan="3">
					<bean:write name="problemNum" />
					��
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdulleft">
					�������������
				</td>
				<td class="tdulright" colspan="3">
					<bean:write name="solveNum" />
					��
				</td>
			</tr>
			<tr class=trwhite>
				<td class="tdulleft">
					�����޸��ʣ�
				</td>
				<td class="tdulright" colspan="3">
					<bean:write name="rate" />
				</td>
			</tr>
		</table>
		</div>
		<div align="right" style="height: 30px; line-height: 30px; width:80%; margin:0px auto;"><a onclick="showOrHiddeInfo();" style="cursor: pointer;">��ʾ/����</a></div>
		<html:form action="/testPlanExamAction.do?method=saveEvaluate"
			styleId="saveEvaluate">
			<input type="hidden" value="${planid}" name="planid" id="planid" />
		</html:form>
		<apptag:appraiseDailyDaily businessId="${planid}"
				contractorId="${contractorId}" businessModule="maintence"
				tableClass="tabout" tableStyle="width:80%;border-top:0px;"></apptag:appraiseDailyDaily>
				<apptag:appraiseDailySpecial businessId="${planid}"
				contractorId="${contractorId}" businessModule="maintence"
				tableClass="tabout" tableStyle="width:80%;border-top:0px;"></apptag:appraiseDailySpecial>
			<table width="80%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr class=trcolor align="center">
					<td colspan="2">
						<html:button property="action" styleClass="button"
							onclick="checkAddInfo()">�ύ</html:button>
						<html:reset property="action" styleClass="button">��д</html:reset>
						<input type="button" value="����" class="button"
							onclick="javascript:history.back();" />
					</td>
				</tr>
			</table>
	</body>
</html>
