<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
	<body>
		<c:if test="${suffix=='_processphotos' }">
			<logic:notEmpty name="FAULT_PROCESS_PHOTOS_LIST" scope="session">
				<div id="_processphotos" class="pikachoose">
					<ul id="pikame_processphotos" class="jcarousel-skin-pika">
						<c:forEach var="oneImage"
							items="${sessionScope.FAULT_PROCESS_PHOTOS_LIST}">
							<c:set var="fileId" value="${oneImage.fileid}"></c:set>
							<c:set var="savePath" value="${oneImage.savepath}"></c:set>
							<li>
								<img src="${ctx }/imageServlet?imageId=${fileId }" />
							</li>
						</c:forEach>
					</ul>
				</div>
				<script type="" language="javascript">
				var contextPath="${ctx}";
				jQuery(document).ready(function (){
					jQuery("#pikame_processphotos").PikaChoose({carousel:true});
				});
				</script>
			</logic:notEmpty>
			<logic:empty name="FAULT_PROCESS_PHOTOS_LIST" scope="session">
			没有可显示的内容。
			</logic:empty>
		</c:if>
		<c:if test="${suffix=='_faultphotos' }">
			<logic:notEmpty name="FAULT_PHOTOS_LIST" scope="session">
				<div id="_faultphotos" class="pikachoose">
					<ul id="pikame_faultphotos" class="jcarousel-skin-pika">
						<c:forEach var="oneImage"
							items="${sessionScope.FAULT_PHOTOS_LIST}">
							<c:set var="fileId" value="${oneImage.fileid}"></c:set>
							<c:set var="savePath" value="${oneImage.savepath}"></c:set>
							<li>
								<img src="${ctx }/imageServlet?imageId=${fileId }" />
							</li>
						</c:forEach>
					</ul>
				</div>
				<script type="" language="javascript">
				var contextPath="${ctx}";
				jQuery(document).ready(function (){
					jQuery("#pikame_faultphotos").PikaChoose({carousel:true});
				});
				</script>
			</logic:notEmpty>
			<logic:empty name="FAULT_PHOTOS_LIST" scope="session">
			没有可显示的内容。
			</logic:empty>
		</c:if>
	</body>
</html>
