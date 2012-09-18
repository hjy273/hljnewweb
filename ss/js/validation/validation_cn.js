/**
 * modified by badqiu (badqiu@gmail.com)
 * web site: http://wiki.javascud.org/display/si/Javascript_EasyValidation
 * bug report: http://jira.javascud.org/browse/SI
 */

/*
 * Really easy field validation with Prototype
 * http://tetlaw.id.au/view/blog/really-easy-field-validation-with-prototype
 * Andrew Tetlaw
 * Version 1.5.3 (2006-07-15)
 * 
 * Copyright (c) 2006 Andrew Tetlaw
 * http://www.opensource.org/licenses/mit-license.php
 */
Validator = Class.create();

Validator.messagesSourceEn = [
	['validation-failed' , 'Validation failed.'],
	['required' , 'This is a required field.'],
	['validate-number' , 'Please enter a valid number in this field.'],
	['validate-digits' , 'Please use numbers only in this field. please avoid spaces or other characters such as dots or commas.'],
	['validate-alpha' , 'Please use letters only (a-z) in this field.'],
	['validate-alphanum' , 'Please use only letters (a-z) or numbers (0-9) only in this field. No spaces or other characters are allowed.'],
	['validate-email' , 'Please enter a valid email address. For example fred@domain.com .'],
	['validate-url' , 'Please enter a valid URL.'],
	['validate-currency-dollar' , 'Please enter a valid $ amount. For example $100.00 .'],
	['validate-one-required' , 'Please select one of the above options.'],
	['validate-integer' , 'Please enter a valid integer in this field'],
	['min-value' , 'min value is %s.'],
	['max-value' , 'max value is %s.'],
	['min-length' , 'min length is %s,current length is %s.'],
	['max-length' , 'max length is %s,current length is %s.'],
	['validate-int-range' , 'Please enter integer value between %s and %s'],
	['validate-float-range' , 'Please enter number between %s and %s'],
	['validate-length-range' , 'Please enter value length between %s and %s,current length is %s'],
	['validate-pattern' , 'Validation failed.'],
	['validate-ip','Please enter a valid IP address'],
	['validate-equals','Conflicting with above value.'],
	['less-than','Input value must be less than above value.'],
	['great-than','Input value must be great than above value.'],
	['validate-date' , 'Please use this date format: yyyy-mm-dd. For example 2006-03-16.'],
	['validate-file' , function(v,elm,args,metadata) {
		return Validation.Utils.format("Please enter file type in [%s]",[args.join(',')]);
	}],
	//�й����е������֤��ʾ��Ϣ
	['validate-id-number','Please enter a valid id number.'],
	['validate-chinese','Please enter chinese'],
	['validate-phone','Please enter a valid phone number,current length is %s.'],
	['validate-mobile-phone','Please enter a valid mobile phone,For example 13910001000.current length is %s.'],
	['validate-zip','Please enter a valid zip code.'],
	['validate-qq','Please enter a valid qq number'],
	['validate-select-one' , 'please select'],
	['validate-select-at-least-one' , 'please select at least one'],
	['validate-integer-float-double' , 'please input valid integer.float or double'],
	['fix-mobile-phone' , 'please input valid fix or mobile phone number'],
	['forserialNum' , 'please input number and it is the last 8 char of  machineSerial'],
	['formachineSerial' , 'please input and it is machineSerial'],
	['validate-alpha-and-number','Input value must be alpha and number']
]

