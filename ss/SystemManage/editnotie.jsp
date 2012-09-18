<%@ include file="/common/header.jsp"%>
<script language="JavaScript" src="${ctx}/js/validate.js"></script>
<script language="javascript" type="text/javascript">
function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
function isValidForm() {
	if(document.NoticeBean.title.value ==""||document.NoticeBean.title.value.trim()==""||document.NoticeBean.title.value.trim()=="null"){
		alert("���ⲻ��Ϊ�ջ��߿ո����null!! ");
		return false;
	}
	if(document.NoticeBean.type.value=="����"){
		if(document.NoticeBean.meetTime.value==""){
			alert("���鿪ʼʱ�䲻��Ϊ�գ�");
			return false;
		}
		if(document.NoticeBean.meetEndTime.value==""){
			alert("�������ʱ�䲻��Ϊ�գ�");
			return false;
		}
		
		document.NoticeBean.mobileIds.value=document.NoticeBean.meetMobiles.value;
		var beginDate=convertStrToDate(document.NoticeBean.meetTime.value," ","/",":");
		var endDate=convertStrToDate(document.NoticeBean.meetEndTime.value," ","/",":");
		if(compareTwoDate(beginDate,endDate)>0){
			alert("���鿪ʼʱ�䲻�ܳ��ڻ������ʱ�䣡");
			return false;
		}
		if(document.NoticeBean.meetAddress.value==""){
			alert("����ص㲻��Ϊ�գ�");
			return false;
		}
		if(document.NoticeBean.meetPerson.value==""){
			alert("�����Ա����Ϊ�գ�");
			return false;
		}
	}else if(document.NoticeBean.type.value=="����"){
		document.NoticeBean.mobileIds.value=document.NoticeBean.noticeMobiles.value;
	}
	return true;
}
function issue(){
   
	NoticeBean.isissue.value='y';
	if(isValidForm()){
		NoticeBean.submit();
	}
	
}
function save(){
	
    NoticeBean.isissue.value='n';
	if(isValidForm()){
		NoticeBean.submit();
	}
}

function changeType(value){
	if(value=="����"){
		document.getElementById("meetTimeTr").style.display="";
		document.getElementById("meetEndTimeTr").style.display="";
		document.getElementById("meetAddressTr").style.display="";
		document.getElementById("meetPersonTr").style.display="";
		document.getElementById("sendSmTbody").style.display="";
		document.getElementById("acceptSmUserTr").style.display="none";
	}else if(value=="����"){
		document.getElementById("meetTimeTr").style.display="none";
		document.getElementById("meetEndTimeTr").style.display="none";
		document.getElementById("meetAddressTr").style.display="none";
		document.getElementById("meetPersonTr").style.display="none";
		document.getElementById("sendSmTbody").style.display="";
		document.getElementById("acceptSmUserTr").style.display="";
	}else {
		document.getElementById("meetTimeTr").style.display="none";
		document.getElementById("meetEndTimeTr").style.display="none";
		document.getElementById("meetAddressTr").style.display="none";
		document.getElementById("meetPersonTr").style.display="none";
		document.getElementById("sendSmTbody").style.display="none";
		document.getElementById("acceptSmUserTr").style.display="none";
	}
}
</script>
<script type="text/javascript" src="${ctx}/js/ckeditor/ckeditor.js"></script>
<body onload="changType(document.NoticeBean.notice.value)">
<template:titile value="��Ϣ����"/>

