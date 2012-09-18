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
                type = infoArr[i][3]
            }
        }
        //写入表格
        var tu = document.createElement("input");
        tu.value= unit;
        tu.maxLength = "6";
        tu.size = "6";
        tu.style.background="#C6D6E2"
        tu.style.font.size="12px";
        tu.readonly="readonly";
        tr.cells[1].innerText ="";
        tr.cells[1].appendChild(tu);

        var tt = document.createElement("input");
        tt.value= type;
        tt.maxLength = "6";
        tt.style.width="160";
        tt.style.background="#C6D6E2"
        tt.style.font.size="12px";
        tt.readonly="readonly";
        tr.cells[2].innerText ="";
        tr.cells[2].appendChild(tt);

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
        //创建一个输入框(新材料)
        var t1 = document.createElement("input");
        t1.name = "newesse"
        t1.id = "text" + onerow.id;
        t1.value= "0";
        t1.maxLength = "6";
        t1.size = "8";
        t1.onblur = checknew;
        t1.style.background="#C6D6E2"
        t1.style.font.size="12px";
        //创建一个输入框(旧材料)
         var t2 = document.createElement("input");
        t2.name = "oldnumber"
        t2.id = "tex2" + onerow.id;
        t2.value= "0";
        t2.maxLength = "6";
        t2.size = "8";
        t2.onblur = checkold;
        t2.style.background="#C6D6E2"
        t2.style.font.size="12px";

        //创建一个select
        var s1 = document.createElement("select");
        s1.options.length = infoArr.length + 1;
        s1.options[0].value = "";
        s1.options[0].text = "请选择材料名称";
        for(i = 1; i<s1.options.length ;i++){
            s1.options[i].value = infoArr[i-1][0];
            s1.options[i].text = infoArr[i-1][1] + " -- " + infoArr[i-1][3];
        }
        s1.name = "id";
        s1.id = "select" + onerow.id;
        s1.onchange = onselectchange;
        s1.style.background="#C6D6E2"
        s1.style.font.size="12px";
        s1.style.width="250";

        //创建删除按钮
        var b1 =document.createElement("button");
        b1.value = "删除";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
        //alert(b1.id + "rrr");

        cell1.appendChild(s1);//文字
        cell2.innerText="没选材料";//文字
        cell3.innerText="没选材料";//文字
        cell4.appendChild(t1);//文字
        cell5.appendChild(t2);//文字
        cell6.appendChild(b1);
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
            this.value=0
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
            obj.value=0

        }
    }

        //添加提交
    function toAddSub(){
      if(queryID.rows.length<2){
          alert("你还没有选择材料,不能提交!")
        return false;
      }
      if(confirm("你确认要初始化库存吗?\n选择确定提交该初始化单,取消返回."))
        addForm.submit();
      else
          return false;
    }

    //显示页面详细信息按钮动作
   function toGetForm(idValue){
         var url = "${ctx}/PartUseAction.do?method=showOneUse&useid=" + idValue;
        self.location.replace(url);
    }

   function  addGoBack(){
         var url = "${ctx}/PartStorageAction.do?method=showAllStorage";
        self.location.replace(url);
    }

	//返回到上级查询页面
	function goquery(){
		window.location.href = "${ctx}/PartStorageAction.do?method=queryShow";
	}


    //选择日期
    function GetSelectDateTHIS(strID) {
        document.all.item(strID).value = getPopDate(document.all.item(strID).value);

       //开始时间
    	var yb = queryForm2.begintime.value.substring(0,4);
		var mb = parseInt(queryForm2.begintime.value.substring(5,7) ,10);
		var db = parseInt(queryForm2.begintime.value.substring(8,10),10);
    	//结束时间
    	var ye = queryForm2.endtime.value.substring(0,4);
		var me = parseInt(queryForm2.endtime.value.substring(5,7) ,10);
		var de = parseInt(queryForm2.endtime.value.substring(8,10),10);
    	if(yb == "" && ye != "" ) {
	  		alert("请输入查询的开始时间!");      		
      		queryForm2.begintime.focus();
      		return false;
		}
		// 在开始和结束时间都输入的情况下做不能跨年度的判断
		if(yb != "" && ye != "") {
			//if(yb!=ye){
      		//	alert("查询时间段不能跨年度!");
      		//	document.all.item(strID).value="";
      		//	queryForm2.endtime.focus();
      		//	return false;
    		//}
			if(ye < yb) {
				alert("查询结束日期不能小于开始日期!");
      			document.all.item(strID).value="";
      			queryForm2.endtime.focus();
      			return false;
			}
			if((ye == yb ) && (me < mb  || de < db)) {
      			alert("查询结束日期不能小于开始日期!");
     		 	document.all.item(strID).value="";
      			queryForm2.endtime.focus();
      			return false;
    		}
		}
        return true;
    }
