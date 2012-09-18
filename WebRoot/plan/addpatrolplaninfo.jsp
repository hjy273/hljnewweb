<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>

<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>

<html>
	<head>
		<script type="text/javascript" language="javascript">
	function valiSub() {
    var rds = "";//��Դ�ַ�ID
    var rtype="";//��Դ����
    if(document.all.item("year").value=='')
	{
		alert("�ƻ���ݲ���Ϊ�գ�");
		return false;
	}
	if(document.all.item("templateid").value=='') {
		alert("Ѳ��ģ�岻��Ϊ�գ�");
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
		  			alert("��ʼʱ�䲻��Ϊ�գ�");
		  			return false;
				}
				if(document.all.item("enddate").value=='')
				{
					alert("����ʱ�䲻��Ϊ�գ�");
					return false;
				}
			}
		}
	}
	if(check==false){
		alert("�ƻ����Ͳ���Ϊ�գ�");
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
	alert("��Դѡ����Ϊ�գ�");
	return false;
	}
	document.PatrolinfoBean.resourceids.value = rds;
	document.PatrolinfoBean.resourcetype.value=rtype;
	PatrolinfoBean.submit();
}
//����ʱ�����ͺ���
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
//��̬���ɼƻ�����
function setPlanName(plantype) {
	var str = document.all.item("patrolgroupname").value
			+ document.all.item("year").value + "��";
	if (plantype == "1") {
	    document.all.item("plantype")[0].checked=true;
		var obj = document.getElementById("yearTr").children[0];
		for (var i = 0; i < obj.length; i++) {
			if (obj[i].selected == true) {
				str = str + obj[i].text + "Ѳ��ƻ�";
			}
		}
	} else if (plantype == "2") {
		var obj = document.getElementById("seasonTr").children[0];
		for (var i = 0; i < obj.length; i++) {
			if (obj[i].selected == true) {
				str = str + obj[i].text + "Ѳ��ƻ�";
			}
		}
	} else if (plantype == "3") {
		var obj = document.getElementById("monthTr").children[0];
		for (var i = 0; i < obj.length; i++) {
			if (obj[i].selected == true) {
				str = str + obj[i].text + "Ѳ��ƻ�";
			}
		}
	} else {
		var year=document.all.item("startdate").value.substr(0,4);
		document.all.item("year").value=year;
		str = document.all.item("patrolgroupname").value
				+ document.all.item("startdate").value + "��"
				+ document.all.item("enddate").value + "Ѳ��ƻ�";
	}
	document.all.item("planname").value = str;
}
//�ƻ����͸ı�ʱ����
function change() {
	var obj = document.getElementsByName("plantype");
	for (var i = 0; i < obj.length; i++) {
		if (obj[i].checked == true) {
			setPlanName(obj[i].value);
		}
	}
}


