<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>

<BR/>
<script language="javascript" type="">
///////////////////////////////示例代码////////////////////////////
    var comptype = 1;
    function onChangeInit(){
       document.all.cId.options.length=0;
       document.all.uId.options.length=0;
    }
    function onChangeInit12(){
       document.all.uId.options.length=0;
    }
//区域和代维
    function onChangeCon(){
      k=0;
      for( i=0;i<document.all.workID.options.length;i++){

             if(document.all.workID.options[i].text.substring(0,6)== document.all.rId.value){
                 k++;
                document.all.cId.options.length=k;
                document.all.cId.options[k-1].value=document.all.workID.options[i].value;
                document.all.cId.options[k-1].text=document.all.workID.options[i].text.substring(8,document.all.workID.options.length);
             }

      }
      if(k==0)
       document.all.cId.options.length=0;
     if(document.all.rId.value.substring(2,6)!='0000'){
         onChangeDeptUser();
     }else{
       document.all.uId.options.length=0;
     }

    }



    //    代维单位和巡检人
    function onChangeDeptUser()
    {
      var iArray = document.all.userID.options;   //所隐藏的数组
      var iIndex = 0;
      var iStart = 0; //
      var iEnd = iArray.length - 1;
      var iCount = 0;
      var iSeek = document.all.cId.value;       // 所要查找的值,上级菜单的值
      //alert("" + iSeek);
      while(iSeek != ""){
           iCount++;
         iIndex =parseInt( ( iStart + iEnd )/2);
        // alert("" + iArray[iIndex].text.substring(8,18));
         if( iArray[iIndex].text.substring(8,18) < iSeek ){ //iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
                iStart = iIndex;
         }else if( iArray[iIndex].text.substring(8,18) > iSeek ){//iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
                    iEnd = iIndex;
         }else{
             if(iIndex == 0){
                break;
            }else{
                if(iArray[iIndex-1].text.substring(8,18) == iSeek){ //iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
                       iEnd = iIndex;
                }else{
                    break;
                }
            }
        }
        if(iIndex==iEnd-1 && iArray[iEnd].text.substring(8,18) != iSeek){ //iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
            index = iArray.length;
            document.all.uId.options.length=0;
            break;
        }
      }

      k=0;
      ////iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
      //=document.all.cId.value 所要查找的值,上级菜单的值
      for( i=iIndex;i<iArray.length && iArray[i].text.substring(8,18)==document.all.cId.value;i++){
          k++;
          document.all.uId.options.length=k;   //document.all.uId.options 要设置的select


          document.all.uId.options[k-1].value=document.all.userID.options[i].value;
          document.all.uId.options[k-1].text=document.all.userID.options[i].text.substring(20);
      }
    }

   function settype(objN){
	var v = objN.value;
	if(v == "1"){
		comptype=1;
		patrolTr.style.display = "";
        endMonthTr.style.display="";
        startMonthTr.style.display="";
        theMonthTr.style.display="none";
	}

	if(v == "2"){
		comptype=2;
		patrolTr.style.display = "none";
        endMonthTr.style.display="none";
        startMonthTr.style.display="none";
        theMonthTr.style.display="";
	}
    document.all.thecomptype.value = comptype;
    }

    function checkvalidate(){
        if (patrolTr.style.display == "" && document.all.uId.options.length == 0){
           alert("巡检人或者巡检维护组不能为空");
           return false; 
        }
        if (startMonthTr.style.display == ""){
           if (document.all.endMonth.value <= document.all.startMonth.value){
              alert("终止月份一定要大于起始月份");
              return false;
           }
        }
        if (document.all.endMonth.value - document.all.startMonth.value >=6){
           alert("起止月份范围不能大于六个月");
           return false;
        }
        return true;
    } 


//////////////////////////////////////////////////////////////////////////////////////////////////
</script>
<Br/>
<%
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
%>

