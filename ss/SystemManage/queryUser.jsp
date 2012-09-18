<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo"%>
<%
  UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
      "LOGIN_USER");
%>
<script language="javascript" type="">
//初始化区域数组信息
    var rowArr = new Array();//一行的信息
    var RegionArr = new Array();//所有的信息
    function initRegionArray(regionid,regionname){
      rowArr[0] = regionid;
      rowArr[1] = regionname;
      RegionArr[RegionArr.length] = rowArr;
      rowArr = new Array();
      return true;
    }
//初始化部门数组信息
    var rowArr1 = new Array();//一行的信息
    var DeptArr = new Array();//所有的信息
    function initDeptArray(deptid,deptname,regionid){
      rowArr1[0] = deptid;
      rowArr1[1] = deptname;
      rowArr1[2] = regionid;
      DeptArr[DeptArr.length] = rowArr1;
      rowArr1 = new Array();
      return true;
    }
//初始化代维数组信息
    var rowArr2 = new Array();//一行的信息
    var ConArr = new Array();//所有的信息
    function initConArray(conid,conname,regionid){
      rowArr2[0] = conid;
      rowArr2[1] = conname;
      rowArr2[2] = regionid;
      ConArr[ConArr.length] = rowArr2;
      rowArr2 = new Array();
      return true;
    }
