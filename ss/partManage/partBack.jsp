<%@include file="/common/header.jsp"%>
<html>
<head>
<script type="text/javascript" src="${ctx}/js/dojo.js"></script>
	<script type="text/javascript">
		dojo.require("dojo.widget.ComboBox");
		
		function OnbackStockSelect(){
        	var obj = dojo.widget.byId("backCbb").getValue();
       
        	if(obj.value==""){
            	alert("��û��ѡ��,�����˿�!!");
            	return false;
        	}
        	document.getElementById("useid").value = obj;        	
        	backForm.submit();
    	}
	</script>
	<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>

   <br/>
  <template:titile value="�����˿⴦��"/>
   <html:form action="/PartUseAction?method=showBackPart" styleId="backForm">
      <table align="center" width="550" border="0">
          <tr>
              <td height="25"><b>��ѡ�������;:</b></td>
              <td>
              	<input type="hidden" id="useid" name="useid" value=""/>
                  <select  dojoType="ComboBox" dataUrl="${ctx}/PartUseAction.do?method=showBackReason"
					style="width: 350px;" id="backCbb" name="name" maxListLength="20" pageSize="40" searchAttr="name"> 
				 </select>
              </td>
          </tr>
          <tr>
              <td colspan="2" align="center">
                  <br/><html:button property="action" styleClass="button" onclick="OnbackStockSelect()" >��һ��</html:button>
                <!-- <html:button property="action" styleClass="button" onclick="javascript:history.back()" >����	</html:button> -->  
              </td>
          </tr>
      </table>
  </html:form>
</body>
</html>        