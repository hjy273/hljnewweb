<%@include file="/common/header.jsp"%>
<html>
<head>
<title>main</title>
<script type="" language="javascript">
    function conShow(){
          if(document.all.deptypeId.value=='2'){
          conTr.style.display="";
          k=0;
          for( i=0;i<document.all.workID.options.length;i++){
             if(document.all.workID.options[i].text.substring(0,6)== document.all.reId.value){
                 k++;
                document.all.dId.options.length=k;
                document.all.dId.options[k-1].value=document.all.workID.options[i].value;
                document.all.dId.options[k-1].text=document.all.workID.options[i].text.substring(8,document.all.workID.options.length);
             }
           }
        }
        else
            conTr.style.display="none";
    }

    function regionChange(){
        conShow();
    }
</script>
</head>
<body bgcolor="#ffffff">
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
    <div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'>��������Ա��½</div>
    <form name="userInfoBean" method="post" action="${ctx}/login.do?method=superLogin" id="addForm">
    <table width="320" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
      <tr>
        <th class="thlist" align="center" width="120">��Ŀ</th>
        <th class="thlist" align="center" width="180">��д</th>
      </tr>
      <tr class=trcolor>
        <td class="tdulleft">����Ա���ƣ�</td>
        <td class="tdulright">
          <input type="text" name="userID" maxlength="16" value="" style="width:150;" class="inputtext">
        </td>
      </tr>
      <tr class=trcolor>
        <td class="tdulleft">����Ա���룺</td>
        <td class="tdulright">
          <input type="text" name="password" maxlength="16" value="" style="width:150;" class="inputtext">
        </td>
      </tr>

      <tr class=trcolor>
        <td class="tdulleft">����������</td>
        <td class="tdulright">
            <logic:present name="lRegion">
                <select name="regionid" class="selecttext" id="reId" style="width:150" onchange="regionChange()">
                     <logic:iterate id="lRegionID" name="lRegion">
                        <option  value="<bean:write name="lRegionID" property="regionid"/>"><bean:write name="lRegionID" property="regionname"/></option>
                     </logic:iterate>
                  </select>
            </logic:present>

        </td>
      </tr>
        <tr class=trcolor>
        <td class="tdulleft">����Ա��ݣ�</td>
        <td class="tdulright">
          <select name="deptype"  id="deptypeId" class="selecttext" onchange="conShow()" style="width:150">
              <option value="1"  selected="selected">�ƶ���˾</option>
            <option value="2">��ά��˾</option>
          </select>
        </td>
      </tr>
      <tr class=trcolor id="conTr" style="display:none">
        <td class="tdulleft">��ά��˾���ƣ�</td>
        <td class="tdulright">
          <select name="deptID" id="dId" class="selecttext" style="width:150">
              <option value="1">ddsfds</option>
          </select>

          <logic:present name="lContractor">
              <select name="workID"   style="display:none">
                <logic:iterate id="conID" name="lContractor">
                    <option value="<bean:write name="conID" property="contractorid"/>"><bean:write name="conID" property="regionid"/>--<bean:write name="conID" property="contractorname"/></option>
                </logic:iterate>
              </select>
          </logic:present>

        </td>
      </tr>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="6">      </td>
        </tr>
        <tr>
          <td align="right">
            <table border="0" align="center" cellpadding="0" cellspacing="0">
              <tr align="center">
                <td>
                  <input type="submit" name="action" value="��½ϵͳ"  class="button">
                </td>
                <td>
                  <input type="reset" name="action" value="ȡ��" class="button">
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </table>


    </form>
</body>
</html>
