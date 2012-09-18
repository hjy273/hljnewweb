/*****************************************************************************
函数名称：fncComCheckIfNumber
处理机能：检查是否是是数字
参数	：sCheck 字符串
日期	：2002/04/16
修改人-------修改日--------概要--------------------------
梅春--------2003-11-1-------添加对数字(非小于1的小数)首位不为零的限制。
******************************************************************************/
function fncComCheckIfNumber(sCheck) {
  if((sCheck=="")||(sCheck==null)) return false;
  if( sCheck.length>1 && sCheck.charAt(0)  == '0' && sCheck.charAt(1)  != '.' ) {
    return false;
  }
  for(cnt=0;cnt<=sCheck.length-1;cnt++){
    if (sCheck.substring(cnt,cnt+1)>"9"||sCheck.substring(cnt,cnt+1)<"0"){
        return false;
    }
  }
  return true;
}

/*****************************************************************************
函数名称：fncComCheckIfPhoneNumber
处理机能：检查是否是电话号码
参数	：sCheck 字符串
日期	：2002/04/16
修改人-------修改日--------概要--------------------------
******************************************************************************/
function fncComCheckIfPhoneNumber(sCheck){
  if ((sCheck=="")||(sCheck==null)){
    return false;
  }else if((sCheck.length<6)||(sCheck.length>25)){
    return false;
  }else{
    for(cnt=0;cnt<=sCheck.length-1;cnt++){
      if (sCheck.substring(cnt,cnt+1) != "-" && sCheck.substring(cnt,cnt+1) != "_"){
        if (sCheck.substring(cnt,cnt+1) > "9"||sCheck.substring(cnt,cnt+1) < "0"){
          return false;
        }
      }
    }
  }
  return true;
}

/*****************************************************************************
函数名称：fncComCheckIfBirthYear
处理机能：检查是否是有效出生年
参数	：sCheck 字符串，代表年
日期	：2002/04/16
修改人-------修改日--------概要--------------------------
******************************************************************************/
function fncComCheckIfBirthYear(sCheck){

    if ( fncComCheckIfNumber(sCheck) == false ){
        return false;
    }

    dteCur = new Date();

    if (parseInt(sCheck) <= 1000 || parseInt(sCheck) > dteCur.getYear() ){
        return false;
    }

    return true;
}

/*****************************************************************************
函数名称：fncComCheckIfEMail
处理机能：检查是否是email
参数	：sCheck email
日期	：2002/04/16
修改人-------修改日--------概要--------------------------
******************************************************************************/
function fncComCheckIfEMail(sCheck){
  if ((sCheck=="")||(sCheck==null)) return false;

  //Check if '@' is proper
  if (sCheck.indexOf("@") <= 0 || sCheck.indexOf("@") == sCheck.length -1 || sCheck.indexOf("@") != sCheck.lastIndexOf("@") ){
      return false;
  }

  //Check if '.' is proper
  if (sCheck.indexOf(".") <= 0 || sCheck.indexOf(".") == sCheck.length -1 || sCheck.indexOf("..") >-1 ){
      return false;
  }

  //Check if each character is digital or letter
  for(cnt=0;cnt<=sCheck.length-1;cnt++){
      if (sCheck.substring(cnt,cnt+1) != "-" && sCheck.substring(cnt,cnt+1) != "_" && sCheck.substring(cnt,cnt+1) != "@" && sCheck.substring(cnt,cnt+1) != "."){
           //                           below '0'                                                    above '9' and below 'A'                                                     above 'Z' and below 'a'                              above 'z'
         if ( (sCheck.substring(cnt,cnt+1) < "0") || (sCheck.substring(cnt,cnt+1) > "9" && sCheck.substring(cnt,cnt+1) < "A") || (sCheck.substring(cnt,cnt+1) > "Z" && sCheck.substring(cnt,cnt+1) < "a") || (sCheck.substring(cnt,cnt+1) > "z") ){
            return false;
         }
    }
  }
  return true;
}

/*****************************************************************************
函数名称：fncTrim()
处理机能：STRING FUNCTIONS，删除字符的前后空格
参数	：
作者	：
日期	：
修改人-------修改日--------概要--------------------------
******************************************************************************/
function fncTrim( str ) {
    // Immediately return if no trimming is needed
    if( (str.charAt(0) != ' ') && (str.charAt(str.length-1) != ' ') ) { return str; }
    // Trim leading spaces
    while( str.charAt(0)  == ' ' ) {
        str = '' + str.substring(1,str.length);
    }
    // Trim trailing spaces
    while( str.charAt(str.length-1)  == ' ' ) {
        str = '' + str.substring(0,str.length-1);
    }

    return str;
}

