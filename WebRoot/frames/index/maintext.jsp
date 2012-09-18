<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="com.cabletech.base.SysConstant" %>
<%@include file="/common/header.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Ѳ��ϵͳ</title>

<link href="${ctx}/css/divstyle.css" rel="stylesheet" type="text/css">
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>


<script type='text/javascript' src='${ctx}/js/highchart/highcharts.js'></script>



<style type="text/css">
#mytable {
	width: 90%;
	padding: 0;
	margin: 0;
}
th {
	font: bold 11px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
	color: #4f6b72;
	border-right: 1px solid #F2FAFF;
	border-bottom: 1px solid #F2FAFF;
	border-top: 1px solid #F2FAFF;

	text-transform: uppercase;
	text-align: left;
	background: #F2FAFF  no-repeat;
}
#row {
	border-right: 1px solid #C1DAD7;
	border-bottom: 1px solid #C1DAD7;
	background: #fff;
	font-size:11px;
	padding: 6px 6px 6px 12px;
	color: #4f6b72;
}


.Frame { width: 98%; margin-left: 10px;margin-right: 10px; }
.Frame ul {  margin:auto; }
.Frame ul li { float: left; width: 130px; display: inline; }
.work_content{ width:80%;  height:90px;  border:#F0EDC6 solid 1px; background-color:#FAFAF2; margin:10px;}
.text{ width:100%; margin:auto; font-size:14px; color:#636306; text-align:center; margin-top:6px;cursor: pointer;}
.work_content ul{ margin:auto; height:60px;}
.work_content ul li{ margin:auto; list-style:none; overflow:hidden; float:left; width:110px; display:inline;}
.text_default{text-align:center;font-size:12px;	color:#636306;	text-decoration: none;}
.text_red{text-align:center;color:red ;font-size:12px;text-decoration: none;}
.text a{color:#636306;text-decoration: none;	}
.img{ width:48px; height:48px; margin:0px auto; margin-top:3px;margin-left:20px;}
legend{ color:#3366ff; font-size:14px;font-weight: bold;}




</style>
<script type="text/javascript">

 jQuery(document).ready(function(){
    terminalchart();//--- ��ά����
    regionBaseStation();
    basestationchart();
    repeaterchart();
    outdoorfacilitieschart();
    indooroverridechart();
    regionxjjdBaseStation();
    patrolgroupxjjdBaseStation();
    regionOutdoor();
    regionxjjgBaseStation();
    patrolgroupxjjgBaseStation();
    fieldSet();
 })
 
 
 function fieldSet(){
 		if(!document.getElementById("fieldSet1")){
      		return;
    	}
    	
    	
    	var	fieldSet1
    	var	fieldSet2
    	
    	
        	fieldSet1 = new Ext.form.FieldSet({
        collapsible: true,
		allowBlank : false,
		animCollapse :true,
        title: '��վ ֱ��վ',
        autoHeight:true,
        renderTo: 'fieldSet1',
        onClick:function(){alert(1);},
        padding:0,
        contentEl:Ext.get("fieldSet1EL"),
        listeners: { 
   			'beforeexpand':function(e) { 
   			  fieldSet2.collapse(true);
   			}
   		}
		});

		
		fieldSet2 = new Ext.form.FieldSet({
        collapsible: true,
		allowBlank : false,
		animCollapse :true,
        title: '���� ���ڸ���',
        collapsed :true,
        autoHeight:true,
        renderTo: 'fieldSet2',
        padding:0,
        contentEl:Ext.get("fieldSet2EL"),
        listeners: { 
   			'beforeexpand':function(e) { 
   			  fieldSet1.collapse(true);
   			}
   		}
		});
 }


function toUrl (url) {
    if(''==url){
      return;
    }
	window.location.href = url;
};
 
//�ն˱��� 
function terminalchart(){
    if(!document.getElementById("container")){
      return;
    }
	var chart = new Highcharts.Chart({
					chart: {
						renderTo: 'container',
						plotBackgroundColor: null,
						plotBorderWidth: null,
						plotShadow: false,
						width:400,
						height:200
					},
					title: {
						text: ''
					},
					tooltip: {
						formatter: function() {
							return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
						}
					},
					plotOptions: {
						pie: {
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: true,
								color: '#000000',
								connectorColor: '#000000',
								formatter: function() {
									return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
								}
							}
						}
					},
					credits: {
						enabled: false
					},
				    series: [{
						type: 'pie',
						name: 'Browser share',
						data: [
								<c:forEach var="item" items="${tels}">
                  					['${item.contractorname }', Math.round(100*(${item.count}/${tecount}))/1],
	    						</c:forEach>
						]
					}]
				});
}





//��վ
function basestationchart(){
    if(!document.getElementById("basestation")){
      return;
    }
	var chart = new Highcharts.Chart({
					chart: {
						renderTo: 'basestation',
						plotBackgroundColor: null,
						plotBorderWidth: null,
						plotShadow: false,
						height:180
					},
					title: {
						text: '��վ'
					},
					tooltip: {
						formatter: function() {
							return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
						}
					},
					plotOptions: {
						pie: {
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: true,
								color: '#000000',
								connectorColor: '#000000',
								formatter: function() {
									return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
								}
							}
						}
					},
					credits: {
						enabled: false
					},
				    series: [{
						type: 'pie',
						name: 'Browser share',
						data: [
								<c:forEach var="item" items="${bsls}">
                  					['${item.contractorname }', Math.round(100*(${item.count}/${bscount}))/1],
	    						</c:forEach>
						]
					}]
				});
}


//ֱ��վ
function repeaterchart(){
    if(!document.getElementById("repeater")){
      return;
    }
	var chart = new Highcharts.Chart({
					chart: {
						renderTo: 'repeater',
						plotBackgroundColor: null,
						plotBorderWidth: null,
						plotShadow: false,
						height:180
					},
					title: {
						text: 'ֱ��վ'
					},
					tooltip: {
						formatter: function() {
							return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
						}
					},
					plotOptions: {
						pie: {
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: true,
								color: '#000000',
								connectorColor: '#000000',
								formatter: function() {
									return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
								}
							}
						}
					},
					credits: {
						enabled: false
					},
				    series: [{
						type: 'pie',
						name: 'Browser share',
						data: [
								<c:forEach var="item" items="${rels}">
                  					['${item.contractorname }', Math.round(100*(${item.count}/${recount}))/1],
	    						</c:forEach>
						]
					}]
				});
}


//����
function outdoorfacilitieschart(){
    if(!document.getElementById("outdoorfacilities")){
      return;
    }
	var chart = new Highcharts.Chart({
					chart: {
						renderTo: 'outdoorfacilities',
						plotBackgroundColor: null,
						plotBorderWidth: null,
						plotShadow: false,
						height:180
					},
					title: {
						text: '����'
					},
					tooltip: {
						formatter: function() {
							return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
						}
					},
					plotOptions: {
						pie: {
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: true,
								color: '#000000',
								connectorColor: '#000000',
								formatter: function() {
									return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
								}
							}
						}
					},
					credits: {
						enabled: false
					},
				    series: [{
						type: 'pie',
						name: 'Browser share',
						data: [
								<c:forEach var="item" items="${ouls}">
                  					['${item.contractorname }', Math.round(100*(${item.count}/${oucount}))/1],
	    						</c:forEach>
						]
					}]
				});
}



//����
function indooroverridechart(){
    if(!document.getElementById("indooroverride")){
      return;
    }
	var chart = new Highcharts.Chart({
					chart: {
						renderTo: 'indooroverride',
						plotBackgroundColor: null,
						plotBorderWidth: null,
						plotShadow: false,
						height:180
					},
					title: {
						text: '���ڷֲ�'
					},
					tooltip: {
						formatter: function() {
							return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
						}
					},
					plotOptions: {
						pie: {
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: true,
								color: '#000000',
								connectorColor: '#000000',
								formatter: function() {
									return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
								}
							}
						}
					},
					credits: {
						enabled: false
					},
				    series: [{
						type: 'pie',
						name: 'Browser share',
						data: [
								<c:forEach var="item" items="${inls}">
                  					['${item.contractorname }', Math.round(100*(${item.count}/${incount}))/1],
	    						</c:forEach>
						]
					}]
				});
}


