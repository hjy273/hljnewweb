<%@include file="/common/header.jsp"%>
<%@ page import="java.util.*" %>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<SCRIPT language=javascript src="${ctx}/js/prototype.js" type=""></SCRIPT>
<SCRIPT language=javascript src="${ctx}/js/xwin.js" type=""></SCRIPT>
<SCRIPT language=JavaScript src="${ctx}/common/PopupDlg.js" type=""></SCRIPT>
<script language="javascript" type="text/javascript">
function toDelete(idValue,startdate,enddate){
   //������������ʽ����ʱ�ſ�
        var nowdate = new Date();//��ǰʱ��

        var yb = startdate.substring(0,4);
		var mb = startdate.substring(5,7) -1;
		var db = startdate.substring(8,10);
		var sdate = new Date(yb,mb,db);

        var ye = enddate.substring(0,4);
    	var me = enddate.substring(5,7)-1;
		var de = enddate.substring(8,10);
		var edate = new Date(ye,me,de);

        if(nowdate >= sdate && nowdate <= edate){
        	alert("�üƻ�����ִ����,����ɾ��!");
            return ;
        }
        if(nowdate >= sdate && nowdate >= edate){
        	alert("�üƻ���ִ�����,����ɾ��!");
            return ;
        }
    if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/PlanAction.do?method=deletePlan&id=" + idValue;
        self.location.replace(url);
    }
}

function toGetForm(idValue){
        var url = "${ctx}/PlanAction.do?method=loadPlanForm&id=" + idValue;
        self.location.replace(url);

}

function toEdit(idValue,startdate,enddate){
  //������������ʽ����ʱ�ſ�
        var nowdate = new Date();//��ǰʱ��

        var yb = startdate.substring(0,4);
		var mb = startdate.substring(5,7) -1;
		var db = startdate.substring(8,10);
		var sdate = new Date(yb,mb,db);

        var ye = enddate.substring(0,4);
    	var me = enddate.substring(5,7)-1;
		var de = enddate.substring(8,10);
		var edate = new Date(ye,me,de);

       if(nowdate >= sdate && nowdate <= edate){
        	alert("�üƻ�����ִ����,�����޸�!");
            return ;
       }
        if(nowdate >= sdate && nowdate >= edate){
        	alert("�üƻ���ִ�����,�����޸�!");
            return ;
        }

        var url = "${ctx}/PlanAction.do?method=loadPlan&id=" + idValue;
        self.location.replace(url);

}
function exportPointList(idValue,patrolName){

    var pageName = "${ctx}/PlanAction.do?method=ExportPlanPointsList&patrolName="+patrolName +"&patrolId=" + idValue + "&id=" + idValue;
    self.location.replace(pageName);

}
var planid;
var executorid;
var startdate;
var enddate;
var patrol;
var planname;
var instance="";
function toCopy(id,eid,sd,ed,p,pn){
	planid = id;
	executorid = eid;
	startdate = sd;
	enddate = ed;
	patrol = p;
	planname = pn;
	//if(instance.indexOf(planid)==-1){
		initialize();
	//	instance +=planid+",";
	//}
	ShowHide(planid,null);
}
//��ʼ��
function initialize()
{
  msg = 
  "<form method=\"post\"  action=\"${ctx}/copyplanAction.do?method=copyPlan\" onsubmit=\"return checkInput()\">"+
  "<div style=\"font-size:14px;font-weight:600;text-align:center\">���Ƽƻ�</div>"+
  "<div><span style=\"width:100px\">ԭ�ƻ���ʼʱ��: </span>"+
  "<span>"+startdate+"</span></div>"+
  "<div><span style=\"width:100px\">ԭ�ƻ�����ʱ��: </span>"+
  "<span>"+enddate+"</span></div>"+
  "<div><span style=\"width:100px\">ԭ�ƻ�Ѳ����: </span>"+
  "<span>"+patrol+"</span></div>"+
  "<div><span style=\"width:100px\">ԭ�ƻ�����: </span>"+
  "<span>"+planname+"</span></div>"+
  "<div><span style=\"width:100px\">�¼ƻ���ʼʱ��: </span>"+
  "<span><input type=\"text\" name=\"startDate\" readonly=\"readonly\" onclick=\"GetSelectDateTHIS(this)\" class=\"inputtext\"></span></div>"+
  "<div><span style=\"width:100px\">�¼ƻ�����ʱ��: </span>"+
  "<span><input type=\"text\" name=\"endDate\" readonly=\"readonly\" onclick=\"GetSelectDate(this)\" class=\"inputtext\"></span></div>"+
  "<input type=\"hidden\" name=\"planId\" value=\""+planid+"\">"+
  "<input type=\"hidden\" name=\"executorid\" value=\""+executorid+"\">"+
  "<div style=\"text-align:center\"><input type=\"submit\" value=\"����\"></div>"+
  "</form>";
  var b = new xWin(planid,400,200,100,100,"�ƻ�����",msg);
  ShowHide(planid,"none");
  center(planid);
}