Validator.messagesSourceCn = [
	['validation-failed' , '��֤ʧ��.'],
	['required' , '������'],
	['validate-number' , '��������Ч������.'],
	['validate-digits' , '������һ������. ��������ո�],����,�ֺŵ��ַ�'],
	['validate-alpha' , '������Ӣ����ĸ.'],
	['validate-alphanum' , '������Ӣ����ĸ��������,�����ַ��ǲ������.'],
	['validate-email' , '��������Ч���ʼ���ַ,�� username@example.com.'],
	['validate-url' , '��������Ч��URL��ַ.'],
	['validate-currency-dollar' , 'Please enter a valid $ amount. For example $100.00 .'],
	['validate-one-required' , '������ѡ������ѡ��һ��.'],
	['validate-integer' , '��������ȷ������'],
	['min-value' , '��СֵΪ%s'],
	['max-value' , '���ֵΪ%s'],
	['min-length' , '��С����Ϊ%s,��ǰ����Ϊ%s.'],
	['max-length', '��󳤶�Ϊ%s,��ǰ����Ϊ%s.'],
	['validate-int-range' , '����ֵӦ��Ϊ %s �� %s ������'],
	['validate-float-range' , '����ֵӦ��Ϊ %s �� %s ������'],
	['validate-length-range' , '����ֵ�ĳ���Ӧ���� %s �� %s ֮��,��ǰ����Ϊ%s'],
	['validate-pattern' , '�����ֵ��ƥ��'],
	['validate-ip','��������ȷ��IP��ַ'],
	['validate-equals','�������벻һ��,����������'],
	['less-than','Ӧ��С��ǰ���ֵ'],
	['great-than','Ӧ�ô���ǰ���ֵ'],
	['validate-date' , '��������Ч������,��ʽΪ %s. ����:%s.'],
	['validate-file' , function(v,elm,args,metadata) {
		return Validation.Utils.format("�ļ�����Ӧ��Ϊ[%s]����֮һ",[args.join(',')]);
	}],
	//�й����е������֤��ʾ��Ϣ
	['validate-id-number','������Ϸ������֤����'],
	['validate-chinese','����������'],
	['validate-phone','��������ȷ�ĵ绰����,��:0920-29392929,��ǰ����Ϊ%s.'],
	['validate-mobile-phone','��������ȷ���ֻ�����,��ǰ����Ϊ%s.'],
	['validate-zip','��������Ч����������'],
	['validate-qq','��������Ч��QQ����.'],
	['validate-select-one' , '��ѡ��'],
	['validate-select-at-least-one' , '������ѡ��һ��'],
	['validate-integer-float-double' , '������Ϸ���������С��'],
	['fix-mobile-phone' , '��������ȷ���ֻ����߹̶��绰����(���źͺ��������"-")'],
	['forserialNum' , '������8λ���֣�����Ϊ�ֳ��豸��װ���ϳ�����ŵĺ��λ����'],
	['formachineSerial' , '�������ֳ��豸��װ���ϵĳ������,��ֵ����ĸ�����ݵ����'],
	['validate-alpha-and-number','��������ĸ������']
]

//zhufeng add
Validator.messagesSourceCn.push(['validate-alpha-and-number','��������ĸ������']);
Validator.messagesSourceCn.push(['validate-date-after','�������ڱ�����ڿ�ʼ����']);

Validator.messagesSource = Validator.messagesSourceCn;
Validator.messages = {};
//init Validator.messages
Validator.messagesSource.each(function(ms){
	Validator.messages[ms[0]] = ms[1];
});


Validator.prototype = {
	initialize : function(className, test, options) {
		this.options = Object.extend({
			ignoreEmptyValue : true,
			depends : []
		}, options || {});
		this._test = test ? test : function(v,elm){ return true };
		this._error = Validator.messages[className] ? Validator.messages[className] : Validator.messages['validation-failed'];
		this.className = className;
		this._dependsTest = this._dependsTest.bind(this);
		this._getDependError = this._getDependError.bind(this);
	},
	_dependsTest : function(v,elm) {
		if(this.options.depends && this.options.depends.length > 0) {
			var dependsResult = $A(this.options.depends).all(function(depend){
				return Validation.get(depend).test(v,elm);
			});
			return dependsResult;
		}
		return true;
	},
	test : function(v, elm) {
		if(!this._dependsTest(v,elm))
			return false;
		if(!elm) elm = {}
		return (this.options.ignoreEmptyValue && ((v == null) || (v.length == 0))) || this._test(v,elm,Validation.Utils.getArgumentsByClassName(this.className,elm.className),this);
	},
	_getDependError : function(v,elm,useTitle) {
		var dependError = null;
		$A(this.options.depends).any(function(depend){
			var validation = Validation.get(depend);
			if(!validation.test(v,elm))  {
				dependError = validation.error(v,elm,useTitle)
				return true;
			}
			return false;
		});
		return dependError;
	}, 
	error : function(v,elm,useTitle) {
		var dependError = this._getDependError(v,elm,useTitle);
		if(dependError != null) return dependError;

		var args  = Validation.Utils.getArgumentsByClassName(this.className,elm.className);
		var error = this._error;
		if(typeof error == 'string') {
			if(v) args.push(v.length);
			error = Validation.Utils.format(this._error,args);
		}else if(typeof error == 'function') {
			error = error(v,elm,args,this);
		}else {
			alert('error must type of string or function');
		}
		if(!useTitle) useTitle = elm.className.indexOf('useTitle') >= 0;
		return useTitle ? ((elm && elm.title) ? elm.title : error) : error;
	}
}

