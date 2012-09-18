<%@include file="/common/header.jsp"%>

<html>
<head>
<script type="" language="javascript">
	var rowArr = new Array();//һ�в��ϵ���Ϣ
    var infoArr = new Array();//���в��ϵ���Ϣ

    //��ʼ������
    function initArray(id,name,unit,type,essenumber){
    	rowArr[0] = id;
        rowArr[1] = name;
        rowArr[2] = unit;
        rowArr[3] = type;
        rowArr[4] = essenumber;
        infoArr[infoArr.length] = rowArr;
        rowArr = new Array();
        return true;
    }
    //�ű����ɵ�ɾ����  ť��ɾ������
    function deleteRow(){
      	//��ð�ť�����е�id
     	var rowid = "000";
        rowid = this.id;
        rowid = rowid.substring(1,rowid.length);
        //��idת��Ϊ�������е�����,��ɾ��
      	for(i =0; i<queryID.rows.length;i++){
        	if(queryID.rows[i].id == rowid){
            	queryID.deleteRow(queryID.rows[i].rowIndex);
            }
        }
    }
    //�ű����ɵ�select����onselectchange
    function onselectchange(){
      	rowid = this.id.substring(6,this.id.length);
        var tr;//�ж���
        for(i =0; i<queryID.rows.length;i++){
        	if(queryID.rows[i].id == rowid){
            	tr = queryID.rows[i];
            }
        }
        //�ҳ�ѡ���������Ӧ��������Ϣ
        var unit;
        var type;
        for(i = 0; i < infoArr.length; i++){
        	if(this.value == infoArr[i][0]){
            	unit = infoArr[i][2];
                if(type == null || type =="")
                	type = "��";
                else
                	type = infoArr[i][3]
            }
        }
        //д����
        tr.cells[2].innerText =unit;
        tr.cells[3].innerText =type;
    }

    //���һ������
    function addRow(){
    	var  onerow=queryID.insertRow(-1);
        onerow.id = queryID.rows.length;

        var   cell1=onerow.insertCell();
		var   cell2=onerow.insertCell();
		var   cell3=onerow.insertCell();
		var   cell4=onerow.insertCell();
        var   cell5=onerow.insertCell();

		//����һ�������
        var t1 = document.createElement("input");
        t1.name = "enumber"
		t1.id = "text" + onerow.id;
        t1.value= "0";
        t1.maxLength = "6";
        t1.size = "6";
        t1.onblur=valiD;

        //����һ��select
        var s1 = document.createElement("select");
        s1.options.length = infoArr.length + 1;
        s1.options[0].value = "";
        s1.options[0].text = "��ѡ�����ⱸ��";
        for(i = 1; i<s1.options.length ;i++){
        	s1.options[i].value = infoArr[i-1][0];
            s1.options[i].text = infoArr[i-1][1];
        }
        s1.name = "id";
        s1.id = "select" + onerow.id;
        s1.onchange = onselectchange;
        s1.Class="inputtext"

        //����ɾ����ť
        var b1 =document.createElement("button");
        b1.value = "ɾ���ñ���";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
        //alert(b1.id + "rrr");

        cell1.appendChild(s1);//����
        cell2.appendChild(t1);//����
        cell3.innerText="��û��ѡ�񱸼�";//����
        cell4.innerText="��û��ѡ�񱸼�";//����
        cell5.appendChild(b1);
	}
    //���һ������,����ҳ���õ�
    function addRowForRev(){
    	var  onerow=queryID.insertRow(-1);
        onerow.id = queryID.rows.length;

        var   cell1=onerow.insertCell();
		var   cell2=onerow.insertCell();
		var   cell3=onerow.insertCell();
		var   cell4=onerow.insertCell();
        var   cell5=onerow.insertCell();

		//����һ�������
        var t1 = document.createElement("input");
        t1.name = "enumber"
		t1.id = "text" + onerow.id;
        t1.value= "0";
        t1.maxLength = "6";
        t1.size = "6";
        t1.onblur=valiDForRev;

        //����һ��select
        var s1 = document.createElement("select");
        s1.options.length = infoArr.length + 1;
        s1.options[0].value = "";
        s1.options[0].text = "��ѡ������ϱ���";
        for(i = 1; i<s1.options.length ;i++){
        	s1.options[i].value = infoArr[i-1][0];
            s1.options[i].text = infoArr[i-1][1];
        }
        s1.name = "id";
        s1.id = "select" + onerow.id;
        s1.onchange = onselectchange;
        s1.Class="inputtext"

        //����ɾ����ť
        var b1 =document.createElement("button");
        b1.value = "ɾ���ñ���";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
        //alert(b1.id + "rrr");

        cell1.appendChild(s1);//����
        cell2.appendChild(t1);//����
        cell3.innerText="��û��ѡ�񱸼�";//����
        cell4.innerText="��û��ѡ�񱸼�";//����
        cell5.appendChild(b1);
	}
    //����ύ
    function toAddSub(){
      if(queryID.rows.length<2){
      	alert("�㻹û��ѡ�񱸼�,�����ύ!")
        return false;
      }
    	addForm.submit();
    }
  //�����Ƿ�����
    function valiD(){
    	var mysplit = /^\d{1,6}$/;
        if(mysplit.test(this.value)){
          return true;
        }
        else{
        	alert("����д�Ĳ�������,����������");
            this.focus();
            this.value=0;
        }
    }
    function valiDForRev(){
    	var mysplit = /^\d{1,6}$/;
        if(!mysplit.test(this.value)){
        	alert("����д�Ĳ�������,����������");
            this.focus();
            this.value=0;
            return false;
        }
        var seId = "select" +(this.id).substring(4,this.id.length);
        var selObj = document.getElementById(seId);  //�õ�select��ѡ����ϵĶ���
        var oldnumber =0;
        for(i=0;i<infoArr.length;i++){
        	if(selObj.value == infoArr[i][0]){
            	oldnumber = infoArr[i][4];
                break;
            }
        }
        if(parseInt(this.value) > parseInt(oldnumber)){
        	alert("Ŀǰ�����" + oldnumber + ",���ܱ�����ô��!!");
            this.focus();
            this.value=0;
            return false;
        }
        return true;
    }

   //��ʾҳ����ϸ��Ϣ��ť����(ruku)
   function toGetForm(idValue){


     	var url = "${ctx}/ToolStockAction.do?method=showOneInfo&stockid=" + idValue;
        self.location.replace(url);
    }
 	//��ӷ���toGoBackForRevoke()
    function addGoBack(){
    	try{
            	location.href = "${ctx}/ToolStockAction.do?method=showAllStock";
                return true;
            }
            catch(e){
            	alert(e);
            }
    }
     function addGoBackFOrRev(){
    	try{
            	location.href = "${ctx}/ToolStockAction.do?method=showAllRevoke";
                return true;
            }
            catch(e){
            	alert(e);
            }
    }
     function toGoBackForRevoke(){
    	try{
            	location.href = "${ctx}/ToolStockAction.do?method=showAllRevoke";
                return true;
            }
            catch(e){
            	alert(e);
            }
    }
	function toGetFormForRevoke(idValue){
     	var url = "${ctx}/ToolStockAction.do?method=showOneRevoke&stockid=" + idValue;
        self.location.replace(url);
    }






    //ɾ��ȷ��
    function toDeletForm(idValue){
    	if(confirm("��ȷ��Ҫִ�д˴�ɾ��������?")){
        	//var url = "${ctx}/PartBaseInfoAction.do?method=deletePartBaseInfo&id=" + idValue;
            self.location.replace(url);
         }
        else
        	return false;
    }
	//��ʾҳ��ת���޸�ҳ��
    function toUpForm(idValue){
    	var url = "${ctx}/PartRequisitionAction.do?method=upshow&reid=" + idValue;
        self.location=url;
    }

    //�޸�ҳ��ͨ���������� ��ɾ����ťɾ������
    function upDelOnClick(id){
     var trid = id.substring(1,id.length);
     var trobj = document.getElementById(trid);
     queryID.deleteRow(trobj.rowIndex);
    }
    //�޸�ҳ����ʾ���еĲ���select����
    function upselectOncheng(id){
    	var trid = id.substring(4,id.length);
     	var trobj = document.getElementById(trid);//�ж���
        var seleobj = document.getElementById(id);
        //alert(seleobj.selectedIndex);//seleobj.selectedIndex����ѡ���ѡ������ֵ
        for(i = 0; i< infoArr.length;i++){
        	if(seleobj.options[seleobj.selectedIndex].value == infoArr[i][0]){
            	trobj.cells[2].innerText = infoArr[i][2];
        		trobj.cells[3].innerText = infoArr[i][3];
                //alert (infoArr[i][3]);
            }
        }
        return true;
    }



    //�޸�ҳ���ύ����
    function toUpSub(){
    	if(queryID.rows.length<2){
	      	alert("��ѱ���ɾû��,�����ύ!")
	        return false;
	      }
      if(confirm("��ȷ��Ҫִ�д˴��޸Ĳ�����?")){
        	UpForm.submit();
         }
        else
        	return false;
    }
	//��ʾҳ��ɾ����ť����
     function toDelForm(idValue){
     	if(confirm("��ȷ��Ҫɾ����?")){
        	var url = "${ctx}/PartRequisitionAction.do?method=delOneReqInfo&reid=" + idValue;
        	self.location.replace(url);
         }
        else
        	return ;
    }
 //ѡ������
    function GetSelectDateTHIS(strID) {
    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    	return ;
	}



    //�����Ƿ�����
    function valiDigit(id){
    	var obj = document.getElementById(id);
      	var mysplit = /^\d{1,6}$/;
        if(mysplit.test(obj.value)){
          return true;
        }
        else{
        	alert("����д�Ĳ�������,����������");
            obj.focus();
            obj.value=0;
            return false;
        }
    }

   function toExportStock(){

     var userid = queryForm2.userid.value;
     var begintime = queryForm2.begintime.value;
     var endtime = queryForm2.endtime.value;
     var url = "${ctx}/ToolStockAction.do?method=exportStockList&userid="+userid+"&begintime="+begintime+"&endtime="+endtime;
     self.location.replace(url);

   }
   function toExportRevoke(){

     var userid = queryForm2.userid.value;
     var remark = queryForm2.remark.value;
     var begintime = queryForm2.begintime.value;
     var endtime = queryForm2.endtime.value;
     var url = "${ctx}/ToolStockAction.do?method=exportRevokeList&userid="+userid+"&remark="+remark+"&begintime="+begintime+"&endtime="+endtime;
     self.location.replace(url);

   }
