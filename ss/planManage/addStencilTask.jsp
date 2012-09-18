<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.planinfo.services.*"%>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo"%>
<%@page import="com.cabletech.commons.config.*"%>
<%
  	UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
  	String regionid = userinfo.getRegionID();
  	TaskBO taskbo = new TaskBO();
  	Vector vct = taskbo.getTaskOperations(regionid);
	//Ѳ�����Ϣ
  	Vector pointUnitsVct = new Vector();
  	String strsubline = (String)session.getAttribute("strsubline");
  	GisConInfo gisip = GisConInfo.newInstance();
%>
<SCRIPT language=javascript src="${ctx}/js/prototype.js" type=""></SCRIPT>
<script language="javascript">
var functionid = "1";
//�Ƿ�ѡȡ��������
var ifCheckedFlag = 0;
var isCheckedPoint = 0;
//��֤����
function checkInput(){
    if(document.stencilTaskBean.description.value.length == 0){
        alert("��������������ƣ�");
        document.stencilTaskBean.description.focus();
        return false;
    }
    if(document.stencilTaskBean.lineleveltext.value.length == 0){
        alert("��ѡ���������·����");
        document.taskBean.describtion.focus();
        return false;
    }
    if(ifCheckedFlag == 0){
        alert("��ѡ��������ͣ�");
        return false;
    }
	if(isCheckedPoint ==0){
		alert("��ѡ������Ѳ��㣡");
        return false;
	}
    if(document.stencilTaskBean.excutetimes.value.length == 0){
        alert("�������Ѳ�������");
        document.stencilTaskBean.excutetimes.focus();
        return false;
    }else{
		 var mysplit = /^\d{1,2}$/;
		 if(!mysplit.test(document.stencilTaskBean.excutetimes.value)){
            document.stencilTaskBean.excutetimes.value="";
        	alert("Ѳ�����ֻ��������,�ҽ�Ϊ����,����������");
        	return false;
        }
		 if(document.stencilTaskBean.excutetimes.value == 0){
			alert("Ѳ���������Ϊ0��ֻ�ܴ��ڵ���1��");
			return false;
		 }
	}
	var subline = document.getElementById('sublineid').value;
	if(document.stencilTaskBean.typeCheck.length== undefined){
		var temp = document.stencilTaskBean.typeCheck.value;
				if(subline.indexOf(temp) != -1){
					alert("ͬһ�߶β��ܰ����ڶ����������!");
					return false;
				}
	}else{
		for(var j=0;j<document.stencilTaskBean.typeCheck.length;j++){
			if(document.stencilTaskBean.typeCheck[j].checked == true){
				var temp = document.stencilTaskBean.typeCheck[j].value;
				if(subline.indexOf(temp) != -1){
					alert("ͬһ�߶β��ܰ����ڶ����������!");
					return false;
				}
			}			
		}
	}
    return true;

}
//�����Ƿ�����
function valiD(id){
	obj = document.getElementById(id);
    var mysplit = /^\d{1,2}$/;
    if(mysplit.test(obj.value)){
    	return true;
    }else{
    	obj.value="";
        alert("����д�Ĳ�������,����������");
    }
}
//��������
function saveData(ss){
	if(document.stencilTaskBean.subtaskpoint==null){
		alert("��Ѳ��Ա�ڴ���·������û�и�����߶Σ���ѡ���Ѳ��Ա��Ӧ��Ѳ���߶ε���·���� ");
		return false;
	}
	ifCheckedFlag = 0;
	isCheckedPoint = 0;
  	for(var i = 0; i < document.stencilTaskBean.operationcheck.length; i ++){
       	if(document.stencilTaskBean.operationcheck[i].checked == true){
           	ifCheckedFlag++;
           	break;
        }
  	}
	if(!document.stencilTaskBean.subtaskpoint.length && document.stencilTaskBean.subtaskpoint.checked){
    	isCheckedPoint++;
   	}else{
		for(var j=0;j<document.stencilTaskBean.subtaskpoint.length;j++){
			if(document.stencilTaskBean.subtaskpoint[j].checked == true){
				isCheckedPoint++;
				break;
            }
		}
	}
    var k = document.stencilTaskBean.linelevel.selectedIndex;
    var levelText = document.stencilTaskBean.linelevel[k].text;
	
    document.stencilTaskBean.lineleveltext.value = levelText;
	if(ss=='1'){
		document.getElementById('SS').value ="1";
	}else{
		document.getElementById('SS').value="0";
	}

    if(checkInput()){
        document.stencilTaskBean.submit();
    }
}
//������������
function setTaskName(objN,name){
    var tempValue = "";
    var operationSize = 0;
    for(var i = 0; i < document.stencilTaskBean.operationcheck.length; i ++){
	    if(document.stencilTaskBean.operationcheck[i].checked == true){
    		document.stencilTaskBean.description.value = document.stencilTaskBean.operationname[i].value;
            operationSize++;
        }
    }
    if(operationSize > 1){
    	document.stencilTaskBean.description.value = "�ۺ�ά������";
    }
}

