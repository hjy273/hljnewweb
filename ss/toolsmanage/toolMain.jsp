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
                if(infoArr[i][3] == null || infoArr[i][3] =="")
                	type="无";
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
        s1.options[0].text = "请选择待报修备件";
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
        	alert("目前库存是" + oldnumber + ",不能报修这么多!!");
            this.focus();
            this.value=0
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
     	var url = "${ctx}/ToolMainAction.do?method=showOneMain&mainid=" + idValue;
        self.location.replace(url);
    }
     function toUpForm(idValue){
     	var url = "${ctx}/ToolMainAction.do?method=upShow&mainid=" + idValue;
        self.location.replace(url);
    }
	 function  addGoBack(){
	     	var url = "${ctx}/ToolMainAction.do?method=showAllMain";
	        self.location.replace(url);
	    }
   function toDeleForm(idValue){
     	var url = "${ctx}/ToolMainAction.do?method=deleMain&mainid=" + idValue;
        if(confirm("确定删除该纪录？"))
          self.location.replace(url);
    }
   function toShowMainForm(idValue){
     	var url = "${ctx}/ToolMainAction.do?method=showTool_Main&id=" + idValue;
        self.location.replace(url);
    }
    //选择日期
    function GetSelectDateTHIS(strID) {
    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    	return ;
	}
   function toExportMainList(){

     var userid = queryForm2.userid.value;
     var mainremark = queryForm2.mainremark.value;
     var begintime = queryForm2.begintime.value;
     var endtime = queryForm2.endtime.value;
     var url = "${ctx}/ToolMainAction.do?method=exportMainList&userid="+userid+"&mainremark="+mainremark+"&begintime="+begintime+"&endtime="+endtime;
     self.location.replace(url);

   }
</script>


