<%@include file="/common/header.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Ajax upload demo</title>
<script language="javascript" src="${ctx}/js/validation/prototype.js"
	type=""></script>
<script language="javascript" src="${ctx}/js/validation/effects.js"
	type=""></script>
<script language="javascript"
	src="${ctx}/js/validation/validation_cn.js" type=""></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet"
	type="text/css">


<script type="text/javascript">
	/*<![CDATA[*/
	function upload() {
		jQuery.ajax( {
			url : '${ctx}/uploaddemo.do?method=uploadfile',
			cache : false,
			success : function(result) {
				jQuery('#newfile').html(result);
			}
		});
	}
	
	/*]]>*/
</script>
</head>
<body>
<div id="text">
<h1>Ajax upload for demo</h1>
</div>
<div><!-- apptag:upload state="add" entityId="0000001"
	entityType="ls_trouble" cssClass="uploadButton" /--> <!--  apptag:upload state="add"-->
<!--addtag:upload state="look" entityId="0000001" entityType="ls_trouble"-->
</div>
<br>
<br>
<input type="button" value="save" onclick=
	upload();
/>
<table width="80%">
	<tr>
		<td width="20%">隐患图片：</td>
		<td width="60%">
		<div><apptag:image entityId="0000001" entityType="ls_trouble" /></div>
		</td>
	</tr>
</table>

<form action="${ctx}/troubleInfoAction.do?method=saveTroubleInfo"
	id="saveTroubleInfo">
<table width="80%" border="0" align="center" cellpadding="3"
	cellspacing="0" class="tabout">
	<tr class=trcolor>
		<td class="tdulleft">是否为重大故障：</td>
		<td class="tdulright"><input type="radio" name="isGreatTrouble"
			value="1" checked="checked">是&nbsp;&nbsp; <input type="radio"
			name="isGreatTrouble" value="0">否</td>
		<td class="tdulleft">故障派发人：</td>
		<td class="tdulright"><input type="text" name="troubleSendMan"
			class="required" /><font color="red">*</font></td>
	</tr>
	<tr class=trwhite>
		<td class="tdulleft">联系人：</td>
		<td class="tdulright"><input type="text" name="connector"
			class="required" style="width: 205" />&nbsp;&nbsp;<font color="red">*</font></td>
		<td class="tdulleft">联系电话：</td>
		<td class="tdulright"><input type="text" name="connectorTel"
			style="width: 205" class="required fix-mobile-phone " />&nbsp;&nbsp;<font
			color="red">*</font></td>
	</tr>
	<tr class=trwhite>
		<td class="tdulleft">添加附件:</td>
		<td class="tdulright" colspan="3">
			<apptag:upload state="edit"  entityId="0000001"
	entityType="ls_trouble" cssClass="uploadButton" ></apptag:upload>
		</td>
	</tr>
</table>
</form>
<script type="text/javascript">
	function formCallback(result, form) {
		window.status = "valiation callback for form '" + form.id + "': result = " + result;
	}

	var valid = new Validation('saveTroubleInfo', {immediate : true, onFormValidate : formCallback});
</script>
</body>
</html>