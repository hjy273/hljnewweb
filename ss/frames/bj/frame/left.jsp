<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
		function getMeetList(){
		     var pars = Math.random();
		     var url='${ctx}/frames/notice.do?method=showMeetCalendar&&par='+pars+"&&rnd="+Math.random();
			var myAjax=new Ajax.Updater(
				"meetCalendarDiv",url,{
					method:"post",
					evalScripts:true
				}
			);
		 }
		function getUserLinkList(){
		    $("linkListDiv").innerHTML = "数据加载中........<img src='${ctx}/images/onloading.gif' border='0'/>";
		     var pars = Math.random();
		     new Ajax.Request('${ctx}/user_link.do?method=queryLatestList',
		      {
		        method:"post",
		        parameters:pars,
		        evalScripts:true,
		        onSuccess: function(transport){
		          var response = transport.responseText || "";
		          $("linkListDiv").innerHTML = response;
		        },
		        onFailure: function(){  $("linkListDiv").innerHTML = "加载失败。。。。。"; }
		      });
		 }
		function getUserMailList(){
		    $("mailListDiv").innerHTML = "数据加载中........<img src='${ctx}/images/onloading.gif' border='0'/>";
		     var pars = Math.random();
		     new Ajax.Request('${ctx}/user_mail.do?method=queryLatestList',
		      {
		        method:"post",
		        parameters:pars,
		        evalScripts:true,
		        onSuccess: function(transport){
		          var response = transport.responseText || "";
		          $("mailListDiv").innerHTML = response;
		        },
		        onFailure: function(){  $("mailListDiv").innerHTML = "加载失败。。。。。"; }
		      });
		 }
</script>
<!-- 会议日程 -->
<div class="Framework">
	<div class="title_bg">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<img src="../images/Agenda.jpg" />
				</td>
				<td>
					&nbsp;
				</td>
				<td align="right">
					<img src="../images/right.jpg" />
				</td>
			</tr>
		</table>
	</div>
	<div id="meetCalendarDiv">
	</div>
	<!-- 绘制下方圆角 -->
	<span class="l4"></span><span class="l3"></span><span class="l2"></span><span
		class="l1"></span>
</div>
<br>
<!-- 常用链接 -->
<div class="Framework">
	<div class="title_bg">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<img src="../images/link.jpg" />
				</td>
				<td>
					&nbsp;
				</td>
				<td align="right">
					<img src="../images/right_arow.jpg" />
				</td>
			</tr>
		</table>
	</div>
	<div class="link" id="linkListDiv">
		<!-- 
		· 中国移动通信集团 [2010-11-29]
		<br />
		· 中国移动通信集团北京有限公司 [2010-11-29]
		<br />
		· 中国移动京有限 [2010-11-29]
		<br />
		· 中国团北京有限 [2010-11-29]
		<br />
		<div class="link_bg"></div>
		 -->
	</div>
	<span class="l4"></span><span class="l3"></span><span class="l2"></span><span
		class="l1"></span>
</div>
<br>
<!-- 我的邮箱  -->
<div class="Framework">
	<div class="title_bg">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<img src="../images/mail.jpg" />
				</td>
				<td>
					&nbsp;
				</td>
				<td align="right">
					<img src="../images/right_arow.jpg" />
				</td>
			</tr>
		</table>
	</div>
	<div class="mail" id="mailListDiv">
		<!-- 
                     · zcw@cabletech.com.cn <br>
                     · zcw832043@163.com <br>
                     · zcw832043@163.com <br>
                     · zcw@cabletech.com.cn <br>
        -->
	</div>
	<span class="l4"></span><span class="l3"></span><span class="l2"></span><span
		class="l1"></span>
	<script type="text/javascript">
	getUserMailList();
	getUserLinkList();
	getMeetList();
	</script>
</div>