// 判断是否为Email
function isEmail(szExp){
    szExp = trim(szExp);

    if ( isNull(szExp) ){
        return false;
    }

    if ( szExp.indexOf("@") <= 0 || szExp.indexOf("@") == szExp.length -1 || szExp.indexOf("@") != szExp.lastIndexOf("@") ){
        return false;
    }

    return true;
}


// 截空
function trim( szExp ) {
    if( szExp == null )	return null;
    if( (szExp.charAt(0) != ' ') && (szExp.charAt(szExp.length-1) != ' ') ) { return szExp; }
    while( szExp.charAt(0)  == ' ' ) {
        szExp = '' + szExp.substring(1,szExp.length);
    }
    while( szExp.charAt(szExp.length-1)  == ' ' ) {
        szExp = '' + szExp.substring(0,szExp.length-1);
    }

    return szExp;
}

// 判断是否为空
function isNull(szExp)
{
    return ( szExp == "" || szExp == null )? true : false;
}

// 判断是否为数值
function isNumeric(szNum)
{
    return ( isInt(szNum)==true || isFloat(szNum)==true )? true:false
}
//聂昕添加，判断是否为数值
function isTonyNumeric(szNum)
{
  if(szNum.length>1){
    var chr1 = szNum.substring(0,1);
    var chr2 = szNum.substring(1,2);
    var chr3 = szNum.substring(2,3);

    if ( chr1 == "0" && chr2!= "." ){
      return false;
    }else if((chr1 =="-")&&(chr2=="0")&&(chr3!=".")){
      return false;
    }
  }
   return ( isInt(szNum)==true || isFloat(szNum)==true )? true:false
}


// 判断是否为正整数
function isPosiInt(szNum)
{
    if ( isNull(szNum) )
        return false;

    if ( isNaN(parseInt(szNum )) )
        return false;

    for(var i = 0; i <= szNum.length-1; i++ ){
        var chr = szNum.substring(i,i+1);

        if( chr > "9" || chr < "0" ){
            return false;
        }
    }
    return true;
}

// 判断是否为整数
function isInt(szNum)
{
    if ( isNull(szNum) )
        return false;

    if ( isNaN(parseInt(szNum )) )
        return false;

    for(var i = 0; i <= szNum.length-1; i++ ){
        var chr = szNum.substring(i,i+1);

        if      ( i == 0 && chr == "-" ){
            continue;
        }else if( chr > "9" || chr < "0" ){
            return false;
        }
    }
    return true;
}

// 判断是否为正浮点数
function isPosiFloat(szNum)
{
    var bDot = false;

    if ( isNull(szNum) )
        return false;

    if ( isNaN(parseInt(szNum )) )
        return false;

    for(var i = 0; i <= szNum.length-1; i++ ){
        var chr = szNum.substring(i,i+1);

        if( chr == "." ){
            if ( bDot == true )
                return false;
            bDot = true;
        }else if( chr > "9" || chr < "0" ){
            return false;
        }
    }
    return true;
}


// 判断是否为浮点数
function isFloat(szNum)
{
    var bDot = false;

    if ( isNull(szNum) )
        return false;

    if ( isNaN(parseInt(szNum )) )
        return false;

    for(var i = 0; i <= szNum.length-1; i++ ){
        var chr = szNum.substring(i,i+1);

        if      ( i == 0 && chr == "-" ){
            continue;
        }else if( chr == "." ){
            if ( bDot == true )
                return false;
            bDot = true;
        }else if( chr > "9" || chr < "0" ){
            return false;
        }
    }
    return true;
}

