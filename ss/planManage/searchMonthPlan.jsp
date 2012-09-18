<%@ include file="/common/header.jsp"%>
<script language="javascript" type="text/javascript">
 function addGoBack(){
	try{
       	location.href = "${ctx}/YearMonthPlanAction.do?method=queryYMPlan&fID=2&year=";
        return true;
    } catch(e){
       	alert(e);
    }
}function conShow(){

           //if(document.all.reId.options[document.all.reId.selectedIndex].value.substring(2,6)=='0000'){
          // 		document.all.wID.options.length=1;
          // 		document.getElementById("wID").options[0].value='';
          //      document.getElementById("wID").options[0].text='请选择区域';
			//	return true;
          //}
          k=0;
          for( i=0;i<document.all.dId.options.length;i++){
                if(document.all.dId.options[i].text.substring(0,6)== document.all.reId.value){
                k++;
                document.getElementById("wID").disabled="";
                if(k==1){
                	document.all.wID.options.length=k;
                	document.all.wID.options[k-1].value='';
                	document.all.wID.options[k-1].text='不限';
                	k++;
                }
                document.all.wID.options.length=k;
                document.all.wID.options[k-1].value=document.all.dId.options[i].value;
                document.all.wID.options[k-1].text=document.all.dId.options[i].text.substring(8,document.all.dId.options[i].length);
             }
        }
        if(k==0){
        	document.all.wID.options.length=1;
            document.all.wID.options[0].value='';
            document.all.wID.options[0].text='无代维单位';
        }
    }

    function regionChange(){
        conShow();
    }
function setDis(){
    	regTr.style.display = "none";
    }
	
</script>

<logic:equal value="12" name="LOGIN_USER" property="type">
	<body onload="conShow();setDis()">
</logic:equal>
<logic:equal value="11" name="LOGIN_USER" property="type">
	<body onload="conShow();">
</logic:equal>
<logic:equal value="21" name="LOGIN_USER" property="type">
	<body onload="conShow();">
</logic:equal>
<logic:equal value="22" name="LOGIN_USER" property="type">
	<body>
</logic:equal>

<br>
<br>
<template:titile value="检索月巡检计划" />

<html:form
	action="/YearMonthPlanAction?method=queryYMPlan&flag1=view&fID=2">
	<template:formTable namewidth="200" contentwidth="200">
		<input type="hidden" name="isQuery" value="1" />
		<logic:notEqual value="22" name="LOGIN_USER" property="type">
			<input type="hidden" name="flag" value="view" />
			<template:formTr name="所属区域" isOdd="false" tagID="regTr"
				style="display:">
				<logic:present name="lRegion">
					<select name="regionid" class="selecttext" id="reId"
						style="width: 200" onchange="regionChange()">
						<logic:iterate id="lRegionID" name="lRegion">
							<option
								value="<bean:write name="lRegionID" property="regionid"/>">
								<bean:write name="lRegionID" property="regionname" />
							</option>
						</logic:iterate>
					</select>
				</logic:present>
			</template:formTr>
			<template:formTr name="代维单位" isOdd="false">
				<select name="workID" class="selecttext" style="width: 200" id="wID">
					<option>
					</option>
				</select>
				<logic:present name="lContractor">
					<select name="dId" style="display: none">
						<logic:iterate id="conID" name="lContractor">
							<option
								value="<bean:write name="conID" property="contractorid"/>">
								<bean:write name="conID" property="regionid" />
								--
								<bean:write name="conID" property="contractorname" />
							</option>
						</logic:iterate>
					</select>
				</logic:present>
			</template:formTr>
		</logic:notEqual>
		<template:formTr name="计划年度" isOdd="false">
			<apptag:getYearOptions />
			<html:select property="year" styleClass="inputtext" style="width:200">
				<html:option value="">不限</html:option>
				<html:options collection="yearCollection" property="value"
					labelProperty="label" />
			</html:select>
		</template:formTr>
		<template:formTr name="计划月份">
			<html:select property="month" styleClass="inputtext"
				style="width:200">
				<html:option value="">不限</html:option>
				<html:option value="01">1 月</html:option>
				<html:option value="02">2 月</html:option>
				<html:option value="03">3 月</html:option>
				<html:option value="04">4 月</html:option>
				<html:option value="05">5 月</html:option>
				<html:option value="06">6 月</html:option>
				<html:option value="07">7 月</html:option>
				<html:option value="08">8 月</html:option>
				<html:option value="09">9 月</html:option>
				<html:option value="10">10 月</html:option>
				<html:option value="11">11 月</html:option>
				<html:option value="12">12 月</html:option>
			</html:select>
		</template:formTr>
		<!--
    <template:formTr name="代维单位内部审批情况" isOdd="false" >
      <html:select property="ifinnercheck" styleClass="inputtext" style="width:200">
		<html:option value="">不限</html:option>
        <html:option value="1">通过审批</html:option>
        <html:option value="2">待审批</html:option>
        <html:option value="3">未通过审批</html:option>
      </html:select>
    </template:formTr>
-->
		<template:formTr name="移动审批">
			<select name="status" class="inputtext" style="width: 200">
				<option value="">
					不限
				</option>
				<option value="1">
					通过审批
				</option>
				<option value="0">
					待审批
				</option>
				<option value="-1">
					未通过审批
				</option>
				<select>
		</template:formTr>

		<template:formSubmit>
			<td>
				<html:submit property="action" styleClass="button">检索</html:submit>
			</td>
			<td>
				<input name="Button2" type="reset" class="button" value="取消">
			</td>
			<td>
				<html:button property="action" styleClass="button"
					onclick="addGoBack()">返回</html:button>
			</td>
		</template:formSubmit>

	</template:formTable>
</html:form>
