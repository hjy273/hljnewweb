/**
 * jQuery Validation �Զ�����չ��֤
 *
 */
// �����������ֽ�
jQuery.validator.addMethod("byteRangeLength", function(value, element, param) {
    var length = value.length;
    for(var i = 0; i < value.length; i++){
        if(value.charCodeAt(i) > 127){
            length++;
        }
    }
return this.optional(element) || ( length >= param[0] && length <= param[1] );   
}, $.validator.format("��ȷ�������ֵ��{0}-{1}���ֽ�֮��(һ����������2���ֽ�)"));
//����������֤   
jQuery.validator.addMethod("isZipCode", function(value, element) {   
    var zip = /^[0-9]{6}$/;
    return this.optional(element) || (zip.test(value));
}, "����ȷ��д������������");
//��֤����γ��
jQuery.validator.addMethod("isJW", function(value, element) {   
    var jw =/^\d+.\d+,\d+.\d+$/;
    return this.optional(element) || (jw.test(value));
}, "����ȷ�ľ���γ��");
//��֤�绰����
jQuery.validator.addMethod("isTel", function(value, element) {   
    var tel =/((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/;
    return this.optional(element) || (tel.test(value));
}, "����ȷ�ĵ绰����");
//��֤�ֻ�
jQuery.validator.addMethod("isMobile", function(value, element) {   
    var mobile =/^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/;
    return this.optional(element) || (mobile.test(value));
}, "����ȷ���ֻ�����");