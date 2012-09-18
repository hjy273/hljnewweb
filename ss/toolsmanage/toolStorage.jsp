<%@include file="/common/header.jsp"%>

<html>
<head>
<script type="" language="javascript">
	var rowArr = new Array();//一行材料的信息
    var infoArr = new Array();//所有材料的信息

    //初始化数组
    function initArray(id,name,unit,type,newesse,oldnumber){
    	rowArr[0] = id;
        rowArr[1] = name;
        rowArr[2] = unit;
        rowArr[3] = type;
        rowArr[4] = newesse;
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
                if(infoArr[i][3] == null || infoArr[i][3] == "")
                	type = "无";
                else
                	type = infoArr[i][3];
            }
        }
        //写入表格
        tr.cells[1].innerText =unit;
        tr.cells[2].innerText =type;
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
        var   cell6=onerow.insertCell();
        var   cell7=onerow.insertCell();
		//创建一个输入框(库存量)
        var t1 = document.createElement("input");
        t1.name = "essenumber"
		t1.id = "text" + onerow.id;
        t1.value= "0";
        t1.maxLength = "6";
        t1.size = "7";
        t1.onblur = checknew;
        //创建一个输入框(报修量)
         var t2 = document.createElement("input");
        t2.name = "portmainnumber"
		t2.id = "tex2" + onerow.id;
        t2.value= "0";
        t2.maxLength = "6";
        t2.size = "7";
        t2.onblur = checkold;
        //创建一个输入框(送修量)
         var t3 = document.createElement("input");
        t3.name = "mainnumber"
		t3.id = "tex3" + onerow.id;
        t3.value= "0";
        t3.maxLength = "6";
        t3.size = "7";
        t3.onblur = checkold;

        //创建一个select
        var s1 = document.createElement("select");
        s1.options.length = infoArr.length + 1;
        s1.options[0].value = "";
        s1.options[0].text = "请选择备件";
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
        cell2.innerText="没选备件";//文字
        cell3.innerText="没选备件";//文字
        cell4.appendChild(t1);
        cell5.appendChild(t2);
        cell6.appendChild(t3);
        cell7.appendChild(b1);
	}
    function checkold(){
    	var mysplit = /^\d{1,6}$/;
        if(!mysplit.test(this.value)){
        	alert("你填写的不是数字,请重新输入");
            this.value=0;
            this.focus();
        }
    }
     function checknew(){
    	var mysplit = /^\d{1,6}$/;
        if(!mysplit.test(this.value)){
        	alert("你填写的不是数字,请重新输入");
            this.value=0;
            this.focus();
        }
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

        }
    }

        //添加提交
    function toAddSub(){
      if(queryID.rows.length<2){
      	alert("你还没有选择备件,不能提交!")
        return false;
      }
      if(confirm("你确认要初始化库存吗?\n选择确定提交该初始化单,取消返回."))
    	addForm.submit();
      else
      	return false;
    }



   function  addGoBack(){
     	var url = "${ctx}/ToolStorageAction.do?method=showAllStorage";
        self.location.replace(url);
    }
    //选择日期
    function GetSelectDateTHIS(strID) {
    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    	return ;
	}
</script>


