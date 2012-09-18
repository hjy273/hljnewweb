<%@ page contentType="text/html; charset=GBK" %>
<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.sysmanage.domainobjects.UserInfo" %>
<%@page import="com.cabletech.commons.util.*"%>
<%
	EncryptDecrypt enCode = new EncryptDecrypt();
	UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");
	String userid = enCode.Encode(userInfo.getUserID());
%>
<jsp:useBean id="gisip" scope="page" class="com.cabletech.commons.config.InterfaceConfig"></jsp:useBean>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<title>${AppName}</title>
<link href="../../css/divstyle.css" rel="stylesheet" type="text/css">
<script language="javascript">
	function index(){
		parent.parent.document.frames.mainFrame.document.frames.maintext.location.href='${ctx}/DesktopAction.do?method=loadinfo';
	}

	function exit(){
		parent.location = "${ctx}/login.do?&method=logout";
    	if(!window.confirm("��л����ʹ�ã�ȷ���뿪��\n�رյ��ȷ�����������µ�¼��")){
			window.open ("${ctx}/");
    	}
    	parent.window.close();
	}

	function help(){
	    window.open ("${ctx}/help/kf.htm");
	}
	function vrp(){
		window.open ("/vrp/locallogin.action?userId=${LOGIN_USER.userID }&password=${LOGIN_USER.password}");
	}
	function colligatePlatForm(){
		var url = "http://${gisip.unifyEntryIP}:${gisip.unifyEntryPort}/${gisip.unifyEntryUrl}?n=<%=userid%>";
		window.open (url);
	}

	function toShowTreeDiv(type){
		selectedTab = "tab"+type;
		//1����IDΪdivTab��Ԫ�أ�2����classNameΪtabs������LIԪ�أ��ų�classNameΪtabspace��LIԪ�أ���3���������class
        jQuery("#divTab .tabs LI[class!='tabspace']").removeClass();
        //Ϊ��ǰѡ�е�tab����class
        jQuery("#"+selectedTab).addClass("selectedTab");

		var requestConfig = {
			url :'${ctx}/DesktopAction.do?method=setMenuType&type='+type,
			callback : function(options,success,response){
				parent.frames.leftFrame.frames.tree.location.href = "${ctx}/frames/index/treediv.jsp";
			}
		}
		Ext.Ajax.request(requestConfig);
	}

	function setClass(){
		var lid = "tab" + document.getElementById('businessType').value;
		document.getElementById(lid).className = "selectedTab";
	}

</script>
</head>

<body class="top_bottom_border" onload="setClass()">
	<input id="businessType" type="hidden" value="${ businessType }" />
	<div class="system_name" >${AppName}</div>
	<div class="layout_top_bg1"><img src="${ctx}/images2/bg/ydlogo.gif" width="180" height="18"></div>
	<div class="layout_top_bg2">
		<div class="top_right">
		  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="16%"><img src="${ctx}/images2/bg/mc.gif" width="300" height="70"></td>
              <td width="84%" align="center" valign="top">
			  	<div class="top_right_1">
				 <div class="top_Toolbar">
				   <table width="100%" height="26"  border="0" cellpadding="0" cellspacing="0">
                     <tr align="center" valign="middle">
                       <td width="8%" align="center"><img src="${ctx}/images2/xst.gif"></td>
                       <td width="72%"><table width="60%" height="26" border="0" align="right" cellpadding="0" cellspacing="0">
                         <tr>
                         	<td width="56"><table width="45" border="0" cellspacing="0" cellpadding="0">
                             <tr>
                               <td width="21"><div align="center"><img src="${ctx}/images2/home.gif" width="16" height="16" /></div></td>
                               <td width="35"><div align="center" class="STYLE1"><a href="javascript:index()">����</a></div></td>
                             </tr>
                           </table></td>
                           <td width="10">|</td>
                           <td width="100"><table width="45" border="0" cellspacing="0" cellpadding="0">
                             <tr>
                               <td width="21"><div align="center"><img src="${ctx}/images2/bm3.gif" width="16" height="16" /></div></td>
                               <td width="70"><div align="center" class="STYLE1"><a href="javascript:vrp()">ȫ��</a></div></td>
                             </tr>
                           </table></td>
                           <td width="10">|</td>
                           <td width="56"><table width="45" border="0" cellspacing="0" cellpadding="0">
                             <tr>
                               <td width="21"><div align="center"><img src="${ctx}/images2/bz.gif" width="16" height="16" /></div></td>
                               <td width="24"><div align="center" class="STYLE1"><a href="javascript:help()">����</a></div></td>
                             </tr>
                           </table></td>
                           <td width="10">|</td>
                           
                           <td width="56"><table width="45" border="0" cellspacing="0" cellpadding="0">
                             <tr>
                               <td width="21"><div align="center"><img src="${ctx}/images2/tc.gif" width="16" height="16" /></div></td>
                               <td width="24"><div align="center" class="STYLE1"><a href="javascript:exit()">�˳�</a></div></td>
                             </tr>
                           </table></td>
                         </tr>
                       </table></td>
                     </tr>
                   </table>
				  </div>
			  	  <table width="100%" height="26"  border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td><table width="100%" height="26"  border="0" cellpadding="0" cellspacing="0" class="user_text">
                        <tr align="center" valign="middle">
                          <td width="3%" valign="center"><img src="${ctx}/images2/yh.gif" width="13" height="15"></td>
                          <td width="97%" align="left">�û���${LOGIN_USER.userName} ��λ��${LOGIN_USER.deptName} ����${LOGIN_USER_REGION_NAME}</td>
                        </tr>
                      </table></td>
                    </tr>
                  </table>
			  	</div>
				<div class="top_right_2">
					<div class="top_right_mc" ></div>
					<div class="top_Columns">
						 <div id="divTab" style="width:500px;">
					        <table cellSpacing="0" cellPadding="0" width="100%" height="28px" border="0">
					            <tr>
					                <td width="100%">
					                    <UL class="tabs">
					                    	<c:forEach items="${resourceList}" var="r">
					                        	<LI id="tab${ r.code }" name="tab" onclick="toShowTreeDiv('${ r.code }')"><span style="width: 60px;text-align: center">${ r.resourcename }</span></LI>
					                    	</c:forEach>
					                    </UL>
					                </td>
					            </tr>
					        </table>
					    </div>
				  	</div>
				</div>
			  </td>
            </tr>
          </table>
		</div>
	</div>
</body>
</html>