//ά�����������
var patrolgroupcombotree;
//��Դ�����
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
                //�ӿ�û�д�Ѳ����regionid
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
                checkModel: 'cascade',   //�����ļ�����ѡ
				applyTo : 'combotree_patrolresourcediv',
				autoScroll : true,
				margins : '0 2 0 0',
				animate:false,
				root : new Ext.tree.AsyncTreeNode({
							id : "root",
							text : "������Դ",
							iconCls : 'treeroot-icon',
							expanded : true,
							loader : resoucetreeload
						})

			});
	//����Jquery��֤
	jQuery("#addPlanInfoForm").validate();
});
</script>
		<title>���Ѳ��ƻ���Ϣ</title>
	</head>
	<body onload="setPlanName(1)">
		<template:titile value="���Ѳ��ƻ���Ϣ" />
		<html:form
			action="/TowerPatrolinfo.do?method=addPlanInfo&businesstype=${businesstype}"
			styleId="addPlanInfoForm">
			<template:formTable>
				<template:formTr name="���">
					<apptag:getYearOptions />
					<html:select id='year' property="year" onchange="change()"
						style="width:300px;" styleClass="inputtext required">
						<html:options collection="yearCollection" property="value"
							labelProperty="label"></html:options>
					</html:select>
				</template:formTr>
				<template:formTr name="�ƻ�����(�Զ�����)">
					<html:text property="planname" styleClass="inputtext required"
						style="width:300;" maxlength="25" readonly="true" />
				</template:formTr>
				<template:formTr name="�ƻ�����">
					<html:radio property="plantype" onclick="setCyc(this)" value="1">
					</html:radio> ����
					<html:radio property="plantype" onclick="setCyc(this)" value="2" /> ����
					<html:radio property="plantype" onclick="setCyc(this)" value="3" /> �¶�
					<html:radio property="plantype" onclick="setCyc(this)" value="4" /> �Զ���
				</template:formTr>
				<template:formTr name="ʱ��">
					<div name="yearTr" id="yearTr" style="display: ">
						<html:select property="startyear" style="width:300px;"
							onchange="change()">
							<html:option value="1">�ϰ���</html:option>
							<html:option value="2">�°���</html:option>
						</html:select>
					</div>
					<div name="seasonTr" id="seasonTr" style="display: none">
						<html:select property="startseason" style="width:300px;"
							onchange="change()">
							<html:option value="1">��һ����</html:option>
							<html:option value="2">�ڶ�����</html:option>
							<html:option value="3">��������</html:option>
							<html:option value="4">���ļ���</html:option>
						</html:select>
					</div>
					<div name="monthTr" id="monthTr" style="display: none">
						<html:select property="startmonth" style="width:300px;"
							onchange="change()">
							<html:option value="1">1�·�</html:option>
							<html:option value="2">2�·�</html:option>
							<html:option value="3">3�·�</html:option>
							<html:option value="4">4�·�</html:option>
							<html:option value="5">5�·�</html:option>
							<html:option value="6">6�·�</html:option>
							<html:option value="7">7�·�</html:option>
							<html:option value="8">8�·�</html:option>
							<html:option value="9">9�·�</html:option>
							<html:option value="10">10�·�</html:option>
							<html:option value="11">11�·�</html:option>
							<html:option value="12">12�·�</html:option>

						</html:select>
					</div>
					<div name="timeTr" id="timeTr" style="display: none">
						��ʼ���ڣ�
						<html:text styleId="startdate" property="startdate"
							readonly="true" styleClass="inputtext Wdate" style="width: 120px"
							onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'enddate\')||\'2020-10-01\'}'})"
							onchange="change()" />

						�������ڣ�
						<html:text styleId="enddate" property="enddate" readonly="true"
							styleClass="inputtext Wdate" style="width: 120px"
							onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startdate\')}',maxDate:'2020-10-01'})"
							onchange="change()" />

					</div>
				</template:formTr>
				<template:formTr name="Ѳ����">
					<div id="combotree_patrolgroupdiv" style="width: 300px;"></div>
					<html:hidden property="patrolgroupname" />
					<html:hidden property="regionid" />
				</template:formTr>
				<!--  
				<template:formTr name="Ѳ�����">
					<html:text property="patrolnum" styleClass="inputtext"
						style="width:180;" maxlength="25" value="1" />
				</template:formTr>
				-->
				<template:formTr name="Ѳ����Դ">
					<div id="combotree_patrolresourcediv" style="width: 300px;"></div>
					<html:hidden property="resourceids" />
					<html:hidden property="resourcetype" />
					<html:hidden property="businesstype" value="${businesstype}" />

				</template:formTr>
				<template:formTr name="Ѳ��ģ��">
					<html:select property="templateid">
						<!-- 		<option value=""></option> -->
						<html:options collection="template" labelProperty="TEMPLATE_NAME"
							property="ID" />
					</html:select>
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:button property="action" styleClass="button"
							onclick="valiSub()">�ύ</html:button>
					</td>
					<td>
						<html:reset property="action" styleClass="button">ȡ��</html:reset>
					</td>
					<td>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>


