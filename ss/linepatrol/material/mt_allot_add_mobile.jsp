<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<script type="" language="javascript">
   
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

 // �����´�ά������ַ 
    function onnewconchange(){
 		rowid = this.id.substring(6,this.id.length);
		var selectValue = this.value;
        var addrID = document.getElementById("addrID");
        var addrid = document.getElementById("useadd"+rowid);
	    addrid.options.length=0;
        
		for(i = 0; i<addrID.length ;i++){
			if(addrID[i].conid != undefined){
				if(addrID[i].conid==selectValue){
 					addrid.options.add(new Option(addrID[i].text, addrID[i].value));
 				}
			}
        }      
   }

    // ���ݴ�ά������ַ ����ַ�������ϣ����ϼ��������Լ���������
    function onconchange(){
 		rowid = this.id.substring(6,this.id.length);
		var selectValue = this.value;
        var addrID = document.getElementById("addrID");
        var addrid = document.getElementById("select"+rowid);
	    addrid.options.length=0;
        
		for(i = 0; i<addrID.length ;i++){
			if(addrID[i].conid != undefined){
				if(addrID[i].conid==selectValue){
 					addrid.options.add(new Option(addrID[i].text, addrID[i].value));
 				}
			}
        }      
        var addrid = addrid.value;
        var selmt = document.getElementById("selmt"+rowid);
		selmt.options.length=0;
        var row=0;
        var materialID = document.getElementById("materialID");
		var contractorid = document.getElementById("oldcon"+rowid).value;
		for(i = 0; i<materialID.length ;i++){
			if(materialID[i].addrid != undefined){
				var conid = materialID[i].conid;
				if(materialID[i].addrid==addrid && conid==contractorid){//��ַ�������� 
					row++;
 					selmt.options.add(new Option(materialID[i].text, materialID[i].value));
					selmt.options[row-1].newstock=materialID[i].newstock;
				    selmt.options[row-1].oldstock=materialID[i].oldstock;
                    selmt.options[row-1].stockid=materialID[i].stockid;
                    selmt.options[row-1].newstockAddr=materialID[i].newstockAddr;
                    selmt.options[row-1].oldstockAddr=materialID[i].oldstockAddr;
                    selmt.options[row-1].mtstockid=materialID[i].mtstockid;
 				}
			}
        }      

	//���ݲ��ϼ���������������������
 		var tr;//�ж���
        for(i =0; i<$('queryID').rows.length;i++){
            if($('queryID').rows[i].id == rowid){
                tr = $('queryID').rows[i];
            }
		}
        var selmaterialid = selmt.value;
        var stockid = document.getElementById("stockid"+rowid);
        var newstockAddr = document.getElementById("newstockAddr"+rowid);
		var oldstockAddr = document.getElementById("oldstockAddr"+rowid);
        var mtstockid = document.getElementById("mtstockid"+rowid);
        if(selmaterialid==""){
			tr.cells[3].innerText ="ûѡ����";
       		tr.cells[4].innerText ="ûѡ����";
            newstockAddr.value="";
            oldstockAddr.value="";
		}
		for(m = 0; m<selmt.options.length ;m++){
			if(selmaterialid==selmt[m].value){
				 tr.cells[3].innerText =selmt[m].newstock;
       			 tr.cells[4].innerText =selmt[m].oldstock;
				 stockid.value=selmt[m].stockid;
                 newstockAddr.value=selmt[m].newstockAddr;
                 oldstockAddr.value=selmt[m].oldstockAddr;
                 mtstockid.value=selmt[m].mtstockid;
			}
		}
		//selmt.onchange = onselmtchange();
    }


    // ���ݵ�ַ��������
    function onAddrchange(){ 
        var materialID = document.getElementById("materialID");
        rowid = this.id.substring(6,this.id.length);
		var contractorid = document.getElementById("oldcon"+rowid).value;
		var selectValue = this.value;
        var selmt = document.getElementById("selmt"+rowid);
		selmt.options.length=0;
        var row=0;
		for(i = 0; i<materialID.length ;i++){
			if(materialID[i].addrid != undefined){
				var conid = materialID[i].conid;
				if(materialID[i].addrid==selectValue && conid==contractorid){
					row++;
              	 // selmt.options.length=row;
 					selmt.options.add(new Option(materialID[i].text, materialID[i].value));
					selmt.options[row-1].newstock=materialID[i].newstock;
				    selmt.options[row-1].oldstock=materialID[i].oldstock;
                    selmt.options[row-1].stockid=materialID[i].stockid;
                    selmt.options[row-1].newstockAddr=materialID[i].newstockAddr;
                    selmt.options[row-1].oldstockAddr=materialID[i].oldstockAddr;
                    selmt.options[row-1].mtstockid=materialID[i].mtstockid;
 				}
			}
        }      

	//���ݲ��ϼ���������������������
 		var tr;//�ж���
        for(i =0; i<$('queryID').rows.length;i++){
            if($('queryID').rows[i].id == rowid){
                tr = $('queryID').rows[i];
            }
		}
        var selmaterialid = selmt.value;
        var stockid = document.getElementById("stockid"+rowid);
        var newstockAddr = document.getElementById("newstockAddr"+rowid);
		var oldstockAddr = document.getElementById("oldstockAddr"+rowid);
        var mtstockid = document.getElementById("mtstockid"+rowid);
        if(selmaterialid==""){
			tr.cells[3].innerText ="ûѡ����";
       		tr.cells[4].innerText ="ûѡ����";
            newstockAddr.value="";
            oldstockAddr.value="";
		}
		for(m = 0; m<selmt.options.length ;m++){
			if(selmaterialid==selmt[m].value){
				 tr.cells[3].innerText =selmt[m].newstock;
       			 tr.cells[4].innerText =selmt[m].oldstock;
				 stockid.value=selmt[m].stockid;
                 newstockAddr.value=selmt[m].newstockAddr;
                 oldstockAddr.value=selmt[m].oldstockAddr;
                 mtstockid.value=selmt[m].mtstockid;
			}
		}
		//selmt.onchange = onselmtchange();
    }

 // ���ݲ��ϼ���������������������
    function onselmtchange(){
		rowid = this.id.substring(5,this.id.length);
		var selectValue = this.value;
        var selmt = document.getElementById("selmt"+rowid);
 		var tr;//�ж���
        for(i =0; i<$('queryID').rows.length;i++){
            if($('queryID').rows[i].id == rowid){
                tr = $('queryID').rows[i];
            }
		}
        var selmaterialid = selmt.value;	
        var stockid = document.getElementById("stockid"+rowid);
		var newstockAddr = document.getElementById("newstockAddr"+rowid);
		var oldstockAddr = document.getElementById("oldstockAddr"+rowid);
        var mtstockid = document.getElementById("mtstockid"+rowid);
		for(m = 0; m<selmt.options.length ;m++){
			if(selmaterialid==selmt[m].value){
				 tr.cells[3].innerText =selmt[m].newstock;
       			 tr.cells[4].innerText =selmt[m].oldstock;
				  stockid.value=selmt[m].stockid;
                  newstockAddr.value=selmt[m].newstockAddr;
                  oldstockAddr.value=selmt[m].oldstockAddr;
                  mtstockid.value=selmt[m].mtstockid;
			}
		}
   }

    //���һ������
    function addRow(){
        var  onerow=$('queryID').insertRow(-1);
        onerow.id = $('queryID').rows.length;

        var   cell9=onerow.insertCell(-1);
        var   cell1=onerow.insertCell(-1);
        var   cell2=onerow.insertCell(-1);
        var   cell3=onerow.insertCell(-1);
        var   cell4=onerow.insertCell(-1);
        var   cell10=onerow.insertCell(-1);
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

        var mtstockid = document.createElement("input");//����id
		mtstockid.name = "mtstockid"
        mtstockid.id = "mtstockid" + onerow.id;
        mtstockid.type="hidden";



        //����һ�������
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
		

		 //����һ��select
        var contractorid = document.getElementById("contractorid");
        var oldcon = document.createElement("select");//ԭ����ά
        oldcon.options.length = contractorid.length+1 ;
        oldcon.options[0].value = "";
        oldcon.options[0].text = "��ѡ��ά";
        for(i = 1; i<oldcon.options.length ;i++){
            oldcon.options[i].value = contractorid[i-1].value;
            oldcon.options[i].text = contractorid[i-1].text;
        }
        oldcon.name = "oldcontractorid";
        oldcon.id = "oldcon" + onerow.id;
        oldcon.onchange = onconchange;
        //oldcon.style.background="#C6D6E2"
        oldcon.style.font.size="12px";
        oldcon.style.width="110";
		cell9.appendChild(oldcon);

        var newcon = document.createElement("select");//�´�ά
        newcon.options.length = contractorid.length+1 ;
        newcon.options[0].value = "";
        newcon.options[0].text = "��ѡ��ά";
        for(i = 1; i<newcon.options.length ;i++){
            newcon.options[i].value = contractorid[i-1].value;
            newcon.options[i].text = contractorid[i-1].text;
        }
        newcon.name = "newcontractorid";
        newcon.id = "newcon" + onerow.id;
        newcon.onchange = onnewconchange;
        //newcon.style.background="#C6D6E2"
        newcon.style.font.size="12px";
        newcon.style.width="110";
        cell10.appendChild(newcon);//����

		//var addrID = document.getElementById("addrID");
        //����һ��select
        var s1 = document.createElement("select");//��ַselect
        s1.options.length = 1 ;
        s1.options[0].value = "";
        s1.options[0].text = "��ѡ��ַ";
        s1.name = "oldaddressid";
        s1.id = "select" + onerow.id;
        s1.onchange = onAddrchange;
        //s1.style.background="#C6D6E2"
        s1.style.font.size="12px";
        s1.style.width="120";

        //����ɾ����ť
        var b1 =document.createElement("input");
		b1.type="button";
        b1.value = "ɾ��";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;

        cell1.appendChild(s1);//����
        cell1.appendChild(stockid);
        cell1.appendChild(newstockAddr);
        cell1.appendChild(oldstockAddr);
        cell1.appendChild(mtstockid);
       
        //����һ��select
        var s2 = document.createElement("select");//����
        s2.options.length = 1;
        s2.options[0].value = "";
        s2.options[0].text = "��ѡ����";
       
        s2.name = "materialID";
 		s2.newstock = "";
 		s2.oldstock = "";
        s2.id = "selmt" + onerow.id;
        s2.onchange = onselmtchange;
        //s2.style.background="#C6D6E2"
        s2.style.font.size="12px";
        s2.style.width="110";

 		//����һ��select
        var s3 = document.createElement("select");//ʹ�õ�ַ
        s3.options.length = 1;
        s3.options[0].value = "";
        s3.options[0].text = "ѡ��ʹ�õ�ַ";        
        s3.name = "newaddressid";
        s3.id = "useadd" + onerow.id;
       // s3.onchange = onselectchange;
        //s3.style.background="#C6D6E2"
        s3.style.font.size="12px";
        s3.style.width="110";
      	
        cell2.appendChild(s2);//����
        cell3.innerText="ûѡ����";//����
        cell4.innerText="ûѡ����";//����
 		cell5.appendChild(s3);//����
		cell6.appendChild(t2);//����
 		cell7.appendChild(t1);//����
        cell8.appendChild(b1);
    }

  //�����Ƿ�����
    function valiD(){
        var mysplit = /^[1-9]\d{0,6}[\.]{0,4}\d{0,6}$|^-?0(\.\d*)?$/;
        var num = this.value;
        var num1 = parseFloat(num);
        if(mysplit.test(num)&& num>0){
			 var selid = this.id;
			 var rowid = this.id.substring(8,this.id.length);
			 var os = "oldstock"+rowid;
             var ns = "newstock"+rowid;
			 if(selid==os){
            	 var oldstockAddr = document.getElementById('oldstockAddr'+rowid).value; 
                 if(oldstockAddr==""){
					 alert("����ѡ�����!");
					 this.value=0;
           			 this.focus();
                  }
                 var os = parseFloat(oldstockAddr);
				 if(oldstockAddr>=0 && num1>os){
					alert("ֵ���ڿ������!");
					this.value=0
                    this.focus();
				 }
             }
             if(selid==ns){
                 var newstockAddr = document.getElementById('newstockAddr'+rowid).value;
				  if(newstockAddr==""){
					 alert("����ѡ�����!");
					 this.value=0;
           			 this.focus();
                  }
                   var ns = parseFloat(newstockAddr);
				 if(newstockAddr>=0 && num>ns){
					alert("ֵ���ڿ������!");
					this.value=0
                    this.focus();
				 }
             }

          return true;
        }
       if(!mysplit.test(num)){
            alert("����д�淶������!");
            this.value=0
            this.focus();
        }
    }
   
 
    //����ύ
    function toAddSub(){
	  var rownum = $('queryID').rows.length;
      if(rownum < 2){
          alert("�㻹û�����һ������,�����ύ!")
          return false;
      }
      var rowid;
	  for(i =1; i<rownum;i++){//�ж����ǲ��ǿ�����
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
				alert("��"+r+"�в��ϲ���Ϊ��!");
				return false;
			}
			if(useadd==""){
				alert("��"+r+"��ʹ�õ�ַ����Ϊ��!");
				return false;
			}
            if(oldstock==0&&newstock==0){
				alert("��"+r+"��ʹ�������Ͳ���Ϊ0!");
				return false;
			}
			var newin = parseFloat(newstock);
			var newout = parseFloat(newstockOut);
			var oldin = parseFloat(oldstock);
      	    var oldout = parseFloat(oldstockOut);
		    if(newin>newout || oldin>oldout){
				alert("��"+r+"�еĵ������ֵ���ڿ������!");
				return false;
		    }
			
			var materialID = document.getElementById('selmt'+rowid).value;
           // alert(materialID);
			var num = 0;
             var oldstockin = 0;
             var newstockin = 0;
 			for(m=1; m<rownum;m++){//�ж����ǲ��ǿ�����
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
					alert(addr+" "+mtname+" �������ɵ��ܺʹ������ɿ������!");
					return false;
				}
				if(parseFloat(newstockin)>newout){
					alert(addr+" "+mtname+" �����������ܺʹ��������������!");
					return false;
				}
			}
			
	 }
	var addsub = document.getElementById("addsub");
	addsub.disabled=true;
	addForm.submit();
    }

 
