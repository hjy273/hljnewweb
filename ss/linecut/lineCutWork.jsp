<%@ page import="com.cabletech.linecut.beans.LineCutBean"%>
<%@include file="/common/header.jsp"%>


<html>
<head>
<script type="" language="javascript">
    var fileNum=0;
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


		//����һ�������
        var t1 = document.createElement("input");
        //t1.name = "filename"+onerow.id;//alert(onerow.id);
        t1.name = "uploadFile["+ fileNum + "].file";
		t1.type= "file";
		t1.id = "text" + onerow.id;
		t1.width="200";
        fileNum++;


        //����ɾ����ť
        var b1 =document.createElement("button");
        b1.value = "ɾ��";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
        //alert(b1.id + "rrr");

        //�޸ĸ�������ʾ��ʽ
        cell1.width="125";
        cell2.width="300";
        cell1.align="right";
        cell1.innerText="ʩ��������";
        cell2.appendChild(t1);//����
        cell2.appendChild(b1);
	}

    function addRow1(){
    	var  onerow=uploadID.insertRow(-1);
        onerow.id = uploadID.rows.length-2;

        var   cell1=onerow.insertCell();
		var   cell2=onerow.insertCell();


		//����һ�������
        var t1 = document.createElement("input");
        //t1.name = "filename"+onerow.id;//alert(onerow.id);
        t1.name = "uploadFile["+ fileNum + "].file";
		t1.type= "file";
		t1.id = "text" + onerow.id;
		t1.width="300";
        fileNum++;


        //����ɾ����ť
        var b1 =document.createElement("button");
        b1.value = "ɾ��";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
        //alert(b1.id + "rrr");

        cell1.appendChild(t1);//����
        cell2.appendChild(b1);
	}
       //�����Ƿ�����
	 function valiD(id){
      	var mysplit = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
        var obj = document.getElementById(id);
        if(mysplit.test(obj.value)){
          return true;
        }
        else{
        	alert("����д�����ֲ��Ϸ�,����������");
            obj.focus();
            obj.value = "0.00";
            return false;
        }
    }


    //ѡ������
    function GetSelectDateTHIS(strID) {
    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    	return ;
	}

    //�����ʩ����Ϣҳ����Ӱ�ť����
   function toGetFormForWork(idValue){
     	var url = "${ctx}/LineCutWorkAction.do?method=showOneInfoForWork&reid=" + idValue;
        window.location.href=url;
    }
    //�鿴ʩ����ϸ��ť����
 	function toGetOneWorkForm(idValue){
     	var url = "${ctx}/LineCutWorkAction.do?method=showOneWorkInfo&reid=" + idValue;
        window.location.href=url;
    }

    //�鿴������ϸ���ض���
 	function  WorkShowGoBack(){
     	var url = "${ctx}/LineCutWorkAction.do?method=showAllWork";
        window.location.href=url;
    }
    function toGetWorkUp(idvalue){
    	var url = "${ctx}/LineCutWorkAction.do?method=showWorkUp&reid=" + idValue;
        window.location.href=url;
    }

	function queryGoBack() {
		var url = "${ctx}/LineCutWorkAction.do?method=doQueryForArAfter";
		window.location.href=url;
	}

	//���鵵ҳ�水ť����
   function toGetFormForAr(idValue){
     	var url = "${ctx}/LineCutWorkAction.do?method=showOneInfoForAr&reid=" + idValue;
        window.location.href=url;
    }
     //�鿴�����ϸ��ť����
 	function toGetOneArForm(idValue){
     	var url = "${ctx}/LineCutWorkAction.do?method=showOneWorkArInfo&reid=" + idValue;
        window.location.href=url;
    }
    //�鿴��ϸ���ض���
 	function  arShowGoBack(){
     	var url = "${ctx}/LineCutWorkAction.do?method=showAllAr";
        window.location.href=url;
    }
    function  addGoBack(){
     	var url = "${ctx}/LineCutWorkAction.do?method=workShow";
        window.location.href=url;
    }
    function toGetWorkEdit(id,archive){
    	if(archive !="����ʩ��"){
          alert("�Բ����Ѿ�ʩ����ϣ������޸ģ���");
          return;
        }
    	var url = "${ctx}/LineCutWorkAction.do?method=showWorkUp&reid=" + id;
        window.location.href=url;
    }

    function dosubmit(){
        if(LineCutBean.isarchive[0].checked==true&&compareDate(LineCutBean.essetime.value)){
          alert("ʩ�����ڱȹ鵵������������ɹ鵵��");
          return false;
        }else{
          if(LineCutBean.flag[1].checked ==true){
            if(window.confirm("ȷ�ϲ������޸�������?"))
            LineCutBean.submit();
            else
            return false;
          }
          LineCutBean.submit();
        }
    }

    function compareDate(time){
      var date=new Date();
      var worktime=parseDate(time+":00");
      return (date<worktime);
    }

