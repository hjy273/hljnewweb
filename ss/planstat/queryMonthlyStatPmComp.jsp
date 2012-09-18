<%@include file="/common/header.jsp"%>
<script language="javascript" type="">
function showV(){
document.getElementById("compAnalysis").src="${ctx}/ShowPmMonthlyCompVChart";
}
function showH(){
document.getElementById("compAnalysis").src="${ctx}/ShowPmMonthlyCompHChart";
}
</script>
<Br/>

<body onload="showV()">
<logic:equal value="group" name="PMType">
   <template:titile value="巡检维护组对比分析"/>
</logic:equal>
<logic:notEqual value="group" name="PMType">
   <template:titile value="巡检员对比分析"/>
</logic:notEqual>


	<table width="500" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout"><tr><th class="thlist" align="center" width="300">项目</th><th class="thlist" align="center" width="200">选择</th></tr>
      <template:formTr name="对比类型" >
   	<input  type="radio"  name="comptype" value="1" checked="checked" onclick="showV()"/> 纵向对比
       <input  type="radio"  name="comptype" value="2" onclick="showH()"/> 横向对比
      </template:formTr>
    </table>

 <div align="center">
 	  <table width="620" border="0" align="center">
        <tr style="color:red">
          <td width="7%" scope="col" valign="top">说明：</td>
          <logic:equal value="group" name="PMType"> 
            <td width="94%" scope="col">
              1、纵向对比:比较某一具体巡检维护组在选定月份及向前邻近两个月共三个月的巡检考核.<br>
              2、横向对比:比较某一具体巡检维护组在选定月份与其所在同一家代维公司下所有巡检维护组的巡检考核.<br> 
		    </td>
		  </logic:equal>
		  <logic:notEqual value="group" name="PMType"> 
            <td width="94%" scope="col">
              1、纵向对比:纵向对比:比较某一具体巡检员在选定月份及向前邻近两个月共三个月的巡检考核.<br>
              2、横向对比:比较某一具体巡检员在选定月份与其所在同一家代维公司下所有巡检员的巡检考核.<br> 
		    </td>
		  </logic:notEqual>
        </tr>
      </table>
   </div>
   
 
   <div>
       <iframe id="compAnalysis" marginWidth="0" marginHeight="0" frameBorder=0 width="100%" scrolling=auto height="100%"> </iframe>
   </div> 

</body>