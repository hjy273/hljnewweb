<%@include file="/common/header.jsp"%>
<script language="javascript" type="text/javascript">
<!--
//�Ƿ�ѡȡ������
var ifCheckedFlag = 0;
var plantype = 7;

function checkInput(){
	var yb = document.batchBean.startdate.value.substring(0,4);
    var mb = parseInt(document.batchBean.startdate.value.substring(5,7) ,10) - 1;
    var db = parseInt(document.batchBean.startdate.value.substring(8,10),10);
    var tdate = new Date();
    var indate = new Date(yb,mb,db);
    var de = parseInt(document.batchBean.enddate.value.substring(8,10),10);
    var tdate = new Date();
    var indate = new Date(yb,mb,db);
    if(de-db > 15){
    	alert("�ƻ����ڲ��ܴ���15�죡");
    	return false;
    }else if(document.batchBean.batchname.value == ""){
          alert("����д�����ƻ����ƣ�");
          document.batchBean.batchname.focus();
        return false;
    }else if(document.batchBean.startdate.value == ""){
        alert("��ѡ��ƻ���ʼ���� ��");
        document.batchBean.startdate.focus();
        return false;
    }else if(document.batchBean.enddate.value == ""){
        alert("��ѡ��ƻ��������� ��");
        document.batchBean.enddate.focus();
        return false;
    }else if(indate < tdate){
		alert("��ʼ���ڲ���С�ڵ�ǰ���ڣ�");
		return false;
	}else
    	return true;
}

function savePlan(){
    if(checkInput()){
    	document.batchBean.submit();
    }
}

// ѡ������
function GetSelectDateTHIS(strID) {
   
   // document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    if(document.getElementById(strID).value !="" && document.getElementById(strID).value !=null){
      if(checkBDate()){
        //preGetWeeklyTaskList();
        setPlanName();
      }
    }
    return true;
}


