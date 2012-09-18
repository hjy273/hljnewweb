<%@include file="/common/header.jsp"%>

<html>
<head>
<script type="" language="javascript">
	var rowArr = new Array();//一行材料的信息
    var infoArr = new Array();//所有材料的信息

    //初始化数组
    function initArray(id,name,unit,type,essenumber,oldnumber){
    	rowArr[0] = id;
        rowArr[1] = name;
        rowArr[2] = unit;
        rowArr[3] = type;
        rowArr[4] = essenumber;
        rowArr[5] = oldnumber;
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
                if(infoArr[i][3] ==null || infoArr[i][3] =="")
                	type = "无";
                else
                	type = infoArr[i][3];
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
        t1.size = "10";
        t1.onblur = checknew;


        //创建一个select
        var s1 = document.createElement("select");
        s1.options.length = infoArr.length + 1;
        s1.options[0].value = "";
        s1.options[0].text = "请选择备件名称";
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
        b1.value = "删除";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
        //alert(b1.id + "rrr");

        cell1.appendChild(s1);//文字
        cell2.appendChild(t1);//文字
        cell3.innerText="没选备件";//文字
        cell4.innerText="没选备件";//文字
        cell5.appendChild(b1);
	}

     function checknew(){
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
        	alert("目前库存是" + oldnumber + ",不能领用这么多!!");
            this.focus();
            this.value=0;
            return false;
        }
        return true;
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

        //添加提交
    function toAddSub(){
      if(queryID.rows.length<2){
      	alert("你还没有选择备件,不能提交!")
        return false;
      }
    	addForm.submit();
    }

    //显示页面详细信息按钮动作
   function toGetForm(idValue){
     	var url = "${ctx}/ToolUseAction.do?method=showOneUse&useid=" + idValue;
        self.location.replace(url);
    }

	function toGetFormForBack(idValue){
     	var url = "${ctx}/ToolUseAction.do?method=showOneUseForBack&useid=" + idValue;
        self.location.replace(url);
    }
   function  toGetFormForShouldBack(idValue){
   		var url = "${ctx}/ToolUseAction.do?method=showShouldBackUse&id=" + idValue;
        self.location.replace(url);
   }
   function  addGoBack(){
     	var url = "${ctx}/ToolUseAction.do?method=showAllUse";
        self.location.replace(url);
    }
    //选择日期
    function GetSelectDateTHIS(strID) {
    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    	return ;
	}
    //填写返还数量时的检查
   function valiForBackBnumber(id){
     	var obj = document.getElementById(id);
      	var mysplit = /^\d{1,6}$/;
        if(!mysplit.test(obj.value)){
        	alert("你填写的不是数字,请重新输入");
            obj.focus();
            obj.value=0;
            return false;
        }
   		var toolid = id.substring(3,id.length);
        var tem1 = "use" + toolid;
        var tem2 = "bac" + toolid
        var useObj = document.getElementById(tem1);
        var bacObj = document.getElementById(tem2);
        if(parseInt(obj.value) > (parseInt(useObj.value) - parseInt(bacObj.value))){
        	alert("你填写的返还数量大于应返还数量,请重新输入");
            obj.focus();
            return false;
        }
		return ;
   }

   function toExportUseList(){

     var userid = queryForm2.userid.value;
     var usename = queryForm2.usename.value;
     var useunit = queryForm2.useunit.value;
     var useremark = queryForm2.useremark.value;
     var back = queryForm2.back.value;
     var begintime = queryForm2.begintime.value;
     var endtime = queryForm2.endtime.value;
     var url = "${ctx}/ToolUseAction.do?method=exportUseList&userid="+userid+"&usename="+usename+"&useunit="+useunit+"&useremark="+useremark+"&back="+back+"&begintime="+begintime+"&endtime="+endtime;
     self.location.replace(url);

   }
</script>