</script>


<title>

</title>
</head>
<body>

	<!--��д��ⵥ-->
	<logic:equal value="2"  name="type" scope="session" >
    	<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
        <logic:present name="baseinfo">
        	<logic:iterate id="baseinfoId" name="baseinfo">
            	<script type="" language="javascript">
                	initArray("<bean:write name="baseinfoId" property="id"/>","<bean:write name="baseinfoId" property="name"/>","<bean:write name="baseinfoId" property="unit"/>","<bean:write name="baseinfoId" property="type"/>");
                </script>
        	</logic:iterate>
        </logic:present>
      	<div id="partDivId"  >
	        <br/>
	        <template:titile value="��д������ⵥ"/>
	        <html:form action="/ToolStockAction?method=addStock" styleId="addForm">
			    <input  type="hidden" name="contractorid" value="<bean:write name="deptid"/>"/>
	          	<input type="hidden" name="userid" value="<bean:write name="userid" />"/>
	            <table align="center" width="600" border="0">
		        	<tr >
		            	<td width="100" height="25"><b>��λ����:</b></td>
	                    <td align="left" width="100"><bean:write name="deptname"/></td>
	                    <td  align="right" width="100"><b>�� �� ��:</b></td>
	                    <td align="left" width="80"><bean:write name="username"/></td>
						<td width="100" align="right"><b>���ʱ��:</b></td>
	                    <td align="left" width="120"><bean:write name="date"/></td>
		            </tr>
                    <!--
	                <tr>
	                	<td ><b>��ŵص�:</b></td>
	                    <td ><html:text property="adress" styleClass="inputtext" style="width:100;" maxlength="50" ></html:text> </td>
                        <td align="right"><b>�� �� ��:</b></td>
	                    <td >
                        	<input type="text" name="patrolid" class="inputtext" style="width:80"/>
                          <select name="patrolid" class="selecttext">
                            	<logic:iterate id="patrolId" name="patrol">
                                	<option value="<bean:write name="patrolId" property="patrolid"/>"><bean:write name="patrolId" property="patrolname"/></option>
                            	</logic:iterate>
                        	</select>
                        </td>
                        <td width="100" align="right"><b>���ʱ��:</b></td>
	                    <td align="left" width="120"><bean:write name="date"/></td>
	                </tr>-->
                     <tr>

	                </tr>
	                <tr>
	                	<td height="25"><b>��ע��Ϣ:</b></td>
	                    <td colspan="5"><html:text property="remark" styleClass="inputtext" style="width:500;" maxlength="510" value="����д�������ı�ע��Ϣ."></html:text></td>
	                </tr>
                    <tr>
						<td colspan="6"><hr /><br> ��ѡ����ⱸ��:</td>
	                </tr>

		        </table>

		            <table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
		            	<tr>
		                	<th  width="25%" class="thlist" align="center">��������</th>
		                    <th  width="15%" class="thlist" align="center">�������</th>
		                    <th  width="20%" class="thlist" align="center">������λ</th>
		                    <th  width="25%" class="thlist" align="center">����ͺ�</th>
		                    <th  width="15%"class="thlist" align="center">����</th>
		            	</tr>
		        	</table>
                    <p align="center">
		                          <html:button property="action" styleClass="button" onclick="addRow()">����±���</html:button>
					       	 	<html:button property="action" styleClass="button"  onclick="toAddSub()">�ύ��ⵥ</html:button>
					       	 	<html:button property="action" styleClass="button" onclick="addGoBack()" >����	</html:button>
						     </p>

	        </html:form>
         </div>
	</logic:equal>

   <!--��ʾ��ⵥ-->
    <logic:equal value="1" name="type" scope="session" >
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
        <br />
            <template:titile value="������ⵥһ����" />
            <!--
                display:column property="patrolid" title="�� �� ��" maxLength="20"align="center"/>
                display:column property="adress" title="��ŵص�" maxLength="10"/>-->
    		<display:table name="sessionScope.stockinfo" id="currentRowObject"  pagesize="18">

            	<display:column property="contractorname" title="��ⵥλ" maxLength="10" align="center"/>
                <display:column property="username" title="�� �� ��" maxLength="20"align="center"/>
                <display:column property="time" title="���ʱ��"maxLength="20"align="center"/>
                <display:column media="html" title="����" >
	                 <%
						    BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
						    String id = (String) object.get("stockid");

	            		%>
	            	 <a href="javascript:toGetForm('<%=id%>')">��ϸ</a>
			  	</display:column>
    		</display:table>
            <html:link action="/ToolStockAction?method=exportStockResult">����ΪExcel�ļ�</html:link>
    </logic:equal>

