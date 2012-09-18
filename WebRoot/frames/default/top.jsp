<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript"
	src="${ctx}/js/jQuery/jquery-1.7.1.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/jQuery/jtimer/jquery.timer.js"></script>
<script type="text/javascript">
	jQuery(function(){
					$('#timer').timer({format: "yy��m��dd��  HH:MM:ss"});
				});

				function help(){
				    window.open ("${ctx}/frames/default/help.jsp");
				}
				function updatepws(){
				    window.open ("${ctx}/SystemManage/updatePsw.jsp");
				}
</script>
<link href="css/style.css" rel="stylesheet" type="text/css">
	<div class="head_top">
		<div class="head_topleft_bg">
			<div class="head_logo"></div>
		</div>
		<div class="head_System_Name">�����ά�ۺϹ���ϵͳ</div>
		<div class="head_text_bg"></div>
		<div class="head_Button">
			<ul>
				<li>
					<div class="pass">
						<a href="javascript:updatepws();">�޸�����</a>
					</div>
				</li>
				<li>
					<div class="help">
						<a href="javascript:help();">����</a>
					</div>
				</li>
				<li>
					<div class="quit">
						<a href="javascript:exitSystem();">�˳�</a>
					</div>
				</li>
			</ul>
		</div>
	</div>
	<div class="head_banner">
		<div class="menu">
			<ul>
				<c:forEach var="item" items="${sessionScope.indexmenu}">
					<c:choose>
						<c:when test="${item.TEXT=='֪ʶ��̳'}">
							<li><a id='${item.ID }' href="${item.HREFURL }" target="_blank">֪ʶ��̳</a>
							</li>
						</c:when>
						<c:otherwise>
							<li><a id='${item.ID }' href="#"
									onclick="gotoMainUrl('${item.ID }','${item.HREFURL }','${item.TEXT}')">${item.TEXT}</a>
								</li>
						</c:otherwise>
					</c:choose>
				</c:forEach>

			</ul>
		</div>
	</div>