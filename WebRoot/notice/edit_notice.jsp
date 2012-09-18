<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ include file="/common/header.jsp"%>
<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx }/js/jQuery/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${ctx }/js/jQuery/easyui/themes/icon.css">
<script type="text/javascript"
	src="${ctx }/js/jQuery/easyui/jquery.easyui.min.js"></script>
<script language="JavaScript" src="${ctx}/js/validate.js"></script>
<script language="javascript" type="text/javascript">
	//function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
	function isValidForm() {
		var patrolmanSon = getChecked();
		jQuery("#acceptUserIds").val(patrolmanSon);
		jQuery("#meetPerson").val(patrolmanSon);
		var title = document.NoticeBean.title.value.replace(/(^\s*)/g, "")
				.replace(/(\s*$)/g, "");
		var type = document.NoticeBean.type.value.replace(/(^\s*)/g, "")
				.replace(/(\s*$)/g, "");
		if (title == "" || title == "null") {
			alert("标题不能为空或者空格或者null!! ");
			return false;
		}
		if (type == "" || type == "null") {
			alert("类型不能为空或者空格或者null!! ");
			return false;
		}
		if (document.NoticeBean.type.value == "B21") {
			if (document.NoticeBean.meetTime.value == "") {
				alert("会议开始时间不能为空！");
				return false;
			}
			if (document.NoticeBean.meetEndTime.value == "") {
				alert("会议结束时间不能为空！");
				return false;
			}

			//document.NoticeBean.mobileIds.value=document.NoticeBean.meetMobiles.value;
			var beginDate = convertStrToDate(
					document.NoticeBean.meetTime.value, " ", "/", ":");
			var endDate = convertStrToDate(
					document.NoticeBean.meetEndTime.value, " ", "/", ":");
			if (compareTwoDate(beginDate, endDate) > 0) {
				alert("会议开始时间不能迟于会议结束时间！");
				return false;
			}
			if (document.NoticeBean.meetAddress.value == "") {
				alert("会议地点不能为空！");
				return false;
			}
		} else if (document.NoticeBean.type.value == "B20") {
			//document.NoticeBean.mobileIds.value=document.NoticeBean.noticeMobiles.value;
		}
		if (document.NoticeBean.type.value != "B22") {
			if (patrolmanSon == "") {
				alert("发布对象不能为空！");
				return false;
			}
		}
		return true;
	}
	function issue() {
		NoticeBean.isissue.value = 'y';
		if (isValidForm()) {
			NoticeBean.submit();
		}
	}
	function save() {
		NoticeBean.isissue.value = 'n';
		if (isValidForm()) {
			NoticeBean.submit();
		}
	}

	function changeType(value) {
		if (value == "B21") {
			document.getElementById("meetTimeTr").style.display = "";
			document.getElementById("meetEndTimeTr").style.display = "";
			document.getElementById("meetAddressTr").style.display = "";
			//document.getElementById("meetPersonTr").style.display="";
			document.getElementById("sendSmTbody").style.display = "";
			document.getElementById("acceptSmUserTr").style.display = "";
		} else if (value == "B20") {
			document.getElementById("meetTimeTr").style.display = "none";
			document.getElementById("meetEndTimeTr").style.display = "none";
			document.getElementById("meetAddressTr").style.display = "none";
			//document.getElementById("meetPersonTr").style.display="";
			document.getElementById("sendSmTbody").style.display = "";
			document.getElementById("acceptSmUserTr").style.display = "";
		} else {
			document.getElementById("meetTimeTr").style.display = "none";
			document.getElementById("meetEndTimeTr").style.display = "none";
			document.getElementById("meetAddressTr").style.display = "none";
			//document.getElementById("meetPersonTr").style.display="none";
			document.getElementById("sendSmTbody").style.display = "none";
			document.getElementById("acceptSmUserTr").style.display = "none";
		}
	}
	Ext
			.onReady(function() {
				//信息类型	type			notice_type			INFORMATION
				var notice_type = new Appcombox(
						{
							hiddenName : 'type',
							hiddenId : 'notice_type',
							width : 150,
							emptyText : '请选择',
							dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=INFORMATION',
							dataCode : 'CODEVALUE',
							dataText : 'LABLE',
							allowBlank : true,
							renderTo : 'notice_type_div'
						});
				notice_type
						.setComboValue(
								'${notice.type}',
								'<apptag:dynLabel objType="dic" id="${notice.type}" dicType="INFORMATION"></apptag:dynLabel>');
				notice_type.on("select", function(combo, record) {
					changeType(combo.getValue());
				});
				var checkValues = "${notice.acceptUserIds}";
				jQuery("#personTree")
						.tree(
								{
									checkbox : true,
									url : "${ctx}/common/externalresources_getOrgDeptUserJson.jspx?flag=1&node=&regionid=${LOGIN_USER.regionid}&orgtype=&objtype=STAFF&lv=3&rnd="
											+ Math.random(),
									onLoadSuccess : function checkedPersonTree() {
										if (checkValues) {
											var arr = checkValues.split(",");
											for (i = 0; i < arr.length; i++) {
												var node = jQuery("#personTree")
														.tree('find', arr[i]);
												if (node) {
													jQuery("#personTree").tree(
															'check',
															node.target);
												}
											}
										}
									}
								});
			});
	function getChecked() {
		var nodes = $('#personTree').tree('getChecked');
		var s = '';
		for ( var i = 0; i < nodes.length; i++) {
			if (s != '') {
				s += ',';
			}
			s += nodes[i].id;
		}
		return s;
	}
