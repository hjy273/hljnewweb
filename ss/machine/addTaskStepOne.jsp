<!-- 移动指派任务的第一步 -->
<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.machine.beans.MobileTaskBean;"%>
<html>
	<head>
		<script type="text/javascript" src="./js/prototype.js"></script>
		<script type="text/javascript">
				//设置主题
				setTaskTitle=function(obj) {
					var value = parseInt(obj.value);
					var titleEle = document.getElementById('tasktitle');
					var titleHidden = document.getElementById('title');
					var titleInfo = '';
					switch(value) {
						case 0:
							titleEle.innerHTML = '';
							titleHidden.value = '';
						break;
						case 1:
							titleInfo = setTitle(value,'核心机房');
							titleEle.innerHTML = titleInfo;
							titleHidden.value = titleInfo;
						break;
						case 2:
							titleInfo = setTitle(value,'接入层SDH机房');
							titleEle.innerHTML = titleInfo;
							titleHidden.value = titleInfo;
						break;
						case 3:
							titleInfo = setTitle(value,'接入层微波机房');
							titleEle.innerHTML = titleInfo;
							titleHidden.value = titleInfo;
						break;
						case 4:
							titleInfo = setTitle(value,'接入层FSO机房');
							titleEle.innerHTML = titleInfo;
							titleHidden.value = titleInfo;
						case 5:
							titleInfo = setTitle(value,'光交维护');
							titleEle.innerHTML = titleInfo;
							titleHidden.value = titleInfo;
						break;
					}
				}
			
				function setTitle(obj,info) {
					var nowDate = new Date();
					var month = nowDate.getMonth() + 1
					var d = nowDate.getDate();
					if(d>=1 && d<=7) {
						return info + month + '月第一周维护情况';
					}
					if(d >=8 && d<=14) {
						return info + month + '月第二周维护情况';
					}
					if(d >=15 && d<=21) {
						return info + month + '月第三周维护情况';
					}
					if(d >=22 && d<=28) {
						return info + month + '月第四周维护情况';
					}
					if(d >=29 && d<=31) {
						return info + month + '月第五周维护情况';
					}
				}
				
				//ajax
				function onConChange(obj) {
					var params = obj.value;
					var connameEle = document.getElementById('conname');
					var useridSelEle = document.getElementById('userid').options;//获取执行人的select
					var index = obj.options.selectedIndex;
					var conname = obj.options[index].text;
					connameEle.value = conname;
					useridSelEle.length = 0;//清空下拉列表
					useridSelEle.add(new Option('===请选择执行人===','0'));
					if(params == 0) {
						return;
					}
					var url = 'MobileTaskAction.do?method=getUserByConId&conId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callback, asynchronous:true});
				}
				
				function callback(originalRequest) {
					var useridSelEle = document.getElementById('userid').options;//获取执行人的select
					var rst = originalRequest.responseText;
					var userarr = eval('('+rst+')');
					for(var i=0; i<userarr.length; i++) {
						useridSelEle.add(new Option(userarr[i].username,userarr[i].userid));
					}
				}
				
				//设置隐藏域的userconname
				function setuserconname(obj) {
					var index = obj.options.selectedIndex;
					var userconname = obj.options[index].text;
					var userconnameEle = document.getElementById('userconname');
					userconnameEle.value = userconname;
				}
				
				//设置隐藏域的checkusername
				function setCheckUsernaem(obj) {
					var index = obj.options.selectedIndex;
					var checkusername = obj.options[index].text;
					var checkusernameEle = document.getElementById('checkusername');
					checkusernameEle.value = checkusername;
				}
				
				//选择时间
				function GetSelectDateTHIS(strID) {
			    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
			    	return ;
				 }
				 
				 //重置信息
				 function resetInfo() {
				 	var titleEle = document.getElementById('tasktitle');
					var titleHidden = document.getElementById('title');
					titleEle.innerHTML = '';
					titleHidden.value = '';
					document.getElementById('userid').options.length = 0;
				 }
				 
				 //检查任务信息
				 function checkAddInfo() {
				 	var machinetypeEle = document.getElementById('machinetype');
				 	if(machinetypeEle.value == 0) {
				 		alert("请选择类型!");
				 		machinetypeEle.focus();
				 		return false;
				 	}
				 	var contractoridEle = document.getElementById('contractorid');
				 	if(contractoridEle.value == 0) {
				 		alert("请选择代维公司!");
				 		contractoridEle.focus();
				 		return false;
				 	}
				 	
				 	
					var useridEle = document.getElementById('userid');
				 	if(useridEle.value == 0) {
						alert("请选择执行人!");
				 		useridEle.focus();
				 		return false;
				 	}
				 	var executetimeEle = document.getElementById('executetime');
				 	var executetime = executetimeEle.value;
				 	if(executetime==''){
				 		alert("执行日期不能为空!");
				 		return false;
				 	}
				 	
				 	if(executetime < getNowDate()) {
				 		alert("执行日期不能小于当前日期!");
				 		return false;
				 	}
				 	var date = new Date();
				 	if(executetime.substr(0,4) > date.getYear()) {
				 		alert("计划不能跨年!");
				 		return false;
				 	}
				 	if(executetime.substr(5,2) > (date.getMonth() + 1) ) {
				 		alert("计划不能跨月!");
				 		return false;
				 	}
				 	
				 	
				 	var checkuserEle = document.getElementById('checkuser');
				 	if(checkuserEle.value == 0) {
				 		alert("请选择核查人!");
				 		checkuserEle.focus();
				 		return false;
				 	}
				 	var remark = document.getElementById('remark');
				 	if(valCharLength(remark.value) > 500) {
				 		alert("备注信息长度应小于500个字母或者250个汉字");
				 		remark.value = '';
				 		return false;
				 	}
				 	addTaskStepOneFormId.submit();
				 }
				 
				 //获取服务器时间
				 function getNowDate() {
				 	var d = new Date();
				 	var s = '';
				 	s += d.getYear() + "/";
				 	
				 	if((d.getMonth() + 1) > 9) {
				 		s += (d.getMonth() + 1) + "/";
				 	} else {
				 		s += "0" + (d.getMonth() + 1) + "/";
				 	}
				 	
				 	if(d.getDate() > 9) {
				 		s += d.getDate() ;
				 	} else {
				 		s += "0" + d.getDate();
				 	}
					return s;
				 }
				 
				 
				 function valCharLength(Value){
				    var j=0;
				    var s = Value;
			      	for(var i=0; i<s.length; i++) {
				        if (s.substr(i,1).charCodeAt(0)>255) {
				        	j = j + 2;
				       	} else { 
				        	j++;
				        }
			      	}
			      	return j;
			     }
			</script>
	</head>

	<body>
		<br>
		<template:titile value="添加机房巡检任务" />
		<html:form action="MobileTaskAction.do?method=addTaskStepOne"
			styleId="addTaskStepOneFormId">
			<template:formTable namewidth="150" contentwidth="300">
				<template:formTr name="类型">
					<select name="machinetype" onchange="setTaskTitle(this)"
						style="width:245px;" class="inputtext">
						<option value="0">
							===请选择类型===
						</option>
						<option value="1">
							核心层
						</option>
						<option value="2">
							接入层SDH
						</option>
						<option value="3">
							接入层微波
						</option>
						<option value="4">
							接入层FSO
						</option>
						<option value="5">
							光交维护
						</option>
					</select>
				</template:formTr>

				<template:formTr name="主题">
					<font id="tasktitle"></font>
					<input type="hidden" name="title" id="title">
				</template:formTr>

				<template:formTr name="代维公司">
					<select class="inputtext" name="contractorid" style="width:245px;" id="contractorid"
						onchange="onConChange(this)">
						<option value="0">
							===请选择代维===
						</option>
						<logic:notEmpty name="conDeptList" scope="request">
							<logic:iterate id="conDeptListid" name="conDeptList"
								scope="request">
								<option
									value="<bean:write name="conDeptListid" property="contractorID"/>">
									<bean:write name="conDeptListid" property="contractorName" />
								</option>
							</logic:iterate>
						</logic:notEmpty>
					</select>
					
					<input type="hidden" name="conname" id="conname">
				</template:formTr>

				<template:formTr name="执行人">
					<select class="inputtext" name="userid" style="width:245px;"
						id="userid" onchange="setuserconname(this)">
						<option value="0"></option>
					</select>
					
					<input type="hidden" name="userconname" id="userconname"> 
				</template:formTr>

				<template:formTr name="执行日期">
					<input id="executetime" type="text" readonly="readonly"
						name="executetime" class="inputtext" style="width: 217" />
					<INPUT TYPE='BUTTON' VALUE='日期' readonly="readonly" ID='btn'
						onclick="GetSelectDateTHIS('executetime')"
						STYLE="font: 'normal small-caps 6pt serif';">
				</template:formTr>

				<template:formTr name="核查人">
					<select class="inputtext" name="checkuser" style="width:245px;" id="checkuser" onchange="setCheckUsernaem(this)">
						<option value="0">
							===请选择核查人===
						</option>
						<logic:notEmpty name="mobileList" scope="request">
							<logic:iterate id="mobileListId" name="mobileList"
								scope="request">
								<option
									value="<bean:write name="mobileListId" property="userid"/>">
									<bean:write name="mobileListId" property="username" />
								</option>
							</logic:iterate>
						</logic:notEmpty>
					</select>
					
					<input type="hidden" name="checkusername" id="checkusername">
				</template:formTr>

				<template:formTr name="制定人">
					<bean:write name="makePeopleName"/>
					<input type="hidden" value="<bean:write name="makePeopleId"/>" name="makepeopleid">
				</template:formTr>


				<template:formTr name="备注信息">
					<textarea name="remark" cols="36" rows="4" id="remark"
						title="最多输入256个汉字，512个英文字母！" class="textarea"></textarea>
				</template:formTr>

			</template:formTable>

			<template:formSubmit>
				<td>
					<html:button property="action" styleClass="button"
						onclick="checkAddInfo()">添加任务</html:button>
				</td>
				<td>
					<html:reset property="action" styleClass="button"
						onclick="resetInfo()">重置	</html:reset>
				</td>
			</template:formSubmit>

		</html:form>
	</body>
</html>
