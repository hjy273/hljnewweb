<%@include file="/common/header.jsp"%>
<br>
<template:titile value="�豸���·���" />
<script language="javascript">
	
</script>
<body>
<html:form method="Post" action="/ResourceAssignAction.do?method=showAssignTerminalResult">
	<table border="0" align="center" cellspacing="0" cellpadding="0">
	<tr>
	<td>
		<label for="leftTitle">
		�������豸
		</label>
		<br>
    	<html:select property="terminal" size="15"  styleId="rId"  multiple="multiple" style="width:200px">
        	<html:options collection="termCollection" property="value" labelProperty="label"/>
     	</html:select>				
	</td>
	<td valign="middle" align="center">
		<input type="button" value="&lt;-" onclick="moveSelected(document.all.target,document.all.terminal)" />
		<br />
		<br />
		<input type="button" value="-&gt;" onclick="moveSelected(document.all.terminal,document.all.target)" />
		<br />
		<br />
		<input type="button" value="&lt;&lt;--" onclick="moveAll(document.all.target,document.all.terminal);" />
		<br />
		<br />
		<input type="button" value="--&gt;&gt;" onclick="moveAll(document.all.terminal,document.all.target)" />
		<br />
		<br />
		<!-- <input type="button" value="ɾ ��" onclick="selectAll(document.all.target);deleteSelectItem(document.all.target)" /> -->
		<br />
		<br />
	</td>
	<td>
		<label for="rightTitle">������豸</label>
		<br />
		<select name="target" style="width: 200px" size="15" multiple="multiple" id="target">
						
		</select>
	</td>
	</tr>
	</table>
	<input type="hidden" name="terminalid" id="targetTerminal"/>
	<template:formSubmit>
	<td><input type="button" value="��һ��" class="button" onclick="javascript:history.go(-1);"/></td>
	<td><html:submit styleClass="button" onclick="return check()">��һ��</html:submit></td>
	<!--<td><html:reset styleClass="button">ȡ��</html:reset></td> -->
	</template:formSubmit>
</html:form>

</body>
<script type="text/javascript">
/***************************************************************************************************************
  * �� �� ����selectListTools.js 
  * �ļ�����������list�б���һЩ���߷���
  * ��Ҫ������
  *          1�� moveUp(oSelect,isToTop) ������������������������ �����ƶ�һ��list�б���ѡ����Ŀ��
  *                                                                ����֧�ֶ�ѡ�ƶ������������Ƿ��ƶ�������
  *          2�� moveDown(oSelect,isToBottom)�������������������� �����ƶ�һ��list�б���ѡ����Ŀ��
  *                                                                ����֧�ֶ�ѡ�ƶ������������Ƿ��ƶ����ײ�
  *          3�� moveSelected(oSourceSel,oTargetSel) ������������ �������б��֮��ת������
  *          4�� moveAll(oSourceSel,oTargetSel)������������������ ת�������б��֮���ȫ������
  *          5�� deleteSelectItem(oSelect) ���������������������� ɾ����ѡ����Ŀ
  * 
 ****************************************************************************************************************/
  
 /**
  * ʹѡ�е���Ŀ����
  *
  * oSelect: Դ�б��
  * isToTop: �Ƿ�����ѡ������ˣ������������ƣ�
  *          trueΪ�ƶ������ˣ�false��֮��Ĭ��Ϊfalse
  */
 function moveUp(oSelect,isToTop)
 {
     //Ĭ��״̬�����ƶ�������
     if(isToTop == null)
         var isToTop = false;
         
     //����Ƕ�ѡ------------------------------------------------------------------
     if(oSelect.multiple)
     {
         for(var selIndex=0; selIndex<oSelect.options.length; selIndex++)
         {
             //����������ƶ������˱�־
             if(isToTop)
             {
                 if(oSelect.options[selIndex].selected)
                 {
                     var transferIndex = selIndex;
                     while(transferIndex > 0 && !oSelect.options[transferIndex - 1].selected)
                     {
                         oSelect.options[transferIndex].swapNode(oSelect.options[transferIndex - 1]);
                         transferIndex --;
                     }
                 }
             }
             //û�������ƶ������˱�־
             else
             {
                 if(oSelect.options[selIndex].selected)
                 {
                     if(selIndex > 0)
                     {
                         if(!oSelect.options[selIndex - 1].selected)
                             oSelect.options[selIndex].swapNode(oSelect.options[selIndex - 1]);
                     }
                 }
             }
         }
     }
     //����ǵ�ѡ--------------------------------------------------------------------
     else
     {
         var selIndex = oSelect.selectedIndex;
         if(selIndex <= 0)
             return;
         //����������ƶ������˱�־
         if(isToTop)
         {
             while(selIndex > 0)
             {
                 oSelect.options[selIndex].swapNode(oSelect.options[selIndex - 1]);
                 selIndex --;
             }
         }
         //û�������ƶ������˱�־
         else        
             oSelect.options[selIndex].swapNode(oSelect.options[selIndex - 1]);
     }
 }

