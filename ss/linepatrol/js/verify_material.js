verifyMaterial = function(addForm) {
	var regx = /^[1-9]\d{0,6}[\.]{0,4}\d{0,6}$|^-?0(\.\d*)?$/;
	if(typeof(addForm.materialStorageNumberUse)!="undefined"){
		if(typeof(addForm.materialStorageNumberUse.length)=="undefined"){
			if(addForm.materialTypeUse.value==""){
				alert("没有选择使用材料类型！");
				return false;
			}
			if(addForm.materialModeUse.value==""){
				alert("没有选择使用材料规格！");
				return false;
			}
			if(addForm.materialUse.value==""){
				alert("没有选择使用材料名称！");
				return false;
			}
			if(addForm.materialStorageAddrUse.value==""){
				alert("没有选择使用材料的存放地点！");
				return false;
			}
			if(addForm.materialStorageTypeUse.value==""){
				alert("没有选择使用材料的库存类型！");
				return false;
			}
			if(addForm.materialStorageNumberUse.value==""){
				alert("没有输入使用材料的库存数量！");
				return false;
			}
			if(addForm.materialUseNumberUse.value==""){
				alert("没有输入使用材料的使用数量！");
				return false;
			}
			if(!regx.test(addForm.materialStorageNumberUse.value)){
				alert("使用材料的库存数量必须为数字！");
				return false;
			}
			if(!regx.test(addForm.materialUseNumberUse.value)){
				alert("使用材料的使用数量必须为数字！");
				return false;
			}
			if(parseFloat(addForm.materialUseNumberUse.value)<=0){
				alert("使用材料的使用数量要大于零！");
				return false;
			}
			if(parseFloat(addForm.materialStorageNumberUse.value)<parseFloat(addForm.materialUseNumberUse.value)){
				alert("使用材料的库存数量不足，请重新选择！");
				return false;
			}
		}else{
			for(i=0;i<addForm.materialStorageNumberUse.length;i++){
				if(addForm.materialTypeUse[i].value==""){
					alert("没有选择第"+(i+1)+"个使用材料类型！");
					return false;
				}
				if(addForm.materialModeUse[i].value==""){
					alert("没有选择第"+(i+1)+"个使用材料规格！");
					return false;
				}
				if(addForm.materialUse[i].value==""){
					alert("没有选择第"+(i+1)+"个使用材料名称！");
					return false;
				}
				if(addForm.materialStorageAddrUse[i].value==""){
					alert("没有选择第"+(i+1)+"个使用材料的存放地点！");
					return false;
				}
				if(addForm.materialStorageTypeUse[i].value==""){
					alert("没有选择第"+(i+1)+"个使用材料的库存类型！");
					return false;
				}
				if(validExistSameMaterial(addForm,i)){
					return false;
				}
				if(addForm.materialStorageNumberUse[i].value==""){
					alert("没有输入第"+(i+1)+"个使用材料的库存数量！");
					return false;
				}
				if(addForm.materialUseNumberUse[i].value==""){
					alert("没有输入第"+(i+1)+"个使用材料的使用数量！");
					return false;
				}
				if(!regx.test(addForm.materialStorageNumberUse[i].value)){
					alert("第"+(i+1)+"个使用材料的库存数量必须为数字！");
					return false;
				}
				if(!regx.test(addForm.materialUseNumberUse[i].value)){
					alert("第"+(i+1)+"个使用材料的使用数量必须为数字！");
					return false;
				}
				if(parseFloat(addForm.materialUseNumberUse[i].value)<=0){
					alert("第"+(i+1)+"个使用材料的使用数量要大于零！");
					return false;
				}
				if(parseFloat(addForm.materialStorageNumberUse[i].value)<parseFloat(addForm.materialUseNumberUse[i].value)){
					alert("第"+(i+1)+"个使用材料的库存数量不足，请重新选择！");
					return false;
				}
			}
	     }
	  }

	
	if(typeof(addForm.materialStorageNumberRecycle)!="undefined"){
		if(typeof(addForm.materialStorageNumberRecycle.length)=="undefined"){
			if(addForm.materialTypeRecycle.value==""){
				alert("没有选择回收材料类型！");
				return false;
			}
			if(addForm.materialModeRecycle.value==""){
				alert("没有选择回收材料规格！");
				return false;
			}
			if(addForm.materialRecycle.value==""){
				alert("没有选择回收材料名称！");
				return false;
			}
			if(addForm.materialStorageAddrRecycle.value==""){
				alert("没有选择回收材料的存放地点！");
				return false;
			}
			if(addForm.materialStorageTypeRecycle.value==""){
				alert("没有选择回收材料的库存类型！");
				return false;
			}
			if(addForm.materialStorageNumberRecycle.value==""){
				alert("没有输入回收材料的库存数量！");
				return false;
			}
			if(addForm.materialUseNumberRecycle.value==""){
				alert("没有输入回收材料的使用数量！");
				return false;
			}
			if(!regx.test(addForm.materialStorageNumberRecycle.value)){
				alert("回收材料的库存数量必须为数字！");
				return false;
			}
			if(!regx.test(addForm.materialUseNumberRecycle.value)){
				alert("回收材料的使用数量必须为数字！");
				return false;
			}
			if(parseFloat(addForm.materialUseNumberRecycle.value)<=0){
				alert("回收材料的使用数量要大于零！");
				return false;
			}
		//	if(parseFloat(addForm.materialStorageNumberRecycle.value)<parseFloat(addForm.materialUseNumberRecycle.value)){
			//	alert("回收材料的库存数量不足，请重新选择！");
			//	return false;
			//}
		}else{
			for(i=0;i<addForm.materialStorageNumberRecycle.length;i++){
				if(addForm.materialTypeRecycle[i].value==""){
					alert("没有选择第"+(i+1)+"个回收材料类型！");
					return false;
				}
				if(addForm.materialModeRecycle[i].value==""){
					alert("没有选择第"+(i+1)+"个回收材料规格！");
					return false;
				}
				if(addForm.materialRecycle[i].value==""){
					alert("没有选择第"+(i+1)+"个回收材料名称！");
					return false;
				}
				if(addForm.materialStorageAddrRecycle[i].value==""){
					alert("没有选择第"+(i+1)+"个回收材料的存放地点！");
					return false;
				}
				if(addForm.materialStorageTypeRecycle[i].value==""){
					alert("没有选择第"+(i+1)+"个回收材料的库存类型！");
					return false;
				}
				if(validExistSameMaterialByRec(addForm,i)){
					return false;
				}
				if(addForm.materialStorageNumberRecycle[i].value==""){
					alert("没有输入第"+(i+1)+"个回收材料的库存数量！");
					return false;
				}
				if(addForm.materialUseNumberRecycle[i].value==""){
					alert("没有输入第"+(i+1)+"个回收材料的使用数量！");
					return false;
				}
				if(!regx.test(addForm.materialStorageNumberRecycle[i].value)){
					alert("第"+(i+1)+"个回收材料的库存数量必须为数字！");
					return false;
				}
				if(!regx.test(addForm.materialUseNumberRecycle[i].value)){
					alert("第"+(i+1)+"个回收材料的使用数量必须为数字！");
					return false;
				}
				if(parseFloat(addForm.materialUseNumberRecycle[i].value)<=0){
					alert("第"+(i+1)+"个回收材料的使用数量要大于零！");
					return false;
				}
				//if(parseFloat(addForm.materialStorageNumberRecycle[i].value)<parseFloat(addForm.materialUseNumberRecycle[i].value)){
					//alert("第"+(i+1)+"个回收材料的库存数量不足，请重新选择！");
					//return false;
				//}
				
			}
	     }
	  }
	
	  
	  return true;
}



