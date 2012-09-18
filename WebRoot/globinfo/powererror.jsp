<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="com.cabletech.commons.config.*"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
<META HTTP-EQUIV="expires" CONTENT="0">
<style type="text/css">

body {margin: 0 auto;}
.bg{width:510px; height:222px; background-image:url(${ctx}/images2/bg/bg_center.jpg); margin-left:20%; margin-right:auto; margin-top:10%;}
.bg_left{ width:11px; height:222px; float:left; background-image:url(../images2/bg/bg_left.jpg);}
.bg_right{ width:495px;height:222px; background:url(${ctx}/images2/bg/bg_right.jpg);background-repeat: no-repeat;background-position: right; float:right;}
.hint{ width:130px; height:130px; background-image:url(${ctx}/images2/bg/State_Tips.jpg); float:left; margin-left:20px; margin-top:20px; background-repeat:no-repeat;}
.error{ width:130px; height:130px; background-image:url(${ctx}/images2/bg/State_Mistake.jpg); float:left; margin-left:20px; margin-top:20px; background-repeat:no-repeat;}
.warning{ width:130px; height:130px; background-image:url(${ctx}/images2/bg/State_Warning.jpg); float:left; margin-left:20px; margin-top:20px; background-repeat:no-repeat;}
.success{ width:130px; height:130px; background-image:url(${ctx}/images2/bg/State_Success.jpg); float:left; margin-left:20px; margin-top:20px; background-repeat:no-repeat;}
.text{ width:320px; height:50px; font-size:16px; color:#FF0000; font-family:"黑体";  line-height:28px;margin-left:150px; margin-top:65px;  }
.layout_button{ padding-top:35px; padding-left:350px; background-repeat:no-repeat;}
.button{ width:82px; height:31px;background:url(${ctx}/images2/bg/button.jpg); border:none;}
</style>
</HEAD>


<body >
<div class="bg">
		<div class="bg_left"></div>
		<div class="bg_right">
			<div class="warning"></div>
			<div class="text">对不起,你的权限不够!!!</div>
			<div class="layout_button">
				 <input name="Submit" type="submit" class="button" value="" style="cursor:hand" onClick="javascript:history.go(-1);">
			</div>
		</div>
	</div>

</body>
</html>
