<%@include file="/common/header.jsp"%>

<html>
<head>
<script type="" language="javascript">
	var rowArr = new Array();//一行材料的信息
    var infoArr = new Array();//所有材料的信息

    //初始化数组
    function initArray(id,name,unit,type,essenumber){
    	rowArr[0] = id;
        rowArr[1] = name;
        rowArr[2] = unit;
        rowArr[3] = type;
        rowArr[4] = essenumber;
        infoArr[infoArr.length] = rowArr;
        rowArr = new Array();
        return true;
    }
    //脚本生成的删除按  钮的删除动作
    function deleteRow(){
      	//获得按钮所在行的id
     	var rowid = "000";
        rowid = this.id;
        rowid = rowid.substring(1,rowid.length);
        //由id转换为行索找行的索引,并删除
      	for(i =0; i<queryID.rows.length;i++){
        	if(queryID.rows[i].id == rowid){
            	queryID.deleteRow(queryID.rows[i].rowIndex);
            }
        }
    }
    //脚本生成的select动作onselectchange
    function onselectchange(){
      	rowid = this.id.substring(6,this.id.length);
        var tr;//行对象
        for(i =0; i<queryID.rows.length;i++){
        	if(queryID.rows[i].id == rowid){
            	tr = queryID.rows[i];
            }
        }
        //找出选择材料所对应的所有信息
        var unit;
        var type;
        for(i = 0; i < infoArr.length; i++){
        	if(this.value == infoArr[i][0]){
            	unit = infoArr[i][2];
                if(type == null || type =="")
                	type = "无";
                else
                	type = infoArr[i][3]
            }
        }
        //写入表格
        tr.cells[2].innerText =unit;
        tr.cells[3].innerText =type;
    }

    //添加一个新行
    function addRow(){
    	var  onerow=queryID.insertRow(-1);
        onerow.id = queryID.rows.length;

        var   cell1=onerow.insertCell();
		var   cell2=onerow.insertCell();
		var   cell3=onerow.insertCell();
		var   cell4=onerow.insertCell();
        var   cell5=onerow.insertCell();

		//创建一个输入框
        var t1 = document.createElement("input");
        t1.name = "enumber"
		t1.id = "text" + onerow.id;
        t1.value= "0";
        t1.maxLength = "6";
        t1.size = "6";
        t1.onblur=valiD;

        //创建一个select
        var s1 = document.createElement("select");
        s1.options.length = infoArr.length + 1;
        s1.options[0].value = "";
        s1.options[0].text = "请选择待入库备件";
        for(i = 1; i<s1.options.length ;i++){
        	s1.options[i].value = infoArr[i-1][0];
            s1.options[i].text = infoArr[i-1][1];
        }
        s1.name = "id";
        s1.id = "select" + onerow.id;
        s1.onchange = onselectchange;
        s1.Class="inputtext"

        //创建删除按钮
        var b1 =document.createElement("button");
        b1.value = "删除该备件";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
        //alert(b1.id + "rrr");

        cell1.appendChild(s1);//文字
        cell2.appendChild(t1);//文字
        cell3.innerText="还没有选择备件";//文字
        cell4.innerText="还没有选择备件";//文字
        cell5.appendChild(b1);
	}
    //添加一个新行,报废页面用的
    function addRowForRev(){
    	var  onerow=queryID.insertRow(-1);
        onerow.id = queryID.rows.length;

        var   cell1=onerow.insertCell();
		var   cell2=onerow.insertCell();
		var   cell3=onerow.insertCell();
		var   cell4=onerow.insertCell();
        var   cell5=onerow.insertCell();

		//创建一个输入框
        var t1 = document.createElement("input");
        t1.name = "enumber"
		t1.id = "text" + onerow.id;
        t1.value= "0";
        t1.maxLength = "6";
        t1.size = "6";
        t1.onblur=valiDForRev;

        //创建一个select
        var s1 = document.createElement("select");
        s1.options.length = infoArr.length + 1;
        s1.options[0].value = "";
        s1.options[0].text = "请选择待报废备件";
        for(i = 1; i<s1.options.length ;i++){
        	s1.options[i].value = infoArr[i-1][0];
            s1.options[i].text = infoArr[i-1][1];
        }
        s1.name = "id";
        s1.id = "select" + onerow.id;
        s1.onchange = onselectchange;
        s1.Class="inputtext"

        //创建删除按钮
        var b1 =document.createElement("button");
        b1.value = "删除该备件";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
        //alert(b1.id + "rrr");

        cell1.appendChild(s1);//文字
        cell2.appendChild(t1);//文字
        cell3.innerText="还没有选择备件";//文字
        cell4.innerText="还没有选择备件";//文字
        cell5.appendChild(b1);
	}
    //添加提交
    function toAddSub(){
      if(queryID.rows.length<2){
      	alert("你还没有选择备件,不能提交!")
        return false;
      }
    	addForm.submit();
    }
  //检验是否数字
    function valiD(){
    	var mysplit = /^\d{1,6}$/;
        if(mysplit.test(this.value)){
          return true;
        }
        else{
        	alert("你填写的不是数字,请重新输入");
            this.focus();
            this.value=0;
        }
    }
    function valiDForRev(){
    	var mysplit = /^\d{1,6}$/;
        if(!mysplit.test(this.value)){
        	alert("你填写的不是数字,请重新输入");
            this.focus();
            this.value=0;
            return false;
        }
        var seId = "select" +(this.id).substring(4,this.id.length);
        var selObj = document.getElementById(seId);  //得到select所选择材料的对象
        var oldnumber =0;
        for(i=0;i<infoArr.length;i++){
        	if(selObj.value == infoArr[i][0]){
            	oldnumber = infoArr[i][4];
                break;
            }
        }
        if(parseInt(this.value) > parseInt(oldnumber)){
        	alert("目前库存是" + oldnumber + ",不能报废这么多!!");
            this.focus();
            this.value=0;
            return false;
        }
        return true;
    }

   //显示页面详细信息按钮动作(ruku)
   function toGetForm(idValue){


     	var url = "${ctx}/ToolStockAction.do?method=showOneInfo&stockid=" + idValue;
        self.location.replace(url);
    }
 	//添加返回toGoBackForRevoke()
    function addGoBack(){
    	try{
            	location.href = "${ctx}/ToolStockAction.do?method=showAllStock";
                return true;
            }
            catch(e){
            	alert(e);
            }
    }
     function addGoBackFOrRev(){
    	try{
            	location.href = "${ctx}/ToolStockAction.do?method=showAllRevoke";
                return true;
            }
            catch(e){
            	alert(e);
            }
    }
     function toGoBackForRevoke(){
    	try{
            	location.href = "${ctx}/ToolStockAction.do?method=showAllRevoke";
                return true;
            }
            catch(e){
            	alert(e);
            }
    }
	function toGetFormForRevoke(idValue){
     	var url = "${ctx}/ToolStockAction.do?method=showOneRevoke&stockid=" + idValue;
        self.location.replace(url);
    }






    //删除确认
    function toDeletForm(idValue){
    	if(confirm("你确定要执行此次删除操作吗?")){
        	//var url = "${ctx}/PartBaseInfoAction.do?method=deletePartBaseInfo&id=" + idValue;
            self.location.replace(url);
         }
        else
        	return false;
    }
	//显示页面转向修改页面
    function toUpForm(idValue){
    	var url = "${ctx}/PartRequisitionAction.do?method=upshow&reid=" + idValue;
        self.location=url;
    }

    //修改页面通过迭代生成 的删除按钮删除动作
    function upDelOnClick(id){
     var trid = id.substring(1,id.length);
     var trobj = document.getElementById(trid);
     queryID.deleteRow(trobj.rowIndex);
    }
    //修改页面显示已有的材料select动作
    function upselectOncheng(id){
    	var trid = id.substring(4,id.length);
     	var trobj = document.getElementById(trid);//行对象
        var seleobj = document.getElementById(id);
        //alert(seleobj.selectedIndex);//seleobj.selectedIndex是所选择的选项索引值
        for(i = 0; i< infoArr.length;i++){
        	if(seleobj.options[seleobj.selectedIndex].value == infoArr[i][0]){
            	trobj.cells[2].innerText = infoArr[i][2];
        		trobj.cells[3].innerText = infoArr[i][3];
                //alert (infoArr[i][3]);
            }
        }
        return true;
    }



    //修改页面提交动作
    function toUpSub(){
    	if(queryID.rows.length<2){
	      	alert("你把备件删没了,不能提交!")
	        return false;
	      }
      if(confirm("你确定要执行此次修改操作吗?")){
        	UpForm.submit();
         }
        else
        	return false;
    }
	//显示页面删除按钮动作
     function toDelForm(idValue){
     	if(confirm("你确定要删除吗?")){
        	var url = "${ctx}/PartRequisitionAction.do?method=delOneReqInfo&reid=" + idValue;
        	self.location.replace(url);
         }
        else
        	return ;
    }
 //选择日期
    function GetSelectDateTHIS(strID) {
    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    	return ;
	}



    //检验是否数字
    function valiDigit(id){
    	var obj = document.getElementById(id);
      	var mysplit = /^\d{1,6}$/;
        if(mysplit.test(obj.value)){
          return true;
        }
        else{
        	alert("你填写的不是数字,请重新输入");
            obj.focus();
            obj.value=0;
            return false;
        }
    }

   function toExportStock(){

     var userid = queryForm2.userid.value;
     var begintime = queryForm2.begintime.value;
     var endtime = queryForm2.endtime.value;
     var url = "${ctx}/ToolStockAction.do?method=exportStockList&userid="+userid+"&begintime="+begintime+"&endtime="+endtime;
     self.location.replace(url);

   }
   function toExportRevoke(){

     var userid = queryForm2.userid.value;
     var remark = queryForm2.remark.value;
     var begintime = queryForm2.begintime.value;
     var endtime = queryForm2.endtime.value;
     var url = "${ctx}/ToolStockAction.do?method=exportRevokeList&userid="+userid+"&remark="+remark+"&begintime="+begintime+"&endtime="+endtime;
     self.location.replace(url);

   }
