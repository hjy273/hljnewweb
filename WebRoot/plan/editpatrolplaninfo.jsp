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
	document.getElementById('resourceids').value = rds;
	document.getElementById('resourcetype').value=rtype;
	return true;
};
// 设置时间类型函数
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
// 动态生成计划名称
function setPlanName(plantype) {
	var str = document.all.item("patrolgroupname").value
			+ document.all.item("year").value + "年";
	if (plantype == "1") {
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
};
// 计划类型改变时触发
function change() {
	var obj = document.getElementsByName("plantype");
	for (var i = 0; i < obj.length; i++) {
		if (obj[i].checked == true) {
			setPlanName(obj[i].value);
		}
	}
};
// 维护组下拉组件
var patrolgroupcombotree;
// 资源树组件
var patrolresoucetree;
var resoucetreeload;
Ext.onReady(function() {
	patrolgroupcombotree = new TreeComboField({
		width : 300,
		height : 100,
		allowBlank : false,
		leafOnly : true,
		renderTo : 'combotree_patrolgroupdiv',
		name : "patrolgroupname",
		hiddenName : "patrolgroupid",
		displayField : 'text',
		forceSelection : true,
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			animate:false,
			root : new Ext.tree.AsyncTreeNode({
				id : 'root',
				loader : new Ext.tree.TreeLoader({
							dataUrl : '${ctx}/common/externalresources_getPatrolmanJson.jspx'
						})

			})
		})
	});
	patrolgroupcombotree.setComboValue("${planinfo.patrolgroupid}",
			"${planinfo.patrolgroupname}");
	patrolgroupcombotree.on('select', function(combo, record) {
		document.PatrolinfoBean.regionid.value = record.attributes.regionid;
			document.all.item("patrolgroupname").value = record.attributes.text;
		resoucetreeload.baseParams = {
			businesstype : "${planinfo.businesstype}",
			patrolgroup_id : combo.getValue(),
			resources_id : "${planinfo.resourceids}"
		};
		patrolresoucetree.getRootNode().reload();
		change();
	});
	resoucetreeload = new Ext.tree.TreeLoader({
				dataUrl : '${ctx}/TowerPatrolinfo.do?method=getPatrolResource',
				baseAttrs: { uiProvider: Ext.ux.TreeCheckNodeUI } 
				
			});
	resoucetreeload.baseParams = {
		businesstype : "${planinfo.businesstype}",
		patrolgroup_id : "${planinfo.patrolgroupid}",
		resources_id : "${planinfo.resourceids}"
	};
	patrolresoucetree = new Ext.tree.TreePanel({
				id : 'check_id',
				width : 300,
				applyTo : 'combotree_patrolresourcediv',
				checkModel: 'cascade',   //对树的级联多选
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
});
function addGoBack() {
	location = "${ctx}/TowerPatrolinfo.do?method=showCertificate";
}
function setDisplayTime(v){
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
}
</script>
		<title>修改巡检计划信息</title>
	</head>
	<body>
		<logic:present name="planinfo">
			<template:titile value="修改巡检计划信息" />
			<html:form
				action="/TowerPatrolinfo.do?method=upPlanInfo&businesstype=${planinfo.businesstype}"
				onsubmit="return valiSub()" method="Post">
				<template:formTable>
					<template:formTr name="年份">
						<apptag:getYearOptions />
						<html:select property="year" name="planinfo"
							value="${planinfo.year}" onchange="change()">
							<html:options collection="yearCollection" property="value"
								labelProperty="label"></html:options>
						</html:select>
					</template:formTr>
					<template:formTr name="计划名称(自动生成)">
						<html:text property="planname" name="planinfo"
							styleClass="inputtext" style="width:300;" maxlength="25"
							readonly="true" />
					</template:formTr>
					<template:formTr name="计划类型">
						<html:radio property="plantype" name="planinfo"
							onclick="setCyc(this)" value="1" /> 半年
						<html:radio property="plantype" name="planinfo"
							onclick="setCyc(this)" value="2" /> 季度
						<html:radio property="plantype" name="planinfo"
							onclick="setCyc(this)" value="3" /> 月份
						<html:radio property="plantype" name="planinfo"
							onclick="setCyc(this)" value="4" /> 自定义
					</template:formTr>
					<template:formTr name="时间">
						<div name="yearTr" id="yearTr" style="display: ">
							<html:select property="startyear" name="planinfo"
								onchange="change()" style="width:300;">
								<html:option value="1">上半年</html:option>
								<html:option value="2">下半年</html:option>
							</html:select>
						</div>
						<div name="seasonTr" id="seasonTr" style="display: ">
							<html:select property="startseason" name="planinfo"
								onchange="change()" style="width:300;">
								<html:option value="1">第一季度</html:option>
								<html:option value="2">第二季度</html:option>
								<html:option value="3">第三季度</html:option>
								<html:option value="4">第四季度</html:option>
							</html:select>
						</div>
						<div name="monthTr" id="monthTr" style="display: ">
							<html:select property="startmonth" name="planinfo"
								onchange="change()" style="width:300;">
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
						<div name="timeTr" id="timeTr" style="display: ">
							开始日期：
							<html:text property="startdate" readonly="true"
								value="${planinfo.startdate}" styleClass="inputtext Wdate"
								style="width: 210"
								onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'enddate\')||\'2020-10-01\'}'})"
								onchange="change()" />
							结束日期：
							<html:text property="enddate" readonly="true"
								value="${planinfo.enddate}" styleClass="inputtext Wdate"
								style="width: 210"
								onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startdate\')}',maxDate:'2020-10-01'})"
								onchange="change()" />

						</div>
					</template:formTr>
					<template:formTr name="巡检组">
						<div id="combotree_patrolgroupdiv" style="width: 300;"></div>
						<html:hidden property="patrolgroupname" name="planinfo" />
						<html:hidden property="regionid" name="planinfo" />
					</template:formTr>
					<template:formTr name="巡检资源">
						<div id="combotree_patrolresourcediv" style="width: 300;"></div>
						<html:hidden property="resourceids" styleId="resourceids"
							name="planinfo" value="${planinfo.resourceids}" />
						<html:hidden property="resourcetype" styleId="resourcetype"
							value="${planinfo.resourcetype}" name="planinfo" />
						<html:hidden property="id" name="planinfo" />
					</template:formTr>
					<template:formTr name="巡检模板">
						<apptag:getYearOptions />
						<html:select property="templateid" name="templateid"
							value="${planinfo.templateid}" onchange="change()">
							<html:options collection="template" labelProperty="TEMPLATE_NAME"
								property="ID" />
						</html:select>
					</template:formTr>
					<template:formSubmit>
						<td>
							<html:submit styleClass="button">提交</html:submit>
						</td>
						<td>
							<html:reset property="action" styleClass="button">取消</html:reset>
						</td>
					</template:formSubmit>
				</template:formTable>
			</html:form>
		</logic:present>
	</body>
	<script type="text/javascript">
	setDisplayTime('${planinfo.plantype}');
</script>
</html>