//初始化用户数组信息
    var rowArr3 = new Array();//一行的信息
    var UserArr = new Array();//所有的信息
    function initUserArray(userid,username,regionid,deptid,deptype){
      rowArr3[0] = userid;
      rowArr3[1] = username;
      rowArr3[2] = regionid;
      rowArr3[3] = deptid;
      rowArr3[4] = deptype;
      UserArr[UserArr.length] = rowArr3;
      rowArr3 = new Array();
      return true;
    }

    function showdep(){
     document.getElementById("type").value=1;
     conTr.style.display = "none";
     deptTr.style.display = "";
    }
    function showcon(){
    document.getElementById("type").value=2;
    deptTr.style.display = "none";
    conTr.style.display = "";
    }
    //区域和部门
    function onChangeDept(objId ) //
	{

		var slt1Obj=document.getElementById(objId);
        var slt2Obj=document.getElementById("dId");
        if(slt1Obj.value == ""){
          i=0;
          for(j= 0; j< this.DeptArr.length; j++ )
          {
            i++;
          }
          slt2Obj.options.length = i+1;
          k=1
          slt2Obj.options[0].text="不限";
          slt2Obj.options[0].value="";
          for(j =0;j<this.DeptArr.length;j++)
          {
              slt2Obj.options[k].text=this.DeptArr[j][1];
              slt2Obj.options[k].value=this.DeptArr[j][0];
              k++;
          }
        }else{
          i=0;
          for(j= 0; j< this.DeptArr.length; j++ )
          {
            if(slt1Obj.value == DeptArr[j][2])
            {
              i++;
            }
          }
          slt2Obj.options.length = i+1;
          k=1
          slt2Obj.options[0].text="不限";
          slt2Obj.options[0].value="";
          for(j =0;j<this.DeptArr.length;j++)
          {
            if(slt1Obj.value == DeptArr[j][2])
            {
              slt2Obj.options[k].text=this.DeptArr[j][1];
              slt2Obj.options[k].value=this.DeptArr[j][0];
              k++;
            }
          }
        }
		return true;
	}
        //区域和代维
    function onChangeCon(objId ) //
	{
		var slt1Obj=document.getElementById(objId);
        var slt2Obj=document.getElementById("cId");
        if(slt1Obj.value == ""){
          i=0;
          for(j= 0; j< this.ConArr.length; j++ )
          {
            i++;
          }
          slt2Obj.options.length = i+1;
          k=1
          slt2Obj.options[0].text="不限";
          slt2Obj.options[0].value="";
          for(j =0;j<this.ConArr.length;j++)
          {
              slt2Obj.options[k].text=this.ConArr[j][1];
              slt2Obj.options[k].value=this.ConArr[j][0];
              k++;
          }
        }else{
          i=0;
          for(j= 0; j< this.ConArr.length; j++ )
          {
            if(slt1Obj.value == ConArr[j][2])
            {
              i++;
            }
          }
          slt2Obj.options.length = i+1;
          k=1
          slt2Obj.options[0].text="不限";
          slt2Obj.options[0].value="";
          for(j =0;j<this.ConArr.length;j++)
          {
            if(slt1Obj.value == ConArr[j][2])
            {
              slt2Obj.options[k].text=this.ConArr[j][1];
              slt2Obj.options[k].value=this.ConArr[j][0];
              k++;
            }
          }
        }
		return true;
	}
        //区域和用户
    function onChangeUser(objId ) //
	{
		var slt1Obj=document.getElementById(objId);
        var slt2Obj=document.getElementById("uId");
        var deptype=document.getElementById("type").value;
        if(slt1Obj.value == ""){
          i=0;
          if(deptype == ""){
            for(j= 0; j< this.UserArr.length; j++ )
            {
              i++;
            }
          }else{
            for(j= 0; j< this.UserArr.length; j++ )
            {
              if(deptype == UserArr[j][4])
              i++;
            }
          }
          slt2Obj.options.length = i+1;
          k=1
          slt2Obj.options[0].text="不限";
          slt2Obj.options[0].value="";
          if(deptype == ""){
            for(j =0;j<this.UserArr.length;j++)
            {
              slt2Obj.options[k].text=this.UserArr[j][1];
              slt2Obj.options[k].value=this.UserArr[j][0];
              k++;
            }
          }else{
          for(j =0;j<this.UserArr.length;j++)
            {
              if(deptype == UserArr[j][4]){
                slt2Obj.options[k].text=this.UserArr[j][1];
                slt2Obj.options[k].value=this.UserArr[j][0];
                k++;
              }
            }

          }
        }else{
          i=0;
          for(j= 0; j< this.UserArr.length; j++ )
          {
            if(slt1Obj.value == UserArr[j][2])
            {
              i++;
            }
          }
          slt2Obj.options.length = i+1;
          k=1
          slt2Obj.options[0].text="不限";
          slt2Obj.options[0].value="";
          for(j =0;j<this.UserArr.length;j++)
          {
            if(slt1Obj.value == UserArr[j][2])
            {
              slt2Obj.options[k].text=this.UserArr[j][1];
              slt2Obj.options[k].value=this.UserArr[j][0];
              k++;
            }
          }
        }
		return true;
	}
        //类别和用户
    function showdeptuser(type ) //
	{
        var slt2Obj=document.getElementById("uId");
        var deptype=document.getElementById("type").value;
        var regionid=document.getElementById("rId").value;
		i=0;
        if(regionid == ""){
          for(j= 0; j< this.UserArr.length; j++ )
          {
            if(type == UserArr[j][4])
            {
              i++;
            }
          }
        }else{
         for(j= 0; j< this.UserArr.length; j++ )
          {
            if(type == UserArr[j][4] && regionid == UserArr[j][2])
            {
              i++;
            }
          }
        }
        slt2Obj.options.length = i+1;
		k=1
        slt2Obj.options[0].text="不限";
        slt2Obj.options[0].value="";
        if(regionid == ""){
          for(j =0;j<this.UserArr.length;j++)
          {
            if(type == UserArr[j][4])
            {
              slt2Obj.options[k].text=this.UserArr[j][1];
              slt2Obj.options[k].value=this.UserArr[j][0];
              k++;
            }
          }
          }else{
            for(j =0;j<this.UserArr.length;j++)
            {
              if(type == UserArr[j][4] && regionid == UserArr[j][2])
              {
                slt2Obj.options[k].text=this.UserArr[j][1];
                slt2Obj.options[k].value=this.UserArr[j][0];
                k++;
              }
            }
          }

		return true;

	}

    function onChangeDeptUser(objId ) //
	{
		var slt1Obj=document.getElementById(objId);
        var slt2Obj=document.getElementById("uId");
        var regionid=document.getElementById("rId").value;
        var deptype=document.getElementById("type").value;
        if(slt1Obj.value == ""){
          i=0;
          if(regionid == ""){
            for(j= 0; j< this.UserArr.length; j++ )
            {
              if(deptype == UserArr[j][4]){
                i++;
              }
            }
          }else{
            for(j= 0; j< this.UserArr.length; j++ )
            {
              if(regionid == UserArr[j][2] && deptype == UserArr[j][4]){
                i++;
              }
            }

          }
          slt2Obj.options.length = i+1;
          k=1
          slt2Obj.options[0].text="不限";
          slt2Obj.options[0].value="";
          if(regionid == ""){
            for(j =0;j<this.UserArr.length;j++)
            {
            if(deptype == UserArr[j][4]){
              slt2Obj.options[k].text=this.UserArr[j][1];
              slt2Obj.options[k].value=this.UserArr[j][0];
              k++;
            }
            }
          }else{
            for(j =0;j<this.UserArr.length;j++)
            {
              if(regionid == UserArr[j][2]  && deptype == UserArr[j][4])
              {
                slt2Obj.options[k].text=this.UserArr[j][1];
                slt2Obj.options[k].value=this.UserArr[j][0];
                k++;
              }
            }
          }
        }else{
          i=0;
          for(j= 0; j< this.UserArr.length; j++ )
          {
            if(slt1Obj.value == UserArr[j][3])
            {
              i++;
            }
          }
          slt2Obj.options.length = i+1;
          k=1
          slt2Obj.options[0].text="不限";
          slt2Obj.options[0].value="";
          for(j =0;j<this.UserArr.length;j++)
          {
            if(slt1Obj.value == UserArr[j][3])
            {
              slt2Obj.options[k].text=this.UserArr[j][1];
              slt2Obj.options[k].value=this.UserArr[j][0];
              k++;
            }
          }
        }
		return true;
	}
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
  if(document.forms[0].beginDate.value!=""&&document.forms[0].endDate.value!=""){
    var d1=parseDate(document.forms[0].beginDate.value,"/");
    var d2=parseDate(document.forms[0].endDate.value,"/");
    if(compareDate(d1,d2)){
      alert("开始日期不能大于结束日期！");
      document.forms[0].endDate.value="";
    }
  }
}
</script>
<br>
<logic:present name="reginfo">
  <logic:iterate id="reginfoId" name="reginfo">
