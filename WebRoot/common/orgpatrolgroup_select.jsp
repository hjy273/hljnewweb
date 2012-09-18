<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ page language="java" pageEncoding="GBK"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>��ѡ��</title>
<link href="${ctx}/js/jQuery/layout/jquery.layout.css" rel="stylesheet"
	type="text/css">
<link href="${ctx }/js/jQuery/ztree/css/zTreeStyle/zTreeStyle.css"
	type="text/css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css" />	
<script type="text/javascript" src="${ctx}/js/jQuery/jquery-1.7.1.min.js"></script>	
<script type="text/javascript"
	src="${ctx}/js/jQuery/layout/jquery.layout-latest.js"></script>
<script type="text/javascript"
	src="${ctx}/js/jQuery/ztree/jquery.ztree.all-3.1.min.js"></script>	
<script type="text/javascript">
	//��������Ա����ʽ	
var setting = {
				view: {
						dblClickExpand: false,
						showLine: false,
						showIcon: true
						},
						data: {
								simpleData: {
								enable: true,
								idKey:"ID",
								pIdKey:"PARENTID",
								rootPId:"root"	
							},
							key: {
								title: "NAME",
								name: "NAME"
								
							}
						},
						check: {
							enable: true,
							chkStyle: "radio",
							radioType: "all"
						},
						callback: {
							onClick: onClick
						}
					};
		jQuery(function(){
			 //Ѳ����������
			bodyLayout=jQuery('body').layout({ applyDefaultStyles: true ,south__resizable:false,south__spacing_open:0});
			 //��ȡѲ������
			 zTree=jQuery.fn.zTree.init(jQuery("#patrolGrouptree"), setting, ${patrolgrouptree});
			 zTree.expandAll(true); 
		})
		//�������ڵ�չ��
		function onClick(e,treeId, treeNode) {
			var zTree = jQuery.fn.zTree.getZTreeObj("patrolGrouptree");
			zTree.expandNode(treeNode);
		}
		//ѡ��ڵ�
		function save() {
			var zTree = jQuery.fn.zTree.getZTreeObj("patrolGrouptree");
			//��ȡѡ�нڵ�
			var checknodes = zTree.getCheckedNodes(true);
			var patrolGroupnodes= new Array();
			if(checknodes){
				for(var i=0;i<checknodes.length;i++){
					if(checknodes[i].OBJTYPE==="PATROLMAN"){
						patrolGroupnodes.push(checknodes[i]);
					}
				}
				if(patrolGroupnodes.length===0){
					alert("��ѡ��Ѳ���飡");
					}
				else{
					window.returnValue = patrolGroupnodes;
					window.close();
				}
				
			}else{
				alert("��ѡ��Ѳ���飡");
			}
			
		}
		//�رմ���
		function quit(){
			window.close();
		}
</script>
</head>
<body>
	<div class="ui-layout-center">
		<div class="title_bg">
			<div id="title" class="title">ѡ��Ѳ����</div>
		</div>
		<div id="patrolGrouptree" class="ztree" style="margin-top:0;"></div>
	</div>
	<div class="ui-layout-south" style="text-align: center;">
		<input name="btnSave" value="ѡ��" type="button" class="button"
			onclick="save();" /> <input name="btnReset" value="�ر�"  type="button"
			class="button" onclick="quit();" />
	</div>
</body>
</html>