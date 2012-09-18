<%@page import="com.cabletech.watchinfo.beans.*"%>
<jsp:useBean id="watchBean" class="com.cabletech.watchinfo.beans.WatchBean" scope="request"/>
<jsp:useBean id="sublineBean" class="com.cabletech.baseinfo.beans.SublineBean" scope="request"/>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo"%>
<%@include file="/common/header.jsp"%>
<%
  String lineid = watchBean.getLid(); //������·
  String conStr = "pointid in (select p.pointid pointid from sublineinfo s,pointinfo p where p.sublineid = s.sublineid and	s.sublineid = '" + lineid + "')";
  String gisweb = (String)request.getAttribute("gisweb");
  String regionID = request.getParameter("regionID");
%>
<script language="javascript" type="">
<!--

var iteName = "";

function getTimeWin(objName){

    iteName = objName;

    showx = event.screenX - event.offsetX - 4 - 210 ;
    showy = event.screenY - event.offsetY + 18;

    var timeWin = window.showModalDialog("${ctx}/common/time.html",self, "dialogWidth:255px; dialogHeight:285px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; status:no; directories:yes;scrollbars:no;Resizable=no;help:no");

}

function setSelecteTime(time) {
    document.all.item(iteName).value = time;
}

function toLoadPoint(){

    opSelect(true);

    var desPage = "${ctx}/pointAction.do?method=loadPoint4Watch&id=" + watchBean.lid.value;
    //alert(desPage);

    hiddenFrame.location.replace(desPage);

}

function opSelect(flag){

    watchBean.startpointid.disabled = flag;
    watchBean.endpointid.disabled = flag;

}

function toSetVis(visStr){

    for(var i = 0; i < toHideTr.length; i ++){

        toHideTr[i].style.display = visStr;
    }

}

function isPosInteger(inputVal) {
var oneChar
    inputStr=inputVal.toString()
    for (var i=0;i<inputStr.length;i++) {
        oneChar=inputStr.charAt(i)
        if (oneChar<"0" || oneChar>"9") {
            return false
        }
    }
    return true
}


