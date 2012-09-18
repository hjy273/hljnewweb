<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<script type="" language="javascript">
   
    //脚本生成的删除按  钮的删除动作
    function deleteRow(){
          //获得按钮所在行的id
         var rowid = "000";
        rowid = this.id;
        rowid = rowid.substring(1,rowid.length);
        //由id转换为行索找行的索引,并删除
          for(i =0; i<$('queryID').rows.length;i++){
            if($('queryID').rows[i].id == rowid){
                $('queryID').deleteRow($('queryID').rows[i].rowIndex);
            }
        }
    }

    //脚本生成的select动作onselectchange  根据地址级联材料
    function onselectchange(){
        var materialID = document.getElementById("materialID");
        rowid = this.id.substring(6,this.id.length);
		var selectValue = this.value;
        var selmt = document.getElementById("selmt"+rowid);
		selmt.options.length=0;
       var row=0;
		for(i = 0; i<materialID.length ;i++){
			if(materialID[i].addrid != undefined){
				if(materialID[i].addrid==selectValue){

					row++;
              	 // selmt.options.length=row;
 					selmt.options.add(new Option(materialID[i].text, materialID[i].value));
					selmt.options[row-1].newstock=materialID[i].newstock;
				    selmt.options[row-1].oldstock=materialID[i].oldstock;
                    selmt.options[row-1].stockid=materialID[i].stockid;
                    selmt.options[row-1].newstockAddr=materialID[i].newstockAddr;
                    selmt.options[row-1].oldstockAddr=materialID[i].oldstockAddr;
 				}
			}
        }      

	//根据材料级联新增数量与利旧数据
 		var tr;//行对象
        for(i =0; i<$('queryID').rows.length;i++){
            if($('queryID').rows[i].id == rowid){
                tr = $('queryID').rows[i];
            }
		}
        var stockid = document.getElementById("stockid"+rowid);
        var newstockAddr = document.getElementById("newstockAddr"+rowid);
		var oldstockAddr = document.getElementById("oldstockAddr"+rowid);
         var selmaterialid = selmt.value;
        if(selmaterialid==""){
			tr.cells[2].innerText ="没选材料";
       		tr.cells[3].innerText ="没选材料";
            newstockAddr.value="";
            oldstockAddr.value="";
		}
		for(m = 0; m<selmt.options.length ;m++){
			if(selmaterialid==selmt[m].value){
				 tr.cells[2].innerText =selmt[m].newstock;
       			 tr.cells[3].innerText =selmt[m].oldstock;
				 stockid.value=selmt[m].stockid;
                 newstockAddr.value=selmt[m].newstockAddr;
                 oldstockAddr.value=selmt[m].oldstockAddr;
			}
		}
		//selmt.onchange = onselmtchange();
    }

 // 根据材料级联新增数量与利旧数据
    function onselmtchange(){
		rowid = this.id.substring(5,this.id.length);
		var selectValue = this.value;
        var selmt = document.getElementById("selmt"+rowid);
 		var tr;//行对象
        for(i =0; i<$('queryID').rows.length;i++){
            if($('queryID').rows[i].id == rowid){
                tr = $('queryID').rows[i];
            }
		}
        var selmaterialid = selmt.value;	
        var stockid = document.getElementById("stockid"+rowid);
		var newstockAddr = document.getElementById("newstockAddr"+rowid);
		var oldstockAddr = document.getElementById("oldstockAddr"+rowid);
		for(m = 0; m<selmt.options.length ;m++){
			if(selmaterialid==selmt[m].value){
				 tr.cells[2].innerText =selmt[m].newstock;
       			 tr.cells[3].innerText =selmt[m].oldstock;
				  stockid.value=selmt[m].stockid;
                  newstockAddr.value=selmt[m].newstockAddr;
                  oldstockAddr.value=selmt[m].oldstockAddr;
			}
		}
   }

    //添加一个新行
    function addRow(){
        var  onerow=$('queryID').insertRow(-1);
        onerow.id = $('queryID').rows.length;

        var   cell1=onerow.insertCell(-1);
        var   cell2=onerow.insertCell(-1);
        var   cell3=onerow.insertCell(-1);
        var   cell4=onerow.insertCell(-1);
        var   cell5=onerow.insertCell(-1);
 		var   cell6=onerow.insertCell(-1);
 		var   cell7=onerow.insertCell(-1);
 		var   cell8=onerow.insertCell(-1);
	

		var stockid = document.createElement("input");
		stockid.name = "addrstockid"
        stockid.id = "stockid" + onerow.id;
        stockid.type="hidden";

		var newstockAddr = document.createElement("input");
		newstockAddr.name = "newstockAddr"
        newstockAddr.id = "newstockAddr" + onerow.id;
        newstockAddr.type="hidden";

		var oldstockAddr = document.createElement("input");
		oldstockAddr.name = "oldstockAddr"
        oldstockAddr.id = "oldstockAddr" + onerow.id;
        oldstockAddr.type="hidden";

        //创建一个输入框
        var t1 = document.createElement("input");
        t1.name = "oldstock"
        t1.id = "oldstock" + onerow.id;
        t1.value= "0";
        t1.maxLength = "10";
        t1.size = "10"
        t1.onblur=valiD;
        //t1.style.background="#C6D6E2"
        t1.style.font.size="12px";

 		var t2 = document.createElement("input");
        t2.name = "newstock"
        t2.id = "newstock" + onerow.id;
        t2.value= "0";
        t2.maxLength = "10";
        t2.size = "10";
        t2.onblur=valiD;
        //t2.style.background="#C6D6E2"
        t2.style.font.size="12px";

		var addrID = document.getElementById("addrID");
        //创建一个select
        var s1 = document.createElement("select");
        s1.options.length = addrID.length+1 ;
        s1.options[0].value = "";
        s1.options[0].text = "请选地址";
        for(i = 1; i<s1.options.length ;i++){
            s1.options[i].value = addrID[i-1].value;
            s1.options[i].text = addrID[i-1].text;
        }
        s1.name = "oldaddressid";
        s1.id = "select" + onerow.id;
        s1.onchange = onselectchange;
        //s1.style.background="#C6D6E2"
        s1.style.font.size="12px";
        s1.style.width="150";

        //创建删除按钮
        var b1 =document.createElement("input");
		b1.type="button";
        b1.value = "删除";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;

        cell1.appendChild(s1);//文字
        cell1.appendChild(stockid);
        cell1.appendChild(newstockAddr);
        cell1.appendChild(oldstockAddr);
       
        //创建一个select
        var s2 = document.createElement("select");//被调拨的材料
        s2.options.length = 1;
        s2.options[0].value = "";
        s2.options[0].text = "请选材料";
       
        s2.name = "materialID";
 		s2.newstock = "";
 		s2.oldstock = "";
        s2.id = "selmt" + onerow.id;
        s2.onchange = onselmtchange;
        //s2.style.background="#C6D6E2"
        s2.style.font.size="12px";
        s2.style.width="150";

 		//创建一个select
        var s3 = document.createElement("select");//使用地址
        s3.options.length = addrID.length+1;
        s3.options[0].value = "";
        s3.options[0].text = "选择使用地址";
        for(i = 1; i<s3.options.length ;i++){
            s3.options[i].value = addrID[i-1].value;
            s3.options[i].text = addrID[i-1].text;
        }
        s3.name = "newaddressid";
        s3.id = "useadd" + onerow.id;
       // s3.onchange = onselectchange;
        //s3.style.background="#C6D6E2"
        s3.style.font.size="12px";
        s3.style.width="150";
      
        cell2.appendChild(s2);//文字
        cell3.innerText="没选材料";//文字
        cell4.innerText="没选材料";//文字
 		cell5.appendChild(s3);//文字
		cell6.appendChild(t2);//文字
 		cell7.appendChild(t1);//文字
        cell8.appendChild(b1);
    }

  //验证数字以及大小
    function valiD(){
        var mysplit = /^[1-9]\d{0,6}[\.]{0,4}\d{0,6}$|^-?0(\.\d*)?$/;
		var num = this.value;
         var num1 = parseFloat(num);
        if(mysplit.test(num) && num>0){
			 var selid = this.id;
			 var rowid = this.id.substring(8,this.id.length);
			 var os = "oldstock"+rowid;
             var ns = "newstock"+rowid;
			 if(selid==os){
            	 var oldstockAddr = document.getElementById('oldstockAddr'+rowid).value; 
                 if(oldstockAddr==""){
					 alert("请先选择材料!");
					 this.value=0;
           			 this.focus();
                  }
                 var os = parseFloat(oldstockAddr);
				 if(oldstockAddr>=0 && num1>os){
					alert("值大于库存数量!");
					this.value=0
                    this.focus();
				 }
             }
             if(selid==ns){
                 var newstockAddr = document.getElementById('newstockAddr'+rowid).value;
				  if(newstockAddr==""){
					 alert("请先选择材料!");
					 this.value=0;
           			 this.focus();
                  }
                   var ns = parseFloat(newstockAddr);
				 if(newstockAddr>=0 && num>ns){
					alert("值大于库存数量!");
					this.value=0
                    this.focus();
				 }
             }
             return true;
        }
         if(!mysplit.test(num)){
            alert("请填写规范的数字!");
            this.value=0
            this.focus();
        }
    }
 

