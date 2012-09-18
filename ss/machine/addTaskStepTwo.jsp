<!-- 指派任务的第二步 -->
<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.machine.beans.MobileTaskBean;"%>
<html>
	<head>
		<title></title>
		<script type="text/javascript">
				var equArr = new Array();//一行的信息
				var infoArr = new Array();
				
				function initArray(eid,ename) {
					equArr[0] = eid;
					equArr[1] = ename;
					infoArr[infoArr.length] = equArr;
					equArr = new Array();
					return true;
				}
			
				function checkOrCancel(obj) {
					if(obj.checked) {
						checkAll();
					} else {
						cancelCheck();
					}
				}
				
				function checkAll() {
					var subChecks = document.getElementsByName("ifCheck");
					var length = subChecks.length;
					for(var i = 0; i < length; i++) {
						subChecks[i].checked = true;
					}		
				}
				
				function cancelCheck() {
					var allChecks = document.getElementsByName('ifCheck');
					var length = allChecks.length;
					for(var i = 0; i < length; i++) {
						allChecks[i].checked = false;
					}
				}
				
				function delCheck(obj) {
					var delChecks = document.getElementsByName('ifCheck');
					var tableBody = document.getElementsByTagName('tbody')[obj];
					var delRows = new Array(delChecks.length);
					var delid = 0;
					if(delChecks.length == 0) {
						alert("您还没有增加要巡检的设备!");
						return;
					}
					for(i = 0; i < delChecks.length; i++) {
						if(delChecks[i].checked) {
							delRows[delid++] = document.getElementById(delChecks[i].value)
						}
					}
					if(delid == 0 ) {
						alert("请选择要删除的巡检设备!");
						return;
					}
					if(delid != 0) {
						if(confirm("确实要删除吗？")) {
							for(i = 0; i <= delid; i++) {
								if(delRows[i] != null && delRows[i].nodeType == 1) {
									tableBody.removeChild(delRows[i]);
								}
							}
						document.getElementById('sel').checked = false;
						}
					}
				}
			
		</script>
		
		<style type="text/css">
			.checkTd {
				width: 40px;
				text-align: center;
			}
			.a{
				text-align: center;
				width: 270px;
			}
			.b{
				text-align: center;
				width: 270px;
			}
		</style>
	</head>

	<body>
		<br>
		<logic:present name="equList" scope="request">
			<logic:iterate id="equListId" name="equList">
				<script type="text/javascript">
					initArray("<bean:write name="equListId" property="eid"/>","<bean:write name="equListId" property="equipment_name"/>");
				</script>
			</logic:iterate>
		</logic:present>

		<logic:equal value="1" name="flag" scope="request">
			<script type="text/javascript">
			//var index = 0;
			function addRowForCoreAndSDH() {//为核心层或SDH层增加一个设备巡检信息
				var maxSeqEle = document.getElementById("maxSeq");//获取隐藏域
				var maxSeq = parseInt(maxSeqEle.value);
				maxSeq++;
				//if(index >= infoArr.length) {
					//alert("巡检设备已全部增加");
					//return;
				//}
				//index++;
				maxSeqEle.value = maxSeq;//设置隐藏域的值
				var tableBody = document.getElementsByTagName("tbody")[0];
				var newTr = document.createElement("tr");
				newTr.id = maxSeq;
				newTr.className = "trcolor";
				
				var checkTd = document.createElement("td");//创建一个TD存放CHECKBOX
				checkTd.innerHTML = "<input type='checkbox' name='ifCheck' checked='checked' value='" + maxSeq + "'>";
				checkTd.className = "tdulleft";
				
				var selTd = document.createElement("td");//创建一个TD存放下拉列表
				selTd.className = "tdulright";
				
				var selEle = document.createElement("select");//创建一个SEL
				selEle.name = 'selEqu_' + maxSeq;//设置SEL的name
				selEle.id = 'selEqu_' + maxSeq;//设置SEL的ID
				selEle.className = "inputtext";
				selEle.style.width = "250px";
				selEle.onchange = onSelChange;
				selEle.options.add(new Option("请选择要巡检的设备","0"));
        		for(var i=0; i<infoArr.length; i++) {
        			selEle.options.add(new Option(infoArr[i][1],infoArr[i][0]));
        		}
				selTd.appendChild(selEle);//将sel防入TD中
				newTr.appendChild(checkTd);//将CHECKTD放入TR中
				newTr.appendChild(selTd);//将SELTD放入TR中
				tableBody.appendChild(newTr);
			}
			
			function addAllForCoreAndSDH() {//为核心层或SDH层增加设备巡检信息
			
				var tableBody = document.getElementsByTagName("tbody")[0];
				for(var i=0; i<infoArr.length; i++) {
				
					var newTr = document.createElement("tr");
					newTr.id = i;
					newTr.className = "trcolor";
				
					var checkTd = document.createElement("td");//创建一个TD存放CHECKBOX
					checkTd.innerHTML = "<input type='checkbox' name='ifCheck' checked='checked' value='" + i + "'>";
					checkTd.className = "tdulleft";
					checkTd.id = i;
					var selTd = document.createElement("td");//创建一个TD存放下拉列表
					selTd.className = "tdulright";
					selTd.id=i;
					selTd.innerHTML= infoArr[i][1]+ "<input type='hidden' value='"+infoArr[i][0]+"' name='selEqu_"+i+"'>";
					newTr.appendChild(checkTd);//将CHECKTD放入TR中
					newTr.appendChild(selTd);//将SELTD放入TR中
					tableBody.appendChild(newTr);
				}
			}
			
			//改变时判断是否已被选过
			function onSelChange() {
				var trId = this.id;
				var thisValue = document.getElementById(trId).value;
				var subCheck = document.getElementsByName('ifCheck');
				for(var i=0;i<subCheck.length;i++) {
					var selId = "selEqu_"+subCheck[i].value;
					if(selId != trId) {
						if(document.getElementById(selId).value == document.getElementById(trId).value) {
							alert("请不要选择同一个设备");
							document.getElementById(trId).value = "0";
							document.getElementById(trId).focus();
							return;
						}
					}
				}
			}
			
			function checkInfo() {
				var subCheck = document.getElementsByName('ifCheck');
				var checked = new Array();
				if(subCheck.length == 0) {
					alert("请增加要巡检的设备!");
					return false;
				}
				for(var i=0;i<subCheck.length;i++) {
					if(subCheck[i].checked) {
						checked[checked.length] = "selEqu_"+subCheck[i].value;
					}
				}
				if(checked.length == 0) {
					alert("请选择需要被巡检的设备");
					return false;
				}
				for(var i=0; i<checked.length; i++) {
					if(document.getElementById(checked[i]).value == "0") {
						alert("请选择需要巡检的设备");
						document.getElementById(checked[i]).focus();
						return false;
					}
				}
			}
			</script>
			<template:titile value="添加机房设备巡检任务" />
			<html:form action="MobileTaskAction.do?method=addTaskStepForSDHAndCore"
				styleId="addTaskStepTwoFormId">
				<template:formTable namewidth="150" contentwidth="350" th1="项目"
					th2="内容">
					<template:formTr name="类型">
						<logic:equal value="1" name="taskbean" property="machinetype">
							核心层
						</logic:equal>
						<logic:equal value="2" name="taskbean" property="machinetype">
							接入层SDH
						</logic:equal>
						<logic:equal value="3" name="taskbean" property="machinetype">
							接入层微波
						</logic:equal>
						<logic:equal value="4" name="taskbean" property="machinetype">
							接入层FSO
						</logic:equal>

						<logic:equal value="5" name="taskbean" property="machinetype">
							光交维护
						</logic:equal>
						<input type="hidden"
							value="<bean:write name="taskbean" property="machinetype"/>"
							name="machinetype">
					</template:formTr>

					<template:formTr name="主题">
						<bean:write name="taskbean" property="title" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="title"/>"
							name="title">
					</template:formTr>

					<template:formTr name="代维公司">
						<bean:write name="taskbean" property="conname" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="contractorid"/>"
							name="contractorid">
					</template:formTr>

					<template:formTr name="执行人">
						<bean:write name="taskbean" property="userconname" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="userid"/>"
							name="userid">
					</template:formTr>

					<template:formTr name="执行日期">
						<bean:write name="taskbean" property="executetime" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="executetime"/>"
							name="executetime">
					</template:formTr>

					<template:formTr name="核查人">
						<bean:write name="taskbean" property="checkusername" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="checkuser"/>"
							name="checkuser">
					</template:formTr>

					<template:formTr name="制定人">
						<bean:write name="makePeopleName" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="makepeopleid"/>"
							name="makepeopleid">
					</template:formTr>

					<template:formTr name="备注信息">
						<bean:write name="taskbean" property="remark" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="remark"/>"
							name="remark">
					</template:formTr>
					
					<template:formTr name="添加巡检设备">
					</template:formTr>

				</template:formTable>

					<div id="mechineList" style="height:100%;overflow-x:hidden;overflow-y:auto;">
							<table>
								<tbody></tbody>
							</table>
							<script language="javascript">
								addAllForCoreAndSDH();
							</script>
					</div>
					
				<table align="center" cellpadding="3" cellspacing="1">
					<tr>
						<td>
							<input type="checkbox" id="sel" onclick="checkOrCancel(this)" />
							全选/全不选
							<input type="hidden" value="0" id="maxSeq">
						</td>
						<td>
							<input type="submit" value="提交" class="button" onclick="return checkInfo()"/>
						</td>
						<td>
							<input type="reset" value="重置" class="button" />
						</td>
					</tr>
				</table>
			</html:form>
		</logic:equal>










		<logic:equal value="2" name="flag" scope="request">
			<script type="text/javascript">
			
				function addRowForMicro() {
					var maxSeqEle = document.getElementById("maxSeq");//获取隐藏域
					var maxSeq = parseInt(maxSeqEle.value);
					maxSeq++;
					maxSeqEle.value = maxSeq;//设置隐藏域的值
					var tableBody = document.getElementsByTagName("tbody")[1];
					var newTr = document.createElement("tr");
					newTr.id = maxSeq;
					newTr.className = "trcolor";
					
					var checkTd = document.createElement("td");//创建一个TD存放CHECKBOX
					checkTd.innerHTML = "<input type='checkbox' name='ifCheck' checked='checked' value='" + maxSeq + "'>";
					
					var selTdA = document.createElement("td");//创建一个TD存放下拉列表
					selTdA.className = "a";
					var selEleA = document.createElement("select");//创建一个SEL
					selEleA.name = 'selEquA_' + maxSeq;//设置SEL的name
					selEleA.id = 'selEquA_' + maxSeq;//设置SEL的ID
					selEleA.className = "inputtext";
					selEleA.style.width = "250px";
					selEleA.onchange = onSelChange;
					selEleA.options.add(new Option("请选择要巡检的A端设备","0"));
	        		for(var i=0; i<infoArr.length; i++) {
	        			selEleA.options.add(new Option(infoArr[i][1],infoArr[i][0]));
	        		}
					selTdA.appendChild(selEleA);//将sel防入TD中
					
					var selTdB = document.createElement("td");//创建一个TD存放下拉列表
					selTdB.className = "b";
					var selEleB = document.createElement("select");//创建一个SEL
					selEleB.name = 'selEquB_' + maxSeq;//设置SEL的name
					selEleB.id = 'selEquB_' + maxSeq;//设置SEL的ID
					selEleB.className = "inputtext";
					selEleB.style.width = "250px";
					selEleB.onchange = onSelChange;
					selEleB.options.add(new Option("请选择要巡检的B端设备","0"));
	        		
	        		for(var i=0; i<infoArr.length; i++) {
	        			selEleB.options.add(new Option(infoArr[i][1],infoArr[i][0]));
	        		}
					selTdB.appendChild(selEleB);//将sel防入TD中
					
					newTr.appendChild(checkTd);//将CHECKTD放入TR中
					newTr.appendChild(selTdA);//将SELTD放入TR中
					newTr.appendChild(selTdB);//将SELTD放入TR中
					
					tableBody.appendChild(newTr);
				}
				
				function onSelChange() {
					var thisId = this.id;
					var thisvalue = this.value;
					var checks = document.getElementsByName('ifCheck');
					for(var i=0;i<checks.length;i++) {
						var selAId = "selEquA_"+checks[i].value;
						var selBId = "selEquB_"+checks[i].value;
						if(selAId != thisId) {
							if(document.getElementById(selAId).value == thisvalue) {
								document.getElementById(thisId).value = "0";
								document.getElementById(thisId).focus();
								alert("请不要选择同一个设备");
								return;
							}
						}
						if(selBId != thisId) {
							if(document.getElementById(selBId).value == thisvalue) {
								document.getElementById(thisId).value = "0";
								document.getElementById(thisId).focus();
								alert("请不要选择同一个设备");
								return;
							}
						}
					}
				}
				
				function checkInfo() {
					var subCheck = document.getElementsByName('ifCheck');
					var equArr = 0;
					if(subCheck.length == 0) {
						alert("请增加要巡检的设备!");
						return false;
					}
					for(var i=0;i<subCheck.length;i++) {
						if(subCheck[i].checked) {
							equArr++;
							if(document.getElementById("selEquA_"+subCheck[i].value).value == "0") {
								alert("请选择要巡检的设备");
								document.getElementById("selEquA_"+subCheck[i].value).focus();
								return false;
							}
							if(document.getElementById("selEquB_"+subCheck[i].value).value == "0") {
								alert("请选择要巡检的设备");
								document.getElementById("selEquB_"+subCheck[i].value).focus();
								return false;
							}
						}
					}
					if(equArr == 0) {
						alert("请选择要巡检的设备!");
						return false;
					}
				}
				
			</script>
			<template:titile value="添加机房设备巡检任务" />
			<html:form action="MobileTaskAction.do?method=addTaskStepForMicro"
				styleId="addTaskStepTwoFormId">
				<template:formTable namewidth="180" contentwidth="400" th1="项目"
					th2="内容">
					<template:formTr name="类型">
						<logic:equal value="1" name="taskbean" property="machinetype">
							核心层
						</logic:equal>
						<logic:equal value="2" name="taskbean" property="machinetype">
							接入层SDH
						</logic:equal>
						<logic:equal value="3" name="taskbean" property="machinetype">
							接入层微波
						</logic:equal>
						<logic:equal value="4" name="taskbean" property="machinetype">
							接入层FSO
						</logic:equal>
						<logic:equal value="5" name="taskbean" property="machinetype">
							光交维护
						</logic:equal>
						<input type="hidden"
							value="<bean:write name="taskbean" property="machinetype"/>"
							name="machinetype">
					</template:formTr>

					<template:formTr name="主题">
						<bean:write name="taskbean" property="title" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="title"/>"
							name="title">
					</template:formTr>

					<template:formTr name="代维公司">
						<bean:write name="taskbean" property="conname" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="contractorid"/>"
							name="contractorid">
					</template:formTr>

					<template:formTr name="执行人">
						<bean:write name="taskbean" property="userconname" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="userid"/>"
							name="userid">
					</template:formTr>

					<template:formTr name="执行日期">
						<bean:write name="taskbean" property="executetime" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="executetime"/>"
							name="executetime">
					</template:formTr>

					<template:formTr name="核查人">
						<bean:write name="taskbean" property="checkusername" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="checkuser"/>"
							name="checkuser">
					</template:formTr>

					<template:formTr name="制定人">
						<bean:write name="makePeopleName" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="makepeopleid"/>"
							name="makepeopleid">
					</template:formTr>

					<template:formTr name="备注信息">
						<bean:write name="taskbean" property="remark" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="remark"/>"
							name="remark">
					</template:formTr>

				</template:formTable>
				
				<table align="center" width="580" cellpadding="0" cellspacing="1" class="tabout">
					<tr class="trcolor">
						<td class="checkTd">选择</td>
						<td class="a">选择A端设备</td>
						<td class="b">选择B端设备</td>
					</tr>
				</table>
			
				<table align="center" cellpadding="3" cellspacing="1">
					<tr>
						<td>
							<input type="checkbox" id="sel" onclick="checkOrCancel(this)" />
							全选/反选
							<input type="hidden" value="0" id="maxSeq">
						</td>
						<td>
							<input type="button" value="增加巡检设备" class="button"
								onclick="addRowForMicro()" />
						</td>
						<td>
							<input type="button" value="删除选中" class="button" onclick="delCheck(1)"/>
						</td>
						<td>
							<input type="submit" value="提交" class="button" onclick="return checkInfo()"/>
						</td>
						<td>
							<input type="reset" value="重置" class="button" />
						</td>
					</tr>
				</table>
			</html:form>
		</logic:equal>









		<logic:equal value="3" name="flag" scope="request">
			<script type="text/javascript">
				function addRowForFSO() {
					var maxSeqEle = document.getElementById("maxSeq");//获取隐藏域
					var maxSeq = parseInt(maxSeqEle.value);
					maxSeq++;
					maxSeqEle.value = maxSeq;//设置隐藏域的值
					var tableBody = document.getElementsByTagName("tbody")[1];
					var newTr = document.createElement("tr");
					newTr.id = maxSeq;
					newTr.className = "trcolor";
					
					var checkTd = document.createElement("td");//创建一个TD存放CHECKBOX
					checkTd.innerHTML = "<input type='checkbox' name='ifCheck' checked='checked' value='" + maxSeq + "'>";
					
					var selTdA = document.createElement("td");//创建一个TD存放下拉列表
					selTdA.className = "equ";
					var selEleA = document.createElement("select");//创建一个SEL
					selEleA.name = 'selEquA_' + maxSeq;//设置SEL的name
					selEleA.id = 'selEquA_' + maxSeq;//设置SEL的ID
					selEleA.onchange = onSelChange;
					selEleA.className = "inputtext";
					selEleA.style.width = "160px";
					selEleA.options.add(new Option("请选择要巡检的A端设备","0"));
	        		for(var i=0; i<infoArr.length; i++) {
	        			selEleA.options.add(new Option(infoArr[i][1],infoArr[i][0]));
	        		}
					selTdA.appendChild(selEleA);//将sel防入TD中
					
					var selTdB = document.createElement("td");//创建一个TD存放下拉列表
					selTdB.className = "equ";
					var selEleB = document.createElement("select");//创建一个SEL
					selEleB.name = 'selEquB_' + maxSeq;//设置SEL的name
					selEleB.id = 'selEquB_' + maxSeq;//设置SEL的ID
					selEleB.onchange = onSelChange;
					selEleB.className = "inputtext";
					selEleB.style.width = "160px";
					selEleB.options.add(new Option("请选择要巡检的B端设备","0"));
	        		for(var i=0; i<infoArr.length; i++) {
	        			selEleB.options.add(new Option(infoArr[i][1],infoArr[i][0]));
	        		}
					selTdB.appendChild(selEleB);//将sel防入TD中
					
					var modelTd = document.createElement("td");//创建一个TD存放文本输入框
					modelTd.className = "inputStyle";
					var modelText = document.createElement("input");
					modelText.name = "model_" + maxSeq;
					modelText.id = "model_" + maxSeq;
					modelText.className = "inputtext";
					modelText.maxLength = "20";
					modelText.style.width = "130px";
					modelTd.appendChild(modelText);
					
					var noTd = document.createElement("td");//创建一个TD存放文本输入框
					noTd.className = "inputStyle";
					var noText = document.createElement("input");
					noText.name = "no_" + maxSeq;
					noText.id = "no_" + maxSeq;
					noText.className = "inputtext";
					noText.maxLength = "20";
					noText.style.width = "130px";
					noTd.appendChild(noText);
				
					var powerTd = document.createElement("td");//创建一个TD存放文本输入框
					powerTd.className = "inputStyle";
					var powerText = document.createElement("input");
					powerText.name = "power_" + maxSeq;
					powerText.id = "power_" + maxSeq;
					powerText.className = "inputtext";
					powerText.maxLength = "20";
					powerText.style.width = "130px";
					powerTd.appendChild(powerText);
				
					newTr.appendChild(checkTd);//将CHECKTD放入TR中
					newTr.appendChild(selTdA);//将SELTDA放入TR中
					newTr.appendChild(selTdB);//将SELTDB放入TR中
					newTr.appendChild(modelTd);
					newTr.appendChild(noTd);
					newTr.appendChild(powerTd);
					
					tableBody.appendChild(newTr);
				}
				
				function onSelChange() {
					var thisId = this.id;
					var thisvalue = this.value;
					var checks = document.getElementsByName('ifCheck');
					for(var i=0;i<checks.length;i++) {
						var selAId = "selEquA_"+checks[i].value;
						var selBId = "selEquB_"+checks[i].value;
						if(selAId != thisId) {
							if(document.getElementById(selAId).value == thisvalue) {
								document.getElementById(thisId).value = "0";
								document.getElementById(thisId).focus();
								alert("请不要选择同一个设备");
								return;
							}
						}
						if(selBId != thisId) {
							if(document.getElementById(selBId).value == thisvalue) {
								document.getElementById(thisId).value = "0";
								document.getElementById(thisId).focus();
								alert("请不要选择同一个设备");
								return;
							}
						}
					}
				}
				
				
				function checkInfo() {
					var subCheck = document.getElementsByName('ifCheck');
					var equArr = 0;
					if(subCheck.length == 0) {
						alert("请增加要巡检的设备!");
						return false;
					}
					for(var i=0;i<subCheck.length;i++) {
						if(subCheck[i].checked) {
							equArr++;
							if(document.getElementById("selEquA_"+subCheck[i].value).value == 0) {
								alert("请选择要巡检的设备");
								document.getElementById("selEquA_"+subCheck[i].value).focus();
								return false;
							}
							if(document.getElementById("selEquB_"+subCheck[i].value).value == 0) {
								alert("请选择要巡检的设备");
								document.getElementById("selEquB_"+subCheck[i].value).focus();
								return false;
							}
							if(document.getElementById("model_"+subCheck[i].value).value.length == 0) {
								alert("请输入设备型号!");
								document.getElementById("model_"+subCheck[i].value).focus();
								return false;
							}
							if(document.getElementById("no_"+subCheck[i].value).value.length == 0) {
								alert("请输入机身号!");
								document.getElementById("no_"+subCheck[i].value).focus();
								return false;
							}
							if(document.getElementById("power_"+subCheck[i].value).value.length == 0) {
								alert("请输入设备/电源类型!");
								document.getElementById("power_"+subCheck[i].value).focus();
								return false;
							}
						}
					}
					if(equArr == 0) {
						alert("请选择要巡检的设备!");
						return false;
					}
				}
			</script>
			<style type="text/css">
				.checkTd {
					width: 40px;
					text-align: center;
				}
				.equ {
					text-align: center;
					width: 160px;
				}
				.inputStyle {
					width: 130px;
					text-align: center;
				}
			</style>
			<template:titile value="添加机房设备巡检任务" />
			<html:form action="MobileTaskAction.do?method=addPollingTaskForFSO"
				styleId="addTaskStepTwoFormId">
				<template:formTable namewidth="200" contentwidth="570" th1="项目"
					th2="内容">
					<template:formTr name="类型">
						<logic:equal value="1" name="taskbean" property="machinetype">
							核心层
						</logic:equal>
						<logic:equal value="2" name="taskbean" property="machinetype">
							接入层SDH
						</logic:equal>
						<logic:equal value="3" name="taskbean" property="machinetype">
							接入层微波
						</logic:equal>
						<logic:equal value="4" name="taskbean" property="machinetype">
							接入层FSO
						</logic:equal>
						<logic:equal value="5" name="taskbean" property="machinetype">
							光交维护
						</logic:equal>
						<input type="hidden"
							value="<bean:write name="taskbean" property="machinetype"/>"
							name="machinetype">
					</template:formTr>

					<template:formTr name="主题">
						<bean:write name="taskbean" property="title" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="title"/>"
							name="title">
					</template:formTr>

					<template:formTr name="代维公司">
						<bean:write name="taskbean" property="conname" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="contractorid"/>"
							name="contractorid">
					</template:formTr>

					<template:formTr name="执行人">
						<bean:write name="taskbean" property="userconname" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="userid"/>"
							name="userid">
					</template:formTr>

					<template:formTr name="执行日期">
						<bean:write name="taskbean" property="executetime" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="executetime"/>"
							name="executetime">
					</template:formTr>

					<template:formTr name="核查人">
						<bean:write name="taskbean" property="checkusername" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="checkuser"/>"
							name="checkuser">
					</template:formTr>

					<template:formTr name="制定人">
						<bean:write name="makePeopleName" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="makepeopleid"/>"
							name="makepeopleid">
					</template:formTr>

					<template:formTr name="备注信息">
						<bean:write name="taskbean" property="remark" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="remark"/>"
							name="remark">
					</template:formTr>

				</template:formTable>
				
				<table align="center" width="770" cellpadding="0" cellspacing="1" class="tabout">
					<tr class="trcolor">
						<td class="checkTd">选择</td>
						<td class="equ">选择A端设备</td>
						<td class="equ">选择B端设备</td>
						<td class="inputStyle">填写设备型号</td>
						<td class="inputStyle">填写机身号</td>
						<td class="inputStyle">填写设备/电源类型</td>
					</tr>
				</table>
				
				<table align="center" cellpadding="3" cellspacing="1">
					<tr>
						<td>
							<input type="checkbox" id="sel" onclick="checkOrCancel(this)" />
							全选/反选
							<input type="hidden" value="0" id="maxSeq">
						</td>
						<td>
							<input type="button" value="增加巡检设备" class="button"
								onclick="addRowForFSO()" />
						</td>
						<td>
							<input type="button" value="删除选中" class="button" onclick="delCheck(1)"/>
						</td>
						<td>
							<input type="submit" value="提交" class="button" onclick="return checkInfo()"/>
						</td>
						<td>
							<input type="reset" value="重置" class="button" />
						</td>
					</tr>
				</table>
			</html:form>
		</logic:equal>
		
		
		
		<logic:equal value="4" name="flag" scope="request">
			<script type="text/javascript">
			function addAllForCoreAndSDH() {//为核心层或SDH层增加设备巡检信息
			
				var tableBody = document.getElementsByTagName("tbody")[0];
				for(var i=0; i<infoArr.length; i++) {
				
					var newTr = document.createElement("tr");
					newTr.id = i;
					newTr.className = "trcolor";
				
					var checkTd = document.createElement("td");//创建一个TD存放CHECKBOX
					checkTd.innerHTML = "<input type='checkbox' name='ifCheck' checked='checked' value='" + i + "'>";
					checkTd.className = "tdulleft";
					checkTd.id = i;
					var selTd = document.createElement("td");//创建一个TD存放下拉列表
					selTd.className = "tdulright";
					selTd.id=i;
					selTd.innerHTML= infoArr[i][1]+ "<input type='hidden' value='"+infoArr[i][0]+"' name='selEqu_"+i+"'>";
					newTr.appendChild(checkTd);//将CHECKTD放入TR中
					newTr.appendChild(selTd);//将SELTD放入TR中
					tableBody.appendChild(newTr);
				}
			}
			
			//改变时判断是否已被选过
			function onSelChange() {
				var trId = this.id;
				var thisValue = document.getElementById(trId).value;
				var subCheck = document.getElementsByName('ifCheck');
				for(var i=0;i<subCheck.length;i++) {
					var selId = "selEqu_"+subCheck[i].value;
					if(selId != trId) {
						if(document.getElementById(selId).value == document.getElementById(trId).value) {
							alert("请不要选择同一个设备");
							document.getElementById(trId).value = "0";
							document.getElementById(trId).focus();
							return;
						}
					}
				}
			}
			
			function checkInfo() {
				var subCheck = document.getElementsByName('ifCheck');
				var checked = new Array();
				if(subCheck.length == 0) {
					alert("请增加要巡检的设备!");
					return false;
				}
				for(var i=0;i<subCheck.length;i++) {
					if(subCheck[i].checked) {
						checked[checked.length] = "selEqu_"+subCheck[i].value;
					}
				}
				if(checked.length == 0) {
					alert("请选择需要被巡检的设备");
					return false;
				}
				for(var i=0; i<checked.length; i++) {
					if(document.getElementById(checked[i]).value == "0") {
						alert("请选择需要巡检的设备");
						document.getElementById(checked[i]).focus();
						return false;
					}
				}
			}
			</script>
			<template:titile value="添加机房设备巡检任务" />
			<html:form action="MobileTaskAction.do?method=addTaskStepForSDHAndCore"
				styleId="addTaskStepTwoFormId">
				<template:formTable namewidth="150" contentwidth="350" th1="项目"
					th2="内容">
					<template:formTr name="类型">
						<logic:equal value="1" name="taskbean" property="machinetype">
							核心层
						</logic:equal>
						<logic:equal value="2" name="taskbean" property="machinetype">
							接入层SDH
						</logic:equal>
						<logic:equal value="3" name="taskbean" property="machinetype">
							接入层微波
						</logic:equal>
						<logic:equal value="4" name="taskbean" property="machinetype">
							接入层FSO
						</logic:equal>

						<logic:equal value="5" name="taskbean" property="machinetype">
							光交维护
						</logic:equal>
						<input type="hidden"
							value="<bean:write name="taskbean" property="machinetype"/>"
							name="machinetype">
					</template:formTr>

					<template:formTr name="主题">
						<bean:write name="taskbean" property="title" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="title"/>"
							name="title">
					</template:formTr>

					<template:formTr name="代维公司">
						<bean:write name="taskbean" property="conname" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="contractorid"/>"
							name="contractorid">
					</template:formTr>

					<template:formTr name="执行人">
						<bean:write name="taskbean" property="userconname" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="userid"/>"
							name="userid">
					</template:formTr>

					<template:formTr name="执行日期">
						<bean:write name="taskbean" property="executetime" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="executetime"/>"
							name="executetime">
					</template:formTr>

					<template:formTr name="核查人">
						<bean:write name="taskbean" property="checkusername" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="checkuser"/>"
							name="checkuser">
					</template:formTr>

					<template:formTr name="制定人">
						<bean:write name="makePeopleName" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="makepeopleid"/>"
							name="makepeopleid">
					</template:formTr>

					<template:formTr name="备注信息">
						<bean:write name="taskbean" property="remark" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="remark"/>"
							name="remark">
					</template:formTr>
					
					<template:formTr name="添加巡检设备">
					</template:formTr>

				</template:formTable>

					<div id="mechineList" style="height:100%;overflow-x:hidden;overflow-y:auto;">
							<table>
								<tbody></tbody>
							</table>
							<script language="javascript">
								addAllForCoreAndSDH();
							</script>
					</div>
					
				<table align="center" cellpadding="3" cellspacing="1">
					<tr>
						<td>
							<input type="checkbox" id="sel" onclick="checkOrCancel(this)" />
							全选/反选
							<input type="hidden" value="0" id="maxSeq">
						</td>
						<td>
							<input type="submit" value="提交" class="button" onclick="return checkInfo()"/>
						</td>
						<td>
							<input type="reset" value="重置" class="button" />
						</td>
					</tr>
				</table>
			</html:form>
		</logic:equal>
	</body>
</html>