// 判断是否为日期, dhan, 2003/08/20 update
function isDateB(strDate) {
    //if ( strDate.length != 10 )
    if ( strDate.length != 8 )
        return false;

    sYear  = strDate.substring(0,4);
    //alert(sYear);
    sMonth = strDate.substring(4,6);
    //alert(sMonth);
    sDay   = strDate.substring(6,8);
    //alert(sDay);
    return isDate(sYear,sMonth,sDay)
}
// 判断是否为日期
function isDateD(strDate) {
        if ( strDate.length != 10 )
                return false;

        sYear  = strDate.substring(0,4);
        sMonth = strDate.substring(5,7);
        sDay   = strDate.substring(8,10);
        return isDate(sYear,sMonth,sDay)
}
// 判断是否为日期
function isDate(sYear,sMonth,sDay)
{
    if( isNull(sYear ) ) return false;
    if( isNull(sMonth) ) return false;
    if( isNull(sDay  ) ) return false;

    if( sMonth.charAt(0) == '0' ) { sMonth = sMonth.substring(1,2); }
    if( sDay.charAt(0)   == '0' ) { sDay   = sDay.substring(1,2);   }

    var nYear  = parseInt(sYear );
    var nMonth = parseInt(sMonth);
    var nDay   = parseInt(sDay  );
    var arrMon = new Array(12);

    if ( isNaN(nYear ) )	return false;
    if ( isNaN(nMonth) )	return false;
    if ( isNaN(nDay  ) )	return false;

    arrMon[ 0] = 31;
    arrMon[ 1] = nYear % 4 == 0 ? 29:28;
    arrMon[ 2] = 31;
    arrMon[ 3] = 30;
    arrMon[ 4] = 31;
    arrMon[ 5] = 30;
    arrMon[ 6] = 31;
    arrMon[ 7] = 31;
    arrMon[ 8] = 30;
    arrMon[ 9] = 31;
    arrMon[10] = 30;
    arrMon[11] = 31;

    if ( nYear  < 1900 || nYear > 2100 )			return false;
    if ( nMonth < 1 || nMonth > 12 )				return false;
    if ( nDay < 1 || nDay > arrMon[nMonth - 1] )	return false;

    return true;
}

//月份从1开始, dhan add.
function getDaybyMonth(sYear, sMonth)
{
    iMonth = parseInt(sMonth);
    iYear  = parseInt(sYear);

    iMonth = iMonth - 1;
    if (iMonth < 0) return 0;

    var arrMon = new Array(12);

    arrMon1[ 0] = 31;
    arrMon1[ 1] = iYear % 4 == 0 ? 29:28;
    arrMon1[ 2] = 31;
    arrMon1[ 3] = 30;
    arrMon1[ 4] = 31;
    arrMon1[ 5] = 30;
    arrMon1[ 6] = 31;
    arrMon1[ 7] = 31;
    arrMon1[ 8] = 30;
    arrMon1[ 9] = 31;
    arrMon1[10] = 30;
    arrMon1[11] = 31;

    return arrMon1[iMonth];
}

// 实际长度
function len(szExp)
{
    var iLen = 0;

    if (isNull(szExp))
        return 0;

    for(i=0; i<szExp.length; i++){
        temp=szExp.charAt(i);
        start=unescape("%00")
        end=unescape("%7f")
        if(temp>end||temp<start)
            iLen ++;
        iLen ++;
    }
    return iLen;
}

// 日期相加
function AddDate(strDate, strFlag,intAdd) {
    sYear  = strDate.substring(0,4);
    sMonth = strDate.substring(5,7);
    sDay   = strDate.substring(8,10);

    if( sMonth.charAt(0) == '0' ) { sMonth = sMonth.substring(1,2); }
    if( sDay.charAt(0)   == '0' ) { sDay   = sDay.substring(1,2);   }

    var nYear  = parseInt( sYear  );
    var nMonth = parseInt( sMonth );
    var nDay   = parseInt( sDay   );

    if ( isNaN(nYear ) )	return false;
    if ( isNaN(nMonth) )	return false;
    if ( isNaN(nDay  ) )	return false;

    nYear  = nYear  - 1900;
    nMonth = nMonth - 1;

    if ( strFlag == null || strFlag.length == 0 )  strFlag = "d"
    strFlag = strFlag.toLowerCase();

    if      ( strFlag == "y" )	{	nYear   = nYear  + intAdd;	}
    else if ( strFlag == "m" )	{	nMonth  = nMonth + intAdd;	}
    else						{	nDay    = nDay   + intAdd;	}

    var datCalc = new Date(nYear,nMonth,nDay);
    nYear  = datCalc.getYear()  + 1900 + "";
    nMonth = datCalc.getMonth() + 1 + "";
    nDay   = datCalc.getDate()  + "";

    if( nMonth.length == 1 ) { nMonth = "0" + nMonth; }
    if( nDay.length   == 1 ) { nDay   = "0" + nDay  ;   }
    return nYear + "/" + nMonth + "/" + nDay ;
}

// 将日期变成字符串
function DateToString( datExp ) {
    if ( datExp == null )
        return "";

    nYear  = datExp.getYear()  + "";
    nMonth = datExp.getMonth() + 1 + "";
    nDay   = datExp.getDate()  + "";

    if( nMonth.length == 1 ) { nMonth = "0" + nMonth; }
    if( nDay.length   == 1 ) { nDay   = "0" + nDay  ;   }
    return nYear + "/" + nMonth + "/" + nDay ;
}

