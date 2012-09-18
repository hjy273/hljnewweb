<%@ page contentType="text/html; charset=GBK" %>
<%@include file="/common/header.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>巡检系统</title>

<link href="${ctx}/css/divstyle.css" rel="stylesheet" type="text/css">
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<script type='text/javascript' src='${ctx}/js/highchart/highcharts.js'></script>
<!-- <script type='text/javascript' src='${ctx}/js/mainpage.js'></script> -->

<style type="text/css">
.Frame { width: 98%; margin-left: 10px;margin-right: 10px; }
.Frame ul {  margin:auto; }
.Frame ul li { float: left; width: 130px; display: inline; }
.work_content{ width:80%;  height:90px;  border:#F0EDC6 solid 1px; background-color:#FAFAF2; margin:10px;}
.text{ width:100%; margin:auto; font-size:14px; color:#636306; text-align:center; margin-top:6px;cursor: pointer;}
.work_content ul{ margin:auto; height:60px;}
.work_content ul li{ margin:auto; list-style:none; overflow:hidden; float:left; width:110px; display:inline;}
.text_default{text-align:center;font-size:12px;	color:#636306;	text-decoration: none;}
.text_red{text-align:center;color:red ;font-size:12px;text-decoration: none;}
.text a{color:#636306;text-decoration: none;	}
.img{ width:48px; height:48px; margin:0px auto; margin-top:3px;margin-left:20px;}
legend{ color:#3366ff; font-size:14px;font-weight: bold;}
</style>

<script type="text/javascript">

Ext.onReady(function(){
	
	var width = Ext.getBody().getWidth()-10;

	var type = ${type};

	var typeIncode ="C31" ;

	if(type == "3"){
		typeIncode="C33";
	}

	if(type == "4"){
		typeIncode="C32";
	}
	
    var p_station_todo = new Ext.form.FieldSet({
        title: '待办任务',
        collapsible:true,
        renderTo: 'p_station_todo',
        autoHeight:true,
        width:width,
        html:'<div class="Frame"><fieldset><legend></legend><ul><c:forEach var="vMenu" items="${ls}"><li><div class="work_content" ><div class="img"><img src="${ctx}/${vMenu.imgurl }" /></div><div class="text" onclick=toUrl("${vMenu.hrefurl }")>【${vMenu.lablename}】</div></li></c:forEach></ul></fieldset></div>'
    });
    
    var p_station_done = new Ext.form.FieldSet({
        title: '计划执行情况',//collapsed :true ,
        collapsible:true,
        renderTo: 'p_station_done',
        width:width,
        autoHeight:true,
        html:'<iframe src="${ctx}/TowerPatrolinfo.do?method=doScheduleQueryForDesktop&flag=1&type='+typeIncode+'" id="xjjdjz" width="100%" height="100%"  frameborder="no"></iframe>'
    });
    /*
    var p_station_bug = new Ext.Panel({
        title: '当前故障',collapsed :true ,
        collapsible:true,
        autoexpend:false,
        renderTo: 'p_station_bug',
        width:width
    });
    
    var p_station_hiddenbug = new Ext.Panel({
        title: '隐患问题情况',collapsed :true ,
        collapsible:true,
        renderTo: 'p_station_hiddenbug',
        width:width
    });   
    
    var p_station_watch = new Ext.Panel({
        title: '当前盯防情况 ',collapsed :true ,
        collapsible:true,
        renderTo: 'p_station_watch',
        width:width
    });
	*/
    
});
function toUrl (url) {
    if(''==url){
      return;
    }
	window.location.href = url;
};
 
</script>

</head>

<body style="width:100%;margin:0px;padding:0px;">
	<div id="p_station_todo" style="margin-left:10px;margin-top:5px;">
		
	
	</div>
	
	<div id="p_station_done" style="margin-left:10px;margin-top:5px;">
	</div>
	
	<div id="p_station_bug" style="margin-left:10px;margin-top:5px;">
	</div>
	
	<div id="p_station_hiddenbug" style="margin-left:10px;margin-top:5px;">
	</div>
	
	<div id="p_station_watch" style="margin-left:10px;margin-top:5px;">
	</div>
</body>
</html>