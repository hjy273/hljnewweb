<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<%
  Vector pointUnitsVct = new Vector();
  pointUnitsVct = (Vector) request.getAttribute("pointlist");
  //System.out.println(pointUnitsVct.size());
  String patrolid = (String) request.getAttribute("patrolid");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>增加编辑操作点</title>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
<!--

function showHideSubTr(objN){

	var i = objN.index;

	try{
		if(detailTr[i].style.display == ""){
			detailTr[i].style.display = "none";
		}else{
			detailTr[i].style.display = "";

		}
	}catch (e) {
		if(detailTr.style.display == ""){
			detailTr.style.display = "none";
		}else{
			detailTr.style.display = "";

		}
	}

}

function setSon(objN){

	var v = objN.value;
	try{
		for(var i = 0; i < pointCheck.length; i++){

			if(pointCheck[i].parentV == v){
				pointCheck[i].checked = objN.checked;
			}

		}
	}catch(e){
		pointCheck.checked = objN.checked;
	}

}

function setParent(objN){

	var v = objN.parentV;
	var typecount = 0;
	var checkcount = 0;

	try{
		for(var k = 0; k < pointCheck.length; k++){
			if(pointCheck[k].parentV == v){
				typecount ++;
				if(pointCheck[k].checked == true ){
					checkcount++;
				}
			}
		}
	}catch (e){
					if(pointCheck.parentV == v){
				typecount ++;
				if(pointCheck.checked == true ){
					checkcount++;
				}
			}
	}

	try{
		for(var i = 0; i < typeCheck.length; i++){

			if(typeCheck[i].value == v){

				if(checkcount == 0){
					typeCheck[i].checked == false;
				}else if(checkcount == typecount){
					typeCheck[i].checked = true;
				}else if(checkcount < typecount){
					//typeCheck[i].checked = true;
					//typeCheck[i].checked = indeterminate;
				}
			}


		}
	}catch (e) {
		if(typeCheck.value == v){

			if(checkcount == 0){
				typeCheck.checked == false;
			}else if(checkcount == typecount){
				typeCheck.checked = true;
			}else if(checkcount < typecount){
				//typeCheck[i].checked = true;
				//typeCheck[i].checked = indeterminate;
			}
		}
	}

}

function preSetCheck(){

	/*
	try{
		for(var i = 0; i < pointCheck.length; i++){

			for(var k = 0; k < opener.taskBean.subtaskpoint.options.length; k++){
				if(pointCheck[i].value == opener.taskBean.subtaskpoint.options[k].value){
					pointCheck[i].checked = true;
				}
			}

			setParent(pointCheck[i]);

		}
	}catch(e){
	}
	*/
}

function apply(){

	removeAllOptions();

	for(var i = 0; i < pointCheck.length; i++){

		if(pointCheck[i].checked == true){

			var lastLineNum = subtaskpoint.options.length;

			subtaskpoint.options[lastLineNum] = new Option(pointCheck[i].nameV, pointCheck[i].value);

		}

	}

	opener.subTaskPointListSpan.innerHTML = tempSpan.innerHTML;

	self.close();


}

function removeAllOptions(){

	for(var i = 0; i < subtaskpoint.options.length; i ++ ){
		subtaskpoint.options.remove(0);
		i--;
	}

}

function loadPoints(flag,patrolid){
	var pageName = "${ctx}/TaskAction.do?method=getSubTaskList&fId="+ flag +"&excutor=" + patrolid;
	self.location.replace(pageName);
}

//-->
</script></head>
<body>
<%
if(pointUnitsVct.size() > 0){
%>
<table border="0" cellspacing="0" cellpadding="0">
  <tr align="center">
    <td width="120" class="lbutton" style="cursor:hand">
      <a href="javascript:loadPoints(2,'<%=patrolid%>')">按所属线段选取</a>
    </td>
    <td width="120" class="lbutton" style="cursor:hand">
      <a href="javascript:loadPoints(1,'<%=patrolid%>')">按点类型选取</a>
    </td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="5">
<%
  for (int i = 0; i < pointUnitsVct.size(); i++) {
    Vector oneUnitVct = (Vector) pointUnitsVct.get(i);
%>
  <tr id="typeTr" index="<%=i%>" onclick>
    <td colspan="2">
      <input type="checkbox" name="typeCheck" value="<%=(String)oneUnitVct.get(0)%>" onclick="setSon(this)" checked>
      <input type="text" index="<%=i%>" style="border:0;background-color:transparent;width:160;cursor:hand" value="<%=(String)oneUnitVct.get(1)%>" onclick="showHideSubTr(this)" readonly>
    </td>
  </tr>
  <tr id="detailTr" index="<%=i%>" status="" style="display:none">
    <td colspan="2">
      <table width="92%" border="0" cellspacing="0" cellpadding="0">
      <%
        Vector pointsVct = (Vector) oneUnitVct.get(2);
        for (int k = 0; k < pointsVct.size(); k++) {
          Vector tempV = (Vector) pointsVct.get(k);
      %>
        <tr>
          <td width="36">&nbsp;</td>
          <td width="92%">
            <input type="checkbox" name="pointCheck" parentV="<%=(String)oneUnitVct.get(0)%>" value="<%=(String)tempV.get(0)%>" onclick="setParent(this)" nameV="<%=(String)tempV.get(1)%>" checked>
<%=(String)tempV.get(1)%>          </td>
        </tr>
      <%}      %>
      </table>
    </td>
  </tr>
<%}%>
</table>
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr><td height="2" background="${ctx}/images/bg_line.gif"><img src="${ctx}/images/1px.gif" width="1" height="1"></td></tr>
    <tr><td align="right">
	    <table  border="0" align="center" cellpadding="0" cellspacing="0">
	    <tr align="center">
	      <td>
	        <input type="button" value="确定" onclick="apply()" class="button">
	      </td>
	      <td>
	        <input type="button" class="button" value="取消" onclick="self.close()">
	      </td>
	    </tr>
	    </table>
    </td></tr>
  </table>
<%

}else{
%>
<table width="75" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="85%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
  <tr class="trcolor">
    <td class="tdulleft" width="19%">提示信息 : </td>
    <td class="tdulright" width="81%">该巡检员暂时没有负责的线段，请在线段管理模块中分配后重试 !
</td>
  </tr>
</table>
<br>
<br>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"><tr><td height="2" background="${ctx}/images/bg_line.gif"><img src="${ctx}/images/1px.gif" width="1" height="1"></td></tr><tr><td align="right"><table  border="0" align="center" cellpadding="0" cellspacing="0"><tr align="center">
      <td>
        <input type="button" value="确定" onclick="self.close()" class="button">
      </td>
      <td>
        <input type="button" class="button" value="取消" onclick="self.close()">
      </td>
    </tr></table></td></tr></table>
<%
}
%>

</body>

<span id="tempSpan" style="display:none">
<select name="subtaskpoint" class="multySelect" style="width:180" size="5" multiple="true">        </select>
</span>

</html>
