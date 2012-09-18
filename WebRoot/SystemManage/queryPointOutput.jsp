<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>

<script language="javascript" type="">
  function checkvalue(){
     var startyear = PointOutputBean.startDate.value.substring(0,4);
     var startmonth = parseInt(PointOutputBean.startDate.value.substring(5,7) ,10) - 1;
     var startday = parseInt(PointOutputBean.startDate.value.substring(8,10),10);
     var thestartdate = new Date(startyear,startmonth,startday);

     var endyear = PointOutputBean.endDate.value.substring(0,4);
     var endmonth = parseInt(PointOutputBean.endDate.value.substring(5,7) ,10) - 1;
     var endday = parseInt(PointOutputBean.endDate.value.substring(8,10),10);
     var theenddate = new Date(endyear,endmonth,endday);

     var todaydate = new Date();
     if (PointOutputBean.startDate.value == ""){
         alert("请选择起始日期");
         return false;
     }else if (PointOutputBean.endDate.value == ""){
         alert("请选择终止日期");
         return false;
     }else if(theenddate < thestartdate){
		alert("终止日期不能小于起始日期！");
		return false;
	 }else if(thestartdate > todaydate){
		alert("起始日期不能大于当前日期！");
		return false;    
     }else if(theenddate > todaydate){
		alert("终止日期不能大于当前日期！");
		return false;
     }
     return true;    
  }
</script>

<body >
<template:titile value="导出巡检点"/>
<html:form method="Post" action="/PointOutputAction?method=outPutPointInfo" onsubmit="return checkvalue();">
  <template:formTable contentwidth="200" namewidth="300">
     <logic:equal value="11" name="LOGIN_USER" property="type">
	    <template:formTr name="查询范围" >
		   <select name="regionID" class="inputtext" style="width:200px"> 
		        <logic:present name="regioninfo">
		          <logic:iterate id="regioninfoId" name="regioninfo">
		            <option value="<bean:write name="regioninfoId" property="regionid"/>">
		                 <bean:write name="regioninfoId" property="regionname"/>
		            </option>
		          </logic:iterate>
		        </logic:present>
		   </select>
	    </template:formTr>
	 </logic:equal>
	 <template:formTr name="起始日期" >
      <html:text readonly="true" property="startDate" styleClass="inputtext" style="width:200" maxlength="12"/>
      <apptag:date property="startDate"/>
    </template:formTr>
    <template:formTr name="终止日期" >
      <html:text readonly="true" property="endDate" styleClass="inputtext" style="width:200" maxlength="12"/>
      <apptag:date property="endDate"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">导出</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
</body>