function isValidForm() {
  var btime = watchBean.orderlyBeginTime.value;
    var etime = watchBean.orderlyEndTime.value;
    var b = btime.substring(0,2);
    var e = etime.substring(0,2);
    var s = Math.abs(e-b);
    if( b > e ){
      s = 24-s;
    }
        //�������ڱ�����ڵ��ڿ�ʼ����
    var yb = watchBean.beginDate.value.substring(0,4);
    var mb = parseInt(watchBean.beginDate.value.substring(5,7) ,10) - 1;
    var db = parseInt(watchBean.beginDate.value.substring(8,10),10);
    var bdate = new Date(yb,mb,db);

    var ye = watchBean.endDate.value.substring(0,4);
    var me = parseInt(watchBean.endDate.value.substring(5,7) ,10) - 1;
    var de = parseInt(watchBean.endDate.value.substring(8,10),10);
    var edate = new Date(ye,me,de);
    if(edate < bdate){
      alert("�������ڲ���С�ڿ�ʼ����!");
      return false;
    }
    //����ʱ�������ڵ��ڿ�ʼʱ��
    //var hb = parseInt(watchBean.orderlyBeginTime.value.substring(0,2),10) +  parseInt(watchBean.error.value,10);
    var hb = parseInt(watchBean.orderlyBeginTime.value.substring(0,2),10);
	var mb = parseInt(watchBean.orderlyBeginTime.value.substring(3,5) ,10) ;
    var sb = parseInt(watchBean.orderlyBeginTime.value.substring(6,8),10);
    var db = new Date();
    db.setHours(hb, mb, sb);
    var he = parseInt(watchBean.orderlyEndTime.value.substring(0,2),10);
    var me = parseInt(watchBean.orderlyEndTime.value.substring(3,5) ,10);
    var se = parseInt(watchBean.orderlyEndTime.value.substring(6,8),10);
    var de = new Date();
    de.setHours(he, me, se);
    if(de < db){
        alert("ֵ��������ڲ���С�ڿ�ʼ����!");
        return false;
    }




    if(watchBean.placeName.value==""){
        alert("���Ʋ���Ϊ��! ");
        watchBean.placeName.focus();
        return false;
    }


    if (isNaN(watchBean.watchwidth.value)){
        alert("������ΧӦ��Ϊ������! ");
        watchBean.watchwidth.focus();
        return false;
    }

	if(parseInt(watchBean.watchwidth.value) > 300){
        alert("������Χ���ܳ���300��! ");
        watchBean.watchwidth.focus();
        return false;
    }

    if(watchBean.principal.value==""){
        alert("����������/�鲻��Ϊ��! ");
        watchBean.principal.focus();
        return false;
    }

    if(watchBean.beginDate.value==""){
        alert("������ʼ���ڲ���Ϊ��! ");
        watchBean.beginDate.focus();
        return false;
    }

	// ȡ��ϵͳ����
	var d = new Date();
	var s = '';
	s += d.getYear() + "/"; 
    if((d.getMonth() + 1) > 9) {
		s += (d.getMonth() + 1) + "/";            // ��ȡ�·ݡ�
	} else {
		s += "0" + (d.getMonth() + 1) + "/";            // ��ȡ�·ݡ�
	}	
   	// ��ȡ�ա�
	if(d.getDate() > 9) {
		s += d.getDate();
	 } else {
		s = s + "0" + d.getDate()
	 }
	 if(s > watchBean.beginDate.value) {
		alert("��ʼ���ڲ���С�ڵ�ǰ����!");
		return false;
	 } 


    if(watchBean.patrolTime.value==""){
        alert("������ʼʱ�䲻��Ϊ��! ");
        watchBean.patrolTime.focus();
        return false;
    }
    if(watchBean.orderlyBeginTime.value==""){
        alert("ֵ�࿪ʼʱ�䲻��Ϊ��! ");
        watchBean.orderlyBeginTime.focus();
        return false;
    }
    if(watchBean.orderlyEndTime.value==""){
        alert("ֵ�����ʱ�䲻��Ϊ��! ");
        watchBean.orderlyEndTime.focus();
        return false;
    }

    if(watchBean.error.value==""){
        alert("ֵ��ʱ��������Ϊ��! ");
        watchBean.error.focus();
        return false;
    }
    if (isNaN(watchBean.error.value)){
        alert("ֵ��ʱ����Ӧ��Ϊ������! ");
        watchBean.error.focus();
        return false;
    }
    if(!isPosInteger(watchBean.error.value)){
      alert("ֵ��ʱ����ֻ��������!");
      watchBean.error.focus();
      return false;
    }
    if(watchBean.error.value > s){
      alert("��Ϣ���ͼ�����ܴ���ֵ��ʱ��");
      watchBean.error.focus();
      return false;
    }
        if(watchBean.involvedsegmentlist.value == ""){
      alert("��Ӱ����²���Ϊ��");
      watchBean.error.focus();
      return false;
    }
        if(watchBean.involvedlinenumber.value == ""){
      alert("��Ӱ�����������Ϊ��");
      watchBean.error.focus();
      return false;
    }
    return true;
}

function toClearEndDate(){
    watchBean.endDate.value = "����";
}

function preSetEndDate(){
    if(watchBean.endDate.value == ""){
        watchBean.endDate.value = "����";
    }
}

function loadForm(){
    var url = "${ctx}/watchAction.do?method=loadWatchAsObj&id=" + watchBean.placeID.value;
    self.location.replace(url);
}

function RedirectPage(){
    var url = "${ctx}/watchAction.do?method=queryWatch";
    self.location.replace(url);
}

function toGetBack(){
        var url = "${ctx}/watchAction.do?method=queryWatch";
        self.location.replace(url);
}

function countNum(){
  //alert(watchBean.involvedsegmentlist.value);
  if(watchBean.involvedlinenumber.value!=watchBean.involvedsegmentlist.length){
    if(watchBean.involvedsegmentlist.length==undefined){
      if(watchBean.involvedsegmentlist.value=="null"){
        watchBean.involvedlinenumber.value = 0;
      }else{
        watchBean.involvedlinenumber.value = 1;
      }
    }else{
      watchBean.involvedlinenumber.value = watchBean.involvedsegmentlist.length;
    }
  }
}

function toEditCable(fID) {
    //fID 1,���� 2,�޸�;
    var pageName = "${ctx}/WatchManage/getWatchRelativeCable4GIS.jsp?fID="+fID+"&regionID=<%=regionID%>";

    var pointsPop = window.open(pageName,'pointsPop','width=400,height=300,scrollbars=yes');
    //,resizable=yes,,status=yes
    pointsPop.focus();
}

//-->
</script>

