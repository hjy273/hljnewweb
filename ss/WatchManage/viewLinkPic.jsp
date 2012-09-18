<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.commons.config.GisConInfo;" %>
<%
 String watchAttachs = (String)request.getAttribute("viewwatchattach")+"*";
 GisConInfo gisServer = GisConInfo.newInstance();
 int daysbetwwen = Integer.parseInt(gisServer.getDaysForMMS());
%>
<script language="javascript">
    //var bFlag=true;//全局变量,用于判断是否允许清除文本框内容
    var histroyString = "";
    fileNum=0;
    
    function clearTip(){
        //alert("clear now:" + this.id);
        var oTxt=document.getElementById(this.id);
        //if(bFlag==true && histroyString.indexOf(this.id)==-1){
        if(histroyString.indexOf(this.id)==-1){
           oTxt.value="";
           histroyString = histroyString + this.id;
           //alert(":" + historyString);
           //bFlag=false;
        }else{
           histroyString = histroyString + this.id;
        }
     }
    
	 //脚本生成的删除按  钮的删除动作
	function deleteRow(){
      	//获得按钮所在行的id
     	var rowid = "000";
        rowid = this.id;
        rowid = rowid.substring(1,rowid.length);
         //由id转换为行索找行的索引,并删除
      	for(i =0; i<uploadID.rows.length;i++){
        	if(uploadID.rows[i].id == rowid){
            	uploadID.deleteRow(uploadID.rows[i].rowIndex);
                fileNum--;
            }
        }
    }
    //添加一个新行
    function addRow(){
    	var  onerow=uploadID.insertRow(-1);
        onerow.id = uploadID.rows.length-2;

        var   cell1=onerow.insertCell();
		var   cell2=onerow.insertCell();
        //var   cell3=onerow.insertCell();
        var   cell4=onerow.insertCell();
		//创建一个输入框
        var t1 = document.createElement("input");
        //t1.name = "filename"+onerow.id;//alert(onerow.id);
        t1.name = "uploadFile["+ fileNum + "].file";
		t1.type= "file";
		t1.id = "text" + onerow.id;
		t1.width="300";

        //创建一个文本框，以输入附件说明
        var t2 =document.createElement("input");
        t2.width="160";
        t2.name = "uploadFile["+ fileNum + "].remark";
        t2.id = "rtext" + onerow.id;
        t2.value="请输入附件说明...";
        t2.onfocus=clearTip;
        fileNum++;
        
        //创建删除按钮
        var b1 =document.createElement("button");
        b1.value = "删除";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
        //alert(b1.id + "rrr");
        
        //var ll = document.createElement("label");
        //ll.innerHTML = "说明:";
        //ll.width="50";
        //ll.id = "l" + onerow.id;
        
        
        
        cell1.appendChild(t1);//文字
        cell2.appendChild(b1);
        //cell3.appendChild(ll);//文字
        cell4.appendChild(t2);//文字
        alet("" + t2.id);
	}
	
	function check(){
	    //var abc=document.getElementById("rtext0");
		//alert("" + abc.value);
		//return false;
	}
		
	function addGoBack(){
		var url = "${ctx}/watchAction.do?method=queryWatch";
      	self.location.replace(url);
	}
	function initeDate(){
	    var today = new Date();
	    //today.setFullYear(2008,6,1)
	    var year = today.getYear();
	    var month = today.getMonth() + 1;
	    var date = today.getDate();
	    today.setDate(today.getDate()-<%=daysbetwwen%>); 
	    var yearb = today.getYear();
	    var monthb = today.getMonth()+1;  
        var dateb = today.getDate();
        //alert(today.getFullYear() +"/"+ (today.getMonth()+1) +"/"+ today.getDate());   
		document.getElementById("beginDate").value = yearb + "/" + monthb + "/" + dateb;
		document.getElementById("endDate").value = year + "/" + month + "/" + date;
	}	

	  function checkvalue(){
     var beginDate = document.getElementById("beginDate").value;
     var endDate = document.getElementById("endDate").value;
     if (beginDate == ""){
         alert("请选择开始日期");
         return false;
     }
     if (endDate == ""){
         alert("请选择结束日期");
         return false;
     }
     var startyear = beginDate.substring(0,4);
     var startmonth = parseInt(beginDate.substring(5,7) ,10) - 1;
     var startday = parseInt(beginDate.substring(8,10),10);
     var thestartdate = new Date(startyear,startmonth,startday);

     var endyear = endDate.substring(0,4);
     var endmonth = parseInt(endDate.substring(5,7) ,10) - 1;
     var endday = parseInt(endDate.substring(8,10),10);
     var theenddate = new Date(endyear,endmonth,endday);
     var todaydate = new Date();
     if(theenddate < thestartdate){
		alert("结束日期不能小于开始日期！");
		return false;
	 }else if(thestartdate > todaydate){
		alert("开始日期不能大于当前日期！");
		return false;    
     }else if(theenddate > todaydate){
		alert("结束日期不能大于当前日期！");
		return false;
     }
     return true;    
  }
</script>
<Br/><Br/><Br/>
<template:titile value="从本地上传附件及显示该盯防对应所有附件" />
<html:form action="/WatchPicAction?method=addDelWatchPic" enctype="multipart/form-data">
<template:formTable namewidth="50" contentwidth="650">
  <template:formTr name="已上传附件">
     <table id="uploadID"  border="0" align="left" cellpadding="0" cellspacing="0" >
	<tr class=trcolor>
    <td>
        <apptag:listAttachmentLink fileIdList="<%=watchAttachs%>"  showdele="true"/>
    </td>
    <td></td>
	</tr>
</table>
  </template:formTr>
  <template:formSubmit>
	<td>
		<html:button property="action" styleClass="button" onclick="addRow()">添加附件</html:button>
	</td>
	<td>
		<html:submit styleClass="button" >提交</html:submit>
	</td>
	<td>
		<html:reset styleClass="button">取消	</html:reset>
	</td>
	<td>
		<html:button property="action" styleClass="button" onclick="addGoBack()">返回</html:button>
	</td>
</template:formSubmit>
</template:formTable>
</html:form>

<Br/><Br/><Br/>
<body onload="initeDate()">
<template:titile value="从彩信提取附件"/>
<html:form action="/WatchPicAction.do?method=showLinkUploadPic" onsubmit="return checkvalue();">
  <template:formTable contentwidth="200" namewidth="200" >
    <template:formTr name="开始日期" isOdd="false">
      <html:text readonly="true" property="beginDate" styleClass="inputtext" style="width:200" maxlength="12"/>
      <apptag:date property="beginDate"/>
    </template:formTr>
    <template:formTr name="结束日期" isOdd="false">
      <html:text readonly="true" property="endDate" styleClass="inputtext" style="width:200" maxlength="12"/>
      <apptag:date property="endDate"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">查询</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">重置</html:reset>
      </td>
    </template:formSubmit>

  </template:formTable>
</html:form>
</body>