<!--��ʾ���뵥��ϸ��Ϣ-->
    <logic:equal value="10"   scope="session"  name="type">
            <br />
      	<template:titile value="������ⵥ��ϸ��Ϣ" />
            <table align="center" width="600" border="0">
	          		<tr >
		            	<td width="100" height="25"><b>��λ����:</b></td>
	                    <td align="left" width="100"><bean:write name="stockinfo" property="factory"/></td>
	                    <td  align="right" width="100"><b>������:</b></td>
	                    <td align="left" width="80"><bean:write name="stockinfo" property="useid"/></td>
						<td width="100" align="right"><b>���ʱ��:</b></td>
	                    <td align="left" width="120"><bean:write name="stockinfo" property="time"/></td>
		            </tr>
                    <!--
	                <tr>
	                	<td ><b>��ŵص�:</b></td>
	                    <td ><bean:write name="stockinfo" property="adress"/> </td>
                        <td align="right"><b>�� �� ��:</b></td>
	                    <td >
                        	<bean:write name="stockinfo" property="patrolname"/>
                        </td>
                        <td width="100" align="right"><b>���ʱ��:</b></td>
	                    <td align="left" width="120"><bean:write name="stockinfo" property="time"/></td>
	                </tr>-->
                     <tr>

	                </tr>
	                <tr>
	                	<td height="25"><b>��ע��Ϣ:</b></td>
	                    <td colspan="5"><bean:write name="stockinfo" property="remark"/></td>
	                </tr>
                     <tr>
                            	<td colspan="6"><hr /></td>
                    </tr>
	                <tr>
	                	<td colspan="6"   valign="bottom">
                        	<b>����ⵥ�����ı���:</b>
                        </td>
	                </tr>
		        </table>
                <table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
	            	<tr>
	                	<th  width="25%" class="thlist" align="center">��������</th>
	                    <th  width="15%" class="thlist" align="center">�������</th>
                        <th  width="20%" class="thlist" align="center">������λ</th>
	                    <th  width="25%" class="thlist" align="center">����ͺ�</th>

	            	</tr>
                    <logic:present name="toolsinfo">
                    	<logic:iterate id="toolsinfoid" name="toolsinfo">
                        	<tr >
                            	<td>
                                  	<bean:write name="toolsinfoid" property="name"/>
                                </td>
                                <td>
                                	<bean:write name="toolsinfoid" property="enumber"/>
                                </td>
                                <td>
                               		 <bean:write name="toolsinfoid" property="unit"/>
                                </td>
                                <td>
                                	<bean:write name="toolsinfoid" property="type"/>
                                </td>
                       	 	</tr>

                    	</logic:iterate>
                    </logic:present>
	        	</table>
                <p align="center"><html:button property="action" styleClass="button" onclick="addGoBack()" >����	</html:button></p>
    </logic:equal>


