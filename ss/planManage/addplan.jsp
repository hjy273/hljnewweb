<%@include file="/common/header.jsp"%>
<script language="javascript" type="text/javascript">
<!--
//�Ƿ�ѡȡ������
var ifCheckedFlag = 0;
var plantype = 7;

function checkInput(){
    //��ʼʱ��
    var yb = parseInt($('planBean').begindate.value.substring(0,4) ,10);
    var mb = parseInt($('planBean').begindate.value.substring(5,7) ,10);
    var db = parseInt($('planBean').begindate.value.substring(8,10),10);
    //����ʱ��
    var ye = parseInt($('planBean').enddate.value.substring(0,4) ,10);
    var me = parseInt($('planBean').enddate.value.substring(5,7) ,10);
    var de = parseInt($('planBean').enddate.value.substring(8,10),10);
    var tdate = new Date();
    var indate = new Date(yb,mb-1,db);
    if(yb!=ye){
      	alert("�ƻ����ܿ���!");
		document.getElementById(strID).value="";
       	$('planBean').enddate.focus();
      	return false;
    }
    if(mb!=me){
      	alert("�ƻ����ܿ���!");
		document.getElementById(strID).value="";
      	$('planBean').enddate.focus();
      	return false;
    }
    if(de-db > 15){
    	alert("�ƻ����ڲ��ܴ���15�죡");
    	return false;
    }else if($('planBean').executorid.value == ""){
        alert("��ѡ��ƻ�ִ���� ��");
        $('planBean').executorid.focus();
        return false;
    }else if($('planBean').begindate.value == ""){
        alert("��ѡ��ƻ���ʼ���� ��");
        $('planBean').begindate.focus();
        return false;
    }else if($('planBean').enddate.value == ""){
        alert("��ѡ��ƻ��������� ��");
        $('planBean').enddate.focus();
        return false;
    }else if(indate < tdate){
    	alert("��ʼ���ڲ������ڵ�ǰ���ڣ�");
		return false;
	}else
    	return true;
}

function savePlan(isload){
    if(checkInput()){
    	$('planBean').isload.value= isload;
        $('planBean').submit();
    }
}

// ѡ������
function GetSelectDateTHIS(strID) {
	if($('planBean').executorid.value == ""){
		alert("��ѡ��Ѳ��ά���� ��");
		$('planBean').executorid.focus();
		document.getElementById(strID).value="";
   		return false;
    }
    if(document.getElementById(strID).value !="" && document.getElementById(strID).value != null){
      	if(checkBDate()){
        	setPlanName();
      	}
    }
    return true;
}


