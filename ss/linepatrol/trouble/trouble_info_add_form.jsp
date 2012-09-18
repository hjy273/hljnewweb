<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linepatrol.trouble.services.TroubleConstant" %>
<html>
	<head>
		<title>故障派单</title>
			<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
			<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
			<script language="javascript" src="${ctx}/js/validate.js" type=""></script>
		<script type="text/javascript">
		function checkAddInfo() {
			var conids = document.getElementById("conids").value;
			if(conids==""){
				alert("代维单位不能为空!");
				return false;
			}
			var troubleName = troubleInfoBean.troubleName.value;
			if(troubleName==""){
				alert("故障名称不能为空!");
				return false;
			}
			var troubleStartTime = troubleInfoBean.troubleStartTime.value;
			if(troubleStartTime==""){
				alert("故障发生时间不能为空!");
				return false;
			}
			var troubleSendMan = troubleInfoBean.troubleSendMan.value;
			if(troubleSendMan==""){
				alert("故障派发人不能为空!");
				return false;
			}
			var replyDeadline = troubleInfoBean.replyDeadline.value;
			if(replyDeadline==""){
				alert("故障反馈时限不能为空!");
				return false;
			}
			var remark = saveTroubleInfo.troubleRemark;
	  		if(valCharLength(remark.value) > 1000) {
	  			 alert("故障描述不能超过500个汉字或者1000个英文字符！")
	             return false;
	  		}
			return true;
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
	   
	   	function judgeType(obj){
			if(obj=="1"){
				$('connectorTel').value=document.getElementById('telCity').value;
			}
			if(obj=="0"){
				$('connectorTel').value=document.getElementById('telOut').value;
			}
			if(obj=="-1"){
				$('connectorTel').value="";
			}
		}
		
		//暂存
		function save(){
  			var btnstate = $('tempstate');
  			btnstate.value="tempsaveTrouble";
  			var troubleName = troubleInfoBean.troubleName.value;
			if(troubleName==""){
				alert("故障名称不能为空!");
				return false;
			}
			var troubleStartTime = troubleInfoBean.troubleStartTime.value;
			if(troubleStartTime==""){
				alert("故障发生时间不能为空!");
				return false;
			}
  			var remark = saveTroubleInfo.troubleRemark;
	  			if(valCharLength(remark.value) > 1024) {
	  			    alert("备注信息不能超过512个汉字或者1024个英文字符！")
	              	return ;
	  		}
	  		// $('savebtn').disabled=true; 
	  		$('subbtn').disabled=true; 
	  	    saveTroubleInfo.submit();
  		}
  		function changeReplyDeadline(troubleType){
  			var troubleSendTime=convertStrToDate("<%=new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date())%>");
  			var deadlineSelect=document.forms[0].elements["deadline_select"];
  			var deadline="";
  			for(i=0;i<deadlineSelect.options.length;i++){
  				if(troubleType==deadlineSelect.options[i].value){
  					deadline=deadlineSelect.options[i].text;
  					break;
  				}
  			}
  			troubleSendTime.setTime(troubleSendTime.getTime()+parseFloat(deadline)*3600*1000);
  			troubleInfoBean.replyDeadline.value=troubleSendTime.getYear()+"/"+(troubleSendTime.getMonth()+1)+"/"+troubleSendTime.getDate()+" "+troubleSendTime.getHours()+":"+troubleSendTime.getMinutes()+":"+troubleSendTime.getSeconds();
  		}
	</script>
	</head>

	<body>
		
		<template:titile value="故障派单" />
		<html:form action="/troubleInfoAction.do?method=saveTroubleInfo"
			styleId="saveTroubleInfo" onsubmit="return checkAddInfo();">
			<input type="hidden" value="<%=TroubleConstant.TEL_OUTSKIRT%>" id="telOut"/>
			<input type="hidden" value="<%=TroubleConstant.TEL_CITY %>" id="telCity"/>
			<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				  <tr class=trwhite >
				  <apptag:contractorselect inputName="conids,useridss"  colSpan="4">
					  </apptag:contractorselect >
				  </tr>
				  <tr class=trcolor >
					  <apptag:processorselect inputName="depftids,userids,mobiles,deptids" labelName="短信接收人"
						spanId="userSpan"  displayType="mobile_contractor"/>
				  </tr>
				  <tr class=trwhite >
				  	<td class="tdulleft">故障名称：</td>
				  	<td class="tdulright"><html:text property="troubleName" styleClass="required" style="width:205"></html:text>&nbsp;&nbsp;<font color="red">*</font></td>
				    <td class="tdulleft" >故障发生时间：</td>
				    <td class="tdulright"><input name="troubleStartTime" class="Wdate" style="width:155px"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate: '%y-%M-%d-%H-%m-%s' })" 
					 readonly/>&nbsp;&nbsp;<font color="red">*</font></td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft">是否为重大故障：</td>
				    <td class="tdulright">
				       <input type="radio" name="isGreatTrouble" value="1" onclick="changeReplyDeadline(this.value);"  checked="checked">是&nbsp;&nbsp;
				       <input type="radio" name="isGreatTrouble" value="0" onclick="changeReplyDeadline(this.value);"  >否
				    </td>
				    <td class="tdulleft">故障反馈时限：</td>
				    <td class="tdulright">
				    	<span id="deadlineSpan" style="display:none;">
				        <c:if test="${deadline_map!=null}">
				        	<select name="deadline_select">
				        		<option value="1">24</option>
				        		<option value="0">54</option>
							<!-- <c:forEach items="${deadline_map}" var="oneDeadline">
								<option value="<c:out value="${oneDeadline.key}" />"><c:out value="${oneDeadline.value}" /></option>
							</c:forEach>
							 -->
							</select>
						</c:if>
						</span>
						<input name="replyDeadline" class="Wdate" style="width:155px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss' })" 
					 	readonly/>&nbsp;&nbsp;<font color="red">*</font>
					 	<script type="text/javascript">changeReplyDeadline("1");</script>
				    </td>
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">故障派发人：</td>
				    <td class="tdulright" colspan="3"><html:text property="troubleSendMan" style="width:155px"  styleClass="required"></html:text>&nbsp;&nbsp;<font color="red">*</font></td>
				  </tr>
				   <tr class=trcolor >
				    <td class="tdulleft">平台：</td>
				    <td class="tdulright">
				    	<c:if test="${LOGIN_USER.userID=='eomsxw'}">
				    		<input type="text" name="connector" value="郊区平台"/>
				    	</c:if>
				    	<c:if test="${LOGIN_USER.userID=='eomsyht'}">
				    		<input type="text" name="connector" value="城区平台"/>
				    	</c:if>
				    	<c:if test="${LOGIN_USER.userID!='eomsyht' && LOGIN_USER.userID!='eomsxw' }">
				    		<input type="text" name="connector" value=""/>
				    	</c:if>
				    	<!-- input type="radio" name="connector" value="1" onclick="judgeType(this.value)"  >城区平台&nbsp;&nbsp;
				        <input type="radio" name="connector" value="0" onclick="judgeType(this.value)" checked="checked">郊区平台
				    	 -->
				    </td>
				    <td class="tdulleft">平台电话：</td>
				    <td class="tdulright" >
				    	<c:if test="${LOGIN_USER.userID=='eomsxw'}">
				    		<input type="text" id="connectorTel" name="connectorTel" value="<%=TroubleConstant.TEL_OUTSKIRT%>" style="width:225px"/>
				    	</c:if>
				    	<c:if test="${LOGIN_USER.userID=='eomsyht'}">
				    		<input type="text" id="connectorTel" name="connectorTel" value="<%=TroubleConstant.TEL_CITY%>" style="width:225px"/>
				    	</c:if>
				    	<c:if test="${LOGIN_USER.userID!='eomsyht' && LOGIN_USER.userID!='eomsxw' }">
				    		<input type="text" id="connectorTel" name="connectorTel" value="" style="width:225px"/>
				    	</c:if>
				    	<!-- <input type="text" id="connectorTel" name="connectorTel" value="<%=TroubleConstant.TEL_CITY%>" style="width:255px"/> -->
				    </td>
				  </tr>
				 
				  <tr class=trwhite>
				    <td class="tdulleft">故障描述：</td>
				    <td class="tdulright" colspan="3"><html:textarea property="troubleRemark"  rows="4" style="width:375px"></html:textarea></td>
				  </tr>
				   <tr class=trcolor>
				   		<td class="tdulleft">添加附件：</td>
				   		<td class="tdulright"  colspan="3">
				   		  <apptag:upload state="add" cssClass=""/>
				   		</td>
				   </tr>
				  <tr>
				  	<td colspan="4">
				  		<font color="red">必选项目若无内容描述时，请填写“无”</font>
				  	</td>
				  </tr>
			</table>
			<div align="center" style="height:80px">
			   <input type="hidden" id="tempstate" name="tempsave"/>
			<!--     <html:button property="action" styleClass="button" styleId="savebtn" onclick="save();">保存</html:button>-->
		       <html:submit property="action" styleId="subbtn" styleClass="button">派单</html:submit> 
			   <html:reset property="action" styleClass="button">重置</html:reset>
			</div>
		</html:form>
		<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('saveTroubleInfo', {immediate : true, onFormValidate : formCallback});
		</script>
		<template:cssTable></template:cssTable>
	</body>
</html>