//��վ��ѯ
function queryBaseStation(){
	window.location.href="${ctx}/baseStationAction_query.jspx?is_query=1&&stationCode="+$('stationCode').value+"&bsLevel="+$('bsLevel').value+"&district="+$('district').value+"&address="+$('address').value;
};

//��վ����
function regionBaseStation(){
    if(!document.getElementById("basestation_combotree_patrolregiondiv")){
      return;
    }
    Ext.BLANK_IMAGE_URL = "${ctx}/js/extjs/resources/images/default/s.gif";
	var patrolregioncombotree = new TreeComboField({
		width : 150,
		maxHeight : 100,
		allowBlank : true,
		renderTo : 'basestation_combotree_patrolregiondiv',
		name : "regionname",
		hiddenName : "district",
		displayField : 'text',
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			root : new Ext.tree.AsyncTreeNode({
				id : '000000',
				loader : new Ext.tree.TreeLoader({
					dataUrl : '${ctx}/TowerPatrolinfo.do?method=getPatrolRegion'
				})

			})
		})
	});
}

//��վ����
function regionOutdoor(){
    if(!document.getElementById("outdoor_combotree_patrolregiondiv")){
      return;
    }
    Ext.BLANK_IMAGE_URL = "${ctx}/js/extjs/resources/images/default/s.gif";
	var patrolregioncombotree = new TreeComboField({
		width : 150,
		maxHeight : 100,
		allowBlank : true,
		renderTo : 'outdoor_combotree_patrolregiondiv',
		name : "regionname",
		hiddenName : "district",
		displayField : 'text',
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			root : new Ext.tree.AsyncTreeNode({
				id : '000000',
				loader : new Ext.tree.TreeLoader({
					dataUrl : '${ctx}/TowerPatrolinfo.do?method=getPatrolRegion'
				})

			})
		})
	});
}


