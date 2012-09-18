<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.planinfo.services.*"%>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo"%>
<%@page import="com.cabletech.commons.config.*"%>
<%@page import="com.cabletech.planinfo.beans.*"%>
<%@page import="com.cabletech.planinfo.domainobjects.*"%>	
<%
  GisConInfo gisip = GisConInfo.newInstance();
  UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
  String regionid = userinfo.getRegionID();
  TaskBO taskbo = new TaskBO();
  Vector vct = taskbo.getTaskOperations(regionid);
  //Ѳ�����Ϣ
  Vector pointUnitsVct = new Vector();
  pointUnitsVct = (Vector) request.getAttribute("pointlist");
  String sel_taskpoint = (String) request.getAttribute("taskpoint");

  String patrolid = (String) request.getAttribute("patrolid");
  String linelevel = (String) request.getAttribute("linelevel");
  
%>
<script language="javascript">
function open_map(ID,FORMAT){
 	URL="http://<%=gisip.getServerip()%>:<%=gisip.getServerport()%>/WEBGIS/gisextend/igis.jsp?actiontype=003&lineid="+ID;
 	myleft=(screen.availWidth-650)/2;
 	mytop=100;
 	mywidth=650;
 	myheight=500;
 	if(FORMAT=="1"){
    	myleft=0;
    	mytop=0;
    	mywidth=screen.availWidth-10;
    	myheight=screen.availHeight-40;
 	}
 	window.open(URL,"","height="+myheight+",width="+mywidth+",status=1,toolbar=no,resizable=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft);
}

var functionid = "1";
//�Ƿ�ѡȡ��������
var ifCheckedFlag = 0;
var ifCheckedPoint = 0;

function checkInput(){
	if(document.taskBean.describtion.value == ""){
		alert("��������������ƣ�");
		document.taskBean.describtion.focus();
		return false;
	}
	if(ifCheckedFlag == 0){
		alert("��ѡ��������ͣ�");
		return false;
	}
	if(ifCheckedPoint == 0){
		alert("��ѡ������Ѳ��㣡");
		return false;
	}
	if(document.taskBean.excutetimes.value.length == 0){
		alert("�������Ѳ�������");
		document.taskBean.excutetimes.focus();
		return false;
	}else{
		 var mysplit = /^\d{1,2}$/;
		 if(mysplit.test(document.taskBean.excutetimes.value)){
          return true;
        }
        else{
            document.taskBean.excutetimes.value="";
        	alert("Ѳ�����ֻ��������,�ҽ�Ϊ����,����������");
        	return false;
        }
		 if(document.taskBean.excutetimes.value == 0){
			alert("Ѳ���������Ϊ0��ֻ�ܴ��ڵ���1��");
			return false;
		 }
	}
	return true;
}
function saveData(ss){
	if(document.taskBean.subtaskpoint==null){
		alert("��Ѳ��Ա�ڴ���·������û�и�����߶Σ���ѡ���Ѳ��Ա��Ӧ��Ѳ���߶ε���·���� ");
		return false;
	}
	for(var i = 0; i < document.taskBean.operationcheck.length; i ++){
		if(document.taskBean.operationcheck[i].checked == true){
			ifCheckedFlag++;
			break;
			}
	}
	for(var i = 0; i < document.taskBean.subtaskpoint.length; i ++){
		if(document.taskBean.subtaskpoint[i].checked == true){
			ifCheckedPoint++;
			break;
		}
	}
	if(ss=='1'){
		document.getElementById('SS').value ="1";
	}else{
		document.getElementById('SS').value="0";
	}
	if(checkInput()){
		document.taskBean.submit();
	}
}

//�Զ�������������
function setTaskName(objN,name){
	var tempValue = "";
	var operationSize = 0;
		for(var i = 0; i < document.taskBean.operationcheck.length; i ++){
			if(document.taskBean.operationcheck[i].checked == true){
				document.taskBean.describtion.value = document.taskBean.operationname[i].value;
				operationSize++;
			}
		}
		if(operationSize > 1){
			document.taskBean.describtion.value = "�ۺ�ά������";
		}
}
//��ʼ��������������
function presetData(){
	for(var i = 0; i < document.taskBean.operationcheck.length; i ++){
		
		for(var k = 0; k < document.getElementById('tasklist').options.length; k ++){
			if(document.taskBean.operationcheck[i].value == document.getElementById('tasklist').options[k].value){
				document.taskBean.operationcheck[i].checked = true;
			}
		}
	}
}
//ѡ�񸸽ڵ��µ����к���
var singledouble=0;
function setSon(objN){
	var v = objN.value;
	var objP = document.getElementsByName("subtaskpoint");
	var tr_id = "detailTr_"+objN.value;
	var objPointid;
	var groupBysublinetr=document.getElementById(tr_id).getElementsByTagName("input");
	var prefix = "point_"+v;
	var len = groupBysublinetr.length-1;
	do{
		if(singledouble==0){
			groupBysublinetr[len].checked = objN.checked;
		}else{
			if(len%2==0 && singledouble==2){//˫��
				groupBysublinetr[len].checked = objN.checked;
			}
			if(len%2==1 && singledouble==1){//����
				groupBysublinetr[len].checked = objN.checked;
			}
		}
	}while(--len>=0);
}

//���ø��ڵ��Ƿ�ѡ��
//�������⣬ѡ��һ��ʱ���ڵ�ͬʱȡ����
function setParent(objN,v){
	var objC = document.getElementsByName('typeCheck');
	if(objC.length == undefined){
		objC.checked = true;
	}else{
		for(var i=0;i<objC.length;i++){
			if(objC[i].value==v){
				objC[i].checked = objN.checked;	
			}
		}
	}
}
//��ʾ�����ӽڵ�
function showHideSubTr(objN,sublineid){
	var tr_id='detailTr_'+sublineid;
	var i = objN.index;
	try{
		if(document.getElementById(tr_id)[i].style.display == ""){
			document.getElementById(tr_id)[i].style.display = "none";
		}else{
			document.getElementById(tr_id)[i].style.display = "";
		}
	}catch (e) {
		if(document.getElementById(tr_id).style.display == ""){
			document.getElementById(tr_id).style.display = "none";
		}else{
			document.getElementById(tr_id).style.display = "";
		}
	}
	
}
//ȫѡ����ȫѡ
function checkAll(val,sindou){
	singledouble = sindou;
	var objC = document.getElementsByName('typeCheck');
	if(objC != undefined){
		if(objC.length == undefined){
			objC.checked = val;
			setSon(objC);
		}else{
			for(var i=0;i<objC.length;i++){
				objC[i].checked = val;
				setSon(objC[i]);
			}
		}
	}
}
var sd = 0;
//��˫��ѡ��
function checksinge(s){
	checkAll(false,0);
	if(sd==s){
		sd=0;
		checkAll(true,2);
		document.getElementById("singledouble").value="ѡ��˫��"
	}else{
		sd = s;
		checkAll(true,1);
		document.getElementById("singledouble").value="ѡ����"
	}
}
/*
function getSameLevelPoints(){
	var pageName = "${ctx}/TaskAction.do?method=getTaskPatrolPoint&fId=2&linelevel=" + taskBean.linelevel.value;
	location.href = pageName;
}*/

//����ʾѲ�����Ϣʱ���丸�ڵ����޽ڵ㱻ѡ��ʱ�����ڵ�Ҳ����ѡ��
/*function checkPoint(){
	var objC = document.all.typeCheck;
	var size = document.all.lineSize.value;
	for(var i=0;i<size;i++){
		var str = "isChecked"+i;
		var objCH = document.getElementById(str).value
		var isChecked = objCH.substring(0,1);
		var index = objCH.substring(1,objCH.length);	
		if(isChecked =="0"){
			objC[index].checked = "";
		}
	}
}*/
</script>
<br>
<template:titile value="�޸ļƻ�������"/>
<body onload="presetData();">
	<html:form action="/TaskAction?method=addPlanTask">
		<input name="ss" type="hidden" id ="SS" value="0"><!-- 0���������һ�� 1������� -->
	  	<template:formTable >
	  	
	    <template:formTr name="��������">
	      	<html:text property="describtion" name="taskinfo" styleClass="inputtext" style="width:200px" maxlength="20"/>
	    </template:formTr>
		
		<template:formTr name="��Ӧ��·����" isOdd="false">
			<html:hidden property="lineleveltext" name="taskinfo"/>
			<html:hidden property="linelevel" name="taskinfo"/>
			<apptag:setSelectOptions columnName1="name" columnName2="code" tableName="lineclassdic" valueName="linetypeColl" order="code"/>
			<html:select property="linelevel" name="taskinfo" styleClass="inputtext" style="width:200px" disabled="true"  onchange="getSameLevelPoints()">
				<html:options collection="linetypeColl" property="value" labelProperty="label"/>
			</html:select>
	    </template:formTr>
	    <template:formTr name="�������">
    	 <html:text property="evaluatepoint" name="taskinfo" styleClass="inputtext" onkeyup="value=value.replace(/[^\d{2,5}]/g,'')" maxlength="5" style="width:200px"></html:text>
    	</template:formTr>
	    
	    <template:formTr name="����Ѳ���" isOdd="false">
	    <div width="100%">
	    	<input type="button" id="uncheckall" onClick="checkAll(false,0)" value="ȫ��ѡ"> 
	    	<input type="button" id="checkall" onClick="checkAll(true,0)" value="ȫѡ"> 
	    	<input type="button" id="singledouble" onClick="checksinge(1)" value="��/˫��ѡ��"> 
	    </div>
      	<div id="TaskSublines" width="100%">
	    	<%=request.getAttribute("tasksublineHtm") %>
	    </div>
    	</template:formTr>
	    <template:formTr name="Ѳ�����" >
		      <html:text property="excutetimes" name="taskinfo" styleClass="inputtext"  style="width:160px" maxlength="2"/>
		      <input type="text" class="inputtext" style="border:0;background-color:transparent;width:30px" value="��" readonly>
	    </template:formTr>
	    
	    <template:formTr name="��������" isOdd="false">
	    <div style="color:red">����ѡ��ΪѲ����Ա����Ѳ���ʱ����Ҫ���Ĳ����������ο���</div>
		    <%
		      if (vct.size() > 0) {
		        out.println("<table border=\"0\" cellspacing=\"0\" cellpadding=\"5\">");
		        for (int i = 0; i < vct.size(); i++) {
		          Vector oneRecord = (Vector) vct.get(i);
		          String id = (String) oneRecord.get(0);
		          String name = (String) oneRecord.get(1);
		          out.println("<tr valign=\"middle\"> ");
		          out.println("	<td>           ");
		          out.println("		<input type=\"checkbox\" name=\"operationcheck\" value=\"" + id + "\" onclick=setTaskName(this,'"+ name +"')>  ");
		          out.println("	</td>     ");
		          out.println("	<td>  ");
		          out.println("		<input type=\"text\" name=\"operationname\" class=\"inputtext\" style=\"border:0;background-color:transparent;width:80\" value=\"" + name + "\" hiddenValue=\"" + id + "\" readonly>");
		          out.println("	</td>  ");
		          out.println("</tr>   ");
		        }
		        out.println("</table>");
		      }
		    %>
	    </template:formTr>
	    <template:formSubmit>
		      <!--  td>
		      
		        <html:button property="action" styleClass="button" onclick="saveData(0)">����</html:button>
		      </td>-->
		      <td>
		        <html:button property="action" styleClass="button" onclick="saveData(1)">���</html:button>
		      </td>
		</template:formSubmit>
		
	  	</template:formTable>
	</html:form>
	<select name="tasklist" id="tasklist" style="display:none">
	<% 
		TaskBean taskinfo = (TaskBean)request.getAttribute("taskinfo");
		List toList = (List)taskinfo.getTaskOpList();
		for (int i = 0; i < toList.size(); i++) {
	    	TaskOperationList tol = (TaskOperationList)toList.get(i);
	    	out.println("<option value=" + (String) tol.getOperationid() + ">");
	    	out.println((String) tol.getOperationid());
	    	out.println("</option>");
	  	}
	%>
	</select>
</body>
