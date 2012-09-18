function remove(delid) {
	jQuery.ajax( {
		type : "POST",
		url : '/WebApp/UploadServlet?type=remove&delid=' + delid,
		cache : false,
		success : function(result) {
			jQuery('#newfile').empty();
			jQuery('#newfile').html(result);
		}
	});
}
function del(delid) {
	jQuery.ajax( {
		type : "POST",
		url : '/WebApp/UploadServlet?type=del&delid=' + delid,
		cache : false,
		success : function(result) {
			jQuery('#oldfile').empty();
			jQuery('#oldfile').html(result);
		}
	});
}
function uploadFile(id) {
	var button = jQuery('#upload'), interval;
	new AjaxUpload(button, {
		action : '/WebApp/UploadServlet?type=upload',
		name : 'myfile',
		onSubmit : function(file, ext) {
			button.text('正在上传');
			this.disable();
			interval = window.setInterval(function() {
				var text = button.text();
				if (text.length < 13) {
					button.text(text + '.');
				} else {
					button.text('正在上传');
				}
			}, 200);
		},
		onComplete : function(file, response) {
			button.text('添加附件');
			window.clearInterval(interval);
			this.enable();
			if (response.indexOf("msg:") == -1) {
				jQuery('#msg').empty();
				jQuery('#newfile').empty();
				jQuery('#newfile').html(response);
			} else {
				error = response.split(":");
				jQuery('#msg').html(error[1]);
			}
		}
	});
}