</script>


<title>

</title>
</head>
<body>

	<!--填写入库单-->
	<logic:equal value="2"  name="type" scope="session" >
    	<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
        <logic:present name="baseinfo">
        	<logic:iterate id="baseinfoId" name="baseinfo">
            	<script type="" language="javascript">
                	initArray("<bean:write name="baseinfoId" property="id"/>","<bean:write name="baseinfoId" property="name"/>","<bean:write name="baseinfoId" property="unit"/>","<bean:write name="baseinfoId" property="type"/>");
                </script>
        	</logic:iterate>
        </logic:present>
      	<div id="partDivId"  >
	        <br/>
	        <template:titile value="填写备件入库单"/>
	        <html:form action="/ToolStockAction?method=addStock" styleId="addForm">
			    <input  type="hidden" name="contractorid" value="<bean:write name="deptid"/>"/>
	          	<input type="hidden" name="userid" value="<bean:write name="userid" />"/>
	            <table align="center" width="600" border="0">
		        	<tr >
		            	<td width="100" height="25"><b>单位名称:</b></td>
	                    <td align="left" width="100"><bean:write name="deptname"/></td>
	                    <td  align="right" width="100"><b>操 作 人:</b></td>
	                    <td align="left" width="80"><bean:write name="username"/></td>
						<td width="100" align="right"><b>入库时间:</b></td>
	                    <td align="left" width="120"><bean:write name="date"/></td>
		            </tr>
                    <!--
	                <tr>
	                	<td ><b>存放地点:</b></td>
	                    <td ><html:text property="adress" styleClass="inputtext" style="width:100;" maxlength="50" ></html:text> </td>
                        <td align="right"><b>保 管 人:</b></td>
	                    <td >
                        	<input type="text" name="patrolid" class="inputtext" style="width:80"/>
                          <select name="patrolid" class="selecttext">
                            	<logic:iterate id="patrolId" name="patrol">
                                	<option value="<bean:write name="patrolId" property="patrolid"/>"><bean:write name="patrolId" property="patrolname"/></option>
                            	</logic:iterate>
                        	</select>
                        </td>
                        <td width="100" align="right"><b>入库时间:</b></td>
	                    <td align="left" width="120"><bean:write name="date"/></td>
	                </tr>-->
                     <tr>

	                </tr>
	                <tr>
	                	<td height="25"><b>备注信息:</b></td>
	                    <td colspan="5"><html:text property="remark" styleClass="inputtext" style="width:500;" maxlength="510" value="请填写备件入库的备注信息."></html:text></td>
	                </tr>
                    <tr>
						<td colspan="6"><hr /><br> 请选择入库备件:</td>
	                </tr>

		        </table>

		            <table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
		            	<tr>
		                	<th  width="25%" class="thlist" align="center">备件名称</th>
		                    <th  width="15%" class="thlist" align="center">入库数量</th>
		                    <th  width="20%" class="thlist" align="center">计量单位</th>
		                    <th  width="25%" class="thlist" align="center">规格型号</th>
		                    <th  width="15%"class="thlist" align="center">操作</th>
		            	</tr>
		        	</table>
                    <p align="center">
		                          <html:button property="action" styleClass="button" onclick="addRow()">入库新备件</html:button>
					       	 	<html:button property="action" styleClass="button"  onclick="toAddSub()">提交入库单</html:button>
					       	 	<html:button property="action" styleClass="button" onclick="addGoBack()" >返回	</html:button>
						     </p>

	        </html:form>
         </div>
	</logic:equal>

   <!--显示入库单-->
    <logic:equal value="1" name="type" scope="session" >
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
        <br />
            <template:titile value="备件入库单一览表" />
            <!--
                display:column property="patrolid" title="保 管 人" maxLength="20"align="center"/>
                display:column property="adress" title="存放地点" maxLength="10"/>-->
    		<display:table name="sessionScope.stockinfo" id="currentRowObject"  pagesize="18">

            	<display:column property="contractorname" title="入库单位" maxLength="10" align="center"/>
                <display:column property="username" title="入 库 人" maxLength="20"align="center"/>
                <display:column property="time" title="入库时间"maxLength="20"align="center"/>
                <display:column media="html" title="操作" >
	                 <%
						    BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
						    String id = (String) object.get("stockid");

	            		%>
	            	 <a href="javascript:toGetForm('<%=id%>')">详细</a>
			  	</display:column>
    		</display:table>
            <html:link action="/ToolStockAction?method=exportStockResult">导出为Excel文件</html:link>
    </logic:equal>