/**
  * ʹѡ�е���Ŀ����
  *
  * oSelect: Դ�б��
  * isToTop: �Ƿ�����ѡ����׶ˣ������������ƣ�
  *          trueΪ�ƶ����׶ˣ�false��֮��Ĭ��Ϊfalse
  */
 function moveDown(oSelect,isToBottom)
 {
     //Ĭ��״̬�����ƶ�������
     if(isToBottom == null)
         var isToBottom = false;
         
     var selLength = oSelect.options.length - 1;
     
     //����Ƕ�ѡ------------------------------------------------------------------
     if(oSelect.multiple)
     {
         for(var selIndex=oSelect.options.length - 1; selIndex>= 0; selIndex--)
         {
             //����������ƶ������˱�־
             if(isToBottom)
             {
                 if(oSelect.options[selIndex].selected)
                 {
                     var transferIndex = selIndex;
                     while(transferIndex < selLength && !oSelect.options[transferIndex + 1].selected)
                     {
                         oSelect.options[transferIndex].swapNode(oSelect.options[transferIndex + 1]);
                         transferIndex ++;
                     }
                 }
             }
             //û�������ƶ������˱�־
             else
             {
                 if(oSelect.options[selIndex].selected)
                 {
                     if(selIndex < selLength)
                     {
                         if(!oSelect.options[selIndex + 1].selected)
                             oSelect.options[selIndex].swapNode(oSelect.options[selIndex + 1]);
                     }
                 }
             }
         }
     }
     //����ǵ�ѡ--------------------------------------------------------------------
     else
     {
         var selIndex = oSelect.selectedIndex;
         if(selIndex >= selLength - 1)
             return;
         //����������ƶ������˱�־
         if(isToBottom)
         {
             while(selIndex < selLength - 1)
             {
                 oSelect.options[selIndex].swapNode(oSelect.options[selIndex + 1]);
                 selIndex ++;
             }
         }
         //û�������ƶ������˱�־
         else        
             oSelect.options[selIndex].swapNode(oSelect.options[selIndex + 1]);
     }
 }

/**
  * �ƶ�select�Ĳ�������,�������value���˺�����valueΪ��׼�����ƶ�
  *
  * oSourceSel: Դ�б����� 
  * oTargetSel: Ŀ���б�����
  */
 function moveSelected(oSourceSel,oTargetSel)
 {
     //�����洢value��text�Ļ�������
     var arrSelValue = new Array();
     var arrSelText = new Array();
     //���������ѡ�е�options����value����Ӧ
     var arrValueTextRelation = new Array();
     var index = 0;//��������������������
     
     //�洢Դ�б�������е����ݵ������У�������value��ѡ��option�Ķ�Ӧ��ϵ
     for(var i=0; i<oSourceSel.options.length; i++)
     {
         if(oSourceSel.options[i].selected)
         {
             //�洢
             arrSelValue[index] = oSourceSel.options[i].value;
             arrSelText[index] = oSourceSel.options[i].text;
             //����value��ѡ��option�Ķ�Ӧ��ϵ
             arrValueTextRelation[arrSelValue[index]] = oSourceSel.options[i];
             index ++;
         }
     }
     
     //���ӻ�������ݵ�Ŀ���б���У���ɾ��Դ�б���еĶ�Ӧ��
     for(var i=0; i<arrSelText.length; i++)  
     {
         //����
         var oOption = document.createElement("option");
         oOption.text = arrSelText[i];
         oOption.value = arrSelValue[i];
         oTargetSel.add(oOption);
         //ɾ��Դ�б���еĶ�Ӧ��
         oSourceSel.removeChild(arrValueTextRelation[arrSelValue[i]]);
     }
 }

/**
  * �ƶ�select����������
  *
  * oSourceSel: Դ�б����� 
  * oTargetSel: Ŀ���б�����
  */
 function moveAll(oSourceSel,oTargetSel)
 {
     //�����洢value��text�Ļ�������
     var arrSelValue = new Array();
     var arrSelText = new Array();
     
     //�洢����Դ�б�����ݵ���������
     for(var i=0; i<oSourceSel.options.length; i++)
     {
         arrSelValue[i] = oSourceSel.options[i].value;
         arrSelText[i] = oSourceSel.options[i].text;
     }
     
     //������������������ӵ�Ŀ��select��
     for(var i=0; i<arrSelText.length; i++)  
     {
         var oOption = document.createElement("option");
         oOption.text = arrSelText[i];
         oOption.value = arrSelValue[i];
         oTargetSel.add(oOption);
     }
     
     //���Դ�б�����ݣ�����ƶ�
     oSourceSel.innerHTML = "";
 }

/**
  * ɾ��ѡ����Ŀ
  *
  * oSelect: Դ�б����� 
  */
 function deleteSelectItem(oSelect)
 {
     
     for(var i=0; i<oSelect.options.length; i++)
     {
         if(i>=0 && i<=oSelect.options.length-1 && oSelect.options[i].selected)
         {
             oSelect.options[i] = null;
             i --;
         }
     }
 }
 /**
 * ѡ��������Ŀ
 * oSelect: Դ�б����� 
 */
 function selectAll(oSelect){
 	for(var i=0; i<oSelect.options.length; i++)
     {
         oSelect.options[i].selected = "selected";
         break;
         
     }
 }
 /**
 * �ƶ�ȫ��
 * ����ifAll�ƶ�ȫ��
 */
 function judgeMove()
 {
     var arrRadio = document.all.ifAll;
     var valOfRadio;
     for(var i=0; i<arrRadio.length; i++)
     {
         if(arrRadio[i].checked)
         {
             valOfRadio = arrRadio[i].value;
             break;
         }
     }
     if(valOfRadio == "left")
         moveAll(document.all.right,document.all.left);
     if(valOfRadio == "right")
         moveAll(document.all.left,document.all.right);
 }
//js�ļ����

	function check(){
		var target = document.getElementById("target");
		var targetInput = document.getElementById("targetTerminal");
		var terminal = "";
		if(target.options.length==0){
			alert("��ѡ��Ҫ���·�����豸");
			return false;
		}
		for(var i=0; i<target.options.length; i++){
             terminal += target.options[i].value+",";
     	}
     	targetInput.value=terminal.substring(0,terminal.length-1)
		return true;
		
	}
</script>