<%@include file="/common/header.jsp"%>

<html>
<head>
<script type="" language="javascript">
	var rowArr = new Array();//һ�в��ϵ���Ϣ
    var infoArr = new Array();//���в��ϵ���Ϣ

    //��ʼ������
    function initArray(id,name,unit,type,essenumber,oldnumber){
    	rowArr[0] = id;
        rowArr[1] = name;
        rowArr[2] = unit;
        rowArr[3] = type;
        rowArr[4] = essenumber;
        rowArr[5] = oldnumber;
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
                if(infoArr[i][3] ==null || infoArr[i][3] =="")
                	type = "��";
                else
                	type = infoArr[i][3];
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
        t1.size = "10";
        t1.onblur = checknew;


        //����һ��select
        var s1 = document.createElement("select");
        s1.options.length = infoArr.length + 1;
        s1.options[0].value = "";
        s1.options[0].text = "��ѡ�񱸼�����";
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
        b1.value = "ɾ��";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
        //alert(b1.id + "rrr");

        cell1.appendChild(s1);//����
        cell2.appendChild(t1);//����
        cell3.innerText="ûѡ����";//����
        cell4.innerText="ûѡ����";//����
        cell5.appendChild(b1);
	}

     function checknew(){
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
        	alert("Ŀǰ�����" + oldnumber + ",����������ô��!!");
            this.focus();
            this.value=0;
            return false;
        }
        return true;
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

        //����ύ
    function toAddSub(){
      if(queryID.rows.length<2){
      	alert("�㻹û��ѡ�񱸼�,�����ύ!")
        return false;
      }
    	addForm.submit();
    }

    //��ʾҳ����ϸ��Ϣ��ť����
   function toGetForm(idValue){
     	var url = "${ctx}/ToolUseAction.do?method=showOneUse&useid=" + idValue;
        self.location.replace(url);
    }

	function toGetFormForBack(idValue){
     	var url = "${ctx}/ToolUseAction.do?method=showOneUseForBack&useid=" + idValue;
        self.location.replace(url);
    }
   function  toGetFormForShouldBack(idValue){
   		var url = "${ctx}/ToolUseAction.do?method=showShouldBackUse&id=" + idValue;
        self.location.replace(url);
   }
   function  addGoBack(){
     	var url = "${ctx}/ToolUseAction.do?method=showAllUse";
        self.location.replace(url);
    }
    //ѡ������
    function GetSelectDateTHIS(strID) {
    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    	return ;
	}
    //��д��������ʱ�ļ��
   function valiForBackBnumber(id){
     	var obj = document.getElementById(id);
      	var mysplit = /^\d{1,6}$/;
        if(!mysplit.test(obj.value)){
        	alert("����д�Ĳ�������,����������");
            obj.focus();
            obj.value=0;
            return false;
        }
   		var toolid = id.substring(3,id.length);
        var tem1 = "use" + toolid;
        var tem2 = "bac" + toolid
        var useObj = document.getElementById(tem1);
        var bacObj = document.getElementById(tem2);
        if(parseInt(obj.value) > (parseInt(useObj.value) - parseInt(bacObj.value))){
        	alert("����д�ķ�����������Ӧ��������,����������");
            obj.focus();
            return false;
        }
		return ;
   }

   function toExportUseList(){

     var userid = queryForm2.userid.value;
     var usename = queryForm2.usename.value;
     var useunit = queryForm2.useunit.value;
     var useremark = queryForm2.useremark.value;
     var back = queryForm2.back.value;
     var begintime = queryForm2.begintime.value;
     var endtime = queryForm2.endtime.value;
     var url = "${ctx}/ToolUseAction.do?method=exportUseList&userid="+userid+"&usename="+usename+"&useunit="+useunit+"&useremark="+useremark+"&back="+back+"&begintime="+begintime+"&endtime="+endtime;
     self.location.replace(url);

   }
</script>