<!--��ѯҳ��-->
    <logic:equal value="3" name="type" scope="session"  >
      	<br />
        <template:titile value="������ѯ��ⵥ"/>
        <html:form action="/ToolStockAction?method=queryExec"   styleId="queryForm2" >
        	<template:formTable namewidth="200"  contentwidth="200">
            	<template:formTr name="�� �� ��"  >
            		<select name="userid"   class="inputtext" style="width:180px" >
			        	<option  value="">ѡ��������Ա</option>
                      	<logic:present name="users">
		                	<logic:iterate id="usersId" name="users">
		                    	<option value="<bean:write name="usersId" property="userid"/>"><bean:write name="usersId" property="username"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
                <!--
                <template:formTr name="��ŵص�"  >
            		<select name="adress"   class="inputtext" style="width:180px" >
			        	<option  value="">ѡ�����еص�</option>
                      	<logic:present name="adress">
		                	<logic:iterate id="adressId" name="adress">
		                    	<option value="<bean:write name="adressId" property="adress"/>"><bean:write name="adressId" property="adress"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
                <template:formTr name="�� �� ��"  >
            		<select name="patrolid"   class="inputtext" style="width:180px" >
			        	<option  value="">ѡ�����б�����</option>
                      	<logic:present name="patrolid">
		                	<logic:iterate id="patrolidId" name="patrolid">
		                    	<option value="<bean:write name="patrolidId" property="patrolid"/>"><bean:write name="patrolidId" property="patrolid"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
				-->
                <template:formTr name="��ʼʱ��">
                	<input   id="begin" type="text"   name="begintime"  readonly="readonly" class="inputtext" style="width:150" />
                    <INPUT TYPE='BUTTON' VALUE='����' ID='btn'  onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';" >
                </template:formTr>
                <template:formTr name="����ʱ��">
                	<input  id="end" type="text"  name="endtime" readonly="readonly" class="inputtext" style="width:150" />
                    <INPUT TYPE='BUTTON' VALUE='����' ID='btn'  onclick="GetSelectDateTHIS('end')" STYLE="font:'normal small-caps 6pt serif';" >
                </template:formTr>
                <template:formSubmit>
					      	<td>
	                          <html:submit property="action" styleClass="button" >����</html:submit>
					      	</td>
                            <td>
                              <html:reset property="action" styleClass="button" >ȡ��</html:reset>
                            </td>
                            <td>
                              <html:button property="action" styleClass="button" onclick="addGoBack()" >����</html:button>
                            </td>
                            <td width="85">
                              <html:button property="action" styleClass="button" onclick="toExportStock()">Excel����</html:button>
                            </td>
					    </template:formSubmit>
        	</template:formTable>

    	</html:form>
    </logic:equal>