// 创建日期控件
function PutDateCtl(strNM) {
    document.write("<INPUT  NAME='" + strNM + "' ID='" + strNM + "' SIZE=25 class=inputtext >");
    document.write("<INPUT TYPE='BUTTON' VALUE='▼' ID='btn" + strNM + "' onclick=\"JavaScript:GetSelectDate('" + strNM + "')\" STYLE=\"font:'normal small-caps 6pt serif';\" >");
    document.all.item('btn' + strNM).style.height = 19;
    document.all.item('btn' + strNM).style.width  = 19;
    document.all.item(strNM).style.height = 19;
    return true;
}
// 创建日期控件
function PutDateCtlDate(strNM,strTemp) {
    if (strTemp==null || strTemp==""){
    PutDateCtl(strNM);
    } else {
    document.write("<INPUT  NAME='" + strNM + "' ID='" + strNM + "' value="+strTemp +"  SIZE=18 class=InputFace  readonly>");
    document.write("<INPUT TYPE='BUTTON' VALUE='▼' ID='btn" + strNM + "' onclick=\"JavaScript:GetSelectDate('" + strNM + "')\" STYLE=\"font:'normal small-caps 6pt serif';\" >");
    document.all.item('btn' + strNM).style.height = 19;
    document.all.item('btn' + strNM).style.width  = 19;
    document.all.item(strNM).style.height = 19;
    }
      return true;
}
// 选择日期
function GetSelectDate(strID) {
    document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    return true;
}


/*****
 * 上载文件的窗口,2003年6月16日，聂昕添加
 */
/*function OpenWindow(AttachName,AttachNum){
    uploadWin = window.open('','FileUpload','toolbar=no, directories=no, location=no, status=no, menubar=no,scrollbars=no,resizable=no,width=500,height=250, screenX=250,screenY=150,left=250,top=150')
    uploadWin.document.write('<HTML>');
    uploadWin.document.write('<%@ page contentType="text/html;charset=gb2312" %>');
    uploadWin.document.write('<%@ page errorPage="/web/common/SystemError.jsp"%>');
    uploadWin.document.write('<HEAD><TITLE>'+AttachName+'上载</TITLE></HEAD>');
    uploadWin.document.write('<meta http-equiv="Content-Type" content="text/html; charset=GBK">');
    uploadWin.document.write('<link rel="stylesheet" href="/BIBM/web/common/window.css" type="text/css">');
    uploadWin.document.write('<body text="#000000">');
    uploadWin.document.write('<form name="form1" action="fileupload.do" enctype="MULTIPART/FORM-DATA" method=post>');
    uploadWin.document.write('<table width="100%" border="0" cellspacing="3" cellpadding="3" align="center" class="dbutton">');
    uploadWin.document.write('<br><tr>');
    uploadWin.document.write('<td class="titlefont" align="center">'+AttachName+'上载</td>');
    uploadWin.document.write('</tr>');
    uploadWin.document.write('</table>');
    uploadWin.document.write('<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="dbutton">');
    uploadWin.document.write('<tr><td>&nbsp; </td></tr>');
    uploadWin.document.write('<tr><td>');
    uploadWin.document.write('<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">');
    uploadWin.document.write('<tr><td><br>');
    uploadWin.document.write('<table border="0" width="95%" bordercolorlight="#000000" cellspacing="0" cellpadding="3" bordercolordark="#FFFFFF" class="tab1" align="center">');
    uploadWin.document.write('<tr>');
    uploadWin.document.write('<td class="pbutton" align="right">'+AttachName+'上载</td>');
    uploadWin.document.write('<td class="pbutton">');
    uploadWin.document.write('<input type="file" name="uploadfile" size="45">');
    uploadWin.document.write('</td>');
    uploadWin.document.write('</tr>');
    uploadWin.document.write('<tr><td>');
    uploadWin.document.write('<input type="hidden" name="attachnum" size="10" value="'+AttachNum+'">');
    uploadWin.document.write('</td></tr>');
    uploadWin.document.write('</table><br><br>');
    uploadWin.document.write('</td></tr>');
    uploadWin.document.write('</table>');
    uploadWin.document.write('</td></tr>');
    uploadWin.document.write('</table>');
    uploadWin.document.write('<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="dbutton">');
    uploadWin.document.write('<tr><td>');
    uploadWin.document.write('<table border="0" cellspacing="4" cellpadding="3" align="center">');
    uploadWin.document.write('<tr align="center"><td>');
    uploadWin.document.write('<input type="image" border="0" name="submit" src="/BIBM/web/images/submit.gif" width="80" height="22" onClick="javascript:document.form1.submit();">');
    uploadWin.document.write('</td></tr>');
    uploadWin.document.write('</table>');
    uploadWin.document.write('</td></tr>');
    uploadWin.document.write('</table>');
    uploadWin.document.write('</form>');
    uploadWin.document.write('</body>');
    uploadWin.document.write('</HTML>');
    uploadWin.document.close();
}*/