function checkBDate(){
    var yb = $('planBean').begindate.value.substring(0,4);
    var mb = parseInt($('planBean').begindate.value.substring(5,7) ,10) - 1;
    var db = parseInt($('planBean').begindate.value.substring(8,10),10);
    var date = new Date(yb,mb,db);
    
    if(plantype == 7){
    	date = new Date(yb,mb,db + 6);	
    }if(plantype == 10)
        date = new Date(yb,mb,db + 9);
    else if(plantype == 15)
        date = new Date(yb,mb,db + 14);
    else if(plantype == 30)
        date = new Date(yb,mb,db + 31);
    //y = date.getYear();
    m = date.getMonth() + 1;
    d = date.getDate();
	
	//ȷ�����ڲ��ܳ�����ĩ
    if(parseInt(m) != mb+1){
       m=mb+1;
       if(m == 2){//��2��
             if(yb %400 ==0 ||(yb %4 == 0  && yb % 100 !=0))
                d=29;
            else
                d=28;
       }else if( m== 1 || m== 3||m== 5||m== 7||m== 8 ||m== 10||m== 12){
         d=31;
       }else{
            d=30;
       }
    }
    if(m < 10){
        m = "0" + m;
    }
    if(d < 10){
        d = "0" + d;
    }
    $('planBean').enddate.value = yb + "/" + m + "/" + d;
    return true;
}
function GetSelectDate(strID) {
    if($('planBean').begindate.value =="" || $('planBean').begindate.value ==null){
        alert("��û�п�ʼ����!");
        $('planBean').begindate.focus();
        return false;
    }
    document.getElementById(strID).value = getPopDate(document.getElementById(strID).value);
    //��ʼʱ��
    var yb = $('planBean').begindate.value.substring(0,4);
    var mb = parseInt($('planBean').begindate.value.substring(5,7) ,10);
    var db = parseInt($('planBean').begindate.value.substring(8,10),10);
    //����ʱ��
    var ye = $('planBean').enddate.value.substring(0,4);
    var me = parseInt(planBean.enddate.value.substring(5,7) ,10);
    var de = parseInt($('planBean').enddate.value.substring(8,10),10);
    if(de-db > 15){
    	alert("�ƻ����ڲ��ܴ���15�죡");
    	return false;
    }
    if(yb!=ye){
      alert("�ƻ����ܿ���!");
      document.getElementById(strID).value="";
       $('planBean').enddate.focus();
      return false;
    }
    if(mb!=me){
      alert("�ƻ����ܿ���!");
      document.getElementById(strID).value="";
      $('planBean').enddate.focus();
      return false;
    }
     if(de < db || me < mb || ye < yb){
      alert("�������ڲ���С�ڿ�ʼ����!");
      document.getElementById(strID).value="";
      $('planBean').enddate.focus();
      return false;
    }
    return true;
}
function setPlanName(weekNumber){
    var newWeekNumber = 1;
    var obj = document.getElementById("deptname");
    var i = $('planBean').executorid.selectedIndex;
    if(i == null){
        i = 0;
    }

    var y = $('planBean').begindate.value.substring(0,4);
    var m = $('planBean').begindate.value.substring(5,7);
    var d = parseInt($('planBean').begindate.value.substring(8,10),10);
    var planname = "";
    if(plantype == 7){
    	if(d >=1 && d<=7)
        	newWeekNumber = 1;
    	if(d >=8 && d<=14)
        	newWeekNumber = 2;
     	if(d >=15 && d<=21)
        	newWeekNumber = 3;
     	if(d >=22 && d<=28)
        	newWeekNumber = 4;
     	if(d >=29 && d<=31)
        	newWeekNumber = 5;
     	planname =obj.value + $('planBean').executorid.options[i].text + y + "��"+ m + "�µ�" + newWeekNumber + "��Ѳ��ƻ�";
     	//$('planBean').planname.value = planname;
     }else if(plantype == 10){
        if(d >=1 && d<=10)
            newWeekNumber ="��";
        if(d >=11 && d<=20)
            newWeekNumber = "��";
         if(d >=21 )
            newWeekNumber ="��";
        planname =obj.value + $('planBean').executorid.options[i].text + y + "��"+ m + "��" + newWeekNumber + "ѮѲ��ƻ�";
    }else if(plantype == 15){
         if(d >=1 && d<=15)
            newWeekNumber = "��";
        if(d >=16 )
            newWeekNumber = "��";
        planname =obj.value + $('planBean').executorid.options[i].text + y + "��"+ m + "��" + newWeekNumber + "����Ѳ��ƻ�";
    }else if(plantype == 0){
        planname =obj.value + $('planBean').executorid.options[i].text + y + "��"+ m + "���Զ���Ѳ��ƻ�";
    }

    $('planBean').planname.value = planname;
    //set hidden id
    if(weekNumber < 10){
        weekNumber = "0"+ weekNumber;
    }

    $('planBean').id.value = $('planBean').executorid.options[i].value + y + m +"0"+ newWeekNumber;
    //alert($('planBean').id.value);
}

 function settype(obj){
        plantype = obj.value;
        $('planBean').begindate.value="";
        $('planBean').enddate.value="";
 }

