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
            	alert("姓名不能为空！");
            	return false;
        	}
        	if(valCharLength(conPersonBean.name.value)>10){
          		alert("姓名不能大于5个汉字字符！");
          		return false;
        	}
        	if(valCharLength(conPersonBean.workrecord.value)>2048){
          		alert("工作经历不能大于1024个汉字字符！");
          		return false;
        	}
        	if(valCharLength(conPersonBean.workrecord.value)>200){
          		alert("备注信息不能大于100个汉字字符！");
          		return false;
        	}
			if(conPersonBean.mobile.value=="" || conPersonBean.mobile.value==null){
            	alert("手机号码不能为空！");
            	return false;
        	}
        	if(conPersonBean.enterTime.value=="" || conPersonBean.enterTime.value==null){
            	alert("入职时间不能为空！");
            	return false;
        	}
			if(new Date(conPersonBean.enterTime.value)-new Date(conPersonBean.leaveTime.value)>0){
				alert("离职时间不能早于入职时间");
				return false;
			}
       		conPersonBean.submit();
    	}
    </script>
	<title>修改人员信息</title>
	</head>
	<body>
		<logic:present name="personinfo">
			<template:titile value="修改人员基本信息" />
			<html:form action="/ConPersonAction?method=upPerson"
				styleId="conPersonBean">
				<template:formTable>
					<template:formTr name="所属区域">
						<input type="text" name="region" Class="inputtext"
							readonly="readonly" style="width: 180px;" maxlength="50"
							value="<%=session.getAttribute("LOGIN_USER_REGION_NAME")%>" />
					</template:formTr>
					<template:formTr name="上级单位">
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
					<template:formTr name="姓名">
						<html:text property="name" name="personinfo"
							styleClass="inputtext" style="width:180px;" maxlength="20" />&nbsp;&nbsp;<font color="red">*</font>
					</template:formTr>
					<template:formTr name="性别">
							<html:select property="sex" name="personinfo" styleClass="inputtext" style="width:180px;">
								<html:option value="男">男</html:option>
								<html:option value="女">女</html:option>
							</html:select>
					</template:formTr>
					<template:formTr name="文化程度">
							<html:select property="culture" name="personinfo" styleClass="inputtext" style="width:180px;">
								<html:option value="高中/中专">高中/中专</html:option>
								<html:option value="大专">大专</html:option>
								<html:option value="本科/本科以上">本科/本科以上</html:option>
							</html:select>
					</template:formTr>
					<!--<template:formTr name="婚姻状况">
						<logic:equal value="未婚" property="ismarriage" name="personinfo">
							<html:select property="ismarriage" name="personinfo"
								styleClass="inputtext" style="width:180px;">
								<option value="未婚">未婚</option>
								<option value="已婚">已婚</option>
							</html:select>
						</logic:equal>
						<logic:equal value="已婚" property="ismarriage" name="personinfo">
							<html:select property="ismarriage" name="personinfo" styleClass="inputtext" style="width:180px;">
								<option value="未婚">未婚</option>
								<option value="已婚" selected="selected">已婚</option>
							</html:select>
						</logic:equal>
					</template:formTr>
					<template:formTr name="是否有效">
						<logic:equal value="有效" property="state" name="personinfo">
							<html:select property="state" styleClass="inputtext"
								style="width:180px">
								<option value="有效">有效</option>
								<option value="无效">无效</option>
							</html:select>
						</logic:equal>
						<logic:equal value="无效" property="state" name="personinfo">
							<html:select property="state" styleClass="inputtext"
								style="width:180px">
								<option value="有效">有效</option>
								<option value="无效" selected="selected">无效</option>
							</html:select>
						</logic:equal>
					</template:formTr>
					<template:formTr name="是否工作人员">
						<logic:equal value="工作人员" property="persontype" name="personinfo">
							<html:select property="persontype" styleClass="inputtext"
								style="width:180px">
								<option value="工作人员">工作人员</option>
								<option value="非工作人员">非工作人员</option>
							</html:select>
						</logic:equal>
						<logic:equal value="非工作人员" property="persontype" name="personinfo">
							<html:select property="persontype" styleClass="inputtext"
								style="width:160">
								<option value="工作人员">工作人员</option>
								<option value="非工作人员" selected="selected">非工作人员</option>
							</html:select>
						</logic:equal>
					</template:formTr>-->
					
					<template:formTr name="固定电话">
						<html:text property="phone" name="personinfo" styleClass="inputtext" style="width:180px;" maxlength="12" />
					</template:formTr>
					<template:formTr name="手机号码">
						<html:text property="mobile" name="personinfo" styleId="mobile"
							styleClass="inputtext" style="width:180px;" maxlength="11" />&nbsp;&nbsp;<font color="red">*</font>
					</template:formTr>
					<template:formTr name="家庭住址">
						<html:text property="familyaddress" name="personinfo"
							styleClass="inputtext" style="width:180px;" maxlength="18" />
					</template:formTr>
					<template:formTr name="邮政编码">
						<html:text property="postalcode" name="personinfo"
							styleClass="inputtext" style="width:180px;" maxlength="6" />
					</template:formTr>
					<template:formTr name="职务">
						<html:text property="jobinfo" name="personinfo" styleClass="inputtext" style="width:180px;" maxlength="50" />
					</template:formTr>
					<template:formTr name="身份证">
						<html:text property="identitycard" name="personinfo" styleClass="inputtext" style="width:180px;" maxlength="50" />
					</template:formTr>
					<template:formTr name="是否接受短信">
						<html:radio property="issendsms" name="personinfo" value="1">是</html:radio>
						<html:radio property="issendsms" name="personinfo" value="2">否</html:radio>
					</template:formTr>
					<template:formTr name="常驻区域">
						<html:text property="residantarea" name="personinfo" styleClass="inputtext" style="width:180px;" maxlength="50" />
					</template:formTr>
					<template:formTr name="入职时间">
						<input name="enterTime" class="Wdate" id="enterTime" style="width:180px"
								onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" value="<bean:write name="personinfo" property="enterTime" format="yyyy/MM/dd"/>" readonly/><font color="red"> *</font>
					</template:formTr>
					<template:formTr name="离职时间">
						<input name="leaveTime" class="Wdate" id="leaveTime" style="width:180px"
								onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" value="<bean:write name="personinfo" property="leaveTime" format="yyyy/MM/dd"/>" readonly/></font>
					</template:formTr>
					<template:formTr name="岗位职责">
						<textarea cols="" rows="2" name="postOffice" style="width: 280px;"
							class="textarea"><bean:write name="personinfo" property="postOffice" /></textarea>
					</template:formTr>	
					<template:formTr name="工作经历">
						<textarea cols="" rows="2" name="workrecord" style="width: 280px;" class="textarea"><bean:write name="personinfo" property="workrecord" /></textarea>
					</template:formTr>
					<template:formTr name="备注信息">
						<textarea cols="" rows="2" name="remark" style="width: 280px;" class="textarea"><bean:write name="personinfo" property="remark" /></textarea>
					</template:formTr>
					<template:formSubmit>
						<td>
							<html:button property="action" styleClass="button" styleId="action" onclick="valiSub()">提交</html:button>
						</td>
						<td>
							<html:reset property="action" styleClass="button">取消</html:reset>
						</td>
						<td>
							<html:button property="action" styleClass="button" onclick="addGoBack()">返回</html:button>
						</td>
					</template:formSubmit>
				</template:formTable>
			</html:form>
		</logic:present>
		<template:cssTable></template:cssTable>
	</body>
</html>