var Validation = Class.create();

Validation.prototype = {
	initialize : function(form, options){
		this.options = Object.extend({
			onSubmit : true,
			stopOnFirst : false,
			immediate : false,
			focusOnError : true,
			useTitles : false,
			onFormValidate : function(result, form) {},
			onElementValidate : function(result, elm) {}
		}, options || {});
		this.form = $(form);
		var formId =  Validation.Utils.getElmID($(form));
		Validation.validations[formId] = this;
		if(this.options.onSubmit) Event.observe(this.form,'submit',this.onSubmit.bind(this),false);
		if(this.options.immediate) {
			var useTitles = this.options.useTitles;
			var callback = this.options.onElementValidate;
			Form.getElements(this.form).each(function(input) { // Thanks Mike!
				Event.observe(input, 'blur', function(ev) { Validation.validateElement(Event.element(ev),{useTitle : useTitles, onElementValidate : callback}); });
			});
		}
	},
	onSubmit :  function(ev){
		if(!this.validate()) Event.stop(ev);
	},
	validate : function() {
		var result = false;
		var useTitles = this.options.useTitles;
		var callback = this.options.onElementValidate;
		if(this.options.stopOnFirst) {
			result = Form.getElements(this.form).all(function(elm) { return Validation.validateElement(elm,{useTitle : useTitles, onElementValidate : callback}); });
		} else {
			result = Form.getElements(this.form).collect(function(elm) { return Validation.validateElement(elm,{useTitle : useTitles, onElementValidate : callback}); }).all();
		}
		if(!result && this.options.focusOnError) {
			var first = Form.getElements(this.form).findAll(function(elm){return $(elm).hasClassName('validation-failed')}).first();
			if(first.select) first.select();
			first.focus();
		}
		this.options.onFormValidate(result, this.form);
		return result;
	},
	reset : function() {
		Form.getElements(this.form).each(Validation.reset);
	}
}

