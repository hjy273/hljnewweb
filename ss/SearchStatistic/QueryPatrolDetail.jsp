<%@include file="/common/header.jsp"%>
<br>
<template:titile value="Ѳ����ϸ��ѯ"/>
<html:form method="Post" action="/StatisticAction.do?method=getPatrolDetail" onSubmit="return subClick()">
<script language="JavaScript" type="text/javascript">

var val = 1;
var userType = 11;
function checkR(obj){
	var r = 0;
	//var obj=document.getElementsByName("cyc");
	for(var i=0;i<obj.length;i++)
	{
		if(obj[i].checked)
		{
		r = obj[i].value;
		}
	}
	return r;
}

function subClick(){
    var rtype = checkR(document.getElementsByName("queryby"));
    if(rtype == '1'){
    	var did = queryConditionForm.deptid.options[queryConditionForm.deptid.selectedIndex].value;
		if(did == ""){
			alert("��ѡ���ά��λ��");
			return false;
		}
    }
    if(rtype == '2'){
    	var pid = queryConditionForm.patrolid.options[queryConditionForm.patrolid.selectedIndex].value;
		if(pid == ""){
			alert("��ѡ��Ѳ���ˣ�");
			return false;
		}
	}
	if(rtype == '3'){
    	var lid = queryConditionForm.sublineid.options[queryConditionForm.sublineid.selectedIndex].value;
		if(lid == ""){
			alert("��ѡ�����Ѳ����·��");
			return false;
		}
	}
	//��ʼʱ��
    var yb = queryConditionForm.begindate.value.substring(0,4);
	var mb = parseInt(queryConditionForm.begindate.value.substring(5,7) ,10);
	var db = parseInt(queryConditionForm.begindate.value.substring(8,10),10);
    //����ʱ��
    var ye = queryConditionForm.enddate.value.substring(0,4);
	var me = parseInt(queryConditionForm.enddate.value.substring(5,7) ,10);
	var de = parseInt(queryConditionForm.enddate.value.substring(8,10),10);
	if( queryConditionForm.begindate.value == "" ){
      alert("û�п�ʼ����!");
      return false;
    }
    if( queryConditionForm.enddate.value == "" ){
      alert("û�н�������!");
      return false;
    }
    if(yb!=ye){
      alert("��֧�ֿ����ѯ!");
      //document.all.item(strID).value="";
      return false;
    }
    if(mb!=me){
      alert("��֧�ֿ��²�ѯ!");
      //document.all.item(strID).value="";
      return false;
    }
	if(de < db || me < mb || ye < yb){
      alert("�������ڲ���С�ڿ�ʼ����!");
      //document.all.item(strID).value="";
      //queryConditionForm.enddate.focus();
      return false;
    }
    if(val==1){
    	document.all.pID.options[document.all.pID.selectedIndex].value="";
    }
     if(val==3){
    	document.all.pID.options[document.all.pID.selectedIndex].value="";
    }

}

 	function settype(flag){

		//var v = objN.value;
		if(flag==1){
			val=1;
			patrolmanTr.style.display = "none";
			contractorTr.style.display = "";
			sublineTr.style.display = "none";

			queryConditionForm.queryby.value = "contractor";
			conShow();

		}

		if(flag==2){
			val=2;
			patrolmanTr.style.display = "";
			if(document.getElementById("contractorTr")){
			contractorTr.style.display = "";
			}
			sublineTr.style.display = "none";
	
			queryConditionForm.queryby.value = "patrol";
			if(userType != 22){
				conShow();
				onChangeExe();
			}
		}

		if(flag==3){
			val = 3;
			patrolmanTr.style.display = "none";
			if(document.getElementById("contractorTr")){
			contractorTr.style.display = "none";
			}
			sublineTr.style.display = "";

			queryConditionForm.queryby.value = "line";
			if(userType == 22){
				showLine();
			}else
				conShow();
		}

	}
	function showLine(){
		var iArray = document.all.lLine.options;   //�����ص�����
    	//var iSeek = document.all.reId.value;       // ��Ҫ���ҵ�ֵ,�ϼ��˵���ֵ
        //var to_Copy = document.all.lID.options;
        var iIndex = 0;
        k=1;
        for( i=iIndex;i<iArray.length;i++){
          	k++;
          	document.all.lID.options.length=k;   //document.all.pID.options Ҫ���õ�select

          	document.all.lID.options[0].value="";
          	document.all.lID.options[0].text="��ѡ��Ѳ����·";

        	document.all.lID.options[k-1].value=document.all.lLine.options[i].value;
          	document.all.lID.options[k-1].text=document.all.lLine.options[i].text.substring(8,document.all.lLine.options[i].text.length);
     	}

	}
	function conShow(){
	//alert("val"+val);
          if(val == 3){
          		//������ѡ�������ڵ���·

          		if(document.all.reId.options[document.all.reId.selectedIndex].value.substring(2,6)=='0000'){
           			document.all.lID.options.length=1;
           			document.getElementById("lID").options[0].value='';
                	document.getElementById("lID").options[0].text='��ѡ������';
					return true;
          		}

          		var iArray = document.all.lLine.options;   //�����ص�����
    			var iSeek = document.all.reId.value;       // ��Ҫ���ҵ�ֵ,�ϼ��˵���ֵ
          		var to_Copy = document.all.lID.options;
          		var iIndex = find(iArray,iSeek,to_Copy);
          		k=1;
          		for( i=iIndex;i<iArray.length && iArray[i].text.substring(0,6)==document.all.reId.value;i++){
          			k++;
          			document.all.lID.options.length=k;   //document.all.pID.options Ҫ���õ�select

          			document.all.lID.options[0].value="";
          			document.all.lID.options[0].text="��ѡ��Ѳ����·";

          			document.all.lID.options[k-1].value=document.all.lLine.options[i].value;
          			document.all.lID.options[k-1].text=document.all.lLine.options[i].text.substring(8,document.all.lLine.options[i].text.length);
     			}
          	return;
          }
          //������ѡ���ά��λ
          if(document.all.reId.options[document.all.reId.selectedIndex].value.substring(2,6)=='0000'){
           		document.all.wID.options.length=1;
           		document.getElementById("wID").options[0].value='';
                document.getElementById("wID").options[0].text='��ѡ������';
				return true;
          }
          k=0;
          for( i=0;i<document.all.dId.options.length;i++){
                if(document.all.dId.options[i].text.substring(0,6)== document.all.reId.value){
                k++;
                document.getElementById("wID").disabled="";
                if(k==1){
                	document.all.wID.options.length=k;
                	document.all.wID.options[k-1].value='';
                	document.all.wID.options[k-1].text='��ѡ���ά��λ';
                	k++;
                }
                document.all.wID.options.length=k;
                document.all.wID.options[k-1].value=document.all.dId.options[i].value;
                document.all.wID.options[k-1].text=document.all.dId.options[i].text.substring(8,document.all.dId.options[i].text.length);
             }
        }
        if(k==0){
        	document.all.wID.options.length=1;
            document.all.wID.options[0].value='';
            document.all.wID.options[0].text='�޴�ά��λ';
        }

    }

    function regionChange(){
        conShow();
    }
    function onChangeExe(){
    if(val != 2){
    	return;
    }
    if(document.all.wID.value==""){
    	document.all.pID.options.length=1;
    	document.all.pID.options[0].value='';
        document.all.pID.options[0].text="��ѡ��Ѳ����";
        return ;

    }
    var iArray = document.all.exeId.options;   //�����ص�����
      var iIndex = 0;
      var iStart = 0; //
      var iEnd = iArray.length - 1;
	//var iEnd = iArray.length;
      var iCount = 0;
      var iSeek = document.all.wID.value;       // ��Ҫ���ҵ�ֵ,�ϼ��˵���ֵ
      while(iSeek != ""){
           iCount++;
         iIndex =parseInt( ( iStart + iEnd )/2);

         if( iArray[iIndex].text.substring(8,18) < iSeek ){ //iArray[iIndex].text.substring(8,18) ,Ҫ�滻���¼��˵��ıȽ�����
                iStart = iIndex;
         }else if( iArray[iIndex].text.substring(8,18) > iSeek ){//iArray[iIndex].text.substring(8,18) ,Ҫ�滻���¼��˵��ıȽ�����
                    iEnd = iIndex;
         }else{
             if(iIndex == 0){
                break;
            }else{
                if(iArray[iIndex-1].text.substring(8,18) == iSeek){ //iArray[iIndex].text.substring(8,18) ,Ҫ�滻���¼��˵��ıȽ�����
                       iEnd = iIndex;
                }else{
                    break;
                }
            }
        }
        //if(iIndex==iEnd-1 && iArray[iEnd].text.substring(8,18) != iSeek){ //iArray[iIndex].text.substring(8,18) ,Ҫ�滻���¼��˵��ıȽ�����
        //   index = iArray.length;
        //    document.all.pID.options.length=0;
        //    break;
        //}
	if(iIndex==iEnd-1){
        	if(iArray[iEnd].text.substring(8,18) == iSeek){ //iArray[iIndex].text.substring(8,18) ,Ҫ�滻���¼��˵��ıȽ�����
            	iIndex = iEnd;
            	//alert("==")
            	break;
        	}else{
        	//	alert("!="+iArray.length)
        		index = iArray.length;
         	   document.all.pID.options.length=0;
          	  break;
       	 	}
       	}
      }

      k=1;
      ////iArray[iIndex].text.substring(8,18) ,Ҫ�滻���¼��˵��ıȽ�����
      //=document.all.wID.value ��Ҫ���ҵ�ֵ,�ϼ��˵���ֵ
      for( i=iIndex;i<iArray.length && iArray[i].text.substring(8,18)==document.all.wID.value;i++){
          k++;
          document.all.pID.options.length=k;   //document.all.pID.options Ҫ���õ�select

          document.all.pID.options[0].value="";
          document.all.pID.options[0].text="��ѡ��Ѳ����";

          document.all.pID.options[k-1].value=document.all.exeId.options[i].value;
          document.all.pID.options[k-1].text=document.all.exeId.options[i].text.substring(20,document.all.exeId.options[i].text.length);
      }
    }
    function find(iArray,iSeek,to_Copy){
    	//var iArray = document.all.exeId.options;   //�����ص�����
    	//var iSeek = document.all.wID.value;       // ��Ҫ���ҵ�ֵ,�ϼ��˵���ֵ
      var iIndex = 0;
      var iStart = 0; //
      var iEnd = iArray.length - 1;
      
      var iCount = 0;

      while(iSeek != ""){
           iCount++;
         iIndex =parseInt( ( iStart + iEnd )/2);

         if( iArray[iIndex].text.substring(0,6) < iSeek ){ //iArray[iIndex].text.substring(8,18) ,Ҫ�滻���¼��˵��ıȽ�����
                iStart = iIndex;
         }else if( iArray[iIndex].text.substring(0,6) > iSeek ){//iArray[iIndex].text.substring(8,18) ,Ҫ�滻���¼��˵��ıȽ�����
                iEnd = iIndex;
         }else{
             if(iIndex == 0){
                break;
            }else{
                if(iArray[iIndex-1].text.substring(0,6) == iSeek){ //iArray[iIndex].text.substring(8,18) ,Ҫ�滻���¼��˵��ıȽ�����
                       iEnd = iIndex;
                }else{
                    break;
                }
            }
        }
        if(iIndex==iEnd-1 && iArray[iEnd].text.substring(0,6) != iSeek){ //iArray[iIndex].text.substring(8,18) ,Ҫ�滻���¼��˵��ıȽ�����
            index = iArray.length;
            to_Copy.length=0;
            //document.all.pID.options.length=0;
            break;
        }
      }
      return iIndex;
    }
	function setDisplay(){
		regTr.style.display = "none";
	}
	function setChecked(){
		userType = 22;
		document.all.r1.checked = "true";
		settype(2);
	}