<script type="" language="javascript">
                  initRegionArray("<bean:write name="reginfoId" property="regionid"/>",
                            "<bean:write name="reginfoId" property="regionname"/>");
                  </script>
  </logic:iterate>
</logic:present>
<logic:present name="deptinfo">
  <logic:iterate id="deptinfoId" name="deptinfo">
<script type="" language="javascript">
                  initDeptArray("<bean:write name="deptinfoId" property="deptid"/>",
                            "<bean:write name="deptinfoId" property="deptname"/>",
                            "<bean:write name="deptinfoId" property="regionid"/>");
                  </script>
  </logic:iterate>
</logic:present>
<logic:present name="coninfo">
  <logic:iterate id="coninfoId" name="coninfo">
<script type="" language="javascript">
                  initConArray("<bean:write name="coninfoId" property="contractorid"/>",
                             "<bean:write name="coninfoId" property="contractorname"/>",
                             "<bean:write name="coninfoId" property="regionid"/>");
                  </script>
  </logic:iterate>
</logic:present>
<logic:present name="uinfo">
  <logic:iterate id="uinfoId" name="uinfo">
<script type="" language="javascript">
                 initUserArray("<bean:write name="uinfoId" property="userid"/>",
                            "<bean:write name="uinfoId" property="username"/>",
                            "<bean:write name="uinfoId" property="regionid"/>",
                            "<bean:write name="uinfoId" property="deptid"/>",
                            "<bean:write name="uinfoId" property="deptype"/>");
                  </script>
  </logic:iterate>
