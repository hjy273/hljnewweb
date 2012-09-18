<%@ page language="java" pageEncoding="UTF-8"%>
<div style="border: 0px dotted; width: 100%">
	<div class="top">
		<div class="top_left"></div>
		<div class="logo"></div>
		<div class="System_Name">${AppName}</div>
		<div class="Sub_system_name">${FirstParty }</div>
		<div class="top_right"></div>
	</div>
	<div class="Login_Information"></div>
	<script type="text/javascript">
	
	</script>
	<div class="banner">
		<div class="banner_menu">
			<div id="menu">
				<ul>
					<li><a href="#" onclick="gotoUrl('index')">首页</a>
					</li>
					<li><a href="#" onclick="gotoUrl('mywork')">我的工作</a>
					</li>
					<li><a href="#">维护专业</a>
						<ul>
							<li><a href="#" onclick="gotoUrl('1')">线路维护</a>
							</li>
							<li><a href="#" onclick="gotoUrl('2')">基站维护</a>
							</li>
							<li><a href="#" onclick="gotoUrl('3')">铁塔维护</a>
							</li>
						</ul></li>
					<li><a href="#">管理模块</a>
						<ul>
							<li><a href="#" onclick="gotoUrl('8')">代维管理</a></li>
							<li><a href="#">维护资源</a>
								<ul>
									<li><a href="#" onclick="gotoUrl('71')">线路资源</a>
									</li>
									<li><a href="#" onclick="gotoUrl('72')">无线资源</a>
									</li>
								</ul></li>
							<li><a href="#"  onclick="gotoUrl('9')">系统管理</a>
							</li>
						</ul></li>
					<li><a href="#" onclick="gotoUrl('vrp')">基站全景图</a></li>
				</ul>
			</div>
		</div>

		<div class="banner_right">
           <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="banner_font">
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
			
				setInterval('refreshCalendarClock()',1000);
			   </script>
			   <font id="calendarClock1" ></font><font id="calendarClock2" ></font><font id="calendarClock3" ></font>&nbsp;
			   <font id="calendarClock4" ></font>&nbsp;
			   <font id="calendarClock5" ></font>
               </td>
               <td><img src="./frames/default/images/arrow.jpg" width="19" height="31"/></td>
               <td>${user.userName}</td>
               <td><img src="./frames/default/images/help.jpg" width="19" height="31" /></td>
               <td><a href="javascript:help();" style="text-decoration:none"><font color="#FFFFFF">帮助</font></a></td>
               <td><img src="./frames/default/images/back.jpg" width="19" height="31" /></td>
               <td><a href="javascript:exitSystem();" style="text-decoration:none"><font color="#FFFFFF">退出</font></a></td>
             </tr>
           </table>
         </div>
	</div>
</div>