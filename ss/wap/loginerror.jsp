<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<body text="#000000">
	<p>
		&nbsp;
	</p>
	<p>
		&nbsp;
	</p>
	<p>
		&nbsp;
	</p>
	<br />
	<br />
	<br />
	<div align="center">
	<img src="${ctx}/images/error.gif" width="49" height="49">
	</div>
	<br/>
	<div align="center">
	<logic:equal value="nouser" name="loginerror">
            		��½ʧ��,û�и��û�!!!
                </logic:equal>
	<logic:equal value="accerror" name="loginerror">
            		��½ʧ��,���û���ͣ��!!!
                </logic:equal>
	<logic:equal value="passerror" name="loginerror">
            		��½ʧ��,�������!!!
                </logic:equal>
	<logic:equal value="accounttermerror" name="loginerror">
            		��½ʧ��,�ʺŹ���!!!
                </logic:equal>
	<logic:equal value="perror" name="loginerror">
            		��½ʧ��,�������!!!
                </logic:equal>
	<logic:equal value="syserror" name="loginerror">
            		��½ʧ�ܡ������µ�½�������Ա��ϵ��
                </logic:equal>
	<logic:equal value="powererror" name="loginerror">
            		��½ʧ�ܡ�û��Ȩ��,�������Ա��ϵ��
                </logic:equal>
	<logic:equal value="nodept" name="loginerror">
            		��¼ʧ�ܡ�û���ҵ��û����ڵĲ�����Ϣ��
                </logic:equal>
    </div>
    <div align="center">
	<input type="button" value="����" onClick="backForm();" alt="">
	</div>
	<p>
		&nbsp;
	</p>
	<script type="text/javascript">
	function backForm() {
		var url = "${ctx}/wap/login.jsp";
		location = url;
	}
</script>