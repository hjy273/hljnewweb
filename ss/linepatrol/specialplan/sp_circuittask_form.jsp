
<div id="dialog" class="popup">
	<div class="popup-header">
		<h2>�������</h2>
		<a href="javascript:;" onclick="jQuery.closePopupLayer('addCTask')" title="Close" class="close-link">X</a>
		<br/>
	</div>
	<div class="popup-body">
		<form id="taskform" action="${ctx}/specialplan.do?method=addCircuitTask" method="post">
			<div style="text-align: right;"><input type="button" name="submit" onclick="saveCTask('${cableLevel}');setCValue('${cableLevel}',$('taskName').value,$('patrolNum').value,$('startTime').value,$('endTime').value)" value="ȷ��"/></div>
			<table width="80%" align="center" border="1" class="tabout" cellpadding="0" cellspacing="1">
				<tr>
					<td class="tdulleft" style="width:30%">��������:</td>
					<td class="tdulright" style="width:70%">
						<input type="text" id="taskName" name="taskName" value="${cableLevelName}"/>
						<input type="hidden" name="lineLevel" id="lineLevel" value="${cableLevel}"/>
					</td>
				</tr>
				<tr>
					<td class="tdulleft">Ѳ��ʱ��:</td>
					<td class="tdulright">
						<input name="startTime" id="startTime" class="inputtext" style="width:40px;" type="text" onfocus="WdatePicker({dateFmt:'HH:mm'})" value="00:00" />--<input name="endTime" id="endTime" class="inputtext" style="width:40px;" type="text" value="23:59" onfocus="WdatePicker({dateFmt:'HH:mm'})"/>
					</td>
				</tr>
				<tr>
					<td class="tdulleft">Ѳ�ش���:</td>
					<td class="tdulright">
						<input type="text" name="patrolNum" id="patrolNum" value="${patrolNum}"/>
					</td>
				</tr>
				<tr>
					<td class="tdulleft">�߶�ɸѡ����:</td>
					<td class="tdulright">
					 	<input type="text" name="key" id="key" onfocus="javascript:$('key').value='';" value="������ɸѡ����"/>	<input type="button" name="" onclick="getTaskSubline()"  value="����߶�"/>
					 	<script type="text/javascript">
					 		function getTaskSubline(){
					 			var url = 'specialplan.do?method=loadSubline&lineLevel=${cableLevel}&key='+$("key").value;
					 			new Ajax.Request(url,{
						        	method:'post',
						        	evalScripts:true,
						        	onSuccess:function(transport){
					 					$('taskSubline').innerHTML = transport.responseText;
						        	},
						        	onFailure: function(){ alert('��������쳣......') }
				       		 	});
					 		}
					 	</script>
					</td>
				</tr>
				<tr style="background:#F5F5F5">
					<td class="tdulleft">&nbsp;
					</td>
					<td class="tdulright">
						<input type="checkbox" name="checkedAll" id="checkedAll"/>ȫѡ/ȡ��ȫѡ
					</td>
				</tr>
				<tr>
					<td class="tdulleft">�����߶Σ�
					</td>
					<td class="tdulright" id="taskSubline">
						
					</td>
				</tr>
			</table>
			<input type="button" name="submit" onclick="submitForm();" value="ȷ��"/>
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
					alert("Ѳ�ش�������Ϊ���֣�");
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
		        	onFailure: function(){ alert('�������ʧ�ܣ�') }
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
			 //�߶�ȫѡ����
			 jQuery(function() {
				 	jQuery("#checkedAll").click(function() { 
				 		if (jQuery(this).attr("checked") == true) { // ȫѡ
							jQuery("input[name='tasksubline']").each(function() {
				  			jQuery(this).attr("checked", true);
				  		});
				 		}else { // ȡ��ȫѡ
				 			jQuery("input[name='tasksubline']").each(function() {
				 				jQuery(this).attr("checked", false);
				  		});
				 		}
					});
			});
		</script>
	</div>
</div>