var win;
function showRule(comId,itemId){
	var url = "/WebApp/questAction.do?method=showRule&comId="+comId+"&itemId="+itemId;
	win = new Ext.Window({
		layout : 'fit',
		width:650,
		height:400,
		resizable:true,
		closeAction : 'close',
		modal:true,
		html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
		plain: true
	});
	win.show(Ext.getBody());
}

function checkForm(state){
	if(state=='3'){
		var comIds=document.getElementById("comIds").value;
		var itemIds=document.getElementById("itemIds").value;
		var comIdsArray=comIds.split(",");
		var itemIdsArray=itemIds.split(",");
		for(var i=0;i<comIdsArray.length;i++){
			for(var j=0;j<itemIdsArray.length;j++){
				var comItemId=""+comIdsArray[i]+""+itemIdsArray[j];
				if(document.getElementById(comItemId).value==""){
					alert("有未完成的评估项，请对每一项进行评估！"); 
					return;
				}
			}
		}
		addFeedback.submit();
	}else{
		document.getElementById("saveflag").value="2";
		addFeedback.submit();
	}
}
