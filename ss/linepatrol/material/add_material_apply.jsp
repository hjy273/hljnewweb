<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link href="${ctx}/css/linepatrol_css.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript" src="${ctx}/js/prototype.js"></script>
<html>
	<head>
	<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
	</head>

	<script type="text/javascript">
		var win;
		function showWin(){
			win = new Ext.Window({
				layout : 'fit',
				width:600,
				height:400,
				resizable:true,
				autoScroll:true,
				closeAction : 'close',
				modal:true,
				html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="${ctx}/linepatrol/material/import_material.jsp" />',
				plain: true
			});
			win.show(Ext.getBody());
		}
		function close(){
			win.close();
		}
  var rowArr = new Array();
  var infoArr = new Array();//���в��ϵ���Ϣ
  var addressArr = new Array();//���е�ַ����Ϣ
  
  //��ʼ������
    function initArray1(id,typename){
        rowArr[0] = id;
        rowArr[1] = typename;
        infoArr[infoArr.length] = rowArr;
        rowArr = new Array();
        return true;
    }
    
    //��ʼ������
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
    
    function typecallback(originalRequest) {
		var ops =  document.getElementById("select2"+rowid);
		ops.options.length = 0;
		var rst = originalRequest.responseText;
		var linearr = eval('('+rst+')');
		if(linearr.length == 0) {
			alert("��������û�й��!");
			return;
		}
	
		ops.options.add(new Option('----��ѡ��----',''));
		for(var i = 0; i < linearr.length; i++) {
			var option=new Option(linearr[i].modelname,linearr[i].id);
			option.varUnit=linearr[i].unit;
			ops.options.add(option);
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
    
    function onModelSelected(){
        rowid = this.id.substring(7,this.id.length);
        var index=this.options.selectedIndex;
    	var selectValue = this.value;
    	var url = 'material_apply.do?method=getMaterials&id=' + selectValue;
    	document.getElementById("unit_"+rowid).innerText=this.options[index].varUnit;
		var myAjax = new Ajax.Request(url, {method:"post", onComplete:modelcallback, asynchronous:true});
    }
    
    function modelcallback(originalRequest) {
		var ops =  document.getElementById("select3"+rowid);
		ops.options.length = 0;
		var rst = originalRequest.responseText;
		var linearr = eval('('+rst+')');
		if(linearr.length == 0) {
			alert("�ù����û�в���!");
			return;
		}

		ops.options.add(new Option('----��ѡ��----',''));
		for(var i = 0; i < linearr.length; i++) {
			ops.options.add(new Option(linearr[i].name,linearr[i].id));
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
				    
  function onselectchange(){
    rowid = this.id.substring(7,this.id.length);
	var selectValue = this.value;
       var tmpValue;
       var tr;//�ж���
       for(i =0; i<$('queryID').rows.length;i++){
           if($('queryID').rows[i].id == rowid){
               tr = $('queryID').rows[i];
           } else {
			if(document.getElementById("select3"+$('queryID').rows[i].id) != null ){
           		tmpValue = document.getElementById("select3"+$('queryID').rows[i].id).value;
				if(tmpValue == selectValue) {
					alert("�ò����Ѿ�ѡ����ˣ�������ѡ��������ϣ�");
					document.getElementById("select3"+rowid).value = "";
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
          for(i =0; i<$('queryID').rows.length;i++){
            if($('queryID').rows[i].id == rowid){
                $('queryID').deleteRow($('queryID').rows[i].rowIndex);
            }
        }
    }
    
    function loadImportMaterial(){
		var myAjax=new Ajax.Updater(
			"applyMaterialTb","${ctx}/linepatrol/material/import_data_list.jsp?method_name=deleteRow",{
				method:"post",
				evalScripts:true
			}
		);
    }
    
     //���һ������
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

        //����һ�������
        var t1 = document.createElement("input");
        t1.name = "count"
        t1.id = "text" + onerow.id;
        t1.value= "0";
        t1.maxLength = "6";
        t1.size = "6";
        t1.onblur=valiD;
        //t1.style.background="#C6D6E2"
        t1.style.font.size="12px";


        //����һ��select
        var s1 = document.createElement("select");
        s1.options.length = infoArr.length + 1;
        s1.options[0].value = "";
        s1.options[0].text = "��ѡ���������";
        for(i = 1; i<s1.options.length ;i++){
            s1.options[i].value = infoArr[i-1][0];
            s1.options[i].text = infoArr[i-1][1];
        }
        s1.name = "typename";
        s1.id = "select1" + onerow.id;
        s1.onchange = onTypeSelected;
        //s1.style.background="#C6D6E2"
        s1.style.font.size="12px";
        s1.style.width="130px";
        
        //����һ��select
        var s3 = document.createElement("select");
        s3.options.length = infoArr.length + 1;
        s3.options[0].value = "";
        s3.options[0].text = "��ѡ����Ϲ��";
        s3.options[0].varUnit="";
        s3.name = "modelname";
        s3.id = "select2" + onerow.id;
        s3.onchange = onModelSelected;
        //s3.style.background="#C6D6E2"
        s3.style.font.size="12px";
        s3.style.width="130px";
        
        //����һ��select
        var s4 = document.createElement("select");
        s4.options.length = infoArr.length + 1;
        s4.options[0].value = "";
        s4.options[0].text = "��ѡ���������";
        s4.name = "materialId";
        s4.id = "select3" + onerow.id;
        s4.onchange = onselectchange;
        //s4.style.background="#C6D6E2"
        s4.style.font.size="12px";
        s4.style.width="130px";
        
        var s5 = document.createElement("span");
        s5.id = "unit_" + onerow.id;
        s5.style.font.size="12px";
      
        var s2 =  document.createElement("select");
         s2.options.length = addressArr.length + 1;
         s2.options[0].value = "";
        s2.options[0].text = "��ѡ���ŵ�ַ";
          for(i = 1; i<s2.options.length ;i++){
            s2.options[i].value = addressArr[i-1][0];
            s2.options[i].text = addressArr[i-1][1] ;
        }
        s2.name = "addressId";
        s2.id = "selectaddr" + onerow.id;
        //s2.style.background="#C6D6E2"
        s2.style.font.size="12px";
        s2.style.width="130px";

        //����ɾ����ť
        var b1 =document.createElement("input");
        b1.type="button";
        b1.value = "ɾ��";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;

        cell1.appendChild(s1);//����
        cell2.appendChild(s3);//����
        cell3.appendChild(s4);//����
        cell4.appendChild(t1);
        cell5.appendChild(s5);//����
        cell6.appendChild(s2);
        cell7.appendChild(b1);
    }
    
    function deleteRow1(value){
      	
        rowid = value;
      
       
      	for(i =0; i<$('queryID').rows.length;i++){
      	   
        	if($('queryID').rows[i].id == rowid){
            	$('queryID').deleteRow($('queryID').rows[i].rowIndex);
               
            }
        }
    }
    
    //�����Ƿ�����
    function valiD(){
         var mysplit = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
        if(mysplit.test(this.value)){
          return true;
        }
        else{
            alert("����д�Ĳ�������,����������");
            this.value=0
            this.focus();

        }
    } 
    
    function saveForm(button){
       var myform = document.forms[0];
       myform.isSubmited.value="0";
       button.disabled = "disabled";
       myform.submit();
    }
    
    function checkForm(button){
       var myform = document.forms[0];
       myform.isSubmited.value="1";
       if($('queryID').rows.length < 2){
          alert("�㻹û��ѡ�����,�����ύ!")
        return false;
      }
      if(myform.approverId.value==""){
      	alert("û��ѡ������ˣ�");
      	return false;
      }
      for(i =0; i<$('queryID').rows.length;i++){
      		if(document.getElementById("select3"+$('queryID').rows[i].id) != null ){
            		tmpValue = document.getElementById("select3"+$('queryID').rows[i].id).value;
					if(tmpValue==''){
					  alert('��ѡ�����');
					  return false;
					}
				}
			if(document.getElementById("selectaddr"+$('queryID').rows[i].id) != null ){
            		tmpValue = document.getElementById("selectaddr"+$('queryID').rows[i].id).value;
					if(tmpValue==''){
					  alert('��ѡ���ַ');
					  return false;
					}
				}	
				if(document.getElementById("text"+$('queryID').rows[i].id) != null ){
            		tmpValue = document.getElementById("text"+$('queryID').rows[i].id).value;
					if(tmpValue=='0'){
					  alert('������������Ϊ0');
					  return false;
					}
				}	
      }
      
      button.disabled = "disabled";
       myform.submit();
    }
    
    function reName(value,deptname,username,date){
      var titlevalue = deptname+username+date;
      if(value== '1'){
         document.getElementById("11").style.display="";     
         document.getElementById("22").style.display="none"; 
         document.getElementById("33").style.display="none"; 
         titlevalue += '����';    
      }else if(value== '2'){
      	 document.getElementById("11").style.display="none";     
         document.getElementById("22").style.display="none"; 
         document.getElementById("33").style.display=""; 
         titlevalue += '�Թ�';
      }else{
         document.getElementById("11").style.display="none";  
         document.getElementById("22").style.display="";     
         document.getElementById("33").style.display="none"; 
         titlevalue += '����'; 
      }
      titlevalue += '�������뵥'; 
       //alert(titlevalue);
       document.getElementById("title").value=titlevalue;
    }
  </script>

	<body>
		<template:titile value="��д�������뵥" />
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
		<html:form action="/material_apply.do?method=addMaterialApply"
			styleId="materialApplyBean">
			<input type="hidden" name="userid" value="11" />
			<input type="hidden" name="isSubmited" value="1" />
			<center>
			<table width="90%" border="0" cellpadding="0"
				cellspacing="0" class="tabout">
				<input type="hidden" id="title" name="title"
					value="<bean:write name="deptName"/><bean:write name="username"/><bean:write name="date"/>�����������뵥">
				<tr id="11" style="display: " class=trcolor>
					<td colspan="4" class="tdc">
						<b><bean:write name="deptName" /> <bean:write name="username" />
							<bean:write name="date" />�����������뵥</b>
					</td>
				</tr>
				<tr id="22" style="display: none" class=trcolor>
					<td colspan="4" class="tdc">
						<b><bean:write name="deptName" /> <bean:write name="username" />
							<bean:write name="date" />���ɲ������뵥</b>
					</td>
				</tr>
				<tr id="33" style="display: none" class=trcolor>
					<td colspan="4" class="tdc">
						<b><bean:write name="deptName" /> <bean:write name="username" />
							<bean:write name="date" />�Թ��������뵥</b>
					</td>
				</tr>
				<tr class="trcolor">
					<td width="15%" class="tdr">
						<b>�� �� ��:</b>
					</td>
					<td width="35%" class="tdl">
						<bean:write name="username" />
					</td>
					<td width="15%" class="tdr">
						<b>����ʱ��:</b>
					</td>
					<td width="35%" class="tdl">
						<bean:write name="date" />
					</td>
				</tr>
				<tr class="trcolor">
					<td height="25" class="tdr">
						<b>������Դ:</b>
					</td>
					<td colspan="3" class="tdl">
						<input type="radio" name="type" value="1" checked="true"
							onclick="reName(this.value,'<bean:write name="deptName"/>','<bean:write name="username"/>','<bean:write name="date"/>')">
						��������
						<input type="radio" name="type" value="0"
							onclick="reName(this.value,'<bean:write name="deptName"/>','<bean:write name="username"/>','<bean:write name="date"/>')">
						���ɲ���
						<input type="radio" name="type" value="2"
							onclick="reName(this.value,'<bean:write name="deptName"/>','<bean:write name="username"/>','<bean:write name="date"/>')">
						�Թ�����
					</td>
				</tr>
				<tr class="trcolor">
					<td height="25" class="tdr">
						<b>��;��Ϣ:</b>
					</td>
					<td colspan="3" class="tdl">
						<html:textarea property="remark" styleClass="inputtextarea"
							style="width:85%;" value=""></html:textarea>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdr">
						<b>��ѡ�����:</b>
					</td>
					<td colspan="3" class="tdl">
					</td>
				</tr>
				<tr class="trcolor">
					<td colspan="4" class="tdl" style="padding:10px;">
						<table width="100%" id="queryID" border="1" align="center"
							cellpadding="0" cellspacing="0"
							style="border-collapse: collapse;">
							<tr>
								<th width="15%" style="text-align:center">
									��������
								</th>
								<th width="20%" style="text-align:center">
									���Ϲ��
								</th>
								<th width="20%" style="text-align:center">
									��������
								</th>
								<th width="10%" style="text-align:center">
									��������
								</th>
								<th width="10%" style="text-align:center">
									��λ
								</th>
								<th width="15%" style="text-align:center">
									��ŵ�ַ
								</th>
								<th width="10%" style="text-align:center">
									����
								</th>
							</tr>
							<tbody id="applyMaterialTb" style="display:"></tbody>
						</table>
					</td>
				</tr>
				<tr class="trcolor">
					<apptag:approverselect inputName="approverId" label="�����"
						inputType="radio"></apptag:approverselect>
				</tr>
				<tr class="trcolor">
					<apptag:approverselect inputName="readerId" label="������"
						notAllowName="approverId" spanId="readerSpan"></apptag:approverselect>
				</tr>
			</table>
			</center>
			<p align="center">
				<html:button property="action" styleClass="button"
					onclick="addRow()">����²���</html:button>
				<html:button property="action" styleClass="button"
					onclick="showWin()">�������</html:button>
				<html:button  property="action" styleClass="button" styleId="savesub" 
					onclick="saveForm(this)">�������뵥</html:button>
				<html:button property="action" styleClass="button" styleId="addsub"
					onclick="checkForm(this)">�ύ���뵥</html:button>
				<!--<html:button property="action" styleClass="button" styleId="upId" onclick="showupload()">�������뵥</html:button>
        <html:button property="action" styleClass="button2" onclick="downloadFile()">���뵥ģ������</html:button>
        <html:button property="action" styleClass="button" onclick="addGoBack()">����</html:button>  -->
			</p>
		</html:form>

	</body>
</html>