//-->
</script>
<body onload="">
<br>
<template:titile value="����Ѳ��ƻ�"/>
<html:form action="/PlanAction?method=createPlan" styleId="planBean" onsubmit="return checkInput()">
  <input  id="deptname" type="hidden"  name="regionid" value="<bean:write name="LOGIN_USER_DEPT_NAME"/>"/>
 <input type="hidden" name="isload" />
  <template:formTable >
    <logic:equal value="group" name="PMType">
        <template:formTr name="Ѳ��ά����" isOdd="false">
                <select  name="executorid"  Class="inputtext" style="width:250px">
                    <option value="">��ѡ��ƻ�Ѳ��ά����</option>
                      <logic:present name="patrolgroup">
                        <logic:iterate id="pgId" name="patrolgroup">
                            <option value="<bean:write name="pgId" property="patrolid"/>"><bean:write name="pgId" property="patrolname"/></option>
                        </logic:iterate>
                      </logic:present>
                </select>
                <!-- hidden id -->
                <html:hidden property="id"/>
                
          </template:formTr>
    </logic:equal>
    <logic:notEqual value="group" name="PMType">
        <template:formTr name="ִ �� ��" isOdd="false">
                <select  name="executorid"  Class="inputtext" style="width:250px">
                    <option value="">��ѡ��ƻ�ִ����</option>
                      <logic:present name="patrolgroup">
                        <logic:iterate id="pgId" name="patrolgroup">
                            <option value="<bean:write name="pgId" property="patrolid"/>"><bean:write name="pgId" property="patrolname"/></option>
                        </logic:iterate>
                      </logic:present>
                </select>
                <!-- hidden id -->
                <html:hidden property="id"/>
          </template:formTr>
    </logic:notEqual>
    <input type="hidden" name="patrolmode" value="01"/>
    <!--  template:formTr name="Ѳ�췽ʽ"  -->
    	<!-- select name="patrolmode" class="inputtext" style="width:250px">
    		<option value="01">�ֶ���ʽ</option>
    		<option value="02">�Զ���ʽ</option>
    	</select -->
    <!-- /template:formTr -->
    <template:formTr name="�ƻ�����" >
    	<input  type="radio"  name="plantype" value="7"  checked="checked" onclick="settype(this)" /> ��
         <input  type="radio"  name="plantype" value="10" onclick="settype(this)" /> Ѯ
        
        <input  type="radio"  name="plantype" value="15" onclick="settype(this)"/> ����
         <!--  <input  type="radio"  name="plantype" value="0" onclick="settype(this)"/> �Զ���-->
        
    </template:formTr>
	

    <template:formTr name="��ʼ����" >
        <html:text property="begindate" styleClass="Wdate" styleId="begindate" style="width:225px;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'});" onchange="GetSelectDateTHIS('begindate')" readonly="true"/>
    </template:formTr>
    <template:formTr name="��������" >
        <html:text property="enddate" styleId="enddate" styleClass="Wdate" style="width:225px;"  onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'begindate\')}'});" readonly="true"/>
    </template:formTr>
    <template:formTr name="�ƻ�����" >
      <html:text property="planname" styleClass="inputtext" style="width:250px"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:button property="action" styleClass="button" onclick="savePlan('n')">��������</html:button>
      </td>
      <td>
        <html:button property="action" styleClass="button" onclick="savePlan('y')">��������</html:button>
      </td>
     
    </template:formSubmit>
  </template:formTable>
</html:form>
 <div align="center">
 	  <table width="620" border="0" align="center">
        <tr style="color:red">
          <td width="7%" scope="col" valign="top">˵����</td>
          <td width="94%" scope="col">
         1�����������ƶ��ƻ�ʱ���ȿ����ܼƻ���Ѯ�ƻ���<br>
         2��Ϊ�˸��ӷ��㡢����ƶ��ƻ����������ƶ��ƻ�ǰ���ƶ��ƻ�ģ�塣ģ���ƶ����������ƻ�ģ���ƶ���.<br>
         3��Ѳ��ƻ����ܿ���<br>
         4���������ƶ���ǰ������ǰ�ļƻ�������ʼ���ڲ���С�ڵ��ڵ�ǰ���ڡ�<br>
      	 5��ͬһѲ���飨�ˣ��ļƻ�ִ��ʱ��β��ܽ��棬����ǰ�ƻ��Ŀ�ʼʱ�䲻�ܿ�����һ�ƻ��ڡ�<br>
      	 6��ͬһѲ����ͬһʱ����ڲ����ظ��ƶ��ƻ���<br>
      	 6��ͬһ���߶β��ܰ���������������������,�����п���Ӱ�쵽����Ѳ���ʡ���Լ���������ڵ�ǰ�ƻ���<br>		 
		  </td>
        </tr>
      </table>
   </div>
</body>
