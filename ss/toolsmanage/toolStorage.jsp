<%@include file="/common/header.jsp"%>

<html>
<head>
<script type="" language="javascript">
	var rowArr = new Array();//һ�в��ϵ���Ϣ
    var infoArr = new Array();//���в��ϵ���Ϣ

    //��ʼ������
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
                if(infoArr[i][3] == null || infoArr[i][3] == "")
                	type = "��";
                else
                	type = infoArr[i][3];
            }
        }
        //д����
        tr.cells[1].innerText =unit;
        tr.cells[2].innerText =type;
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
        var   cell6=onerow.insertCell();
        var   cell7=onerow.insertCell();
		//����һ�������(�����)
        var t1 = document.createElement("input");
        t1.name = "essenumber"
		t1.id = "text" + onerow.id;
        t1.value= "0";
        t1.maxLength = "6";
        t1.size = "7";
        t1.onblur = checknew;
        //����һ�������(������)
         var t2 = document.createElement("input");
        t2.name = "portmainnumber"
		t2.id = "tex2" + onerow.id;
        t2.value= "0";
        t2.maxLength = "6";
        t2.size = "7";
        t2.onblur = checkold;
        //����һ�������(������)
         var t3 = document.createElement("input");
        t3.name = "mainnumber"
		t3.id = "tex3" + onerow.id;
        t3.value= "0";
        t3.maxLength = "6";
        t3.size = "7";
        t3.onblur = checkold;

        //����һ��select
        var s1 = document.createElement("select");
        s1.options.length = infoArr.length + 1;
        s1.options[0].value = "";
        s1.options[0].text = "��ѡ�񱸼�";
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
        cell2.innerText="ûѡ����";//����
        cell3.innerText="ûѡ����";//����
        cell4.appendChild(t1);
        cell5.appendChild(t2);
        cell6.appendChild(t3);
        cell7.appendChild(b1);
	}
    function checkold(){
    	var mysplit = /^\d{1,6}$/;
        if(!mysplit.test(this.value)){
        	alert("����д�Ĳ�������,����������");
            this.value=0;
            this.focus();
        }
    }
     function checknew(){
    	var mysplit = /^\d{1,6}$/;
        if(!mysplit.test(this.value)){
        	alert("����д�Ĳ�������,����������");
            this.value=0;
            this.focus();
        }
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

        }
    }

        //����ύ
    function toAddSub(){
      if(queryID.rows.length<2){
      	alert("�㻹û��ѡ�񱸼�,�����ύ!")
        return false;
      }
      if(confirm("��ȷ��Ҫ��ʼ�������?\nѡ��ȷ���ύ�ó�ʼ����,ȡ������."))
    	addForm.submit();
      else
      	return false;
    }



   function  addGoBack(){
     	var url = "${ctx}/ToolStorageAction.do?method=showAllStorage";
        self.location.replace(url);
    }
    //ѡ������
    function GetSelectDateTHIS(strID) {
    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    	return ;
	}
</script>


