<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>

<BR/>
<script language="javascript" type="">
///////////////////////////////ʾ������////////////////////////////
    var comptype = 1;

//����ʹ�ά
    function onChangeCon(){
      k=0;
      for( i=0;i<document.all.workID.options.length;i++){

             if(document.all.workID.options[i].text.substring(0,6)== document.all.rId.value){
                 k++;
                document.all.cId.options.length=k;
                document.all.cId.options[k-1].value=document.all.workID.options[i].value;
                document.all.cId.options[k-1].text=document.all.workID.options[i].text.substring(8,document.all.workID.options.length);
             }

      }
      if(k==0)
       document.all.cId.options.length=0;
     if(document.all.rId.value.substring(2,6)!='0000'){
         onChangeDeptUser();
     }else{
       document.all.uId.options.length=0;
     }

    }


   function settype(objN){
	var v = objN.value;
	if(v == "1"){
		comptype=1;
        endMonthTr.style.display="";
        startMonthTr.style.display="";
        theMonthTr.style.display="none";
        conTr.style.display="";
	}

	if(v == "2"){
		comptype=2;
        endMonthTr.style.display="none";
        startMonthTr.style.display="none";
        theMonthTr.style.display="";
        conTr.style.display="none";
	}
    document.all.thecomptype.value = comptype;
    }

    function checkvalidate(){
        if (startMonthTr.style.display == ""){
           if (document.all.endMonth.value <= document.all.startMonth.value){
              alert("��ֹ�·�һ��Ҫ������ʼ�·�");
              return false;
           }
        }
        if (document.all.endMonth.value - document.all.startMonth.value >=6){
           alert("��ֹ�·ݷ�Χ���ܴ���������");
           return false;
        }
        return true;
    } 


//////////////////////////////////////////////////////////////////////////////////////////////////
</script>
<Br/>
<%
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
%>

<body>
 <template:titile value="�д�ά��˾�Աȷ���"/>
<html:form method="Post" action="/CompAnalysisAction?method=showComCompAnalysis">
  <input  id="thecomptype"  name="thecomptype" type="hidden" value="1"/>
  <template:formTable contentwidth="200" namewidth="300">
        <template:formTr name="�Ա�����" >
	    	<input  type="radio"  name="comptype" value="1"  checked="checked" onclick="settype(this)" /> ����Ա�
	    	<%
	    	   if( !userinfo.getType().equals("22") ){
	    	%>
	           <input  type="radio"  name="comptype" value="2" onclick="settype(this)" /> ����Ա�
	        <%} %>
        </template:formTr>
        <logic:notEmpty name="reginfo">
          <template:formTr name="��������" isOdd="false">
            <select name="regionId" class="inputtext" style="width:180px" id="rId"  onchange="onChangeCon()"  >
              <logic:present name="reginfo">
                <logic:iterate id="reginfoId" name="reginfo">
                  <option value="<bean:write name="reginfoId" property="regionid"/>">
                  <bean:write name="reginfoId" property="regionname"/>
                  </option>
                </logic:iterate>
              </logic:present>
            </select>
          </template:formTr>
        </logic:notEmpty>

        <logic:notEmpty name="coninfo">
          <template:formTr name="��ά��λ" tagID="conTr">
            <select name="contractorID" class="inputtext" style="width:180px" id="cId" onchange="onChangeDeptUser()">
              <logic:present name="coninfo">
                <logic:iterate id="coninfoId" name="coninfo">
                  <option value="<bean:write name="coninfoId" property="contractorid"/>">
                  <bean:write name="coninfoId" property="contractorname"/></option>
                </logic:iterate>
              </logic:present>
            </select>
          </template:formTr>

          <logic:present name="coninfo">
              <select name="workID" style="display:none">
                <logic:iterate id="coninfoId" name="coninfo">
                    <option value="<bean:write name="coninfoId" property="contractorid"/>"><bean:write name="coninfoId" property="regionid"/>--<bean:write name="coninfoId" property="contractorname"/></option>
                </logic:iterate>
              </select>
          </logic:present>
      </logic:notEmpty>
       
    <template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��" isOdd="false">
        <apptag:getYearOptions/>
        <html:select property="endYear" styleClass="inputtext" style="width:180">
          <html:options collection="yearCollection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
      <template:formTr tagID="startMonthTr" name="��ʼ�·�" isOdd="false">
        <html:select property="startMonth" styleClass="inputtext" style="width:180">
          <html:option value="01">һ��</html:option>
          <html:option value="02">����</html:option>
          <html:option value="03">����</html:option>
          <html:option value="04">����</html:option>
          <html:option value="05">����</html:option>
          <html:option value="06">����</html:option>
          <html:option value="07">����</html:option>
          <html:option value="08">����</html:option>
          <html:option value="09">����</html:option>
          <html:option value="10">ʮ��</html:option>
          <html:option value="11">ʮһ��</html:option>
          <html:option value="12">ʮ����</html:option>
        </html:select>
      </template:formTr>
      <template:formTr tagID="endMonthTr" name="��ֹ�·�" isOdd="false">
        <html:select property="endMonth" styleClass="inputtext" style="width:180">
          <html:option value="01">һ��</html:option>
          <html:option value="02">����</html:option>
          <html:option value="03">����</html:option>
          <html:option value="04">����</html:option>
          <html:option value="05">����</html:option>
          <html:option value="06">����</html:option>
          <html:option value="07">����</html:option>
          <html:option value="08">����</html:option>
          <html:option value="09">����</html:option>
          <html:option value="10">ʮ��</html:option>
          <html:option value="11">ʮһ��</html:option>
          <html:option value="12">ʮ����</html:option>
        </html:select>
      </template:formTr>
      
      <template:formTr tagID="theMonthTr" name="��&nbsp;&nbsp;&nbsp;&nbsp;��" isOdd="false" style="display:none">
        <html:select property="theMonth" styleClass="inputtext" style="width:180">
          <html:option value="01">һ��</html:option>
          <html:option value="02">����</html:option>
          <html:option value="03">����</html:option>
          <html:option value="04">����</html:option>
          <html:option value="05">����</html:option>
          <html:option value="06">����</html:option>
          <html:option value="07">����</html:option>
          <html:option value="08">����</html:option>
          <html:option value="09">����</html:option>
          <html:option value="10">ʮ��</html:option>
          <html:option value="11">ʮһ��</html:option>
          <html:option value="12">ʮ����</html:option>
        </html:select>
      </template:formTr>

    <template:formSubmit>
      <td>
        <html:submit styleClass="button" onclick="return checkvalidate()">��ѯ</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">ȡ��</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>

 <div align="center">
 	  <table width="620" border="0" align="center">
        <tr style="color:red">
          <td width="7%" scope="col" valign="top">˵����</td>
            <td width="94%" scope="col">
              1������Ա�:�Ƚ�ĳһ�����д�ά��˾��ѡ����ֹ�·���(���ܳ���������)������Ѳ���ʼ�ʵ��Ѳ����.<br>
              2������Ա�:�Ƚ�ĳ����ĳһ���ƶ���˾����Ͻ�������д�ά��˾��Ѳ���ʼ�ʵ��Ѳ����.<br> 
		    </td>
        </tr>
      </table>
   </div>
</body>