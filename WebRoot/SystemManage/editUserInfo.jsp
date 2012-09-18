<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>

<script language=javascript src="${ctx}/js/validation/prototype.js" type=""></script>
<script language=javascript src="${ctx}/js/validation/effects.js" type=""></script>
<script language=javascript src="${ctx}/js/validation/validation_cn.js" type=""></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">

<script language="javascript">
	function init(){
		var types = document.getElementsByName("radiobutton");
		var type = ${userInfoBean.deptype};
		var region = ${userInfoBean.regionid};
		for(var i = 0 ; i < types.length ; i++){
			if(types[i].value == type){
				types[i].checked = true;
			}
		}
		var rIds = $("rId").options;
		for(var i = 0 ; i < rIds.length ; i++){
			if(rIds[i].value == region){
				rIds[i].selected = true;
			}
		}

		setDepType(type);

		var cIds;
		if(type=="1"){
			cIds = $("dId").options;
		}
		else{
			cIds = $("cId").options;
		}
		for(var i = 0 ; i < cIds.length ; i++){
			if(cIds[i].value == $("tempdeptid").value){
				cIds[i].selected = true;
			}
		}

		var group = "${userInfoBean.usergroups}";
		var groups = document.getElementsByName("usergroupid");
		for(var i = 0 ; i < groups.length ; i++){
			if(group.indexOf(groups[i].value) != -1){
				groups[i].checked = true;
			}
		}
	}

	function setDepType(deptType){
	    onChangeGroup(deptType);
	    $("deptype").value = deptType;
	    if(deptType=='2'||deptType=='3'){
	    	$("deptID").value=$("cId").value;
	    	deptlabel.style.display="none";
	    	conlabel.style.display="";
	    }
	    else if(deptType=='1'){
	    	$("deptID").value=$("dId").value;
	    	deptlabel.style.display="";
	    	conlabel.style.display="none";
	    }
	    if(deptType=='3'){
	    	{conLabel.innerHTML="����λ��";}
	    }
	    if(deptType=='2'){
	    	{conLabel.innerHTML="��ά��λ��";}
	    }
	}

	function onChangeGroup(deptType){
		var value = deptType;
		

		if($("ugpworkid").length){
			$("userGroupDiv").innerHTML = "";
		}

		var uo = $("ugpworkid").options;
		for( i=0 ; i < uo.length ; i++){
			var v = uo[i].value;
			if(value==v.substring(v.lastIndexOf("-")+1,v.length)){
				var val = uo[i].value.substring(0,uo[i].value.indexOf('-'));
				var text = uo[i].text.substring(8,uo[i].text.length);
				$("userGroupDiv").innerHTML += "<input type='checkbox' name='usergroupid' value='"+val+"' />"+text+"<br/>";
			}
		}
		onChangeCon();
	}
	function setDeptId(dpetId){
		$("deptID").value=	dpetId;
	}
	
	function onChangeCon(){
		var k=0;
		for( i=0;i<document.all.deptworkid.options.length;i++){
			if(document.all.deptworkid.options[i].text.substring(0,6)== document.all.rId.value || document.all.rId.value.substring(2,6)=="1111" ){
			  k++;
			  document.all.dId.options.length=k;
			  document.all.dId.options[k-1].value=document.all.deptworkid.options[i].value;
			  document.all.dId.options[k-1].text=document.all.deptworkid.options[i].text.substring(8,document.all.deptworkid.options[i].text.length);
			}
		}
		if(k==0)
			document.all.dId.options.length=0;
		k=0;
		for( i=0;i<document.all.conworkid.options.length;i++){
			if(document.all.conworkid.options[i].text.substring(0,6)== document.all.rId.value||document.all.rId.value.substring(2,6)=="1111"){
				k++;
				document.all.cId.options.length=k;
				document.all.cId.options[k-1].value=document.all.conworkid.options[i].value;
				document.all.cId.options[k-1].text=document.all.conworkid.options[i].text.substring(8,document.all.conworkid.options[i].text.length);
			}
		}
		if(k==0)
			document.all.cId.options.length=0;
	}

	function checkUserID(){
		var userID = $("userID").value;
		var URL = "${ctx}/userinfoAction.do?method=ifUserIDValid&userID=" + userID;
		innerMsgFrame.location.replace(URL);
	}
	function toGetBack(){
    location.href = "${ctx}/userinfoAction.do?method=queryUserinfo&userName";
    return true;
}
</script>

<head>
	<template:titile value="�޸��û���Ϣ"/>
</head>