<!--显示申请单详细信息-->
    <logic:equal value="10"   scope="session"  name="type">
            <br />
      	<template:titile value="备件入库单详细信息" />
            <table align="center" width="600" border="0">
	          		<tr >
		            	<td width="100" height="25"><b>单位名称:</b></td>
	                    <td align="left" width="100"><bean:write name="stockinfo" property="factory"/></td>
	                    <td  align="right" width="100"><b>操作人:</b></td>
	                    <td align="left" width="80"><bean:write name="stockinfo" property="useid"/></td>
						<td width="100" align="right"><b>入库时间:</b></td>
	                    <td align="left" width="120"><bean:write name="stockinfo" property="time"/></td>
		            </tr>
                    <!--
	                <tr>
	                	<td ><b>存放地点:</b></td>
	                    <td ><bean:write name="stockinfo" property="adress"/> </td>
                        <td align="right"><b>保 管 人:</b></td>
	                    <td >
                        	<bean:write name="stockinfo" property="patrolname"/>
                        </td>
                        <td width="100" align="right"><b>入库时间:</b></td>
	                    <td align="left" width="120"><bean:write name="stockinfo" property="time"/></td>
	                </tr>-->
                     <tr>

	                </tr>
	                <tr>
	                	<td height="25"><b>备注信息:</b></td>
	                    <td colspan="5"><bean:write name="stockinfo" property="remark"/></td>
	                </tr>
                     <tr>
                            	<td colspan="6"><hr /></td>
                    </tr>
	                <tr>
	                	<td colspan="6"   valign="bottom">
                        	<b>该入库单所入库的备件:</b>
                        </td>
	                </tr>
		        </table>
                <table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
	            	<tr>
	                	<th  width="25%" class="thlist" align="center">备件名称</th>
	                    <th  width="15%" class="thlist" align="center">入库数量</th>
                        <th  width="20%" class="thlist" align="center">计量单位</th>
	                    <th  width="25%" class="thlist" align="center">规格型号</th>

	            	</tr>
                    <logic:present name="toolsinfo">
                    	<logic:iterate id="toolsinfoid" name="toolsinfo">
                        	<tr >
                            	<td>
                                  	<bean:write name="toolsinfoid" property="name"/>
                                </td>
                                <td>
                                	<bean:write name="toolsinfoid" property="enumber"/>
                                </td>
                                <td>
                               		 <bean:write name="toolsinfoid" property="unit"/>
                                </td>
                                <td>
                                	<bean:write name="toolsinfoid" property="type"/>
                                </td>
                       	 	</tr>

                    	</logic:iterate>
                    </logic:present>
	        	</table>
                <p align="center"><html:button property="action" styleClass="button" onclick="addGoBack()" >返回	</html:button></p>
    </logic:equal>