//添加提交
    function toAddSub(){
	  var rownum = $('queryID').rows.length;
      if(rownum < 2){
          alert("你还没有添加一个数据,不能提交!")
          return false;
      }
      var rowid;
	  for(i =1; i<rownum;i++){//判断行是不是空数据
			rowid=$('queryID').rows[i].id;
            var r = rowid-1;
            var selmt = document.getElementById('selmt'+rowid).value;
            var useadd = document.getElementById('useadd'+rowid).value;
			var oldstock = document.getElementById('oldstock'+rowid).value;
            var newstock = document.getElementById('newstock'+rowid).value;

            var oldstockOut = document.getElementById('oldstockAddr'+rowid).value;
            var newstockOut = document.getElementById('newstockAddr'+rowid).value;
			var addrOut = document.getElementById('select'+rowid).value;
           
			var addrsel = document.getElementById('select'+rowid);
            var addr = addrsel.options[addrsel.selectedIndex].innerText;
			var mtnamesel = document.getElementById('selmt'+rowid);
			var mtname = mtnamesel.options[mtnamesel.selectedIndex].innerText;
			if(selmt==""){
				alert("第"+r+"行材料不能为空!");
				return false;
			}
			if(useadd==""){
				alert("第"+r+"行使用地址不能为空!");
				return false;
			}
            if(oldstock==0&&newstock==0){
				alert("第"+r+"行使用数量和不能为0!");
				return false;
			}
			var newin = parseFloat(newstock);
			var newout = parseFloat(newstockOut);
			var oldin = parseFloat(oldstock);
      	    var oldout = parseFloat(oldstockOut);
		    if(newin>newout || oldin>oldout){
				alert("第"+r+"行的调出库存值大于库存数量!");
				return false;
		    }
			
			var materialID = document.getElementById('selmt'+rowid).value;
           // alert(materialID);
			var num = 0;
             var oldstockin = 0;
             var newstockin = 0;
 			for(m=1; m<rownum;m++){//判断行是不是空数据
				var rowID=$('queryID').rows[m].id;
                var mtID = document.getElementById('selmt'+rowID).value;
        
           		var oldstockin1 = parseFloat(document.getElementById('oldstock'+rowID).value);
                var newstockin1 = parseFloat(document.getElementById('newstock'+rowID).value);
				var addrout = document.getElementById('select'+rowID).value;
                if(materialID==mtID && addrout==addrOut){
					oldstockin+=oldstockin1;
                    newstockin+=newstockin1;
					num++;
				}
			}
            if(num>=2){
				if(parseFloat(oldstockin)>oldout){
					alert(addr+" "+mtname+" 调入利旧的总和大于利旧库存总数!");
					return false;
				}
				if(parseFloat(newstockin)>newout){
					alert(addr+" "+mtname+" 调入新增的总和大于新增库存总数!");
					return false;
				}
			}
			
	 }

	var addsub = document.getElementById("addsub");
	addsub.disabled=true;
	addForm.submit();
    }  

