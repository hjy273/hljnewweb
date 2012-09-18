
<div id="dialog" class="popup">
	<div class="popup-header">
		<h2>添加任务</h2>
		<a href="javascript:;" onclick="jQuery.closePopupLayer('addCTask')" title="Close" class="close-link">X</a>
		<br/>
	</div>
	<div class="popup-body">
		<form id="taskform" action="${ctx}/specialplan.do?method=addCircuitTask" method="post">
			<div style="text-align: right;"><input type="button" name="submit" onclick="saveCTask('${cableLevel}');setCValue('${cableLevel}',$('taskName').value,$('patrolNum').value,$('startTime').value,$('endTime').value)" value="确定"/></div>
			<table width="80%" align="center" border="1" class="tabout" cellpadding="0" cellspacing="1">
				<tr>
					<td class="tdulleft" style="width:30%">任务名称:</td>
					<td class="tdulright" style="width:70%">
						<input type="text" id="taskName" name="taskName" value="${cableLevelName}"/>
						<input type="hidden" name="lineLevel" id="lineLevel" value="${cableLevel}"/>
					</td>
				</tr>
				<tr>
					<td class="tdulleft">巡回时段:</td>
					<td class="tdulright">
						<input name="startTime" id="startTime" class="inputtext" style="width:40px;" type="text" onfocus="WdatePicker({dateFmt:'HH:mm'})" value="00:00" />--<input name="endTime" id="endTime" class="inputtext" style="width:40px;" type="text" value="23:59" onfocus="WdatePicker({dateFmt:'HH:mm'})"/>
					</td>
				</tr>
				<tr>
					<td class="tdulleft">巡回次数:</td>
					<td class="tdulright">
						<input type="text" name="patrolNum" id="patrolNum" value="${patrolNum}"/>
					</td>
				</tr>
				<tr>
					<td class="tdulleft">线段筛选条件:</td>
					<td class="tdulright">
					 	<input type="text" name="key" id="key" onfocus="javascript:$('key').value='';" value="请输入筛选条件"/>	<input type="button" name="" onclick="getTaskSubline()"  value="获得线段"/>
					 	<script type="text/javascript">
					 		function getTaskSubline(){
					 			var url = 'specialplan.do?method=loadSubline&lineLevel=${cableLevel}&key='+$("key").value;
					 			new Ajax.Request(url,{
						        	method:'post',
						        	evalScripts:true,
						        	onSuccess:function(transport){
					 					$('taskSubline').innerHTML = transport.responseText;
						        	},
						        	onFailure: function(){ alert('请求服务异常......') }
				       		 	});
					 		}
					 	</script>
					</td>
				</tr>
				<tr style="background:#F5F5F5">
					<td class="tdulleft">&nbsp;
					</td>
					<td class="tdulright">
						<input type="checkbox" name="checkedAll" id="checkedAll"/>全选/取消全选
					</td>
				</tr>
				<tr>
					<td class="tdulleft">任务线段：
					</td>
					<td class="tdulright" id="taskSubline">
						
					</td>
				</tr>
			</table>
			<input type="button" name="submit" onclick="submitForm();" value="确定"/>
		</form>
		<script type="text/javascript">
			function submitForm(){
				if(saveCTask('${cableLevel}')){
					setCValue('${cableLevel}',$('taskName').value,$('patrolNum').value,$('startTime').value,$('endTime').value);
				}
			}
			function saveCTask(level){
				var regNumber=/^[0-9]*$/;
				if(!regNumber.test($("patrolNum").value)){
					alert("巡回次数必须为数字！");
					return false;
				}
				
				var sltNodes = checkboxSelect();
				var url = "${ctx}/specialplan.do?method=addCircuitTask";

				url = url+"&taskName="+$("taskName").value +"&lineLevel=${cableLevel}&patrolNum="+$("patrolNum").value;
				url = url+"&startTime="+$("startTime").value +"&endTime="+$("endTime").value;
				url = url+"&tasksubline="+sltNodes;

	 			new Ajax.Request(url,{
		        	method:'post',
		        	evalScripts:true,
		        	onSuccess:function(transport){
		 				jQuery('#tasksublinelabel_${cableLevel}').empty();
	 					jQuery('#tasksublinelabel_${cableLevel}').append(transport.responseText);
		        	},
		        	onFailure: function(){ alert('添加任务失败！') }
       		 	});
       		 	return true;
			};
			 function checkboxSelect(){   

			        var chkbox = document.getElementsByName('tasksubline');   
			        var nodes = $A(chkbox);   
			        var sltNodes = nodes.select(function(node){   
			        	return node.checked;   
			        }); 
			        var slt ="";
					sltNodes.each(function(node)   
			        {   
			         slt += node.value+"," ;
			        });  

			        return slt;
			 }
			 //线段全选设置
			 jQuery(function() {
				 	jQuery("#checkedAll").click(function() { 
				 		if (jQuery(this).attr("checked") == true) { // 全选
							jQuery("input[name='tasksubline']").each(function() {
				  			jQuery(this).attr("checked", true);
				  		});
				 		}else { // 取消全选
				 			jQuery("input[name='tasksubline']").each(function() {
				 				jQuery(this).attr("checked", false);
				  		});
				 		}
					});
			});
		</script>
	</div>
</div>