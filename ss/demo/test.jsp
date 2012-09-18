<%@include file="/common/header.jsp"%>


<html>
<head>
<title>upload</title>
<link rel='stylesheet' type='text/css'
	href='${ctx}/js/extjs/resources/css/ext-all.css' />
<script type='text/javascript'
	src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<script type="text/javascript">
var win;
function viewPalnDetail(){
  url="${ctx}/demo/upload.jsp";
  win = new Ext.Window({
  layout : 'fit',
  width:750,
  height:400, 
  resizable:true,
  closeAction : 'close', 
  modal:true,
  html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src='+url+' />',
  plain: true
 });
  win.show(Ext.getBody());
}
</script>
</head>
<body bgcolor="#ffffff">
<table>
	<tr>
		<td height="200px"></td>
	</tr>
</table>
<table>
	<tr>
		<td height="200px"></td>
	</tr>
</table>
<table>
	<tr>
		<td height="200px"></td>
	</tr>
</table>
<table>
	<tr>
		<td height="200px"></td>
	</tr>
</table>
<table>
	<tr>
		<td height="200px"></td>
	</tr>
</table>
<table>
	<tr>
		<td height="200px"></td>
	</tr>
</table>
<table>
	<tr>
		<td height="200px"></td>
	</tr>
</table>
<table>
	<tr>
		<td height="200px"></td>
	</tr>
</table>
<table>

	<tr class=trwhite>
		<td class="tdulleft">添加附件:</td>
		<td class="tdulright" colspan="3">
		<script type='text/javascript' 	src='${ctx}/js/uploader/ajaxupload.js'></script>
		<script type='text/javascript'>
		function removefile(delid){jQuery.ajax({type:"POST",url:'${ctx}/UploadServlet?type=remove&delid='+delid,cache : false,success:function(result){jQuery('#newfile').empty();jQuery('#newfile').html(result);}	});	}
		function del(delid){jQuery.ajax({type:"POST",url:'${ctx}/UploadServlet?type=del&delid='+delid,cache : false,success:function(result){jQuery('#oldfile').empty();jQuery('#oldfile').html(result);}	});	}
		jQuery(document).ready(
				function(){
					var button = jQuery('#upload'), 
					interval;
					new AjaxUpload(button, {
						action: '${ctx}/UploadServlet?type=upload', 
						name: 'myfile',
						onSubmit : 
							function(file, ext){	
						button.text('正在上传');	
						this.disable();	
						interval = window.setInterval(
								function(){var text = button.text();
								if (text.length < 13){button.text(text + '.');}
								else {button.text('正在上传');}}, 200);},
								onComplete: function(file, response){
									button.text('添加附件');
									window.clearInterval(interval);
									this.enable();
									if(response.indexOf("msg:") == -1){
										jQuery('#msg').empty();
										jQuery('#newfile').empty();
										jQuery('#newfile').html(response);
									}else{
										error = response.split(":");
										jQuery('#msg').html(error[1]);	
										}
									}
				});});
		/*]]>*/
		</script>
		<a
			href="javascript:;" id="upload">添加附件</a><span id="msg"
			style="color: red;"></span>
		
		<div id="newfile" class="files"></div>
		</td>
	</tr>

</table>
<a href="javascript:;" onclick=
	viewPalnDetail();
>extjs</a>

</body>
</html>
