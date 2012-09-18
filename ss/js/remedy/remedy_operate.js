judgeItem = function(addForm) {
	if (typeof (addForm.itemName) == "undefined") {
		alert("没有添加工作！");
		return false;
	}
	if (typeof (addForm.itemName.length) == "undefined") {
		if (addForm.itemName.value == "") {
			alert("没有填写工作项！");
			addForm.itemName.focus();
			return false;
		}
		//if (addForm.itemType.value == "") {
		//	alert("没有选择工作项类别！");
		//	return false;
		//}
//		if (addForm.itemUnit.value == "") {
//			alert("修缮定额项没有计量单位！");
//			return false;
//		}
		// if (addForm.itemUnitPrice.value == "") {
		// alert("修缮定额项没有单价！");
		// return false;
		// }
//		if (addForm.itemWorkNumber.value == "") {
//			alert("没有输入修缮定额项的工作量！");
//			return false;
//		}
		// if (addForm.itemFee.value == "") {
		// alert("没有输入修缮定额项的费用！");
		// return false;
		// }
//		if (!regx.test(addForm.itemWorkNumber.value)) {
//			alert("修缮定额项的工作量必须为数字！");
//			return false;
//		}
		// if (!regx.test(addForm.itemFee.value)) {
		// alert("修缮定额项的费用必须为数字！");
		// return false;
		// }
	} else {
		for (i = 0; i < addForm.itemName.length; i++) {
			if (addForm.itemName[i].value == "") {
				alert("没有填写第" + (i + 1) + "个工作项！");
				return false;
			}
			//if (addForm.itemType[i].value == "") {
			//	alert("没有选择第" + (i + 1) + "个工作项类别！");
			//	return false;
			//}
//			if (addForm.itemUnit[i].value == "") {
//				alert("第" + (i + 1) + "个修缮定额项没有计量单位！");
//				return false;
//			}
			// if (addForm.itemUnitPrice[i].value == "") {
			// alert("第"
			// + (i + 1)
			// + "个修缮定额项没有单价！");
			// return false;
			// }
//			if (addForm.itemWorkNumber[i].value == "") {
//				alert("没有输入第" + (i + 1) + "个修缮定额项的工作量！");
//				return false;
//			}
			// if (addForm.itemFee[i].value == "") {
			// alert("没有输入第"
			// + (i + 1)
			// + "个修缮定额项的费用！");
			// return false;
			// }
//			if (!regx.test(addForm.itemWorkNumber[i].value)) {
//				alert("第" + (i + 1) + "个修缮定额项的工作量必须为数字！");
//				return false;
//			}
			// if (!regx.test(addForm.itemFee[i].value)) {
			// alert("第"
			// + (i + 1)
			// + "个修缮定额项的费用必须为数字！");
			// return false;
			// }
		}
	}
	if (addForm.totalFee.value == "") {
		alert("没有输入工程费用合计金额");
		addForm.totalFee.focus();
		return false;
	}
	return true;
};
judgeMaterial = function(addForm, suffix) {
	if (suffix == null || typeof (suffix) == "undefined") {
		suffix = "";
	}
	if (typeof (addForm.elements["materialUseNumber" + suffix]) == "undefined") {
		//alert("没有添加修缮材料！");
		//return false;
		return true;
	}
	if (typeof (addForm.elements["materialUseNumber" + suffix].length) == "undefined") {
		if (addForm.elements["materialType" + suffix].value == "") {
			alert("没有选择修缮材料类型！");
			return false;
		}
		if (addForm.elements["materialMode" + suffix].value == "") {
			alert("没有选择修缮材料规格！");
			return false;
		}
		if (addForm.elements["material" + suffix].value == "") {
			alert("没有选择修缮材料名称！");
			return false;
		}
		if (addForm.elements["materialStorageAddr" + suffix].value == "") {
			alert("没有选择修缮材料的存放地点！");
			return false;
		}
		if (addForm.elements["materialStorageType" + suffix].value == "") {
			alert("没有选择修缮材料的库存类型！");
			return false;
		}
		if (addForm.elements["materialStorageNumber" + suffix].value == "") {
			alert("没有输入修缮材料的库存数量！");
			return false;
		}
		if (addForm.elements["materialUseNumber" + suffix].value == "") {
			alert("没有输入修缮材料的使用数量！");
			return false;
		}
		if (!regx
				.test(addForm.elements["materialStorageNumber" + suffix].value)) {
			alert("修缮材料的库存数量必须为数字！");
			return false;
		}
		if (!regx.test(addForm.elements["materialUseNumber" + suffix].value)) {
			alert("修缮材料的使用数量必须为数字！");
			return false;
		}
		if(suffix == 'Use'){
		if (parseFloat(addForm.elements["materialStorageNumber" + suffix].value) < parseFloat(addForm.elements["materialUseNumber"
				+ suffix].value)) {
			alert("修缮材料的库存数量不足，请重新选择！");
			return false;
		}
		}
	} else {
		for (i = 0; i < addForm.elements["materialStorageNumber" + suffix].length; i++) {
			if (addForm.elements["materialType" + suffix][i].value == "") {
				alert("没有选择第" + (i + 1) + "个修缮材料类型！");
				return false;
			}
			if (addForm.elements["materialMode" + suffix][i].value == "") {
				alert("没有选择第" + (i + 1) + "个修缮材料规格！");
				return false;
			}
			if (addForm.elements["material" + suffix][i].value == "") {
				alert("没有选择第" + (i + 1) + "个修缮材料名称！");
				return false;
			}
			if (addForm.elements["materialStorageAddr" + suffix][i].value == "") {
				alert("没有选择第" + (i + 1) + "个修缮材料的存放地点！");
				return false;
			}
			if (addForm.elements["materialStorageType" + suffix][i].value == "") {
				alert("没有选择第" + (i + 1) + "个修缮材料的库存类型！");
				return false;
			}
			if (judgeExistSameMaterial(addForm, i, suffix)) {
				return false;
			}
			if (addForm.elements["materialStorageNumber" + suffix][i].value == "") {
				alert("没有输入第" + (i + 1) + "个修缮材料的库存数量！");
				return false;
			}
			if (addForm.elements["materialUseNumber" + suffix][i].value == "") {
				alert("没有输入第" + (i + 1) + "个修缮材料的使用数量！");
				return false;
			}
			if (!regx
					.test(addForm.elements["materialStorageNumber" + suffix][i].value)) {
				alert("第" + (i + 1) + "个修缮材料的库存数量必须为数字！");
				return false;
			}
			if (!regx
					.test(addForm.elements["materialUseNumber" + suffix][i].value)) {
				alert("第" + (i + 1) + "个修缮材料的使用数量必须为数字！");
				return false;
			}
			if(suffix == 'Use'){
			if (parseFloat(addForm.elements["materialStorageNumber" + suffix][i].value) < parseFloat(addForm.elements["materialUseNumber"
					+ suffix][i].value)) {
				alert("第" + (i + 1) + "个修缮材料的库存数量不足，请重新选择！");
				return false;
			}
			}
		}
	}
	return true;
};
judgeExistSameMaterial = function(addForm, itemSeq, suffix) {
	var material = addForm.elements["material" + suffix][itemSeq].value;
	var materialStorageAddr = addForm.elements["materialStorageAddr" + suffix][itemSeq].value;
	var materialStorageType = addForm.elements["materialStorageType" + suffix][itemSeq].value;
	for (j = 0; j < addForm.materialStorageNumber.length; j++) {
		if (j == itemSeq) {
			continue;
		}
		var sampleMaterial = addForm.material[j].value;
		var sampleMaterialStorageAddr = addForm.materialStorageAddr[j].value;
		var sampleMaterialStorageType = addForm.materialStorageType[j].value;
		if (material == sampleMaterial
				&& materialStorageAddr == sampleMaterialStorageAddr
				&& materialStorageType == sampleMaterialStorageType) {
			alert("\u7b2c"
					+ (itemSeq + 1)
					+ "\u4e2a\u4fee\u7f2e\u6750\u6599\u548c\u7b2c"
					+ (j + 1)
					+ "\u4e2a\u4fee\u7f2e\u6750\u6599\u5b8c\u5168\u76f8\u540c\uff0c\u8bf7\u8fdb\u884c\u4fee\u6539\uff01");
			return true;
		}
	}
	return false;
};
changeItemType = function(itemId, sItemTypeId, itemTypeId, seqId) {
	var sItemType = document.getElementById(sItemTypeId);
	var itemSeqId = document.getElementById("itemId_" + seqId);
	//var itemUnit = document.getElementById("itemUnit_" + seqId);
	var itemType = document.getElementById(itemTypeId + "_" + seqId);
	var item = document.getElementById(itemId + "_" + seqId);
	var row = 0;
	if (typeof (sItemType.length) != "undefined" && sItemType != null) {
		for (i = 0; i < sItemType.length; i++) {
			var itemTypeParentValue = sItemType.options[i].varRefId;
			//var itemTypeUnit = sItemType.options[i].varUnit;
			//var itemTypeUnitPrice = sItemType.options[i].varUnitPrice;
			//if (typeof (itemUnit.length) == "undefined") {
				var index = item.options.selectedIndex;
				var parentValue = item.options[index].varId;
				itemSeqId.value = item.options[index].varId;
				if (itemTypeParentValue == parentValue) {
					row++;
					itemType.options.length = row;
					itemType.options[row - 1] = new Option(
							sItemType.options[i].text,
							sItemType.options[i].value, false, false);
					itemType.options[row - 1].varId = sItemType.options[i].varId;
					itemType.options[row - 1].varRefId = sItemType.options[i].varRefId;
					itemType.options[row - 1].varUnit = sItemType.options[i].varUnit;
					itemType.options[row - 1].varUnitPrice = sItemType.options[i].varUnitPrice;
					displayUnit(itemTypeId, seqId);
				}
			//}
			if (row == 0) {
				itemType.options.length = 1;
				itemType.options[0] = new Option("\u8bf7\u9009\u62e9", "",
						false, false);
				itemType.options[0].varId = "";
				itemType.options[0].varRefId = "";
				itemType.options[0].varUnit = "";
				itemType.options[0].varUnitPrice = "0";
				displayUnit(itemTypeId, seqId);
			}
		}
	}
};
displayUnit = function(itemTypeId, seqId) {
	var itemType = document.getElementById(itemTypeId + "_" + seqId);
	var itemTypeSeqId = document.getElementById("itemTypeId_" + seqId);
	//var itemUnit = document.getElementById("itemUnit_" + seqId);
	//var itemUnitDis = document.getElementById("itemUnitDis_" + seqId);
	// var itemUnitPrice = document.getElementById("itemUnitPrice_" + seqId);
	// var itemUnitPriceDis = document.getElementById("itemUnitPriceDis_" +
	// seqId);
	//if (typeof (itemUnit.length) == "undefined") {
		var index = itemType.options.selectedIndex;
		itemTypeSeqId.value = itemType.options[index].varId;
	//	itemUnit.value = itemType.options[index].varUnit;
	//	itemUnitDis.innerText = itemType.options[index].varUnit;
		// itemUnitPrice.value = itemType.options[index].varUnitPrice;
		// itemUnitPriceDis.innerText = itemType.options[index].varUnitPrice;
	//}
};
changeMaterialMode = function(itemId, sItemTypeId, itemTypeId, seqId,
		materialUseType) {
	if (typeof (materialUseType) == "undefined" || materialUseType == null) {
		materialUseType = "";
	}
	var sItemType = document.getElementById(sItemTypeId);
	var itemUnit = document.getElementById("materialStorageNumber"
			+ materialUseType + "_" + seqId);
	var itemType = document.getElementById(itemTypeId + "_" + seqId);
	var item = document.getElementById(itemId + "_" + seqId);
	var row = 0;
	if (typeof (sItemType.length) != "undefined" && sItemType != null) {
		for (i = 0; i < sItemType.length; i++) {
			var itemTypeParentValue = sItemType.options[i].varRefId;
			if (typeof (itemUnit.length) == "undefined") {
				var index = item.options.selectedIndex;
				var parentValue = item.options[index].value;
				if (itemTypeParentValue == parentValue) {
					row++;
					itemType.options.length = row;
					itemType.options[row - 1] = new Option(
							sItemType.options[i].text,
							sItemType.options[i].value, false, false);
					itemType.options[row - 1].varRefId = sItemType.options[i].varRefId;
					changeMaterial("materialMode" + materialUseType,
							"sMaterial" + materialUseType, "material"
									+ materialUseType, seqId, materialUseType);
					// changeMaterialPrice("material"+materialUseType,
					// seqId,materialUseType);
				}
			}
		}
		if (row == 0) {
			itemType.options.length = 1;
			itemType.options[0] = new Option("\u8bf7\u9009\u62e9", "", false,
					false);
			itemType.options[0].varRefId = "";
			changeMaterial("materialMode" + materialUseType, "sMaterial"
					+ materialUseType, "material" + materialUseType, seqId,
					materialUseType);
			// changeMaterialPrice("material"+materialUseType,
			// seqId,materialUseType);
		}
	}
};
changeMaterial = function(itemId, sItemTypeId, itemTypeId, seqId,
		materialUseType) {
	if (typeof (materialUseType) == "undefined" || materialUseType == null) {
		materialUseType = "";
	}
	var sItemType = document.getElementById(sItemTypeId);
	var itemUnit = document.getElementById("materialStorageNumber"
			+ materialUseType + "_" + seqId);
	var itemType = document.getElementById(itemTypeId + "_" + seqId);
	var item = document.getElementById(itemId + "_" + seqId);
	var row = 0;
	if (typeof (sItemType.length) != "undefined" && sItemType != null) {
		for (j = 0; j < sItemType.length; j++) {
			var itemTypeParentValue = sItemType.options[j].varRefId;
			if (typeof (itemUnit.length) == "undefined") {
				var index = item.options.selectedIndex;
				var parentValue = item.options[index].value;
				if (itemTypeParentValue == parentValue) {
					row++;
					itemType.options.length = row;
					itemType.options[row - 1] = new Option(
							sItemType.options[j].text,
							sItemType.options[j].value, false, false);
					itemType.options[row - 1].varRefId = sItemType.options[j].varRefId;
					itemType.options[row - 1].varUnitPrice = sItemType.options[j].varUnitPrice;
					changeMaterialStorage("material" + materialUseType,
							"sMaterialStorageAddr" + materialUseType,
							"materialStorageAddr" + materialUseType, seqId,
							materialUseType);
					// changeMaterialPrice("material"+materialUseType, seqId);
				}
			}
		}
		if (row == 0) {
			itemType.options.length = 1;
			itemType.options[0] = new Option("\u8bf7\u9009\u62e9", "", false,
					false);
			itemType.options[0].varRefId = "";
			itemType.options[0].varUnitPrice = "";
			changeMaterialStorage("material" + materialUseType,
					"sMaterialStorageAddr" + materialUseType,
					"materialStorageAddr" + materialUseType, seqId,
					materialUseType);
			// changeMaterialPrice("material"+materialUseType, seqId);
		}
	}
};

