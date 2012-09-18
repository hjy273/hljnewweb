<%@include file="/common/header.jsp"%>
<html>
<head>
<script type="text/javascript" src="${ctx}/js/prototype.js"></script>
<script type="" language="javascript">
	var rowArr = new Array();//һ�в��ϵ���Ϣ
    var infoArr = new Array();//���в��ϵ���Ϣ

    //��ʼ������
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
					alert("��" + i + "��û��ѡ����;����ѡ��");
                    return;
				}

				tmpName = document.getElementById("select"+trid).options[selIndex].text;
				tmpNum = document.getElementById("text"+trid).value;
				if(tmpNum == "0") {
					alert("��" + i + "��û��������;�����������룡");
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
			alert("���ϵĳ�������:" + parentSum + ",������������;����!");
			return;
		}	

		//alert(str);
		pWindow.document.getElementById(parentObjId).value = str;
		this.close();

	}

	function checkinputnum() {
		var mysplit = /^\d{1,6}$/;
        if(!mysplit.test(this.value)){
            alert("����д�Ĳ�������,����������");
            this.value="0";
            this.focus();
            return false;
        }
	}

	function addRow() {
		// ȡ��ҳ������ʾ�Ǹ�ӻ�������
		var flg = document.getElementById("flg").value;

		var  onerow=tableId.insertRow(-1);
		onerow.style.background="#F5F5F5";
		onerow.style.height=40;
        onerow.id = tableId.rows.length;
        var   cell1=onerow.insertCell();
        var   cell2=onerow.insertCell();
		var   cell3=onerow.insertCell();
		//����һ��select
        var s1 = document.createElement("select");
        s1.options.length = infoArr.length + 1;
        s1.options[0].value = "-1";
        s1.options[0].text = "��ѡ��";
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

		//����һ�������(�²���)
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
        b1.value = "ɾ��";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
		
		if(flg == "1") {
			cell1.innerText = "��ӣ�";
		} else {
			cell1.innerText = "���ɣ�";
		}
		
		cell1.appendChild(s1);
		cell2.appendChild(t1);
		cell3.appendChild(b1);

		return onerow.id;
	
	}

	function addRowaddRowLineCut() {
		// ȡ��ҳ������ʾ�Ǹ�ӻ�������
		var flg = document.getElementById("flg").value;		
		var  onerow=tableId.insertRow(-1);
		onerow.style.background="#F5F5F5";
		onerow.style.height=40;
        onerow.id = tableId.rows.length;
        var   cell1=onerow.insertCell();
        var   cell2=onerow.insertCell();
		var   cell3=onerow.insertCell();

		//����һ�������(�²���)
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
        b1.value = "ɾ��";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
		
		if(flg == "1") {
			// ���
			//����һ��select
        	var s1 = document.createElement("select");
        	s1.options.length = infoArr.length + 1;
        	s1.options[0].value = "mention";
        	s1.options[0].text = "-ѡ����·����-";
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

			//����һ��select
        	var s2 = document.createElement("select");   
        	s2.name = "line";
        	s2.id = "line" + onerow.id;
       	 	s2.onchange = onSelChangeLine;
        	s2.style.background="#C6D6E2"
        	s2.style.font.size="12px";
        	s2.style.width="120";

			//����һ��select
        	var s3 = document.createElement("select");     
        	s3.name = "sublineid";
        	s3.id = "clew" + onerow.id;
        	s3.onchange = onSelectChangeClew;
        	s3.style.background="#C6D6E2"
        	s3.style.font.size="12px";
        	s3.style.width="130";

			//����һ��select
        	var s4 = document.createElement("select");     
        	s4.name = "id";
        	s4.id = "select" + onerow.id;
        	s4.onchange = onselectchange;
        	//s1.Class="inputtext"
        	s4.style.background="#C6D6E2"
        	s4.style.font.size="12px";
        	s4.style.width="150";
			cell1.innerText = "��ӣ�";
			cell1.appendChild(s1);
			cell1.appendChild(s2);
			cell1.appendChild(s3);
			cell1.appendChild(s4);
		} else {
			var ss1 = document.createElement("select");
        	ss1.options.length = infoArr.length + 1;
        	ss1.options[0].value = "mention";
        	ss1.options[0].text = "-ѡ����·����-";
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

			//����һ��select
        	var ss4 = document.createElement("select");     
        	ss4.name = "id";
        	ss4.id = "select" + onerow.id;
        	ss4.onchange = onselectchange;
        	//s1.Class="inputtext"
        	ss4.style.background="#C6D6E2"
        	ss4.style.font.size="12px";
        	ss4.style.width="250";
			// ����
			cell1.innerText = "���ɣ�";
			cell1.appendChild(ss1);
			cell1.appendChild(ss4);
			
		}
		
		
		cell2.appendChild(t1);
		cell3.appendChild(b1);

		return onerow.id;
	
	}

	//�ű����ɵ�select����onselectchange
    function onselectchange(){
        rowid = this.id.substring(6,this.id.length);        
		var selectValue = this.value;
		var trid;
		var tmpValue;
		// ����������ѡ����
        for(i =1; i<tableId.rows.length;i++){
            trid = tableId.rows[i].id;
			if(rowid != trid) {
				if(document.getElementById("select"+trid) != null ){
            		tmpValue = document.getElementById("select"+trid).value;
					if(tmpValue == selectValue) {
						alert("����;�Ѿ�ѡ����ˣ�������ѡ��");
						this.value = "-1";
						return;
					}
				}
			}
            
        }
    }

	 //�ű����ɵ�ɾ����  ť��ɾ������
    function deleteRow(){
          //��ð�ť�����е�id
         var rowid = "000";
        rowid = this.id;
        rowid = rowid.substring(1,rowid.length);
        //��idת��Ϊ�������е�����,��ɾ��
          for(i =0; i<tableId.rows.length;i++){
            if(tableId.rows[i].id == rowid){
                tableId.deleteRow(tableId.rows[i].rowIndex);
            }
        }
    }


	//��Ӽ���..............
				var rowid = '0';
				function onSelChangeLevel() {
					rowid = this.id.substring(5,this.id.length); 
					var ops = document.getElementById('line'+rowid);
					ops.options.length = 0;
					var clewOps = document.getElementById('clew'+rowid);
					clewOps.options.length = 0;
					var newClewOp = document.createElement("option");
					newClewOp.value = "";
					newClewOp.text = "--ѡ���м̶���--";
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
						alert("�ü�����δ�ƶ���·!");
						return;
					}

					ops.options.add(new Option('--ѡ����·����--','mention'));
					for(var i = 0; i < linearr.length; i++) {
						ops.options.add(new Option(linearr[i].linename,linearr[i].lineid));
					}
					var myGlobalHandlers = {onCreate:function () {
					//Element.show("Loadingimg"+rowid);
					}, onFailure:function () {
						alert("�������ӳ������⣬��رպ�����!");
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
					ops.options.length = 0;//��������б�
					ops.options.add(new Option("--ѡ���м̶���--",""));
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
						alert("��·����δ�ƶ��߶�!");
						return;
					}
					for(var i = 0; i < clewarr.length; i++) {
						ops.options.add(new Option(clewarr[i].sublinename,clewarr[i].sublineid));
					}
					var myGlobalHandlers = {onCreate:function () {
					//Element.show("Loadingimg"+rowid);
					}, onFailure:function () {
						alert("�������ӳ������⣬��رպ�����!");
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
					ops.options.length = 0;//��������б�
					ops.options.add(new Option("---��ѡ��������---","-1"));
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
						alert("��·����û�и��������Ϣ!");
						return;
					}
					for(var i = 0; i < nameArr.length; i++) {
						ops.options.add(new Option(nameArr[i].name,nameArr[i].id));
					}
				}

// ���ɼ���

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
						alert("����·������û��������Ϣ!");
						return;
					}
					ops.options.add(new Option('--ѡ����������--','-1'));
					for(var i = 0; i < linearr.length; i++) {
						ops.options.add(new Option(linearr[i].name,linearr[i].id));
					}
					var myGlobalHandlers = {onCreate:function () {
					//Element.show("Loadingimg"+rowid);
					}, onFailure:function () {
						alert("�������ӳ������⣬��رպ�����!");
					}, onComplete:function () {
						if (Ajax.activeRequestCount == 0) {
							Element.hide("Loadingimg");
						}
					}};
					Ajax.Responders.register(myGlobalHandlers);
				}
</script>
<title>
	ѡ�������;ҳ��
</title>
</head>
<body >
      <table height="30"><tr><td></td></tr></table>
      <table id="tableId" width="680" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">      
      <tr>
      	<th width="580" class="thlist" align="center" >������;</th>
      	<th width="60" class="thlist" align="center">��;����</th>
      	<input type="hidden" id="parentobjid" value="<bean:write name='hiddenobjname'/>"/>
      	<input type="hidden" id="sum" value="<bean:write name='sum'/>"/>
      	<input type="hidden" id="flg" value="<bean:write name='flg'/>"/>
      	<th class="thlist" align="center" width="40">����</th>
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
      	 <input type="button" id="add" class="button" value="������;" onclick="addRowaddRowLineCut()" >
         <input type="button" id="submit" class="button" value="�ύ��;" onclick="submit()" >
         <input type="button" id="cancel" class="button" value="ȡ��" onclick="cancel()">
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

	// ���������ѡ�����;��Ϣ�����Ӻ��ύ��ť������
//	if(infoArr.length == 0) {
//		document.getElementById("add").disabled  = true;
//	document.getElementById("submit").disabled  = true;
//	}
	
	
</script>
</html>
                  