<title>
partRequisition
</title>
</head>
<body>

	<!--��д���õ�-->
	<logic:equal value="use2"  name="type" scope="session" >
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

        <br/>
        <template:titile value="��д�������õ�"/>
        <html:form action="/ToolUseAction?method=addUse" styleId="addForm">
		    <input  type="hidden" name="contractorid" value="<bean:write name="deptid"/>"/>
          	<input type="hidden" name="userid" value="<bean:write name="userid" />"/>
            <table align="center" width="600" border="0">

               <tr>
                	<td width="100" height="25"  ><b>���õ�λ:</b></td>
                    <td width="100"><input type="text" name="useunit"  Class="inputtext" maxlength="15" value="<bean:write name="deptname"/>"/></td>
                 	<td width="100"  height="25" align="right" ><b>������:</b></td>
                    <td width="200" colspan="3"><input type="text" name="usename"  style="width:100" Class="inputtext"  maxlength="5" value="<bean:write name="username"/>"/></td>

                </tr>
                <tr>
                	<td height="25"><b>������;:</b></td>
                    <td colspan="5"><html:text property="useremark" styleClass="inputtext" style="width:500;" maxlength="510" value="����д������;."></html:text></td>
                </tr>
                <tr >
	            	<td  height="25"><b>��λ����:</b></td>
                    <td align="left" ><bean:write name="deptname"/></td>
                    <td  align="right" ><b>������:</b></td>
                    <td align="left" ><bean:write name="username"/></td>
                    <td align="right"><b>ʱ��:</b></td>
                    <td align="left" ><bean:write name="date"/></td>
	            </tr>
               <tr>
               		<td colspan="6"><hr/><br />��ѡ�񱸼�:</td>
               </tr>
	        </table>
        	<table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
            	<tr>
                	<th  width="20%" class="thlist" align="center">��������</th>
                    <th  width="15%" class="thlist" align="center">��������</th>
                    <th  width="10%" class="thlist" align="center">������λ</th>
                    <th  width="20%" class="thlist" align="center">����ͺ�</th>
                    <th  width="8%"class="thlist" align="center">����</th>
            	</tr>
        	</table>

	      	<p align="center">
              <html:button property="action" styleClass="button" onclick="addRow()">�����±���</html:button>
              <html:button property="action" styleClass="button"  onclick="toAddSub()">�ύ���õ�</html:button>
              <html:button property="action" styleClass="button" onclick="addGoBack()" >����	</html:button>
			</p>
        </html:form>
	</logic:equal>


      <!--��ʾ���õ�-->
    <logic:equal value="use1" name="type" scope="session" >
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
    	    <br />
            <template:titile value="�������õ�һ����" />
    		<display:table name="sessionScope.useinfo" id="currentRowObject"  pagesize="18">
            	<display:column property="useunit" title="���õ�λ" maxLength="10" align="center"/>
                <display:column property="usename" title="������" maxLength="20"align="center"/>
                <display:column property="time" title="����ʱ��"maxLength="20"align="center"/>
                <display:column property="useremark" title="������;" maxLength="20"align="center"/>
                <display:column property="username" title="������" maxLength="10"/>
                <display:column media="html" title="����" >
	            	 <%
					    BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
					    String id = (String) object.get("useid");
					  %>
	            	 <a href="javascript:toGetForm('<%=id%>')">��ϸ</a>
			  	</display:column>
    		</display:table>
            <html:link action="/ToolUseAction?method=exportUseResult">����ΪExcel�ļ�</html:link>
    </logic:equal>
    <!--��ʾ���õ���ϸ��Ϣ-->
    <logic:equal value="use10"  scope="session"   name="type">
            <br />
      	<template:titile value="�������õ���ϸ��Ϣ" />
        	 <table align="center" width="600" border="0">
	          		<tr >
		            	<td width="60" height="25"><b>��λ����:</b></td>
	                    <td align="left" width="120"><bean:write name="useinfo" property="contractorname"/></td>
	                    <td  align="right" width="50"><b>������:</b></td>
	                    <td align="left" width="100"><bean:write name="useinfo" property="username"/></td>
	                    <td width="70" align="right"><b>����ʱ��:</b></td>
	                    <td align="left" width="100"><bean:write name="useinfo" property="time"/></td>
		            </tr>
                    <tr >
		            	<td width="60" height="25"><b>���õ�λ:</b></td>
	                    <td align="left" width="120"><bean:write name="useinfo" property="useunit"/></td>
	                    <td  align="right" width="50"><b>������:</b></td>
	                    <td align="left" width="100"><bean:write name="useinfo" property="usename"/></td>

		            </tr>
                    <tr>
	                	<td height="25"><b>����ԭ��:</b></td>
	                    <td colspan="5"><bean:write name="useinfo" property="useremark"/></td>
	                </tr>
	                 <tr>
                    	<td colspan="6"><hr /></td>
                    </tr>
	                <tr>
	                	<td colspan="6"   valign="bottom">
                        	<b>�����õ������õı���:</b>
                        </td>
	                </tr>
		        </table>
                <table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
	            	<tr>
	                	<th  width="25%" class="thlist" align="center">��������</th>
	                    <th  width="15%" class="thlist" align="center">�������� </th>
                        <th  width="15%" class="thlist" align="center">�������� </th>
                        <th  width="20%" class="thlist" align="center">������λ</th>
	                    <th  width="25%" class="thlist" align="center">����ͺ�</th>

	            	</tr>
                    <logic:present name="usepartinfo">
                    	<logic:iterate id="useid" name="usepartinfo">
                        	<tr   >
                            	<td>
                                  	<bean:write name="useid" property="name"/>
                                </td>
                                <td>
                                	<bean:write name="useid" property="enumber"/>
                                </td>
                                 <td>
                                	<bean:write name="useid" property="bnumber"/>
                                </td>
                                <td>
                               		 <bean:write name="useid" property="unit"/>
                                </td>
                                <td>
                                	<bean:write name="useid" property="type"/>
                                </td>
                       	 	</tr>
                    	</logic:iterate>
                    </logic:present>
	        	</table>
                <p align="center">
                  <html:button property="action" styleClass="button" onclick="addGoBack()" >����	</html:button>
                </p>

    </logic:equal>

    <!--��ѯҳ��-->
    <logic:equal value="use3" name="type" scope="session"  >
      	<br />
        <template:titile value="�������ұ������õ�"/>
        <html:form action="/ToolUseAction?method=queryExec"   styleId="queryForm2" >
        	<template:formTable namewidth="200"  contentwidth="200">
            	<template:formTr name="����������"  >
            		<select name="userid"   class="inputtext" style="width:180px" >
			        	<option  value="">ѡ�����в�����</option>
                      	<logic:present name="useuser">
		                	<logic:iterate id="useuserId" name="useuser">
		                    	<option value="<bean:write name="useuserId" property="userid"/>"><bean:write name="useuserId" property="username"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
                <template:formTr name="����������"  >
            		<select name="usename"   class="inputtext" style="width:180px" >
			        	<option  value="">ѡ������������</option>
                      	<logic:present name="lusename">
		                	<logic:iterate id="lusenameId" name="lusename">
		                    	<option value="<bean:write name="lusenameId" property="usename"/>"><bean:write name="lusenameId" property="usename"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
                  <template:formTr name="���õ�λ"  >
            		<select name="useunit"   class="inputtext" style="width:180px" >
			        	<option  value="">ѡ���������õ�λ</option>
                      	<logic:present name="luseunit">
		                	<logic:iterate id="luseunitId" name="luseunit">
		                    	<option value="<bean:write name="luseunitId" property="useunit"/>"><bean:write name="luseunitId" property="useunit"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
              	<template:formTr name="����ԭ��" >
            		<select name="useremark" style="width:180px" class="inputtext"  >
			        	<option  value="">ѡ������ԭ��</option>
                      	<logic:present name="useremark">
		                	<logic:iterate id="luseremarkId" name="useremark">
		                    	<option value="<bean:write name="luseremarkId"  property="useremark"/>"><bean:write name="luseremarkId"  property="useremark"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr >
                <template:formTr name="�黹���"  >
            		<select name="back"   class="inputtext" style="width:180px" >
			        	<option  value="">ѡ������</option>
                        <option  value="1">ȫ���黹</option>
                        <option  value="2">��δ�黹</option>
		    		</select>
                </template:formTr>
                <template:formTr name="��ʼʱ��">
                	<input   id="begin" type="text"   readonly="readonly" name="begintime" class="inputtext" style="width:150" />
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
                              <html:button property="action" styleClass="button" onclick="toExportUseList()">Excel����</html:button>
                            </td>
					    </template:formSubmit>
        	</template:formTable>
			</html:form>
    </logic:equal>




    <!--����ʱ��ѯ���õ���ʾ-->
    <logic:equal value="back3" name="type"  scope="session" >
      	<br />
        <template:titile value="�������Ҵ��黹�����õ�"/>
        <html:form action="/ToolUseAction?method=backQueryExec"   styleId="queryForm2" >
        	<template:formTable namewidth="200"  contentwidth="200">
            	<template:formTr name="����������"  >
            		<select name="userid"   class="inputtext" style="width:180px" >
			        	<option  value="">ѡ�����в�����</option>
                      	<logic:present name="useuser">
		                	<logic:iterate id="useuserId" name="useuser">
		                    	<option value="<bean:write name="useuserId" property="userid"/>"><bean:write name="useuserId" property="username"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
                <template:formTr name="����������"  >
            		<select name="usename"   class="inputtext" style="width:180px" >
			        	<option  value="">ѡ������������</option>
                      	<logic:present name="lusename">
		                	<logic:iterate id="lusenameId" name="lusename">
		                    	<option value="<bean:write name="lusenameId" property="usename"/>"><bean:write name="lusenameId" property="usename"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
                  <template:formTr name="���õ�λ"  >
            		<select name="useunit"   class="inputtext" style="width:180px" >
			        	<option  value="">ѡ���������õ�λ</option>
                      	<logic:present name="luseunit">
		                	<logic:iterate id="luseunitId" name="luseunit">
		                    	<option value="<bean:write name="luseunitId" property="useunit"/>"><bean:write name="luseunitId" property="useunit"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
              	<template:formTr name="����ԭ��" >
            		<select name="useremark" style="width:180px" class="inputtext"  >
			        	<option  value="">ѡ������ԭ��</option>
                      	<logic:present name="useremark">
		                	<logic:iterate id="luseremarkId" name="useremark">
		                    	<option value="<bean:write name="luseremarkId"  property="useremark"/>"><bean:write name="luseremarkId"  property="useremark"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr >
                <template:formTr name="��ʼʱ��">
                	<input   id="begin" type="text"   readonly="readonly" name="begintime" class="inputtext" style="width:150" />
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
					    </template:formSubmit>
        	</template:formTable>
    	</html:form>
    </logic:equal>


    <!--��ʾ�����������õ�-->
    <logic:equal value="back1" name="type" scope="session" >
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
    	   	<br />
            <template:titile value="�������������õ�һ����" />
    		<display:table name="sessionScope.useinfo" id="currentRowObject"  pagesize="18">
            	<display:column property="useunit" title="���õ�λ" maxLength="10" align="center"/>
                <display:column property="usename" title="������" maxLength="20"align="center"/>
                <display:column property="time" title="����ʱ��"maxLength="20"align="center"/>
                <display:column property="useremark" title="������;" maxLength="20"align="center"/>
                <display:column property="username" title="������" maxLength="10"/>

                <display:column media="html" title="����" >
	            	 <%
					    BasicDynaBean  object2 = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
					    String id2 = (String) object2.get("useid");
					  %>
	            	 <a href="javascript:toGetFormForBack('<%=id2%>')">����</a>
			  	</display:column>
    		</display:table>
            <html:link action="/ToolUseAction?method=exportBackResult">����ΪExcel�ļ�</html:link>
    </logic:equal>

      <!--��ʾ��������ҳ��-->
    <logic:equal value="back10"   name="type" scope="session" >
            <br />
      	<template:titile value="��д���������嵥" />
        <html:form action="/ToolUseAction?method=addBack"   styleId="queryForm2" >
       		 	<input  type="hidden" name="useid" value="<bean:write name="useinfo" property="useid"/>"/>
          		<table align="center" width="600" border="0">
                    <tr >
                      	<td width="80" height="25"><b>���õ�λ:</b></td>
	                    <td width="120"align="left" ><bean:write name="useinfo" property="useunit"/></td>
	                    <td width="50" align="right" ><b>������:</b></td>
	                    <td  width="150"align="left"><bean:write name="useinfo" property="usename"/></td>
						 <td width="70" align="right"><b>����ʱ��:</b></td>
	                    <td width="130"align="left" ><bean:write name="useinfo" property="time"/></td>
		            </tr>
                    <tr>
	                	<td height="25"><b>����ԭ��:</b></td>
	                    <td colspan="5"><bean:write name="useinfo" property="useremark"/></td>
	                </tr>
	                 <tr>
                    	<td colspan="6"><hr /></td>
                    </tr>


                    <tr >
		            	<td height="25"><b>������λ:</b></td>
	                    <td align="left" > <input type="text"  name="bunit" class="inputtext" value=" <bean:write name="useinfo" property="useunit"/>"/> </td>
	                    <td align="right" ><b>������:</b></td>
	                    <td align="left" > <input  type="text"  name="bname" class="inputtext" value="<bean:write name="useinfo" property="usename"/>" /></td>

		            </tr>
                    <tr>
	                	<td height="25"><b>������ע:</b></td>
	                    <td colspan="5"><input  type="text"  name="bremark" class="inputtext" style="width:500;" maxlength="510"  value="<bean:write name="useinfo" property="useremark"/>" /></td>
	                </tr>
                     <tr >
		            	<td  height="25"><b>��λ����:</b></td>
	                    <td align="left" ><bean:write name="useinfo" property="contractorname"/></td>
	                    <td align="right"><b>������:</b></td>
	                    <td align="left" ><bean:write name="useinfo" property="username"/></td>
	                    <td align="right"><b>����ʱ��:</b></td>
	                    <td align="left"><bean:write name="date" /></td>
		            </tr>
	                 <tr>
                    	<td colspan="6"><hr /></td>
                    </tr>
	                <tr>
	                	<td colspan="6"   valign="bottom">
                        	<b>���������б�:</b>
                        </td>
	                </tr>
		        </table>
                <table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
	            	<tr>
	                	<th  width="25%" class="thlist" align="center">��������</th>
                        <th  width="20%" class="thlist" align="center">������λ</th>
	                    <th  width="20%" class="thlist" align="center">����ͺ�</th>
	                    <th  width="10%" class="thlist" align="center">���� </th>
                        <th  width="10%" class="thlist" align="center">�ѷ��� </th>
                        <th  width="15%" class="thlist" align="center">���� </th>


	            	</tr>
                    <logic:present name="usepartinfo">
                    	<logic:iterate id="useid" name="usepartinfo">
                        	<tr   >
                            	<td>
                                 <input type="hidden" name="id" value="<bean:write name="useid" property="id"/>" />	<bean:write name="useid" property="name"/>
                                </td>
                                <td>
                               		 <bean:write name="useid" property="unit"/>
                                </td>
                                <td>

                                		<bean:write name="useid" property="type"/>
                                </td>
                                <td>
                                	<input  type="hidden" id="use<bean:write name="useid" property="id"/>" value="<bean:write name="useid" property="enumber"/>"/>
                                  	<bean:write name="useid" property="enumber"/>
                                </td>
                                 <td>
                                 	<input  type="hidden" id="bac<bean:write name="useid" property="id"/>" value="<bean:write name="useid" property="bnumber"/>"/>
                                	<bean:write name="useid" property="bnumber"/>
                                </td>
                                 <td>
                                	<input type="text" name="bnumber"   class="inputtext"  onblur="valiForBackBnumber(id)" id="bed<bean:write name="useid" property="id"/>" style="width:80" value="0"/>
                                </td>

                       	 	</tr>
                    	</logic:iterate>
                    </logic:present>
	        	</table>
                <p align="center">
						       	 	<html:submit property="action" styleClass="button"  >�ύ����	</html:submit>

						       	 	<html:button property="action" styleClass="button" onclick="javascript:history.back()" >����	</html:button>
                </p>
            </html:form>
    </logic:equal>

        <!--��ʾ�������ı���-->
    <logic:equal value="back07" name="type" scope="session" >
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
            <br />
            <template:titile value="����������һ����" />
    		<display:table name="sessionScope.shouldBackTool" id="currentRowObject"  pagesize="18">
            	<display:column property="name" title="��������" maxLength="10" align="center"/>
                <display:column property="style" title="��������" maxLength="20"align="center"/>
                <display:column property="type" title="�����ͺ�"maxLength="20"align="center"/>
                <display:column property="tooluse" title="������;" maxLength="20"align="center"/>
                <display:column property="enumber" title="��������" maxLength="20"align="center"/>

                <display:column media="html" title="����" >
	            	 <%
					    BasicDynaBean  object3 = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
					    String id3 = (String) object3.get("id");
					  %>
	            	 <a href="javascript:toGetFormForShouldBack('<%=id3%>')">������ϸ</a>
			  	</display:column>
    		</display:table>
    </logic:equal>





	<iframe id="hiddenIframe" style="display:none"></iframe>
</body>
</html>
