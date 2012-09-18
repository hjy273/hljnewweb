<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>

<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>

<html>
	<head>
		<script type="text/javascript" language="javascript">
	function valiSub() {
    var rds = "";//资源字符ID
    var rtype="";//资源类型
    if(document.all.item("year").value=='')
	{
		alert("计划年份不能为空！");
		return false;
	}
	if(document.all.item("templateid").value=='') {
		alert("巡检模板不能为空！");
		return false;
	}
	var obj = document.getElementsByName("plantype");
	var check=false;
	for (var i = 0; i < obj.length; i++) {
		if(obj[i].checked)
		{
			check=true
			if (obj[i].value == "4") {
				if(document.all.item("startdate").value=='')
				{
		  			alert("开始时间不能为空！");
		  			return false;
				}
				if(document.all.item("enddate").value=='')
				{
					alert("结束时间不能为空！");
					return false;
				}
			}
		}
	}
	if(check==false){
		alert("计划类型不能为空！");
		return false;
	}
	var checkedNodes = patrolresoucetree.getChecked();
	for (var i = 0; i < checkedNodes.length; i++) {
		if (checkedNodes[i].isLeaf()) {
			rds +=","+checkedNodes[i].attributes.rsid;
			rtype+=","+checkedNodes[i].attributes.rstype;
		}
		//rtype=checkedNodes[i].parentNode.attributes.id;
	}
	if (rds != "") {
		rds = rds.substring(1, rds.length);
		rtype=rtype.substring(1, rtype.length);
	}
	if(rds.length==0)
	{
	alert("资源选择不能为空！");
	return false;
	}
	document.PatrolinfoBean.resourceids.value = rds;
	document.PatrolinfoBean.resourcetype.value=rtype;
	PatrolinfoBean.submit();
}
//设置时间类型函数
function setCyc(objN) {
	var v = objN.value;
	if (v == "1") {
		yearTr.style.display = "";
		seasonTr.style.display = "none";
		monthTr.style.display = "none";
		timeTr.style.display = "none";
	} else if (v == "2") {
		yearTr.style.display = "none";
		seasonTr.style.display = "";
		monthTr.style.display = "none";
		timeTr.style.display = "none";
	} else if (v == "3") {
		yearTr.style.display = "none";
		seasonTr.style.display = "none";
		monthTr.style.display = "";
		timeTr.style.display = "none";
	} else {
		yearTr.style.display = "none";
		seasonTr.style.display = "none";
		monthTr.style.display = "none";
		timeTr.style.display = "";

	}
	setPlanName(v);
};
//动态生成计划名称
function setPlanName(plantype) {
	var str = document.all.item("patrolgroupname").value
			+ document.all.item("year").value + "年";
	if (plantype == "1") {
	    document.all.item("plantype")[0].checked=true;
		var obj = document.getElementById("yearTr").children[0];
		for (var i = 0; i < obj.length; i++) {
			if (obj[i].selected == true) {
				str = str + obj[i].text + "巡检计划";
			}
		}
	} else if (plantype == "2") {
		var obj = document.getElementById("seasonTr").children[0];
		for (var i = 0; i < obj.length; i++) {
			if (obj[i].selected == true) {
				str = str + obj[i].text + "巡检计划";
			}
		}
	} else if (plantype == "3") {
		var obj = document.getElementById("monthTr").children[0];
		for (var i = 0; i < obj.length; i++) {
			if (obj[i].selected == true) {
				str = str + obj[i].text + "巡检计划";
			}
		}
	} else {
		var year=document.all.item("startdate").value.substr(0,4);
		document.all.item("year").value=year;
		str = document.all.item("patrolgroupname").value
				+ document.all.item("startdate").value + "至"
				+ document.all.item("enddate").value + "巡检计划";
	}
	document.all.item("planname").value = str;
}
//计划类型改变时触发
function change() {
	var obj = document.getElementsByName("plantype");
	for (var i = 0; i < obj.length; i++) {
		if (obj[i].checked == true) {
			setPlanName(obj[i].value);
		}
	}
}


