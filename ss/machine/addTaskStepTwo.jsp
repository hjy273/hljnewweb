<!-- ָ������ĵڶ��� -->
<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.machine.beans.MobileTaskBean;"%>
<html>
	<head>
		<title></title>
		<script type="text/javascript">
				var equArr = new Array();//һ�е���Ϣ
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
						alert("����û������ҪѲ����豸!");
						return;
					}
					for(i = 0; i < delChecks.length; i++) {
						if(delChecks[i].checked) {
							delRows[delid++] = document.getElementById(delChecks[i].value)
						}
					}
					if(delid == 0 ) {
						alert("��ѡ��Ҫɾ����Ѳ���豸!");
						return;
					}
					if(delid != 0) {
						if(confirm("ȷʵҪɾ����")) {
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
			function addRowForCoreAndSDH() {//Ϊ���Ĳ��SDH������һ���豸Ѳ����Ϣ
				var maxSeqEle = document.getElementById("maxSeq");//��ȡ������
				var maxSeq = parseInt(maxSeqEle.value);
				maxSeq++;
				//if(index >= infoArr.length) {
					//alert("Ѳ���豸��ȫ������");
					//return;
				//}
				//index++;
				maxSeqEle.value = maxSeq;//�����������ֵ
				var tableBody = document.getElementsByTagName("tbody")[0];
				var newTr = document.createElement("tr");
				newTr.id = maxSeq;
				newTr.className = "trcolor";
				
				var checkTd = document.createElement("td");//����һ��TD���CHECKBOX
				checkTd.innerHTML = "<input type='checkbox' name='ifCheck' checked='checked' value='" + maxSeq + "'>";
				checkTd.className = "tdulleft";
				
				var selTd = document.createElement("td");//����һ��TD��������б�
				selTd.className = "tdulright";
				
				var selEle = document.createElement("select");//����һ��SEL
				selEle.name = 'selEqu_' + maxSeq;//����SEL��name
				selEle.id = 'selEqu_' + maxSeq;//����SEL��ID
				selEle.className = "inputtext";
				selEle.style.width = "250px";
				selEle.onchange = onSelChange;
				selEle.options.add(new Option("��ѡ��ҪѲ����豸","0"));
        		for(var i=0; i<infoArr.length; i++) {
        			selEle.options.add(new Option(infoArr[i][1],infoArr[i][0]));
        		}
				selTd.appendChild(selEle);//��sel����TD��
				newTr.appendChild(checkTd);//��CHECKTD����TR��
				newTr.appendChild(selTd);//��SELTD����TR��
				tableBody.appendChild(newTr);
			}
			
			function addAllForCoreAndSDH() {//Ϊ���Ĳ��SDH�������豸Ѳ����Ϣ
			
				var tableBody = document.getElementsByTagName("tbody")[0];
				for(var i=0; i<infoArr.length; i++) {
				
					var newTr = document.createElement("tr");
					newTr.id = i;
					newTr.className = "trcolor";
				
					var checkTd = document.createElement("td");//����һ��TD���CHECKBOX
					checkTd.innerHTML = "<input type='checkbox' name='ifCheck' checked='checked' value='" + i + "'>";
					checkTd.className = "tdulleft";
					checkTd.id = i;
					var selTd = document.createElement("td");//����һ��TD��������б�
					selTd.className = "tdulright";
					selTd.id=i;
					selTd.innerHTML= infoArr[i][1]+ "<input type='hidden' value='"+infoArr[i][0]+"' name='selEqu_"+i+"'>";
					newTr.appendChild(checkTd);//��CHECKTD����TR��
					newTr.appendChild(selTd);//��SELTD����TR��
					tableBody.appendChild(newTr);
				}
			}
			
			//�ı�ʱ�ж��Ƿ��ѱ�ѡ��
			function onSelChange() {
				var trId = this.id;
				var thisValue = document.getElementById(trId).value;
				var subCheck = document.getElementsByName('ifCheck');
				for(var i=0;i<subCheck.length;i++) {
					var selId = "selEqu_"+subCheck[i].value;
					if(selId != trId) {
						if(document.getElementById(selId).value == document.getElementById(trId).value) {
							alert("�벻Ҫѡ��ͬһ���豸");
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
					alert("������ҪѲ����豸!");
					return false;
				}
				for(var i=0;i<subCheck.length;i++) {
					if(subCheck[i].checked) {
						checked[checked.length] = "selEqu_"+subCheck[i].value;
					}
				}
				if(checked.length == 0) {
					alert("��ѡ����Ҫ��Ѳ����豸");
					return false;
				}
				for(var i=0; i<checked.length; i++) {
					if(document.getElementById(checked[i]).value == "0") {
						alert("��ѡ����ҪѲ����豸");
						document.getElementById(checked[i]).focus();
						return false;
					}
				}
			}
			</script>
			<template:titile value="��ӻ����豸Ѳ������" />
			<html:form action="MobileTaskAction.do?method=addTaskStepForSDHAndCore"
				styleId="addTaskStepTwoFormId">
				<template:formTable namewidth="150" contentwidth="350" th1="��Ŀ"
					th2="����">
					<template:formTr name="����">
						<logic:equal value="1" name="taskbean" property="machinetype">
							���Ĳ�
						</logic:equal>
						<logic:equal value="2" name="taskbean" property="machinetype">
							�����SDH
						</logic:equal>
						<logic:equal value="3" name="taskbean" property="machinetype">
							�����΢��
						</logic:equal>
						<logic:equal value="4" name="taskbean" property="machinetype">
							�����FSO
						</logic:equal>

						<logic:equal value="5" name="taskbean" property="machinetype">
							�⽻ά��
						</logic:equal>
						<input type="hidden"
							value="<bean:write name="taskbean" property="machinetype"/>"
							name="machinetype">
					</template:formTr>

					<template:formTr name="����">
						<bean:write name="taskbean" property="title" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="title"/>"
							name="title">
					</template:formTr>

					<template:formTr name="��ά��˾">
						<bean:write name="taskbean" property="conname" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="contractorid"/>"
							name="contractorid">
					</template:formTr>

					<template:formTr name="ִ����">
						<bean:write name="taskbean" property="userconname" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="userid"/>"
							name="userid">
					</template:formTr>

					<template:formTr name="ִ������">
						<bean:write name="taskbean" property="executetime" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="executetime"/>"
							name="executetime">
					</template:formTr>

					<template:formTr name="�˲���">
						<bean:write name="taskbean" property="checkusername" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="checkuser"/>"
							name="checkuser">
					</template:formTr>

					<template:formTr name="�ƶ���">
						<bean:write name="makePeopleName" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="makepeopleid"/>"
							name="makepeopleid">
					</template:formTr>

					<template:formTr name="��ע��Ϣ">
						<bean:write name="taskbean" property="remark" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="remark"/>"
							name="remark">
					</template:formTr>
					
					<template:formTr name="���Ѳ���豸">
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
							ȫѡ/ȫ��ѡ
							<input type="hidden" value="0" id="maxSeq">
						</td>
						<td>
							<input type="submit" value="�ύ" class="button" onclick="return checkInfo()"/>
						</td>
						<td>
							<input type="reset" value="����" class="button" />
						</td>
					</tr>
				</table>
			</html:form>
		</logic:equal>










		<logic:equal value="2" name="flag" scope="request">
			<script type="text/javascript">
			
				function addRowForMicro() {
					var maxSeqEle = document.getElementById("maxSeq");//��ȡ������
					var maxSeq = parseInt(maxSeqEle.value);
					maxSeq++;
					maxSeqEle.value = maxSeq;//�����������ֵ
					var tableBody = document.getElementsByTagName("tbody")[1];
					var newTr = document.createElement("tr");
					newTr.id = maxSeq;
					newTr.className = "trcolor";
					
					var checkTd = document.createElement("td");//����һ��TD���CHECKBOX
					checkTd.innerHTML = "<input type='checkbox' name='ifCheck' checked='checked' value='" + maxSeq + "'>";
					
					var selTdA = document.createElement("td");//����һ��TD��������б�
					selTdA.className = "a";
					var selEleA = document.createElement("select");//����һ��SEL
					selEleA.name = 'selEquA_' + maxSeq;//����SEL��name
					selEleA.id = 'selEquA_' + maxSeq;//����SEL��ID
					selEleA.className = "inputtext";
					selEleA.style.width = "250px";
					selEleA.onchange = onSelChange;
					selEleA.options.add(new Option("��ѡ��ҪѲ���A���豸","0"));
	        		for(var i=0; i<infoArr.length; i++) {
	        			selEleA.options.add(new Option(infoArr[i][1],infoArr[i][0]));
	        		}
					selTdA.appendChild(selEleA);//��sel����TD��
					
					var selTdB = document.createElement("td");//����һ��TD��������б�
					selTdB.className = "b";
					var selEleB = document.createElement("select");//����һ��SEL
					selEleB.name = 'selEquB_' + maxSeq;//����SEL��name
					selEleB.id = 'selEquB_' + maxSeq;//����SEL��ID
					selEleB.className = "inputtext";
					selEleB.style.width = "250px";
					selEleB.onchange = onSelChange;
					selEleB.options.add(new Option("��ѡ��ҪѲ���B���豸","0"));
	        		
	        		for(var i=0; i<infoArr.length; i++) {
	        			selEleB.options.add(new Option(infoArr[i][1],infoArr[i][0]));
	        		}
					selTdB.appendChild(selEleB);//��sel����TD��
					
					newTr.appendChild(checkTd);//��CHECKTD����TR��
					newTr.appendChild(selTdA);//��SELTD����TR��
					newTr.appendChild(selTdB);//��SELTD����TR��
					
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
								alert("�벻Ҫѡ��ͬһ���豸");
								return;
							}
						}
						if(selBId != thisId) {
							if(document.getElementById(selBId).value == thisvalue) {
								document.getElementById(thisId).value = "0";
								document.getElementById(thisId).focus();
								alert("�벻Ҫѡ��ͬһ���豸");
								return;
							}
						}
					}
				}
				
				function checkInfo() {
					var subCheck = document.getElementsByName('ifCheck');
					var equArr = 0;
					if(subCheck.length == 0) {
						alert("������ҪѲ����豸!");
						return false;
					}
					for(var i=0;i<subCheck.length;i++) {
						if(subCheck[i].checked) {
							equArr++;
							if(document.getElementById("selEquA_"+subCheck[i].value).value == "0") {
								alert("��ѡ��ҪѲ����豸");
								document.getElementById("selEquA_"+subCheck[i].value).focus();
								return false;
							}
							if(document.getElementById("selEquB_"+subCheck[i].value).value == "0") {
								alert("��ѡ��ҪѲ����豸");
								document.getElementById("selEquB_"+subCheck[i].value).focus();
								return false;
							}
						}
					}
					if(equArr == 0) {
						alert("��ѡ��ҪѲ����豸!");
						return false;
					}
				}
				
			</script>
			<template:titile value="��ӻ����豸Ѳ������" />
			<html:form action="MobileTaskAction.do?method=addTaskStepForMicro"
				styleId="addTaskStepTwoFormId">
				<template:formTable namewidth="180" contentwidth="400" th1="��Ŀ"
					th2="����">
					<template:formTr name="����">
						<logic:equal value="1" name="taskbean" property="machinetype">
							���Ĳ�
						</logic:equal>
						<logic:equal value="2" name="taskbean" property="machinetype">
							�����SDH
						</logic:equal>
						<logic:equal value="3" name="taskbean" property="machinetype">
							�����΢��
						</logic:equal>
						<logic:equal value="4" name="taskbean" property="machinetype">
							�����FSO
						</logic:equal>
						<logic:equal value="5" name="taskbean" property="machinetype">
							�⽻ά��
						</logic:equal>
						<input type="hidden"
							value="<bean:write name="taskbean" property="machinetype"/>"
							name="machinetype">
					</template:formTr>

					<template:formTr name="����">
						<bean:write name="taskbean" property="title" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="title"/>"
							name="title">
					</template:formTr>

					<template:formTr name="��ά��˾">
						<bean:write name="taskbean" property="conname" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="contractorid"/>"
							name="contractorid">
					</template:formTr>

					<template:formTr name="ִ����">
						<bean:write name="taskbean" property="userconname" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="userid"/>"
							name="userid">
					</template:formTr>

					<template:formTr name="ִ������">
						<bean:write name="taskbean" property="executetime" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="executetime"/>"
							name="executetime">
					</template:formTr>

					<template:formTr name="�˲���">
						<bean:write name="taskbean" property="checkusername" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="checkuser"/>"
							name="checkuser">
					</template:formTr>

					<template:formTr name="�ƶ���">
						<bean:write name="makePeopleName" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="makepeopleid"/>"
							name="makepeopleid">
					</template:formTr>

					<template:formTr name="��ע��Ϣ">
						<bean:write name="taskbean" property="remark" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="remark"/>"
							name="remark">
					</template:formTr>

				</template:formTable>
				
				<table align="center" width="580" cellpadding="0" cellspacing="1" class="tabout">
					<tr class="trcolor">
						<td class="checkTd">ѡ��</td>
						<td class="a">ѡ��A���豸</td>
						<td class="b">ѡ��B���豸</td>
					</tr>
				</table>
			
				<table align="center" cellpadding="3" cellspacing="1">
					<tr>
						<td>
							<input type="checkbox" id="sel" onclick="checkOrCancel(this)" />
							ȫѡ/��ѡ
							<input type="hidden" value="0" id="maxSeq">
						</td>
						<td>
							<input type="button" value="����Ѳ���豸" class="button"
								onclick="addRowForMicro()" />
						</td>
						<td>
							<input type="button" value="ɾ��ѡ��" class="button" onclick="delCheck(1)"/>
						</td>
						<td>
							<input type="submit" value="�ύ" class="button" onclick="return checkInfo()"/>
						</td>
						<td>
							<input type="reset" value="����" class="button" />
						</td>
					</tr>
				</table>
			</html:form>
		</logic:equal>









		<logic:equal value="3" name="flag" scope="request">
			<script type="text/javascript">
				function addRowForFSO() {
					var maxSeqEle = document.getElementById("maxSeq");//��ȡ������
					var maxSeq = parseInt(maxSeqEle.value);
					maxSeq++;
					maxSeqEle.value = maxSeq;//�����������ֵ
					var tableBody = document.getElementsByTagName("tbody")[1];
					var newTr = document.createElement("tr");
					newTr.id = maxSeq;
					newTr.className = "trcolor";
					
					var checkTd = document.createElement("td");//����һ��TD���CHECKBOX
					checkTd.innerHTML = "<input type='checkbox' name='ifCheck' checked='checked' value='" + maxSeq + "'>";
					
					var selTdA = document.createElement("td");//����һ��TD��������б�
					selTdA.className = "equ";
					var selEleA = document.createElement("select");//����һ��SEL
					selEleA.name = 'selEquA_' + maxSeq;//����SEL��name
					selEleA.id = 'selEquA_' + maxSeq;//����SEL��ID
					selEleA.onchange = onSelChange;
					selEleA.className = "inputtext";
					selEleA.style.width = "160px";
					selEleA.options.add(new Option("��ѡ��ҪѲ���A���豸","0"));
	        		for(var i=0; i<infoArr.length; i++) {
	        			selEleA.options.add(new Option(infoArr[i][1],infoArr[i][0]));
	        		}
					selTdA.appendChild(selEleA);//��sel����TD��
					
					var selTdB = document.createElement("td");//����һ��TD��������б�
					selTdB.className = "equ";
					var selEleB = document.createElement("select");//����һ��SEL
					selEleB.name = 'selEquB_' + maxSeq;//����SEL��name
					selEleB.id = 'selEquB_' + maxSeq;//����SEL��ID
					selEleB.onchange = onSelChange;
					selEleB.className = "inputtext";
					selEleB.style.width = "160px";
					selEleB.options.add(new Option("��ѡ��ҪѲ���B���豸","0"));
	        		for(var i=0; i<infoArr.length; i++) {
	        			selEleB.options.add(new Option(infoArr[i][1],infoArr[i][0]));
	        		}
					selTdB.appendChild(selEleB);//��sel����TD��
					
					var modelTd = document.createElement("td");//����һ��TD����ı������
					modelTd.className = "inputStyle";
					var modelText = document.createElement("input");
					modelText.name = "model_" + maxSeq;
					modelText.id = "model_" + maxSeq;
					modelText.className = "inputtext";
					modelText.maxLength = "20";
					modelText.style.width = "130px";
					modelTd.appendChild(modelText);
					
					var noTd = document.createElement("td");//����һ��TD����ı������
					noTd.className = "inputStyle";
					var noText = document.createElement("input");
					noText.name = "no_" + maxSeq;
					noText.id = "no_" + maxSeq;
					noText.className = "inputtext";
					noText.maxLength = "20";
					noText.style.width = "130px";
					noTd.appendChild(noText);
				
					var powerTd = document.createElement("td");//����һ��TD����ı������
					powerTd.className = "inputStyle";
					var powerText = document.createElement("input");
					powerText.name = "power_" + maxSeq;
					powerText.id = "power_" + maxSeq;
					powerText.className = "inputtext";
					powerText.maxLength = "20";
					powerText.style.width = "130px";
					powerTd.appendChild(powerText);
				
					newTr.appendChild(checkTd);//��CHECKTD����TR��
					newTr.appendChild(selTdA);//��SELTDA����TR��
					newTr.appendChild(selTdB);//��SELTDB����TR��
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
								alert("�벻Ҫѡ��ͬһ���豸");
								return;
							}
						}
						if(selBId != thisId) {
							if(document.getElementById(selBId).value == thisvalue) {
								document.getElementById(thisId).value = "0";
								document.getElementById(thisId).focus();
								alert("�벻Ҫѡ��ͬһ���豸");
								return;
							}
						}
					}
				}
				
				
				function checkInfo() {
					var subCheck = document.getElementsByName('ifCheck');
					var equArr = 0;
					if(subCheck.length == 0) {
						alert("������ҪѲ����豸!");
						return false;
					}
					for(var i=0;i<subCheck.length;i++) {
						if(subCheck[i].checked) {
							equArr++;
							if(document.getElementById("selEquA_"+subCheck[i].value).value == 0) {
								alert("��ѡ��ҪѲ����豸");
								document.getElementById("selEquA_"+subCheck[i].value).focus();
								return false;
							}
							if(document.getElementById("selEquB_"+subCheck[i].value).value == 0) {
								alert("��ѡ��ҪѲ����豸");
								document.getElementById("selEquB_"+subCheck[i].value).focus();
								return false;
							}
							if(document.getElementById("model_"+subCheck[i].value).value.length == 0) {
								alert("�������豸�ͺ�!");
								document.getElementById("model_"+subCheck[i].value).focus();
								return false;
							}
							if(document.getElementById("no_"+subCheck[i].value).value.length == 0) {
								alert("����������!");
								document.getElementById("no_"+subCheck[i].value).focus();
								return false;
							}
							if(document.getElementById("power_"+subCheck[i].value).value.length == 0) {
								alert("�������豸/��Դ����!");
								document.getElementById("power_"+subCheck[i].value).focus();
								return false;
							}
						}
					}
					if(equArr == 0) {
						alert("��ѡ��ҪѲ����豸!");
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
			<template:titile value="��ӻ����豸Ѳ������" />
			<html:form action="MobileTaskAction.do?method=addPollingTaskForFSO"
				styleId="addTaskStepTwoFormId">
				<template:formTable namewidth="200" contentwidth="570" th1="��Ŀ"
					th2="����">
					<template:formTr name="����">
						<logic:equal value="1" name="taskbean" property="machinetype">
							���Ĳ�
						</logic:equal>
						<logic:equal value="2" name="taskbean" property="machinetype">
							�����SDH
						</logic:equal>
						<logic:equal value="3" name="taskbean" property="machinetype">
							�����΢��
						</logic:equal>
						<logic:equal value="4" name="taskbean" property="machinetype">
							�����FSO
						</logic:equal>
						<logic:equal value="5" name="taskbean" property="machinetype">
							�⽻ά��
						</logic:equal>
						<input type="hidden"
							value="<bean:write name="taskbean" property="machinetype"/>"
							name="machinetype">
					</template:formTr>

					<template:formTr name="����">
						<bean:write name="taskbean" property="title" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="title"/>"
							name="title">
					</template:formTr>

					<template:formTr name="��ά��˾">
						<bean:write name="taskbean" property="conname" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="contractorid"/>"
							name="contractorid">
					</template:formTr>

					<template:formTr name="ִ����">
						<bean:write name="taskbean" property="userconname" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="userid"/>"
							name="userid">
					</template:formTr>

					<template:formTr name="ִ������">
						<bean:write name="taskbean" property="executetime" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="executetime"/>"
							name="executetime">
					</template:formTr>

					<template:formTr name="�˲���">
						<bean:write name="taskbean" property="checkusername" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="checkuser"/>"
							name="checkuser">
					</template:formTr>

					<template:formTr name="�ƶ���">
						<bean:write name="makePeopleName" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="makepeopleid"/>"
							name="makepeopleid">
					</template:formTr>

					<template:formTr name="��ע��Ϣ">
						<bean:write name="taskbean" property="remark" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="remark"/>"
							name="remark">
					</template:formTr>

				</template:formTable>
				
				<table align="center" width="770" cellpadding="0" cellspacing="1" class="tabout">
					<tr class="trcolor">
						<td class="checkTd">ѡ��</td>
						<td class="equ">ѡ��A���豸</td>
						<td class="equ">ѡ��B���豸</td>
						<td class="inputStyle">��д�豸�ͺ�</td>
						<td class="inputStyle">��д�����</td>
						<td class="inputStyle">��д�豸/��Դ����</td>
					</tr>
				</table>
				
				<table align="center" cellpadding="3" cellspacing="1">
					<tr>
						<td>
							<input type="checkbox" id="sel" onclick="checkOrCancel(this)" />
							ȫѡ/��ѡ
							<input type="hidden" value="0" id="maxSeq">
						</td>
						<td>
							<input type="button" value="����Ѳ���豸" class="button"
								onclick="addRowForFSO()" />
						</td>
						<td>
							<input type="button" value="ɾ��ѡ��" class="button" onclick="delCheck(1)"/>
						</td>
						<td>
							<input type="submit" value="�ύ" class="button" onclick="return checkInfo()"/>
						</td>
						<td>
							<input type="reset" value="����" class="button" />
						</td>
					</tr>
				</table>
			</html:form>
		</logic:equal>
		
		
		
		<logic:equal value="4" name="flag" scope="request">
			<script type="text/javascript">
			function addAllForCoreAndSDH() {//Ϊ���Ĳ��SDH�������豸Ѳ����Ϣ
			
				var tableBody = document.getElementsByTagName("tbody")[0];
				for(var i=0; i<infoArr.length; i++) {
				
					var newTr = document.createElement("tr");
					newTr.id = i;
					newTr.className = "trcolor";
				
					var checkTd = document.createElement("td");//����һ��TD���CHECKBOX
					checkTd.innerHTML = "<input type='checkbox' name='ifCheck' checked='checked' value='" + i + "'>";
					checkTd.className = "tdulleft";
					checkTd.id = i;
					var selTd = document.createElement("td");//����һ��TD��������б�
					selTd.className = "tdulright";
					selTd.id=i;
					selTd.innerHTML= infoArr[i][1]+ "<input type='hidden' value='"+infoArr[i][0]+"' name='selEqu_"+i+"'>";
					newTr.appendChild(checkTd);//��CHECKTD����TR��
					newTr.appendChild(selTd);//��SELTD����TR��
					tableBody.appendChild(newTr);
				}
			}
			
			//�ı�ʱ�ж��Ƿ��ѱ�ѡ��
			function onSelChange() {
				var trId = this.id;
				var thisValue = document.getElementById(trId).value;
				var subCheck = document.getElementsByName('ifCheck');
				for(var i=0;i<subCheck.length;i++) {
					var selId = "selEqu_"+subCheck[i].value;
					if(selId != trId) {
						if(document.getElementById(selId).value == document.getElementById(trId).value) {
							alert("�벻Ҫѡ��ͬһ���豸");
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
					alert("������ҪѲ����豸!");
					return false;
				}
				for(var i=0;i<subCheck.length;i++) {
					if(subCheck[i].checked) {
						checked[checked.length] = "selEqu_"+subCheck[i].value;
					}
				}
				if(checked.length == 0) {
					alert("��ѡ����Ҫ��Ѳ����豸");
					return false;
				}
				for(var i=0; i<checked.length; i++) {
					if(document.getElementById(checked[i]).value == "0") {
						alert("��ѡ����ҪѲ����豸");
						document.getElementById(checked[i]).focus();
						return false;
					}
				}
			}
			</script>
			<template:titile value="��ӻ����豸Ѳ������" />
			<html:form action="MobileTaskAction.do?method=addTaskStepForSDHAndCore"
				styleId="addTaskStepTwoFormId">
				<template:formTable namewidth="150" contentwidth="350" th1="��Ŀ"
					th2="����">
					<template:formTr name="����">
						<logic:equal value="1" name="taskbean" property="machinetype">
							���Ĳ�
						</logic:equal>
						<logic:equal value="2" name="taskbean" property="machinetype">
							�����SDH
						</logic:equal>
						<logic:equal value="3" name="taskbean" property="machinetype">
							�����΢��
						</logic:equal>
						<logic:equal value="4" name="taskbean" property="machinetype">
							�����FSO
						</logic:equal>

						<logic:equal value="5" name="taskbean" property="machinetype">
							�⽻ά��
						</logic:equal>
						<input type="hidden"
							value="<bean:write name="taskbean" property="machinetype"/>"
							name="machinetype">
					</template:formTr>

					<template:formTr name="����">
						<bean:write name="taskbean" property="title" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="title"/>"
							name="title">
					</template:formTr>

					<template:formTr name="��ά��˾">
						<bean:write name="taskbean" property="conname" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="contractorid"/>"
							name="contractorid">
					</template:formTr>

					<template:formTr name="ִ����">
						<bean:write name="taskbean" property="userconname" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="userid"/>"
							name="userid">
					</template:formTr>

					<template:formTr name="ִ������">
						<bean:write name="taskbean" property="executetime" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="executetime"/>"
							name="executetime">
					</template:formTr>

					<template:formTr name="�˲���">
						<bean:write name="taskbean" property="checkusername" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="checkuser"/>"
							name="checkuser">
					</template:formTr>

					<template:formTr name="�ƶ���">
						<bean:write name="makePeopleName" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="makepeopleid"/>"
							name="makepeopleid">
					</template:formTr>

					<template:formTr name="��ע��Ϣ">
						<bean:write name="taskbean" property="remark" />

						<input type="hidden"
							value="<bean:write name="taskbean" property="remark"/>"
							name="remark">
					</template:formTr>
					
					<template:formTr name="���Ѳ���豸">
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
							ȫѡ/��ѡ
							<input type="hidden" value="0" id="maxSeq">
						</td>
						<td>
							<input type="submit" value="�ύ" class="button" onclick="return checkInfo()"/>
						</td>
						<td>
							<input type="reset" value="����" class="button" />
						</td>
					</tr>
				</table>
			</html:form>
		</logic:equal>
	</body>
</html>
