<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>首页</title>
<link href="${ctx}/frames/default/css/style.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/js/jQuery/layout/jquery.layout.css" rel="stylesheet"
	type="text/css" />
<link
	href="${ctx }/js/jQuery/jqueryui/css/redmond/jquery-ui-1.8.16.custom.css"
	type="text/css" rel="stylesheet" />
<link href="${ctx }/js/jQuery/ztree/css/zTreeStyle/zTreeStyle.css"
	type="text/css" rel="stylesheet" />
<script type="text/javascript"
	src="${ctx}/js/jQuery/jquery-1.7.1.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/jQuery/layout/jquery.layout-latest.js"></script>
<script type="text/javascript"
	src="${ctx}/js/jQuery/jqueryui/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/jQuery/ztree/jquery.ztree.all-3.1.min.js"></script>
<script type="text/javascript">
		//设置ZTREE模式为PID形式,不显示树线
		var setting = {
				view: {
					showLine: false
				},
				data:{
					simpleData: {
						enable: true,
						idKey:"ID",
						pIdKey:"PARENTID",
						rootPId:"root"	
					},
					key: {
						title: "TEXT",
						name: "TEXT"
						
					}
				},
				callback: {
					onClick:pageSkip
				}
				
			};
	jQuery(function(){
		  //使用折叠样式
	 	accordionObj=jQuery("#accordion").accordion({
	 				autoHeight:false,
	 				header: 'h3',
	 				navigation:true,
	 				active: 0,
	 				collapsible: true ,
	 				change:function(event, ui){
	 					//获取菜单折叠组件头ID
	 					var currentHeaderID=ui.newHeader.find("a").attr("id");
	 					if(currentHeaderID){
	 						//获取菜单ID
	 						var menuid=currentHeaderID.replace("accord","");
	 						var treeid="ztree"+menuid;
	 						//根据菜单ID获取是否有菜单
	 						var treeobj= jQuery.fn.zTree.getZTreeObj(treeid);
	 						//如果没有从服务器上获取菜单
	 						if(!treeobj){
	 							getMenuTree(menuid,treeid);
	 						}
	 					}
	 				}
	 			});
	     //左右布局
		jQuery('body').layout({ applyDefaultStyles: true});
	 	var actiontreeid=accordionObj.find('.ui-accordion-content-active').attr('id');
	    //如果不为空
	 	if(!!actiontreeid){
	 		var menuid=actiontreeid.replace("ztree","");
	 		getMenuTree(menuid,actiontreeid);
	 	}
	    });
	    
	  //页面跳转
	  function pageSkip(e, treeId, node) {
		  if(node.HREFURL)
			document.getElementById("mainFrame").src = node.HREFURL; 
		}
	  function loadingtext(id,gv){
			jQuery("<div class='loading ui-state-default ui-state-active' style='margin:0' id='load_"+id+"'>读取中...</div>").appendTo(gv);
		}
	   //生成菜单数
	  function getMenuTree(menuid,treeid){
		  jQuery.ajax({
				url : '${ctx}/desktop/leftNavigateAction_getmenutree.jspx?menuid='+menuid,
				dataType : "json",
				type:'GET',
				cache:true,
				async:true,
				beforeSend:function(){
					loadingtext(treeid,jQuery("#"+treeid));
				},
				success : function(result) {
					jQuery("#load_"+treeid).hide();
					if(!!result){
				 	jQuery.fn.zTree.init(jQuery("#"+treeid), setting,result);
					}
				},
				error : function() {
					alert("获取菜单数据异常！");
					jQuery("#load_"+treeid).hide();
				}
			});	
	  }
</script>
</head>
<body>
	<div class="ui-layout-west">
		<div id="accordion">
			<c:forEach var="item" items="${sessionScope.menuheadList}">
				<div class="panel_boder">
					<h3>
						<a href="#" id="accord${item.id}">${item.text}</a>
					</h3>
					<div id="ztree${item.id}" class="ztree"></div>
				</div>
			</c:forEach>
		</div>
	</div>
	<div class="ui-layout-center">
		<iframe id="mainFrame" name="mainFrame" height="100%" frameborder="0"
			width="100%" scrolling="auto" src="/nbspweb${action_url}"></iframe>
	</div>
</body>
</html>
