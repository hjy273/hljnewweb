<%@include file="/common/header.jsp"%>
<script language="JavaScript" type="">
    var allArr = new Array();
    var lineArr = new Array();
    var flag = 0;

    function changeFlag(){
        flag = 1;
        (document.getElementById("flagID")).value = 1;
    }
    //初始化数组
    function initArr(simnumber,name,phone){
        lineArr[0] = simnumber;
        lineArr[1] = name;
        lineArr[2] = phone;
        allArr[allArr.length] = lineArr;
        lineArr = new Array();
        return true;
    }
    //改变SIM卡号
    function selectChange(){
      var sObj =document.getElementById("seleID");
      var temp = new Array();
      var phoneArr = new Array();

      for( i = 0; i < allArr.length; i++){
          if(allArr[i][0] == sObj.value){
            temp[0] = allArr[i][1];
            temp[1] = allArr[i][2];
            phoneArr[phoneArr.length] = temp;
            temp = new Array();
        }
     }
     for(i=phoneID.rows.length-1;i>=0;i--){
         phoneID.deleteRow(i);
     }
     for(i = 0; i<phoneArr.length;i++){
         addRow(phoneArr[i][0],phoneArr[i][1]);
     }
    }
     //添加一个新行
    function addRow(name,phone){

        if(phoneID.rows.length >32){
          alert("最多支持32个通讯录，不能再添加了！");
          return false;
        }
        var  onerow=phoneID.insertRow(phoneID.rows.length);
        onerow.id = phoneID.rows.length;

        var   cell1=onerow.insertCell();
        var   cell2=onerow.insertCell();
        var   cell3=onerow.insertCell();

        //创建liag个输入框
        var t1 = document.createElement("input");
        t1.name = "name";
        t1.id = "text" + onerow.id;
        t1.value= name;
        t1.maxLength = "10";
        t1.size = "10";
        t1.onchange=changeFlag;
        t1.style.background="#C6D6E2"
        t1.style.font.size="12px";

        var t2 = document.createElement("input");
        t2.name = "phone";
        t2.id = "text" + onerow.id;
        t2.value= phone;
        t2.maxLength = "15";
        t2.size = "16";
        t2.onchange=changeFlag;
        t2.title="请输入数字";
        t2.style.background="#C6D6E2"
        t2.style.font.size="12px";
        //创建删除按钮
        var b1 =document.createElement("button");
        b1.value = "删除";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
        obj = b1.id;

        cell1.appendChild(t1);
        cell2.appendChild(t2);
        cell3.appendChild(b1);
    }

    //脚本生成的删除按  钮的删除动作
    function deleteRow(){
      //获得按钮所在行的id
         var rowid = "000";
        rowid = this.id;
        rowid = rowid.substring(1,rowid.length);
        //由id转换为行索找行的索引,并删除
          for(i =0; i<phoneID.rows.length;i++){
            if(phoneID.rows[i].id == rowid){
                phoneID.deleteRow(phoneID.rows[i].rowIndex);
            }
        }
       changeFlag();
    }

    function onSubmit(){
        if(flag == 1){
            if(window.confirm("通讯录已经更改，请确认是否提交?")){
              //alert(document.all.simnumber.value);
              addForm.submit();
            }
            else{
              return false;
            }
        }else{
            alert("通讯录没有被更改，不用提交");
            return false;
        }

    }

    function onCheckboxSelect(value){
        changeFlag();
          terminalBean.setnumber.value =	value;
           if(value == 'all'){
            terminalBean.simnumber.multiple = true;
            terminalBean.simnumber.size = 5;
            obj = document.getElementById("seleID");
            for(i=0;i<obj.options.length;i++){
                obj.options[i].selected = true;
            }
        }
        if(value == 'some'){
              terminalBean.simnumber.multiple = true;
            terminalBean.simnumber.size = 5;
        }
        if(value == 'one'){
            terminalBean.simnumber.multiple = false;
            terminalBean.simnumber.size = "";
        }
    }
    function onGradClick(){
      obj = document.getElementById("gradID");
          if(obj.style.display == 'none'){
            obj.style.display = '';
            return;
        }
        if(obj.style.display == ''){
            obj.style.display = 'none';
            terminalBean.simnumber.multiple = false;
            terminalBean.simnumber.size = "";
            return;
        }
    }
</script>
<body onload="selectChange()">
    <logic:present name="lPhoneBook">
        <logic:iterate id="pbID" name="lPhoneBook">
            <script language="javascript" type="" >
                initArr('<bean:write name="pbID"  property="simNumber"/>','<bean:write name="pbID"  property="name"/>','<bean:write name="pbID"  property="phone"/>');
            </script>
        </logic:iterate>
    </logic:present>
    <br />
    <template:titile value="编辑通讯录"/>
    <html:form action="/terminalAction?method=addUpPhoneBook" styleId="addForm">
        <input  type="hidden" name="flag" id="flagID" value="0"/>
        <input type="hidden" name="setnumber" id="setnumberID" value="one"/>
          <template:formTable >
            <template:formTr name="SIM卡号">
                <select id="seleID" name="simnumber" class="selecttext" style='width:200px' onchange="selectChange()">
                    <logic:present name="lSimNumber">
                        <logic:iterate id="simId" name="lSimNumber" >
                            <option value="<bean:write name="simId" property="simnumber" />"><bean:write name="simId" property="simnumbername" /></option>
                        </logic:iterate>
                    </logic:present>
                </select>
                <input  type="button" value="高级"  class="inputtext" onclick="onGradClick()"/>
            </template:formTr>
            <template:formTr name="高级选项" tagID="gradID"  style="display:none">
                <input   type="radio" name="number"  value="all"  onclick="onCheckboxSelect(value)"/>所有SIM卡统一如下通讯录
                <br />
                <input  type="radio" name="number"  value="some" onclick="onCheckboxSelect(value)"/>部分SIM卡统一如下通讯录
                <br />
                <input  type="radio" name="number" value="one" onclick="onCheckboxSelect(value)"/>单个SIM卡设置如下通讯录
            </template:formTr>
            <template:formTr name="通 讯 录">
                <table id="phoneID" border="0" cellpadding="0" class="tabout" cellspacing="0">

                </table>
            </template:formTr>

                    <template:formSubmit>
                          <td>
                          <html:button property="action" styleClass="button"  onclick="addRow('用户姓名','电话号码')">添加新记录</html:button>
                          </td>
                        <td>
                                <html:button property="action" styleClass="button" onclick="onSubmit()" >提交	</html:button>
                          </td>
                    </template:formSubmit>


                </template:formTable>
            </html:form>
</body>