</script>
		<title>���ϵ�����</title>
	</head>
	<style type="text/css">
.subject {
	text-align: center;
}
</style>
	<body>
		<!--��ʾ���뵥��ϸ��Ϣ-->
		<select name="contractorid" style="display: none" id="contractorid">
			<logic:present scope="request" name="cons">
				<logic:iterate id="r" name="cons">
					<option value="<bean:write name="r" property="contractorid" />">
						<bean:write name="r" property="contractorname" />
					</option>
				</logic:iterate>
			</logic:present>
		</select>
		<select name="addrID" style="display: none" id="addrID">
			<logic:present scope="request" name="address">
				<logic:iterate id="r" name="address">
					<option value="<bean:write name="r" property="id" />"
						conid="<bean:write name="r" property="contractorid" />">
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
						stockid="<bean:write name="r" property="stockid" />"
						mtstockid="<bean:write name="r" property="mtstockid" />"
						conid="<bean:write name="r" property="conid" />"
						addr="<bean:write name="r" property="address" />"
						mtname="<bean:write name="r" property="name" />">
						<bean:write name="r" property="name" />
					</option>
				</logic:iterate>
			</logic:present>
		</select>
		<br />
		<template:titile value="���ϵ�����" />
		<html:form action="/materialAllotAction.do?method=saveMaterialsAllot"
			styleId="addForm">
			<center>
				<table width="97%" border="1" cellpadding="0" cellspacing="0"
					style="border-collapse: collapse;" class="tabout">
					<tr class=trcolor>
						<td colspan="2" class="tdl" style="padding: 10px;">
							<table width="100%" id="queryID" border="1" align="center"
								cellpadding="0" cellspacing="0"
								style="border-collapse: collapse;">
								<tr>
									<th width="10%" align="center">
										������ά
									</th>
									<th width="10%" align="center">
										������ַ
									</th>
									<th width="12%" align="center">
										��������
									</th>
									<th width="10%" align="center">
										�������
									</th>
									<th width="10%" align="center">
										���ɿ��
									</th>
									<th width="10%" align="center">
										�����ά
									</th>
									<th width="12%" align="center">
										�����ַ
									</th>
									<th width="10%" align="center">
										�����������
									</th>
									<th width="10%" align="center">
										�������ɿ��
									</th>
									<th width="8%" align="center">
										����
									</th>
								</tr>
							</table>
						</td>
					</tr>
					<tr class=trcolor>
						<td height="25" width="15%" class="tdr">
							<b>��ע��Ϣ:</b>
						</td>
						<td class="tdl">
							<html:textarea property="remark" styleClass="textarea"
								style="width:500px;" rows="5">
							</html:textarea>
						</td>
					</tr>
				</table>
				<p align="center">
					<html:button property="action" styleClass="button"
						onclick="addRow()">����²���</html:button>
					<html:button property="action" styleClass="button" styleId="addsub"
						onclick="toAddSub()">�ύ</html:button>
				</p>
			</center>
		</html:form>
	</body>
</html>
