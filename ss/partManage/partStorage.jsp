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
                type = infoArr[i][3]
            }
        }
        //д����
        var tu = document.createElement("input");
        tu.value= unit;
        tu.maxLength = "6";
        tu.size = "6";
        tu.style.background="#C6D6E2"
        tu.style.font.size="12px";
        tu.readonly="readonly";
        tr.cells[1].innerText ="";
        tr.cells[1].appendChild(tu);

        var tt = document.createElement("input");
        tt.value= type;
        tt.maxLength = "6";
        tt.style.width="160";
        tt.style.background="#C6D6E2"
        tt.style.font.size="12px";
        tt.readonly="readonly";
        tr.cells[2].innerText ="";
        tr.cells[2].appendChild(tt);

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
        //����һ�������(�²���)
        var t1 = document.createElement("input");
        t1.name = "newesse"
        t1.id = "text" + onerow.id;
        t1.value= "0";
        t1.maxLength = "6";
        t1.size = "8";
        t1.onblur = checknew;
        t1.style.background="#C6D6E2"
        t1.style.font.size="12px";
        //����һ�������(�ɲ���)
         var t2 = document.createElement("input");
        t2.name = "oldnumber"
        t2.id = "tex2" + onerow.id;
        t2.value= "0";
        t2.maxLength = "6";
        t2.size = "8";
        t2.onblur = checkold;
        t2.style.background="#C6D6E2"
        t2.style.font.size="12px";

        //����һ��select
        var s1 = document.createElement("select");
        s1.options.length = infoArr.length + 1;
        s1.options[0].value = "";
        s1.options[0].text = "��ѡ���������";
        for(i = 1; i<s1.options.length ;i++){
            s1.options[i].value = infoArr[i-1][0];
            s1.options[i].text = infoArr[i-1][1] + " -- " + infoArr[i-1][3];
        }
        s1.name = "id";
        s1.id = "select" + onerow.id;
        s1.onchange = onselectchange;
        s1.style.background="#C6D6E2"
        s1.style.font.size="12px";
        s1.style.width="250";

        //����ɾ����ť
        var b1 =document.createElement("button");
        b1.value = "ɾ��";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
        //alert(b1.id + "rrr");

        cell1.appendChild(s1);//����
        cell2.innerText="ûѡ����";//����
        cell3.innerText="ûѡ����";//����
        cell4.appendChild(t1);//����
        cell5.appendChild(t2);//����
        cell6.appendChild(b1);
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
            this.value=0
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
            obj.value=0

        }
    }

        //����ύ
    function toAddSub(){
      if(queryID.rows.length<2){
          alert("�㻹û��ѡ�����,�����ύ!")
        return false;
      }
      if(confirm("��ȷ��Ҫ��ʼ�������?\nѡ��ȷ���ύ�ó�ʼ����,ȡ������."))
        addForm.submit();
      else
          return false;
    }

    //��ʾҳ����ϸ��Ϣ��ť����
   function toGetForm(idValue){
         var url = "${ctx}/PartUseAction.do?method=showOneUse&useid=" + idValue;
        self.location.replace(url);
    }

   function  addGoBack(){
         var url = "${ctx}/PartStorageAction.do?method=showAllStorage";
        self.location.replace(url);
    }

	//���ص��ϼ���ѯҳ��
	function goquery(){
		window.location.href = "${ctx}/PartStorageAction.do?method=queryShow";
	}


    //ѡ������
    function GetSelectDateTHIS(strID) {
        document.all.item(strID).value = getPopDate(document.all.item(strID).value);

       //��ʼʱ��
    	var yb = queryForm2.begintime.value.substring(0,4);
		var mb = parseInt(queryForm2.begintime.value.substring(5,7) ,10);
		var db = parseInt(queryForm2.begintime.value.substring(8,10),10);
    	//����ʱ��
    	var ye = queryForm2.endtime.value.substring(0,4);
		var me = parseInt(queryForm2.endtime.value.substring(5,7) ,10);
		var de = parseInt(queryForm2.endtime.value.substring(8,10),10);
    	if(yb == "" && ye != "" ) {
	  		alert("�������ѯ�Ŀ�ʼʱ��!");      		
      		queryForm2.begintime.focus();
      		return false;
		}
		// �ڿ�ʼ�ͽ���ʱ�䶼���������������ܿ���ȵ��ж�
		if(yb != "" && ye != "") {
			//if(yb!=ye){
      		//	alert("��ѯʱ��β��ܿ����!");
      		//	document.all.item(strID).value="";
      		//	queryForm2.endtime.focus();
      		//	return false;
    		//}
			if(ye < yb) {
				alert("��ѯ�������ڲ���С�ڿ�ʼ����!");
      			document.all.item(strID).value="";
      			queryForm2.endtime.focus();
      			return false;
			}
			if((ye == yb ) && (me < mb  || de < db)) {
      			alert("��ѯ�������ڲ���С�ڿ�ʼ����!");
     		 	document.all.item(strID).value="";
      			queryForm2.endtime.focus();
      			return false;
    		}
		}
        return true;
    }