</script>
		<title>材料调拨单</title>
	</head>
	<style type="text/css">
.subject {
	text-align: center;
}
</style>
	<body>

		<!--显示申请单详细信息-->
		<select name="addrID" style="display: none" id="addrID">
			<logic:present scope="request" name="address">
				<logic:iterate id="r" name="address">
					<option value="<bean:write name="r" property="id" />">
						<bean:write name="r" property="address" />
					</option>
				</logic:iterate>
			</logic:present>
		</select>
		<select name="materialID" style="display: none" id="materialID">
			<logic:present scope="request" name="materials">
				<logic:iterate id="r" name="materials">
					<option value="<bean:write name="r" property="id" />"
						addrid="<bean:write name="r" property="addrid" />"
						newstockAddr="<bean:write name="r" property="newstock" />"
						oldstockAddr="<bean:write name="r" property="oldstock" />"
						newstock="<bean:write name="r" property="newstock" />"
						oldstock="<bean:write name="r" property="oldstock" />"
						stockid="<bean:write name="r" property="stockid" />">
						<bean:write name="r" property="name" />
					</option>
				</logic:iterate>
			</logic:present>
		</select>
		<br />
		<template:titile value="材料调拨单" />
		<html:form action="/materialAllotAction.do?method=saveMaterialsAllot"
			styleId="addForm">
			<center>
				<table width="97%" border="1" cellpadding="0" cellspacing="0" style="border-collapse:collapse;" class="tabout">
					<tr class=trcolor>
						<td colspan="2" class="tdl" style="padding:10px;">
							<table width="100%" id="queryID" border="1" align="center"
								cellpadding="0" cellspacing="0"
								style="border-collapse:collapse;">
								<tr>
									<th width="10%" align="center">
										调出地址
									</th>
									<th width="12%" align="center">
										调出材料
									</th>
									<th width="10%" align="center">
										新增库存
									</th>
									<th width="10%" align="center">
										利旧库存
									</th>
									<th width="12%" align="center">
										调入地址
									</th>
									<th width="10%" align="center">
										调入新增库存
									</th>
									<th width="10%" align="center">
										调入利旧库存
									</th>
									<th width="8%" align="center">
										操作
									</th>
								</tr>
							</table>
						</td>
					</tr>
					<tr class=trcolor>
						<td height="25" width="15%" class="tdr">
							<b>备注信息:</b>
						</td>
						<td class="tdl">
							<html:textarea property="remark" styleClass="textarea"
								style="width:500px;" rows="5">
							</html:textarea>
						</td>
					</tr>
				</table>
			</center>
			<p align="center">
				<html:button property="action" styleClass="button"
					onclick="addRow()">添加新材料</html:button>
				<html:button property="action" styleClass="button" styleId="addsub"
					onclick="toAddSub()">提交</html:button>
			</p>
		</html:form>
	</body>
</html>
