<%@include file="/common/header.jsp"%>
<script language="javascript" type="">
function showV(){
document.getElementById("compAnalysis").src="${ctx}/ShowPmMonthlyCompVChart";
}
function showH(){
document.getElementById("compAnalysis").src="${ctx}/ShowPmMonthlyCompHChart";
}
</script>
<Br/>

<body onload="showV()">
<logic:equal value="group" name="PMType">
   <template:titile value="Ѳ��ά����Աȷ���"/>
</logic:equal>
<logic:notEqual value="group" name="PMType">
   <template:titile value="Ѳ��Ա�Աȷ���"/>
</logic:notEqual>


	<table width="500" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout"><tr><th class="thlist" align="center" width="300">��Ŀ</th><th class="thlist" align="center" width="200">ѡ��</th></tr>
      <template:formTr name="�Ա�����" >
   	<input  type="radio"  name="comptype" value="1" checked="checked" onclick="showV()"/> ����Ա�
       <input  type="radio"  name="comptype" value="2" onclick="showH()"/> ����Ա�
      </template:formTr>
    </table>

 <div align="center">
 	  <table width="620" border="0" align="center">
        <tr style="color:red">
          <td width="7%" scope="col" valign="top">˵����</td>
          <logic:equal value="group" name="PMType"> 
            <td width="94%" scope="col">
              1������Ա�:�Ƚ�ĳһ����Ѳ��ά������ѡ���·ݼ���ǰ�ڽ������¹������µ�Ѳ�쿼��.<br>
              2������Ա�:�Ƚ�ĳһ����Ѳ��ά������ѡ���·���������ͬһ�Ҵ�ά��˾������Ѳ��ά�����Ѳ�쿼��.<br> 
		    </td>
		  </logic:equal>
		  <logic:notEqual value="group" name="PMType"> 
            <td width="94%" scope="col">
              1������Ա�:����Ա�:�Ƚ�ĳһ����Ѳ��Ա��ѡ���·ݼ���ǰ�ڽ������¹������µ�Ѳ�쿼��.<br>
              2������Ա�:�Ƚ�ĳһ����Ѳ��Ա��ѡ���·���������ͬһ�Ҵ�ά��˾������Ѳ��Ա��Ѳ�쿼��.<br> 
		    </td>
		  </logic:notEqual>
        </tr>
      </table>
   </div>
   
 
   <div>
       <iframe id="compAnalysis" marginWidth="0" marginHeight="0" frameBorder=0 width="100%" scrolling=auto height="100%"> </iframe>
   </div> 

</body>