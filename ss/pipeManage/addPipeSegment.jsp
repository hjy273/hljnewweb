<%@include file="/common/header.jsp"%>
<jsp:useBean id="statdao" class="com.cabletech.statistics.dao.StatDao" scope="request"/>

<script language="javascript" type="">

function addGoBack(){
    location.href = "${ctx}/PipeSegmentAction.do?method=queryPipeSegment";
    return true;
}
function setCyc(){
    finishTime.style.display = "";
}

function downloadFile() {
  location.href = "${ctx}/PipeSegmentAction.do?method=downloadTemplet";
}

function showupload() {
    var objpart = document.getElementById("groupDivId");
    var objup = document.getElementById("pupDivId");
    objpart.style.display="none";
    objup.style.display="block";
}

function noupload()
{
    var objpart = document.getElementById("groupDivId");
    var objup = document.getElementById("upDivId");

    objpart.style.display="block";
    objup.style.display="none";
}

function isValidForm() {
    var myform = document.forms[0];
    if(myform.pipeno.value.replace(/(^\s*)|(\s*$)/g,"")==''){
       alert('�ܵ��α�Ų���Ϊ�գ�');
        return false;
    }
    if(myform.pipename.value.replace(/(^\s*)|(\s*$)/g,"")==''){
       alert('�ܵ������Ʋ���Ϊ�գ�');
        return false;
    }
    if(myform.length.value.replace(/(^\s*)|(\s*$)/g,"") == ""){
       alert('���벻��Ϊ�գ�');
        return false;
    }
     var patrn1 =/^([0-9]{1,20})(.)([0-9]{1,20})||([0-9]{1,20})$/; 
    if (!patrn1.exec(myform.length.value.replace(/(^\s*)|(\s*$)/g,""))){
       alert('������������֣�');
       return false;
    }
    if(myform.pipehole.value.replace(/(^\s*)|(\s*$)/g,"") == ""){
	alert('�ܿ�������Ϊ�գ�');
        return false;
    }
    var patrn2=/^[0-9]{1,20}$/; 
    if(!patrn2.exec(myform.pipehole.value.replace(/(^\s*)|(\s*$)/g,""))){
       alert('�ܿ������������֣�');
       return false;
    }
    if(myform.subpipehole.value.replace(/(^\s*)|(\s*$)/g,"") == ""){
	alert('�ӹ�������Ϊ�գ�');
        return false;
    }
    if (!patrn2.exec(myform.subpipehole.value.replace(/(^\s*)|(\s*$)/g,""))){
       alert('�ӹ������������֣�');
        return false;
    }
    if(myform.unuserpipe.value.replace(/(^\s*)|(\s*$)/g,"") == ""){
       alert('δ���ӹ�������Ϊ�գ�');
        return false;
    }
  if (!patrn2.exec(myform.unuserpipe.value.replace(/(^\s*)|(\s*$)/g,""))){
       alert('δ���ӹ������������֣�');
        return false;
    }
	return true;
}
function onChangeCon(){
      k=1;
      for( i=0;i<document.all.workID.options.length;i++){
             if(document.all.workID.options[i].text.substring(0,6)== document.all.rId.value){
                k++;
                document.all.dId.options.length=k;
                document.all.dId.options[0].value="";

                document.all.dId.options[k-1].value=document.all.workID.options[i].value;
                document.all.dId.options[k-1].text=document.all.workID.options[i].text.substring(8,document.all.workID.options.length);
             }
      }
      if(k==0)
        document.all.dId.options.length=0;

    }