function checkBDate(){
    var yb = document.batchBean.startdate.value.substring(0,4);
    var mb = parseInt(document.batchBean.startdate.value.substring(5,7) ,10) - 1;
    var db = parseInt(document.batchBean.startdate.value.substring(8,10),10);
    var date = new Date(yb,mb,db);
    if(plantype == 7){
    	date = new Date(yb,mb,db + 6);	
    }if(plantype == 10)
        date = new Date(yb,mb,db + 9);
    else if(plantype > 10)
        date = new Date(yb,mb,db + 14);
    else if(plantype > 10)
        date = new Date(yb,mb,db + 9);
    y = date.getYear();
    m = date.getMonth() + 1;
    d = date.getDate();
//ȷ�����ڲ��ܳ�����ĩ
    if(parseInt(m) != mb+1){
       m=mb+1;
       if(m == 2){//��2��
             if(y %400 ==0 ||(y %4 == 0  && y % 100 !=0))
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
    document.batchBean.enddate.value = yb + "/" + m + "/" + d;
    return true;
}
function GetSelectDate(strID) {
    if(document.batchBean.startdate.value =="" || document.batchBean.startdate.value ==null){
        alert("��û�п�ʼ����!");
        document.batchBean.startdate.focus();
        return false;
    }
    document.getElementById(strID).value = getPopDate(document.getElementById(strID).value);
    //��ʼʱ��
    var yb = document.batchBean.startdate.value.substring(0,4);
    var mb = parseInt(document.batchBean.startdate.value.substring(5,7) ,10);
    var db = parseInt(document.batchBean.startdate.value.substring(8,10),10);
    //����ʱ��
    var ye = document.batchBean.enddate.value.substring(0,4);
    var me = parseInt(document.batchBean.enddate.value.substring(5,7) ,10);
    var de = parseInt(document.batchBean.enddate.value.substring(8,10),10);
    if(de-db > 15){
    	alert("�ƻ����ڲ��ܴ���15�죡");
    	return false;
    }
    if(yb!=ye){
      alert("�ƻ����ܿ���!");
      document.getElementById(strID).value="";
      document.batchBean.enddate.focus();
      return false;
    }
    if(mb!=me){
      alert("�ƻ����ܿ���!");
      document.getElementById(strID).value="";
      document.batchBean.enddate.focus();
      return false;
    }
     if(de < db || me < mb || ye < yb){
      alert("�������ڲ���С�ڿ�ʼ����!");
      document.getElementById(strID).value="";
      document.batchBean.enddate.focus();
      return false;
    }
    return true;
}
function setPlanName(weekNumber){
    var newWeekNumber = 1;
    var obj = document.getElementById("deptname");

    var y = document.batchBean.startdate.value.substring(0,4);
    var m = document.batchBean.startdate.value.substring(5,7);
    var d = parseInt(document.batchBean.startdate.value.substring(8,10),10);
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
     	planname =obj.value + y + "��"+ m + "�µ�" + newWeekNumber + "������Ѳ��ƻ�";
     	//batchBean.batchname.value = planname;
     }else if(plantype == 10){
        if(d >=1 && d<=10)
            newWeekNumber ="��";
        if(d >=11 && d<=20)
            newWeekNumber = "��";
         if(d >=21 )
            newWeekNumber ="��";
        planname =obj.value + y + "��"+ m + "��" + newWeekNumber + "Ѯ����Ѳ��ƻ�";
    }else if(plantype == 15){
         if(d >=1 && d<=15)
            newWeekNumber = "��";
        if(d >=16 )
            newWeekNumber = "��";
        planname =obj.value + y + "��"+ m + "��" + newWeekNumber + "��������Ѳ��ƻ�";
    }else if(plantype == 0){
        planname =obj.value + y + "��"+ m + "���Զ�������Ѳ��ƻ�";
    }

    document.batchBean.batchname.value = planname;
    document.batchBean.taskopname.value = planname;
    //set hidden id
    

   
    //alert(batchBean.id.value);
}

 function settype(obj){
        plantype = obj.value;
        document.batchBean.startdate.value="";
        document.batchBean.enddate.value="";
 }
function setopname(){
	var opname = document.all.IdOp.options[document.all.IdOp.selectedIndex].text
	document.all.IdName.value=opname;
}
//-->
</script>
<body onload="">
<br>
<template:titile value="�����ƶ�Ѳ��ƻ�"/>
<html:form action="/batchPlan.do?method=addBatchPlan" onsubmit="return checkInput()">
	<input  id="deptname" type="hidden"  name="deptname" value="<bean:write name="LOGIN_USER_DEPT_NAME"/>"/>
	
  <template:formTable>
    <input type="hidden" name="patrolmode" value="01"/>
    <!-- 
     <template:formTr name="Ѳ�췽ʽ" >
    	<select name="patrolmode" class="inputtext" style="width:250px">
    		<option value="01">�ֶ���ʽ</option>
    		<option value="02">�Զ���ʽ</option>
    	</select>
    </template:formTr> -->
    <template:formTr name="�ƻ�����" >
    	<input  type="radio"  name="batchtype" value="7"  checked="checked" onclick="settype(this)" /> ��
        <input  type="radio"  name="batchtype" value="10" onclick="settype(this)" /> Ѯ
       
        <input  type="radio"  name="batchtype" value="15" onclick="settype(this)"/> ����
         <!-- <input  type="radio"  name="batchtype" value="0" onclick="settype(this)"/> �Զ���
         -->
    </template:formTr>
    
    <template:formTr name="��ʼ����" >
        <html:text property="startdate" styleId="startdate" styleClass="Wdate" style="width:225px;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'});" onchange="GetSelectDateTHIS('startdate')" readonly="true"/>
    </template:formTr>
    
    <template:formTr name="��������" >
        <html:text property="enddate" styleId="enddate" styleClass="Wdate" style="width:225px;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'});" readonly="true"/>
    </template:formTr>
    <template:formTr name="�����ƻ�����" >
      <html:text property="batchname" styleId="batchname" styleClass="inputtext" style="width:250px"/>
      <html:hidden property="taskopname"/>
    </template:formTr>
    <template:formSubmit>
     
      <td>
        <html:button property="action" styleClass="button" onclick="savePlan()">��һ��</html:button>
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
         2�������ƻ��ƶ�ֻ��Լƻ�ģ���ƶ�����û���ƶ��ƻ�ģ�������ƶ�ģ�塣<br>
         3��Ϊ��������Ĺ���Ч�ʡ��������Ĺ�������������Ϊ�����ӷ��㡢��ݵ������ƶ��ƻ����ܡ�<br>
         4��ͬһ���߶β��ܰ��������������������С���Լ���������ڵ�ǰ�ƻ�ģ�塣<br>
         5���������ƶ���ǰ������ǰ�ļƻ�������ʼ���ڲ���С�ڵ��ڵ�ǰ���ڡ�<br>
      	 6��ͬһѲ���飨�ˣ��ļƻ�ִ��ʱ��β��ܽ��棬����ǰ�ƻ��Ŀ�ʼʱ�䲻�ܿ�����һ�ƻ��ڡ�<br>
      	 7��ͬһѲ����ͬһʱ����ڲ����ظ��ƶ��ƻ���<br>	 
      	 8��Ѳ��ƻ����ܿ���<br>
		  </td>
        </tr>
      </table>
   </div>

</body>