<%if(gisweb!=null){%><template:titile value="�޸����������ֳ���Ϣ       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[ <a href=javascript:loadForm()>������Ϣ���ģʽ</a> ]"/>
<%}else{%><template:titile value="�޸����������ֳ���Ϣ"/><%}%>
<html:form onsubmit="return isValidForm()" method="Post" action="/watchAction.do?method=updateWatch">
  <template:formTable contentwidth="250" namewidth="250">
    <template:formTr name="��������" isOdd="false">
     
      <bean:write name="watchBean" property="placeName" />
      <html:hidden property="placeName"/>
      <html:hidden property="placeID"/>
      <html:hidden property="regionID"/>
      <html:hidden property="dealstatus"/>
    </template:formTr>
  <%
    WatchBean bean = (WatchBean) request.getAttribute("watchBean");
    String sqlcon = "select contractorname from contractorinfo where state is null and contractorid = '" + bean.getContractorid() + "'";
    java.sql.ResultSet rsc = null;
    com.cabletech.commons.hb.QueryUtil util = new com.cabletech.commons.hb.QueryUtil();
    rsc = util.executeQuery(sqlcon);
  %>
    <template:formTr name="��ά��λ" isOdd="false">
      <html:select property="contractorid" styleClass="inputtext" style="width:160">
      <%while (rsc.next()) {      %>
        <html:option value="<%=bean.getContractorid()%>"><%=rsc.getString(1)%>        </html:option>
      <%}rsc.close();      %>
      </html:select>
    </template:formTr>
  <%
    String sql = "select * from patrolmaninfo where state is null and patrolid = '" + bean.getPrincipal() + "'";
    java.sql.ResultSet rs = null;
    rs = util.executeQuery(sql);
  %>
    <logic:equal value="group"  name="PMType">
            <template:formTr name="����������">
              <html:select property="principal" styleClass="inputtext" style="width:160">
              <%while (rs.next()) {      %>
                <html:option value="<%=rs.getString(1)%>"><%=rs.getString(2)%>        </html:option>
              <%}rs.close();      %>
              </html:select>
            </template:formTr>
    </logic:equal>
    <logic:notEqual value="group" name="PMType">
                <template:formTr name="����������">
                  <html:select property="principal" styleClass="inputtext" style="width:160">
                  <%while (rs.next()) {      %>
                    <html:option value="<%=rs.getString(1)%>"><%=rs.getString(2)%>        </html:option>
                  <%}  rs.close();    %>
                  </html:select>
                </template:formTr>
    </logic:notEqual>


    <template:formTr name="��������" isOdd="false">
      <html:hidden property="innerregion" />
      <bean:write name="watchBean" property="innerregion" />
    </template:formTr>
    <template:formTr name="����λ��">
      <html:hidden property="watchplace"/>
      <bean:write name="watchBean" property="watchplace" />
    </template:formTr>
 <logic:equal value="show" name="ShowFIB">

    <template:formTr name="��������">
      <html:select property="placetype" styleClass="inputtext" style="width:160">
          <html:option value="��¥��">��¥��</html:option>
          <html:option value="��·����">��·����</html:option>
          <html:option value="����ʩ��">����ʩ��</html:option>
          <html:option value="���¹ܵ��޽�">���¹ܵ��޽�</html:option>
          <html:option value="���ݲ�">���ݲ�</html:option>
          <html:option value="��·����">��·����</html:option>
          <html:option value="���³���������">���³���������</html:option>
          <html:option value="��վװ��">��վװ��</html:option>
      </html:select>
    </template:formTr>
 </logic:equal>
 <logic:notEqual value="show" name="ShowFIB">

    <template:formTr name="��������">
      <html:text property="placetype" styleClass="inputtext" style="width:160"  />

      <!--
      <html:select property="placetype" styleClass="inputtext" style="width:160">
          <html:option value="��¥��">��¥��</html:option>
          <html:option value="��·����">��·����</html:option>
          <html:option value="����ʩ��">����ʩ��</html:option>
          <html:option value="���¹ܵ��޽�">���¹ܵ��޽�</html:option>
          <html:option value="���ݲ�">���ݲ�</html:option>
          <html:option value="��·����">��·����</html:option>
          <html:option value="��վװ��">��վװ��</html:option>
      </html:select>
      -->
    </template:formTr>
 </logic:notEqual>
    <template:formTr name="��������">
      <html:select property="dangerlevel" styleClass="inputtext" style="width:160">
        <html:option value="����">����</html:option>
        <html:option value="��Ҫ">��Ҫ</html:option>
        <html:option value="һ��">һ��</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="����ԭ��" isOdd="false">
      <html:text property="watchreason" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
    <template:formTr name="����">  
      <html:hidden property="gpscoordinate" />    
      <bean:write name="watchBean" property="x" />
    </template:formTr>
    <template:formTr name="γ��">      
      <bean:write name="watchBean" property="y" />
    </template:formTr>
    <template:formTr name="�����뾶" isOdd="false">
      <html:text property="watchwidth" styleClass="inputtext" style="width:160"/>
      &nbsp;
      (��λ����)
    </template:formTr>
    <template:formTr name="��ʼ����" isOdd="false">
      <html:text property="beginDate" styleClass="inputtext" size="25" maxlength="45" readonly="true"/>
      <apptag:date property="beginDate"/>
    </template:formTr>
    <template:formTr name="��������">
      <html:text property="endDate" styleClass="inputtext" size="25" maxlength="45" readonly="true"/>
      <apptag:date property="endDate"/>
      &nbsp;&nbsp;
      <input type="button" value="����" onclick="toClearEndDate()">
    </template:formTr>
    <template:formTr name="�ƻ�Ѳ��ʱ��" style="display:none">
      <html:text property="patrolTime" styleClass="inputtext" size="25" maxlength="45" value="2005/01/01"/>
      <apptag:date property="patrolTime"/>
    </template:formTr>
    <template:formTr name="ֵ�࿪ʼʱ��" isOdd="false">
      <html:text property="orderlyBeginTime" styleClass="inputtext" size="25" maxlength="45" readonly="true"/>
      <input type='button' value="��" id='btn' onclick="getTimeWin('orderlybegintime')" style="font:'normal small-caps 6pt serif';">
    </template:formTr>
    <template:formTr name="ֵ�����ʱ��">
      <html:text property="orderlyEndTime" styleClass="inputtext" size="25" maxlength="45" readonly="true"/>
      <input type='button' value="��" id='btn' onclick="getTimeWin('orderlyEndTime')" style="font:'normal small-caps 6pt serif';">
    </template:formTr>
    <template:formTr name="��Ϣ���ͼ��" isOdd="false">
      <html:text property="error" styleClass="inputtext" size="25" maxlength="45"/>
      &nbsp;
      (��λ��Сʱ)
    </template:formTr>
   <logic:equal value="show" name="ShowFIB">
      <template:formTr name="��Ӱ���������">
        <span id = "listSpan"><br>
          <table>
          <%
          //String[][] kidArr = sublineBean.getCablelist();
          String [][] kidArr = (String[][])request.getAttribute("tempArr");
          if( kidArr != null){
            for(int i = 0; i < kidArr.length; i++){
              %>
              <tr>
                <td>
                  <input type= "hidden" name="involvedsegmentlist" value="<%=kidArr[i][0]%>">
                  <input type="text" name ="cablename" value="<%=kidArr[i][1]%>" style = "border:0;background-color:transparent;width:160;font-size:9pt"  readonly>
                </td>
              </tr>
              <%
              }
            } else {%>
            <input type= "hidden" name="involvedsegmentlist" value="null">
            <%}
            %>
            </table>
        </span>
        <br>
        <br>
        <span id="toEditSubListSpan"><a href="javascript:toEditCable(2)">��ӱ༭��Ӱ�����</a></span>
      </template:formTr>
    <template:formTr name="��Ӱ������ܶ���" isOdd="false">
      <html:text property="involvedlinenumber" styleClass="inputtext" style="width:160" onclick="countNum()"/>
    </template:formTr>
   </logic:equal>

   <logic:notEqual value="noShow" name="ShowFIB">
   </logic:notEqual>
   <input type="hidden" name="gisweb" value="<%=gisweb%>">
    <template:formSubmit>
    <logic:equal value="show" name="ShowFIB">
      <td>
        <html:submit styleClass="button" onmouseover="countNum()">����</html:submit>
      </td>
    </logic:equal>
    <logic:notEqual value="show" name="ShowFIB">
      <td>
        <html:submit styleClass="button">����</html:submit>
      </td>
    </logic:notEqual>
      <td>
      <%if(gisweb!=null){%>
        <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="����">
      <%}else{%>
        <html:button property="action"styleClass="button"  onclick="self.close()">ȡ��</html:button>
      <%}%>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe id="hiddenFrame" style="display:none"></iframe>
<script language="javascript" type="">
<!--
preSetEndDate();
//-->
</script>