</logic:present>
<body>
<template:titile value="查询用户登录信息"/>
<html:form   styleId="form1"   method="Post" action="/onlineuseraction.do?method=queryUserOnlineTime">
  <template:formTable contentwidth="300" namewidth="200">
    <logic:notEmpty name="reginfo">
      <template:formTr name="所属区域">
        <select name="regionid" class="inputtext" style="width:180px" id="rId" onchange="onChangeDept(id),onChangeCon(id),onChangeUser(id)">
          <option value="">不限</option>
          <logic:present name="reginfo">
            <logic:iterate id="reginfoId" name="reginfo">
              <option value="<bean:write name="reginfoId" property="regionid"/>">
                <bean:write name="reginfoId" property="regionname"/>
              </option>
            </logic:iterate>
          </logic:present>
          <select>

      </template:formTr>
    </logic:notEmpty>
  <%if (userinfo.getDeptype().equals("1") | userinfo.getRegionID().substring(2, 6).equals("0000")) {  %>
    <template:formTr name="部门类别">
      <html:hidden property="deptype" value="" styleId="type"/>
    <%if (!"2".equals(userinfo.getDeptype())) {    %>
      <input type="radio" name="radiobutton" id="deptype" value="" onclick="showdep(),showdeptuser(1)">
      内部部门
    <%}    %>
      <input type="radio" name="radiobutton" id="contype" value="" onclick="showcon(),showdeptuser(2)">
      代维单位
    </template:formTr>
    <template:formTr name="部&nbsp;&nbsp;&nbsp;&nbsp;门" tagID="deptTr" style="display:none">
      <select name="deptid" class="inputtext" style="width:180px" id="dId" onchange="onChangeDeptUser(id)">
        <option value="">不限</option>
        <logic:present name="deptinfo">
          <logic:iterate id="deptinfoId" name="deptinfo">
            <option value="<bean:write name="deptinfoId" property="deptid"/>">
              <bean:write name="deptinfoId" property="deptname"/>
            </option>
          </logic:iterate>
        </logic:present>
      </select>
    </template:formTr>
    <template:formTr name="代维单位" tagID="conTr" style="display:none">
      <select name="contractorid" class="inputtext" style="width:180px" id="cId" onchange="onChangeDeptUser(id)">
        <option value="">不限</option>
        <logic:present name="coninfo">
          <logic:iterate id="coninfoId" name="coninfo">
            <option value="<bean:write name="coninfoId" property="contractorid"/>">
              <bean:write name="coninfoId" property="contractorname"/>
            </option>
          </logic:iterate>
        </logic:present>
      </select>
    </template:formTr>
  <%}  %>
    <template:formTr name="用&nbsp;&nbsp;&nbsp;&nbsp;户">
      <select name="userid" class="inputtext" style="width:180px" id="uId">
        <option value="">不限</option>
        <logic:present name="uinfo">
          <logic:iterate id="uinfoId" name="uinfo">
            <option value="<bean:write name="uinfoId" property="userid"/>">
              <bean:write name="uinfoId" property="username"/>
            </option>
          </logic:iterate>
        </logic:present>
      </select>
    </template:formTr>
    <template:formTr name="开始日期">
      <input type="text" name="beginDate" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" size="25" maxlength="45" readonly="readonly"/>
    </template:formTr>
    <template:formTr name="结束日期">
      <input type="text" name="endDate" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" size="25" maxlength="45" readonly="readonly"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">查询</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
      <td>
        <input type="button" class="button" onclick="window.history.go(-1);" value="返回">
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
</body>
<script language="javascript">
<!--
var flag=0;
document.forms[0].beginDate.onpropertychange=function(){
  vertifyDate();
}
document.forms[0].endDate.onpropertychange=function(){
  vertifyDate();
}
</script>