</script>

<logic:equal value="21" name="LOGIN_USER" property="type">
	<body onload="conShow();onChangeExe();">
</logic:equal>
<logic:equal value="22" name="LOGIN_USER" property="type">
	<body onload="setChecked();">
</logic:equal>
<logic:equal value="11" name="LOGIN_USER" property="type">
	<body onload="conShow();onChangeExe();">
</logic:equal>
<logic:equal value="12" name="LOGIN_USER" property="type">
	<body onload="conShow();onChangeExe();setDisplay();">
</logic:equal>

<template:formTable namewidth="200" contentwidth="300">
    <template:formTr name="��ѯ����" isOdd="false">
    <logic:notEqual value="22" name="LOGIN_USER" property="type">
        	<input type="radio" name="queryby" value="1" onclick="settype(1)" checked/>����ά��λ
    </logic:notEqual>
        <logic:equal value="group" name="PMType">
        	<input type="radio" name="queryby" value="2" id='r1' onclick="settype(2)"/>��Ѳ��ά����
        </logic:equal>
        <logic:notEqual value="group" name="PMType">
        	<input type="radio" name="queryby" value="2" id='r1'onclick="settype(2)"/>��Ѳ����
        </logic:notEqual>
        <input type="radio" name="queryby" value="3" onclick="settype(3)"/>���߶�
    </template:formTr>
	<logic:notEqual value="22" name="LOGIN_USER" property="type">
	<template:formTr name="��������" isOdd="false" tagID="regTr" style="display:">
		<logic:present name="lRegion">
			<select name="regionid" class="selecttext" id="reId" style="width:225" onchange="regionChange()">
				<logic:iterate id="lRegionID" name="lRegion">
					<option value="<bean:write name="lRegionID" property="regionid"/>">
						<bean:write name="lRegionID" property="regionname" />
					</option>
				</logic:iterate>
			</select>
		</logic:present>
	</template:formTr>

	<template:formTr name="��ά��λ" isOdd="false" tagID="contractorTr" style="display:">
		<select name="deptid" class="selecttext" style="width:225" id="wID" onchange="onChangeExe()">
			<option> </option>
		</select>
		<logic:present name="lCon">
			<select name="dId" style="display:none">
			<logic:iterate id="lConID" name="lCon">
				<option value="<bean:write name="lConID" property="contractorid"/>">
					<bean:write name="lConID" property="regionid" />--<bean:write name="lConID" property="contractorname" />
				</option>
			</logic:iterate>
			</select>
		</logic:present>
	</template:formTr>
	</logic:notEqual>
	<template:formTr name="Ѳ �� ��" tagID="sublineTr" style="display:none">
		<select name="sublineid" class="selecttext" style="width:225" id="lID">
			<option> </option>
		</select>
		<logic:present name="lsubline">
			<select name="lLine" style="display:none">
				<logic:iterate id="lsublineId" name="lsubline">
					<option value="<bean:write name="lsublineId" property="sublineid"/>">
						<bean:write name="lsublineId" property="regionid" />--<bean:write name="lsublineId" property="sublinename" />
						</option>
				</logic:iterate>
			</select>
		</logic:present>
	</template:formTr>

    <logic:equal value="group" name="PMType">
    		<template:formTr name="Ѳ �� ��" tagID="patrolmanTr" style="display:none">
		        <select name="patrolid" Class="inputtext" style="width:225"  id='pID'>
			            <logic:present name="lpatrol">
			            	<logic:iterate id="lpatrolId" name="lpatrol">
			                	<option value="<bean:write name="lpatrolId" property="patrolid"/>"><bean:write name="lpatrolId" property="patrolname"/></option>
			            	</logic:iterate>
			            </logic:present>
		        <select>
		    </template:formTr>
    </logic:equal>
    <logic:notEqual value="group" name="PMType">
		    <template:formTr name="Ѳ �� Ա" tagID="patrolmanTr" style="display:none">
		        <select name="patrolid" Class="inputtext" style="width:225" id='pID'>
						<option value="">��ѡ��Ѳ����</option>
			            <logic:present name="lpatrol">
			            	<logic:iterate id="lpatrolId" name="lpatrol">
			                	<option value="<bean:write name="lpatrolId" property="patrolid"/>"><bean:write name="lpatrolId" property="patrolname"/></option>
			            	</logic:iterate>
			            </logic:present>
		        <select>
		    </template:formTr>
    </logic:notEqual>
    <logic:present name="lpatrol">
		<select name="exeId" style="display:none" id="exeId">
			<logic:iterate id="pgId" name="lpatrol">
				<option value="<bean:write name="pgId" property="patrolid"/>">
					<bean:write name="pgId" property="regionid" />--<bean:write name="pgId" property="parentid" />--<bean:write name="pgId" property="patrolname" />
				</option>
			</logic:iterate>
		</select>
	</logic:present>

    <template:formTr name="��ʼʱ��" isOdd="false">
        <html:text property="begindate" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:200" readonly="true" />
    </template:formTr>
    <template:formTr name="��ֹʱ��">
        <html:text property="enddate" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:200" readonly="true" />
    </template:formTr>
</template:formTable>
<template:formSubmit>
    <td width="85">
        <html:submit property="action" styleClass="button">��ѯ</html:submit>
    </td>
    <td width="85">
        <html:reset property="action" styleClass="button">ȡ��</html:reset>
    </td>
</template:formSubmit>
</html:form>
