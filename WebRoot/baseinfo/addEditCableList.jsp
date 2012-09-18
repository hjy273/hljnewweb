<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>

<script language="javascript">
<!--
function isValidForm() {

	if(document.sublineBean.subLineName.value==""){
		alert("名称不能为空!! ");
		return false;
	}

	if(document.sublineBean.lineID.value==""){
		alert("所属线路不能为空!! ");
		return false;
	}

	return true;
}

//-->
</script>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"><tr>
  <td height="24" align="center" class="title2">增加编辑巡检段对应光缆信息</td>
</tr>  <tr><td height="2" background="${ctx}/images/bg_line.gif"><img src="${ctx}/images/1px.gif" width="1" height="1"></td> </tr></table>
<form name="sublineBean" method="Post" action="${ctx}/sublineAction.do?method=addSubline" onsubmit="return isValidForm()">
  <table width="98%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
    <tr>
    <th width="50%" class="thlist">该线段对应</th>
    <th width="50%" class="thlist">全部光缆信息</th>
  </tr>
    <tr class=trcolor >
      <td valign="top" class="tdulleft">
        <select name="select" multiple="multiple" size="5" style="width:160;height:80" class="multySelect">
        </select>
		<br>
		<table width="73%" cellpadding="0" cellspacing="0">
	  <tr><td align="right">
	  <input type="button" value=" >> " onclick="self.close()" class="button">
	  </td></tr>
	  </table>
	  </td>
      <td class="tdulright">
      <select name="select2" multiple="multiple" size="5" style="width:160;height:80" class="multySelect">
      </select>
      <br>
	  <table width="100%" cellpadding="0" cellspacing="0">
	  <tr><td>
	  <input type="button" value=" << " onclick="self.close()" class="button">
	  </td></tr>
	  </table>

	  </td></tr>

 </table>
</form>
        <br>
        <br>
        <br>
        <br>
        <table width="80%"  border="0" align="center">
          <tr>
            <td align="center"><input name="submit" type="submit" class="button" value="确定">
              <input name="button" type="button" class="button" onClick="self.close()" value="取消"></td>
          </tr>
        </table>
