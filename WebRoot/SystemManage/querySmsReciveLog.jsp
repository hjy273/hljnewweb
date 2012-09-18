<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>

<%
    UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    String type = userInfo.getType().trim();
    String region=userInfo.getRegionID();
%>
<script language="javascript">
var usertype="<%=type%>";
function parseDate(date,sep){
  var d=date.split(sep);
  var selectDate=new Date();
  if(d[1].substring(0,1)=="0") d[1]=d[1].substring(1,2);
  if(d[2].substring(0,1)=="0") d[2]=d[2].substring(1,2);
  selectDate.setFullYear(parseInt(d[0]));
  selectDate.setMonth(parseInt(d[1])-1);
  selectDate.setDate(parseInt(d[2]));
  return selectDate;
}
function compareDate(date1,date2){
  if(date1>date2) return true;
  else return false;
}
function vertifyDate(){
  if(document.forms[0].begindate.value!=""&&document.forms[0].enddate.value!=""){
    var d1=parseDate(document.forms[0].begindate.value,"/");
    var d2=parseDate(document.forms[0].enddate.value,"/");
    if(compareDate(d1,d2)){
      alert("开始日期不能大于结束日期！");
      document.forms[0].enddate.value="";
    }
  }
}
function autoGetEndDate(){
  if(document.forms[0].begindate.value!=""){
    var d1=parseDate(document.forms[0].begindate.value,"/");
    var d2=new Date(),value="";
    d2.setTime(d1.getTime()+24*3600*1000);
    value=d2.getYear()+"/";
    if(d2.getMonth()+1<10) value=value+"0"+(d2.getMonth()+1)+"/";
    else value=value+(d2.getMonth()+1)+"/";
    if(d2.getDate()<10) value=value+"0"+d2.getDate();
    else value=value+d2.getDate();
    document.forms[0].enddate.value=value;
  }
}
function vertifyForm(){
  if(document.forms[0].begindate.value==""&&document.forms[0].enddate.value=="")
    if(confirm("你选择了全部查询，\n数据量太大可能查不出！")) return true;
    else return false;
  else{
    var d1=parseDate(document.forms[0].begindate.value,"/");
    var d2=parseDate(document.forms[0].enddate.value,"/");
    var d=new Date();
    d.setTime(d1.getTime()+15*24*3600*1000);
    var time=(d2.getTime()-d1.getTime())/(24*3600*1000);
    if(compareDate(d2,d))
      if(confirm("你选择了"+(time)+"天的查询范围，\n数据量太大可能查不出！")) return true;
      else return false;
  }
}
///////////////////////////////示例代码////////////////////////////
//区域和代维
    function onChangeCon(){
          k=1;
          for( i=0;i<document.all.workID.options.length;i++){
             if(document.all.workID.options[i].text.substring(0,6)== document.all.rId.value){
                 k++;
                document.all.cId.options[0].value="";
                document.all.cId.options[0].text="不限";
                document.all.cId.options.length=k;
                document.all.cId.options[k-1].value=document.all.workID.options[i].value;
                document.all.cId.options[k-1].text=document.all.workID.options[i].text.substring(8,document.all.workID.options[i].text.length);
             }
           }
           if(k==1){
             document.all.cId.options.length=1;
             document.all.cId.options[0].value="";
             document.all.cId.options[0].text="不限";
           }
           onChangeDeptUser();
    }

//    代维单位和巡检人
    function onChangeDeptUser()
    {
      var iArray = document.all.userID.options;   //所隐藏的数组
      var iIndex = 0;
      var iStart = 0; //
      var iEnd = iArray.length - 1;
      var iCount = 0;
      var iSeek = document.all.cId.value;       // 所要查找的值,上级菜单的值
      while(iSeek != ""){
           iCount++;
         iIndex =parseInt( ( iStart + iEnd )/2);

         if( iArray[iIndex].text.substring(8,18) < iSeek ){ //iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
                iStart = iIndex;
         }else if( iArray[iIndex].text.substring(8,18) > iSeek ){//iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
                    iEnd = iIndex;
         }else{
             if(iIndex == 0){
                break;
            }else{
                if(iArray[iIndex-1].text.substring(8,18) == iSeek){ //iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
                       iEnd = iIndex;
                }else{
                     break;
                }
            }
        }
        if(iIndex==iEnd-1){
        	if(iArray[iEnd].text.substring(8,18) == iSeek){ //iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
            	iIndex = iEnd;
            	//alert("==")
            	break;
        	}else{
        	//	alert("!="+iArray.length)
        		index = iArray.length;
         	   document.all.pID.options.length=0;
          	  break;
       	 	}
       	}
      }

      k=1;
      ////iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
      //=document.all.cId.value 所要查找的值,上级菜单的值
      for( i=iIndex;i<iArray.length && iArray[i].text.substring(8,18)==document.all.cId.value;i++){
          k++;
          document.all.uId.options.length=k;   //document.all.uId.options 要设置的select

          document.all.uId.options[0].value="";
          document.all.uId.options[0].text="不限";

          document.all.uId.options[k-1].value=document.all.userID.options[i].value;
          document.all.uId.options[k-1].text=document.all.userID.options[i].text.substring(20);
      }
      if(k==1){
        document.all.uId.options.length=1;
        document.all.uId.options[0].value="";
        document.all.uId.options[0].text="不限";
      }
      onChangePhone();
    }
