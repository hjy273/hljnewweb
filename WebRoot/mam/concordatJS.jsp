<%@ page language="java" contentType="text/html; charset=GBK"%>
<script type="text/javascript">
	var toView = function(id){
		var url = "${ctx}/concordat.do?method=showViewConcordat&id="+id;
        self.location.replace(url);
	}
	var toDelete = function(id){
		if(confirm("确定要删除吗？")){
			var url = "${ctx}/concordat.do?method=deleteConordat&id="+id;
			self.location.replace(url);
		}
	}
	var toEdit = function(id){
		var url = "${ctx}/concordat.do?method=showEditConcordat&id="+id;
        self.location.replace(url);
	}
	var pushMessage=function(error,content,msg){
		if(content){
			content.style.cssText='border:1px red solid;';
		}
		error.style.cssText='display:block;color:red;';
		error.innerHTML=msg;
		window.setTimeout(function(){
			error.style.display="none";
		}, 3000);
	}
	var validCno = function(obj){
		if(obj.value.replace(/(^\s*)|(\s*$)/g,"")){
			var id = document.getElementById('id');
			var url = "";
			if(id){
				url = "${ctx}/concordat.do?method=valid&id="+id.value+"&cno="+obj.value+"&"+Math.random();
			}else{
				url = "${ctx}/concordat.do?method=valid&cno="+obj.value+"&"+Math.random();
			}
			new Ajax.Request(encodeURI(url),{
				method: 'post',
		       	evalScripts: true,
		       	onSuccess: function(transport){
		       		var writestr = transport.responseText;
		       		if(writestr !=''){
		       			$('notUnique').value = '1';
		       			//pushMessage($('errorcno'), null, writestr);
		       			alert(writestr);
		       		}else{
		       			$('notUnique').value = '0';
		       		}		
		       	},
		       	onFailure: function(){
					alert("验证合同编号未知异常！");
				}
			});
		}
	}
	function isValidForm() {
		var cno = document.concordatBean.cno.value.replace(/(^\s*)|(\s*$)/g,"");
		var patrolregion = document.concordatBean.patrolregion.value.replace(/(^\s*)|(\s*$)/g,"");
		var cname = document.concordatBean.cname.value.replace(/(^\s*)|(\s*$)/g,"");
		var bookdate = document.concordatBean.bookdate.value.replace(/(^\s*)|(\s*$)/g,"");
		var perioddate = document.concordatBean.perioddate.value.replace(/(^\s*)|(\s*$)/g,"");
		if($('notUnique').value == '1'){
			//pushMessage($('errorcno'), null, "合同编号重复！");
			alert("合同编号重复！");
			return false;	
		}
		if(cno == ''){
			//pushMessage($('errorcno'), null, "合同编号不能为空！");
			alert("合同编号不能为空！");
			return false;
		}
		else if(patrolregion == ''){
			//pushMessage($('errorcno'), null, "维护区域不能为空！");
			alert("维护区域不能为空！");
			return false;
		}
		else if(cname == ''){
			//pushMessage($('errorcno'), null, "合同名称不能为空！");
			alert("合同名称不能为空！");
			return false;
		}	
		else if(bookdate == ''){
			//pushMessage($('errorcno'), null, "签订日期不能为空！");
			alert("签订日期不能为空！");
			return false;
		}
		else if(perioddate == ''){
			//pushMessage($('errorcno'), null, "合同有效期不能为空！");
			alert("合同有效期不能为空！");
			return false;
		}else {
			return true;
		}
	}		
	function _back4bidden(e) {   
	    var code;   
	    if (!e) var e = window.event;   
	    if (e.keyCode) code = e.keyCode;   
	    else if (e.which) code = e.which;   
	    if (((event.keyCode == 8) && 
	    	((event.srcElement.type != "text" &&    
	         event.srcElement.type != "textarea" &&    
	         event.srcElement.type != "password") ||    
	         event.srcElement.readOnly == true)) ||    
	        ((event.ctrlKey) && ((event.keyCode == 78) || (event.keyCode == 82)) ) ||    
	        (event.keyCode == 116) ) {    
	        event.keyCode = 0;    
	        event.returnValue = false;    
	    }   
	    return true;   
	}
	document.onkeydown = _back4bidden;
	document.oncontextmenu=new Function("return false");
</script>