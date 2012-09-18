/**
 * jQuery Validation 自定义扩展验证
 *
 */
// 中文字两个字节
jQuery.validator.addMethod("byteRangeLength", function(value, element, param) {
    var length = value.length;
    for(var i = 0; i < value.length; i++){
        if(value.charCodeAt(i) > 127){
            length++;
        }
    }
return this.optional(element) || ( length >= param[0] && length <= param[1] );   
}, $.validator.format("请确保输入的值在{0}-{1}个字节之间(一个中文字算2个字节)"));
//邮政编码验证   
jQuery.validator.addMethod("isZipCode", function(value, element) {   
    var zip = /^[0-9]{6}$/;
    return this.optional(element) || (zip.test(value));
}, "请正确填写您的邮政编码");
//验证经度纬度
jQuery.validator.addMethod("isJW", function(value, element) {   
    var jw =/^\d+.\d+,\d+.\d+$/;
    return this.optional(element) || (jw.test(value));
}, "不正确的经度纬度");
//验证电话号码
jQuery.validator.addMethod("isTel", function(value, element) {   
    var tel =/((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/;
    return this.optional(element) || (tel.test(value));
}, "不正确的电话号码");
//验证手机
jQuery.validator.addMethod("isMobile", function(value, element) {   
    var mobile =/^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/;
    return this.optional(element) || (mobile.test(value));
}, "不正确的手机号码");