
<div id="dialog" class="popup">
	<div class="popup-header">
		<h2>�������</h2>
		<a href="javascript:;" onclick="jQuery.closePopupLayer('addWTask')" title="Close" class="close-link">X</a>
		<br/>
	</div>
	<div class="popup-body">
		<form action="" id="form">
			<table width="80%" align="center" border="1" class="tabout" cellpadding="0" cellspacing="1">
				<tr>
					<td class="tdulleft" style="width:30%">����ʱ�Σ�</td>
					<td class="tdulright" style="width:70%">
						<input name="startTime" id="startTime" class="inputtext" style="width:40px;" type="text" onfocus="WdatePicker({dateFmt:'HH:mm'})" value="8:00" />--<input name="endTime" id="endTime" class="inputtext" style="width:40px;" type="text" value="22:00" onfocus="WdatePicker({dateFmt:'HH:mm'})"/>
					</td>
				</tr>
				<tr>
					<td class="tdulleft" style="width:30%">�Զ����ͼ����</td>
					<td class="tdulright" style="width:70%">
						<input name="space" id="space" class="inputtext" style="width:40px;" type="text" value="30" />����
					</td>
				</tr>
				<tr>
					<td class="tdulleft">����ɸѡ����:</td>
					<td class="tdulright">
					 	<input type="text" name="key" id="key" onfocus="javascript:$('key').value='';" value="������ɸѡ����"/>	<input type="button" name="" onclick="getTaskArea()"  value="��� ����"/><input type="button" onclick="openwin()" value="�ƶ���������"/>
					 	<script type="text/javascript">
					 		function getTaskArea(){
					 			var url = 'specialplan.do?method=loadTaskArea&key='+$("key").value;
					 			new Ajax.Request(url,{
						        	method:'post',
						        	evalScripts:true,
						        	onSuccess:function(transport){
					 					$('taskarea').innerHTML = transport.responseText;
						        	},
						        	onFailure: function(){ alert('��������쳣......') }
				       		 	});
					 		}
					 	</script>
					</td>
				</tr>
				<tr>
					<td class="tdulleft">��������
					</td>
					<td class="tdulright" id="taskarea">
						
					</td>
				</tr>
			</table>
			<input type="button" name='submit' onclick="saveWTask();setWValue($('startTime').value,$('endTime').value,$('space').value)" value="ȷ��"/>
		</form>
		<script type="text/javascript">
			
			function saveWTask(){
				var sltNodes = checkboxSelect();
				var url = '${ctx}/specialplan.do?method=addWatchTask';
				url = url+"&startTime="+$("startTime").value +"&endTime="+$("endTime").value+"&space="+$("space").value;
				url = url+"&taskArea="+sltNodes;
	 			new Ajax.Request(url,{
		        	method:'post',
		        	evalScripts:true,
		        	onSuccess:function(transport){
		 				jQuery('#taskwatcharea').empty();
	 					jQuery('#taskwatcharea').append(transport.responseText);
		        	},
		        	onFailure: function(){ alert('�������ʧ�ܣ�') }
       		 	});
			};
			 function checkboxSelect(){   
			        var chkbox = document.getElementsByName('taskArea');   
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
			

		</script>
	</div>
</div>