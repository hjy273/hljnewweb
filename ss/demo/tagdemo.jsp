<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>

<html>
<script language="javascript" src="${pageContext.request.contextPath}/js/validation/prototype.js" type=""></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/validation/effects.js" type=""></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/validation/validation_cn.js" type=""></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
<head>
<style type="text/css">
	.input{
		width:80px;	
	}
	
	.input_file{
		width:200px;
		height:22px;
		border:1px; 
		border-color:#AACFE4; 
		border-style:solid; 
		background-color: #FFFFFF; 
		font-size: 12px;
		line-height:24px;
	}
</style>
</head>
<body>
<div>

<pre>
<code>
quickLoadList��ǩʹ��˵����
&lt;apptag:quickLoadList  cssClass="input" name="cabletype" id="cabletype" listName="cabletype" type="select" &gt;
apptag:quickLoadList��ǩ���ڼ��س��õ������б���ѡ�򣬵�ѡť�ȡ�
cssClass����ָ������ʽ��
name��ָ������
id��input �� select �� id��
listName��ָ��Ҫ�ӳ��ñ����ֵ����ȡ��һ��ֵ����ӦDICTIONARY_FORMITEM�е�ASSORTMENT_ID�ֶΣ�
type����������Ͱ�����select,radio,checkbox,Ĭ��Ϊselect
</code>
</pre>
����<br>
���¼���<apptag:quickLoadList cssClass="input" name="cabletype" listName="cabletype" type="select"/>
	<br>
���跽ʽ��<apptag:quickLoadList cssClass="checkbox" name="layingmethod" listName="layingmethod" type="checkbox"></apptag:quickLoadList>
	<br>
�������ͣ�<apptag:quickLoadList cssClass="radio" name="lp_trouble_type" listName="lp_trouble_type" type="radio"></apptag:quickLoadList>
</div>

<div>
<pre>
<code>
uploadFile��ǩʹ��˵����
&lt;apptag:upload  state="add" entityId="" entityType="" &gt;
state:��ǩ��״̬������ӣ�add�����༭��edit�����鿴��look����Ĭ��Ϊlook
entityId ��edit��look״̬ʱ�����ݿ��м�¼id���ܹ�ͨ����¼��ѯ����ü�¼��صĸ�����
entityType ���ݿ����
ʹ�øñ�ǩ��Ҫ����js�ļ�
</code>
</pre>

</div>	

<form action="${ctx}/uploaddemo.do?method=uploadfile" method="post" enctype="multipart/form-data">
<apptag:upload state="edit" entityId="0000001" entityType="ls_trouble" cssClass="input_file"/>
<br>
<input type="button" onclick="addRow()" value="���"> <input type="submit" value="�ύ"/>
</form>

<input type="button" name="action" value="��Ӹ���" onclick="addRow()" class="button">
<div>
<pre>
<code>
Attachment��ǩʹ��˵����
&lt;apptag:Attachment  state="add" fileIdList="" &gt;
state:��ǩ��״̬������ӣ�add�����༭��edit�����鿴��look����Ĭ��Ϊlook
fileIdList��edit��look״̬ʱ��������id��
ʹ�øñ�ǩ��Ҫ����js�ļ�
</code>
</pre>

</div>	
<c:if test="${'123' eq '123'}">
	sss
</c:if>
<!--  apptag:Attachment state="add" fileIdList=""/-->
<br>
<!--input type="button" name="action" value="��Ӹ���" onclick="addRow()" class="button"-->
</body>
</html>