</script>


<title>
partRequisition
</title>
</head>
<body>

     <!--��ʾ���п��-->
    <logic:equal value="st1" scope="session"name="type">
        <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
               <apptag:checkpower thirdmould="80601" ishead="1">
                <jsp:forward page="/globinfo/powererror.jsp"/>
            </apptag:checkpower>
            <br />
            <template:titile value="���Ͽ��һ����" />
            <display:table name="sessionScope.storageinfo" id="currentRowObject"  pagesize="18" style="align:center">
                <display:column property="contractorname" title="��λ����" maxLength="10" style="align:center" />
                  <display:column property="name" title="��������" maxLength="10" style="align:center" />
                <display:column property="unit" title="������λ" maxLength="20" style="align:center" />
                <display:column property="type" title="����ͺ�" maxLength="10" />
                <display:column property="newshould" title="�²���Ӧ�п��" maxLength="20"style="align:center"/>
                <display:column property="newesse" title="�²��Ͽ��" maxLength="20"style="align:center"/>
                <display:column property="oldnumber" title="�ɲ��Ͽ��"maxLength="20"style="align:center" />


             </display:table>
           	 <logic:notEmpty name="storageinfo">
             	<html:link action="/PartStorageAction.do?method=exportStorageResult">����ΪExcel�ļ�</html:link><br>
             </logic:notEmpty>
             <div align="center"><html:button property="action" styleClass="button" onclick="goquery()" >����</html:button></div>
    </logic:equal>
     <!--��ѯҳ��-->
    <logic:equal value="st3" scope="session"name="type" >
        <apptag:checkpower thirdmould="80303" ishead="1">
            <jsp:forward page="/globinfo/powererror.jsp"/>
        </apptag:checkpower>
          <br />
        <template:titile value="�������ҿ��"/>
        <html:form action="/PartStorageAction?method=queryExec"   styleId="queryForm2" >
            <template:formTable namewidth="200"  contentwidth="200">
                   <logic:equal value="1" name="unittype">
                           <logic:present name="contractorname">
                              <template:formTr name="��λ����"  >
                                <select name="id"   class="inputtext" style="width:180px" >
                                    <option  value="">ѡ�����е�λ</option>
                                        <logic:iterate id="contractornameId" name="contractorname">
                                            <option value="<bean:write name="contractornameId" property="contractorid"/>"><bean:write name="contractornameId" property="contractorname"/></option>
                                        </logic:iterate>
                                </select>
                            </template:formTr>
                        </logic:present>
                    </logic:equal>
               <template:formTr name="��������"  >
                    <select name="name"   class="inputtext" style="width:180px" >
                        <option  value="">ѡ�����в���</option>
                          <logic:present name="partname">
                            <logic:iterate id="partnameId" name="partname">
                                <option value="<bean:write name="partnameId" property="name"/>"><bean:write name="partnameId" property="name"/></option>
                            </logic:iterate>
                        </logic:present>
                    </select>
                </template:formTr>
                  <template:formTr name="��������" >
                    <select name="type" style="width:180px" class="inputtext"  >
                        <option  value="">ѡ����������</option>
                          <logic:present name="parttype">
                            <logic:iterate id="parttypeId" name="parttype">
                                <option value="<bean:write name="parttypeId"  property="type"/>"><bean:write name="parttypeId"  property="type"/></option>
                            </logic:iterate>
                        </logic:present>
                    </select>
                </template:formTr >
                <template:formTr name="�²��Ͽ��" >
                    <b>����&nbsp;&nbsp;</b><input   type="text" name="newlownumber" id="newlow" class="inputtext" size="10" maxlength="6"   onkeyup="valiDigit(id)"/>
                    <b>С��&nbsp;&nbsp;</b><input   type="text" name="newhignumber" id="newhig" class="inputtext" size="10" maxlength="6"   onkeyup="valiDigit(id)"/>
                </template:formTr >
                 <template:formTr name="�ɲ��Ͽ��" >
                    <b>����&nbsp;&nbsp;</b><input   type="text" name="oldlownumber" id="oldlow" class="inputtext" size="10" maxlength="6"  onkeyup="valiDigit(id)"/>
                    <b>С��&nbsp;&nbsp;</b><input   type="text" name="oldhignumber" id="oldhig" class="inputtext" size="10" maxlength="6"   onkeyup="valiDigit(id)"/>
                </template:formTr >
                <template:formSubmit>
                              <td>
                              <html:submit property="action" styleClass="button" >����</html:submit>
                              </td>
                              <td>
                                    <html:reset property="action" styleClass="button" >����</html:reset>
                              </td>
                        </template:formSubmit>
            </template:formTable>

        </html:form>
    </logic:equal>


    <!--��ʼ�����-->
    <logic:equal value="st2" scope="session" name="type">
        <link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
          <apptag:checkpower thirdmould="80601" ishead="1">
                <jsp:forward page="/globinfo/powererror.jsp"/>
        </apptag:checkpower>
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
        <template:titile value="��ʼ�����Ͽ��"/>
        <html:form action="/PartStorageAction?method=initStorage" styleId="addForm">
            <input  type="hidden" name="contractorid" value="<bean:write name="deptid"/>"/>
              <input type="hidden" name="useuserid" value="<bean:write name="userid" />"/>
            <table align="center" width="90%" border="0">
                <tr >
                    <td width="10%" height="25"><b>��λ����:</b></td>
                    <td align="left" width="15%"><bean:write name="deptname"/></td>
                    <td  align="right" width="10%"><b>������:</b></td>
                    <td align="left" width="15%"><bean:write name="username"/></td>
                    <td width="10%" align="right"><b>����ʱ��:</b></td>
                    <td align="left" ><bean:write name="date"/></td>
                </tr>
                <tr >
                    <td width="10%" height="25" valign="top"><b>��ע��Ϣ:</b></td>
                    <td align="left" colspan="5">
                        ��ʼ�����Ͽ����ָ�������������趨Ϊָ����ֵ,�ù��ܿ�������������:<br />
                        1��ϵͳ�տ�ʼ���е�ʱ���������еĿ����.<br />
                        2���ڿ���̵��и��ݿ��ʵ��ֵ�趨�����.
                    </td>

                </tr>
                <tr>
                    <td colspan="6">
                        <hr /><br />��ѡ�����:
                    </td>
                </tr>
            </table>
            <table    id="queryID"  border="1" align="center" cellpadding="3" cellspacing="0" width="90%">
                <tr>
                    <th  width="35%" class="thlist" align="center">��������</th>
                    <th  width="10%" class="thlist" align="center">������λ</th>
                    <th  width="25%" class="thlist" align="center">����ͺ�</th>
                    <th  width="10%" class="thlist" align="center">�²��ϳ�ֵ</th>
                    <th  width="10%" class="thlist" align="center">�ɲ��ϳ�ֵ</th>
                    <th  width="6%" class="thlist" align="center">����</th>
                </tr>
            </table>
            <p align="center">
                              <html:button property="action" styleClass="button" onclick="addRow()">��Ӳ���</html:button>
                                    <html:button property="action" styleClass="button"  onclick="toAddSub()">�ύ��ʼ��</html:button>
                                    <html:button property="action" styleClass="button" onclick="addGoBack()" >����	</html:button>
            </p>

        </html:form>
    </logic:equal>

    <logic:equal value="showst1" scope="session"name="type">
        <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
               <apptag:checkpower thirdmould="80609" ishead="1">
                <jsp:forward page="/globinfo/powererror.jsp"/>
            </apptag:checkpower>
            <br />
            <template:titile value="���Ͽ��һ����" />
            <display:table name="sessionScope.storageinfo" id="currentRowObject"  pagesize="18" style="align:center">
                <display:column property="contractorname" title="��λ����" maxLength="10" style="align:center" />
                  <display:column property="name" title="��������" maxLength="10" style="align:center" />
                <display:column property="unit" title="������λ" maxLength="20" style="align:center"/>
                <display:column property="type" title="����ͺ�" maxLength="10" />
                <display:column property="newshould" title="�²���Ӧ�п��" maxLength="20" style="align:center"/>
                <display:column property="newesse" title="�²��Ͽ��" maxLength="20" style="align:center"/>
                <display:column property="oldnumber" title="�ɲ��Ͽ��"maxLength="20" style="align:center" />


             </display:table>
             <logic:notEmpty name="storageinfo">
               <html:link action="/PartStorageAction.do?method=exportStorageResult">����ΪExcel�ļ�</html:link><br>
             </logic:notEmpty>
             <div align="center"><html:button property="action" styleClass="button" onclick="goquery()" >����</html:button></div>
    </logic:equal>
   
    <iframe id="hiddenIframe" style="display:none"></iframe>
</body>
</html>