//��վѲ���������
function regionxjjdBaseStation(){
    if(!document.getElementById("basestation_xjjd_combotree_patrolregiondiv")){
      return;
    }
    Ext.BLANK_IMAGE_URL = "${ctx}/js/extjs/resources/images/default/s.gif";
	var patrolregioncombotree = new TreeComboField({
		width : 150,
		maxHeight : 100,
		allowBlank : true,
		renderTo : 'basestation_xjjd_combotree_patrolregiondiv',
		name : "regionname",
		hiddenName : "regionid",
		displayField : 'text',
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			root : new Ext.tree.AsyncTreeNode({
				id : '000000',
				loader : new Ext.tree.TreeLoader({
					dataUrl : '${ctx}/TowerPatrolinfo.do?method=getPatrolRegion'
				})

			})
		})
	});
}

//��վѲ�����ά����
function patrolgroupxjjdBaseStation(){
    if(!document.getElementById("basestation_xjjd_combotree_patrolgroupdiv")){
      return;
    }
	Ext.BLANK_IMAGE_URL = "${ctx}/js/extjs/resources/images/default/s.gif";
	var patrolgroupcombotree = new TreeComboField({
		width : 150,
		maxHeight : 100,
		allowBlank : true,
		leafOnly : true,
		renderTo : 'basestation_xjjd_combotree_patrolgroupdiv',
		name : "patrolgroupname",
		hiddenName : "patrolgroupid",
		displayField : 'text',
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			root : new Ext.tree.AsyncTreeNode({
				id : 'root',
				loader : new Ext.tree.TreeLoader({
							dataUrl : '${ctx}/TowerPatrolinfo.do?method=getPatrolGroup'
						})

			})
		})
	});

}


//��վѲ��������
function regionxjjgBaseStation(){
    if(!document.getElementById("basestation_xjjg_combotree_patrolregiondiv")){
      return;
    }
    Ext.BLANK_IMAGE_URL = "${ctx}/js/extjs/resources/images/default/s.gif";
	var patrolregioncombotree = new TreeComboField({
		width : 150,
		maxHeight : 100,
		allowBlank : true,
		renderTo : 'basestation_xjjg_combotree_patrolregiondiv',
		name : "regionname",
		hiddenName : "regionid",
		displayField : 'text',
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			root : new Ext.tree.AsyncTreeNode({
				id : '000000',
				loader : new Ext.tree.TreeLoader({
					dataUrl : '${ctx}/TowerPatrolinfo.do?method=getPatrolRegion'
				})

			})
		})
	});
}

