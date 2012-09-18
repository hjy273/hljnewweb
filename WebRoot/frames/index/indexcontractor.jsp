<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="com.cabletech.base.SysConstant"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>Ѳ��ϵͳ</title>
<style type="text/css">

.Detailed_list_analysis{ width:100%; /* IE7+FF */ font-size:12px;  text-align:center; color:#10284B; margin-top:10px; margin-left:0%; margin-right:0%; border:1px solid #8DC5E2;  }
.Detailed_list_analysis_title{ height:24px!important; font-size:12px; color:#000000; text-align:center;}
.Detailed_list_analysis th{text-align:left; padding-left:20px; line-height:24px; color:#09235E; }
.Detailed_list_analysis,table.Detailed_list_analysis th,table.Detailed_list_analysis th{  border-collapse:collapse!important;}
.Detailed_list_analysis td{ border:1px solid #8DC5E2; height:24px; line-height:24px; }

</style>
		<link href="${ctx}/css/divstyle.css" rel="stylesheet" type="text/css">
		<script src="${ctx}/js/extjs/ux/ux-all-debug.js"
			type="text/javascript"></script>
		<script src="${ctx}/js/extjs/ux/RowLayout.js"
			type="text/javascript"></script>
		<script type='text/javascript' src='${ctx}/js/highchart/highcharts.js'></script>
		<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
		<script type="text/javascript">

Ext.onReady(function(){
	
	//��Ա�����ʲ���
	var  staff=new Ext.Panel({
	    id:"staff_id",
        title: '��Ա�������',
        columnWidth: .5,
        margins :5,
        border : false,
        contentEl:Ext.get('Staff')
    });
	//�豸ʹ��������� 
	var  equipment=new Ext.Panel({
        title: '�豸ʹ�����',
        columnWidth: .5,
        margins :5,
        border : false,
        contentEl:Ext.get('Equipment')
    });
	var onepanel= new Ext.form.FieldSet({
		title: '������',
        collapsible: true,
		layout:'column',
		autoHeight:true,
		items:[staff,equipment]
	});
	//ά��רҵ���� 
	var  maintainBusiness=new Ext.form.FieldSet({  
        title: 'ά��רҵ',
        collapsible:true,
        autoHeight:true,
        contentEl:Ext.get('MaintainBusiness')
    });
	//��ά��˾�������� 
	var  maintainCompanySearch=new Ext.Panel({
        title: '��ά��˾',
        columnWidth: .5,
        margins :5,
        border : false,
        contentEl:Ext.get('MaintainCompanySearch')
    });
	//������������ 
	var  certificateSearch=new Ext.Panel({
        title: '����',
        columnWidth: .5,
        margins :5,
        border : false,
        contentEl:Ext.get('CertificateSearch')
    });
	var towpanel= new Ext.form.FieldSet({
		title: '��Ϣ����',
		layout:'column',
		collapsible:true,
		items:[maintainCompanySearch,certificateSearch]
	});

var root = new Ext.tree.AsyncTreeNode({
		text : '��Ա����',
		expanded : true,
		id : 'root'
	});
// ���Ҳ�
   var lefttree = new Ext.tree.TreePanel({
		// �����첽���ڵ�����ݼ�����
		loader : new Ext.tree.TreeLoader({
					baseAttrs : {// �����ӽڵ�Ļ�������
						cust : 'client'
					},
					dataUrl : '${ctx}/DesktopAction.do?method=getMenu'
				}),
		border : false,
		margins:5,
		padding:5,
		autoScroll : true,
		title : '��Ա����',
		root : root,
		rootVisible : true
		//renderTo : 'eastpanel'
	});

	// �������
	var centerpanel = new Ext.Panel({
		autoHeight:true,
		border : false,
		items:[onepanel,maintainBusiness,towpanel],
		renderTo:'centerpanel'
	});
	
	//����---��ά��˾��Ա����ͼ��--add by wangjie
	percentagesChart();
	//����---�ն˱���--add by wangjie
	terminalChart();
	//��ʾ---ά��רҵ������--add by wangjie
	Ext.get("MaintainBusinessTable").setStyle("display","");
	//��ʾ---��������FORM--add by wangjie
	Ext.get("CertificateSearchForm").setStyle("display","");
	//��ʾ---��������FORM--add by wangjie
	Ext.get("MaintainCompanySearchForm").setStyle("display","");
	
	
	
	//��ά��˾����----����
    maintainregioncombotree = new TreeComboField({
		width : 160,
		height : 100,
		allowBlank : true,
		leafOnly : false,
		renderTo : 'combotree_regionid',
		name : "regionname",
		hiddenName : "regionid",
		displayField : 'text',
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			autoHeight:true, 
			root : new Ext.tree.AsyncTreeNode({
				id : '000000',
				loader : new Ext.tree.TreeLoader({
					dataUrl : '${ctx}/common/externalresources_getRegionJson.jspx'
				})
			})
		})
	});//����
	
	//��ά��˾����----�ϼ���˾
	combotreeparentcontractor = new TreeComboField({
		width : 160,
		allowBlank:true,
		leafOnly : false,
		height : 100,
		renderTo : 'combotree_parent_contractor',
		name : "patrolgroupname",
		hiddenName : "parentcontractorid",
		displayField : 'text',
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			autoHeight:true, 
			root : new Ext.tree.AsyncTreeNode({
				id : 'root',
				loader : new Ext.tree.TreeLoader({
							dataUrl : '${ctx}/common/externalresources_getContractorJson.jspx'
						})
			})
		})
	});//�ϼ���˾
	
	
	//��������----��ά��˾
	combotreecontractor = new TreeComboField({
		width : 160,
		allowBlank:true,
		leafOnly : false,
		height : 100,
		renderTo : 'combotree_contractor',
		name : "patrolgroupname",
		hiddenName : "objectid",
		displayField : 'text',
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			autoHeight:true, 
			root : new Ext.tree.AsyncTreeNode({
				id : 'root',
				loader : new Ext.tree.TreeLoader({
							dataUrl : '${ctx}/common/externalresources_getContractorJson.jspx'
						})
			})
		})
	});//��ά��˾
	//��������
	var  apptitudetype = new Appcombox({
	    width : 160,
	   	hiddenName : 'certificatetype',
	   	emptyText : '��ѡ��',
	   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=APTITUDETYPE',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:true,
	   	renderTo: 'apptitude_type'
	})

})