//��ҳ��������ʾ
function center(id)
{
 xwin = document.getElementById("xMsg"+id);
 var wleft = (document.body.clientWidth-xwin.style.width.replace("px",""))/2 < 0 ? 0 : (document.body.clientWidth-xwin.style.width.replace("px",""))/2;
 var wtop = (document.body.clientHeight-xwin.style.height.replace("px",""))/2 < 0 ? 0 : (document.body.clientHeight-xwin.style.height.replace("px",""))/2;;
 
 xwin.style.left = wleft;
 xwin.style.top  = wtop;
 
 xwinbg = document.getElementById("xMsg"+id+"bg");
 xwinbg.style.left = wleft;
 xwinbg.style.top  = wtop;
}
// ѡ������
var plantype = 7;
function GetSelectDateTHIS(strID) {
    strID.value = getPopDate(strID.value);
    if(strID.value !="" && strID.value !=null){
      if(checkBDate(strID)){
        //preGetWeeklyTaskList();
        //setPlanName();
      }
    }
    return true;
}
function checkBDate(strID){
    var yb = strID.value.substring(0,4);
    var mb = parseInt(strID.value.substring(5,7) ,10) - 1;
    var db = parseInt(strID.value.substring(8,10),10);
    var date = new Date(yb,mb,db);
    
    if(plantype == 7){
    	date = new Date(yb,mb,db + 6);	
    }if(plantype == 10)
        date = new Date(yb,mb,db + 9);
    else if(plantype == 15)
        date = new Date(yb,mb,db + 14);
    else if(plantype == 30)
        date = new Date(yb,mb,db + 31);
    //y = date.getYear();
    m = date.getMonth() + 1;
    d = date.getDate();
	
	//ȷ�����ڲ��ܳ�����ĩ
    if(parseInt(m) != mb+1){
       m=mb+1;
       if(m == 2){//��2��
             if(yb %400 ==0 ||(yb %4 == 0  && yb % 100 !=0))
                d=29;
            else
                d=28;
       }else if( m== 1 || m== 3||m== 5||m== 7||m== 8 ||m== 10||m== 12){
         d=31;
       }else{
            d=30;
       }
    }
    if(m < 10){
        m = "0" + m;
    }
    if(d < 10){
        d = "0" + d;
    }
    document.getElementById("endDate").value = yb + "/" + m + "/" + d;
   
    return true;
}
function GetSelectDate(tdc) {
    if(document.getElementById("startDate").value =="" || document.getElementById("startDate").value ==null){
        alert("��û����д��ʼ����!");
        document.getElementById("startDate").focus();
        return false;
    }
    tdc.value = getPopDate(tdc.value);
    //��ʼʱ��
    var yb = document.getElementById("startDate").value.substring(0,4);
    var mb = parseInt(document.getElementById("startDate").value.substring(5,7) ,10);
    var db = parseInt(document.getElementById("startDate").value.substring(8,10),10);
    //����ʱ��
    var ye = document.getElementById("endDate").value.substring(0,4);
    var me = parseInt(document.getElementById("endDate").value.substring(5,7) ,10);
    var de = parseInt(document.getElementById("endDate").value.substring(8,10),10);
    if(de-db > 15){
    	alert("�ƻ����ڲ��ܴ���15�죡");
    	return false;
    }
    if(yb!=ye){
       alert("�ƻ����ܿ���!");
       tdc.value="";
       document.getElementById("endDate").focus();
      return false;
    }
    if(mb!=me){
      alert("�ƻ����ܿ���!");
      tdc.value="";
      document.getElementById("endDate").focus();
      return false;
    }
     if(de < db || me < mb || ye < yb){
      alert("�������ڲ���С�ڿ�ʼ����!");
      tdc.value="";
      document.getElementById("endDate").focus();
      return false;
    }
    return true;
}
function checkInput(){
	var yb = document.getElementById("startDate").value.substring(0,4);
    var mb = parseInt(document.getElementById("startDate").value.substring(5,7) ,10) - 1;
    var db = parseInt(document.getElementById("startDate").value.substring(8,10),10);
    var de = parseInt(document.getElementById("endDate").value.substring(8,10),10);
    var tdate = new Date();
    var indate = new Date(yb,mb,db);
    if(de-db > 10){
    	alert("�ƻ����ڲ��ܴ���10�죡");
    	return false;
    }else if(document.getElementById("startDate").value == ""){
        alert("����д�ƻ���ʼ���� ��");
        document.getElementById("startDate").focus();
        return false;
    }else if(document.getElementById("enddate").value == ""){
        alert("����д�ƻ��������� ��");
        document.getElementById("enddate").focus();
        return false;
    }else if(indate < tdate){
		alert("��ʼ���ڲ���С�ڵ�ǰ���ڣ�");
		return false;
	}else
    	return true;
}
</script>