<!--查询页面-->
    <logic:equal value="3" name="type" scope="session"  >
      	<br />
        <template:titile value="条件查询入库单"/>
        <html:form action="/ToolStockAction?method=queryExec"   styleId="queryForm2" >
        	<template:formTable namewidth="200"  contentwidth="200">
            	<template:formTr name="入 库 人"  >
            		<select name="userid"   class="inputtext" style="width:180px" >
			        	<option  value="">选择所有人员</option>
                      	<logic:present name="users">
		                	<logic:iterate id="usersId" name="users">
		                    	<option value="<bean:write name="usersId" property="userid"/>"><bean:write name="usersId" property="username"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
                <!--
                <template:formTr name="存放地点"  >
            		<select name="adress"   class="inputtext" style="width:180px" >
			        	<option  value="">选择所有地点</option>
                      	<logic:present name="adress">
		                	<logic:iterate id="adressId" name="adress">
		                    	<option value="<bean:write name="adressId" property="adress"/>"><bean:write name="adressId" property="adress"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
                <template:formTr name="保 管 人"  >
            		<select name="patrolid"   class="inputtext" style="width:180px" >
			        	<option  value="">选择所有保管人</option>
                      	<logic:present name="patrolid">
		                	<logic:iterate id="patrolidId" name="patrolid">
		                    	<option value="<bean:write name="patrolidId" property="patrolid"/>"><bean:write name="patrolidId" property="patrolid"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
				-->
                <template:formTr name="开始时间">
                	<input   id="begin" type="text"   name="begintime"  readonly="readonly" class="inputtext" style="width:150" />
                    <INPUT TYPE='BUTTON' VALUE='日期' ID='btn'  onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';" >
                </template:formTr>
                <template:formTr name="结束时间">
                	<input  id="end" type="text"  name="endtime" readonly="readonly" class="inputtext" style="width:150" />
                    <INPUT TYPE='BUTTON' VALUE='日期' ID='btn'  onclick="GetSelectDateTHIS('end')" STYLE="font:'normal small-caps 6pt serif';" >
                </template:formTr>
                <template:formSubmit>
					      	<td>
	                          <html:submit property="action" styleClass="button" >查找</html:submit>
					      	</td>
                            <td>
                              <html:reset property="action" styleClass="button" >取消</html:reset>
                            </td>
                            <td>
                              <html:button property="action" styleClass="button" onclick="addGoBack()" >返回</html:button>
                            </td>
                            <td width="85">
                              <html:button property="action" styleClass="button" onclick="toExportStock()">Excel报表</html:button>
                            </td>
					    </template:formSubmit>
        	</template:formTable>

    	</html:form>
    </logic:equal>