<title>
partRequisition
</title>
</head>
<body>

	<!--填写领用单-->
	<logic:equal value="use2"  name="type" scope="session" >
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

        <br/>
        <template:titile value="填写备件领用单"/>
        <html:form action="/ToolUseAction?method=addUse" styleId="addForm">
		    <input  type="hidden" name="contractorid" value="<bean:write name="deptid"/>"/>
          	<input type="hidden" name="userid" value="<bean:write name="userid" />"/>
            <table align="center" width="600" border="0">

               <tr>
                	<td width="100" height="25"  ><b>领用单位:</b></td>
                    <td width="100"><input type="text" name="useunit"  Class="inputtext" maxlength="15" value="<bean:write name="deptname"/>"/></td>
                 	<td width="100"  height="25" align="right" ><b>领用人:</b></td>
                    <td width="200" colspan="3"><input type="text" name="usename"  style="width:100" Class="inputtext"  maxlength="5" value="<bean:write name="username"/>"/></td>

                </tr>
                <tr>
                	<td height="25"><b>备件用途:</b></td>
                    <td colspan="5"><html:text property="useremark" styleClass="inputtext" style="width:500;" maxlength="510" value="请填写备件用途."></html:text></td>
                </tr>
                <tr >
	            	<td  height="25"><b>单位名称:</b></td>
                    <td align="left" ><bean:write name="deptname"/></td>
                    <td  align="right" ><b>操作人:</b></td>
                    <td align="left" ><bean:write name="username"/></td>
                    <td align="right"><b>时间:</b></td>
                    <td align="left" ><bean:write name="date"/></td>
	            </tr>
               <tr>
               		<td colspan="6"><hr/><br />请选择备件:</td>
               </tr>
	        </table>
        	<table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
            	<tr>
                	<th  width="20%" class="thlist" align="center">备件名称</th>
                    <th  width="15%" class="thlist" align="center">领用数量</th>
                    <th  width="10%" class="thlist" align="center">计量单位</th>
                    <th  width="20%" class="thlist" align="center">规格型号</th>
                    <th  width="8%"class="thlist" align="center">操作</th>
            	</tr>
        	</table>

	      	<p align="center">
              <html:button property="action" styleClass="button" onclick="addRow()">领用新备件</html:button>
              <html:button property="action" styleClass="button"  onclick="toAddSub()">提交领用单</html:button>
              <html:button property="action" styleClass="button" onclick="addGoBack()" >返回	</html:button>
			</p>
        </html:form>
	</logic:equal>


      <!--显示领用单-->
    <logic:equal value="use1" name="type" scope="session" >
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
    	    <br />
            <template:titile value="备件领用单一览表" />
    		<display:table name="sessionScope.useinfo" id="currentRowObject"  pagesize="18">
            	<display:column property="useunit" title="领用单位" maxLength="10" align="center"/>
                <display:column property="usename" title="领用人" maxLength="20"align="center"/>
                <display:column property="time" title="领用时间"maxLength="20"align="center"/>
                <display:column property="useremark" title="备件用途" maxLength="20"align="center"/>
                <display:column property="username" title="操作人" maxLength="10"/>
                <display:column media="html" title="操作" >
	            	 <%
					    BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
					    String id = (String) object.get("useid");
					  %>
	            	 <a href="javascript:toGetForm('<%=id%>')">详细</a>
			  	</display:column>
    		</display:table>
            <html:link action="/ToolUseAction?method=exportUseResult">导出为Excel文件</html:link>
    </logic:equal>
    <!--显示领用单详细信息-->
    <logic:equal value="use10"  scope="session"   name="type">
            <br />
      	<template:titile value="备件领用单详细信息" />
        	 <table align="center" width="600" border="0">
	          		<tr >
		            	<td width="60" height="25"><b>单位名称:</b></td>
	                    <td align="left" width="120"><bean:write name="useinfo" property="contractorname"/></td>
	                    <td  align="right" width="50"><b>操作人:</b></td>
	                    <td align="left" width="100"><bean:write name="useinfo" property="username"/></td>
	                    <td width="70" align="right"><b>领用时间:</b></td>
	                    <td align="left" width="100"><bean:write name="useinfo" property="time"/></td>
		            </tr>
                    <tr >
		            	<td width="60" height="25"><b>领用单位:</b></td>
	                    <td align="left" width="120"><bean:write name="useinfo" property="useunit"/></td>
	                    <td  align="right" width="50"><b>领用人:</b></td>
	                    <td align="left" width="100"><bean:write name="useinfo" property="usename"/></td>

		            </tr>
                    <tr>
	                	<td height="25"><b>领用原因:</b></td>
	                    <td colspan="5"><bean:write name="useinfo" property="useremark"/></td>
	                </tr>
	                 <tr>
                    	<td colspan="6"><hr /></td>
                    </tr>
	                <tr>
	                	<td colspan="6"   valign="bottom">
                        	<b>该领用单所领用的备件:</b>
                        </td>
	                </tr>
		        </table>
                <table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
	            	<tr>
	                	<th  width="25%" class="thlist" align="center">备件名称</th>
	                    <th  width="15%" class="thlist" align="center">领用数量 </th>
                        <th  width="15%" class="thlist" align="center">返还数量 </th>
                        <th  width="20%" class="thlist" align="center">计量单位</th>
	                    <th  width="25%" class="thlist" align="center">规格型号</th>

	            	</tr>
                    <logic:present name="usepartinfo">
                    	<logic:iterate id="useid" name="usepartinfo">
                        	<tr   >
                            	<td>
                                  	<bean:write name="useid" property="name"/>
                                </td>
                                <td>
                                	<bean:write name="useid" property="enumber"/>
                                </td>
                                 <td>
                                	<bean:write name="useid" property="bnumber"/>
                                </td>
                                <td>
                               		 <bean:write name="useid" property="unit"/>
                                </td>
                                <td>
                                	<bean:write name="useid" property="type"/>
                                </td>
                       	 	</tr>
                    	</logic:iterate>
                    </logic:present>
	        	</table>
                <p align="center">
                  <html:button property="action" styleClass="button" onclick="addGoBack()" >返回	</html:button>
                </p>

    </logic:equal>

    <!--查询页面-->
    <logic:equal value="use3" name="type" scope="session"  >
      	<br />
        <template:titile value="条件查找备件领用单"/>
        <html:form action="/ToolUseAction?method=queryExec"   styleId="queryForm2" >
        	<template:formTable namewidth="200"  contentwidth="200">
            	<template:formTr name="操作人姓名"  >
            		<select name="userid"   class="inputtext" style="width:180px" >
			        	<option  value="">选择所有操作人</option>
                      	<logic:present name="useuser">
		                	<logic:iterate id="useuserId" name="useuser">
		                    	<option value="<bean:write name="useuserId" property="userid"/>"><bean:write name="useuserId" property="username"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
                <template:formTr name="领用人姓名"  >
            		<select name="usename"   class="inputtext" style="width:180px" >
			        	<option  value="">选择所有领用人</option>
                      	<logic:present name="lusename">
		                	<logic:iterate id="lusenameId" name="lusename">
		                    	<option value="<bean:write name="lusenameId" property="usename"/>"><bean:write name="lusenameId" property="usename"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
                  <template:formTr name="领用单位"  >
            		<select name="useunit"   class="inputtext" style="width:180px" >
			        	<option  value="">选择所有领用单位</option>
                      	<logic:present name="luseunit">
		                	<logic:iterate id="luseunitId" name="luseunit">
		                    	<option value="<bean:write name="luseunitId" property="useunit"/>"><bean:write name="luseunitId" property="useunit"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
              	<template:formTr name="领用原因" >
            		<select name="useremark" style="width:180px" class="inputtext"  >
			        	<option  value="">选择所有原因</option>
                      	<logic:present name="useremark">
		                	<logic:iterate id="luseremarkId" name="useremark">
		                    	<option value="<bean:write name="luseremarkId"  property="useremark"/>"><bean:write name="luseremarkId"  property="useremark"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr >
                <template:formTr name="归还情况"  >
            		<select name="back"   class="inputtext" style="width:180px" >
			        	<option  value="">选择所有</option>
                        <option  value="1">全部归还</option>
                        <option  value="2">尚未归还</option>
		    		</select>
                </template:formTr>
                <template:formTr name="开始时间">
                	<input   id="begin" type="text"   readonly="readonly" name="begintime" class="inputtext" style="width:150" />
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
                              <html:button property="action" styleClass="button" onclick="toExportUseList()">Excel报表</html:button>
                            </td>
					    </template:formSubmit>
        	</template:formTable>
			</html:form>
    </logic:equal>




    <!--返还时查询领用单显示-->
    <logic:equal value="back3" name="type"  scope="session" >
      	<br />
        <template:titile value="条件查找待归还的领用单"/>
        <html:form action="/ToolUseAction?method=backQueryExec"   styleId="queryForm2" >
        	<template:formTable namewidth="200"  contentwidth="200">
            	<template:formTr name="操作人姓名"  >
            		<select name="userid"   class="inputtext" style="width:180px" >
			        	<option  value="">选择所有操作人</option>
                      	<logic:present name="useuser">
		                	<logic:iterate id="useuserId" name="useuser">
		                    	<option value="<bean:write name="useuserId" property="userid"/>"><bean:write name="useuserId" property="username"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
                <template:formTr name="领用人姓名"  >
            		<select name="usename"   class="inputtext" style="width:180px" >
			        	<option  value="">选择所有领用人</option>
                      	<logic:present name="lusename">
		                	<logic:iterate id="lusenameId" name="lusename">
		                    	<option value="<bean:write name="lusenameId" property="usename"/>"><bean:write name="lusenameId" property="usename"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
                  <template:formTr name="领用单位"  >
            		<select name="useunit"   class="inputtext" style="width:180px" >
			        	<option  value="">选择所有领用单位</option>
                      	<logic:present name="luseunit">
		                	<logic:iterate id="luseunitId" name="luseunit">
		                    	<option value="<bean:write name="luseunitId" property="useunit"/>"><bean:write name="luseunitId" property="useunit"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
              	<template:formTr name="领用原因" >
            		<select name="useremark" style="width:180px" class="inputtext"  >
			        	<option  value="">选择所有原因</option>
                      	<logic:present name="useremark">
		                	<logic:iterate id="luseremarkId" name="useremark">
		                    	<option value="<bean:write name="luseremarkId"  property="useremark"/>"><bean:write name="luseremarkId"  property="useremark"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr >
                <template:formTr name="开始时间">
                	<input   id="begin" type="text"   readonly="readonly" name="begintime" class="inputtext" style="width:150" />
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
					    </template:formSubmit>
        	</template:formTable>
    	</html:form>
    </logic:equal>


    <!--显示待返还的领用单-->
    <logic:equal value="back1" name="type" scope="session" >
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
    	   	<br />
            <template:titile value="待返还备件领用单一览表" />
    		<display:table name="sessionScope.useinfo" id="currentRowObject"  pagesize="18">
            	<display:column property="useunit" title="领用单位" maxLength="10" align="center"/>
                <display:column property="usename" title="领用人" maxLength="20"align="center"/>
                <display:column property="time" title="领用时间"maxLength="20"align="center"/>
                <display:column property="useremark" title="备件用途" maxLength="20"align="center"/>
                <display:column property="username" title="操作人" maxLength="10"/>

                <display:column media="html" title="操作" >
	            	 <%
					    BasicDynaBean  object2 = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
					    String id2 = (String) object2.get("useid");
					  %>
	            	 <a href="javascript:toGetFormForBack('<%=id2%>')">返还</a>
			  	</display:column>
    		</display:table>
            <html:link action="/ToolUseAction?method=exportBackResult">导出为Excel文件</html:link>
    </logic:equal>

      <!--显示返还备件页面-->
    <logic:equal value="back10"   name="type" scope="session" >
            <br />
      	<template:titile value="填写备件返还清单" />
        <html:form action="/ToolUseAction?method=addBack"   styleId="queryForm2" >
       		 	<input  type="hidden" name="useid" value="<bean:write name="useinfo" property="useid"/>"/>
          		<table align="center" width="600" border="0">
                    <tr >
                      	<td width="80" height="25"><b>领用单位:</b></td>
	                    <td width="120"align="left" ><bean:write name="useinfo" property="useunit"/></td>
	                    <td width="50" align="right" ><b>领用人:</b></td>
	                    <td  width="150"align="left"><bean:write name="useinfo" property="usename"/></td>
						 <td width="70" align="right"><b>领用时间:</b></td>
	                    <td width="130"align="left" ><bean:write name="useinfo" property="time"/></td>
		            </tr>
                    <tr>
	                	<td height="25"><b>领用原因:</b></td>
	                    <td colspan="5"><bean:write name="useinfo" property="useremark"/></td>
	                </tr>
	                 <tr>
                    	<td colspan="6"><hr /></td>
                    </tr>


                    <tr >
		            	<td height="25"><b>返还单位:</b></td>
	                    <td align="left" > <input type="text"  name="bunit" class="inputtext" value=" <bean:write name="useinfo" property="useunit"/>"/> </td>
	                    <td align="right" ><b>返还人:</b></td>
	                    <td align="left" > <input  type="text"  name="bname" class="inputtext" value="<bean:write name="useinfo" property="usename"/>" /></td>

		            </tr>
                    <tr>
	                	<td height="25"><b>返还备注:</b></td>
	                    <td colspan="5"><input  type="text"  name="bremark" class="inputtext" style="width:500;" maxlength="510"  value="<bean:write name="useinfo" property="useremark"/>" /></td>
	                </tr>
                     <tr >
		            	<td  height="25"><b>单位名称:</b></td>
	                    <td align="left" ><bean:write name="useinfo" property="contractorname"/></td>
	                    <td align="right"><b>操作人:</b></td>
	                    <td align="left" ><bean:write name="useinfo" property="username"/></td>
	                    <td align="right"><b>返还时间:</b></td>
	                    <td align="left"><bean:write name="date" /></td>
		            </tr>
	                 <tr>
                    	<td colspan="6"><hr /></td>
                    </tr>
	                <tr>
	                	<td colspan="6"   valign="bottom">
                        	<b>备件返还列表:</b>
                        </td>
	                </tr>
		        </table>
                <table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
	            	<tr>
	                	<th  width="25%" class="thlist" align="center">备件名称</th>
                        <th  width="20%" class="thlist" align="center">计量单位</th>
	                    <th  width="20%" class="thlist" align="center">规格型号</th>
	                    <th  width="10%" class="thlist" align="center">领用 </th>
                        <th  width="10%" class="thlist" align="center">已返还 </th>
                        <th  width="15%" class="thlist" align="center">返还 </th>


	            	</tr>
                    <logic:present name="usepartinfo">
                    	<logic:iterate id="useid" name="usepartinfo">
                        	<tr   >
                            	<td>
                                 <input type="hidden" name="id" value="<bean:write name="useid" property="id"/>" />	<bean:write name="useid" property="name"/>
                                </td>
                                <td>
                               		 <bean:write name="useid" property="unit"/>
                                </td>
                                <td>

                                		<bean:write name="useid" property="type"/>
                                </td>
                                <td>
                                	<input  type="hidden" id="use<bean:write name="useid" property="id"/>" value="<bean:write name="useid" property="enumber"/>"/>
                                  	<bean:write name="useid" property="enumber"/>
                                </td>
                                 <td>
                                 	<input  type="hidden" id="bac<bean:write name="useid" property="id"/>" value="<bean:write name="useid" property="bnumber"/>"/>
                                	<bean:write name="useid" property="bnumber"/>
                                </td>
                                 <td>
                                	<input type="text" name="bnumber"   class="inputtext"  onblur="valiForBackBnumber(id)" id="bed<bean:write name="useid" property="id"/>" style="width:80" value="0"/>
                                </td>

                       	 	</tr>
                    	</logic:iterate>
                    </logic:present>
	        	</table>
                <p align="center">
						       	 	<html:submit property="action" styleClass="button"  >提交返还	</html:submit>

						       	 	<html:button property="action" styleClass="button" onclick="javascript:history.back()" >返回	</html:button>
                </p>
            </html:form>
    </logic:equal>

        <!--显示待返还的备件-->
    <logic:equal value="back07" name="type" scope="session" >
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
            <br />
            <template:titile value="待返还备件一览表" />
    		<display:table name="sessionScope.shouldBackTool" id="currentRowObject"  pagesize="18">
            	<display:column property="name" title="备件名称" maxLength="10" align="center"/>
                <display:column property="style" title="备件类型" maxLength="20"align="center"/>
                <display:column property="type" title="备件型号"maxLength="20"align="center"/>
                <display:column property="tooluse" title="备件用途" maxLength="20"align="center"/>
                <display:column property="enumber" title="待还数量" maxLength="20"align="center"/>

                <display:column media="html" title="操作" >
	            	 <%
					    BasicDynaBean  object3 = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
					    String id3 = (String) object3.get("id");
					  %>
	            	 <a href="javascript:toGetFormForShouldBack('<%=id3%>')">领用详细</a>
			  	</display:column>
    		</display:table>
    </logic:equal>





	<iframe id="hiddenIframe" style="display:none"></iframe>
</body>
</html>
