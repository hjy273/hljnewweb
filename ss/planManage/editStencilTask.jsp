<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.planinfo.services.*"%>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo"%>
<%@page import="com.cabletech.commons.config.*"%>
<%@page import="com.cabletech.planinfo.beans.*"%>
<%@page import="com.cabletech.planinfo.domainobjects.*"%>	
<%
  UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
  String regionid = userinfo.getRegionID();
  TaskBO taskbo = new TaskBO();
  Vector vct = taskbo.getTaskOperations(regionid);
  //巡检点信息
  //Vector pointUnitsVct = new Vector();
  //pointUnitsVct = (Vector) request.getAttribute("pointlist");
  //String sel_taskpoint = (String) request.getAttribute("taskpoint"); 
  //System.out.println(pointUnitsVct.size());
 // String patrolid = (String) request.getAttribute("patrolid");
  //String linelevel = (String) request.getAttribute("linelevel");
  
%>
<script language="javascript">
<!--
var functionid = "1";
//是否选取了子任务
var ifCheckedFlag = 0;
var isCheckedPoint =0;

function checkInput(){

	if(document.stencilTaskBean.description.value == ""){
		alert("请输入该任务名称！");
		document.stencilTaskBean.description.focus();
		return false;

	}
	if(ifCheckedFlag == 0){
		alert("请选择操作类型！");
		return false;
	}
	if(isCheckedPoint ==0){
		alert("请选择任务巡检点！");
        return false;
	}
	if(document.stencilTaskBean.excutetimes.value.length == 0){
		alert("请输入该巡检次数！");
		document.stencilTaskBean.excutetimes.focus();
		return false;
	}else{
		 var mysplit = /^\d{1,2}$/;
		 if(mysplit.test(document.stencilTaskBean.excutetimes.value)){
          return true;
        }
        else{
            document.stencilTaskBean.excutetimes.value="";
        	alert("巡检次数只能是数字,且仅为整数,请重新输入");
        	return false;
        }
		 if(document.stencilTaskBean.excutetimes.value == 0){
			alert("巡检次数不能为0，只能大于等于1。");
			return false;
		 }
	}
	return true;
}
function saveData(ss){
	if(document.stencilTaskBean.subtaskpoint==null){
		alert("该巡检员在此线路级别内没有负责的线段，请选择该巡检员对应的巡检线段的线路级别 ");
		return false;
	}
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
	//var k = document.stencilTaskBean.linelevel.selectedIndex;
	//var levelText = document.stencilTaskBean.linelevel[k].text;

	//document.stencilTaskBean.lineleveltext.value = levelText;
	if(ss=='1'){
		document.getElementById('SS').value ="1";
	}else{
		document.getElementById('SS').value="0";
	}
	if(checkInput()){
		document.stencilTaskBean.submit();
	}
}

//自动分配任务名称
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
			document.stencilTaskBean.description.value = "综合维护任务";
		}
}
//初始化操作类型数据
function presetData(){
	for(var i = 0; i < document.stencilTaskBean.operationcheck.length; i ++){
		for(var k = 0; k <  document.getElementById('tasklist').options.length; k ++){
			if(document.stencilTaskBean.operationcheck[i].value == document.getElementById('tasklist').options[k].value){
				document.stencilTaskBean.operationcheck[i].checked = true;
			}
		}
	}
	//var j = document.stencilTaskBean.linelevel.selectedIndex;
	//var levelText = document.stencilTaskBean.linelevel[j].text;
	//document.stencilTaskBean.lineleveltext.value = levelText;

}
//选择父节点下的所有孩子
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
				if(len%2==0 && singledouble==2){
					groupBysublinetr[len].checked = objN.checked;
				}
				if(len%2==1 && singledouble==1){
					groupBysublinetr[len].checked = objN.checked;
				}
			}
	}while(--len>=0);
}