//��վѲ����ά����
function patrolgroupxjjgBaseStation(){
    if(!document.getElementById("basestation_xjjg_combotree_patrolgroupdiv")){
      return;
    }
	Ext.BLANK_IMAGE_URL = "${ctx}/js/extjs/resources/images/default/s.gif";
	var patrolgroupcombotree = new TreeComboField({
		width : 150,
		maxHeight : 100,
		allowBlank : true,
		leafOnly : true,
		renderTo : 'basestation_xjjg_combotree_patrolgroupdiv',
		name : "patrolgroupname",
		hiddenName : "patrolgroupid",
		displayField : 'text',
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			root : new Ext.tree.AsyncTreeNode({
				id : 'root',
				loader : new Ext.tree.TreeLoader({
							dataUrl : '${ctx}/TowerPatrolinfo.do?method=getPatrolGroup'
						})

			})
		})
	});

}






</script>

</head>

<body>
<div class="layout_main">

<!-- ��ά���� -->
<c:if test="${type=='8'}">

<div class="Module_midleft">
			<div class="Module_title3">
				<table width="100%" height="28"  border="0" cellpadding="0" cellspacing="0" class="title">
				    <tr>
						<td width="8%" align="center" valign="middle"><img src="${ctx}/images2/ur-anony.gif" width="16" height="16"></td>
						<td width="86%" align="left">��ά��λ��Ϣ����</td>
						<td width="6%" align="left">&nbsp;</td>
				    </tr>
				</table>
			</div>
			<div class="Module_contentmid">
				<div class="Module3_text">
				   <html:form method="Post" action="/contractorAction.do?method=queryContractor">
					<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0" class="tabout">
						<tr class="trcolor">
						<td class="tdulleft">��λ���ƣ�</td><td class="tdulright">
							<html:text property="contractorName" maxlength="45" />
							</td>
						</tr>
						<tr class="trwhite">
							<td class="tdulleft">��֯���ͣ�</td>
							<td class="tdulright">
							<select name="organizationType" class="inputtext" style="width: 160">
								<option value="">
									����
								</option>
							<option value="1">
									��������
							</option>
							<option value="2">
									��֧����
							</option>
							</select>
							</td>
						</tr>
						<tr class="trcolor"><td class="tdulleft">��������</td><td class="tdulright">
							<apptag:setSelectOptions valueName="parentRegionCellection"
								tableName="region" columnName1="regionname" region="true"
								columnName2="regionid" order="regionid" />
							<html:select property="regionid" styleClass="inputtext"
								style="width:160">
							<html:option value="">����</html:option>
							<html:options collection="parentRegionCellection" property="value"
								labelProperty="label" />
							</html:select>
						</tr>
						<tr class="trwhite"><td class="tdulleft">�ϼ���˾��</td><td class="tdulright">
							<apptag:setSelectOptions valueName="parentcontractorCollection"
								tableName="contractorinfo" region="true"
								columnName1="contractorname" columnName2="contractorid" />
							<html:select property="parentcontractorid" styleClass="inputtext"
								style="width:160">
							<html:option value="0">����</html:option>
							<html:option value="0">��  </html:option>
							<html:options collection="parentcontractorCollection"
								property="value" labelProperty="label" />
							</html:select>
						</td>
						<tr class="trcolor">
							<td class="tdulleft">����רҵ��</td>
							<td class="tdulright">
									<c:forEach var="res" items="${resources}" varStatus="stauts">
										<html:checkbox property="resourcesId" value="${res.code}">${res.resourcename}</html:checkbox>
											<c:if test="${(stauts.index+1)%2==0}">
												<br>
											</c:if>
									</c:forEach>
							</td>
						</tr>
						<tr class="trwhite"><td class="tdulleft" colspan="2" style="text-align:center;">
							<html:submit styleClass="button">��ѯ</html:submit>
						</tr>
					</table>
					</html:form>
				</div>
			</div>
        </div>
		<div class="Module_midright">
			<div class="Module_title3">
				<table width="100%" height="28"  border="0" cellpadding="0" cellspacing="0" class="title">
				    <tr>
						<td width="8%" align="center" valign="middle"><img src="${ctx}/images2/ur-anony.gif" width="16" height="16"></td>
						<td width="86%" align="left">Ѳ���ն�ʹ�����ͳ��</td>
						<td width="6%" align="left">&nbsp;</td>
				    </tr>
				</table>
			</div>
			<div class="Module_contentmid">
				<div class="Module3_text">
 					<div id="container">
					</div>
				</div>
			</div>
		</div>
		
		 <div class="Module_midleft">
			<div class="Module_title3">
				<table width="100%" height="28"  border="0" cellpadding="0" cellspacing="0" class="title">
				    <tr>
						<td width="8%" align="center" valign="middle"><img src="${ctx}/images2/ur-anony.gif" width="16" height="16"></td>
						<td width="86%" align="left">��ά��λ��ԱͨѶ¼</td>
						<td width="6%" align="left">&nbsp;</td>
				    </tr>
				</table>
			</div>
			<div class="Module_contentmid">
				<div class="Module3_text">
	<html:form action="/ConPersonAction?method=deskTopQuery" styleId="queryForm2" >
 					<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0" class="tabout">
					<logic:equal value="1" name="LOGIN_USER" property="deptype">
						<tr class="trcolor">
						<td class="tdulleft">��ά��λ��</td>
						<td class="tdulright">
							<apptag:setSelectOptions valueName="deptCollection"
							tableName="contractorinfo" columnName1="contractorname"
							columnName2="contractorid" region="true" />
							<html:select property="contractorid" styleClass="inputtext"
							style="width:160" alt="��ά��λ">
							<html:option value="">��ѡ��</html:option>
							<html:options collection="deptCollection" property="value"
								labelProperty="label" />
							</html:select>
							<input type="hidden" name="objecttype" value="CONTRACTORINFO" />
						</td>
						</tr>
						</logic:equal>
						
						<logic:equal value="2" name="LOGIN_USER" property="deptype">
						<tr class="trwhite"><td class="tdulleft">��ά��λ��</td><td class="tdulright">
							<select name="contractorid" Class="inputtext" style="width: 200">
							<option  value="${LOGIN_USER.deptID }">
								${LOGIN_USER.deptName }
							</option>
							</select>
							<input type="hidden" name="objecttype" value="CONTRACTORINFO" />
						</td>
						</tr>
						</logic:equal>
						<tr class="trwhite"><td class="tdulleft">������</td><td class="tdulright">
							<html:text property="name" maxlength="5" />
						</td>
						</tr>
						<tr class="trcolor"><td class="tdulleft">Ա����ţ�</td><td class="tdulright">
							<html:text property="employeeNum"  maxlength="12" />
						</td>
						</tr>
						<tr class="trwhite"><td class="tdulleft">�ڸڷ�</td><td class="tdulright">
						<html:select property="conditions">
							<html:option value="">����</html:option>
							<html:option value="0">�ڸ�</html:option>
							<html:option value="1">��ְ</html:option>
						</html:select>
						</td>
						</tr>
						<tr class="trwhite"><td class="tdulleft" colspan="2" style="text-align:center;">
							<html:submit styleClass="button">��ѯ</html:submit>
						</tr>
					</table>
					<table id="txl" width="70%" border="0" align="center" cellpadding="0" cellspacing="0" class="tabout">

					</table>
					</html:form>
				</div>
			</div>
        </div>
        
		<div class="Module_midright">
			<div class="Module_title3">
				<table width="100%" height="28"  border="0" cellpadding="0" cellspacing="0" class="title">
				    <tr>
						<td width="8%" align="center" valign="middle"><img src="${ctx}/images2/ur-anony.gif" width="16" height="16"></td>
						<td width="86%" align="left">��ά������Ϣ����</td>
						<td width="6%" align="left">&nbsp;</td>
				    </tr>
				</table>
			</div>
			<div class="Module_contentmid">
				<div class="Module3_text">
				 	<html:form action="/CertificateAction?method=doQuery" styleId="queryForm2">
					<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0" class="tabout">
						<tr class="trcolor">
						<td class="tdulleft">֤���ţ�</td><td class="tdulright">
								<html:text property="certificatecode"  maxlength="20" />
							</td>
						</tr>
						<tr class="trwhite"><td class="tdulleft">֤�����ƣ�</td><td class="tdulright"><html:text property="certificatename" maxlength="25" />
							<br></td>
						</tr>
						<tr class="trcolor"><td class="tdulleft">֤�����ͣ�</td><td class="tdulright">
							<apptag:quickLoadList name="certificatetype" listName="DWZZ" type="select" isRegion="false" />
						<br></tr>
						
						<logic:equal value="1" name="LOGIN_USER" property="deptype">
						<tr class="trwhite"><td class="tdulleft">��ά��λ��</td><td class="tdulright">
							<apptag:setSelectOptions valueName="deptCollection"
							tableName="contractorinfo" columnName1="contractorname"
							columnName2="contractorid" region="true" />
							<html:select property="objectid" styleClass="inputtext"
							style="width:160" alt="��ά��λ">
							<html:option value="">����</html:option>
							<html:options collection="deptCollection" property="value"
								labelProperty="label" />
							</html:select>
							<input type="hidden" name="objecttype" value="CONTRACTORINFO" />
						</td>
						</tr>
						</logic:equal>
						
						<logic:equal value="2" name="LOGIN_USER" property="deptype">
						<tr class="trwhite"><td class="tdulleft">��ά��λ��</td><td class="tdulright">
							<select name="objectid" Class="inputtext" style="width: 200">
							<option  value="${LOGIN_USER.deptID }">
								${LOGIN_USER.deptName }
							</option>
							</select>
							<input type="hidden" name="objecttype" value="CONTRACTORINFO" />
						</td>
						</tr>
						</logic:equal>
						
						
						<tr class="trwhite"><td class="tdulleft" colspan="2" style="text-align:center;">
							<html:submit property="action" styleClass="button">��ѯ</html:submit>
						</tr>
					</table>
					</html:form>
				</div>
			</div>
		</div>
		</c:if>
