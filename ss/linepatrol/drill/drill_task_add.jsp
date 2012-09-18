<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>制定演练任务</title>
		<script type="text/javascript">
			function checkForm(state){
				if(state=="1"){
					if(valCharLength($("name").value)>50){
						alert("演练任务名称不能超过50个字符！");
					    return;
					}
					if(valCharLength($("locale").value)>500){
						alert("演练地点不能超过500个字符！");
					    return;
					}
					if(valCharLength($("locale").value)>500){
						alert("演练地点不能超过500个字符！");
					    return;
					}
					if(valCharLength($("demand").value)>300){
						alert("演练要求不能超过300个字符！");
					    return;
					}
					if(valCharLength($("remark").value)>300){
						alert("备注不能超过300个字符！");
					    return;
					}
					$("saveflag").value="1";
					processBar($('addDrillTask'));
					$('addDrillTask').submit();
					return;
				}
				if($("name").value.length==0){
					alert("演练任务名称不能为空！");
				    return;
				}
				if(valCharLength($("name").value)>50){
					alert("演练任务名称不能超过50个字符！");
				    return;
				}
				if($("beginTime").value.length==0){
					alert("演练开始时间不能为空！");
				    return;
				}
				if($("endTime").value.length==0){
					alert("演练结束时间不能为空！");
				    return;
				}
				if($("deadline").value.length==0){
					alert("方案提交时限不能为空！");
				    return;
				}
				if($("locale").value.length==0){
					alert("演练地点不能为空！");
				    return;
				}
				if(valCharLength($("locale").value)>500){
					alert("演练地点不能超过500个字符！");
				    return;
				}
				if($("contractorId").value.length==0){
					alert("至少选择一个代维公司！");
				    return;
				}
				if($("demand").value.length==0){
					alert("演练要求不能为空！");
				    return;
				}
				if(valCharLength($("demand").value)>300){
					alert("演练要求不能超过300个字符！");
				    return;
				}
				if($("remark").value.length==0){
					alert("备注不能为空！");
				    return;
				}
				if(valCharLength($("remark").value)>300){
					alert("备注不能超过300个字符！");
				    return;
				}
				processBar($('addDrillTask'));
				$('addDrillTask').submit();
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
		<template:titile value="制定演练任务"/>
		<html:form action="/drillTaskAction.do?method=addDrillTask" enctype="multipart/form-data" styleId="addDrillTask">
			<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">任务名称：</td>
					<td class="tdulright">
						<input type="text" name="name" id="name" class="inputtext" style="width:150px;" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">演练级别：</td>
					<td class="tdulright">
						<select name="level" style="width:150px;" class="inputtext">
							<option value="1">一般演练</option>
							<option value="2">重点演练</option>
						</select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">建议演练时间：</td>
					<td class="tdulright">
						<input name="beginTime" class="Wdate" id="beginTime" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'%y-%M-%d'})" readonly/>
						―
						<input name="endTime" class="Wdate" id="endTime" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginTime\')}'})" readonly/>
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
					<td class="tdulleft" style="width:20%;">演练地点：</td>
					<td class="tdulright">
						<input type="text" name="locale" id="locale" class="inputtext" style="width:150px;" />
						<font color="red">*</font>
					</td>
				</tr>
				<apptag:processorselect inputName="contractorId,userId,mobileId,conUser"
						spanId="userSpan" displayType="contractor"  />
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">演练要求：</td>
					<td class="tdulright">
						<textarea class="textarea" rows="3" name="demand" id="demand"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">备注：</td>
					<td class="tdulright">
						<textarea class="textarea" rows="3" id="remark" name="remark"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">任务附件：</td>
					<td class="tdulright">
						<apptag:upload cssClass="" entityId="" entityType="LP_DRILLTASK" state="add"/>
					</td>
				</tr>
			</table>
			<div style="height:20px; margin-left: 5%; width: 95%; margin-top: 5px;">
				<font color="red">所有项均为必填项，若无内容描述请填写“无”</font>
			</div>
			<div align="center" style="height:40px">
				<input type="hidden" id="saveflag" name="saveflag" value="0"/>
				<html:button property="action" styleClass="button" onclick="checkForm(0)">提交</html:button> &nbsp;&nbsp;
				<html:button property="action" styleClass="button" onclick="checkForm(1)">暂存</html:button> &nbsp;&nbsp;
				<html:reset property="action" styleClass="button">重写</html:reset>
			</div>
		</html:form>
	</body>
</html>