<title>
partRequisition
</title>
</head>
<body>

     <!--��ʾ���п��-->
    <logic:equal value="st1" name="type" scope="session" >
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
            <br />
        <template:titile value="�������һ����" />
    		<display:table name="sessionScope.storageinfo" id="currentRowObject"  pagesize="18" align="center">
            	<display:column property="contractorname" title="��λ����" maxLength="10" align="center" />
              	<display:column property="name" title="��������" maxLength="10" align="center" />
                 <display:column property="unit" title="������λ" maxLength="20"align="center" />
                <display:column property="type" title="����ͺ�" maxLength="10" />
                <display:column property="essenumber" title="�� �� ��" maxLength="20"align="center"/>
                <display:column property="portmainnumber" title="�� �� ��"maxLength="20"align="center" />
                <display:column property="mainnumber" title="�� �� ��"maxLength="20"align="center" />
             </display:table>
            <html:link action="/ToolStorageAction?method=exportStorageResult">����ΪExcel�ļ�</html:link>
    </logic:equal>
     <!--��ѯҳ��-->
    <logic:equal value="st3" name="type"  scope="session" >
      	<br />
        <template:titile value="�������ҿ��"/>
        <html:form action="/ToolStorageAction?method=queryExec"   styleId="queryForm2" >
        	<template:formTable namewidth="200"  contentwidth="200">
		           <logic:equal value="1" name="unittype">
		           		<logic:present name="contractorname">
		              		<template:formTr name="��ά��λ"  >
			            		<select name="contractorid"   class="inputtext" style="width:180px" >
						        	<option  value="">ѡ�����е�λ</option>
					                	<logic:iterate id="contractornameId" name="contractorname">
					                    	<option value="<bean:write name="contractornameId" property="contractorid"/>"><bean:write name="contractornameId" property="contractorname"/></option>
					                	</logic:iterate>
					    		</select>
			                </template:formTr>
	                    </logic:present>
                    </logic:equal>
               <template:formTr name="��������"  >
            		<select name="id"   class="inputtext" style="width:180px" >
			        	<option  value="">ѡ�����б���</option>
                      	<logic:present name="toolname">
		                	<logic:iterate id="toolnameId" name="toolname">
		                    	<option value="<bean:write name="toolnameId" property="id"/>"><bean:write name="toolnameId" property="name"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
              	<template:formTr name="��������" >
            		<select name="type" style="width:180px" class="inputtext"  >
			        	<option  value="">ѡ����������</option>
                      	<logic:present name="tooltype">
		                	<logic:iterate id="tooltypeId" name="tooltype">
		                    	<option value="<bean:write name="tooltypeId"  property="type"/>"><bean:write name="tooltypeId"  property="type"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr >
                <template:formTr name="�� �� ��" >
            		<b>����&nbsp;&nbsp;</b><input   type="text" name="esselownumber" id="newlow" class="inputtext" size="10" maxlength="6"   onkeyup="valiDigit(id)"/>
                    <b>С��&nbsp;&nbsp;</b><input   type="text" name="essehighnumber" id="newhig" class="inputtext" size="10" maxlength="6"   onkeyup="valiDigit(id)"/>
                </template:formTr >
                 <template:formTr name="�� �� ��" >
            		<b>����&nbsp;&nbsp;</b><input   type="text" name="portlownumber" id="oldlow" class="inputtext" size="10" maxlength="6"  onkeyup="valiDigit(id)"/>
                    <b>С��&nbsp;&nbsp;</b><input   type="text" name="porthighnumber" id="oldhig" class="inputtext" size="10" maxlength="6"   onkeyup="valiDigit(id)"/>
                </template:formTr >
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


    <!--��ʼ�����-->
	<logic:equal value="st2"  name="type" scope="session" >
    	<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
        <logic:present name="baseinfo">
        	<logic:iterate id="baseinfoId" name="baseinfo">
            	<script type="" language="javascript">
                	initArray("<bean:write name="baseinfoId" property="id"/>","<bean:write name="baseinfoId" property="name"/>",
                    		  "<bean:write name="baseinfoId" property="unit"/>","<bean:write name="baseinfoId" property="type"/>",
                             "0","0");
                </script>
        	</logic:iterate>
        </logic:present>

        <br/>
        <template:titile value="��ʼ���������"/>
        <html:form action="/ToolStorageAction?method=initStorage" styleId="addForm">
		    <input  type="hidden" name="contractorid" value="<bean:write name="deptid"/>"/>
          	<input type="hidden" name="useuserid" value="<bean:write name="userid" />"/>
            <table align="center" width="600" border="0">
	        	<tr >
	            	<td width="60" height="25"><b>��λ����:</b></td>
                    <td align="left" width="100"><bean:write name="deptname"/></td>
                    <td  align="right" width="50"><b>������:</b></td>
                    <td align="left" width="100"><bean:write name="username"/></td>
                    <td width="100" align="right"><b>����ʱ��:</b></td>
                    <td align="left" width="100"><bean:write name="date"/></td>
	            </tr>
                <tr >
	            	<td width="60" height="25" valign="top"><b>��ע��Ϣ:</b></td>
                    <td align="left" colspan="5">
                    	��ʼ�����������ָ�������������趨Ϊָ����ֵ,�ù��ܿ�������������:<br />
                        1��ϵͳ�տ�ʼ���е�ʱ���������еĿ����.<br />
                        2���ڿ���̵��и��ݿ��ʵ��ֵ�趨�����.
                    </td>

	            </tr>
                <tr>
                	<td colspan="6">
                    	<hr /><br />��ѡ�񱸼�:
                    </td>
                </tr>
	        </table>
        	<table    id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0" >
            	<tr>
                	<th  width="20%" class="thlist" align="center">��������</th>
                    <th  width="10%" class="thlist" align="center">������λ</th>
                    <th  width="20%" class="thlist" align="center">����ͺ�</th>
                     <th  width="10%" class="thlist" align="center">�� �� ��</th>
                     <th  width="10%" class="thlist" align="center">�� �� ��</th>
                     <th  width="10%" class="thlist" align="center">�� �� ��</th>
                    <th  width="8%"class="thlist" align="center">����</th>
            	</tr>
        	</table>
            <p align="center">
	                          <html:button property="action" styleClass="button" onclick="addRow()">��ӱ���</html:button>
					       	 	<html:button property="action" styleClass="button"  onclick="toAddSub()">�ύ��ʼ��</html:button>
					       	 	<html:button property="action" styleClass="button" onclick="addGoBack()" >����	</html:button>
            </p>

        </html:form>
	</logic:equal>
	<iframe id="hiddenIframe" style="display:none"></iframe>
</body>
</html>

