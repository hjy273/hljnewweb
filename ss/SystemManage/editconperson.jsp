<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<html>
	<head>
		<script type="" language="javascript">
		function addGoBack()
        {
            try{
                location.href = "${ctx}/ConPersonAction.do?method=showConPerson";
                return true;
            }
            catch(e){
                alert(e);
            }
        }
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

    	function valiSub(){
        	if(conPersonBean.name.value=="" || conPersonBean.name.value==null){
            	alert("��������Ϊ�գ�");
            	return false;
        	}
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
			if(conPersonBean.mobile.value=="" || conPersonBean.mobile.value==null){
            	alert("�ֻ����벻��Ϊ�գ�");
            	return false;
        	}
        	if(conPersonBean.enterTime.value=="" || conPersonBean.enterTime.value==null){
            	alert("��ְʱ�䲻��Ϊ�գ�");
            	return false;
        	}
			if(new Date(conPersonBean.enterTime.value)-new Date(conPersonBean.leaveTime.value)>0){
				alert("��ְʱ�䲻��������ְʱ��");
				return false;
			}
       		conPersonBean.submit();
    	}
    </script>
	<title>�޸���Ա��Ϣ</title>
	</head>
	<body>
		<logic:present name="personinfo">
			<template:titile value="�޸���Ա������Ϣ" />
			<html:form action="/ConPersonAction?method=upPerson"
				styleId="conPersonBean">
				<template:formTable>
					<template:formTr name="��������">
						<input type="text" name="region" Class="inputtext"
							readonly="readonly" style="width: 180px;" maxlength="50"
							value="<%=session.getAttribute("LOGIN_USER_REGION_NAME")%>" />
					</template:formTr>
					<template:formTr name="�ϼ���λ">
						<input type="hidden" name="regionid"
							value="<bean:write name="personinfo" property="regionid"/>" />
						<input type="hidden" name="id"
							value="<bean:write name="personinfo" property="id"/>" />
						<input type="hidden" name="contractorid"
							value="<bean:write name="personinfo" property="contractorid"/>" />
						<input type="text" name="deptname" readonly="readonly"
							value="<bean:write name="personinfo" property="contractorname"/>"
							Class="inputtext" style="width: 180px;" maxlength="6" />
					</template:formTr>
					<template:formTr name="����">
						<html:text property="name" name="personinfo"
							styleClass="inputtext" style="width:180px;" maxlength="20" />&nbsp;&nbsp;<font color="red">*</font>
					</template:formTr>
					<template:formTr name="�Ա�">
							<html:select property="sex" name="personinfo" styleClass="inputtext" style="width:180px;">
								<html:option value="��">��</html:option>
								<html:option value="Ů">Ů</html:option>
							</html:select>
					</template:formTr>
					<template:formTr name="�Ļ��̶�">
							<html:select property="culture" name="personinfo" styleClass="inputtext" style="width:180px;">
								<html:option value="����/��ר">����/��ר</html:option>
								<html:option value="��ר">��ר</html:option>
								<html:option value="����/��������">����/��������</html:option>
							</html:select>
					</template:formTr>
					<!--<template:formTr name="����״��">
						<logic:equal value="δ��" property="ismarriage" name="personinfo">
							<html:select property="ismarriage" name="personinfo"
								styleClass="inputtext" style="width:180px;">
								<option value="δ��">δ��</option>
								<option value="�ѻ�">�ѻ�</option>
							</html:select>
						</logic:equal>
						<logic:equal value="�ѻ�" property="ismarriage" name="personinfo">
							<html:select property="ismarriage" name="personinfo" styleClass="inputtext" style="width:180px;">
								<option value="δ��">δ��</option>
								<option value="�ѻ�" selected="selected">�ѻ�</option>
							</html:select>
						</logic:equal>
					</template:formTr>
					<template:formTr name="�Ƿ���Ч">
						<logic:equal value="��Ч" property="state" name="personinfo">
							<html:select property="state" styleClass="inputtext"
								style="width:180px">
								<option value="��Ч">��Ч</option>
								<option value="��Ч">��Ч</option>
							</html:select>
						</logic:equal>
						<logic:equal value="��Ч" property="state" name="personinfo">
							<html:select property="state" styleClass="inputtext"
								style="width:180px">
								<option value="��Ч">��Ч</option>
								<option value="��Ч" selected="selected">��Ч</option>
							</html:select>
						</logic:equal>
					</template:formTr>
					<template:formTr name="�Ƿ�����Ա">
						<logic:equal value="������Ա" property="persontype" name="personinfo">
							<html:select property="persontype" styleClass="inputtext"
								style="width:180px">
								<option value="������Ա">������Ա</option>
								<option value="�ǹ�����Ա">�ǹ�����Ա</option>
							</html:select>
						</logic:equal>
						<logic:equal value="�ǹ�����Ա" property="persontype" name="personinfo">
							<html:select property="persontype" styleClass="inputtext"
								style="width:160">
								<option value="������Ա">������Ա</option>
								<option value="�ǹ�����Ա" selected="selected">�ǹ�����Ա</option>
							</html:select>
						</logic:equal>
					</template:formTr>-->
					
					<template:formTr name="�̶��绰">
						<html:text property="phone" name="personinfo" styleClass="inputtext" style="width:180px;" maxlength="12" />
					</template:formTr>
					<template:formTr name="�ֻ�����">
						<html:text property="mobile" name="personinfo" styleId="mobile"
							styleClass="inputtext" style="width:180px;" maxlength="11" />&nbsp;&nbsp;<font color="red">*</font>
					</template:formTr>
					<template:formTr name="��ͥסַ">
						<html:text property="familyaddress" name="personinfo"
							styleClass="inputtext" style="width:180px;" maxlength="18" />
					</template:formTr>
					<template:formTr name="��������">
						<html:text property="postalcode" name="personinfo"
							styleClass="inputtext" style="width:180px;" maxlength="6" />
					</template:formTr>
					<template:formTr name="ְ��">
						<html:text property="jobinfo" name="personinfo" styleClass="inputtext" style="width:180px;" maxlength="50" />
					</template:formTr>
					<template:formTr name="���֤">
						<html:text property="identitycard" name="personinfo" styleClass="inputtext" style="width:180px;" maxlength="50" />
					</template:formTr>
					<template:formTr name="�Ƿ���ܶ���">
						<html:radio property="issendsms" name="personinfo" value="1">��</html:radio>
						<html:radio property="issendsms" name="personinfo" value="2">��</html:radio>
					</template:formTr>
					<template:formTr name="��פ����">
						<html:text property="residantarea" name="personinfo" styleClass="inputtext" style="width:180px;" maxlength="50" />
					</template:formTr>
					<template:formTr name="��ְʱ��">
						<input name="enterTime" class="Wdate" id="enterTime" style="width:180px"
								onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" value="<bean:write name="personinfo" property="enterTime" format="yyyy/MM/dd"/>" readonly/><font color="red"> *</font>
					</template:formTr>
					<template:formTr name="��ְʱ��">
						<input name="leaveTime" class="Wdate" id="leaveTime" style="width:180px"
								onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" value="<bean:write name="personinfo" property="leaveTime" format="yyyy/MM/dd"/>" readonly/></font>
					</template:formTr>
					<template:formTr name="��λְ��">
						<textarea cols="" rows="2" name="postOffice" style="width: 280px;"
							class="textarea"><bean:write name="personinfo" property="postOffice" /></textarea>
					</template:formTr>	
					<template:formTr name="��������">
						<textarea cols="" rows="2" name="workrecord" style="width: 280px;" class="textarea"><bean:write name="personinfo" property="workrecord" /></textarea>
					</template:formTr>
					<template:formTr name="��ע��Ϣ">
						<textarea cols="" rows="2" name="remark" style="width: 280px;" class="textarea"><bean:write name="personinfo" property="remark" /></textarea>
					</template:formTr>
					<template:formSubmit>
						<td>
							<html:button property="action" styleClass="button" styleId="action" onclick="valiSub()">�ύ</html:button>
						</td>
						<td>
							<html:reset property="action" styleClass="button">ȡ��</html:reset>
						</td>
						<td>
							<html:button property="action" styleClass="button" onclick="addGoBack()">����</html:button>
						</td>
					</template:formSubmit>
				</template:formTable>
			</html:form>
		</logic:present>
		<template:cssTable></template:cssTable>
	</body>
</html>