<!-- ��ά���� -->


<!-- ������Դ -->
<c:if test="${type=='7'}">

		<div class="Module_all">
			<div class="Module_title3">
				<table width="100%" height="28"  border="0" cellpadding="0" cellspacing="0" class="title">
				    <tr>
						<td width="8%" align="center" valign="middle"><img src="${ctx}/images2/ur-anony.gif" width="16" height="16"></td>
						<td width="86%" align="left">��ά��λά����Դ����ͳ��</td>
						<td width="6%" align="left">&nbsp;</td>
				    </tr>
				</table>
			</div>
			<div class="Module_contentmid">
				<div class="Module_textall">

					<div id="fieldSet1" style="width: 100%;"></div>
				 	<div id="fieldSet1EL">
						<table width="95%">
				    	<tr>
							<td width="50%" align="center"><div id="basestation"></div></td>
							<td width="50%" align="center"><div id="repeater"></div></td>
				    	</tr>
						</table>
					</div>
					<div id="fieldSet2" style="width: 100%;"></div>
				 	<div id="fieldSet2EL">
						<table width="95%">
				    	<tr>
							<td width="50%" align="center"><div id="outdoorfacilities"></div></td>
							<td width="50%" align="center"><div id="indooroverride"></div></td>
				    	</tr>
						</table>
					</div>
				
				
				</div>
			</div>
		</div>



