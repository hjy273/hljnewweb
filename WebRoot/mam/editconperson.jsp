<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<jsp:useBean id="selectForTag"
	class="com.cabletech.commons.tags.SelectForTag" scope="request" />
<script language="javascript" src="${ctx}/js/prototype.js" type=""></script>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<%
    String condition = "";
    UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
    //���ƶ�
    if (userinfo.getDeptype().equals("1")
            && !userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE regionid IN ('" + userinfo.getRegionID()
                + "') AND state IS NULL";
    }
    //�д�ά
    if (userinfo.getDeptype().equals("2")
            && !userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE state IS NULL  and contractorid='" + userinfo.getDeptID()
                + "' ";
    }
    //ʡ�ƶ�
    if (userinfo.getDeptype().equals("1")
            && userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE state IS NULL";
    }
    //ʡ��ά
    if (userinfo.getDeptype().equals("2")
            && userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE contractorid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
                + userinfo.getDeptID() + "')";
    }
    List deptCollection = selectForTag.getSelectForTag("contractorname", "contractorid",
            "contractorinfo", condition);
    request.setAttribute("deptCollection", deptCollection);
%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
		var regx=/^\d{11}$/;
		var regxIdCard=/^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;
		function valCharLength(Value){
			var j=0;
			var s = Value;
			for(var i=0; i<s.length; i++)
			{
				if   (s.substr(i,1).charCodeAt(0)>255)   j   =   j   +   2;
				else   j++;
			}
  			return j;
		}

    	function valiSub(){
        	if(conPersonBean.employeeNum.value=="" || conPersonBean.employeeNum.value==null||conPersonBean.employeeNum.value.trim()=="" || conPersonBean.employeeNum.value.trim()==null){
            	alert("Ա����Ų���Ϊ�ջ��߿ո�");
            	return false;
        	}
        	if(conPersonBean.name.value=="" || conPersonBean.name.value==null||conPersonBean.name.value.trim()=="" || conPersonBean.name.value.trim()==null){
            	alert("��������Ϊ�ջ��߿ո�");
            	return false;
        	}
        	if(conPersonBean.identitycard.value=="" || conPersonBean.identitycard.value==null||conPersonBean.identitycard.value.trim()=="" || conPersonBean.identitycard.value.trim()==null){
            	alert("���֤���벻��Ϊ�ջ��߿ո�");
            	return false;
        	}
        	if(!regxIdCard.test(conPersonBean.identitycard.value)){
        		alert("���֤�������Ϊ15λ��18λ���ֻ���17λ����+x��");
        		return false;
        	}
        	if(conPersonBean.technicalTitle.value=="" || conPersonBean.technicalTitle.value==null||conPersonBean.technicalTitle.value.trim()=="" || conPersonBean.technicalTitle.value.trim()==null){
            	alert("ְ�Ʋ���Ϊ�ջ��߿ո�");
            	return false;
        	}
        	if(conPersonBean.enterTime.value=="" || conPersonBean.enterTime.value==null||conPersonBean.enterTime.value.trim()=="" || conPersonBean.enterTime.value.trim()==null){
            	alert("��ְʱ�䲻��Ϊ�ջ��߿ո�");
            	return false;
        	}
        	if(conPersonBean.residantarea.value=="" || conPersonBean.residantarea.value==null||conPersonBean.residantarea.value.trim()=="" || conPersonBean.residantarea.value.trim()==null){
            	alert("��פ������Ϊ�ջ��߿ո�");
            	return false;
        	}
        	if(conPersonBean.jobinfo.value=="" || conPersonBean.jobinfo.value==null||conPersonBean.jobinfo.value.trim()=="" || conPersonBean.jobinfo.value.trim()==null){
            	alert("ְ����Ϊ�ջ��߿ո�");
            	return false;
        	}
        	if(conPersonBean.postOffice.value=="" || conPersonBean.postOffice.value==null||conPersonBean.postOffice.value.trim()=="" || conPersonBean.postOffice.value.trim()==null){
            	alert("��Ҫ����ְ����Ϊ�ջ��߿ո�");
            	return false;
        	}
        	if(conPersonBean.mobile.value=="" || conPersonBean.mobile.value==null||conPersonBean.mobile.value.trim()=="" || conPersonBean.mobile.value.trim()==null){
            	alert("�ֻ����벻��Ϊ�ջ��߿ո�");
            	return false;
        	}
        	if(!regx.test(conPersonBean.mobile.value)){
        		alert("�ֻ��������Ϊ11λ���֣�");
        		return false;
        	}
        	if(conPersonBean.phone.value=="" || conPersonBean.phone.value==null||conPersonBean.phone.value.trim()=="" || conPersonBean.phone.value.trim()==null){
            	alert("�̶��绰���벻��Ϊ�ջ��߿ո�");
            	return false;
        	}
        	if(conPersonBean.email.value=="" || conPersonBean.email.value==null||conPersonBean.email.value.trim()=="" || conPersonBean.email.value.trim()==null){
            	alert("E-mail���䲻��Ϊ�ջ��߿ո�");
            	return false;
        	}
        	if(conPersonBean.familyaddress.value=="" || conPersonBean.familyaddress.value==null||conPersonBean.familyaddress.value.trim()=="" || conPersonBean.familyaddress.value.trim()==null){
            	alert("��ͥסַ����Ϊ�ջ��߿ո�");
            	return false;
        	}
        	if(conPersonBean.graduateDate.value=="" || conPersonBean.graduateDate.value==null||conPersonBean.graduateDate.value.trim()=="" || conPersonBean.graduateDate.value.trim()==null){
            	alert("���ѧ�����ʱ�䲻��Ϊ�ջ��߿ո�");
            	return false;
        	}
        	if(conPersonBean.academy.value=="" || conPersonBean.academy.value==null||conPersonBean.academy.value.trim()=="" || conPersonBean.academy.value.trim()==null){
            	alert("��ҵԺУ����Ϊ�ջ��߿ո�");
            	return false;
        	}
        	if(conPersonBean.speciality.value=="" || conPersonBean.speciality.value==null||conPersonBean.speciality.value.trim()=="" || conPersonBean.speciality.value.trim()==null){
            	alert("��ѧרҵ����Ϊ�ջ��߿ո�");
            	return false;
        	}
        	if(conPersonBean.workrecord.value=="" || conPersonBean.workrecord.value==null||conPersonBean.workrecord.value.trim()=="" || conPersonBean.workrecord.value.trim()==null){
            	alert("������������Ϊ�ջ��߿ո�");
            	return false;
        	}        	
        	if(valCharLength(conPersonBean.name.value)>20){
          		alert("�������ܴ���10�������ַ���");
          		return false;
        	}
        	if(valCharLength(conPersonBean.postOffice.value)>2048){
          		alert("��Ҫ����ְ���ܴ���1024�������ַ���");
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
        	if(conPersonBean.familyaddress.value=="" || conPersonBean.familyaddress.value==null||conPersonBean.familyaddress.value.trim()=="" || conPersonBean.familyaddress.value.trim()==null){
            	alert("��ͥסַ����Ϊ�ջ��߿ո�");
            	return false;
        	}
        	if(judgeExist()){
        		alert("�ô�ά��λ���Ѿ�������ͬԱ����ŵĵ�λ��Ա��");
        		return false;
        	}
       		conPersonBean.submit();
    	}
    	judgeExist=function(){
			var flag=false;
			var employeeNum=conPersonBean.employeeNum.value;
			var contractorid=conPersonBean.contractorid.value;
			var queryString="employeeNum="+employeeNum+"&contractorid="+contractorid+"&id=<bean:write name='personinfo' property='id' />&rnd="+Math.random();
			var actionUrl="${ctx}/ConPersonAction.do?method=judgePersonExist&&"+queryString;
			new Ajax.Request(actionUrl, 
				{
					method:"post", 
					onSuccess:function (transport) {
						if(transport.responseText=='1'){
							flag=true;
						}
					}, 
					asynchronous:false
				}
			);
			return flag;
		}
		addGoBack=function(){
			location="${ctx}/ConPersonAction.do?method=showConPerson";
		}
    </script>
		<title>�޸���Ա��Ϣ</title>
	</head>
	<body>
		<logic:present name="personinfo">
			<template:titile value="�޸���Ա������Ϣ" />
			<html:form action="/ConPersonAction?method=upPerson"
				styleId="addtoosbaseFormId" enctype="multipart/form-data">
				<table width="720" border="0" align="center" cellpadding="3"
					cellspacing="0" class="tabout">
					<!-- ������Ϣ -->
					<tr class="trwhite">
						<td class="tdulleft" style="width: 15%">
							Ա����ţ�
						</td>
						<td class="tdulright" style="width: 35%">
							<input type="hidden" name="id"
								value="<bean:write name="personinfo" property="id"/>" />
							<input type="hidden" name="regionid"
								value="<bean:write name="personinfo" property="regionid"/>" />
							<input type="hidden" name="contractorid"
								value="<bean:write name="personinfo" property="contractorid" />" />
							<html:text name="personinfo" property="employeeNum"
								styleClass="inputtext" style="width:160" maxlength="15" />
							<font color="#FF0000">*</font>
						</td>
						<td class="tdulleft" style="width: 15%" rowspan="4">
							��Ƭ��
						</td>
						<td class="tdulright" style="width: 35%" rowspan="4">
							<bean:define name="personinfo" property="id" id="conperson_id" />
							<apptag:upload state="edit" entityId="${conperson_id}"
								entityType="CONTRACTORPERSON"></apptag:upload>
						</td>
					</tr>
					<tr class="trwhite">
						<td class="tdulleft" style="width: 15%">
							������
						</td>
						<td class="tdulright" style="width: 35%">
							<html:text name="personinfo" property="name"
								styleClass="inputtext" style="width:160" maxlength="10" />
							<font color="#FF0000">*</font>
						</td>
					</tr>
					<tr class="trwhite">
						<td class="tdulleft" style="width: 15%">
							�Ա�
						</td>
						<td class="tdulright" style="width: 35%">
							<logic:equal value="��" property="sex" name="personinfo">
								<html:select property="sex" name="personinfo"
									styleClass="inputtext" style="width:160;">
									<html:option value="��">��</html:option>
									<html:option value="Ů">Ů</html:option>
								</html:select>
							</logic:equal>
							<logic:equal value="Ů" property="sex" name="personinfo">
								<html:select property="sex" name="personinfo"
									styleClass="inputtext" style="width:160;">
									<option value="��">
										��
									</option>
									<option value="Ů" selected="selected">
										Ů
									</option>
								</html:select>
							</logic:equal>
							<font color="#FF0000">*</font>
						</td>
					</tr>
					<tr class="trwhite">
						<td class="tdulleft" style="width: 15%">
							���ڴ�ά��λ��
						</td>
						<td class="tdulright" style="width: 35%">
							<html:select name="personinfo" property="contractorid"
								styleClass="inputtext" style="width:160">
								<html:options collection="deptCollection" property="value"
									labelProperty="label" />
							</html:select>
							<font color="#FF0000">*</font>
						</td>
					</tr>
					<tr class="trwhite">
						<td class="tdulleft" style="width: 15%">
							���壺
						</td>
						<td class="tdulright" style="width: 35%">
							<html:text name="personinfo" property="nation"
								styleClass="inputtext" style="width:160" maxlength="10" />
						</td>
						<td class="tdulleft" style="width: 15%">
							���֤���룺
						</td>
						<td class="tdulright" style="width: 35%">
							<html:text name="personinfo" property="identitycard"
								styleClass="inputtext" style="width:160;" maxlength="18" />
							<font color="#FF0000">*</font>
						</td>
					</tr>
					<tr class="trwhite">
						<td class="tdulleft" style="width: 15%">
							ְ�ƣ�
						</td>
						<td class="tdulright" style="width: 35%">
							<html:text name="personinfo" property="technicalTitle"
								styleClass="inputtext" style="width:160" maxlength="25" />
							<font color="#FF0000">*</font>
						</td>
						<td class="tdulleft" style="width: 15%">
							��ְʱ�䣺
						</td>
						<td class="tdulright" style="width: 35%">
							<html:text name="personinfo" property="enterTime"
								styleClass="inputtext" readonly="true" style="width:160"
								onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate:'%y-%M-%d'})" />
							<font color="#FF0000">*</font>
						</td>
					</tr>
					<tr class="trwhite">
						<td class="tdulleft" style="width: 15%">
							��פ����
						</td>
						<td class="tdulright" style="width: 35%">
							<html:text name="personinfo" property="residantarea"
								styleClass="inputtext" style="width:160;" maxlength="50" />
							<font color="#FF0000">*</font>
						</td>
						<td class="tdulleft" style="width: 15%">
							ְ��
						</td>
						<td class="tdulright" style="width: 35%">
							<html:text name="personinfo" property="jobinfo"
								styleClass="inputtext" style="width:160;" maxlength="50" />
							<font color="#FF0000">*</font>
						</td>
					</tr>
					<tr class="trwhite">
						<td class="tdulleft" style="width: 15%">
							��Ҫ����ְ��
						</td>
						<td class="tdulright" style="width: 85%" colspan="3">
							<html:textarea name="personinfo" property="postOffice"
								styleClass="inputtext" style="width:160" rows="3" />
							<font color="#FF0000">*</font>
						</td>
					</tr>
					<tr class="trwhite">
						<td class="tdulleft" style="width: 15%">
							�ֻ����룺
						</td>
						<td class="tdulright" style="width: 35%">
							<html:text name="personinfo" property="mobile"
								styleClass="inputtext" style="width:160;" maxlength="11" />
							<font color="#FF0000">*</font>
						</td>
						<td class="tdulleft" style="width: 15%">
							�������룺
						</td>
						<td class="tdulright" style="width: 35%">
							<html:text name="personinfo" property="postalcode"
								styleClass="inputtext" style="width:160;" maxlength="6" />
						</td>
					</tr>
					<tr class="trwhite">
						<td class="tdulleft" style="width: 15%">
							�̶��绰���룺
						</td>
						<td class="tdulright" style="width: 35%">
							<html:text name="personinfo" property="phone"
								styleClass="inputtext" style="width:160;" maxlength="12" />
							<font color="#FF0000">*</font>
						</td>
						<td class="tdulleft" style="width: 15%">
							E-mail���䣺
						</td>
						<td class="tdulright" style="width: 35%">
							<html:text name="personinfo" property="email"
								styleClass="inputtext" style="width:160" maxlength="50" />
							<font color="#FF0000">*</font>
						</td>
					</tr>
					<tr class="trwhite">
						<td class="tdulleft" style="width: 15%">
							��ͥסַ��
						</td>
						<td class="tdulright" style="width: 85%" colspan="3">
							<html:text name="personinfo" property="familyaddress"
								styleClass="inputtext" style="width:160;" maxlength="40" />
							<font color="#FF0000">*</font>
						</td>
					</tr>
					<tr class="trwhite">
						<td class="tdulleft" style="width: 15%">
							�Ļ��̶ȣ�
						</td>
						<td class="tdulright" style="width: 35%">
							<logic:equal value="����/��ר" property="culture" name="personinfo">
								<html:select property="culture" name="personinfo"
									styleClass="inputtext" style="width:160;">
									<html:option value="����/��ר">����/��ר</html:option>
									<html:option value="��ר">��ר</html:option>
									<html:option value="����/��������">����/��������</html:option>
								</html:select>
							</logic:equal>
							<logic:equal value="��ר" property="culture" name="personinfo">
								<html:select property="culture" name="personinfo"
									styleClass="inputtext" style="width:160;">
									<option value="����/��ר">
										����/��ר
									</option>
									<option value="��ר" selected="selected">
										��ר
									</option>
									<option value="����/��������">
										����/��������
									</option>
								</html:select>
							</logic:equal>
							<logic:equal value="����/��������" property="culture" name="personinfo">
								<html:select property="culture" name="personinfo"
									styleClass="inputtext" style="width:160;">
									<option value="����/��ר">
										����/��ר
									</option>
									<option value="��ר">
										��ר
									</option>
									<option value="����/��������" selected="selected">
										����/��������
									</option>
								</html:select>
							</logic:equal>
							<font color="#FF0000">*</font>
						</td>
						<td class="tdulleft" style="width: 15%">
							���ѧ�����ʱ�䣺
						</td>
						<td class="tdulright" style="width: 35%">
							<html:text name="personinfo" property="graduateDate"
								styleClass="inputtext" readonly="true" style="width:160"
								onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate:'%y-%M-%d'})" />
							<font color="#FF0000">*</font>
						</td>
					</tr>
					<tr class="trwhite">
						<td class="tdulleft" style="width: 15%">
							��ҵԺУ��
						</td>
						<td class="tdulright" style="width: 35%">
							<html:text name="personinfo" property="academy"
								styleClass="inputtext" style="width:160" maxlength="40" />
							<font color="#FF0000">*</font>
						</td>
						<td class="tdulleft" style="width: 15%">
							��ѧרҵ��
						</td>
						<td class="tdulright" style="width: 35%">
							<html:text name="personinfo" property="speciality"
								styleClass="inputtext" style="width:160" maxlength="40" />
							<font color="#FF0000">*</font>
						</td>
					</tr>
					<tr class="trwhite">
						<td class="tdulleft" style="width: 15%">
							�ڸڷ�
						</td>
						<td class="tdulright" style="width: 85%" colspan="3">
							<logic:equal value="0" property="conditions" name="personinfo">
								<html:select property="conditions" name="personinfo">
									<option value="0" selected="selected">
										�ڸ�
									</option>
									<option value="1">
										��ְ
									</option>
								</html:select>
							</logic:equal>
							<logic:equal value="1" property="conditions" name="personinfo">
								<html:select property="conditions" name="personinfo">
									<option value="0">
										�ڸ�
									</option>
									<option value="1" selected="selected">
										��ְ
									</option>
								</html:select>
							</logic:equal>
							<font color="#FF0000">*</font>
						</td>
						<!-- 
				<td class="tdulleft" style="width:15%">
					��ְʱ�䣺
				</td>
				<td class="tdulright" style="width:35%">
					
				</td>
				 -->
					</tr>
					<tr class="trwhite">
						<td class="tdulleft" style="width: 15%">
							����������
						</td>
						<td class="tdulright" style="width: 85%" colspan="3">
							<textarea cols="" rows="2" name="workrecord" style="width: 280;"
								class="textarea"><bean:write name="personinfo" property="workrecord" /></textarea>
							<font color="#FF0000">*</font>
						</td>
					</tr>
					<tr class="trwhite">
						<td class="tdulleft" style="width: 15%">
							��ע��
						</td>
						<td class="tdulright" style="width: 85%" colspan="3">
							<textarea cols="" rows="2" name="remark" style="width: 280;"
								class="textarea"><bean:write name="personinfo" property="remark" /></textarea>
						</td>
					</tr>
					<template:formSubmit>
						<td>
							<html:button property="action" styleClass="button"
								onclick="valiSub()">�ύ</html:button>
						</td>
						<td>
							<html:reset property="action" styleClass="button">ȡ��</html:reset>
						</td>
						<td>
							<html:button property="action" styleClass="button"
								onclick="addGoBack()">����	</html:button>
						</td>
					</template:formSubmit>
				</table>
			</html:form>
		</logic:present>
	</body>
</html>