</script>
<script type="text/javascript" src="${ctx}/js/ckeditor/ckeditor.js"></script>
<body onload="//changeType(jQuery('#notice').val())">
	<template:titile value="信息发布" />

	<html:form method="Post" action="/NoticeAction.do?method=updateNotice"
		enctype="multipart/form-data">
		<template:formTable>
			<template:formTr name="标题" isOdd="false">
				<html:text property="title" name="notice" styleClass="inputtext"
					style="width:500px" maxlength="25" />&nbsp;&nbsp;<font color="red">*</font>
			</template:formTr>

			<template:formTr name="类型" isOdd="false">
				<div id="notice_type_div"></div>
			</template:formTr>
			<template:formTr name="重要程度" isOdd="false">
				<html:select property="grade" name="notice" styleClass="inputtext"
					style="width:150px">
					<html:option value="一般">一般</html:option>
					<html:option value="重要">重要</html:option>
				</html:select>
				<html:hidden property="isCanceled" name="notice" />
				<html:hidden property="oldMeetTime" name="notice" />
				<html:hidden property="oldMeetEndTime" name="notice" />
				<html:hidden property="oldMeetAddress" name="notice" />
				<html:hidden property="oldMeetPerson" name="notice" />
			</template:formTr>

			<template:formTr name="会议开始时间" isOdd="true" tagID="meetTimeTr"
				style="display:none;">
				<input type="text" id="protimeid" name="meetTime"
					value="${notice.meetTime}" readonly="readonly" class="Wdate"
					style="width: 150px"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1'})" />
			</template:formTr>
			<template:formTr name="会议结束时间" isOdd="true" tagID="meetEndTimeTr"
				style="display:none;">
				<input type="text" id="protimeid" name="meetEndTime"
					value="${notice.meetEndTime}" readonly="readonly" class="Wdate"
					style="width: 150px"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1'})" />
			</template:formTr>
			<template:formTr name="会议地点" isOdd="true" tagID="meetAddressTr"
				style="display:none;">
				<html:text property="meetAddress" name="notice"
					styleClass="inputtext" style="width:300px" />
			</template:formTr>
			<tbody id="meetPersonTr" style="display: none;">
			</tbody>
			<tbody id="acceptSmUserTr">
				<template:formTr name="发布对象" isOdd="true">
					<div id="personTree"></div>
					<input id="meetPerson" name="meetPerson" value="" type="hidden">
					<input id="acceptUserIds" name="acceptUserIds" value=""
						type="hidden">
				</template:formTr>
			</tbody>
			<tbody id="sendSmTbody" style="display: ;">
				<tr class=trcolor>
					<td class="tdr">
						短信发送方式：
					</td>
					<td class="tdl">
						<input name="sendMethod" value="0" type="radio"
							onclick="timeInputSpan.style.display='none';timeSpaceTr.style.display='none';" />
						即时发送
						<input name="sendMethod" value="1" type="radio"
							onclick="timeInputSpan.style.display='';timeSpaceTr.style.display='';" />
						定时发送
					</td>
				</tr>
				<tr class=trcolor id="timeInputSpan" style="display: none;">
					<td class="tdr">
						短信发送时间：
					</td>
					<td>
						<input type="text" id="protimeid" name="beginDate"
							value="${notice.beginDate}" readonly="readonly" class="Wdate"
							style="width: 150px"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1'})" />
						到
						<input type="text" id="protimeid" name="endDate"
							value="${notice.endDate}" readonly="readonly" class="Wdate"
							style="width: 150px"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1'})" />
					</td>
				</tr>
				<tr class=trcolor id="timeSpaceTr" style="display: none;">
					<td class="tdr">
						短信发送间隔：
					</td>
					<td class="tdl">
						<input name="sendTimeSpace" value="${notice.sendTimeSpace}"
							type="text" style="width: 100px" />
						<input name="sendIntervalType" value="-1" type="radio" />
						单次
						<input name="sendIntervalType" value="0" type="radio" />
						秒
						<input name="sendIntervalType" value="1" type="radio" />
						分
						<input name="sendIntervalType" value="2" type="radio" />
						时
						<input name="sendIntervalType" value="3" type="radio" />
						天
						<c:if test="${notice.sendIntervalType=='-1'}">
							<script type="text/javascript">
	document.NoticeBean.sendIntervalType[0].checked = true;
