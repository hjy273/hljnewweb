<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<script language="javascript" src="${ctx}/js/prototype.js" type=""></script>
<jsp:useBean id="selectForTag"
	class="com.cabletech.commons.tags.SelectForTag" scope="request" />
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<%
    String condition = "";
    UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
    //市移动
    if (userinfo.getDeptype().equals("1")
            && !userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE regionid IN ('" + userinfo.getRegionID()
                + "') AND state IS NULL";
    }
    //市代维
    if (userinfo.getDeptype().equals("2")
            && !userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE state IS NULL  and contractorid='" + userinfo.getDeptID()
                + "' ";
    }
    //省移动
    if (userinfo.getDeptype().equals("1")
            && userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE state IS NULL";
    }
    //省代维
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
    		//民族、邮编、备注不需验证
        	if(conPersonBean.employeeNum.value=="" || conPersonBean.employeeNum.value==null||conPersonBean.employeeNum.value.trim()=="" || conPersonBean.employeeNum.value.trim()==null){
            	alert("员工编号不能为空或者空格！");
            	conPersonBean.employeeNum.focus();
            	return false;
        	}
        	if(conPersonBean.name.value=="" || conPersonBean.name.value==null||conPersonBean.name.value.trim()=="" || conPersonBean.name.value.trim()==null){
            	alert("姓名不能为空或者空格！");
            	conPersonBean.name.focus();
            	return false;
        	}
        	if(conPersonBean.identitycard.value=="" || conPersonBean.identitycard.value==null||conPersonBean.identitycard.value.trim()=="" || conPersonBean.identitycard.value.trim()==null){
            	alert("身份证号码不能为空或者空格！");
            	conPersonBean.identitycard.focus();
            	return false;
        	}
        	if(!regxIdCard.test(conPersonBean.identitycard.value)){
        		alert("身份证号码必须为15位、18位数字或者17位数字+x！");
        		conPersonBean.identitycard.focus();
            	return false;
        	}
        	if(conPersonBean.technicalTitle.value=="" || conPersonBean.technicalTitle.value==null||conPersonBean.technicalTitle.value.trim()=="" || conPersonBean.technicalTitle.value.trim()==null){
            	alert("职称不能为空或者空格！");
            	conPersonBean.technicalTitle.focus();
            	return false;
        	}
        	if(conPersonBean.enterTime.value=="" || conPersonBean.enterTime.value==null||conPersonBean.enterTime.value.trim()=="" || conPersonBean.enterTime.value.trim()==null){
            	alert("入职时间不能为空或者空格！");
            	conPersonBean.enterTime.focus();
            	return false;
        	}
        	if(conPersonBean.residantarea.value=="" || conPersonBean.residantarea.value==null||conPersonBean.residantarea.value.trim()=="" || conPersonBean.residantarea.value.trim()==null){
            	alert("常驻区域不能为空或者空格！");
            	conPersonBean.residantarea.focus();
            	return false;
        	}
        	if(conPersonBean.jobinfo.value=="" || conPersonBean.jobinfo.value==null||conPersonBean.jobinfo.value.trim()=="" || conPersonBean.jobinfo.value.trim()==null){
            	alert("职务不能为空或者空格！");
            	conPersonBean.jobinfo.focus();
            	return false;
        	}
        	if(conPersonBean.postOffice.value=="" || conPersonBean.postOffice.value==null||conPersonBean.postOffice.value.trim()=="" || conPersonBean.postOffice.value.trim()==null){
            	alert("主要工作职责不能为空或者空格！");
            	conPersonBean.postOffice.focus();
            	return false;
        	}
        	if(conPersonBean.mobile.value=="" || conPersonBean.mobile.value==null||conPersonBean.mobile.value.trim()=="" || conPersonBean.mobile.value.trim()==null){
            	alert("手机号码不能为空或者空格！");
            	conPersonBean.mobile.focus();
            	return false;
        	}
        	if(!regx.test(conPersonBean.mobile.value)){
        		alert("手机号码必须为11位数字！");
        		conPersonBean.mobile.focus();
            	return false;
        	}
        	if(conPersonBean.phone.value=="" || conPersonBean.phone.value==null||conPersonBean.phone.value.trim()=="" || conPersonBean.phone.value.trim()==null){
            	alert("固定电话号码不能为空或者空格！");
            	conPersonBean.phone.focus();
            	return false;
        	}
        	if(conPersonBean.email.value=="" || conPersonBean.email.value==null||conPersonBean.email.value.trim()=="" || conPersonBean.email.value.trim()==null){
            	alert("E-mail邮箱不能为空或者空格！");
            	conPersonBean.email.focus();
            	return false;
        	}
        	if(conPersonBean.familyaddress.value=="" || conPersonBean.familyaddress.value==null||conPersonBean.familyaddress.value.trim()=="" || conPersonBean.familyaddress.value.trim()==null){
            	alert("家庭住址不能为空或者空格！");
            	conPersonBean.familyaddress.focus();
            	return false;
        	}
        	if(conPersonBean.graduateDate.value=="" || conPersonBean.graduateDate.value==null||conPersonBean.graduateDate.value.trim()=="" || conPersonBean.graduateDate.value.trim()==null){
            	alert("最高学历获得时间不能为空或者空格！");
            	conPersonBean.graduateDate.focus();
            	return false;
        	}
        	if(conPersonBean.academy.value=="" || conPersonBean.academy.value==null||conPersonBean.academy.value.trim()=="" || conPersonBean.academy.value.trim()==null){
            	alert("毕业院校不能为空或者空格！");
            	conPersonBean.academy.focus();
            	return false;
        	}
        	if(conPersonBean.speciality.value=="" || conPersonBean.speciality.value==null||conPersonBean.speciality.value.trim()=="" || conPersonBean.speciality.value.trim()==null){
            	alert("所学专业不能为空或者空格！");
            	conPersonBean.speciality.focus();
            	return false;
        	}
        	if(conPersonBean.workrecord.value=="" || conPersonBean.workrecord.value==null||conPersonBean.workrecord.value.trim()=="" || conPersonBean.workrecord.value.trim()==null){
            	alert("工作经历不能为空或者空格！");
            	conPersonBean.workrecord.focus();
            	return false;
        	}
        	if(valCharLength(conPersonBean.name.value)>20){
          		alert("姓名不能大于10个汉字字符！");
          		conPersonBean.name.focus();
            	return false;
        	}
        	if(valCharLength(conPersonBean.postOffice.value)>2048){
          		alert("主要工作职责不能大于1024个汉字字符！");
          		conPersonBean.postOffice.focus();
            	return false;
        	}
        	if(valCharLength(conPersonBean.workrecord.value)>2048){
          		alert("工作经历不能大于1024个汉字字符！");
          		conPersonBean.workrecord.focus();
            	return false;
        	}
        	if(valCharLength(conPersonBean.remark.value)>200){
          		alert("备注信息不能大于100个汉字字符！");
          		conPersonBean.remark.focus();
            	return false;
        	}
        	if(judgeExist()){
        		alert("该代维单位下已经存在相同员工编号的单位人员！");
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

		<title>添加人员信息</title>
	</head>
	<body>
		<template:titile value="添加人员基本信息" />
		<html:form action="/ConPersonAction?method=addConPerson"
			styleId="addtoosbaseFormId" enctype="multipart/form-data">
			<table width="720" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<!-- 基础信息 -->
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						员工编号：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="hidden" name="regionid"
							value="<bean:write name="regionid"/>" />
						<html:text property="employeeNum" styleClass="inputtext"
							style="width:160" maxlength="15" />
						<font color="#FF0000">*</font>
					</td>
					<td class="tdulleft" style="width: 15%" rowspan="4">
						照片：
					</td>
					<td class="tdulright" style="width: 35%" rowspan="4">
						<apptag:upload state="add"></apptag:upload>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						姓名：
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="name" styleClass="inputtext"
							style="width:160"  maxlength="10" />
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						性别：
					</td>
					<td class="tdulright" style="width: 35%">
						<html:select property="sex" styleClass="inputtext"
							style="width:160">
							<html:option value="男">男</html:option>
							<html:option value="女">女</html:option>
						</html:select>
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						所在代维单位：
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
						民族：
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="nation" styleClass="inputtext"
							style="width:160" maxlength="10" />
					</td>
					<td class="tdulleft" style="width: 15%">
						身份证号码：
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="identitycard" styleClass="inputtext"
							style="width:160;" maxlength="18" />
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						职称：
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="technicalTitle" styleClass="inputtext"
							style="width:160" maxlength="25" />
						<font color="#FF0000">*</font>
					</td>
					<td class="tdulleft" style="width: 15%">
						入职时间：
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
						常驻区域：
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="residantarea" styleClass="inputtext"
							style="width:160;" maxlength="50" />
						<font color="#FF0000">*</font>
					</td>
					<td class="tdulleft" style="width: 15%">
						职务：
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="jobinfo" styleClass="inputtext"
							style="width:160;" maxlength="50" />
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						主要工作职责：
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<html:textarea property="postOffice" styleClass="inputtext"
							style="width:160" rows="3" />
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						手机号码：
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="mobile" styleClass="inputtext"
							style="width:160;" maxlength="11" />
						<font color="#FF0000">*</font>
					</td>
					<td class="tdulleft" style="width: 15%">
						邮政编码：
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="postalcode" styleClass="inputtext"
							style="width:160;" maxlength="6" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						固定电话号码：
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="phone" styleClass="inputtext"
							style="width:160;" maxlength="12" />
						<font color="#FF0000">*</font>
					</td>
					<td class="tdulleft" style="width: 15%">
						E-mail邮箱：
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="email" styleClass="inputtext"
							style="width:160" maxlength="50" />
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						家庭住址：
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<html:text property="familyaddress" styleClass="inputtext"
							style="width:160;" maxlength="40" />
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						文化程度：
					</td>
					<td class="tdulright" style="width: 35%">
						<html:select property="culture" styleClass="inputtext"
							style="width:160">
							<html:option value="高中/中专">高中/中专</html:option>
							<html:option value="大专">大专</html:option>
							<html:option value="本科/本科以上">本科/本科以上</html:option>
						</html:select>
						<font color="#FF0000">*</font>
					</td>
					<td class="tdulleft" style="width: 15%">
						最高学历获得时间：
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
						毕业院校：
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="academy" styleClass="inputtext"
							style="width:160" maxlength="40" />
						<font color="#FF0000">*</font>
					</td>
					<td class="tdulleft" style="width: 15%">
						所学专业：
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="speciality" styleClass="inputtext"
							style="width:160" maxlength="40" />
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						在岗否：
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<html:select property="conditions">
							<html:option value="0">在岗</html:option>
							<html:option value="1">离职</html:option>
						</html:select>
						<font color="#FF0000">*</font>
					</td>
					<!-- 
				<td class="tdulleft" style="width:15%">
					离职时间：
				</td>
				<td class="tdulright" style="width:35%">
					
				</td>
				 -->
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						工作经历：
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<textarea cols="" rows="2" name="workrecord" style="width: 280;"
							class="textarea"></textarea>
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						备注：
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<textarea cols="" rows="2" name="remark" style="width: 280;"
							class="textarea"></textarea>
					</td>
				</tr>
				<template:formSubmit>
					<td>
						<html:button property="action" styleClass="button"
							onclick="valiSub()">提交</html:button>
					</td>
					<td>
						<html:reset property="action" styleClass="button">取消</html:reset>
					</td>
					<td>
					</td>
				</template:formSubmit>
			</table>
		</html:form>
	</body>
</html>