getSameLevelPoints = function(){
	var url = "${ctx}/StencilTaskAction.do?method=getTaskPatrolPoint&linelevel="+document.stencilTaskBean.linelevel.value+"&fId=2";
	var ajax = new Ajax.Request(
		url,{
        	method:'post',
        	onLoading:function(){
        		document.getElementById('TaskSublines').innerHTML = "<img src='images/indicator.gif'/>���ڼ���,���Ժ�.."; 
        	},
        	onSuccess:function(transport){
        		document.getElementById('TaskSublines').innerHTML = transport.responseText;
        	},
        	onFailure: function(){ alert('��������쳣...'); }
        }
	);
};
/*
function getSameLevelPoints(){
	var pageName = "${ctx}/StencilTaskAction.do?method=getTaskPatrolPoint&fId=2&linelevel=" + document.stencilTaskBean.linelevel.value;
	location.href = pageName;
}*/

function goBack(){
	var url = "${ctx}/planManage/showStencil.jsp";
	self.location.replace(url);
}
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
</script>
	<body >
<template:titile value="���Ӽƻ�ģ��"/>
	<html:form action="/StencilTaskAction?method=addStencilTask">
	<input name="ss" type="hidden" id ="SS" value="0"><!-- 0���������һ�� 1������� -->
  	<input name="strsubline" id="sublineid" value="<%=strsubline%>" type="hidden"/>
  	<template:formTable contentwidth="450" namewidth="150">
    <template:formTr name="��������">
      <html:text property="description" styleClass="inputtext" maxlength="20" style="width:200px"/>
    </template:formTr>

    <template:formTr name="��Ӧ��·����" isOdd="false">
    <html:hidden property="lineleveltext" />
    <apptag:setSelectOptions columnName1="name" columnName2="code" tableName="lineclassdic" valueName="linetypeColl" order="code"/>
      <html:select property="linelevel" styleClass="inputtext" style="width:200px" onchange="getSameLevelPoints()">
      	<option value="">��ѡ����·����</option>
        <html:options collection="linetypeColl" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
	 <template:formTr name="�������">
    	 <html:text property="evaluatepoint" styleClass="inputtext"  onkeyup="value=value.replace(/[^\d{2,5}]/g,'')" maxlength="5" style="width:200px"></html:text>
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
    <div style="color:red">Ѳ�������ʾ����Ҫ������ѡ���߶�Ѳ����Ѳ�����</div>
      <html:text property="excutetimes"  styleId="exID" onblur="valiD(id)" value="1" maxlength="2" styleClass="inputtext" style="width:200px"/>
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
      <td titile="����������Ӳ���">
        <html:button property="action" styleClass="button" onclick="saveData(0)">����</html:button>
      </td>
      <td titile="�����Ӳ��������ƻ�ģ����ҳ��">
        <html:button property="action" styleClass="button" onclick="saveData(1)">���</html:button>
      </td>
      <td titile="ȡ��������Ӳ��������ƻ�ģ����ҳ��">
      <html:button property="action" styleClass="button" onclick="goBack()">ȡ��</html:button>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>

</body>
<iframe id="hiddenIframe" style="display:none"></iframe>