<%
if(userinfo.getType().equals( "11" )){%>
<body onload="onChangeInit()">
<% }else if (userinfo.getType().equals( "12" )){%>
<body onload="onChangeInit12()">
<% }else{%>
<body>
<%}%>
<logic:equal value="group" name="PMType">
   <template:titile value="巡检维护组对比分析"/>
</logic:equal>
<logic:notEqual value="group" name="PMType">
   <template:titile value="巡检员对比分析"/>
</logic:notEqual>
<html:form method="Post" action="/CompAnalysisAction?method=showPmCompAnalysis">
  <input  id="thecomptype"  name="thecomptype" type="hidden" value="1"/>
  <template:formTable contentwidth="200" namewidth="300">
        <template:formTr name="对比类型" >
	    	<input  type="radio"  name="comptype" value="1"  checked="checked" onclick="settype(this)" /> 纵向对比
	        <input  type="radio"  name="comptype" value="2" onclick="settype(this)" /> 横向对比
        </template:formTr>
        <logic:notEmpty name="reginfo">
          <template:formTr name="所属区域" isOdd="false">
            <select name="regionID" class="inputtext" style="width:180px" id="rId"  onchange="onChangeCon()"  >
              <option value="">请选择...</option>
              <logic:present name="reginfo">
                <logic:iterate id="reginfoId" name="reginfo">
                  <option value="<bean:write name="reginfoId" property="regionid"/>">
                  <bean:write name="reginfoId" property="regionname"/>
                  </option>
                </logic:iterate>
              </logic:present>
            </select>
          </template:formTr>
        </logic:notEmpty>
        
            
        <logic:notEmpty name="coninfo">
          <template:formTr name="代维单位" tagID="conTr">
            <select name="contractorID" class="inputtext" style="width:180px" id="cId" onchange="onChangeDeptUser()">
              <logic:equal value="12" name="LOGIN_USER" property="type">
                 <option value="">请选择...</option>
              </logic:equal>
              <logic:present name="coninfo">
                <logic:iterate id="coninfoId" name="coninfo">
                  <option value="<bean:write name="coninfoId" property="contractorid"/>">
                  <bean:write name="coninfoId" property="contractorname"/></option>
                </logic:iterate>
              </logic:present>
            </select>
          </template:formTr>
          <logic:present name="coninfo">
              <select name="workID" style="display:none">
                <logic:iterate id="coninfoId" name="coninfo">
                    <option value="<bean:write name="coninfoId" property="contractorid"/>"><bean:write name="coninfoId" property="regionid"/>--<bean:write name="coninfoId" property="contractorname"/></option>
                </logic:iterate>
              </select>
          </logic:present>
        </logic:notEmpty>

    <logic:equal value="group" name="PMType">
    <template:formTr name="巡检维护组" tagID="patrolTr" isOdd="false">
      <select name="patrolID" class="inputtext" style="width:180px" id="uId">
        <logic:present name="uinfo">
          <logic:iterate id="uinfoId" name="uinfo">
            <option value="<bean:write name="uinfoId" property="patrolid"/>">
              <bean:write name="uinfoId" property="patrolname"/>
            </option>
          </logic:iterate>
        </logic:present>
      </select>
    </template:formTr>
    </logic:equal>

    <logic:notEqual value="group" name="PMType">
    <template:formTr name="巡 检 人" tagID="patrolTr" isOdd="false">
      <select name="patrolID" class="inputtext" style="width:180px" id="uId">
        <logic:present name="uinfo">
          <logic:iterate id="uinfoId" name="uinfo">
            <option value="<bean:write name="uinfoId" property="patrolid"/>">
              <bean:write name="uinfoId" property="patrolname"/>
            </option>
          </logic:iterate>
        </logic:present>
      </select>
    </template:formTr>
    </logic:notEqual>

   <logic:present name="uinfo">
      <select name="userID" style="display:none">
        <logic:iterate id="uinfoId" name="uinfo">
            <option value="<bean:write name="uinfoId" property="patrolid"/>"><bean:write name="uinfoId" property="regionid"/>--<bean:write name="uinfoId" property="parentid"/>--<bean:write name="uinfoId" property="patrolname"/></option>
        </logic:iterate>
      </select>
   </logic:present>

    <template:formTr name="年&nbsp;&nbsp;&nbsp;&nbsp;份" isOdd="false">
        <apptag:getYearOptions/>
        <html:select property="endYear" styleClass="inputtext" style="width:180">
          <html:options collection="yearCollection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
      <template:formTr tagID="startMonthTr" name="起始月份" isOdd="false">
        <html:select property="startMonth" styleClass="inputtext" style="width:180">
          <html:option value="01">一月</html:option>
          <html:option value="02">二月</html:option>
          <html:option value="03">三月</html:option>
          <html:option value="04">四月</html:option>
          <html:option value="05">五月</html:option>
          <html:option value="06">六月</html:option>
          <html:option value="07">七月</html:option>
          <html:option value="08">八月</html:option>
          <html:option value="09">九月</html:option>
          <html:option value="10">十月</html:option>
          <html:option value="11">十一月</html:option>
          <html:option value="12">十二月</html:option>
        </html:select>
      </template:formTr>
      <template:formTr tagID="endMonthTr" name="终止月份" isOdd="false">
        <html:select property="endMonth" styleClass="inputtext" style="width:180">
          <html:option value="01">一月</html:option>
          <html:option value="02">二月</html:option>
          <html:option value="03">三月</html:option>
          <html:option value="04">四月</html:option>
          <html:option value="05">五月</html:option>
          <html:option value="06">六月</html:option>
          <html:option value="07">七月</html:option>
          <html:option value="08">八月</html:option>
          <html:option value="09">九月</html:option>
          <html:option value="10">十月</html:option>
          <html:option value="11">十一月</html:option>
          <html:option value="12">十二月</html:option>
        </html:select>
      </template:formTr>
      
      <template:formTr tagID="theMonthTr" name="月&nbsp;&nbsp;&nbsp;&nbsp;份" isOdd="false" style="display:none">
        <html:select property="theMonth" styleClass="inputtext" style="width:180">
          <html:option value="01">一月</html:option>
          <html:option value="02">二月</html:option>
          <html:option value="03">三月</html:option>
          <html:option value="04">四月</html:option>
          <html:option value="05">五月</html:option>
          <html:option value="06">六月</html:option>
          <html:option value="07">七月</html:option>
          <html:option value="08">八月</html:option>
          <html:option value="09">九月</html:option>
          <html:option value="10">十月</html:option>
          <html:option value="11">十一月</html:option>
          <html:option value="12">十二月</html:option>
        </html:select>
      </template:formTr>

    <template:formSubmit>
      <td>
        <html:submit styleClass="button" onclick="return checkvalidate()">查询</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>

 <div align="center">
 	  <table width="620" border="0" align="center">
        <tr style="color:red">
          <td width="7%" scope="col" valign="top">说明：</td>
          <logic:equal value="group" name="PMType"> 
            <td width="94%" scope="col">
              1、纵向对比:比较某一具体巡检维护组在选定起止月份范围内(不能超过六个月)各个月巡检率及实际巡检点次.<br>
              2、横向对比:比较某个月某一具体代维公司下所有巡检维护组的巡检率及实际巡检点次.<br> 
		    </td>
		  </logic:equal>
		  <logic:notEqual value="group" name="PMType"> 
            <td width="94%" scope="col">
              1、纵向对比:比较某一具体巡检员在选定起止月份范围内(不能超过六个月)各个月巡检率及实际巡检点次.<br>
              2、横向对比:比较某个月某一具体代维公司下所有巡检员的巡检率及实际巡检点次.<br> 
		    </td>
		  </logic:notEqual>
        </tr>
      </table>
   </div>
</body>