//��ά��˾��Ա����ͼ��--add by wangjie
function percentagesChart(){
	var chart = new Highcharts.Chart({
		chart: {
				renderTo: 'percentagesChart_container',
				defaultSeriesType: 'column',
				height:220
		},
		title: {
				text: ''
		},
		xAxis: {
				categories: [
					<c:forEach var="item" items="${psls}">
                  		'${item.CONTRACTORNAME}',
	    			</c:forEach>
				]
		},
		yAxis: {
				min: 0,
				title: {
					text: '�ٷֱ�(%)'
				}
		},
		legend: {
				 layout: 'horizontal',
				 backgroundColor: '#FFFFFF',
				align: 'right',
				verticalAlign: 'top',
				floating: true,
				shadow: true
		},
		tooltip: {
				formatter: function() {
					return ''+this.x +': '+ this.y +'%';
				}
		},
		plotOptions: {
				column: {
					pointPadding: 0.2,
					borderWidth: 0
				}
		},
		credits: {
					enabled: false
		},
		series: [
                    {
             			name: '������',
              			data: [
              			   <c:forEach var="item" items="${psls}">
      								${item.PERCENTAGE},
      					   </c:forEach>
      					]},	
				]
		});
}

//�ն˱���--add by wangjie
function terminalChart(){
	var chart = new Highcharts.Chart({
		chart: {
				renderTo: 'terminalChart_container',
				plotBackgroundColor: null,
				plotBorderWidth: null,
				plotShadow: false,
				height:220
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

</script>

	</head>

	<body style="margin:5px;padding:5px;border: none;">
	<div id='centerpanel' style="width:85%;float: left;">
	</div>
	<div id='eastpanel' style="width:12%;float:right">
	</div>
	<!--��Ա������ -->
    <div id='Staff'>
    	<!-- ��Ա�����ʱ������� add by wangjie -->
     	<div id='percentagesChart_container'></div>
    </div>
    
    <!--�豸ʹ����� -->
    <div id='Equipment'>
    	<!-- �豸ʹ������������� add by wangjie -->
     	<div id='terminalChart_container'></div>
    </div>
    <!--ά��רҵ -->
    <div id='MaintainBusiness' >
        <!-- ά��רҵ ��ϸ�� add by wangjie -->
    	<table id="MaintainBusinessTable" cellspacing="0" cellpadding="0" border="0" style="display:none" class="Detailed_list_analysis">
    		<tr>
				<td style="background-color:#eee">
    				��ά��˾
    			</td>
    			<td style="background-color:#eee">
     				ά��רҵ
    			</td>
    			<td style="background-color:#eee">
     				ά������
    			</td>
    			<td style="background-color:#eee">
    	 			ά����
    			</td>
   			</tr>
   			<c:forEach var="item" items="${rsls}">
    		<tr>
				<td>
    				${item.contractorname}
    			</td>
    			<td>
     				${item.resource_id}
    			</td>
    			<td>
     				${item.certificatename}
    			</td>
   				<td>
    	 			${item.patrolmancount}
    			</td>
   			</tr>
   			</c:forEach>
		</table>
		<!-- end table -->
    </div>
    <!--��ά��˾����-->
    <div id='MaintainCompanySearch' >
        <!--��ά��˾���� FORM add by wangjie-->
        <div id="MaintainCompanySearchForm" style="display:none">
    	<html:form method="Post" action="/contractorAction.do?method=queryContractor">
			<template:formTable contentwidth="220" namewidth="220">
			<template:formTr name="��λ����">
				<html:text property="contractorName" styleClass="inputtext" style="width:160" maxlength="45" />
			</template:formTr>
			<template:formTr name="��֯����">
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
			</template:formTr>
			<template:formTr name="��������">
				<div id="combotree_regionid"></div>
			</template:formTr>
			<template:formTr name="�ϼ���˾">
				<div id="combotree_parent_contractor"></div>
			</template:formTr>
			<template:formTr name="����רҵ">
				<c:forEach var="res" items="${resources}" varStatus="stauts">
					<html:checkbox property="resourcesId" value="${res.code}">${res.resourcename}</html:checkbox>
					<c:if test="${(stauts.index+1)%2==0}">
						<br>
					</c:if>
				</c:forEach>
			</template:formTr>
			<template:formSubmit>
			<td>
				<html:submit styleClass="button">��ѯ</html:submit>
			</td>
			<td>
				<html:reset styleClass="button">ȡ��</html:reset>
					</td>
					<td>
						<html:button property="action" styleClass="button" onclick="addGoBack()">����</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
		</div>
		<!-- end form -->
    </div>
     <!--��������-->
    <div id='CertificateSearch' >
    	 <!-- ��������FORM add by wangjie-->
    	<html:form action="/CertificateAction?method=doQuery" styleId="queryForm2">
			<table id="CertificateSearchForm" style="display:none"  border="0" align="center" cellpadding="0" cellspacing="0" class="tabout">
				<tr class="trcolor">
					<td class="tdulleft">֤���ţ�</td><td class="tdulright">
						<html:text property="certificatecode"  maxlength="20" />
					</td>
				</tr>
				<tr class="trwhite"><td class="tdulleft">֤�����ƣ�</td><td class="tdulright"><html:text property="certificatename" maxlength="25" />
					<br></td>
				</tr>
				<tr class="trcolor"><td class="tdulleft">֤�����ͣ�</td><td class="tdulright">
					<div id="apptitude_type"></div>
				</tr>
				<logic:equal value="1" name="LOGIN_USER" property="deptype">
				<tr class="trwhite"><td class="tdulleft">��ά��λ��</td><td class="tdulright">
						<div id="combotree_contractor"></div>
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
		<!-- end form -->
    </div>

	</body>
</html>