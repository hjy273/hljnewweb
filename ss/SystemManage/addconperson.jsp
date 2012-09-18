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
		//手机号码验证 
		String.prototype.isMobile = function() {  
			return (/^(?:13\d|15[0123456789])-?\d{5}(\d{3}|\*{3})$/.test(this.Trim()));  
		}  

    	function valiSub(){
        	if(conPersonBean.name.value==""){
            	alert("姓名不能为空！");
            	return false;
        	}
        	
        	/**
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
        	if(conPersonBean.familyaddress.value=="" || conPersonBean.familyaddress.value==null){
            	alert("家庭住址不能为空！");
            	return false;
        	}
        	if(trim(conPersonBean.familyaddress.value)=="" || trim(conPersonBean.familyaddress.value)==null){
            	alert("家庭住址不能为空格！");
            	return false;
        	}**/
        	if(document.conPersonBean.mobile.value==""){
        		alert("手机号码不能为空！");
        		return false;
        	}
        	if(!document.getElementById("mobile").value.isMobile()){
        		alert("请输入正确的手机号码！");
        		return;
        	}
        	if(document.conPersonBean.enterTime.value==""){
        		alert("入职时间不能为空！");
        		return false;
        	}
       		conPersonBean.submit();
    	}
    </script>

	<title>添加人员信息</title>
	</head>
	<body>
		<template:titile value="添加人员基本信息" />
		<html:form action="/ConPersonAction?method=addConPerson"
			styleId="addtoosbaseFormId">
			<template:formTable>
				<template:formTr name="所属区域">
					<input type="text" name="region" Class="inputtext"
						readonly="readonly" style="width: 180px;" maxlength="50"
						value="<%=session.getAttribute("LOGIN_USER_REGION_NAME")%>" />
				</template:formTr>
				<template:formTr name="代维单位">
					<input type="hidden" name="regionid"
						value="<bean:write name="regionid"/>" />
					<input type="hidden" name="contractorid"
						value="<bean:write name="deptid"/>" />
					<input type="text" name="deptname" readonly="readonly"
						value="<bean:write name="deptname"/>" Class="inputtext"
						style="width: 180px;" maxlength="6" />
				</template:formTr>
				<template:formTr name="姓名">
					<html:text property="name" styleClass="inputtext"
						style="width:180px;" maxlength="20" /><font color="red"> *</font>
				</template:formTr>
				<template:formTr name="性别">
					<html:select property="sex" styleClass="inputtext"
						style="width:180px">
						<html:option value="男">男</html:option>
						<html:option value="女">女</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="文化程度">
					<html:select property="culture" styleClass="inputtext"
						style="width:180px">
						<html:option value="高中/中专">高中/中专</html:option>
						<html:option value="大专">大专</html:option>
						<html:option value="本科/本科以上">本科/本科以上</html:option>
					</html:select>
				</template:formTr>
				<!--<template:formTr name="婚姻状况">
					<html:select property="ismarriage" styleClass="inputtext"
						style="width:180px">
						<html:option value="未婚">未婚</html:option>
						<html:option value="已婚">已婚</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="是否有效">
					<html:select property="state" styleClass="inputtext"
						style="width:180px">
						<html:option value="有效">有效</html:option>
						<html:option value="无效">无效</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="是否工作人员">
					<html:select property="persontype" styleClass="inputtext"
						style="width:180px">
						<html:option value="工作人员">工作人员</html:option>
						<html:option value="非工作人员">非工作人员</html:option>
					</html:select>
				</template:formTr>-->
				<template:formTr name="固定电话">
					<html:text property="phone" styleClass="inputtext"
						style="width:180px;" maxlength="12" />
				</template:formTr>
				<template:formTr name="手机号码">
					<html:text property="mobile" styleClass="inputtext" styleId="mobile"
						style="width:180px;" maxlength="13" /><font color="red"> *</font>
				</template:formTr>
				<template:formTr name="家庭住址">
					<html:text property="familyaddress" styleClass="inputtext"
						style="width:180px;" maxlength="18" />
				</template:formTr>
				<template:formTr name="邮政编码">
					<html:text property="postalcode" styleClass="inputtext"
						style="width:180px;" maxlength="6" />
				</template:formTr>
				<template:formTr name="职务">
					<html:text property="jobinfo" styleClass="inputtext"
						style="width:180px;" maxlength="50" />
				</template:formTr>
				<template:formTr name="身份证">
					<html:text property="identitycard" styleClass="inputtext"
						style="width:180px;" maxlength="50" />
				</template:formTr>
				<template:formTr name="是否接受短信">
					<html:radio property="issendsms" value="1">是</html:radio>
					<html:radio property="issendsms" value="2">否</html:radio>
				</template:formTr>
				<template:formTr name="常驻区域">
					<html:text property="residantarea" styleClass="inputtext"
						style="width:180px;" maxlength="50" />
				</template:formTr>
				<template:formTr name="入职时间">
					<input name="enterTime" class="Wdate" id="enterTime" style="width:180px"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" readonly/><font color="red"> *</font>
				</template:formTr>
				<template:formTr name="岗位职责">
					<textarea cols="" rows="2" name="postOffice" style="width: 280px;"
						class="textarea"></textarea>
				</template:formTr>
				
				<template:formTr name="工作经历">
					<textarea cols="" rows="2" name="workrecord" style="width: 280px;"
						class="textarea"></textarea>
				</template:formTr>
				<template:formTr name="备注信息">
					<textarea cols="" rows="2" name="remark" style="width: 280px;"
						class="textarea"></textarea>
				</template:formTr>

				<template:formSubmit>
					<td>
						<html:button property="action" styleClass="button"
							onclick="valiSub()">提交</html:button>
					</td>
					<td>
						<html:reset property="action" styleClass="button">取消</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
		<template:cssTable></template:cssTable>
	</body>
</html>


