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
            	if(infoArr[i][3] ==null || infoArr[i][3] == "")
                	type ="无";
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
        s1.options[0].text = "请选择待送修备件";
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
        	alert("目前报修是" + oldnumber + ",不能送修这么多!!");
            this.focus();
            this.value=0;
            return false;
        }
        return true;
    }

        //添加一个新行(入库)
    function addRowBack(){
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
        t1.onblur = checknewBack;


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
    function checknewBack(){
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
        	alert("目前送修是" + oldnumber + ",不能入库这么多!!");
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
     	var url = "${ctx}/ToolToMainAction.do?method=showOneMain&mainid=" + idValue;
        self.location.replace(url);
    }
    function  addGoBack(){
	     	var url = "${ctx}/ToolToMainAction.do?method=showAllMain";
	        self.location.replace(url);
	    }

   function toShowMainForm(idValue){
     	var url = "${ctx}/ToolToMainAction.do?method=showTool_Main&id=" + idValue;
        self.location.replace(url);
    }
    //选择日期
    function GetSelectDateTHIS(strID) {
    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    	return ;
	}
   function toExportToMainList(){

     var userid = queryForm2.userid.value;
     var mainunit = queryForm2.mainunit.value;
     var mainname = queryForm2.mainname.value;
     var begintime = queryForm2.begintime.value;
     var endtime = queryForm2.endtime.value;
     var url = "${ctx}/ToolToMainAction.do?method=exportToMainList&userid="+userid+"&mainunit="+mainunit+"&mainname="+mainname+"&begintime="+begintime+"&endtime="+endtime;
     self.location.replace(url);

   }
</script>


<title>
partRequisition
</title>
</head>
<body>

	<!--填写送修单-->
	<logic:equal value="main2"  name="type" scope="session" >
    	<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
        <logic:present name="baseinfo">
        	<logic:iterate id="baseinfoId" name="baseinfo">
            	<script type="" language="javascript">
                	initArray("<bean:write name="baseinfoId" property="id"/>","<bean:write name="baseinfoId" property="name"/>",
                    		  "<bean:write name="baseinfoId" property="unit"/>","<bean:write name="baseinfoId" property="type"/>",
                              "<bean:write name="baseinfoId" property="portmainnumber"/>");
                </script>
        	</logic:iterate>
        </logic:present>

        <br/>
        <template:titile value="填写备件送修单"/>
        <html:form action="/ToolToMainAction?method=addMain" styleId="addForm">
		    <input  type="hidden" name="contractorid" value="<bean:write name="deptid"/>"/>
          	<input type="hidden" name="userid" value="<bean:write name="userid" />"/>
            <table align="center" width="600" border="0">

                 <tr >
	            	<td  width="70"><b>维修单位:</b></td>
                    <td align="left" ><input name="mainunit" type="text"  maxlength="20" class="inputtext" value="" /></td>
                   <td  height="25" width="80" align="right"><b>送修人:</b></td>
                    <td align="left" ><input name="mainname" type="text"  maxlength="5" class="inputtext" value=""  />  </td>
                    <td align="right" width="80"><b>联系电话:</b></td>
                    <td align="left" ><input name="mainphone" type="text" maxlength="15" class="inputtext" value="" style="width:80" /></td>
	            </tr>
                <tr>
                	<td height="25"><b>维修地址:</b></td>
                    <td colspan="5"><html:text property="mainadd" styleClass="inputtext" style="width:500" maxlength="510" value=""></html:text></td>
                </tr>
                <tr>
                	<td height="25"><b>送修备注:</b></td>
                    <td colspan="5"><html:text property="mainremark" styleClass="inputtext" style="width:520" maxlength="510" value="请填写送修备注."></html:text></td>
                </tr>
                <tr >
	            	<td  height="25"><b>单位名称:</b></td>
                    <td align="left" ><bean:write name="deptname"/></td>
                    <td align="right"><b>送修时间:</b></td>
                    <td align="left" ><bean:write name="date"/></td>
                    <td  align="right" ><b>操作人:</b></td>
                    <td align="left" ><bean:write name="username"/></td>

	            </tr>
               <tr>
               		<td colspan="6"><hr/><br />请选择备件:</td>
               </tr>
	        </table>
        	<table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
            	<tr>
                	<th  width="20%" class="thlist" align="center">备件名称</th>
                    <th  width="15%" class="thlist" align="center">送修数量</th>
                    <th  width="10%" class="thlist" align="center">计量单位</th>
                    <th  width="20%" class="thlist" align="center">规格型号</th>
                    <th  width="8%"class="thlist" align="center">操作</th>
            	</tr>
        	</table>

	      	<p align="center">
              <html:button property="action" styleClass="button" onclick="addRow()">送修新备件</html:button>
              <html:button property="action" styleClass="button"  onclick="toAddSub()">提交送修单</html:button>
              <html:button property="action" styleClass="button" onclick="addGoBack()" >返回	</html:button>
			</p>
        </html:form>
	</logic:equal>


      <!--显示送修单-->
    <logic:equal value="mian1" name="type" scope="session" >
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
           <br />
           <template:titile value="备件送修单一览表" />
	            <display:table name="sessionScope.maininfo" id="currentRowObject"  pagesize="18">
	            	<display:column property="mainname" title="送修人" maxLength="20"align="center"/>
                    <display:column property="mainunit" title="维修单位" maxLength="20"align="center"/>
                    <display:column property="username" title="送修人" maxLength="20"align="center"/>
	                <display:column property="time" title="送修时间"maxLength="20"align="center"/>
	                <display:column property="mainremark" title="送修原因" maxLength="30"align="center"/>

	                <display:column media="html" title="操作" >
                     	  <%
						    BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
						    String id = (String) object.get("mainid");
						  %>
		            	 <a href="javascript:toGetForm('<%=id%>')">详细</a>
				  	</display:column>
                </display:table>
                <html:link action="/ToolToMainAction?method=exportToMainResult">导出为Excel文件</html:link>
    </logic:equal>

    <!--显示送修单详细信息-->
    <logic:equal value="main10"   name="type" scope="session" >
            <br />
      	<template:titile value="备件送修单详细信息" />
        	 <table align="center" width="600" border="0">
	          		<tr >
		            	<td width="60" height="25"><b>单位名称:</b></td>
	                    <td align="left" width="120"><bean:write name="maininfo" property="contractorname"/></td>
	                    <td width="50" ><b>操作人:</b></td>
	                    <td align="left" width="100"><bean:write name="maininfo" property="username"/></td>
	                    <td width="70" align="right"><b>送修时间:</b></td>
	                    <td align="left" width="100"><bean:write name="maininfo" property="time"/></td>
		            </tr>
                    <tr >
		            	<td  ><b>维修单位:</b></td>
	                    <td align="left" ><bean:write name="maininfo" property="mainunit"/></td>
                        <td width="60" height="25" ><b>送修人:</b></td>
	                    <td align="left" ><bean:write name="maininfo" property="mainname"/></td>
	                    <td width="70" align="right"><b>联系电话:</b></td>
	                    <td align="left" ><bean:write name="maininfo" property="mainphone"/></td>
		            </tr>
                     <tr >

	                    <td  ><b>维修地址:</b></td>
	                    <td align="left"  colspan="5"><bean:write name="maininfo" property="mainadd"/></td>

		            </tr>
                    <tr>
	                	<td height="25"><b>送修原因:</b></td>
	                    <td colspan="5"><bean:write name="maininfo" property="mainremark"/></td>
	                </tr>
	                 <tr>
                    	<td colspan="6"><hr /></td>
                    </tr>
	                <tr>
	                	<td colspan="6"   valign="bottom">
                        	<b>该送修单所送修的备件:</b>
                        </td>
	                </tr>
		        </table>
                <table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
	            	<tr>
	                	<th  width="25%" class="thlist" align="center">备件名称</th>
	                    <th  width="15%" class="thlist" align="center">送修数量 </th>
                        <th  width="20%" class="thlist" align="center">计量单位</th>
	                    <th  width="25%" class="thlist" align="center">规格型号</th>
                         <th  width="25%" class="thlist" align="center">备件类型</th>

	            	</tr>
                    <logic:present name="maintoolinfo">
                    	<logic:iterate id="maintoolinfoid" name="maintoolinfo">
                        	<tr   >
                            	<td>
                                  	<bean:write name="maintoolinfoid" property="name"/>
                                </td>
                                <td>
                                	<bean:write name="maintoolinfoid" property="enumber"/>
                                </td>
                                <td>
                               		 <bean:write name="maintoolinfoid" property="unit"/>
                                </td>
                                <td>
                                	<bean:write name="maintoolinfoid" property="type"/>
                                </td>
                                  <td>
                                	<bean:write name="maintoolinfoid" property="style"/>
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
    <logic:equal value="main3" name="type" scope="session"  >
      	<br />
        <template:titile value="条件查找备件送修单"/>
        <html:form action="/ToolToMainAction?method=queryExec"   styleId="queryForm2" >
        	<template:formTable namewidth="200"  contentwidth="200">
            	<template:formTr name="操作人姓名"  >
            		<select name="userid"   class="inputtext" style="width:180px" >
			        	<option  value="">选择所有操作人</option>
                      	<logic:present name="mainuser">
		                	<logic:iterate id="mainuserId" name="mainuser">
		                    	<option value="<bean:write name="mainuserId" property="userid"/>"><bean:write name="mainuserId" property="username"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
              	<template:formTr name="维修单位" >
            		<select name="mainunit" style="width:180px" class="inputtext"  >
			        	<option  value="">选择所有单位</option>
                      	<logic:present name="mainunit">
		                	<logic:iterate id="mainunitId" name="mainunit">
		                    	<option value="<bean:write name="mainunitId"  property="mainunit"/>"><bean:write name="mainunitId"  property="mainunit"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr >
                <template:formTr name="送修人" >
            		<select name="mainname" style="width:180px" class="inputtext"  >
			        	<option  value="">选择所有送修人</option>
                      	<logic:present name="mainname">
		                	<logic:iterate id="mainnameId" name="mainname">
		                    	<option value="<bean:write name="mainnameId"  property="mainname"/>"><bean:write name="mainnameId"  property="mainname"/></option>
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
                            <td width="85">
                              <html:button property="action" styleClass="button" onclick="toExportToMainList()">Excel报表</html:button>
                            </td>
					    </template:formSubmit>
        	</template:formTable>
			</html:form>
    </logic:equal>

      <!--显示所有送修备件-->
    <logic:equal value="main6" name="type" scope="session" >
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
               	<br />
        <template:titile value="送修备件一览表" />
	            <display:table name="sessionScope.mainTool" id="currentRowObject"  pagesize="18">
	            	<display:column property="name" title="备件名称" maxLength="20"align="center"/>
	                <display:column property="unit" title="计量单位"maxLength="20"align="center"/>
                    <display:column property="style" title="备件类型"maxLength="20"align="center"/>
                    <display:column property="type" title="备件型号"maxLength="20"align="center"/>
                    <display:column property="factory" title="生产厂家"maxLength="20"align="center"/>
                    <display:column property="mainnumber" title="送修数量"maxLength="20"align="center"/>

	                <display:column media="html" title="操作" >
                    <%
						    BasicDynaBean  object3 = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
						    String id3 = (String) object3.get("id");
						  %>
		            	 <a href="javascript:toShowMainForm('<%=id3%>')">涉及送修单</a>
				  	</display:column>
	    		</display:table>
    </logic:equal>

    	<!--填写送修入库单-->
	<logic:equal value="main7"  name="type" scope="session" >
    	<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
        <logic:present name="baseinfo">
        	<logic:iterate id="baseinfoId" name="baseinfo">
            	<script type="" language="javascript">
                	initArray("<bean:write name="baseinfoId" property="id"/>","<bean:write name="baseinfoId" property="name"/>",
                    		  "<bean:write name="baseinfoId" property="unit"/>","<bean:write name="baseinfoId" property="type"/>",
                              "<bean:write name="baseinfoId" property="mainnumber"/>");
                </script>
        	</logic:iterate>
        </logic:present>


		        <br/>
		        <template:titile value="填写备件送修入库单"/>
		        <html:form action="/ToolToMainAction?method=addMainBack" styleId="addForm">
				    <input  type="hidden" name="contractorid" value="<bean:write name="deptid"/>"/>
		          	<input type="hidden" name="userid" value="<bean:write name="userid" />"/>
		            <table align="center" width="600" border="0">

		                 <tr >
			            	<td  width="100"><b>维修单位:</b></td>
		                    <td width="160"align="left" ><input name="mainunit" type="text" class="inputtext" value="" /></td>
		                    <td align="right" width="80"><b>联系电话:</b></td>
		                    <td width="120"align="left" ><input name="mainphone" type="text" class="inputtext" value="" style="width:80" /></td>
		                     <td  height="25" width="80" align="right"><b>入库人:</b></td>
		                    <td width="80"align="left" ><input name="mainname" type="text"  style="width:70px" class="inputtext" value=""  /> </td>
			            </tr>
		                <tr>
		                	<td height="25"><b>维修地址:</b></td>
		                    <td colspan="5"><html:text property="mainadd" styleClass="inputtext" style="width:520px" maxlength="510" value=""></html:text></td>
		                </tr>
		                <tr>
		                	<td height="25"><b>入库备注:</b></td>
		                    <td colspan="5"><html:text property="mainremark" styleClass="inputtext" style="width:520" maxlength="510" value="请填写送修入库备注."></html:text></td>
		                </tr>
		                <tr >
			            	<td  height="25"><b>单位名称:</b></td>
		                    <td align="left" ><bean:write name="deptname"/></td>
		                    <td align="right"><b>入库时间:</b></td>
		                    <td align="left" ><bean:write name="date"/></td>
		                    <td  align="right" ><b>操作人:</b></td>
		                    <td align="left" ><bean:write name="username"/></td>

			            </tr>
		               <tr>
		               		<td colspan="6"><hr/><br />请选择备件:</td>
		               </tr>
			        </table>
		        	<table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
		            	<tr>
		                	<th  width="20%" class="thlist" align="center">备件名称</th>
		                    <th  width="15%" class="thlist" align="center">入库数量</th>
		                    <th  width="10%" class="thlist" align="center">计量单位</th>
		                    <th  width="20%" class="thlist" align="center">规格型号</th>
		                    <th  width="8%"class="thlist" align="center">操作</th>
		            	</tr>
		        	</table>

			      	<p align="center">
		              <html:button property="action" styleClass="button" onclick="addRowBack()">入库新备件</html:button>
		              <html:button property="action" styleClass="button"  onclick="toAddSub()">提交入库单</html:button>
		              <html:button property="action" styleClass="button" onclick="addGoBack()" >返回	</html:button>
					</p>
	        </html:form>

	</logic:equal>


    <!--填写送修报废单-->
	<logic:equal value="main8"  name="type" scope="session" >
    	<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
        <logic:present name="baseinfo">
        	<logic:iterate id="baseinfoId" name="baseinfo">
            	<script type="" language="javascript">
                	initArray("<bean:write name="baseinfoId" property="id"/>","<bean:write name="baseinfoId" property="name"/>",
                    		  "<bean:write name="baseinfoId" property="unit"/>","<bean:write name="baseinfoId" property="type"/>",
                              "<bean:write name="baseinfoId" property="mainnumber"/>");
                </script>
        	</logic:iterate>
        </logic:present>

        <br/>
	        <template:titile value="填写备件送修报废单"/>
	        <html:form action="/ToolToMainAction?method=addMainDele" styleId="addForm">
			    <input  type="hidden" name="contractorid" value="<bean:write name="deptid"/>"/>
	          	<input type="hidden" name="userid" value="<bean:write name="userid" />"/>
	            <table align="center" width="600" border="0">

	                 <tr >
		            	<td  width="100"><b>维修单位:</b></td>
	                    <td width="160"align="left" ><input name="mainunit" type="text" class="inputtext" value="" /></td>
	                    <td align="right" width="80"><b>联系电话:</b></td>
	                    <td width="120"align="left" ><input name="mainphone" type="text" class="inputtext" value="" style="width:80" /></td>
	                     <td  height="25" width="80" align="right"><b>报废人:</b></td>
	                    <td width="80"align="left" ><input name="mainname" type="text"  style="width:70px" class="inputtext" value=""  /> </td>
		            </tr>
	                <tr>
	                	<td height="25"><b>维修地址:</b></td>
	                    <td colspan="5"><html:text property="mainadd" styleClass="inputtext" style="width:520px" maxlength="510" value=""></html:text></td>
	                </tr>
	                <tr>
	                	<td height="25"><b>报废备注:</b></td>
	                    <td colspan="5"><html:text property="mainremark" styleClass="inputtext" style="width:520" maxlength="510" value="请填写送修入库备注."></html:text></td>
	                </tr>
	                <tr >
		            	<td  height="25"><b>单位名称:</b></td>
	                    <td align="left" ><bean:write name="deptname"/></td>
	                    <td align="right"><b>报废时间:</b></td>
	                    <td align="left" ><bean:write name="date"/></td>
	                    <td  align="right" ><b>操作人:</b></td>
	                    <td align="left" ><bean:write name="username"/></td>

		            </tr>
	               <tr>
	               		<td colspan="6"><hr/><br />请选择备件:</td>
	               </tr>
		        </table>
	        	<table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
	            	<tr>
	                	<th  width="20%" class="thlist" align="center">备件名称</th>
	                    <th  width="15%" class="thlist" align="center">报废数量</th>
	                    <th  width="10%" class="thlist" align="center">计量单位</th>
	                    <th  width="20%" class="thlist" align="center">规格型号</th>
	                    <th  width="8%"class="thlist" align="center">操作</th>
	            	</tr>
	        	</table>

		      	<p align="center">
	              <html:button property="action" styleClass="button" onclick="addRowBack()">报废新备件</html:button>
	              <html:button property="action" styleClass="button"  onclick="toAddSub()">提交报废单</html:button>
	              <html:button property="action" styleClass="button" onclick="addGoBack()" >返回	</html:button>
				</p>
	        </html:form>
	</logic:equal>

	<iframe id="hiddenIframe" style="display:none"></iframe>
</body>
</html>
