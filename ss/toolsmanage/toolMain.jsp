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
                if(infoArr[i][3] == null || infoArr[i][3] =="")
                	type="��";
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
        s1.options[0].text = "��ѡ������ޱ���";
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
        	alert("Ŀǰ�����" + oldnumber + ",���ܱ�����ô��!!");
            this.focus();
            this.value=0
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
     	var url = "${ctx}/ToolMainAction.do?method=showOneMain&mainid=" + idValue;
        self.location.replace(url);
    }
     function toUpForm(idValue){
     	var url = "${ctx}/ToolMainAction.do?method=upShow&mainid=" + idValue;
        self.location.replace(url);
    }
	 function  addGoBack(){
	     	var url = "${ctx}/ToolMainAction.do?method=showAllMain";
	        self.location.replace(url);
	    }
   function toDeleForm(idValue){
     	var url = "${ctx}/ToolMainAction.do?method=deleMain&mainid=" + idValue;
        if(confirm("ȷ��ɾ���ü�¼��"))
          self.location.replace(url);
    }
   function toShowMainForm(idValue){
     	var url = "${ctx}/ToolMainAction.do?method=showTool_Main&id=" + idValue;
        self.location.replace(url);
    }
    //ѡ������
    function GetSelectDateTHIS(strID) {
    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    	return ;
	}
   function toExportMainList(){

     var userid = queryForm2.userid.value;
     var mainremark = queryForm2.mainremark.value;
     var begintime = queryForm2.begintime.value;
     var endtime = queryForm2.endtime.value;
     var url = "${ctx}/ToolMainAction.do?method=exportMainList&userid="+userid+"&mainremark="+mainremark+"&begintime="+begintime+"&endtime="+endtime;
     self.location.replace(url);

   }
</script>


