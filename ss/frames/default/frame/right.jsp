<%@ page language="java" pageEncoding="UTF-8"%>

 <script type="text/javascript" language="javascript">
	function addressList(){
		   var name = document.getElementById("user_name").value;
		   var departName = document.getElementById("depart_name").value;
		   var mobile = document.getElementById("user_mobile").value;
		   URL="${ctx}/addressListAction.do?method=showAddrDetailList&&depart_name="+departName+"&&user_name="+name+"&user_mobile="+mobile;
		   myleft=(screen.availWidth-650)/2;
		   mytop=100
		   mywidth=720;
		   myheight=450;
		   window.open(URL,"","height="+myheight+",width="+mywidth+",status=1,resizable=no,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft+",resizable=yes");
	}

 </script>
<div class="Framework">
<div class="title_bg">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td><img src="../images/txl.jpg" /></td>
		<td>&nbsp;</td>
		<td align="right"><img src="../images/right.jpg" /></td>
	</tr>
</table>
</div>
<div class="Contacts">
<div class="Contacts_left">
<form action="" method="get">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="16%">单 位：</td>
		<td width="70%"><input type="text" name="depart_name" id="depart_name"
			class="Contacts_input" /></td>
		<td width="14%">&nbsp;</td>
	</tr>
	<tr>
		<td width="16%">姓 名：</td>
		<td width="70%"><input type="text" name="user_name" id="user_name"
			class="Contacts_input" /></td>
		<td width="14%">&nbsp;</td>
	</tr>
	<tr>
		<td>电 话：</td>
		<td><input type="text" name="user_mobile" id="user_mobile"
			class="Contacts_input" /></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td align="left">
		<img src="../images/Search.jpg" style="cursor:hand;" onclick="addressList();">
        </td>
		<td>&nbsp;</td>
	</tr>
</table>
</form> 

</div>
</div>
<span class="l4"></span>
<span class="l3"></span>
<span class="l2"></span>
<span class="l1"></span>
</div>

<br>

<div class="Framework">
<div class="title_bg">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td><img src="../images/jptj.jpg"/></td>
		<td>&nbsp;</td>
		<td align="right"><img src="../images/right.jpg" /></td>
	</tr>
</table>

</div>
<div class="mail">
<iframe id="jinghua"  frameborder="0" src="http://221.179.174.203:8080/bbs/search2.php" style="padding:0px; margin:0px;"  scrolling="no"></iframe>
<!-- 
	如何写精品博文<br>
	故障处理经验方法分享<br>
	怎样提高日常工作的维护效率及质量<br> -->
</div>
<span class="l4"></span>
<span class="l3"></span>
<span class="l2"></span>
<span class="l1"></span>
</div>
