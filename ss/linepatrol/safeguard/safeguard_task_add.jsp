<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>保障任务派单</title>
		<script type="text/javascript">
			function checkForm(state){
				if(state=="1"){
					if(valCharLength($("safeguardName").value)>100){
						alert("任务派单名称不能超过100个字符！");
					    return;
					}
					if(valCharLength($("region").value)>500){
						alert("保障地点不能超过500个字符！");
					    return;
					}
					if(valCharLength($("requirement").value)>1024){
						alert("保障要求不能超过1024个字符！");
					    return;
					}
					if(valCharLength($("remark").value)>1024){
						alert("保障备注不能超过1024个字符！");
					    return;
					}
					$("saveflag").value="1";
					addsafeguardtask.submit();
					return;
				}
				if(getTrimValue("safeguardName").length==0){
					alert("任务派单名称不能为空！");
					getElement("safeguardName").focus();
				    return;
				}
				if(valCharLength($("safeguardName").value)>100){
					alert("任务派单名称不能超过100个字符！");
				    return;
				}
				if(getTrimValue("startDate").length==0){
					alert("开始时间不能为空！");
					getElement("startDate").focus();
				    return;
				}
				if(getTrimValue("endDate").length==0){
					alert("结束时间不能为空！");
					getElement("endDate").focus();
				    return;
				}
				if(getTrimValue("deadline").length==0){
					alert("方案提交时限不 能为空！");
					getElement("deadline").focus();
				    return;
				}
				if(getTrimValue("contractorId").length==0){
					alert("至少选择一个代维公司！");
				    return;
				}				
				if(getTrimValue("region").length==0){
					alert("保障地点不能为空！");
					getElement("region").focus();
				    return;
				}
				if(valCharLength($("region").value)>500){
					alert("保障地点不能超过500个字符！");
				    return;
				}
				if(getTrimValue("requirement").length==0){
					alert("保障要求不能为空！");
					getElement("requirement").focus();
				    return;
				}
				if(valCharLength($("requirement").value)>1024){
					alert("保障要求不能超过1024个字符！");
				    return;
				}
				if(getTrimValue("remark").length==0){
					alert("保障备注不能为空！");
					getElement("remark").focus();
				    return;
				}
				if(valCharLength($("remark").value)>1024){
					alert("保障备注不能超过1024个字符！");
				    return;
				}
				addsafeguardtask.submit();
			}
			function getTrimValue(value){
				return document.getElementById(value).value.trim();
			}
			String.prototype.trim = function() {
				return this.replace(/^\s+|\s+$/g,"");
			}
			function getElement(value){
				return document.getElementById(value);
			}
			//判断字符长度
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
		</script>
	</head>
	<body>
		<template:titile value="保障任务派单"/>
		<html:form action="/safeguardTaskAction.do?method=addSafeguardTask" styleId="addsafeguardtask" enctype="multipart/form-data">
			<input type="hidden" name="safeguardCode" value="${safeguardCode }"/>
			<table border="1" align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">任务名称：</td>
					<td class="tdulright">
						<input type="text" name="safeguardName" id="safeguardName" class="inputtext" style="width:150px;">
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">保障时间：</td>
					<td class="tdulright">
						<input name="startDate" class="Wdate" id="startDate" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'%y-%M-%d'})" readonly/>
						―
						<input name="endDate" class="Wdate" id="endDate" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'#F{$dp.$D(\'startDate\')}'})" readonly/>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">方案提交时限：</td>
					<td class="tdulright">
						<input name="deadline" class="Wdate" id="deadline" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'%y-%M-%d'})" readonly/>
						<font color="red">*</font>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">保障级别：</td>
					<td class="tdulright">
						<input type="radio" name="level" value="4" checked="checked">特级
						<input type="radio" name="level" value="1">一级
						<input type="radio" name="level" value="2">二级
						<input type="radio" name="level" value="3">三级
					</td>
				</tr>
				<apptag:processorselect inputName="contractorId,userId,mobileId,conUser" 
						spanId="userSpan" displayType="contractor" />
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">保障地点：</td>
					<td class="tdulright">
						<input type="text" name="region" class="inputtext" id="region" style="width:150px;" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">保障要求：</td>
					<td class="tdulright">
						<textarea class="textarea" rows="3" id="requirement" name="requirement"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">保障备注：</td>
					<td class="tdulright">
						<textarea class="textarea" rows="3" id="remark" name="remark"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">任务附件：</td>
					<td class="tdulright">
						<apptag:upload cssClass="" entityId="" entityType="LP_SAFEGUARD_TASK" state="add"/>
					</td>
				</tr>
			</table>
			<div style="height:20px; margin-left: 5%; width: 95%; margin-top: 5px;">
				<font color="red">所有项均为必填项，若无内容描述请填写“无”</font>
			</div>
			<div align="center" style="height:40px">
				<input type="hidden" name="saveflag" id="saveflag" value="0">
				<html:button property="action" styleClass="button" onclick="checkForm(0)">提交</html:button> &nbsp;&nbsp;
				<html:button property="action" styleClass="button" onclick="checkForm(1)">暂存</html:button> &nbsp;&nbsp;
				<html:reset property="action" styleClass="button">重写</html:reset>
			</div>
		</html:form>
	</body>
</html>