Object.extend(Validation, {
	validateElement : function(elm, options){
		options = Object.extend({
			useTitle : false,
			onElementValidate : function(result, elm) {}
		}, options || {});
		elm = $(elm);
		var cn = elm.classNames();
		return result = cn.all(function(value) {
			var test = Validation.test(value,elm,options.useTitle);
			options.onElementValidate(test, elm);
			return test;
		});
	},
	newErrorMsgAdvice : function(name,elm,errorMsg) {
		var advice = '<div class="validation-advice" id="advice-' + name + '-' + Validation.Utils.getElmID(elm) +'" style="display:none">' + errorMsg + '</div>'
		switch (elm.type.toLowerCase()) {
			case 'checkbox':
			case 'radio':
				var p = elm.parentNode;
				if(p) {
					new Insertion.Bottom(p, advice);
				} else {
					new Insertion.After(elm, advice);
				}
				break;
			default:
				new Insertion.After(elm, advice);
	    }
		advice = $('advice-' + name + '-' + Validation.Utils.getElmID(elm));
		return advice;
	},
	showErrorMsg : function(name,elm,errorMsg) {
		var elm = $(elm);
		var prop = Validation._getAdviceProp(name);
		if(!elm[prop]) {
			var advice = Validation.getAdvice(name, elm);
			if(typeof advice == 'undefined') {
				advice = Validation.newErrorMsgAdvice(name,elm,errorMsg);
			}
			if(typeof Effect == 'undefined') {
				advice.style.display = 'block';
			} else {
				new Effect.Appear(advice, {duration : 1 });
			}
		}
		var advice = Validation.getAdvice(name, elm);
		advice.innerHTML = errorMsg;
		elm[prop] = true;
		elm.removeClassName('validation-passed');
		elm.addClassName('validation-failed');
	},
	showErrorMsgByValidator : function(name,elm,useTitle) {
		Validation.showErrorMsg(name,elm,Validation.get(name).error(Validation.Utils.getInputValue(elm),elm,useTitle));
	},
	hideErrorMsg : function(name,elm) {
		var elm = $(elm);
		var prop = Validation._getAdviceProp(name);
		var advice = Validation.getAdvice(name, elm);
		if(typeof advice != 'undefined') {
			if(typeof Effect == 'undefined')
				advice.hide()
			else 
				new Effect.Fade(advice, {duration : 1 });
		}
		
		elm[prop] = '';
		elm.removeClassName('validation-failed');
		elm.addClassName('validation-passed');
	},
	_getAdviceProp : function(validatorName) {
		return '__advice'+validatorName.camelize();
	},
	test : function(name, elm, useTitle) {
		var v = Validation.get(name);
		if(Validation.Utils.isVisible(elm) && !v.test(Validation.Utils.getInputValue(elm),elm)) {
			Validation.showErrorMsgByValidator(name,elm,useTitle);
			return false;
		} else {
			Validation.hideErrorMsg(name,elm);
			return true;
		}
	},
	getAdvice : function(name, elm) {
		return Try.these(
			function(){ return $('advice-' + name + '-' + Validation.Utils.getElmID(elm)) },
			function(){ return $('advice-' + Validation.Utils.getElmID(elm)) }
		);
	},
	reset : function(elm) {
		elm = $(elm);
		var cn = elm.classNames();
		cn.each(function(value) {
			var prop = Validation._getAdviceProp(value);
			if(elm[prop]) {
				var advice = Validation.getAdvice(value, elm);
				advice.hide();
				elm[prop] = '';
			}
			elm.removeClassName('validation-failed');
			elm.removeClassName('validation-passed');
		});
	},
	add : function(className, test, options) {
		var nv = {};
		var testFun = test;
		if(test instanceof RegExp)
			testFun = function(v,elm,args,metadata){ return test.test(v); }
		nv[className] = new Validator(className, testFun, options);
		Object.extend(Validation.methods, nv);
	},
	addAllThese : function(validators) {
		$A(validators).each(function(value) {
				Validation.add(value[0], value[1], (value.length > 2 ? value[2] : {}));
		});
	},
	get : function(name) {
		var resultMethodName;
		for(var methodName in Validation.methods) {
			if(name == methodName) {
				resultMethodName = methodName;
				break;
			}
			if(name.indexOf(methodName) >= 0) {
				resultMethodName = methodName;
			}
		}
		return Validation.methods[resultMethodName] ? Validation.methods[resultMethodName] : new Validator();
	},
	$ : function(formId) {
		return Validation.validations[formId];
	},
	methods : {},
	validations : {},
	Utils : {
		isVisible : function(elm) {
			while(elm && elm.tagName != 'BODY') {
				if(!$(elm).visible()) return false;
				elm = elm.parentNode;
			}
			return true;
		},
		getInputValue : function(elm) {
			var elm = $(elm);
			if(elm.type.toLowerCase() == 'file') {
				return elm.value;
			}else {
				return $F(elm);
			}
		},
		getElmID : function(elm) {
			return elm.id ? elm.id : elm.name;
		},
		format : function(str,args) {
			args = args || [];
			Validation.Utils.assert(args.constructor == Array,"Validation.Utils.format() arguement 'args' must is Array");
			var result = str
			for (var i = 0; i < args.length; i++){
				result = result.replace(/%s/, args[i]);	
			}
			return result;
		},
		// ͨ��classname���ݵĲ�������ͨ��'-'�ָ���������
		// ����ֵ����һ������singleArgument,��:validate-pattern-/[a-c]/gi,singleArgumentֵΪ/[a-c]/gi
		getArgumentsByClassName : function(prefix,className) {
			if(!className || !prefix)
				return [];
			var pattern = new RegExp(prefix+'-(\\S+)');
			var matchs = className.match(pattern);
			if(!matchs)
				return [];
			var results = [];
			results.singleArgument = matchs[1];
			var args =  matchs[1].split('-');
			for(var i = 0; i < args.length; i++) {
				if(args[i] == '') {
					if(i+1 < args.length) args[i+1] = '-'+args[i+1];
				}else{
					results.push(args[i]);
				}
			}
			return results;
		},
		assert : function(condition,message) {
			var errorMessage = message || ("assert failed error,condition="+condition);
			if (!condition) {
				alert(errorMessage);
				throw new Error(errorMessage);
			}else {
				return condition;
			}
		},
		isDate : function(v,dateFormat) {
			var MONTH = "MM";
		   	var DAY = "dd";
		   	var YEAR = "yyyy";
			var regex = '^'+dateFormat.replace(YEAR,'\\d{4}').replace(MONTH,'\\d{2}').replace(DAY,'\\d{2}')+'$';
			if(!new RegExp(regex).test(v)) return false;

			var year = v.substr(dateFormat.indexOf(YEAR),4);
			var month = v.substr(dateFormat.indexOf(MONTH),2);
			var day = v.substr(dateFormat.indexOf(DAY),2);
			
			var d = new Date(Validation.Utils.format('%s/%s/%s',[year,month,day]));
			return ( parseInt(month, 10) == (1+d.getMonth()) ) && 
						(parseInt(day, 10) == d.getDate()) && 
						(parseInt(year, 10) == d.getFullYear() );		
		}
	}
});