//////////////////////////////////////////////////////////////////////////////////////////////////
function onChangePhone(){
  /*
  for(i=0;i<document.all.phoneworkId.options.length;i++){
    if(document.all.phoneworkId.options[i].value==document.all.uId.value){
      document.all.simid.value=document.all.phoneworkId.options[i].text;
    }
    if(document.all.phoneworkId.options[i].value==""&&document.all.phoneworkId.options[i].text=="不限"){
      document.all.simid.value="";
    }
  }
  if(document.all.uId.options.length==0||document.all.uId.options.selectedIndex==0)
    document.all.simid.value="";
  */
  var userID=document.all.uId.value;
  if(userID!="")
    hiddenInfoFrame.location="${ctx}/SystemManage/getSimForPatrolman.jsp?userid="+userID;
 // else 
	  //document.all.simid.value="";
}
</script>
<%
if(userInfo.getRegionID().substring( 2, 6 ).equals( "0000" )){%>
<body onload="onChangeCon();//init();">
<% }else{%>
<body onload="//init();">
<% }%>
<template:titile value="查询短信接收日志"/>
<html:form method="Post" action="/smsLogAction.do?method=querySMS_ReciveLog" onsubmit=" return vertifyForm()">
  <template:formTable contentwidth="300" namewidth="200">
        <logic:notEmpty name="reginfo">
          <template:formTr name="所属区域">
            <select name="regionid" class="inputtext" style="width:180px" id="rId"  onchange="onChangeCon()" >
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
          <template:formTr name="代维单位" tagID="conTr" >
            <select name="contractorid" class="inputtext" style="width:180px" id="cId" onchange="onChangeDeptUser()">
              <option value="">不限</option>
              <logic:present name="coninfo">
                <logic:iterate id="coninfoId" name="coninfo">
                  <option value="<bean:write name="coninfoId" property="contractorid"/>">
                  <bean:write name="coninfoId" property="contractorname"/></option>
                </logic:iterate>
              </logic:present>
            </select>
          </template:formTr>

          <logic:present name="coninfo">
              <select name="workID"   style="display:none">
                <logic:iterate id="coninfoId" name="coninfo">
                    <option value="<bean:write name="coninfoId" property="contractorid"/>"><bean:write name="coninfoId" property="regionid"/>--<bean:write name="coninfoId" property="contractorname"/></option>
                </logic:iterate>
              </select>
          </logic:present>

        </logic:notEmpty>

    <logic:equal value="group" name="PMType">
      <template:formTr name="巡检维护组">
       <select name="patrolmanid" class="inputtext" style="width:180px" id="uId" onchange="onChangePhone()">
        <option value="">不限</option>
        <logic:present name="uinfo">
          <logic:iterate id="uinfoId" name="uinfo">
            <option value="<bean:write name="uinfoId" property="patrolid"/>">
              <bean:write name="uinfoId" property="patrolname"/>
            </option>
          </logic:iterate>
        </logic:present>
      </select>
    </template:formTr>
    </logic:equal>

    <logic:notEqual value="group" name="PMType">
      <template:formTr name="巡 检 人">
       <select name="patrolmanid" class="inputtext" style="width:180px" id="uId" onchange="onChangePhone()">
        <option value="">不限</option>
        <logic:present name="uinfo">
          <logic:iterate id="uinfoId" name="uinfo">
            <option value="<bean:write name="uinfoId" property="patrolid"/>">
              <bean:write name="uinfoId" property="patrolname"/>
            </option>
          </logic:iterate>
        </logic:present>
      </select>
    </template:formTr>
    </logic:notEqual>

          <logic:present name="uinfo">
              <select name="userID"   style="display:none">
                <logic:iterate id="uinfoId" name="uinfo">
                    <option value="<bean:write name="uinfoId" property="patrolid"/>"><bean:write name="uinfoId" property="regionid"/>--<bean:write name="uinfoId" property="parentid"/>--<bean:write name="uinfoId" property="patrolname"/></option>
                </logic:iterate>
              </select>
          </logic:present>

    <template:formTr name="设备编号"  >
      <html:text property="deviceId" styleClass="inputtext" style="width:180;" maxlength="11"/>
    </template:formTr>


    <template:formTr name="开始日期">
      <html:text property="begindate" styleClass="Wdate inputtext" style="width:180;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" readonly="true"/>
    </template:formTr>
    <template:formTr name="结束日期" >
      <html:text property="enddate" styleClass="Wdate inputtext" style="width:180;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" readonly="true"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">查询</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
<script language="javascript">
<!--
var flag=0;
document.forms[0].begindate.onpropertychange=function(){
  autoGetEndDate();
}
document.forms[0].enddate.onpropertychange=function(){
  vertifyDate();
}
if(usertype=="11")onChangeCon();
else if(usertype=="12") onChangeDeptUser();
else if(usertype=="21") onChangeCon();
else if(usertype=="22") onChangePhone();
</script>
