<%@include file="/common/header.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>ͳ�Ʒ�����ϸ��Ϣ��</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
		<script src="${ctx}/js/highchart/highcharts.js" type="text/javascript"></script>

		<script type="text/javascript" language="javascript">
   function addGoBack()
        {
            try
			{
                location.href = "${ctx}/TowerPatrolinfo.do?method=showPlanInfo";
                return true;
            }
            catch(e){
                alert(e);
            }
        }
    //���ID
  	function loadPatrolItemDetail(id){
 		var url = "${ctx}/TowerPatrolinfo.do?method=loadPatrolItemDetail&rid="+id;
    	window.location.href=url;
 	}
Ext.onReady(function() {
  var	fieldSet1 = new Ext.form.FieldSet({
        collapsible: true,
		allowBlank : false,
        title: '�������',
        autoHeight:true,
  		renderTo: 'fieldSet1',
        padding:5,
        contentEl:Ext.get("fieldSet1EL")
		});

	var fieldSet2 = new Ext.form.FieldSet({
        collapsible: true,
		allowBlank : false,
        title: '�ƻ�������',
        autoHeight:true,
      	renderTo: 'fieldSet2',
        padding :5,
		contentEl:Ext.get("fieldSet2EL")
		});
	
	var fieldSet3 = new Ext.form.FieldSet({
        collapsible: true,
		allowBlank : false,
        title: 'ͼ��',
        autoHeight:true,
        padding:5,
       	renderTo: 'fieldSet3',
        contentEl:Ext.get("fieldSet3EL")
		});

	var resCount = ${patrolanalysiscount.planrescount};
	if(resCount != 0) {
	    //��״̬
   		var piechart = new Highcharts.Chart({
					chart: {
						renderTo: 'container',
						plotBackgroundColor: null,
						plotBorderWidth: null,
						plotShadow: false
					},
					title: {
						text: 'δѲ����������Ѳ�����Ա�'
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
						data: [
							['δѲ����', Math.round(100*(${patrolanalysiscount.planrescount}-${patrolanalysiscount.alreadyrescount})/${patrolanalysiscount.planrescount})],
							['��Ѳ����', Math.round(100* ${patrolanalysiscount.alreadyrescount}/${patrolanalysiscount.planrescount})],
						]
					}]
				});
            //��״ͼ
        	columnchart = new Highcharts.Chart({
					chart: {
						renderTo: 'container1',
						defaultSeriesType: 'column'
					},
					title: {
						text: '��Ѳ����Ա�'
					},
					xAxis: {
						categories: [${patrolnames}]
					},
					yAxis: {
						min: 0,
						title: {
							text: '�ƻ�Ѳ����'
						},
						stackLabels: {
							enabled: true,
							style: {
								fontWeight: 'bold',
								color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
							}
						}
					},
                     credits: {
						enabled: false
					},
					legend: {
						layout: 'vertical',
						backgroundColor: '#FFFFFF',
						align: 'left',
						verticalAlign: 'top',
						x: 100,
						y: 70,
						floating: true,
						shadow: true

					},
					tooltip: {
						formatter: function() {
							return ''+
								this.series.name  +': '+ this.y;

						}
					},
					plotOptions: {
						column: {
							pointPadding: 0.2,
							borderWidth: 0
						}
					},
				    series: [{
						name: '�ƻ�Ѳ����',
						data: [${rescounts}]
					}, {
						name: 'δѲ����',
						data: [${norescounts}]
					}, {
						name: '��Ѳ����',
						data: [${alrescounts}]
					}]
				});
	}
	});
</script>
	</head>
	<body>
		<template:titile value="${title }" />
		<div id="fieldSet1" style="width: 99%;">
		</div>
		<div id="fieldSet2" style="width: 99%;">
		</div>
		<div id="fieldSet3" style="width: 99%;">
		</div>
		<div id="fieldSet1EL">
			<table width="850" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tbody id="geoInfoTb" style="display: ;">
					<tr class="trwhite">
						<td class="tdulleft" style="width: 15%">
							Ѳ��ƻ�����
						</td>
						<td class="tdulright" style="width: 35%">
							${patrolanalysiscount.plancount}
						</td>
						<td class="tdulleft" style="width: 15%">
							�ƻ�Ѳ������
						</td>
						<td class="tdulright" style="width: 35%">
							${patrolanalysiscount.planrescount}
						</td>
					</tr>
					<tr class="trwhite">
						<td class="tdulleft" style="width: 15%">
							��Ѳ������
						</td>
						<td class="tdulright" style="width: 35%">
							${patrolanalysiscount.alreadyrescount}
						</td>
						<td class="tdulleft" style="width: 15%">
							Ѳ���ʣ�
						</td>
						<td class="tdulright" style="width: 35%">
							${patrolanalysiscount.planresrate}
						</td>
					</tr>
				</tbody>
			</table>
			<display:table name="sessionScope.patrolgroupanalysis"
				id="currentRowObject">
				<display:column property="contractorname" title="��ά��˾"
				 />
				<display:column title="Ѳ����" property="patrolname" />
				<display:column title="������Ա��" property="patrolmancount" />
				<display:column title="Ѳ����" property="patrolrate" />
				<display:column title="����վ������" property="exceptioncount" />
			</display:table>
		</div>
		<div id="fieldSet2EL">
			<display:table name="sessionScope.patrolplanalreadyinfo"
				id="currentRowObject">
				<display:column property="plan_name" title="�ƻ�����"
					/>
				<display:column title="��ά��˾" property="contractorname" />
				<display:column title="Ѳ����" property="patrolname" />
				<display:column title="�ƻ�ʱ��" property="plantime" />
				<display:column title="�ƻ�Ѳ����" property="rescount" />
				<display:column title="��Ѳ����" property="alreadyrescount" />
				<display:column title="Ѳ����" property="patrolpercent" />
				<display:column title="����վ������" property="exceptioncount" />
			</display:table>
		</div>
		<div id="fieldSet3EL">
			<div id="container" style="float:left;width: 33%; height: 200px"></div>
			<div id="container1" style="float:right;width: 66%; height: 200px"></div>
		</div>
	</body>
</html>