</script>
						</c:if>
						<c:if test="${notice.sendIntervalType=='0'}">
							<script type="text/javascript">
	document.NoticeBean.sendIntervalType[1].checked = true;
</script>
						</c:if>
						<c:if test="${notice.sendIntervalType=='1'}">
							<script type="text/javascript">
	document.NoticeBean.sendIntervalType[2].checked = true;
</script>
						</c:if>
						<c:if test="${notice.sendIntervalType=='2'}">
							<script type="text/javascript">
	document.NoticeBean.sendIntervalType[3].checked = true;
</script>
						</c:if>
						<c:if test="${notice.sendIntervalType=='3'}">
							<script type="text/javascript">
	document.NoticeBean.sendIntervalType[4].checked = true;
</script>
						</c:if>
					</td>
				</tr>
				<c:if test="${notice.sendMethod=='0'}">
					<script type="text/javascript">
	document.NoticeBean.sendMethod[0].checked = true;
	timeInputSpan.style.display = "none";
	timeSpaceTr.style.display = "none";
</script>
				</c:if>
				<c:if test="${notice.sendMethod=='1'}">
					<script type="text/javascript">
	document.NoticeBean.sendMethod[1].checked = true;
	timeInputSpan.style.display = "";
	timeSpaceTr.style.display = "";
</script>
				</c:if>
			</tbody>
			<template:formTr name="附件" isOdd="false">
				<apptag:upload state="edit" entityId="${notice.id}"
					entityType="NOTICE_CLOB"></apptag:upload>
			</template:formTr>
			<template:formTr name="内容">
				<textarea cols="200px" id="editor_v2" name="contentString" rows="10">${notice.contentString}</textarea>
				<script type="text/javascript">
	//         
	CKEDITOR.replace('editor_v2', {
		skin : 'v2',
		toolbar : 'MyToolbar',
		filebrowserUploadUrl : '${ctx}/ckeditor/uploader?Type=File',
		filebrowserImageUploadUrl : '${ctx}/ckeditor/uploader?Type=Image',
		filebrowserFlashUploadUrl : '${ctx}/ckeditor/uploader?Type=Flash'
	});
	//
</script>
			</template:formTr>
			<html:hidden property="isissue" name="notice" />
			<html:hidden property="id" name="notice" />
			<html:hidden property="fileinfo" name="notice" />

			<template:formSubmit>
				<td>
					<html:button property="action" styleClass="button"
						onclick="issue()">立即发布</html:button>
				</td>
				<td>
					<html:button styleClass="button" onclick="save()" property="action">保存</html:button>
				</td>
				<td>
					<html:reset styleClass="button">取消</html:reset>
				</td>
			</template:formSubmit>


		</template:formTable>
	</html:form>
	<script type="text/javascript">
	changeType("${notice.type}");
</script>
</body>