<title>
partRequisition
</title>
</head>
<body>

	<!--��д���޵�-->
	<logic:equal value="main2"  name="type"scope="session" >
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
        <template:titile value="��д�������޵�"/>
        <html:form action="/ToolMainAction?method=addMain" styleId="addForm">
		    <input  type="hidden" name="contractorid" value="<bean:write name="deptid"/>"/>
          	<input type="hidden" name="userid" value="<bean:write name="userid" />"/>
            <table align="center" width="600" border="0">
                <tr>
                	<td height="25"><b>���ޱ�ע:</b></td>
                    <td colspan="5"><html:text property="mainremark" styleClass="inputtext" style="width:500;" maxlength="510" value="����д����ԭ��."></html:text></td>
                </tr>
                <tr >
	            	<td  height="25"><b>��λ����:</b></td>
                    <td align="left" ><bean:write name="deptname"/></td>
                    <td  align="right" ><b>������:</b></td>
                    <td align="left" ><bean:write name="username"/></td>
                    <td align="right"><b>����ʱ��:</b></td>
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
              <html:button property="action" styleClass="button"  onclick="toAddSub()">�ύ���޵�</html:button>
              <html:button property="action" styleClass="button" onclick="addGoBack()" >����	</html:button>
			</p>
        </html:form>
	</logic:equal>


      <!--��ʾ���޵�-->
    <logic:equal value="mian1" name="type"scope="session" >
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
        <br />
            	<template:titile value="�������޵�һ����" />
	            <display:table name="sessionScope.maininfo" id="currentRowObject"  pagesize="18">
	            	<display:column property="username" title="������" maxLength="20"align="center"/>
	                <display:column property="time" title="����ʱ��"maxLength="20"align="center"/>
	                <display:column property="mainremark" title="����ԭ��" maxLength="30"align="center"/>

	                <display:column media="html" title="����" >
                         <%
						   String id="";
                         	BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
                              id = (String) object.get("mainid");
						  %>
		            	 <a href="javascript:toGetForm('<%=id%>')">��ϸ</a>
				  	</display:column>
	                <apptag:checkpower thirdmould="90304">
                        <display:column media="html" title="����" >
			            	 <%
							   String id1="";
	                         	BasicDynaBean  object1 = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
	                              id1 = (String) object1.get("mainid");
							  %>
                          	<a href="javascript:toUpForm('<%=id1%>')">�޸�</a>
					  	</display:column>
	                 </apptag:checkpower>
	                 <apptag:checkpower thirdmould="90305">
		                <display:column media="html" title="����" >
                        		<%
								   String id2="";
		                         	BasicDynaBean  object2 = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
		                              id2 = (String) object2.get("mainid");
								  %>
			            	 <a href="javascript:toDeleForm('<%=id2%>')">ɾ��</a>
					  	</display:column>
	                 </apptag:checkpower>
	    		</display:table>
                <html:link action="/ToolMainAction?method=exportMainResult">����ΪExcel�ļ�</html:link>
    </logic:equal>

    <!--��ʾ���޵���ϸ��Ϣ-->
    <logic:equal value="main10"   name="type"scope="session" >
            <br />
      	<template:titile value="�������޵���ϸ��Ϣ" />
        	 <table align="center" width="600" border="0">
	          		<tr >
		            	<td width="60" height="25"><b>��λ����:</b></td>
	                    <td align="left" width="120"><bean:write name="maininfo" property="contractorname"/></td>
	                    <td  align="right" width="50"><b>������:</b></td>
	                    <td align="left" width="100"><bean:write name="maininfo" property="username"/></td>
	                    <td width="70" align="right"><b>����ʱ��:</b></td>
	                    <td align="left" width="100"><bean:write name="maininfo" property="time"/></td>
		            </tr>
                    <tr>
	                	<td height="25"><b>����ԭ��:</b></td>
	                    <td colspan="5"><bean:write name="maininfo" property="mainremark"/></td>
	                </tr>
	                 <tr>
                    	<td colspan="6"><hr /></td>
                    </tr>
	                <tr>
	                	<td colspan="6"   valign="bottom">
                        	<b>�ñ��޵������޵ı���:</b>
                        </td>
	                </tr>
		        </table>
                <table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
	            	<tr>
	                	<th  width="25%" class="thlist" align="center">��������</th>
	                    <th  width="15%" class="thlist" align="center">�������� </th>
                        <th  width="20%" class="thlist" align="center">������λ</th>
	                    <th  width="25%" class="thlist" align="center">����ͺ�</th>
                         <th  width="25%" class="thlist" align="center">��������</th>

	            	</tr>
                    <logic:present name="maintoolinfo">
                    	<logic:iterate id="maintoolinfoid" name="maintoolinfo">
                        	<tr   >
                            	<td>
                                  	<bean:write name="maintoolinfoid" property="name"/>
                                </td>
                                <td>
                                	<bean:write name="maintoolinfoid" property="enumber"/>
                                </td>
                                <td>
                               		 <bean:write name="maintoolinfoid" property="unit"/>
                                </td>
                                <td>
                                	<bean:write name="maintoolinfoid" property="type"/>
                                </td>
                                  <td>
                                	<bean:write name="maintoolinfoid" property="style"/>
                                </td>
                       	 	</tr>
                    	</logic:iterate>
                    </logic:present>
	        	</table>
                <p align="center">
                  <html:button property="action" styleClass="button" onclick="addGoBack()" >����	</html:button>
                </p>

    </logic:equal>

        <!--�޸���ʾ-->
     <logic:equal value="main4"   name="type"scope="session" >
            <br />
      	<template:titile value="�޸ı������޵�" />
        <logic:present name="maininfo">
	        <html:form action="/ToolMainAction?method=UpMain" styleId="UpForm">
	        	<input  type="hidden" name="mainid" value="<bean:write name="maininfo" property="mainid"/>"/>
	          	<input type="hidden" name="userid" value="<bean:write name="userid" />"/>
		        <table align="center" width="600" border="0">

		          		<tr >
			            	<td width="60" height="25"><b>��λ����:</b></td>
		                    <td align="left" width="120"><bean:write name="maininfo" property="contractorname"/></td>
		                    <td  align="right" width="50"><b>������:</b></td>
		                    <td align="left" width="100"><bean:write name="maininfo" property="username"/></td>
		                    <td width="70" align="right"><b>����ʱ��:</b></td>
		                    <td align="left" width="100"><bean:write name="maininfo" property="time"/></td>
			            </tr>
	                     <tr >
			            	<td height="25"width="60"><b>��λ����:</b></td>
		                    <td align="left"width="120" ><bean:write name="deptname"/></td>
		                    <td align="right" width="50"><b>�޸���:</b></td>
		                    <td align="left" width="100"><bean:write name="username"/></td>
		                    <td align="right"width="70"><b>�޸�ʱ��:</b></td>
		                    <td align="left"width="100" ><bean:write name="date"/></td>
			            </tr>
	                    <tr>
		                	<td height="25"><b>����ԭ��:</b></td>
		                    <td colspan="5"><input  type="text" name="mainremark" Class="inputtext" style="width:500;" maxlength="50" value="<bean:write name="maininfo" property="mainremark"/>"/> </td>
		                </tr>
		                <tr>
		                	<td colspan="6" height="40" valign="top">
		                    	<hr /><br>���ޱ����б�:
		                    </td>
		                </tr>
			        </table>
	                <table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
		            	<tr>
		                	<th  width="25%" class="thlist" align="center">��������</th>
		                    <th  width="15%" class="thlist" align="center">��������</th>
		                    <th  width="20%" class="thlist" align="center">������λ</th>
		                    <th  width="25%" class="thlist" align="center">�����ͺ�</th>
		                    <th  width="15%"class="thlist" align="center">��������</th>
		            	</tr>
	                    <logic:present name="maintoolinfo">
	                    	<logic:iterate id="maintoolinfoid" name="maintoolinfo">
	                        	<tr >

	                            	<td>
	                                	<input type="hidden" name="id" value="<bean:write name="maintoolinfoid" property="id"/>"/>
	                                  	<bean:write name="maintoolinfoid" property="name"/>
	                                </td>
	                                <td>
	                                	<input  type="hidden" name="bnumber" value="<bean:write name="maintoolinfoid" property="enumber"/>" />
                                    	<input   name="enumber" maxlength="6" class="inputtext" size="6" onblur="valiDigit(id)" value="<bean:write name="maintoolinfoid" property="enumber"/>"/>
	                                </td>
	                                <td>
	                               		 <bean:write name="maintoolinfoid" property="unit"/>
	                                </td>
	                                <td>
	                                	<bean:write name="maintoolinfoid" property="type"/>
	                                </td>
	                               <td>
	                                	<bean:write name="maintoolinfoid" property="factory"/>
	                                </td>
	                       		 </tr>
	                    	</logic:iterate>
	                    </logic:present>
		        	</table>
	                <p align="center">
							       	 	<html:submit property="action" styleClass="button"   onclick="toUpSub()" title="һ���ύ,�����뵥�������˾͸���Ϊ�޸���!">�ύ�޸�</html:submit>
							       	 	<html:button property="action" styleClass="button" onclick="addGoBack()" >����	</html:button>
	                </p>
	        </html:form>
		 </logic:present>
    </logic:equal>

 <!--��ѯҳ��-->
    <logic:equal value="main3" name="type" scope="session" >
      	<br />
        <template:titile value="�������ұ������޵�"/>
        <html:form action="/ToolMainAction?method=queryExec"   styleId="queryForm2" >
        	<template:formTable namewidth="200"  contentwidth="200">
            	<template:formTr name="����������"  >
            		<select name="userid"   class="inputtext" style="width:180px" >
			        	<option  value="">ѡ�����в�����</option>
                      	<logic:present name="mainuser">
		                	<logic:iterate id="mainuserId" name="mainuser">
		                    	<option value="<bean:write name="mainuserId" property="userid"/>"><bean:write name="mainuserId" property="username"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
              	<template:formTr name="����ԭ��" >
            		<select name="mainremark" style="width:180px" class="inputtext"  >
			        	<option  value="">ѡ������ԭ��</option>
                      	<logic:present name="mianremark">
		                	<logic:iterate id="mianremarkId" name="mianremark">
		                    	<option value="<bean:write name="mianremarkId"  property="mainremark"/>"><bean:write name="mianremarkId"  property="mainremark"/></option>
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
                            <td width="85">
                              <html:button property="action" styleClass="button" onclick="toExportMainList()">Excel����</html:button>
                            </td>
					    </template:formSubmit>
        	</template:formTable>
			</html:form>
    </logic:equal>

      <!--��ʾ���б��ޱ���-->
    <logic:equal value="main6" name="type" scope="session" >
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>

            <br />
            	<template:titile value="�ѱ��ޱ���һ����" />
	            <display:table name="sessionScope.mainTool" id="currentRowObject"  pagesize="18">
	            	<display:column property="name" title="��������" maxLength="20"align="center"/>
	                <display:column property="unit" title="������λ"maxLength="20"align="center"/>
                    <display:column property="style" title="��������"maxLength="20"align="center"/>
                    <display:column property="type" title="�����ͺ�"maxLength="20"align="center"/>
                    <display:column property="factory" title="��������"maxLength="20"align="center"/>
                    <display:column property="portmainnumber" title="��������"maxLength="20"align="center"/>

	                <display:column media="html" title="����" >
                    	 <%
						    BasicDynaBean  object13 = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
						    String id13 = (String) object13.get("id");
						  %>
		            	 <a href="javascript:toShowMainForm('<%=id13%>')">�漰���޵�</a>
				  	</display:column>
	    		</display:table>

    </logic:equal>
	<iframe id="hiddenIframe" style="display:none"></iframe>
</body>
</html>