<div class="Module_midleft">
			<div class="Module_title3">
				<table width="100%" height="28"  border="0" cellpadding="0" cellspacing="0" class="title">
				    <tr>
						<td width="8%" align="center" valign="middle"><img src="${ctx}/images2/ur-anony.gif" width="16" height="16"></td>
						<td width="86%" align="left">��վ��Ϣ����</td>
						<td width="6%" align="left">&nbsp;</td>
				    </tr>
				</table>
			</div>
			<div class="Module_contentmid">
				<div class="Module3_text">
					<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0" class="tabout">
						<tr class="trcolor">
						<td class="tdulleft">վַ��ţ�</td><td class="tdulright">
							<input type="text" class="inputtext" style="width:150px" name="stationCode" value="${baseStation.stationCode}"/>
							</td>
						</tr>
						<tr class="trwhite"><td class="tdulleft">��վ��ַ��</td><td class="tdulright">
							<input type="text" class="inputtext" style="width:150px" name="address" alt="֧��ģ������" value ="${baseStation.address}"/>
							</td>
						</tr>
						<tr class="trcolor"><td class="tdulleft">��վ����</td><td class="tdulright">
							<apptag:quickLoadList name="bsLevel" listName="basestation_level" type="select" isRegion="false" isQuery="query" keyValue="${baseStation.bsLevel }" />
						</tr>
						<tr class="trwhite"><td class="tdulleft">�������أ�</td><td class="tdulright">
							<div id="basestation_combotree_patrolregiondiv" style="width: 150;"></div>
						</td>
						<tr class="trwhite"><td class="tdulleft" colspan="2" style="text-align:center;">
							<input type="button" onclick="queryBaseStation()" class="button" value="��ѯ"/>
						</tr>
					</table>
				</div>
			</div>
        </div>
		<div class="Module_midright">
			<div class="Module_title3">
				<table width="100%" height="28"  border="0" cellpadding="0" cellspacing="0" class="title">
				    <tr>
						<td width="8%" align="center" valign="middle"><img src="${ctx}/images2/ur-anony.gif" width="16" height="16"></td>
						<td width="86%" align="left">������Ϣ����</td>
						<td width="6%" align="left">&nbsp;</td>
				    </tr>
				</table>
			</div>
			<div class="Module_contentmid">
				<div class="Module3_text">
				<form action="${ctx }/outdoorFacilitiesAction_query.jspx" method="post">
 					<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0" class="tabout">
						<tr class="trcolor">
						<td class="tdulleft">������ţ�</td><td class="tdulright">
							<input type="text" class="inputtext" style="width:150px" name="towerCode"/>
							</td>
						</tr>
						<tr class="trwhite"><td class="tdulleft">�������ƣ�</td><td class="tdulright">
							<input type="text" class="inputtext" style="width:150px" name="towerName"/>
							</td>
						</tr>
						<tr class="trcolor"><td class="tdulleft">ԭ���ƣ�</td><td class="tdulright">
							<input type="text" class="inputtext" style="width:150px" name="oldName"/>
						</tr>
						<tr class="trwhite"><td class="tdulleft">�������أ�</td><td class="tdulright">
						    <div id="outdoor_combotree_patrolregiondiv" style="width: 150;"></div>
							<div id="" style="width: 150;"></div>
						</td>
						<tr class="trcolor"><td class="tdulleft">��վ��ţ�</td><td class="tdulright">
							<input type="text" class="inputtext" style="width:150px" name="stationCode"/>
						</tr>
						<tr class="trwhite"><td class="tdulleft">��վ���ƣ�</td><td class="tdulright">
							<input type="text" class="inputtext" style="width:150px" name="stationName"/>
						</tr>
						<tr class="trwhite"><td class="tdulleft" colspan="2" style="text-align:center;">
							<input type="submit" class="button" value="��ѯ"/>
						</tr>
					</table>
					</form>
				</div>
			</div>
		</div>
		</c:if>