validExistSameMaterial=function(addForm,itemSeq){
	var material=addForm.materialUse[itemSeq].value;
	var materialStorageAddr=addForm.materialStorageAddrUse[itemSeq].value;
	var materialStorageType=addForm.materialStorageTypeUse[itemSeq].value;
	for(j=0;j<addForm.materialStorageNumberUse.length;j++){
		if (j == itemSeq) {
			continue;
		}
		var sampleMaterial=addForm.materialUse[j].value;
		var sampleMaterialStorageAddr=addForm.materialStorageAddrUse[j].value;
		var sampleMaterialStorageType=addForm.materialStorageTypeUse[j].value;
		if(material==sampleMaterial&&materialStorageAddr==sampleMaterialStorageAddr&&materialStorageType==sampleMaterialStorageType){
			alert("第"+(itemSeq+1)+"个使用材料和第"+(j+1)+"个使用材料完全相同，请进行修改！");
			return true;
		}
	}
	return false;
}

validExistSameMaterialByRec=function(addForm,itemSeq){
	var material=addForm.materialRecycle[itemSeq].value;
	var materialStorageAddr=addForm.materialStorageAddrRecycle[itemSeq].value;
	var materialStorageType=addForm.materialStorageTypeRecycle[itemSeq].value;
	for(j=0;j<addForm.materialStorageNumberRecycle.length;j++){
		if (j == itemSeq) {
			continue;
		}
		var sampleMaterial=addForm.materialRecycle[j].value;
		var sampleMaterialStorageAddr=addForm.materialStorageAddrRecycle[j].value;
		var sampleMaterialStorageType=addForm.materialStorageTypeRecycle[j].value;
		if(material==sampleMaterial&&materialStorageAddr==sampleMaterialStorageAddr&&materialStorageType==sampleMaterialStorageType){
			alert("第"+(itemSeq+1)+"个回收材料和第"+(j+1)+"个回收材料完全相同，请进行修改！");
			return true;
		}
	}
	return false;
}

