<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<jsp:useBean id="selectForTag"
	class="com.cabletech.commons.tags.SelectForTag" scope="request" />
<script language="javascript" src="${ctx}/js/validation/prototype.js"
	type=""></script>
<html>
	<head>
		<script type="text/javascript" language="javascript">
		function valCharLength(Value){
			var j=0;
			var s = Value;
			for(var i=0; i<s.length; i++)
			{
				if   (s.substr(i,1).charCodeAt(0)>255)   j   =   j   +   2;
				else   j++;
			}
  			return j;
		};    	
    	function valiSub(){
        	if(certificateBean.certificatecode.value.trim()=="" || certificateBean.certificatecode.value.trim()==null){
            	alert("֤���Ų���Ϊ�գ�");
            	certificateBean.certificatecode.focus();
            	return false;
        	}
        	if(certificateBean.certificatename.value.trim()=="" || certificateBean.certificatename.value.trim()==null){
         		alert("֤�����Ʋ���Ϊ�գ�");
            	certificateBean.certificatename.focus();
            	return false;
        	}
        	if(certificateBean.licenceissuingauthority.value.trim()=="" || certificateBean.licenceissuingauthority.value.trim()==null){
         		alert("��֤��������Ϊ�գ�");
            	certificateBean.licenceissuingauthority.focus();
            	return false;
        	}
        	if(certificateBean.validperiodstr.value.trim()=="" || certificateBean.validperiodstr.value.trim()==null){
         		alert("��Ч�ڲ���Ϊ�գ�");
            	certificateBean.validperiodstr.focus();
            	return false;
        	}
       		certificateBean.submit();
    	};
		addGoBack=function(){
			location="${ctx}/CertificateAction.do?method=showCertificate";
		}
    </script>
		<title>�޸Ĵ�ά������Ϣ</title>
	</head>
	<body>
		<logic:present name="certificateinfo">
			<template:titile value="�޸Ĵ�ά���ʻ�����Ϣ" />
			<html:form action="/CertificateAction?method=upCertificate"
				styleId="addtoosbaseFormId">
				<template:formTable namewidth="150" contentwidth="300">
					<template:formTr name="֤����">
						<html:hidden property="id" name="certificateinfo" />
						<html:text property="certificatecode" name="certificateinfo"
							styleClass="inputtext" style="width:180;" maxlength="20" />
						<font color="#FF0000">*</font>
					</template:formTr>
					<template:formTr name="֤������">
						<html:text property="certificatename" name="certificateinfo"
							styleClass="inputtext" style="width:180;" maxlength="25" />
						<font color="#FF0000">*</font>
					</template:formTr>
					<template:formTr name="��֤����">
						<html:text property="licenceissuingauthority"
							name="certificateinfo" styleClass="inputtext" style="width:180;"
							maxlength="25" />
						<font color="#FF0000">*</font>
					</template:formTr>
					<template:formTr name="֤������">
						<apptag:quickLoadList name="certificatetype" listName="DWZZ"
							type="select" isRegion="false"
							keyValue="${certificateinfo.certificatetype}"></apptag:quickLoadList>
					</template:formTr>
					<template:formTr name="��֤����">
						<html:text property="issauthoritydatestr" name="certificateinfo"
							styleClass="inputtext" style="width:180;" maxlength="25"
							onfocus="WdatePicker({el:$dp.$('issauthoritydatestr')})" />
						<img onclick="WdatePicker({el:$dp.$('issauthoritydatestr')})"
							src="${ctx}/js/WdatePicker/skin/datePicker.gif" width="16"
							height="22" align="absmiddle">
					</template:formTr>
					<logic:equal value="1" name="LOGIN_USER" property="deptype">
						<template:formTr name="��ά��λ">
							<apptag:setSelectOptions valueName="deptCollection"
								tableName="contractorinfo" columnName1="contractorname"
								columnName2="contractorid" region="true" />
							<html:select property="objectid" styleClass="inputtext"
								name="certificateinfo" style="width:200" alt="��ά��λ">
								<html:options collection="deptCollection" property="value"
									labelProperty="label" />
							</html:select>
							<input type="hidden" name="objecttype" value="CONTRACTORINFO" />
						</template:formTr>
					</logic:equal>
					<logic:equal value="2" name="LOGIN_USER" property="deptype">
						<template:formTr name="��ά��λ">
							<select name="objectid" Class="inputtext" style="width: 200">
								<option value="${LOGIN_USER.deptID }">
									${LOGIN_USER.deptName }
								</option>
							</select>
							<input type="hidden" name="objecttype" value="CONTRACTORINFO" />
						</template:formTr>
					</logic:equal>
					<template:formTr name="��Ч��">
						<input name="validperiodstr" class="inputtext"
							style="width:180;" maxlength="25"
							readonly="true" value="<bean:write name="certificateinfo" property="validperiod" format="yyyy/MM/dd" />"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
						<font color="#FF0000">*</font>
					</template:formTr>
					<template:formTr name="����">
						<bean:define name="certificateinfo" property="id" id="cid" />
						<apptag:upload state="edit" entityId="${cid}"
							entityType="CERTIFICATE"></apptag:upload>
					</template:formTr>
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
				</template:formTable>
			</html:form>
		</logic:present>
	</body>
</html>


