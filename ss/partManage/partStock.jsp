<%@include file="/common/header.jsp"%>

<html>
<head>
<script type="" language="javascript">
    var rowArr = new Array();//һ�в��ϵ���Ϣ
    var infoArr = new Array();//���в��ϵ���Ϣ

    //��ʼ������
    function initArray(id,name,unit,type){
        rowArr[0] = id;
        rowArr[1] = name;
        rowArr[2] = unit;
        rowArr[3] = type;
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
        var selectValue = this.value;
		var tmpValue;
        for(i =0; i<queryID.rows.length;i++){
            if(queryID.rows[i].id == rowid){
                tr = queryID.rows[i];
            } else {
				if(document.getElementById("select"+queryID.rows[i].id) != null ){
            		tmpValue = document.getElementById("select"+queryID.rows[i].id).value;
					if(tmpValue == selectValue) {
						alert("�ò����Ѿ�ѡ����ˣ�������ѡ�����ɲ��ϣ�");
						document.getElementById("select"+rowid).value = "";
						return;
					}
				}
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
        tt.size = "27";
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

        //����һ�������
        var t1 = document.createElement("input");
        t1.name = "oldnumber"
        t1.id = "text" + onerow.id;
        t1.value= "0";
        t1.maxLength = "6";
        t1.size = "8";
        t1.onblur = valiD;
        t1.style.background="#C6D6E2"
        t1.style.font.size="12px";

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
        cell2.innerText="ûѡ�����";//����
        cell3.innerText="ûѡ�����";//����
        cell4.appendChild(t1);//����
        cell5.appendChild(b1);
    }
     function valiD(){
          var mysplit = /^\d{1,6}$/;
        if(mysplit.test(this.value)){
          return true;
        }
        else{
            alert("����д�Ĳ�������,����������");
            this.focus();
            this.value=0;
            return false;
        }
    }



    //����ύ
    function toAddSub(){
      if(queryID.rows.length<2){
          alert("�㻹û��ѡ�����,�����ύ!")
        return false;
      }

      if(confirm("��ȷ��Ҫ�ύ������ⵥ��?"))
        addForm.submit();
      else
         return false;
    }

    //��ʾ��������뵥ҳ��,�������뵥��ť����
   function toStockFormExec(idValue){
         var url = "${ctx}/PartStockAction.do?method=doStockPartForOneReq&reid=" + idValue;
        self.location.replace(url);
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
            return false;
        }
    }

    function toShowOneStock(idValue){
         var url = "${ctx}/PartStockAction.do?method=doShowOneStock&stockid=" + idValue;
        self.location.replace(url);
    }
   function toStockForm(){
           var url = "${ctx}/PartStockAction.do?method=dokShowStockInfo";
        self.location.replace(url);
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
     function toShowOneOldStock(idValue){
         var url = "${ctx}/PartStockAction.do?method=showOneOfStock&oldid=" + idValue;
        self.location.replace(url);
    }
   function toShowAllOldStock(){
           var url = "${ctx}/PartStockAction.do?method=showAllOldStock";
        self.location.replace(url);
   }
   function subForStock(){
           if(confirm("�ύ�󲻿ɸ���,ȷ���ύ��?"))
            StockAddForm.submit();
        else
        return false;

   }
   function toExpotr(){

     var stockuserid = queryForm2.stockuserid.value;
     var reid = queryForm2.reid.value;
     var begintime = queryForm2.begintime.value;
     var endtime = queryForm2.endtime.value;
     var url = "${ctx}/PartStockAction.do?method=exportStorkList&stockuserid="+stockuserid+"&reid="+reid+"&begintime="+begintime+"&endtime="+endtime;
     self.location.replace(url);

   }
   function toExpotrOldUse(){

     var olduserid = queryForm2.olduserid.value;
     var reid = queryForm2.reid.value;
     var begintime = queryForm2.begintime.value;
     var endtime = queryForm2.endtime.value;
     var url = "${ctx}/PartStockAction.do?method=exportOldUseList&olduserid="+olduserid+"&reid="+reid+"&begintime="+begintime+"&endtime="+endtime;
     self.location.replace(url);

   }

function textCounter(field,maxlimit) {   
  if(field.value.length > maxlimit)     
  	field.value = field.value.substring(0,maxlimit);   
   
}
</script>
</head>
<body>
  <!--��ʾ���������뵥-->
<logic:equal value="stock2" scope="session" name="type">
  <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
  <apptag:checkpower thirdmould="80401" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="�����������뵥һ����"/>
  <display:table name="sessionScope.reqinfo" id="currentRowObject" pagesize="18">
    <%
      BasicDynaBean objectA1 = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      String idA1 = "";
      String reason = "";
      String auditresult = "";
      String reasonTitle = "";
      if(objectA1 != null) {
      	idA1 = (String) objectA1.get("reid");
      	reasonTitle = (String)objectA1.get("reason");
    	 if(reasonTitle != null && reasonTitle.length() > 15) {
    		reason = reasonTitle.substring(0,15) + "...";
    	 } else {
    		reason = reasonTitle;
    	 }
      	
      	auditresult = (String)objectA1.get("auditresult");
      }
    %>
    <display:column media="html" title="���뵥��ˮ��" style="width:100px">    
      <a href="javascript:toStockFormExec('<%=idA1%>')"><%=idA1%></a>
    </display:column>
        <display:column media="html" title="����ԭ��">    
      <a href="javascript:toStockFormExec('<%=idA1%>')" title="<%=reasonTitle %>"><%=reason%></a>
    </display:column>
    <display:column property="contractorname" title="���뵥λ" maxLength="20" style="align:center"/>
    <display:column property="username" title="������" maxLength="20" style="align:center"/>
    <display:column property="time" title="����ʱ��" maxLength="20" style="align:center" style="width:120px"/>     
     <display:column media="html" title="����״̬" style="width:80px">    
  		<%if("������".equals(auditresult)) { %>    	
    		<font color="#0000CD" ><%=auditresult %></font>
    	<%} else if("ͨ������".equals(auditresult)) { %>
    		<font color="#008000" ><%=auditresult %></font>
    	<%} else if("��������".equals(auditresult)) {%>
    		<font color="red" ><%=auditresult %></font>
    	<%} else if("�����".equals(auditresult)) {%>
    		<font color="#FFA500" ><%=auditresult %></font>
    	<%} else {%>
    		<%=auditresult %>
    	<%} %>
    </display:column> 
  </display:table>
</logic:equal>
  <!--Ϊ�������뵥���-->
<logic:equal value="stock20" scope="session" name="type">
  <apptag:checkpower thirdmould="80401" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="ά���������"/>
  <html:form action="/PartStockAction?method=doStockPart" styleId="StockAddForm">
    <input type="hidden" name="reid" value="<bean:write name="reqinfo" property="reid"/>"/>
    <input type="hidden" name="stockuserid" value="<bean:write name="userid" />"/>
    <input type="hidden" name="contractorid" value="<bean:write name="deptid" />"/>
    <table class="tabout" align="center" width="90%" border="0">
      <tr class=trcolor>
        <td>
          <b>��������:</b>
        </td>
        <td colspan="5">
          <bean:write name="reqinfo" property="reason"/>
        </td>
      </tr>
      <tr class=trcolor>
        <td>
          <b>���뱸ע:</b>
        </td>
        <td colspan="5">
          <bean:write name="reqinfo" property="remark"/>
        </td>
      </tr>
      <tr class=trcolor>
        <td width="8%">
          <b>��λ����:</b>
        </td>
        <td align="left" width="25%">
          <bean:write name="reqinfo" property="contractorname"/>
        </td>
        <td align="right" width="8%">
          <b>������:</b>
        </td>
        <td align="left" width="15%">
          <bean:write name="reqinfo" property="username"/>
        </td>
        <td width="10%" align="right">
          <b>����ʱ��:</b>
        </td>
        <td align="left" >
          <bean:write name="reqinfo" property="time"/>
        </td>
      </tr>
      <tr class=trcolor>
        <td colspan="6" valign="bottom">
          <hr/>
          </br>
          <b>����д�����뵥�����������:</b>
        </td>
      </tr>
      <tr class=trcolor>
      	<td colspan="6">
      		<table width="100%" id="queryID" border="1" align="center" cellpadding="3" cellspacing="0">
		      <tr>
		        <th width="250" class="thlist" align="center">��������</th>
		        <th width="60" class="thlist" align="center">������λ</th>
		        <th width="180" class="thlist" align="center">����ͺ�</th>
		        <th width="60" class="thlist" align="center">��������</th>
		        <th width="60" class="thlist" align="center">�������</th>
		      </tr>
		      <logic:present name="reqpartinfo">
		        <logic:iterate id="reqid" name="reqpartinfo">
		          <tr id="<bean:write name="reqid" property="id"/>">
		            <td>
		              <input type="hidden" name="id" value="<bean:write name="reqid" property="id"/>"/>
		              <bean:write name="reqid" property="name"/> -- <bean:write name="reqid" property="type"/>
		            </td>
		            <td>
		              <bean:write name="reqid" property="unit"/>
		            </td>
		            <td>
		              <bean:write name="reqid" property="type"/>
		            </td>
		            <td>
		              <bean:write name="reqid" property="audnumber"/>
		            </td>
		            <td>
		              <input id="Stocknumber" onblur="valiDigit(id)" maxlength="6" align="right" class="inputtext" size="8" name="stocknumber" value="<bean:write name="reqid" property="stocknumber"/>"/>
		            </td>
		          </tr>
		        </logic:iterate>
		      </logic:present>
		    </table>
		    <br/>
    <br/></td></tr>
     <tr class=trcolor>
      	<td colspan="6">
    <table class="tabout" align="center" width="100%" border="0">
      <!--
        <tr>
        <td align="right">
        <b>������:</b>
        </td>
        <td>
        <input type="text" name="keeperid" class="inputtext"  maxlength="5"  value=""/>
        </td>
        <td align="right">
        <b>����ص�</b>:
        </td>
        <td colspan="3">
        <input type="text" name="stockaddress" class="inputtext" maxlength="20"  style="width:280px" value=""/>
        </td>
        </tr>
      -->
      <tr>
        <td width="60" height="30">
          <b>��ⵥλ:</b>
        </td>
        <td align="left" width="120">
          <bean:write name="deptname"/>
        </td>
        <td align="right" width="100">
          <b>��������:</b>
        </td>
        <td align="left" width="100">
          <bean:write name="username"/>
        </td>
        <td width="70" align="right">
          <b>���ʱ��:</b>
        </td>
        <td align="left" width="100">
          <bean:write name="date"/>
        </td>
      </tr>
      <tr>
        <td colspan="6" valign="bottom">
          <hr/>
        </td>
      </tr>    
     
    </table>
      	</td>
      </tr>
    </table>
    <p align="center">
    	 <html:button property="action" styleClass="button" onclick="subForStock()">�ύ��ⵥ</html:button>
    </p>
    
  </html:form>
</logic:equal>
  <!--��ʾ������ⵥ-->
<logic:equal value="stock1" scope="session" name="type">
  <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
  <apptag:checkpower thirdmould="80403" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br>
  <template:titile value="ά��������ⵥһ����"/>
  <display:table name="sessionScope.stockinfo" requestURI="${ctx}/PartStockAction.do?method=queryExecForStock" id="currentRowObject" pagesize="18">
  	<display:column media="html" title="��ⵥ��ˮ��" style="width:100px">
    <%
      BasicDynaBean objectb1 = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      String idb1 = (String) objectb1.get("stockid");
    %>
      <a href="javascript:toShowOneStock('<%=idb1%>')"><%=idb1%></a>
    </display:column>
    <display:column property="contractorname" title="��ⵥλ" maxLength="30" style="align:center"/>
    <display:column property="username" title="�����" maxLength="8" style="align:center"/>
    <display:column property="stocktime" title="���ʱ��" maxLength="20" style="align:center"/>
    <display:column property="reid" title="��Ӧ���뵥��ˮ��" maxLength="15" style="align:center;width:120px" />
    <display:column property="reason" title="��Ӧ����ԭ��" maxLength="15" style="align:center"/>
    
  </display:table>
  
  
   <logic:notEmpty name="stockinfo">
         <html:link action="/PartStockAction.do?method=exportStockResult">����ΪExcel�ļ�</html:link><br>
   </logic:notEmpty>
   <div align="center"><html:button property="action" styleClass="button" onclick="goqueryStock()" >����</html:button></div>
</logic:equal>
  <!--��ʾ������ⵥ��ϸ��Ϣ-->
<logic:equal value="stock10" scope="session" name="type">
  <apptag:checkpower thirdmould="80403" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="ά��������ⵥ��ϸ��Ϣ"/>
  <html:form action="/PartStockAction?method=doStockPart" styleId="addForm">
    <table class="tabout" align="center" width="90%" border="0">
      <tr class=trcolor>
        <td width="100">
          <b>��ⵥ��:</b>
        </td>
        <td align="left">
          <bean:write name="stockinfo" property="stockid"/>
        </td>
        <td width="80">
          <b>���ʱ��:</b>
        </td>
        <td>
          <bean:write name="stockinfo" property="stocktime"/>
        </td>
      </tr>
      <tr class=trcolor>
        <td>
          <b>�� �� ��:</b>
        </td>
        <td>
          <bean:write name="stockinfo" property="username"/>
        </td>
        <td>
          <b>��ⵥλ:</b>
        </td>
        <td>
          <bean:write name="stockinfo" property="contractorname"/>
        </td>
      </tr>
      <tr class=trcolor>
        <td>
          <b>���뵥��:</b>
        </td>
        <td>
          <bean:write name="stockinfo" property="reid"/>
        </td>
        <td>
          <b>����ԭ��:</b>
        </td>
        <td>
          <bean:write name="stockinfo" property="reason"/>
        </td>
      </tr>
      <!--
        <tr>
        <td ><b>����ص�:</b></td>
        <td > <bean:write name="stockinfo" property="stockaddress"/></td>
        <td ><b>������Ա:</b></td>
        <td > <bean:write name="stockinfo" property="keeperid"/></td>
        </tr>
      -->
      <tr class=trcolor>
        <td colspan="4" valign="bottom">
          <br/>
          <b>����ⵥ�����ϵ���ϸ��Ϣ:</b>
        </td>
      </tr>
      
      <tr class=trcolor>
        <td colspan="4" valign="bottom">
         	<table width="100%"id="queryID" border="1" align="center" cellpadding="3" cellspacing="0">
		      <tr>
		        <th width="42%" class="thlist" align="center">��������</th>
		        <th width="15%" class="thlist" align="center">������λ</th>
		        <th width="33%" class="thlist" align="center">����ͺ�</th>
		        <th width="10%" class="thlist" align="center">�������</th>
		      </tr>
		      <logic:present name="stockpartinfo">
		        <logic:iterate id="reqid" name="stockpartinfo">
		          <tr id="<bean:write name="reqid" />">
		            <td>
		              <bean:write name="reqid" property="name"/> -- <bean:write name="reqid" property="type"/>
		            </td>
		            <td>
		              <bean:write name="reqid" property="unit"/>
		            </td>
		            <td>
		              <bean:write name="reqid" property="type"/>
		            </td>
		            <td>
		              <bean:write name="reqid" property="stocknumber"/>
		            </td>
		          </tr>
		        </logic:iterate>
		      </logic:present>
		    </table>
        </td>
      </tr>
    </table>
    
    <p align="center">
     <input type="button" value="����" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"/>
    </p>
  </html:form>
</logic:equal>
  <!--���,������ѯҳ��-->
<logic:equal value="stock3" name="type" scope="session">
  <apptag:checkpower thirdmould="80303" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="����������ⵥ"/>
  <html:form action="/PartStockAction?method=queryExecForStock" styleId="queryForm2">
    <template:formTable namewidth="200" contentwidth="200">
      <template:formTr name="�� �� ��">
        <input type="text" name="stockuserid" class="inputtext" style="width:180px">
      </template:formTr>
      <!--
        <template:formTr name="��Ӧ���뵥���"  >
        <select name="reid"   class="inputtext" style="width:180px" >
        <option  value="">ѡ�����б��</option>
        <logic:present name="reqreid">
        <logic:iterate id="reqreidId" name="reqreid">
        <option value="<bean:write name="reqreidId" property="reid"/>"><bean:write name="reqreidId" property="reid"/></option>
        </logic:iterate>
        </logic:present>
        </select>
        </template:formTr>
      -->
      <template:formTr name="��ʼʱ��">
        <input id="begin" type="text" readonly="readonly" name="begintime" class="inputtext" style="width:150"/>
        <INPUT TYPE='BUTTON' VALUE='����' readonly="readonly" ID='btn' onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';">
      </template:formTr>
      <template:formTr name="����ʱ��">
        <input id="end" type="text" name="endtime" class="inputtext" style="width:150"/>
        <INPUT TYPE='BUTTON' VALUE='����' ID='btn' onclick="GetSelectDateTHIS('end')" STYLE="font:'normal small-caps 6pt serif';">
      </template:formTr>
      <template:formSubmit>
        <td>
          <html:submit property="action" styleClass="button">����</html:submit>
        </td>
        <td>
          <html:reset property="action" styleClass="button">����</html:reset>
        </td>
      </template:formSubmit>
    </template:formTable>
  </html:form>
</logic:equal>
  <!--///////////��������ѯ////////-->
  <!--�������ͳ�Ʋ�ѯҳ��-->
<logic:equal value="stock4" name="type" scope="session">
  <apptag:checkpower thirdmould="80307" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="��ѯ����������"/>
  <html:form action="/PartStockAction?method=dostat" styleId="queryForm2">
    <template:formTable namewidth="200" contentwidth="200">
      <logic:notEqual value="22" name="usetype">
        <template:formTr name="��ⵥλ">
          <select name="contractorid" class="inputtext" style="width:180px">
            <option value="">ѡ�����е�λ</option>
            <logic:present name="deptinfo">
              <logic:iterate id="deptinfoId" name="deptinfo">
                <option value="<bean:write name="deptinfoId" property="contractorid"/>">
                  <bean:write name="deptinfoId" property="contractorname"/>
                </option>
              </logic:iterate>
            </logic:present>
          </select>
        </template:formTr>
      </logic:notEqual>
      <template:formTr name="��������">
        <select name="name" style="width:180px" class="inputtext">
          <option value="">ѡ����������</option>
          <logic:present name="nameinfo">
            <logic:iterate id="nameinfoId" name="nameinfo">
              <option value="<bean:write name="nameinfoId"  property="name"/>">
                <bean:write name="nameinfoId" property="name"/>
              </option>
            </logic:iterate>
          </logic:present>
        </select>
      </template:formTr>
      <template:formTr name="�����ͺ�">
        <select name="type" style="width:180px" class="inputtext">
          <option value="">ѡ�������ͺ�</option>
          <logic:present name="typeinfo">
            <logic:iterate id="typeinfoId" name="typeinfo">
              <option value="<bean:write name="typeinfoId"  property="type"/>">
                <bean:write name="typeinfoId" property="type"/>
              </option>
            </logic:iterate>
          </logic:present>
        </select>
      </template:formTr>
      <template:formTr name="��ʼʱ��">
        <input id="begin" type="text" readonly="readonly" name="begintime" class="inputtext" style="width:150"/>
        <INPUT TYPE='BUTTON' VALUE='����' ID='btn' onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';">
      </template:formTr>
      <template:formTr name="����ʱ��">
        <input id="end" type="text" readonly="readonly" name="endtime" class="inputtext" style="width:150"/>
        <INPUT TYPE='BUTTON' VALUE='����' ID='btn' onclick="GetSelectDateTHIS('end')" STYLE="font:'normal small-caps 6pt serif';">
      </template:formTr>
      <template:formSubmit>
        <td>
          <html:submit property="action" styleClass="button">����</html:submit>
        </td>
        <td>
          <html:reset property="action" styleClass="button">����</html:reset>
        </td>
      </template:formSubmit>
    </template:formTable>
  </html:form>
</logic:equal>
  <!--��ʾ��������ѯ���-->
<logic:equal value="use70" name="type" scope="session">
  <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
  <br/>
  <template:titile value="�������һ����"/>
  <display:table name="sessionScope.useinfo" id="currentRowObject" pagesize="18">
    <display:column property="contractorname" title="��λ����" maxLength="10" style="align:center"/>
    <display:column property="name" title="��������" maxLength="20" style="align:center"/>
    <display:column property="unit" title="������λ" maxLength="20" style="align:center"/>
    <display:column property="type" title="�����ͺ�" maxLength="20" style="align:center"/>
    <display:column property="stocktime" title="���ʱ��" maxLength="12" style="align:center"/>
    <display:column property="stocknumber" title="�������" maxLength="10"/>
  </display:table>
   <logic:notEmpty name="useinfo">
       <html:link action="/PartStockAction.do?method=exportStock">����ΪExcel�ļ�</html:link><br>
   </logic:notEmpty>
   <div align="center"><html:button property="action" styleClass="button" onclick="goquery()" >����</html:button></div>
  
</logic:equal>


  <!--////////////////////////// For OldStock  ///////////////////////////////////////-->
  <!--��д���ɲ�����ⵥ-->
<logic:equal value="old2" scope="session" name="type">
  <link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
  <apptag:checkpower thirdmould="80501" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <logic:present name="baseinfo">
    <logic:iterate id="baseinfoId" name="baseinfo">
<script type="" language="javascript">
                        initArray("<bean:write name="baseinfoId" property="id"/>","<bean:write name="baseinfoId" property="name"/>","<bean:write name="baseinfoId" property="unit"/>","<bean:write name="baseinfoId" property="type"/>");
                    </script>
    </logic:iterate>
  </logic:present>
  <br/>
  <template:titile value="��д���ɲ�����ⵥ"/>
  <html:form action="/PartStockAction?method=addOLdStock" styleId="addForm">
    <input type="hidden" name="contractorid" value="<bean:write name="deptid"/>"/>
    <input type="hidden" name="olduserid" value="<bean:write name="userid" />"/>
    <table  class="tabout" align="center" width="90%" border="0">
      <tr class=trcolor>
        <td width="60" height="25">
          <b>��λ����:</b>
        </td>
        <td align="left" width="100">
          <bean:write name="deptname"/>
        </td>
        <td align="right" width="50">
          <b>������:</b>
        </td>
        <td align="left" width="100">
          <bean:write name="username"/>
        </td>
        <td width="100" align="right">
          <b>��ʼ���ʱ��:</b>
        </td>
        <td align="left" width="100">
          <bean:write name="date"/>
        </td>
      </tr>
      <tr class=trcolor>
        <td height="25">
          <b>������Դ:</b>
        </td>
        <td colspan="5">
          <html:text property="oldreason" styleClass="inputtext" style="width:88%;" maxlength="24" value="����д���ɲ������ںδ�.">          </html:text>
        </td>
      </tr>
      <tr class=trcolor>
        <td height="25">
          <b>��ע��Ϣ:</b>
        </td>
        <td colspan="5">
          <html:textarea property="oldremark" styleClass="inputtextarea" style="width:88%;" onkeydown="textCounter(this,254)" onkeyup="textCounter(this,254)" value="����д���ɲ�������˵����Ϣ.">          </html:textarea>
        </td>
      </tr>
      <tr class=trcolor>
        <td colspan="6">
          <hr/>
          <br>
          ��ѡ�����:
        </td>
      </tr>
      
      <tr class=trcolor>
        <td colspan="6">
         	 <table width="100%"  id="queryID"  border="1" align="center" cellpadding="3" cellspacing="0">
		      <tr>
		        <th width="40%" class="thlist" align="center">��������</th>
		        <th width="12%" class="thlist" align="center">������λ</th>
		        <th width="28%" class="thlist" align="center">����ͺ�</th>
		        <th width="10%" class="thlist" align="center">�������</th>
		        <th width="10%" class="thlist" align="center">����</th>
		      </tr>
		    </table>
        </td>
      </tr>
    </table>
   
    <p align="center">
      <html:button property="action" styleClass="button" onclick="addRow()">����²���</html:button>
      <html:button property="action" styleClass="button" onclick="toAddSub()">�ύ��ⵥ</html:button>
      <!-- <html:button property="action" styleClass="button" onclick="toShowAllOldStock()">����</html:button> -->
    </p>
  </html:form>
</logic:equal>
  <!--��ʾ��ⵥ-->
<logic:equal value="old1" scope="session" name="type">
  <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
  <apptag:checkpower thirdmould="80503" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="���ɲ�����ⵥһ����"/>
  <display:table name="sessionScope.oldinfo" requestURI="${ctx}/PartStockAction.do?method=queryExecForOldStock" id="currentRowObject" pagesize="18">
    <display:column media="html" title="������ⵥ��ˮ��" style="width:120px">
    <%
      BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      String id = (String) object.get("oldid");
    %>
      <a href="javascript:toShowOneOldStock('<%=id%>')"><%=id%></a>
    </display:column>
    <display:column property="contractorname" title="��λ����" maxLength="20" style="align:center"/>
    <display:column property="username" title="�����" maxLength="10" style="align:center"/>
    <display:column property="oldtime" title="���ʱ��" maxLength="15" style="align:center"/>
    <display:column property="oldreason" title="������Դ" maxLength="20"/>
    
  </display:table>
    <logic:notEmpty name="oldinfo">
         <html:link action="/PartStockAction.do?method=exportOldStockResult">����ΪExcel�ļ�</html:link>
   </logic:notEmpty>
   <div align="center"><html:button property="action" styleClass="button" onclick="gooldquery()" >����</html:button></div>

</logic:equal>
  <!--��ʾ���ɲ�����ⵥ����ϸ��Ϣ-->
<logic:equal value="old10" scope="session" name="type">
  <apptag:checkpower thirdmould="80503" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="���ɲ�����ⵥ��ϸ��Ϣ"/>
  <html:form action="/PartStockAction?method=doStockPart" styleId="addForm">
    <table align="center" width="90%" class="tabout" border="0">
      <tr class=trcolor>
        <td width="100">
          <b>��ⵥ���:</b>
        </td>
        <td align="left">
          <bean:write name="oldinfo" property="oldid"/>
        </td>
        <td width="80">
          <b>���ʱ��:</b>
        </td>
        <td>
          <bean:write name="oldinfo" property="oldtime"/>
        </td>
      </tr>
      <tr class=trcolor>
        <td>
          <b>���������:</b>
        </td>
        <td>
          <bean:write name="oldinfo" property="username"/>
        </td>
        <td>
          <b>��ⵥλ:</b>
        </td>
        <td>
          <bean:write name="oldinfo" property="contractorname"/>
        </td>
      </tr>
      <tr class=trcolor>
        <td>
          <b>������Դ:</b>
        </td>
        <td>
          <bean:write name="oldinfo" property="oldreason"/>
        </td>
        <td>
          <b>��ע��Ϣ:</b>
        </td>
        <td>
          <bean:write name="oldinfo" property="oldremark"/>
        </td>
      </tr>
      <tr class=trcolor>
        <td colspan="4" valign="bottom">
          <hr/>
          <br/>
          <b>����ⵥ�����ϵ���ϸ��Ϣ:</b>
        </td>
      </tr>
      <tr class=trcolor>
        <td colspan="4" valign="bottom">
          <table id="queryID"  width="100%" border="1" align="center" cellpadding="3" cellspacing="0">
      <tr>
        <th width="40%" class="thlist" align="center">��������</th>
        <th width="15%" class="thlist" align="center">������λ</th>
        <th width="30%" class="thlist" align="center">����ͺ�</th>
         <th width="15%" class="thlist" align="center">�������</th>
      </tr>
      <logic:present name="oldpartinfo">
        <logic:iterate id="reqid" name="oldpartinfo">
          <tr id="<bean:write name="reqid" />">
            <td>
                <bean:write name="reqid" property="name"/> -- <bean:write name="reqid" property="type"/>
            </td>
            <td>
                <bean:write name="reqid" property="unit"/>
            </td>
            <td>
                <bean:write name="reqid" property="type"/>
            </td>
            <td>
               <bean:write name="reqid" property="oldnumber"/>
            </td>
          </tr>
        </logic:iterate>
      </logic:present>
    </table>
        </td>
      </tr>
    </table>
    
    <p align="center">
      <input type="button" value="����" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"/>
    </p>
  </html:form>
</logic:equal>
  <!--���ɲ������,������ѯҳ��-->
<logic:equal value="old3" name="type" scope="session">
  <apptag:checkpower thirdmould="80503" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="�������������ɲ�����ⵥ"/>
  <html:form action="/PartStockAction?method=queryExecForOldStock" styleId="queryForm2">
    <template:formTable namewidth="200" contentwidth="200">
      <template:formTr name="���������">
        <input type="text" name="olduserid" class="inputtext" style="width:180px"/>
      </template:formTr>
      <template:formTr name="������Դ">
        <input type="text" name="oldreason" class="inputtext" style="width:180px"/>
      </template:formTr>
      <template:formTr name="��ʼʱ��">
        <input id="begin" type="text" readonly="readonly" name="begintime" class="inputtext" style="width:150"/>
        <INPUT TYPE='BUTTON' VALUE='����' readonly="readonly" ID='btn' onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';">
      </template:formTr>
      <template:formTr name="����ʱ��">
        <input id="end" type="text" name="endtime" class="inputtext" style="width:150"/>
        <INPUT TYPE='BUTTON' VALUE='����' ID='btn' onclick="GetSelectDateTHIS('end')" STYLE="font:'normal small-caps 6pt serif';">
      </template:formTr>
      <template:formSubmit>
        <td>
          <html:submit property="action" styleClass="button">����</html:submit>
        </td>
        <td>
          <html:reset property="action" styleClass="button">����</html:reset>
        </td>
      </template:formSubmit>
    </template:formTable>
  </html:form>
</logic:equal>
  <!--///////////���ɲ�������ѯ////////-->
  <!--���ɲ������ͳ�Ʋ�ѯҳ��-->
<logic:equal value="old4" name="type" scope="session">
  <apptag:checkpower thirdmould="80307" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="�����������ɲ���������"/>
  <html:form action="/PartStockAction?method=dostatOld" styleId="queryForm2">
    <template:formTable namewidth="200" contentwidth="200">
      <logic:notEqual value="22" name="usetype">
        <template:formTr name="��ⵥλ">
          <select name="contractorid" class="inputtext" style="width:180px">
            <option value="">ѡ�����е�λ</option>
            <logic:present name="deptinfo">
              <logic:iterate id="deptinfoId" name="deptinfo">
                <option value="<bean:write name="deptinfoId" property="contractorid"/>">
                  <bean:write name="deptinfoId" property="contractorname"/>
                </option>
              </logic:iterate>
            </logic:present>
          </select>
        </template:formTr>
      </logic:notEqual>
      <template:formTr name="��������">
        <select name="name" style="width:180px" class="inputtext">
          <option value="">ѡ����������</option>
          <logic:present name="nameinfo">
            <logic:iterate id="nameinfoId" name="nameinfo">
              <option value="<bean:write name="nameinfoId"  property="name"/>">
                <bean:write name="nameinfoId" property="name"/>
              </option>
            </logic:iterate>
          </logic:present>
        </select>
      </template:formTr>
      <template:formTr name="�����ͺ�">
        <select name="type" style="width:180px" class="inputtext">
          <option value="">ѡ�������ͺ�</option>
          <logic:present name="typeinfo">
            <logic:iterate id="typeinfoId" name="typeinfo">
              <option value="<bean:write name="typeinfoId"  property="type"/>">
                <bean:write name="typeinfoId" property="type"/>
              </option>
            </logic:iterate>
          </logic:present>
        </select>
      </template:formTr>
      <template:formTr name="��ʼʱ��">
        <input id="begin" type="text" readonly="readonly" name="begintime" class="inputtext" style="width:150"/>
        <INPUT TYPE='BUTTON' VALUE='����' ID='btn' onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';">
      </template:formTr>
      <template:formTr name="����ʱ��">
        <input id="end" type="text" readonly="readonly" name="endtime" class="inputtext" style="width:150"/>
        <INPUT TYPE='BUTTON' VALUE='����' ID='btn' onclick="GetSelectDateTHIS('end')" STYLE="font:'normal small-caps 6pt serif';">
      </template:formTr>
      <template:formSubmit>
        <td>
          <html:submit property="action" styleClass="button">����</html:submit>
        </td>
        <td>
          <html:reset property="action" styleClass="button">����</html:reset>
        </td>
      </template:formSubmit>
    </template:formTable>
  </html:form>
</logic:equal>
  <!--��ʾ���ɲ�������ѯ���-->
<logic:equal value="old40" name="type" scope="session">
  <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
  <br/>
  <template:titile value="���ɲ������һ����"/>
  <display:table name="sessionScope.useinfo" id="currentRowObject" pagesize="18">
    <display:column property="contractorname" title="��λ����" maxLength="10" style="align:center"/>
    <display:column property="name" title="��������" maxLength="20" style="align:center"/>
    <display:column property="unit" title="������λ" maxLength="20" style="align:center"/>
    <display:column property="type" title="�����ͺ�" maxLength="20" style="align:center"/>
    <display:column property="stocktime" title="���ʱ��" maxLength="12" style="align:center"/>
    <display:column property="oldnumber" title="�������" maxLength="10"/>
  </display:table>
  
   <logic:notEmpty name="useinfo">
         <html:link action="/PartStockAction.do?method=exportOldStock">����ΪExcel�ļ�</html:link><br>
   </logic:notEmpty>
   <div align="center"><html:button property="action" styleClass="button" onclick="goOldquery()" >����</html:button></div>
 
</logic:equal>

<logic:equal value="showstock1" scope="session" name="type">
  <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
 
  <br>
  <template:titile value="ά��������ⵥһ����"/>
  <display:table name="sessionScope.stockinfo" id="currentRowObject" pagesize="18">
  	 <display:column media="html" title="��ⵥ��ˮ��" style="width:100px">
    <%
      BasicDynaBean objectb1 = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      String idb1 = (String) objectb1.get("stockid");
    %>
      <a href="javascript:toShowOneStock('<%=idb1%>')"><%=idb1%></a>
    </display:column>
    <display:column property="contractorname" title="��ⵥλ" maxLength="30" style="align:center"/>
    <display:column property="username" title="�����" maxLength="8" style="align:center"/>
    <display:column property="stocktime" title="���ʱ��" maxLength="20" style="align:center"/>
    <display:column property="reid" title="��Ӧ���뵥��ˮ��" maxLength="15" style="align:center" style="width:120px"/>
    <display:column property="reason" title="��Ӧ����ԭ��" maxLength="15" style="align:center"/>   
  </display:table>
  
    <logic:notEmpty name="stockinfo">
         <html:link action="/PartStockAction.do?method=exportStockResult">����ΪExcel�ļ�</html:link><br>
   </logic:notEmpty>
   <div align="center"><html:button property="action" styleClass="button" onclick="goOldquery()" >����</html:button></div>
   

</logic:equal>

<logic:equal value="showold1" scope="session" name="type">
  <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
   <br/>
  <template:titile value="���ɲ�����ⵥһ����1"/>
  <display:table name="sessionScope.oldinfo" id="currentRowObject" pagesize="18">>
    <display:column media="html" title="������ⵥ��ˮ��" style="width:120px">
    <%
      BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      String id = (String) object.get("oldid");
    %>
      <a href="javascript:toShowOneOldStock('<%=id%>')"><%=id%></a>
    </display:column>
    <display:column property="contractorname" title="��λ����" maxLength="20" style="align:center"/>
    <display:column property="username" title="�����" maxLength="10" style="align:center"/>
    <display:column property="oldtime" title="���ʱ��" maxLength="15" style="align:center"/>
    <display:column property="oldreason" title="������Դ" maxLength="20"/>    
  </display:table>
  <html:link action="/PartStockAction.do?method=exportOldStockResult">����ΪExcel�ļ�</html:link>
</logic:equal>

<iframe id="hiddenIframe" style="display:none"></iframe>
<script type="text/javascript">
	//���ص��ϼ���ѯҳ��
	function goquery(){
		window.location.href = "${ctx}/PartStockAction.do?method=queryShowForStat";
	}
	
	function goOldquery(){
		window.location.href = "${ctx}/PartStockAction.do?method=queryShowForOldStat";
	}
	
	function gooldquery(){
		window.location.href = "${ctx}/PartStockAction.do?method=queryShowForOldSrock";
	}
	
	function goqueryStock(){
		window.location.href = "${ctx}/PartStockAction.do?method=queryShowForSrock";
	}

</script>
</body>
</html>