<title>
partRequisition
</title>
</head>
<body>

     <!--显示所有库存-->
    <logic:equal value="st1" name="type" scope="session" >
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
            <br />
        <template:titile value="备件库存一览表" />
    		<display:table name="sessionScope.storageinfo" id="currentRowObject"  pagesize="18" align="center">
            	<display:column property="contractorname" title="单位名称" maxLength="10" align="center" />
              	<display:column property="name" title="备件名称" maxLength="10" align="center" />
                 <display:column property="unit" title="计量单位" maxLength="20"align="center" />
                <display:column property="type" title="规格型号" maxLength="10" />
                <display:column property="essenumber" title="库 存 量" maxLength="20"align="center"/>
                <display:column property="portmainnumber" title="报 修 量"maxLength="20"align="center" />
                <display:column property="mainnumber" title="送 修 量"maxLength="20"align="center" />
             </display:table>
            <html:link action="/ToolStorageAction?method=exportStorageResult">导出为Excel文件</html:link>
    </logic:equal>
     <!--查询页面-->
    <logic:equal value="st3" name="type"  scope="session" >
      	<br />
        <template:titile value="条件查找库存"/>
        <html:form action="/ToolStorageAction?method=queryExec"   styleId="queryForm2" >
        	<template:formTable namewidth="200"  contentwidth="200">
		           <logic:equal value="1" name="unittype">
		           		<logic:present name="contractorname">
		              		<template:formTr name="代维单位"  >
			            		<select name="contractorid"   class="inputtext" style="width:180px" >
						        	<option  value="">选择所有单位</option>
					                	<logic:iterate id="contractornameId" name="contractorname">
					                    	<option value="<bean:write name="contractornameId" property="contractorid"/>"><bean:write name="contractornameId" property="contractorname"/></option>
					                	</logic:iterate>
					    		</select>
			                </template:formTr>
	                    </logic:present>
                    </logic:equal>
               <template:formTr name="备件名称"  >
            		<select name="id"   class="inputtext" style="width:180px" >
			        	<option  value="">选择所有备件</option>
                      	<logic:present name="toolname">
		                	<logic:iterate id="toolnameId" name="toolname">
		                    	<option value="<bean:write name="toolnameId" property="id"/>"><bean:write name="toolnameId" property="name"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
              	<template:formTr name="备件类型" >
            		<select name="type" style="width:180px" class="inputtext"  >
			        	<option  value="">选择所有类型</option>
                      	<logic:present name="tooltype">
		                	<logic:iterate id="tooltypeId" name="tooltype">
		                    	<option value="<bean:write name="tooltypeId"  property="type"/>"><bean:write name="tooltypeId"  property="type"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr >
                <template:formTr name="库 存 量" >
            		<b>大于&nbsp;&nbsp;</b><input   type="text" name="esselownumber" id="newlow" class="inputtext" size="10" maxlength="6"   onkeyup="valiDigit(id)"/>
                    <b>小于&nbsp;&nbsp;</b><input   type="text" name="essehighnumber" id="newhig" class="inputtext" size="10" maxlength="6"   onkeyup="valiDigit(id)"/>
                </template:formTr >
                 <template:formTr name="报 修 量" >
            		<b>大于&nbsp;&nbsp;</b><input   type="text" name="portlownumber" id="oldlow" class="inputtext" size="10" maxlength="6"  onkeyup="valiDigit(id)"/>
                    <b>小于&nbsp;&nbsp;</b><input   type="text" name="porthighnumber" id="oldhig" class="inputtext" size="10" maxlength="6"   onkeyup="valiDigit(id)"/>
                </template:formTr >
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


    <!--初始化库存-->
	<logic:equal value="st2"  name="type" scope="session" >
    	<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
        <logic:present name="baseinfo">
        	<logic:iterate id="baseinfoId" name="baseinfo">
            	<script type="" language="javascript">
                	initArray("<bean:write name="baseinfoId" property="id"/>","<bean:write name="baseinfoId" property="name"/>",
                    		  "<bean:write name="baseinfoId" property="unit"/>","<bean:write name="baseinfoId" property="type"/>",
                             "0","0");
                </script>
        	</logic:iterate>
        </logic:present>

        <br/>
        <template:titile value="初始化备件库存"/>
        <html:form action="/ToolStorageAction?method=initStorage" styleId="addForm">
		    <input  type="hidden" name="contractorid" value="<bean:write name="deptid"/>"/>
          	<input type="hidden" name="useuserid" value="<bean:write name="userid" />"/>
            <table align="center" width="600" border="0">
	        	<tr >
	            	<td width="60" height="25"><b>单位名称:</b></td>
                    <td align="left" width="100"><bean:write name="deptname"/></td>
                    <td  align="right" width="50"><b>操作人:</b></td>
                    <td align="left" width="100"><bean:write name="username"/></td>
                    <td width="100" align="right"><b>操作时间:</b></td>
                    <td align="left" width="100"><bean:write name="date"/></td>
	            </tr>
                <tr >
	            	<td width="60" height="25" valign="top"><b>备注信息:</b></td>
                    <td align="left" colspan="5">
                    	初始化备件库存是指将库存材料数量设定为指定的值,该功能可用于两个方面:<br />
                        1、系统刚开始运行的时候输入现有的库存量.<br />
                        2、在库存盘点中根据库存实际值设定库存量.
                    </td>

	            </tr>
                <tr>
                	<td colspan="6">
                    	<hr /><br />请选择备件:
                    </td>
                </tr>
	        </table>
        	<table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
            	<tr>
                	<th  width="20%" class="thlist" align="center">备件名称</th>
                    <th  width="10%" class="thlist" align="center">计量单位</th>
                    <th  width="20%" class="thlist" align="center">规格型号</th>
                     <th  width="10%" class="thlist" align="center">库 存 量</th>
                     <th  width="10%" class="thlist" align="center">报 修 量</th>
                     <th  width="10%" class="thlist" align="center">送 修 量</th>
                    <th  width="8%"class="thlist" align="center">操作</th>
            	</tr>
        	</table>
            <p align="center">
	                          <html:button property="action" styleClass="button" onclick="addRow()">添加备件</html:button>
					       	 	<html:button property="action" styleClass="button"  onclick="toAddSub()">提交初始化</html:button>
					       	 	<html:button property="action" styleClass="button" onclick="addGoBack()" >返回	</html:button>
            </p>

        </html:form>
	</logic:equal>
	<iframe id="hiddenIframe" style="display:none"></iframe>
</body>
</html>

