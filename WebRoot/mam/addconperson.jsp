<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<script language="javascript" src="${ctx}/js/prototype.js" type=""></script>
<jsp:useBean id="selectForTag"
	class="com.cabletech.commons.tags.SelectForTag" scope="request" />
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
    		//���塢�ʱࡢ��ע������֤
        	if(conPersonBean.employeeNum.value=="" || conPersonBean.employeeNum.value==null||conPersonBean.employeeNum.value.trim()=="" || conPersonBean.employeeNum.value.trim()==null){
            	alert("Ա����Ų���Ϊ�ջ��߿ո�");
            	conPersonBean.employeeNum.focus();
            	return false;
        	}
        	if(conPersonBean.name.value=="" || conPersonBean.name.value==null||conPersonBean.name.value.trim()=="" || conPersonBean.name.value.trim()==null){
            	alert("��������Ϊ�ջ��߿ո�");
            	conPersonBean.name.focus();
            	return false;
        	}
        	if(conPersonBean.identitycard.value=="" || conPersonBean.identitycard.value==null||conPersonBean.identitycard.value.trim()=="" || conPersonBean.identitycard.value.trim()==null){
            	alert("���֤���벻��Ϊ�ջ��߿ո�");
            	conPersonBean.identitycard.focus();
            	return false;
        	}
        	if(!regxIdCard.test(conPersonBean.identitycard.value)){
        		alert("���֤�������Ϊ15λ��18λ���ֻ���17λ����+x��");
        		conPersonBean.identitycard.focus();
            	return false;
        	}
        	if(conPersonBean.technicalTitle.value=="" || conPersonBean.technicalTitle.value==null||conPersonBean.technicalTitle.value.trim()=="" || conPersonBean.technicalTitle.value.trim()==null){
            	alert("ְ�Ʋ���Ϊ�ջ��߿ո�");
            	conPersonBean.technicalTitle.focus();
            	return false;
        	}
        	if(conPersonBean.enterTime.value=="" || conPersonBean.enterTime.value==null||conPersonBean.enterTime.value.trim()=="" || conPersonBean.enterTime.value.trim()==null){
            	alert("��ְʱ�䲻��Ϊ�ջ��߿ո�");
            	conPersonBean.enterTime.focus();
            	return false;
        	}
        	if(conPersonBean.residantarea.value=="" || conPersonBean.residantarea.value==null||conPersonBean.residantarea.value.trim()=="" || conPersonBean.residantarea.value.trim()==null){
            	alert("��פ������Ϊ�ջ��߿ո�");
            	conPersonBean.residantarea.focus();
            	return false;
        	}
        	if(conPersonBean.jobinfo.value=="" || conPersonBean.jobinfo.value==null||conPersonBean.jobinfo.value.trim()=="" || conPersonBean.jobinfo.value.trim()==null){
            	alert("ְ����Ϊ�ջ��߿ո�");
            	conPersonBean.jobinfo.focus();
            	return false;
        	}
        	if(conPersonBean.postOffice.value=="" || conPersonBean.postOffice.value==null||conPersonBean.postOffice.value.trim()=="" || conPersonBean.postOffice.value.trim()==null){
            	alert("��Ҫ����ְ����Ϊ�ջ��߿ո�");
            	conPersonBean.postOffice.focus();
            	return false;
        	}
        	if(conPersonBean.mobile.value=="" || conPersonBean.mobile.value==null||conPersonBean.mobile.value.trim()=="" || conPersonBean.mobile.value.trim()==null){
            	alert("�ֻ����벻��Ϊ�ջ��߿ո�");
            	conPersonBean.mobile.focus();
            	return false;
        	}
        	if(!regx.test(conPersonBean.mobile.value)){
        		alert("�ֻ��������Ϊ11λ���֣�");
        		conPersonBean.mobile.focus();
            	return false;
        	}
        	if(conPersonBean.phone.value=="" || conPersonBean.phone.value==null||conPersonBean.phone.value.trim()=="" || conPersonBean.phone.value.trim()==null){
            	alert("�̶��绰���벻��Ϊ�ջ��߿ո�");
            	conPersonBean.phone.focus();
            	return false;
        	}
        	if(conPersonBean.email.value=="" || conPersonBean.email.value==null||conPersonBean.email.value.trim()=="" || conPersonBean.email.value.trim()==null){
            	alert("E-mail���䲻��Ϊ�ջ��߿ո�");
            	conPersonBean.email.focus();
            	return false;
        	}
        	if(conPersonBean.familyaddress.value=="" || conPersonBean.familyaddress.value==null||conPersonBean.familyaddress.value.trim()=="" || conPersonBean.familyaddress.value.trim()==null){
            	alert("��ͥסַ����Ϊ�ջ��߿ո�");
            	conPersonBean.familyaddress.focus();
            	return false;
        	}
        	if(conPersonBean.graduateDate.value=="" || conPersonBean.graduateDate.value==null||conPersonBean.graduateDate.value.trim()=="" || conPersonBean.graduateDate.value.trim()==null){
            	alert("���ѧ�����ʱ�䲻��Ϊ�ջ��߿ո�");
            	conPersonBean.graduateDate.focus();
            	return false;
        	}
        	if(conPersonBean.academy.value=="" || conPersonBean.academy.value==null||conPersonBean.academy.value.trim()=="" || conPersonBean.academy.value.trim()==null){
            	alert("��ҵԺУ����Ϊ�ջ��߿ո�");
            	conPersonBean.academy.focus();
            	return false;
        	}
        	if(conPersonBean.speciality.value=="" || conPersonBean.speciality.value==null||conPersonBean.speciality.value.trim()=="" || conPersonBean.speciality.value.trim()==null){
            	alert("��ѧרҵ����Ϊ�ջ��߿ո�");
            	conPersonBean.speciality.focus();
            	return false;
        	}
        	if(conPersonBean.workrecord.value=="" || conPersonBean.workrecord.value==null||conPersonBean.workrecord.value.trim()=="" || conPersonBean.workrecord.value.trim()==null){
            	alert("������������Ϊ�ջ��߿ո�");
            	conPersonBean.workrecord.focus();
            	return false;
        	}
        	if(valCharLength(conPersonBean.name.value)>20){
          		alert("�������ܴ���10�������ַ���");
          		conPersonBean.name.focus();
            	return false;
        	}
        	if(valCharLength(conPersonBean.postOffice.value)>2048){
          		alert("��Ҫ����ְ���ܴ���1024�������ַ���");
          		conPersonBean.postOffice.focus();
            	return false;
        	}
        	if(valCharLength(conPersonBean.workrecord.value)>2048){
          		alert("�����������ܴ���1024�������ַ���");
          		conPersonBean.workrecord.focus();
            	return false;
        	}
        	if(valCharLength(conPersonBean.remark.value)>200){
          		alert("��ע��Ϣ���ܴ���100�������ַ���");
          		conPersonBean.remark.focus();
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
			var queryString="employeeNum="+employeeNum+"&contractorid="+contractorid+"&id=-1&rnd="+Math.random();
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
    </script>

		<title>�����Ա��Ϣ</title>
	</head>
	<body>
		<template:titile value="�����Ա������Ϣ" />
		<html:form action="/ConPersonAction?method=addConPerson"
			styleId="addtoosbaseFormId" enctype="multipart/form-data">
			<table width="720" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<!-- ������Ϣ -->
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						Ա����ţ�
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="hidden" name="regionid"
							value="<bean:write name="regionid"/>" />
						<html:text property="employeeNum" styleClass="inputtext"
							style="width:160" maxlength="15" />
						<font color="#FF0000">*</font>
					</td>
					<td class="tdulleft" style="width: 15%" rowspan="4">
						��Ƭ��
					</td>
					<td class="tdulright" style="width: 35%" rowspan="4">
						<apptag:upload state="add"></apptag:upload>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						������
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="name" styleClass="inputtext"
							style="width:160"  maxlength="10" />
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�Ա�
					</td>
					<td class="tdulright" style="width: 35%">
						<html:select property="sex" styleClass="inputtext"
							style="width:160">
							<html:option value="��">��</html:option>
							<html:option value="Ů">Ů</html:option>
						</html:select>
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						���ڴ�ά��λ��
					</td>
					<td class="tdulright" style="width: 35%">
						<html:select property="contractorid" styleClass="inputtext"
							style="width:160">
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
						<html:text property="nation" styleClass="inputtext"
							style="width:160" maxlength="10" />
					</td>
					<td class="tdulleft" style="width: 15%">
						���֤���룺
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="identitycard" styleClass="inputtext"
							style="width:160;" maxlength="18" />
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						ְ�ƣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="technicalTitle" styleClass="inputtext"
							style="width:160" maxlength="25" />
						<font color="#FF0000">*</font>
					</td>
					<td class="tdulleft" style="width: 15%">
						��ְʱ�䣺
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="enterTime" styleClass="inputtext"
							readonly="true" style="width:160"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate:'%y-%M-%d'})" />
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��פ����
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="residantarea" styleClass="inputtext"
							style="width:160;" maxlength="50" />
						<font color="#FF0000">*</font>
					</td>
					<td class="tdulleft" style="width: 15%">
						ְ��
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="jobinfo" styleClass="inputtext"
							style="width:160;" maxlength="50" />
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��Ҫ����ְ��
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<html:textarea property="postOffice" styleClass="inputtext"
							style="width:160" rows="3" />
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�ֻ����룺
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="mobile" styleClass="inputtext"
							style="width:160;" maxlength="11" />
						<font color="#FF0000">*</font>
					</td>
					<td class="tdulleft" style="width: 15%">
						�������룺
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="postalcode" styleClass="inputtext"
							style="width:160;" maxlength="6" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�̶��绰���룺
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="phone" styleClass="inputtext"
							style="width:160;" maxlength="12" />
						<font color="#FF0000">*</font>
					</td>
					<td class="tdulleft" style="width: 15%">
						E-mail���䣺
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="email" styleClass="inputtext"
							style="width:160" maxlength="50" />
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��ͥסַ��
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<html:text property="familyaddress" styleClass="inputtext"
							style="width:160;" maxlength="40" />
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�Ļ��̶ȣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<html:select property="culture" styleClass="inputtext"
							style="width:160">
							<html:option value="����/��ר">����/��ר</html:option>
							<html:option value="��ר">��ר</html:option>
							<html:option value="����/��������">����/��������</html:option>
						</html:select>
						<font color="#FF0000">*</font>
					</td>
					<td class="tdulleft" style="width: 15%">
						���ѧ�����ʱ�䣺
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="graduateDate" styleClass="inputtext"
							readonly="true" style="width:160"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate:'%y-%M-%d'})" />
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��ҵԺУ��
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="academy" styleClass="inputtext"
							style="width:160" maxlength="40" />
						<font color="#FF0000">*</font>
					</td>
					<td class="tdulleft" style="width: 15%">
						��ѧרҵ��
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="speciality" styleClass="inputtext"
							style="width:160" maxlength="40" />
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�ڸڷ�
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<html:select property="conditions">
							<html:option value="0">�ڸ�</html:option>
							<html:option value="1">��ְ</html:option>
						</html:select>
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
							class="textarea"></textarea>
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��ע��
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<textarea cols="" rows="2" name="remark" style="width: 280;"
							class="textarea"></textarea>
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
					</td>
				</template:formSubmit>
			</table>
		</html:form>
	</body>
</html>