<!--/////////////////////////////////////////�����Ǳ��ϵò���//////////////////////////////////////////////////////-->
	<!--��д���ϵ�-->
	<logic:equal value="revoke2"  name="type" scope="session" >
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
      	<div id="partDivId"  >
	        <br/>
	        <template:titile value="��д�������ϵ�"/>
	        <html:form action="/ToolStockAction?method=addRevoke" styleId="addForm">
			    <input  type="hidden" name="contractorid" value="<bean:write name="deptid"/>"/>
	          	<input type="hidden" name="userid" value="<bean:write name="userid" />"/>
	            <table align="center" width="600" border="0">
		        	<tr >
		            	<td width="100" height="25"><b>��λ����:</b></td>
	                    <td align="left" width="100"><bean:write name="deptname"/></td>
	                    <td  align="right" width="100"><b>�� �� ��:</b></td>
	                    <td align="left" width="80"><bean:write name="username"/></td>

		            </tr>
	                <tr>
	                	<td height="25"><b>����ԭ��:</b></td>
	                    <td colspan="5"><html:text property="remark" styleClass="inputtext" style="width:500;" maxlength="510" value="����д�������ϵ�ԭ��."></html:text></td>
	                </tr>
	                <tr>
	                	<td colspan="6">
	                    	<hr /><br>��ѡ�񱨷ϵı���:
	                    </td>
	                </tr>
		        </table>

		            <table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
		            	<tr>
		                	<th  width="25%" class="thlist" align="center">��������</th>
		                    <th  width="15%" class="thlist" align="center">��������</th>
		                    <th  width="20%" class="thlist" align="center">������λ</th>
		                    <th  width="25%" class="thlist" align="center">����ͺ�</th>
		                    <th  width="15%"class="thlist" align="center">����</th>
		            	</tr>
		        	</table>
                    <p align="center">
		                          <html:button property="action" styleClass="button" onclick="addRowForRev()">��ӱ��ϱ���</html:button>
						       	 	<html:button property="action" styleClass="button"  onclick="toAddSub()">�ύ���ϵ�</html:button>
						       	 	<html:button property="action" styleClass="button" onclick="addGoBackFOrRev()" >����	</html:button>
                    </p>

	        </html:form>
         </div>
	</logic:equal>

       <!--��ʾ���ϵ�-->
    <logic:equal value="revoke1" name="type" scope="session" >
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
        <br />
            <template:titile value="�������ϵ�һ����" />
    		<display:table name="sessionScope.stockinfo" id="currentRowObject"  pagesize="18">
            	<display:column property="contractorname" title="���ϵ�λ" maxLength="10" align="center"/>
                <display:column property="username" title="�� �� ��" maxLength="20"align="center"/>
                <display:column property="time" title="����ʱ��"maxLength="20"align="center"/>
                <display:column property="remark" title="����ԭ��"maxLength="20"align="center"/>
                <display:column media="html" title="����" >
	            	 <%
					    BasicDynaBean object1 = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
					    String id1 = (String) object1.get("stockid");
					  %>
	            	 <a href="javascript:toGetFormForRevoke('<%=id1%>')">��ϸ</a>
			  	</display:column>
    		</display:table>
            <html:link action="/ToolStockAction?method=exportRevokeResult">����ΪExcel�ļ�</html:link>
    </logic:equal>

    <!--��ʾ���ϵ���ϸ��Ϣ-->
    <logic:equal value="revoke10"  scope="session"   name="type">
            <br />
      	<template:titile value="�������ϵ���ϸ��Ϣ" />
            <table align="center" width="600" border="0">
	          		<tr >
		            	<td width="100" height="25"><b>��λ����:</b></td>
	                    <td align="left" width="100"><bean:write name="stockinfo" property="factory"/></td>
	                    <td  align="right" width="100"><b>�� �� ��:</b></td>
	                    <td align="left" width="80"><bean:write name="stockinfo" property="useid"/></td>
                        <td width="100" align="right"><b>����ʱ��:</b></td>
	                    <td align="left" width="120"><bean:write name="stockinfo" property="time"/></td>
	                </tr>
                     <tr>

	                </tr>
	                <tr>
	                	<td height="25"><b>����ԭ��:</b></td>
	                    <td colspan="5"><bean:write name="stockinfo" property="remark"/></td>
	                </tr>
                     <tr>
                            	<td colspan="6"><hr /></td>
                    </tr>
	                <tr>
	                	<td colspan="6"   valign="bottom">
                        	<b>�ñ��ϵ��������ϵı���:</b>
                        </td>
	                </tr>
		        </table>
                <table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
	            	<tr>
	                	<th  width="25%" class="thlist" align="center">��������</th>
	                    <th  width="15%" class="thlist" align="center">��������</th>
                        <th  width="20%" class="thlist" align="center">������λ</th>
	                    <th  width="25%" class="thlist" align="center">����ͺ�</th>

	            	</tr>
                    <logic:present name="toolsinfo">
                    	<logic:iterate id="toolsinfoid" name="toolsinfo">
                        	<tr >
                            	<td>
                                  	<bean:write name="toolsinfoid" property="name"/>
                                </td>
                                <td>
                                	<bean:write name="toolsinfoid" property="enumber"/>
                                </td>
                                <td>
                               		 <bean:write name="toolsinfoid" property="unit"/>
                                </td>
                                <td>
                                	<bean:write name="toolsinfoid" property="type"/>
                                </td>
                       	 	</tr>

                    	</logic:iterate>
                    </logic:present>
	        	</table>
                <p align="center"><html:button property="action" styleClass="button" onclick="toGoBackForRevoke()" >����	</html:button></p>
    </logic:equal>

    <!--��ѯҳ��-->
    <logic:equal value="revoke3"  scope="session" name="type" >
      	<br />
        <template:titile value="������ѯ���ϵ�"/>
        <html:form action="/ToolStockAction?method=queryExecForRevoke"   styleId="queryForm2" >
        	<template:formTable namewidth="200"  contentwidth="200">
            	<template:formTr name="�� �� ��"  >
            		<select name="userid"   class="inputtext" style="width:180px" >
			        	<option  value="">ѡ��������Ա</option>
                      	<logic:present name="users">
		                	<logic:iterate id="usersId" name="users">
		                    	<option value="<bean:write name="usersId" property="userid"/>"><bean:write name="usersId" property="username"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
                <template:formTr name="����ԭ��"  >
            		<select name="remark"   class="inputtext" style="width:180px" >
			        	<option  value="">ѡ������ԭ��</option>
                      	<logic:present name="remark">
		                	<logic:iterate id="remarkId" name="remark">
		                    	<option value="<bean:write name="remarkId" property="remark"/>"><bean:write name="remarkId" property="remark"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>

                <template:formTr name="��ʼʱ��">
                	<input   id="begin" type="text"   name="begintime"  readonly="readonly" class="inputtext" style="width:150" />
                    <INPUT TYPE='BUTTON' VALUE='����' ID='btn'  onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';" >
                </template:formTr>
                <template:formTr name="����ʱ��">
                	<input  id="end" type="text"  name="endtime" readonly="readonly" class="inputtext" style="width:150" />
                    <INPUT TYPE='BUTTON' VALUE='����' ID='btn'  onclick="GetSelectDateTHIS('end')" STYLE="font:'normal small-caps 6pt serif';" >
                </template:formTr>
                <template:formSubmit>
					      	<td>
	                          <html:submit property="action" styleClass="button" >����</html:submit>
					      	</td>
					      	<td>
					       	 	<html:reset property="action" styleClass="button" >ȡ��</html:reset>
					      	</td>
	                        <td>
					       	 	<html:button property="action" styleClass="button" onclick="addGoBackFOrRev()" >����</html:button>
                            </td>
                            <td width="85">
                              <html:button property="action" styleClass="button" onclick="toExportRevoke()">Excel����</html:button>
                            </td>
					    </template:formSubmit>
        	</template:formTable>

    	</html:form>
    </logic:equal>


	<iframe id="hiddenIframe" style="display:none"></iframe>
</body>

</html>