function parseDate(str){
  if(typeof str == 'string'){
    var results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) *$/);
    if(results && results.length>3)
      return new Date(parseInt(results[1]),parseInt(results[2]) -1,parseInt(results[3]));
    results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) +(\d{1,2}):(\d{1,2}):(\d{1,2}) *$/);
    if(results && results.length>6)
      return new Date(parseInt(results[1]),parseInt(results[2]) -1,parseInt(results[3]),parseInt(results[4]),parseInt(results[5]),parseInt(results[6]));
    results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) +(\d{1,2}):(\d{1,2}):(\d{1,2})\.(\d{1,9}) *$/);
    if(results && results.length>7)
      return new Date(parseInt(results[1]),parseInt(results[2]) -1,parseInt(results[3]),parseInt(results[4]),parseInt(results[5]),parseInt(results[6]),parseInt(results[7]));
  }
  return null;
}

    function getTimeWin(objName){
		iteName = objName;
		showx = event.screenX - event.offsetX - 4 - 210 ;
		showy = event.screenY - event.offsetY + 18;
		var timeWin = window.showModalDialog("${ctx}/common/time.html",self, "dialogWidth:255px; dialogHeight:285px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; status:no; directories:yes;scrollbars:no;Resizable=no;help:no");
	}
	function setSelecteTime(time) {
	    	document.all.item(iteName).value = time;
    }

    function addworksub(){
        if(LineCutBean.date.value == null || LineCutBean.date.value ==""){
	        alert("����дʩ�������ڣ�");
	        return false;
        }
        if(LineCutBean.time.value == null || LineCutBean.time.value ==""){
	        alert("����дʩ����ʱ�䣡");
	        return false;
        }
	
    	LineCutBean.essetime.value = LineCutBean.date.value + " " + LineCutBean.time.value;
        LineCutBean.submit();
    }


	//��̬�ϴ�����
	function addRowMod() {
			var maxSeq = parseInt(document.getElementById('maxSeq').value);
			maxSeq ++;//�Լ�
			var tableBody = document.getElementsByTagName("tbody")[1];
			var newTr = document.createElement("tr");//����һ��tr
			newTr.id = maxSeq;
			newTr.className = 'trColor';
			
			var checkTd = document.createElement("td");//����һ��TD��+��TR��
			checkTd.innerHTML = "<input type='checkbox' name='ifCheck' value='" + maxSeq + "'>";
			checkTd.className = 'selTd';
			checkTd.style.width = "100px";
			newTr.appendChild(checkTd);
			
			var fileTd = document.createElement("td");//�ϴ��ļ���td��+��TR��
			fileTd.className = 'rigthTdStyle';
			var fileUpLoad = document.createElement("input");
			fileUpLoad.type = 'file';
			fileUpLoad.className = 'fileStyle';
			fileUpLoad.name = 'uploadFile[' + maxSeq + '].file';
			fileUpLoad.id = 'uploadFile[' + maxSeq + '].file';
			fileTd.appendChild(fileUpLoad);
			newTr.appendChild(fileTd);
			
			document.getElementById('maxSeq').value = maxSeq;//�����������ֵ
			tableBody.appendChild(newTr);
		}
		
		function checkOrCancel() {
			var all = document.getElementById('sel');
			if(all.checked) {
				checkAll();
			} else {
				cancelCheck();
			}
		}

		function checkAll() {
			var subChecks = document.getElementsByName("ifCheck");
			var length = subChecks.length;
			for(var i = 0; i < length; i++) {
				subChecks[i].checked = true;
			}		
		}
		
		function cancelCheck() {
			var allChecks = document.getElementsByName('ifCheck');
			var length = allChecks.length;
			for(var i = 0; i < length; i++) {
				allChecks[i].checked = false;
			}
		}
		
		
		function delRow() {
			var delChecks = document.getElementsByName('ifCheck');
			var tableBody = document.getElementsByTagName('tbody')[1];
			var delRows = new Array(delChecks.length);
			var delid = 0;
			if(delChecks.length == 0) {
				alert("����û�����Ӹ���!");
				return;
			}
			for(i = 0; i < delChecks.length; i++) {
				if(delChecks[i].checked) {
					delRows[delid++] = document.getElementById(delChecks[i].value)
				}
			}
			if(delid == 0 ) {
				alert("��ѡ��Ҫɾ���ĸ���!");
				return;
			}
			if(delid != 0) {
				if(confirm("ȷʵҪɾ����")) {
					for(i = 0; i <= delid; i++) {
						if(delRows[i] != null && delRows[i].nodeType == 1) {
							tableBody.removeChild(delRows[i]);
						}
					}
				document.getElementById('sel').checked = false;
				}
			}
		}

		function oneReGoBack() {
			var url= "LineCutWorkAction.do?method=doQueryForWorkAfterMod";
			window.location.href=url;
		}
