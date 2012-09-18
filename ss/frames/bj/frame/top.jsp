<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<script type="text/javascript" src="${ctx}/js/jquery-1.3.2.min.js"></script>
<link href="../css/header.css" rel="stylesheet" type="text/css" />
<%
com.cabletech.commons.config.GisConInfo gisConfig=com.cabletech.commons.config.GisConInfo.newInstance();
 %>
<script>
$(document).ready(function(){ 
    $("#td_1").click(function(){ 
         	   $('.naw li').removeClass('hovertad');
         	   $(this).addClass('hovertad');
         	   window.parent.bodyFrame.location='main.jsp';
    });
    $("#td_2").click(function(){  
         	   $('.naw li').removeClass('hovertad');
         	   $(this).addClass('hovertad');
         	   window.parent.bodyFrame.location='my_work.jsp';
    });
    $("#td_3").click(function(){  
         	   $('.naw li').removeClass('hovertad');
         	   $(this).addClass('hovertad');
         	   window.open("http://<%=gisConfig.getBbsServerIp()%>:<%=gisConfig.getBbsServerPort()%>/bbs","知识交流");
         	  // window.parent.bodyFrame.location='about:blank';
    });  
});  
function exitSystem(){
	if(confirm("您确实要退出系统吗？")){
		self.location="${ctx}/frames/login.do?method=relogin";
		top.location='${ctx}';
	}
}
function help(){
	URL="${ctx}/frames/bj/help.jsp";
	   myleft=(screen.availWidth-1024)/2;
	   mytop=100;
	   mywidth=1024;
	   myheight=768;
	 window.open(URL,"read_news","height="+myheight+",width="+mywidth+",status=1,resizable=no,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft+",resizable=yes");
}
$(document).ready(function(){
    $(window).unload( function () { 
        //alert("Bye now!"); 
        self.location = "${ctx}/login.do?&method=logout";
    } );
});


</script>
</head>
<body onload="window.parent.bodyFrame.location='main.jsp';">
	<div class="top">
		<div class="top_left"></div>
		<div class="logo_${LogoImg}"></div>
		<div class="System_Name">${AppName}</div>
		<div class="Sub_system_name">${FirstParty }</div>
		<div class="top_right_${LogoImg}"></div>
	</div>
	<div class="Login_Information"></div>
<div class="Login_Information">
这是您第${sessionScope.CURRENT_USER_LOGIN_TIMES }次登陆系统，上次登录时间为
<c:if test="${sessionScope.CURRENT_USER_LAST_LOGIN_TIME!=null&&sessionScope.CURRENT_USER_LAST_LOGIN_TIME!='' }">${sessionScope.CURRENT_USER_LAST_LOGIN_TIME}</c:if>
<c:if test="${sessionScope.CURRENT_USER_LAST_LOGIN_TIME==null||sessionScope.CURRENT_USER_LAST_LOGIN_TIME=='' }">无</c:if>
</div>
<div class="banner">
    	<div class="banner_menu">
				<div id="td_" class="td_">
					<ul class="naw" >
						<li id="td_1" class="hovertad" >
							<div id="tb1">首 &nbsp; 页</div>
							
						</li>
						<li id="td_2" class="dj">
							<div id="tb2">我的工作</div>
							
						</li> 
						<c:if test="${REGION_ROOT =='110000' }">
						<li id="td_3" class="dj">
							<div id="tb3">知识管理</div>
						</li>
						</c:if>
					</ul>
				</div>
  		 </div>
         
         <div class="banner_right">
           <table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="banner_font">
             <tr>
               <td align="center">
               <script language=JavaScript>
				function Year_Month(){ 
					var now = new Date(); 		
					var mm = now.getMonth(); 
					var mmm=new Array();
					mmm[0]="January";
					mmm[1]="February ";
					mmm[2]="March";
					mmm[3]="April";
					mmm[4]="May";
					mmm[5]="June";
					mmm[6]="July";
					mmm[7]="August";
					mmm[8]="September";
					mmm[9]="October";
					mmm[10]="November";
					mmm[11]="December";
					mm=mmm[mm];
					return(mm ); 
				}
				function thisYear(){ 
					var now = new Date(); 
					var yy = now.getYear();
					yy = (yy<1900?(1900+yy):yy); 
					return(yy ); 
				}
				function Date_of_Today(){ 
					var now = new Date(); 
					return(now.getDate()); 
				}
				function thisMonth(){
					var now  = new Date();
					var mon = now.getMonth();
					return (mon+1);
				}
				function thisDay(){
					var now = new Date();
					var day = now.getDay();
					var ddd=new Array();
			        ddd[0]="星期日";
			        ddd[1]="星期一";
			        ddd[2]="星期二";
			        ddd[3]="星期三";
			        ddd[4]="星期四";
			        ddd[5]="星期五";
			        ddd[6]="星期六";
					day = ddd[day] ;
					return (day);
				}
				
				function CurentTime(){ 
					var now = new Date(); 
					var hh = now.getHours(); 
					var mm = now.getMinutes(); 
					var ss = now.getTime() % 60000; 
					ss = (ss - (ss % 1000)) / 1000; 
					var clock = hh+':'; 
					if (mm < 10) clock += '0'; 
						clock += mm+':'; 
					if (ss < 10) clock += '0'; 
						clock += ss; 
					return(clock); 
				} 
				function refreshCalendarClock(){ 
					document.getElementById("calendarClock1").innerHTML =thisYear()+"年"; 
					document.getElementById("calendarClock2").innerHTML =thisMonth()+"月"; 
					document.getElementById("calendarClock3").innerHTML =Date_of_Today()+"日"; 
					document.getElementById("calendarClock4").innerHTML =thisDay();
					document.getElementById("calendarClock5").innerHTML =CurentTime(); 
				}
				//document.write('<font id="calendarClock1" ></font>');
				//document.write('<font id="calendarClock2" ></font>');
				//document.write('<font id="calendarClock3" ></font>&nbsp;');
				//document.write('<font id="calendarClock4" ></font>&nbsp;');
				//document.write('<font id="calendarClock5" ></font>');
				setInterval('refreshCalendarClock()',1000);
			   </script>
			   <font id="calendarClock1" ></font><font id="calendarClock2" ></font><font id="calendarClock3" ></font>&nbsp;
			   <font id="calendarClock4" ></font>&nbsp;
			   <font id="calendarClock5" ></font>
               </td>
               <td><img src="../images/arrow.jpg" width="19" height="31"/></td>
               <td>${LOGIN_USER.userName}</td>
               <td><img src="../images/help.jpg" width="19" height="31" /></td>
               <td><a href="javascript:help();" style="text-decoration:none"><font color="#FFFFFF">帮助</font></a></td>
               <td><img src="../images/back.jpg" width="19" height="31" /></td>
               <td><a href="javascript:exitSystem();" style="text-decoration:none"><font color="#FFFFFF">退出</font></a></td>
             </tr>
           </table>
         </div>
</div>

</body>
 </html>