<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<template:titile value="�߶����·���ȷ��ҳ��" />
<script language="javascript">

	//    ��ά��λ��Ѳ����
    function onChangeDeptUser()
    {
      var iArray = document.all.userID.options;   //�����ص�����
      var iIndex = 0;
      var iStart = 0; //
      var iEnd = iArray.length - 1;
      var iCount = 0;
      var iSeek = document.all.cId.value;       // ��Ҫ���ҵ�ֵ,�ϼ��˵���ֵ
      //alert("" + iSeek);
      while(iSeek != ""){
           iCount++;
         iIndex =parseInt( ( iStart + iEnd )/2);
        // alert("" + iArray[iIndex].text.substring(8,18));
         if( iArray[iIndex].text.substring(8,18) < iSeek ){ 
                iStart = iIndex;
         }else if( iArray[iIndex].text.substring(8,18) > iSeek ){
                    iEnd = iIndex;
         }else{
             if(iIndex == 0){
                break;
            }else{
                if(iArray[iIndex-1].text.substring(8,18) == iSeek){
                       iEnd = iIndex;
                }else{
                    break;
                }
            }
        }
        if(iIndex==iEnd-1  ){
        	
        if(iArray[iEnd].text.substring(8,18) != iSeek){ 
            index = iArray.length;
            document.all.uId.options.length=0;
            break;
        }else{
        	//alert("safads");
        	iIndex = iEnd;
        	break;
        }
        }
      }

      k=0;
      ////iArray[iIndex].text.substring(8,18) ,Ҫ�滻���¼��˵��ıȽ�����
      //=document.all.cId.value ��Ҫ���ҵ�ֵ,�ϼ��˵���ֵ
      for( i=iIndex;i<iArray.length && iArray[i].text.substring(8,18)==document.all.cId.value;i++){
          k++;
          document.all.uId.options.length=k;   //document.all.uId.options Ҫ���õ�select


          document.all.uId.options[k-1].value=document.all.userID.options[i].value;
          document.all.uId.options[k-1].text=document.all.userID.options[i].text.substring(20);
      }
    }
	function check(){
		var patrolID = document.all.uId.value;
		if(patrolID != ""){
			return true;
		}else{
			alert("��ѡ��Ѳ����");
			return false;
		}
		
	}
	function affirm(){
		document.all.btt.disabled=false;
	}
</script>
<body>
<html:form method="Post" action="/ResourceAssignAction.do?method=assignSubline">
<div align="center"> 
	<br>�� <apptag:setSelectOptions valueName="connCollection" tableName="contractorinfo" columnName1="contractorname" region="true" columnName2="contractorid" order="contractorname" />
     <html:select property="contractor" name="RABean" disabled="true" styleClass="inputtext" styleId="rId" style="width:100" >
        <html:options collection="connCollection" property="value" labelProperty="label"/>
     </html:select>			
	
		�� <select name="contractorID" class="inputtext" style="width:100px" id="cId" onchange="onChangeDeptUser()">
              <option value="">��ѡ��...</option>
              <logic:present name="coninfo">
                <logic:iterate id="coninfoId" name="coninfo">
                  <option value="<bean:write name="coninfoId" property="contractorid"/>">
                  <bean:write name="coninfoId" property="contractorname"/></option>
                </logic:iterate>
              </logic:present>
            </select>
            <logic:present name="coninfo">
              <select name="workID" style="display:none">
                <logic:iterate id="coninfoId" name="coninfo">
                    <option value="<bean:write name="coninfoId" property="contractorid"/>"><bean:write name="coninfoId" property="regionid"/>--<bean:write name="coninfoId" property="contractorname"/></option>
                </logic:iterate>
              </select>
          </logic:present>
          <select name="patrolID" class="inputtext" style="width:100px" id="uId">
	      </select>
	   <logic:present name="uinfo">
	      <select name="userID" style="display:none">
	        <logic:iterate id="uinfoId" name= "uinfo">
	            <option value="<bean:write name="uinfoId" property="patrolid"/>"><bean:write name="uinfoId" property="regionid"/>--<bean:write name="uinfoId" property="parentid"/>--<bean:write name="uinfoId" property="patrolname"/></option>
	        </logic:iterate>
	      </select>
	   </logic:present>
</div><br>
	<div align="center"><input type="submit" class="button" value="����" disabled id="btt" onclick="return check()">
	 <html:reset styleClass="button">ȡ��</html:reset>
	</div>
</html:form>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<div align="left" style="color:red"> * ����Ϊ�������߶Σ��� <a href="javascript:affirm()"> ȷ��</a></div>
<display:table name="requestScope.queryresult" id="currentRowObject">
  <display:column title="���"><c:out value="${currentRowObject_rowNum}"/></display:column>
  <display:column property="sublinename" title="�߶�"/>
  <display:column property="linename" title="��·"/>
  <display:column property="linetype" title="����"/>
  <display:column property="patrolname" title="������"/>
</display:table>
</body>