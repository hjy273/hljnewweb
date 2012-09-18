<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>增加修缮类别</title>
		<script type="text/javascript">
	function checkAddInfo(value) {
		var typeName = document.getElementById('typeName');
		var addForm = document.forms[0];
		addForm.continue_add_type.value = value;
		if (typeName.value.length == 0) {
			alert("请填写类别名称!");
			typeName.focus();
			return;
		}
		var itemID = document.getElementById('itemID');
		if (itemID.value.length == 0) {
			alert("请选择所属项目!");
			itemID.focus();
			return;
		}
		var price = document.getElementById('price');
		if (price.value.length == 0) {
			alert("请填写单价!");
			//price.focus();
			return;
		} else {
			var s = calculateScore(price);
			if (!s) {
				return;
			}
		}

		var unit = document.getElementById('unit');
		if (unit.value.length == 0) {
			alert("请填写单位!");
			unit.focus();
			return;
		}

		var remark = document.getElementById('remark');
		if (valCharLength(remark.value) > 1024) {
			alert("备注信息不能超过512个汉字或者1024个英文字符！")
			return;
		}
		addForm.submit();
	}

	var regx = /^\d{1,12}[\.]{0,1}\d{0,5}$/;
	calculateScore = function(element) {
		if (!regx.test(element.value)) {
			alert("输入值必须为有效数！");
			element.value = "0";
			element.focus();
			return false;
		}
		return true;
	}

	function valCharLength(Value) {
		var j = 0;
		var s = Value;
		for ( var i = 0; i < s.length; i++) {
			if (s.substr(i, 1).charCodeAt(0) > 255) {
				j = j + 2;
			} else {
				j++;
			}
		}
		return j;
	}
</script>
	</head>
	<body>
		<br>
		<template:titile value="增加修缮类别" />
		<html:form
			action="/project/remedy_type.do?method=addRemedyTypeByOneItem"
			styleId="addRemedyType">
			<template:formTable namewidth="150" contentwidth="300">
				<template:formTr name="类别名称">
					<input name="typeName" id="typeName" class="inputtext"
						style="width: 215;" type="text" />
				</template:formTr>
				<template:formTr name="所属项目">
					<input name="itemID" value="${item_id}" type="hidden" />
					<input name="itemName" value="${item_name}" type="hidden" />
					${item_name}
					<input name="continue_add_type" type="hidden" value="1" />
					<input name="price" id="price" class="inputtext"
						style="width: 215;" type="hidden" value="0" />
				</template:formTr>
				<template:formTr name="单位">
					<input name="unit" id="unit" class="inputtext" style="width: 215;"
						type="text" />
				</template:formTr>
				<template:formTr name="备注">
					<textarea name="remark" id="remark" cols="36" rows="4"
						class="textarea" title="最多输入512个汉字，1024个英文字母！"></textarea>
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:button property="action" styleClass="button"
							onclick="checkAddInfo('1')">继续添加</html:button>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="checkAddInfo('0')">完成</html:button>
					</td>
					<td>
						<html:reset property="action" styleClass="button">重置</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