<html:form  method="Post" action="/NoticeAction.do?method=updateNotice" enctype="multipart/form-data">
    <template:formTable>
		<template:formTr name="����" isOdd="false">
            <html:text property="title" name="notice" styleClass="inputtext" style="width:500px" maxlength="25"/>&nbsp;&nbsp;<font color="red">*</font>
        </template:formTr>

		<template:formTr name="����" isOdd="false">
 			<input name="mobileIds" type="hidden" value="" />
        	<html:select property="type" name="notice" styleClass="inputtext"  style="width:150px" onchange="changeType(this.value);">
        		<html:option value="����">����</html:option>
        	 	<html:option value="����">����</html:option>
        	 	<html:option value="����">����</html:option>
        	</html:select>        
        </template:formTr>
		<template:formTr name="��Ҫ�̶�" isOdd="false">
        	<html:select property="grade" name="notice" styleClass="inputtext"  style="width:150px">
        		<html:option value="һ��">һ��</html:option>
        		<html:option value="��Ҫ">��Ҫ</html:option>
        	</html:select>        
        	<html:hidden property="isCanceled" name="notice" />
        	<html:hidden property="oldMeetTime" name="notice" />
        	<html:hidden property="oldMeetEndTime" name="notice" />
        	<html:hidden property="oldMeetAddress" name="notice" />
        	<html:hidden property="oldMeetPerson" name="notice" />
        </template:formTr>
		
        <template:formTr name="���鿪ʼʱ��" isOdd="true" tagID="meetTimeTr" style="display:none;">
        	<input type="text" id="protimeid" name="meetTime" value="${notice.meetTime}"
				readonly="readonly" class="Wdate" style="width: 150px" 
				onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1'})" />
        </template:formTr>
        <template:formTr name="�������ʱ��" isOdd="true" tagID="meetEndTimeTr" style="display:none;">
        	<input type="text" id="protimeid" name="meetEndTime" value="${notice.meetEndTime}"
				readonly="readonly" class="Wdate" style="width: 150px" 
				onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1'})" />
        </template:formTr>
        <template:formTr name="����ص�" isOdd="true" tagID="meetAddressTr" style="display:none;">
        	 <html:text property="meetAddress" name="notice"  styleClass="inputtext" style="width:300px" />
        </template:formTr>
        <tbody id="meetPersonTr" style="display:none;">
			<apptag:processorselect inputName="departIds,meetPerson,meetMobiles" exceptSelf="false"
				labelName="�����Ա" displayType="mobile_contractor" colSpan="1" spanId="acceptMeetUser"
				existValue="${notice.acceptUserIds}--${notice.acceptUserTels}" spanValue="${notice.acceptUserNames}"></apptag:processorselect>
		</tbody>
        <tbody id="acceptSmUserTr" style="display:none;">
			<apptag:processorselect inputName="departIds,acceptUserIds,noticeMobiles" exceptSelf="false"
				labelName="���Ž�����" displayType="mobile_contractor" colSpan="1" spanId="acceptUser"
				existValue="${notice.acceptUserIds}--${notice.acceptUserTels}" spanValue="${notice.acceptUserNames}"></apptag:processorselect>
		</tbody>
 		<tbody id="sendSmTbody" style="display:;">
			<tr class=trcolor>
				<td class="tdr">
					���ŷ��ͷ�ʽ��
				</td>
				<td class="tdl">
					<input name="sendMethod" value="0" type="radio"
						onclick="timeInputSpan.style.display='none';timeSpaceTr.style.display='none';" />
					��ʱ����
					<input name="sendMethod" value="1" type="radio"
						onclick="timeInputSpan.style.display='';timeSpaceTr.style.display='';" />
					��ʱ����
				</td>
			</tr>
			<tr class=trcolor id="timeInputSpan" style="display: none;">
				<td class="tdr">
					���ŷ���ʱ�䣺
				</td>
				<td>
					<input type="text" id="protimeid" name="beginDate" value="${notice.beginDate}"
						readonly="readonly" class="Wdate" style="width: 150px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1'})" />
					��
					<input type="text" id="protimeid" name="endDate" value="${notice.endDate}"
						readonly="readonly" class="Wdate" style="width: 150px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1'})" />
				</td>
			</tr>
			<tr class=trcolor id="timeSpaceTr" style="display: none;">
				<td class="tdr">
					���ŷ��ͼ����
				</td>
				<td class="tdl">
					<input name="sendTimeSpace" value="${notice.sendTimeSpace}" type="text"
						style="width: 100px" />
					<input name="sendIntervalType" value="-1" type="radio" />
					����
					<input name="sendIntervalType" value="0" type="radio" />
					��
					<input name="sendIntervalType" value="1" type="radio" />
					��
					<input name="sendIntervalType" value="2" type="radio" />
					ʱ
					<input name="sendIntervalType" value="3" type="radio" />
					��
					<c:if test="${notice.sendIntervalType=='-1'}">
						<script type="text/javascript">
							document.NoticeBean.sendIntervalType[0].checked=true;
						</script>
					</c:if>
					<c:if test="${notice.sendIntervalType=='0'}">
						<script type="text/javascript">
							document.NoticeBean.sendIntervalType[1].checked=true;
						</script>
					</c:if>
					<c:if test="${notice.sendIntervalType=='1'}">
						<script type="text/javascript">
							document.NoticeBean.sendIntervalType[2].checked=true;
						</script>
					</c:if>
					<c:if test="${notice.sendIntervalType=='2'}">
						<script type="text/javascript">
							document.NoticeBean.sendIntervalType[3].checked=true;
						</script>
					</c:if>
					<c:if test="${notice.sendIntervalType=='3'}">
						<script type="text/javascript">
							document.NoticeBean.sendIntervalType[4].checked=true;
						</script>
					</c:if>
				</td>
			</tr>
					<c:if test="${notice.sendMethod=='0'}">
						<script type="text/javascript">
							document.NoticeBean.sendMethod[0].checked=true;
							timeInputSpan.style.display="none";
							timeSpaceTr.style.display="none";
						</script>
					</c:if>
					<c:if test="${notice.sendMethod=='1'}">
						<script type="text/javascript">
							document.NoticeBean.sendMethod[1].checked=true;
							timeInputSpan.style.display="";
							timeSpaceTr.style.display="";
						</script>
					</c:if>
		</tbody>
        <template:formTr name="����" isOdd="false">
        	  <apptag:upload state="edit" entityId="${notice.id}" entityType="NOTICE_CLOB"></apptag:upload>
        </template:formTr>
        <template:formTr name="����">
        	<textarea cols="200px" id="editor_v2" name="contentString" rows="10">${notice.contentString}</textarea>
			<script type="text/javascript">
			//<![CDATA[
				CKEDITOR.replace( 'editor_v2',
					{
						skin : 'v2',
						toolbar : 'MyToolbar',
						filebrowserUploadUrl : '${ctx}/ckeditor/uploader?Type=File',  
						filebrowserImageUploadUrl : '${ctx}/ckeditor/uploader?Type=Image',  
						filebrowserFlashUploadUrl : '${ctx}/ckeditor/uploader?Type=Flash'
					});
			//]]>
			</script>
        </template:formTr>
		<html:hidden property="isissue" name="notice"/>
		<html:hidden property="id" name="notice"/>
		<html:hidden property="fileinfo" name="notice"/>
		 
		<template:formSubmit>
            <td >
            	<html:button property="action" styleClass="button" onclick="issue()">��������</html:button>
            </td>
            <td >
                <html:button styleClass="button"  onclick="save()" property="action">����</html:button>
            </td>
            <td >
                <html:reset styleClass="button"  >ȡ��</html:reset>
            </td>
        </template:formSubmit>


   </template:formTable>
</html:form>
<script type="text/javascript">
changeType("${notice.type}");
</script>
</body>