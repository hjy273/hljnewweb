<%@include file="/common/header.jsp"%>
<%

String fID = "1";
String rId = (String)request.getParameter("regionID");
System.out.println("rId====="+rId);
if(request.getParameter("fID") != null){
	fID = request.getParameter("fID");
}
System.out.println("fID====="+fID);
%>

<script language="javascript" type="">
<!--

function presetData(fID){
	//alert(fID);

	if(fID == "1"){
		return;
	}else{

		try{

			//alert(opener.sublineBean.sublinecablelist.length);

			for(var i = 0; i < opener.sublineBean.sublinecablelist.length; i++ ){
				txt = opener.sublineBean.cablename[i].value;
				val = opener.sublineBean.sublinecablelist[i].value;
				toList.options[toList.length] = new Option(txt,val);

			}

			for(var i = 0; i < opener.sublineBean.sublinecablelist.length; i++ ){

				for(var k = 0; k < sublineBean.sublinecablelist.options.length; k++){

					if(opener.sublineBean.sublinecablelist[i].value == sublineBean.sublinecablelist.options[k].value){

						sublineBean.sublinecablelist.options[k] = null;
						continue;

					}
				}

			}

			if(toList.length == 0){

				txt = opener.sublineBean.cablename.value;
				val = opener.sublineBean.sublinecablelist.value;
				toList.options[toList.length] = new Option(txt,val);


				for(var k = 0; k < sublineBean.sublinecablelist.options.length; k++){

					if(opener.sublineBean.sublinecablelist.value == sublineBean.sublinecablelist.options[k].value){

						sublineBean.sublinecablelist.options[k] = null;
						continue;

					}
				}

			}

		}catch(e){}

	}

}

function getSelectedItems(){

	for(var i = 0; i < sublineBean.sublinecablelist.options.length; i++){
		if(sublineBean.sublinecablelist.options[i].selected == true ){

			txt = sublineBean.sublinecablelist.options[i].text;
			val = sublineBean.sublinecablelist.options[i].value;
			toList.options[toList.length] = new Option(txt,val);

			sublineBean.sublinecablelist.options[i] = null;
			i--;
		}

	}

}



function getAllItems(){

}

function removeSelectedItems(){

	for(var i = 0; i < toList.options.length; i++){
		if(toList.options[i].selected == true ){

			txt = toList.options[i].text;
			val = toList.options[i].value;
			sublineBean.sublinecablelist.options[toList.length] = new Option(txt,val);

			toList.options[i] = null;
			i--;
		}

	}
}

function removeAllItems(){

}

function setParent(){

	if (toList.options.length == 0){
		alert("请选择对应光缆！");
		return;

	}

	var conStr = "<br><table>";

	for(var i = 0; i < toList.options.length; i++){

		conStr += "<tr><td><input type=hidden name=sublinecablelist value="+toList.options[i].value+">";
		conStr += "<input type=text name=cablename value = "+toList.options[i].text+" style = border:0;background-color:transparent;width:280;font-size:9pt  readonly>";
		conStr += "</td></tr>";

	}
	conStr += "</table><br>";

	opener.listSpan.innerHTML = conStr;
	self.close();


}

//-->
</script>


<body onload="presetData('<%=fID%>')">
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
  <td height="24" align="center" class="title2">添加修改对应光缆信息</td>
</tr>  <tr><td height="2" background="${ctx}/images/bg_line.gif"><img src="${ctx}/images/1px.gif" width="1" height="1"></td> </tr></table>
  <table width="100%"  border="0" align="center">
    <tr>
      <td width="45%" align="center">
          <!--  -->
		  <table width="90%"  border="0" cellpadding="0" cellspacing="0"  class="tabout">
			<tr>
			  <th width="160" class="thlist">该线段对应光缆</th>
			</tr>
			<tr>
			  <td align="center">
				<select name="toList" multiple="multiple" size="15" style="width:160" class="multySelect">
				</select>
			  </td>
			</tr>
		  </table>
		  <!--  -->
	  </td>
      <td width="10%" align="right"  align="center">
	  <input type="button" name="button" value="  &lt; " onclick="getSelectedItems()">
      <br>
      <input type="button" name="button" value=" &gt;  " onclick="removeSelectedItems()">
	  </td>
      <td width="45%" align="center">
          <html:form  action="/sublineAction.do?method=addSubline" >
	      <!--  -->
		  <br>
		  <table width="90%"  border="0" cellpadding="0" cellspacing="0"  class="tabout">
			<tr>
			  <th width="160" class="thlist">全部光缆信息</th>
			</tr>
			<tr>
			  <td align="center">
					<apptag:setSelectOptions valueName="cableCollection"  tableName="cablesegment" columnName1="segmentname"    columnName2="kid"/>
					<html:select property="sublinecablelist" styleClass="multySelect" style="width:160" size="15" multiple="true">
					<html:options collection="cableCollection" property="value" labelProperty="label"/>
					</html:select>

			  </td>
			</tr>
		  </table>
		  <!--  -->
		  </html:form>

	  </td>
    </tr>
  </table>
  <template:formSubmit>
  <td>
	<input type="button" class="button" onclick="setParent()" value="确定" >
  </td>
  <td>
	<input type="button" class="button" onclick="self.close()" value="取消" >
  </td>
</template:formSubmit>
