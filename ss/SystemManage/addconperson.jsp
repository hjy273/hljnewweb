<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>


<html>
	<head>
		<script type="text/javascript" language="javascript">
		function valCharLength(Value){
			var j=0;
			var s = Value;
			for(var i=0; i<s.length; i++)
			{
				if   (s.substr(i,1).charCodeAt(0)>255)   j   =   j   +   2;
				else   j++
			}
  			return j;
		}

		String.prototype.Trim = function() {  
			var m = this.match(/^\s*(\S+(\s+\S+)*)\s*$/);  
			return (m == null) ? "" : m[1];  
		} 
		//�ֻ�������֤ 
		String.prototype.isMobile = function() {  
			return (/^(?:13\d|15[0123456789])-?\d{5}(\d{3}|\*{3})$/.test(this.Trim()));  
		}  

    	function valiSub(){
        	if(conPersonBean.name.value==""){
            	alert("��������Ϊ�գ�");
            	return false;
        	}
        	
        	/**
			if(valCharLength(conPersonBean.name.value)>10){
          		alert("�������ܴ���5�������ַ���");
          		return false;
        	}
        	if(valCharLength(conPersonBean.workrecord.value)>2048){
          		alert("�����������ܴ���1024�������ַ���");
          		return false;
        	}
        	if(valCharLength(conPersonBean.workrecord.value)>200){
          		alert("��ע��Ϣ���ܴ���100�������ַ���");
          		return false;
        	}
        	if(conPersonBean.familyaddress.value=="" || conPersonBean.familyaddress.value==null){
            	alert("��ͥסַ����Ϊ�գ�");
            	return false;
        	}
        	if(trim(conPersonBean.familyaddress.value)=="" || trim(conPersonBean.familyaddress.value)==null){
            	alert("��ͥסַ����Ϊ�ո�");
            	return false;
        	}**/
        	if(document.conPersonBean.mobile.value==""){
        		alert("�ֻ����벻��Ϊ�գ�");
        		return false;
        	}
        	if(!document.getElementById("mobile").value.isMobile()){
        		alert("��������ȷ���ֻ����룡");
        		return;
        	}
        	if(document.conPersonBean.enterTime.value==""){
        		alert("��ְʱ�䲻��Ϊ�գ�");
        		return false;
        	}
       		conPersonBean.submit();
    	}
    </script>

	<title>�����Ա��Ϣ</title>
	</head>
	<body>
		<template:titile value="�����Ա������Ϣ" />
		<html:form action="/ConPersonAction?method=addConPerson"
			styleId="addtoosbaseFormId">
			<template:formTable>
				<template:formTr name="��������">
					<input type="text" name="region" Class="inputtext"
						readonly="readonly" style="width: 180px;" maxlength="50"
						value="<%=session.getAttribute("LOGIN_USER_REGION_NAME")%>" />
				</template:formTr>
				<template:formTr name="��ά��λ">
					<input type="hidden" name="regionid"
						value="<bean:write name="regionid"/>" />
					<input type="hidden" name="contractorid"
						value="<bean:write name="deptid"/>" />
					<input type="text" name="deptname" readonly="readonly"
						value="<bean:write name="deptname"/>" Class="inputtext"
						style="width: 180px;" maxlength="6" />
				</template:formTr>
				<template:formTr name="����">
					<html:text property="name" styleClass="inputtext"
						style="width:180px;" maxlength="20" /><font color="red"> *</font>
				</template:formTr>
				<template:formTr name="�Ա�">
					<html:select property="sex" styleClass="inputtext"
						style="width:180px">
						<html:option value="��">��</html:option>
						<html:option value="Ů">Ů</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="�Ļ��̶�">
					<html:select property="culture" styleClass="inputtext"
						style="width:180px">
						<html:option value="����/��ר">����/��ר</html:option>
						<html:option value="��ר">��ר</html:option>
						<html:option value="����/��������">����/��������</html:option>
					</html:select>
				</template:formTr>
				<!--<template:formTr name="����״��">
					<html:select property="ismarriage" styleClass="inputtext"
						style="width:180px">
						<html:option value="δ��">δ��</html:option>
						<html:option value="�ѻ�">�ѻ�</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="�Ƿ���Ч">
					<html:select property="state" styleClass="inputtext"
						style="width:180px">
						<html:option value="��Ч">��Ч</html:option>
						<html:option value="��Ч">��Ч</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="�Ƿ�����Ա">
					<html:select property="persontype" styleClass="inputtext"
						style="width:180px">
						<html:option value="������Ա">������Ա</html:option>
						<html:option value="�ǹ�����Ա">�ǹ�����Ա</html:option>
					</html:select>
				</template:formTr>-->
				<template:formTr name="�̶��绰">
					<html:text property="phone" styleClass="inputtext"
						style="width:180px;" maxlength="12" />
				</template:formTr>
				<template:formTr name="�ֻ�����">
					<html:text property="mobile" styleClass="inputtext" styleId="mobile"
						style="width:180px;" maxlength="13" /><font color="red"> *</font>
				</template:formTr>
				<template:formTr name="��ͥסַ">
					<html:text property="familyaddress" styleClass="inputtext"
						style="width:180px;" maxlength="18" />
				</template:formTr>
				<template:formTr name="��������">
					<html:text property="postalcode" styleClass="inputtext"
						style="width:180px;" maxlength="6" />
				</template:formTr>
				<template:formTr name="ְ��">
					<html:text property="jobinfo" styleClass="inputtext"
						style="width:180px;" maxlength="50" />
				</template:formTr>
				<template:formTr name="���֤">
					<html:text property="identitycard" styleClass="inputtext"
						style="width:180px;" maxlength="50" />
				</template:formTr>
				<template:formTr name="�Ƿ���ܶ���">
					<html:radio property="issendsms" value="1">��</html:radio>
					<html:radio property="issendsms" value="2">��</html:radio>
				</template:formTr>
				<template:formTr name="��פ����">
					<html:text property="residantarea" styleClass="inputtext"
						style="width:180px;" maxlength="50" />
				</template:formTr>
				<template:formTr name="��ְʱ��">
					<input name="enterTime" class="Wdate" id="enterTime" style="width:180px"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" readonly/><font color="red"> *</font>
				</template:formTr>
				<template:formTr name="��λְ��">
					<textarea cols="" rows="2" name="postOffice" style="width: 280px;"
						class="textarea"></textarea>
				</template:formTr>
				
				<template:formTr name="��������">
					<textarea cols="" rows="2" name="workrecord" style="width: 280px;"
						class="textarea"></textarea>
				</template:formTr>
				<template:formTr name="��ע��Ϣ">
					<textarea cols="" rows="2" name="remark" style="width: 280px;"
						class="textarea"></textarea>
				</template:formTr>

				<template:formSubmit>
					<td>
						<html:button property="action" styleClass="button"
							onclick="valiSub()">�ύ</html:button>
					</td>
					<td>
						<html:reset property="action" styleClass="button">ȡ��</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
		<template:cssTable></template:cssTable>
	</body>
</html>


