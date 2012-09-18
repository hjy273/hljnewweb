<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<script type="text/javascript" src="${ctx}/js/prototype.js"></script>
<link href="${ctx}/css/linepatrol_css.css" rel="stylesheet"
	type="text/css">
<html>
	<head>
		<script type="text/javascript">
		listApproveInfo=function(){
			var url="${ctx}/material_put_storage.do?method=queryMaterialPutStorageApproveInfoList";
			var queryString="put_storage_id=${bean.id}";
			var actionUrl=url+"&&"+queryString+"&&rnd="+Math.random();
			var myAjax=new Ajax.Updater(
				"putStorageApproveInfoTd",actionUrl,{
					method:"post",
					evalScripts:true
				}
			);
		}
		</script>
	</head>

	<script type="text/javascript">
  var rowArr = new Array();
  var infoArr = new Array();//所有材料的信息
  var addressArr = new Array();//所有地址的信息
  
  //初始化数组
    function initArray1(id,typename){
        rowArr[0] = id;
        rowArr[1] = typename;
        infoArr[infoArr.length] = rowArr;
        rowArr = new Array();
        return true;
    }
    
    //初始化数组
    function initArray2(id,address){
        rowArr[0] = id;
        rowArr[1] = address;
        
        addressArr[addressArr.length] = rowArr;
        rowArr = new Array();
        return true;
    }
    
    
  function onTypeSelected(){
        rowid = this.id.substring(7,this.id.length);
    	var selectValue = this.value;
    	var url = 'material_apply.do?method=getMaterialModes&id=' + selectValue;
		var myAjax = new Ajax.Request(url, {method:"post", onComplete:typecallback, asynchronous:true});
    }
    
      function onTypeSelected1(id,value){
        rowid = id.substring(7,id.length);
    	var selectValue = value;
    	var url = 'material_apply.do?method=getMaterialModes&id=' + selectValue;
		var myAjax = new Ajax.Request(url, {method:"post", onComplete:typecallback, asynchronous:true});
    }
    
    function typecallback(originalRequest) {
					var ops =  document.all.item("select2"+rowid);
					ops.options.length = 0;
					var rst = originalRequest.responseText;
					var linearr = eval('('+rst+')');
					if(linearr.length == 0) {
						alert("该类型下没有规格!");
						return;
					}

					ops.options.add(new Option('----请选择----',''));
					for(var i = 0; i < linearr.length; i++) {
						//ops.options.add(new Option(linearr[i].modelname,linearr[i].id));
						var option=new Option(linearr[i].modelname,linearr[i].id);
						option.varUnit=linearr[i].unit;
						ops.options.add(option);
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
    
    function onModelSelected(){
        rowid = this.id.substring(7,this.id.length);
        var index=this.options.selectedIndex;
    	var selectValue = this.value;
    	var url = 'material_apply.do?method=getMaterials&id=' + selectValue;
		document.getElementById("unit_"+rowid).innerText=this.options[index].varUnit;
		var myAjax = new Ajax.Request(url, {method:"post", onComplete:modelcallback, asynchronous:true});
    }
    
     function onModelSelected1(id,value){
        rowid = id.substring(7,id.length);
    	var selectValue = value;
    	var url = 'material_apply.do?method=getMaterials&id=' + selectValue;
		var myAjax = new Ajax.Request(url, {method:"post", onComplete:modelcallback, asynchronous:true});
    }
    
    function modelcallback(originalRequest) {
					var ops =  document.all.item("select3"+rowid);
					ops.options.length = 0;
					var rst = originalRequest.responseText;
					var linearr = eval('('+rst+')');
					if(linearr.length == 0) {
						alert("该规格下没有材料!");
						return;
					}

					ops.options.add(new Option('----请选择----',''));
					for(var i = 0; i < linearr.length; i++) {
						ops.options.add(new Option(linearr[i].name,linearr[i].id));
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
				    
  function onselectchange(){
        rowid = this.id.substring(7,this.id.length);
		var selectValue = this.value;
        var tmpValue;
        var tr;//行对象
        for(i =0; i<queryID.rows.length;i++){
            if(queryID.rows[i].id == rowid){
                tr = queryID.rows[i];
            } else {
				if(document.getElementById("select3"+queryID.rows[i].id) != null ){
            		tmpValue = document.getElementById("select3"+queryID.rows[i].id).value;
					if(tmpValue == selectValue) {
						alert("该材料已经选择过了，请重新选择入库材料！");
						document.getElementById("select3"+rowid).value = "";
						return;
					}
				}
			}
        }
        
    }
    
     function onselectchange1(id,value){
        rowid = id.substring(7,id.length);
		var selectValue = value;
        var tmpValue;
        var tr;//行对象
        for(i =0; i<queryID.rows.length;i++){
            if(queryID.rows[i].id == rowid){
                tr = queryID.rows[i];
            } else {
				if(document.getElementById("select3"+queryID.rows[i].id) != null ){
            		tmpValue = document.getElementById("select3"+queryID.rows[i].id).value;
					if(tmpValue == selectValue) {
						alert("该材料已经选择过了，请重新选择入库材料！");
						document.getElementById("select3"+rowid).value = "";
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
          for(i =0; i<queryID.rows.length;i++){
            if(queryID.rows[i].id == rowid){
                queryID.deleteRow(queryID.rows[i].rowIndex);
            }
        }
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
        var   cell8=onerow.insertCell();

        //创建一个输入框
        var t1 = document.createElement("input");
        t1.name = "count"
        t1.id = "text" + onerow.id;
        t1.value= "0";
        t1.maxLength = "6";
        t1.size = "6";
        t1.onblur=valiD;
        //t1.style.background="#C6D6E2"
        t1.style.font.size="12px";


        //创建一个select
        var s1 = document.createElement("select");
        s1.options.length = infoArr.length + 1;
        s1.options[0].value = "";
        s1.options[0].text = "请选择材料类型";
        for(i = 1; i<s1.options.length ;i++){
            s1.options[i].value = infoArr[i-1][0];
            s1.options[i].text = infoArr[i-1][1];
        }
        s1.name = "typename";
        s1.id = "select1" + onerow.id;
        s1.onchange = onTypeSelected;
        //s1.style.background="#C6D6E2"
        s1.style.font.size="12px";
        s1.style.width="100px";
        
        //创建一个select
        var s3 = document.createElement("select");
        s3.options.length = infoArr.length + 1;
        s3.options[0].value = "";
        s3.options[0].text = "请选择材料规格";
        s3.name = "modelname";
        s3.id = "select2" + onerow.id;
        s3.onchange = onModelSelected;
        //s3.style.background="#C6D6E2"
        s3.style.font.size="12px";
        s3.style.width="100px";
        
        //创建一个select
        var s4 = document.createElement("select");
        s4.options.length = infoArr.length + 1;
        s4.options[0].value = "";
        s4.options[0].text = "请选择材料名称";
        s4.name = "materialId";
        s4.id = "select3" + onerow.id;
        s4.onchange = onselectchange;
        //s4.style.background="#C6D6E2"
        s4.style.font.size="12px";
        s4.style.width="100px";
        
        var s5 = document.createElement("span");
        s5.id = "unit_" + onerow.id;
        s5.style.font.size="12px";
        
        var s9 = document.createElement("span");
        s9.id = "count_apply_" + onerow.id;
        s9.style.font.size="12px";
        s9.innerText="0";
        
        var h1 = document.createElement("input");
        h1.name = "applyCount"
        h1.id = "text_apply" + onerow.id;
        h1.value= "0";
        h1.maxLength = "6";
        h1.size = "6";
        h1.onblur=valiD;
        h1.type="hidden";
        //h1.style.background="#C6D6E2"
        h1.style.font.size="12px";
      
      
        var s2 =  document.createElement("select");
         s2.options.length = addressArr.length + 1;
         s2.options[0].value = "";
        s2.options[0].text = "请选择存放地址";
          for(i = 1; i<s2.options.length ;i++){
            s2.options[i].value = addressArr[i-1][0];
            s2.options[i].text = addressArr[i-1][1] ;
        }
        s2.name = "addressId";
        s2.id = "selectaddr" + onerow.id;
        //s2.style.background="#C6D6E2"
        s2.style.font.size="12px";
        s2.style.width="100px";

        //创建删除按钮
        var b1 =document.createElement("button");
        b1.value = "删除";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;

        cell1.appendChild(s1);//文字
        cell2.appendChild(s3);//文字
        cell3.appendChild(s4);//文字
        cell4.appendChild(s9);//文字
        cell4.appendChild(h1);
        cell5.appendChild(t1);//文字
        cell6.appendChild(s5);//文字
        cell7.appendChild(s2);
        cell8.appendChild(b1);
    }
    
    function deleteRow1(value){
      	
        rowid = value;
      
       
      	for(i =0; i<queryID.rows.length;i++){
      	   
        	if(queryID.rows[i].id == rowid){
            	queryID.deleteRow(queryID.rows[i].rowIndex);
               
            }
        }
    }
    
    
    //检验是否数字
    function valiD(){
         var mysplit = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
        if(mysplit.test(this.value)){
          return true;
        }
        else{
            alert("你填写的不是数字,请重新输入");
            this.value=0
            this.focus();

        }
    } 
    
    function saveForm(){
       var myform = document.forms[0];
       myform.isSubmited.value="0";
       processBar(addForm);
       myform.submit();
    }
    
    function checkForm(){
       var myform = document.forms[0];
       myform.isSubmited.value="1";
        if(queryID.rows.length < 2){
          alert("你还没有选择材料,不能提交!")
        return false;
      }
      if(myform.approverId.value==""){
      	alert("没有选择审核人！");
      	return false;
      }
       for(i =0; i<queryID.rows.length;i++){
      		if(document.getElementById("select3"+queryID.rows[i].id) != null ){
            		tmpValue = document.getElementById("select3"+queryID.rows[i].id).value;
					if(tmpValue==''){
					  alert('请选择材料');
					  return false;
					}
				}
			if(document.getElementById("selectaddr"+queryID.rows[i].id) != null ){
            		tmpValue = document.getElementById("selectaddr"+queryID.rows[i].id).value;
					if(tmpValue==''){
					  alert('请选择地址');
					  return false;
					}
				}	
				if(document.getElementById("text"+queryID.rows[i].id) != null ){
            		tmpValue = document.getElementById("text"+queryID.rows[i].id).value;
					if(tmpValue=='0'){
					  alert('入库数量不能为0');
					  return false;
					}
				}	
      }
     processBar(addForm);
       myform.submit();
    }
    
    function reName(value,deptname,username,date){
      var titlevalue = deptname+username+''+date;
      if(value== '1'){
         document.getElementById("11").style.display="";     
         document.getElementById("22").style.display="none"; 
         document.getElementById("33").style.display="none"; 
         titlevalue += '新增';    
      }else if(value== '2'){
      	 document.getElementById("11").style.display="none";     
         document.getElementById("22").style.display="none"; 
         document.getElementById("33").style.display=""; 
         titlevalue += '自购';
      }else{
         document.getElementById("22").style.display="";     
         document.getElementById("11").style.display="none";  
         document.getElementById("33").style.display="none"; 
         titlevalue += '利旧'; 
      }
      titlevalue += '材料入库单'; 
       //alert(titlevalue);
       document.getElementById("title").value=titlevalue;
    }
  </script>

	<body>
		<logic:present name="typeList">
			<logic:iterate id="type" name="typeList">
				<script type="" language="javascript">
                    initArray1("<bean:write name="type" property="id"/>","<bean:write name="type" property="typename"/>");
                </script>
			</logic:iterate>
		</logic:present>
		<logic:present name="addressList">
			<logic:iterate id="address" name="addressList">
				<script type="" language="javascript">
                    initArray2("<bean:write name="address" property="id"/>","<bean:write name="address" property="address"/>");
                </script>
			</logic:iterate>
		</logic:present>
		<template:titile value="修改材料入库单" />
		<html:form action="/material_put_storage.do?method=modMaterialPutStorage"
			styleId="addForm">
			<input type="hidden" name="id"
				value="<bean:write name="bean" property="id"/>" />
			<input type="hidden" name="isSubmited" value="1" />
			<input type="hidden" name="applyId"
				value="<bean:write name="applyBean" property="id"/>" />
			<input type="hidden" name="title"
				value="<bean:write name="applyBean" property="title"/>" />
			<input type="hidden" name="creator"
				value="<bean:write name="bean" property="creator"/>" />
			<input type="hidden" name="createDate"
				value="<bean:write name="bean" property="createDate"/>" />
			<input type="hidden" name="contractorId"
				value="<bean:write name="bean" property="contractorId"/>" />
			<center>
				<table width="90%" border="0" class="tabout" cellpadding="0"
					cellspacing="0">
					<tr id="33" style="display: " class=trcolor>
						<td colspan="4" class="tdc">
							<b><bean:write name="applyBean" property="title" /> </b>
						</td>
					</tr>
					<tr id="11" style="display: none" class=trcolor>
						<td colspan="4" class="tdc">
							<b><bean:write name="deptName" />
								<bean:write name="username" /> <bean:write name="date" />新增材料入库单</b>
						</td>
					</tr>
					<tr id="22" style="display: none" class=trcolor>
						<td colspan="4" class="tdc">
							<b><bean:write name="deptName" />
								<bean:write name="username" /> <bean:write name="date" />利旧材料入库单</b>
						</td>
					</tr>
					<tr id="33" style="display: none" class=trcolor>
						<td colspan="4" class="tdc">
							<b><bean:write name="deptName" />
								<bean:write name="username" /> <bean:write name="date" />自购材料入库单</b>
						</td>
					</tr>
					<tr class=trcolor>
						<td width="15%" class="tdr">
							<b>入库人:</b>
						</td>
						<td width="35%" class="tdl">
							<bean:write name="bean" property="creatorName" />
						</td>
						<td width="15%" class="tdr">
							<b>入库时间:</b>
						</td>
						<td width="35%" class="tdl">
							<bean:write name="bean" property="createDate" />
						</td>
					</tr>
					<tr class=trcolor>
						<td height="25" class="tdr">
							<b>材料来源:</b>
						</td>
						<td colspan="3" class="tdl">
							<html:hidden property="type" name="applyBean"/>
							<bean:define id="applyType" name="applyBean" property="type"
								type="java.lang.String" />
							<c:if test="${applyType=='1'}">新增材料</c:if>
							<c:if test="${applyType=='0'}">利旧材料</c:if>
							<c:if test="${applyType=='2'}">自购材料</c:if>
						</td>
					</tr>
					<tr class=trcolor>
						<td height="25" class="tdr">
							<b>用途信息:</b>
						</td>
						<td colspan="3" class="tdl">
							<bean:write name="applyBean" property="remark" />
						</td>
					</tr>
					<tr class=trcolor>
						<td class="tdr">
							<b>请选择材料:</b>
						</td>
						<td colspan="3" class="tdl">
						</td>
					</tr>
					<tr class=trcolor>
						<td colspan="4" class="tdl" style="padding: 10px;">
							<table width="100%" id="queryID" border="1" align="center"
								cellpadding="0" cellspacing="0"
								style="border-collapse: collapse;">
								<tr>
									<th width="15%" style="text-align:center">
										材料类型
									</th>
									<th width="15%" style="text-align:center">
										材料规格
									</th>
									<th width="15%" style="text-align:center">
										材料名称
									</th>
									<th width="10%" style="text-align:center">
										申请数量
									</th>
									<th width="10%" style="text-align:center">
										入库数量
									</th>
									<th width="10%" style="text-align:center">
										单位
									</th>
									<th width="15%" style="text-align:center">
										存放地址
									</th>
									<th width="10%" style="text-align:center">
										操作
									</th>
								</tr>
								<logic:notEmpty name="bean" property="count">
									<bean:define id="applyCount" name="bean" property="applyCount"
										type="java.lang.String[]" />
									<bean:define id="count" name="bean" property="count"
										type="java.lang.String[]" />
									<bean:define id="materialid" name="bean" property="materialId"
										type="java.lang.String[]" />
									<bean:define id="addressid" name="bean" property="addressId"
										type="java.lang.String[]" />
									<bean:define id="modelname" name="bean"
										property="materialModelId" type="java.lang.String[]" />
									<bean:define id="modelunit" name="bean" property="materialUnit"
										type="java.lang.String[]" />
									<bean:define id="typename" name="bean"
										property="materialTypeId" type="java.lang.String[]" />
									<%
										for (int i = 0; i < count.length; i++) {
									%>
									<tr id="rows<%=i%>">
										<td>
											<select name="typename" style="width: 150px"
												id="select1rows<%=i%>"
												onchange="onTypeSelected1(this.id,this.value)">
												<logic:present name="typeList">
													<option value="">
														----请选择----
													</option>
													<logic:iterate id="oneType" name="typeList">
														<logic:notEqual name="oneType" property="id"
															value="<%=typename[i] %>">
															<option value="<bean:write name="oneType" property="id"/>">
																<bean:write name="oneType" property="typename" />
															</option>
														</logic:notEqual>
														<logic:equal name="oneType" property="id"
															value="<%=typename[i] %>">
															<option value="<bean:write name="oneType" property="id"/>"
																selected="true">
																<bean:write name="oneType" property="typename" />
															</option>
														</logic:equal>
													</logic:iterate>
												</logic:present>
											</select>
										</td>
										<td>
											<select name="modelname" 
												id="select2rows<%=i%>" style="width: 150px"
												onchange="onModelSelected1(this.id,this.value)">
												<option value="">
													----请选择----
												</option>
												<logic:present name="modelList">
													<logic:iterate id="oneModel" name="modelList">
														<logic:equal name="oneModel" property="typeid"
															value="<%=typename[i] %>">
															<logic:notEqual name="oneModel" property="id"
																value="<%=modelname[i]%>">
																<option value="<bean:write name="oneModel" property="id"/>">
																	<bean:write name="oneModel" property="modelname" />
																</option>
															</logic:notEqual>
															<logic:equal name="oneModel" property="id"
																value="<%=modelname[i] %>">
																<option value="<bean:write name="oneModel" property="id"/>"
																	selected="true">
																	<bean:write name="oneModel" property="modelname" />
																</option>
															</logic:equal>
														</logic:equal>
													</logic:iterate>
												</logic:present>
											</select>
										</td>
										<td>
											<select name="materialId" 
												id="select3rows<%=i%>" style="width: 150px"
												onchange="onselectchange1(this.id,this.value)">
												<logic:present name="materialList">
													<option value="">
														----请选择----
													</option>
													<logic:iterate id="material" name="materialList">
														<logic:equal name="material" property="modelid"
															value="<%=modelname[i] %>">
															<logic:notEqual name="material" property="id"
																value="<%=materialid[i]%>">
																<option
																	value="<bean:write name="material" property="id"/>">
																	<bean:write name="material" property="name" />
																</option>
															</logic:notEqual>
															<logic:equal name="material" property="id"
																value="<%=materialid[i] %>">
																<option
																	value="<bean:write name="material" property="id"/>"
																	selected="true">
																	<bean:write name="material" property="name" />
																</option>
															</logic:equal>
														</logic:equal>
													</logic:iterate>
												</logic:present>
											</select>
										</td>
										<td>
											<%=applyCount[i]%>
											<input type="hidden" id="text_applyrows<%=i%>"
												value="<%=applyCount[i]%>" name="applyCount" size="6">
										</td>
										<td>
											<input type="text" id="textrows<%=i%>"
												value="<%=count[i]%>" name="count" size="6">
										</td>
										<td>
											<span id="unit_<%=i%>"><%=modelunit[i]%></span>
										</td>
										<td>
											<select name="addressId" id="selectaddrrows<%=i%>"
												 style="width: 150px">
												<option value="">
													----请选择----
												</option>
												<logic:present name="addressList">
													<logic:iterate id="address" name="addressList">
														<logic:notEqual name="address" property="id"
															value="<%=addressid[i] %>">
															<option
																value="<bean:write name="address" property="id"/>">
																<bean:write name="address" property="address" />
															</option>
														</logic:notEqual>
														<logic:equal name="address" property="id"
															value="<%=addressid[i] %>">
															<option
																value="<bean:write name="address" property="id"/>"
																selected="true">
																<bean:write name="address" property="address" />
															</option>
														</logic:equal>
													</logic:iterate>
												</logic:present>
											</select>
										</td>
										<td>
											<input type="button" value="删除"
												onclick="deleteRow1('rows<%=i%>')">
										</td>
									</tr>
									<%
										}
									%>
								</logic:notEmpty>
							</table>
						</td>
					</tr>
					<tr class=trcolor>
						<td colspan="4" class="tdl" style="padding: 10px"
							id="putStorageApproveInfoTd">
						</td>
					</tr>
					<tr class="trcolor">
						<apptag:approverselect inputName="approverId" label="审核人"
							inputType="radio" objectId="${bean.id}"
							objectType="LP_MT_STORAGE"></apptag:approverselect>
					</tr>
					<tr class="trcolor">
						<apptag:approverselect inputName="readerId" label="抄送人"
							notAllowName="approverId" spanId="readerSpan"
							objectId="${bean.id}" objectType="LP_MT_STORAGE"
							approverType="reader"></apptag:approverselect>
					</tr>
				</table>
			</center>
			<p align="center">
				<html:button property="action" styleClass="button"
					onclick="addRow()">添加新材料</html:button>
				<html:button property="action" styleClass="button" styleId="savesub"
					onclick="saveForm()">保存入库单</html:button>
				<html:button property="action" styleClass="button" styleId="addsub"
					onclick="checkForm()">提交入库单</html:button>
				<!-- <input type="button" class="button"
					onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"
					value="返回">  -->
					<input type="button" class="button" onclick="history.back()" value="返回"/>
			</p>
		</html:form>
		<script type="text/javascript">
		listApproveInfo();
		</script>
	</body>
</html>
