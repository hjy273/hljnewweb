<!-- �ƶ�ָ������ĵ�һ�� -->
<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.machine.beans.MobileTaskBean;"%>
<html>
	<head>
		<script type="text/javascript" src="./js/prototype.js"></script>
		<script type="text/javascript">
				//��������
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
							titleInfo = setTitle(value,'���Ļ���');
							titleEle.innerHTML = titleInfo;
							titleHidden.value = titleInfo;
						break;
						case 2:
							titleInfo = setTitle(value,'�����SDH����');
							titleEle.innerHTML = titleInfo;
							titleHidden.value = titleInfo;
						break;
						case 3:
							titleInfo = setTitle(value,'�����΢������');
							titleEle.innerHTML = titleInfo;
							titleHidden.value = titleInfo;
						break;
						case 4:
							titleInfo = setTitle(value,'�����FSO����');
							titleEle.innerHTML = titleInfo;
							titleHidden.value = titleInfo;
						case 5:
							titleInfo = setTitle(value,'�⽻ά��');
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
						return info + month + '�µ�һ��ά�����';
					}
					if(d >=8 && d<=14) {
						return info + month + '�µڶ���ά�����';
					}
					if(d >=15 && d<=21) {
						return info + month + '�µ�����ά�����';
					}
					if(d >=22 && d<=28) {
						return info + month + '�µ�����ά�����';
					}
					if(d >=29 && d<=31) {
						return info + month + '�µ�����ά�����';
					}
				}
				
				//ajax
				function onConChange(obj) {
					var params = obj.value;
					var connameEle = document.getElementById('conname');
					var useridSelEle = document.getElementById('userid').options;//��ȡִ���˵�select
					var index = obj.options.selectedIndex;
					var conname = obj.options[index].text;
					connameEle.value = conname;
					useridSelEle.length = 0;//��������б�
					useridSelEle.add(new Option('===��ѡ��ִ����===','0'));
					if(params == 0) {
						return;
					}
					var url = 'MobileTaskAction.do?method=getUserByConId&conId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callback, asynchronous:true});
				}
				
				function callback(originalRequest) {
					var useridSelEle = document.getElementById('userid').options;//��ȡִ���˵�select
					var rst = originalRequest.responseText;
					var userarr = eval('('+rst+')');
					for(var i=0; i<userarr.length; i++) {
						useridSelEle.add(new Option(userarr[i].username,userarr[i].userid));
					}
				}
				
				//�����������userconname
				function setuserconname(obj) {
					var index = obj.options.selectedIndex;
					var userconname = obj.options[index].text;
					var userconnameEle = document.getElementById('userconname');
					userconnameEle.value = userconname;
				}
				
				//�����������checkusername
				function setCheckUsernaem(obj) {
					var index = obj.options.selectedIndex;
					var checkusername = obj.options[index].text;
					var checkusernameEle = document.getElementById('checkusername');
					checkusernameEle.value = checkusername;
				}
				
				//ѡ��ʱ��
				function GetSelectDateTHIS(strID) {
			    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
			    	return ;
				 }
				 
				 //������Ϣ
				 function resetInfo() {
				 	var titleEle = document.getElementById('tasktitle');
					var titleHidden = document.getElementById('title');
					titleEle.innerHTML = '';
					titleHidden.value = '';
					document.getElementById('userid').options.length = 0;
				 }
				 
				 //���������Ϣ
				 function checkAddInfo() {
				 	var machinetypeEle = document.getElementById('machinetype');
				 	if(machinetypeEle.value == 0) {
				 		alert("��ѡ������!");
				 		machinetypeEle.focus();
				 		return false;
				 	}
				 	var contractoridEle = document.getElementById('contractorid');
				 	if(contractoridEle.value == 0) {
				 		alert("��ѡ���ά��˾!");
				 		contractoridEle.focus();
				 		return false;
				 	}
				 	
				 	
					var useridEle = document.getElementById('userid');
				 	if(useridEle.value == 0) {
						alert("��ѡ��ִ����!");
				 		useridEle.focus();
				 		return false;
				 	}
				 	var executetimeEle = document.getElementById('executetime');
				 	var executetime = executetimeEle.value;
				 	if(executetime==''){
				 		alert("ִ�����ڲ���Ϊ��!");
				 		return false;
				 	}
				 	
				 	if(executetime < getNowDate()) {
				 		alert("ִ�����ڲ���С�ڵ�ǰ����!");
				 		return false;
				 	}
				 	var date = new Date();
				 	if(executetime.substr(0,4) > date.getYear()) {
				 		alert("�ƻ����ܿ���!");
				 		return false;
				 	}
				 	if(executetime.substr(5,2) > (date.getMonth() + 1) ) {
				 		alert("�ƻ����ܿ���!");
				 		return false;
				 	}
				 	
				 	
				 	var checkuserEle = document.getElementById('checkuser');
				 	if(checkuserEle.value == 0) {
				 		alert("��ѡ��˲���!");
				 		checkuserEle.focus();
				 		return false;
				 	}
				 	var remark = document.getElementById('remark');
				 	if(valCharLength(remark.value) > 500) {
				 		alert("��ע��Ϣ����ӦС��500����ĸ����250������");
				 		remark.value = '';
				 		return false;
				 	}
				 	addTaskStepOneFormId.submit();
				 }
				 
				 //��ȡ������ʱ��
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
		<template:titile value="��ӻ���Ѳ������" />
		<html:form action="MobileTaskAction.do?method=addTaskStepOne"
			styleId="addTaskStepOneFormId">
			<template:formTable namewidth="150" contentwidth="300">
				<template:formTr name="����">
					<select name="machinetype" onchange="setTaskTitle(this)"
						style="width:245px;" class="inputtext">
						<option value="0">
							===��ѡ������===
						</option>
						<option value="1">
							���Ĳ�
						</option>
						<option value="2">
							�����SDH
						</option>
						<option value="3">
							�����΢��
						</option>
						<option value="4">
							�����FSO
						</option>
						<option value="5">
							�⽻ά��
						</option>
					</select>
				</template:formTr>

				<template:formTr name="����">
					<font id="tasktitle"></font>
					<input type="hidden" name="title" id="title">
				</template:formTr>

				<template:formTr name="��ά��˾">
					<select class="inputtext" name="contractorid" style="width:245px;" id="contractorid"
						onchange="onConChange(this)">
						<option value="0">
							===��ѡ���ά===
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

				<template:formTr name="ִ����">
					<select class="inputtext" name="userid" style="width:245px;"
						id="userid" onchange="setuserconname(this)">
						<option value="0"></option>
					</select>
					
					<input type="hidden" name="userconname" id="userconname"> 
				</template:formTr>

				<template:formTr name="ִ������">
					<input id="executetime" type="text" readonly="readonly"
						name="executetime" class="inputtext" style="width: 217" />
					<INPUT TYPE='BUTTON' VALUE='����' readonly="readonly" ID='btn'
						onclick="GetSelectDateTHIS('executetime')"
						STYLE="font: 'normal small-caps 6pt serif';">
				</template:formTr>

				<template:formTr name="�˲���">
					<select class="inputtext" name="checkuser" style="width:245px;" id="checkuser" onchange="setCheckUsernaem(this)">
						<option value="0">
							===��ѡ��˲���===
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

				<template:formTr name="�ƶ���">
					<bean:write name="makePeopleName"/>
					<input type="hidden" value="<bean:write name="makePeopleId"/>" name="makepeopleid">
				</template:formTr>


				<template:formTr name="��ע��Ϣ">
					<textarea name="remark" cols="36" rows="4" id="remark"
						title="�������256�����֣�512��Ӣ����ĸ��" class="textarea"></textarea>
				</template:formTr>

			</template:formTable>

			<template:formSubmit>
				<td>
					<html:button property="action" styleClass="button"
						onclick="checkAddInfo()">�������</html:button>
				</td>
				<td>
					<html:reset property="action" styleClass="button"
						onclick="resetInfo()">����	</html:reset>
				</td>
			</template:formSubmit>

		</html:form>
	</body>
</html>