<body>
	<%Date nowDate = new Date(); %>
	<template:titile value="��ѯ�ƻ���Ϣ���" />
	<display:table name="sessionScope.queryresult" requestURI="${ctx}/PlanAction.do?method=queryPlan" id="currentRowObject" pagesize="18">
		<display:column property="startdate" title="��ʼ����" sortable="true" />
		<display:column property="enddate" title="����ʱ��" sortable="true" />
		<logic:equal value="group" name="PMType">
			<display:column property="patrol" title="Ѳ��ά����" sortable="true" />
		</logic:equal>
		<logic:notEqual value="group" name="PMType">
			<display:column property="patrol" title="ִ����" sortable="true" />
		</logic:notEqual>
		<display:column property="name" title="�ƻ�����" sortable="true" />

		<display:column media="html" title="����">
			<%
				BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				String id = (String) object.get("id");				
			%>
			<a href="javascript:toGetForm('<%=id%>')">�鿴</a>
		</display:column>
		<display:column media="html" title="����">
			<%
				BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				String id = (String) object.get("id");
				String patrolName = (String) object.get("patrol");
			%>
			<a href="javascript:exportPointList('<%=id%>','<%=patrolName %>')" title='��������Ѳ���'>����</a>
		</display:column>
		<apptag:checkpower thirdmould="20304" ishead="0">
			<display:column media="html" title="����">
				<%
					DateUtil util = new DateUtil();
					BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
					String id = (String) object.get("id");
					String startdate = (String) object.get("startdate");
					String enddate = (String) object.get("enddate");
					Date sdate = util.parseDate(startdate);
					Date edate = util.parseDate(enddate);
					if(sdate.before(nowDate) && edate.after(nowDate)){
						out.print("--");
					}else if(sdate.before(nowDate) && edate.before(nowDate)){
					out.print("--");
					}else{
				%>
				<a href="javascript:toEdit('<%=id%>','<%=startdate%>','<%=enddate%>')">�޸�</a>
				<%} %>
			</display:column>
		</apptag:checkpower>
		<apptag:checkpower thirdmould="20305" ishead="0">
			<display:column media="html" title="����">
				<%
					DateUtil util = new DateUtil();
					BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
					String id = (String) object.get("id");
					String startdate = (String) object.get("startdate");
					String enddate = (String) object.get("enddate");
					Date sdate = util.parseDate(startdate);
					Date edate = util.parseDate(enddate);
					if(sdate.before(nowDate) && edate.after(nowDate)){
						out.print("--");
					}else if(sdate.before(nowDate) && edate.before(nowDate)){
					out.print("--");
					}else{
				%>
				<a href="javascript:toDelete('<%=id%>','<%=startdate%>','<%=enddate%>')">ɾ��</a>
				<%} %>
			</display:column>
		</apptag:checkpower>
	</display:table>
	<html:link action="/PlanAction.do?method=exportWeekPlanResult">����ΪExcel�ļ�</html:link>
</body>
