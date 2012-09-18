<html>
<head>
<link type="text/css" rel="stylesheet" href="${ctx}/frames/bj/css/load.css" />
<link type="text/css" rel="stylesheet"
	href="${ctx}/frames/bj/js/jscal2/src/css/jscal2.css" />
	
<link type="text/css" rel="stylesheet"
	href="${ctx}/frames/bj/js/jscal2/src/css/cabletech/gold.css" />


<style type="text/css">
</style>
		<script src="${ctx}/frames/bj/js/jscal2/src/js/jscal2.js"></script>
<script src="${ctx}/frames/bj/js/jscal2/src/js/lang/cn.js"></script>
<script type="text/javascript"> 
    var DATE_INFO = {${meet_string}};
    <%
    System.out.println(request.getAttribute("meet_string"));
    %>
    function getDateInfo(date, wantsClassName) {  
      var as_number = Calendar.dateToInt(date);  
      return DATE_INFO[as_number];  
    }; 
    function showPopWindow(url){
		myleft=(screen.availWidth-650)/2;
		mytop=100
		mywidth=720;
		myheight=450;
    	window.open(url,"","height="+myheight+",width="+mywidth
    	    	+",status=1,resizable=no,toolbar=no,menubar=no,location=no,scrollbars=yes,top="
    	    	+mytop+",left="+myleft+",resizable=yes");
    }

    var LEFT_CAL = Calendar.setup( { 
        animation : false,
		cont : "cont",  
		selectionType : Calendar.SEL_SINGLE,  
		bottomBar : false,		 
		titleFormat : "%Y"+String.fromCharCode(24180)+" %B",  
		dateInfo : getDateInfo,
		onSelect:function() { 
			var url="${ctx}/NoticeAction.do?method=queryNoticeMeet&&isissue=y&&type=ª·“È&&rnd="+Math.random();
			var date = this.selection.get(); 
			if(typeof(DATE_INFO[date])!="undefined"&&DATE_INFO[date]!=null){
				date = Calendar.intToDate(date); 
				date = Calendar.printDate(date, "%Y-%m-%d"); 
				url+="&&meetTime="+date;
				showPopWindow(url);

			}
          }
        }); 
</script> 
</head>
<body>
<div id="cont" class="Agenda"></div>
</body>
</html>