<!-- ������Դ -->

<!-- ��վѲ�� -->

<c:if test="${type=='2'}">



<!-- ���칤������ -->

<!-- �ҵĹ��� -->

<div class="Module_midleftall">
			<div class="Module_title3">
				<table width="100%" height="28"  border="0" cellpadding="0" cellspacing="0" class="title">
				    <tr>
						<td width="8%" align="center" valign="middle"><img src="${ctx}/images2/ur-anony.gif" width="16" height="16"></td>
						<td width="86%" align="left">���칤��</td>
						<td width="6%" align="left">&nbsp;</td>
				    </tr>
				</table>
			</div>
			<div class="Module_contentmidall">
				<div class="Module_textall">

					<div class="Frame">
						<fieldset>
 							<legend></legend>
							<ul>
      							<c:forEach var="vMenu" items="${ls}">
        							<li>
	        							<div class="work_content" >
	        							<div class="img"><img src="${ctx}/${vMenu.imgurl }" /></div>
	            						<div class="text" onclick="toUrl('${vMenu.hrefurl}');">��${vMenu.lablename}��</div>
	    						</li>
      							</c:forEach>

							</ul>
						</fieldset>
					</div>


				</div>
			</div>
        </div>
        
		<div class="Module_midrightall">
			<div class="Module_title3">
				<table width="100%" height="28"  border="0" cellpadding="0" cellspacing="0" class="title">
				    <tr>
						<td width="8%" align="center" valign="middle"><img src="${ctx}/images2/ur-anony.gif" width="16" height="16"></td>
						<td width="86%" align="left">Ѳ��ִ�н��Ȳ�ѯ</td>
						<td width="6%" align="left">&nbsp;</td>
				    </tr>
				</table>
			</div>
			<div class="Module_contentmidall">
				<div class="Module_textall">
					<iframe src="${ctx}//TowerPatrolinfo.do?method=doScheduleQueryForDesktop&flag=1&type=C31" id="xjjdjz" width="100%" height="100%" scrolling="auto" frameborder="no"></iframe>
				</div>
			</div>
		</div>
</c:if>

<!-- ��վѲ�� -->


