<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
com.cabletech.commons.config.GisConInfo gisConfig=com.cabletech.commons.config.GisConInfo.newInstance();
 %>
<!-- 待办工作数量 -->
<c:if test="${isdowork=='1'}">
	<c:forEach var="vMenu" items="${sessionScope.MENU_FIRSTMENU}">
		<c:if test="${vMenu.waitHandleNumber != 0 }">
		<a href="#" style="TEXT-DECORATION: none"
			onclick="toUrl('${vMenu.id }','${vMenu.subMenuId }','${vMenu.lablename }');">【${vMenu.lablename
			}】</a>&nbsp;&nbsp;......................................&nbsp;&nbsp;${vMenu.waitHandleNumber
			}项待办
			<br />
		</c:if>
	</c:forEach>
</c:if>
<!-- 我的工作 -->
<c:if test="${isdowork=='0'}">
	
	<div class="Frame">
	<fieldset>
 	<legend>现场</legend>
	<ul>
	<c:forEach var="vMenu" items="${sessionScope.MENU_FIRSTMENU}">
	<c:if test="${vMenu.id==1||vMenu.id==2||vMenu.id==22||vMenu.id==26||vMenu.id==21}">
		<c:if test="${vMenu.id != 1}">
		<li>
	        <div class="work_content" >
	        	<div class="img"><img src="${ctx}${vMenu.imgurl}" /></div>
	            <div class="text" onclick="toUrl('${vMenu.id }','${vMenu.subMenuId }','${vMenu.lablename }');">【${vMenu.lablename}】</div>
	            <c:if test="${vMenu.waitHandleNumber > 0}">
	            <div class="text_red">${vMenu.waitHandleNumber}项待办</div>
	            </c:if>
	            <c:if test="${vMenu.waitHandleNumber <= 0}">
	            <div class="text_default">${vMenu.waitHandleNumber}项待办</div>
	            </c:if>
	        </div>
	    </li>
		</c:if>
		<c:if test="${vMenu.id == 1}"><!-- 巡检监测 -->
		<li>
	        <div class="work_content" >
	        	<div class="img"><img src="${ctx}${vMenu.imgurl}" /></div>
	            <div class="text" ><a href="/<%=gisConfig.getServerapp() %>/<%=gisConfig.getGisUrl() %>${LOGIN_USER.userID}&init=yes&regionID=${LOGIN_USER.regionid}"
										target="_blank">【${vMenu.lablename}】</a></div>
				<div class="text_default"></div>
	            
	        </div>
	    </li>
		</c:if>
	</c:if>
	</c:forEach>
	</ul>
	</fieldset>
	
	<fieldset>
 	<legend>综合</legend>
	<ul>
	<c:forEach var="vMenu" items="${sessionScope.MENU_FIRSTMENU}">
	<c:if test="${vMenu.id==12||vMenu.id==27||vMenu.id==25||vMenu.id==24||vMenu.id==23||vMenu.id==28||vMenu.id==31||vMenu.id==8||vMenu.id==30||vMenu.id==11||vMenu.id==29}">
	<li>
		<div class="work_content" >
	    	<div class="img"><img src="${ctx}${vMenu.imgurl}" /></div>
	        <div class="text" onclick="toUrl('${vMenu.id }','${vMenu.subMenuId }','${vMenu.lablename }');">【${vMenu.lablename}】</div>
	        <c:if test="${vMenu.waitHandleNumber > 0}">
	        <div class="text_red">${vMenu.waitHandleNumber}项待办</div>
	        </c:if>
	        <c:if test="${vMenu.waitHandleNumber <= 0}">
	        <div class="text_default">${vMenu.waitHandleNumber}项待办</div>
	        </c:if>
	    </div>
	</li>
	</c:if>
	</c:forEach>
	</ul>
	</fieldset>
	
	<fieldset>
 	<legend>考核</legend>
	<ul>
	<c:forEach var="vMenu" items="${sessionScope.MENU_FIRSTMENU}">
	<c:if test="${vMenu.id==32||vMenu.id==33||vMenu.id==34||vMenu.id==35}">
	<li>
		<div class="work_content" >
	    	<div class="img"><img src="${ctx}${vMenu.imgurl}" /></div>
	        <div class="text" onclick="toUrl('${vMenu.id }','${vMenu.subMenuId }','${vMenu.lablename }');">【${vMenu.lablename}】</div>
	        <c:if test="${vMenu.waitHandleNumber > 0}">
	        <div class="text_red">${vMenu.waitHandleNumber}项待办</div>
	        </c:if>
	        <c:if test="${vMenu.waitHandleNumber <= 0}">
	        <div class="text_default">${vMenu.waitHandleNumber}项待办</div>
	        </c:if>
	    </div>
	</li>
	</c:if>
	</c:forEach>
	</ul>
	</fieldset>
	
	<fieldset>
 	<legend>其他</legend>
	<ul>
	<c:forEach var="vMenu" items="${sessionScope.MENU_FIRSTMENU}">
	<c:if test="${vMenu.id==10||vMenu.id==7}">
	<li>
		<div class="work_content" >
	    	<div class="img"><img src="${ctx}${vMenu.imgurl}" /></div>
	        <div class="text" onclick="toUrl('${vMenu.id }','${vMenu.subMenuId }','${vMenu.lablename }');">【${vMenu.lablename}】</div>
	        <c:if test="${vMenu.waitHandleNumber > 0}">
	        <div class="text_red"></div>
	        </c:if>
	        <c:if test="${vMenu.waitHandleNumber <= 0}">
	        <div class="text_default"></div>
	        </c:if>
	    </div>
	</li>
	</c:if>
	</c:forEach>
	</ul>
	</fieldset>
	</div>
</c:if>