</script>
<%
String condition="";
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
//���ƶ�
if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
   condition = " WHERE regionid IN ('"+userinfo.getRegionID()+"') AND state IS NULL";
}
//�д�ά
if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL  and contractorid='"+userinfo.getDeptID()+"' ";
}
//ʡ�ƶ�
if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL";
}
//ʡ��ά
if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE contractorid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"+userinfo.getDeptID()+"')";
}
condition += " and depttype=2 ";
List deptCollection = statdao.getSelectForTag("contractorname","contractorid","contractorinfo",condition);
request.setAttribute("deptCollection",deptCollection);
%>
<body>
	<div id="groupDivId">
		<template:titile value="���ӹܵ�����Ϣ" />

		<html:form onsubmit="return isValidForm()" method="Post"
			action="/PipeSegmentAction.do?method=addPipeSegment">
			<template:formTable contentwidth="220" namewidth="220">
				<template:formTr name="�ܵ��α��">
					<html:text property="pipeno" styleClass="inputtext"
						style="width:160" maxlength="10" />
				</template:formTr>
				<template:formTr name="�ܵ�������">
					<html:text property="pipename" styleClass="inputtext"
						style="width:160" maxlength="240" />
				</template:formTr>
				 <template:formTr name="��������" isOdd="false">
				<apptag:setSelectOptions valueName="parentRegionCellection"
					tableName="region" columnName1="regionname" columnName2="regionid"
					region="true" order="regionid"
					condition="substr(REGIONID,3,4) != '1111' " />
				<html:select property="regionid" styleClass="inputtext"
					style="width:160">
					<html:options collection="parentRegionCellection" property="value"
						labelProperty="label" />
				</html:select>
			</template:formTr>
				<logic:notEmpty name="coninfo">
            <template:formTr name="��ά��λ" tagID="conTr">
              <select name="contractorid" class="inputtext" style="width:160px" id="dId" onclick="onChangeCon">
                <logic:present name="coninfo">
                  <logic:iterate id="coninfoId" name="coninfo">
                    <option value="<bean:write name="coninfoId" property="contractorid"/>">
                    <bean:write name="coninfoId" property="contractorname"/></option>
                  </logic:iterate>
                </logic:present>
              </select>
            </template:formTr>
    <logic:present name="coninfo">
              <select name="workID" style="display:none" >
                <logic:iterate id="coninfoId" name="coninfo">
                  <option value="<bean:write name="coninfoId" property="contractorid"/>"><bean:write name="coninfoId" property="regionid"/>--<bean:write name="coninfoId" property="contractorname"/></option>
                </logic:iterate>
              </select>
            </logic:present>
        </logic:notEmpty>
  
     <logic:empty name="reginfo">
      <template:formTr name="��ά��λ" isOdd="false">
        <html:select property="contractorid" styleClass="inputtext" style="width:160">
          <html:options collection="deptCollection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
   </logic:empty>
				<template:formTr name="ά������">
					<html:text property="county" styleClass="inputtext"
						style="width:160" maxlength="120" />
				</template:formTr>
				<template:formTr name="Ƭ��">
					<html:text property="area" styleClass="inputtext" style="width:160"
						maxlength="120" />
				</template:formTr>
				<template:formTr name="��������">
					<html:text property="town" styleClass="inputtext" style="width:160"
						maxlength="120" />
				</template:formTr>
				<template:formTr name="����">
					<html:text property="length" styleClass="inputtext"
						style="width:160" maxlength="120" />
				</template:formTr>
				<template:formTr name="�ܵ�����">
					<html:text property="material" styleClass="inputtext"
						style="width:160" maxlength="120" />
				</template:formTr>
				<template:formTr name="��Ȩ">
					<html:text property="right" styleClass="inputtext"
						style="width:160" maxlength="120" />
				</template:formTr>

				<template:formTr name="�ܿ���">
					<html:text property="pipehole" styleClass="inputtext"
						style="width:160" maxlength="120" />
				</template:formTr>

				<template:formTr name="�ܿ׹��">
					<html:text property="pipetype" styleClass="inputtext"
						style="width:160" maxlength="120" />
				</template:formTr>

				<template:formTr name="�ӹ�����">
					<html:text property="subpipehole" styleClass="inputtext"
						style="width:160" maxlength="120" />
				</template:formTr>

				<template:formTr name="δ���ӹ���">
					<html:text property="unuserpipe" styleClass="inputtext"
						style="width:160" maxlength="120" />
				</template:formTr>

				<template:formTr name="�Ƿ�����">
					<html:select property="isaccept" styleClass="inputtext"
						style="width:160">
						<html:option value="0">��</html:option>
						<html:option value="1">��</html:option>
					</html:select>
				</template:formTr>

				<template:formTr name="��ע">
					<html:text property="remark1" styleClass="inputtext"
						style="width:160;height:100;" maxlength="120" />
				</template:formTr>

				<template:formTr name="��ע2">
					<html:text property="remark2" styleClass="inputtext"
						style="width:160;height:100;" maxlength="120" />
				</template:formTr>

				<template:formTr name="ͼֽ���">
					<html:text property="bluepointno" styleClass="inputtext"
						style="width:160" maxlength="120" />
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit styleClass="button">����</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">ȡ��</html:reset>
					</td>
					<td>
						<html:button property="action" styleClass="button2" styleId="upId"
							onclick="showupload()">����ܵ�����Ϣ</html:button>
					</td>
					<td>
						<html:button property="action" styleClass="button2"
							onclick="downloadFile()">�ܵ���ģ������</html:button>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="addGoBack()">����</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</div>
	<div id="pupDivId" style="display: none">
		<html:form styleId="upform"
			action="/PipeSegmentAction?method=upLoadShow" method="post"
			enctype="multipart/form-data">
			<table align="center" border="0" width="600" height="100%">
				<tr>
					<td valign="top" height="100%">
						<table align="center" border="0">
							<tr>
								<td align="left" height="50">
									<br />
									<br />
									<br />
									<br />
									<br />
									<br />
									<br />
									<b>��ѡ��Ҫ�����Excel�ļ�:</b>
									<br />
								</td>
							</tr>
							<tr>
								<td>
									<html:file property="file" style="width:300px"
										styleClass="inputtext" />
								</td>
							</tr>
							<tr>
								<td align="center" valign="middle" height="60">
									<html:button styleClass="button" value="��������" property="sub"
										onclick="javascript:upform.submit()">�ύ</html:button>
									<html:button value="ȡ������" styleClass="button" property="action"
										onclick="noupload()" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</html:form>
	</div>
	<iframe name="hiddenInfoFrame" style="display: none"></iframe>
</body>

