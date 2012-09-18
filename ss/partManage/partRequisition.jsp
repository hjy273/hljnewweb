<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.partmanage.beans.Part_requisitionBean" %>
<%@page import="com.cabletech.baseinfo.dao.UserInfoDAOImpl" %>

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
		var selectValue = this.value;
        var tmpValue;
        var tr;//�ж���
        for(i =0; i<queryID.rows.length;i++){
            if(queryID.rows[i].id == rowid){
                tr = queryID.rows[i];
            } else {
				if(document.getElementById("select"+queryID.rows[i].id) != null ){
            		tmpValue = document.getElementById("select"+queryID.rows[i].id).value;
					if(tmpValue == selectValue) {
						alert("�ò����Ѿ�ѡ����ˣ�������ѡ��������ϣ�");
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

        //tr.cells[1].innerText =unit;
        //tr.cells[2].innerText =type;
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
        t1.name = "renumber"
        t1.id = "text" + onerow.id;
        t1.value= "0";
        t1.maxLength = "6";
        t1.size = "6";
        t1.onblur=valiD;
        t1.style.background="#C6D6E2"
        t1.style.font.size="12px";


        //����һ��select
        var s1 = document.createElement("select");
        s1.options.length = infoArr.length + 1;
        s1.options[0].value = "";
        s1.options[0].text = "��ѡ�����";
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

        cell1.appendChild(s1);//����
        cell2.innerText="ûѡ����";//����
        cell3.innerText="ûѡ����";//����
        cell4.appendChild(t1);//����
        cell5.appendChild(b1);
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
   //��ӷ���
    function addGoBack(type){
        try{
          if(type=="1")
             location.href="${ctx}/PartRequisitionAction.do?method=showAllRequisition&&querytype=1";
          else
             location.href = "${ctx}/PartRequisitionAction.do?method=showAllRequisition";
             return true;
            }
            catch(e){
                alert(e);
            }
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
    function toUpForm(idValue,state){
       if(state!="������"){
         alert('���뵥�Ѿ��������������޸ģ�');
       }else{
         var url = "${ctx}/PartRequisitionAction.do?method=upshow&reid=" + idValue;
         self.location=url;
       }
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
                var tu = document.createElement("input");
                tu.value= infoArr[i][2];
                tu.maxLength = "6";
                tu.size = "6";
                tu.style.background="#C6D6E2"
                tu.style.font.size="12px";
                tu.readonly="readonly";
                trobj.cells[1].innerText ="";
                trobj.cells[1].appendChild(tu);

                var tt = document.createElement("input");
                tt.value= infoArr[i][3];
                tt.maxLength = "6";
                tt.size = "27";
                tt.style.background="#C6D6E2"
                tt.style.font.size="12px";
                tt.readonly="readonly";
                trobj.cells[2].innerText ="";
                trobj.cells[2].appendChild(tt);
            }
        }
        return true;
    }


   //��ʾҳ����ϸ��Ϣ��ť����
   function toGetForm(idValue,cIdValue,type){
         var url = "${ctx}/PartRequisitionAction.do?method=showOneInfo&querytype="+type+"&contractorid="+cIdValue+"&reid=" + idValue;
        self.location.replace(url);
    }

    //�޸�ҳ���ύ����
    function toUpSub(){
        if(queryID.rows.length<2){
              alert("��Ѳ���ɾû��,�����ύ!")
            return false;
          }
      if(confirm("��ȷ��Ҫִ�д˴��޸Ĳ�����?")){
            UpForm.submit();
         }
        else
            return false;
    }
    //��ʾҳ��ɾ����ť����
     function toDelForm(idValue,state){
       if(state!="������"){
         alert('���뵥�Ѿ�������������ɾ����');
       }else{
         if(confirm("��ȷ��Ҫɾ���������뵥��?")){
           var url = "${ctx}/PartRequisitionAction.do?method=delOneReqInfo&reid=" + idValue;
           self.location.replace(url);
         }
         else
           return ;
       }
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
    //����ύ
    function toAddSub(){
      if(queryID.rows.length < 2){
          alert("�㻹û��ѡ�����,�����ύ!")
        return false;
      }
	var addsub = document.getElementById("addsub");
	addsub.disabled=true;
	addForm.submit();
    }

    //��ʾ���������뵥ҳ�����������뵥��ť����
     function toAuditForm(idValue,audresult){
         //if(confirm("��ȷ��Ҫ�����������뵥��?")){
        if(audresult == "�����"){
              if(confirm("��ȷ��Ҫ����������뵥��?")){
              var url = "${ctx}/PartRequisitionAction.do?method=doShowOneForReAudit&reid=" + idValue;
              self.location.replace(url);
            }
        }else{
              if(confirm("��ȷ��Ҫ�����������뵥��?")){
                var url = "${ctx}/PartRequisitionAction.do?method=doAuditShowOne&reid=" + idValue;
                self.location.replace(url);
              }
        }
        return

    }

    //�����Ƿ�����
    function valiDigit(id){
        var obj = document.getElementById(id);
        if(obj.value=="")
            obj.value="0";
        var mysplit = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
        if(mysplit.test(obj.value)){
          return true;
        }else{
            alert("����д�Ĳ�������,����������");
               obj.focus();
            obj.value=0
            return false;
        }
    }

    //��ʾ������ҳ��,��ϸ��Ϣ��ť����
   function toShowOneAudit(idValue){
         var url = "${ctx}/PartRequisitionAction.do?method=doShowOneAudit&reid=" + idValue;
        self.location.replace(url);
    }

    //��ʾ������ҳ��,��ϸ��Ϣ��ť����
   function  toGoBackDistail(){
         var url = "${ctx}/PartRequisitionAction.do?method=showAllAudit";
        self.location.replace(url);
    }
    //�����������뵥ʱ����ҳ��
function showupload()
{
    var objpart = document.getElementById("partDivId");
    var objup = document.getElementById("upDivId");

    objpart.style.display="none";
    objup.style.display="block";
}
function noupload()
{
    var objpart = document.getElementById("partDivId");
    var objup = document.getElementById("upDivId");

    objpart.style.display="block";
    objup.style.display="none";
}

function onmouseover(){
    alert("jdfkalj");
}
function exportReq(){
  try{
    location.href = "${ctx}/PartRequisitionAction.do?method=exportOneRequisition";
    return true;
  }
  catch(e){
    alert(e);
  }
}

function downloadFile() {
  location.href = "${ctx}/PartRequisitionAction.do?method=downloadTemplet";
}

function toAuditShow() {
  location.href = "${ctx}/PartRequisitionAction.do?method=doAuditShow";
}

function textCounter(field,maxlimit) {   
  if(field.value.length > maxlimit)     
  	field.value = field.value.substring(0,maxlimit);   
   
} 

function backToAuditShow() {
	location.href = "${ctx}/PartRequisitionAction.do?method=doAuditShow"
}
</script>
<title>partRequisition</title>
</head>
<style type="text/css">
.subject{text-align:center;}
</style>
<body>
  <!--��ʾ���뵥��ϸ��Ϣ-->
<logic:equal value="10" scope="session" name="type">
  <br/>
  <template:titile value="ά���������뵥��ϸ��Ϣ"/>
  <html:form action="/PartRequisitionAction?method=upRequisition" styleId="addForm">
    <input type="hidden" name="reid" value="<bean:write name="reqinfo" property="reid"/>"/>
    <input type="hidden" name="userid" value="<bean:write name="userid" />"/>
    <table align="center" width="90%" border="0" class="tabout">
      <tr class=trcolor>
        <td width="60" height="25">
          <b>��λ����:</b>
        </td>
        <td align="left" width="120">
          <bean:write name="reqinfo" property="contractorname"/>
        </td>
        <td align="right" width="50">
          <b>������:</b>
        </td>
        <td align="left" width="100">
          <bean:write name="reqinfo" property="username"/>
        </td>
        <td width="70" align="right">
          <b>����ʱ��:</b>
        </td>
        <td align="left" width="100">
          <bean:write name="reqinfo" property="time"/>
        </td>
      </tr>
      <tr class=trcolor>
        <td height="25">
          <b>��������:</b>
        </td>
        <td colspan="3">
          <bean:write name="reqinfo" property="reason"/>
        </td>
        <td width="80" align="right">
          <b>Ŀ�괦����:</b>
        </td>
        <td align="left" >
          <bean:write name="reqinfo" property="targetmanname"/>
        </td>
        
      </tr>
      <tr class=trcolor>
        <td height="25">
          <b>��ע��Ϣ:</b>
        </td>
        <td colspan="5">
          <bean:write name="reqinfo" property="remark"/>
        </td>
      </tr>
      <logic:notEqual value="������" name="reqinfo" property="auditresult">
        <tr class=trcolor>
          <td height="25">
            <b>�� �� ��:</b>
          </td>
          <td colspan="5">
            <bean:write name="reqinfo" property="auditusername"/>
          </td>
        </tr>
        <tr class=trcolor>
          <td height="25">
            <b>��������:</b>
          </td>
          <td colspan="5">
            <bean:write name="reqinfo" property="audittime"/>
          </td>
        </tr>
        <tr class=trcolor>
          <td height="25">
            <b>�������:</b>
          </td>
          <td colspan="5">
            <bean:write name="reqinfo" property="auditresult"/>
          </td>
        </tr>
        <tr class=trcolor>
          <td height="25">
            <b>������ע:</b>
          </td>
          <td colspan="5">
            <bean:write name="reqinfo" property="auditremark"/>
          </td>
        </tr>
      </logic:notEqual>
      <tr class=trcolor>
        <td colspan="6" height="20" >
          <hr/>
        </td>
      </tr>
      
       <tr class=trcolor>
        <td colspan="6" >
          	<table border="0"  align="center" width="100%">
		      <tr>
		        <td colspan="8" valign="bottom">
		          <b>            �����뵥������Ĳ���:(
		            <bean:write name="reqinfo" property="auditresult"/>
		            )
		          </b>
		        </td>
		      </tr>
		      <tr>
		          <td>
		            <table id="queryID"  width="100%" border="1" align="center" cellpadding="3" cellspacing="0">
		      <tr>
		        <th  width="35%" class="thlist" align="center">��������</th>
		        <th  width="10%" class="thlist" align="center">������λ</th>
		        <th  width="25%" class="thlist" align="center">����ͺ�</th>
		        <th  width="10%" class="thlist" align="center">��������</th>
		        <logic:notEqual value="������" name="reqinfo" property="auditresult">
		          <th width="10% " class="thlist" align="center">��������</th>
		          <th width="10%" class="thlist" align="center">�������</th>
		        </logic:notEqual>
		      </tr>
		      <logic:present name="reqpartinfo">
		        <logic:iterate id="reqid" name="reqpartinfo">
		          <tr id="<bean:write name="reqid" property="id"/>">
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
		               <bean:write name="reqid" property="renumber"/>
		            </td>
		            <logic:notEqual value="������" name="reqinfo" property="auditresult">
		              <logic:equal value="��������" name="reqinfo" property="auditresult">
		                <td>0</td>
		                <td>0</td>
		              </logic:equal>
		              <logic:notEqual value="��������" name="reqinfo" property="auditresult">
		                <td>
		                  <bean:write name="reqid" property="audnumber"/>
		                </td>
		                <td>
		                    <bean:write name="reqid" property="stocknumber"/>
		                </td>
		              </logic:notEqual>
		            </logic:notEqual>
		          </tr>
		        </logic:iterate>
		      </logic:present>
		    </table>
		        </td>
		      </tr>
		    </table>
		        </td>
		      </tr>
    </table>
    

    <p align="center">
      <input type="button" class="button" value="����" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"/>
      <html:button property="action" styleClass="button" onclick="exportReq()">Excel����</html:button>
    </p>
  </html:form>
</logic:equal>
  <!--�޸����뵥-->
<logic:equal value="4" scope="session" name="type">
  <apptag:checkpower thirdmould="80104" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="�޸�ά���������뵥"/>
  <logic:present name="baseinfo">
    <logic:iterate id="baseinfoId" name="baseinfo">
<script type="" language="javascript">
                    initArray("<bean:write name="baseinfoId" property="id"/>","<bean:write name="baseinfoId" property="name"/>","<bean:write name="baseinfoId" property="unit"/>","<bean:write name="baseinfoId" property="type"/>");
                </script>
    </logic:iterate>
  </logic:present>
  <html:form action="/PartRequisitionAction?method=upRequisition" styleId="UpForm">
    <input type="hidden" name="reid" value="<bean:write name="reqinfo" property="reid"/>"/>
    <input type="hidden" name="userid" value="<bean:write name="userid" />"/>
    <table class="tabout" align="center" width="90%" border="0">
      <tr class=trcolor>
        <td width="60" height="25">
          <b>��λ����:</b>
        </td>
        <td align="left" width="120">
          <bean:write name="reqinfo" property="contractorname"/>
        </td>
        <td align="right" width="50">
          <b>������:</b>
        </td>
        <td align="left" width="100">
          <bean:write name="reqinfo" property="username"/>
        </td>
        <td width="70" align="right">
          <b>����ʱ��:</b>
        </td>
        <td align="left" width="100">
          <bean:write name="reqinfo" property="time"/>
        </td>
      </tr>
      <tr class=trcolor>
        <td height="25" width="80">
          <b>��λ����:</b>
        </td>
        <td align="left" width="120">
          <bean:write name="deptname"/>
        </td>
        <td align="right" width="50">
          <b>�޸���:</b>
        </td>
        <td align="left" width="100">
          <bean:write name="username"/>
        </td>
        <td align="right" width="70">
          <b>�޸�ʱ��:</b>
        </td>
        <td align="left" width="100">
          <bean:write name="date"/>
        </td>
      </tr>
      <tr class=trcolor>
      	<td align="left" width="80"><b>Ŀ�괦����:</b></td>
      	<td colspan="5">  
      
      	 <bean:define id="tem1" name="reqinfo" property="targetman" type="java.lang.String"/>
              <select name="targetman" style="width:100" class="inputtext">
              
                <logic:iterate id="targetmanId" name="ltargetman">
                <logic:equal value="<%=tem1%>" name="targetmanId" property="userid">
                  <option value="<bean:write name="targetmanId" property="userid" />" selected="selected">
                    <bean:write name="targetmanId" property="username"/>
                  </option>
                </logic:equal>  
                <logic:notEqual value="<%=tem1%>" name="targetmanId" property="userid">
                	<option value="<bean:write name="targetmanId" property="userid"/>">
                    <bean:write name="targetmanId" property="username"/>
                  	</option>
                </logic:notEqual>
                </logic:iterate>
              </select>
      	</td>
      </tr>
     
      <tr class=trcolor>
        <td height="25">
          <b>��������:</b>
        </td>
        <td colspan="5">
          <input type="text" name="reason" Class="inputtext" style="width:88%;" maxlength="24" value="<bean:write name="reqinfo" property="reason"/>"/>
        </td>
      </tr>
      <tr class=trcolor>
        <td height="25">
          <b>��ע��Ϣ:</b>
        </td>
        <td colspan="5">
          <input type="text" name="remark" Class="inputtextarea" style="width:88%;" onkeydown="textCounter(this,254)" onkeyup="textCounter(this,254)"  value="<bean:write name="reqinfo" property="remark"/>"/>
        </td>
      </tr>
      <tr class=trcolor>
        <td colspan="6" height="20" valign="top">
          <hr/>
          <br>
          ��ѡ�����:
        </td>
      </tr>
      <tr class=trcolor>
        <td colspan="6" valign="top">
         <table width="100%" id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0">
	      <tr>
	        <th width="40%" class="thlist" align="center">��������</th>
	        <th width="10%" class="thlist" align="center">������λ</th>
	        <th width="32%" class="thlist" align="center">����ͺ�</th>
	        <th width="10%" class="thlist" align="center">��������</th>
	        <th width="8%" class="thlist" align="center">����</th>
	      </tr>
	      <logic:present name="reqpartinfo">
	        <logic:iterate id="reqid" name="reqpartinfo">
	          <tr id="<bean:write name="reqid" property="id"/>">
	            <td >
	              <bean:define id="tem1" name="reqid" property="id" type="java.lang.String"/>
	              <select class="selecttext"  name="id" style="width=250" id="sele<bean:write name="reqid" property="id"/>" onchange="upselectOncheng(id)">
	                  <logic:iterate id="baseinfoId" name="baseinfo">
	                  <logic:equal name="baseinfoId" property="id" value="<%=tem1%>">
	                    <option selected="selected"  value="<bean:write name="baseinfoId" property="id"/>">
	                      <bean:write name="baseinfoId" property="name"/> -- <bean:write name="baseinfoId" property="type"/>
	                    </option>
	                  </logic:equal>
	                  <logic:notEqual value="<%=tem1%>" name="baseinfoId" property="id">
	                    <option value="<bean:write name="baseinfoId" property="id"/>">
	                      <bean:write name="baseinfoId" property="name"/> -- <bean:write name="baseinfoId" property="type"/>
	                    </option>
	                  </logic:notEqual>
	                </logic:iterate>
	              </select>
	            </td>
	
	            <td>
	             <input   readonly="readonly" class="inputtext" size="8" value="<bean:write name="reqid" property="unit"/>"/>
	            </td>
	            <td>
	            <input   readonly="readonly" class="inputtext" size="32" value=" <bean:write name="reqid" property="type"/>"/>
	            </td>
	            <td>
	              <input class="inputtext" name="renumber" id="reu<bean:write name="reqid" property="id"/>" maxlength="6" size="8" onblur="valiDigit(id)" value="<bean:write name="reqid" property="renumber"/>"/>
	            </td>
	            <td>
	              <input  type="button" value="ɾ��" id="b<bean:write name="reqid" property="id"/>" onclick="upDelOnClick(id)"/>
	            </td>
	          </tr>
	        </logic:iterate>
	      </logic:present>
	    </table>
        </td>
      </tr>
    </table>
    
    <p align="center">
      <html:button property="action" styleClass="button" onclick="addRow()">����²���</html:button>
      <html:button property="action" styleClass="button" onclick="toUpSub()" title="һ���ύ,�����뵥�������˾͸���Ϊ�޸���!">�ύ�޸�</html:button>
      <input type="button" value="����" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"/>
    </p>
  </html:form>
</logic:equal>
  <!--��д���뵥-->
<logic:equal value="2" scope="session" name="type">
  <link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
  <apptag:checkpower thirdmould="80101" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <logic:present name="baseinfo">
    <logic:iterate id="baseinfoId" name="baseinfo">
<script type="" language="javascript">
                    initArray("<bean:write name="baseinfoId" property="id"/>","<bean:write name="baseinfoId" property="name"/>","<bean:write name="baseinfoId" property="unit"/>","<bean:write name="baseinfoId" property="type"/>");
                </script>
    </logic:iterate>
  </logic:present>
  <div id="partDivId">
    <br/>
    <template:titile value="��дά���������뵥"/>
    <html:form action="/PartRequisitionAction?method=addRequisition" styleId="addForm">
      <input type="hidden" name="contractorid" value="<bean:write name="deptid"/>"/>
      <input type="hidden" name="userid" value="<bean:write name="userid" />"/>
      <table align="center" width="90%" border="0" class="tabout">
        <tr class=trcolor>
          <td  width="8%">
            <b>�� �� ��:</b>
          </td>
          <td align="left" width="10%">
            <bean:write name="username"/>
          </td>
          <td width="10%" align="right">
            <b>����ʱ��:</b>
          </td>
          <td align="left" width="10%">
            <bean:write name="date"/>
          </td>
          <logic:equal value="send" name="isSendSm">
            <td width="12%" align="right">
              <b>Ŀ�괦����:</b>
            </td>
            <td align="left">
              <select name="targetman" style="width:160" class="inputtext">
                <logic:iterate id="targetmanId" name="ltargetman">
                  <option value="<bean:write name="targetmanId" property="userid"/>">
                    <bean:write name="targetmanId" property="username"/>
                  </option>
                </logic:iterate>
              </select>
            </td>
          </logic:equal>
          <logic:notEqual value="send" name="isSendSm">
            <td>            </td>
          </logic:notEqual>
        </tr>
        <tr class=trcolor>
          <td height="25" width="8%">
            <b>��������:</b>
          </td>
          <td colspan="5" >
            <html:text property="reason" styleClass="inputtext" style="width:85%;" maxlength="24" value="����д������ϵ�ԭ����߲��ϵ���;.">            </html:text>
          </td>
        </tr>
        <tr class=trcolor>
          <td height="25">
            <b>��ע��Ϣ:</b>
          </td>
          <td colspan="5">
            <html:textarea property="remark" styleClass="inputtextarea" style="width:85%;" onkeydown="textCounter(this,254)" onkeyup="textCounter(this,254)" value="����д�����������˵����Ϣ.">            </html:textarea>
          </td>
        </tr>
        <tr class=trcolor>
          <td colspan="6">
            <hr/>
            <br/>
            ��ѡ�����:
          </td>
        </tr>
        <tr class=trcolor>
        	<td colspan="6">
        		 <table width="100%" id="queryID"  border="1" align="center" cellpadding="3" cellspacing="0">
			        <tr>
			          <th width="40%" class="thlist" align="center">��������</th>
			          <th width="12%" class="thlist" align="center">������λ</th>
			          <th width="30%" class="thlist" align="center">����ͺ�</th>
			          <th width="10%" class="thlist" align="center">��������</th>
			          <th width="8%" class="thlist" align="center">����</th>
			        </tr>
      </table>
        	</td>
        </tr>
      </table>
     
      <p align="center">
        <html:button property="action" styleClass="button" onclick="addRow()">����²���</html:button>
        <html:button property="action" styleClass="button" styleId="addsub" onclick="toAddSub()">�ύ���뵥</html:button>
        <html:button property="action" styleClass="button" styleId="upId" onclick="showupload()">�������뵥</html:button>
        <html:button property="action" styleClass="button2" onclick="downloadFile()">���뵥ģ������</html:button>
        <!--<html:button property="action" styleClass="button" onclick="addGoBack()">����</html:button>  -->
      </p>
    </html:form>
  </div>
  <div id="upDivId" style="display:none">
    <html:form styleId="upform" action="/PartRequisitionAction?method=upLoadShow" method="post" enctype="multipart/form-data">
      <table align="center" border="0" width="600" height="100%">
        <tr >
          <td valign="top" height="100%">
            <table align="center" border="0">
              <tr>
                <td align="left" height="50">
                  <br/>
                  <br/>
                  <br/>
                  <br/>
                  <br/>
                  <br/>
                  <br/>
                  <b>��ѡ��Ҫ�����Excel�ļ�:</b>
                  <br/>
                </td>
              </tr>
              <tr>
                <td>
                  <html:file property="file" style="width:300px" styleClass="inputtext"/>
                </td>
              </tr>
              <tr>
                <td align="center" valign="middle" height="60">
                  <html:button styleClass="button" value="�������뵥" property="sub" onclick="javascript:upform.submit()">�ύ</html:button>
                  <html:button value="ȡ������" styleClass="button" property="action" onclick="noupload()"/>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </html:form>
  </div>
</logic:equal>
  <!--�����ļ�����ʾ-->
<logic:equal value="upshow" scope="session" name="type">
  <apptag:checkpower thirdmould="80104" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="ȷ��ά���������뵥"/>
  <logic:present name="baseinfo">
    <logic:iterate id="baseinfoId" name="baseinfo">
<script type="" language="javascript">
                    initArray("<bean:write name="baseinfoId" property="id"/>","<bean:write name="baseinfoId" property="name"/>","<bean:write name="baseinfoId" property="unit"/>","<bean:write name="baseinfoId" property="type"/>");
                </script>
    </logic:iterate>
  </logic:present>
  <html:form action="/PartRequisitionAction?method=addRequisition" styleId="addForm">
    <input type="hidden" name="contractorid" value="<bean:write name="deptid"/>"/>
    <input type="hidden" name="userid" value="<bean:write name="userid" />"/>
    <table align="center" width="90%" border="0" class="tabout">
      <tr class=trcolor>
        <td width="8%" align="right">
          <b>�� �� ��:</b>
        </td>
        <td width="10%">
          <bean:write name="username"/>
        </td>
        <td width="10%" align="right">
          <b>����ʱ��:</b>
        </td>
        <td align="left" width="10%">
          <bean:write name="date"/>
        </td>
        <logic:equal value="send" name="isSendSm">
          <td align="right" width="12%">
            <b>Ŀ�괦����:</b>
          </td>
          <td align="left">
            <select name="targetman" style="width:100" class="inputtext">
              <logic:iterate id="targetmanId" name="ltargetman">
                <option value="<bean:write name="targetmanId" property="userid"/>">
                  <bean:write name="targetmanId" property="username"/>
                </option>
              </logic:iterate>
            </select>
          </td>
        </logic:equal>
        <logic:notEqual value="send" name="isSendSm">
          <td >          </td>
        </logic:notEqual>
      </tr>
      <tr class=trcolor>
        <td height="25" align="right">
          <b>��������:</b>
        </td>
        <td colspan="5"  >
          <html:text property="reason" styleClass="inputtext" style="width:85%;" maxlength="24" value="����д������ϵ�ԭ����߲��ϵ���;.">          </html:text>
        </td>
      </tr>
      <tr class=trcolor>
        <td height="25"  align="right">
          <b>��ע��Ϣ:</b>
        </td>
        <td colspan="5">
          <html:textarea property="remark" styleClass="inputtextarea" style="width:85%;" onkeydown="textCounter(this,254)" onkeyup="textCounter(this,254)" value="����д�����������˵����Ϣ.">          </html:textarea>
        </td>
      </tr>
      <tr class=trcolor>
        <td colspan="6">
          <hr/>
          <br/>
          ��ѡ�����:
        </td>
      </tr>
 
    <tr class=trcolor>
    <td colspan="6">
    <table id="queryID"  width="100%" border="1" align="center" cellpadding="3" cellspacing="0">
      <tr>
        <th width="40%" class="thlist" align="center">��������</th>
        <th width="12%" class="thlist" align="center">������λ</th>
        <th width="30%" class="thlist" align="center">����ͺ�</th>
        <th width="10%" class="thlist" align="center">��������</th>
        <th width="8%" class="thlist" align="center">����</th>
      </tr>
      <logic:present name="reqpartinfo">
        <logic:iterate id="UpLoadId" name="reqpartinfo">
          <tr id="<bean:write name="UpLoadId" property="id"/>">
            <td>
              <bean:define id="tem1" name="UpLoadId" property="id" type="java.lang.String"/>
              <select name="id"   style="width:250" class="selecttext" id="sele<bean:write name="UpLoadId" property="id"/>" onchange="upselectOncheng(id)">
                <logic:iterate id="baseinfoId" name="baseinfo">
                  <logic:equal name="baseinfoId" property="id" value="<%=tem1%>">
                    <option selected="selected" value="<bean:write name="baseinfoId" property="id"/>">
                      <bean:write name="baseinfoId" property="name"/> -- <bean:write name="baseinfoId" property="type"/>
                    </option>
                  </logic:equal>
                  <logic:notEqual value="<%=tem1%>" name="baseinfoId" property="id">
                    <option value="<bean:write name="baseinfoId" property="id"/>">
                      <bean:write name="baseinfoId" property="name"/>-- <bean:write name="baseinfoId" property="type"/>
                    </option>
                  </logic:notEqual>
                </logic:iterate>
              </select>
            </td>

            <td>
                <input  size="8" class="inputtext"  value=" <bean:write name="UpLoadId" property="unit"/>"/>

            </td>
            <td>
             <input  size="32" class="inputtext"  value=" <bean:write name="UpLoadId" property="type"/>"/>
            </td>
            <td>
              <input id="re<bean:write name="UpLoadId" property="id"/>" class="inputtext" name="renumber" maxlength="6" onblur="valiDigit(id)" size="8" value="<bean:write name="UpLoadId" property="renumber"/>"/>
            </td>
            <td>
              <input type="button" value="ɾ��" id="b<bean:write name="UpLoadId" property="id"/>" onclick="upDelOnClick(id)"/>
            </td>
          </tr>
        </logic:iterate>
      </logic:present>
    </table>
 </td>
</tr>   
   </table>
    <p align="center">
      <html:button property="action" styleClass="button" onclick="addRow()">����²���</html:button>
      <html:button property="action" styleClass="button" styleId="addsub" onclick="toAddSub()">�ύ���뵥</html:button>
    </p>
  </html:form>
</logic:equal>
  <!--��ʾ���뵥-->
<logic:equal value="1" scope="session" name="type">
  <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
  <br/>
  <template:titile value="ά���������뵥һ����"/>
  <display:table name="sessionScope.reqinfo" requestURI="${ctx}/PartRequisitionAction.do?method=queryShow" id="currentRowObject" pagesize="18">
  	<%
      	BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      	String id = "";
      	String reason = "";
      	String reasonTitle = "";
      	String auditresult = "";
      	if(object != null) {
      		id = (String) object.get("reid");
      		reasonTitle = (String)object.get("reason");
      		if(reasonTitle != null && reasonTitle.length() > 15) {
      			reason = reasonTitle.substring(0,15) + "...";
      		} else {
      			reason = reasonTitle;
      		}
      		
      		auditresult=(String) object.get("auditresult");
      	}      	
    %>
    <display:column media="html" title="���뵥��ˮ��" style="width:100px">
        <%if(id != null) { %>
    		<a href="javascript:toGetForm('<%=id%>')"><%=id%></a>
    	<%} %>
    </display:column>    
    <display:column media="html" title="����ԭ��">
        <%if(reason != null) { %>
    		<a href="javascript:toGetForm('<%=id%>')" title="<%=reasonTitle %>"><%=reason%></a>
    	<%} %>
    </display:column>
    <display:column property="contractorname" title="���뵥λ" maxLength="20" style="align:center"/>
    <display:column property="username" title="�� �� ��" maxLength="20" style="align:center"/>
    <display:column property="time" title="����ʱ��" maxLength="20" style="align:center"/>
    <display:column media="html" title="����״̬">    
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
    <display:column media="html" title="����" headerClass="subject" class="subject" style="width:100px">  
      <apptag:checkpower thirdmould="80104" ishead="0">
      	<%
        
        if("������".equals(auditresult)) {
      	%>
        <a href="javascript:toUpForm('<%=id%>','<%=auditresult%>')">�޸�</a> | 
       
        <%} else {%>
        	--
        <%} %>
    </apptag:checkpower>
    <apptag:checkpower thirdmould="80105" ishead="0">     
      	<%
        	if("������".equals(auditresult)) {
      	%>
        <a href="javascript:toDelForm('<%=id%>','<%=auditresult%>')">ɾ��</a>
       
        <%} %>
    </apptag:checkpower>
  </display:column>      
  </display:table>
   <logic:notEmpty name="reqinfo">
          <html:link action="/PartRequisitionAction.do?method=exportRequisitonResult">����ΪExcel�ļ�</html:link><br>
   </logic:notEmpty>
   <logic:equal value="query" name="queryApply">
  		 <div align="center"><html:button property="action" styleClass="button" onclick="goApplyquery()" >����</html:button></div>
   </logic:equal>
 
 
</logic:equal>


  <!--��ѯҳ��-->
<logic:equal value="3" scope="session" name="type">
  <apptag:checkpower thirdmould="80103" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="��������ά���������뵥"/>
  <html:form action="/PartRequisitionAction?method=queryExec" styleId="queryForm2">
    <template:formTable namewidth="200" contentwidth="200">
      <template:formTr name="����״̬">
        <select name="auditresult" class="inputtext" style="width:180px">
          <option value="">ѡ������״̬</option>
          <option value="������">�� �� ��</option>
          <option value="ͨ������">ͨ������</option>
          <option value="�����">�� �� ��</option>
          <option value="��������">��������</option>
        </select>
      </template:formTr>
      <template:formTr name="�� �� ��">
        <input type="text" name="userid" class="inputtext" style="width:180px"/>
      </template:formTr>
      <template:formTr name="����ԭ��">
        <input type="text" name="reason" style="width:180px" class="inputtext">
      </template:formTr>
      <template:formTr name="��ʼʱ��">
        <input id="begin" type="text" name="begintime" readonly="readonly" class="inputtext" style="width:150"/>
        <INPUT TYPE='BUTTON' VALUE='����' ID='btn' onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';">
      </template:formTr>
      <template:formTr name="����ʱ��">
        <input id="end" type="text" name="endtime" readonly="readonly" class="inputtext" style="width:150"/>
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
<iframe id="hiddenIframe" style="display:none"></iframe>

<!--//////////////////////////////////��������///////////////////////////////////////////-->
  <!--��ʾ�����������뵥-->
<logic:equal value="audit2" scope="session" name="type">
  <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
  <br/>
  <template:titile value="���������뵥һ����"/>
  <display:table name="sessionScope.reqinfo" requestURI="${ctx}/PartRequisitionAction.do?method=doAuditShow" id="currentRowObject" pagesize="18">
  	<%
      BasicDynaBean objectA1 = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      String idA1 = "";
      String audresult = "";
      String reason = "";
      String auditresult = "";
      String reasonTitle = "";
      if(objectA1 != null) {
      	idA1 = (String) objectA1.get("reid");
      	audresult = (String) objectA1.get("auditresult");
      	
      	reasonTitle = (String)objectA1.get("reason");
    	if(reasonTitle != null && reasonTitle.length() > 15) {
    		reason = reasonTitle.substring(0,15) + "...";
    	} else {
    		reason = reasonTitle;
    	}
      }      
    %>
  	<display:column media="html" title="���뵥��ˮ��" style="width:100px">    
  		<%if(idA1 != null) { %>
    		<a href="javascript:toAuditForm('<%=idA1%>','<%=audresult%>')"><%=idA1 %></a>
    	<%} %>
    </display:column>
    <display:column media="html" title="����ԭ��">    
  		<%if(reason != null) { %>
    		<a href="javascript:toAuditForm('<%=idA1%>','<%=audresult%>')" title="<%=reasonTitle %>"><%=reason %></a>
    	<%} %>
    </display:column>  	
    <display:column property="contractorname" title="���뵥λ" maxLength="20" style="align:center"/>
    <display:column property="username" title="�� �� ��" maxLength="20" style="align:center"/>
    <display:column property="time" title="����ʱ��" maxLength="20" style="align:center"/>    
    <display:column media="html" title="����״̬">    
  		<%if("������".equals(audresult)) { %>    	
    		<font color="#0000CD" ><%=audresult %></font>
    	<%} else if("ͨ������".equals(audresult)) { %>
    		<font color="#008000" ><%=audresult %></font>
    	<%} else if("��������".equals(audresult)) {%>
    		<font color="red" ><%=audresult %></font>
    	<%} else if("�����".equals(audresult)) {%>
    		<font color="#FFA500" ><%=audresult %></font>
    	<%} else {%>
    		<%=auditresult %>
    	<%} %>
    </display:column>

  </display:table>
</logic:equal>

  <!--�����������뵥-->
<logic:equal value="audit20" scope="session" name="type">

  <br/>
  <template:titile value="�����������뵥"/>
  <html:form action="/PartRequisitionAction?method=auditRequisition" styleId="addForm">
    <input type="hidden" name="reid" value="<bean:write name="reqinfo" property="reid"/>"/>
    <input type="hidden" name="useid" value="<bean:write name="reqinfo" property="useid"/>"/>
    <input type="hidden" name="userid" value="<bean:write name="userid" />"/>
    <table class="tabout" align="center" width="90%" border="0">
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
        <td colspan="6" valign="bottom">
         <br/>
          <br/>
        </td>
      </tr>
      <tr class=trcolor>
        <td colspan="6" valign="bottom">
          <b>            �����뵥������Ĳ���:(
            <bean:write name="reqinfo" property="auditresult"/>
            )
          </b>
          <br/>
        </td>
      </tr>
       
      <tr class=trcolor>
        <td colspan="6" valign="bottom">
         	<table width="100%" id="queryID"  border="1" align="center" cellpadding="3" cellspacing="0">
		      <tr>
		        <th width="40%" class="thlist" align="center">��������</th>
		        <th width="10%" class="thlist" align="center">������λ</th>
		        <th width="30%" class="thlist" align="center">����ͺ�</th>
		        <th width="10%" class="thlist" align="center">��������</th>
		        <th width="10%" class="thlist" align="center">��������</th>
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
		              <bean:write name="reqid" property="renumber"/> 
		            </td>
		            <td>
		              <input id="aduditnub<bean:write name="reqid" property="id"/>" onblur="valiDigit(id)" size="8" maxlength="6" align="right" class="inputtext"  name="audnumber" value="<bean:write name="reqid" property="renumber"/>"/>
		            </td>
		          </tr>
		        </logic:iterate>
		      </logic:present>
		    </table>
		    <br/>
    		<br/>
        </td>
      </tr>
      <tr class=trcolor>
        <td colspan="6" valign="bottom">
         	<table class="tabout" width="100%" align="center" width="100%" border="0">
		      <tr class=trcolor>
		        <td height="30" width="60">
		          <b>�������:</b>
		        </td>
		        <td colspan="5">
		          <select name="auditresult" class="selecttext">
		            <option value="ͨ������">ͨ������</option>
		            <option value="��������">��������</option>
		          </select>
		        </td>
		      </tr>
		      <tr class=trcolor>
		        <td height="30" width="60">
		          <b>������ע:</b>
		        </td>
		        <td colspan="5">
		          <textarea rows="5" name="auditremark" maxlength="512" style="width:70%" class="inputtextarea" value="����д������ע" ></textarea>
		        </td>
		      </tr>
		      <tr class=trcolor>
		        <td width="60" height="30">
		          <b>������λ:</b>
		        </td>
		        <td align="left" width="120">
		          <bean:write name="deptname"/>
		        </td>
		        <td align="right" width="50">
		          <b>������:</b>
		        </td>
		        <td align="left" width="100">
		          <bean:write name="username"/>
		        </td>
		        <td width="70" align="right">
		          <b>����ʱ��:</b>
		        </td>
		        <td align="left" width="100">
		          <bean:write name="date"/>
		        </td>
		      </tr>
		      
		    </table>
        </td>
      </tr>
    </table>
   <p align="center">
   		<html:submit property="action" styleClass="button">�ύ����</html:submit>
		<input type="button" value="��&nbsp;&nbsp;��" Class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"/>
   
   </p>
    
    
    
  </html:form>
</logic:equal>
  <!--��˵������뵥-->
<logic:equal value="audit200" scope="session" name="type">
  
  <br/>
  <template:titile value="��˲������뵥"/>
  <html:form action="/PartRequisitionAction?method=doReAuditRequisition" styleId="addForm">
    <input type="hidden" name="reid" value="<bean:write name="reqinfo" property="reid"/>"/>
    <input type="hidden" name="userid" value="<bean:write name="userid" />"/>
    <table align="center" width="90%" border="0">
      <tr>
        <td width="10%%">
          <b>��λ����:</b>
        </td>
        <td align="left" width="15%">
          <bean:write name="reqinfo" property="contractorname"/>
        </td>
        <td align="right" width="10%">
          <b>������:</b>
        </td>
        <td align="left" width="15%">
          <bean:write name="reqinfo" property="username"/>
        </td>
        <td width="10%" align="right">
          <b>����ʱ��:</b>
        </td>
        <td align="left" width="100">
          <bean:write name="reqinfo" property="time"/>
        </td>
      </tr>
      <tr>
        <td>
          <b>��������:</b>
        </td>
        <td colspan="5">
          <bean:write name="reqinfo" property="reason"/>
        </td>
      </tr>
      <tr>
        <td>
          <b>���뱸ע:</b>
        </td>
        <td colspan="5">
          <bean:write name="reqinfo" property="remark"/>
        </td>
      </tr>
      <tr height="10">
        <td colspan="6">
         
        </td>
      </tr>
      <tr>
        <td colspan="6" valign="bottom">
          <b>�����뵥���в���û��ȫ�����,��������˺�ȷ��:</b>
        </td>
      </tr>
    </table>
    <table id="queryID"  border="1" align="center" cellpadding="3" cellspacing="0" width="90%">
      <tr>
        <th width="250" class="thlist" align="center">��������</th>
        <th width="60" class="thlist" align="center">������λ</th>
        <th width="200" class="thlist" align="center">����ͺ�</th>
        <th width="60" class="thlist" align="center">��������</th>
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
              <bean:write name="reqid" property="renumber"/>
            </td>
            <td>
              <font color="blue">
              <bean:write name="reqid" property="audnumber"/>
              </font>
            </td>
            <td>
              <font color="red">
              <bean:write name="reqid" property="stocknumber"/>
              </font>
            </td>
          </tr>
        </logic:iterate>
      </logic:present>
    </table>
    <br/>
    <hr/>
    <br/>
    <table align="center" width="90%" border="0">
      <tr>
        <td width="10%" height="30">
          <b>��˵�λ:</b>
        </td>
        <td align="left" width="15%">
          <bean:write name="deptname"/>
        </td>
        <td align="right" width="10%">
          <b>�����:</b>
        </td>
        <td align="left" width="15%">
          <bean:write name="username"/>
        </td>
        <td width="10%" align="right">
          <b>���ʱ��:</b>
        </td>
        <td align="left" >
          <bean:write name="date"/>
        </td>
      </tr>
      <tr>
        <td colspan="6" height="40" valign="top">
          <template:formSubmit>
            <td>              &nbsp;
              <html:submit property="action" styleClass="button" title="�ύ���,��ζ�Ž�����������Ϊ���������">�ύ���</html:submit>
              <input type="button" value="��&nbsp;&nbsp;��" Class="button" onclick="javascript:backToAuditShow()"/>
            </td>
          </template:formSubmit>
        </td>
      </tr>
    </table>
  </html:form>
</logic:equal>

  <!--��ʾ����������-->
<logic:equal value="audit1" scope="session" name="type">
  <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
  <apptag:checkpower thirdmould="80201" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="ά������������һ����"/>
  <display:table name="sessionScope.reqinfo" requestURI="${ctx}/PartRequisitionAction.do?method=showAllAudit" id="currentRowObject" pagesize="18">
  	 <%
      BasicDynaBean objectb1 = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      String idb1 = "";
      String reason = "";
      String auditresult = "";
      String reasonTitle = "";
      if(objectb1 != null) {
      	 idb1 = (String) objectb1.get("reid");
      	
      	 reasonTitle = (String)objectb1.get("reason");
    	 if(reasonTitle != null && reasonTitle.length() > 15) {
    		reason = reasonTitle.substring(0,15) + "...";
    	 } else {
    		reason = reasonTitle;
    	 }
      	 auditresult = (String)objectb1.get("auditresult");
      }
    %>
  	<display:column media="html" title="���뵥��ˮ��" style="width:100px">   
  	  <%if(idb1 != null ) { %>
      	<a href="javascript:toShowOneAudit('<%=idb1%>')"><%=idb1 %></a>
      <%} %>
    </display:column>
    <display:column media="html" title="����ԭ��">   
  	  <%if(reason != null ) { %>
      	<a href="javascript:toShowOneAudit('<%=idb1%>')" title="<%=reasonTitle %>"><%=reason %></a>
      <%} %>
    </display:column>
    <display:column property="contractorname" title="���뵥λ" maxLength="10"/>    
    <display:column property="username" title="�� �� ��" maxLength="8" style="align:center"/>
    <display:column property="time" title="����ʱ��" maxLength="20" style="align:center"/>
   
    <display:column media="html" title="�������">    
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
  <logic:notEmpty name="reqinfo">
  	<html:link action="/PartRequisitionAction.do?method=exportRequisiton">����ΪExcel�ļ�</html:link>
  </logic:notEmpty>
  <logic:equal value="query" name="audit1">
  	 <div align="center"><html:button property="action" styleClass="button" onclick="goquery()" >����</html:button></div>
  </logic:equal>
  
</logic:equal>

  <!--�鿴һ������������ϸ��Ϣ-->
<logic:equal value="audit10" scope="session" name="type">
  <apptag:checkpower thirdmould="80201" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="�������뵥������Ϣ"/>
  <html:form action="/PartRequisitionAction?method=auditRequisition" styleId="addForm">
    <input type="hidden" name="reid" value="<bean:write name="reqinfo" property="reid"/>"/>
    <input type="hidden" name="userid" value="<bean:write name="userid" />"/>
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
          <br/><br/>
        </td>
      </tr>
      <tr class=trcolor>
        <td colspan="6" valign="bottom">
          <b>�����뵥������Ĳ���:</b>
        </td>
      </tr>
      <tr class=trcolor>
        <td colspan="6" valign="bottom">
         	<table width="100%" id="queryID"  border="1" align="center" cellpadding="3" cellspacing="0">
		      <tr>
		        <th width="40%" class="thlist" align="center">��������</th>
		        <th width="10%" class="thlist" align="center">������λ</th>
		        <th width="30%" class="thlist" align="center">����ͺ�</th>
		        <th width="10%" class="thlist" align="center">��������</th>
		        <th width="10%" class="thlist" align="center">��������</th>
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
		              <bean:write name="reqid" property="renumber"/>
		            </td>
		            <td>
		              <bean:write name="reqid" property="audnumber"/>
		            </td>
		          </tr>
		        </logic:iterate>
		      </logic:present>
		    </table>
        </td>
      </tr>
      
      <tr class=trcolor>
        <td colspan="6" valign="bottom">
           
		    <br/>
		    <hr/>
		    <br/>
        </td>
      </tr>
       <tr class=trcolor>
        <td colspan="6" valign="bottom">
           <logic:present name="auditinfo">
      <table class="tabout" align="center" width="100%" border="0">
        <tr class=trcolor>
          <td height="30">
            <b>�������:</b>
          </td>
          <td colspan="5">
            <bean:write name="auditinfo" property="auditresult"/>
          </td>
        </tr>
        <tr class=trcolor>
          <td height="30">
            <b>������ע:</b>
          </td>
          <td colspan="5">
            <bean:write name="auditinfo" property="auditremark"/>
          </td>
        </tr>
        <tr class=trcolor>
          <td width="60" height="30">
            <b>������λ:</b>
          </td>
          <td align="left" width="120">
            <bean:write name="auditinfo" property="deptname"/>
          </td>
          <td align="right" width="50">
            <b>������:</b>
          </td>
          <td align="left" width="100">
            <bean:write name="auditinfo" property="username"/>
          </td>
          <td width="70" align="right">
            <b>����ʱ��:</b>
          </td>
          <td align="left" width="100">
            <bean:write name="auditinfo" property="audittime"/>
          </td>
        </tr>
       
      </table>
    </logic:present>
        </td>
      </tr>
    </table>  
   <p align="center">
   <input type="button" class="button" value="����" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">	
   </p>
  </html:form>
</logic:equal>
  <!--����,������ѯҳ��-->
<logic:equal value="audit3" scope="session" name="type">
  <apptag:checkpower thirdmould="80203" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="����������������"/>
  <html:form action="/PartRequisitionAction?method=queryExecForAudit" styleId="queryForm2">
    <template:formTable namewidth="200" contentwidth="200">

      <template:formTr name="����״̬">
        <select name="auditresult" class="inputtext" style="width:180px">
          <option value="">ѡ������״̬</option>
          <option value="ͨ������">ͨ������</option>
          <option value="��������">��������</option>
        </select>
      </template:formTr>
      <template:formTr name="���뵥λ">
        <select name="contractorid" style="width:180px" class="inputtext">
          <option value="">ѡ���������뵥λ</option>
          <logic:present name="deptname">
            <logic:iterate id="deptnameId" name="deptname">
              <option value="<bean:write name="deptnameId"  property="contractorid"/>">
                <bean:write name="deptnameId" property="contractorname"/>
              </option>
            </logic:iterate>
          </logic:present>
        </select>
      </template:formTr>
       <template:formTr name="�� �� ��">
        <input type="text" name="audituserid" class="inputtext" style="width:180px"/>
      </template:formTr>
      <template:formTr name="����ԭ��">
        <input type="text"  name="reason" style="width:180px" class="inputtext"/>
      </template:formTr>
      <template:formTr name="��ʼʱ��">
        <input id="begin" type="text" name="begintime" class="inputtext" style="width:150"/>
        <INPUT TYPE='BUTTON' VALUE='����' ID='btn' onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';">
      </template:formTr>
      <template:formTr name="����ʱ��">
        <input id="end" type="text" class="inputtext" name="endtime" style="width:150"/>
        <INPUT TYPE='BUTTON' VALUE='����' ID='btn' onclick="GetSelectDateTHIS('end')" STYLE="font:'normal small-caps 6pt serif';">
      </template:formTr>
      <template:formSubmit>
        <td>
          <html:submit property="action" styleClass="button">����</html:submit>
        </td>
        <td>
          <html:reset property="action" styleClass="button">ȡ��</html:reset>
        </td>
      </template:formSubmit>
    </template:formTable>
  </html:form>
</logic:equal>
<logic:equal value="showall10" scope="session" name="type">
  <apptag:checkpower thirdmould="80109" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="ά���������뵥��ϸ��Ϣ"/>
  <html:form action="/PartRequisitionAction?method=upRequisition" styleId="addForm">
    <input type="hidden" name="reid" value="<bean:write name="reqinfo" property="reid"/>"/>
    <input type="hidden" name="userid" value="<bean:write name="userid" />"/>
    <table align="center" width="90%" border="0" class="tabout">
      <tr class=trcolor>
        <td width="60" height="25">
          <b>��λ����:</b>
        <br></td>
        <td align="left" width="120">
          
        <br></td>
        <td align="right" width="50">
          <b>������:</b>
        <br></td>
        <td align="left" width="100">
          
        <br></td>
        <td width="70" align="right">
          <b>����ʱ��:</b>
        <br></td>
        <td align="left" width="100">
          
        <br></td>
      </tr>
      <tr class=trcolor>
        <td height="25">
          <b>��������:</b>
        <br></td>
        <td colspan="5">
          
        <br></td>
      </tr>
      <tr class=trcolor>
        <td height="25">
          <b>��ע��Ϣ:</b>
        <br></td>
        <td colspan="5">
          
        <br></td>
      </tr>
      <logic:notEqual value="������" name="reqinfo" property="auditresult">
        <tr class=trcolor>
          <td height="25">
            <b>�� �� ��:</b>
          <br></td>
          <td colspan="5">
            
          <br></td>
        </tr>
        <tr class=trcolor>
          <td height="25">
            <b>��������:</b>
          <br></td>
          <td colspan="5">
            
          <br></td>
        </tr>
        <tr class=trcolor>
          <td height="25">
            <b>�������:</b>
          <br></td>
          <td colspan="5">
            
          <br></td>
        </tr>
        <tr class=trcolor>
          <td height="25">
            <b>������ע:</b>
          <br></td>
          <td colspan="5">
            
          <br></td>
        </tr>
      </logic:notEqual>
      <tr class=trcolor>
        <td colspan="6" height="40" valign="top">
          <hr/>
        <br></td>
      </tr>
      <tr class=trcolor>
        <td colspan="6"  valign="top">
         	    <table border="0"  align="center" width="100%">
		      <tr>
		        <td colspan="8" valign="bottom">
		          <b>            �����뵥������Ĳ���:(
		            
		            )
		          </b>
		        <br></td>
		      </tr>
		      <tr>
		          <td>
		            <table id="queryID"  width="100%" border="1" align="center" cellpadding="3" cellspacing="0">
		      <tr>
		        <th  width="35%" class="thlist" align="center">��������<br></th>
		        <th  width="10%" class="thlist" align="center">������λ<br></th>
		        <th  width="25%" class="thlist" align="center">����ͺ�<br></th>
		        <th  width="10%" class="thlist" align="center">��������<br></th>
		        <logic:notEqual value="������" name="reqinfo" property="auditresult">
		          <th width="10% " class="thlist" align="center">��������<br></th>
		          <th width="10%" class="thlist" align="center">�������<br></th>
		        </logic:notEqual>
		      </tr>
		      <logic:present name="reqpartinfo">
		        <logic:iterate id="reqid" name="reqpartinfo">
		          <tr id="<bean:write name="reqid" property="id"/>">
		            <td>
		                 -- 
		            <br></td>
		            <td>
		               
		            <br></td>
		            <td>
		                
		            <br></td>
		            <td>
		                
		            <br></td>
		            <logic:notEqual value="������" name="reqinfo" property="auditresult">
		              <logic:equal value="��������" name="reqinfo" property="auditresult">
		                <td>0<br></td>
		                <td>0<br></td>
		              </logic:equal>
		              <logic:notEqual value="��������" name="reqinfo" property="auditresult">
		                <td>
		                  
		                <br></td>
		                <td>s
		                    <bean:write name="reqid" property="stocknumber"/>
		                </td>
		              </logic:notEqual>
		            </logic:notEqual>
		          </tr>
		        </logic:iterate>
		      </logic:present>
		    </table>
		        </td>
		      </tr>
		    </table>
		        </td>
      </tr>
    </table>


    <p align="center">
      <input type="button" style="button" value="����" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"/>
      <html:button property="action" styleClass="button" onclick="exportReq()">Excel����</html:button>
    </p>
  </html:form>
</logic:equal>
<logic:equal value="showall01" scope="session" name="type">
  <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
  <br/>
  <template:titile value="ά���������뵥һ����"/>
  <display:table name="sessionScope.reqinfo" requestURI="${ctx}/PartRequisitionAction.do?method=queryShow" id="currentRowObject" pagesize="18">
  	<%
      	BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      	String id = "";
      	String reason = "";
      	String auditresult = "";
      	String contractorid = "";
      	String reasonTitle = "";
      	if(object != null) {
      		id = (String) object.get("reid");
      		
      		reasonTitle = (String)object.get("reason");
      		if(reasonTitle != null && reasonTitle.length() > 15) {
      			reason = reasonTitle.substring(0,15) + "...";
      		} else {
      			reason = reasonTitle;
      		}
      		auditresult=(String) object.get("auditresult");
      		contractorid=(String) object.get("contractorid");  	
      	}    
      	
      	
    %>
     <display:column media="html" title="���뵥��ˮ��" style="width:100px">
        <%if(id != null) { %>
    		<a href="javascript:toGetForm('<%=id%>','<%=contractorid%>','1')"><%=id %></a>
    	<%} %>
    </display:column>    
    <display:column media="html" title="����ԭ��">
        <%if(reason != null) { %>
    		<a href="javascript:toGetForm('<%=id%>','<%=contractorid%>','1')" title="<%=reasonTitle %>"><%=reason %></a>
    	<%} %>
    </display:column>
    <display:column property="contractorname" title="���뵥λ" maxLength="20" style="align:center"/>
    <display:column property="username" title="�� �� ��" maxLength="20" style="align:center"/>
    <display:column property="time" title="����ʱ��" maxLength="20" style="align:center"/>    
    <display:column media="html" title="����״̬">    
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
    <display:column media="html" title="����" headerClass="subject" class="subject">    	
      <apptag:checkpower thirdmould="80104" ishead="0">
        <a href="javascript:toUpForm('<%=id%>')">�޸�</a> | 
    </apptag:checkpower>
    <apptag:checkpower thirdmould="80105" ishead="0"> 
        <a href="javascript:toDelForm('<%=id%>')">ɾ��</a>
    </apptag:checkpower>
  </display:column>      
  </display:table>
  <html:link action="/PartRequisitionAction.do?method=exportRequisitonResult">����ΪExcel�ļ�</html:link>
</logic:equal>

<logic:equal value="showaudit1" scope="session" name="type">
  <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>

  <br/>
  <template:titile value="ά������������һ����"/>
  <display:table name="sessionScope.reqinfo" requestURI="${ctx}/PartRequisitionAction.do?method=queryExecForAudit" id="currentRowObject" pagesize="18">
  	<%
      BasicDynaBean objectb1 = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      String idb1 = "";
      String reason = "";
      String auditresult = "";
      String reasonTitle = "";
      if(objectb1 != null) {
      	 idb1 = (String) objectb1.get("reid");
      	 reason = (String)objectb1.get("reason");
      	 
      	 reasonTitle = (String)objectb1.get("reason");
    	 if(reasonTitle != null && reasonTitle.length() > 15) {
    		reason = reasonTitle.substring(0,15) + "...";
    	 } else {
    		reason = reasonTitle;
    	 }
      	 auditresult = (String)objectb1.get("auditresult");
      }
    %>
  	<display:column media="html" title="���뵥��ˮ��">    
      <a href="javascript:toShowOneAudit('<%=idb1%>')"><%=idb1 %></a>
     </display:column>
  	<display:column media="html" title="����ԭ��">   
  	  <%if(reason != null ) { %>
      	<a href="javascript:toShowOneAudit('<%=idb1%>')" title="<%=reasonTitle %>"><%=reason %></a>
      <%} %>
    </display:column>
    <display:column property="contractorname" title="���뵥λ" maxLength="10"/>    
    <display:column property="username" title="�� �� ��" maxLength="8" style="align:center"/>
    <display:column property="time" title="����ʱ��" maxLength="20" style="align:center"/>
    <display:column media="html" title="�������">    
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
  <logic:notEmpty name="reqinfo">
  	<html:link action="/PartRequisitionAction.do?method=exportRequisiton">����ΪExcel�ļ�</html:link>
  </logic:notEmpty>
</logic:equal>

<logic:equal value="showaudit20" scope="session" name="type">
  <apptag:checkpower thirdmould="80209" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="�����������뵥"/>
  <html:form action="/PartRequisitionAction?method=auditRequisition" styleId="addForm">
    <input type="hidden" name="reid" value="<bean:write name="reqinfo" property="reid"/>"/>
    <input type="hidden" name="useid" value="<bean:write name="reqinfo" property="useid"/>"/>
    <input type="hidden" name="userid" value="<bean:write name="userid" />"/>
    <table align="center" width="600" border="0">
      <tr>
        <td width="60">
          <b>��λ����:</b>
        <br></td>
        <td align="left" width="120">
          
        <br></td>
        <td align="right" width="50">
          <b>������:</b>
        <br></td>
        <td align="left" width="100">
          
        <br></td>
        <td width="70" align="right">
          <b>����ʱ��:</b>
        <br></td>
        <td align="left" width="100">
          
        <br></td>
      </tr>
      <tr>
        <td>
          <b>��������:</b>
        <br></td>
        <td colspan="5">
          
        <br></td>
      </tr>
      <tr>
        <td>
          <b>���뱸ע:</b>
        <br></td>
        <td colspan="5">
          
        <br></td>
      </tr>
      <tr>
        <td colspan="6" valign="bottom">
          <b>            �����뵥������Ĳ���:(
            
            )
          </b>
        <br></td>
      </tr>
    </table>
    <br/>
    <table id="queryID"  border="1" align="center" cellpadding="3" cellspacing="0">
      <tr>
        <th width="250" class="thlist" align="center">��������<br></th>
        <th width="60" class="thlist" align="center">������λ<br></th>
        <th width="200" class="thlist" align="center">����ͺ�<br></th>
        <th width="60" class="thlist" align="center">��������<br></th>
        <th width="60" class="thlist" align="center">��������<br></th>
      </tr>
      <logic:present name="reqpartinfo">
        <logic:iterate id="reqid" name="reqpartinfo">
          <tr id="<bean:write name="reqid" property="id"/>">
            <td>
              <input type="hidden" name="id" value="<bean:write name="reqid" property="id"/>"/>
              <input type="text" readonly="readonly" class="inputtext" size="32"  value="<bean:write name="reqid" property="name"/> -- <bean:write name="reqid" property="type"/>"/>
            <br></td>
            <td>
             <input type="text"  readonly="readonly" class="inputtext" size="8"  value="<bean:write name="reqid" property="unit"/>"/>
            <br></td>
            <td>
              <input type="text"readonly="readonly" class="inputtext" size="32" value="<bean:write name="reqid" property="type"/>"/>
            <br></td>
            <td>
              <input type="text" readonly="readonly" class="inputtext" size="8"  value="<bean:write name="reqid" property="renumber"/> "/>
            <br></td>
            <td>
              <input id="aduditnub<bean:write name="reqid" property="id"/>" onblur="valiDigit(id)" size="8" maxlength="6" align="right" class="inputtext"  name="audnumber" value="<bean:write name="reqid" property="renumber"/>"/>
            <br></td>
          </tr>
        </logic:iterate>
      </logic:present>
    </table>
    <br/>
    <hr/>
    <br/>
    <table align="center" width="600" border="0">
      <tr>
        <td height="30">
          <b>�������:</b>
        <br></td>
        <td colspan="5">
          <select name="auditresult" class="selecttext">
            <option value="ͨ������">ͨ������</option>
            <option value="��������">��������</option>
          </select>
        <br></td>
      </tr>
      <tr>
        <td height="30">
          <b>������ע:</b>
        <br></td>
        <td colspan="5">
          <input class="inputtext" name="auditremark" maxlength="512" style="width:520" value="����д������ע"/>
        <br></td>
      </tr>
      <tr>
        <td width="60" height="30">
          <b>������λ:</b>
        <br></td>
        <td align="left" width="120">
          
        <br></td>
        <td align="right" width="50">
          <b>������:</b>
        <br></td>
        <td align="left" width="100">
          
        <br></td>
        <td width="70" align="right">
          <b>����ʱ��:</b>
        <br></td>
        <td align="left" width="100">1
          <bean:write name="date"/>
        </td>
      </tr>
      <tr>
        <td colspan="6" height="40" valign="top">
          <template:formSubmit>
            <td>
              <html:submit property="action" styleClass="button">�ύ����</html:submit>
              <input type="button" value="��&nbsp;&nbsp;��" Class="button" onclick="javascript:backToAuditShow()"/>
            </td>
          </template:formSubmit>
        </td>
      </tr>
    </table>
  </html:form>
</logic:equal>
<iframe id="hiddenIframe" style="display:none"></iframe>
<script type="text/javascript">
	//���ص��ϼ���ѯҳ��
	function goquery(){
		window.location.href = "${ctx}/PartRequisitionAction.do?method=queryShowForAudit";
	}
	
	function goApplyquery(){
		window.location.href = "${ctx}/PartRequisitionAction.do?method=queryShow";
	}
</script>
</body>
</html>