changeMaterialPrice = function(itemId, seqId, materialUseType) {
	if (typeof (materialUseType) == "undefined" || materialUseType == null) {
		materialUseType = "";
	}
	var itemUnitPrice = document.getElementById("materialUnitPrice"
			+ materialUseType + "_" + seqId);
	var itemUnitPriceDis = document.getElementById("materialUnitPriceDis"
			+ materialUseType + "_" + seqId);
	var item = document.getElementById(itemId + "_" + seqId);
	var index = item.options.selectedIndex;
	itemUnitPrice.value = item.options[index].varUnitPrice;
	itemUnitPriceDis.innerText = item.options[index].varUnitPrice;

};

changeMaterialStorage = function(itemId, sItemTypeId, itemTypeId, seqId,
		materialUseType) {
	if (typeof (materialUseType) == "undefined" || materialUseType == null) {
		materialUseType = "";
	}
	var sItemType = document.getElementById(sItemTypeId);
	var itemUnit = document.getElementById("materialStorageNumber"
			+ materialUseType + "_" + seqId);
	var itemUnitPrice = document.getElementById("materialUnitPrice"
			+ materialUseType + "_" + seqId);
	var itemUnitPriceDis = document.getElementById("materialUnitPriceDis"
			+ materialUseType + "_" + seqId);
	var itemType = document.getElementById(itemTypeId + "_" + seqId);
	var item = document.getElementById(itemId + "_" + seqId);
	var row = 0;
	if (typeof (sItemType.length) != "undefined" && sItemType != null) {
		for (k = 0; k < sItemType.length; k++) {
			var itemTypeParentValue = sItemType.options[k].varRefId;
			if (typeof (itemUnit.length) == "undefined") {
				var index = item.options.selectedIndex;
				var parentValue = item.options[index].value;
				if (itemTypeParentValue == parentValue) {
					row++;
					itemType.options.length = row;
					itemType.options[row - 1] = new Option(
							sItemType.options[k].text,
							sItemType.options[k].value, false, false);
					itemType.options[row - 1].varRefId = sItemType.options[k].varRefId;
					itemType.options[row - 1].varOldStorage = sItemType.options[k].varOldStorage;
					itemType.options[row - 1].varNewStorage = sItemType.options[k].varNewStorage;
					// itemUnitPrice.value = item.options[index].varUnitPrice;
					// itemUnitPriceDis.innerText =
					// item.options[index].varUnitPrice;
					changeMaterialStorageType(seqId, materialUseType);
				}
			}
		}
		if (row == 0) {
			itemType.options.length = 1;
			itemType.options[0] = new Option("\u8bf7\u9009\u62e9", "", false,
					false);
			itemType.options[0].varRefId = "";
			itemType.options[0].varOldStorage = "0";
			itemType.options[0].varNewStorage = "0";
			// itemUnitPrice.value = "0";
			// itemUnitPriceDis.innerText = "0";
			changeMaterialStorageType(seqId, materialUseType);
		}
	}
};