<!-- ����Ѳ�� -->
<c:if test="${type=='3'}">
<!-- ���칤������ -->

<!-- �ҵĹ��� -->

<div class="Module_midleftall">
			<div class="Module_title3">
				<table width="100%" height="28"  border="0" cellpadding="0" cellspacing="0" class="title">
				    <tr>
						<td width="8%" align="center" valign="middle"><img src="${ctx}/images2/ur-anony.gif" width="16" height="16"></td>
						<td width="86%" align="left">���칤��</td>
						<td width="6%" align="left">&nbsp;</td>
				    </tr>
				</table>
			</div>
			<div class="Module_contentmidall">
				<div class="Module_textall">
				
				
					<div class="Frame">
						<fieldset>
 							<legend></legend>
							<ul>
      							<c:forEach var="vMenu" items="${ls}">
        							<li>
	        							<div class="work_content" >
	        							<div class="img"><img src="${ctx}/${vMenu.imgurl }" /></div>
	            						<div class="text" onclick="toUrl('${vMenu.hrefurl}');">��${vMenu.lablename}��</div>
	    						</li>
      							</c:forEach>

							</ul>
						</fieldset>
					</div>
					
					
				</div>
			</div>
        </div>
        
		<div class="Module_midrightall">
			<div class="Module_title3">
				<table width="100%" height="28"  border="0" cellpadding="0" cellspacing="0" class="title">
				    <tr>
						<td width="8%" align="center" valign="middle"><img src="${ctx}/images2/ur-anony.gif" width="16" height="16"></td>
						<td width="86%" align="left">Ѳ��ִ�н��Ȳ�ѯ</td>
						<td width="6%" align="left">&nbsp;</td>
				    </tr>
				</table>
			</div>
			<div class="Module_contentmidall">
				<div class="Module_textall">
					
					<iframe src="${ctx}//TowerPatrolinfo.do?method=doScheduleQueryForDesktop&flag=1&type=C33" id="xjjdjz" width="100%" height="100%" scrolling="auto" frameborder="no"></iframe>
					
				</div>
			</div>
		</div>

</c:if>

<!-- ����Ѳ�� -->


<!-- �ۺϸ���Ѳ�� -->
<c:if test="${type=='4'}">
<!-- ���칤������ -->

<!-- �ҵĹ��� -->

<div class="Module_midleftall">
			<div class="Module_title3">
				<table width="100%" height="28"  border="0" cellpadding="0" cellspacing="0" class="title">
				    <tr>
						<td width="8%" align="center" valign="middle"><img src="${ctx}/images2/ur-anony.gif" width="16" height="16"></td>
						<td width="86%" align="left">���칤��</td>
						<td width="6%" align="left">&nbsp;</td>
				    </tr>
				</table>
			</div>
			<div class="Module_contentmidall">
				<div class="Module_textall">

					<div class="Frame">
						<fieldset>
 							<legend></legend>
							<ul>
      							<c:forEach var="vMenu" items="${ls}">
        							<li>
	        							<div class="work_content" >
	        							<div class="img"><img src="${ctx}/${vMenu.imgurl }" /></div>
	            						<div class="text" onclick="toUrl('${vMenu.hrefurl}');">��${vMenu.lablename}��</div>
	    						</li>
      							</c:forEach>

							</ul>
						</fieldset>
					</div>


				</div>
			</div>
        </div>
        
		<div class="Module_midrightall">
			<div class="Module_title3">
				<table width="100%" height="28"  border="0" cellpadding="0" cellspacing="0" class="title">
				    <tr>
						<td width="8%" align="center" valign="middle"><img src="${ctx}/images2/ur-anony.gif" width="16" height="16"></td>
						<td width="86%" align="left">Ѳ��ִ�н��Ȳ�ѯ</td>
						<td width="6%" align="left">&nbsp;</td>
				    </tr>
				</table>
			</div>
			<div class="Module_contentmidall">
				<div class="Module_textall">
					
					<iframe src="${ctx}//TowerPatrolinfo.do?method=doScheduleQueryForDesktop&flag=1&type=C32" id="xjjdjz" width="100%" height="100%" scrolling="auto" frameborder="no"></iframe>
					
				</div>
			</div>
		</div>
</c:if>

<!-- �ۺϸ���Ѳ�� -->

		
		
	</div>
</body>
</html>