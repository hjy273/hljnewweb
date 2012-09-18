<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=GBK" %>
<%@include file="/common/header.jsp"%>
<html>

<head>
<style type="text/css">

body {margin: 0 auto;}
.bg{width:510px; height:222px; background-image:url(${ctx }/images2/bg/bg_center.jpg); margin-left:20%; margin-right:auto; margin-top:10%;}
.bg_left{ width:11px; height:222px; float:left; background-image:url(${ctx}/images2/bg/bg_left.jpg);}
.bg_right{ width:495px;height:222px; background:url(${ctx }/images2/bg/bg_right.jpg);background-repeat: no-repeat;background-position: right; float:right;}
.hint{ width:130px; height:130px; background-image:url(${ctx }/images2/bg/State_Tips.jpg); float:left; margin-left:20px; margin-top:20px; background-repeat:no-repeat;}
.error{ width:130px; height:130px; background-image:url(${ctx }/images2/bg/State_Mistake.jpg); float:left; margin-left:20px; margin-top:20px; background-repeat:no-repeat;}
.warning{ width:130px; height:130px; background-image:url(${ctx }/images2/bg/State_Warning.jpg); float:left; margin-left:20px; margin-top:20px; background-repeat:no-repeat;}
.success{ width:130px; height:130px; background-image:url(${ctx }/images2/bg/State_Success.jpg); float:left; margin-left:20px; margin-top:20px; background-repeat:no-repeat;}
.text{ width:320px; height:50px; font-size:16px; color:#FF0000; font-family:"黑体";  line-height:28px;margin-left:150px; margin-top:65px;  }
.layout_button{ padding-top:35px; padding-left:350px; background-repeat:no-repeat;}
.button{ width:82px; height:31px;background:url(${ctx }/images2/bg/button.jpg); border:none;}
</style>
<script language="javascript" type="text/javascript">
jQuery(function(){
	parent.location.reload(); 
}
)
</script>
</HEAD>
<body >

	<div class="bg">
		<div class="bg_left"></div>
		<div class="bg_right">
			<div class="error"></div>
			<div class="text">系统内部错误,请重新登录!<br><!--s:property value="exception"/--></div>
			<div class="layout_button">
			</div>
		</div>
	</div>
</body>
</html>