<!--/////////////////////////////////////////以下是报废得操作//////////////////////////////////////////////////////-->
	<!--填写报废单-->
	<logic:equal value="revoke2"  name="type" scope="session" >
    	<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
        <logic:present name="baseinfo">
        	<logic:iterate id="baseinfoId" name="baseinfo">
            	<script type="" language="javascript">
                	initArray("<bean:write name="baseinfoId" property="id"/>","<bean:write name="baseinfoId" property="name"/>",
                              "<bean:write name="baseinfoId" property="unit"/>","<bean:write name="baseinfoId" property="type"/>",
                              "<bean:write name="baseinfoId" property="essenumber"/>");
                </script>
        	</logic:iterate>
        </logic:present>
      	<div id="partDivId"  >
	        <br/>
	        <template:titile value="填写备件报废单"/>
	        <html:form action="/ToolStockAction?method=addRevoke" styleId="addForm">
			    <input  type="hidden" name="contractorid" value="<bean:write name="deptid"/>"/>
	          	<input type="hidden" name="userid" value="<bean:write name="userid" />"/>
	            <table align="center" width="600" border="0">
		        	<tr >
		            	<td width="100" height="25"><b>单位名称:</b></td>
	                    <td align="left" width="100"><bean:write name="deptname"/></td>
	                    <td  align="right" width="100"><b>操 作 人:</b></td>
	                    <td align="left" width="80"><bean:write name="username"/></td>

		            </tr>
	                <tr>
	                	<td height="25"><b>报废原因:</b></td>
	                    <td colspan="5"><html:text property="remark" styleClass="inputtext" style="width:500;" maxlength="510" value="请填写备件报废的原因."></html:text></td>
	                </tr>
	                <tr>
	                	<td colspan="6">
	                    	<hr /><br>请选择报废的备件:
	                    </td>
	                </tr>
		        </table>

		            <table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
		            	<tr>
		                	<th  width="25%" class="thlist" align="center">备件名称</th>
		                    <th  width="15%" class="thlist" align="center">报废数量</th>
		                    <th  width="20%" class="thlist" align="center">计量单位</th>
		                    <th  width="25%" class="thlist" align="center">规格型号</th>
		                    <th  width="15%"class="thlist" align="center">操作</th>
		            	</tr>
		        	</table>
                    <p align="center">
		                          <html:button property="action" styleClass="button" onclick="addRowForRev()">添加报废备件</html:button>
						       	 	<html:button property="action" styleClass="button"  onclick="toAddSub()">提交报废单</html:button>
						       	 	<html:button property="action" styleClass="button" onclick="addGoBackFOrRev()" >返回	</html:button>
                    </p>

	        </html:form>
         </div>
	</logic:equal>

       <!--显示报废单-->
    <logic:equal value="revoke1" name="type" scope="session" >
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
        <br />
            <template:titile value="备件报废单一览表" />
    		<display:table name="sessionScope.stockinfo" id="currentRowObject"  pagesize="18">
            	<display:column property="contractorname" title="报废单位" maxLength="10" align="center"/>
                <display:column property="username" title="操 作 人" maxLength="20"align="center"/>
                <display:column property="time" title="报废时间"maxLength="20"align="center"/>
                <display:column property="remark" title="报废原因"maxLength="20"align="center"/>
                <display:column media="html" title="操作" >
	            	 <%
					    BasicDynaBean object1 = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
					    String id1 = (String) object1.get("stockid");
					  %>
	            	 <a href="javascript:toGetFormForRevoke('<%=id1%>')">详细</a>
			  	</display:column>
    		</display:table>
            <html:link action="/ToolStockAction?method=exportRevokeResult">导出为Excel文件</html:link>
    </logic:equal>

    <!--显示报废单详细信息-->
    <logic:equal value="revoke10"  scope="session"   name="type">
            <br />
      	<template:titile value="备件报废单详细信息" />
            <table align="center" width="600" border="0">
	          		<tr >
		            	<td width="100" height="25"><b>单位名称:</b></td>
	                    <td align="left" width="100"><bean:write name="stockinfo" property="factory"/></td>
	                    <td  align="right" width="100"><b>操 作 人:</b></td>
	                    <td align="left" width="80"><bean:write name="stockinfo" property="useid"/></td>
                        <td width="100" align="right"><b>报废时间:</b></td>
	                    <td align="left" width="120"><bean:write name="stockinfo" property="time"/></td>
	                </tr>
                     <tr>

	                </tr>
	                <tr>
	                	<td height="25"><b>报废原因:</b></td>
	                    <td colspan="5"><bean:write name="stockinfo" property="remark"/></td>
	                </tr>
                     <tr>
                            	<td colspan="6"><hr /></td>
                    </tr>
	                <tr>
	                	<td colspan="6"   valign="bottom">
                        	<b>该报废单所所报废的备件:</b>
                        </td>
	                </tr>
		        </table>
                <table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
	            	<tr>
	                	<th  width="25%" class="thlist" align="center">备件名称</th>
	                    <th  width="15%" class="thlist" align="center">报废数量</th>
                        <th  width="20%" class="thlist" align="center">计量单位</th>
	                    <th  width="25%" class="thlist" align="center">规格型号</th>

	            	</tr>
                    <logic:present name="toolsinfo">
                    	<logic:iterate id="toolsinfoid" name="toolsinfo">
                        	<tr >
                            	<td>
                                  	<bean:write name="toolsinfoid" property="name"/>
                                </td>
                                <td>
                                	<bean:write name="toolsinfoid" property="enumber"/>
                                </td>
                                <td>
                               		 <bean:write name="toolsinfoid" property="unit"/>
                                </td>
                                <td>
                                	<bean:write name="toolsinfoid" property="type"/>
                                </td>
                       	 	</tr>

                    	</logic:iterate>
                    </logic:present>
	        	</table>
                <p align="center"><html:button property="action" styleClass="button" onclick="toGoBackForRevoke()" >返回	</html:button></p>
    </logic:equal>

    <!--查询页面-->
    <logic:equal value="revoke3"  scope="session" name="type" >
      	<br />
        <template:titile value="条件查询报废单"/>
        <html:form action="/ToolStockAction?method=queryExecForRevoke"   styleId="queryForm2" >
        	<template:formTable namewidth="200"  contentwidth="200">
            	<template:formTr name="操 作 人"  >
            		<select name="userid"   class="inputtext" style="width:180px" >
			        	<option  value="">选择所有人员</option>
                      	<logic:present name="users">
		                	<logic:iterate id="usersId" name="users">
		                    	<option value="<bean:write name="usersId" property="userid"/>"><bean:write name="usersId" property="username"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
                <template:formTr name="报废原因"  >
            		<select name="remark"   class="inputtext" style="width:180px" >
			        	<option  value="">选择所有原因</option>
                      	<logic:present name="remark">
		                	<logic:iterate id="remarkId" name="remark">
		                    	<option value="<bean:write name="remarkId" property="remark"/>"><bean:write name="remarkId" property="remark"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>

                <template:formTr name="开始时间">
                	<input   id="begin" type="text"   name="begintime"  readonly="readonly" class="inputtext" style="width:150" />
                    <INPUT TYPE='BUTTON' VALUE='日期' ID='btn'  onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';" >
                </template:formTr>
                <template:formTr name="结束时间">
                	<input  id="end" type="text"  name="endtime" readonly="readonly" class="inputtext" style="width:150" />
                    <INPUT TYPE='BUTTON' VALUE='日期' ID='btn'  onclick="GetSelectDateTHIS('end')" STYLE="font:'normal small-caps 6pt serif';" >
                </template:formTr>
                <template:formSubmit>
					      	<td>
	                          <html:submit property="action" styleClass="button" >查找</html:submit>
					      	</td>
					      	<td>
					       	 	<html:reset property="action" styleClass="button" >取消</html:reset>
					      	</td>
	                        <td>
					       	 	<html:button property="action" styleClass="button" onclick="addGoBackFOrRev()" >返回</html:button>
                            </td>
                            <td width="85">
                              <html:button property="action" styleClass="button" onclick="toExportRevoke()">Excel报表</html:button>
                            </td>
					    </template:formSubmit>
        	</template:formTable>

    	</html:form>
    </logic:equal>


	<iframe id="hiddenIframe" style="display:none"></iframe>
</body>

</html>