<title>
partRequisition
</title>
</head>
<body>

	<!--填写报修单-->
	<logic:equal value="main2"  name="type"scope="session" >
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
        <template:titile value="填写备件报修单"/>
        <html:form action="/ToolMainAction?method=addMain" styleId="addForm">
		    <input  type="hidden" name="contractorid" value="<bean:write name="deptid"/>"/>
          	<input type="hidden" name="userid" value="<bean:write name="userid" />"/>
            <table align="center" width="600" border="0">
                <tr>
                	<td height="25"><b>报修备注:</b></td>
                    <td colspan="5"><html:text property="mainremark" styleClass="inputtext" style="width:500;" maxlength="510" value="请填写报修原因."></html:text></td>
                </tr>
                <tr >
	            	<td  height="25"><b>单位名称:</b></td>
                    <td align="left" ><bean:write name="deptname"/></td>
                    <td  align="right" ><b>操作人:</b></td>
                    <td align="left" ><bean:write name="username"/></td>
                    <td align="right"><b>报修时间:</b></td>
                    <td align="left" ><bean:write name="date"/></td>
	            </tr>
               <tr>
               		<td colspan="6"><hr/><br />请选择备件:</td>
               </tr>
	        </table>
        	<table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
            	<tr>
                	<th  width="20%" class="thlist" align="center">备件名称</th>
                    <th  width="15%" class="thlist" align="center">报修数量</th>
                    <th  width="10%" class="thlist" align="center">计量单位</th>
                    <th  width="20%" class="thlist" align="center">规格型号</th>
                    <th  width="8%"class="thlist" align="center">操作</th>
            	</tr>
        	</table>

	      	<p align="center">
              <html:button property="action" styleClass="button" onclick="addRow()">报修新备件</html:button>
              <html:button property="action" styleClass="button"  onclick="toAddSub()">提交报修单</html:button>
              <html:button property="action" styleClass="button" onclick="addGoBack()" >返回	</html:button>
			</p>
        </html:form>
	</logic:equal>


      <!--显示报修单-->
    <logic:equal value="mian1" name="type"scope="session" >
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
        <br />
            	<template:titile value="备件报修单一览表" />
	            <display:table name="sessionScope.maininfo" id="currentRowObject"  pagesize="18">
	            	<display:column property="username" title="报修人" maxLength="20"align="center"/>
	                <display:column property="time" title="报修时间"maxLength="20"align="center"/>
	                <display:column property="mainremark" title="报修原因" maxLength="30"align="center"/>

	                <display:column media="html" title="操作" >
                         <%
						   String id="";
                         	BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
                              id = (String) object.get("mainid");
						  %>
		            	 <a href="javascript:toGetForm('<%=id%>')">详细</a>
				  	</display:column>
	                <apptag:checkpower thirdmould="90304">
                        <display:column media="html" title="操作" >
			            	 <%
							   String id1="";
	                         	BasicDynaBean  object1 = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
	                              id1 = (String) object1.get("mainid");
							  %>
                          	<a href="javascript:toUpForm('<%=id1%>')">修改</a>
					  	</display:column>
	                 </apptag:checkpower>
	                 <apptag:checkpower thirdmould="90305">
		                <display:column media="html" title="操作" >
                        		<%
								   String id2="";
		                         	BasicDynaBean  object2 = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
		                              id2 = (String) object2.get("mainid");
								  %>
			            	 <a href="javascript:toDeleForm('<%=id2%>')">删除</a>
					  	</display:column>
	                 </apptag:checkpower>
	    		</display:table>
                <html:link action="/ToolMainAction?method=exportMainResult">导出为Excel文件</html:link>
    </logic:equal>

    <!--显示报修单详细信息-->
    <logic:equal value="main10"   name="type"scope="session" >
            <br />
      	<template:titile value="备件报修单详细信息" />
        	 <table align="center" width="600" border="0">
	          		<tr >
		            	<td width="60" height="25"><b>单位名称:</b></td>
	                    <td align="left" width="120"><bean:write name="maininfo" property="contractorname"/></td>
	                    <td  align="right" width="50"><b>报修人:</b></td>
	                    <td align="left" width="100"><bean:write name="maininfo" property="username"/></td>
	                    <td width="70" align="right"><b>报修时间:</b></td>
	                    <td align="left" width="100"><bean:write name="maininfo" property="time"/></td>
		            </tr>
                    <tr>
	                	<td height="25"><b>报修原因:</b></td>
	                    <td colspan="5"><bean:write name="maininfo" property="mainremark"/></td>
	                </tr>
	                 <tr>
                    	<td colspan="6"><hr /></td>
                    </tr>
	                <tr>
	                	<td colspan="6"   valign="bottom">
                        	<b>该报修单所报修的备件:</b>
                        </td>
	                </tr>
		        </table>
                <table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
	            	<tr>
	                	<th  width="25%" class="thlist" align="center">备件名称</th>
	                    <th  width="15%" class="thlist" align="center">报修数量 </th>
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

        <!--修改显示-->
     <logic:equal value="main4"   name="type"scope="session" >
            <br />
      	<template:titile value="修改备件报修单" />
        <logic:present name="maininfo">
	        <html:form action="/ToolMainAction?method=UpMain" styleId="UpForm">
	        	<input  type="hidden" name="mainid" value="<bean:write name="maininfo" property="mainid"/>"/>
	          	<input type="hidden" name="userid" value="<bean:write name="userid" />"/>
		        <table align="center" width="600" border="0">

		          		<tr >
			            	<td width="60" height="25"><b>单位名称:</b></td>
		                    <td align="left" width="120"><bean:write name="maininfo" property="contractorname"/></td>
		                    <td  align="right" width="50"><b>报修人:</b></td>
		                    <td align="left" width="100"><bean:write name="maininfo" property="username"/></td>
		                    <td width="70" align="right"><b>报修时间:</b></td>
		                    <td align="left" width="100"><bean:write name="maininfo" property="time"/></td>
			            </tr>
	                     <tr >
			            	<td height="25"width="60"><b>单位名称:</b></td>
		                    <td align="left"width="120" ><bean:write name="deptname"/></td>
		                    <td align="right" width="50"><b>修改人:</b></td>
		                    <td align="left" width="100"><bean:write name="username"/></td>
		                    <td align="right"width="70"><b>修改时间:</b></td>
		                    <td align="left"width="100" ><bean:write name="date"/></td>
			            </tr>
	                    <tr>
		                	<td height="25"><b>报修原因:</b></td>
		                    <td colspan="5"><input  type="text" name="mainremark" Class="inputtext" style="width:500;" maxlength="50" value="<bean:write name="maininfo" property="mainremark"/>"/> </td>
		                </tr>
		                <tr>
		                	<td colspan="6" height="40" valign="top">
		                    	<hr /><br>报修备件列表:
		                    </td>
		                </tr>
			        </table>
	                <table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
		            	<tr>
		                	<th  width="25%" class="thlist" align="center">备件名称</th>
		                    <th  width="15%" class="thlist" align="center">报修数量</th>
		                    <th  width="20%" class="thlist" align="center">计量单位</th>
		                    <th  width="25%" class="thlist" align="center">备件型号</th>
		                    <th  width="15%"class="thlist" align="center">生产厂家</th>
		            	</tr>
	                    <logic:present name="maintoolinfo">
	                    	<logic:iterate id="maintoolinfoid" name="maintoolinfo">
	                        	<tr >

	                            	<td>
	                                	<input type="hidden" name="id" value="<bean:write name="maintoolinfoid" property="id"/>"/>
	                                  	<bean:write name="maintoolinfoid" property="name"/>
	                                </td>
	                                <td>
	                                	<input  type="hidden" name="bnumber" value="<bean:write name="maintoolinfoid" property="enumber"/>" />
                                    	<input   name="enumber" maxlength="6" class="inputtext" size="6" onblur="valiDigit(id)" value="<bean:write name="maintoolinfoid" property="enumber"/>"/>
	                                </td>
	                                <td>
	                               		 <bean:write name="maintoolinfoid" property="unit"/>
	                                </td>
	                                <td>
	                                	<bean:write name="maintoolinfoid" property="type"/>
	                                </td>
	                               <td>
	                                	<bean:write name="maintoolinfoid" property="factory"/>
	                                </td>
	                       		 </tr>
	                    	</logic:iterate>
	                    </logic:present>
		        	</table>
	                <p align="center">
							       	 	<html:submit property="action" styleClass="button"   onclick="toUpSub()" title="一旦提交,该申请单的申请人就更改为修改人!">提交修改</html:submit>
							       	 	<html:button property="action" styleClass="button" onclick="addGoBack()" >返回	</html:button>
	                </p>
	        </html:form>
		 </logic:present>
    </logic:equal>

 <!--查询页面-->
    <logic:equal value="main3" name="type" scope="session" >
      	<br />
        <template:titile value="条件查找备件报修单"/>
        <html:form action="/ToolMainAction?method=queryExec"   styleId="queryForm2" >
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
              	<template:formTr name="报修原因" >
            		<select name="mainremark" style="width:180px" class="inputtext"  >
			        	<option  value="">选择所有原因</option>
                      	<logic:present name="mianremark">
		                	<logic:iterate id="mianremarkId" name="mianremark">
		                    	<option value="<bean:write name="mianremarkId"  property="mainremark"/>"><bean:write name="mianremarkId"  property="mainremark"/></option>
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
                              <html:button property="action" styleClass="button" onclick="toExportMainList()">Excel报表</html:button>
                            </td>
					    </template:formSubmit>
        	</template:formTable>
			</html:form>
    </logic:equal>

      <!--显示所有报修备件-->
    <logic:equal value="main6" name="type" scope="session" >
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>

            <br />
            	<template:titile value="已报修备件一览表" />
	            <display:table name="sessionScope.mainTool" id="currentRowObject"  pagesize="18">
	            	<display:column property="name" title="备件名称" maxLength="20"align="center"/>
	                <display:column property="unit" title="计量单位"maxLength="20"align="center"/>
                    <display:column property="style" title="备件类型"maxLength="20"align="center"/>
                    <display:column property="type" title="备件型号"maxLength="20"align="center"/>
                    <display:column property="factory" title="生产厂家"maxLength="20"align="center"/>
                    <display:column property="portmainnumber" title="报修数量"maxLength="20"align="center"/>

	                <display:column media="html" title="操作" >
                    	 <%
						    BasicDynaBean  object13 = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
						    String id13 = (String) object13.get("id");
						  %>
		            	 <a href="javascript:toShowMainForm('<%=id13%>')">涉及报修单</a>
				  	</display:column>
	    		</display:table>

    </logic:equal>
	<iframe id="hiddenIframe" style="display:none"></iframe>
</body>
</html>