//维护组下拉组件
var patrolgroupcombotree;
//资源树组件
var patrolresoucetree;
Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = "${ctx}/js/extjs/resources/images/default/s.gif";

	patrolgroupcombotree = new TreeComboField({
		width : 300,
		maxHeight : 100,
		leafOnly : true,
		renderTo : 'combotree_patrolgroupdiv',
		hiddenName : "patrolgroupid",
		displayField : 'text',
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			rootVisible : false,
			height:100,
			autoScroll : true,
			animate:false,
			root : new Ext.tree.AsyncTreeNode({
				id : 'root',
				loader : new Ext.tree.TreeLoader({
							dataUrl : '${ctx}/common/externalresources_getPatrolmanJson.jspx'
						})

			})
		})
	});
	patrolgroupcombotree.on('select', function(combo, record) {
                //接口没有传巡检组regionid
				//document.PatrolinfoBean.regionid.value = record.attributes.regionid;
				document.all.item("patrolgroupname").value = record.attributes.text;
				resoucetreeload.baseParams = {
					businesstype : '${businesstype}',
					patrolgroup_id : combo.getValue()
				};
				patrolresoucetree.getRootNode().reload();
				change();
			});
	resoucetreeload = new Ext.tree.TreeLoader({
				dataUrl : '${ctx}/TowerPatrolinfo.do?method=getPatrolResource',
				baseAttrs: { uiProvider: Ext.ux.TreeCheckNodeUI } 
			});
	patrolresoucetree = new Ext.tree.TreePanel({
				id : 'check_id',
                checkModel: 'cascade',   //对树的级联多选
				applyTo : 'combotree_patrolresourcediv',
				autoScroll : true,
				margins : '0 2 0 0',
				animate:false,
				root : new Ext.tree.AsyncTreeNode({
							id : "root",
							text : "所有资源",
							iconCls : 'treeroot-icon',
							expanded : true,
							loader : resoucetreeload
						})

			});
	//启用Jquery验证
	jQuery("#addPlanInfoForm").validate();
});
</script>
		<title>添加巡检计划信息</title>
	</head>
	<body onload="setPlanName(1)">
		<template:titile value="添加巡检计划信息" />
		<html:form
			action="/TowerPatrolinfo.do?method=addPlanInfo&businesstype=${businesstype}"
			styleId="addPlanInfoForm">
			<template:formTable>
				<template:formTr name="年份">
					<apptag:getYearOptions />
					<html:select id='year' property="year" onchange="change()"
						style="width:300px;" styleClass="inputtext required">
						<html:options collection="yearCollection" property="value"
							labelProperty="label"></html:options>
					</html:select>
				</template:formTr>
				<template:formTr name="计划名称(自动生成)">
					<html:text property="planname" styleClass="inputtext required"
						style="width:300;" maxlength="25" readonly="true" />
				</template:formTr>
				<template:formTr name="计划类型">
					<html:radio property="plantype" onclick="setCyc(this)" value="1">
					</html:radio> 半年
					<html:radio property="plantype" onclick="setCyc(this)" value="2" /> 季度
					<html:radio property="plantype" onclick="setCyc(this)" value="3" /> 月度
					<html:radio property="plantype" onclick="setCyc(this)" value="4" /> 自定义
				</template:formTr>
				<template:formTr name="时间">
					<div name="yearTr" id="yearTr" style="display: ">
						<html:select property="startyear" style="width:300px;"
							onchange="change()">
							<html:option value="1">上半年</html:option>
							<html:option value="2">下半年</html:option>
						</html:select>
					</div>
					<div name="seasonTr" id="seasonTr" style="display: none">
						<html:select property="startseason" style="width:300px;"
							onchange="change()">
							<html:option value="1">第一季度</html:option>
							<html:option value="2">第二季度</html:option>
							<html:option value="3">第三季度</html:option>
							<html:option value="4">第四季度</html:option>
						</html:select>
					</div>
					<div name="monthTr" id="monthTr" style="display: none">
						<html:select property="startmonth" style="width:300px;"
							onchange="change()">
							<html:option value="1">1月份</html:option>
							<html:option value="2">2月份</html:option>
							<html:option value="3">3月份</html:option>
							<html:option value="4">4月份</html:option>
							<html:option value="5">5月份</html:option>
							<html:option value="6">6月份</html:option>
							<html:option value="7">7月份</html:option>
							<html:option value="8">8月份</html:option>
							<html:option value="9">9月份</html:option>
							<html:option value="10">10月份</html:option>
							<html:option value="11">11月份</html:option>
							<html:option value="12">12月份</html:option>

						</html:select>
					</div>
					<div name="timeTr" id="timeTr" style="display: none">
						开始日期：
						<html:text styleId="startdate" property="startdate"
							readonly="true" styleClass="inputtext Wdate" style="width: 120px"
							onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'enddate\')||\'2020-10-01\'}'})"
							onchange="change()" />

						结束日期：
						<html:text styleId="enddate" property="enddate" readonly="true"
							styleClass="inputtext Wdate" style="width: 120px"
							onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startdate\')}',maxDate:'2020-10-01'})"
							onchange="change()" />

					</div>
				</template:formTr>
				<template:formTr name="巡检组">
					<div id="combotree_patrolgroupdiv" style="width: 300px;"></div>
					<html:hidden property="patrolgroupname" />
					<html:hidden property="regionid" />
				</template:formTr>
				<!--  
				<template:formTr name="巡检次数">
					<html:text property="patrolnum" styleClass="inputtext"
						style="width:180;" maxlength="25" value="1" />
				</template:formTr>
				-->
				<template:formTr name="巡检资源">
					<div id="combotree_patrolresourcediv" style="width: 300px;"></div>
					<html:hidden property="resourceids" />
					<html:hidden property="resourcetype" />
					<html:hidden property="businesstype" value="${businesstype}" />

				</template:formTr>
				<template:formTr name="巡检模板">
					<html:select property="templateid">
						<!-- 		<option value=""></option> -->
						<html:options collection="template" labelProperty="TEMPLATE_NAME"
							property="ID" />
					</html:select>
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:button property="action" styleClass="button"
							onclick="valiSub()">提交</html:button>
					</td>
					<td>
						<html:reset property="action" styleClass="button">取消</html:reset>
					</td>
					<td>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>


