<%@include file="/common/header.jsp"%>
<html>
<head>
<script type="text/javascript" src="${ctx}/js/prototype.js"></script>
<script type="text/javascript" src="${ctx}/js/dojo.js"></script>
	<script type="text/javascript">
		dojo.require("dojo.widget.ComboBox");
	</script>
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
		var selectValue = this.value;
        var tmpValue;
        var tr;//行对象
        for(i =0; i<queryID.rows.length;i++){
            if(queryID.rows[i].id == rowid){
                tr = queryID.rows[i];
            } else {
				if(document.getElementById("select"+queryID.rows[i].id) != null ){
            		tmpValue = document.getElementById("select"+queryID.rows[i].id).value;
					if(tmpValue == selectValue) {
						alert("该材料已经选择过了，请重新选择出库的材料！");
						document.getElementById("select"+rowid).value = "";
						return;
					}
				}
			}
        }
        //找出选择材料所对应的所有信息
        var unit = "";
        var type = "";
        for(i = 0; i < infoArr.length; i++){
            if(this.value == infoArr[i][0]){
                unit = infoArr[i][2];
                type = infoArr[i][3]
            }
        }
		// 判断选择了材料是否
		if(document.getElementById("select"+rowid).value != "") {
			// 将用途选择按钮设为可用
			document.getElementById("useb1"+rowid).disabled  = false;
			document.getElementById("useb2"+rowid).disabled  = false;

		} else {
			document.getElementById("useb1"+rowid).disabled  = true;
			document.getElementById("useb2"+rowid).disabled  = true;
			tr.cells[1].innerText = "没选材料";
			tr.cells[2].innerText = "没选材料";
			return;
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
        tt.size = "16";
        tt.style.background="#C6D6E2"
        tt.style.font.size="12px";
        tt.readonly="readonly";
        tr.cells[2].innerText ="";
        tr.cells[2].appendChild(tt);

		

       // tr.cells[3].innerText =unit;
       // tr.cells[4].innerText =type;
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

        //创建一个输入框(新材料)
        var t1 = document.createElement("input");
        t1.name = "usenewnumber"
        t1.id = "text" + onerow.id;
        t1.value= "0";
        t1.maxLength = "6";
        t1.size = "6";
        t1.onblur = checknew;
         t1.style.background="#C6D6E2"
        t1.style.font.size="12px";
        //创建一个输入框(旧材料)
         var t2 = document.createElement("input");
        t2.name = "useoldnumber"
        t2.id = "tex2" + onerow.id;
        t2.value= "0";
        t2.maxLength = "6";
        t2.size = "6";
        t2.onblur = checkold;
         t2.style.background="#C6D6E2"
        t2.style.font.size="12px";


        //创建一个select
        var s1 = document.createElement("select");
        s1.options.length = infoArr.length + 1;
        s1.options[0].value = "";
        s1.options[0].text = "选择待出库材料";
        for(i = 1; i<s1.options.length ;i++){
            s1.options[i].value = infoArr[i-1][0];
            s1.options[i].text = infoArr[i-1][1]+ " -- " +  infoArr[i-1][3];
        }
        s1.name = "id";
        s1.id = "select" + onerow.id;
        s1.onchange = onselectchange;
        //s1.Class="inputtext"
        s1.style.background="#C6D6E2"
        s1.style.font.size="12px";
        s1.style.width="230";

        //创建删除按钮
        var b1 =document.createElement("button");
        b1.value = "删除";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
        //alert(b1.id + "rrr");

		//创建用途按钮
        var linecut =document.createElement("button");
        linecut.value = "割接";
        linecut.id = "useb1" + onerow.id;
       	linecut.onclick=openUsePage;
		linecut.disabled = true;

		var change =document.createElement("button");
        change.value = "修缮";
        change.id = "useb2" + onerow.id;
       	change.onclick=openUsePage;
		change.disabled = true;
		// 创建用途对应的隐藏域
        var h1 =document.createElement("<input type='hidden' >");
        h1.id = "h1" + onerow.id;
		h1.name = "useobjectstr1" // 割接
        
		var h2 =document.createElement("<input type='hidden' >");
        h2.id = "h2" + onerow.id;
		h2.name = "useobjectstr2" // 修缮

        cell1.appendChild(s1);//文字
        cell2.innerText="没选材料";//文字
        cell3.innerText="没选材料";//文字
        cell4.appendChild(t1);//文字
        cell5.appendChild(t2);//文字
        cell6.appendChild(linecut);// 用途
		cell6.appendChild(h1); // 隐藏域
		cell6.appendChild(change);// 用途
		cell6.appendChild(h2); // 隐藏域
		cell7.appendChild(b1);
    }

	// 打开选择用途的页面
	function openUsePage(flg) {
		var rowid = this.id;        
		var flg = rowid.substring(4,5);
		rowid = rowid.substring(5,rowid.length);
		// 判断是否有输入了材料的数量
		var newNum = document.getElementById("text"+rowid).value;
		var oldNum = document.getElementById("tex2"+rowid).value;		
		if((newNum == "0" || newNum == "") && (oldNum == "" || oldNum == "0")) {
			alert("请输入材料出库的数量！");
     		return;
		}
		var sum = parseInt(newNum) + parseInt(oldNum);
        var hobj = "h"+flg+rowid;
        var url = "${ctx}/PartUseAction.do?method=showUseChange&obj="+hobj+"&sumnum="+sum+"&flg="+flg;
		showModalDialog(url,window,"scroll=yes;dialogHeight=350px;dialogWidth=700px;Status:YES;help:no;"); 
		//window.open(url);
	}

    function checkold(){
        var mysplit = /^\d{1,6}$/;
        if(!mysplit.test(this.value)){
            alert("你填写的不是数字,请重新输入");
            this.value="0";
            this.focus();
            return false;
        }

        var seId = "select" +(this.id).substring(4,this.id.length);
        var selObj = document.getElementById(seId);  //得到select所选择材料的对象
        var oldnumber =0;
        for(i=0;i<infoArr.length;i++){
            if(selObj.value == infoArr[i][0]){
                oldnumber = infoArr[i][5];
                break;
            }
        }
        if(parseInt(this.value) > parseInt(oldnumber)){
            alert("目前库存是" + oldnumber + ",不能出库这么多!!");
            this.value="0";
            this.focus();
            return false;
        }
        return true;
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
            alert("目前库存是" + oldnumber + ",不能出库这么多!!");
            this.value="0";
            this.focus();
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
            obj.value="0";
            obj.focus();
            return false;
        }
    }

        //添加提交
    function toAddSub(){
      if(queryID.rows.length<2){
          alert("你还没有选择材料,不能提交!")
        return false;
      }
	  var rowid;
	  var noSum;
	  var objSum;
	  for(i =1; i<queryID.rows.length;i++){
		 rowid = queryID.rows[i].id;
		 if((document.getElementById("select"+rowid).value == "") 
			|| (document.getElementById("text"+rowid).value == "0" 
				&& document.getElementById("tex2"+rowid).value == "0")
				){
			alert("第"+i+"行没有选择材料或输入出库数量，请检查！");
			return;
		 }
         noSum = newoldSum(rowid);
		
		 objSum = objNum(rowid);
		
         if(noSum != objSum) {
			alert("第"+i+"行选择的材料的用途数量与出库数量不相符，请检查！");
			return;
		 }
      }
      addForm.submit();
    }

	// 一行材料的出库数
    function newoldSum(rowid) {
		var newNum = document.getElementById("text"+rowid).value
		var oldNum = document.getElementById("tex2"+rowid).value
        var sumNum = parseInt(newNum) + parseInt(oldNum);
		return sumNum;
	}

	// 指定行材料用途数
	function objNum(rowid) {
		var linecut = document.getElementById("h1"+rowid).value
		var change = document.getElementById("h2"+rowid).value
		var objNum = 0;
		if(linecut  != "" && linecut != ";") {
			var parArray = linecut.split(';');
			for(var i=0;i<parArray.length;i++) {
				if(parArray[i] != "") {
					var par = parArray[i].split(":");
					objNum = objNum + parseInt(par[1]);	
				}
			}	
		}
		if(change  != "" && change != ";") {
			var parArray2 = change.split(';');
			for(var j=0;j<parArray2.length;j++) {
				if(parArray2[i] != "") {
					var par2 = parArray2[j].split(":");
					objNum = objNum + parseInt(par2[1]);	
				}
			}	
		}

		return objNum;
	}

    //显示页面详细信息按钮动作
   function toGetForm(idValue){
         var url = "${ctx}/PartUseAction.do?method=showOneUse&useid=" + idValue;
        self.location.replace(url);
    }

   function  addGoBack(){
         var url = "${ctx}/PartUseAction.do?method=showAllUse";
        self.location.replace(url);
    }

    function goBack(){
        var url = "${ctx}/PartUseAction.do?method=dostat";
        self.location.replace(url);
    }

  //返回到上级查询页面
	function goquery(){
		window.location.href = "${ctx}/PartUseAction.do?method=queryShow";
	}

   //返回到使用统计查询页面
	function gostatquery(){
		window.location.href = "${ctx}/PartUseAction.do?method=queryShowForStat";
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
    function OnbackStockSelect(){
        var obj = document.getElementById("useid");
        if(obj.value==""){
            alert("你没有选择,不能退库!!");
            return false;
        }
        backForm.submit();

    }
    //退库时检查是否超过出库数
    function checkback(id){
        var obj = document.getElementById(id);
          var mysplit = /^\d{1,6}$/;
        if(!mysplit.test(obj.value)){
            alert("你填写的不是数字,请重新输入");
            obj.focus();
            obj.value=0;
            return false;
        }

        var partid = obj.id.substring(2,obj.id.length);
        var newstocknumber=0;
        var oldstocknumber = 0;
        for(i=0;i<infoArr.length;i++){
            if(partid == infoArr[i][0]){
                newstocknumber = infoArr[i][4];
                oldstocknumber = infoArr[i][5];
                break;
            }
        }

        if(obj.id.substring(0,2) == "t1"){//是新品退库检查

            if(parseInt(obj.value) > newstocknumber){
                alert("出库是" + newstocknumber + ",不能退库这么多!!");
                obj.focus();
                obj.value=0;
                return false;
            }
        }
         if(obj.id.substring(0,2) == "t2"){//是新品退库检查

            if(parseInt(obj.value) > oldstocknumber){
                alert("出库是" + oldstocknumber + ",不能退库这么多!!");
                obj.focus();
                obj.value=0;
                return false;
            }
        }

        return true;
    }

   function toExpotr(){

     var useuserid = queryForm2.useuserid.value;
     var usereason = queryForm2.usereason.value;
     var begintime = queryForm2.begintime.value;
     var endtime = queryForm2.endtime.value;
     var url = "${ctx}/PartUseAction.do?method=exportUseList&useuserid="+useuserid+"&usereason="+usereason+"&begintime="+begintime+"&endtime="+endtime;
     self.location.replace(url);

   }

function textCounter(field,maxlimit) {   
  if(field.value.length > maxlimit)     
  	field.value = field.value.substring(0,maxlimit);   
   
}

function displaypage(baseid) {
	//alert(baseid);
	var useidstr = document.getElementById("useid").value;
	var url = "${ctx}/PartUseAction.do?method=displayUse&useid="+useidstr+"&baseid="+baseid;
	showModalDialog(url,window,"scroll=yes;dialogHeight=350px;dialogWidth=600px;Status:YES;help:no;"); 

}

var flag = "0";
function settype(v){
  if(v == 1){
	flag = 1;
//    deptTr.style.display = "";
	nameTr.style.display = "";
	typeTr.style.display = "";
	factoryTr.style.display = "";
	usereasonTr.style.display = "";
	levelTr.style.display = "none";
	sublineTr.style.display = "none";
    sublineIdTr.style.display = "none";
	linecutTr.style.display = "none";
	linechangeTr.style.display = "none";

  }

  if(v == 2){
	flag = 2;
//	deptTr.style.display = "";
	nameTr.style.display = "none";
	typeTr.style.display = "none";
	factoryTr.style.display = "none";
	usereasonTr.style.display = "none";
	levelTr.style.display = "";
	sublineTr.style.display = "";
    sublineIdTr.style.display = "";
	linecutTr.style.display = "";
	linechangeTr.style.display = "none";
  }

  if(v == 3){
	flag = 3;
//	deptTr.style.display = "";
	nameTr.style.display = "none";
	typeTr.style.display = "none";
	factoryTr.style.display = "none";
	usereasonTr.style.display = "none";
	levelTr.style.display = "";
	sublineTr.style.display = "none";
    sublineIdTr.style.display = "none";
	linecutTr.style.display = "none";
	linechangeTr.style.display = "";
  }

}

var rowid = '0';
				function onSelChangeLevel() {
					var ops = document.getElementById('sublinename');
					ops.options.length = 0;
					var newClewOp = document.createElement("option");
					newClewOp.value = "";
					newClewOp.text = "选择光缆线段";
					ops.add(newClewOp);
					var params = document.getElementById('levelObj').value;
                    var kkt =params.split(',');
                    params=kkt[0];
					if(params == "") {
						//ops.options.add(new Option("",""));
					}
					var url = 'LineCutReAction.do?method=getLineByLevelId&levelId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callback, asynchronous:true});
				}
				
				function callback(originalRequest) {
					var ops = document.getElementById('sublinename');
					var rst = originalRequest.responseText;
					var linearr = eval('('+rst+')');
					if(linearr.length == 0) {
						alert("该级别尚未制定线路!");
						return;
					}

					//ops.options.add(new Option('选择光缆线段',''));
					for(var i = 0; i < linearr.length; i++) {
						ops.options.add(new Option(linearr[i].linename,linearr[i].lineid+','+linearr[i].linename));
					}
					var myGlobalHandlers = {onCreate:function () {
					//Element.show("Loadingimg"+rowid);
					}, onFailure:function () {
						alert("网络连接出现问题，请关闭后重试!");
					}, onComplete:function () {
						if (Ajax.activeRequestCount == 0) {
							//Element.hide("Loadingimg");
						}
					}};
					Ajax.Responders.register(myGlobalHandlers);
				}
              
                 function onSelChangeLine(){
                     var ops = document.getElementById('sublineId');
					 ops.options.length = 0;
					 var newClewOp = document.createElement("option");
					 newClewOp.value = "";
					 newClewOp.text = "选择中继站";
					 ops.add(newClewOp);
                     var params = document.getElementById('sublinename').value;
                     var kk =params.split(',');
                    params=kk[0];
                     if(params == "mention") {
						//ops.options.add(new Option("",""));
					 }
                     var url = 'LineCutReAction.do?method=getClewByLineId&lineId=' + params;
					 var myAjax = new Ajax.Request(url, {method:"post", onComplete:callbackToServer, asynchronous:true});
                 }

                 function callbackToServer(originalRequest) {
					var ops = document.getElementById('sublineId');
					var rst = originalRequest.responseText;
					var clewarr = eval('('+rst+')');
					if(clewarr.length == 0) {
						alert("该路线尚未制定线段!");
						return;
					}
					for(var i = 0; i < clewarr.length; i++) {
						ops.options.add(new Option(clewarr[i].sublinename,clewarr[i].sublineid+','+clewarr[i].sublinename));
					}
					var myGlobalHandlers = {onCreate:function () {
					//Element.show("Loadingimg"+rowid);
					}, onFailure:function () {
						alert("网络连接出现问题，请关闭后重试!");
					}, onComplete:function () {
						if (Ajax.activeRequestCount == 0) {
							//Element.hide("Loadingimg");
						}
					}};
					Ajax.Responders.register(myGlobalHandlers);
				}

                function onSelectChangeClew() {
					var ops = document.getElementById('linechangename');
					var length = ops.length;
					ops.options.length = 0;//清空下拉列表
					ops.options.add(new Option("---请选择割接名称---",""));
					var params = document.getElementById('sublineId').value;
                    var kk =params.split(',');
                    params=kk[0];
					if(params == "mention") {
						//ops.options.add(new Option("",""));
					}
					var url = 'PartUseAction.do?method=getCutNameBySublineid&sublineId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callbackToServerByName, asynchronous:true});
				}
				
				function callbackToServerByName(originalRequest) {
					var ops = document.getElementById('linechangename');
					var rst = originalRequest.responseText;
					var nameArr = eval('('+rst+')');
					if(nameArr.length == 0) {
						alert("该路线下没有割接申请信息!");
						return;
					}
					for(var i = 0; i < nameArr.length; i++) {
						ops.options.add(new Option(nameArr[i].name,nameArr[i].id+','+nameArr[i].name));
					}
				}

                function toAuditForm(partId){
                     //alert('ttt ' +useId);
                     var url = "${ctx}/PartUseAction.do?method=doShowOneForPart&partId=" + partId;
                     self.location.replace(url);
                }

function deptChange(obj) {
	var tmpName = obj.options[obj.selectedIndex].text;
	document.getElementById("deptNameStr").value = tmpName;
}


</script>


<title>
partRequisition
</title>
</head>
<body>

    <!--填写出库单-->
    <logic:equal value="use2" scope="session" name="type">
        <link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
          <apptag:checkpower thirdmould="80301" ishead="1">
                <jsp:forward page="/globinfo/powererror.jsp"/>
        </apptag:checkpower>
        <logic:present name="baseinfo">
            <logic:iterate id="baseinfoId" name="baseinfo">
                <script type="" language="javascript">
                    initArray("<bean:write name="baseinfoId" property="id"/>","<bean:write name="baseinfoId" property="name"/>",
                              "<bean:write name="baseinfoId" property="unit"/>","<bean:write name="baseinfoId" property="type"/>",
                              "<bean:write name="baseinfoId" property="newesse"/>","<bean:write name="baseinfoId" property="oldnumber"/>");
                </script>
            </logic:iterate>
        </logic:present>

        <br/>
        <template:titile value="填写材料出库单"/>
        <html:form action="/PartUseAction?method=addUse" styleId="addForm">
            <input  type="hidden" name="contractorid" value="<bean:write name="deptid"/>"/>
              <input type="hidden" name="useuserid" value="<bean:write name="userid" />"/>
            <table align="center" width="95%" border="0" class="tabout">
                <tr class=trcolor >
                    <td width="60" height="25"><b>单位名称:</b></td>
                    <td align="left" width="100"><bean:write name="deptname"/></td>
                    <td  align="right" width="50"><b>出库人:</b></td>
                    <td align="left" width="100"><bean:write name="username"/></td>
                    <td width="100" align="right"><b>出库时间:</b></td>
                    <td align="left" width="100"><bean:write name="date"/></td>
                </tr>
                <tr class=trcolor>
                    <td height="25"><b>材料用途:</b></td>
                    <td colspan="5"><html:text property="usereason" styleClass="inputtext" style="width:88%;" maxlength="30" value="请填写材料的用途."></html:text> </td>
                </tr>
                <tr class=trcolor>
                    <td height="25"><b>备注信息:</b></td>
                    <td colspan="5"><html:textarea property="useremark" styleClass="inputtextarea" style="width:88%;" onkeydown="textCounter(this,254)" onkeyup="textCounter(this,254)"value="请填写其他说明信息."></html:textarea></td>
                </tr>
                <tr class=trcolor>
                    <td colspan="6">
                        <hr /><br /> 请选择材料:
                    </td>
                </tr>
                <tr class=trcolor>
                    <td colspan="6">
                        <table   width="100%" id="queryID"  border="1" align="center" cellpadding="3" cellspacing="0" >
			                <tr>
			                    <th  width="30%" class="thlist" align="center">材料名称</th>
			                    <th  width="10%" class="thlist" align="center">计量单位</th>
			                    <th  width="19%" class="thlist" align="center">规格型号</th>
			                    <th  width="11%" class="thlist" align="center">新材料出库</th>
			                    <th  width="11%" class="thlist" align="center">旧材料出库</th>
			                    <th  width="12%" class="thlist" align="center">用途</th>
			                    <th  width="7%"class="thlist" align="center">操作</th>
			                </tr>
			            </table>
                    </td>
                </tr>
            </table>
            
            <p align="center">
                              <html:button property="action" styleClass="button" onclick="addRow()">出库新材料</html:button>
                                    <html:button property="action" styleClass="button"  onclick="toAddSub()">提交出库单</html:button>
                               <!-- <html:button property="action" styleClass="button" onclick="addGoBack()" >返回	</html:button> -->     
            </p>

        </html:form>
    </logic:equal>

      <!--显示出库单-->
    <logic:equal value="use1" name="type"scope="session">
        <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
               <apptag:checkpower thirdmould="80303" ishead="1">
                <jsp:forward page="/globinfo/powererror.jsp"/>
            </apptag:checkpower>
            <br />
            <template:titile value="材料出库单一览表" />
            <display:table name="sessionScope.useinfo" requestURI="${ctx}/PartUseAction.do?method=showAllUse" id="currentRowObject"  pagesize="18">
            	<%
                   BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
                   String id = "";
                   String usereason = "";
                   String reasonTitle = "";
                   if(object != null) {
                   	 id = (String) object.get("useid");
                   	 reasonTitle = (String)object.get("usereason");
			    	 if(reasonTitle != null && reasonTitle.length() > 15) {
			    		usereason = reasonTitle.substring(0,15) + "...";
			    	 } else {
			    		usereason = reasonTitle;
			    	 }
                   }
                %>
                <display:column media="html" title="出库单流水号" style="width:100px" > 
                	<%if(id != null) {%>                    
                     <a href="javascript:toGetForm('<%=id%>')"><%=id %></a>
                  	<%} %>
                </display:column>
                <display:column media="html" title="材料用途" > 
                	<%if(id != null) {%>                    
                     <a href="javascript:toGetForm('<%=id%>')" title="<%=reasonTitle %>"><%=usereason %></a>
                  	<%} %>
                </display:column>            
                <display:column property="contractorname" title="出库单位" maxLength="20" style="align:center"/>
                <display:column property="username" title="出库人"maxLength="20" style="align:center"/>
                <display:column property="usetime" title="出库时间" maxLength="20" style="align:center;width:120px"/>                
                
            </display:table>
            <logic:notEmpty name="useinfo">
                  <html:link action="/PartUseAction.do?method=exportUseResult">导出为Excel文件</html:link><br>
             </logic:notEmpty>
             <div align="center"><html:button property="action" styleClass="button" onclick="goquery()" >返回</html:button></div>
    </logic:equal>

    <!--显示出库单详细信息-->
    <logic:equal value="use10"   name="type"scope="session">
        <apptag:checkpower thirdmould="80303" ishead="1">
                <jsp:forward page="/globinfo/powererror.jsp"/>
        </apptag:checkpower>
            <br />
          <template:titile value="材料出库单详细信息" />
             <table class="tabout" align="center" width="95%" border="0">
                  <tr class=trcolor >
                   <input type="hidden" id="useid" value='<bean:write name="useinfo" property="useid"/>'/>
                     <td width="8%" height="25"><b>单位名称:</b></td>
                     <td align="left" width="25%"><bean:write name="useinfo" property="contractorname"/></td>
                     <td  align="right" width="8%"><b>出库人:</b></td>
                     <td align="left" width="15%"><bean:write name="useinfo" property="username"/></td>
                     <td width="10%" align="right"><b>出库时间:</b></td>
                     <td align="left" ><bean:write name="useinfo" property="usetime"/></td>
                 </tr>
                 <tr class=trcolor>
                     <td height="25"><b>材料用途:</b></td>
                     <td colspan="5"><bean:write name="useinfo" property="usereason"/></td>
                 </tr>
                 <tr class=trcolor>
                     <td height="25"><b>备注信息:</b></td>
                     <td colspan="5"><bean:write name="useinfo" property="useremark"/></td>
                 </tr>
                 <tr class=trcolor>
                     <td colspan="6" height="30" valign="top">
                         <hr />
                     </td>
                 </tr>
                 <tr class=trcolor> 
                     <td colspan="6"   valign="bottom">
                         <b>该出库单所出库的材料:</b>
                     </td>
                 </tr>
                 <tr class=trcolor>
                     <td colspan="6"   valign="bottom">
                        <table   width="100%" id="queryID"  border="1" align="center" cellpadding="3" cellspacing="0" >
		                    <tr>
		                        <th  width="35%" class="thlist" align="center">材料名称</th>
		                        <th  width="10%" class="thlist" align="center">计量单位</th>
		                        <th  width="15%" class="thlist" align="center">规格型号</th>
		                        <th  width="11%" class="thlist" align="center">新材料出库</th>
		                        <th  width="11%" class="thlist" align="center">旧材料出库 </th>
		                        <th  width="8%" class="thlist" align="center">用途 </th>
		                    </tr>
		                    <logic:present name="usepartinfo">
		                        <logic:iterate id="useid" name="usepartinfo">
		                            <tr  >
		                                <td>
		                                    <bean:write name="useid" property="name"/> -- <bean:write name="useid" property="type"/>
		                                </td>
		                                <td>
		                                   <bean:write name="useid" property="unit"/>
		                                </td>
		                                <td>
		                                    <bean:write name="useid" property="type"/>
		                                </td>
		                                <td>
		                                    <bean:write name="useid" property="usenewnumber"/>
		                                </td>
		                                <td>
		                                    <bean:write name="useid" property="useoldnumber"/>
		                                </td>
										<td align="center">                                   
											<a href='javascript:displaypage("<bean:write name='useid' property='id' />")'>详细</a>                                    
		                                </td>
		                           </tr>
		                        </logic:iterate>
		                    </logic:present>
		                </table>
                     </td>
                 </tr>
             </table>
                
                <p align="center">
                	<input type="button" class="button" value="返回" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"/>
                </p>

    </logic:equal>

    <!--查询页面-->
    <logic:equal value="use3" name="type" scope="session">
        <apptag:checkpower thirdmould="80303" ishead="1">
            <jsp:forward page="/globinfo/powererror.jsp"/>
        </apptag:checkpower>
          <br />
        <template:titile value="条件查找维护材料出库单"/>
        <html:form action="/PartUseAction?method=queryExec"   styleId="queryForm2" >
            <template:formTable namewidth="200"  contentwidth="200">
            	<logic:equal value="1" name="LOGIN_USER" property="deptype">
            		<template:formTr name="代维单位"  >
            		<select name="contractorid" class="inputtext" style="width:180px">
            			<option value="">全部</option>
            			<logic:notEmpty name="condept">
            			<logic:present name="condept">
            				<logic:iterate id="element" name="condept">
            					<option value="<bean:write name='element' property='deptid'/>"><bean:write name='element' property='deptname'/></option>
            				</logic:iterate>
            			</logic:present>
            			</logic:notEmpty>
            		</select>
                	</template:formTr>
            	</logic:equal>
            	<logic:notEqual value="1" name="LOGIN_USER" property="deptype">
            		<template:formTr name="出库人姓名"  >
                    	<input type="text" name="useuserid"   class="inputtext" style="width:180px" />
                	</template:formTr>
            	</logic:notEqual>
                
                  <template:formTr name="材料用途" >
                    <input type="text" name="usereason" style="width:180px" class="inputtext"  />
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
                                    <html:reset property="action" styleClass="button" >重置</html:reset>
                              </td>
                        </template:formSubmit>
            </template:formTable>

        </html:form>
    </logic:equal>


    <!--///////////材料使用查询////////-->
       <!--材料使用查询页面-->
    <logic:equal value="use7" name="type" scope="session">
        <apptag:checkpower thirdmould="80307" ishead="1">
            <jsp:forward page="/globinfo/powererror.jsp"/>
        </apptag:checkpower>
          <br />
        <template:titile value="条件查找维护材料使用情况"/>
        <html:form action="/PartUseAction?method=dostat"   styleId="queryForm2">
            <template:formTable namewidth="200"  contentwidth="200">
	         <template:formTr name="统计类型" >
	          	<input type="radio" name="totaltype" checked="true" value="1" onclick="settype(1)"　/> 全部      
	        	<input type="radio" name="totaltype" value="2" onclick="settype(2)"/> 割接
	        	<input type="radio" name="totaltype" value="3" onclick="settype(3)"/> 修缮
      		</template:formTr>
            <logic:notEqual value="22" name="usetype">
               <template:formTr name="使用单位"  tagID="deptTr">
                    <select name="contractorid"   class="inputtext" style="width:180px" onchange="deptChange(this)">
                        <option  value="">选择所有单位</option>
                          <logic:present name="deptinfo">
                            <logic:iterate id="deptinfoId" name="deptinfo">
                                <option value="<bean:write name="deptinfoId" property="contractorid"/>"><bean:write name="deptinfoId" property="contractorname"/></option>
                            </logic:iterate>
                        </logic:present>
                    </select>
                    <input type="hidden" id="deptNameStr" name="contractorname"/>
                </template:formTr>
              </logic:notEqual>

                 <template:formTr name="材料名称" tagID="nameTr" >
                    <select name="name" style="width:180px" class="inputtext"  >
                        <option  value="">选择所有名称</option>
                          <logic:present name="nameinfo">
                            <logic:iterate id="nameinfoId" name="nameinfo">
                                <option value="<bean:write name="nameinfoId"  property="name"/>"><bean:write name="nameinfoId"  property="name"/></option>
                            </logic:iterate>
                        </logic:present>
                    </select>
                </template:formTr >

                 <template:formTr name="材料型号" tagID="typeTr" >
                    <select name="type" style="width:180px" class="inputtext"  >
                        <option  value="">选择所有型号</option>
                          <logic:present name="typeinfo">
                            <logic:iterate id="typeinfoId" name="typeinfo">
                                <option value="<bean:write name="typeinfoId"  property="type"/>"><bean:write name="typeinfoId"  property="type"/></option>
                            </logic:iterate>
                        </logic:present>
                    </select>
                </template:formTr >

                <template:formTr name="生产厂家" tagID="factoryTr" >
                    <select name="factory" style="width:180px" class="inputtext"  >
                        <option  value="">选择所有厂家</option>
                          <logic:present name="factoryinfo">
                            <logic:iterate id="factoryinfoId" name="factoryinfo">
                                <option value="<bean:write name="factoryinfoId"  property="factory"/>"><bean:write name="factoryinfoId"  property="factory"/></option>
                            </logic:iterate>
                        </logic:present>
                    </select>
                </template:formTr >

                <template:formTr name="材料用途" tagID="usereasonTr">
                    <select name="usereason" style="width:180px" class="inputtext"  >
                        <option  value="">选择所有用途</option>
                          <logic:present name="usereason">
                            <logic:iterate id="usereasonId" name="usereason">
                                <option value="<bean:write name="usereasonId"  property="usereason"/>"><bean:write name="usereasonId"  property="usereason"/></option>
                            </logic:iterate>
                        </logic:present>
                    </select>
                </template:formTr >
                
                <template:formTr name="线路级别" tagID="levelTr" style="display:none">
                    <select id="levelObj" name="level" style="width:180px" class="inputtext" onchange = "onSelChangeLevel()" >
                        <option  value="">选择线路级别</option>
                          <logic:present name="levelInfo">
                            <logic:iterate id="element" name="levelInfo">
                                <option value="<bean:write name="element"  property="code"/>,<bean:write name="element"  property="name"/>"><bean:write name="element"  property="name"/></option>
                            </logic:iterate>
                        </logic:present>
                    </select>
                </template:formTr >
                <template:formTr name="光缆线段" tagID="sublineTr" style="display:none">
                    <select id="sublinename" name="subline" style="width:180px" class="inputtext" onchange = "onSelChangeLine()" >
                        <option  value="">选择光缆线段</option>
                    </select>
                </template:formTr >
                <template:formTr name="中继段名" tagID="sublineIdTr" style="display:none">
                    <select id="sublineId" name="sublineId" style="width:180px" class="inputtext" onchange = "onSelectChangeClew()" >
                        <option  value="">选择中继段</option>
                    </select>
                </template:formTr >
                <template:formTr name="割接名称" tagID="linecutTr" style="display:none">
                    <select id="linechangename" name="linechangename" style="width:180px" class="inputtext"  >
                        <option  value="">选择割接名称</option>
                    </select>
                </template:formTr >
                <template:formTr name="修缮名称" tagID="linechangeTr" style="display:none">
                    <input name="cutchangename" type="text" class="inputtext" style="width:180" />
                </template:formTr >
                <template:formTr name="开始时间" >
                    <input   id="begin" type="text"  readonly="readonly"  name="begintime" class="inputtext" style="width:150" />
                    <INPUT TYPE='BUTTON' VALUE='日期' ID='btn'  onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';" >
                </template:formTr>
                <template:formTr name="结束时间">
                    <input  id="end" type="text"  readonly="readonly" name="endtime" class="inputtext" style="width:150" />
                    <INPUT TYPE='BUTTON' VALUE='日期' ID='btn'  onclick="GetSelectDateTHIS('end')" STYLE="font:'normal small-caps 6pt serif';" >
                </template:formTr>
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

      <!--显示材料使用查询结果-->
    <logic:equal value="use70" name="type" scope="session">
        <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
               <apptag:checkpower thirdmould="80303" ishead="1">
                <jsp:forward page="/globinfo/powererror.jsp"/>
            </apptag:checkpower>
            <br />
            <template:titile value="材料使用一览表" />
            
            <font face="宋体" size="2" color="red">
            	<bean:write name="bean" property="contractorname"/>
               <logic:empty name="bean" property="begintime">
                   开始
               </logic:empty>
               <logic:notEmpty name="bean" property="begintime">
                   <bean:write name="bean" property="begintime"/>
               </logic:notEmpty>
               <logic:empty name="bean" property="endtime">
                  -- 至今
               </logic:empty>
               <logic:notEmpty name="bean" property="endtime">
                  -- <bean:write name="bean" property="endtime"/>
               </logic:notEmpty>段时间内
            <logic:notEmpty name="bean" property="level">
               <bean:write name="bean" property="level"/>级别下 
            </logic:notEmpty>
            <logic:notEmpty name="bean" property="subline">
              <bean:write name="bean" property="subline"/>段线路
            </logic:notEmpty> 
            <logic:notEmpty name="bean" property="sublineId">
               <bean:write name="bean" property="sublineId"/>中继段下的
            </logic:notEmpty>  
            <logic:notEmpty name="bean" property="linechangename">
               <bean:write name="bean" property="linechangename"/>的
            </logic:notEmpty>
            <logic:notEmpty name="bean" property="cutchangename">
               <bean:write name="bean" property="cutchangename"/>的
            </logic:notEmpty>
               <logic:equal value="1" name="bean" property="totaltype" scope="session">全部</logic:equal>
               <logic:equal value="2" name="bean" property="totaltype" scope="session">割接</logic:equal>
               <logic:equal value="3" name="bean" property="totaltype" scope="session">修缮</logic:equal>统计
            </font>
            <display:table name="sessionScope.useinfo" requestURI="${ctx}/PartUseAction.do?method=dostat" id="currentRowObject"  pagesize="18">
                <%
                    BasicDynaBean objectA1 = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
                    String partId = "";
                    String partName = "";
                    if(objectA1 != null){   
                        partId = (String) objectA1.get("id");                     
                        partName = (String) objectA1.get("name");
                    }
                 %>
                 
                   <display:column property="contractorname" title="单位名称" maxLength="10" style="align:center"/>
                   <logic:equal value="1" name="bean" property="totaltype">
                      <display:column property="name" title="材料名称" maxLength="10" style="align:center"/>
                  </logic:equal>      
                  
                  <logic:notEqual value="1" name="bean" property="totaltype">
                  <display:column media="html" title="材料名称" style="width:100px" >
                    <%if(partId != null){ %>
                        <a href="javascript:toAuditForm('<%=partId %>')" ><%=partName %></a>
                    <%} %>
                    </display:column>
                  </logic:notEqual>
                
                <display:column property="unit" title="计量单位"maxLength="20"  style="align:center"/>
                <logic:equal value="1" name="bean" property="totaltype">
                	<display:column property="usetime" title="使用时间" maxLength="12" style="align:center"/>
                </logic:equal>
                <logic:equal value="1" name="bean" property="totaltype">
                <display:column property="type" title="材料型号" maxLength="20" style="align:center"/>
                	<display:column property="usenewnumber" title="新品用量" maxLength="10"/>
                	<display:column property="useoldnumber" title="旧品用量" maxLength="8" style="align:center"/>
                </logic:equal>
                <logic:notEqual value="1" name="bean" property="totaltype">
                	<display:column property="usenewnumber" title="使用量" maxLength="10"/>
                </logic:notEqual>

            </display:table>
              <logic:notEmpty name="useinfo">
                    <html:link action="/PartUseAction.do?method=exportUse">导出为Excel文件</html:link><br>
             </logic:notEmpty>
             <div align="center"><html:button property="action" styleClass="button" onclick="gostatquery()" >返回</html:button></div>
    </logic:equal>
    
    <logic:equal value="use700" name="type"scope="session">
         <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
               <apptag:checkpower thirdmould="80303" ishead="1">
                <jsp:forward page="/globinfo/powererror.jsp"/>
            </apptag:checkpower>
            <br />
            <template:titile value="材料使用详细信息统计结果" />
       <table align="center" width="90%" border="0">
       <tr class=trcolor>
        <td align="right" width="8%">
          <b>材料名称:</b>
        </td>
        <td align="left" width="25%"><bean:write name="partinfo" property="name"/></td>
        
        <td align="right" width="8%">
          <b>型号:</b>
        </td>
        <td align="left" width="20%"><bean:write name="partinfo" property="type"/></td>
    
        <td align="right" width="8%">
          <b>单位:</b>
        </td>
        <td align="left" width="21%"><bean:write name="partinfo" property="unit"/></td>
        </tr>
        <tr class=trcolor>
            <td colspan="6" valign="bottom">
            
            </td>
        </tr>
        <tr class=trcolor>
            <td colspan="6" valign="bottom">
            <b>该材料的使用情况：</b>
            </td>
        </tr>
         <tr class=trcolor>
            <td colspan="6" valign="bottom">
               <display:table name="sessionScope.userInfo" id="currentRowObject"  pagesize="18">
                   
                   <logic:equal value="2" name="bean" property="totaltype">
                   <display:column property="name" title="割接名称" maxLength="20" style="align:center"/>
                   </logic:equal>
                   <logic:equal value="3" name="bean" property="totaltype">
                   <display:column property="name" title="修缮名称" maxLength="20" style="align:center"/>
                   </logic:equal>
                   <display:column property="usereason" title="材料用途" maxLength="20" style="align:center"/>
                   <display:column property="usetime" title="出库时间" maxLength="20" style="align:center"/>
                   <display:column property="usenewnumber" title="使用量" maxLength="20" style="align:center"/>                 
               </display:table>
            </td>
         </tr>
       </table>
       <p align="center">
             <html:button property="action" styleClass="button" onclick="goBack()" >返回	</html:button>
       </p>
 </logic:equal>

    <!--/////////////////退库处理////////////////-->

        <!--填写退库单-->
    <logic:equal value="use6"  name="type"scope="session">
       
        <link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
          <apptag:checkpower thirdmould="80306" ishead="1">
                <jsp:forward page="/globinfo/powererror.jsp"/>
        </apptag:checkpower>
         <br/>
        <template:titile value="材料退库处理"/>
         <html:form action="/PartUseAction?method=showBackPart" styleId="backForm">
            <table align="center" width="300" border="0">
                <tr>
                    <td height="25"><b>请选择材料用途:</b></td>
                    <td>
                        <select dojoType="ComboBox" dataUrl="PartUseAction.do?method=showBackReason"
		 					style="width: 180px;" name="name"  pageSize="10" > 
						</select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <br/><html:button property="action" styleClass="button" onclick="OnbackStockSelect()" >开始退库</html:button>
                      <!-- <html:button property="action" styleClass="button" onclick="javascript:history.back()" >返回	</html:button> -->  
                    </td>
                </tr>
            </table>
        </html:form>
    </logic:equal>

        <!--输入退库详细信息-->
    <logic:equal value="use60"   name="type" scope="session">
        <apptag:checkpower thirdmould="80306" ishead="1">
                <jsp:forward page="/globinfo/powererror.jsp"/>
        </apptag:checkpower>
            <br />
          <template:titile value="材料出库单详细信息" />
             <table class="tabout" align="center" width="90%" border="0">
                      <tr class=trcolor>
                        <td width="8%" height="25"><b>单位名称:</b></td>
                        <td align="left" width="25%"><bean:write name="useinfo" property="contractorname"/></td>
                        <td  align="right" width="8%"><b>出库人:</b></td>
                        <td align="left" width="15%"><bean:write name="useinfo" property="username"/></td>
                        <td width="10%" align="right"><b>出库时间:</b></td>
                        <td align="left" ><bean:write name="useinfo" property="usetime"/></td>
                    </tr>
                    <tr class=trcolor>
                        <td height="25"><b>材料用途:</b></td>
                        <td colspan="5"><bean:write name="useinfo" property="usereason"/></td>
                    </tr>
                    <tr class=trcolor>
                        <td height="25"><b>备注信息:</b></td>
                        <td colspan="5"><bean:write name="useinfo" property="useremark"/></td>
                    </tr>

                    <tr class=trcolor>
                        <td colspan="6"   valign="bottom">
                            <br /><b>请填写该出库单所要退回的材料:</b>
                        </td>
                    </tr>
                    
                    <tr class=trcolor>
                        <td colspan="6"   valign="bottom">
                            <html:form action="/PartUseAction?method=doBackStock" styleId="addForm">
			                <logic:present name="usepartinfo">
			                    <logic:iterate id="usepartinfoId" name="usepartinfo">
			                        <script type="" language="javascript">
                            		initArray("<bean:write name="usepartinfoId" property="id"/>","<bean:write name="usepartinfoId" property="name"/>",
                                      "<bean:write name="usepartinfoId" property="unit"/>","<bean:write name="usepartinfoId" property="type"/>",
                                      "<bean:write name="usepartinfoId" property="usenewnumber"/>","<bean:write name="usepartinfoId" property="useoldnumber"/>");
                        		</script>
			                    </logic:iterate>
			                </logic:present>
			                   <input  type="hidden" name="useid" value="<bean:write name="useinfo" property="useid"/>"/>
			                   <table  width="100%"  id="queryID"  border="1" align="center" cellpadding="3" cellspacing="0" >
			
			                   <tr>
			                        <th  width="35%" class="thlist" align="center">材料名称</th>
			                         <th  width="10%" class="thlist" align="center">计量单位</th>
			                        <th  width="30%" class="thlist" align="center">规格型号</th>
			                        <th  width="12%" class="thlist" align="center">新材料退库</th>
			                        <th  width="12%" class="thlist" align="center">旧材料退库 </th>
			                    </tr>
			                    <logic:present name="usepartinfo">
			                        <logic:iterate id="useid" name="usepartinfo">
			                            <tr   >
			                                <td>
			                                  <input type="hidden" name="id" value="<bean:write name="useid" property="id"/>" />
			
			                                  <bean:write name="useid" property="name"/> -- <bean:write name="useid" property="type"/>
			                                </td>
			                                 <td>
			                                    <bean:write name="useid" property="unit"/>
			                                </td>
			                                <td>
			                                  <bean:write name="useid" property="type"/>
			
			                                </td>
			                                <td>
			                                    <input  type="text"  name="newbacknumber"  maxlength="6" size="8" class="inputtext" value="0"
			                                             id="t1<bean:write name="useid" property="id"/>" onblur="checkback(id)"/>
			                                </td>
			                                <td>
			                                    <input  type="text" name="oldbacknumber"  maxlength="6" size="8" class="inputtext" value="0"
			                                             id="t2<bean:write name="useid" property="id"/>" onblur="checkback(id)"/>
			                                </td>
			
			                                </tr>
			                        </logic:iterate>
			                    </logic:present>
			                </table>
                        </td>
                    </tr>
                </table>

         
                <p align="center">
                        <html:submit property="action" styleClass="button" >开始退库</html:submit>
                        <html:button property="action" styleClass="button" onclick="javascript:history.back()" >返回	</html:button>
                </p>
         </html:form>
    </logic:equal>

    <logic:equal value="showuse1" name="type"scope="session">
        <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>              
            <br />
            <template:titile value="材料出库单一览表" />
            <display:table name="sessionScope.useinfo" id="currentRowObject"  pagesize="18">
            	<%
                    BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
                    String id = "";
                    String usereason = "";
                    String reasonTitle = "";
                    if(object != null) {
                    	id = (String) object.get("useid");
                    	reasonTitle = (String) object.get("usereason");
			    	 	if(reasonTitle != null && reasonTitle.length() > 15) {
			    			usereason = reasonTitle.substring(0,15) + "...";
			    	 	} else {
			    			usereason = reasonTitle;
			    	 	}
                    }
                 %>
                <display:column media="html" title="出库单流水号" style="width:100px">                    
                     <a href="javascript:toGetForm('<%=id%>')"><%=id%></a>
                 </display:column>
                <display:column media="html" title="材料用途" >                    
                     <a href="javascript:toGetForm('<%=id%>')" title="<%=reasonTitle %>"><%=usereason%></a>
                 </display:column>
                <display:column property="contractorname" title="出库单位" maxLength="20" style="align:center"/>
                <display:column property="username" title="出库人"maxLength="20" style="align:center"/>
                <display:column property="usetime" title="出库时间" maxLength="20" style="align:center;width:120px"/> 
            </display:table>
             <logic:notEmpty name="useinfo">
                   <html:link action="/PartUseAction.do?method=exportUseResult">导出为Excel文件</html:link><br>
             </logic:notEmpty>
             <div align="center"><html:button property="action" styleClass="button" onclick="goquery()" >返回</html:button></div>
    </logic:equal>
    <iframe id="hiddenIframe" style="display:none"></iframe>
</body>
</html>
