<%@include file="/common/header.jsp"%>
<html>
<head>
<script type="text/javascript" src="${ctx}/js/prototype.js"></script>
<script type="" language="javascript">
	var rowArr = new Array();//一行材料的信息
    var infoArr = new Array();//所有材料的信息

    //初始化数组
    function initArray(id,name){
        rowArr[0] = id;
        rowArr[1] = name;
		infoArr[infoArr.length] = rowArr;
        rowArr = new Array();
        return true;
    }
	
    function cancel() {
  		this.close();
	}

	function submit() {
		var pWindow=window.dialogArguments;
		var parentObjId = document.getElementById("parentobjid").value
		var parentSum = document.getElementById("sum").value;

 		var sumNum = 0;
        var str = "";
		var tmpValue;
		var tmpNum;
		var tmpName;
		var selIndex;
		for(i =1; i<tableId.rows.length;i++){
            var trid = tableId.rows[i].id;
			
			if(document.getElementById("select"+trid) != null ){

            	tmpValue = document.getElementById("select"+trid).value;
				selIndex = document.getElementById("select"+trid).selectedIndex;
				if(selIndex == -1 || tmpValue == "-1") {
					alert("第" + i + "行没有选择用途，请选择！");
                    return;
				}

				tmpName = document.getElementById("select"+trid).options[selIndex].text;
				tmpNum = document.getElementById("text"+trid).value;
				if(tmpNum == "0") {
					alert("第" + i + "行没有输入用途数量，请输入！");
					return;
				}
				sumNum = sumNum + parseInt(tmpNum);
				if(str == "") {
					str = tmpName + "#" + tmpValue + ":" + tmpNum;
				} else {
					str = str + ";" + tmpName + "#" + tmpValue + ":" + tmpNum;
				}
			}
        }	

		if(parseInt(parentSum) < sumNum) {
			alert("材料的出库数是:" + parentSum + ",请重新输入用途数量!");
			return;
		}	

		//alert(str);
		pWindow.document.getElementById(parentObjId).value = str;
		this.close();

	}

	function checkinputnum() {
		var mysplit = /^\d{1,6}$/;
        if(!mysplit.test(this.value)){
            alert("你填写的不是数字,请重新输入");
            this.value="0";
            this.focus();
            return false;
        }
	}

	function addRow() {
		// 取得页面上显示是割接还是修缮
		var flg = document.getElementById("flg").value;

		var  onerow=tableId.insertRow(-1);
		onerow.style.background="#F5F5F5";
		onerow.style.height=40;
        onerow.id = tableId.rows.length;
        var   cell1=onerow.insertCell();
        var   cell2=onerow.insertCell();
		var   cell3=onerow.insertCell();
		//创建一个select
        var s1 = document.createElement("select");
        s1.options.length = infoArr.length + 1;
        s1.options[0].value = "-1";
        s1.options[0].text = "请选择";
        for(i = 1; i<s1.options.length ;i++){
            s1.options[i].value = infoArr[i-1][0];
            s1.options[i].text = infoArr[i-1][1];
        }
        s1.name = "id";
        s1.id = "select" + onerow.id;
        s1.onchange = onselectchange;
        //s1.Class="inputtext"
        s1.style.background="#C6D6E2"
        s1.style.font.size="12px";
        s1.style.width="230";

		//创建一个输入框(新材料)
        var t1 = document.createElement("input");
        t1.name = "number"
        t1.id = "text" + onerow.id;
        t1.value= "0";
        t1.maxLength = "6";
        t1.size = "4";
        t1.onblur = checkinputnum;
        t1.style.background="#C6D6E2"
        t1.style.font.size="12px";
		t1.style.width="60px";

		var b1 =document.createElement("button");
        b1.value = "删除";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
		
		if(flg == "1") {
			cell1.innerText = "割接：";
		} else {
			cell1.innerText = "修缮：";
		}
		
		cell1.appendChild(s1);
		cell2.appendChild(t1);
		cell3.appendChild(b1);

		return onerow.id;
	
	}

	function addRowaddRowLineCut() {
		// 取得页面上显示是割接还是修缮
		var flg = document.getElementById("flg").value;		
		var  onerow=tableId.insertRow(-1);
		onerow.style.background="#F5F5F5";
		onerow.style.height=40;
        onerow.id = tableId.rows.length;
        var   cell1=onerow.insertCell();
        var   cell2=onerow.insertCell();
		var   cell3=onerow.insertCell();

		//创建一个输入框(新材料)
        var t1 = document.createElement("input");
        t1.name = "number"
        t1.id = "text" + onerow.id;
        t1.value= "0";
        t1.maxLength = "6";
        t1.size = "4";
        t1.onblur = checkinputnum;
        t1.style.background="#C6D6E2"
        t1.style.font.size="12px";
		t1.style.width="60px";

		var b1 =document.createElement("button");
        b1.value = "删除";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
		
		if(flg == "1") {
			// 割接
			//创建一个select
        	var s1 = document.createElement("select");
        	s1.options.length = infoArr.length + 1;
        	s1.options[0].value = "mention";
        	s1.options[0].text = "-选择线路级别-";
        	for(i = 1; i<s1.options.length ;i++){
            	s1.options[i].value = infoArr[i-1][0];
            	s1.options[i].text = infoArr[i-1][1];
        	}
        	s1.name = "level";
        	s1.id = "level" + onerow.id;
        	s1.onchange = onSelChangeLevel;
        	//s1.Class="inputtext"
        	s1.style.background="#C6D6E2"
        	s1.style.font.size="12px";
        	s1.style.width="120";

	//	var imgObj = document.createElement("<img src='${ctx}/images/ajaxtags/indicator.gif' style='display: none'/>");
    //    imgObj.id = "Loadingimg" + onerow.id;

			//创建一个select
        	var s2 = document.createElement("select");   
        	s2.name = "line";
        	s2.id = "line" + onerow.id;
       	 	s2.onchange = onSelChangeLine;
        	s2.style.background="#C6D6E2"
        	s2.style.font.size="12px";
        	s2.style.width="120";

			//创建一个select
        	var s3 = document.createElement("select");     
        	s3.name = "sublineid";
        	s3.id = "clew" + onerow.id;
        	s3.onchange = onSelectChangeClew;
        	s3.style.background="#C6D6E2"
        	s3.style.font.size="12px";
        	s3.style.width="130";

			//创建一个select
        	var s4 = document.createElement("select");     
        	s4.name = "id";
        	s4.id = "select" + onerow.id;
        	s4.onchange = onselectchange;
        	//s1.Class="inputtext"
        	s4.style.background="#C6D6E2"
        	s4.style.font.size="12px";
        	s4.style.width="150";
			cell1.innerText = "割接：";
			cell1.appendChild(s1);
			cell1.appendChild(s2);
			cell1.appendChild(s3);
			cell1.appendChild(s4);
		} else {
			var ss1 = document.createElement("select");
        	ss1.options.length = infoArr.length + 1;
        	ss1.options[0].value = "mention";
        	ss1.options[0].text = "-选择线路级别-";
        	for(i = 1; i<ss1.options.length ;i++){
            	ss1.options[i].value = infoArr[i-1][0];
            	ss1.options[i].text = infoArr[i-1][1];
        	}
        	ss1.name = "level";
        	ss1.id = "level" + onerow.id;
        	ss1.onchange = onLineChange;
        	//ss1.Class="inputtext"
        	ss1.style.background="#C6D6E2"
        	ss1.style.font.size="12px";
        	ss1.style.width="120";

			//创建一个select
        	var ss4 = document.createElement("select");     
        	ss4.name = "id";
        	ss4.id = "select" + onerow.id;
        	ss4.onchange = onselectchange;
        	//s1.Class="inputtext"
        	ss4.style.background="#C6D6E2"
        	ss4.style.font.size="12px";
        	ss4.style.width="250";
			// 修缮
			cell1.innerText = "修缮：";
			cell1.appendChild(ss1);
			cell1.appendChild(ss4);
			
		}
		
		
		cell2.appendChild(t1);
		cell3.appendChild(b1);

		return onerow.id;
	
	}

	//脚本生成的select动作onselectchange
    function onselectchange(){
        rowid = this.id.substring(6,this.id.length);        
		var selectValue = this.value;
		var trid;
		var tmpValue;
		// 不能有重新选择项
        for(i =1; i<tableId.rows.length;i++){
            trid = tableId.rows[i].id;
			if(rowid != trid) {
				if(document.getElementById("select"+trid) != null ){
            		tmpValue = document.getElementById("select"+trid).value;
					if(tmpValue == selectValue) {
						alert("该用途已经选择过了，请重新选择！");
						this.value = "-1";
						return;
					}
				}
			}
            
        }
    }

	 //脚本生成的删除按  钮的删除动作
    function deleteRow(){
          //获得按钮所在行的id
         var rowid = "000";
        rowid = this.id;
        rowid = rowid.substring(1,rowid.length);
        //由id转换为行索找行的索引,并删除
          for(i =0; i<tableId.rows.length;i++){
            if(tableId.rows[i].id == rowid){
                tableId.deleteRow(tableId.rows[i].rowIndex);
            }
        }
    }


	//割接级联..............
				var rowid = '0';
				function onSelChangeLevel() {
					rowid = this.id.substring(5,this.id.length); 
					var ops = document.getElementById('line'+rowid);
					ops.options.length = 0;
					var clewOps = document.getElementById('clew'+rowid);
					clewOps.options.length = 0;
					var newClewOp = document.createElement("option");
					newClewOp.value = "";
					newClewOp.text = "--选择中继段名--";
					clewOps.add(newClewOp);
					var params = document.getElementById('level'+rowid).value;
					if(params == "mention") {
						//ops.options.add(new Option("",""));
					}
					var url = 'LineCutReAction.do?method=getLineByLevelId&levelId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callback, asynchronous:true});
				}
				
				function callback(originalRequest) {
					var ops = document.getElementById('line'+rowid);
					var rst = originalRequest.responseText;
					var linearr = eval('('+rst+')');
					if(linearr.length == 0) {
						alert("该级别尚未制定线路!");
						return;
					}

					ops.options.add(new Option('--选择线路名称--','mention'));
					for(var i = 0; i < linearr.length; i++) {
						ops.options.add(new Option(linearr[i].linename,linearr[i].lineid));
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
				
				function onSelChangeLine() {
					rowid = this.id.substring(4,this.id.length); 
					var ops = document.getElementById('clew'+rowid);
					var length = ops.length;
					ops.options.length = 0;//清空下拉列表
					ops.options.add(new Option("--选择中继段名--",""));
					var params = document.getElementById('line'+rowid).value;
					if(params == "mention") {
						ops.options.add(new Option("",""));
					}
					var url = 'LineCutReAction.do?method=getClewByLineId&lineId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callbackToServer, asynchronous:true});
				}
				
				function callbackToServer(originalRequest) {
					var ops = document.getElementById('clew'+rowid);
					var rst = originalRequest.responseText;
					var clewarr = eval('('+rst+')');
					if(clewarr.length == 0) {
						alert("该路线尚未制定线段!");
						return;
					}
					for(var i = 0; i < clewarr.length; i++) {
						ops.options.add(new Option(clewarr[i].sublinename,clewarr[i].sublineid));
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
					rowid = this.id.substring(4,this.id.length);
					var ops = document.getElementById('select'+rowid);
					var length = ops.length;
					ops.options.length = 0;//清空下拉列表
					ops.options.add(new Option("---请选择割接名称---","-1"));
					var params = document.getElementById('clew'+rowid).value;
					if(params == "mention") {
						//ops.options.add(new Option("",""));
					}
					var url = 'PartUseAction.do?method=getCutNameBySublineid&sublineId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callbackToServerByName, asynchronous:true});
				}
				
				function callbackToServerByName(originalRequest) {
					var ops = document.getElementById('select'+rowid);
					var rst = originalRequest.responseText;
					var nameArr = eval('('+rst+')');
					if(nameArr.length == 0) {
						alert("该路线下没有割接申请信息!");
						return;
					}
					for(var i = 0; i < nameArr.length; i++) {
						ops.options.add(new Option(nameArr[i].name,nameArr[i].id));
					}
				}

// 修缮级联

				function onLineChange() {
					rowid = this.id.substring(5,this.id.length); 
					var ops = document.getElementById('select'+rowid);
					ops.options.length = 0;
									
					var params = document.getElementById('level'+rowid).value;
					var url = 'PartUseAction.do?method=getLinechangeNameByLevel&levelId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:linechangecallback, asynchronous:true});
				}
				
				function linechangecallback(originalRequest) {
					var ops = document.getElementById('select'+rowid);
					var rst = originalRequest.responseText;
					var linearr = eval('('+rst+')');
					if(linearr.length == 0) {
						alert("该线路级别下没有修缮信息!");
						return;
					}
					ops.options.add(new Option('--选择修缮名称--','-1'));
					for(var i = 0; i < linearr.length; i++) {
						ops.options.add(new Option(linearr[i].name,linearr[i].id));
					}
					var myGlobalHandlers = {onCreate:function () {
					//Element.show("Loadingimg"+rowid);
					}, onFailure:function () {
						alert("网络连接出现问题，请关闭后重试!");
					}, onComplete:function () {
						if (Ajax.activeRequestCount == 0) {
							Element.hide("Loadingimg");
						}
					}};
					Ajax.Responders.register(myGlobalHandlers);
				}
</script>
<title>
	选择材料用途页面
</title>
</head>
<body >
      <table height="30"><tr><td></td></tr></table>
      <table id="tableId" width="680" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">      
      <tr>
      	<th width="580" class="thlist" align="center" >材料用途</th>
      	<th width="60" class="thlist" align="center">用途数量</th>
      	<input type="hidden" id="parentobjid" value="<bean:write name='hiddenobjname'/>"/>
      	<input type="hidden" id="sum" value="<bean:write name='sum'/>"/>
      	<input type="hidden" id="flg" value="<bean:write name='flg'/>"/>
      	<th class="thlist" align="center" width="40">操作</th>
      </tr>
      <logic:notEmpty name="linelevelList">
	      <logic:present name="linelevelList">	 
	      		<logic:iterate id="element" name="linelevelList">
  					<script language="javascript">
      					initArray("<bean:write name='element' property='code'/>","<bean:write name='element' property='name'/>");
      				</script>
      			</logic:iterate>	      		
	      </logic:present>
      </logic:notEmpty>
       </table>  
       <p align="center">
      	 <input type="button" id="add" class="button" value="增加用途" onclick="addRowaddRowLineCut()" >
         <input type="button" id="submit" class="button" value="提交用途" onclick="submit()" >
         <input type="button" id="cancel" class="button" value="取消" onclick="cancel()">
       </p>
</body>
<script type="" language="javascript">
	var parentObjId = document.getElementById("parentobjid").value
	var pWindow=window.dialogArguments;
	var str = pWindow.document.getElementById(parentObjId).value;
	if(str != "" && str != ";") {
		var rowid;
		var par;
		var parArray = str.split(';');
		var ops;
		var ele;
		for (var j=0; j<parArray.length; j++) {				
			if(parArray[j] != "") {
				rowid = addRowaddRowLineCut();
				par = parArray[j].split(":");

				ops = document.getElementById("select"+rowid);
				ele = par[0].split("?");
				ops.options.add(new Option(ele[0],ele[1]));
				//document.getElementById("select"+rowid).value = par[0];
				document.getElementById("text"+rowid).value = par[1];
			}
		}					 
	}

	// 如果不存在选择的用途信息，增加和提交按钮不可用
//	if(infoArr.length == 0) {
//		document.getElementById("add").disabled  = true;
//	document.getElementById("submit").disabled  = true;
//	}
	
	
</script>
</html>
                  