<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.commons.config.GisConInfo;" %>
<%
 String watchAttachs = (String)request.getAttribute("viewwatchattach")+"*";
 GisConInfo gisServer = GisConInfo.newInstance();
 int daysbetwwen = Integer.parseInt(gisServer.getDaysForMMS());
%>
<script language="javascript">
    //var bFlag=true;//ȫ�ֱ���,�����ж��Ƿ���������ı�������
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
    
	 //�ű����ɵ�ɾ����  ť��ɾ������
	function deleteRow(){
      	//��ð�ť�����е�id
     	var rowid = "000";
        rowid = this.id;
        rowid = rowid.substring(1,rowid.length);
         //��idת��Ϊ�������е�����,��ɾ��
      	for(i =0; i<uploadID.rows.length;i++){
        	if(uploadID.rows[i].id == rowid){
            	uploadID.deleteRow(uploadID.rows[i].rowIndex);
                fileNum--;
            }
        }
    }
    //���һ������
    function addRow(){
    	var  onerow=uploadID.insertRow(-1);
        onerow.id = uploadID.rows.length-2;

        var   cell1=onerow.insertCell();
		var   cell2=onerow.insertCell();
        //var   cell3=onerow.insertCell();
        var   cell4=onerow.insertCell();
		//����һ�������
        var t1 = document.createElement("input");
        //t1.name = "filename"+onerow.id;//alert(onerow.id);
        t1.name = "uploadFile["+ fileNum + "].file";
		t1.type= "file";
		t1.id = "text" + onerow.id;
		t1.width="300";

        //����һ���ı��������븽��˵��
        var t2 =document.createElement("input");
        t2.width="160";
        t2.name = "uploadFile["+ fileNum + "].remark";
        t2.id = "rtext" + onerow.id;
        t2.value="�����븽��˵��...";
        t2.onfocus=clearTip;
        fileNum++;
        
        //����ɾ����ť
        var b1 =document.createElement("button");
        b1.value = "ɾ��";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
        //alert(b1.id + "rrr");
        
        //var ll = document.createElement("label");
        //ll.innerHTML = "˵��:";
        //ll.width="50";
        //ll.id = "l" + onerow.id;
        
        
        
        cell1.appendChild(t1);//����
        cell2.appendChild(b1);
        //cell3.appendChild(ll);//����
        cell4.appendChild(t2);//����
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
         alert("��ѡ��ʼ����");
         return false;
     }
     if (endDate == ""){
         alert("��ѡ���������");
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
		alert("�������ڲ���С�ڿ�ʼ���ڣ�");
		return false;
	 }else if(thestartdate > todaydate){
		alert("��ʼ���ڲ��ܴ��ڵ�ǰ���ڣ�");
		return false;    
     }else if(theenddate > todaydate){
		alert("�������ڲ��ܴ��ڵ�ǰ���ڣ�");
		return false;
     }
     return true;    
  }
</script>
<Br/><Br/><Br/>
<template:titile value="�ӱ����ϴ���������ʾ�ö�����Ӧ���и���" />
<html:form action="/WatchPicAction?method=addDelWatchPic" enctype="multipart/form-data">
<template:formTable namewidth="50" contentwidth="650">
  <template:formTr name="���ϴ�����">
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
		<html:button property="action" styleClass="button" onclick="addRow()">��Ӹ���</html:button>
	</td>
	<td>
		<html:submit styleClass="button" >�ύ</html:submit>
	</td>
	<td>
		<html:reset styleClass="button">ȡ��	</html:reset>
	</td>
	<td>
		<html:button property="action" styleClass="button" onclick="addGoBack()">����</html:button>
	</td>
</template:formSubmit>
</template:formTable>
</html:form>

<Br/><Br/><Br/>
<body onload="initeDate()">
<template:titile value="�Ӳ�����ȡ����"/>
<html:form action="/WatchPicAction.do?method=showLinkUploadPic" onsubmit="return checkvalue();">
  <template:formTable contentwidth="200" namewidth="200" >
    <template:formTr name="��ʼ����" isOdd="false">
      <html:text readonly="true" property="beginDate" styleClass="inputtext" style="width:200" maxlength="12"/>
      <apptag:date property="beginDate"/>
    </template:formTr>
    <template:formTr name="��������" isOdd="false">
      <html:text readonly="true" property="endDate" styleClass="inputtext" style="width:200" maxlength="12"/>
      <apptag:date property="endDate"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">��ѯ</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">����</html:reset>
      </td>
    </template:formSubmit>

  </template:formTable>
</html:form>
</body>


