<%@include file="/common/header.jsp"%>

<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo"%>

<style type="text/css">
.ooib {
	border-width: 1px;
	border-style: none none none none;
	border-color: transparent;
	background-color: transparent;
}

.ooih td {
	border-width: 1px;
	padding: 0 10;
	border-style: none none solid solid;
	border-color: transparent transparent transparent black
}

<!--
bottom-->.ooihbefore td {
	border-width: 1px;
	padding: 0 10;
	border-style: none none none none;
	border-color: transparent transparent transparent black
}

<!--
bottom-->.ooihj {
	color: #CC3333;
	background-color: #E4E5EE;
	border-style: none;
	border-color: transparent;
	cursor: hand
}

.ooihs {
	color: #247DAB;
	background-color: #ADD8E6;
	border-style: none;
	border-color: #6600CC #6600CC transparent;
	cursor: hand
}

.ooiother {
	border-width: 1px;
	border-style: solid solid none solid;
	border-color: transparent;
	background-color: transparent;
}
</style>
<script language="javascript" type="text/javascript">
    var activepanel = 0;
    function showpanel(){
         var strshowpanel = <%=session.getAttribute("isshowpanel")%>;
         if (strshowpanel == 0){ 
           document.getElementById("paneltable").style.display="none";
         }else{
	       document.getElementById("paneltable").style.display="";
         }
         if(document.all.conId.selectedIndex!=-1){ 
         document.all.conid.value = document.all.conId.options[document.all.conId.selectedIndex].text;
         } 
       }

    function ghbq(td)
	{
	  var tr = td.parentNode.cells;
	  var ob = document.getElementById("obody").rows;
	  for(var ii=0; ii<tr.length-1; ii++)
	  {
	    tr[ii].className = (td.cellIndex==ii)?"ooihj":"ooihs";
	    ob[ii].style.display = (td.cellIndex==ii)?"":"none";
	  }
	  if (ob[1].style.display ==""){
	    activepanel = 1;
	    document.getElementById("graphicFrame").src ="${ctx}/ShowPlanProgressChart";
	  }else{
	    activepanel = 0;
	    document.getElementById("graphicFrame").style.display="";
	  }
	}
    
    function refreshpanel(){
      document.getElementById("tdtext").parentNode.cells[0].className="ooihj";
      document.getElementById("tdgraphic").parentNode.cells[1].className="ooihs";
      ghbq(document.getElementById("tdtext"));
      if(document.all.conId.selectedIndex!=-1){
      document.all.conid.value = document.all.conId.options[document.all.conId.selectedIndex].text;
      }	
    }
    function onChangeCon(){	
      document.all.conid.value = document.all.conId.options[document.all.conId.selectedIndex].text;
      showpanel();
      document.getElementById("graphicFrame").src="${ctx}/common/blank.html"; 
      document.getElementById("dataFormFrame").src="${ctx}/common/blank.html"; 
    }
    function onChangeConAll(){
    showpanel();
    k=0;
      for( i=0;i<document.all.workID.options.length;i++){

             if(document.all.workID.options[i].text.substring(0,6)== document.all.rId.value){
                 k++;
                document.all.conId.options.length=k;
                document.all.conId.options[k-1].value=document.all.workID.options[i].value;
                document.all.conId.options[k-1].text=document.all.workID.options[i].text.substring(8,document.all.workID.options.length);
             }

      }
      if(k==0)
       document.all.conId.options.length=0;
      if(document.all.conId.selectedIndex!=-1){
      document.all.conid.value = document.all.conId.options[document.all.conId.selectedIndex].text;
      }
 }
</script>
<%
    UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
    if (userinfo.getType().equals("11")) {
%>
<body onload="onChangeConAll()">
	<%
	    } else {
	%>

<body onload="showpanel()">
	<%
	    }
	%>
	<span id="mainSpan" style="display: "> <template:titile
			value="计划执行进度查询" /> <html:form
			action="/PlanProgressAction?method=showProgressText"
			target="dataFormFrame">
			<input id="conid" name="conname" type="hidden" />
			<template:formTable contentwidth="200" namewidth="300">
				<logic:equal value="11" name="LOGIN_USER" property="type">
					<logic:notEmpty name="reginfo">
						<template:formTr name="所属区域" isOdd="false">
							<select name="regionId" class="inputtext" style="width: 180px"
								id="rId" onclick="onChangeConAll()">
								<logic:present name="reginfo">
									<logic:iterate id="reginfoId" name="reginfo">
										<option
											value="<bean:write name="reginfoId" property="regionid"/>">
											<bean:write name="reginfoId" property="regionname" />
										</option>
									</logic:iterate>
								</logic:present>
							</select>
						</template:formTr>
						<logic:present name="coninfo">
							<select name="workID" style="display: none">
								<logic:iterate id="coninfoId" name="coninfo">
									<option
										value="<bean:write name="coninfoId" property="contractorid"/>">
										<bean:write name="coninfoId" property="regionid" />
										--
										<bean:write name="coninfoId" property="contractorname" />
									</option>
								</logic:iterate>
							</select>
						</logic:present>
					</logic:notEmpty>
				</logic:equal>
				<template:formTr name="市代维公司" isOdd="false">
					<select name="conID" class="inputtext" style="width: 180px"
						id="conId" onchange="onChangeCon()">
						<logic:present name="coninfo">
							<logic:iterate id="coninfoId" name="coninfo">
								<option
									value="<bean:write name="coninfoId" property="contractorid"/>">
									<bean:write name="coninfoId" property="contractorname" />
								</option>
							</logic:iterate>
						</logic:present>
					</select>
				</template:formTr>

				<template:formSubmit>
					<td>
						<html:submit styleClass="button" onclick="refreshpanel();">查询</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">重置</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form> </span>
	<table class="ooib" id="obody" border="0" cellspacing="0"
		cellpadding="0" width="100%" height="340px">
		<tr>
			<td style="width:100%">
				<div id="iframeDiv" style="width:100%;height: 340px">
					<iframe id=dataFormFrame border=0 marginWidth=0 marginHeight=0
						src="${ctx}/common/blank.html" frameBorder=0 width="100%"
						scrolling="auto" height="320px">
					</iframe>
				</div>
			</td>
		</tr>
		<tr style="display: none">
			<td style="width:100%">
				<div style="width:100%;height: 340px">
					<img src="${ctx}/images/1px.gif" alt="">
					<br>
					<iframe id=graphicFrame border=0 marginWidth=0 marginHeight=0
						src="${ctx}/common/blank.html" frameBorder=0 width="100%"
						scrolling=auto height="320px">
					</iframe>
				</div>
			</td>
		</tr>
	</table>
	<table id="paneltable" class="ooih" border="0" style="display: none"
		cellspacing="0" cellpadding="0" width="100" height="19">
		<tr>
			<td id="tdtext" class="ooihj" nowrap onclick="ghbq(this)">
				文本方式
			</td>
			<td id="tdgraphic" class="ooihs" nowrap onclick="ghbq(this)">
				图形方式
			</td>
			<td width="100%">
				&nbsp;
			</td>
		</tr>
	</table>