function setParent(objN,v){
	var objC = document.getElementsByName('typeCheck');
	//var v = objN.parentV;
	var typecount = 0;
	var checkcount = 0;
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
//显示隐藏子节点
function showHideSubTr(objN,sublineid){
	var tr_id='detailTr_'+sublineid;
	var i = objN.index;
	//var d = document.getElementById('detailTr_124');
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
//全选，不全选
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
//单双数选择
function checksinge(s){
	checkAll(false,0);
	if(sd==s){
		sd=0;
		checkAll(true,2);
		document.getElementById("singledouble").value="选择双数"
	}else{
		sd = s;
		checkAll(true,1);
		document.getElementById("singledouble").value="选择单数"
	}
}
function getSameLevelPoints(){
	var pageName = "${ctx}/StencilTaskAction.do?method=getTaskPatrolPoint&fId=2&linelevel=" + document.stencilTaskBean.linelevel.value;
	location.href = pageName;
}

//-->
</script>
<br>
<template:titile value="修改计划子任务"/>
<body onload="presetData();">
	<html:form action="/StencilTaskAction?method=addStencilTask">
	<input name="ss" type="hidden" id ="SS" value="0"><!-- 0代表继续下一步 1代表完成 -->
	<template:formTable >
		<template:formTr name="任务名称">
			<html:text property="description" name="taskinfo" styleClass="inputtext" style="width:200px" maxlength="20" onblur="value=value.replace(/\s/g,'')"/>
	    </template:formTr>
		
		<template:formTr name="对应线路级别" isOdd="false">
			<html:hidden property="lineleveltext" name="taskinfo"/>
			<html:hidden property="linelevel" name="taskinfo"/>
			<apptag:setSelectOptions columnName1="name" columnName2="code" tableName="lineclassdic" valueName="linetypeColl" order="code"/>
			<html:select property="linelevel" name="taskinfo" styleClass="inputtext" style="width:200px" disabled="true" onchange="getSameLevelPoints()">
				<html:options collection="linetypeColl" property="value" labelProperty="label"/>
			</html:select>
	    </template:formTr>
	    
	    <template:formTr name="估算点数">
    	 <html:text property="evaluatepoint" name="taskinfo" styleClass="inputtext" onkeyup="value=value.replace(/[^\d{2,5}]/g,'')" maxlength="5" style="width:200px"></html:text>
    	</template:formTr>
	    
	    <template:formTr name="任务巡检点" isOdd="false">
	     <div width="100%">
	    	<input type="button" id="uncheckall" onClick="checkAll(false,0)" value="全不选"> 
	    	<input type="button" id="checkall" onClick="checkAll(true,0)" value="全选"> 
	    	<input type="button" id="singledouble" onClick="checksinge(1)" value="单/双数选择"> 
	    </div>
      	<div id="TaskSublines" width="100%">
    	<%=request.getAttribute("tasksublineHtm") %>
    	</div>
    	</template:formTr>
	    <template:formTr name="巡检次数" >
		      <html:text property="excutetimes" name="taskinfo" styleClass="inputtext"  style="width:160px" maxlength="2"/>
		      <input type="text" class="inputtext" style="border:0;background-color:transparent;width:30px" value="次" readonly>
	    </template:formTr>
	    <template:formTr name="操作类型" isOdd="false">
	    <div style="color:red">以下选项为巡检人员到达巡检点时可能要做的操作（仅供参考）</div>
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
		        <html:button property="action" styleClass="button" onclick="saveData(0)">继续</html:button>
		      </td>-->
		      <td>
		        <html:button property="action" styleClass="button" onclick="saveData(1)">完成</html:button>
		      </td>
		    </template:formSubmit>
	  </template:formTable>
</html:form>
<select name="tasklist" id="tasklist" style="display:none">
<% 
	StencilSubTaskBean taskinfo = (StencilSubTaskBean)request.getAttribute("taskinfo");
	List toList = (List)taskinfo.getStencilsubtaskop();
	for (int i = 0; i < toList.size(); i++) {
    	StencilSubTaskOper tol = (StencilSubTaskOper)toList.get(i);
    	out.println("<option value=" + (String) tol.getToid() + ">");
    	out.println((String) tol.getToid());
    	out.println("</option>");
  	}
%>
</select>

</body>
