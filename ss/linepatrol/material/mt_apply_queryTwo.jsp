<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
  <head>    
    <title></title>  
<script type="text/javascript" defer="defer"
			src="<%=request.getContextPath()%>/js/WdatePicker/WdatePicker.js"></script>
  </head>
  <script type="text/javascript">
      function GetSelectDateTHIS(strID) {
        document.all.item(strID).value = getPopDate(document.all.item(strID).value);
		//��ʼʱ��
    	var yb = queryForm2.begintime.value.substring(0,4);
		var mb = parseInt(queryForm2.begintime.value.substring(5,7) ,10);
		var db = parseInt(queryForm2.begintime.value.substring(8,10),10);
    	//����ʱ��
    	var ye = queryForm2.endtime.value.substring(0,4);
		var me = parseInt(queryForm2.endtime.value.substring(5,7) ,10);
		var de = parseInt(queryForm2.endtime.value.substring(8,10),10);
    	if(yb == "" && ye != "" ) {
	  		alert("�������ѯ�Ŀ�ʼʱ��!");      		
      		queryForm2.begintime.focus();
      		return false;
		}
		// �ڿ�ʼ�ͽ���ʱ�䶼���������������ܿ���ȵ��ж�
		if(yb != "" && ye != "") {
			//if(yb!=ye){
      		//	alert("��ѯʱ��β��ܿ����!");
      		//	document.all.item(strID).value="";
      		//	queryForm2.endtime.focus();
      		//	return false;
    		//}
			if(ye < yb) {
				alert("��ѯ�������ڲ���С�ڿ�ʼ����!");
      			document.all.item(strID).value="";
      			queryForm2.endtime.focus();
      			return false;
			}
			if((ye == yb ) && (me < mb  || de < db)) {
      			alert("��ѯ�������ڲ���С�ڿ�ʼ����!");
     		 	document.all.item(strID).value="";
      			queryForm2.endtime.focus();
      			return false;
    		}
		}
        return true;
    }
  </script>
  <body>
    <template:titile value="��ѯ��������"/>
    <html:form action="/LinePatrolManagerAction.do?method=queryParolManagerList" >
  	<template:formTable namewidth="150" contentwidth="300">
  		
  		<template:formTr name="������Դ">
  						<select name="type" class="inputtext" style="width:215px" id="typeID">
  						<option value="">����</option>
         				<option value="1">��������</option>
         				<option value="0">���ɲ���</option>
         				<option value="2">�Թ�����</option>
         				</select>
  		</template:formTr>
  		<template:formTr name="��������">
  				<select name="state" class="inputtext" style="width:215px" id="typeID">
  						<option value="">ѡ������״̬</option>
         					<option value="1">������</option>
         					<option value="2">����ͨ��</option>
         					<option value="3">ͨ������</option>
         		</select>
  		</template:formTr>
  		<template:formTr name="��ʼʱ��">
        <input id="begin" type="text" name="begintime" readonly="readonly" class="inputtext" style="width:215px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})"/>
      </template:formTr>
      <template:formTr name="����ʱ��">
        <input id="end" type="text" name="endtime" readonly="readonly" class="inputtext" style="width:215px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})"/>
      </template:formTr>
  		 <template:formSubmit>
				      	<td>
                          <html:submit property="action" styleClass="button">��ѯ</html:submit>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >����</html:reset>
				      	</td>
			</template:formSubmit>
  	</template:formTable>
  	</html:form>
  </body>
</html>