/*****
 * 上载文件的窗口,2003年6月16日，聂昕添加
 */
function OpenWindow(AttachName,AttachNum){
    uploadWin = window.open('/BIBM/web/BusinessAccept/Databusiness/FileUpload.jsp?AttachName='+AttachName+'&AttachNum='+AttachNum,'附件上载','toolbar=no, directories=no, location=no, status=no, menubar=no,scrollbars=no,resizable=no,width=600,height=250, screenX=250,screenY=150,left=250,top=150')
}


//Lpad函数
function LPad(strIn ,iLen, pasteChar)
{
  var strOut="";

  strInTmp=""+strIn;

  if ( strInTmp.length>=iLen )
    return strInTmp;

  while( iLen>strInTmp.length )
  {
     iLen = iLen - 1;
     strOut=strOut+pasteChar;
  }
  return (strOut+strInTmp);
}

//取得当前时间
function getCurrIEDate(split)
{
  var my_date=new Date();
  var
retStr=my_date.getFullYear()+split+LPad(my_date.getMonth()+1,2,"0")+split+LPad(my_date.getDate(),2,
"0");

  return retStr;
}

/*****************************************************************************
函数名称：checkPercents
处理机能：对百分数的验证
参数	：acode 控件name,aname 验证的内容,startvalue 起始值,endvalue 终止值
日期	：2002/11/06
修改人-------修改日--------概要--------------------------
杨林--------2003-11-6-------添加对百分数的验证
******************************************************************************/
function checkPercents(acode,aname,startvalue,endvalue)
{
    if(acode.value.charAt(acode.value.length-1)== '.'||acode.value.charAt(0)== '.') {
             alert(aname+"格式不正确！");
               acode.value='';
               acode.focus();
                return 0;
                  }
    if( acode.value.length>1 && acode.value.charAt(0)  == '0' && acode.value.charAt(1)!= '.' ) {
             alert(aname+"格式不正确！");
               acode.value='';
               acode.focus();
                return 0;
  }
        if (Number(acode.value) > endvalue || Number(acode.value < startvalue) ){
           alert(aname+"数值只能大于"+startvalue+"小于"+endvalue+"");
               acode.value='';
               acode.focus();
           return 0;
        }

        if (isNaN(acode.value) == true) {
           alert(aname+"只能为数字!");
               acode.value='';
               acode.focus();
           return 0;
        }
        return 1;
}
/*****************************************************************************
函数名称：checkisnum
处理机能：验证是否是正确的数字
参数	：acode 控件name
日期	：2002/11/06
修改人-------修改日--------概要--------------------------
杨林--------2003-11-6-------验证是否是正确的数字
******************************************************************************/
function checkisnum(acode)
{
  if((acode.value=='')||(acode.value==null)) return 0;
  if( acode.value.length>1 && acode.value.charAt(0)  == '0' && acode.value.charAt(1)  != '.' ) {
        alert("请输入正确的日期!");
         acode.value='0';
               acode.focus();
    return 0;
  }
  for(cnt=0;cnt<=acode.value.length-1;cnt++){
    if (acode.value.substring(cnt,cnt+1)>'9'||acode.value.substring(cnt,cnt+1)<'0'){
      alert('请输入正确的日期!');
        acode.value='0';
        acode.focus();
          return 0;
    }
  }
  return 1;
}

function checkisprice(acode)
{
  if((acode.value=='')||(acode.value==null)) return 0;
  if( acode.value.length>1 && acode.value.charAt(0)  == '0' && acode.value.charAt(1)  != '.' ) {
        alert("请输入正确的价格!");
         acode.value='0';
               acode.focus();
    return 0;
  }
  if (isNaN(acode.value) == true) {
     alert("请输入正确的价格!");
         acode.value='0';
         acode.focus();
     return 0;
        }
  return 1;
}
//禁止右键
//document.oncontextmenu = function() { return false;}
