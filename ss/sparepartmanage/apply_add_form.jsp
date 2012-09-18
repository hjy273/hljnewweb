<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript">
	var maxStorageNum="<bean:write name="spare_part_storage" property="storageNumber" />";
	checkValid=function(addForm){
		if(addForm.sparePartId.value==""){
			alert("��ⱸ������Ϊ�գ�");
			addForm.sparePartId.focus();
			return false;
		}
		if(addForm.serialNumber.value==""){
			alert("�������кŲ���Ϊ�գ�");
			addForm.serialNumber.focus();
			return false;
		}
		if(addForm.useNumber.value==""){
			alert("������������Ϊ�գ�");
			addForm.useNumber.focus();
			return false;
		}
		if(addForm.useMode.value=="02"){
			if(typeof(addForm.usedSparePartId.value)=="undefined"||addForm.usedSparePartId.value==""){
				alert("�������������Ʋ���Ϊ�գ�");
				//addForm.usedSparePartId.focus();
				return false;
			}
			if(typeof(addForm.usedSerialNumber.value)=="undefined"||addForm.usedSerialNumber.value==""){
				alert("�������������кŲ���Ϊ�գ�");
				//addForm.usedSerialNumber.focus();
				return false;
			}
			var index=addForm.usedSparePartId.options.selectedIndex;
			var maxUpdateNum=parseFloat(addForm.usedSparePartId.options[index].usednumber);
			if(parseFloat(addForm.useNumber.value)>maxUpdateNum){
				alert("û���㹻�ľɱ������и�����");
				addForm.useNumber.focus();
				return false;
			}
		}
		if(addForm.applyUsePosition.value==""){
			alert("����ʹ��λ�ò���Ϊ�գ�");
			addForm.applyUsePosition.focus();
			return false;
		}
		if(!valiDigit(addForm.useNumber,"��������")){
			return false;
		}
		if(parseFloat(addForm.useNumber.value)>parseFloat(maxStorageNum)){
			alert("�����������ڿ����������������д��");
			addForm.useNumber.focus();
			return false;
		}
		
		return true;
	}
	addGoBack=function(){
		var url = "${ctx}/SparepartApplyAction.do?method=listApply&storage_id=<bean:write name="spare_part_storage" property="tid" />";
		self.location.replace(url);
	}
	function addGoBackMod() {
		var url ="${ctx}/SparepartStorageAction.do?method=listSparepartStorageForRe";
		self.location.replace(url);
	}
	updateSerialNumber=function(index){
		if(typeof(addForm.usedSparePartId.options)!="undefined"&&addForm.usedSparePartId.options.length-1>=index){
			var opt=addForm.usedSparePartId.options[index];
			var text=opt.serialnumber;
			document.getElementById("serialNumTd").innerHTML="<input name='usedSerialNumber' value='"+text+"' type='hidden' />"+text;
		}
	}
	updatePosition=function(index){
		if(typeof(addForm.usedSparePartId.options)!="undefined"&&addForm.usedSparePartId.options.length-1>=index){
			var opt=addForm.usedSparePartId.options[index];
			var text=opt.usedposition;
			document.getElementById("positionTd").innerHTML="<input name='usedPosition' value='"+text+"' type='hidden' />";
		}
	}
	valiDigit=function(obj,msg){
        var mysplit = /^\d{1,6}$/;
        if(mysplit.test(obj.value)){
          return true;
        }
        else{
            alert("����д��"+msg+"��������,����������");
            obj.value="0";
            obj.focus();
            return false;
        }
    }
    displayUpdateSpan=function(value){
    	if(value=="01"){
    		document.getElementById("updateSpan1").style.display="none";
    		document.getElementById("updateSpan2").style.display="none";
    		document.getElementById("updateSpan3").style.display="none";
    	}
    	if(value=="02"){
    		document.getElementById("updateSpan1").style.display="";
    		document.getElementById("updateSpan2").style.display="";
    		document.getElementById("updateSpan3").style.display="";
    	}
    }
	</script>
	<body>
		<template:titile value="Ѳ��ά�������뱸��" />
		<html:form action="/SparepartApplyAction.do?method=addApply"
			styleId="addForm" onsubmit="return checkValid(this);">
			<template:formTable namewidth="150" contentwidth="350">
				<template:formTr name="��������">
					<input name="sparePartId"
						value="<bean:write name="spare_part_storage" property="sparePartId" />"
						type="hidden" />
					<bean:write name="spare_part_storage" property="sparePartName" />
					<input name="takenOutStorage" type="hidden"
						value="<bean:write name="spare_part_storage" property="tid" />" />
				</template:formTr>
				<template:formTr name="�������к�">
					<input name="serialNumber"
						value="<bean:write name="spare_part_storage" property="serialNumber" />"
						class="inputtext" type="hidden" style="width:250;" maxlength="25" />
					<bean:write name="spare_part_storage" property="serialNumber" />
				</template:formTr>
				<template:formTr name="���벿��">
					<input name="patrolgroupId"
						value="<%=(String) request.getAttribute("dept_id")%>"
						type="hidden" class="inputtext" style="width:250;" />
					<%=(String) request.getAttribute("dept_name")%>
				</template:formTr>
				<template:formTr name="ʹ�÷�ʽ">
					<html:select property="useMode" style="width:250;" onchange="displayUpdateSpan(this.value);">
						<html:option value="01">ֱ��ʹ��</html:option>
						<html:option value="02">����ʹ��</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="��������" tagID="updateSpan1" style="display:none;">
					<html:select property="replaceType" style="width:250;">
						<html:option value="01">�˻��ɱ���</html:option>
						<html:option value="02">���޾ɱ���</html:option>
						<html:option value="03">���Ͼɱ���</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="������������ʹ��λ��" tagID="updateSpan2" style="display:none;">
					<select name="usedSparePartId" style="width:250;"
						onchange="updateSerialNumber(this.selectedIndex);">
						<logic:present name="using_spare_part_list">
							<logic:iterate id="one_part" name="using_spare_part_list">
								<option
									serialnumber="<bean:write name="one_part"  property="serial_number"/>"
									usednumber="<bean:write name="one_part"  property="storage_number"/>"
									usedposition="<bean:write name="one_part"  property="storage_position"/>"
									value="<bean:write name="one_part"  property="spare_part_id"/>">
									<bean:write name="one_part" property="spare_part_name" />_<bean:write name="one_part" property="storage_position" />
								</option>
							</logic:iterate>
						</logic:present>
					</select>
				</template:formTr>
				<template:formTr name="�������������к�" tagID="updateSpan3" style="display:none;">
					<span id="serialNumTd"></span>
					<span id="positionTd"></span>
				</template:formTr>
				<template:formTr name="��������">
					<html:text property="useNumber" styleClass="inputtext"
						style="width:250;" maxlength="25" />
				</template:formTr>
				<template:formTr name="����ʹ��λ��">
					<html:text property="applyUsePosition" styleClass="inputtext"
						style="width:250;" maxlength="25" />
				</template:formTr>
				<template:formTr name="������">
					<input name="applyPerson" type="text"
						value="<%=(String) request.getAttribute("user_name")%>"
						class="inputtext" style="width:250;" maxlength="25" />
				</template:formTr>
				<template:formTr name="���뱸ע">
					<html:textarea property="applyRemark" styleClass="inputtextarea"
						style="width:250;" rows="5" />
				</template:formTr>

				<template:formSubmit>
					<td>
						<html:submit styleClass="button">�ύ</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">ȡ��	</html:reset>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="addGoBackMod()">����</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
		<script type="text/javascript">
		updateSerialNumber(0);updatePosition(0);
		</script>
	</body>
</html>
