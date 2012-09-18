<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>

<jsp:useBean id="selectForTag"
	class="com.cabletech.commons.tags.SelectForTag" scope="request" />
<link href="${ctx}/js/extjs/resources/css/ext-all.css" rel="stylesheet"
	type="text/css">
<script src="${ctx}/js/extjs/adapter/ext/ext-base.js"
	type="text/javascript"></script>
<script src="${ctx}/js/extjs/ext-all.js" type="text/javascript"></script>
<html>
	<head>
		<script type="text/javascript" language="javascript">
var tree;
function valiSub() {
	if (personcertificateBean.certificatecode.value.trim() == ""
			|| personcertificateBean.certificatecode.value.trim() == null) {
		alert("证书编号不能为空！");
		personcertificateBean.certificatecode.focus();
		return false;
	}
	if (personcertificateBean.certificatename.value.trim() == ""
			|| personcertificateBean.certificatename.value.trim() == null) {
		alert("证书名称不能为空！");
		personcertificateBean.certificatename.focus();
		return false;
	}
	if (personcertificateBean.licenceissuingauthority.value.trim() == ""
			|| personcertificateBean.licenceissuingauthority.value.trim() == null) {
		alert("发证机构不能为空！");
		personcertificateBean.licenceissuingauthority.focus();
		return false;
	}
	if (personcertificateBean.validperiodstr.value.trim() == ""
			|| personcertificateBean.validperiodstr.value.trim() == null) {
		alert("有效期不能为空！");
		personcertificateBean.validperiodstr.focus();
		return false;
	}
	var mds = "";
	var checkedNodes = tree.getChecked();
	for (var i = 0; i < checkedNodes.length; i++) {
		if (checkedNodes[i].isLeaf()) {
			mds = checkedNodes[i].id;
		}
	}
	if (mds.length == 0) {
		alert("人员选择不能为空！");
		return false;
	}
	document.personcertificateBean.objectid.value = mds;
	personcertificateBean.submit();
}
Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = "${ctx}/js/extjs/resources/images/default/s.gif";
	tree = new Ext.tree.TreePanel({
		id : 'check_id',
		width : 180,
		applyTo : 'tree-div',
		autoScroll : true,
		margins : '0 2 0 0',
		root : new Ext.tree.AsyncTreeNode({
					id : "root",
					text : "所有人员",
					iconCls : 'treeroot-icon',
					expanded : true,
					loader : new Ext.tree.TreeLoader({
								dataUrl : '${ctx}/ConPersonAction.do?method=getPerson'
							})
				}),
		listeners : {
			'checkchange' : function(node, bool) {
				if (!bool) {
					return;
				}
				var startNode = this.getRootNode();
				var r = [];
				var f = function() {
					r.push(this);
				};
				startNode.cascade(f);
				var nodes = r;
				if (nodes && nodes.length > 0) {
					for (var i = 0, len = nodes.length; i < len; i++) {
						if (nodes[i].id != node.id) {
							if (nodes[i].getUI().checkbox) {
								nodes[i].getUI().checkbox.checked = false;
							}
						}
					}
				}
			}
		}
	});
});
    </script>

		<title>添加人员证书信息</title>
	</head>
	<body>
		<template:titile value="添加证书信息" />
		<html:form action="/PersonCertificateAction?method=addCertificate"
			styleId="addtoosbaseFormId">
			<template:formTable namewidth="150" contentwidth="300">
				<template:formTr name="证书编号">
					<html:text property="certificatecode" styleClass="inputtext"
						style="width:180;" maxlength="20" />
					<font color="#FF0000">*</font>
				</template:formTr>
				<template:formTr name="证书名称">
					<html:text property="certificatename" styleClass="inputtext"
						style="width:180;" maxlength="25" />
					<font color="#FF0000">*</font>
				</template:formTr>
				<template:formTr name="发证机构">
					<html:text property="licenceissuingauthority"
						styleClass="inputtext" style="width:180;" maxlength="25" />
					<font color="#FF0000">*</font>
				</template:formTr>
				<template:formTr name="证书类型">
					<apptag:quickLoadList name="certificatetype" listName="RYZS"
						type="select" isRegion="false" />
				</template:formTr>
				<template:formTr name="发证日期">
					<html:text property="issauthoritydatestr" styleClass="inputtext"
						style="width:180;" maxlength="25"
						onfocus="WdatePicker({el:$dp.$('issauthoritydatestr')})" />
					<img onclick="WdatePicker({el:$dp.$('issauthoritydatestr')})"
						src="${ctx}/js/WdatePicker/skin/datePicker.gif" width="16"
						height="22" align="absmiddle">
				</template:formTr>
				<template:formTr name="选择人员">
					<div id="tree-div" style="height: 300; width: 100%"></div>
					<html:hidden property="objectid" />
					<html:hidden property="objecttype" value="CONTRACTORPERSON" />
					<font color="#FF0000">*</font>
				</template:formTr>
				<template:formTr name="有效期">
					<html:text property="validperiodstr" styleClass="inputtext"
						style="width:180;" maxlength="25" readonly="true"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
					<font color="#FF0000">*</font>
				</template:formTr>
				<template:formTr name="附件">
					<apptag:upload state="add"></apptag:upload>
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