changeMaterialStorageType = function(seqId, materialUseType) {
	if (typeof (materialUseType) == "undefined" || materialUseType == null) {
		materialUseType = "";
	}
	var itemStorageType = document.getElementById("materialStorageType"
			+ materialUseType + "_" + seqId);
	var itemStorage = document.getElementById("materialStorageNumber"
			+ materialUseType + "_" + seqId);
	var itemStorageDis = document.getElementById("materialStorageNumberDis"
			+ materialUseType + "_" + seqId);
	itemStorageType.options.selectedIndex = 0;
	itemStorage.value = "";
	itemStorageDis.innerText = "";
};
changeMaterialStorageNumber = function(itemId, seqId, materialUseType) {
	if (typeof (materialUseType) == "undefined" || materialUseType == null) {
		materialUseType = "";
	}
	var itemUnit = document.getElementById("materialStorageNumber"
			+ materialUseType + "_" + seqId);
	var itemUnitDis = document.getElementById("materialStorageNumberDis"
			+ materialUseType + "_" + seqId);
	var item = document.getElementById(itemId + "_" + seqId);
	var itemStorageAddress = document.getElementById("materialStorageAddr"
			+ materialUseType + "_" + seqId);
	if (typeof (itemUnit.length) == "undefined") {
		var index = item.options.selectedIndex;
		var parentValue = item.options[index].value;
		if (parentValue == "0") {
			index = itemStorageAddress.options.selectedIndex;
			itemUnit.value = itemStorageAddress.options[index].varOldStorage;
			itemUnitDis.innerText = itemStorageAddress.options[index].varOldStorage;
		}
		if (parentValue == "1") {
			index = itemStorageAddress.options.selectedIndex;
			itemUnit.value = itemStorageAddress.options[index].varNewStorage;
			itemUnitDis.innerText = itemStorageAddress.options[index].varNewStorage;
		}
		if (itemUnit.value == "undefined" || itemUnit.value == null) {
			itemUnit.value = "0";
			itemUnitDis.innerText = "0";
		}
	}
};
calculateOne = function(seqId) {
	// var regx = /^\d+(\.\d+)?$/
	// var itemUnitPrice = document.getElementById("itemUnitPrice_" + seqId);
	// var itemWorkNumber = document.getElementById("itemWorkNumber_" + seqId);
	// var itemFee = document.getElementById("itemFee_" + seqId);
	// var itemFeeDis = document.getElementById("itemFeeDis_" + seqId);
	// if (typeof (itemUnitPrice.length) == "undefined") {
	// if (regx.test(itemWorkNumber.value) && regx.test(itemUnitPrice.value)) {
	// var fee = parseFloat(itemUnitPrice.value)
	// * parseFloat(itemWorkNumber.value);
	// itemFee.value = "" + Math.round(fee * 100) / 100;
	// itemFeeDis.innerText = itemFee.value;
	// }
	// }
};
calculateAll = function() {
	// var regx = /^\d+(\.\d+)?$/
	// var itemFee = document.forms[0].elements["itemFee"];
	// var totalFee = document.forms[0].elements["totalFee"];
	// var totalFeeDis = document.getElementById("total_fee");
	// if (typeof (itemFee) == "undefined") {
	// totalFee.value = "0";
	// } else {
	// var fee = 0;
	// if (typeof (itemFee.length) == "undefined") {
	// if (regx.test(itemFee.value)) {
	// fee = parseFloat(itemFee.value);
	// }
	// totalFee.value = "" + Math.round(fee * 100) / 100;
	// } else {
	// for (i = 0; i < itemFee.length; i++) {
	// if (regx.test(itemFee[i].value)) {
	// fee = fee + parseFloat(itemFee[i].value);
	// }
	// }
	// totalFee.value = "" + Math.round(fee * 100) / 100;
	// }
	// }
	// totalFeeDis.innerText = totalFee.value + "\u5143";
};
compareStorageNumber = function(seqId, materialUseType) {
	var regx = /^\d+(\.\d+)?$/
	if (typeof (materialUseType) == "undefined" || materialUseType == null) {
		materialUseType = "";
	}
	var itemStorage = document.getElementById("materialStorageNumber"
			+ materialUseType + "_" + seqId);
	var itemUse = document.getElementById("materialUseNumber" + materialUseType
			+ "_" + seqId);
	if (regx.test(itemStorage.value) && regx.test(itemUse.value)) {
		if (parseFloat(itemStorage.value) < parseFloat(itemUse.value)) {
			alert("\u6750\u6599\u7684\u5e93\u5b58\u6570\u91cf\u4e0d\u8db3\uff0c\u8bf7\u91cd\u65b0\u9009\u62e9\uff01");
			itemUse.focus();
		}
	}
};
calculateOneMaterial = function(seqId) {
	var regx = /^\d+(\.\d+)?$/
	var itemUnitPrice = document.getElementById("materialUnitPrice_" + seqId);
	var itemWorkNumber = document.getElementById("materialUseNumber_" + seqId);
	var itemFee = document.getElementById("materialPrice_" + seqId);
	var itemFeeDis = document.getElementById("materialPriceDis_" + seqId);
	if (typeof (itemUnitPrice.length) == "undefined") {
		if (regx.test(itemWorkNumber.value) && regx.test(itemUnitPrice.value)) {
			var fee = parseFloat(itemUnitPrice.value)
					* parseFloat(itemWorkNumber.value);
			itemFee.value = "" + Math.round(fee * 100) / 100;
			itemFeeDis.innerText = itemFee.value;
		}
	}
};
calculateAllMaterial = function() {
	var regx = /^\d+(\.\d+)?$/
	var itemFee = document.forms[0].elements["materialPrice"];
	var totalFee = document.forms[0].elements["totalMaterialFee"];
	var totalFeeDis = document.getElementById("total_material_fee");
	if (typeof (itemFee) == "undefined") {
		totalFee.value = "0";
	} else {
		var fee = 0;
		if (typeof (itemFee.length) == "undefined") {
			if (regx.test(itemFee.value)) {
				fee = parseFloat(itemFee.value);
			}
			totalFee.value = "" + Math.round(fee * 100) / 100;
		} else {
			for (i = 0; i < itemFee.length; i++) {
				if (regx.test(itemFee[i].value)) {
					fee = fee + parseFloat(itemFee[i].value);
				}
			}
			totalFee.value = "" + Math.round(fee * 100) / 100;
		}
	}
	totalFeeDis.innerText = totalFee.value + "\u5143";
};