<body onload="init()">
	<html:form styleId="editUser" method="Post" action="/userinfoAction.do?method=updateUserinfo">
		<template:formTable contentwidth="300" namewidth="200">
			<html:hidden property="tempdeptid" value="${userInfoBean.deptID}"/>
  			<html:hidden property="deptID" value=""/>
  			<html:hidden property="deptype" value=""/>
  			<template:formTr name="�û���¼��" >
		      	<html:text readonly="true" property="userID" styleClass="inputtext" style="width:200" maxlength="20"/>
		    </template:formTr>
		    <template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��">
		      	<html:text property="userName" styleClass="required" style="width:200" maxlength="20"/>
		    </template:formTr>
			<template:formTr name="�������">
			    <html:hidden property="isSuperviseUnit" value="0"/>
			        <c:if test="${LOGIN_USER.deptype != '2'}">
			      		<input type="radio" name="radiobutton" value="1" onclick="setDepType(this.value)">�ڲ�����
			        </c:if>
			      		<input type="radio" name="radiobutton" value="2" onclick="setDepType(this.value)">��ά��λ
			      		<input type="radio" name="radiobutton" value="3" onclick="setDepType(this.value)">����λ
		    </template:formTr>
		    <c:choose>
		    	<c:when test="${LOGIN_USER.type eq '11'}">
					<logic:notEmpty name="regionlist">
						<template:formTr name="��������">
							<select name="regionid" class="inputtext" style="width:200" id="rId"  onchange="onChangeCon()" >
								<logic:present name="regionlist">
									<logic:iterate id="reginfoId" name="regionlist">
										<option value="<bean:write name="reginfoId" property="regionid"/>">
											<bean:write name="reginfoId" property="regionname"/>
										</option>
									</logic:iterate>
								</logic:present>
							</select>
						</template:formTr>
					</logic:notEmpty>
		    	</c:when>
		    	<c:otherwise>
					<template:formTr name="��������"  style="display:none">
					    <html:select property="regionid" styleId="rId" styleClass="inputtext" style="width:200">
					     	<html:option value="${LOGIN_USER.regionid}">${LOGIN_USER_REGION_NAME}</html:option>
					    </html:select>
					</template:formTr>
		    	</c:otherwise>
	    	</c:choose>
	    	<logic:present name="ugplist">
              <select name="ugpworkid"   style="display:none">
                <logic:iterate id="ugpid" name="ugplist">
                    <option value="<bean:write name="ugpid" property="id"/>--<bean:write name="ugpid" property="type" />"><bean:write name="ugpid" property="regionid"/>--<bean:write name="ugpid" property="groupname"/></option>
                </logic:iterate>
              </select>
    		</logic:present>
    		<logic:present name="deptlist">
		        <select name="deptworkid"   style="display:none">
			        <logic:iterate id="depid" name="deptlist">
			            <option value="<bean:write name="depid" property="deptid"/>"><bean:write name="depid" property="regionid"/>--<bean:write name="depid" property="deptname"/></option>
			        </logic:iterate>
		        </select>
		    </logic:present>
		    <logic:present name="conlist">
		        <select name="conworkid"   style="display:none">
		            <logic:iterate id="conid" name="conlist">
		                <option value="<bean:write name="conid" property="contractorid"/>"><bean:write name="conid" property="regionid"/>--<bean:write name="conid" property="contractorname"/></option>
		            </logic:iterate>
		        </select>
		    </logic:present>
		    <logic:notEmpty name="deptlist">
			    <template:formTr tagID="deptlabel" name="��&nbsp;&nbsp;&nbsp;&nbsp;��">
				    <select  class="inputtext" style="width:200" id="dId"  onchange="setDeptId(this.value)" >
					    <logic:present name="deptlist">
					        <logic:iterate id="id" name="deptlist">
					        	<option value="<bean:write name="id" property="deptid"/>">
					        		<bean:write name="id" property="deptname"/>
					        	</option>
					        </logic:iterate>
					    </logic:present>
				    </select>
			    </template:formTr>
		    </logic:notEmpty>
			<logic:notEmpty name="conlist">
			    <tr class=trwhite id=conlabel style=display:none ><td id="conLabel" class="tdulleft">��ά��λ��</td><td class="tdulright">
				    <select  class="inputtext" style="width:200" id="cId"  onchange="setDeptId(this.value)" >
				        <logic:present name="conlist">
				          	<logic:iterate id="id" name="conlist">
					     	    <option value="<bean:write name="id" property="contractorid"/>">
					     	    	<bean:write name="id" property="contractorname"/>
					     		</option>
				          	</logic:iterate>
				        </logic:present>
				    </select>
			    </td></tr>
		    </logic:notEmpty>
			<logic:notEmpty name="ugplist">
		        <template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;ɫ" >
			        <div id="userGroupDiv">
		          		<logic:present name="ugplist">
		            		<logic:iterate id="id" name="ugplist">
	            				<input type="checkbox" name="usergroupid" value="<bean:write name="id" property="id"/>" /><bean:write name="id" property="groupname"/></br>
		            		</logic:iterate>
		         		 </logic:present>
		         	</div>
		      	</template:formTr>
		    </logic:notEmpty>
		    <template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��">
		        <html:text property="workID" styleClass="inputtext" style="width:200" maxlength="7"/>
		    </template:formTr>
		    <template:formTr name="�˺�״̬">
			    <html:select property="accountState" styleClass="inputtext" style="width:200">
				    <html:option value="1">����</html:option>
				    <html:option value="2">��ͣ</html:option>
				    <html:option value="3">ֹͣ</html:option>
			    </html:select>
		    </template:formTr>
		    <template:formTr name="��&nbsp;��&nbsp;��" >
		        <html:text property="phone" styleClass="inputtext" style="width:200" maxlength="11"/>
		    </template:formTr>
		    <template:formTr name="�����ʼ�">
		        <html:text property="email" styleClass="validate-email" style="width:200" maxlength="30"/>
		    </template:formTr>
		    <template:formTr name="ְ&nbsp;&nbsp;&nbsp;&nbsp;λ" >
		        <html:text property="position" styleClass="inputtext" style="width:200" maxlength="20"/>
		    </template:formTr>
		    <template:formTr name="��������" >
		    <apptag:quickLoadList name="district" keyValue="${userInfoBean.district }" isRegion="false" cssClass="inputtext" style="width:220px" listName="${LOGIN_USER.regionID}" type="select" isQuery="query"></apptag:quickLoadList>
			</template:formTr>
		    <template:formSubmit>
			    	<html:submit styleClass="button">����</html:submit>
			   		<input type="button" class="button" onclick="history.back()" value="����" >
			</template:formSubmit>
		</template:formTable>
	</html:form>
	<script type="text/javascript">
		function formCallback(result, form) {
			window.status = "valiation callback for form '" + form.id + "': result = " + result;
		}

		var valid = new Validation('editUser', {immediate : true, onFormValidate : formCallback});
	</script>
</body>