</script>


<title>
partRequisition
</title>
</head>
<body>

     <!--显示所有库存-->
    <logic:equal value="st1" scope="session"name="type">
        <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
               <apptag:checkpower thirdmould="80601" ishead="1">
                <jsp:forward page="/globinfo/powererror.jsp"/>
            </apptag:checkpower>
            <br />
            <template:titile value="材料库存一览表" />
            <display:table name="sessionScope.storageinfo" id="currentRowObject"  pagesize="18" style="align:center">
                <display:column property="contractorname" title="单位名称" maxLength="10" style="align:center" />
                  <display:column property="name" title="材料名称" maxLength="10" style="align:center" />
                <display:column property="unit" title="计量单位" maxLength="20" style="align:center" />
                <display:column property="type" title="规格型号" maxLength="10" />
                <display:column property="newshould" title="新材料应有库存" maxLength="20"style="align:center"/>
                <display:column property="newesse" title="新材料库存" maxLength="20"style="align:center"/>
                <display:column property="oldnumber" title="旧材料库存"maxLength="20"style="align:center" />


             </display:table>
           	 <logic:notEmpty name="storageinfo">
             	<html:link action="/PartStorageAction.do?method=exportStorageResult">导出为Excel文件</html:link><br>
             </logic:notEmpty>
             <div align="center"><html:button property="action" styleClass="button" onclick="goquery()" >返回</html:button></div>
    </logic:equal>
     <!--查询页面-->
    <logic:equal value="st3" scope="session"name="type" >
        <apptag:checkpower thirdmould="80303" ishead="1">
            <jsp:forward page="/globinfo/powererror.jsp"/>
        </apptag:checkpower>
          <br />
        <template:titile value="条件查找库存"/>
        <html:form action="/PartStorageAction?method=queryExec"   styleId="queryForm2" >
            <template:formTable namewidth="200"  contentwidth="200">
                   <logic:equal value="1" name="unittype">
                           <logic:present name="contractorname">
                              <template:formTr name="单位名称"  >
                                <select name="id"   class="inputtext" style="width:180px" >
                                    <option  value="">选择所有单位</option>
                                        <logic:iterate id="contractornameId" name="contractorname">
                                            <option value="<bean:write name="contractornameId" property="contractorid"/>"><bean:write name="contractornameId" property="contractorname"/></option>
                                        </logic:iterate>
                                </select>
                            </template:formTr>
                        </logic:present>
                    </logic:equal>
               <template:formTr name="材料名称"  >
                    <select name="name"   class="inputtext" style="width:180px" >
                        <option  value="">选择所有材料</option>
                          <logic:present name="partname">
                            <logic:iterate id="partnameId" name="partname">
                                <option value="<bean:write name="partnameId" property="name"/>"><bean:write name="partnameId" property="name"/></option>
                            </logic:iterate>
                        </logic:present>
                    </select>
                </template:formTr>
                  <template:formTr name="材料类型" >
                    <select name="type" style="width:180px" class="inputtext"  >
                        <option  value="">选择所有类型</option>
                          <logic:present name="parttype">
                            <logic:iterate id="parttypeId" name="parttype">
                                <option value="<bean:write name="parttypeId"  property="type"/>"><bean:write name="parttypeId"  property="type"/></option>
                            </logic:iterate>
                        </logic:present>
                    </select>
                </template:formTr >
                <template:formTr name="新材料库存" >
                    <b>大于&nbsp;&nbsp;</b><input   type="text" name="newlownumber" id="newlow" class="inputtext" size="10" maxlength="6"   onkeyup="valiDigit(id)"/>
                    <b>小于&nbsp;&nbsp;</b><input   type="text" name="newhignumber" id="newhig" class="inputtext" size="10" maxlength="6"   onkeyup="valiDigit(id)"/>
                </template:formTr >
                 <template:formTr name="旧材料库存" >
                    <b>大于&nbsp;&nbsp;</b><input   type="text" name="oldlownumber" id="oldlow" class="inputtext" size="10" maxlength="6"  onkeyup="valiDigit(id)"/>
                    <b>小于&nbsp;&nbsp;</b><input   type="text" name="oldhignumber" id="oldhig" class="inputtext" size="10" maxlength="6"   onkeyup="valiDigit(id)"/>
                </template:formTr >
                <template:formSubmit>
                              <td>
                              <html:submit property="action" styleClass="button" >查找</html:submit>
                              </td>
                              <td>
                                    <html:reset property="action" styleClass="button" >重置</html:reset>
                              </td>
                        </template:formSubmit>
            </template:formTable>

        </html:form>
    </logic:equal>


    <!--初始化库存-->
    <logic:equal value="st2" scope="session" name="type">
        <link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
          <apptag:checkpower thirdmould="80601" ishead="1">
                <jsp:forward page="/globinfo/powererror.jsp"/>
        </apptag:checkpower>
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
        <template:titile value="初始化材料库存"/>
        <html:form action="/PartStorageAction?method=initStorage" styleId="addForm">
            <input  type="hidden" name="contractorid" value="<bean:write name="deptid"/>"/>
              <input type="hidden" name="useuserid" value="<bean:write name="userid" />"/>
            <table align="center" width="90%" border="0">
                <tr >
                    <td width="10%" height="25"><b>单位名称:</b></td>
                    <td align="left" width="15%"><bean:write name="deptname"/></td>
                    <td  align="right" width="10%"><b>操作人:</b></td>
                    <td align="left" width="15%"><bean:write name="username"/></td>
                    <td width="10%" align="right"><b>操作时间:</b></td>
                    <td align="left" ><bean:write name="date"/></td>
                </tr>
                <tr >
                    <td width="10%" height="25" valign="top"><b>备注信息:</b></td>
                    <td align="left" colspan="5">
                        初始化材料库存是指将库存材料数量设定为指定的值,该功能可用于两个方面:<br />
                        1、系统刚开始运行的时候输入现有的库存量.<br />
                        2、在库存盘点中根据库存实际值设定库存量.
                    </td>

                </tr>
                <tr>
                    <td colspan="6">
                        <hr /><br />请选择材料:
                    </td>
                </tr>
            </table>
            <table    id="queryID"  border="1" align="center" cellpadding="3" cellspacing="0" width="90%">
                <tr>
                    <th  width="35%" class="thlist" align="center">材料名称</th>
                    <th  width="10%" class="thlist" align="center">计量单位</th>
                    <th  width="25%" class="thlist" align="center">规格型号</th>
                    <th  width="10%" class="thlist" align="center">新材料初值</th>
                    <th  width="10%" class="thlist" align="center">旧材料初值</th>
                    <th  width="6%" class="thlist" align="center">操作</th>
                </tr>
            </table>
            <p align="center">
                              <html:button property="action" styleClass="button" onclick="addRow()">添加材料</html:button>
                                    <html:button property="action" styleClass="button"  onclick="toAddSub()">提交初始化</html:button>
                                    <html:button property="action" styleClass="button" onclick="addGoBack()" >返回	</html:button>
            </p>

        </html:form>
    </logic:equal>

    <logic:equal value="showst1" scope="session"name="type">
        <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
               <apptag:checkpower thirdmould="80609" ishead="1">
                <jsp:forward page="/globinfo/powererror.jsp"/>
            </apptag:checkpower>
            <br />
            <template:titile value="材料库存一览表" />
            <display:table name="sessionScope.storageinfo" id="currentRowObject"  pagesize="18" style="align:center">
                <display:column property="contractorname" title="单位名称" maxLength="10" style="align:center" />
                  <display:column property="name" title="材料名称" maxLength="10" style="align:center" />
                <display:column property="unit" title="计量单位" maxLength="20" style="align:center"/>
                <display:column property="type" title="规格型号" maxLength="10" />
                <display:column property="newshould" title="新材料应有库存" maxLength="20" style="align:center"/>
                <display:column property="newesse" title="新材料库存" maxLength="20" style="align:center"/>
                <display:column property="oldnumber" title="旧材料库存"maxLength="20" style="align:center" />


             </display:table>
             <logic:notEmpty name="storageinfo">
               <html:link action="/PartStorageAction.do?method=exportStorageResult">导出为Excel文件</html:link><br>
             </logic:notEmpty>
             <div align="center"><html:button property="action" styleClass="button" onclick="goquery()" >返回</html:button></div>
    </logic:equal>
   
    <iframe id="hiddenIframe" style="display:none"></iframe>
</body>
</html>