Validation.addAllThese([
	['required', function(v) {
				return !((v == null) || (v.length == 0) || /^\s+$/.test(v));
			},{ignoreEmptyValue:false}],
	['validate-number', function(v) {
				return (!isNaN(v) && !/^\s+$/.test(v));
			}],
	['validate-digits', function(v) {
				return !/[^\d]/.test(v);
			}],
	['validate-alphanum', function(v) {
				return !/\W/.test(v)
			}],
	['validate-one-required', function (v,elm) {
				var p = elm.parentNode;
				var options = p.getElementsByTagName('INPUT');
				return $A(options).any(function(elm) {
					return $F(elm);
				});
			},{ignoreEmptyValue : false}],
			
	['validate-alpha',/^[a-zA-Z]+$/],
	['validate-alpha-and-number',/^(([0-9]+[a-zA-Z]+)|([a-zA-Z]+[0-9]+))+$/],
	['validate-email',/\w{1,}[@][\w\-]{1,}([.]([\w\-]{1,})){1,3}$/],
	['validate-url',/^(http|https|ftp):\/\/(([A-Z0-9][A-Z0-9_-]*)(\.[A-Z0-9][A-Z0-9_-]*)+)(:(\d+))?\/?/i],
	// [$]1[##][,###]+[.##]
	// [$]1###+[.##]
	// [$]0.##
	// [$].##
	['validate-currency-dollar',/^\$?\-?([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/]
]);

//custom validate start

Validation.addAllThese([
	/**
	 * Usage : validate-equals-otherInputId
	 * Example : validate-equals-username or validate-equals-email etc..
	 */
	['validate-equals', function(v,elm,args,metadata) {
				return $F(args[0]) == v;
			},{ignoreEmptyValue:false}],
	/**
	 * Usage : less-than-otherInputId
	 */
	['less-than', function(v,elm,args,metadata) {
				if(Validation.get('validate-number').test(v) && Validation.get('validate-number').test($F(args[0])))
					return parseFloat(v) < parseFloat($F(args[0]));
				return v < $F(args[0]);
			}],
	/**
	 * Usage : great-than-otherInputId
	 */
	['great-than', function(v,elm,args,metadata) {
				if(Validation.get('validate-number').test(v) && Validation.get('validate-number').test($F(args[0])))
					return parseFloat(v) > parseFloat($F(args[0]));
				return v > $F(args[0]);
			}],
	/*
	 * Usage: min-length-number
	 * Example: min-length-10
	 */
	['min-length',function(v,elm,args,metadata) {
		return v.length >= parseInt(args[0]);
	}],
	/*
	 * Usage: max-length-number
	 * Example: max-length-10
	 */
	['max-length',function(v,elm,args,metadata) {
		return v.length <= parseInt(args[0]);
	}],
	/*
	 * Usage: validate-file-type1-type2-typeX
	 * Example: validate-file-png-jpg-jpeg
	 */
	['validate-file',function(v,elm,args,metadata) {
		return $A(args).any(function(extentionName) {
			return new RegExp('\\.'+extentionName+'$','i').test(v);
		});
	}],
	/*
	 * Usage: validate-float-range-minValue-maxValue
	 * Example: -2.1 to 3 = validate-float-range--2.1-3
	 */
	['validate-float-range',function(v,elm,args,metadata) {
		return (parseFloat(v) >= parseFloat(args[0]) && parseFloat(v) <= parseFloat(args[1]))
	},{depends : ['validate-number']}],
	/*
	 * Usage: validate-int-range-minValue-maxValue
	 * Example: -10 to 20 = validate-int-range--10-20
	 */
	['validate-int-range',function(v,elm,args,metadata) {
		return (parseInt(v) >= parseInt(args[0]) && parseInt(v) <= parseInt(args[1]))
	},{depends : ['validate-integer']}],
	/*
	 * Usage: validate-length-range-minLength-maxLength
	 * Example: 10 to 20 = validate-length-range-10-20
	 */
	['validate-length-range',function(v,elm,args,metadata) {
		return (v.length >= parseInt(args[0]) && v.length <= parseInt(args[1]))
	}],
	/*
	 * Usage: max-value-number
	 * Example: max-value-10
	 */
	['max-value',function(v,elm,args,metadata) {
		return parseFloat(v) <= parseFloat(args[0]);
	},{depends : ['validate-number']}],
	/*
	 * Usage: min-value-number
	 * Example: min-value-10
	 */
	['min-value',function(v,elm,args,metadata) {
		return parseFloat(v) >= parseFloat(args[0]);
	},{depends : ['validate-number']}],
	/*
	 * Usage: validate-pattern-RegExp
	 * Example: <input id='sex' class='validate-pattern-/^[fm]$/i'>
	 */
	['validate-pattern',function(v,elm,args,metadata) {
		return  eval('('+args.singleArgument+')').test(v);
	}],
	/*
	 * Example: <input id='email' class='validate-ajax' validateUrl='http://localhost:8080/validate-email.jsp' validateFailedMessage='email already exists'>
	 */
	['validate-ajax',function(v,elm,args,metadata) {
		var request = new Ajax.Request(args.singleArgument,{
			//���������URL����: validate-email.jsp?email=badqiu@gmail.com&what=email&value=badqiu@gmail.com
			//whatΪinput��name,valueΪinput��value
			parameters : Form.Element.serialize(elm),
			asynchronous : false,
			method : "get",
			contentType : "text/html"
		});
		
		var responseText = request.transport.responseText;
		if("" == responseText.strip()) return true;
		metadata._error = responseText;
		return false;
	}],
	['validate-ajax-post',function(v,elm,args,metadata) {
		var request = new Ajax.Request(args.singleArgument,{
			//���������URL����: validate-email.jsp?email=badqiu@gmail.com&what=email&value=badqiu@gmail.com
			//whatΪinput��name,valueΪinput��value
			parameters : Form.Element.serialize(elm)+Validation.Utils.format("&what=%s&value=%s",[elm.name,encodeURIComponent(v)]),
			asynchronous : false,
			method : "post",
			contentType : "text/html"
		});
		
		var responseText = request.transport.responseText;
		if("" == responseText.strip()) return true;
		metadata._error = responseText;
		return false;
	}],
	['validate-date', function(v,elm,args,metadata) {
			var dateFormat = args.singleArgument || 'yyyy-MM-dd';
			metadata._error = Validation.Utils.format(Validator.messages[metadata.className],[dateFormat,dateFormat.replace('yyyy','2006').replace('MM','03').replace('dd','12')]);
			return Validation.Utils.isDate(v,dateFormat);
		}],	
	['validate-integer',/^[-+]?[\d]+$/],
	['validate-ip',/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/],
	
	//�й������֤��ʼ
	['validate-id-number',function(v,elm,args,metadata) {
		if(!(/^\d{17}(\d|x)$/i.test(v) || /^\d{15}$/i.test(v))) return false;
		if((parseInt(v.substr(0,2)) < 11) || (parseInt(v.substr(0,2)) > 91)) return false;
		var forTestDate = v.length == 18 ? v : v.substr(0,6)+"19"+v.substr(6,15);
		var birthday = forTestDate.substr(6,8);
		if(!Validation.Utils.isDate(birthday,'yyyyMMdd')) return false;
		if(v.length == 18) {
			v = v.replace(/x$/i,"a");
			var verifyCode = 0;
			for(var i = 17;i >= 0;i--)   
            	verifyCode += (Math.pow(2,i) % 11) * parseInt(v.charAt(17 - i),11);
            if(verifyCode % 11 != 1) return false;
		}
		return true;
	}],
	['validate-chinese',/^[\u4e00-\u9fa5]+$/],
	['validate-phone',/^((0[1-9]{3})?(0[12][0-9])?[-])?\d{6,8}$/],
	['validate-mobile-phone',/(^0?[1][3456789][0-9]{9}$)/],
	['validate-zip',/^[1-9]\d{5}$/],
	['validate-qq',/^[1-9]\d{4,8}$/],
	['validate-select-one', function(v) {
				return (!isNaN(v) && !/^\s+$/.test(v));
	}],
	['validate-select-at-least-one', function(v) {
		return !((v == null) || (v.length == 0) || /^\s+$/.test(v));
	},{ignoreEmptyValue:false}],
	['validate-integer-float-double',/^\d*\.?\d*$/],
	['fix-mobile-phone',/(^(0\d{2,3})?\d{7,8})$|(1[0-9]{10})/] ,
	['forserialNum', function(v) {
	            //return !((v == null) || (v.length == 0) || /^\s+$/.test(v)) && !/\W/.test(v);
	            return (!isNaN(v) && !/^\s+$/.test(v))  &&  !((v == null) || (v.length == 0) || /^\s+$/.test(v));
			},{ignoreEmptyValue:false}],
	['formachineSerial', function(v) {
				return !((v == null) || (v.length == 0) || /^\s+$/.test(v)) && !/\W/.test(v);
			},{ignoreEmptyValue:false}]
]);

//zhufeng add
Validation.addAllThese([
	['validate-alpha-and-number',/^(([0-9]+[a-zA-Z]+)|([a-zA-Z]+[0-9]+))+$/],
	['validate-date-after', function(v,elm,args,metadata) {
		if(Validation.get('validate-number').test(v) && Validation.get('validate-number').test($F(args[0])))
			return parseFloat(v) > parseFloat($F(args[0]));
		return v > $F(args[0]);
	}]
]);

Validation.autoBind = function() {
	 var forms = document.getElementsByClassName('required-validate');
	 $A(forms).each(function(form){
		var validation = new Validation(form,{immediate:true,useTitles:true});
		Event.observe(form,'reset',function() {validation.reset();},false);
	 });
};

Event.observe(window,'load',Validation.autoBind,false);