</script>
<title>
partRequisition
</title>
</head>
<body>

  <!--��ʾ�����ʩ����Ϣ�ĸ��-->
    <logic:equal value="reforwork1"scope="session" name="type">
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"  />
        <br />
        <%
			DynaBean object = null;
			String id = null;
			String numerical = null;
			String name = null;
		%>
        <template:titile value="�����ʩ����Ϣ�ĸ��һ����" />
    		<display:table name="sessionScope.reqinfo" requestURI="${ctx}/LineCutWorkAction.do?method=workShow" id="currentRowObject"   pagesize="18">
    			<display:column media="html" title="��ˮ��" sortable="true">
    				<%
						object = (DynaBean)pageContext.getAttribute("currentRowObject");
						if(object != null) {
							id = (String) object.get("reid");
							numerical = (String)object.get("numerical");
							numerical = numerical == null? "": numerical;
							name =  (String)object.get("name");
							if(name != null) {
								name = name.length() < 10 ? name: name.substring(0,10)+"...";
							} else {
								name = "";
							}
						}
					 %>
					 <a href="javascript:toGetFormForWork('<%=id%>')"><%=numerical %></a>
    			</display:column>
            	<display:column media="html"  title="�������" sortable="true">
					  <a href="javascript:toGetFormForWork('<%=id%>')"><%=name %></a>
            	</display:column>
                <display:column property="sublinename" title="�м̶�" maxLength="10"style="align:center"sortable="true"/>
                <%--<display:column property="prousetime" title="�ƻ���ʱ"maxLength="10"style="align:center"sortable="true"/>
                --%>
                <display:column property="protime" title="�ƻ����ʱ��" maxLength="18"style="align:center"sortable="true"/>
                <display:column property="address" title="��ӵص�" maxLength="10"sortable="true"/>
                <display:column property="contractorname" title="�ύ��λ" maxLength="10"sortable="true"/>
    		</display:table>
    </logic:equal>


	<!--�����Ϣҳ��-->
	<logic:equal value="work2" name="type">
    <br />
		<template:titile value="��·��ӻ�����Ϣ"/>
		  <template:formTable namewidth="100" contentwidth="400"  th2="����" th1="��Ŀ">
		    <template:formTr name="�������">
		      <bean:write name="reqinfo" property="name"/>
		    </template:formTr>
		    <template:formTr name="�����߶�">
		    	<%
		    		String sublinenamecon = ((LineCutBean) request.getAttribute("reqinfo")).getSublinename();
					String[] atrArr = sublinenamecon.split("<br>");
					for(int i = 0; i < atrArr.length; i++ ) { %>
						<%=atrArr[i] %>;<br>
				<%}%>
		    </template:formTr>
            <template:formTr name="���ԭ��">
		      <bean:write name="reqinfo" property="reason"/>
		    </template:formTr>
            <template:formTr name="��ӵص�">
		      <bean:write name="reqinfo" property="address"/>
		    </template:formTr>
		    <template:formTr name="�������">
		      <bean:write name="reqinfo" property="cutType"/>
		    </template:formTr>
            <template:formTr name="�ƻ��������">
		      <bean:write name="reqinfo" property="protime"/>
		    </template:formTr>
            <template:formTr name="Ԥ�Ƹ����ʱ">
		      <bean:write name="reqinfo" property="prousetime"/>Сʱ
		    </template:formTr>
            <template:formTr name="���ǰ˥��">
		      <bean:write name="reqinfo" property="provalue"/>�ֱ�(db)
		    </template:formTr>
            <template:formTr name="��Ӱ��ϵͳ">
		      <bean:write name="reqinfo" property="efsystem"/>
		    </template:formTr>
            <template:formTr name="�������">
		      <bean:write name="reqinfo" property="auditresult"/>
		    </template:formTr>
		  </template:formTable>
          <hr />
          
          <html:form action="/LineCutWorkAction?method=addWork" styleId="addApplyForm" enctype="multipart/form-data" onsubmit="checkAffix()">
          		<html:hidden property="reid" name="reqinfo"/>
          		<html:hidden property="essetime" />

            	<template:formTable namewidth="100" contentwidth="400"  th2="��д" th1="��Ŀ">
            		<%
            			String dateTime = ((LineCutBean)request.getAttribute("reqinfo")).getProtime();
            			String[] date = dateTime.split(" ");
            		 %>
				    <template:formTr name="ʵ�ʸ������">
				      	 <input  type="text"  id="protimeid" name="date" value="<%=date[0] %>" readonly="readonly" class="inputtext" style="width:125" />
                    	<INPUT TYPE='BUTTON' VALUE='����' ID='btn'  onclick="GetSelectDateTHIS('protimeid')" STYLE="font:'normal small-caps 6pt serif';" >
                        <html:text property="time"   value="<%=date[1] %>" styleClass="inputtext" style="width:88"  readonly="true"/>
                        <input type='button' value="ʱ��" id='btn'  onclick="getTimeWin('time')" style="font:'normal small-caps 6pt serif';">

				    </template:formTr>
				    <%
				    	String time = ((LineCutBean)request.getAttribute("reqinfo")).getProusetime();
				     %>
					 <template:formTr name="�����ʱ" style="width:270px;" >
				     	<html:text property="usetime"  value="<%=time %>" styleId="usetimeid" styleClass="inputtext"  onchange="valiD(id)" style="width:250;" />Сʱ
				     </template:formTr>
				     <%
				     	String manpower = ((LineCutBean)request.getAttribute("reqinfo")).getManpower();
				      %>
                      <template:formTr name="��������"  style="width:270" >
				     	<html:text property="manpower"   value="<%=manpower %>" styleId="manpowerid" styleClass="inputtext"  onchange="valiD(id)" style="width:250;" />����
				     </template:formTr>
				     <%
				     	String endvalue = ((LineCutBean)request.getAttribute("reqinfo")).getProvalue();
				      %>
                      <template:formTr name="ʵ��˥��" style="width:270" >
				     	<html:text property="endvalue"  value="<%=endvalue %>" styleId="endvalueid" styleClass="inputtext"  onchange="valiD(id)" style="width:250;" />�ֱ�(db)
				     </template:formTr>
                     <template:formTr name="�����д"  style="width:270" >
                     	<input  type="radio" name="flag" value="1"  checked="checked" />�Ѿ����
                        <input  type="radio" name="flag" value="0"   />��δ���
                     </template:formTr>
				     <template:formTr name="ʩ����ע" style="width:270" >
                       <html:textarea property="workremark" cols="36" rows="4" value="" title="�벻Ҫ����250�����ֻ���512��Ӣ���ַ������򽫽ضϡ�" style="width:270" styleClass="textarea"/>
				     </template:formTr>
				    <table  id="uploadID"  width=500 border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				    	<tbody></tbody>
				    </table>
				    <template:formSubmit>
				        <td>
				    		<input type="checkbox" onclick="checkOrCancel()" id="sel">ȫѡ&nbsp;
				    	</td>
				        <td>
				        	<input type="hidden" value="0" id="maxSeq">
				        	<html:button  property="action" styleClass="button"  onclick="addRowMod()">��Ӹ���</html:button>
				        </td>
				        <td>
							<input type="button" value="ɾ��ѡ��" onclick="delRow()" class="button">
						</td>
				        <td>
				          <html:button styleClass="button"  onclick="addworksub()" property="">�ύ</html:button>
				        </td>
				        <td>
				          <html:reset styleClass="button" >ȡ��	</html:reset>
				        </td>
				        <td>
				            <input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
				        </td>
				  </template:formSubmit>
			  </template:formTable>
	     </html:form>
	</logic:equal>


      <!--��ʾʩ����Ϣ-->
    <logic:equal value="work1"scope="session" name="type">
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"  />
          <br />
          <%
				DynaBean object1=null;
				String id1= null;
				String numerical = null;
				String name = null;
				String isarchive = null;
		  %>
        <template:titile value="�Ѿ�ʩ�����һ����" />
    		<display:table name="sessionScope.reqinfo" requestURI="${ctx}/LineCutWorkAction.do?method=doQueryForWork" id="currentRowObject"   pagesize="18">
            	<display:column media="html" title="��ˮ��" sortable="true">
            		<%
						object1 = (DynaBean)pageContext.getAttribute("currentRowObject");
						if(object1 != null) {
							id1 = (String) object1.get("reid");
							numerical = (String)object1.get("numerical");
							numerical = numerical==null ? "": numerical;
							name =  (String)object1.get("name");
							if(name != null) {
								name = name.length() < 10 ? name: name.substring(0,10)+"...";
							} else {
								name = "";
							}
							isarchive = (String)object1.get("isarchive");
						}
					 %>
					 <a href="javascript:toGetOneWorkForm('<%=id1%>')"><%=numerical %></a> 
            	</display:column>
            	<display:column media="html" title="�������"  sortable="true">
					  <a href="javascript:toGetOneWorkForm('<%=id1%>')"><%=name %></a>
				</display:column>
            	
                <display:column property="sublinename" title="�м̶�" maxLength="10"style="align:center"sortable="true"/>
                <display:column property="address" title="��ӵص�" maxLength="10"sortable="true"/>
                <display:column property="endvalue" title="��Ӻ�˥��" maxLength="10"sortable="true"/>
                <display:column property="usetime" title="�����ʱ" maxLength="10"sortable="true"/>
                <display:column property="essetime" title="�������" maxLength="18"sortable="true"/>

                <display:column media="html" title="��ǰ״̬" sortable="true">
                	 <%if("�Ѿ��鵵".equals(isarchive)) {%>
						<font color=""><%=isarchive %></font>
					  <%} 
					  else if("ʩ�����".equals(isarchive)) {%>
					  	<font color="#DA70D6"><%=isarchive %></font>
					  <%} 
					  else if("����ʩ��".equals(isarchive)) {%>
					  	<font color="#FFA500"><%=isarchive %></font>
					  <%} else {%>
					  	<%=isarchive %>
					  <%} %>
                </display:column>

				<%
					UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
                    String deptType = userinfo.getDeptype();
                    if("2".equals(deptType)) { %>
                <display:column media="html" title="����" >
	            	 <apptag:checkpower thirdmould="30304" ishead="0">
	            	 <%
	            	 	if("����ʩ��".equals(isarchive)) { %>
	            	 		 <a href="javascript:toGetWorkEdit('<%=id1%>','<%=isarchive%>')">�޸�</a>
	            	 <%	}%>
	            	 </apptag:checkpower>
			  	</display:column>
			  	
			  	<%} %>
            </display:table>
            <html:link action="/LineCutWorkAction.do?method=exportLineCutWork">����ΪExcel�ļ�</html:link>
    </logic:equal>



    	<!--��ʾ���ʩ������ϸ��Ϣ-->
	<logic:equal value="work10" name="type">
    <br />
		<template:titile value="��·���ʩ����ϸ��Ϣ"/>
		  <template:formTable namewidth="100" contentwidth="450"  th2="����" th1="��Ŀ">

		    <template:formTr name="�������">
                <bean:write name="reqinfo" property="name"/>
		    </template:formTr>
		    <template:formTr name="�����߶�">
		    	<%
		    		String sublinenamecon = ((LineCutBean) request.getAttribute("reqinfo")).getSublinename();
					String[] atrArr = sublinenamecon.split("<br>");
					for(int i = 0; i < atrArr.length; i++ ) { %>
						<%=atrArr[i] %>;<br>
				<%}%>
		    </template:formTr>
            <template:formTr name="���ԭ��">
                <bean:write name="reqinfo" property="reason"/>
		    </template:formTr>
            <template:formTr name="��ӵص�">
                <bean:write name="reqinfo" property="address"/>
		    </template:formTr>
		    <template:formTr name="�������">
		      <bean:write name="reqinfo" property="cutType"/>
		    </template:formTr>
            <template:formTr name="���ǰ˥��">
                <bean:write name="reqinfo" property="provalue"/>�ֱ�(db)
		    </template:formTr>
            <template:formTr name="��Ӱ��ϵͳ">
                <bean:write name="reqinfo" property="efsystem"/>
		    </template:formTr>
             <template:formTr name="��Ӻ�˥��">
                <bean:write name="reqinfo" property="endvalue"/>�ֱ�(db)
		    </template:formTr>
            <template:formTr name="��������">
                <bean:write name="reqinfo" property="manpower"/>����
		    </template:formTr>
            <template:formTr name="�����ʱ">
                <bean:write name="reqinfo" property="usetime"/>Сʱ
		    </template:formTr>
            <template:formTr name="���ʱ��">
                <bean:write name="reqinfo" property="essetime"/>
		    </template:formTr>
             <template:formTr name="ʩ����ע" >
               <bean:write name="reqinfo" property="workremark" />
		    </template:formTr>
		    <template:formTr name="�޸�����">
		    	<bean:write name="reqinfo" property="updoc" />
		    </template:formTr>
            <template:formTr name="ʩ������">
				<logic:empty name="reqinfo" property="workacce">
                	<apptag:listAttachmentLink fileIdList=""/>
				</logic:empty>
                <logic:notEmpty name="reqinfo" property="workacce">
                  	<bean:define id="tem1" name="reqinfo" property="workacce" type="java.lang.String"/>
			   		<apptag:listAttachmentLink fileIdList="<%=tem1%>"/>
                </logic:notEmpty>


		    </template:formTr>
		  </template:formTable>

		   <p align="center"><input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"></p>
	</logic:equal>



    	<!--���ʩ���޸���ʾ-->
	<logic:equal value="work4" name="type">
    <br />
		<template:titile value="��·���ʩ����Ϣ�޸�"/>
            <html:form action="/LineCutWorkAction?method=WorkUp" styleId="addApplyForm" enctype="multipart/form-data" onsubmit="checkAffix()">
            	<html:hidden property="workacce" name="reqinfo"/>
                <html:hidden property="reid" name="reqinfo"/>
                <html:hidden property="workid"  name="reqinfo"/>
                <html:hidden property="essetime"  name="reqinfo"/>

			  <template:formTable namewidth="100" contentwidth="450">
			    <template:formTr name="�������">
			    	<bean:write property="name"  name="reqinfo"/>
			    </template:formTr>
			    <template:formTr name="�����߶�">
			    	<%
			    		String sublinenamecon = ((LineCutBean) request.getAttribute("reqinfo")).getSublinename();
						String[] atrArr = sublinenamecon.split("<br>");
						for(int i = 0; i < atrArr.length; i++ ) { %>
							<%=atrArr[i] %>;<br>
					<%}%>
			    </template:formTr>
	            <template:formTr name="���ԭ��">
	            	<bean:write property="reason"  name="reqinfo"/>
			    </template:formTr>
	            <template:formTr name="��ӵص�">
	            	<bean:write property="address"  name="reqinfo"/>
			    </template:formTr>
	            <template:formTr name="���ǰ˥��">
	            	<bean:write property="provalue"  name="reqinfo"/>
			    </template:formTr>
	            <template:formTr name="��Ӱ��ϵͳ">
	            	<bean:write property="efsystem"  name="reqinfo"/>
			    </template:formTr>
	             <template:formTr name="��Ӻ�˥��">
                 	<html:text property="endvalue"  name="reqinfo" styleClass="inputtext"  style="width:180" />�ֱ�(db)
			    </template:formTr>
	            <template:formTr name="��������">
                	<html:text property="manpower"  name="reqinfo" styleClass="inputtext"  style="width:200" />����
			    </template:formTr>
	            <template:formTr name="�����ʱ">
                	<html:text property="usetime"  name="reqinfo" styleClass="inputtext"  style="width:200" />Сʱ
			    </template:formTr>
	            <template:formTr name="���ʱ��">
                	<% String temp1 = ((LineCutBean)request.getAttribute("reqinfo")).getEssetime();
                   		if(temp1 == null  || temp1.equals(""))
                            temp1 = "                       ";
                    %>
				      	 <input  type="text"  id="protimeid" name="date" value="<%=temp1.substring(0,10)%>" readonly="readonly" class="inputtext" style="width:100" />
                    	<INPUT TYPE='BUTTON' VALUE='����' ID='btn'  onclick="GetSelectDateTHIS('protimeid')" STYLE="font:'normal small-caps 6pt serif';" >
                        <html:text property="time"   value="<%= temp1.substring(11,16)%>" styleClass="inputtext" style="width:60"  readonly="true"/>
                        <input type='button' value="ʱ��" id='btn'  onclick="getTimeWin('time')" style="font:'normal small-caps 6pt serif';">

			    </template:formTr>
	             <template:formTr name="ʩ����ע">
                 	<html:textarea property="workremark"  name="reqinfo" cols="34"  rows="3"  title="�벻Ҫ����250�����ֻ���512��Ӣ���ַ������򽫽ضϡ�"   styleClass="forTextArea3">
                    </html:textarea>
			    </template:formTr>
                <template:formTr name="�����д"  style="width:270" >
                     	<input  type="radio" name="flag" value="1"  checked="checked" />�Ѿ����
                        <input  type="radio" name="flag" value="0"   />��δ���
                     </template:formTr>
	            <template:formTr name="ʩ������">
					<%
				    	String reacce1="";
				        LineCutBean bean1 = (LineCutBean )request.getAttribute("reqinfo");
				        reacce1 = bean1.getWorkacce();
				        if(reacce1==null){
				        	reacce1="";
				        }
				 	%>
				   <apptag:listAttachmentLink fileIdList="<%=reacce1%>" showdele="true"/>

                </template:formTr>
                    <table  id="uploadID"  width=550 border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
                    	<tbody></tbody>
				    </table>
				    <template:formSubmit>
				        <td>
				    		<input type="checkbox" onclick="checkOrCancel()" id="sel">ȫѡ&nbsp;
				    	</td>
				        <td>
				        	<input type="hidden" value="0" id="maxSeq">
				        	<html:button  property="action" styleClass="button"  onclick="addRowMod()">��Ӹ���</html:button>
				        </td>
				        <td>
							<input type="button" value="ɾ��ѡ��" onclick="delRow()" class="button">
						</td>
				        <td>
				          <html:button styleClass="button"  onclick="addworksub()" property="">�ύ</html:button>
				        </td>
				        <td>
				          <html:reset styleClass="button" >ȡ��	</html:reset>
				        </td>
				        <td>
				            <input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
				        </td>
				  </template:formSubmit>
			  </template:formTable>

			</html:form>
	</logic:equal>



        <!--������ѯҳ��-->
	    <logic:equal value="work3" name="type"scope="session" >
	      	<script type="text/javascript" src="./js/json2.js"></script>
			<script type="text/javascript" src="./js/prototype.js"></script>
			<script type="text/javascript">
				//����
				function onSelChangeLevel() {
					var ops = document.getElementById('line');
					ops.options.length = 0;
					var clewOps = document.getElementById('clew');
					clewOps.options.length = 0;
					var newClewOp = document.createElement("option");
					newClewOp.value = "";
					newClewOp.text = "===ѡ�����ж���===";
					clewOps.add(newClewOp);
					var params = document.getElementById('level').value;
					if(params == "mention") {
						ops.options.add(new Option("",""));
						return;
					}
					var url = 'LineCutReAction.do?method=getLineByLevelId&levelId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callback, asynchronous:true});
				}
				
				function callback(originalRequest) {
					var ops = document.getElementById('line');
					var rst = originalRequest.responseText;
					var linearr = eval('('+rst+')');
					if(linearr.length == 0) {
						alert("�ü�����δ�ƶ���·!");
						return;
					}
					ops.options.add(new Option('===��ѡ����·===','')); 
					for(var i = 0; i < linearr.length; i++) {
						ops.options.add(new Option(linearr[i].linename,linearr[i].lineid));
					}
					var myGlobalHandlers = {onCreate:function () {
					Element.show("Loadingimg");
					}, onFailure:function () {
						alert("�������ӳ������⣬��رպ�����!");
					}, onComplete:function () {
						if (Ajax.activeRequestCount == 0) {
							Element.hide("Loadingimg");
						}
					}};
					Ajax.Responders.register(myGlobalHandlers);
				}
				
				function onSelChangeLine() {
					var ops = document.getElementById('clew');
					var length = ops.length;
					ops.options.length = 0;
					var params = document.getElementById('line').value;
					if(params == "mention") {
						ops.options.add(new Option("",""));
						return;
					}
					ops.options.add(new Option('===ѡ�����ж���===','')); 
					var url = 'LineCutReAction.do?method=getClewByLineId&lineId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callbackToServer, asynchronous:true});
				}
				
				function callbackToServer(originalRequest) {
					var ops = document.getElementById('clew');
					var rst = originalRequest.responseText;
					var clewarr = eval('('+rst+')');
					if(clewarr.length == 0) {
						alert("��·����δ�ƶ��߶�!");
						return;
					}
					for(var i = 0; i < clewarr.length; i++) {
						ops.options.add(new Option(clewarr[i].sublinename,clewarr[i].sublineid));
					}
					var myGlobalHandlers = {onCreate:function () {
					Element.show("Loadingimg");
					}, onFailure:function () {
						alert("�������ӳ������⣬��رպ�����!");
					}, onComplete:function () {
						if (Ajax.activeRequestCount == 0) {
							Element.hide("Loadingimg");
						}
					}};
					Ajax.Responders.register(myGlobalHandlers);
				}
				
				function onSelectChangeClew() {
					var ops = document.getElementById('cutLineName');
					var length = ops.length;
					ops.options.length = 0;//��������б�
					ops.options.add(new Option("===ѡ����������===",""));
					var params = document.getElementById('clew').value;
					if(params == "mention") {
						ops.options.add(new Option("",""));
					}
					var url = 'LineCutReAction.do?method=getNameBySublineid&sublineId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callbackToServerByName, asynchronous:true});
				}
				
				function callbackToServerByName(originalRequest) {
					var ops = document.getElementById('cutLineName');
					var rst = originalRequest.responseText;
					var nameArr = eval('('+rst+')');
					if(nameArr.length == 0) {
						alert("��·����û�и��ʩ����Ϣ��\n���߻�δ��ʼʩ��!");
						return;
					}
					for(var i = 0; i < nameArr.length; i++) {
						ops.options.add(new Option(nameArr[i].name,nameArr[i].name));
					}
				}
				
				function resetInfo() {
					var ops = document.getElementById('line');
					ops.options.length = 0;
					ops.options.add(new Option("",""));
					
					var ops1 = document.getElementById('clew');
					ops1.options.length = 0;
					ops1.options.add(new Option("===ѡ�����ж���===",""));
					
					var ops2 = document.getElementById('cutLineName');
					ops2.options.length = 0;
					ops2.options.add(new Option("===ѡ�����ж���===",""));
					
				}
				
			</script>
	        <template:titile value="���������Ҹ��ʩ����Ϣ"/>
	        <html:form action="/LineCutWorkAction?method=doQueryForWork"   styleId="queryForm2" >
	        	<template:formTable namewidth="200"  contentwidth="200">
	        		<template:formTr name="��·����">
	        			<select class="inputtext" id="level" onchange="onSelChangeLevel()" name="level" style="width:180px" >
		  					<option value="mention">===��ѡ����·����===</option>
		  					<logic:iterate id="levelList" name="lineLevelList">
		  						<option value="<bean:write name="levelList" property="code"/>"><bean:write name="levelList" property="name"/></option>
		  					</logic:iterate>
		  				</select>
		  				<img id="Loadingimg" src="./images/ajaxtags/indicator.gif" style="display:none">
	        		</template:formTr>
	        		
	        		<template:formTr name="��·����">
	        			<select class="inputtext" id="line" onchange="onSelChangeLine()" name="line" style="width:180px" >
		  					<option></option>
		  				</select>
	        		</template:formTr>
	        		
	            	<template:formTr name="Ѳ���߶�"  >
	            		<select class="inputtext" id="clew" style="width:180px" name="sublineid" onchange="onSelectChangeClew()">
		  					<option  value="">===ѡ�����ж���===</option>
		  				</select>
	                </template:formTr>
	                
                    <template:formTr name="�������"  >
	            		<select name="name"   class="inputtext" style="width:180px" id="cutLineName">
				        	<option  value="">===ѡ�����и��===</option>
			    		</select>
	                </template:formTr>
	                
                   
	                <template:formTr name="��ӿ�ʼʱ��">
	                	<input   id="begin" type="text" readonly="readonly"  name="begintime" class="inputtext" style="width:150" />
	                    <INPUT TYPE='BUTTON' VALUE='����'  readonly="readonly" ID='btn'  onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';" >
	                </template:formTr>
	                <template:formTr name="��ӽ���ʱ��">
	                	<input  id="end" type="text"  name="endtime" class="inputtext" style="width:150" />
	                    <INPUT TYPE='BUTTON' VALUE='����' ID='btn'  onclick="GetSelectDateTHIS('end')" STYLE="font:'normal small-caps 6pt serif';" >
	                </template:formTr>
	                <template:formSubmit>
						      	<td>
		                          <html:submit property="action" styleClass="button" >����</html:submit>
						      	</td>
						      	<td>
						       	 	<html:reset property="action" styleClass="button" >ȡ��</html:reset>
						      	</td>
						    </template:formSubmit>
	        	</template:formTable>
	    	</html:form>
	    </logic:equal>

        <!--//=================�鵵���Ӳ�ѯ===================//-->
  <!--��ʾ���鵵�ĸ����Ϣ-->
    <logic:equal value="reforAr1"scope="session" name="type">
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"  />
        <br />
        <template:titile value="���鵵���һ����" />
    		<display:table name="sessionScope.reqinfo" id="currentRowObject"   pagesize="18">
            	<display:column property="contractorname" title="��ӵ�λ" maxLength="10" style="align:center"  sortable="true"/>
            	<display:column property="name" title="�������" maxLength="10" style="align:center"  sortable="true"/>
                <display:column property="name" title="��ӵص�" maxLength="10"style="align:center"  sortable="true"/>
                <display:column property="sublinename" title="�����߶�" maxLength="10"style="align:center"sortable="true"/>
                <display:column property="essetime" title="���ʱ��" maxLength="18"style="align:center"sortable="true"/>
                <display:column property="reason" title="���ԭ��" maxLength="10"style="align:center"sortable="true"/>
                <display:column property="isarchive" title="��ǰ״̬" maxLength="10"style="align:center"sortable="true"/>
                <display:column media="html" title="����" >
	            	 <%
					    BasicDynaBean  objecta1 = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
					    String ida1 = (String) objecta1.get("reid");
					  %>
	            	 <a href="javascript:toGetFormForAr('<%=ida1%>')">�鵵�����Ϣ</a>
			  	</display:column>
    		</display:table>
    </logic:equal>

    	<!--�鵵ҳ��-->
	<logic:equal value="ar2" name="type">
		 <html:form action="/LineCutWorkAction?method=addAr" styleId="addApplyForm" enctype="multipart/form-data">
         		<html:hidden property="reid" name="reqinfo"/>
                <html:hidden property="essetime" name="reqinfo"/>
	            <br>
                <template:titile value="��·��ӹ鵵"/>
			  <template:formTable namewidth="100" contentwidth="400">
				     <template:formTr name="��ӵ�λ">
				      	<bean:write name="reqinfo" property="contractorname"/>
				    </template:formTr>
		            <template:formTr name="�������">
				      	<bean:write name="reqinfo" property="name"/>
				    </template:formTr>
				    <template:formTr name="�����߶�">
				      	<%
				    		String sublinenamecon = ((LineCutBean) request.getAttribute("reqinfo")).getSublinename();
							String[] atrArr = sublinenamecon.split("<br>");
							for(int i = 0; i < atrArr.length; i++ ) { %>
								<%=atrArr[i] %>;<br>
						<%}%>
				    </template:formTr>
		            <template:formTr name="���ԭ��">
				      	<bean:write name="reqinfo" property="reason"/>
				    </template:formTr>
		            <template:formTr name="��ӵص�">
				      	<bean:write name="reqinfo" property="address"/>
				    </template:formTr>
		           <template:formTr name="��Ӱ��ϵͳ">
				      	<bean:write name="reqinfo" property="efsystem"/>
				    </template:formTr>
		            <template:formTr name="�������">
				      	<bean:write name="reqinfo" property="essetime"/>
				    </template:formTr>
		             <template:formTr name="�����ʱ">
				      	<bean:write name="reqinfo" property="usetime"/>Сʱ
				    </template:formTr>
		             <template:formTr name="��Ӻ�˥��">
				      	<bean:write name="reqinfo" property="endvalue"/>�ֱ�(db)
				    </template:formTr>
		            <template:formTr name="�鵵���">
                    				<input  type="radio" name="isarchive" value="�Ѿ��鵵" /> ��ɹ鵵 &nbsp;&nbsp;&nbsp
                                    <input  type="radio" name="isarchive" value="�Ѿ�����" checked="checked"/> ��δ��ɹ鵵
		            </template:formTr>
                     <template:formTr name="�����޸�">
		               				<input  type="radio" name="flag" value="�����޸�" checked="checked"/> �����޸� &nbsp;&nbsp;&nbsp
                                    <input  type="radio" name="flag" value="�����޸�"/> �����޸�
		            </template:formTr>
		            <template:formTr name="�鵵�ļ�">
		                    	<table  id="uploadID"  width=400 border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
										<tr  class=trcolor ><td ></td></tr>
					            </table>
		            </template:formTr>
		            <template:formSubmit>
						        <td><html:button  property="action" styleClass="button"  onclick="addRow1()">��Ӹ���</html:button>
						        </td>
						        <td>
						          <html:button styleClass="button" property="action" onclick="dosubmit()">�ύ</html:button>
						        </td>
						        <td>
						          <html:reset styleClass="button" >ȡ��	</html:reset>
						        </td>
						        <td>
						            <html:button property="action" styleClass="button" onclick="javascript:window.history.go(-1)" >����</html:button>
						        </td>
		            </template:formSubmit>
			  </template:formTable>
	     </html:form>
	</logic:equal>

        <!--��ʾ�����Ϣ-->
    <logic:equal value="ar1"scope="session" name="type">
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"  />
			<%
				DynaBean object1=null;
				String id1= null;
				String numerical = null;
				String name = null;
			 %>
        <br />
        <template:titile value="���һ����" />
    		<display:table name="sessionScope.reqinfo" requestURI="${ctx}/LineCutWorkAction.do?method=doQueryForAr" id="currentRowObject"   pagesize="18">
    			<display:column media="html" title="��ˮ��" sortable="true">
					<%
						object1 = (DynaBean)pageContext.getAttribute("currentRowObject");
						if(object1 != null) {
							id1 = (String) object1.get("reid");
							numerical = (String)object1.get("numerical");
							numerical = numerical == null ? "":numerical;
							name = (String)object1.get("name");
							if(name!= null) {
								name = name.length() < 10 ? name: name.substring(0,10)+"...";
							} else {
								name = "";
							}
						}
					 %>
					 <a href="javascript:toGetOneArForm('<%=id1%>')"><%=numerical %></a>
				</display:column>
				<display:column media="html" title="�������" sortable="true">
					  <a href="javascript:toGetOneArForm('<%=id1%>')"><%=name %></a>
				</display:column>
    			
            	<display:column property="contractorname" title="��ӵ�λ" maxLength="20" style="align:center"  sortable="true"/>
                <display:column property="sublinename" title="�м̶�" maxLength="15"style="align:center"sortable="true"/>
                <display:column property="address" title="��ӵص�" maxLength="10"sortable="true"/>
                <display:column property="essetime" title="���ʱ��" maxLength="18"sortable="true"/>

                <display:column property="updoc" title="�޸�����" maxLength="10"style="align:center"sortable="true"/>
                <display:column property="isarchive" title="�����׶�" maxLength="10"sortable="true"/>
    		</display:table>
            <html:link action="/LineCutWorkAction.do?method=exportLineCut">����ΪExcel�ļ�</html:link>
    </logic:equal>

    <!--��ʾ�����ϸ��Ϣ-->
	<logic:equal value="ar10" name="type">
    <br />
    	
    		<template:titile value="��·�����ϸ��Ϣ"/>
			  <template:formTable namewidth="100" contentwidth="500" th2="����" th1="��Ŀ">
	
			    <template:formTr name="�������">
			      <bean:write name="reqinfo" property="name"/>
			    </template:formTr>
	            <template:formTr name="��ӵص�">
	              <bean:write name="reqinfo" property="address"/>
			    </template:formTr>
			    <template:formTr name="�����߶�">
			    	<%
			    		String sublinenamecon = ((LineCutBean) request.getAttribute("reqinfo")).getSublinename();
						String[] atrArr = sublinenamecon.split("<br>");
						for(int i = 0; i < atrArr.length; i++ ) { %>
							<%=atrArr[i] %>;<br>
					<%}%>
			    </template:formTr>
	            <template:formTr name="���ԭ��">
			      <bean:write name="reqinfo" property="reason"/>
			    </template:formTr>
			    
			    <template:formTr name="�������">
			      <bean:write name="reqinfo" property="cutType"/>
			    </template:formTr>
	
	            <template:formTr name="��Ӱ��ϵͳ">
			      <bean:write name="reqinfo" property="efsystem"/>
			    </template:formTr>
	            
	            <template:formTr name="�޸�����">
					 <bean:write name="reqinfo" property="updoc"/>
				</template:formTr>
	            
			    <template:formTr name="���뱸ע">
					<bean:write name="reqinfo" property="reremark"/>
				</template:formTr>
	            
	            <template:formTr name="���븽��">
	                    <logic:empty name="reqinfo" property="reacce">
	                     	<apptag:listAttachmentLink fileIdList=""/>
	                    </logic:empty>
	                    <logic:notEmpty name="reqinfo" property="reacce">
	                    	<bean:define id="temr" name="reqinfo" property="reacce" type="java.lang.String"/>
						   	<apptag:listAttachmentLink fileIdList="<%=temr%>"/>
	                    </logic:notEmpty>
				</template:formTr>
	            
				 <tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                 </tr>
                 
			     <template:formTr name="�������">
					  <bean:write name="reqinfo" property="auditresult"/>
				</template:formTr>
                 
			    <template:formTr name="������ע">
					   <bean:write name="reqinfo" property="auditremark"/>
				</template:formTr>
	            
	            <template:formTr name="��������">
	                <logic:empty name="reqinfo" property="auditacce">
	                    <apptag:listAttachmentLink fileIdList=""/>
	                </logic:empty>
	                <logic:notEmpty name="reqinfo" property="auditacce">
	                    <bean:define id="tema" name="reqinfo" property="auditacce" type="java.lang.String"/>
						<apptag:listAttachmentLink fileIdList="<%=tema%>"/>
	                </logic:notEmpty>
				</template:formTr>
	            
	             <tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                 </tr>
				
	            <logic:notEmpty name="reqinfo" property="essetime">
			        <template:formTr name="ʩ��ʱ��">
					    <bean:write name="reqinfo" property="essetime"/>
					</template:formTr>
	            </logic:notEmpty>
	
	            <template:formTr name="���ǰ˥��">
			      <bean:write name="reqinfo" property="provalue"/>�ֱ�(db)
			    </template:formTr>
	
	            <template:formTr name="��Ӻ�˥��">
					<bean:write name="reqinfo" property="endvalue"/>�ֱ�(db)
				</template:formTr>
				
			    <template:formTr name="��������">
					 <bean:write name="reqinfo" property="manpower"/>����
				</template:formTr>
	
	            <template:formTr name="�����ʱ">
					<bean:write name="reqinfo" property="usetime"/>Сʱ
				</template:formTr>
	            
	            <template:formTr name="ʩ����λ">
			         <bean:write name="reqinfo" property="contractorname"/>
			    </template:formTr>
			    
			     <template:formTr name="ʩ����ע">
				 	<bean:write name="reqinfo" property="workremark"/>
				 </template:formTr>
			    
			     <template:formTr name="ʩ������">
	                 <logic:empty name="reqinfo" property="workacce">
	                     	<apptag:listAttachmentLink fileIdList=""/>
	                 </logic:empty>
	                 <logic:notEmpty name="reqinfo" property="workacce">
	                    	<bean:define id="temw" name="reqinfo" property="workacce" type="java.lang.String"/>
						   	<apptag:listAttachmentLink fileIdList="<%=temw%>"/>
	                 </logic:notEmpty>
				</template:formTr>
			    
			    <tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                 </tr>
                
			     <template:formTr name="���ձ�ע">
	                    <bean:write name="reqinfo" property="acceptremark"/>
				 </template:formTr>
	            
			     <template:formTr name="���ո���">
	                 <logic:empty name="reqinfo" property="acceptacce">
	                     <apptag:listAttachmentLink fileIdList=""/>
	                 </logic:empty>
	                 <logic:notEmpty name="reqinfo" property="acceptacce">
	                    <bean:define id="tema" name="reqinfo" property="acceptacce" type="java.lang.String"/>
						<apptag:listAttachmentLink fileIdList="<%=tema%>"/>
	                 </logic:notEmpty>
				</template:formTr>
                 
                  <tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                 </tr>
                 
	             <template:formTr name="��ǰ״̬">
			      	<bean:write name="reqinfo" property="isarchive"/>
			    </template:formTr>
	
			    <template:formTr name="�鵵�ļ�">
	            	<logic:empty name="reqinfo" property="archives">
	                	<apptag:listAttachmentLink fileIdList=""/>
	                 </logic:empty>
	                 <logic:notEmpty name="reqinfo" property="archives">
	                    <bean:define id="temar" name="reqinfo" property="archives" type="java.lang.String"/>
						<apptag:listAttachmentLink fileIdList="<%=temar%>"/>
	                 </logic:notEmpty>
				</template:formTr>
			  </template:formTable>
	
			   <p align="center"><input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"></p>
		
	</logic:equal>


       <!--������ѯҳ��-->
	    <logic:equal value="au3" name="type"scope="session" >
	    	<script type="text/javascript" src="./js/json2.js"></script>
			<script type="text/javascript" src="./js/prototype.js"></script>
	    	<script type="text/javascript" language="javascript">
				//����..............
				function onSelChangeLevel() {
					var ops = document.getElementById('line');
					ops.options.length = 0;
					var clewOps = document.getElementById('clew');
					clewOps.options.length = 0;
					var newClewOp = document.createElement("option");
					newClewOp.value = "";
					newClewOp.text = "===ѡ�����ж���===";
					clewOps.add(newClewOp);
					var params = document.getElementById('level').value;
					if(params == "mention") {
						ops.options.add(new Option("",""));
					}
					var url = 'LineCutReAction.do?method=getLineByLevelId&levelId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callback, asynchronous:true});
				}
				
				function callback(originalRequest) {
					var ops = document.getElementById('line');
					var rst = originalRequest.responseText;
					var linearr = eval('('+rst+')');
					if(linearr.length == 0) {
						alert("�ü�����δ�ƶ���·!");
						return;
					}
					ops.options.add(new Option('===��ѡ����·===','mention'));
					for(var i = 0; i < linearr.length; i++) {
						ops.options.add(new Option(linearr[i].linename,linearr[i].lineid));
					}
					var myGlobalHandlers = {onCreate:function () {
					Element.show("Loadingimg");
					}, onFailure:function () {
						alert("�������ӳ������⣬��رպ�����!");
					}, onComplete:function () {
						if (Ajax.activeRequestCount == 0) {
							Element.hide("Loadingimg");
						}
					}};
					Ajax.Responders.register(myGlobalHandlers);
				}
				
				function onSelChangeLine() {
					var ops = document.getElementById('clew');
					var length = ops.length;
					ops.options.length = 0;//��������б�
					ops.options.add(new Option("===ѡ�����ж���===",""));
					var params = document.getElementById('line').value;
					if(params == "mention") {
						ops.options.add(new Option("",""));
					}
					var url = 'LineCutReAction.do?method=getClewByLineId&lineId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callbackToServer, asynchronous:true});
				}
				
				function callbackToServer(originalRequest) {
					var ops = document.getElementById('clew');
					var rst = originalRequest.responseText;
					var clewarr = eval('('+rst+')');
					if(clewarr.length == 0) {
						alert("��·����δ�ƶ��߶�!");
						return;
					}
					for(var i = 0; i < clewarr.length; i++) {
						ops.options.add(new Option(clewarr[i].sublinename,clewarr[i].sublineid));
					}
					var myGlobalHandlers = {onCreate:function () {
					Element.show("Loadingimg");
					}, onFailure:function () {
						alert("�������ӳ������⣬��رպ�����!");
					}, onComplete:function () {
						if (Ajax.activeRequestCount == 0) {
							Element.hide("Loadingimg");
						}
					}};
					Ajax.Responders.register(myGlobalHandlers);
				}
				
				function onSelectChangeClew() {
					var ops = document.getElementById('cutLineName');
					var length = ops.length;
					ops.options.length = 0;//��������б�
					ops.options.add(new Option("===ѡ����������===",""));
					var params = document.getElementById('clew').value;
					if(params == "mention") {
						ops.options.add(new Option("",""));
					}
					var url = 'LineCutReAction.do?method=getNameBySublineidAndDeptid&sublineId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callbackToServerByName, asynchronous:true});
				}
				
				function callbackToServerByName(originalRequest) {
					var ops = document.getElementById('cutLineName');
					var rst = originalRequest.responseText;
					var nameArr = eval('('+rst+')');
					if(nameArr.length == 0) {
						alert("��·����û�и�ӻ��߻�û�й鵵!");
						return;
					}
					for(var i = 0; i < nameArr.length; i++) {
						ops.options.add(new Option(nameArr[i].name,nameArr[i].name));
					}
				}
				
	    	</script>
	    	
	      	<br />
	        <template:titile value="���������Ҹ����Ϣ"/>
	        <html:form action="/LineCutWorkAction?method=doQueryForAr"   styleId="queryForm2" >
	        	<template:formTable namewidth="200"  contentwidth="200">

                  	
                  	<template:formTr name="��·����"  >
	            		<select name="level"   class="inputtext" style="width:180px" onchange="onSelChangeLevel()" id="level">
				        	<option  value="mention">===��ѡ����·����===</option>
			                	<logic:iterate id="linelevel" name="linelevelList">
			                		<bean:write name="linelevel" property="name"/>
			                    	<option value="<bean:write name="linelevel" property="code"/>"><bean:write name="linelevel" property="name"/></option>
			                	</logic:iterate>
			    		</select>
			    		<img id="Loadingimg" src="./images/ajaxtags/indicator.gif" style="display:none">
	                </template:formTr>
	                
	                <template:formTr name="��·����"  >
	            		<select class="inputtext" id="line" onchange="onSelChangeLine()" name="line" style="width: 180px;">
		  					<option></option>
			    		</select>
	                </template:formTr>
	                
	               
	              	<template:formTr name="�м̶���"  >
	            		<select name="sublineid"   class="inputtext" style="width:180px" id="clew" onchange="onSelectChangeClew()">
				        	<option value="">===ѡ�����ж���===</option>
			    		</select>
	                </template:formTr>
	                
	                 <template:formTr name="�������"  >
	            		<select name="name"   class="inputtext" style="width:180px" id="cutLineName">
				        	<option  value="">===ѡ�����и��===</option>
			    		</select>
	                </template:formTr>
	            
	                
					<input type="hidden" name="isarchive" value="�Ѿ��鵵" >
	                <template:formTr name="��ӿ�ʼʱ��">
	                	<input   id="begin" type="text" readonly="readonly"  name="begintime" class="inputtext" style="width:150" />
	                    <INPUT TYPE='BUTTON' VALUE='����'  readonly="readonly" ID='btn'  onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';" >
	                </template:formTr>
	                <template:formTr name="��ӽ���ʱ��">
	                	<input  id="end" type="text"  name="endtime" class="inputtext" style="width:150" />
	                    <INPUT TYPE='BUTTON' VALUE='����' ID='btn'  onclick="GetSelectDateTHIS('end')" STYLE="font:'normal small-caps 6pt serif';" >
	                </template:formTr>
	                <template:formSubmit>
						      	<td>
		                          <html:submit property="action" styleClass="button" >����</html:submit>
						      	</td>
						      	<td>
						       	 	<html:reset property="action" styleClass="button" >ȡ��</html:reset>
						      	</td>
		                      
						    </template:formSubmit>
	        	</template:formTable>
	    	</html:form>
	    </logic:equal>




        <!--��ʾ����ʩ���׶εĸ����Ϣ-->
    <logic:equal value="working1"scope="session" name="type">
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"  />
            <table width="100%" border="0" style="align:center" cellpadding="0" cellspacing="0"><tr><td valign="middle" align="center" class="title2">��ǰ�����Ϣ</td></tr></table>
    		<display:table name="sessionScope.reqinfo" id="currentRowObject"   pagesize="2">
            	<display:column property="contractorname" title="��ӵ�λ" maxLength="20" style="align:center"  sortable="true"/>
            	<display:column property="name" title="�������" maxLength="20" style="align:center"sortable="true"/>
                <display:column property="sublinename" title="�����߶�" maxLength="15"style="align:center"sortable="true"/>
                <display:column property="address" title="��ӵص�" maxLength="10"sortable="true"/>
                <display:column property="reason" title="���ԭ��" maxLength="13"sortable="true"/>
                <display:column property="isarchive" title="�����׶�" maxLength="10"sortable="true"/>

    		</display:table>
    </logic:equal>





	<iframe id="hiddenIframe" style="display:none"></iframe>
</body>

</html>
